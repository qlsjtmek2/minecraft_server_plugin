package me.shinkhan.Friends;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.ArrayList;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class friendHandler implements CommandExecutor
{
    main Rpgf;
    
    public friendHandler(final main rpgf) {
        this.Rpgf = rpgf;
    }
    
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length >= 3) {
            player.sendMessage("��6/ģ�� <�߰�/����/���/����/����/����/����>");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("��6/ģ�� <�߰�/����/���/����/����/����/����>");
            return true;
        }
        if (args[0].equalsIgnoreCase("����")) {
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� ���� <�г���> ��f- ģ�� ��Ͽ��� �����մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (!this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                player.sendMessage("��c�� �÷��̾�� ģ�� ��Ͽ� �����ϴ�.");
                return true;
            }
            p.sendMessage("��c" + player.getName() + " ��6���� ����� ��cģ����6���� �����Ͽ����ϴ�.");
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Friends." + p.getName(), (Object)null);
            this.Rpgf.getConfig().set(String.valueOf(p.getName()) + ".Friends." + player.getName(), (Object)null);
            player.sendMessage("��6����� ���������� ��c" + p.getName() + " ��6���� ģ�� ��Ͽ��� ��c���š�6�Ͽ����ϴ�.");
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("�߰�")) {
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� �߰� <�г���> ��f- ģ���� �߰��մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                player.sendMessage("��c�� �÷��̾�� �̹� ģ�� ��Ͽ� �����մϴ�.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                player.sendMessage("��c�� �÷��̾�� ����� �����Ͽ����ϴ�.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection("Invited") != null && this.Rpgf.getConfig().getConfigurationSection("Invited").contains(p.getName())) {
                player.sendMessage("��c�̹� ģ�� �߰��� �ߴ� �÷��̾� �Դϴ�.");
                return true;
            }
            player.sendMessage("��6���������� ��c" + args[1] + "��6 �Կ��� �ʴ� �޼����� ��c���ۡ�6�߽��ϴ�.");
            p.sendMessage("��c" + player.getName() + " ��6���� ��ſ��� ģ�� �߰��� �߽��ϴ�.");
			p.sendMessage("��6/ģ�� ���� ��f- ģ�� ��û�� �����մϴ�.");
			p.sendMessage("��6/ģ�� ���� ��f- ģ�� ��û�� �����մϴ�.");
            this.Rpgf.getConfig().set("Invited." + p.getName(), (Object)player.getName());
            this.Rpgf.saveConfig();
            this.Rpgf.friendAccept(player, p);
            return true;
        }
        else if (args[0].equalsIgnoreCase("����")) {
            if (!this.Rpgf.getConfig().getConfigurationSection("Invited").contains(player.getName())) {
                player.sendMessage("��c����� ������� ģ�� ��û�� �����ϴ�.");
                return true;
            }
            final String name = this.Rpgf.getConfig().getString("Invited." + player.getName());
            final String name2 = player.getName();
            this.Rpgf.getConfig().set(String.valueOf(name) + ".Friends." + name2, (Object)true);
            this.Rpgf.getConfig().set(String.valueOf(name2) + ".Friends." + name, (Object)true);
            this.Rpgf.getConfig().set("Invited." + name2, (Object)null);
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("����")) {
            if (!this.Rpgf.getConfig().getConfigurationSection("Invited").contains(player.getName())) {
                player.sendMessage("��c����� ������� ģ�� ��û�� �����ϴ�.");
                return true;
            }
            final String name3 = player.getName();
            this.Rpgf.getConfig().set("Invited." + name3, (Object)null);
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("����")) {
            if (!this.Rpgf.getConfig().getBoolean("Chat")) {
                player.sendMessage("ignore features have been turned off on this server!");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage("You must use /friend ignore {playername}");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("player could not be found!");
                return true;
            }
            if (p.isOp()) {
                player.sendMessage("You cannot ignore a server op!");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") != null) {
                if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                    player.sendMessage("You must remove this player from your friends list before you can ignore them!");
                    return true;
                }
                if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                    player.sendMessage("You already have this player ignored!");
                    return true;
                }
            }
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Ignore." + p.getName(), (Object)true);
            player.sendMessage(String.valueOf(p.getName()) + " ��4has been added to your ignore list!");
            p.sendMessage(String.valueOf(p.getName()) + " ��4has added you to their ignore list!");
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("����")) {
            if (!this.Rpgf.getConfig().getBoolean("Chat")) {
                player.sendMessage("ignore features have been turned off on this server!");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage("You must use /friend unignore {playername}");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("player could not be found!");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") == null) {
                player.sendMessage("You don't have anyone on your ignore list!");
                return true;
            }
            if (!this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                player.sendMessage("You don't have this person on your ignore list!");
                return true;
            }
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Ignore." + p.getName(), (Object)null);
            player.sendMessage(String.valueOf(p.getName()) + " ��4has been removed from your ignore list!");
            p.sendMessage(String.valueOf(p.getName()) + " ��4has removed you from their ignore list!");
            this.Rpgf.saveConfig();
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("list")) {
                return false;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(player.getName()) == null) {
                player.sendMessage("You do not have anyone on your friends list!");
                return true;
            }
            Integer islot = 0;
            final Inventory flist = Bukkit.createInventory((InventoryHolder)null, 45, "Friends List");
            for (final String key : this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").getKeys(false)) {
                if (Bukkit.getPlayer(key) != null) {
                    final Player p2 = Bukkit.getPlayer(key);
                    final ItemStack skull = new ItemStack(397, 1, (short)3);
                    final SkullMeta meta = (SkullMeta)skull.getItemMeta();
                    final ArrayList<String> lore = new ArrayList<String>();
                    final String world = p2.getLocation().getWorld().getName();
                    lore.add("��f[World]��2" + world);
                    lore.add("��f[Left Click]��2" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("��f[Right Click]��2" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("��f[shift Click]��2" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    meta.setLore((List<String>)lore);
                    meta.setOwner(p2.getName());
                    skull.setItemMeta((ItemMeta)meta);
                    flist.setItem((int)islot, skull);
                    ++islot;
                }
                else {
                    final ItemStack skull2 = new ItemStack(397, 1, (short)0);
                    final SkullMeta meta2 = (SkullMeta)skull2.getItemMeta();
                    final ArrayList<String> lore2 = new ArrayList<String>();
                    lore2.add("��f[World]��4Offline");
                    lore2.add("��f[Left Click]��2" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("��f[Right Click]��2" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("��f[Shift Click]��2" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    meta2.setLore((List<String>)lore2);
                    meta2.setDisplayName(key);
                    skull2.setItemMeta((ItemMeta)meta2);
                    flist.setItem((int)islot, skull2);
                    ++islot;
                }
            }
            player.openInventory(flist);
            return true;
        }
    }
}
