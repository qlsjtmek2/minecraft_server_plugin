// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebean.text.json.JsonElement;
import com.avaje.ebean.text.json.JsonElementObject;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import com.avaje.ebean.config.CompoundTypeProperty;
import java.util.Map;
import com.avaje.ebean.config.CompoundType;

public final class CtCompoundType<V> implements ScalarDataReader<V>
{
    private final Class<V> cvoClass;
    private final CompoundType<V> cvoType;
    private final Map<String, CompoundTypeProperty<V, ?>> propertyMap;
    private final ScalarDataReader<Object>[] propReaders;
    private final CompoundTypeProperty<V, ?>[] properties;
    
    public CtCompoundType(final Class<V> cvoClass, final CompoundType<V> cvoType, final ScalarDataReader<Object>[] propReaders) {
        this.cvoClass = cvoClass;
        this.cvoType = cvoType;
        this.properties = cvoType.getProperties();
        this.propReaders = propReaders;
        this.propertyMap = new LinkedHashMap<String, CompoundTypeProperty<V, ?>>();
        for (final CompoundTypeProperty<V, ?> cp : this.properties) {
            this.propertyMap.put(cp.getName(), cp);
        }
    }
    
    public String toString() {
        return this.cvoClass.toString();
    }
    
    public Class<V> getCompoundTypeClass() {
        return this.cvoClass;
    }
    
    public V create(final Object[] propertyValues) {
        return this.cvoType.create(propertyValues);
    }
    
    public V create(final Map<String, Object> valueMap) {
        if (valueMap.size() != this.properties.length) {
            return null;
        }
        final Object[] propertyValues = new Object[this.properties.length];
        for (int i = 0; i < this.properties.length; ++i) {
            propertyValues[i] = valueMap.get(this.properties[i].getName());
            if (propertyValues[i] == null) {
                final String m = "Null value for " + this.properties[i].getName() + " in map " + valueMap;
                throw new RuntimeException(m);
            }
        }
        return this.create(propertyValues);
    }
    
    public CompoundTypeProperty<V, ?>[] getProperties() {
        return this.cvoType.getProperties();
    }
    
    public Object[] getPropertyValues(final V valueObject) {
        final Object[] values = new Object[this.properties.length];
        for (int i = 0; i < this.properties.length; ++i) {
            values[i] = this.properties[i].getValue(valueObject);
        }
        return values;
    }
    
    public V read(final DataReader source) throws SQLException {
        boolean nullValue = false;
        final Object[] values = new Object[this.propReaders.length];
        for (int i = 0; i < this.propReaders.length; ++i) {
            final Object o = this.propReaders[i].read(source);
            if ((values[i] = o) == null) {
                nullValue = true;
            }
        }
        if (nullValue) {
            return null;
        }
        return this.create(values);
    }
    
    public void loadIgnore(final DataReader dataReader) {
        for (int i = 0; i < this.propReaders.length; ++i) {
            this.propReaders[i].loadIgnore(dataReader);
        }
    }
    
    public void bind(final DataBind b, final V value) throws SQLException {
        final CompoundTypeProperty<V, ?>[] props = this.cvoType.getProperties();
        for (int i = 0; i < props.length; ++i) {
            final Object o = props[i].getValue(value);
            this.propReaders[i].bind(b, o);
        }
    }
    
    public void accumulateScalarTypes(final String parent, final CtCompoundTypeScalarList list) {
        final CompoundTypeProperty<V, ?>[] props = this.cvoType.getProperties();
        for (int i = 0; i < this.propReaders.length; ++i) {
            final String propName = this.getFullPropName(parent, props[i].getName());
            list.addCompoundProperty(propName, this, props[i]);
            this.propReaders[i].accumulateScalarTypes(propName, list);
        }
    }
    
    private String getFullPropName(final String parent, final String propName) {
        if (parent == null) {
            return propName;
        }
        return parent + "." + propName;
    }
    
    public Object jsonRead(final ReadJsonContext ctx) {
        if (!ctx.readObjectBegin()) {
            return null;
        }
        final JsonElementObject jsonObject = new JsonElementObject();
        while (ctx.readKeyNext()) {
            final String propName = ctx.getTokenKey();
            final JsonElement unmappedJson = ctx.readUnmappedJson(propName);
            jsonObject.put(propName, unmappedJson);
            if (!ctx.readValueNext()) {
                return this.readJsonElementObject(ctx, jsonObject);
            }
        }
        return this.readJsonElementObject(ctx, jsonObject);
    }
    
    private Object readJsonElementObject(final ReadJsonContext ctx, final JsonElementObject jsonObject) {
        boolean nullValue = false;
        final Object[] values = new Object[this.propReaders.length];
        for (int i = 0; i < this.propReaders.length; ++i) {
            final String propName = this.properties[i].getName();
            final JsonElement jsonElement = jsonObject.get(propName);
            if (this.propReaders[i] instanceof CtCompoundType) {
                values[i] = ((CtCompoundType)this.propReaders[i]).readJsonElementObject(ctx, (JsonElementObject)jsonElement);
            }
            else {
                values[i] = ((ScalarType)this.propReaders[i]).jsonFromString(jsonElement.toPrimitiveString(), ctx.getValueAdapter());
            }
            if (values[i] == null) {
                nullValue = true;
            }
        }
        if (nullValue) {
            return null;
        }
        return this.create(values);
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object valueObject, final String propertyName) {
        if (valueObject == null) {
            ctx.beginAssocOneIsNull(propertyName);
        }
        else {
            ctx.pushParentBean(valueObject);
            ctx.beginAssocOne(propertyName);
            this.jsonWriteProps(ctx, valueObject, propertyName);
            ctx.endAssocOne();
            ctx.popParentBean();
        }
    }
    
    private void jsonWriteProps(final WriteJsonContext ctx, final Object valueObject, final String propertyName) {
        ctx.appendObjectBegin();
        final WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(valueObject);
        for (int i = 0; i < this.properties.length; ++i) {
            final String propName = this.properties[i].getName();
            final Object value = this.properties[i].getValue((V)valueObject);
            if (this.propReaders[i] instanceof CtCompoundType) {
                ((CtCompoundType)this.propReaders[i]).jsonWrite(ctx, value, propName);
            }
            else {
                final String js = ((ScalarType)this.propReaders[i]).jsonToString(value, ctx.getValueAdapter());
                ctx.appendKeyValue(propName, js);
            }
        }
        ctx.pushPreviousState(prevState);
        ctx.appendObjectEnd();
    }
}
