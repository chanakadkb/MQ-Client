package esb.wso2.org.client;

import javax.jms.Message;

/**
 * Created by chanaka on 3/12/17.
 */
public class MessageHandler{


	public void handle(Message message){
		System.out.println("A message received : \n"+message);
	}
}
