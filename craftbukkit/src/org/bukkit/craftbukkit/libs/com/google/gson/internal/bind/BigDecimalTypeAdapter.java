// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.com.google.gson.internal.bind;

import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonToken;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonReader;
import java.math.BigDecimal;
import org.bukkit.craftbukkit.libs.com.google.gson.TypeAdapter;

public final class BigDecimalTypeAdapter extends TypeAdapter<BigDecimal>
{
    public BigDecimal read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            return new BigDecimal(in.nextString());
        }
        catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
    
    public void write(final JsonWriter out, final BigDecimal value) throws IOException {
        out.value(value);
    }
}
