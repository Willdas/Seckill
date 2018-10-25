package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entry.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

/**
 * 
 * @ClassName: SeckillService
 * @Description:TODO(秒杀商品接口 站在'使用者'角度设计接口)
 * 三个方面: 方法定义粒度，参数，返回类型(return 类型/异常)
 * @author: willdas
 * @date: 2018年10月24日 下午8:44:10
 *
 */
public interface SeckillService {

	/**
	 * 
	 * @Title: getSeckillList   
	 * @Description: TODO(查询所有秒杀记录)   
	 * @param: @return      
	 * @return: List<Seckill>      
	 * @throws
	 */
	public List<Seckill> getSeckillList();
	
	/**
	 * 
	 * @Title: getById   
	 * @Description: TODO(查询单个秒杀记录)   
	 * @param: @param seckillId
	 * @param: @return      
	 * @return: Seckill      
	 * @throws
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 
	 * @Title: exportSeckillUrl   
	 * @Description: TODO(秒杀开启时输出秒杀接口的地址 否则输出系统时间和秒杀时间)   
	 * @param: @param seckillId      
	 * @return: void      
	 * @throws
	 */
	Exposer exportSeckillUrl(long seckillId);
	
	/**
	 * 
	 * @Title: executeSeckill   
	 * @Description: TODO(执行秒杀操作)   
	 * @param: @param seckillId
	 * @param: @param userPhone
	 * @param: @param md5      
	 * @return: void      
	 * @throws
	 */
	SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, SeckillCloseException, RepeatKillException;
	
}
