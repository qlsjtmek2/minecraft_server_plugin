// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
import java.io.File;

public class BanList
{
    private final InsensitiveStringMap a;
    private final File b;
    private boolean c;
    
    public BanList(final File b) {
        this.a = new InsensitiveStringMap();
        this.c = true;
        this.b = b;
    }
    
    public boolean isEnabled() {
        return this.c;
    }
    
    public void setEnabled(final boolean c) {
        this.c = c;
    }
    
    public Map getEntries() {
        this.removeExpired();
        return this.a;
    }
    
    public boolean isBanned(final String s) {
        if (!this.isEnabled()) {
            return false;
        }
        this.removeExpired();
        return this.a.containsKey(s);
    }
    
    public void add(final BanEntry banEntry) {
        this.a.put(banEntry.getName(), banEntry);
        this.save();
    }
    
    public void remove(final String s) {
        this.a.remove(s);
        this.save();
    }
    
    public void removeExpired() {
        final Iterator<BanEntry> iterator = (Iterator<BanEntry>)this.a.values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().hasExpired()) {
                iterator.remove();
            }
        }
    }
    
    public void load() {
        if (!this.b.isFile()) {
            return;
        }
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(this.b));
        }
        catch (FileNotFoundException ex2) {
            throw new Error();
        }
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("#")) {
                    final BanEntry c = BanEntry.c(line);
                    if (c == null) {
                        continue;
                    }
                    this.a.put(c.getName(), c);
                }
            }
        }
        catch (IOException ex) {
            MinecraftServer.getServer().getLogger().severe("Could not load ban list", ex);
        }
    }
    
    public void save() {
        this.save(true);
    }
    
    public void save(final boolean b) {
        this.removeExpired();
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.b, false));
            if (b) {
                printWriter.println("# Updated " + new SimpleDateFormat().format(new Date()) + " by Minecraft " + "1.5.2");
                printWriter.println("# victim name | ban date | banned by | banned until | reason");
                printWriter.println();
            }
            final Iterator<BanEntry> iterator = this.a.values().iterator();
            while (iterator.hasNext()) {
                printWriter.println(iterator.next().g());
            }
            printWriter.close();
        }
        catch (IOException ex) {
            MinecraftServer.getServer().getLogger().severe("Could not save ban list", ex);
        }
    }
}
