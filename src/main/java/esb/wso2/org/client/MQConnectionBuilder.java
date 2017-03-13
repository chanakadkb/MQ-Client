package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.*;
import esb.wso2.org.client.util.MQConfiguration;

import javax.jms.*;


/**
 * Configuring connection
 */
public class MQConnectionBuilder {

	private static MQConnectionBuilder instance;
	private  MQQueue queue;
	private MQTopic topic;
	private MQConfiguration config;
	private QueueConnection connection;
	private MQQueueConnectionFactory conFactory;

	private MQConnectionBuilder() throws JMSException{
		this.config=new MQConfiguration.MQConfigurationBuilder().build();

		//Configuring connectionFactory
		conFactory=new MQQueueConnectionFactory();
		conFactory.setHostName(config.getHost());
		conFactory.setPort(config.getPort());
		conFactory.setQueueManager(config.getqManger());
		conFactory.setChannel(config.getChannel());
		conFactory.setTransportType(config.getTransportType());

	}

	public static MQConnectionBuilder getInstance(){
		if(instance==null){
			try {
				instance = new MQConnectionBuilder();
			}catch (JMSException e){
				System.err.println("Exception:Connection_builder_creation_failed :"+e);
			}
		}
		return instance;
	}

	/**
	 * Get MQQueue
	 * @return MQQueue
	 */
	public MQQueue getDestination() {
		try {
			if (queue == null) {
				queue = new MQQueue(config.getQueue());
			}
			return queue;
		}catch (JMSException e){
			System.err.println("ERROR:Queue_creation_failed :"+e);
			return null;
		}
	}

	/**
	 * Get MQTopic
	 * @return MQTopic
	 */
	public MQTopic getTopic() {
		try {
			if (topic == null) {
				topic = new MQTopic(config.getTopic());
			}
			return topic;
		}catch (JMSException e){
			System.err.println("ERROR:Topic_creation_failed :"+e);
			return null;
		}
	}

	/**
	 * get a started connection
	 * @return QueueConnection
	 * @throws Exception
	 */
	public QueueConnection getConnection() throws JMSException{
		if(connection==null){
			connection=conFactory.createQueueConnection(config.getUserName(),config.getPassword());
			connection.start();
		}
		return connection;
	}

	/**
	 * Close cached connection
	 */
	public void closeConnection(){
		if(connection!=null){
			try {
				connection.close();
				connection=null;
			}catch (Exception e){
				connection=null;
			}
		}
	}

	public MQConfiguration getConfig() {
		return config;
	}

}
