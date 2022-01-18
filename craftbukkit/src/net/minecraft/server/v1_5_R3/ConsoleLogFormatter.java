// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.LogRecord;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;

class ConsoleLogFormatter extends Formatter
{
    private SimpleDateFormat b;
    final ConsoleLogManager a;
    private Pattern pattern;
    private boolean strip;
    
    private ConsoleLogFormatter(final ConsoleLogManager consolelogmanager) {
        this.pattern = Pattern.compile("\\x1B\\[([0-9]{1,2}(;[0-9]{1,2})*)?[m|K]");
        this.strip = false;
        this.a = consolelogmanager;
        this.b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.strip = MinecraftServer.getServer().options.has("log-strip-color");
    }
    
    public String format(final LogRecord logrecord) {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(this.b.format(logrecord.getMillis()));
        if (ConsoleLogManager.a(this.a) != null) {
            stringbuilder.append(ConsoleLogManager.a(this.a));
        }
        stringbuilder.append(" [").append(logrecord.getLevel().getName()).append("] ");
        stringbuilder.append(this.formatMessage(logrecord));
        stringbuilder.append('\n');
        final Throwable throwable = logrecord.getThrown();
        if (throwable != null) {
            final StringWriter stringwriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }
        if (this.strip) {
            return this.pattern.matcher(stringbuilder.toString()).replaceAll("");
        }
        return stringbuilder.toString();
    }
    
    ConsoleLogFormatter(final ConsoleLogManager consolelogmanager, final EmptyClass3 emptyclass3) {
        this(consolelogmanager);
    }
}
