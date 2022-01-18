// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.Query;

public class ManyType
{
    public static final ManyType JAVA_LIST;
    public static final ManyType JAVA_SET;
    public static final ManyType JAVA_MAP;
    private final Query.Type queryType;
    private final Underlying underlying;
    private final CollectionTypeConverter typeConverter;
    
    private ManyType(final Underlying underlying) {
        this(underlying, null);
    }
    
    public ManyType(final Underlying underlying, final CollectionTypeConverter typeConverter) {
        this.underlying = underlying;
        this.typeConverter = typeConverter;
        switch (underlying) {
            case LIST: {
                this.queryType = Query.Type.LIST;
                break;
            }
            case SET: {
                this.queryType = Query.Type.SET;
                break;
            }
            default: {
                this.queryType = Query.Type.MAP;
                break;
            }
        }
    }
    
    public Query.Type getQueryType() {
        return this.queryType;
    }
    
    public Underlying getUnderlying() {
        return this.underlying;
    }
    
    public CollectionTypeConverter getTypeConverter() {
        return this.typeConverter;
    }
    
    static {
        JAVA_LIST = new ManyType(Underlying.LIST);
        JAVA_SET = new ManyType(Underlying.SET);
        JAVA_MAP = new ManyType(Underlying.MAP);
    }
    
    public enum Underlying
    {
        LIST, 
        SET, 
        MAP;
    }
}
