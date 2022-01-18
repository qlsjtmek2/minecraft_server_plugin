// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public class CommunicationsException extends SQLException implements StreamingNotifiable
{
    private String exceptionMessage;
    private boolean streamingResultSetInPlay;
    private MySQLConnection conn;
    private long lastPacketSentTimeMs;
    private long lastPacketReceivedTimeMs;
    private Exception underlyingException;
    
    public CommunicationsException(final MySQLConnection conn, final long lastPacketSentTimeMs, final long lastPacketReceivedTimeMs, final Exception underlyingException) {
        this.exceptionMessage = null;
        this.streamingResultSetInPlay = false;
        this.conn = conn;
        this.lastPacketReceivedTimeMs = lastPacketReceivedTimeMs;
        this.lastPacketSentTimeMs = lastPacketSentTimeMs;
        this.underlyingException = underlyingException;
        if (underlyingException != null) {
            this.initCause(underlyingException);
        }
    }
    
    public String getMessage() {
        if (this.exceptionMessage == null) {
            this.exceptionMessage = SQLError.createLinkFailureMessageBasedOnHeuristics(this.conn, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, this.underlyingException, this.streamingResultSetInPlay);
            this.conn = null;
            this.underlyingException = null;
        }
        return this.exceptionMessage;
    }
    
    public String getSQLState() {
        return "08S01";
    }
    
    public void setWasStreamingResults() {
        this.streamingResultSetInPlay = true;
    }
}
