// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

public class ConnectionFeatureNotAvailableException extends CommunicationsException
{
    public ConnectionFeatureNotAvailableException(final MySQLConnection conn, final long lastPacketSentTimeMs, final Exception underlyingException) {
        super(conn, lastPacketSentTimeMs, 0L, underlyingException);
    }
    
    public String getMessage() {
        return "Feature not available in this distribution of Connector/J";
    }
    
    public String getSQLState() {
        return "01S00";
    }
}
