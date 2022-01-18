// 
// Decompiled by Procyon v0.5.29
// 

package Cro.Zombie.Event;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import Cro.Zombie.Moudule.Util;
import org.bukkit.Effect;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.Iterator;
import java.util.List;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.ChatColor;
import Cro.Zombie.Command.Zombie;
import org.bukkit.Material;
import Cro.Zombie.Command.CroZombie;
import org.bukkit.entity.Snowball;
import Cro.Zombie.Data.GameData;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import java.util.Timer;
import org.bukkit.event.Listener;

public class EventManager implements Listener
{
    public static boolean DamageGuard;
    public static int Check2;
    public static int Live;
    public static int Pleple;
    public static int Buff;
    public static int Kill3;
    public static String Killer;
    public static Timer Cool;
    
    static {
        EventManager.DamageGuard = true;
        EventManager.Check2 = 0;
        EventManager.Live = 0;
        EventManager.Pleple = 0;
        EventManager.Buff = 0;
        EventManager.Kill3 = 0;
        EventManager.Cool = new Timer();
    }
    
    @EventHandler
    public static void onEntityDamage(EntityDamageEvent event) { if ((event.getEntity() instanceof Player))
        if (DamageGuard) {
          event.setCancelled(true);
          event.getEntity().setFireTicks(0);
        } else {
          Player entity = (Player)event.getEntity();
          if ((event instanceof EntityDamageByEntityEvent)) {
            EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
            Event.getCause();
            String entityTeam = (String)GameData.Zombie.get(entity.getName());
            if (Event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
              if ((Event.getDamager() instanceof Snowball)) {
                Snowball pn = (Snowball)Event.getDamager();
                Location l = pn.getLocation();
                Location l2 = pn.getLocation();
                double degrees = Math.toRadians(-(l.getYaw() % 360.0F));
                double ydeg = Math.toRadians(-(l.getPitch() % 360.0F));
                l2.setX(l.getX() + 0.1D * (Math.sin(degrees) * Math.cos(ydeg)));
                l2.setY(l.getY() + 0.1D * Math.sin(ydeg));
                l2.setZ(l.getZ() + 0.1D * (Math.cos(degrees) * Math.cos(ydeg)));
                Snowball snow = (Snowball)Event.getDamager();
                Player Snow = (Player)snow.getShooter();
                String SnowTeam = (String)GameData.Zombie.get(Snow.getName());
                if (((SnowTeam == "생존자") && (entityTeam == "영웅")) || ((SnowTeam == "영웅") && (entityTeam == "생존자"))) {
                  Event.setCancelled(true);
                } else if ((SnowTeam == entityTeam) || (SnowTeam == null) || (entityTeam == null)) {
                  Event.setCancelled(true);
                } else {
                  Player p = (Player)Event.getEntity();
                  if ((entityTeam == "숙주") || (entityTeam == "감염자")) {
                    if ((SnowTeam == "숙주") || (SnowTeam == "감염자")) {
                      Event.setCancelled(true);
                    } else {
                      World w = p.getWorld();
                      if ((entityTeam == "숙주") || (entityTeam == "감염자")) {
                        if (Math.random() <= 0.03D) {
                          if (SnowTeam == "영웅") {
                            if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                              Event.setDamage(CroZombie.UserSniperDamage * 2);
                            else
                              Event.setDamage(CroZombie.HeroGunDamage * 2);
                          }
                          else if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                            Event.setDamage(CroZombie.UserSniperDamage * 2);
                          else
                            Event.setDamage(CroZombie.UserGunDamage * 2);
                          w.createExplosion(p.getLocation(), 0.0F);
                        }
                        else if (SnowTeam == "영웅") {
                          if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                            Event.setDamage(CroZombie.UserSniperDamage);
                          else
                            Event.setDamage(CroZombie.HeroGunDamage);
                        }
                        else if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper)) {
                          Event.setDamage(CroZombie.UserSniperDamage);
                        } else {
                          Event.setDamage(CroZombie.UserGunDamage);
                        }
                        if (Zombie.ZombieBuff == 1)
                          Event.getEntity().setVelocity(
                            Event.getEntity().getVelocity().add(
                            Event.getDamager().getLocation().toVector().subtract(
                            Event.getEntity().getLocation().toVector()).normalize().multiply(-0.6D)));
                        else {
                          Event.getEntity().setVelocity(
                            Event.getEntity().getVelocity().add(
                            Event.getDamager().getLocation().toVector().subtract(
                            Event.getEntity().getLocation().toVector()).normalize().multiply(-0.4D)));
                        }
                        if (CroZombie.UseHealth)
                          Snow.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                      } else if ((entityTeam == "생존자") || (entityTeam == "영웅")) {
                        if ((Buff == 1) || (Buff == 2)) {
                          if (entityTeam == "영웅")
                            Event.setDamage(Event.getDamage() / 3);
                          else
                            Event.setDamage(Event.getDamage() / 2);
                        } else if (Buff == 3) {
                          if (entityTeam == "영웅")
                            Event.setDamage(Event.getDamage() / 4);
                          else
                            Event.setDamage(Event.getDamage() / 3);
                        }
                        if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                          Snow.sendMessage(ChatColor.BLUE + "생존자의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                      }
                    }
                  } else {
                    World w = p.getWorld();
                    if ((entityTeam == "숙주") || (entityTeam == "감염자")) {
                      if (Math.random() <= 0.03D) {
                        if (SnowTeam == "영웅") {
                          if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                            Event.setDamage(CroZombie.UserSniperDamage * 2);
                          else
                            Event.setDamage(CroZombie.HeroGunDamage * 2);
                        }
                        else if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                          Event.setDamage(CroZombie.UserSniperDamage * 2);
                        else
                          Event.setDamage(CroZombie.UserGunDamage * 2);
                        w.createExplosion(p.getLocation(), 0.0F);
                      }
                      else if (SnowTeam == "영웅") {
                        if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper))
                          Event.setDamage(CroZombie.UserSniperDamage);
                        else
                          Event.setDamage(CroZombie.HeroGunDamage);
                      }
                      else if (Snow.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper)) {
                        Event.setDamage(CroZombie.UserSniperDamage);
                      } else {
                        Event.setDamage(CroZombie.UserGunDamage);
                      }
                      if (Zombie.ZombieBuff == 1)
                        Event.getEntity().setVelocity(
                          Event.getEntity().getVelocity().add(
                          Event.getDamager().getLocation().toVector().subtract(
                          Event.getEntity().getLocation().toVector()).normalize().multiply(-0.6D)));
                      else {
                        Event.getEntity().setVelocity(
                          Event.getEntity().getVelocity().add(
                          Event.getDamager().getLocation().toVector().subtract(
                          Event.getEntity().getLocation().toVector()).normalize().multiply(-0.4D)));
                      }
                      if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                        Snow.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                    } else if ((entityTeam == "생존자") || (entityTeam == "영웅")) {
                      if ((Buff == 1) || (Buff == 2)) {
                        if (entityTeam == "영웅")
                          Event.setDamage(Event.getDamage() / 3);
                        else
                          Event.setDamage(Event.getDamage() / 2);
                      } else if (Buff == 3) {
                        if (entityTeam == "영웅")
                          Event.setDamage(Event.getDamage() / 4);
                        else
                          Event.setDamage(Event.getDamage() / 3);
                      }
                      if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                        Snow.sendMessage(ChatColor.BLUE + "생존자의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                    }
                  }
                }
              }
            } else if ((Event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) && 
              (Event.getDamager().getType() == EntityType.PLAYER)) {
              Player damager = (Player)Event.getDamager();
              String damagerTeam = (String)GameData.Zombie.get(damager.getName());
              if (((damagerTeam == "생존자") && (entityTeam == "영웅")) || ((damagerTeam == "영웅") && (entityTeam == "생존자"))) {
                Event.setCancelled(true);
              } else if ((damagerTeam == entityTeam) || (damagerTeam == null) || (entityTeam == null)) {
                Event.setCancelled(true);
              } else {
                Player p = (Player)Event.getEntity();
                if ((entityTeam == "숙주") || (entityTeam == "감염자")) {
                  if ((damagerTeam == "숙주") || (damagerTeam == "감염자")) {
                    Event.setCancelled(true);
                  } else {
                    World w = p.getWorld();
                    if (entityTeam == "숙주") {
                      if (Math.random() <= 0.05D) {
                        if (damagerTeam == "영웅")
                          Event.setDamage((int)(Event.getDamage() * 1.2D));
                        else
                          Event.setDamage(Event.getDamage());
                        w.createExplosion(p.getLocation(), 0.0F);
                      }
                      else if (damagerTeam == "영웅") {
                        Event.setDamage((int)(Event.getDamage() * 0.8D));
                      } else {
                        Event.setDamage((int)(Event.getDamage() * 0.5D));
                      }
                      if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                        damager.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                    } else if (entityTeam == "감염자") {
                      if ((damager.getItemInHand().getTypeId() == 39) && (CroZombie.UseVac)) {
                        damager.setItemInHand(null);
                        GameData.Zombie.remove(entity.getName());
                        entity.setDisplayName(ChatColor.BLUE + entity.getName());
                        entity.setPlayerListName(ChatColor.BLUE + entity.getName());
                        entity.getInventory().clear();
                        entity.setFoodLevel(10);
                        entity.setLevel(0);
                        entity.setExhaustion(0.0F);
                        entity.setExp(0.0F);
                        entity.setHealth(20);
                        entity.getInventory().setItem(0, new ItemStack(267, 1));
                        entity.getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                        entity.getInventory().setHelmet(new ItemStack(306, 1));
                        entity.getInventory().setBoots(new ItemStack(309, 1));
                        entity.getInventory().setChestplate(new ItemStack(307, 1));
                        entity.getInventory().setLeggings(new ItemStack(308, 1));
                        if (CroZombie.UseBullet)
                          entity.getInventory().setItem(8, new ItemStack(CroZombie.GunBulletCode, CroZombie.GiveBullet));
                        GameData.Zombie.put(entity.getName(), "생존자");
                        GameData.Delay.put(entity.getName(), "0");
                        GameData.bullet.put(entity.getName(), Integer.valueOf(CroZombie.GunBullet));
                        damager.sendMessage(ChatColor.BLUE + "[안내] 백신을 사용하였습니다.");
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE).append("[공지] ").append(damager.getName()).append("님이 백신을 사용하여 ").append(entity.getName()).append("님을 치료 하엿습니다.").toString());
                      } else {
                        if (Math.random() <= 0.05D) {
                          if (damagerTeam == "영웅")
                            Event.setDamage((int)(Event.getDamage() * 1.2D));
                          else
                            Event.setDamage(Event.getDamage());
                          w.createExplosion(p.getLocation(), 0.0F);
                        }
                        else if (damagerTeam == "영웅") {
                          Event.setDamage((int)(Event.getDamage() * 0.8D));
                        } else {
                          Event.setDamage((int)(Event.getDamage() * 0.5D));
                        }
                        if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                          damager.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                      }
                    } else if ((entityTeam == "생존자") || (entityTeam == "영웅")) {
                      if (Buff == 1) {
                        if (damagerTeam == "숙주") {
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 24);
                          else
                            Event.setDamage(Event.getDamage() * 20);
                        } else if (damagerTeam == "감염자")
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 22);
                          else
                            Event.setDamage(Event.getDamage() * 18);
                      }
                      else if (Buff == 2) {
                        if (damagerTeam == "숙주") {
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 20);
                          else
                            Event.setDamage(Event.getDamage() * 16);
                        } else if (damagerTeam == "감염자")
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 14);
                          else
                            Event.setDamage(Event.getDamage() * 12);
                      }
                      else if (Buff == 3) {
                        if (damagerTeam == "숙주") {
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 16);
                          else
                            Event.setDamage(Event.getDamage() * 12);
                        } else if (damagerTeam == "감염자") {
                          if (entityTeam == "생존자")
                            Event.setDamage(Event.getDamage() * 12);
                          else
                            Event.setDamage(Event.getDamage() * 10);
                        }
                      }
                      else if (damagerTeam == "숙주") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 26);
                        else
                          Event.setDamage(Event.getDamage() * 22);
                      } else if (damagerTeam == "감염자") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 24);
                        else {
                          Event.setDamage(Event.getDamage() * 20);
                        }
                      }
                      if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                        damager.sendMessage(ChatColor.BLUE + "생존자의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                    }
                  }
                } else {
                  World w = p.getWorld();
                  if (entityTeam == "숙주") {
                    if (Math.random() <= 0.05D) {
                      if (damagerTeam == "영웅")
                        Event.setDamage((int)(Event.getDamage() * 1.2D));
                      else
                        Event.setDamage(Event.getDamage());
                      w.createExplosion(p.getLocation(), 0.0F);
                    }
                    else if (damagerTeam == "영웅") {
                      Event.setDamage((int)(Event.getDamage() * 0.8D));
                    } else {
                      Event.setDamage((int)(Event.getDamage() * 0.5D));
                    }
                    if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                      damager.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                  } else if (entityTeam == "감염자") {
                    if ((damager.getItemInHand().getTypeId() == 39) && (CroZombie.UseVac)) {
                      damager.setItemInHand(null);
                      GameData.Zombie.remove(entity.getName());
                      entity.setDisplayName(ChatColor.BLUE + entity.getName());
                      entity.setPlayerListName(ChatColor.BLUE + entity.getName());
                      entity.getInventory().clear();
                      entity.setFoodLevel(10);
                      entity.setLevel(0);
                      entity.setExhaustion(0.0F);
                      entity.setExp(0.0F);
                      entity.setHealth(20);
                      entity.getInventory().setItem(0, new ItemStack(267, 1));
                      entity.getInventory().setItem(1, new ItemStack(CroZombie.Gun, 1));
                      entity.getInventory().setHelmet(new ItemStack(306, 1));
                      entity.getInventory().setBoots(new ItemStack(309, 1));
                      entity.getInventory().setChestplate(new ItemStack(307, 1));
                      entity.getInventory().setLeggings(new ItemStack(308, 1));
                      if (CroZombie.UseBullet)
                        entity.getInventory().setItem(8, new ItemStack(266, CroZombie.GiveBullet));
                      GameData.Zombie.put(entity.getName(), "생존자");
                      GameData.Delay.put(entity.getName(), "0");
                      GameData.bullet.put(entity.getName(), Integer.valueOf(CroZombie.GunBullet));
                      damager.sendMessage(ChatColor.BLUE + "[안내] 백신을 사용하였습니다.");
                      Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE).append("[공지]").append(damager.getName()).append("님이 백신을 사용하여").append(entity.getName()).append("님을 치료 하엿습니다.").toString());
                    } else {
                      if (Math.random() <= 0.05D) {
                        if (damagerTeam == "영웅")
                          Event.setDamage((int)(Event.getDamage() * 1.2D));
                        else
                          Event.setDamage(Event.getDamage());
                        w.createExplosion(p.getLocation(), 0.0F);
                      }
                      else if (damagerTeam == "영웅") {
                        Event.setDamage((int)(Event.getDamage() * 0.8D));
                      } else {
                        Event.setDamage((int)(Event.getDamage() * 0.5D));
                      }
                      if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                        damager.sendMessage(ChatColor.RED + "좀비의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                    }
                  } else if ((entityTeam == "생존자") || (entityTeam == "영웅")) {
                    if (Buff == 1) {
                      if (damagerTeam == "숙주") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 24);
                        else
                          Event.setDamage(Event.getDamage() * 20);
                      } else if (damagerTeam == "감염자")
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 22);
                        else
                          Event.setDamage(Event.getDamage() * 18);
                    }
                    else if (Buff == 2) {
                      if (damagerTeam == "숙주") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 20);
                        else
                          Event.setDamage(Event.getDamage() * 16);
                      } else if (damagerTeam == "감염자")
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 14);
                        else
                          Event.setDamage(Event.getDamage() * 12);
                    }
                    else if (Buff == 3) {
                      if (damagerTeam == "숙주") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 16);
                        else
                          Event.setDamage(Event.getDamage() * 12);
                      } else if (damagerTeam == "감염자") {
                        if (entityTeam == "생존자")
                          Event.setDamage(Event.getDamage() * 12);
                        else
                          Event.setDamage(Event.getDamage() * 10);
                      }
                    }
                    else if (damagerTeam == "숙주") {
                      if (entityTeam == "생존자")
                        Event.setDamage(Event.getDamage() * 26);
                      else
                        Event.setDamage(Event.getDamage() * 22);
                    } else if (damagerTeam == "감염자") {
                      if (entityTeam == "생존자")
                        Event.setDamage(Event.getDamage() * 24);
                      else {
                        Event.setDamage(Event.getDamage() * 20);
                      }
                    }
                    if ((CroZombie.UseHealth) && (20 - p.getHealth() >= 0))
                      damager.sendMessage(ChatColor.BLUE + "생존자의 남은 체력 " + ChatColor.WHITE + ": " + ChatColor.DARK_RED + p.getHealth() + ChatColor.WHITE + " / 20");
                  }
                }
              }
            }
          }
        }
    }
    
    @EventHandler
    public static void onPlayerDeath(final PlayerDeathEvent event) {
        final Player killed = event.getEntity();
        if (event.getDeathMessage().indexOf("ground") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774 \uc6b4\uc9c0 \ud558\uc167\uc2b5\ub2c8\ub2e4.").toString());
        }
        else if (event.getDeathMessage().indexOf("drowned") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774 \ubb3c\uc18d\uc5d0\uc11c \uc0ac\ub9dd \ud558\uc167\uc2b5\ub2c8\ub2e4.").toString());
        }
        else if (event.getDeathMessage().indexOf("lava") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774 \uc6a9\uc554\uc5d0 \ubd88\ud0c0 \uc0ac\ub9dd \ud558\uc167\uc2b5\ub2c8\ub2e4.").toString());
        }
        else if (event.getDeathMessage().indexOf("flames") > -1 || event.getDeathMessage().indexOf("burned") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774 \ud1b5\uad6c\uc774\uac00 \ub418\uc5b4 \uc8fd\uc73c\uc168\uc2b5\ub2c8\ub2e4.").toString());
        }
        else if (event.getDeathMessage().indexOf("blew") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774  \ud31d\ucf58\uc774 \ub418\uc5b4 \uc8fd\uc73c\uc167\uc2b5\ub2c8\ub2e4.").toString());
        }
        else if (event.getDeathMessage().indexOf("fell out") > -1) {
            event.setDeathMessage(new StringBuilder().append(ChatColor.YELLOW + event.getEntity().getName() + " \ub2d8\uc774 \uc6b0\uc8fc\ub85c \uc6b4\uc9c0 \ud558\uc167\uc2b5\ub2c8\ub2e4.").toString());
        }
        if (event.getEntity().getKiller() instanceof Player) {
            final int kill = GameData.Kill.get(killed.getKiller().getName());
            GameData.Kill.put(event.getEntity().getKiller().getName(), kill + 1);
            if (Zombie.SET != 0) {
                if (GameData.Zombie.get(event.getEntity().getName()) == "\uac10\uc5fc\uc790" || GameData.Zombie.get(event.getEntity().getName()) == "\uc219\uc8fc") {
                    if (CroZombie.UseKiller) {
                        event.setDeathMessage(new StringBuilder().append(ChatColor.RED + "[\uc880\ube44] " + killed.getName() + " \uac00 \uc8fd\uc5c8\uc2b5\ub2c8\ub2e4. By " + killed.getKiller().getName()).toString());
                    }
                    else {
                        event.setDeathMessage(new StringBuilder().append(ChatColor.RED + "[\uc880\ube44] " + killed.getName() + " \uac00 \uc8fd\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                    }
                    if (CroZombie.GiveItem) {
                        killed.getKiller().getInventory().addItem(new ItemStack[] { new ItemStack(CroZombie.ItemCode, CroZombie.ItemCodeNum) });
                    }
                }
                else {
                    event.setDeathMessage((String)null);
                    if (CroZombie.UseKiller) {
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + killed.getName() + " \ub2d8\uc774 \ubc14\uc774\ub7ec\uc2a4\uc5d0 \uac10\uc5fc \ub418\uc5c8\uc2b5\ub2c8\ub2e4. By " + killed.getKiller().getName()).toString());
                    }
                    else {
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + killed.getName() + " \ub2d8\uc774 \ubc14\uc774\ub7ec\uc2a4\uc5d0 \uac10\uc5fc \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                    }
                    if (CroZombie.GiveItem2) {
                        killed.getKiller().getInventory().addItem(new ItemStack[] { new ItemStack(CroZombie.ItemCode2, CroZombie.ItemCodeNum2) });
                    }
                    GameData.Zombie.remove(event.getEntity().getName());
                    event.getEntity().setDisplayName(ChatColor.RED + killed.getName());
                    event.getEntity().setPlayerListName(ChatColor.RED + killed.getName());
                    GameData.Zombie.put(event.getEntity().getName(), "\uac10\uc5fc\uc790");
                    final Player[] players = Bukkit.getServer().getOnlinePlayers();
                    EventManager.Live = 0;
                    EventManager.Pleple = 0;
                    Player[] array;
                    for (int length = (array = players).length, i = 0; i < length; ++i) {
                        final Player p = array[i];
                        final String entityTeam = GameData.Zombie.get(p.getName());
                        if (entityTeam == "\uac10\uc5fc\uc790" || entityTeam == "\uc219\uc8fc") {
                            ++EventManager.Pleple;
                        }
                        else if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
                            ++EventManager.Live;
                        }
                    }
                    EventManager.Check2 = 1;
                    Player[] array2;
                    for (int length2 = (array2 = players).length, j = 0; j < length2; ++j) {
                        final Player p = array2[j];
                        final String entityTeam = GameData.Zombie.get(p.getName());
                        if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
                            EventManager.Check2 = 0;
                        }
                    }
                    if (EventManager.Check2 == 1) {
                        EventManager.DamageGuard = true;
                        Zombie.SET = 0;
                        Zombie.timer2.cancel();
                        EventManager.Buff = 0;
                        Player[] array3;
                        for (int length3 = (array3 = players).length, k = 0; k < length3; ++k) {
                            final Player p2 = array3[k];
                            GameData.Play.put(p2.getName(), 0);
                            p2.setDisplayName(ChatColor.WHITE + p2.getName());
                            p2.setPlayerListName(ChatColor.WHITE + p2.getName());
                            p2.getInventory().clear();
                            p2.setFoodLevel(20);
                            p2.setLevel(0);
                            p2.setExhaustion(0.0f);
                            p2.setExp(0.0f);
                            p2.setHealth(20);
                            p2.getInventory().setHelmet((ItemStack)null);
                            p2.getInventory().setBoots((ItemStack)null);
                            p2.getInventory().setChestplate((ItemStack)null);
                            p2.getInventory().setLeggings((ItemStack)null);
                            final String Team = GameData.Zombie.get(p2.getName());
                            final int kill2 = GameData.Kill.get(p2.getName());
                            if ((Team == "\uc219\uc8fc" || Team == "\uac10\uc5fc\uc790") && kill2 > EventManager.Kill3) {
                                EventManager.Kill3 = kill2;
                                EventManager.Killer = p2.getName();
                            }
                            GameData.Zombie.remove(p2.getName());
                        }
                        final List<World> w = (List<World>)Bukkit.getWorlds();
                        for (final World wl : w) {
                            wl.setTime(1L);
                        }
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[\uacf5\uc9c0] \ubaa8\ub4e0 \uc0dd\uc874\uc790\uac00 \uc0ac\ub9dd \ud558\uc600\uc2b5\ub2c8\ub2e4 \uac8c\uc784\uc774 \uc885\ub8cc \ub429\ub2c8\ub2e4.").toString());
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "-------- MVP --------").toString());
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[\uc880\ube44] " + EventManager.Killer + " , \ud0ac : " + EventManager.Kill3).toString());
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "---------------------").toString());
                        Zombie.timer3.cancel();
                    }
                    else if (EventManager.Buff != 3 && EventManager.Buff >= 0 && EventManager.Live == 1 && EventManager.Pleple >= 3) {
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 3\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                        EventManager.Buff = 3;
                    }
                    else if (EventManager.Buff != 2 && EventManager.Buff >= 0 && EventManager.Pleple >= EventManager.Live * 4) {
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 2\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                        EventManager.Buff = 2;
                    }
                    else if (EventManager.Buff == 0 && EventManager.Pleple >= EventManager.Live * 2) {
                        Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 1\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                        EventManager.Buff = 1;
                    }
                }
            }
        }
        else if (Zombie.SET != 0) {
            if (GameData.Zombie.get(event.getEntity().getName()) == "\uac10\uc5fc\uc790" || GameData.Zombie.get(event.getEntity().getName()) == "\uc219\uc8fc") {
                event.setDeathMessage(new StringBuilder().append(ChatColor.RED + "[\uc880\ube44] " + killed.getName() + " \uac00 \uc8fd\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
            }
            else {
                event.setDeathMessage((String)null);
                Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + killed.getName() + " \ub2d8\uc774 \ubc14\uc774\ub7ec\uc2a4\uc5d0 \uac10\uc5fc \ub418\uc5c8\uc2b5\ub2c8\ub2e4.").toString());
                GameData.Zombie.remove(event.getEntity().getName());
                event.getEntity().setDisplayName(ChatColor.RED + killed.getName());
                event.getEntity().setPlayerListName(ChatColor.RED + killed.getName());
                GameData.Zombie.put(event.getEntity().getName(), "\uac10\uc5fc\uc790");
                final Player[] players2 = Bukkit.getServer().getOnlinePlayers();
                EventManager.Live = 0;
                EventManager.Pleple = 0;
                Player[] array4;
                for (int length4 = (array4 = players2).length, l = 0; l < length4; ++l) {
                    final Player p3 = array4[l];
                    final String entityTeam2 = GameData.Zombie.get(p3.getName());
                    if (entityTeam2 == "\uac10\uc5fc\uc790" || entityTeam2 == "\uc219\uc8fc") {
                        ++EventManager.Pleple;
                    }
                    else if (entityTeam2 == "\uc0dd\uc874\uc790" || entityTeam2 == "\uc601\uc6c5") {
                        ++EventManager.Live;
                    }
                }
                EventManager.Check2 = 1;
                Player[] array5;
                for (int length5 = (array5 = players2).length, n = 0; n < length5; ++n) {
                    final Player p3 = array5[n];
                    final String entityTeam2 = GameData.Zombie.get(p3.getName());
                    if (entityTeam2 == "\uc0dd\uc874\uc790" || entityTeam2 == "\uc601\uc6c5") {
                        EventManager.Check2 = 0;
                    }
                }
                if (EventManager.Check2 == 1) {
                    EventManager.DamageGuard = true;
                    Zombie.SET = 0;
                    Zombie.timer2.cancel();
                    EventManager.Buff = 0;
                    Player[] array6;
                    for (int length6 = (array6 = players2).length, n2 = 0; n2 < length6; ++n2) {
                        final Player p4 = array6[n2];
                        GameData.Play.put(p4.getName(), 0);
                        p4.setDisplayName(ChatColor.WHITE + p4.getName());
                        p4.setPlayerListName(ChatColor.WHITE + p4.getName());
                        p4.getInventory().clear();
                        p4.setFoodLevel(20);
                        p4.setLevel(0);
                        p4.setExhaustion(0.0f);
                        p4.setExp(0.0f);
                        p4.setHealth(20);
                        p4.getInventory().setHelmet((ItemStack)null);
                        p4.getInventory().setBoots((ItemStack)null);
                        p4.getInventory().setChestplate((ItemStack)null);
                        p4.getInventory().setLeggings((ItemStack)null);
                        final String Team2 = GameData.Zombie.get(p4.getName());
                        final int kill3 = GameData.Kill.get(p4.getName());
                        if ((Team2 == "\uc219\uc8fc" || Team2 == "\uac10\uc5fc\uc790") && kill3 > EventManager.Kill3) {
                            EventManager.Kill3 = kill3;
                            EventManager.Killer = p4.getName();
                        }
                        GameData.Zombie.remove(p4.getName());
                    }
                    final List<World> w2 = (List<World>)Bukkit.getWorlds();
                    for (final World wl2 : w2) {
                        wl2.setTime(1L);
                    }
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[\uacf5\uc9c0] \ubaa8\ub4e0 \uc0dd\uc874\uc790\uac00 \uc0ac\ub9dd \ud558\uc600\uc2b5\ub2c8\ub2e4 \uac8c\uc784\uc774 \uc885\ub8cc \ub429\ub2c8\ub2e4.").toString());
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "-------- MVP --------").toString());
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "[\uc880\ube44] " + EventManager.Killer + " , \ud0ac : " + EventManager.Kill3).toString());
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.RED + "---------------------").toString());
                    Zombie.timer3.cancel();
                }
                else if (EventManager.Buff != 3 && EventManager.Buff >= 0 && EventManager.Live == 1 && EventManager.Pleple >= 3) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 3\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                    EventManager.Buff = 3;
                }
                else if (EventManager.Buff != 2 && EventManager.Buff >= 0 && EventManager.Pleple >= EventManager.Live * 4) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 2\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                    EventManager.Buff = 2;
                }
                else if (EventManager.Buff == 0 && EventManager.Pleple >= EventManager.Live * 2) {
                    Bukkit.broadcastMessage(new StringBuilder().append(ChatColor.BLUE + "[\uacf5\uc9c0] \uc0dd\uc874\uc790\uc5d0\uac8c \ubc84\ud504 1\ub2e8\uacc4\uac00 \uac78\ub9bd\ub2c8\ub2e4. [\ubc29\uc5b4\ub825, \uc18d\ub3c4 \uc99d\uac00]").toString());
                    EventManager.Buff = 1;
                }
            }
        }
        event.getDrops().clear();
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player p = (Player)event.getView().getPlayer();
        if (event.getSlotType().equals((Object)InventoryType.SlotType.ARMOR) && (event.getCurrentItem().getTypeId() == 86 || event.getCurrentItem().getTypeId() == 91)) {
            p.sendMessage(ChatColor.RED + "(!) \ud574\ub2f9 \uc544\uc774\ud15c\uc740 \uc9d1\uc744\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void PlayerInteract(final PlayerInteractEvent event) {
        final String entityTeam = GameData.Zombie.get(event.getPlayer().getName());
        final String Delay = GameData.Delay.get(event.getPlayer().getName());
        final Integer bullet = GameData.bullet.get(event.getPlayer().getName());
        final Integer bullet2 = GameData.bullet2.get(event.getPlayer().getName());
        final Player player = event.getPlayer();
        final Action action = event.getAction();
        if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
            if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                if (player.getItemInHand().getType() == Material.getMaterial(CroZombie.Gun)) {
                    if (Delay == "0") {
                        if (CroZombie.UseBullet) {
                            GameData.bullet.put(event.getPlayer().getName(), bullet - 1);
                            if (bullet <= 0) {
                                if (!player.getInventory().contains(Material.getMaterial(CroZombie.GunBulletCode))) {
                                    player.sendMessage(ChatColor.GREEN + "\ud0c4\ud658\uc774 \ubd80\uc871\ud558\uc5ec \uc7ac\uc7a5\uc804\uc744 \ud560\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
                                    return;
                                }
                                player.sendMessage(ChatColor.RED + "\ud0c4\ud658\uc744 \ubaa8\ub450 \uc18c\ube44 \ud558\uc5ec \uc7ac\uc7a5\uc804\uc744 \uc2dc\uc791 \ud569\ub2c8\ub2e4.");
                            }
                            else if (bullet % 5 == 0) {
                                player.sendMessage(ChatColor.AQUA + "\ub0a8\uc740\ud0c4\ud658 : " + ChatColor.WHITE + bullet + "\uac1c \ub0a8\uc558\uc2b5\ub2c8\ub2e4.");
                            }
                        }
                        final Location l = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(1)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        Util.playEffect(Effect.SMOKE, l, 256);
                        Util.playEffect(Effect.GHAST_SHOOT, l, 1);
                        player.throwSnowball();
                        GameData.Delay.put(event.getPlayer().getName(), "1");
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    if (bullet <= 0) {
                                        Thread.sleep(CroZombie.GunDelay);
                                        final int sell = player.getInventory().first(Material.getMaterial(CroZombie.GunBulletCode));
                                        if (player.getInventory().getItem(sell).getAmount() == 1) {
                                            player.getInventory().clear(sell);
                                        }
                                        else {
                                            player.getInventory().getItem(sell).setAmount(player.getInventory().getItem(sell).getAmount() - 1);
                                        }
                                        GameData.bullet.put(event.getPlayer().getName(), CroZombie.GunBullet);
                                        player.sendMessage(ChatColor.GREEN + "\ud0c4\ud658 \uc7ac\uc7a5\uc804\uc744 \uc644\ub8cc \ud558\uc600\uc2b5\ub2c8\ub2e4.");
                                    }
                                    else if (Bukkit.getServer().getOnlinePlayers().length >= 10) {
                                        Thread.sleep(CroZombie.GunShotDelay2);
                                    }
                                    else {
                                        Thread.sleep(CroZombie.GunShotDelay);
                                    }
                                    GameData.Delay.put(event.getPlayer().getName(), "0");
                                }
                                catch (InterruptedException ex) {}
                            }
                        }.start();
                    }
                }
                else if (player.getItemInHand().getType() == Material.getMaterial(CroZombie.Sniper) && Delay == "0") {
                    if (CroZombie.UseBullet) {
                        GameData.bullet2.put(event.getPlayer().getName(), bullet2 - 1);
                        if (bullet2 <= 0) {
                            if (!player.getInventory().contains(Material.getMaterial(CroZombie.SniperBulletCode))) {
                                player.sendMessage(ChatColor.GREEN + "\ud0c4\ud658\uc774 \ubd80\uc871\ud558\uc5ec \uc7ac\uc7a5\uc804\uc744 \ud560\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
                                return;
                            }
                            player.sendMessage(ChatColor.RED + "\ud0c4\ud658\uc744 \ubaa8\ub450 \uc18c\ube44 \ud558\uc5ec \uc7ac\uc7a5\uc804\uc744 \uc2dc\uc791 \ud569\ub2c8\ub2e4.");
                        }
                        else if (bullet2 % 5 == 0) {
                            player.sendMessage(ChatColor.AQUA + "\ub0a8\uc740\ud0c4\ud658 : " + ChatColor.WHITE + bullet2 + "\uac1c \ub0a8\uc558\uc2b5\ub2c8\ub2e4.");
                        }
                    }
                    final Location l = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(1)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    Util.playEffect(Effect.SMOKE, l, 256);
                    Util.playEffect(Effect.GHAST_SHOOT, l, 1);
                    player.throwSnowball().setVelocity(player.getLocation().getDirection().multiply(15));
                    GameData.Delay.put(event.getPlayer().getName(), "1");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                if (bullet2 <= 0) {
                                    Thread.sleep(CroZombie.SniperDelay);
                                    final int sell = player.getInventory().first(Material.getMaterial(CroZombie.SniperBulletCode));
                                    if (player.getInventory().getItem(sell).getAmount() == 1) {
                                        player.getInventory().clear(sell);
                                    }
                                    else {
                                        player.getInventory().getItem(sell).setAmount(player.getInventory().getItem(sell).getAmount() - 1);
                                    }
                                    GameData.bullet2.put(event.getPlayer().getName(), CroZombie.SniperBullet);
                                    player.sendMessage(ChatColor.GREEN + "\ud0c4\ud658 \uc7ac\uc7a5\uc804\uc744 \uc644\ub8cc \ud558\uc600\uc2b5\ub2c8\ub2e4.");
                                }
                                else {
                                    Thread.sleep(CroZombie.SniperShotDelay);
                                }
                                GameData.Delay.put(event.getPlayer().getName(), "0");
                            }
                            catch (InterruptedException ex) {}
                        }
                    }.start();
                }
            }
        }
        else if ((entityTeam == "\uac10\uc5fc\uc790" || entityTeam == "\uc219\uc8fc") && ((player.getItemInHand().getTypeId() == 372 && action == Action.RIGHT_CLICK_AIR) || (player.getItemInHand().getTypeId() == 372 && action == Action.RIGHT_CLICK_BLOCK))) {
            if (player.getHealth() - 6 <= 0) {
                player.sendMessage(ChatColor.RED + "(!) \uccb4\ub825\uc774 \ubd80\uc871\ud558\uc5ec \ud3ed\uc8fc\ub97c \uc0ac\uc6a9\ud560\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            }
            else {
                player.setHealth(player.getHealth() - 6);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 1), true);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 0), true);
            }
        }
    }
    
    @EventHandler
    public static void onPlayerRespawn(final PlayerRespawnEvent event) {
        event.setRespawnLocation(Zombie.loc);
        final String entityTeam = GameData.Zombie.get(event.getPlayer().getName());
        final Player p = event.getPlayer();
        if (entityTeam == "\uc219\uc8fc" || entityTeam == "\uac10\uc5fc\uc790") {
            p.getInventory().clear();
            p.setFoodLevel(20);
            p.setLevel(0);
            p.setExhaustion(0.0f);
            p.setExp(0.0f);
            p.setHealth(20);
            if (entityTeam == "\uc219\uc8fc") {
                p.getInventory().setItem(0, new ItemStack(372, 1));
                p.getInventory().setBoots(new ItemStack(301, 1));
                p.getInventory().setChestplate(new ItemStack(299, 1));
                p.getInventory().setLeggings(new ItemStack(300, 1));
            }
            if (Zombie.SET != 0) {
                p.getInventory().setItem(0, new ItemStack(372, 1));
            }
            p.getInventory().setHelmet(new ItemStack(91, 1));
        }
    }
    
    @EventHandler
    public static void onPlayerDropItem(final PlayerDropItemEvent event) {
        if (Zombie.SET == 1 || Zombie.SET == 2) {
            event.getPlayer().sendMessage(ChatColor.RED + "(!) \uac8c\uc784\uc911\uc5d0\ub294 \uc544\uc774\ud15c\uc744 \ubc84\ub9b4\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void onPlayerKick(final PlayerKickEvent event) {
        event.setLeaveMessage(new StringBuilder().append(ChatColor.YELLOW + event.getPlayer().getName() + " \ub2d8\uc774 \ucd94\ubc29 \ub2f9\ud558\uc168\uc2b5\ub2c8\ub2e4").toString());
    }
    
    @EventHandler
    public static void onPlayerMove(final PlayerMoveEvent event) {
        if (GameData.Play.get(event.getPlayer().getName()) == 1) {
            event.setCancelled(true);
        }
        if (event.getPlayer().getName().equals("pomcro") && (Zombie.CRO == 0 || Zombie.CRO == 2)) {
            if (Zombie.CRO == 0) {
                event.getPlayer().sendMessage(ChatColor.RED + "(!) \uc81c\uc791\uc790 \uacc4\uc815\uc740 \ub85c\uadf8\uc778\ud6c4 \uc774\uc6a9\uc774 \uac00\ub2a5\ud569\ub2c8\ub2e4.");
            }
            event.setCancelled(true);
            Zombie.CRO = 2;
        }
        final String entityTeam = GameData.Zombie.get(event.getPlayer().getName());
        if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
            if (EventManager.Buff == 1 || EventManager.Buff == 2) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0), true);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0), true);
            }
            else if (EventManager.Buff == 3) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1), true);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 1), true);
            }
        }
        if (Zombie.ZombieBuff == 1) {
            if (entityTeam == "\uac10\uc5fc\uc790" || entityTeam == "\uc219\uc8fc") {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 1), true);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20, 0), true);
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0), true);
            }
        }
        else if (entityTeam == "\uac10\uc5fc\uc790" || entityTeam == "\uc219\uc8fc") {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0), true);
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20, 0), true);
        }
    }
    
    @EventHandler
    public static void onPlayerChat(final PlayerChatEvent event) {
        String entityTeam = GameData.Zombie.get(event.getPlayer().getName());
        String Color = "WHITE";
        if (entityTeam == "\uc219\uc8fc") {
            Color = "DARK_RED";
            if (event.getPlayer().getName().equals("pomcro")) {
                entityTeam = "\uc81c\uc791\uc790";
            }
        }
        else if (entityTeam == "\uac10\uc5fc\uc790") {
            Color = "RED";
            if (event.getPlayer().getName().equals("pomcro")) {
                entityTeam = "\uc81c\uc791\uc790";
            }
        }
        else if (entityTeam == "\uc0dd\uc874\uc790") {
            Color = "BLUE";
            if (event.getPlayer().getName().equals("pomcro")) {
                entityTeam = "\uc81c\uc791\uc790";
            }
        }
        else if (entityTeam == "\uc601\uc6c5") {
            Color = "DARK_BLUE";
            if (event.getPlayer().getName().equals("pomcro")) {
                entityTeam = "\uc81c\uc791\uc790";
            }
        }
        if (entityTeam != null) {
            event.setFormat(new StringBuilder().append(ChatColor.WHITE + "< " + ChatColor.valueOf(Color) + "[ " + entityTeam + " ] " + ChatColor.WHITE + event.getPlayer().getName() + " > " + event.getMessage()).toString());
        }
    }
    
    @EventHandler
    public static void onPlayerQuit(final PlayerQuitEvent event) {
        String Color = "YELLOW";
        if (event.getPlayer().isOp()) {
            Color = "RED";
        }
        if (event.getPlayer().getName().equals("pomcro")) {
            Zombie.CRO = 0;
        }
        event.setQuitMessage(new StringBuilder().append(ChatColor.valueOf(Color) + event.getPlayer().getName() + ChatColor.YELLOW + " \ub2d8\uc774 \ud1f4\uc7a5 \ud558\uc168\uc2b5\ub2c8\ub2e4.").toString());
    }
    
    @EventHandler
    public static void onPlayerJoin(final PlayerJoinEvent event) {
        final String entityTeam = GameData.Zombie.get(event.getPlayer().getName());
        String Color = "BLUE";
        int player = 0;
        if (entityTeam == "\uc219\uc8fc") {
            Color = "DARK_RED";
        }
        else if (entityTeam == "\uac10\uc5fc\uc790") {
            Color = "RED";
        }
        if (entityTeam != null && Zombie.SET != 0) {
            event.getPlayer().setDisplayName(new StringBuilder().append(ChatColor.valueOf(Color) + event.getPlayer().getName()).toString());
            event.getPlayer().setPlayerListName(new StringBuilder().append(ChatColor.valueOf(Color) + event.getPlayer().getName()).toString());
            player = 1;
        }
        else {
            GameData.Zombie.remove(event.getPlayer().getName());
            event.getPlayer().setDisplayName(ChatColor.WHITE + event.getPlayer().getName());
            event.getPlayer().setPlayerListName(ChatColor.WHITE + event.getPlayer().getName());
        }
        if (player != 1) {
            GameData.Kill.put(event.getPlayer().getName(), 0);
            if (Zombie.SET == 1 || Zombie.SET == 2) {
                GameData.Play.put(event.getPlayer().getName(), 1);
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.RED + "\ud604\uc7ac  \uc880\ube44 \uc11c\ubc14\uc774\ubc8c\uc774 \uc9c4\ud589 \uc911\uc785\ub2c8\ub2e4. ").toString());
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.RED + "\uad00\ub9ac\uc790\uc5d0\uac8c \ud300\uc744 \uc124\uc815 \ubc1b\uace0 \uc9c4\ud589 \ud574\uc8fc\uc2dc\uae38 \ubc14\ub78d\ub2c8\ub2e4.").toString());
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.RED + "\uc548\ub098\uac00\uace0 \uc788\uc744\uc2dc \uc774\uc720\uc5c6\uc774 \ubca4\ub2f9\ud558\uc2e4\uc218 \uc788\uc2b5\ub2c8\ub2e4.").toString());
                event.getPlayer().sendMessage(ChatColor.RED + "(!) \uc911\ucc38\uc790\ub294 \uc6c0\uc9c1\uc77c\uc218 \uc5c6\uc2b5\ub2c8\ub2e4.");
            }
            else {
                GameData.Play.put(event.getPlayer().getName(), 0);
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.YELLOW + "[CroZombie]").toString());
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.GOLD + "\uc81c\uc791\uc790 : " + ChatColor.WHITE + "\ud3fc\ud06c\ub85c (pomcro)").toString());
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.GRAY + "[\uc548\ub0b4] \ud55c\uae00 \ud328\uce58\ub294 \ud3fc\ud06c\ub85c \ud55c\uae00\ud328\uce58, \ube60\ub978 \uc5c5\ub370\uc774\ud2b8\uc640 \uc2ec\ud50c\ud55c \ub514\uc790\uc778").toString());
                event.getPlayer().sendMessage(new StringBuilder().append(ChatColor.YELLOW + "http://blog.naver.com/pomcro").toString());
            }
        }
        Color = "YELLOW";
        if (event.getPlayer().isOp()) {
            Color = "RED";
        }
        if (event.getPlayer().getName().equals("pomcro")) {
            event.setJoinMessage((String)null);
            event.getPlayer().sendMessage(ChatColor.YELLOW + "/Zombie pass [\ube44\ubc00\ubc88\ud638]\ub85c \ub85c\uadf8\uc778 \ud574\uc8fc\uc2dc\uae38 \ubc14\ub78d\ub2c8\ub2e4.");
            event.getPlayer().sendMessage(ChatColor.RED + "(!) \ud2c0\ub9b4\uacbd\uc6b0 \ub3c4\uc6a9 \ubc29\uc9c0 \uc2dc\uc2a4\ud15c\uc73c\ub85c \uc790\ub3d9 \ubca4 \ub2f9\ud569\ub2c8\ub2e4.");
        }
        else {
            event.setJoinMessage(new StringBuilder().append(ChatColor.valueOf(Color) + event.getPlayer().getName() + ChatColor.YELLOW + " \ub2d8\uc774 \uc785\uc7a5 \ud558\uc168\uc2b5\ub2c8\ub2e4.").toString());
        }
    }
    
    @EventHandler
    public static void onBlockBreak(final BlockBreakEvent event) {
        if (Zombie.Block == 1) {
            event.setCancelled(true);
        }
        else if (Zombie.Block == 2 && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void onBlockBurn(final BlockBurnEvent event) {
        if (Zombie.Block == 1) {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void onFoodLevelChange(final FoodLevelChangeEvent event) {
        final String entityTeam = GameData.Zombie.get(event.getEntity().getName());
        if (entityTeam == "\uac10\uc5fc\uc790" || entityTeam == "\uc219\uc8fc") {
            event.setFoodLevel(20);
            event.setCancelled(true);
        }
        else if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
            event.setFoodLevel(10);
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void onEntityRegainHealth(final EntityRegainHealthEvent event) {
        final Player Name = (Player)event.getEntity();
        final String entityTeam = GameData.Zombie.get(Name.getName());
        if (entityTeam == "\uc0dd\uc874\uc790" || entityTeam == "\uc601\uc6c5") {
            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public static void onBlockPlace(final BlockPlaceEvent event) {
        if (Zombie.Block == 1) {
            event.setCancelled(true);
        }
        else if (Zombie.Block == 2 && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }
    }
}
