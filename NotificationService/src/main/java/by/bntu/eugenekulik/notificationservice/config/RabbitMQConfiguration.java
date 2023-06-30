package by.bntu.eugenekulik.notificationservice.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

  private static final String topicExchangeName = "notification-service-exchange";
  private static final String queueName = "notification-service";

  @Value("${spring.rabbitmq.host}")
  private String host;

  @Value("${spring.rabbitmq.port}")
  private int port;

  @Value("${spring.rabbitmq.username}")
  private String username;

  @Value("${spring.rabbitmq.password}")
  private String password;
  @Bean
  public Queue queue(){
    return new Queue(queueName);
  }

  @Bean
  public TopicExchange topicExchange(){
    return new TopicExchange(topicExchangeName);
  }

  @Bean
  public Binding binding(){
    return BindingBuilder.bind(queue()).to(topicExchange()).with("notification-service");
  }

  @Bean
  public MessageConverter messageConverter(){
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ConnectionFactory connectionFactory(){
    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(){
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setDefaultRequeueRejected(false);
    factory.setConnectionFactory(connectionFactory());
    factory.setMessageConverter(messageConverter());
    return factory;
  }

}
