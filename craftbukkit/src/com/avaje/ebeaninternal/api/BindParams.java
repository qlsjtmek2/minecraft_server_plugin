// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

public class BindParams implements Serializable
{
    private static final long serialVersionUID = 4541081933302086285L;
    private ArrayList<Param> positionedParameters;
    private HashMap<String, Param> namedParameters;
    private int queryPlanHash;
    private String preparedSql;
    
    public BindParams() {
        this.positionedParameters = new ArrayList<Param>();
        this.namedParameters = new HashMap<String, Param>();
        this.queryPlanHash = 1;
    }
    
    public BindParams copy() {
        final BindParams copy = new BindParams();
        for (final Param p : this.positionedParameters) {
            copy.positionedParameters.add(p.copy());
        }
        for (final Map.Entry<String, Param> entry : this.namedParameters.entrySet()) {
            copy.namedParameters.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }
    
    public int queryBindHash() {
        int hc = this.namedParameters.hashCode();
        for (int i = 0; i < this.positionedParameters.size(); ++i) {
            hc = hc * 31 + this.positionedParameters.get(i).hashCode();
        }
        return hc;
    }
    
    public int hashCode() {
        int hc = this.getClass().hashCode();
        hc = hc * 31 + this.namedParameters.hashCode();
        for (int i = 0; i < this.positionedParameters.size(); ++i) {
            hc = hc * 31 + this.positionedParameters.get(i).hashCode();
        }
        hc = hc * 31 + ((this.preparedSql == null) ? 0 : this.preparedSql.hashCode());
        return hc;
    }
    
    public boolean equals(final Object o) {
        return o != null && (o == this || (o instanceof BindParams && this.hashCode() == o.hashCode()));
    }
    
    public boolean isEmpty() {
        return this.positionedParameters.isEmpty() && this.namedParameters.isEmpty();
    }
    
    public int size() {
        return this.positionedParameters.size();
    }
    
    public boolean requiresNamedParamsPrepare() {
        return !this.namedParameters.isEmpty() && this.positionedParameters.isEmpty();
    }
    
    public void setNullParameter(final int position, final int jdbcType) {
        final Param p = this.getParam(position);
        p.setInNullType(jdbcType);
    }
    
    public void setParameter(final int position, final Object value, final int outType) {
        this.addToQueryPlanHash(String.valueOf(position), value);
        final Param p = this.getParam(position);
        p.setInValue(value);
        p.setOutType(outType);
    }
    
    public void setParameter(final int position, final Object value) {
        this.addToQueryPlanHash(String.valueOf(position), value);
        final Param p = this.getParam(position);
        p.setInValue(value);
    }
    
    public void registerOut(final int position, final int outType) {
        final Param p = this.getParam(position);
        p.setOutType(outType);
    }
    
    private Param getParam(final String name) {
        Param p = this.namedParameters.get(name);
        if (p == null) {
            p = new Param();
            this.namedParameters.put(name, p);
        }
        return p;
    }
    
    private Param getParam(final int position) {
        final int more = position - this.positionedParameters.size();
        if (more > 0) {
            for (int i = 0; i < more; ++i) {
                this.positionedParameters.add(new Param());
            }
        }
        return this.positionedParameters.get(position - 1);
    }
    
    public void setParameter(final String name, final Object value, final int outType) {
        this.addToQueryPlanHash(name, value);
        final Param p = this.getParam(name);
        p.setInValue(value);
        p.setOutType(outType);
    }
    
    public void setNullParameter(final String name, final int jdbcType) {
        final Param p = this.getParam(name);
        p.setInNullType(jdbcType);
    }
    
    public Param setParameter(final String name, final Object value) {
        this.addToQueryPlanHash(name, value);
        final Param p = this.getParam(name);
        p.setInValue(value);
        return p;
    }
    
    private void addToQueryPlanHash(final String name, final Object value) {
        if (value != null && value instanceof Collection) {
            this.queryPlanHash = this.queryPlanHash * 31 + name.hashCode();
            this.queryPlanHash = this.queryPlanHash * 31 + ((Collection)value).size();
        }
    }
    
    public int getQueryPlanHash() {
        return this.queryPlanHash;
    }
    
    public Param setEncryptionKey(final String name, final Object value) {
        final Param p = this.getParam(name);
        p.setEncryptionKey(value);
        return p;
    }
    
    public void registerOut(final String name, final int outType) {
        final Param p = this.getParam(name);
        p.setOutType(outType);
    }
    
    public Param getParameter(final int position) {
        return this.getParam(position);
    }
    
    public Param getParameter(final String name) {
        return this.getParam(name);
    }
    
    public List<Param> positionedParameters() {
        return this.positionedParameters;
    }
    
    public void setPreparedSql(final String preparedSql) {
        this.preparedSql = preparedSql;
    }
    
    public String getPreparedSql() {
        return this.preparedSql;
    }
    
    public static final class OrderedList
    {
        final List<Param> paramList;
        final StringBuilder preparedSql;
        
        public OrderedList() {
            this(new ArrayList<Param>());
        }
        
        public OrderedList(final List<Param> paramList) {
            this.paramList = paramList;
            this.preparedSql = new StringBuilder();
        }
        
        public void add(final Param param) {
            this.paramList.add(param);
        }
        
        public int size() {
            return this.paramList.size();
        }
        
        public List<Param> list() {
            return this.paramList;
        }
        
        public void appendSql(final String parsedSql) {
            this.preparedSql.append(parsedSql);
        }
        
        public String getPreparedSql() {
            return this.preparedSql.toString();
        }
    }
    
    public static final class Param
    {
        private boolean encryptionKey;
        private boolean isInParam;
        private boolean isOutParam;
        private int type;
        private Object inValue;
        private Object outValue;
        private int textLocation;
        
        public Param copy() {
            final Param copy = new Param();
            copy.isInParam = this.isInParam;
            copy.isOutParam = this.isOutParam;
            copy.type = this.type;
            copy.inValue = this.inValue;
            copy.outValue = this.outValue;
            return copy;
        }
        
        public int hashCode() {
            int hc = this.getClass().hashCode();
            hc = hc * 31 + (this.isInParam ? 0 : 1);
            hc = hc * 31 + (this.isOutParam ? 0 : 1);
            hc = hc * 31 + this.type;
            hc = hc * 31 + ((this.inValue == null) ? 0 : this.inValue.hashCode());
            return hc;
        }
        
        public boolean equals(final Object o) {
            return o != null && (o == this || (o instanceof Param && this.hashCode() == o.hashCode()));
        }
        
        public boolean isInParam() {
            return this.isInParam;
        }
        
        public boolean isOutParam() {
            return this.isOutParam;
        }
        
        public int getType() {
            return this.type;
        }
        
        public void setOutType(final int type) {
            this.type = type;
            this.isOutParam = true;
        }
        
        public void setInValue(final Object in) {
            this.inValue = in;
            this.isInParam = true;
        }
        
        public void setEncryptionKey(final Object in) {
            this.inValue = in;
            this.isInParam = true;
            this.encryptionKey = true;
        }
        
        public void setInNullType(final int type) {
            this.type = type;
            this.inValue = null;
            this.isInParam = true;
        }
        
        public Object getOutValue() {
            return this.outValue;
        }
        
        public Object getInValue() {
            return this.inValue;
        }
        
        public void setOutValue(final Object out) {
            this.outValue = out;
        }
        
        public int getTextLocation() {
            return this.textLocation;
        }
        
        public void setTextLocation(final int textLocation) {
            this.textLocation = textLocation;
        }
        
        public boolean isEncryptionKey() {
            return this.encryptionKey;
        }
    }
}
