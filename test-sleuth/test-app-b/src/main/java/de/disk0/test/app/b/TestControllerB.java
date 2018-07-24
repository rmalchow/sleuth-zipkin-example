package de.disk0.test.app.b;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.Tracer;

@Configuration
@RestController(value="/")
public class TestControllerB {

	private static Log log = LogFactory.getLog(TestControllerB.class);

	@Autowired
	private Tracer tracer;

	@Autowired
	private 	RestTemplate rt; 
	
	@Bean 
	public RestTemplate rtBean() {
		return new RestTemplate();
	}
	
	@GetMapping(value="")
	public String get() {
		log.info("getting from A");
		String s = rt.getForEntity("http://127.0.0.1:8081", String.class).getBody();
		log.info("value from A: "+s);
		return s;
	}
	
	@GetMapping(value = "/deeper")
	public String getDeeper() {
		log.info("request from B");
		String s = "B: "+new SimpleDateFormat().format(new Date());
		log.info("value: " + s);
		log.info("trace: " + tracer.currentSpan().context().traceIdString());
		return s;
	}

	
}
