import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageProducerClass {

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://EPINHYDW0C47:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("Testing");

        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        String text = "Hello World from : " + Thread.currentThread().getName();
        TextMessage message = session.createTextMessage(text);

        System.out.println("Sent Message" + message.hashCode()+ " : " + Thread.currentThread().getName());
        producer.send(message);

        session.close();
        connection.close();
    }
}
