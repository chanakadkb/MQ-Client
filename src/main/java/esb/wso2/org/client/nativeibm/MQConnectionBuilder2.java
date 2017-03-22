package esb.wso2.org.client.nativeibm;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.MQTopic;
import esb.wso2.org.client.MQConnectionBuilder;
import esb.wso2.org.client.util.MQConfiguration;

import javax.jms.QueueConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * Created by chanaka on 3/22/17.
 */
public class MQConnectionBuilder2 {

	private static MQConnectionBuilder2 instance;
	private  MQQueue queue,readQueue;
	private MQTopic topic;
	private MQConfiguration config;
	//private QueueConnection connection;
	private static MQQueueManager queueManager;

	private MQConnectionBuilder2(){
		this.config=new MQConfiguration.MQConfigurationBuilder().build();

		MQEnvironment.hostname=config.getHost();
		MQEnvironment.channel=config.getChannel();
		MQEnvironment.port=config.getPort();
		MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY,CMQC.TRANSPORT_MQSERIES_CLIENT);
		MQEnvironment.properties.put(CMQC.USER_ID_PROPERTY,config.getUserName());
		MQEnvironment.properties.put(CMQC.PASSWORD_PROPERTY,config.getPassword());
		try {
			queueManager=new MQQueueManager(config.getqManger());
		}catch (MQException e){
			e.printStackTrace();
		}

	}

	public static MQConnectionBuilder2 getInstace(){
		if(instance==null){
			instance=new MQConnectionBuilder2();
		}
		return instance;
	}

	public MQQueueManager getQueueManager(){
		if(queueManager==null){
			try {
				queueManager=new MQQueueManager(config.getqManger());
			}catch (MQException e){
				e.printStackTrace();
			}
		}
		return queueManager;
	}

	/**
	 * Get MQQueue
	 * @return MQQueue
	 */
	public MQQueue getDestination() {
		try {
			if (queue == null) {
				queue = queueManager.accessQueue(config.getQueue(),CMQC.MQOO_OUTPUT);
			}
			return queue;
		}catch (Exception e){
			System.err.println("ERROR:Queue_creation_failed :"+e);
			return null;
		}
	}

	public MQQueue getReadingQueue(){
		try {
			if (readQueue == null) {
				readQueue = queueManager.accessQueue(config.getQueue(),CMQC.MQRC_READ_AHEAD_MSGS);
			}
			return readQueue;
		}catch (Exception e){
			System.err.println("ERROR:Queue_creation_failed :"+e);
			return null;
		}
	}

	/**
	 * Get MQTopic
	 * @return MQTopic
	 */
	/*public MQTopic getTopic() {
		try {
			if (topic == null) {
				topic = new MQTopic(config.getTopic());
			}
			return topic;
		}catch (Exception e){
			System.err.println("ERROR:Topic_creation_failed :"+e);
			return null;
		}
	}*/


	/**
	 * Close cached connection
	 */
	public void closeConnection(){
		if(queueManager!=null && queueManager.isConnected()){
			try {
				queue.close();
				queue=null;
				queueManager.disconnect();
				queueManager=null;
			}catch (Exception e){
				queueManager=null;
			}
		}
	}

	public MQConfiguration getConfig() {
		return config;
	}

	public SSLContext createSSLContext(){
		try {
			Class.forName("com.sun.net.ssl.internal.ssl.Provider");

			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(new FileInputStream(config.getKeyStore()), config.getKeyPassword().toCharArray());

			KeyStore trustStore = KeyStore.getInstance("JKS");
			trustStore.load(new FileInputStream(config.getTrustStore()), config.getTrustPassword().toCharArray());

			TrustManagerFactory trustManagerFactory =
					TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

			KeyManagerFactory keyManagerFactory =
					KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

			trustManagerFactory.init(trustStore);
			keyManagerFactory.init(ks, config.getKeyPassword().toCharArray());

			SSLContext sslContext = SSLContext.getInstance("SSLv3");

			sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(),
			                null);

			return sslContext;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
