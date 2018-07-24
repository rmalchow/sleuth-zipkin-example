# SLEUTH/ZIPKIN EXAMPLE

This is a simple example that demonstrates the use of sleuth and zipkin to trace requests across different services. The main point (in sprinboot 2.X) is to have these two components:

***for sleuth***

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-sleuth</artifactId>
		</dependency>
```

***and to use zipkin (with delivery via HTTP) on top:***		

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-sleuth-zipkin</artifactId>
		</dependency>
```

this will instrument the sending and receiving sides of any RestTemplate (including feign), Kafka or RabbitMQ communication.

## Sleuth

instruments the entry- and exit points to make sure that a unique Id is generated for and then carried through all components in a distributed architecture.

## Zipkin

collects a configurable percentage (default is 10% of all requests) and reports it to a central server, where dependencies and relative execution times can be monitored. 

> Zipkin server uses an in-memory database for persistence by default. Other persistence backends are available

## Instrumentation

Note that it is necessary that the appropriate components are visible in the application context. This means that this:

### NO (X):

```
	@GetMapping(value="")
	public String get() {
		log.info("getting from A");
		RestTemplate rt = new RestTemplate();
		String s = rt.getForEntity("http://127.0.0.1:8081", String.class).getBody();
		log.info("value from A: "+s);
		return s;
	}
```

will not work, since the RestTemplate is not visible to the sleuth instrumentation. this, however:\

### YES (✓):

```
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
```

will work. as you can see, the RestTemplate is exposed as a Bean. similar things will be applicable to all other communitcations.

## How does it work

Sleuth will look for a header called "X-B3-TraceID" in every incoming request, generating a new one if none is set. It will also include a new "X-B3-SpanId" on every entry. It will then include these headers in all outgoing request. The TraceID stays unmodified throughout the entire chain, while the "SpanId" of one service becomes the "ParentId" of the next one. Thus, the services can report:

```
Service: A
TraceId: AAAAAA
SpanId: BBBBBB
ParentId: [empty]
[more details]
```

```
Service: B
TraceId: AAAAAA
SpanId: CCCCCC
ParentId: BBBBBB
[more details]
```

… and so on. With the SpanId and the ParentId, the actual sequence can be reassembled from these reports, even if they arrive in Zipkin in random order. It enables us to see the recorded traces in Zipkin through all components.

> Attention: The default config of zipkin only captures 10% of all requests. So you will see only 10% of your requests fully traced in Zipkin.







