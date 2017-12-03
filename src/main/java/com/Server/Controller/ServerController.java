package com.Server.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.Common.Credentials;
import com.Common.RMIClient;
import com.Common.RMIServer;
import com.Common.ServerFiles;
import com.Server.Model.DatabaseFileUpload;
import com.Server.Model.ParticipantManager;

import com.Common.RMIInputStream;
import com.Common.RMIInputStreamImpl;

import com.Common.RMIOutputStream;
import com.Common.RMIOutputStreamImpl;

public class ServerController extends UnicastRemoteObject implements RMIServer {

	private final ParticipantManager participantManager = new ParticipantManager();

	public ServerController() throws RemoteException {
	}

	public long register(RMIClient remoteNode, Credentials credentials) {
		long participantId = participantManager.createParticipant(remoteNode, credentials);
		participantManager.sendConvToParticipant(participantId);
		System.out.println("NEW USER LOGGED IN");
		return participantId;
	}

	public long login(RMIClient remoteNode, Credentials credentials) {
		long participantId = participantManager.checkParticipant(remoteNode, credentials);
		participantManager.sendConvToParticipant(participantId);
		System.out.println("NEW USER LOGGED IN");
		return participantId;
	}

	public void leaveConversation(long id) {
		participantManager.findParticipant(id).leaveConversation();
		participantManager.removeParticipant(id);
		System.out.println("USER LOGGED OUT");
	}

	public void changeNickname(long id, String username) throws RemoteException {
		participantManager.findParticipant(id).changeUsername(username);
		System.out.println("USER NAME CHANGED");
	}

	public OutputStream getOutputStream(File f, long userid) {
		String filename = f.getName();
		String filepath = System.getProperty("user.dir");

		DatabaseFileUpload model = null;
		model = new DatabaseFileUpload();
		OutputStream output = null;
		boolean status = model.UploadFile(filename, filepath, userid);
		if (status) {
			try {
				output = new RMIOutputStream(new RMIOutputStreamImpl(new FileOutputStream(f)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return output;
	}

	public InputStream getInputStream(File file, long userid) throws RemoteException {
		InputStream output = null;
		String filename = file.getName();
		String filepath = System.getProperty("user.dir");

		DatabaseFileUpload model = new DatabaseFileUpload();
		boolean status = model.DownloadFile(filename, filepath, userid);
		if (status) {
			try {
				output = new RMIInputStream(new RMIInputStreamImpl(new FileInputStream(file)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return output;
	}

	public ArrayList<ServerFiles> showUserFiles(long userid) {
		DatabaseFileUpload model = null;
		model = new DatabaseFileUpload();
		return model.getUserFile(userid);
	}
}
