// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.exceptions.jdbc4;

import com.mysql.jdbc.SQLError;
import com.mysql.jdbc.MySQLConnection;
import com.mysql.jdbc.StreamingNotifiable;
import java.sql.SQLRecoverableException;

public class CommunicationsException extends SQLRecoverableException implements StreamingNotifiable
{
    private String exceptionMessage;
    private boolean streamingResultSetInPlay;
    
    public CommunicationsException(final MySQLConnection conn, final long lastPacketSentTimeMs, final long lastPacketReceivedTimeMs, final Exception underlyingException) {
        this.streamingResultSetInPlay = false;
        this.exceptionMessage = SQLError.createLinkFailureMessageBasedOnHeuristics(conn, lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException, this.streamingResultSetInPlay);
        if (underlyingException != null) {
            this.initCause(underlyingException);
        }
    }
    
    @Override
    public String getMessage() {
        return this.exceptionMessage;
    }
    
    @Override
    public String getSQLState() {
        return "08S01";
    }
    
    @Override
    public void setWasStreamingResults() {
        this.streamingResultSetInPlay = true;
    }
}
