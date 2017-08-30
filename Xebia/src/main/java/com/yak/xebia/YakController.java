package com.yak.xebia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yak.domain.Herd;
import com.yak.domain.OrderInput;
import com.yak.domain.Stock;

@Controller
@RequestMapping(value = "/yak-shop")
public class YakController {

	@Autowired
	private YakService yakService;

	@RequestMapping(value = "/stock/{elapsedTime}", method = RequestMethod.GET)
	@ResponseBody
	public Stock stock(@PathVariable int elapsedTime,
			final HttpServletRequest request) throws Exception {
		return yakService.getStock(elapsedTime);

	}
	
	@RequestMapping(value = "/herd/{elapsedTime}", method = RequestMethod.GET)
	@ResponseBody
	public Herd herd(@PathVariable int elapsedTime,
			final HttpServletRequest request) throws Exception {
		return yakService.getHerdList(elapsedTime);

	}
	
	@RequestMapping(value = "/order/{elapsedTime}", method = RequestMethod.POST)
	@ResponseBody
	public Stock order(@PathVariable int elapsedTime,
			@RequestBody OrderInput inputJson, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Stock stock =  yakService.placeOrder(elapsedTime, inputJson);
		if(stock == null || (stock.getMilk() == null && stock.getSkins() == null)){
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			return stock;
		}
		if( stock != null && (stock.getMilk() != null && stock.getSkins() == null) || (stock.getSkins() != null && stock.getMilk() == null)) {
			response.setStatus(HttpStatus.PARTIAL_CONTENT.value());
			return stock;
		}
		response.setStatus(HttpStatus.CREATED.value());
		return stock;
	}


}
