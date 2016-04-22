import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lds.exito.ic.client.jenkins.JenkinsRestClient;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger log = LoggerFactory.getLogger(App.class);
    public static void main( String[] args )
    {
       JenkinsRestClient rest = new JenkinsRestClient("http://192.168.147.226:8080/jenkins", "admin", "lds.123", "lds");
       log.debug("Creando prueba");
       rest.setBuildValidationThreshold(30000);
       log.info(rest.buildJob("DomainModel").toString());
    }
}
