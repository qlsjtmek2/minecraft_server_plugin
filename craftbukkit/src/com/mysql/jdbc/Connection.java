// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Properties;
import java.util.TimeZone;
import com.mysql.jdbc.log.Log;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Connection extends java.sql.Connection, ConnectionProperties
{
    void changeUser(final String p0, final String p1) throws SQLException;
    
    void clearHasTriedMaster();
    
    PreparedStatement clientPrepareStatement(final String p0) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1, final int p2) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int[] p1) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final int p1, final int p2, final int p3) throws SQLException;
    
    PreparedStatement clientPrepareStatement(final String p0, final String[] p1) throws SQLException;
    
    int getActiveStatementCount();
    
    long getIdleFor();
    
    Log getLog() throws SQLException;
    
    String getServerCharacterEncoding();
    
    TimeZone getServerTimezoneTZ();
    
    String getStatementComment();
    
    boolean hasTriedMaster();
    
    boolean isInGlobalTx();
    
    void setInGlobalTx(final boolean p0);
    
    boolean isMasterConnection();
    
    boolean isNoBackslashEscapesSet();
    
    boolean isSameResource(final Connection p0);
    
    boolean lowerCaseTableNames();
    
    boolean parserKnowsUnicode();
    
    void ping() throws SQLException;
    
    void resetServerState() throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1, final int p2) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int p1, final int p2, final int p3) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final int[] p1) throws SQLException;
    
    PreparedStatement serverPrepareStatement(final String p0, final String[] p1) throws SQLException;
    
    void setFailedOver(final boolean p0);
    
    void setPreferSlaveDuringFailover(final boolean p0);
    
    void setStatementComment(final String p0);
    
    void shutdownServer() throws SQLException;
    
    boolean supportsIsolationLevel();
    
    boolean supportsQuotedIdentifiers();
    
    boolean supportsTransactions();
    
    boolean versionMeetsMinimum(final int p0, final int p1, final int p2) throws SQLException;
    
    void reportQueryTime(final long p0);
    
    boolean isAbonormallyLongQuery(final long p0);
    
    void initializeExtension(final Extension p0) throws SQLException;
    
    int getAutoIncrementIncrement();
    
    boolean hasSameProperties(final Connection p0);
    
    Properties getProperties();
    
    String getHost();
    
    void setProxy(final MySQLConnection p0);
}
