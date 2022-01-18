// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MainModule;

import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import Physical.Fighters.Script.MainScripter;
import org.bukkit.event.entity.EntityDeathEvent;
import Physical.Fighters.MajorModule.AbilityList;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Event;
import Physical.Fighters.PhysicalFighters;
import org.bukkit.event.entity.EntityDamageEvent;
import Physical.Fighters.MinerModule.EventData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.ArrayList;
import org.bukkit.event.Listener;

public class EventManager implements Listener
{
    static int diemes;
    public static ArrayList<AbilityBase> LeftHandEvent;
    public static ArrayList<AbilityBase> RightHandEvent;
    public static boolean DamageGuard;
    public static HashMap<Player, ItemStack[]> invsave;
    public static HashMap<Player, ItemStack[]> arsave;
    public static ArrayList<EventData> onEntityDamage;
    public static ArrayList<EventData> onEntityDamageByEntity;
    public static ArrayList<EventData> onEntityTarget;
    public static ArrayList<EventData> onFoodLevelChange;
    public static ArrayList<EventData> onEntityRegainHealth;
    public static ArrayList<EventData> onPlayerDropItem;
    public static ArrayList<EventData> onPlayerRespawn;
    public static ArrayList<EventData> onEntityDeath;
    public static ArrayList<EventData> onPlayerInteract;
    public static ArrayList<EventData> onPlayerMoveEvent;
    public static ArrayList<EventData> onProjectileHitEvent;
    public static ArrayList<EventData> onProjectileLaunchEvent;
    public static ArrayList<EventData> onBlockPlaceEvent;
    public static ArrayList<EventData> onSignChangeEvent;
    public static PhysicalFighters M;
    
