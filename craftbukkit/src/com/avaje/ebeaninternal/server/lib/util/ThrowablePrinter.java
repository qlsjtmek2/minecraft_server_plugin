// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.util;

public class ThrowablePrinter
{
    private static final String atString = "        at ";
    private String newLineChar;
    private int maxStackTraceLines;
    
    public ThrowablePrinter() {
        this.newLineChar = "\\r\\n";
        this.maxStackTraceLines = 3;
    }
    
    public void setMaxStackTraceLines(final int maxStackTraceLines) {
        this.maxStackTraceLines = maxStackTraceLines;
    }
    
    public void setNewLineChar(final String newLineChar) {
        this.newLineChar = newLineChar;
    }
    
    public String print(final Throwable e) {
        final StringBuffer sb = new StringBuffer();
        this.printThrowable(sb, e, false);
        String line = sb.toString();
        line = StringHelper.replaceString(line, "\r", "\\r");
        line = StringHelper.replaceString(line, "\n", "\\n");
        return line;
    }
    
    protected void printThrowable(final StringBuffer sb, final Throwable e, final boolean isCause) {
        if (e != null) {
            if (isCause) {
                sb.append("Caused by: ");
            }
            sb.append(e.getClass().getName());
            sb.append(":");
            sb.append(e.getMessage()).append(this.newLineChar);
            final StackTraceElement[] ste = e.getStackTrace();
            int outputStackLines = ste.length;
            int notShownCount = 0;
            if (ste.length > this.maxStackTraceLines) {
                outputStackLines = this.maxStackTraceLines;
                notShownCount = ste.length - outputStackLines;
            }
            for (int i = 0; i < outputStackLines; ++i) {
                sb.append("        at ");
                sb.append(ste[i].toString()).append(this.newLineChar);
            }
            if (notShownCount > 0) {
                sb.append("        ... ");
                sb.append(notShownCount);
                sb.append(" more").append(this.newLineChar);
            }
            final Throwable cause = e.getCause();
            if (cause != null) {
                this.printThrowable(sb, cause, true);
            }
        }
    }
}
