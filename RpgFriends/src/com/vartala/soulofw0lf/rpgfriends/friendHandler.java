package com.vartala.soulofw0lf.rpgfriends;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class friendHandler implements CommandExecutor
{
    RpgFriends Rpgf;
    
    public friendHandler(final RpgFriends rpgf) {
        this.Rpgf = rpgf;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player player = (Player)sender;
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length >= 3) {
            player.sendMessage("��f�ùٸ��� ���� ��ɾ� �Դϴ�. - ��6/ģ�� <�߰�/����/���/����/����>");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("��f�ùٸ��� ���� ��ɾ� �Դϴ�. - ��6/ģ�� <�߰�/����/���/����/����>");
            return true;
        }
        if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("����")) {
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� ���� <�г���> ��f- ģ�� ��Ͽ��� �����մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (player == p) {
                player.sendMessage("��c�ڱ� �ڽ��� ���� �� �����ϴ�.");
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
        else if (args[0].equalsIgnoreCase("�߰�") || args[0].equalsIgnoreCase("�ʴ�")) {
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� �߰� <�г���> ��f- ģ���� �߰��մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (player == p) {
                player.sendMessage("��c�ڱ� �ڽ��� �߰��� �� �����ϴ�.");
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
        else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("����")) {
            if (!this.Rpgf.getConfig().getBoolean("Chat")) {
                player.sendMessage("��c���� ���� ����� �۵����� �ʽ��ϴ�.");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� ���� <�г���> ��f- ģ���� �����մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (player == p) {
                player.sendMessage("��c�ڱ� �ڽ��� ������ �� �����ϴ�.");
                return true;
            }
            if (p.isOp()) {
                player.sendMessage("��c������ ���Ǵ� ���ܽ�ų �� �����ϴ�.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") != null) {
                if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                    player.sendMessage("��c����� �÷��̾ �����ϱ� ���� �÷��̾ ģ�� ��Ͽ��� ������ �ճ���.");
                    return true;
                }
                if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                    player.sendMessage("��c�̹� �� �÷��̾�� ���ܵǾ� �ֽ��ϴ�.");
                    return true;
                }
            }
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Ignore." + p.getName(), (Object)true);
            player.sendMessage("��c" + String.valueOf(p.getName()) + " ��6�÷��̾ ��c���� ��ϡ�6�� �߰��Ͽ����ϴ�.");
            p.sendMessage("��c" + String.valueOf(p.getName()) + " ��6���� ����� ��c���ܡ�6�Ͽ����ϴ�.");
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("����") || args[0].equalsIgnoreCase("��������")) {
            if (!this.Rpgf.getConfig().getBoolean("Chat")) {
                player.sendMessage("��c���� ���� ����� �۵����� �ʽ��ϴ�.");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage("��6/ģ�� ���� <�г���> ��f- ģ���� ���ܿ��� �����մϴ�.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("��c�÷��̾ ã�� �� �����ϴ�.");
                return true;
            }
            if (player == p) {
                player.sendMessage("��c�ڱ� �ڽ��� ���� ������ �� �����ϴ�.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") == null) {
                player.sendMessage("��c�÷��̾�� �̹� ������ �����Ǿ� �ֽ��ϴ�.");
                return true;
            }
            if (!this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                player.sendMessage("��c������ ������ų ����� �����ϴ�.");
                return true;
            }
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Ignore." + p.getName(), (Object)null);
            player.sendMessage("��c" + String.valueOf(p.getName()) + " ��6���� ��c���� ��ϡ�6�� �����Ǿ����ϴ�.");
            p.sendMessage("��c" + String.valueOf(p.getName()) + " ��6���� ����� ������ ��c������6�Ͽ����ϴ�.");
            this.Rpgf.saveConfig();
            return true;
        }
        else {
            if (!args[0].equalsIgnoreCase("���")) {
                return false;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(player.getName()) == null) {
                player.sendMessage("��c����� ���� ģ���� �������� �ʽ��ϴ�.");
                return true;
            }
            Integer islot = 0;
            final Inventory flist = Bukkit.createInventory((InventoryHolder)null, 45, "ģ�� ���");
            for (final String key : this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").getKeys(false)) {
                if (Bukkit.getPlayer(key) != null) {
                    final Player p2 = Bukkit.getPlayer(key);
                    final ItemStack skull = new ItemStack(397, 1, (short)3);
                    final SkullMeta meta = (SkullMeta)skull.getItemMeta();
                    final ArrayList<String> lore = new ArrayList<String>();
                    final String world = p2.getLocation().getWorld().getName();
                    lore.add("��a���� ��7:: ��2" + world);
                    lore.add("��6���� Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("��6������ Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("��e����Ʈ + Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
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
                    lore2.add("��a���� ��7:: ��4��������");
                    lore2.add("��6���� Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("��6������ Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("��e����Ʈ + Ŭ�� ��7:: ��f" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
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
