package org.seckill.entry;

import java.util.Date;

/**
 * 
 * @ClassName:  SuccessSeckilled   
 * @Description:TODO(秒杀成功明细Dao接口)   
 * @author: willdas 
 * @date:   2018年10月23日 下午11:15:47   
 *
 */
public class SuccessSeckilled {
	
	private long seckillId;
	
	private long userPhone;
	
	private short state;
	
	private Date creaeTime;

	//多对一
	private Seckill seckill;
	
	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreaeTime() {
		return creaeTime;
	}

	public void setCreaeTime(Date creaeTime) {
		this.creaeTime = creaeTime;
	}
	@Override
	public String toString() {
		return "SuccessSeckilled [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", creaeTime=" + creaeTime + ", seckill=" + seckill + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creaeTime == null) ? 0 : creaeTime.hashCode());
		result = prime * result + (int) (seckillId ^ (seckillId >>> 32));
		result = prime * result + state;
		result = prime * result + (int) (userPhone ^ (userPhone >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SuccessSeckilled other = (SuccessSeckilled) obj;
		if (creaeTime == null) {
			if (other.creaeTime != null)
				return false;
		} else if (!creaeTime.equals(other.creaeTime))
			return false;
		if (seckillId != other.seckillId)
			return false;
		if (state != other.state)
			return false;
		if (userPhone != other.userPhone)
			return false;
		return true;
	}

	
}
