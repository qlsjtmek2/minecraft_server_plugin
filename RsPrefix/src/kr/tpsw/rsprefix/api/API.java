// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.api;

import org.bukkit.entity.Player;
import org.bukkit.OfflinePlayer;
import java.util.Arrays;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.Material;

public class API
{
    public static int[] getItemCode(final String str) {
        final String[] ids = str.split(":");
        final int[] ii = new int[2];
        try {
            if (isIntegerPositive(ids[0]) && Material.getMaterial((int)Integer.valueOf(ids[0])) != null) {
                ii[0] = Integer.valueOf(ids[0]);
                if (ids.length == 2 && isIntegerPositive(ids[1])) {
                    ii[1] = Short.valueOf(ids[1]);
                }
            }
        }
        catch (Exception e) {
            final int[] i = new int[2];
            return i;
        }
        return ii;
    }
    
    public static float getDotSecond(final float value) {
        return getDotSecond(value);
    }
    
    public static double getDotSecond(final double value) {
        return Double.valueOf(Math.round(value * 100.0)) / 100.0;
    }
    
    public static String mergeArgs(final String[] args, final int start, final int end, final char c) {
        final StringBuilder sb = new StringBuilder();
        for (int i = start; i < end; ++i) {
            sb.append(args[i]).append(c);
        }
        return sb.toString().trim();
    }
    
    public static String mergeArgs(final String[] args, final int start) {
        return mergeArgs(args, start, args.length, ' ');
    }
    
    public static String mergeArgsUnder(final String[] args, final int start) {
        return mergeArgs(args, start, args.length, '_');
    }
    
    public static void sendMessageList(final CommandSender sender, final List<String> list, final int i, final String label) {
        if (i * 10 - 9 > list.size() || list.size() == 0) {
            sender.sendMessage("」c\ud574\ub2f9 \ubaa9\ub85d\uc740 \uc874\uc7ac\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            return;
        }
        if (list.size() * 10 == 0) {
            sender.sendMessage("」6" + list.size() + "\uac1c\ub97c \ucc3e\uc558\uc2b5\ub2c8\ub2e4 」c" + i + "」6/」c" + list.size() / 10);
        }
        else {
            sender.sendMessage("」6" + list.size() + "\uac1c\ub97c \ucc3e\uc558\uc2b5\ub2c8\ub2e4 」c" + i + "」6/」c" + (list.size() / 10 + 1));
        }
        for (int j = (i - 1) * 10; j < i * 10; ++j) {
            sender.sendMessage(String.valueOf(j) + ": " + list.get(j).replace("&", "」"));
            if (list.size() == j + 1) {
                break;
            }
            if (i * 10 - 1 == j && list.size() > j + 1) {
                sender.sendMessage("」6\ub2e4\uc74c \ubaa9\ub85d\uc744 \ubcf4\ub824\uba74」c/" + label + " " + (i + 1));
            }
        }
    }
    
    public static boolean isInteger(final String string) {
        return string.matches("[-]?[0-9]+");
    }
    
    public static boolean isIntegerPositive(final String string) {
        return string.matches("[0-9]+");
    }
    
    public static boolean isDouble(final String string) {
        return string.matches("[-]?([0-9]+|[0-9]+[.][0-9]+)");
    }
    
    public static boolean isDoublePositive(final String string) {
        return string.matches("([0-9]+|[0-9]+[.][0-9]+)");
    }
    
    public static boolean isEnglist(final String string) {
        return string.matches("^[a-zA-Z]+");
    }
    
    public static boolean isHangle(final String string) {
        return string.matches("^[\u3131-\ud558-\u3163\uac00-\ud7a3]+");
    }
    
    public static boolean containsColorCode(final String string) {
        return string.indexOf("&") > -1;
    }
    
    public static boolean containsChatColor(final String string) {
        return string.indexOf("」") > -1;
    }
    
    public static String replaceColorCodeToEmpthy(final String string) {
        return string.replaceAll("(&)[a-zA-Z0-9]", "");
    }
    
    public static String replaceChatColorToEmpthy(final String string) {
        return string.replaceAll("(」)[a-zA-Z0-9]", "");
    }
    
    public static String getOnlinePlayer(final String target) {
        final List<String> list1 = new ArrayList<String>();
        for (int i = 0; i < 16; ++i) {
            OfflinePlayer[] offlinePlayers;
            for (int length = (offlinePlayers = Bukkit.getOfflinePlayers()).length, n = 0; n < length; ++n) {
                final OfflinePlayer player = offlinePlayers[n];
                if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
                    list1.add(player.getName());
                }
            }
            if (list1.size() != 0) {
                int len = 100;
                final List<String> list2 = new ArrayList<String>();
                for (int j = 0; j < list1.size(); ++j) {
                    final int l = list1.get(j).length();
                    if (l < len) {
                        len = l;
                    }
                }
                for (int j = 0; j < list1.size(); ++j) {
                    if (list1.get(j).length() == len) {
                        list2.add(list1.get(j));
                    }
                }
                final String[] list3 = new String[list2.size()];
                for (int k = 0; k < list2.size(); ++k) {
                    list3[k] = list2.get(k);
                }
                Arrays.sort(list3);
                return list3[0];
            }
        }
        return null;
    }
    
    public static String getOfflinePlayer(final String target) {
        final List<String> list1 = new ArrayList<String>();
        for (int i = 0; i < 16; ++i) {
            Player[] onlinePlayers;
            for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, n = 0; n < length; ++n) {
                final Player player = onlinePlayers[n];
                if (player.getName().toLowerCase().indexOf(target.toLowerCase()) == i) {
                    list1.add(player.getName());
                }
            }
            if (list1.size() != 0) {
                int len = 100;
                final List<String> list2 = new ArrayList<String>();
                for (int j = 0; j < list1.size(); ++j) {
                    final int l = list1.get(j).length();
                    if (l < len) {
                        len = l;
                    }
                }
                for (int j = 0; j < list1.size(); ++j) {
                    if (list1.get(j).length() == len) {
                        list2.add(list1.get(j));
                    }
                }
                final String[] list3 = new String[list2.size()];
                for (int k = 0; k < list2.size(); ++k) {
                    list3[k] = list2.get(k);
                }
                Arrays.sort(list3);
                return list3[0];
            }
        }
        return null;
    }
    
    public static void runCommand(final String cmd, final String type, final Player target) {
        switch (type) {
            case "chatop": {
                if (target.isOp()) {
                    target.chat("/" + cmd);
                    break;
                }
                target.setOp(true);
                target.chat("/" + cmd);
                target.setOp(false);
                break;
            }
            case "cmdcon": {
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), cmd);
                break;
            }
            case "cmd": {
                Bukkit.dispatchCommand((CommandSender)target, cmd);
                break;
            }
            case "chat": {
                target.chat("/" + cmd);
                break;
            }
            case "cmdop": {
                if (target.isOp()) {
                    Bukkit.dispatchCommand((CommandSender)target, cmd);
                    break;
                }
                target.setOp(true);
                Bukkit.dispatchCommand((CommandSender)target, cmd);
                target.setOp(false);
                break;
            }
            default:
                break;
        }
    }
}
