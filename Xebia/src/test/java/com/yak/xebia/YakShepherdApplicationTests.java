package com.yak.xebia;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.yak.domain.Herd;
import com.yak.domain.Herd.Yak;
import com.yak.domain.LabYak;
import com.yak.domain.OrderInput;
import com.yak.domain.Stock;

/**
 * Basic integration tests for yak service.
 *
 * @author Pallavi Agarwal
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = YakShepherdApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
public class YakShepherdApplicationTests {

	@Value("${local.server.port}")
	private int port;
	@Autowired
	@InjectMocks
	private YakService yakService;
	
	@Mock
	private HerdListCache herdCache;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		List<LabYak> yakCache = new ArrayList<LabYak>();
		LabYak yak1 = new LabYak("Betty-1", "4", "f");
		LabYak yak2 = new LabYak("Betty-2", "8", "f");
		LabYak yak3 = new LabYak("Betty-3", "9.5", "f");
		yakCache.add(yak1);
		yakCache.add(yak2);
		yakCache.add(yak3);
		Mockito.when(herdCache.getYakCache()).thenReturn(yakCache);
		Mockito.when(herdCache.getStock()).thenReturn(null);
	}
	
	@Test
	public void testStockCalculationLogic() throws Exception {
		Stock stock = yakService.getStock(13);
		Assert.assertNotNull(stock);
		Assert.assertEquals(new Double(1104.48), stock.getMilk());
		Assert.assertEquals(new Integer(3), stock.getSkins());
	}
	
	@Test
	public void testAllYakShavedAfterYearOne() throws Exception {
		List<LabYak> yakCache = new ArrayList<LabYak>();
		LabYak yak1 = new LabYak("Betty-1", ".5", "f");
		LabYak yak2 = new LabYak("Betty-2", ".9", "f");
		yakCache.add(yak1);
		yakCache.add(yak2);
		Mockito.when(herdCache.getYakCache()).thenReturn(yakCache);
		Mockito.when(herdCache.getStock()).thenReturn(null);
		Stock stock = yakService.getStock(10);
		Assert.assertNotNull(stock);
		Assert.assertEquals(new Integer(0), stock.getSkins());
		
		Stock stock1 = yakService.getStock(100);
		Assert.assertNotNull(stock1);
		Assert.assertTrue(stock1.getSkins() > 0);
	}

	@Test
	public void testYakDiesAtAgeOf10() throws Exception {
		List<LabYak> yakCache = new ArrayList<LabYak>();
		LabYak yak1 = new LabYak("Betty-1", "9.5", "f");
		LabYak yak2 = new LabYak("Betty-2", "9.9", "f");
		yakCache.add(yak1);
		yakCache.add(yak2);
		Mockito.when(herdCache.getYakCache()).thenReturn(yakCache);
		Mockito.when(herdCache.getStock()).thenReturn(null);
		Herd herd = yakService.getHerdList(150);
		Assert.assertNotNull(herd);
		for (Yak labYak : herd.getHerd()) {
			Assert.assertEquals(10.0f, labYak.getAge(), 0.0);
		}
	}
	
	@Test
	public void testOrderSuccessIfInStock() throws Exception {
		OrderInput inputJson = new OrderInput();
		inputJson.setCustomer("Pallavi");
		Stock order = new Stock();
		order.setMilk(1100.00);
		order.setSkins(2);
		inputJson.setOrder(order);
		Stock stockOutput = yakService.placeOrder(13, inputJson );
		Assert.assertNotNull(stockOutput);
		Assert.assertEquals(order.getMilk(), stockOutput.getMilk());
		Assert.assertEquals(order.getSkins(), stockOutput.getSkins());
	}
	
}
