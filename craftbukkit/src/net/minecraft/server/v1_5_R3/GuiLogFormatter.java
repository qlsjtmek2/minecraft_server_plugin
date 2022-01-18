// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;
import java.util.logging.Formatter;

class GuiLogFormatter extends Formatter
{
    final /* synthetic */ GuiLogOutputHandler a;
    
    GuiLogFormatter(final GuiLogOutputHandler a) {
        this.a = a;
    }
    
    public String format(final LogRecord logRecord) {
        final StringBuilder sb = new StringBuilder();
        sb.append(" [").append(logRecord.getLevel().getName()).append("] ");
        sb.append(this.formatMessage(logRecord));
        sb.append('\n');
        final Throwable thrown = logRecord.getThrown();
        if (thrown != null) {
            final StringWriter stringWriter = new StringWriter();
            thrown.printStackTrace(new PrintWriter(stringWriter));
            sb.append(stringWriter.toString());
        }
        return sb.toString();
    }
}
