package me.espoo.rpg2;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.espoo.option.PlayerYml;
import me.espoo.rpg.guild.GuildAPI;
import uk.org.whoami.authme.events.LoginEvent;
import uk.org.whoami.authme.events.RegisterTeleportEvent;

@SuppressWarnings("deprecation")
public class mainEvent extends JavaPlugin implements Listener {
	main main;
	
	public mainEvent(main main)
	{
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!e.getPlayer().isOp()) {
			String world = e.getTo().getWorld().getName();
			if (e.getTo().getY() < 0) {
				Player p = e.getPlayer();
				EntityDamageEvent ede = new EntityDamageEvent(p, EntityDamageEvent.DamageCause.CUSTOM, 32767);
				Bukkit.getServer().getPluginManager().callEvent(ede);

				p.damage(32767);

				if (p.getHealth() > 0)
				{
					p.setHealth(0);
				}
				
				return;
			}
			
			if (world.equalsIgnoreCase("world_dun")) {
				if (e.getTo().getY() > 105) {
					e.getPlayer().chat("/spawn");
					e.getPlayer().sendMessage("§c이 구간은 출입 금지 구간입니다.");
				}
			}
			  
			else if (world.equalsIgnoreCase("world_dun2")) {
				if (e.getTo().getY() > 100) {
					e.getPlayer().chat("/spawn");
					e.getPlayer().sendMessage("§c이 구간은 출입 금지 구간입니다.");
				}
			}
			  
			else if (world.equalsIgnoreCase("world_dun3")) {
				if (e.getTo().getY() > 112) {
					e.getPlayer().chat("/spawn");
					e.getPlayer().sendMessage("§c이 구간은 출입 금지 구간입니다.");
				}
			}  
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
	{
		if ((event.getRightClicked() instanceof Player)) {
			Player player = event.getPlayer();
			OfflinePlayer object = (OfflinePlayer) event.getRightClicked();
			if (object.getName().equalsIgnoreCase("§2NPC §7| §f포탈알바"))
				Method.sendCommand("chc open 포탈 " + player.getName());
		}
	}
	
	@EventHandler
	public void onRegister(RegisterTeleportEvent e) {
		e.setTo(new Location(Bukkit.getWorld("world"), 516.5D, 48.0D, 969.5D, -268.48062F, -1.1999996F));
	}
	
	@EventHandler
	public void onLogin(LoginEvent e) {
		Player p = e.getPlayer();
		if (Method.getInfoInt(p, "전직 카운트") == 0 && !p.isOp()) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "warp 전직선택관 " + p.getName());
		}
	}
	
	public static void OPMessage(String message) {
		for (Player op : Bukkit.getOnlinePlayers())
			if (op.isOp())
				op.sendMessage(message);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		String cmd = event.getMessage().replaceAll("/", "");
		if ((cmd.equalsIgnoreCase("tpyes") || cmd.equalsIgnoreCase("tpaccept") || cmd.equalsIgnoreCase("etpaccept"))) {
			if (p.getWorld().getName().contains("world_dun") || p.getWorld().getName().contains("world_pvp")) {
				event.setCancelled(true);
				p.sendMessage("§f[§4경고§f] §c이곳에선 텔레포트 요청을 받으실 수 없습니다. 던전이나 PVP장을 나가서 수락해주세요.");	
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;

		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (main.in.get(p.getName()) != null && i.hasItemMeta()) {
        	System.out.println(i.getItemMeta().getDisplayName());
        	addList(i.getItemMeta().getDisplayName());
        	e.setCancelled(true);
     	}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler(priority=EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e) {
		Action act = e.getAction();
		Player p = e.getPlayer();
		
		if (act == Action.RIGHT_CLICK_AIR || act == Action.RIGHT_CLICK_BLOCK) {
			ItemStack item = p.getItemInHand();
	     	if (item != null) {
	     		if (item.getTypeId() == 368 || item.getTypeId() == 332) {
	     			e.setCancelled(true);
	     		}
	     	}
		}
		
		else if (act == Action.LEFT_CLICK_BLOCK) {
	     	if (main.gp.get(p.getName()) != null) {
	     		System.out.println(e.getClickedBlock().getLocation());
	     		addList2(e.getClickedBlock().getLocation());
	     	}
		}
	}
	
	public static void addList(String name) {
		List<String> list = Config.getList("아이템 이름");
		if (list == null || list.isEmpty()) list = new ArrayList<String>();
		list.add(name);
		Config.setList("아이템 이름", list);;
	}
	
	public static void addList2(Location name) {
		List<String> list = Config.getList("블럭 좌표");
		if (list == null || list.isEmpty()) list = new ArrayList<String>();
		list.add(name.getBlockX() + "," + name.getBlockZ() + "," + name.getWorld().getName());
		Config.setList("블럭 좌표", list);;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Timer timer1 = new Timer();
		Date timeToRun = new Date(System.currentTimeMillis() + 300);
		timer1.schedule(new TimerTask() {
			public void run() {
				for (int i = 0; i < 100; i++) {
					p.sendMessage("");
				}
				p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				Message.sendCenteredMessage(p, "§6환영합니다 §e" + p.getName() + "§6님! 오늘도 즐거운 §c마인크래프트 §6하세요~");
				p.sendMessage("");
		        p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		        p.sendMessage("");
		        Message.sendCenteredMessage(p, "§6매일매일 마인리스트에서 서버를 추천해주시면 §e홍보코인 1개§6가 지급됩니다.");
		        Message.sendCenteredMessage(p, "                              §b§nhttps://minelist.kr/servers/espoo.minesv.kr");
		        Message.sendCenteredMessage(p, "              §6위의 링크에서 추천을 하시면 됩니다.");
		        p.sendMessage("");
		        p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				p.sendMessage("");
				String guild = GuildAPI.getJoinGuild(p.getName());
				
				if (guild != null) {
					if (GuildAPI.isWar(guild)) {
						String emeny = GuildAPI.getWarGuild(guild);
						
						if (GuildAPI.isSubmit(emeny)) {
							Message.sendCenteredMessage(p, "§6현재 우리 길드가 §c" + emeny + " §6길드에게 항복한 상태입니다.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
						
						else if (GuildAPI.isSubmit(guild)) {
							Message.sendCenteredMessage(p, "§6현재 상대 길드가 우리 길드에게 항복한 상태입니다.");
							if (GuildAPI.getJoinMaster(guild).equalsIgnoreCase(p.getName()))
								Message.sendCenteredMessage(p, "§e[ /길드 -> 전쟁 항복 수락/거절 ] §6으로 항복을 수락 또는 거절하세요.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
						
						else {
							Message.sendCenteredMessage(p, "§6현재 §c" + guild + " §6길드와 §c" + emeny + " §6길드가 전쟁중인 상태입니다.");
							p.sendMessage("");
							p.sendMessage("§e■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							p.sendMessage("");
						}
					}
				}
				
				return;
			}
		}, timeToRun);
		
		File f = new File("plugins/ActionRPG/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/ActionRPG");
		File folder2 = new File("plugins/ActionRPG/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreatePlayerInfo(p, f, folder, folder2, config);
		if (Method.getConfigBoolean("리로드") == false) {
			Method.setConfigBoolean("리로드", true);
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "noitem reload");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 미쳐버린_언데드");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 각성한_스켈레톤");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 진화한_위더");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 깨어난_베드락");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 부두술사_각성");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 부두술사_두번째_분신");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 부두술사_첫번째_분신");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss killtype 부두술사");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 미쳐버린_언데드 미쳐버린_언데드");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 각성한_스켈레톤 각성한_스켈레톤");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 깨어난_베드락 깨어난_베드락");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 진화한_위더 진화한_위더");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "eb boss spawn 부두술사 부두술사");
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "퀘스트 reload");
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (main.Megaphone.get(p.getName()) != null) {
			String chatMessage = e.getMessage();
			Bukkit.broadcastMessage("§f[§a확성기§f] §6" + p.getName() + " :: " + chatMessage);
			main.Megaphone.remove(p.getName());
		}
		
		else if (main.EMegaphone.get(p.getName()) != null) {
			if (p.hasPermission("이벤터")) {
				String chatMessage = e.getMessage();
				Bukkit.broadcastMessage("§f[§4이벤터§f] §6" + p.getName() + " :: " + chatMessage);
				main.EMegaphone.remove(p.getName());
			}
		}
		
		if (main.shop.containsKey(p.getName()) && main.buyme.containsKey(p.getName()) && main.buyto.containsKey(p.getName()) && main.buytype.containsKey(p.getName())) {
			if (main.isNumeric(e.getMessage())) {
				int i = Integer.parseInt(e.getMessage());
				int money = main.shop.get(p.getName());
				int maxstack = new MaterialData(main.buytype.get(p.getName()), (byte) 0).toItemStack(1).getMaxStackSize();
				double y = (double) money * i;
				String rpgitem = main.buyme.get(p.getName());
				String itemname = main.buyto.get(p.getName());
				
				if (main.economy.getBalance(p.getName()) >= y) {
					int t = 0;
		            ItemStack[] contents;
		            for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
		                ItemStack it = contents[j];
		                if (it == null) {
		                    ++t;
		                }
		            }

		            if ((double) t < ((double) i / maxstack)) {
		            	p.sendMessage("§c인벤토리에 공간이 부족하여 아이템을 구매할 수 없습니다.");
		            	main.shop.remove(p.getName());
		            	main.buyme.remove(p.getName());
		            	main.buyto.remove(p.getName());
						main.buytype.remove(p.getName());
						e.setCancelled(true);
		            	return;
		            } else {
		            	main.economy.withdrawPlayer(p.getName(), y);
		            	DecimalFormat df = new DecimalFormat("##########");
						p.sendMessage("§c" + itemname + "§6 아이템을 §c" + df.format(y) + " §6원 지불하고 구매하셨습니다.");
						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem " + rpgitem + " give " + p.getName() + " " + i);
						if (PlayerYml.getInfoBoolean(p, "효과음")) p.playSound(p.getLocation(), Sound.NOTE_PLING, 2.0F, 1.5F);
						e.setCancelled(true);
						main.shop.remove(p.getName());
						main.buyme.remove(p.getName());
						main.buyto.remove(p.getName());
						main.buytype.remove(p.getName());
						return;
		            }
				} else {
					p.sendMessage("§c돈이 부족하여 불건을 구매할 수 없습니다.");
					main.shop.remove(p.getName());
					main.buyme.remove(p.getName());
					main.buyto.remove(p.getName());
					main.buytype.remove(p.getName());
					e.setCancelled(true);
					return;
				}
			} else {
				p.sendMessage("§c물건 양은 채팅에 숫자로만 입력해 주시기 바랍니다.");
				main.shop.remove(p.getName());
				main.buyme.remove(p.getName());
				main.buyto.remove(p.getName());
				main.buytype.remove(p.getName());
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player kp = (Player) p.getKiller();
		
		if (kp == null) return;
		
		if (kp.getWorld().getName().equalsIgnoreCase("world_pvp") || kp.getWorld().getName().equalsIgnoreCase("world_pvp2")) {
			if (main.KillMessage.get(p.getName()) != null ) main.KillMessage.remove(p.getName());
			if (main.KillMessage.get(kp.getName()) == null) main.KillMessage.put(kp.getName(), 1);
			else 
			{
				int i = main.KillMessage.get(kp.getName()) + 1;
				main.KillMessage.put(kp.getName(), i); 
			}
			
			int x = main.KillMessage.get(kp.getName());
			kp.sendMessage("§f[§a안내§f] §6현재 당신은 §c" + x + " §6연승을 기록중입니다.");
			kp.playSound(kp.getLocation(), Sound.AMBIENCE_THUNDER, 100.0f, 200.0f);
			
			if (x == 1) main.economy.depositPlayer(p.getName(), 100);
			else if (x == 2) main.economy.depositPlayer(p.getName(), 200);
			else if (x == 3) main.economy.depositPlayer(p.getName(), 500);
			else if (x == 4) main.economy.depositPlayer(p.getName(), 800);
			else if (x == 5) main.economy.depositPlayer(p.getName(), 1000);
			else if (x == 6) main.economy.depositPlayer(p.getName(), 1500);
			else if (x >= 7) main.economy.depositPlayer(p.getName(), 2000);
		}
		
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (p.getWorld().getName().equalsIgnoreCase("skyworld")) {
			if (b.getTypeId() == 14) 
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "expgive " + p.getName() + " 20 1 금 블럭");
			else if (b.getTypeId() == 15)
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "expgive " + p.getName() + " 20 1 철 블럭");
			else if (b.getTypeId() == 16)
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "expgive " + p.getName() + " 15 1 석탄 블럭");
			else if (b.getTypeId() == 56)
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "expgive " + p.getName() + " 50 1 다이아몬드 블럭");
			else if (b.getTypeId() == 129)
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "expgive " + p.getName() + " 80 1 에메랄드 블럭");
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onThunderChange(ThunderChangeEvent event) {
		event.setCancelled(true);
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
	public void onLightningStrike(LightningStrikeEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(BlockExpEvent e) {
		e.setExpToDrop(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(EntityDeathEvent e) {
		e.setDroppedExp(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(ExpBottleEvent e) {
		e.setExperience(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onExp(PlayerFishEvent e) {
		e.setExpToDrop(0);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onIce(BlockFadeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onPotal(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (p.isSneaking()) {
			p.chat("/포탈");
			e.setCancelled(true);
		}
	}
}	
