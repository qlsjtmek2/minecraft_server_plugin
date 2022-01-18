// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.api;

import java.util.ArrayList;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import java.util.List;
import kr.tpsw.api.bukkit.DataAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class InvAPI
{
    private static ItemStack back;
    private static ItemStack next;
    private static ItemStack main;
    public static String horusCode;
    
    static {
        InvAPI.back = new ItemStack(Material.WORKBENCH);
        InvAPI.next = new ItemStack(Material.ANVIL);
        InvAPI.main = new ItemStack(Material.ENCHANTMENT_TABLE);
        InvAPI.horusCode = "」a」a";
        ItemMeta im = InvAPI.back.getItemMeta();
        im.setDisplayName("」e\uc774\uc804 \ubaa9\ub85d");
        InvAPI.back.setItemMeta(im);
        im = InvAPI.next.getItemMeta();
        im.setDisplayName("」e\ub2e4\uc74c \ubaa9\ub85d");
        InvAPI.next.setItemMeta(im);
        im = InvAPI.main.getItemMeta();
        im.setDisplayName("」e\ub300\ud45c \uce6d\ud638");
        InvAPI.main.setItemMeta(im);
    }
    
    public static void viewInv(final String target, final Player caster, final int index) {
        if (!FileAPI.isLoadedPlayer(target)) {
            caster.sendMessage("」6\uc811\uc18d\uc911\uc778 \ud50c\ub808\uc774\uc5b4\ub9cc \ubcfc \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
            return;
        }
        final PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
        if (pp.needUpdateInv) {
            pp.updateInvList();
            pp.needUpdateInv = false;
        }
        final List<Inventory> invList = pp.getInvList();
        if (!DataAPI.isListhasIndex(invList, index - 1)) {
            caster.sendMessage("」c\ud574\ub2f9 \ubaa9\ub85d\uc740 \uc874\uc7ac\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4.");
            return;
        }
        final Inventory inv = invList.get(index - 1);
        caster.openInventory(inv);
    }
    
    public static Inventory createInv(final PrefixPlayer pp, final int index) {
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 54, String.valueOf(InvAPI.horusCode) + pp.getName() + " 」b:" + index);
        final List<String> list = pp.getList();
        if (list.size() == 0 || index == 0) {
            return null;
        }
        if (list.size() > (index - 1) * 45) {
            final int start = (index - 1) * 45;
            final int end = index * 45 - 1;
            final ItemStack is = new ItemStack(Material.PAPER);
            final ItemMeta im = is.getItemMeta();
            for (int i = start; i <= end && list.size() != i; ++i) {
                final List<String> lore = new ArrayList<String>();
                lore.add("」r" + list.get(i));
                final ItemMeta imm = im.clone();
                imm.setLore((List)lore);
                imm.setDisplayName("」e" + i + "\ubc88 \uc9f8 \uce6d\ud638");
                final ItemStack iss = is.clone();
                iss.setItemMeta(imm);
                inv.addItem(new ItemStack[] { iss });
            }
            if (index > 1) {
                inv.setItem(45, InvAPI.back);
            }
            else {
                final ItemStack iss2 = InvAPI.main.clone();
                final ItemMeta imm = InvAPI.main.getItemMeta();
                final List<String> lore = new ArrayList<String>();
                lore.add("」r<" + pp.getMainPrefix() + "」r>");
                imm.setLore((List)lore);
                iss2.setItemMeta(imm);
                inv.setItem(45, iss2);
            }
            if (list.size() > index * 45) {
                inv.setItem(53, InvAPI.next);
            }
            return inv;
        }
        return null;
    }
}
