package esb.wso2.org.client.util;

import java.util.Properties;

/**
 * Created by chanaka on 3/12/17.
 */
public class MQConfiguration {
	private final int port;
	private final String host;
	private Properties properties;
	private String qManger;
	private String queue;
	private String topic;
	private String channel;
	private String userName;
	private String password;
	private int transportType;
	private int receiveTimeout;
	private String ciphersuit;
	private Boolean flipRequired;
	private boolean sslEnable;
	private String trustStore;
	private String trustPassword;
	private String keyStore;
	private String keyPassword;

	private MQConfiguration(MQConfiguration.MQConfigurationBuilder builder){
		this.port=builder.port;
		this.host=builder.host;
		this.qManger=builder.qManger;
		this.queue=builder.queue;
		this.topic=builder.topic;
		this.userName=builder.userName;
		this.password=builder.password;
		this.channel=builder.channel;
		this.properties=builder.properties;
		this.transportType=builder.transportType;
		this.receiveTimeout=builder.receiveTimeout;
		this.ciphersuit=builder.ciphersuit;
		this.flipRequired=builder.flipRequired;
		this.sslEnable=builder.sslEnable;
		this.trustStore=builder.trustStore;
		this.trustPassword=builder.trustPassword;
		this.keyStore=builder.keyStore;
		this.keyPassword=builder.keyPassword;
	}

	public int getReceiveTimeout() {
		return receiveTimeout;
	}

	public String getCiphersuit() {
		return ciphersuit;
	}

	public Boolean getFlipRequired() {
		return flipRequired;
	}

	public boolean isSslEnable() {
		return sslEnable;
	}

	public String getTrustStore() {
		return trustStore;
	}

	public String getTrustPassword() {
		return trustPassword;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public String getKeyPassword() {
		return keyPassword;
	}

	public int getTransportType() {
		return transportType;
	}

	public String getTopic() {
		return topic;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getqManger() {
		return qManger;
	}

	public String getQueue() {
		return queue;
	}

	public String getChannel() {
		return channel;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public static class MQConfigurationBuilder{
		private final int port;
		private final String host;
		private final int receiveTimeout;
		private Properties properties;
		private String qManger;
		private String queue;
		private String topic;
		private String channel;
		private String userName;
		private String password;
		private int transportType;
		//SSL properties
		private String ciphersuit;
		private boolean flipRequired;
		private boolean sslEnable;
		private String trustStore=null;
		private String trustPassword=null;
		private String keyStore=null;
		private String keyPassword=null;

		public MQConfigurationBuilder() {

			this.properties = new PropertyUtils().get();

			if(properties.getProperty(MQConstants.PORT)!=null) {
				this.port = Integer.parseInt(properties.getProperty(
						MQConstants.PORT));
			}else{
				this.port=1414;
			}
			if(properties.getProperty(MQConstants.SSL_ENABLE)!=null) {
				this.sslEnable = Boolean.parseBoolean(properties.getProperty(
						MQConstants.SSL_ENABLE));
			}else{
				this.sslEnable=false;
			}

			if(properties.getProperty(MQConstants.CIPHERSUIT)!=null) {
				this.ciphersuit = properties.getProperty(
						MQConstants.CIPHERSUIT);
			}else{
				this.ciphersuit="SSL_RSA_WITH_3DES_EDE_CBC_SHA";
			}
			if(properties.getProperty(MQConstants.FLIP_REQUIRED)!=null) {
				this.flipRequired = Boolean.parseBoolean(properties.getProperty(
						MQConstants.FLIP_REQUIRED));
			}else{
				this.flipRequired=false;
			}
			if(properties.getProperty(MQConstants.TRUST_STORE)!=null) {
				this.trustStore = properties.getProperty(
						MQConstants.TRUST_STORE);
			}else{
				this.trustStore=null;
			}if(properties.getProperty(MQConstants.TRUST_PASSWORD)!=null) {
				this.trustPassword = properties.getProperty(
						MQConstants.TRUST_PASSWORD);
			}else{
				this.trustPassword=null;
			}if(properties.getProperty(MQConstants.KEYSTORE_STORE)!=null) {
				this.keyStore = properties.getProperty(
						MQConstants.KEYSTORE_STORE);
			}else{
				this.keyStore=null;
			}if(properties.getProperty(MQConstants.KEY_PASSWORD)!=null) {
				this.keyPassword = properties.getProperty(
						MQConstants.KEY_PASSWORD);
			}else{
				this.keyPassword=null;
			}

			if(properties.getProperty(MQConstants.HOST)!=null) {
				this.host = properties.getProperty(
						MQConstants.HOST);
			}else{
				this.host="localhost";
			}

			if(properties.getProperty(MQConstants.TRANSPORT_TYPE)!=null) {
				this.transportType = Integer.parseInt(properties.getProperty(
						MQConstants.TRANSPORT_TYPE));
			}else{
				this.transportType=1; //Default client type
			}

			if(properties.getProperty(MQConstants.QMANAGER)!=null) {
				this.qManger = properties.getProperty(
						MQConstants.QMANAGER);
			}else{
				this.qManger="QManager";
			}

			if(properties.getProperty(MQConstants.QUEUE)!=null) {
				this.queue = properties.getProperty(
						MQConstants.QUEUE);
			}else{
				this.queue=null;
			}

			if(properties.getProperty(MQConstants.QUEUE)!=null) {
				this.topic = properties.getProperty(
						MQConstants.TOPIC);
			}else{
				this.topic=null;
			}
			if(properties.getProperty(MQConstants.RECEIVE_TIMEOUT)!=null) {
				this.receiveTimeout = Integer.parseInt(properties.getProperty(
						MQConstants.RECEIVE_TIMEOUT));
			}else{
				this.receiveTimeout=1000;
			}


			if(properties.getProperty(MQConstants.CHANNEL)!=null) {
				this.channel = properties.getProperty(
						MQConstants.CHANNEL);
			}else{
				this.channel="LocalChaneel1";
			}

			if(properties.getProperty(MQConstants.USERNAME)!=null) {
				this.userName = properties.getProperty(
						MQConstants.USERNAME);
			}else{
				this.userName="mqm";
			}

			if(properties.getProperty(MQConstants.PASSWORD)!=null) {
				this.password = properties.getProperty(
						MQConstants.PASSWORD);
			}else{
				this.password="mqm";
			}
		}
		public MQConfiguration build() {
			return new MQConfiguration(this);
		}
	}
}
