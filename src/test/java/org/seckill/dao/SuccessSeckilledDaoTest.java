package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.SuccessSeckilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessSeckilledDaoTest {
	
	@Autowired
	private SuccessSeckilledDao successSeckilledDao;

	@Test
	public void testInsertSuccessKilled() {
		int insertSuccessKilled = successSeckilledDao.insertSuccessKilled(1001, Long.valueOf("13902121234"),new Date());
		System.out.println(insertSuccessKilled);
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessSeckilled seckill = successSeckilledDao.queryByIdWithSeckill(1001,Long.valueOf("13902121234"));
		System.out.println(seckill);
		System.out.println(seckill.getSeckill());
	}

}
