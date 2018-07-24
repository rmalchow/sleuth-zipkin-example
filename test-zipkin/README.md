# ZIPKIN SERVER

to take full advantage of the tracing in sleuth, you will have to run a zipkin server. zipkin has stopped to support "custom" zipkin server (i.e. through @EnableZipkinServer), and has instead opted for a full distribution, a sprigboot application which is also available as an executable jar (with the "exec" classifier):

http://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/

You can run this by simply doing:

```java -jar zipkin-server-${VERSION}-exec.jar```

this should bring up a server with the default configuration on 9411. Further documentation is here:

https://github.com/openzipkin/zipkin/tree/master/zipkin-server

The default setting for the zipkin REST API URL on the sleuth side is:

http://127.0.0.1:9411

So it perfectly lines up with the default settings in the server. In a containerized environment, you will have to add the appropriate configuration. Sleuth can also be configured to use eureka to find the zipkin server.





