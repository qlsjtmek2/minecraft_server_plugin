// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class CommandHandler implements ICommandHandler
{
    private final Map a;
    private final Set b;
    
    public CommandHandler() {
        this.a = new HashMap();
        this.b = new HashSet();
    }
    
    public int a(final ICommandListener commandListener, String s) {
        s = s.trim();
        if (s.startsWith("/")) {
            s = s.substring(1);
        }
        final String[] split = s.split(" ");
        final String s2 = split[0];
        final String[] a = a(split);
        final ICommand command = this.a.get(s2);
        final int a2 = this.a(command, a);
        int n = 0;
        try {
            if (command == null) {
                throw new ExceptionUnknownCommand();
            }
            if (command.b(commandListener)) {
                if (a2 > -1) {
                    final EntityPlayer[] players = PlayerSelector.getPlayers(commandListener, a[a2]);
                    final String s3 = a[a2];
                    final EntityPlayer[] array = players;
                    for (int length = array.length, i = 0; i < length; ++i) {
                        a[a2] = array[i].getLocalizedName();
                        try {
                            command.b(commandListener, a);
                            ++n;
                        }
                        catch (CommandException ex) {
                            commandListener.sendMessage(EnumChatFormat.RED + commandListener.a(ex.getMessage(), ex.a()));
                        }
                    }
                    a[a2] = s3;
                }
                else {
                    command.b(commandListener, a);
                    ++n;
                }
            }
            else {
                commandListener.sendMessage("" + EnumChatFormat.RED + "You do not have permission to use this command.");
            }
        }
        catch (ExceptionUsage exceptionUsage) {
            commandListener.sendMessage(EnumChatFormat.RED + commandListener.a("commands.generic.usage", commandListener.a(exceptionUsage.getMessage(), exceptionUsage.a())));
        }
        catch (CommandException ex2) {
            commandListener.sendMessage(EnumChatFormat.RED + commandListener.a(ex2.getMessage(), ex2.a()));
        }
        catch (Throwable t) {
            commandListener.sendMessage(EnumChatFormat.RED + commandListener.a("commands.generic.exception", new Object[0]));
            t.printStackTrace();
        }
        return n;
    }
    
    public ICommand a(final ICommand command) {
        final List b = command.b();
        this.a.put(command.c(), command);
        this.b.add(command);
        if (b != null) {
            for (final String s : b) {
                final ICommand command2 = this.a.get(s);
                if (command2 == null || !command2.c().equals(s)) {
                    this.a.put(s, command);
                }
            }
        }
        return command;
    }
    
    private static String[] a(final String[] array) {
        final String[] array2 = new String[array.length - 1];
        for (int i = 1; i < array.length; ++i) {
            array2[i - 1] = array[i];
        }
        return array2;
    }
    
    public List b(final ICommandListener commandListener, final String s) {
        final String[] split = s.split(" ", -1);
        final String s2 = split[0];
        if (split.length == 1) {
            final ArrayList<String> list = new ArrayList<String>();
            for (final Map.Entry<String, V> entry : this.a.entrySet()) {
                if (CommandAbstract.a(s2, entry.getKey()) && ((ICommand)entry.getValue()).b(commandListener)) {
                    list.add(entry.getKey());
                }
            }
            return list;
        }
        if (split.length > 1) {
            final ICommand command = this.a.get(s2);
            if (command != null) {
                return command.a(commandListener, a(split));
            }
        }
        return null;
    }
    
    public List a(final ICommandListener commandListener) {
        final ArrayList<ICommand> list = new ArrayList<ICommand>();
        for (final ICommand command : this.b) {
            if (command.b(commandListener)) {
                list.add(command);
            }
        }
        return list;
    }
    
    public Map a() {
        return this.a;
    }
    
    private int a(final ICommand command, final String[] array) {
        if (command == null) {
            return -1;
        }
        for (int i = 0; i < array.length; ++i) {
            if (command.a(array, i) && PlayerSelector.isList(array[i])) {
                return i;
            }
        }
        return -1;
    }
}
