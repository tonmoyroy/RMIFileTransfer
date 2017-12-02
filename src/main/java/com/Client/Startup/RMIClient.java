package com.Client.Startup;

import java.rmi.RemoteException;

import com.Client.View.RMIClientInterpreter;

public class RMIClient {

	public static void main(String[] args) {
		try {
            new RMIClientInterpreter().start();
        } catch (RemoteException ex) {
            System.out.println("Could not start client.");
        }
	}

}
