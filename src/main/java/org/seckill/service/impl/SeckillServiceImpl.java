package org.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.RedisDao;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessSeckilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entry.Seckill;
import org.seckill.entry.SuccessSeckilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * 
 * @ClassName: SeckillServiceImpl
 * @Description:TODO(秒杀实现类)
 * @author: willdas
 * @date: 2018年10月24日 下午11:11:38
 *
 */
@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(SeckillServiceImpl.class);

	@Autowired
	private SeckillDao seckillDao;

	@Autowired
	private SuccessSeckilledDao successSeckilledDao;

	@Autowired
	private RedisDao redisDao;

	// md5盐值字符串 用于混淆MD5
	private final String slat = "fassafafrqrqrw2421425&*$%";

	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 4);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		// 缓冲优化
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else{
				redisDao.putSeckill(seckill);
			}
		}
		Date stateTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		// 当前时间
		Date nowTime = new Date();
		if (nowTime.getTime() < stateTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), stateTime.getTime(), endTime.getTime());
		}
		String md5 = getMD5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	// 生成MD5
	private String getMD5(long seckillId) {
		String base = seckillId + "/" + slat;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	/*
	 * 使用注解控制事务方法的优点：
	 *  1.开发团队 达成一致 明确标注事务方法的编程风格 
	 *  2.保证事务方法的执行时间尽可能短 
	 *  3.不是所有方法都需要事务
	 *  只读不需要 只有一条数据修改不需要
	 */
	@Transactional
	@Override
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws SeckillException, SeckillException, RepeatKillException {
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillCloseException("秒杀被串改");
		}
		// 执行秒杀逻辑 减库存 +记录购买行为
		Date nowTime = new Date();
		try {
			// 记录购买行为
			int insertCount = successSeckilledDao.insertSuccessKilled(seckillId, userPhone, nowTime);
			// 唯一的seckillId userPhone
			if (insertCount <= 0) {
				// 重复秒杀
				throw new RepeatKillException("重复秒杀");
			}else{
				// 减库存  修改涉及到commit 和 rollback
				int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
				if (updateCount <= 0) {
					// 没有更新到记录 秒杀结束
					throw new SeckillCloseException("秒杀结束");
				} else {
					// 秒杀成功
					SuccessSeckilled successSeckilled = successSeckilledDao.queryByIdWithSeckill(seckillId, userPhone);
					return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successSeckilled);
				}
			}
		} catch (SeckillCloseException e1) {
			throw e1;
		} catch (RepeatKillException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译期异常 转化为运行期异常
			throw new SeckillException("秒杀 失败" + e.getMessage());
		}
		
		
	}

	@Override
	public SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5){
		if (md5 == null || !md5.equals(getMD5(seckillId))) {
			throw new SeckillCloseException("秒杀被串改");
		}
		Date killDate = new Date();
		java.util.Map<String, Object> paramMap = new HashMap<String,Object>();
		paramMap.put("seckillId", seckillId);		
		paramMap.put("phone", userPhone);		
		paramMap.put("killTime", killDate);		
		paramMap.put("result", null);		
		try {
			seckillDao.killByProcedure(paramMap);
			int result = MapUtils.getInteger(paramMap, "result", -2);
			if (result == 1) {
				SuccessSeckilled sk = successSeckilledDao.queryByIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS,sk);
			}else{
				return new SeckillExecution(seckillId, SeckillStateEnum.stateOf(result));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}
}
