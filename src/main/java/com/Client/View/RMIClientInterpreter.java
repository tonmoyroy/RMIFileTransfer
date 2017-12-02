package com.Client.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import com.Common.Credentials;
import com.Common.RMIClient;
import com.Common.RMIServer;

public class RMIClientInterpreter implements Runnable {
	private static final String PROMPT = "> ";
	private final Scanner console = new Scanner(System.in);
	private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
	private final RMIClient myRemoteObj;
	private RMIServer server;
	private long myIdAtServer;
	private boolean receivingCmds = false;
	final public static int BUF_SIZE = 1024 * 64;
	
	public RMIClientInterpreter() throws RemoteException {
		myRemoteObj = new ConsoleOutput();
	}

	public static void copy(InputStream in, OutputStream out) 
            throws IOException {
    	
    	byte[] b = new byte[BUF_SIZE];
        int len;
        while ((len = in.read(b)) >= 0) {
        	out.write(b, 0, len);
        }
        in.close();
        out.close();
    }
	
	public static void upload(RMIServer server, File src, File dest) throws IOException {
        copy (new FileInputStream(src), server.getOutputStream(dest));
    }
	
	/**
	 * Starts the interpreter. The interpreter will be waiting for user input when
	 * this method returns. Calling <code>start</code> on an interpreter that is
	 * already started has no effect.
	 */
	public void start() {
		if (receivingCmds) {
			return;
		}
		receivingCmds = true;
		new Thread(this).start();
	}

	/**
	 * Interprets and performs user commands.
	 */
	public void run() {
		while (receivingCmds) {
			try {
				CmdLine cmdLine = new CmdLine(readNextLine());
				switch (cmdLine.getCmd()) {
				case QUIT:
					receivingCmds = false;
					server.leaveConversation(myIdAtServer);
					boolean forceUnexport = false;
					UnicastRemoteObject.unexportObject(myRemoteObj, forceUnexport);
					break;
				case LOGIN:
					lookupServer(cmdLine.getParameter(0));
					myIdAtServer = server.login(myRemoteObj,
							new Credentials(cmdLine.getParameter(1), cmdLine.getParameter(2)));
					System.out.println("Login Successfull.");
					break;
				case USER:
					server.changeNickname(myIdAtServer, cmdLine.getParameter(0));
					break;
				case UPLOAD:
					if (myIdAtServer != 0) {
						String srcFilename = cmdLine.getParameter(0);
						if (srcFilename != null && srcFilename.length() > 0) {
							String destFilename = srcFilename;
							
							upload(server, new File(srcFilename), new File(destFilename));
							System.out.println("Upload Successfull");
						}
					}
				default:
					System.out.println("Invalid User/Command");
				}
			} catch (Exception e) {
				outMgr.println("Operation failed");
			}
		}
	}

	private void lookupServer(String host) throws NotBoundException, MalformedURLException, RemoteException {
		server = (RMIServer) Naming.lookup("//" + host + "/" + RMIServer.SERVER_NAME_IN_REGISTRY);
	}

	private String readNextLine() {
		outMgr.print(PROMPT);
		return console.nextLine();
	}

	private class ConsoleOutput extends UnicastRemoteObject implements RMIClient {

		public ConsoleOutput() throws RemoteException {
		}

		public void recvMsg(String msg) {
			outMgr.println((String) msg);
		}
	}
}
