package esb.wso2.org.client;


/**
 * Created by chanaka on 3/12/17.
 */

public class MQClient {
	public static void main(String[] args) throws Exception{

		MQProducer producer=new MQProducer();
		producer.send("Hello This is a test");

		MQConsumer consumer=new MQConsumer(new MessageHandler());

		consumer.consume();

		MQConnectionBuilder.getInstance().closeConnection();
	}
}
