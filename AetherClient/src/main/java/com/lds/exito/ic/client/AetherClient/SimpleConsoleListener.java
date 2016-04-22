package com.lds.exito.ic.client.AetherClient;

import org.eclipse.aether.transfer.AbstractTransferListener;
import org.eclipse.aether.transfer.TransferEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleConsoleListener extends AbstractTransferListener {

	private static Logger log = LoggerFactory.getLogger(SimpleConsoleListener.class);
	
	 @Override
	    public void transferSucceeded( TransferEvent event )
	    {
	     log.trace((event.getRequestType() == TransferEvent.RequestType.PUT ? "Cargado " : "Descargado ") + 
	    		 event.getResource().getFile().getName());   
	    }

	    @Override
	    public void transferFailed( TransferEvent event )
	    {
	    	log.error("Error en transferencia",event.getException());
	    }
}
