package com.highAvailability.RabbitMQ;

public class readmeTests {
    /*
     * <project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>rabbitmq-ha-springboot</artifactId>
  <version>1.0.0</version>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-amqp</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
  </dependencies>
</project>
*
* @Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue}")
    private String queue;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    @Bean
    public Queue haQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public DirectExchange haExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding binding(Queue haQueue, DirectExchange haExchange) {
        return BindingBuilder.bind(haQueue).to(haExchange).with(routingKey);
    }
}
*
* @Component
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key}")
    private String routingKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
*
* @Component
public class MessageListener {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(String message) {
        System.out.println("Received: " + message);
    }
}
*
* @RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private MessageProducer producer;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody String message) {
        producer.send(message);
        return ResponseEntity.ok("Sent: " + message);
    }
}
*
* curl -X POST http://localhost:8080/api/send -d "hello from HA" -H "Content-Type: text/plain"
*
*
* docker run -d \
  --hostname rabbit1 \
  --name rabbit1 \
  --network rabbitmq-net \
  -e RABBITMQ_ERLANG_COOKIE='rabbitcookie' \
  -e RABBITMQ_NODENAME=rabbit@rabbit1 \
  -p 15672:15672 \
  -p 5672:5672 \
  rabbitmq:3.12-management
  *
  *
  * docker run -d \
  --hostname rabbit2 \
  --name rabbit2 \
  --network rabbitmq-net \
  -e RABBITMQ_ERLANG_COOKIE='rabbitcookie' \
  -e RABBITMQ_NODENAME=rabbit@rabbit2 \
  -p 15673:15672 \
  -p 5673:5672 \
  rabbitmq:3.12-management
  *
  * docker exec -it rabbit2 bash
  *
  * rabbitmqctl stop_app
rabbitmqctl join_cluster rabbit@rabbit1
rabbitmqctl start_app
*
* docker exec rabbit1 rabbitmqctl cluster_status
     */
}
