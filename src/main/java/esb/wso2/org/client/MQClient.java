package esb.wso2.org.client;

/**
 * Created by chanaka on 3/12/17.
 */

public class MQClient {
	public static void main(String[] args) {

		Thread thread=new Thread(new MQListener(new messageHandler()));
		thread.start();
	}
}
