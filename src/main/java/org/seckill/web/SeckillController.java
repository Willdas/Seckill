package org.seckill.web;

import java.util.Date;
import java.util.List;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entry.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @ClassName: SeckillController
 * @Description:TODO(秒杀Controller)
 * @author: willdas
 * @date: 2018年10月25日 下午9:42:48
 *
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	/**
	 * 
	 * @Title: list   
	 * @Description: TODO(查询所有秒杀商品)   
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public String list(Model model){
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("list",seckillList);
		return "list";
	}
	
	/**
	 * 
	 * @Title: detail   
	 * @Description: TODO(查询单个秒杀商品信息)   
	 * @param: @param seckillId
	 * @param: @param model
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	@RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model){
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}
	
	/**
	 * 
	 * @Title: exposer   
	 * @Description: TODO(暴露秒杀链接)   
	 * @param: @param seckillId
	 * @param: @return      
	 * @return: SeckillResult<Exposer>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/{seckillId}/exposer", 
					method = RequestMethod.POST,
					produces = {"application/json;charset=UTF-8"})
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId){
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @Title: execute   
	 * @Description: TODO(开始秒杀)   
	 * @param: @param seckillId
	 * @param: @param md5
	 * @param: @param userPhone
	 * @param: @return      
	 * @return: SeckillResult<SeckillExecution>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/{seckillId}/{md5}/execution",
					method = RequestMethod.POST,
					produces = {"application/json;charset=UTF-8"})
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
													@PathVariable("md5") String md5,
													@CookieValue(value = "killPhone", required = false) Long userPhone){
		SeckillResult<SeckillExecution> result = null;
		if (userPhone == null) {
			result = new SeckillResult<SeckillExecution>(false, "未注册");
		}
		try {
			//调用存储过程
			SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, userPhone, md5);
			result = new SeckillResult<SeckillExecution>(true, execution);
		} catch(RepeatKillException e){
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, execution);
		}catch (SeckillCloseException e) {
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, execution);
		}catch (Exception e) {
			logger.error(e.getMessage());
			SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, execution);
		}
		return result;
	}
	
	/**
	 *
	 * @Title: seckillTime   
	 * @Description: TODO(秒杀时间)   
	 * @param: @return      
	 * @return: SeckillResult<Long>      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/time/now",method = RequestMethod.GET)
	public SeckillResult<Long> seckillTime(){
		Date date = new Date();
		return new SeckillResult<Long>(true, date.getTime());
	}
}
 