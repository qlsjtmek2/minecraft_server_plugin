package me.espoo.mg.Data;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import me.espoo.mg.main;

public class ItemData {
    public static void item() {
        for (final String playerName : GameData.players) {
            if (!GameData.diePlayers.contains(playerName)) {
                RandomItem(Bukkit.getPlayerExact(playerName));
            }
        }
    }
    
    public static void Speeditem() {
        for (final String playerName : GameData.players) {
            if (!GameData.diePlayers.contains(playerName)) {
                SpeedRandomItem(Bukkit.getPlayerExact(playerName));
            }
        }
    }
    
    private static void RandomItem(final Player player) {
        if (player != null) {
            ItemStack item = null;
            ItemMeta meta = null;
            PotionMeta pMeta = null;
            Potion potion = null;
            switch (new Random().nextInt(7)) {
                case 0: {
                    potion = new Potion(PotionType.WATER);
                    potion.setSplash(false);
                    item = potion.toItemStack(1);
                    pMeta = (PotionMeta)item.getItemMeta();
                    pMeta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)), true);
                    pMeta.setDisplayName(ChatColor.GREEN + "신속의 포션");
                    item.setItemMeta((ItemMeta)pMeta);
                    break;
                }
                case 1: {
                    potion = new Potion(PotionType.WATER);
                    potion.setSplash(false);
                    item = potion.toItemStack(1);
                    pMeta = (PotionMeta)item.getItemMeta();
                    pMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, (new Random().nextInt(5) + 5) * 20, new Random().nextInt(5)), true);
                    pMeta.setDisplayName(ChatColor.GREEN + "점프의 포션");
                    item.setItemMeta((ItemMeta)pMeta);
                    break;
                }
                case 2: {
                    item = new ItemStack(Material.SNOW_BALL, GameData.players.size());
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "눈덩이");
                    item.setItemMeta(meta);
                    break;
                }
                case 3: {
                    item = new ItemStack(Material.SNOW_BALL, 1);
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "독눈덩이");
                    item.setItemMeta(meta);
                    break;
                }
                case 4: {
                    item = new ItemStack(Material.SNOW_BALL, 1);
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "느려지는 눈덩이");
                    item.setItemMeta(meta);
                    break;
                }
                case 5: {
                    item = new ItemStack(Material.SNOW_BALL, 1);
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "어지러운 눈덩이");
                    item.setItemMeta(meta);
                    break;
                }
                case 6: {
                    item = new ItemStack(Material.SNOW_BALL, 1);
                    meta = item.getItemMeta();
                    meta.setDisplayName(ChatColor.GREEN + "안보이는 눈덩이");
                    item.setItemMeta(meta);
                    break;
                }
            }
            player.getInventory().addItem(new ItemStack[] { item });
            player.sendMessage(main.a + "§f아이템이 지급되었습니다.");
        }
    }
    
    private static void SpeedRandomItem(final Player player) {
        if (player != null) {
            ItemStack item = null;
            ItemMeta meta = null;
            switch (new Random().nextInt(5) + 1) {
            	case 1: {
            		item = new ItemStack(Material.SNOW_BALL, GameData.players.size());
            		meta = item.getItemMeta();
            		meta.setDisplayName(ChatColor.GREEN + "눈덩이");
            		item.setItemMeta(meta);
            		break;
            	}
            	case 2: {
            		item = new ItemStack(Material.SNOW_BALL, 1);
            		meta = item.getItemMeta();
            		meta.setDisplayName(ChatColor.GREEN + "독눈덩이");
            		item.setItemMeta(meta);
            		break;
            	}
            	case 3: {
            		item = new ItemStack(Material.SNOW_BALL, 1);
            		meta = item.getItemMeta();
            		meta.setDisplayName(ChatColor.GREEN + "느려지는 눈덩이");
            		item.setItemMeta(meta);
            		break;
            	}
            	case 4: {
            		item = new ItemStack(Material.SNOW_BALL, 1);
            		meta = item.getItemMeta();
            		meta.setDisplayName(ChatColor.GREEN + "어지러운 눈덩이");
            		item.setItemMeta(meta);
            		break;
            	}
            	case 5: {
            		item = new ItemStack(Material.SNOW_BALL, 1);
            		meta = item.getItemMeta();
            		meta.setDisplayName(ChatColor.GREEN + "안보이는 눈덩이");
            		item.setItemMeta(meta);
            		break;
            	} 
            } 
            player.getInventory().addItem(new ItemStack[] { item });
            player.sendMessage(main.a + "§f아이템이 지급되었습니다.");
        }
    }
}
