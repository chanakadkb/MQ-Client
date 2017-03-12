package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */

import com.ibm.mq.jms.MQQueue;
import javax.jms.*;

public class MQListener implements Runnable{

	private messageHandler handler;
	private QueueReceiver receiver;
	private MQShared sharedInstance;
	private MQQueue queue;
	private QueueSession session;

	public MQListener(messageHandler handler) {
		this.handler = handler;
		this.sharedInstance=MQShared.getInstance();
		this.queue=sharedInstance.getQueue();
		this.session=sharedInstance.getSession();
	}

	/**
	 * Start the listener
	 */
	public void run() {
		try {
			if(session!=null){
				if(this.receiver==null){
					receiver=session.createReceiver(queue);
				}
				Message message=receiver.receive();
				handler.handle(message);
			}
		}catch (Exception e){
			System.err.println("Exception-Listener :"+e);
		}
	}
}
