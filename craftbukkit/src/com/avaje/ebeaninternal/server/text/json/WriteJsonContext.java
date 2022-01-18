// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.EntityBeanIntercept;
import java.util.Set;
import com.avaje.ebean.text.json.JsonWriteOptions;
import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
import java.util.Map;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.util.ArrayStack;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.json.JsonWriter;

public class WriteJsonContext implements JsonWriter
{
    private final WriteJsonBuffer buffer;
    private final boolean pretty;
    private final JsonValueAdapter valueAdapter;
    private final ArrayStack<Object> parentBeans;
    private final PathProperties pathProperties;
    private final Map<String, JsonWriteBeanVisitor<?>> visitorMap;
    private final String callback;
    private final PathStack pathStack;
    private WriteBeanState beanState;
    private int depthOffset;
    boolean assocOne;
    
    public WriteJsonContext(final WriteJsonBuffer buffer, final boolean pretty, final JsonValueAdapter dfltValueAdapter, final JsonWriteOptions options, final String requestCallback) {
        this.parentBeans = new ArrayStack<Object>();
        this.buffer = buffer;
        this.pretty = pretty;
        this.pathStack = new PathStack();
        this.callback = this.getCallback(requestCallback, options);
        if (options == null) {
            this.valueAdapter = dfltValueAdapter;
            this.visitorMap = null;
            this.pathProperties = null;
        }
        else {
            this.valueAdapter = this.getValueAdapter(dfltValueAdapter, options.getValueAdapter());
            this.visitorMap = this.emptyToNull(options.getVisitorMap());
            this.pathProperties = this.emptyToNull(options.getPathProperties());
        }
        if (this.callback != null) {
            buffer.append(requestCallback).append("(");
        }
    }
    
    public void end() {
        if (this.callback != null) {
            this.buffer.append(")");
        }
    }
    
    private <MK, MV> Map<MK, MV> emptyToNull(final Map<MK, MV> m) {
        if (m == null || m.isEmpty()) {
            return null;
        }
        return m;
    }
    
    private PathProperties emptyToNull(final PathProperties m) {
        if (m == null || m.isEmpty()) {
            return null;
        }
        return m;
    }
    
    private String getCallback(final String requestCallback, final JsonWriteOptions options) {
        if (requestCallback != null) {
            return requestCallback;
        }
        if (options != null) {
            return options.getCallback();
        }
        return null;
    }
    
    private JsonValueAdapter getValueAdapter(final JsonValueAdapter dfltValueAdapter, final JsonValueAdapter valueAdapter) {
        return (valueAdapter == null) ? dfltValueAdapter : valueAdapter;
    }
    
    public Set<String> getIncludeProperties() {
        if (this.pathProperties != null) {
            final String path = this.pathStack.peekWithNull();
            return this.pathProperties.get(path);
        }
        return null;
    }
    
    public JsonWriteBeanVisitor<?> getBeanVisitor() {
        if (this.visitorMap != null) {
            final String path = this.pathStack.peekWithNull();
            return this.visitorMap.get(path);
        }
        return null;
    }
    
    public String getJson() {
        return this.buffer.toString();
    }
    
    private void appendIndent() {
        this.buffer.append("\n");
        for (int depth = this.depthOffset + this.parentBeans.size(), i = 0; i < depth; ++i) {
            this.buffer.append("    ");
        }
    }
    
    public void appendObjectBegin() {
        if (this.pretty && !this.assocOne) {
            this.appendIndent();
        }
        this.buffer.append("{");
    }
    
    public void appendObjectEnd() {
        this.buffer.append("}");
    }
    
    public void appendArrayBegin() {
        if (this.pretty) {
            this.appendIndent();
        }
        this.buffer.append("[");
        ++this.depthOffset;
    }
    
    public void appendArrayEnd() {
        --this.depthOffset;
        if (this.pretty) {
            this.appendIndent();
        }
        this.buffer.append("]");
    }
    
    public void appendComma() {
        this.buffer.append(",");
    }
    
    public void addDepthOffset(final int offset) {
        this.depthOffset += offset;
    }
    
    public void beginAssocOneIsNull(final String key) {
        ++this.depthOffset;
        this.internalAppendKeyBegin(key);
        this.appendNull();
        --this.depthOffset;
    }
    