    static {
        EventManager.diemes = 0;
        EventManager.LeftHandEvent = new ArrayList<AbilityBase>();
        EventManager.RightHandEvent = new ArrayList<AbilityBase>();
        EventManager.DamageGuard = false;
        EventManager.invsave = new HashMap<Player, ItemStack[]>();
        EventManager.arsave = new HashMap<Player, ItemStack[]>();
        EventManager.onEntityDamage = new ArrayList<EventData>();
        EventManager.onEntityDamageByEntity = new ArrayList<EventData>();
        EventManager.onEntityTarget = new ArrayList<EventData>();
        EventManager.onFoodLevelChange = new ArrayList<EventData>();
        EventManager.onEntityRegainHealth = new ArrayList<EventData>();
        EventManager.onPlayerDropItem = new ArrayList<EventData>();
        EventManager.onPlayerRespawn = new ArrayList<EventData>();
        EventManager.onEntityDeath = new ArrayList<EventData>();
        EventManager.onPlayerInteract = new ArrayList<EventData>();
        EventManager.onPlayerMoveEvent = new ArrayList<EventData>();
        EventManager.onProjectileHitEvent = new ArrayList<EventData>();
        EventManager.onProjectileLaunchEvent = new ArrayList<EventData>();
        EventManager.onBlockPlaceEvent = new ArrayList<EventData>();
        EventManager.onSignChangeEvent = new ArrayList<EventData>();
    }
    
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
    	p.getInventory().remove(Material.GOLD_INGOT);
    	p.getInventory().remove(Material.IRON_INGOT);
		if (!MainScripter.ExceptionList.contains(p)) MainScripter.ExceptionList.add(p);
		if (p.getWorld().getName().equalsIgnoreCase("world_pvp")) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "spawn " + p.getName());
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (MainScripter.PlayerList.contains(p)) {
			p.getInventory().remove(Material.GOLD_INGOT);
			p.getInventory().remove(Material.IRON_INGOT);
			p.getInventory().remove(Material.SNOW_BALL);
			p.getInventory().remove(Material.BONE);
			p.getInventory().remove(Material.FEATHER);
		  	p.getInventory().remove(Material.DIAMOND);
         	p.getInventory().remove(Material.FISHING_ROD);
        	p.getInventory().remove(Material.RAW_FISH);
			p.setFireTicks(0);
			p.setHealth(p.getMaxHealth());
			for (PotionEffect effect : p.getActivePotionEffects())
				p.removePotionEffect(effect.getType());
			if (MainScripter.PlayerList.contains(p)) MainScripter.PlayerList.remove(p);
			for (Player player : MainScripter.PlayerList)
				player.sendMessage(PhysicalFighters.p + "§c" + p.getName() + "§6 님이 게임중 §c퇴장§6하였습니다.");
			if (MainScripter.PlayerList.size() <= 1) {
				MainScripter.vacstop("§c적정 인원§6이 맞지 않아 ");
			}
		}
	}
    
    @EventHandler
    public static void onEntityDamage(final EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (EventManager.DamageGuard && ((Player) event.getEntity()).getWorld().getName().equalsIgnoreCase("world_pvp")) {
                event.setCancelled(true);
                event.getEntity().setFireTicks(0);
            }
            if (PhysicalFighters.InfinityDur) {
                final Player p = (Player)event.getEntity();
                final PlayerInventory inv = p.getInventory();
                if (inv.getChestplate() != null) {
                    inv.getChestplate().setDurability((short)0);
                }
                if (inv.getHelmet() != null) {
                    inv.getHelmet().setDurability((short)0);
                }
                if (inv.getLeggings() != null) {
                    inv.getLeggings().setDurability((short)0);
                }
                if (inv.getBoots() != null) {
                    inv.getBoots().setDurability((short)0);
                }
            }
        }
        AbilityExcuter(EventManager.onEntityDamage, (Event)event);
        if (event instanceof EntityDamageByEntityEvent) {
            AbilityExcuter(EventManager.onEntityDamageByEntity, (Event)event);
        }
    }
    
    @EventHandler
    public static void onEntityTarget(final EntityTargetEvent event) {
        AbilityExcuter(EventManager.onEntityTarget, (Event)event);
    }
    
    @EventHandler
    public static void onFoodLevelChange(final FoodLevelChangeEvent event) {
        if (PhysicalFighters.NoFoodMode && ((Player) event.getEntity()).getWorld().getName().equalsIgnoreCase("world_pvp")) {
            event.setFoodLevel(20);
            event.setCancelled(true);
            return;
        }
        AbilityExcuter(EventManager.onFoodLevelChange, (Event)event);
    }
    
    @EventHandler
    public static void onEntityRegainHealth(final EntityRegainHealthEvent event) {
        AbilityExcuter(EventManager.onEntityRegainHealth, (Event)event);
    }
    
    @EventHandler
    public static void onPlayerDropItem(final PlayerDropItemEvent event) {
        AbilityExcuter(EventManager.onPlayerDropItem, (Event)event);
    }
    
    @EventHandler
    public static void onPlayerRespawn(final PlayerRespawnEvent event) {
		for (PotionEffect effect : event.getPlayer().getActivePotionEffects())
			event.getPlayer().removePotionEffect(effect.getType());
        if (PhysicalFighters.InventorySave && !AbilityList.phoenix.PlayerCheck(event.getPlayer())) {
            final ItemStack[] ar = EventManager.arsave.get(event.getPlayer());
            final ItemStack[] inv = EventManager.invsave.get(event.getPlayer());
            if (ar != null) {
                event.getPlayer().getInventory().setArmorContents(ar);
            }
            if (inv != null) {
                event.getPlayer().getInventory().setContents(inv);
            }
            EventManager.arsave.remove(event.getPlayer());
            EventManager.invsave.remove(event.getPlayer());
        }
        AbilityExcuter(EventManager.onPlayerRespawn, (Event)event);
    }
    
    @EventHandler
    public static void onEntityDeath(final EntityDeathEvent event) {
        AbilityExcuter(EventManager.onEntityDeath, (Event)event);
        if (MainScripter.Scenario == MainScripter.ScriptStatus.GameStart && event instanceof PlayerDeathEvent
         && event.getEntity().getKiller() instanceof Player && event.getEntity() instanceof Player) {
            final PlayerDeathEvent pde = (PlayerDeathEvent)event;
            final Player killed = (Player)event.getEntity();
            final Player killerP = killed.getKiller();
            
            if (killed.getWorld().getName().equals("world_pvp") && killerP.getWorld().getName().equals("world_pvp")) {
            	killed.getInventory().remove(Material.GOLD_INGOT);
            	killed.getInventory().remove(Material.IRON_INGOT);
    			killed.getInventory().remove(Material.SNOW_BALL);
    			killed.getInventory().remove(Material.BONE);
    			killed.getInventory().remove(Material.FEATHER);
    		  	killed.getInventory().remove(Material.DIAMOND);
    		  	killed.getInventory().remove(Material.FISHING_ROD);
    		  	killed.getInventory().remove(Material.RAW_FISH);
    			killed.setFireTicks(0);
    			for (PotionEffect effect : killed.getActivePotionEffects())
    				killed.removePotionEffect(effect.getType());
                
                if (event.getEntity().getKiller() instanceof Player) {
                	
                    PhysicalFighters.log.info(pde.getDeathMessage());
                    if (PhysicalFighters.KillerOutput) {
                        pde.setDeathMessage(null);
                        for (Player player : MainScripter.PlayerList)
                        	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 §c%s §6님의 '살겠다는'의지를 꺾었습니다.", killerP.getName(), killed.getName()));
                        ++EventManager.diemes;
                        if (MainScripter.PlayerList.contains(killed)) MainScripter.PlayerList.remove(killed);
                    } else {
                        pde.setDeathMessage(null);
                        for (Player player : MainScripter.PlayerList)
                        	player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 누군가에게 §c살해§6당했습니다.", killed.getName()));
                    }
                    
                    if (MainScripter.PlayerList.size() <= 1) {
                    	MainScripter.vawstop(killerP);
                    }
                }
            }
        }
        
        else if (MainScripter.Scenario == MainScripter.ScriptStatus.GameStart && event instanceof PlayerDeathEvent && event.getEntity() instanceof Player) {
            final PlayerDeathEvent pde = (PlayerDeathEvent)event;
            final Player killed = (Player)event.getEntity();
            
            if (killed.getWorld().getName().equals("world_pvp")) {
            	killed.getInventory().remove(Material.GOLD_INGOT);
            	killed.getInventory().remove(Material.IRON_INGOT);
                
            	PhysicalFighters.log.info(pde.getDeathMessage());
            	if (PhysicalFighters.KillerOutput) {
            		pde.setDeathMessage(null);
            		for (Player player : MainScripter.PlayerList)
            			player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 의문사 하셨습니다.", killed.getName()));
            		++EventManager.diemes;
            		if (MainScripter.PlayerList.contains(killed)) MainScripter.PlayerList.remove(killed);
            	} else {
            		pde.setDeathMessage(null);
            		for (Player player : MainScripter.PlayerList)
            			player.sendMessage(String.format(PhysicalFighters.p + "§c%s §6님이 의문사 하셨습니다.", killed.getName()));
            	}
            	
            	if (MainScripter.PlayerList.size() <= 1) {
            		MainScripter.vawstop(MainScripter.PlayerList.get(0));
            	}
            }
        }
    }
    
    @EventHandler
    public static void onPlayerInteract(final PlayerInteractEvent event) {
        _AbilityEventFilter(event);
        if (PhysicalFighters.InfinityDur && event.getPlayer().getWorld().getName().equalsIgnoreCase("world_pvp")) {
            event.getPlayer().getItemInHand().setDurability((short)0);
        }
        AbilityExcuter(EventManager.onPlayerInteract, (Event)event);
    }
    
    @EventHandler
    public static void onPlayerMove(final PlayerMoveEvent event) {
        AbilityExcuter(EventManager.onPlayerMoveEvent, (Event)event);
    }
    
    @EventHandler
    public static void onProjectileHit(final ProjectileHitEvent event) {
        AbilityExcuter(EventManager.onProjectileHitEvent, (Event)event);
    }
    
    @EventHandler
    public static void onProjectileLaunchEvent(final ProjectileLaunchEvent event) {
        AbilityExcuter(EventManager.onProjectileLaunchEvent, (Event)event);
    }
    
    @EventHandler
    public static void onBlockPlaceEvent(final BlockPlaceEvent event) {
        AbilityExcuter(EventManager.onBlockPlaceEvent, (Event)event);
    }
    
    @EventHandler
    public static void SignChangeEvent(final SignChangeEvent event) {
        AbilityExcuter(EventManager.onSignChangeEvent, (Event)event);
    }
    
	@EventHandler
	public void onPlayerCommnadPreprocess(PlayerCommandPreprocessEvent e)
	{
		String[] a = e.getMessage().split(" ");
		if (e.getPlayer().getWorld().getName().equalsIgnoreCase("world_pvp")) {
			if (!e.getPlayer().isOp()) {
				if (!(a[0].equalsIgnoreCase("/도움말")) && !(a[0].equalsIgnoreCase("/메뉴")) && !(a[0].equalsIgnoreCase("/list"))
						&& !(a[0].equalsIgnoreCase("/카페주소")) && !(a[0].equalsIgnoreCase("/명령어"))
						&& !(a[0].equalsIgnoreCase("/좀비")) && !(a[0].equalsIgnoreCase("/펫")) && !(a[0].equalsIgnoreCase("/rpgitem"))
						&& !(a[0].equalsIgnoreCase("/색깔을변경하자")) && !(a[0].equalsIgnoreCase("/nick")) && !(a[0].equalsIgnoreCase("/색깔을복구하자"))
						&& !(a[0].equalsIgnoreCase("/상태")) && !(a[0].equalsIgnoreCase("/닉색지급")) && !(a[0].equalsIgnoreCase("/닉복지급"))
						&& !(a[0].equalsIgnoreCase("/귓")) && !(a[0].equalsIgnoreCase("/m")) && !(a[0].equalsIgnoreCase("/me"))
						&& !(a[0].equalsIgnoreCase("/w")) && !(a[0].equalsIgnoreCase("/pay")) && !(a[0].equalsIgnoreCase("/돈"))
						&& !(a[0].equalsIgnoreCase("/money")) && !(a[0].equalsIgnoreCase("/돈순위")) && !(a[0].equalsIgnoreCase("/va"))) {
					e.getPlayer().sendMessage(PhysicalFighters.a + "§c능력자 진행중에는 명령어를 입력하실 수 없습니다.");
					e.setCancelled(true);
				}
			}
		}
	}
    
    public static void AbilityExcuter(final ArrayList<EventData> ED, final Event event) {
        for (final EventData ed : ED) {
            if (ed.ab.GetAbilityType() == AbilityBase.Type.Active_Continue) {
                if (ed.ab.AbilityDuratinEffect(event, ed.parameter)) {
                    return;
                }
                continue;
            }
            else {
                if (ed.ab.AbilityExcute(event, ed.parameter)) {
                    return;
                }
                continue;
            }
        }
    }
    
    private static void _AbilityEventFilter(final PlayerInteractEvent event) {
        int i = 0;
        if (!event.getAction().equals((Object)Action.LEFT_CLICK_AIR)) {
            if (!event.getAction().equals((Object)Action.LEFT_CLICK_BLOCK)) {
                if (!event.getAction().equals((Object)Action.RIGHT_CLICK_AIR)) {
                    if (!event.getAction().equals((Object)Action.RIGHT_CLICK_BLOCK)) {
                        return;
                    }
                }
                while (i < EventManager.RightHandEvent.size() && EventManager.RightHandEvent.size() != 0) {
                    if (EventManager.RightHandEvent.get(i).AbilityExcute((Event)event, 1)) {
                        return;
                    }
                    ++i;
                }
                return;
            }
        }
        while (i < EventManager.LeftHandEvent.size()) {
            if (EventManager.LeftHandEvent.size() == 0) {
                break;
            }
            if (EventManager.LeftHandEvent.get(i).AbilityExcute((Event)event, 0)) {
                return;
            }
            ++i;
        }
    }
}
