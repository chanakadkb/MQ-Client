package esb.wso2.org.client;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by chanaka on 3/12/17.
 */

public class MQClient {
	public static void main(String[] args) {

		MQConsumer consumer=new MQConsumer();
		consumer.start();

		MQProducer producer=new MQProducer();
		for (int i=0;i<10;i++){
			producer.send("message no :"+i);
		}

		consumer.stop();
	}
}
