README.TXT

The complete project is MAVEN-SPRING BOOT Project

The application runner class is YakShepherdApplication.java which would run the tomcat server.

All the rest api make sense only when this xml file has been uploaded

1. To Upload XML file, hit below URL.
http://<HOST>:<PORT>/yak-admin/processXML?inputXMLPath=resources/SampleInputHerd.xml&elapsedTime=14
Desired output for case study 1 would be in log file.

2. http://<HOST>:<PORT>/yak-shop/stock/13
3. http://<HOST>:<PORT>/yak-shop/herd/13

4. http://<HOST>:<PORT>/yak-shop/order/14  :
input json : OrderInput.java


Test cases of service layer exist in YakShepherdApplicationTests.java

