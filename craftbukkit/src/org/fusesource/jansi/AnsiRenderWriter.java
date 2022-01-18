// 
// Decompiled by Procyon v0.5.30
// 

package org.fusesource.jansi;

import java.util.Locale;
import java.io.Writer;
import java.io.OutputStream;
import java.io.PrintWriter;

public class AnsiRenderWriter extends PrintWriter
{
    public AnsiRenderWriter(final OutputStream out) {
        super(out);
    }
    
    public AnsiRenderWriter(final OutputStream out, final boolean autoFlush) {
        super(out, autoFlush);
    }
    
    public AnsiRenderWriter(final Writer out) {
        super(out);
    }
    
    public AnsiRenderWriter(final Writer out, final boolean autoFlush) {
        super(out, autoFlush);
    }
    
    public void write(final String s) {
        if (AnsiRenderer.test(s)) {
            super.write(AnsiRenderer.render(s));
        }
        else {
            super.write(s);
        }
    }
    
    public PrintWriter format(final String format, final Object... args) {
        this.print(String.format(format, args));
        return this;
    }
    
    public PrintWriter format(final Locale l, final String format, final Object... args) {
        this.print(String.format(l, format, args));
        return this;
    }
}
