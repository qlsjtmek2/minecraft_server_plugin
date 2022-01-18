// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.text.json;

import com.avaje.ebean.text.json.JsonElementNumber;
import com.avaje.ebean.text.json.JsonElementBoolean;
import com.avaje.ebean.text.json.JsonElementNull;
import com.avaje.ebean.text.json.JsonElementString;
import com.avaje.ebean.text.json.JsonElementObject;
import com.avaje.ebean.text.json.JsonElementArray;
import com.avaje.ebean.text.json.JsonElement;

public class ReadJsonRawReader
{
    private final ReadJsonContext ctx;
    
    public ReadJsonRawReader(final ReadJsonContext ctx) {
        this.ctx = ctx;
    }
    
    public JsonElement readUnknownValue() {
        return this.readValue();
    }
    
    private JsonElement readValue() {
        this.ctx.ignoreWhiteSpace();
        final char c = this.ctx.nextChar();
        switch (c) {
            case '{': {
                return this.readObject();
            }
            case '[': {
                return this.readArray();
            }
            case '\"': {
                return this.readString();
            }
            default: {
                return this.readUnquoted(c);
            }
        }
    }
    
    private JsonElement readArray() {
        final JsonElementArray a = new JsonElementArray();
        do {
            final JsonElement value = this.readValue();
            a.add(value);
        } while (this.ctx.readArrayNext());
        return a;
    }
    
    private JsonElement readObject() {
        final JsonElementObject o = new JsonElementObject();
        while (this.ctx.readKeyNext()) {
            final String key = this.ctx.getTokenKey();
            final JsonElement value = this.readValue();
            o.put(key, value);
            if (!this.ctx.readValueNext()) {
                return o;
            }
        }
        return o;
    }
    
    private JsonElement readString() {
        final String s = this.ctx.readQuotedValue();
        return new JsonElementString(s);
    }
    
    private JsonElement readUnquoted(final char c) {
        final String s = this.ctx.readUnquotedValue(c);
        if ("null".equals(s)) {
            return JsonElementNull.NULL;
        }
        if ("true".equals(s)) {
            return JsonElementBoolean.TRUE;
        }
        if ("false".equals(s)) {
            return JsonElementBoolean.FALSE;
        }
        return new JsonElementNumber(s);
    }
}
