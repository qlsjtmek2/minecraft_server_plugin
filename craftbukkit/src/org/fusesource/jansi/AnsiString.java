// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

import java.io.IOException;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class AnsiString implements CharSequence
{
    private final CharSequence encoded;
    private final CharSequence plain;
    
    public AnsiString(final CharSequence str) {
        assert str != null;
        this.encoded = str;
        this.plain = this.chew(str);
    }
    
    private CharSequence chew(final CharSequence str) {
        assert str != null;
        final ByteArrayOutputStream buff = new ByteArrayOutputStream();
        final AnsiOutputStream out = new AnsiOutputStream(buff);
        try {
            out.write(str.toString().getBytes());
            out.flush();
            out.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new String(buff.toByteArray());
    }
    
    public CharSequence getEncoded() {
        return this.encoded;
    }
    
    public CharSequence getPlain() {
        return this.plain;
    }
    
    public char charAt(final int index) {
        return this.getEncoded().charAt(index);
    }
    
    public CharSequence subSequence(final int start, final int end) {
        return this.getEncoded().subSequence(start, end);
    }
    
    public int length() {
        return this.getPlain().length();
    }
    
    public boolean equals(final Object obj) {
        return this.getEncoded().equals(obj);
    }
    
    public int hashCode() {
        return this.getEncoded().hashCode();
    }
    
    public String toString() {
        return this.getEncoded().toString();
    }
}