    public void beginAssocOne(final String key) {
        this.pathStack.pushPathKey(key);
        this.internalAppendKeyBegin(key);
        this.assocOne = true;
    }
    
    public void endAssocOne() {
        this.pathStack.pop();
        this.assocOne = false;
    }
    
    public Boolean includeMany(final String key) {
        if (this.pathProperties != null) {
            final String fullPath = this.pathStack.peekFullPath(key);
            return this.pathProperties.hasPath(fullPath);
        }
        return null;
    }
    
    public void beginAssocMany(final String key) {
        this.pathStack.pushPathKey(key);
        --this.depthOffset;
        this.internalAppendKeyBegin(key);
        ++this.depthOffset;
        this.buffer.append("[");
    }
    
    public void endAssocMany() {
        this.pathStack.pop();
        if (this.pretty) {
            --this.depthOffset;
            this.appendIndent();
            ++this.depthOffset;
        }
        this.buffer.append("]");
    }
    
    private void internalAppendKeyBegin(final String key) {
        if (!this.beanState.isFirstKey()) {
            this.buffer.append(",");
        }
        if (this.pretty) {
            this.appendIndent();
        }
        this.appendKeyWithComma(key, false);
    }
    
    public void appendKey(final String key) {
        this.appendKeyWithComma(key, true);
    }
    
    private void appendKeyWithComma(final String key, final boolean withComma) {
        if (withComma && !this.beanState.isFirstKey()) {
            this.buffer.append(",");
        }
        this.buffer.append("\"");
        if (key == null) {
            this.buffer.append("null");
        }
        else {
            this.buffer.append(key);
        }
        this.buffer.append("\":");
    }
    
    public void appendKeyValue(final String key, final String escapedValue) {
        this.appendKey(key);
        this.buffer.append(escapedValue);
    }
    
    public void appendNull(final String key) {
        this.appendKey(key);
        this.buffer.append("null");
    }
    
    public void appendNull() {
        this.buffer.append("null");
    }
    
    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }
    
    public String toString() {
        return this.buffer.toString();
    }
    
    public void popParentBean() {
        this.parentBeans.pop();
    }
    
    public void pushParentBean(final Object parentBean) {
        this.parentBeans.push(parentBean);
    }
    
    public void popParentBeanMany() {
        this.parentBeans.pop();
        --this.depthOffset;
    }
    
    public void pushParentBeanMany(final Object parentBean) {
        this.parentBeans.push(parentBean);
        ++this.depthOffset;
    }
    
    public boolean isParentBean(final Object bean) {
        return !this.parentBeans.isEmpty() && bean == this.parentBeans.peek();
    }
    
    public WriteBeanState pushBeanState(final Object bean) {
        final WriteBeanState newState = new WriteBeanState(bean);
        final WriteBeanState prevState = this.beanState;
        this.beanState = newState;
        return prevState;
    }
    
    public void pushPreviousState(final WriteBeanState previousState) {
        this.beanState = previousState;
    }
    
    public boolean isReferenceBean() {
        return this.beanState.isReferenceBean();
    }
    
    public boolean includedProp(final String name) {
        return this.beanState.includedProp(name);
    }
    
    public Set<String> getLoadedProps() {
        return this.beanState.getLoadedProps();
    }
    
    public static class WriteBeanState
    {
        private final EntityBeanIntercept ebi;
        private final Set<String> loadedProps;
        private final boolean referenceBean;
        private boolean firstKeyOut;
        
        public WriteBeanState(final Object bean) {
            if (bean instanceof EntityBean) {
                this.ebi = ((EntityBean)bean)._ebean_getIntercept();
                this.loadedProps = this.ebi.getLoadedProps();
                this.referenceBean = this.ebi.isReference();
            }
            else {
                this.ebi = null;
                this.loadedProps = null;
                this.referenceBean = false;
            }
        }
        
        public Set<String> getLoadedProps() {
            return this.loadedProps;
        }
        
        public boolean includedProp(final String name) {
            return this.loadedProps == null || this.loadedProps.contains(name);
        }
        
        public boolean isReferenceBean() {
            return this.referenceBean;
        }
        
        public boolean isFirstKey() {
            return !this.firstKeyOut && (this.firstKeyOut = true);
        }
    }
}
