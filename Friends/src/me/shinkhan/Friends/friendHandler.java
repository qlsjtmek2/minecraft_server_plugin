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
            player.sendMessage("§6/친구 <추가/삭제/목록/수락/거절/차단/해제>");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("§6/친구 <추가/삭제/목록/수락/거절/차단/해제>");
            return true;
        }
        if (args[0].equalsIgnoreCase("삭제")) {
            if (args.length != 2) {
                player.sendMessage("§6/친구 삭제 <닉네임> §f- 친구 목록에서 삭제합니다.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("§c플레이어를 찾을 수 없습니다.");
                return true;
            }
            if (!this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                player.sendMessage("§c이 플레이어는 친구 목록에 없습니다.");
                return true;
            }
            p.sendMessage("§c" + player.getName() + " §6님이 당신을 §c친구§6에서 제거하였습니다.");
            this.Rpgf.getConfig().set(String.valueOf(player.getName()) + ".Friends." + p.getName(), (Object)null);
            this.Rpgf.getConfig().set(String.valueOf(p.getName()) + ".Friends." + player.getName(), (Object)null);
            player.sendMessage("§6당신은 성공적으로 §c" + p.getName() + " §6님을 친구 목록에서 §c제거§6하였습니다.");
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("추가")) {
            if (args.length != 2) {
                player.sendMessage("§6/친구 추가 <닉네임> §f- 친구를 추가합니다.");
                return true;
            }
            final Player p = Bukkit.getPlayer(args[1]);
            if (p == null) {
                player.sendMessage("§c플레이어를 찾을 수 없습니다.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Friends").contains(p.getName())) {
                player.sendMessage("§c이 플레이어는 이미 친구 목록에 존재합니다.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore") != null && this.Rpgf.getConfig().getConfigurationSection(String.valueOf(player.getName()) + ".Ignore").contains(p.getName())) {
                player.sendMessage("§c그 플레이어는 당신을 차단하였습니다.");
                return true;
            }
            if (this.Rpgf.getConfig().getConfigurationSection("Invited") != null && this.Rpgf.getConfig().getConfigurationSection("Invited").contains(p.getName())) {
                player.sendMessage("§c이미 친구 추가를 했던 플레이어 입니다.");
                return true;
            }
            player.sendMessage("§6성공적으로 §c" + args[1] + "§6 님에게 초대 메세지를 §c전송§6했습니다.");
            p.sendMessage("§c" + player.getName() + " §6님이 당신에게 친구 추가를 했습니다.");
			p.sendMessage("§6/친구 수락 §f- 친구 신청을 수락합니다.");
			p.sendMessage("§6/친구 거절 §f- 친구 신청을 거절합니다.");
            this.Rpgf.getConfig().set("Invited." + p.getName(), (Object)player.getName());
            this.Rpgf.saveConfig();
            this.Rpgf.friendAccept(player, p);
            return true;
        }
        else if (args[0].equalsIgnoreCase("수락")) {
            if (!this.Rpgf.getConfig().getConfigurationSection("Invited").contains(player.getName())) {
                player.sendMessage("§c당신은 대기중인 친구 요청이 업습니다.");
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
        else if (args[0].equalsIgnoreCase("거절")) {
            if (!this.Rpgf.getConfig().getConfigurationSection("Invited").contains(player.getName())) {
                player.sendMessage("§c당신은 대기중인 친구 요청이 업습니다.");
                return true;
            }
            final String name3 = player.getName();
            this.Rpgf.getConfig().set("Invited." + name3, (Object)null);
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("차단")) {
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
            player.sendMessage(String.valueOf(p.getName()) + " §4has been added to your ignore list!");
            p.sendMessage(String.valueOf(p.getName()) + " §4has added you to their ignore list!");
            this.Rpgf.saveConfig();
            return true;
        }
        else if (args[0].equalsIgnoreCase("해제")) {
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
            player.sendMessage(String.valueOf(p.getName()) + " §4has been removed from your ignore list!");
            p.sendMessage(String.valueOf(p.getName()) + " §4has removed you from their ignore list!");
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
                    lore.add("§f[World]§2" + world);
                    lore.add("§f[Left Click]§2" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("§f[Right Click]§2" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
                    lore.add("§f[shift Click]§2" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", p2.getName()).replaceAll("@p", player.getName()));
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
                    lore2.add("§f[World]§4Offline");
                    lore2.add("§f[Left Click]§2" + this.Rpgf.getConfig().getString("Commands.Left Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("§f[Right Click]§2" + this.Rpgf.getConfig().getString("Commands.Right Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
                    lore2.add("§f[Shift Click]§2" + this.Rpgf.getConfig().getString("Commands.Shift Click.Description").replaceAll("@t", key).replaceAll("@p", player.getName()));
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
