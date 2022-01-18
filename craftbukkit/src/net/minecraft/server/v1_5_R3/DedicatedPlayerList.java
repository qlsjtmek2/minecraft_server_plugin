// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;

public class DedicatedPlayerList extends PlayerList
{
    private File d;
    private File e;
    
    public DedicatedPlayerList(final DedicatedServer minecraftserver) {
        super(minecraftserver);
        this.d = minecraftserver.e("ops.txt");
        this.e = minecraftserver.e("white-list.txt");
        this.c = minecraftserver.a("view-distance", 10);
        this.maxPlayers = minecraftserver.a("max-players", 20);
        this.setHasWhitelist(minecraftserver.a("white-list", false));
        if (!minecraftserver.I()) {
            this.getNameBans().setEnabled(true);
            this.getIPBans().setEnabled(true);
        }
        this.getNameBans().load();
        this.getNameBans().save();
        this.getIPBans().load();
        this.getIPBans().save();
        this.t();
        this.v();
        this.u();
        if (!this.e.exists()) {
            this.w();
        }
    }
    
    public void setHasWhitelist(final boolean hasWhitelist) {
        super.setHasWhitelist(hasWhitelist);
        this.getServer().a("white-list", (Object)hasWhitelist);
        this.getServer().a();
    }
    
    public void addOp(final String s) {
        super.addOp(s);
        this.u();
    }
    
    public void removeOp(final String s) {
        super.removeOp(s);
        this.u();
    }
    
    public void removeWhitelist(final String s) {
        super.removeWhitelist(s);
        this.w();
    }
    
    public void addWhitelist(final String s) {
        super.addWhitelist(s);
        this.w();
    }
    
    public void reloadWhitelist() {
        this.v();
    }
    
    private void t() {
        try {
            this.getOPs().clear();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.d));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.getOPs().add(line.trim().toLowerCase());
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            this.getServer().getLogger().warning("Failed to load operators list: " + ex);
        }
    }
    
    private void u() {
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.d, false));
            final Iterator<String> iterator = this.getOPs().iterator();
            while (iterator.hasNext()) {
                printWriter.println(iterator.next());
            }
            printWriter.close();
        }
        catch (Exception ex) {
            this.getServer().getLogger().warning("Failed to save operators list: " + ex);
        }
    }
    
    private void v() {
        try {
            this.getWhitelisted().clear();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(this.e));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                this.getWhitelisted().add(line.trim().toLowerCase());
            }
            bufferedReader.close();
        }
        catch (Exception ex) {
            this.getServer().getLogger().warning("Failed to load white-list: " + ex);
        }
    }
    
    private void w() {
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.e, false));
            final Iterator<String> iterator = this.getWhitelisted().iterator();
            while (iterator.hasNext()) {
                printWriter.println(iterator.next());
            }
            printWriter.close();
        }
        catch (Exception ex) {
            this.getServer().getLogger().warning("Failed to save white-list: " + ex);
        }
    }
    
    public boolean isWhitelisted(String lowerCase) {
        lowerCase = lowerCase.trim().toLowerCase();
        return !this.getHasWhitelist() || this.isOp(lowerCase) || this.getWhitelisted().contains(lowerCase);
    }
    
    public DedicatedServer getServer() {
        return (DedicatedServer)super.getServer();
    }
}
