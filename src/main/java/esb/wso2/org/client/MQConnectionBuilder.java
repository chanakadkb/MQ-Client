package esb.wso2.org.client;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQPoolToken;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import com.ibm.mq.MQQueue;
import com.ibm.mq.constants.CMQXC;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.MQTopic;
import esb.wso2.org.client.util.MQConfiguration;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Collection;
import java.util.Vector;

/**
 * Created by chanaka on 3/22/17.
 */
public class MQConnectionBuilder {

	private static MQConnectionBuilder instance;
	private  MQQueue writableQueue,readableQueue;
	private MQTopic subcriber,publisher;
	private MQConfiguration config;
	private static MQQueueManager queueManager;
	MQPoolToken token;

	private MQConnectionBuilder(){
		this.config=new MQConfiguration.MQConfigurationBuilder().build();

		this.token=MQEnvironment.addConnectionPoolToken();  //Pooling connection

		MQEnvironment.hostname=config.getHost();
		MQEnvironment.channel=config.getChannel();
		MQEnvironment.port=config.getPort();
		MQEnvironment.properties.put(CMQC.TRANSPORT_PROPERTY,CMQC.TRANSPORT_MQSERIES_CLIENT);
		MQEnvironment.properties.put(CMQC.USER_ID_PROPERTY,config.getUserName());
		MQEnvironment.properties.put(CMQC.PASSWORD_PROPERTY,config.getPassword());
		//Compress headers
		Collection headerComp = new Vector();
		headerComp.add(new Integer(CMQXC.MQCOMPRESS_SYSTEM));
		MQEnvironment.hdrCompList = headerComp;

		try {
			queueManager=new MQQueueManager(config.getqManger());  //create connection and return
		}catch (MQException e){
			e.printStackTrace();
		}
	}

	public static MQConnectionBuilder getInstance(){
		if(instance==null){
			instance=new MQConnectionBuilder();
		}
		return instance;
	}

	public MQQueueManager getQueueManager(){
		if(queueManager==null ||!queueManager.isConnected()){
			try {
				queueManager=new MQQueueManager(config.getqManger());
			}catch (MQException e){
				e.printStackTrace();
			}
		}
		return queueManager;
	}


	/**
	 * Close cached connection
	 */
	public void closeConnection(){
		try {
			if (queueManager.isConnected()) {
				queueManager.close();
				queueManager = null;
				MQEnvironment.removeConnectionPoolToken(token);
			}
		}catch (MQException e){
			e.printStackTrace();
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
