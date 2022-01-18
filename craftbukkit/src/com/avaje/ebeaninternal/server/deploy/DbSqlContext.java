// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

public interface DbSqlContext
{
    void addJoin(final String p0, final String p1, final TableJoinColumn[] p2, final String p3, final String p4);
    
    void pushSecondaryTableAlias(final String p0);
    
    void pushTableAlias(final String p0);
    
    void popTableAlias();
    
    void addEncryptedProp(final BeanProperty p0);
    
    BeanProperty[] getEncryptedProps();
    
    DbSqlContext append(final char p0);
    
    DbSqlContext append(final String p0);
    
    String peekTableAlias();
    
    void appendRawColumn(final String p0);
    
    void appendColumn(final String p0, final String p1);
    
    void appendColumn(final String p0);
    
    void appendFormulaSelect(final String p0);
    
    void appendFormulaJoin(final String p0, final boolean p1);
    
    int length();
    
    String getContent();
    
    String peekJoin();
    
    void pushJoin(final String p0);
    
    void popJoin();
    
    String getTableAlias(final String p0);
    
    String getTableAliasManyWhere(final String p0);
    
    String getRelativePrefix(final String p0);
}
