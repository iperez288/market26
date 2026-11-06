package businessLogic;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import configuration.ConfigXML;
import data_access.DataAccess;

public class BLFactory {
	
	private BLFacade mockObj=null;
	
	public BLFactory(BLFacade mock) {
        mockObj = mock;
    }
	
	public BLFactory() {}

	public BLFacade createBLFacade(ConfigXML buisnessLogical) {
		
		if (mockObj!=null) {
			return mockObj;
		}
		
		try {
		BLFacade appFacadeInterface;

		if(buisnessLogical.isBusinessLogicLocal()) {
			DataAccess da= new DataAccess();
			appFacadeInterface=new BLFacadeImplementation(da);
			return appFacadeInterface;
		}else {
			 String serviceName= "http://"+buisnessLogical.getBusinessLogicNode() +":"+ buisnessLogical.getBusinessLogicPort()+"/ws/"+buisnessLogical.getBusinessLogicName()+"?wsdl";	 
			 URL url = new URL(serviceName);


	        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
	 
	        Service service = Service.create(url, qname);

	        appFacadeInterface = service.getPort(BLFacade.class);
	        return appFacadeInterface;
	        }
		}catch(Exception e) {
			throw new RuntimeException();
		}
	}
}
