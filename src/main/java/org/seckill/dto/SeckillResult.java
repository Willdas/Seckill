package org.seckill.dto;

/**
 * 
 * @ClassName:  SeckillResult   
 * @Description:TODO(封装结果)   
 * @author: willdas 
 * @param <T>
 * @date:   2018年10月25日 下午9:54:28   
 *
 */
public class SeckillResult<T> {
	
	private boolean success;
	
	private T data;
	
	private String error;

	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
