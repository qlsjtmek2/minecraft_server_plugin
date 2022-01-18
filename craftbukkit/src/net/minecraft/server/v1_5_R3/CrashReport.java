// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import org.bukkit.craftbukkit.v1_5_R3.CraftCrashReport;
import java.util.concurrent.Callable;
import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class CrashReport
{
    private final String a;
    private final Throwable b;
    private final CrashReportSystemDetails c;
    private final List d;
    private File e;
    private boolean f;
    private StackTraceElement[] g;
    
    public CrashReport(final String s, final Throwable throwable) {
        this.c = new CrashReportSystemDetails(this, "System Details");
        this.d = new ArrayList();
        this.e = null;
        this.f = true;
        this.g = new StackTraceElement[0];
        this.a = s;
        this.b = throwable;
        this.h();
    }
    
    private void h() {
        this.c.a("Minecraft Version", new CrashReportVersion(this));
        this.c.a("Operating System", new CrashReportOperatingSystem(this));
        this.c.a("Java Version", new CrashReportJavaVersion(this));
        this.c.a("Java VM Version", new CrashReportJavaVMVersion(this));
        this.c.a("Memory", new CrashReportMemory(this));
        this.c.a("JVM Flags", new CrashReportJVMFlags(this));
        this.c.a("AABB Pool Size", new CrashReportAABBPoolSize(this));
        this.c.a("Suspicious classes", new CrashReportSuspiciousClasses(this));
        this.c.a("IntCache", new CrashReportIntCacheSize(this));
        this.c.a("CraftBukkit Information", new CraftCrashReport());
    }
    
    public String a() {
        return this.a;
    }
    
    public Throwable b() {
        return this.b;
    }
    
    public void a(final StringBuilder stringbuilder) {
        if (this.g != null && this.g.length > 0) {
            stringbuilder.append("-- Head --\n");
            stringbuilder.append("Stacktrace:\n");
            for (final StackTraceElement stacktraceelement : this.g) {
                stringbuilder.append("\t").append("at ").append(stacktraceelement.toString());
                stringbuilder.append("\n");
            }
            stringbuilder.append("\n");
        }
        for (final CrashReportSystemDetails crashreportsystemdetails : this.d) {
            crashreportsystemdetails.a(stringbuilder);
            stringbuilder.append("\n\n");
        }
        this.c.a(stringbuilder);
    }
    
    public String d() {
        StringWriter stringwriter = null;
        PrintWriter printwriter = null;
        String s = this.b.toString();
        try {
            stringwriter = new StringWriter();
            printwriter = new PrintWriter(stringwriter);
            this.b.printStackTrace(printwriter);
            s = stringwriter.toString();
        }
        finally {
            try {
                if (stringwriter != null) {
                    stringwriter.close();
                }
                if (printwriter != null) {
                    printwriter.close();
                }
            }
            catch (IOException ex) {}
        }
        return s;
    }
    
    public String e() {
        final StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("---- Minecraft Crash Report ----\n");
        stringbuilder.append("// ");
        stringbuilder.append(i());
        stringbuilder.append("\n\n");
        stringbuilder.append("Time: ");
        stringbuilder.append(new SimpleDateFormat().format(new Date()));
        stringbuilder.append("\n");
        stringbuilder.append("Description: ");
        stringbuilder.append(this.a);
        stringbuilder.append("\n\n");
        stringbuilder.append(this.d());
        stringbuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int i = 0; i < 87; ++i) {
            stringbuilder.append("-");
        }
        stringbuilder.append("\n\n");
        this.a(stringbuilder);
        return stringbuilder.toString();
    }
    
    public boolean a(final File file1, final IConsoleLogManager iconsolelogmanager) {
        if (this.e != null) {
            return false;
        }
        if (file1.getParentFile() != null) {
            file1.getParentFile().mkdirs();
        }
        try {
            final FileWriter filewriter = new FileWriter(file1);
            filewriter.write(this.e());
            filewriter.close();
            this.e = file1;
            return true;
        }
        catch (Throwable throwable) {
            iconsolelogmanager.severe("Could not save crash report to " + file1, throwable);
            return false;
        }
    }
    
    public CrashReportSystemDetails g() {
        return this.c;
    }
    
    public CrashReportSystemDetails a(final String s) {
        return this.a(s, 1);
    }
    
    public CrashReportSystemDetails a(final String s, final int i) {
        final CrashReportSystemDetails crashreportsystemdetails = new CrashReportSystemDetails(this, s);
        if (this.f) {
            final int j = crashreportsystemdetails.a(i);
            final StackTraceElement[] astacktraceelement = this.b.getStackTrace();
            StackTraceElement stacktraceelement = null;
            StackTraceElement stacktraceelement2 = null;
            if (astacktraceelement != null && astacktraceelement.length - j < astacktraceelement.length) {
                stacktraceelement = astacktraceelement[astacktraceelement.length - j];
                if (astacktraceelement.length + 1 - j < astacktraceelement.length) {
                    stacktraceelement2 = astacktraceelement[astacktraceelement.length + 1 - j];
                }
            }
            this.f = crashreportsystemdetails.a(stacktraceelement, stacktraceelement2);
            if (j > 0 && !this.d.isEmpty()) {
                final CrashReportSystemDetails crashreportsystemdetails2 = this.d.get(this.d.size() - 1);
                crashreportsystemdetails2.b(j);
            }
            else if (astacktraceelement != null && astacktraceelement.length >= j) {
                System.arraycopy(astacktraceelement, 0, this.g = new StackTraceElement[astacktraceelement.length - j], 0, this.g.length);
            }
            else {
                this.f = false;
            }
        }
        this.d.add(crashreportsystemdetails);
        return crashreportsystemdetails;
    }
    
    private static String i() {
        final String[] astring = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!" };
        try {
            return astring[(int)(System.nanoTime() % astring.length)];
        }
        catch (Throwable throwable) {
            return "Witty comment unavailable :(";
        }
    }
    
    public static CrashReport a(final Throwable throwable, final String s) {
        CrashReport crashreport;
        if (throwable instanceof ReportedException) {
            crashreport = ((ReportedException)throwable).a();
        }
        else {
            crashreport = new CrashReport(s, throwable);
        }
        return crashreport;
    }
}
