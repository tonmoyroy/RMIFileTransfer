package com.Server.Startup;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.Server.Controller.ServerController;

public class RMIFileTransferServer {

	public static void main(String[] args) {
		try {
			new RMIFileTransferServer().startRegistry();
			Naming.rebind(ServerController.SERVER_NAME_IN_REGISTRY, new ServerController());
			System.out.println("Server is ready...");
		} catch (RemoteException ex) {
			System.out.println("Could not start RMI server.");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void startRegistry() throws RemoteException {
		try {
			LocateRegistry.getRegistry().list();
		} catch (RemoteException noRegistryIsRunning) {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
		}
	}

}
