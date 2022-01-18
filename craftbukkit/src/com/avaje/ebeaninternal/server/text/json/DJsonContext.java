// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import java.util.Iterator;
import java.util.Collection;
import com.avaje.ebean.text.json.JsonWriteOptions;
import java.io.Writer;
import com.avaje.ebeaninternal.util.ParamTypeHelper;
import com.avaje.ebean.text.TextException;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.text.json.JsonReadOptions;
import java.io.Reader;
import java.lang.reflect.Type;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.text.json.JsonContext;

public class DJsonContext implements JsonContext
{
    private final SpiEbeanServer server;
    private final JsonValueAdapter dfltValueAdapter;
    private final boolean dfltPretty;
    
    public DJsonContext(final SpiEbeanServer server, final JsonValueAdapter dfltValueAdapter, final boolean dfltPretty) {
        this.server = server;
        this.dfltValueAdapter = dfltValueAdapter;
        this.dfltPretty = dfltPretty;
    }
    
    public boolean isSupportedType(final Type genericType) {
        return this.server.isSupportedType(genericType);
    }
    
    private ReadJsonSource createReader(final Reader jsonReader) {
        return new ReadJsonSourceReader(jsonReader, 256, 512);
    }
    
    public <T> T toBean(final Class<T> cls, final String json) {
        return this.toBean(cls, new ReadJsonSourceString(json), null);
    }
    
    public <T> T toBean(final Class<T> cls, final Reader jsonReader) {
        return this.toBean(cls, this.createReader(jsonReader), null);
    }
    
    public <T> T toBean(final Class<T> cls, final String json, final JsonReadOptions options) {
        return this.toBean(cls, new ReadJsonSourceString(json), options);
    }
    
    public <T> T toBean(final Class<T> cls, final Reader jsonReader, final JsonReadOptions options) {
        return this.toBean(cls, this.createReader(jsonReader), options);
    }
    
    private <T> T toBean(final Class<T> cls, final ReadJsonSource src, final JsonReadOptions options) {
        final BeanDescriptor<T> d = this.getDecriptor(cls);
        final ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
        return d.jsonReadBean(ctx, null);
    }
    
    public <T> List<T> toList(final Class<T> cls, final String json) {
        return this.toList(cls, new ReadJsonSourceString(json), null);
    }
    
    public <T> List<T> toList(final Class<T> cls, final String json, final JsonReadOptions options) {
        return this.toList(cls, new ReadJsonSourceString(json), options);
    }
    
    public <T> List<T> toList(final Class<T> cls, final Reader jsonReader) {
        return this.toList(cls, this.createReader(jsonReader), null);
    }
    
    public <T> List<T> toList(final Class<T> cls, final Reader jsonReader, final JsonReadOptions options) {
        return this.toList(cls, this.createReader(jsonReader), options);
    }
    
    private <T> List<T> toList(final Class<T> cls, final ReadJsonSource src, final JsonReadOptions options) {
        try {
            final BeanDescriptor<T> d = this.getDecriptor(cls);
            final List<T> list = new ArrayList<T>();
            final ReadJsonContext ctx = new ReadJsonContext(src, this.dfltValueAdapter, options);
            ctx.readArrayBegin();
            do {
                final T bean = d.jsonReadBean(ctx, null);
                if (bean != null) {
                    list.add(bean);
                }
            } while (ctx.readArrayNext());
            return list;
        }
        catch (RuntimeException e) {
            throw new TextException("Error parsing " + src, e);
        }
    }
    
    public Object toObject(final Type genericType, final String json, final JsonReadOptions options) {
        final ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
        final ParamTypeHelper.ManyType manyType = info.getManyType();
        switch (manyType) {
            case NONE: {
                return this.toBean(info.getBeanType(), json, options);
            }
            case LIST: {
                return this.toList(info.getBeanType(), json, options);
            }
            default: {
                final String msg = "ManyType " + manyType + " not supported yet";
                throw new TextException(msg);
            }
        }
    }
    
    public Object toObject(final Type genericType, final Reader json, final JsonReadOptions options) {
        final ParamTypeHelper.TypeInfo info = ParamTypeHelper.getTypeInfo(genericType);
        final ParamTypeHelper.ManyType manyType = info.getManyType();
        switch (manyType) {
            case NONE: {
                return this.toBean(info.getBeanType(), json, options);
            }
            case LIST: {
                return this.toList(info.getBeanType(), json, options);
            }
            default: {
                final String msg = "ManyType " + manyType + " not supported yet";
                throw new TextException(msg);
            }
        }
    }
    
    public void toJsonWriter(final Object o, final Writer writer) {
        this.toJsonWriter(o, writer, this.dfltPretty, null, null);
    }
    
    public void toJsonWriter(final Object o, final Writer writer, final boolean pretty) {
        this.toJsonWriter(o, writer, pretty, null, null);
    }
    
    public void toJsonWriter(final Object o, final Writer writer, final boolean pretty, final JsonWriteOptions options) {
        this.toJsonWriter(o, writer, pretty, null, null);
    }
    
    public void toJsonWriter(final Object o, final Writer writer, final boolean pretty, final JsonWriteOptions options, final String callback) {
        this.toJsonInternal(o, new WriteJsonBufferWriter(writer), pretty, options, callback);
    }
    
    public String toJsonString(final Object o) {
        return this.toJsonString(o, this.dfltPretty, null);
    }
    
    public String toJsonString(final Object o, final boolean pretty) {
        return this.toJsonString(o, pretty, null);
    }
    
    public String toJsonString(final Object o, final boolean pretty, final JsonWriteOptions options) {
        return this.toJsonString(o, pretty, options, null);
    }
    
    public String toJsonString(final Object o, final boolean pretty, final JsonWriteOptions options, final String callback) {
        final WriteJsonBufferString b = new WriteJsonBufferString();
        this.toJsonInternal(o, b, pretty, options, callback);
        return b.getBufferOutput();
    }
    
    private void toJsonInternal(final Object o, final WriteJsonBuffer buffer, final boolean pretty, final JsonWriteOptions options, final String requestCallback) {
        if (o instanceof Collection) {
            this.toJsonFromCollection((Collection<Object>)o, buffer, pretty, options, requestCallback);
        }
        else {
            final BeanDescriptor<?> d = this.getDecriptor(o.getClass());
            final WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
            d.jsonWrite(ctx, o);
            ctx.end();
        }
    }
    
    private <T> String toJsonFromCollection(final Collection<T> c, final WriteJsonBuffer buffer, final boolean pretty, final JsonWriteOptions options, final String requestCallback) {
        final Iterator<T> it = c.iterator();
        if (!it.hasNext()) {
            return null;
        }
        final WriteJsonContext ctx = new WriteJsonContext(buffer, pretty, this.dfltValueAdapter, options, requestCallback);
        final Object o = it.next();
        final BeanDescriptor<?> d = this.getDecriptor(o.getClass());
        ctx.appendArrayBegin();
        d.jsonWrite(ctx, o);
        while (it.hasNext()) {
            ctx.appendComma();
            final T t = it.next();
            d.jsonWrite(ctx, t);
        }
        ctx.appendArrayEnd();
        ctx.end();
        return ctx.getJson();
    }
    
    private <T> BeanDescriptor<T> getDecriptor(final Class<T> cls) {
        final BeanDescriptor<T> d = this.server.getBeanDescriptor(cls);
        if (d == null) {
            final String msg = "No BeanDescriptor found for " + cls;
            throw new RuntimeException(msg);
        }
        return d;
    }
}
