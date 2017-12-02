package com.Server.Model;

import java.rmi.RemoteException;

import com.Common.MessageException;
import com.Common.RMIClient;


public class Participant {
    private static final String JOIN_MESSAGE = " joined conversation.";
    private static final String LEAVE_MESSAGE = " left conversation.";
    private static final String USERNAME_DELIMETER = ": ";
    private static final String DEFAULT_USERNAME = "anonymous";
    private final long id;
    private final RMIClient remoteNode;
    private final ParticipantManager participantMgr;
    private String username;

    /**
     * Creates a new instance with the specified username and remote node.
     *
     * @param id         The unique identifier of this participant.
     * @param username   The username of the newly created instance.
     * @param remoteNode The remote endpoint of the newly created instance.
     * @param mgr        The only existing participant manager.
     */
    public Participant(long id, String username, RMIClient remoteNode, ParticipantManager mgr) {
        this.id = id;
        this.username = username;
        this.remoteNode = remoteNode;
        this.participantMgr = mgr;
    }

    /**
     * Creates a new instance with the specified remote node and the default username.
     *
     * @param id         The unique identifier of this participant.
     * @param remoteNode The remote endpoint of the newly created instance.
     * @param mgr        The only existing participant manager.
     */
    public Participant(long id, RMIClient remoteNode, ParticipantManager mgr) {
        this(id, DEFAULT_USERNAME, remoteNode, mgr);
    }

    /**
     * Send the specified message to the participant's remote node.
     *
     * @param msg The message to send.
     */
    public void send(String msg) {
        try {
            remoteNode.recvMsg(msg);
        } catch (RemoteException re) {
            throw new MessageException("Failed to deliver message to " + username + ".");
        }
    }

    /**
     * Send the specified message to all participants in the conversation, including myself.
     *
     * @param msg The message to send.
     */
    public void broadcast(String msg) {
        participantMgr.broadcast(username + USERNAME_DELIMETER + msg);
    }

    /**
     * Checks if the specified remote node is the remote endpoint of this participant.
     *
     * @param remoteNode The searched remote node.
     * @return <code>true</code> if the specified remote node is the remote endpoint of this
     *         participant, <code>false</code> if it is not.
     */
    public boolean hasRemoteNode(RMIClient remoteNode) {
        return remoteNode.equals(this.remoteNode);
    }

    /**
     * @param username The new username of this participant.
     */
    public void changeUsername(String username) {
        this.username = username;
        broadcast(username + JOIN_MESSAGE);
    }

    /**
     * Inform other participants that this participant is leaving the conversation.
     */
    public void leaveConversation() {
        broadcast(username + LEAVE_MESSAGE);
    }

}
