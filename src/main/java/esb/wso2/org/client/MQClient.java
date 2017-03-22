package esb.wso2.org.client;

import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import esb.wso2.org.client.nativeibm.MQConnectionBuilder2;

/**
 * Created by chanaka on 3/12/17.
 */

public class MQClient {
	public static void main(String[] args) {

		try {
			MQConnectionBuilder2 builder2 = MQConnectionBuilder2.getInstace();
			MQMessage myMessage = new MQMessage();
			String name = "Charlie Jordan";
			myMessage.writeInt(505);
			myMessage.writeString(name);

			MQPutMessageOptions pmo = new MQPutMessageOptions();
			MQQueue queue=builder2.getDestination();
			queue.put(myMessage);

			MQQueue readingQueue=builder2.getReadingQueue();
			MQMessage theMessage    = new MQMessage();
			MQGetMessageOptions gmo = new MQGetMessageOptions();

			readingQueue.get(theMessage,gmo);

			System.out.println(theMessage.readInt());
			System.out.println(theMessage.readLine());



			builder2.closeConnection();

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
