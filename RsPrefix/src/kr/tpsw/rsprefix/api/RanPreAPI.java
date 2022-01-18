// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.api;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.Random;
import java.util.List;

public class RanPreAPI
{
    public static List<String> basic;
    private static List<String> classic;
    private static List<String> rare;
    private static List<String> epic;
    private static List<String> legendary;
    private static Random rr;
    public static ItemStack ranpre;
    
    static {
        RanPreAPI.rr = new Random();
        RanPreAPI.ranpre = new ItemStack(Material.BOOK);
        ItemMeta im = RanPreAPI.ranpre.getItemMeta();
        im.setDisplayName("¡×b ·£´ý ÄªÈ£ÆÑ");
        List<String> list = new ArrayList<String>();
        list.add("¡×7¿ìÅ¬¸¯ÇÏ¿© ·£´ý ÄªÈ£¸¦ È¹µæÇÒ ¼ö ÀÖ½À´Ï´Ù.");
        im.setLore(list);
        RanPreAPI.ranpre.setItemMeta(im);
    }
    
    public static ItemStack getPrefixBook(String prx) {
    	ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
    	item.setDurability((short) 147);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName("¡×e¡×lÅ¸ÀÌÆ²ºÏ");
        List<String> list = new ArrayList<String>();
        list.add(prx);
        list.add("¡×7¿ìÅ¬¸¯ÇÏ¿© ÇØ´ç ÄªÈ£¸¦ È¹µæÇÒ ¼ö ÀÖ½À´Ï´Ù.");
        im.setLore(list);
        item.setItemMeta(im);
        return item;
    }
    
    public static void runRandomPrefix(final Player sender) {
        final PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
        final int i = getRanClass(sender.getName());
        if (i == 1) {
            final String pre = getRan(RanPreAPI.classic);
            final List<String> list = pp.getList();
            if (!list.contains(pre)) {
                list.add(pre);
            }
            pp.needUpdateInv = true;
            sender.sendMessage("¡×f\uc77c\ubc18 ¡×r<" + pre + "¡×r> ¡×e\uce6d\ud638\ub97c \ud68d\ub4dd\ud588\uc2b5\ub2c8\ub2e4");
        }
        else if (i == 2) {
            final String pre = getRan(RanPreAPI.rare);
            final List<String> list = pp.getList();
            if (!list.contains(pre)) {
                list.add(pre);
            }
            pp.needUpdateInv = true;
            sender.sendMessage("¡×b\ud76c\uadc0 ¡×r<" + pre + "¡×r> ¡×e\uce6d\ud638\ub97c \ud68d\ub4dd\ud588\uc2b5\ub2c8\ub2e4!");
        }
        else if (i == 3) {
            final String pre = getRan(RanPreAPI.epic);
            final List<String> list = pp.getList();
            if (!list.contains(pre)) {
                list.add(pre);
            }
            pp.needUpdateInv = true;
            sender.sendMessage("¡×e\uc640\uc6b0! ¡×6\uc5d0\ud53d ¡×r<" + pre + "¡×r> ¡×e\uce6d\ud638\uc785\ub2c8\ub2e4!");
        }
        else if (i == 4) {
            final String pre = getRan(RanPreAPI.legendary);
            final List<String> list = pp.getList();
            if (!list.contains(pre)) {
                list.add(pre);
            }
            pp.needUpdateInv = true;
            Bukkit.broadcastMessage("¡×e\uc640\uc6b0! ¡×c" + sender.getName() + "¡×e\ub2d8\uc774 ¡×6\uc804\uc124 ¡×r<" + pre + "¡×r> ¡×e\uce6d\ud638\ub97c \ud68d\ub4dd\ud588\uc2b5\ub2c8\ub2e4!");
        }
    }
    
    public static void initSetting() {
        initSetting2(new File("plugins\\RsPrefix\\RanPrefixs\\basic.txt"), "¡×f\ub274\ube44");
        initSetting2(new File("plugins\\RsPrefix\\RanPrefixs\\classic.txt"), "¡×f[\ucd08\ubcf4\uc790]");
        initSetting2(new File("plugins\\RsPrefix\\RanPrefixs\\rare.txt"), "¡×b[\uce5c\uc808\ud55c]");
        initSetting2(new File("plugins\\RsPrefix\\RanPrefixs\\epic.txt"), "¡×f[¡×6Poi¡×f]");
        initSetting2(new File("plugins\\RsPrefix\\RanPrefixs\\legendary.txt"), "¡×a[¡×c\uc11c\ubc84_\ub9c8\uc2a4\ud130¡×a]");
    }
    
