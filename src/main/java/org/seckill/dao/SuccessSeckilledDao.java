package org.seckill.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.SuccessSeckilled;
import org.seckill.exception.RepeatKillException;

/**
 * 
 * @ClassName:  SuccessSeckilledDao   
 * @Description:TODO(秒杀商品明细Dao接口)   
 * @author: willdas 
 * @date:   2018年10月23日 下午11:16:25   
 *
 */
public interface SuccessSeckilledDao {
	
	/**
	 * 
	 * @Title: insertSuccessKilled   
	 * @Description: TODO(查询秒杀成功明细)   
	 * @param: @param seckillId
	 * @param: @param userPhone
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone, @Param("createTime") Date createTime);
	
	/**
	 * 
	 * @Title: queryByIdWithSeckill   
	 * @Description: TODO(根据ID查询秒杀明细并查询出秒杀商品实体对象)   
	 * @param: @param seckillId
	 * @param: @return      
	 * @return: SuccessSeckilled      
	 * @throws
	 */
	SuccessSeckilled queryByIdWithSeckill(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);

}
