// 
// Decompiled by Procyon v0.5.30
// 

package com.bpo.service;

import java.util.Iterator;
import java.io.IOException;
import org.bukkit.command.CommandSender;
import java.io.InputStream;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import com.bpo.calladmin.CallAdminEntry;
import java.lang.reflect.InvocationTargetException;
import org.bukkit.entity.Player;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.json.simple.JSONValue;
import com.bpo.secure.AES128Cipher;
import java.io.DataInputStream;
import org.json.simple.JSONObject;
import java.util.logging.StreamHandler;
import java.io.DataOutputStream;
import java.net.Socket;

public class SingleClientHandler extends Thread
{
    protected Socket client;
    protected int number;
    private DataOutputStream dout;
    private StreamHandler handler;
    JSONObject logs;
    
    SingleClientHandler(final Socket client, final int number, final StreamHandler handler) {
        this.logs = new JSONObject();
        this.client = client;
        this.number = number;
        this.handler = handler;
    }
    
    @Override
    public void run() {
        try {
            final DataInputStream din = new DataInputStream(this.client.getInputStream());
            this.dout = new DataOutputStream(this.client.getOutputStream());
            this.send();
            while (!this.client.isConnected() || !this.client.isInputShutdown() || !this.client.isOutputShutdown()) {
                String str = din.readUTF();
                try {
                    str = AES128Cipher.decrypt(str);
                }
                catch (Exception e2) {
                    System.out.println("[CallAdmin] \ubcc0\uc870\uac00 \uc758\uc2ec\ub418\ub294 \uba85\ub839");
                    continue;
                }
                final JSONObject command = (JSONObject)JSONValue.parse(str);
                str = command.get((Object)"Command").toString();
                System.out.println("[CallAdmin]" + this.client.getInetAddress() + " : " + str);
                if (str.equalsIgnoreCase("Chat")) {
                    str = command.get((Object)"Data").toString();
                    int check = -1;
                    Collection<?> list = null;
                    Player[] player = null;
                    try {
                        if (Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).getReturnType() == Collection.class) {
                            list = (Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                            check = 1;
                        }
                        else {
                            player = (Player[])Bukkit.class.getMethod("getOnlinePlayers", (Class<?>[])new Class[0]).invoke(null, new Object[0]);
                            check = 2;
                        }
                    }
                    catch (NoSuchMethodException ex) {}
                    catch (InvocationTargetException ex2) {}
                    catch (IllegalAccessException ex3) {}
                    if (check == 1) {
                        for (final Object p : list) {
                            ((Player)p).sendMessage(str);
                        }
                    }
                    else {
                        if (check != 2) {
                            continue;
                        }
                        Player[] array;
                        for (int length = (array = player).length, i = 0; i < length; ++i) {
                            final Player p2 = array[i];
                            p2.sendMessage(str);
                        }
                    }
                }
                else if (str.equalsIgnoreCase("Register")) {
                    CallAdminEntry.io.addAdmin(command.get((Object)"UUID").toString(), command.get((Object)"regID").toString());
                }
                else if (str.equalsIgnoreCase("Remove")) {
                    CallAdminEntry.io.removeAdmin(command.get((Object)"UUID").toString());
                }
                else if (str.equalsIgnoreCase("Memory")) {
                    this.write(ReadInfo.memory().toString());
                }
                else if (str.equalsIgnoreCase("UserCount")) {
                    this.write(ReadInfo.getPlayers().toString());
                }
                else if (str.equalsIgnoreCase("ServerOFF")) {
                    Bukkit.shutdown();
                }
                else if (str.equalsIgnoreCase("ServerInfo")) {
                    this.write(ReadInfo.GetServer().toString());
                }
                else if (str.equalsIgnoreCase("ClearLog")) {
                    new FileOutputStream("plugins\\Bpo_CallAdmin\\Log.txt").close();
                }
                else if (str.equalsIgnoreCase("getLogs")) {
                    final Scanner s = new Scanner(new FileInputStream(ApplicationServer.file));
                    while (s.hasNext()) {
                        this.sendLog(s.nextLine());
                    }
                    s.close();
                }
                else if (str.equalsIgnoreCase("opfalse")) {
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "deop " + command.get((Object)"NickName").toString());
                }
                else if (str.equalsIgnoreCase("optrue")) {
                    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "op " + command.get((Object)"NickName").toString());
                }
                else if (str.equalsIgnoreCase("Command")) {
                    if (command.get((Object)"Data").toString().equals("reload")) {
                        ApplicationServer.close();
                        Bukkit.reload();
                    }
                    else {
                        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), command.get((Object)"Data").toString());
                    }
                }
                else {
                    if (!str.equalsIgnoreCase("Delete")) {
                        continue;
                    }
                    CallAdminEntry.io.removeList(command.get((Object)"Data").toString());
                    this.send();
                }
            }
        }
        catch (IOException ex4) {}
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        this.close();
        ApplicationServer.removeHandler(this.handler, this.number);
    }
    
    public void write(final String str) {
        try {
            this.dout.writeUTF(AES128Cipher.encrypt(str));
        }
        catch (IOException e) {
            ApplicationServer.removeHandler(this.handler, this.number);
        }
    }
    
    private void send() {
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Notice");
        obj.put((Object)"Ver", (Object)ApplicationServer.Ver);
        obj.put((Object)"Data", (Object)CallAdminEntry.io.getList());
        this.write(obj.toString());
    }
    
    public void sendLog(final String str) {
        this.logs.put((Object)"Command", (Object)"Log");
        this.logs.put((Object)"Data", (Object)str);
        this.write(this.logs.toString());
    }
    
    public void close() {
        ApplicationServer.removeHandler(this.handler, this.number);
        final JSONObject obj = new JSONObject();
        obj.put((Object)"Command", (Object)"Close");
        this.write(obj.toString());
        try {
            this.client.close();
        }
        catch (IOException ex) {}
    }
}
