import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageConsumerClass {

    public static void main(String args[]) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://EPINHYDW0C47:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue("Testing");
        MessageConsumer consumer = session.createConsumer(destination);

        Message message = consumer.receive(1000);

        if(message instanceof TextMessage)
        {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Received : " + message);
        }
        else
            System.out.println("Received : " + message);

        connection.close();
        consumer.close();;
        session.close();

    }
}
