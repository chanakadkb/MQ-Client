package esb.wso2.org.client;

import com.ibm.mq.MQMessage;
import com.ibm.mq.headers.MQDH;
import com.ibm.mq.headers.MQHeaderList;
import com.ibm.mq.headers.MQMD;

public class MessageHandler{


	public void handle(MQMessage message)throws Exception {

		System.out.println("A message received : \n" + new MQHeaderList(message));

		//Print message description
		MQMD md=new MQMD();
		md.copyFrom(message);
		System.out.println(md);

		message.getDataLength();
		int strLen = message.getDataLength();
		byte[] strData = new byte[strLen];
		message.readFully(strData);
		System.out.println(new String(strData));
	}
}
