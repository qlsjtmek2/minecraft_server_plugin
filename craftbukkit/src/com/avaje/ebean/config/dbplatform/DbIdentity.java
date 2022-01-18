// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class DbIdentity
{
    private boolean supportsSequence;
    private boolean supportsIdentity;
    private boolean supportsGetGeneratedKeys;
    private String selectLastInsertedIdTemplate;
    private IdType idType;
    
    public DbIdentity() {
        this.idType = IdType.IDENTITY;
    }
    
    public boolean isSupportsGetGeneratedKeys() {
        return this.supportsGetGeneratedKeys;
    }
    
    public void setSupportsGetGeneratedKeys(final boolean supportsGetGeneratedKeys) {
        this.supportsGetGeneratedKeys = supportsGetGeneratedKeys;
    }
    
    public String getSelectLastInsertedId(final String table) {
        if (this.selectLastInsertedIdTemplate == null) {
            return null;
        }
        return this.selectLastInsertedIdTemplate.replace("{table}", table);
    }
    
    public void setSelectLastInsertedIdTemplate(final String selectLastInsertedIdTemplate) {
        this.selectLastInsertedIdTemplate = selectLastInsertedIdTemplate;
    }
    
    public boolean isSupportsSequence() {
        return this.supportsSequence;
    }
    
    public void setSupportsSequence(final boolean supportsSequence) {
        this.supportsSequence = supportsSequence;
    }
    
    public boolean isSupportsIdentity() {
        return this.supportsIdentity;
    }
    
    public void setSupportsIdentity(final boolean supportsIdentity) {
        this.supportsIdentity = supportsIdentity;
    }
    
    public IdType getIdType() {
        return this.idType;
    }
    
    public void setIdType(final IdType idType) {
        this.idType = idType;
    }
}
