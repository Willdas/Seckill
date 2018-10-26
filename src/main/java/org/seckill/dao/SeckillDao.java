package org.seckill.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.seckill.entry.Seckill;


/**
 * 
 * @ClassName:  SeckillDao   
 * @Description:TODO(查询商品Dao接口)   
 * @author: willdas 
 * @date:   2018年10月23日 下午11:15:18   
 *
 */
public interface SeckillDao {
	
	/**
	 * 
	 * @Title: reduceNumber   
	 * @Description: TODO(减库存)   
	 * @param: @param seckillId
	 * @param: @param killTime
	 * @param: @return      
	 * @return: int      
	 * @throws
	 */
	int reduceNumber(@Param("seckillId")long seckillId, @Param("killTime")Date killTime);
	
	/**
	 * 
	 * @Title: queryById   
	 * @Description: TODO(根据商品id查询商品)   
	 * @param: @param seckillId
	 * @param: @return      
	 * @return: Seckill      
	 * @throws
	 */
	Seckill queryById(long seckillId);
	
	/**
	 * 
	 * @Title: queryAll   
	 * @Description: TODO(查询所有商品)   
	 * @param: @param offet
	 * @param: @param limit
	 * @param: @return      
	 * @return: List<Seckill>      
	 * @throws
	 */
	List<Seckill> queryAll(@Param("offset")int offset, @Param("limit")int limit);
	
	/**
	 * 
	 * @Title: killByProcedure   
	 * @Description: TODO(调用存储过程完成秒杀)   
	 * @param: @param paramMap      
	 * @return: void      
	 * @throws
	 */
	void killByProcedure(Map<String, Object> paramMap);
	
	
}
