package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */

import com.ibm.mq.jms.MQQueue;
import esb.wso2.org.client.util.MQConfiguration;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 * Listening to a queue;
 */
public class MQConsumer{

	private MQConnectionBuilder connectionBuilder;
	private MQConfiguration config;
	private QueueConnection connection;
	private QueueSession session;
	private MessageConsumer consumer;

	public MQConsumer() {
		this.connectionBuilder = MQConnectionBuilder.getInstance();
		this.config= connectionBuilder.getConfig();
	}

	/**
	 * Starting asynchronous event listener
	 */
	public void start(){
		try {
			connection = connectionBuilder.getConnection();
			if(connection==null){
				return;
			}
			session=connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			if(session==null){
				connectionBuilder.closeConnection();
				return;
			}
			MQQueue queue= connectionBuilder.getDestination();
			consumer=session.createConsumer(queue);
			consumer.setMessageListener(new MQListener());
		}catch (JMSException e){
			System.out.println("Exception:connection initiating failed :");
			e.printStackTrace();
			return;
		}
	}
	public void stop(){
		if(consumer!=null) {
			try {
				consumer.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
