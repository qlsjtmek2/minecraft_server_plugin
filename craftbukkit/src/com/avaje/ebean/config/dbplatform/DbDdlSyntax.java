// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DbDdlSyntax
{
    private boolean renderIndexForFkey;
    private boolean inlinePrimaryKeyConstraint;
    private boolean addOneToOneUniqueContraint;
    private int maxConstraintNameLength;
    private int columnNameWidth;
    private String dropTableCascade;
    private String dropIfExists;
    private String newLine;
    private String identity;
    private String pkPrefix;
    private String disableReferentialIntegrity;
    private String enableReferentialIntegrity;
    private String foreignKeySuffix;
    
    public DbDdlSyntax() {
        this.renderIndexForFkey = true;
        this.inlinePrimaryKeyConstraint = false;
        this.addOneToOneUniqueContraint = false;
        this.maxConstraintNameLength = 32;
        this.columnNameWidth = 25;
        this.newLine = "\r\n";
        this.identity = "auto_increment";
        this.pkPrefix = "pk_";
    }
    
    public String getPrimaryKeyName(final String tableName) {
        String pk = this.pkPrefix + tableName;
        if (pk.length() > this.maxConstraintNameLength) {
            pk = pk.substring(0, this.maxConstraintNameLength);
        }
        return pk;
    }
    
    public String getIdentity() {
        return this.identity;
    }
    
    public void setIdentity(final String identity) {
        this.identity = identity;
    }
    
    public int getColumnNameWidth() {
        return this.columnNameWidth;
    }
    
    public void setColumnNameWidth(final int columnNameWidth) {
        this.columnNameWidth = columnNameWidth;
    }
    
    public String getNewLine() {
        return this.newLine;
    }
    
    public void setNewLine(final String newLine) {
        this.newLine = newLine;
    }
    
    public String getPkPrefix() {
        return this.pkPrefix;
    }
    
    public void setPkPrefix(final String pkPrefix) {
        this.pkPrefix = pkPrefix;
    }
    
    public String getDisableReferentialIntegrity() {
        return this.disableReferentialIntegrity;
    }
    
    public void setDisableReferentialIntegrity(final String disableReferentialIntegrity) {
        this.disableReferentialIntegrity = disableReferentialIntegrity;
    }
    
    public String getEnableReferentialIntegrity() {
        return this.enableReferentialIntegrity;
    }
    
    public void setEnableReferentialIntegrity(final String enableReferentialIntegrity) {
        this.enableReferentialIntegrity = enableReferentialIntegrity;
    }
    
    public boolean isRenderIndexForFkey() {
        return this.renderIndexForFkey;
    }
    
    public void setRenderIndexForFkey(final boolean renderIndexForFkey) {
        this.renderIndexForFkey = renderIndexForFkey;
    }
    
    public String getDropIfExists() {
        return this.dropIfExists;
    }
    
    public void setDropIfExists(final String dropIfExists) {
        this.dropIfExists = dropIfExists;
    }
    
    public String getDropTableCascade() {
        return this.dropTableCascade;
    }
    
    public void setDropTableCascade(final String dropTableCascade) {
        this.dropTableCascade = dropTableCascade;
    }
    
    public String getForeignKeySuffix() {
        return this.foreignKeySuffix;
    }
    
    public void setForeignKeySuffix(final String foreignKeySuffix) {
        this.foreignKeySuffix = foreignKeySuffix;
    }
    
    public int getMaxConstraintNameLength() {
        return this.maxConstraintNameLength;
    }
    
    public void setMaxConstraintNameLength(final int maxFkeyLength) {
        this.maxConstraintNameLength = maxFkeyLength;
    }
    
    public boolean isAddOneToOneUniqueContraint() {
        return this.addOneToOneUniqueContraint;
    }
    
    public void setAddOneToOneUniqueContraint(final boolean addOneToOneUniqueContraint) {
        this.addOneToOneUniqueContraint = addOneToOneUniqueContraint;
    }
    
    public boolean isInlinePrimaryKeyConstraint() {
        return this.inlinePrimaryKeyConstraint;
    }
    
    public void setInlinePrimaryKeyConstraint(final boolean inlinePrimaryKeyConstraint) {
        this.inlinePrimaryKeyConstraint = inlinePrimaryKeyConstraint;
    }
    
    public String getIndexName(final String table, final String propName, final int ixCount) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("ix_");
        buffer.append(table);
        buffer.append("_");
        buffer.append(propName);
        this.addSuffix(buffer, ixCount);
        return buffer.toString();
    }
    
    public String getForeignKeyName(final String table, final String propName, final int fkCount) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append("fk_");
        buffer.append(table);
        buffer.append("_");
        buffer.append(propName);
        this.addSuffix(buffer, fkCount);
        return buffer.toString();
    }
    
    protected void addSuffix(final StringBuilder buffer, final int count) {
        final String suffixNr = Integer.toString(count);
        final int suffixLen = suffixNr.length() + 1;
        if (buffer.length() + suffixLen > this.maxConstraintNameLength) {
            buffer.setLength(this.maxConstraintNameLength - suffixLen);
        }
        buffer.append("_");
        buffer.append(suffixNr);
    }
}
