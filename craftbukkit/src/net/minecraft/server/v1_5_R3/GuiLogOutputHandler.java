// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.logging.LogRecord;
import javax.swing.JTextArea;
import java.util.logging.Formatter;
import java.util.logging.Handler;

public class GuiLogOutputHandler extends Handler
{
    private int[] b;
    private int c;
    Formatter a;
    private JTextArea d;
    
    public GuiLogOutputHandler(final JTextArea d) {
        this.b = new int[1024];
        this.c = 0;
        this.setFormatter(this.a = new GuiLogFormatter(this));
        this.d = d;
    }
    
    public void close() {
    }
    
    public void flush() {
    }
    
    public void publish(final LogRecord logRecord) {
        final int length = this.d.getDocument().getLength();
        this.d.append(this.a.format(logRecord));
        this.d.setCaretPosition(this.d.getDocument().getLength());
        final int n = this.d.getDocument().getLength() - length;
        if (this.b[this.c] != 0) {
            this.d.replaceRange("", 0, this.b[this.c]);
        }
        this.b[this.c] = n;
        this.c = (this.c + 1) % 1024;
    }
}
