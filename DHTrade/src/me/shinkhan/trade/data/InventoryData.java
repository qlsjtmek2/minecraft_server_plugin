package me.shinkhan.trade.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.shinkhan.trade.main;

public class InventoryData implements Runnable
{
	private main M;
	private final ChatColor YELLOW = ChatColor.YELLOW;
	private final ChatColor GOLD = ChatColor.GOLD;
	private final ChatColor GREEN = ChatColor.GREEN;
	private final ChatColor GRAY = ChatColor.GRAY;
    private final int taskId;
    private final Player[] players;
    private final GameData data;
    private final Inventory[] inventorys;
    private int time;
    private boolean[] readys;
    
    public InventoryData(Player... players) {
		this.M = main.plugin;
        this.data = M.getGameData();
        this.inventorys = new Inventory[] { Bukkit.createInventory((InventoryHolder)null, 45, main.INVENTORY_NAME), Bukkit.createInventory((InventoryHolder)null, 45, main.INVENTORY_NAME) };
        this.time = this.data.getTime() * 20;
        this.readys = new boolean[2];
        this.players = players;
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("DHTrade"), (Runnable)this, 0L, 1L);
    }
    
    @Override
    public void run() {
        switch (this.time) {
            case 0: {
                if (this.getPlayer1().isOnline()) {
                    this.getPlayer1().sendMessage(GOLD + this.getName2() + GREEN + "님과의 거래가 시간초과로 취소되었습니다.");
                }
                if (this.getPlayer2().isOnline()) {
                    this.getPlayer2().sendMessage(GOLD + this.getName1() + GREEN + "님과의 거래가 시간초과로 취소되었습니다.");
                }
                this.cancel();
                break;
            }
            case -1: {
                this.checkInventory();
                if (this.readys[0] && this.readys[1]) {
                    this.cancel();
                    break;
                }
                break;
            }
            default: {
                if (this.getPlayer1().isOnline() && this.getPlayer2().isOnline()) {
                    --this.time;
                    break;
                }
                this.time = 0;
                break;
            }
        }
    }
    
    private void checkInventory() {
        Player[] players;
        for (int length = (players = this.players).length, j = 0; j < length; ++j) {
            final Player player = players[j];
            final int n1 = this.isFirst((HumanEntity)player) ? 0 : 1;
            final int n2 = this.isFirst((HumanEntity)player) ? 1 : 0;
            for (int i = 9; i < this.inventorys[n1].getSize(); ++i) {
                ItemStack item;
                if (i / 9 == 2) {
                    item = this.inventorys[n2].getItem(i - 18);
                }
                else if (i / 9 == 4 && 1 == i % 9) {
                    if (this.readys[n1]) {
                        item = this.getItemStack(Material.REDSTONE_LAMP_ON, GREEN + "확인하셨습니다.");
                    }
                    else {
                        item = this.getItemStack(Material.REDSTONE_LAMP_OFF, GREEN + "확인", GRAY + "클릭시 확인합니다.");
                    }
                }
                else if (i / 9 == 4 && i % 9 == 7) {
                    if (this.readys[n2]) {
                        item = this.getItemStack(Material.REDSTONE_LAMP_ON, GOLD + this.players[n2].getName() + GREEN + "님의 확인 여부", GRAY + "확인하셨습니다.");
                    }
                    else {
                        item = this.getItemStack(Material.REDSTONE_LAMP_OFF, GOLD + this.players[n2].getName() + GREEN + " 님의 확인 여부", GRAY + "확인하지 않았습니다.");
                    }
                }
                else {
                    item = this.getItemStack(Material.THIN_GLASS, "§f");
                }
                
                this.inventorys[n1].setItem(i, item);
            }
        }
    }
    
    private ItemStack getItemStack(Material type, String name) {
        return getItemStack(type, name, null);
    }
    
    private ItemStack getItemStack(Material type, String name, String lore) {
        ItemStack item = new ItemStack(type);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null) {
        	List<String> lorelist = new ArrayList<String>();
        	lorelist.add(lore);
        	meta.setLore(lorelist);
        }
        item.setItemMeta(meta);
        return item;
    }
    
    public void changeReady(final HumanEntity entity) {
        if (this.isFirst(entity)) {
            this.readys[0] = !this.readys[0];
        }
        else {
            this.readys[1] = !this.readys[1];
        }
    }
    
    public void changeItem() {
        this.readys[0] = (this.readys[1] = false);
    }
    
    public void cancel() {
        this.data.removeInventoryData(this);
        if (this.time == -1) {
            if (this.readys[0] && this.readys[1]) {
                for (int i = 0; i < 9; ++i) {
                    if (this.inventorys[1].getItem(i) != null) {
                        for (final ItemStack item : this.getPlayer1().getInventory().addItem(new ItemStack[] { this.inventorys[1].getItem(i) }).values()) {
                            this.getPlayer1().getWorld().dropItem(this.getPlayer1().getLocation(), item);
                        }
                    }
                    if (this.inventorys[0].getItem(i) != null) {
                        for (final ItemStack item : this.getPlayer2().getInventory().addItem(new ItemStack[] { this.inventorys[0].getItem(i) }).values()) {
                            this.getPlayer2().getWorld().dropItem(this.getPlayer2().getLocation(), item);
                        }
                    }
                }
                this.getPlayer1().sendMessage(GOLD + this.getName2() + GREEN + "님과 거래가 완료되었습니다.");
                this.getPlayer2().sendMessage(GOLD + this.getName1() + GREEN + "님과 거래가 완료되었습니다.");
            }
            else {
                for (int i = 0; i < 9; ++i) {
                    if (this.inventorys[0].getItem(i) != null) {
                        this.players[0].getInventory().addItem(new ItemStack[] { this.inventorys[0].getItem(i) });
                    }
                    if (this.inventorys[1].getItem(i) != null) {
                        this.players[1].getInventory().addItem(new ItemStack[] { this.inventorys[1].getItem(i) });
                    }
                }
                this.getPlayer1().sendMessage(GOLD + this.getName2() + YELLOW + "님과 거래가 취소되었습니다.");
                this.getPlayer2().sendMessage(GOLD + this.getName1() + YELLOW + "님과 거래가 취소되었습니다.");
            }
            for (int i = 0; i < this.players.length; ++i) {
                this.inventorys[i].clear();
            }
            Player[] players;
            for (int length = (players = this.players).length, j = 0; j < length; ++j) {
                final Player player = players[j];
                player.closeInventory();
            }
        }
        Bukkit.getScheduler().cancelTask(this.taskId);
    }
    
    public void openInventory() {
        for (int i = 0; i < this.players.length; ++i) {
            this.players[i].openInventory(this.inventorys[i]);
        }
        this.time = -1;
    }
    
    public Player getPlayer1() {
        return this.players[0];
    }
    
    public Player getPlayer2() {
        return this.players[1];
    }
    
    public String getName1() {
        return this.players[0].getName();
    }
    
    public String getName2() {
        return this.players[1].getName();
    }
    
    public boolean isFirst(final HumanEntity entity) {
        return this.players[0] == entity;
    }
    
    public boolean isPlayer(final HumanEntity entity) {
        Player[] players;
        for (int length = (players = this.players).length, i = 0; i < length; ++i) {
            final Player p = players[i];
            if (p == entity) {
                return true;
            }
        }
        return false;
    }
}
