package org.roblybarger;

import java.util.Vector;

public class TestClient implements ServiceBrowserListener {

	public static final String SERVICE_NAME = "discoveryDemo";
	ServiceBrowser browser;
	Vector<ServiceDescription> descriptors;
	
	public static void main(String[] args) {
		new TestClient();
	}
	
	public TestClient() {
		descriptors = new Vector<ServiceDescription>();
		browser = new ServiceBrowser();
		browser.addServiceBrowserListener(this);
		browser.setServiceName(SERVICE_NAME);
		browser.startListener();
		browser.startLookup();
		System.out.println("Browser started. Will search for 2 secs.");
		try {
			Thread.sleep(2000);
		}
		catch (InterruptedException ie) {
			// ignore
		}
		browser.stopLookup();
		browser.stopListener();

		
		if (descriptors.size()>0) {
			System.out.println("\n---DEMO SERVERS---");
			for (ServiceDescription descriptor : descriptors) {
				System.out.println(descriptor.toString());
			}

		}
		else {
			System.out.println("\n---NO DEMO SERVERS FOUND---");
		}
		
		System.out.println("\nThat's all folks.");
		System.exit(0);
	}

	public void serviceReply(ServiceDescription descriptor) {
		int pos = descriptors.indexOf(descriptor);
		if (pos>-1) {
			descriptors.removeElementAt(pos);
		}
		System.out.println(descriptor.getEncodedInstanceName());
		descriptors.add(descriptor);
	}

}
