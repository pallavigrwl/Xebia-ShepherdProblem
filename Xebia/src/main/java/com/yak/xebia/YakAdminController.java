package com.yak.xebia;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.yak.domain.HerdPojo;
import com.yak.domain.Stock;

@Controller
@RequestMapping(value = "/yak-admin")
public class YakAdminController {
	@Autowired
	private YakService yakService;
	@Autowired
	private HerdListCache herdCache;

	@RequestMapping(value = "/processXML", method = RequestMethod.GET)
	@ResponseBody
	public HerdListCache processXML(@RequestParam String inputXMLPath, @RequestParam int elapsedTime,
			final HttpServletRequest request) throws Exception {
		validateXMLPath(inputXMLPath);
		inputXMLPath = "src/main/".concat(inputXMLPath);
		File fXmlFile = new File(inputXMLPath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		Element element = doc.getDocumentElement();
		JAXBContext context = JAXBContext.newInstance(HerdPojo.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		JAXBElement<HerdPojo> loader = unmarshaller.unmarshal(element, HerdPojo.class);
		HerdPojo inputFromXml = loader.getValue();
		herdCache.initializeCache(inputFromXml);
		Stock stock = yakService.calculateStock(inputFromXml, elapsedTime);
		herdCache.setStock(stock);
		return herdCache;
	}

	private boolean validateXMLPath(String inputXMLPath) {
		return inputXMLPath.startsWith("resources");
	}

}
