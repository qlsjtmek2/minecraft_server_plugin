// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.Instance;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import GoldenMine.Main;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;

public class ConfigSetting
{
    public static boolean KEEP_COERCIVE_EXCHANGE;
    public static boolean SHIFT_EXCHANGE;
    public static String LOCALE;
    public static boolean RPGITEM_COPY_BUG;
    public static boolean LOOK_MESSAGE_SHOPKEEPERS;
    public static boolean LOOK_MESSAGE_RPGITEM;
    public static boolean ONE_COPY_BUG;
    public static boolean HOPPER_CART_REMOVE_CHEST;
    public static boolean HOPPER_CART_REMOVE_PORTAL;
    public static boolean BOAT_REMOVE;
    public static boolean ARROW_CANCEL;
    public static boolean CHEST_BREAK;
    public static boolean TRIGGER_BUG;
    public static boolean FREECAM;
    public static boolean SkriptGUI;
    public static boolean CartBug;
    public static boolean ace;
    public static boolean co_l;
    public static boolean VGH;
    public static boolean chestshop;
    
    public static void CreateConfig(final File file) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            w.append((CharSequence)"# \uc81c\uc791: Golden_Mine\r\n");
            w.append((CharSequence)"# \uc81c\uc791\uc790 \ud55c\ub9c8\ub514: \ubc84\uadf8 \uc798\ub9c9\ud788\uba74 \ube14\ub85c\uadf8\uc5d0 \ub313\uae00\ud558\ub098 \ub2ec\uc544\uc8fc\uc2dc\uc8e0 \u3147\u3147 \uadf8\ub798\uc57c\uc9c0 \u3147\u3147\r\n");
            w.append((CharSequence)"# \uc81c\uc791\uc790 \ud55c\ub9c8\ub514: \uad6c\ubc84\uc804 \uc4f0\uc9c0 \ub9c8\uc138\uc694 \uc2e0\ubc84\uc804 \ube14\ub85c\uadf8\uc5d0\uc11c \ubc1b\uc544\uc4f0\uc138\uc694 \uc11c\ub85c\uc774\uc6c3 or \uc774\uc6c3 \u3131\u3131\r\n");
            w.append((CharSequence)"# \uc81c\uc791\uc790 \ud55c\ub9c8\ub514: 1\ub2ec\ucbe4\ub9c8\ub2e4 \ub0b4\ube14\ub85c\uadf8 \uc640\uc11c \ucd5c\uc2e0\ubc84\uc804 \uc788\ub098 \ud655\uc778\ud558\uace0 \uac00\uc0bc - \uad6c\ubc84\uc804 \uc368\ub193\uace0 \ubc84\uadf8\uc548\ub9c9\ud78c\ub2e4\uace0 \ucc21\ucc21\ub300\uba74 \uc8fd\uc5ec\ubc84\ub9bc\r\n");
            w.append((CharSequence)"# \uc81c\uc791\uc790 \ud55c\ub9c8\ub514: KEEP_COERCIVE_EXCHANGE \uc548\ub9c9\ud788\uc2dc\ub294\ubd84\ub4e4 craftbukkit \ucd5c\uc2e0\ubc84\uc804 \uc4f0\uc2dc\uac70\ub098 spigot\uc73c\ub85c \ubc84\ud0b7 \ubc14\uafb8\uc2ed\uc2dc\uc694\r\n");
            w.append((CharSequence)"# true\ub97c \uc785\ub825\ud558\uba74 \ubc29\uc5b4\ud558\uace0 false\ub97c \uc785\ub825\ud558\uba74 \ubc29\uc5b4\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# KEEP_COERCIVE_EXCHANGE\ub294 \ub354\ube14\ud074\ub9ad\uc744 \uc774\uc6a9\ud55c \uac15\uc81c\uad50\ud658\uc744 \ub9c9\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# SHIFT_EXCHANGE\ub294 \uc26c\ud504\ud2b8\ub97c \uc774\uc6a9\ud55c \ubcf5\uc0ac\ubc84\uadf8\ub97c \ub9c9\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# RPGITEM_COPY_BUG\ub294 RPGITEM\uc758 \ubcf5\uc0ac\ubc84\uadf8\ub97c \ub9c9\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# RPGITEM_COPY_BUG_2\ub294 \uc778\ubca4\ud1a0\ub9ac\uc5d0 \ub0a8\uc740\uc790\ub9ac\uac00 3\uc790\ub9ac \uc774\ud558\uc77c\ub54c \ud074\ub9ad\uc744 \ubc29\uc9c0\ud558\ub294 \uae30\ub2a5\uc785\ub2c8\ub2e4. (\uac70\uc758 \ud544\uc694\ud558\uc9c4 \uc54a\uc73c\ubbc0\ub85c false\ub85c \ud574\ub450\uc138\uc694)\r\n");
            w.append((CharSequence)"# ONE_COPY_BUG\ub294 \ud68c\uc6d0\ud0c8\ud1f4\ub97c \uc774\uc6a9\ud55c \ubcf5\uc0ac\ubc84\uadf8\ub97c \ub9c9\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# LOOK_MESSAGE_SHOPKEEPERS \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0\ub85c \ucc28\ub2e8\ub418\uc5c8\uc744\ub54c \uacbd\uace0 \uba54\uc2dc\uc9c0\ub97c \ubcf4\uc5ec\uc904\uac83\uc778\uc9c0 \uc124\uc815\ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# LOOK_MESSAGE_RPGITEM 65\uac1c \ubcf5\uc0ac\ubc84\uadf8 \ubc29\uc9c0\ub85c \ucc28\ub2e8\ub418\uc5c8\uc744\ub54c \uacbd\uace0 \uba54\uc2dc\uc9c0\ub97c \ubcf4\uc5ec\uc904\uac83\uc778\uc9c0 \uc124\uc815\ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# ONE_COPY_BUG\ub294 \ud68c\uc6d0\ud0c8\ud1f4\ub97c \uc774\uc6a9\ud55c \ubcf5\uc0ac\ubc84\uadf8\ub97c \ub9c9\uc2b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# HOPPER_CART_REMOVE_CHEST\ub294 \uae54\ub54c\uae30 \uce74\ud2b8\uac00 \uc0c1\uc790 \uc544\ub798\ub85c \uac14\uc744\uacbd\uc6b0 \uc544\uc774\ud15c\ud654 \uc2dc\ucf1c\uc90d\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# HOPPER_CART_REMOVE_PORTAL\uc740 \uae54\ub54c\uae30/\uc0c1\uc790/\ud654\ub85c \uce74\ud2b8\uac00 \uc9c0\uc625\ubb38 \uc8fc\uc704\ub85c \uac14\uc744\ub54c \uac15\uc81c\ub85c \uc81c\uac70\uc2dc\ud0b5\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# HOPPER_CART_REMOVE_PORTAL \uc8fc\uc758\uc810: \uac15\uc81c\ub85c \uc81c\uac70\uc2dc\ud0a4\ubbc0\ub85c \ub0b4\uc6a9\ubb3c\uc774 \uadf8\ub300\ub85c \uc99d\ubc1c\ub429\ub2c8\ub2e4!\r\n");
            w.append((CharSequence)"# ARROW_CANCEL\uc740 \ub514\uc2a4\ud39c\uc11c\uc5d0\uc11c \ud654\uc0b4\uc744 \ubc1c\uc0ac\ud560\uc218 \uc5c6\ub3c4\ub85d \uc124\uc815\ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# Locale \uc740 \ud55c\uad6d\uc5b4\ub85c \ud558\uc2e4 \uc2dc ko, if you want to see English, input us.\r\n");
            w.append((CharSequence)"# CHEST_BREAK\uc740 \uc0c1\uc790(\ub610\ub294 \uc0c1\uc790\uce74\ud2b8)\ub97c \ubd80\uc220\ub54c \uc0c1\uc790 \uc8fc\uc704\uc5d0 \ub2e4\ub978\ud50c\ub808\uc774\uc5b4\uac00 \uc788\uc744\uacbd\uc6b0 \ubabb\ubd80\uc218\uac8c \ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# TRIGGER_BUG\ub294 \uba85\ub839\uc5b4\uc5d0 <>\uc774 \ub4e4\uc5b4\uac14\uc744\uc2dc \uc0ac\uc6a9\uc744 \uce94\uc2ac\ud558\uace0 \ud0a5\ud558\ub294 \uae30\ub2a5\uc785\ub2c8\ub2e4. (\ud2b8\ub9ac\uac70 \ubc84\uadf8 \ubc29\uc9c0)\r\n");
            w.append((CharSequence)"# SkriptGUI\ub294 \uc2a4\ud06c\ub9bd\ud2b8\ub97c \uc774\uc6a9\ud55c GUI\ub97c \uc0c1\uc790\ub97c \uc5f4\uba70 \ub3d9\uc2dc\uc5d0 \uc624\ud508\uc2dc \ubcf5\uc0ac\ubc84\uadf8\uac00 \ub418\ub294\uac83\uc744 \ubc29\uc9c0\ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# CartBug\ub294 \ud504\ub9ac\ucea0+\uce74\ud2b8\ub97c \uc774\uc6a9\ud55c \ubc84\uadf8\ub97c \ubc29\uc9c0\ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# ace\ub294 AntiCreeper\uc758 ace\uba85\ub839\uc5b4\ub97c \uc624\ud53c\uac00 \uc544\ub2c8\uba74 \ubabb\uce58\uac8c \ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# col\uc740 \uadf8\ub0e5 \ub2e4 /co l \ubabb\uce58\uac8c \ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# VGH\uc740 \uadf8\ub0e5 \ub2e4 /VGH \ubabb\uce58\uac8c \ud569\ub2c8\ub2e4.\r\n");
            w.append((CharSequence)"# chestshop\uc740 \uc0c1\uc790 \uc5f4\uc5b4\ubcfc\ub54c \uc8fc\uc704\uc5d0 ChestShop \ud45c\uc9c0\ud310 \uc788\uc744\uc2dc \ubabb\uc5f4\uac8c\ud569\ub2c8\ub2e4(\uccb4\uc2a4\ud2b8\uc0f5\ubc84\uadf8\ubc29\uc9c0)\r\n");
            w.append((CharSequence)("\r\n\ubc84\uc804: " + Main.version + "\r\n"));
            w.append((CharSequence)("Locale: " + ConfigSetting.LOCALE + "\r\n"));
            w.append((CharSequence)("KEEP_COERCIVE_EXCHANGE: " + ConfigSetting.KEEP_COERCIVE_EXCHANGE + "\r\n"));
            w.append((CharSequence)("SHIFT_EXCHANGE: " + ConfigSetting.SHIFT_EXCHANGE + "\r\n"));
            w.append((CharSequence)("RPGITEM_COPY_BUG: " + ConfigSetting.RPGITEM_COPY_BUG + "\r\n"));
            w.append((CharSequence)("FREECAM: " + ConfigSetting.FREECAM + "\r\n"));
            w.append((CharSequence)("LOOK_MESSAGE_SHOPKEEPERS: " + ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS + "\r\n"));
            w.append((CharSequence)("LOOK_MESSAGE_RPGITEM: " + ConfigSetting.LOOK_MESSAGE_RPGITEM + "\r\n"));
            w.append((CharSequence)("ONE_COPY_BUG: " + ConfigSetting.ONE_COPY_BUG + "\r\n"));
            w.append((CharSequence)("HOPPER_CART_REMOVE_CHEST: " + ConfigSetting.HOPPER_CART_REMOVE_CHEST + "\r\n"));
            w.append((CharSequence)("HOPPER_CART_REMOVE_PORTAL: " + ConfigSetting.HOPPER_CART_REMOVE_PORTAL + "\r\n"));
            w.append((CharSequence)("BOAT_REMOVE: " + ConfigSetting.BOAT_REMOVE + "\r\n"));
            w.append((CharSequence)("ARROW_CANCEL: " + ConfigSetting.ARROW_CANCEL + "\r\n"));
            w.append((CharSequence)("CHEST_BREAK: " + ConfigSetting.CHEST_BREAK + "\r\n"));
            w.append((CharSequence)("TRIGGER_BUG: " + ConfigSetting.TRIGGER_BUG + "\r\n"));
            w.append((CharSequence)("SkriptGUI: " + ConfigSetting.SkriptGUI + "\r\n"));
            w.append((CharSequence)("CartBug: " + ConfigSetting.CartBug + "\r\n"));
            w.append((CharSequence)("ace: " + ConfigSetting.ace + "\r\n"));
            w.append((CharSequence)("co_l: " + ConfigSetting.co_l + "\r\n"));
            w.append((CharSequence)("VGH: " + ConfigSetting.VGH + "\r\n"));
            w.append((CharSequence)("chestshop: " + ConfigSetting.chestshop + "\r\n"));
            w.flush();
            w.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Main.PrintBukkit("\ucf58\ud53c\uadf8 \ub85c\ub4dc\uc911 \ubb38\uc81c\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");
        }
    }
    
    public static boolean LoadConfig() {
        final File Folder = new File("plugins/BlockMCBug");
        Folder.mkdir();
        final File file = new File("plugins/BlockMCBug/config.yml");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            final BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String s;
            while ((s = r.readLine()) != null) {
                String[] temp;
                try {
                    temp = s.split(": ");
                }
                catch (Exception e2) {
                    continue;
                }
                final String s2 = temp[0];
                switch (s2) {
                    case "KEEP_COERCIVE_EXCHANGE": {
                        ConfigSetting.KEEP_COERCIVE_EXCHANGE = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "SHIFT_EXCHANGE": {
                        ConfigSetting.SHIFT_EXCHANGE = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "LOCALE": {
                        ConfigSetting.LOCALE = temp[1].toLowerCase();
                        if (temp[1].equals("us")) {
                            Main.PrintMessage("§eYour Locale: us");
                            continue;
                        }
                        Main.PrintMessage("§e\ub2f9\uc2e0\uc758 \uc5b8\uc5b4: ko");
                        continue;
                    }
                    case "RPGITEM_COPY_BUG": {
                        ConfigSetting.RPGITEM_COPY_BUG = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "FREECAM": {
                        ConfigSetting.FREECAM = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "LOOK_MESSAGE_SHOPKEEPERS": {
                        ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "LOOK_MESSAGE_RPGITEM": {
                        ConfigSetting.LOOK_MESSAGE_RPGITEM = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "ONE_COPY_BUG": {
                        ConfigSetting.ONE_COPY_BUG = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "HOPPER_CART_REMOVE_CHEST": {
                        ConfigSetting.HOPPER_CART_REMOVE_CHEST = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "HOPPER_CART_REMOVE_PORTAL": {
                        ConfigSetting.HOPPER_CART_REMOVE_PORTAL = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "BOAT_REMOVE": {
                        ConfigSetting.BOAT_REMOVE = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "ARROW_CANCEL": {
                        ConfigSetting.ARROW_CANCEL = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "CHEST_BREAK": {
                        ConfigSetting.CHEST_BREAK = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "TRIGGER_BUG": {
                        ConfigSetting.TRIGGER_BUG = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "CartBug": {
                        ConfigSetting.CartBug = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "SkriptGUI": {
                        ConfigSetting.SkriptGUI = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "ace": {
                        ConfigSetting.ace = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "VGH": {
                        ConfigSetting.VGH = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "co_l": {
                        ConfigSetting.co_l = Boolean.valueOf(temp[1]);
                        continue;
                    }
                    case "chestshop": {
                        ConfigSetting.chestshop = Boolean.valueOf(temp[1]);
                        continue;
                    }
                }
            }
            file.delete();
            CreateConfig(file);
            r.close();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            Main.PrintBukkit("\ucf58\ud53c\uadf8 \ub85c\ub4dc\uc911 \ubb38\uc81c\uac00 \ubc1c\uc0dd\ud588\uc2b5\ub2c8\ub2e4.");
            return false;
        }
    }
    
    public void tepoimp(final int k, final long g) {
        final ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        list.stream().filter(num -> num % 2 == 0);
    }
    
    static {
        ConfigSetting.KEEP_COERCIVE_EXCHANGE = true;
        ConfigSetting.SHIFT_EXCHANGE = true;
        ConfigSetting.LOCALE = "ko";
        ConfigSetting.RPGITEM_COPY_BUG = true;
        ConfigSetting.LOOK_MESSAGE_SHOPKEEPERS = true;
        ConfigSetting.LOOK_MESSAGE_RPGITEM = true;
        ConfigSetting.ONE_COPY_BUG = true;
        ConfigSetting.HOPPER_CART_REMOVE_CHEST = true;
        ConfigSetting.HOPPER_CART_REMOVE_PORTAL = true;
        ConfigSetting.BOAT_REMOVE = true;
        ConfigSetting.ARROW_CANCEL = true;
        ConfigSetting.CHEST_BREAK = true;
        ConfigSetting.TRIGGER_BUG = true;
        ConfigSetting.FREECAM = true;
        ConfigSetting.SkriptGUI = true;
        ConfigSetting.CartBug = true;
        ConfigSetting.ace = true;
        ConfigSetting.co_l = true;
        ConfigSetting.VGH = true;
        ConfigSetting.chestshop = true;
    }
}
