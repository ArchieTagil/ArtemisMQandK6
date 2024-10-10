package com.example.k6_to_artemismq;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.jms.*;


@RestController
public class Controller {
    private Connection connection;
    Session session;
    Queue queue;

    public Controller() {
        try {
            ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://192.168.0.2:5672");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue("q1");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String get_root() {
        System.out.println("I'm teapot ^_^");
        return "I'm teapot ^_^";
    }

    @PostMapping("/")
    public String post_root(@RequestBody String requestBody) throws JMSException {
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage(Helper.getDateTime() + " received " + requestBody));
        return "data was received";
    }

    @GetMapping("/getMessages")
    public String get_messages() throws JMSException {
        Message message;
        String str = "";
        MessageConsumer consumer = session.createConsumer(queue);

        while ((message = consumer.receive(2000)) != null) {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message is: " + textMessage.getText());
                str = str + "Received message is: " + textMessage.getText() + "\n";
            }
        }
        return str;
    }
}
