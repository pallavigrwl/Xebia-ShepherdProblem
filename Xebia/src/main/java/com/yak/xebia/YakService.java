package com.yak.xebia;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yak.domain.Herd;
import com.yak.domain.HerdPojo;
import com.yak.domain.LabYak;
import com.yak.domain.OrderInput;
import com.yak.domain.Stock;
import com.yak.domain.Herd.Yak;

@Component
public class YakService {
	@Autowired
	private ServiceProperties configuration;
	private int daysInYear ;
	private int maxAgeOfYak;
	private int minAgeForShave ;
	private static final Logger LOG = Logger.getLogger(YakService.class);
	
	@PostConstruct
	public void init(){
		daysInYear = configuration.getDaysInYear() ;
		maxAgeOfYak = configuration.getMaxAgeOfYak();
		minAgeForShave = configuration.getMinimumAgeToShave();
	}
	
	@Autowired
	private HerdListCache cache;

	public Stock calculateStock(HerdPojo herdPojo, int elapsedTime) throws Exception {
		Stock stock = null;
		List<LabYak> labyakList = herdPojo.getLabyakList();
		if (labyakList == null) {
			return stock;
		}
		double milk = 0;
		int wools = 0;
		for (int i = 0; i < elapsedTime; i++) {
			for (LabYak labYak : labyakList) {
				if (!labYak.isAlive()) {
					continue;
				}
				// at age 0 all are shaved
				if (i == 0 && labYak.getAgeInDays() > minAgeForShave*daysInYear) {
					++wools;
				}

				int yakAgeToday = labYak.getAgeInDays() + i;
				// labYak.setAge(String.valueOf((float)yakAgeToday/daysInYear));
				System.out.println("lab yak age today for " + labYak + " : " + yakAgeToday);
				// at age 0 all are shaved
				if (yakAgeToday > daysInYear) {
					int j = (int) (8 + labYak.getAgeInDays() * 0.01) + 1;
					if (i > 0 && i % j == 0) {
						wools++;
					}
				}
				milk += 50 - yakAgeToday * .03;
				System.out.println("Milk of " + labYak.getName() + " at day " + i + ": " + milk);
				System.out.println("wool of " + labYak.getName() + " at day " + i + ": " + wools);
			}
		}
		stock = new Stock();
		stock.setMilk((Math.round(milk * 1000) / 1000.00));
		stock.setSkins(wools);
		LOG.debug("**********************OUTPUT**************************");
		LOG.info("In stock:");
		LOG.info("\t" + (Math.round(milk * 1000) / 1000.00) + " liters of milk");
		LOG.info(("\t" + wools + " skins of wool"));
		LOG.info("Herd:");
		float elapsedTimeInYear = (float) elapsedTime / daysInYear;
		for (LabYak labYak : labyakList) {
			float f = Float.valueOf(labYak.getAge()) + elapsedTimeInYear;
			// labYak.setAge(String.valueOf(f));
			LOG.info("\t" + labYak.getName() + " " + f + " years old");
		}
		return stock;

	}

	/*
	 * public static void main(String[] args) throws Exception { File fXmlFile =
	 * new File(
	 * "D:/workspacePaymntServices/spring/src/main/resources/SampleInputHerd.xml"
	 * ); System.out.println(fXmlFile); DocumentBuilderFactory dbFactory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder dBuilder =
	 * dbFactory.newDocumentBuilder(); Document doc = dBuilder.parse(fXmlFile);
	 * Element element = doc.getDocumentElement(); JAXBContext context =
	 * JAXBContext.newInstance(HerdPojo.class); Unmarshaller unmarshaller =
	 * context.createUnmarshaller(); JAXBElement<HerdPojo> loader =
	 * unmarshaller.unmarshal(element, HerdPojo.class); HerdPojo inputFromXml =
	 * loader.getValue(); Stock stock = calculateStock(inputFromXml, 13); }
	 */

	public Stock getStock(int elapsedTime) throws Exception {
		List<LabYak> yakCache = cache.getYakCache();
		if (yakCache == null) {
			return null;
		}
		if (cache.getStock() != null && elapsedTime == cache.getStock().getElapsedTime()) {
			return cache.getStock();
		}
		HerdPojo herdPojo = new HerdPojo();
		herdPojo.setLabyakList(yakCache);
		return calculateStock(herdPojo, elapsedTime);
	}

	public Herd getHerdList(int elapsedTime) {
		Herd herd = new Herd();
		herd.setHerd(new TreeSet<Yak>(new Comparator<Herd.Yak>() {
			public int compare(Yak o1, Yak o2) {
				return o1.getName().compareTo(o2.getName());
			};
		}));
		if (cache != null && cache.getYakCache() != null && !cache.getYakCache().isEmpty()) {
			for (LabYak labYak : cache.getYakCache()) {
				if (!labYak.isAlive()) {
					continue;
				}
				float age = Float.valueOf(labYak.getAge());
				Yak yak = herd.new Yak(labYak.getName(), age, age);
				if (herd.getHerd() != null) {
					herd.getHerd().add(yak);
				}
				int ageOfYakAt0day = labYak.getAgeInDays();
				for (int i = 0; i < elapsedTime; i++) {
					if (yak.getAge() >= maxAgeOfYak) {
						break;
					}
					int yakAgeInDaysToday = labYak.getAgeInDays() + i + 1;
					float ageToday = (float) yakAgeInDaysToday / daysInYear;
					yak.setAge(ageToday);
					// at age 0 all are shaved
					if (yak.getAge() < minAgeForShave) {
						yak.setAgeLastShaved(0.0f);
					} else {
						int j = (int) (8 + ageOfYakAt0day * 0.01) + 1;
						if (i > 0 && i % j == 0) {
							int ageShavedInDays = labYak.getAgeInDays() + i;
							float ageShaved = (float) ageShavedInDays / daysInYear;
							yak.setAgeLastShaved(ageShaved);
						}
					}
				}

			}
		}
		return herd;
	}

	public Stock placeOrder(int elapsedTime, OrderInput inputJson) throws Exception {
		Stock stock = getStock(elapsedTime);
		Stock order = inputJson.getOrder();
		if (stock == null) {
			return null;
		}
		Stock outputStock = new Stock();
		boolean isPartial = false;
		if (order.getMilk() < stock.getMilk()) {
			outputStock.setMilk(order.getMilk());
			isPartial = true;
		}
		if (order.getSkins() < stock.getSkins()) {
			outputStock.setSkins(order.getSkins());
			if (isPartial == true) {
				isPartial = false;
			}
			isPartial = true;
		}
		return outputStock;
	}

}
