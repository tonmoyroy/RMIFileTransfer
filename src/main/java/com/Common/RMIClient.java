package com.Common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClient extends Remote{

	void recvMsg(String msg) throws RemoteException;
}
