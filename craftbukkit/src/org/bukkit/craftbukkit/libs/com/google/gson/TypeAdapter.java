// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson;

import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeReader;
import java.io.StringReader;
import java.io.Reader;
import org.bukkit.craftbukkit.libs.com.google.gson.internal.bind.JsonTreeWriter;
import java.io.StringWriter;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import java.io.Writer;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;

public abstract class TypeAdapter<T>
{
    public abstract void write(final JsonWriter p0, final T p1) throws IOException;
    
    final void toJson(final Writer out, final T value) throws IOException {
        final JsonWriter writer = new JsonWriter(out);
        this.write(writer, value);
    }
    
    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>() {
            public void write(final JsonWriter out, final T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                }
                else {
                    TypeAdapter.this.write(out, value);
                }
            }
            
            public T read(final JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                }
                return TypeAdapter.this.read(reader);
            }
        };
    }
    
    final String toJson(final T value) throws IOException {
        final StringWriter stringWriter = new StringWriter();
        this.toJson(stringWriter, value);
        return stringWriter.toString();
    }
    
    final JsonElement toJsonTree(final T value) {
        try {
            final JsonTreeWriter jsonWriter = new JsonTreeWriter();
            jsonWriter.setLenient(true);
            this.write(jsonWriter, value);
            return jsonWriter.get();
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
    
    public abstract T read(final JsonReader p0) throws IOException;
    
    final T fromJson(final Reader in) throws IOException {
        final JsonReader reader = new JsonReader(in);
        reader.setLenient(true);
        return this.read(reader);
    }
    
    final T fromJson(final String json) throws IOException {
        return this.fromJson(new StringReader(json));
    }
    
    final T fromJsonTree(final JsonElement jsonTree) {
        try {
            final JsonReader jsonReader = new JsonTreeReader(jsonTree);
            jsonReader.setLenient(true);
            return this.read(jsonReader);
        }
        catch (IOException e) {
            throw new JsonIOException(e);
        }
    }
}
