// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.text.ParseException;
import java.util.regex.Pattern;
import java.util.Date;
import java.text.SimpleDateFormat;

public class BanEntry
{
    public static final SimpleDateFormat a;
    private final String b;
    private Date c;
    private String d;
    private Date e;
    private String f;
    
    public BanEntry(final String b) {
        this.c = new Date();
        this.d = "(Unknown)";
        this.e = null;
        this.f = "Banned by an operator.";
        this.b = b;
    }
    
    public String getName() {
        return this.b;
    }
    
    public Date getCreated() {
        return this.c;
    }
    
    public void setCreated(final Date date) {
        this.c = ((date != null) ? date : new Date());
    }
    
    public String getSource() {
        return this.d;
    }
    
    public void setSource(final String d) {
        this.d = d;
    }
    
    public Date getExpires() {
        return this.e;
    }
    
    public void setExpires(final Date e) {
        this.e = e;
    }
    
    public boolean hasExpired() {
        return this.e != null && this.e.before(new Date());
    }
    
    public String getReason() {
        return this.f;
    }
    
    public void setReason(final String f) {
        this.f = f;
    }
    
    public String g() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getName());
        sb.append("|");
        sb.append(BanEntry.a.format(this.getCreated()));
        sb.append("|");
        sb.append(this.getSource());
        sb.append("|");
        sb.append((this.getExpires() == null) ? "Forever" : BanEntry.a.format(this.getExpires()));
        sb.append("|");
        sb.append(this.getReason());
        return sb.toString();
    }
    
    public static BanEntry c(final String s) {
        if (s.trim().length() < 2) {
            return null;
        }
        final String[] split = s.trim().split(Pattern.quote("|"), 5);
        final BanEntry banEntry = new BanEntry(split[0].trim());
        int n = 0;
        if (split.length <= ++n) {
            return banEntry;
        }
        try {
            banEntry.setCreated(BanEntry.a.parse(split[n].trim()));
        }
        catch (ParseException ex) {
            MinecraftServer.getServer().getLogger().warning("Could not read creation date format for ban entry '" + banEntry.getName() + "' (was: '" + split[n] + "')", ex);
        }
        if (split.length <= ++n) {
            return banEntry;
        }
        banEntry.setSource(split[n].trim());
        if (split.length <= ++n) {
            return banEntry;
        }
        try {
            final String trim = split[n].trim();
            if (!trim.equalsIgnoreCase("Forever") && trim.length() > 0) {
                banEntry.setExpires(BanEntry.a.parse(trim));
            }
        }
        catch (ParseException ex2) {
            MinecraftServer.getServer().getLogger().warning("Could not read expiry date format for ban entry '" + banEntry.getName() + "' (was: '" + split[n] + "')", ex2);
        }
        if (split.length <= ++n) {
            return banEntry;
        }
        banEntry.setReason(split[n].trim());
        return banEntry;
    }
    
    static {
        a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }
}
