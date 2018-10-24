package org.seckill.exception;

/**
 * 
 * @ClassName:  RepeatKillException   
 * @Description:TODO(重复秒杀异常(运行期异常))   
 * @author: willdas 
 * @date:   2018年10月24日 下午9:03:51   
 *
 */
public class RepeatKillException extends SeckillException{

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}
	
}
