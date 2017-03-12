package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */
import com.ibm.mq.jms.MQQueue;
import com.ibm.msg.client.wmq.*;
import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.compat.base.internal.*;
import esb.wso2.org.client.util.MQConfiguration;

import javax.jms.*;


/**
 * Sharing single session and queue among senders and receivers
 */
public class MQShared {

	private static MQShared instance;

	private volatile QueueSession session;
	private volatile MQQueue queue;
	private MQConfiguration config;
	private QueueConnection connection;


	private MQShared(){
		this.config=new MQConfiguration.MQConfigurationBuilder().build();
		try {
			this.session = createSession();  //create session
			this.queue = new MQQueue(config.getQueue());   //create queue
		}catch (Exception e){
			System.err.println("Exception-Shared :"+e);
		}
	}
	public static MQShared getInstance(){
		if(instance==null){
			instance=new MQShared();
		}
		return instance;
	}

	public QueueSession getSession() {
		return session;
	}

	public MQQueue getQueue() {
		return queue;
	}

	public void close(){
		try {
			this.session.close();
			this.connection.close();
			this.session=null;
		} catch (Exception e) {
			System.err.println(e);
		}
		instance=null;
	}

	private QueueSession createSession() throws Exception{
		QueueConnection connection=createConnection();
		connection.start();
		return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	private QueueConnection createConnection() throws Exception{
		MQQueueConnectionFactory conFactory=new MQQueueConnectionFactory();
		conFactory.setHostName(config.getHost());
		conFactory.setPort(config.getPort());
		conFactory.setQueueManager(config.getqManger());
		conFactory.setChannel(config.getChannel());
		conFactory.setTransportType(config.getTransportType());
		return conFactory.createQueueConnection(config.getUserName(),config.getPassword());
	}

}
