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
	private String channel;
	private String userName;
	private String password;
	private int transportType;
	private MQConfiguration(MQConfiguration.MQConfigurationBuilder builder){
		this.port=builder.port;
		this.host=builder.host;
		this.qManger=builder.qManger;
		this.queue=builder.queue;
		this.userName=builder.userName;
		this.password=builder.password;
		this.channel=builder.channel;
		this.properties=builder.properties;
		this.transportType=builder.transportType;
	}

	public int getTransportType() {
		return transportType;
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
		private Properties properties;
		private String qManger;
		private String queue;
		private String channel;
		private String userName;
		private String password;
		private int transportType;

		public MQConfigurationBuilder() {

			this.properties = new PropertyUtils().get();

			if(properties.getProperty(MQConstants.PORT)!=null) {
				this.port = Integer.parseInt(properties.getProperty(
						MQConstants.PORT));
			}else{
				this.port=1414;
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
				this.queue="LocalQueue1";
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