    private static void initSetting2(final File f, final String s) {
        try {
            final FileWriter fw = new FileWriter(f);
            final BufferedWriter bw = new BufferedWriter(fw);
            bw.write(s);
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void initLoad() {
        RanPreAPI.basic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\basic.txt");
        RanPreAPI.classic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\classic.txt");
        RanPreAPI.rare = getBuffered("plugins\\RsPrefix\\RanPrefixs\\rare.txt");
        RanPreAPI.epic = getBuffered("plugins\\RsPrefix\\RanPrefixs\\epic.txt");
        RanPreAPI.legendary = getBuffered("plugins\\RsPrefix\\RanPrefixs\\legendary.txt");
    }
    
    private static String getRan(final List<String> list) {
        final int size = RanPreAPI.rr.nextInt(list.size());
        return list.get(size);
    }
    
    private static int getRanClass(final String name) {
        final int ran = RanPreAPI.rr.nextInt(1000);
        if (getMap(Pclass.LEGENDARY, name) >= 200) {
            setMap(Pclass.LEGENDARY, name);
            return 4;
        }
        if (getMap(Pclass.EPIC, name) >= 50) {
            if (ran < 10) {
                setMap(Pclass.LEGENDARY, name);
                return 4;
            }
            addMap(Pclass.LEGENDARY, name);
            setMap(Pclass.EPIC, name);
            return 3;
        }
        else if (getMap(Pclass.RARE, name) >= 5) {
            if (ran < 10) {
                setMap(Pclass.LEGENDARY, name);
                return 4;
            }
            if (ran < 50) {
                addMap(Pclass.LEGENDARY, name);
                setMap(Pclass.EPIC, name);
                return 3;
            }
            addMap(Pclass.LEGENDARY, name);
            addMap(Pclass.EPIC, name);
            setMap(Pclass.RARE, name);
            return 2;
        }
        else {
            if (ran < 10) {
                setMap(Pclass.LEGENDARY, name);
                return 4;
            }
            if (ran < 50) {
                addMap(Pclass.LEGENDARY, name);
                setMap(Pclass.EPIC, name);
                return 3;
            }
            if (ran < 250) {
                addMap(Pclass.LEGENDARY, name);
                addMap(Pclass.EPIC, name);
                setMap(Pclass.RARE, name);
                return 2;
            }
            addMap(Pclass.LEGENDARY, name);
            addMap(Pclass.EPIC, name);
            addMap(Pclass.RARE, name);
            return 1;
        }
    }
    
    private static void setMap(final Pclass pc, final String name) {
        pc.map.put(name, 0);
    }
    
    private static int getMap(final Pclass pc, final String name) {
        if (pc.map.containsKey(name)) {
            return pc.map.get(name);
        }
        return 0;
    }
    
    private static void addMap(final Pclass pc, final String name) {
        if (pc.map.containsKey(name)) {
            pc.map.put(name, pc.map.get(name) + 1);
        }
        else {
            pc.map.put(name, 1);
        }
    }
    
    private static List<String> getBuffered(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        final BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        final List<String> list = new ArrayList<String>();
        try {
            String s;
            while ((s = br.readLine()) != null) {
                list.add(s);
            }
            br.close();
            fis.close();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
        return list;
    }
    
    public enum Pclass
    {
        RARE("RARE", 0), 
        EPIC("EPIC", 1), 
        LEGENDARY("LEGENDARY", 2);
        
        public Map<String, Integer> map;
        
        private Pclass(final String s, final int n) {
            this.map = new HashMap<String, Integer>();
        }
        
        public void loadMap() {
            final File f = new File("plugins\\RsPrefix\\" + this.name() + ".ser");
            if (f.exists()) {
                try {
                    final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                    this.map = (Map<String, Integer>)ois.readObject();
                    ois.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void saveMap() {
            try {
                final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("plugins\\RsPrefix\\" + this.name() + ".ser")));
                oos.writeObject(this.map);
                oos.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
