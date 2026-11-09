package businessLogic;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import data_access.DataAccess;

public class BLFactory {

    public BLFacade getBusinessLogicFactory(boolean isLocal) {
        try {
            if (isLocal) {
                DataAccess da = new DataAccess();
                return new BLFacadeImplementation(da);
            } else {
                ConfigXML config = ConfigXML.getInstance();
                String serviceName = "http://" + config.getBusinessLogicNode() + ":" 
                        + config.getBusinessLogicPort() + "/ws/" 
                        + config.getBusinessLogicName() + "?wsdl";     
                URL url = new URL(serviceName);

                QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
                Service service = Service.create(url, qname);

                return service.getPort(BLFacade.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}