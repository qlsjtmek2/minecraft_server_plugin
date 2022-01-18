// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Arrays;
import javax.persistence.PersistenceException;

public class ValidationException extends PersistenceException
{
    private static final long serialVersionUID = -6185355529565362494L;
    final InvalidValue invalid;
    
    public ValidationException(final InvalidValue invalid) {
        super("validation failed for: " + invalid.getBeanType());
        this.invalid = invalid;
    }
    
    public InvalidValue getInvalid() {
        return this.invalid;
    }
    
    public InvalidValue[] getErrors() {
        return this.invalid.getErrors();
    }
    
    public String toString() {
        return super.toString() + ": " + Arrays.toString(this.getErrors());
    }
}
