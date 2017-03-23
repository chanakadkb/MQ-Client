package esb.wso2.org.client;

import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQTopic;
import com.ibm.mq.constants.CMQC;
import esb.wso2.org.client.util.MQConfiguration;

public class MQConsumer{

	private MQConnectionBuilder connectionBuilder;
	private MQConfiguration config;
	private MQQueueManager manager;
	private MessageHandler handler;

	public MQConsumer(MessageHandler handler) {
		this.connectionBuilder = MQConnectionBuilder.getInstance();
		this.config= connectionBuilder.getConfig();
		this.manager=connectionBuilder.getQueueManager();
		this.handler=handler;
	}

	public void consume() throws Exception{
		MQQueue readableQueue;
		if(!manager.isConnected()){
			return;
		}
		readableQueue = manager.accessQueue(config.getQueue(), CMQC.MQRC_READ_AHEAD_MSGS);
		if(readableQueue==null)
			return;
		MQMessage message=new MQMessage();
		MQGetMessageOptions gmo = new MQGetMessageOptions();

		readableQueue.get(message,gmo);
		handler.handle(message);
		readableQueue.close();
	}

	/*public MQTopic getPublisherTopic(){
		try {
			if (publisher == null || !publisher.isOpen()) {
				publisher = queueManager.accessTopic(config.getTopic(),"TOPICNAME",CMQC
						.MQTOPIC_OPEN_AS_PUBLICATION,CMQC.MQOO_OUTPUT);
			}
			return publisher;
		}catch (Exception e){
			System.err.println("ERROR:Topic_creation_failed :"+e);
			return null;
		}
	}*/

}
