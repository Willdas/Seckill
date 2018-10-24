package org.seckill.exception;

/**
 * 
 * @ClassName:  SeckillException   
 * @Description:TODO(秒杀异常)   
 * @author: willdas 
 * @date:   2018年10月24日 下午9:15:32   
 *
 */
public class SeckillException extends RuntimeException{

	public SeckillException(String message) {
		super(message);
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}	
}
