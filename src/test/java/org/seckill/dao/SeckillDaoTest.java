package org.seckill.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entry.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	@Autowired
	private SeckillDao seckillDao;
	
	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckillDao.queryById(id);
		System.out.println(seckill);
	}
	
	@Test
	public void testReduceNumber() {
		Date date = new Date();
		int reduceNumber = seckillDao.reduceNumber(1000, date);
		System.out.println(reduceNumber);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> queryAll = seckillDao.queryAll(0, 10);
		for (Seckill seckill : queryAll) {
			System.out.println(seckill);
		}
	}

}
