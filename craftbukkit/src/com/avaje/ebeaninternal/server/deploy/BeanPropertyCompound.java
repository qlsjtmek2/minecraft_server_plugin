// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.query.SqlBeanLoad;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import java.util.Iterator;
import java.util.List;
import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebeaninternal.server.type.CtCompoundPropertyElAdapter;
import java.util.LinkedHashMap;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.CtCompoundType;

public class BeanPropertyCompound extends BeanProperty
{
    private final CtCompoundType<?> compoundType;
    private final ScalarTypeConverter typeConverter;
    private final BeanProperty[] scalarProperties;
    private final LinkedHashMap<String, BeanProperty> propertyMap;
    private final LinkedHashMap<String, CtCompoundPropertyElAdapter> nonScalarMap;
    private final BeanPropertyCompoundRoot root;
    
    public BeanPropertyCompound(final BeanDescriptorMap owner, final BeanDescriptor<?> descriptor, final DeployBeanPropertyCompound deploy) {
        super(owner, descriptor, deploy);
        this.propertyMap = new LinkedHashMap<String, BeanProperty>();
        this.nonScalarMap = new LinkedHashMap<String, CtCompoundPropertyElAdapter>();
        this.compoundType = deploy.getCompoundType();
        this.typeConverter = deploy.getTypeConverter();
        this.root = deploy.getFlatProperties(owner, descriptor);
        this.scalarProperties = this.root.getScalarProperties();
        for (int i = 0; i < this.scalarProperties.length; ++i) {
            this.propertyMap.put(this.scalarProperties[i].getName(), this.scalarProperties[i]);
        }
        final List<CtCompoundProperty> nonScalarPropsList = this.root.getNonScalarProperties();
        for (int j = 0; j < nonScalarPropsList.size(); ++j) {
            final CtCompoundProperty ctProp = nonScalarPropsList.get(j);
            final CtCompoundPropertyElAdapter adapter = new CtCompoundPropertyElAdapter(ctProp);
            this.nonScalarMap.put(ctProp.getRelativeName(), adapter);
        }
    }
    
    public void initialise() {
        if (!this.isTransient && this.compoundType == null) {
            final String msg = "No cvoInternalType assigned to " + this.descriptor.getFullName() + "." + this.getName();
            throw new RuntimeException(msg);
        }
    }
    
    public void setDeployOrder(final int deployOrder) {
        this.deployOrder = deployOrder;
        for (final CtCompoundPropertyElAdapter adapter : this.nonScalarMap.values()) {
            adapter.setDeployOrder(deployOrder);
        }
    }
    
    public Object getValueUnderlying(final Object bean) {
        Object value = this.getValue(bean);
        if (this.typeConverter != null) {
            value = this.typeConverter.unwrapValue(value);
        }
        return value;
    }
    
    public Object getValue(final Object bean) {
        return super.getValue(bean);
    }
    
    public Object getValueIntercept(final Object bean) {
        return super.getValueIntercept(bean);
    }
    
    public void setValue(final Object bean, final Object value) {
        super.setValue(bean, value);
    }
    
    public void setValueIntercept(final Object bean, final Object value) {
        super.setValueIntercept(bean, value);
    }
    
    public ElPropertyValue buildElPropertyValue(final String propName, final String remainder, ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        if (chain == null) {
            chain = new ElPropertyChainBuilder(true, propName);
        }
        chain.add(this);
        final BeanProperty p = this.propertyMap.get(remainder);
        if (p != null) {
            return chain.add(p).build();
        }
        final CtCompoundPropertyElAdapter elAdapter = this.nonScalarMap.get(remainder);
        if (elAdapter == null) {
            throw new RuntimeException("property [" + remainder + "] not found in " + this.getFullBeanName());
        }
        return chain.add(elAdapter).build();
    }
    
    public void appendSelect(final DbSqlContext ctx, final boolean subQuery) {
        if (!this.isTransient) {
            for (int i = 0; i < this.scalarProperties.length; ++i) {
                this.scalarProperties[i].appendSelect(ctx, subQuery);
            }
        }
    }
    
    public BeanProperty[] getScalarProperties() {
        return this.scalarProperties;
    }
    
    public Object readSet(final DbReadContext ctx, final Object bean, final Class<?> type) throws SQLException {
        final boolean assignable = type == null || this.owningType.isAssignableFrom(type);
        final Object v = this.compoundType.read(ctx.getDataReader());
        if (assignable) {
            this.setValue(bean, v);
        }
        return v;
    }
    
    public Object read(final DbReadContext ctx) throws SQLException {
        Object v = this.compoundType.read(ctx.getDataReader());
        if (this.typeConverter != null) {
            v = this.typeConverter.wrapValue(v);
        }
        return v;
    }
    
    public void loadIgnore(final DbReadContext ctx) {
        this.compoundType.loadIgnore(ctx.getDataReader());
    }
    
    public void load(final SqlBeanLoad sqlBeanLoad) throws SQLException {
        sqlBeanLoad.load(this);
    }
    
    public Object elGetReference(final Object bean) {
        return bean;
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object bean) {
        final Object valueObject = this.getValueIntercept(bean);
        this.compoundType.jsonWrite(ctx, valueObject, this.name);
    }
    
    public void jsonRead(final ReadJsonContext ctx, final Object bean) {
        final Object objValue = this.compoundType.jsonRead(ctx);
        this.setValue(bean, objValue);
    }
}
