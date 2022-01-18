// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.IO;

import java.util.Iterator;
import java.io.OutputStream;
import java.io.FileWriter;
import com.bpo.service.SingleClientHandler;
import java.util.ArrayList;
import java.io.PrintStream;

public class BPoPrintStream extends PrintStream
{
    private ArrayList<SingleClientHandler> list;
    private FileWriter log;
    
    public BPoPrintStream(final OutputStream out) {
        super(out);
    }
    
    public OutputStream getOutputStream() {
        return this.out;
    }
    
    public void setList(final ArrayList<SingleClientHandler> list) {
        this.list = list;
    }
    
    public void setLog(final FileWriter out) {
        this.log = out;
    }
    
    @Override
    public void println(final String str) {
        try {
            for (final SingleClientHandler select : this.list) {
                select.sendLog(String.valueOf(str) + "\r\n");
            }
        }
        catch (Exception ex) {}
        try {
            this.log.append((CharSequence)(String.valueOf(str) + "\r\n"));
            this.log.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        super.println(str);
    }
}
