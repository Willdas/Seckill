package org.seckill.dao;

import org.seckill.entry.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 
 * @ClassName:  RedisDao   
 * @Description:TODO(Redis缓冲优化)   
 * @author: willdas 
 * @date:   2018年10月26日 下午7:28:53   
 *
 */
public class RedisDao {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JedisPool jedisPool;
	
	private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

	public RedisDao(String ip, int port) {
		super();
		jedisPool = new JedisPool(ip, port);
	}
	
	public Seckill getSeckill(long seckillId){
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckillId;
				//对象序列化 get -> byte[] -> 序列化 -> Object
				byte[] bytes = jedis.get(key.getBytes());
				if (bytes != null) {
					Seckill seckill = schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes,seckill, schema);
					return seckill;
				}
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	public String putSeckill(Seckill seckill){
		//set Object -> 序列化 -> byte[]
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:" + seckill.getSeckillId();
				//对象序列化 get -> byte[] -> 序列化 -> Object
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
										LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeOut = 60 * 60;
				String result = jedis.setex(key.getBytes(), timeOut, bytes);
				return result;
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
