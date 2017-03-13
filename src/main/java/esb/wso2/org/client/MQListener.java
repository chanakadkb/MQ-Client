package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */

import javax.jms.*;

/**
 * Listening to a queue;
 */
public class MQListener implements MessageListener{

	public void onMessage(final Message message) {

		new Thread(new Runnable(){
			public void run() {
				new MessageHandler().handle(message);
			}
		}).start();

	}
}
