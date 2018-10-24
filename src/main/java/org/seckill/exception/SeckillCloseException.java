package org.seckill.exception;

/**
 * 
 * @ClassName: SeckillCloseException
 * @Description:TODO(秒杀关闭异常)
 * @author: willdas
 * @date: 2018年10月24日 下午9:10:05
 *
 */
public class SeckillCloseException extends SeckillException {

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillCloseException(String message) {
		super(message);
	}

	
}
