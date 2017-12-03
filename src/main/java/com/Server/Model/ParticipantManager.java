package com.Server.Model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import com.Common.RMIClient;
import com.Common.Credentials;

/**
 * Keeps track of all active participants in the conversation, and is also
 * responsible for sending messages to participants.
 */
public class ParticipantManager {
	private final Random idGenerator = new Random();
	private final Conversation conversation = new Conversation();
	private final Map<Object, Object> participants = Collections.synchronizedMap(new HashMap<Object, Object>());

	public long createParticipant(RMIClient remoteNode, Credentials credentials) {
		long participantId = idGenerator.nextLong();
		Participant newParticipant = new Participant(participantId, credentials.getUsername(),
				credentials.getPassword(), remoteNode, this);
		participants.put(participantId, newParticipant);
		return participantId;
	}

	/**
	 * Sends the entire conversation to the specified participant.
	 *
	 * @param id
	 *            The id of the participant that shall receive the conversation.
	 */
	public void sendConvToParticipant(long id) {
		Participant participant = (Participant) participants.get(id);
		for (String entry : conversation.getConversation()) {
			participant.send(entry);
		}
	}

	/**
	 * Searches for a participant with the specified id.
	 *
	 * @param id
	 *            The id of the searched participant.
	 * @return The participant with the specified id, or <code>null</code> if there
	 *         is no such participant.
	 */
	public Participant findParticipant(long id) {
		return (Participant) participants.get(id);
	}

	public long checkParticipant(RMIClient remoteNode, Credentials credentials) {
		long participantId = 0;
		Participant newParticipant = new Participant(participantId, credentials.getUsername(),
				credentials.getPassword(), remoteNode, this);
		participantId = newParticipant.id;
		if (participantId != 0) {
			participants.put(participantId, newParticipant);
			return participantId;
		} else
			return 0;
	}

	/**
	 * Removes the specified participant from the conversation. No more messages
	 * will be sent to that participant.
	 *
	 * @param id
	 *            The id of the participant that shall be removed.
	 */
	public void removeParticipant(long id) {
		participants.remove(id);
	}

	/**
	 * Send the specified message to all participants in the conversation.
	 *
	 * @param msg
	 *            The message to send.
	 */
	void broadcast(String msg) {
		conversation.appendEntry(msg);
		synchronized (participants) {
			for (Object participant : participants.values()) {
				((Participant) participant).send(msg);
			}
		}
	}

}
