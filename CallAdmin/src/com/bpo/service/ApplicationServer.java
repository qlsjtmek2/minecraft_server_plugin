// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.service;

import java.net.InetAddress;
import java.net.Socket;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.logging.Handler;
import java.util.logging.Formatter;
import java.util.logging.SimpleFormatter;
import java.io.FileOutputStream;
import org.bukkit.Bukkit;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import com.bpo.secure.AES128Cipher;
import com.bpo.calladmin.CallAdminEntry;
import java.io.OutputStream;
import java.io.File;
import com.bpo.IO.BPoPrintStream;
import java.util.ArrayList;
import java.util.logging.StreamHandler;
import java.net.ServerSocket;

public class ApplicationServer extends Thread
{
    protected static String Ver;
    public static ServerSocket soc;
    public static StreamHandler handlers;
    protected static ArrayList<SingleClientHandler> list;
    private Widget widget;
    private BPoPrintStream SystemOut;
    private boolean stateOfThread;
    public static File file;
    
    static {
        ApplicationServer.Ver = "5.0";
        ApplicationServer.list = new ArrayList<SingleClientHandler>();
        ApplicationServer.file = new File("plugins\\Bpo_CallAdmin\\Log.txt");
    }
    
    public ApplicationServer() {
        this.widget = new Widget();
        this.SystemOut = new BPoPrintStream(System.out);
        this.stateOfThread = true;
    }
    
    @Override
    public void run() {
        final int count = 0;
        AES128Cipher.setKey(CallAdminEntry.io.getKey());
        try {
            ApplicationServer.soc = new ServerSocket(CallAdminEntry.io.getPort());
        }
        catch (Exception e3) {
            System.out.println("BPo's CallAdmin Plugin : \uc11c\ubc84 \ud3ec\ud2b8\ub97c \uc815\ud655\ud788 \uc785\ub825\ud574 \uc8fc\uc138\uc694");
            return;
        }
        Socket client = null;
        System.setOut(this.SystemOut);
        this.SystemOut.setList(ApplicationServer.list);
        if (!ApplicationServer.file.exists()) {
            try {
                ApplicationServer.file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        Label_0149: {
            try {
                this.SystemOut.setLog(new FileWriter(ApplicationServer.file, true));
                Bukkit.getServer().getLogger().addHandler(ApplicationServer.handlers = new StreamHandler(new FileOutputStream(ApplicationServer.file, true), new SimpleFormatter()));
                break Label_0149;
            }
            catch (SecurityException | IOException ex2) {
                final Exception ex;
                final Exception e2 = ex;
                e2.printStackTrace();
            }
            try {
                while (true) {
                    client = ApplicationServer.soc.accept();
                    System.out.println("[CallAdmin]" + client.getInetAddress() + " : \uc811\uc18d");
                    String strs = new DataInputStream(client.getInputStream()).readUTF();
                    strs = AES128Cipher.decrypt(strs);
                    final DataOutputStream dout = new DataOutputStream(client.getOutputStream());
                    final JSONObject command = (JSONObject)JSONValue.parse(strs);
                    final String str = command.get((Object)"Command").toString();
                    if (str.equalsIgnoreCase("Widget")) {
                        this.widget.work(client);
                    }
                    else if (str.equalsIgnoreCase("Auth")) {
                        if (CallAdminEntry.io.checkKey(command.get((Object)"Key").toString())) {
                            dout.writeBoolean(true);
                            final StreamHandler handler = new StreamHandler(client.getOutputStream(), new SimpleFormatter());
                            Bukkit.getServer().getLogger().addHandler(handler);
                            ApplicationServer.list.add(new SingleClientHandler(client, count, handler));
                            System.out.println("[CallAdmin]" + client.getInetAddress() + "(\uc778\uc99d \uc131\uacf5) : \uc811\uc18d\uc790 : " + ApplicationServer.list.size() + " \ucf58\uc194 Out : " + Bukkit.getServer().getLogger().getHandlers().length);
                            ApplicationServer.list.get(ApplicationServer.list.size() - 1).start();
                            continue;
                        }
                        dout.writeBoolean(false);
                    }
                    else {
                        str.equalsIgnoreCase("Ping");
                    }
                    client.close();
                }
            }
            catch (Exception e4) {
                try {
                    client.close();
                }
                catch (Exception ex3) {}
            }
        }
    }
    
    public static void close() {
        Bukkit.getServer().getLogger().removeHandler(ApplicationServer.handlers);
        for (int i = 0; i < ApplicationServer.list.size(); ++i) {
            ApplicationServer.list.get(i).close();
        }
        try {
            ApplicationServer.soc.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(final String str) {
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Chat");
        obj.put((Object)"Data", (Object)str);
        for (int i = 0; i < ApplicationServer.list.size(); ++i) {
            ApplicationServer.list.get(i).write(obj.toString());
        }
    }
    
    public void send() {
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Notice");
        obj.put((Object)"Data", (Object)CallAdminEntry.io.getList());
        for (int i = 0; i < ApplicationServer.list.size(); ++i) {
            ApplicationServer.list.get(i).write(obj.toString());
        }
    }
    
    static void removeHandler(final StreamHandler handler, final int c) {
        InetAddress ip = null;
        for (int i = 0; i < ApplicationServer.list.size(); ++i) {
            if (ApplicationServer.list.get(i).number == c) {
                ip = ApplicationServer.list.get(i).client.getInetAddress();
                ApplicationServer.list.remove(i);
            }
        }
        Bukkit.getServer().getLogger().removeHandler(handler);
        System.out.println("[CallAdmin]" + ip + " : \uc811\uc18d\uc790 : " + ApplicationServer.list.size() + " \ucf58\uc194 Out : " + Bukkit.getServer().getLogger().getHandlers().length);
    }
    
    public File getFile() {
        return ApplicationServer.file;
    }
    
    public void playerReset() {
        if (this.stateOfThread) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationServer.access$0(ApplicationServer.this, false);
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final String str = ReadInfo.getPlayers().toString();
                    for (int i = 0; i < ApplicationServer.list.size(); ++i) {
                        ApplicationServer.list.get(i).write(str);
                    }
                    ApplicationServer.access$0(ApplicationServer.this, true);
                }
            }).start();
        }
    }
    
    static /* synthetic */ void access$0(final ApplicationServer applicationServer, final boolean stateOfThread) {
        applicationServer.stateOfThread = stateOfThread;
    }
}
