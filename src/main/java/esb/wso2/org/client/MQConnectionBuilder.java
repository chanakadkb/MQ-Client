package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */
import com.ibm.mq.MQC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;
import esb.wso2.org.client.util.MQConfiguration;

import javax.net.ssl.SSLContext;

import java.io.FileInputStream;
//import java.io.Console;
import java.security.*;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;


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
	private MQConnectionBuilder() throws Exception{

		this.config=new MQConfiguration.MQConfigurationBuilder().build();

		//Configuring connectionFactory
		conFactory=new MQQueueConnectionFactory();
		conFactory.setHostName(config.getHost());
		conFactory.setPort(config.getPort());
		conFactory.setQueueManager(config.getqManger());
		conFactory.setChannel(config.getChannel());
		conFactory.setTransportType(config.getTransportType());

		//Configuring SSL properties
		if(config.isSslEnable()) {
			conFactory.setSSLSocketFactory(createSSLContext().getSocketFactory());
			conFactory.setSSLCipherSuite(config.getCiphersuit());  //Ciperset
			conFactory.setSSLFipsRequired(config.getFlipRequired());
		}
	}
	public static MQConnectionBuilder getInstance(){
		if(instance==null){
			try {
				instance = new MQConnectionBuilder();
			}catch (Exception e){
				System.err.println("Exception:Connection_builder_creation_failed :");
				e.printStackTrace();
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
	public QueueConnection getConnection() throws Exception{
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
