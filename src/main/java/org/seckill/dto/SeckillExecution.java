package org.seckill.dto;

import org.seckill.entry.SuccessSeckilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 
 * @ClassName:  SeckillExecution   
 * @Description:TODO(封装秒杀执行后的结果)   
 * @author: willdas 
 * @date:   2018年10月24日 下午8:58:33   
 *
 */
public class SeckillExecution {
	
	private long seckillId;
	
	//秒杀执行结果状态
	private int state;
	
	//状态表示
	private String stateInfo;
	
	//秒杀成功对象
	private SuccessSeckilled successSeckilled;
	
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum, SuccessSeckilled successSeckilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successSeckilled = successSeckilled;
	}
	
	public SeckillExecution(long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessSeckilled getSuccessSeckilled() {
		return successSeckilled;
	}

	public void setSuccessSeckilled(SuccessSeckilled successSeckilled) {
		this.successSeckilled = successSeckilled;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successSeckilled=" + successSeckilled + "]";
	}
	
	
}
