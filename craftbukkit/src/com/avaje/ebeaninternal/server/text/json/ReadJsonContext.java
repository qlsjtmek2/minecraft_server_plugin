// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import java.beans.PropertyChangeEvent;
import java.util.LinkedHashMap;
import java.util.HashSet;
import com.avaje.ebean.bean.EntityBean;
import java.util.Set;
import com.avaje.ebean.bean.EntityBeanIntercept;
import java.beans.PropertyChangeListener;
import com.avaje.ebean.text.json.JsonElement;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.text.TextException;
import com.avaje.ebean.text.json.JsonReadOptions;
import com.avaje.ebeaninternal.server.util.ArrayStack;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.json.JsonReadBeanVisitor;
import java.util.Map;

public class ReadJsonContext
{
    private final ReadJsonSource src;
    private final Map<String, JsonReadBeanVisitor<?>> visitorMap;
    private final JsonValueAdapter valueAdapter;
    private final PathStack pathStack;
    private final ArrayStack<ReadBeanState> beanState;
    private ReadBeanState currentState;
    private char tokenStart;
    private String tokenKey;
    
    public ReadJsonContext(final ReadJsonSource src, final JsonValueAdapter dfltValueAdapter, final JsonReadOptions options) {
        this.src = src;
        this.beanState = new ArrayStack<ReadBeanState>();
        if (options == null) {
            this.valueAdapter = dfltValueAdapter;
            this.visitorMap = null;
            this.pathStack = null;
        }
        else {
            this.valueAdapter = this.getValueAdapter(dfltValueAdapter, options.getValueAdapter());
            this.visitorMap = options.getVisitorMap();
            this.pathStack = ((this.visitorMap == null || this.visitorMap.isEmpty()) ? null : new PathStack());
        }
    }
    
    private JsonValueAdapter getValueAdapter(final JsonValueAdapter dfltValueAdapter, final JsonValueAdapter valueAdapter) {
        return (valueAdapter == null) ? dfltValueAdapter : valueAdapter;
    }
    
    public JsonValueAdapter getValueAdapter() {
        return this.valueAdapter;
    }
    
    public char getToken() {
        return this.tokenStart;
    }
    
    public String getTokenKey() {
        return this.tokenKey;
    }
    
    public boolean isTokenKey() {
        return '\"' == this.tokenStart;
    }
    
    public boolean isTokenObjectEnd() {
        return '}' == this.tokenStart;
    }
    
