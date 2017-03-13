package esb.wso2.org.client;

import com.ibm.mq.jms.MQQueue;
import esb.wso2.org.client.util.MQConfiguration;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by chanaka on 3/12/17.
 */
public class MQProducer {

	private MessageHandler handler;
	private QueueReceiver receiver;
	private MQConnectionBuilder connectionBuilder;
	private MQConfiguration config;
	private QueueConnection connection;
	private QueueSession session;

	public MQProducer() {
		this.connectionBuilder = MQConnectionBuilder.getInstance();
		this.config= connectionBuilder.getConfig();
	}

	/**
	 * Sending messages to queue
	 */
	public void send(String msg){
		try {
			connection = connectionBuilder.getConnection();
			if(connection==null){
				return;
			}
			session=connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			if(session==null){
				connectionBuilder.closeConnection();
				return;
			}
			MQQueue destination= connectionBuilder.getDestination();
			MessageProducer producer=session.createProducer(destination);
			TextMessage message=session.createTextMessage();
			message.setText(msg);
			producer.send(message,Message.DEFAULT_DELIVERY_MODE,
			              Message.DEFAULT_PRIORITY,
			              Message.DEFAULT_TIME_TO_LIVE);
		}catch (JMSException e){
			System.out.println("Exception:message sending failed :"+e);
			return;
		}
	}

}
