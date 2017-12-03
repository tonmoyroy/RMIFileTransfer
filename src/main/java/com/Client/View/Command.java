package com.Client.View;

/**
 * Defines all commands that can be performed by a user of the chat application.
 */
public enum Command {
    /**
     * Specifies a user name. This name will be prepended to all entries in the chat conversation.
     */
    USER,
    /**
     * Establish a connection to the server. The first parameter is IP address (or host name), the
     * second is port number.
     */
    LOGIN,
    REGISTER,
    /**
     * Leave application.
     */
    QUIT,
    /**
     * IF ALREADY LOGGED IN USER THEN TYPE COMMAND - UPLOAD FILENAME 
     */
    UPLOAD,
    /**
     * IF ALREADY LOGGED IN USER THEN TYPE COMMAND - DOWNLOAD FILENAME 
     */
    DOWNLOAD,
    /**
     * IF ALREADY LOGGED IN USER THEN TYPE COMMAND - SHOWFILES to list all the uploaded files 
     */
    SHOWFILES,
    /**
     * No command was specified. This means the entire command line is interpreted as an entry in
     * the conversation, and is sent to all clients.
     */
    NO_COMMAND
}
