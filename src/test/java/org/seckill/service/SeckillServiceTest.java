package org.seckill.service;


import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entry.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> seckillList = seckillService.getSeckillList();
		for (Seckill seckill : seckillList) {
			logger.info(seckill.toString());
		}
	}

	@Test
	public void testGetById() {
		Seckill seckill = seckillService.getById(1001);
		logger.info(seckill.toString());
	}

	@Test
	public void testExportSeckill() {
		long seckillId = 1000;
		long userPhone = 13623121241L;
		Exposer export = seckillService.exportSeckillUrl(seckillId);
		if (export.isExposed()) {
			logger.info(export.toString());
			try {
				SeckillExecution execute = seckillService.executeSeckill(seckillId, userPhone, "c168380d46b0a8d31308cfa0ffdd7ddb");
				logger.info(execute.toString());
			} catch (RepeatKillException e) {
				logger.error(e.getMessage());
			}catch (SeckillCloseException e) {
				logger.error(e.getMessage());
			}
		}else {
			logger.warn("秒杀未开启->",export);
		}
	}
	@Test
	public void testExecuteSeckillProcedure() {
		long seckillId = 1000;
		long userPhone = 13623121541L;
		Exposer export = seckillService.exportSeckillUrl(seckillId);
		String md5 = export.getMd5();
		
		SeckillExecution executeSeckillProcedure = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
		System.out.println(executeSeckillProcedure);
	}
	
}
