package de.disk0.test.app.a;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import brave.Tracer;

@RestController(value = "/")
public class TestControllerA {

	private static Log log = LogFactory.getLog(TestControllerA.class);

	@Autowired
	private Tracer tracer;

	@Autowired
	private 	RestTemplate rt; 
	
	@Bean 
	public RestTemplate rtBean() {
		return new RestTemplate();
	}
	
	@GetMapping(value = "")
	public String get() {
		
		double x = Math.random();
		
		if(x > 0.5) {
			log.info("request from B");
			String s = "A: "+new SimpleDateFormat().format(new Date());
			log.info("value: " + s);
			tracer.currentSpan().annotate("this_is_a_blah");
			tracer.currentSpan().name("foo_requests");
			tracer.startScopedSpan("foo_bar");
			log.info("trace: " + tracer.currentSpan().context().traceIdString());
			return s;
		} else {
			return rt.getForEntity("http://127.0.0.1:8080/deeper", String.class).getBody();
		}
	}

}
