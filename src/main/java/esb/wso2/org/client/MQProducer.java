
package esb.wso2.org.client;

import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQTopic;
import com.ibm.mq.constants.CMQC;
import esb.wso2.org.client.util.MQConfiguration;


/**
 * Created by chanaka on 3/12/17.
 */

public class MQProducer {

	private MQConnectionBuilder connectionBuilder;
	private MQConfiguration config;
	private MQQueueManager manager;

	public MQProducer() {
		this.connectionBuilder = MQConnectionBuilder.getInstance();
		this.config= connectionBuilder.getConfig();
		this.manager=connectionBuilder.getQueueManager();
	}

	public void send(String msg) throws Exception{
		MQQueue queue;
		queue = manager.accessQueue(config.getQueue(), CMQC.MQOO_OUTPUT);
		if(queue==null){
			return;
		}
		MQMessage mqMessage=new MQMessage();
		mqMessage.writeString(msg);
		MQPutMessageOptions pmo = new MQPutMessageOptions();
		queue.put(mqMessage,pmo);
		queue.close();
	}

/*
	public MQTopic getSubscriberTopic(){
		try {
			if (subcriber == null || !subcriber.isOpen()) {
				subcriber = queueManager.accessTopic(config.getTopic(),"TOPICNAME",CMQC
						.MQTOPIC_OPEN_AS_SUBSCRIPTION,CMQC.MQSO_CREATE);
			}
			return subcriber;
		}catch (Exception e){
			System.err.println("ERROR:Topic_creation_failed :"+e);
			return null;
		}
	}
*/

}
