package me.shinkhan.antibug;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecation")
public class Event extends JavaPlugin implements Listener {
	main M;

	public Event(main main) {
		this.M = main;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (main.chatSave.containsKey(p))
			main.chatSave.remove(p);
	}

	@EventHandler
	public void onPlayerChatDobae(PlayerChatEvent e) {
		Player p = e.getPlayer();
		String message = e.getMessage();
		if (!main.chatSave.containsKey(p)) {
			main.chatSave.put(p, message);
			return;
		}

		String savemessage = main.chatSave.get(p);
		if (!p.isOp() && savemessage.equalsIgnoreCase(message)) {
			e.setCancelled(true);
			return;
		} else {
			main.chatSave.put(p, message);
			return;
		}
	}

	@EventHandler
	public void onPlayerChatSwear(PlayerChatEvent e) {
		String message = e.getMessage();

		for (String wards : main.badwards) {
			if (API.containString(message, wards)) {
				message = API.changeString(message, wards);
			}
		}
		e.setMessage(message);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCMD(PlayerCommandPreprocessEvent e) {
		if (API.isCrashColor(e.getMessage())) {
			e.getPlayer().sendMessage("§c색코드 버그 사용을 차단했습니다.");
			API.sendMSGtoOP(API.colorize("&c" + e.getPlayer().getName() + " &6님이 명령어를 통한 색코드 버그 사용을 시도했습니다."));
			e.setMessage("/ccfblock");
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent e) {
		if (API.isCrashColor(e.getMessage())) {
			e.getPlayer().sendMessage("§c색코드 버그 사용을 차단했습니다.");
			API.sendMSGtoOP(API.colorize("&c" + e.getPlayer().getName() + " &6님이 채팅을 통한 색코드 버그 사용을 시도했습니다."));
			e.setMessage("k");
		}
	}

    @EventHandler(priority = EventPriority.LOWEST)
    public void preprocess(PlayerCommandPreprocessEvent e) {
    	String s = e.getMessage().replace("/", "");
        String[] split = s.split(" ");
        if (split.length >= 2 && split[0].equalsIgnoreCase("co") && split[1].equalsIgnoreCase("l")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
        }
        
        else if (split.length >= 2 && split[0].equalsIgnoreCase("co") && split[1].equalsIgnoreCase("purge")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
        }
        
        else if (split.length >= 3 && split[0].equalsIgnoreCase("npc") && split[1].equalsIgnoreCase("remove") && split[2].equalsIgnoreCase("all")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
        }
        
        else if (split.length >= 1 && split[0].equalsIgnoreCase("op") || split.length >= 1 && split[0].equalsIgnoreCase("deop")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
        }
        
        else if (split.length >= 1 && split[0].equalsIgnoreCase("sk") || split.length >= 1 && split[0].equalsIgnoreCase("ska") || split.length >= 1 && split[0].equalsIgnoreCase("skript")) {
            if (!e.getPlayer().getName().equalsIgnoreCase("shinkhan")) {
            	e.setCancelled(true);
            	e.getPlayer().sendMessage("§f입력하신 명령어는 존재하지 않습니다. \"/도움말\" 을 참조해주세요.");
            }
        }
    }
	
	@EventHandler
	public void vehivelMove(VehicleMoveEvent e) {
		if ((e.getVehicle() instanceof StorageMinecart)) {
			StorageMinecart cart = (StorageMinecart) e.getVehicle();
			if (cart.getInventory().getViewers().size() >= 1) {
				for (HumanEntity v : cart.getInventory().getViewers()) {
					if (v.getLocation().distance(e.getVehicle().getLocation()) >= 15.0D) {
						((Player) v).sendMessage("§9[ChestBugPrevent] §c상자 카트 이용중에 일정 거리 이상 카트가 이동하여 인벤토리가 강제로 닫혔습니다.");
						v.closeInventory();
					}
				}
			}
		} else if ((e.getVehicle() instanceof HopperMinecart)) {
			HopperMinecart cart = (HopperMinecart) e.getVehicle();
			if (cart.getInventory().getViewers().size() >= 1)
				for (HumanEntity v : cart.getInventory().getViewers()) {
					if (v.getLocation().distance(e.getVehicle().getLocation()) >= 15.0D) {
						((Player) v)
								.sendMessage("§9[ChestBugPrevent] §c깔때기 카트 이용중에 일정 거리 이상 카트가 이동하여 인벤토리가 강제로 닫혔습니다.");
						v.closeInventory();
					}
				}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void check(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String IP = e.getAddress().getHostAddress().toString();
		if (!API.getAllowIPList().contains(IP) && !API.getAllowIPList().contains(p.getName())) {
			if (!main.isProxy.containsKey(IP))
				main.isProxy.put(IP, Boolean.valueOf(API.CheckIP(IP)));
			if (!((Boolean) main.isProxy.get(IP)).booleanValue()) {
				PlayerTimer timer = new PlayerTimer(p, new Runnable() {
					@Override
					public void run() {
						p.kickPlayer("§a[§cOnlyKoreanIP§a]§f 해외 아이피 차단! - 한국 IP만 출입이 가능합니다.\n\nSry :( Our Server only can join Korean IP");
						e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
						Bukkit.getConsoleSender().sendMessage("§a[§cOnlyKoreanIP§a]§f 해외 아이피 차단 - " + p.getName() + " / " + IP);
					}
				});
				
				timer.setTime(1);
				timer.Start();
			}
		}
		
		if (p.isOp() && !API.getAllowOpIPList().contains(IP)) {
			PlayerTimer timer = new PlayerTimer(p, new Runnable() {
				@Override
				public void run() {
					p.kickPlayer("§c당신은 오피 계정 접속에 허용된 아이피가 아닙니다.");
				}
			});
			
			timer.setTime(1);
			timer.Start();
		}
	}
}
