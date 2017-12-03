package com.Common;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RMIServer extends Remote {
	/**
	 * The default URI of the chat server in the RMI registry.
	 */
	public static final String SERVER_NAME_IN_REGISTRY = "RMI_FILE_TRANSFER_SERVER";

	/**
	 * Makes a new participant join the conversation.
	 *
	 * @param remoteNode
	 *            The remote endpoint of the joining participant. This is the remote
	 *            object that will be used to send messages to the participant.
	 * @param credentials
	 *            The credentials of the joining participant.
	 * @return The id of the joining participant. A participant must use this id for
	 *         identification in all communication with the server.
	 */
	long register(RMIClient remoteNode, Credentials credentials) throws RemoteException;
	
	/**
	 * Makes a existing participant join the conversation.
	 *
	 * @param remoteNode
	 *            The remote endpoint of the joining participant. This is the remote
	 *            object that will be used to send messages to the participant.
	 * @param credentials
	 *            The credentials of the joining participant.
	 * @return The id of the joining participant. A participant must use this id for
	 *         identification in all communication with the server.
	 */
	long login(RMIClient remoteNode, Credentials credentials) throws RemoteException;

	/**
	 * Sets a new username for the participant with the specified remote endpoint.
	 *
	 * @param id
	 *            The id of the participant wishing to change username.
	 * @param userName
	 *            The participant's new username.
	 */
	void changeNickname(long id, String username) throws RemoteException;

	/**
	 * The specified participant is removed from the conversation, no more messages
	 * will be sent to that node.
	 *
	 * @param id
	 *            The id of the leaving participant.
	 */
	void leaveConversation(long id) throws RemoteException;

	OutputStream getOutputStream(File file, long userid) throws RemoteException;

	InputStream getInputStream(File file, long userid) throws RemoteException;

	ArrayList<ServerFiles> showUserFiles(long userid) throws RemoteException;
}
