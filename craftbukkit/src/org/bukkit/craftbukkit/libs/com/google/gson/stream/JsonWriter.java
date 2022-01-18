// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.Writer;
import java.io.Closeable;

public class JsonWriter implements Closeable
{
    private final Writer out;
    private final List<JsonScope> stack;
    private String indent;
    private String separator;
    private boolean lenient;
    private boolean htmlSafe;
    private String deferredName;
    private boolean serializeNulls;
    
    public JsonWriter(final Writer out) {
        (this.stack = new ArrayList<JsonScope>()).add(JsonScope.EMPTY_DOCUMENT);
        this.separator = ":";
        this.serializeNulls = true;
        if (out == null) {
            throw new NullPointerException("out == null");
        }
        this.out = out;
    }
    
    public final void setIndent(final String indent) {
        if (indent.length() == 0) {
            this.indent = null;
            this.separator = ":";
        }
        else {
            this.indent = indent;
            this.separator = ": ";
        }
    }
    
    public final void setLenient(final boolean lenient) {
        this.lenient = lenient;
    }
    
    public boolean isLenient() {
        return this.lenient;
    }
    
    public final void setHtmlSafe(final boolean htmlSafe) {
        this.htmlSafe = htmlSafe;
    }
    
    public final boolean isHtmlSafe() {
        return this.htmlSafe;
    }
    
    public final void setSerializeNulls(final boolean serializeNulls) {
        this.serializeNulls = serializeNulls;
    }
    
    public final boolean getSerializeNulls() {
        return this.serializeNulls;
    }
    
    public JsonWriter beginArray() throws IOException {
        this.writeDeferredName();
        return this.open(JsonScope.EMPTY_ARRAY, "[");
    }
    
    public JsonWriter endArray() throws IOException {
        return this.close(JsonScope.EMPTY_ARRAY, JsonScope.NONEMPTY_ARRAY, "]");
    }
    
    public JsonWriter beginObject() throws IOException {
        this.writeDeferredName();
        return this.open(JsonScope.EMPTY_OBJECT, "{");
    }
    
    public JsonWriter endObject() throws IOException {
        return this.close(JsonScope.EMPTY_OBJECT, JsonScope.NONEMPTY_OBJECT, "}");
    }
    
    private JsonWriter open(final JsonScope empty, final String openBracket) throws IOException {
        this.beforeValue(true);
        this.stack.add(empty);
        this.out.write(openBracket);
        return this;
    }
    
    private JsonWriter close(final JsonScope empty, final JsonScope nonempty, final String closeBracket) throws IOException {
        final JsonScope context = this.peek();
        if (context != nonempty && context != empty) {
            throw new IllegalStateException("Nesting problem: " + this.stack);
        }
        if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        }
        this.stack.remove(this.stack.size() - 1);
        if (context == nonempty) {
            this.newline();
        }
        this.out.write(closeBracket);
        return this;
    }
    
    private JsonScope peek() {
        return this.stack.get(this.stack.size() - 1);
    }
    
    private void replaceTop(final JsonScope topOfStack) {
        this.stack.set(this.stack.size() - 1, topOfStack);
    }
    
    public JsonWriter name(final String name) throws IOException {
        if (name == null) {
            throw new NullPointerException("name == null");
        }
        if (this.deferredName != null) {
            throw new IllegalStateException();
        }
        this.deferredName = name;
        return this;
    }
    
    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            this.beforeName();
            this.string(this.deferredName);
            this.deferredName = null;
        }
    }
    
    public JsonWriter value(final String value) throws IOException {
        if (value == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.string(value);
        return this;
    }
    
    public JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (!this.serializeNulls) {
                this.deferredName = null;
                return this;
            }
            this.writeDeferredName();
        }
        this.beforeValue(false);
        this.out.write("null");
        return this;
    }
    
    public JsonWriter value(final boolean value) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.write(value ? "true" : "false");
        return this;
    }
    
    public JsonWriter value(final double value) throws IOException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.append((CharSequence)Double.toString(value));
        return this;
    }
    
    public JsonWriter value(final long value) throws IOException {
        this.writeDeferredName();
        this.beforeValue(false);
        this.out.write(Long.toString(value));
        return this;
    }
    
    public JsonWriter value(final Number value) throws IOException {
        if (value == null) {
            return this.nullValue();
        }
        this.writeDeferredName();
        final String string = value.toString();
        if (!this.lenient && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + value);
        }
        this.beforeValue(false);
        this.out.append((CharSequence)string);
        return this;
    }
    
    public void flush() throws IOException {
        this.out.flush();
    }
    
    public void close() throws IOException {
        this.out.close();
        if (this.peek() != JsonScope.NONEMPTY_DOCUMENT) {
            throw new IOException("Incomplete document");
        }
    }
    
    private void string(final String value) throws IOException {
        this.out.write("\"");
        for (int i = 0, length = value.length(); i < length; ++i) {
            final char c = value.charAt(i);
            switch (c) {
                case '\"':
                case '\\': {
                    this.out.write(92);
                    this.out.write(c);
                    break;
                }
                case '\t': {
                    this.out.write("\\t");
                    break;
                }
                case '\b': {
                    this.out.write("\\b");
                    break;
                }
                case '\n': {
                    this.out.write("\\n");
                    break;
                }
                case '\r': {
                    this.out.write("\\r");
                    break;
                }
                case '\f': {
                    this.out.write("\\f");
                    break;
                }
                case '&':
                case '\'':
                case '<':
                case '=':
                case '>': {
                    if (this.htmlSafe) {
                        this.out.write(String.format("\\u%04x", (int)c));
                        break;
                    }
                    this.out.write(c);
                    break;
                }
                case '\u2028':
                case '\u2029': {
                    this.out.write(String.format("\\u%04x", (int)c));
                    break;
                }
                default: {
                    if (c <= '\u001f') {
                        this.out.write(String.format("\\u%04x", (int)c));
                        break;
                    }
                    this.out.write(c);
                    break;
                }
            }
        }
        this.out.write("\"");
    }
    
    private void newline() throws IOException {
        if (this.indent == null) {
            return;
        }
        this.out.write("\n");
        for (int i = 1; i < this.stack.size(); ++i) {
            this.out.write(this.indent);
        }
    }
    
    private void beforeName() throws IOException {
        final JsonScope context = this.peek();
        if (context == JsonScope.NONEMPTY_OBJECT) {
            this.out.write(44);
        }
        else if (context != JsonScope.EMPTY_OBJECT) {
            throw new IllegalStateException("Nesting problem: " + this.stack);
        }
        this.newline();
        this.replaceTop(JsonScope.DANGLING_NAME);
    }
    
    private void beforeValue(final boolean root) throws IOException {
        switch (this.peek()) {
            case EMPTY_DOCUMENT: {
                if (!this.lenient && !root) {
                    throw new IllegalStateException("JSON must start with an array or an object.");
                }
                this.replaceTop(JsonScope.NONEMPTY_DOCUMENT);
                break;
            }
            case EMPTY_ARRAY: {
                this.replaceTop(JsonScope.NONEMPTY_ARRAY);
                this.newline();
                break;
            }
            case NONEMPTY_ARRAY: {
                this.out.append(',');
                this.newline();
                break;
            }
            case DANGLING_NAME: {
                this.out.append((CharSequence)this.separator);
                this.replaceTop(JsonScope.NONEMPTY_OBJECT);
                break;
            }
            case NONEMPTY_DOCUMENT: {
                throw new IllegalStateException("JSON must have only one top-level value.");
            }
            default: {
                throw new IllegalStateException("Nesting problem: " + this.stack);
            }
        }
    }
}
