package esb.wso2.org.client.util;

/**
 * Created by chanaka on 3/12/17.
 */
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

	private InputStream in;

	public Properties get(){
		Properties props=null;
		try{
			props=new Properties();
			in = getClass().getClassLoader().getResourceAsStream(MQConstants.PROPERTIES);
			if(in!=null){
				props.load(in);
				in.close();
			}else{
				throw new FileNotFoundException("Property file not found in the class path");
			}
		}catch (Exception e){
			System.err.print("Exception: "+e);
		}
		return props;
	}
}