    public boolean readObjectBegin() {
        this.readNextToken();
        if ('{' == this.tokenStart) {
            return true;
        }
        if ('n' == this.tokenStart) {
            return false;
        }
        if (']' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected object begin at " + this.src.getErrorHelp());
    }
    
    public boolean readKeyNext() {
        this.readNextToken();
        if ('\"' == this.tokenStart) {
            return true;
        }
        if ('}' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected '\"' or '}' at " + this.src.getErrorHelp());
    }
    
    public boolean readValueNext() {
        this.readNextToken();
        if (',' == this.tokenStart) {
            return true;
        }
        if ('}' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected ',' or '}' at " + this.src.getErrorHelp() + " but got " + this.tokenStart);
    }
    
    public boolean readArrayBegin() {
        this.readNextToken();
        if ('[' == this.tokenStart) {
            return true;
        }
        if ('n' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected array begin at " + this.src.getErrorHelp());
    }
    
    public boolean readArrayNext() {
        this.readNextToken();
        if (',' == this.tokenStart) {
            return true;
        }
        if (']' == this.tokenStart) {
            return false;
        }
        throw new RuntimeException("Expected ',' or ']' at " + this.src.getErrorHelp());
    }
    
    public String readScalarValue() {
        this.ignoreWhiteSpace();
        final char prevChar = this.src.nextChar("EOF reading scalarValue?");
        if ('\"' == prevChar) {
            return this.readQuotedValue();
        }
        return this.readUnquotedValue(prevChar);
    }
    
    public void readNextToken() {
        this.ignoreWhiteSpace();
        switch (this.tokenStart = this.src.nextChar("EOF finding next token")) {
            case '\"': {
                this.internalReadKey();
                break;
            }
            case '{': {
                break;
            }
            case '}': {
                break;
            }
            case '[': {
                break;
            }
            case ']': {
                break;
            }
            case ',': {
                break;
            }
            case ':': {
                break;
            }
            case 'n': {
                this.internalReadNull();
                break;
            }
            default: {
                throw new RuntimeException("Unexpected tokenStart[" + this.tokenStart + "] " + this.src.getErrorHelp());
            }
        }
    }
    
    protected String readQuotedValue() {
        boolean escape = false;
        final StringBuilder sb = new StringBuilder();
    Label_0069:
        while (true) {
            final char ch = this.src.nextChar("EOF reading quoted value");
            if (escape) {
                sb.append(ch);
            }
            else {
                switch (ch) {
                    case '\\': {
                        escape = true;
                        continue;
                    }
                    case '\"': {
                        break Label_0069;
                    }
                    default: {
                        sb.append(ch);
                        continue;
                    }
                }
            }
        }
        return sb.toString();
    }
    
    protected String readUnquotedValue(final char c) {
        final String v = this.readUnquotedValueRaw(c);
        if ("null".equals(v)) {
            return null;
        }
        return v;
    }
    
    private String readUnquotedValueRaw(final char c) {
        final StringBuilder sb = new StringBuilder();
        sb.append(c);
        while (true) {
            switch (this.tokenStart = this.src.nextChar("EOF reading unquoted value")) {
                case ',': {
                    this.src.back();
                    return sb.toString();
                }
                case '}': {
                    this.src.back();
                    return sb.toString();
                }
                case ' ': {
                    return sb.toString();
                }
                case '\t': {
                    return sb.toString();
                }
                case '\r': {
                    return sb.toString();
                }
                case '\n': {
                    return sb.toString();
                }
                default: {
                    sb.append(this.tokenStart);
                    continue;
                }
            }
        }
    }
    
    private void internalReadNull() {
        final StringBuilder sb = new StringBuilder(4);
        sb.append(this.tokenStart);
        for (int i = 0; i < 3; ++i) {
            final char c = this.src.nextChar("EOF reading null ");
            sb.append(c);
        }
        if (!"null".equals(sb.toString())) {
            throw new TextException("Expected 'null' but got " + sb.toString() + " " + this.src.getErrorHelp());
        }
    }
    
    private void internalReadKey() {
        final StringBuilder sb = new StringBuilder();
        while (true) {
            final char c = this.src.nextChar("EOF reading key");
            if ('\"' == c) {
                break;
            }
            sb.append(c);
        }
        this.tokenKey = sb.toString();
        this.ignoreWhiteSpace();
        final char c = this.src.nextChar("EOF reading ':'");
        if (':' != c) {
            throw new TextException("Expected to find colon after key at " + (this.src.pos() - 1) + " but found [" + c + "]" + this.src.getErrorHelp());
        }
    }
    
    protected void ignoreWhiteSpace() {
        this.src.ignoreWhiteSpace();
    }
    
    public void pushBean(final Object bean, final String path, final BeanDescriptor<?> beanDescriptor) {
        this.currentState = new ReadBeanState(bean, (BeanDescriptor)beanDescriptor);
        this.beanState.push(this.currentState);
        if (this.pathStack != null) {
            this.pathStack.pushPathKey(path);
        }
    }
    
    public ReadBeanState popBeanState() {
        if (this.pathStack != null) {
            final String path = this.pathStack.peekWithNull();
            final JsonReadBeanVisitor<?> beanVisitor = this.visitorMap.get(path);
            if (beanVisitor != null) {
                this.currentState.visit(beanVisitor);
            }
            this.pathStack.pop();
        }
        final ReadBeanState s = this.currentState;
        this.beanState.pop();
        this.currentState = this.beanState.peekWithNull();
        return s;
    }
    
    public void setProperty(final String propertyName) {
        this.currentState.setLoaded(propertyName);
    }
    
    public JsonElement readUnmappedJson(final String key) {
        final ReadJsonRawReader rawReader = new ReadJsonRawReader(this);
        final JsonElement rawJsonValue = rawReader.readUnknownValue();
        if (this.visitorMap != null) {
            this.currentState.addUnmappedJson(key, rawJsonValue);
        }
        return rawJsonValue;
    }
    
    protected char nextChar() {
        return this.tokenStart = this.src.nextChar("EOF getting nextChar for raw json");
    }
    
    public static class ReadBeanState implements PropertyChangeListener
    {
        private final Object bean;
        private final BeanDescriptor<?> beanDescriptor;
        private final EntityBeanIntercept ebi;
        private final Set<String> loadedProps;
        private Map<String, JsonElement> unmapped;
        
        private ReadBeanState(final Object bean, final BeanDescriptor<?> beanDescriptor) {
            this.bean = bean;
            this.beanDescriptor = beanDescriptor;
            if (bean instanceof EntityBean) {
                this.ebi = ((EntityBean)bean)._ebean_getIntercept();
                this.loadedProps = new HashSet<String>();
            }
            else {
                this.ebi = null;
                this.loadedProps = null;
            }
        }
        
        public String toString() {
            return this.bean.getClass().getSimpleName() + " loaded:" + this.loadedProps;
        }
        
        public void setLoaded(final String propertyName) {
            if (this.ebi != null) {
                this.loadedProps.add(propertyName);
            }
        }
        
        private void addUnmappedJson(final String key, final JsonElement value) {
            if (this.unmapped == null) {
                this.unmapped = new LinkedHashMap<String, JsonElement>();
            }
            this.unmapped.put(key, value);
        }
        
        private <T> void visit(final JsonReadBeanVisitor<T> beanVisitor) {
            if (this.ebi != null) {
                this.ebi.addPropertyChangeListener(this);
            }
            beanVisitor.visit((T)this.bean, this.unmapped);
            if (this.ebi != null) {
                this.ebi.removePropertyChangeListener(this);
            }
        }
        
        public void setLoadedState() {
            if (this.ebi != null) {
                this.beanDescriptor.setLoadedProps(this.ebi, this.loadedProps);
            }
        }
        
        public void propertyChange(final PropertyChangeEvent evt) {
            final String propName = evt.getPropertyName();
            this.loadedProps.add(propName);
        }
        
        public Object getBean() {
            return this.bean;
        }
    }
}
