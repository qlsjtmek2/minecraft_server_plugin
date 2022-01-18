package me.espoo.book;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;

		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (main.shop.get(p.getName()) != null) {
        	System.out.println(e.getCurrentItem().getItemMeta().getDisplayName());
        	e.setCancelled(true);
     	}

     	if (e.getInventory().getName().equalsIgnoreCase("도감")) {
     		e.setCancelled(true);
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 무기 도감")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e다음으로 §f]")) {
     			GUIXWaepon.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장갑")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§c§b§7§l§6 레이저 빔")) {
     			if (P.getInfoBoolean(p, "일반 무기.구동기사")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§d§6§7§l§6 금속 배트")) {
     			if (P.getInfoBoolean(p, "일반 무기.금속배트")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§e§1§7§l§6 숟가락")) {
     			if (P.getInfoBoolean(p, "일반 무기.돈신")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§8§7§7§l§6 동제 머신건")) {
     			if (P.getInfoBoolean(p, "일반 무기.동제")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§e§c§7§l§6 메탈나이트 미사일")) {
     			if (P.getInfoBoolean(p, "일반 무기.메탈나이트")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§f§7§7§l§6 번견맨의 손톱")) {
     			if (P.getInfoBoolean(p, "일반 무기.번견맨")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§0§d§7§l§6 섬광의 플래시 검")) {
     			if (P.getInfoBoolean(p, "일반 무기.섬광의플래시")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§2§a§7§l§6 유수암쇄권")) {
     			if (P.getInfoBoolean(p, "일반 무기.실버팽")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§1§8§7§l§6 꽃미남 가면")) {
         		if (P.getInfoBoolean(p, "일반 무기.아마이마스크")) e.setCancelled(true);
     		}
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§2§3§7§l§6 아토믹 사무라이 검")) {
         		if (P.getInfoBoolean(p, "일반 무기.아토믹")) e.setCancelled(true);
         	}
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§5§a§7§l§6 엔젤 스타일")) {
             	if (P.getInfoBoolean(p, "일반 무기.프리즈너")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§c§0§7§l§6 음속의 소닉 칼")) {
                if (P.getInfoBoolean(p, "일반 무기.음속의소닉")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§2§e§7§l§6 제노스의 파츠")) {
                if (P.getInfoBoolean(p, "일반 무기.제노스")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§3§9§7§l§6 좀비맨 도끼")) {
         		if (P.getInfoBoolean(p, "일반 무기.좀비맨")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§4§4§7§l§6 검은빛의 역기")) {
         		if (P.getInfoBoolean(p, "일반 무기.초합금검은빛")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§5§8§7§l§6 킹의 게임기")) {
         		if (P.getInfoBoolean(p, "일반 무기.킹")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§1§f§7§l§6 타츠마키 염동력")) {
         		if (P.getInfoBoolean(p, "일반 무기.타츠마키")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§4§f§7§l§6 탱크톱 마스터 근육")) {
         		if (P.getInfoBoolean(p, "일반 무기.탱크톱마스터")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§6§5§7§l§6 후부키 염동력")) {
         		if (P.getInfoBoolean(p, "일반 무기.후부키")) e.setCancelled(true);
            }
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("풀강 무기 도감")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUIWaepon.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§a§l■  §b10강 §6강화 주문서  §a§l■")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§d§5§7§l§6 레이저 빔 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.구동기사")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§e§0§7§l§6 금속 배트 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.금속배트")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§e§b§7§l§6 숟가락 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.돈신")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§9§1§7§l§6 동제 머신건 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.동제")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§f§6§7§l§6 메탈나이트 미사일 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.메탈나이트")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§0§1§7§l§6 번견맨의 손톱 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.번견맨")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§1§7§7§l§6 섬광의 플래시 검 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.섬광의플래시")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§3§4§7§l§6 유수암쇄권 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 무기.실버팽")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§2§2§7§l§6 꽃미남 가면 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.아마이마스크")) e.setCancelled(true);
     		}
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§2§d§7§l§6 아토믹 사무라이 검 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.아토믹")) e.setCancelled(true);
         	}
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§6§4§7§l§6 엔젤 스타일 §f+10")) {
             	if (P.getInfoBoolean(p, "풀강 무기.프리즈너")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§5§c§a§7§l§6 음속의 소닉 칼 §f+10")) {
                if (P.getInfoBoolean(p, "풀강 무기.음속의소닉")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§3§8§7§l§6 제노스의 파츠 §f+10")) {
                if (P.getInfoBoolean(p, "풀강 무기.제노스")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§4§3§7§l§6 좀비맨 도끼 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.좀비맨")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§4§e§7§l§6 검은빛의 역기 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.초합금검은빛")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§6§2§7§l§6 킹의 게임기 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.킹")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§2§9§7§l§6 타츠마키 염동력 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.타츠마키")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§5§9§7§l§6 탱크톱 마스터 근육 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.탱크톱마스터")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§6§6§f§7§l§6 후부키 염동력 §f+10")) {
         		if (P.getInfoBoolean(p, "풀강 무기.후부키")) e.setCancelled(true);
            }
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 1/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e다음으로 §f]")) {
     			GUInGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§f§7§l§6 구동기사 투구")) {
     			if (P.getInfoBoolean(p, "일반 장비.구동기사모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§0§7§l§6 구동기사 갑옷")) {
     			if (P.getInfoBoolean(p, "일반 장비.구동기사튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§1§7§l§6 구동기사 레깅스")) {
     			if (P.getInfoBoolean(p, "일반 장비.구동기사바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§2§7§l§6 구동기사 부츠")) {
     			if (P.getInfoBoolean(p, "일반 장비.구동기사신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§5§f§7§l§6 금속 배트 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.금속배트모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§0§7§l§6 금속 배트 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.금속배트튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§1§7§l§6 금속 배트 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.금속배트바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§2§7§l§6 금속 배트 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.금속배트신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§f§7§l§6 돈신 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.돈신모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§0§7§l§6 돈신 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.돈신튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§1§7§l§6 돈신 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.돈신바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§2§7§l§6 돈신 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.돈신신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§3§7§l§6 동제 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.동제모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§4§7§l§6 동제 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.동제튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§5§7§l§6 동제 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.동제바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§6§7§l§6 동제 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.동제신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§f§7§l§6 메탈나이트 투구")) {
     			if (P.getInfoBoolean(p, "일반 장비.메탈나이트모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§0§7§l§6 메탈나이트 갑옷")) {
     			if (P.getInfoBoolean(p, "일반 장비.메탈나이트튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§1§7§l§6 메탈나이트 레깅스")) {
     			if (P.getInfoBoolean(p, "일반 장비.메탈나이트바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§2§7§l§6 메탈나이트 부츠")) {
     			if (P.getInfoBoolean(p, "일반 장비.메탈나이트신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§d§f§7§l§6 번견맨 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.번견맨모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§0§7§l§6 번견맨 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.번견맨튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§1§7§l§6 번견맨 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.번견맨바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§2§7§l§6 번견맨 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.번견맨신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§1§7§l§6 섬광의 플래시 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.섬광의플래시모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§2§7§l§6 섬광의 플래시 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.섬광의플래시튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§3§7§l§6 섬광의 플래시 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.섬광의플래시바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§4§7§l§6 섬광의 플래시 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.섬광의플래시신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 2/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUInGear.openGUI1(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e다음으로 §f]")) {
     			GUInGear.openGUI3(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§4§3§7§l§f 실버팽 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.실버팽모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§4§4§7§l§f 실버팽 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.실버팽튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§4§5§7§l§f 실버팽 레깅스")) {
     			if (P.getInfoBoolean(p, "일반 장비.실버팽바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§4§6§7§l§f 실버팽 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.실버팽신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§f§7§l§f 아마이마스크 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.아마이마스크모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§0§7§l§f 아마이마스크 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.아마이마스크튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§1§7§l§f 아마이마스크 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.아마이마스크바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§2§7§l§f 아마이마스크 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.아마이마스크신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§3§f§7§l§f 아토믹 사무라이 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.아토믹모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§0§7§l§f 아토믹 사무라이 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.아토믹튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§1§7§l§f 아토믹 사무라이 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.아토믹바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§2§7§l§f 아토믹 사무라이 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.아토믹신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§b§f§7§l§f 음속의 소닉 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.음속의소닉모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§0§7§l§f 음속의 소닉 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.음속의소닉튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§1§7§l§f 음속의 소닉 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.음속의소닉바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§2§7§l§f 음속의 소닉 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.음속의소닉신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§3§f§7§l§f 제노스 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.제노스모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§0§7§l§f 제노스 갑옷")) {
     			if (P.getInfoBoolean(p, "일반 장비.제노스튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§1§7§l§f 제노스 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.제노스바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§2§7§l§f 제노스 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.제노스신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§f§7§l§f 좀비맨 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.좀비맨모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§0§7§l§f 좀비맨 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.좀비맨튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§1§7§l§f 좀비맨 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.좀비맨바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§2§7§l§f 좀비맨 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.좀비맨신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§f§f§7§l§f 초합금 검은빛 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.초합금검은빛모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§0§0§7§l§f 초합금 검은빛 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.초합금검은빛튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§0§1§7§l§f 초합금 검은빛 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.초합금검은빛바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§0§2§7§l§f 초합금 검은빛 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.초합금검은빛신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 3/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUInGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§7§f§7§l§f 킹 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.킹모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§0§7§l§f 킹 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.킹튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§1§7§l§f 킹 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.킹바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§2§7§l§f 킹 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.킹신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§3§7§l§f 타츠마키 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.타츠마키모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§4§7§l§f 타츠마키 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.타츠마키튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§5§7§l§f 타츠마키 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.타츠마키바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§6§7§l§f 타츠마키 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.타츠마키신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§f§7§l§f 탱크톱 마스터 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.탱크톱마스터모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§0§7§l§f 탱크톱 마스터 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.탱크톱마스터튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§1§7§l§f 탱크톱 마스터 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.탱크톱마스터바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§2§7§l§f 탱크톱 마스터 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.탱크톱마스터신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§f§7§l§f 포동포동 프리즈너 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.프리즈너모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§0§7§l§f 포동포동 프리즈너 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.프리즈너튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§1§7§l§f 포동포동 프리즈너 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.프리즈너바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§2§7§l§f 포동포동 프리즈너 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.프리즈너신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§f§7§l§f 후부키 모자")) {
     			if (P.getInfoBoolean(p, "일반 장비.후부키모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§0§7§l§f 후부키 튜닉")) {
     			if (P.getInfoBoolean(p, "일반 장비.후부키튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§1§7§l§f 후부키 바지")) {
     			if (P.getInfoBoolean(p, "일반 장비.후부키바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§2§7§l§f 후부키 신발")) {
     			if (P.getInfoBoolean(p, "일반 장비.후부키신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 1/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e다음으로 §f]")) {
     			GUIuGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§b§7§l§6 구동기사 투구")) {
     			if (P.getInfoBoolean(p, "유니크 장비.구동기사모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§c§7§l§6 구동기사 갑옷")) {
     			if (P.getInfoBoolean(p, "유니크 장비.구동기사튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§d§7§l§6 구동기사 레깅스")) {
     			if (P.getInfoBoolean(p, "유니크 장비.구동기사바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§b§e§7§l§6 구동기사 부츠")) {
     			if (P.getInfoBoolean(p, "유니크 장비.구동기사신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§b§7§l§6 금속 배트 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.금속배트모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§c§7§l§6 금속 배트 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.금속배트튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§d§7§l§6 금속 배트 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.금속배트바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§6§e§7§l§6 금속 배트 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.금속배트신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§b§7§l§6 돈신 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.돈신모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§c§7§l§6 돈신 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.돈신튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§d§7§l§6 돈신 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.돈신바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§c§e§7§l§6 돈신 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.돈신신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§f§7§l§6 동제 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.동제모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§6§0§7§l§6 동제 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.동제튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§6§1§7§l§6 동제 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.동제바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§6§2§7§l§6 동제 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.동제신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§b§7§l§6 메탈나이트 투구")) {
     			if (P.getInfoBoolean(p, "유니크 장비.메탈나이트모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§c§7§l§6 메탈나이트 갑옷")) {
     			if (P.getInfoBoolean(p, "유니크 장비.메탈나이트튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§d§7§l§6 메탈나이트 레깅스")) {
     			if (P.getInfoBoolean(p, "유니크 장비.메탈나이트바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§9§e§7§l§6 메탈나이트 부츠")) {
     			if (P.getInfoBoolean(p, "유니크 장비.메탈나이트신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§b§7§l§6 번견맨 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.번견맨모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§c§7§l§6 번견맨 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.번견맨튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§d§7§l§6 번견맨 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.번견맨바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§e§e§7§l§6 번견맨 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.번견맨신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§d§7§l§6 섬광의 플래시 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§e§7§l§6 섬광의 플래시 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§d§f§7§l§6 섬광의 플래시 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§4§e§0§7§l§6 섬광의 플래시 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.섬광의플래시신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 2/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUIuGear.openGUI1(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §e다음으로 §f]")) {
     			GUIuGear.openGUI3(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§4§f§7§l§6 실버팽 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.실버팽모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§0§7§l§6 실버팽 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.실버팽튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§1§7§l§6 실버팽 레깅스")) {
     			if (P.getInfoBoolean(p, "유니크 장비.실버팽바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§5§2§7§l§6 실버팽 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.실버팽신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§b§7§l§6 아마이마스크 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아마이마스크모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§c§7§l§6 아마이마스크 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아마이마스크튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§d§7§l§6 아마이마스크 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아마이마스크바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§9§e§7§l§6 아마이마스크 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아마이마스크신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§b§7§l§6 아토믹 사무라이 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아토믹모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§c§7§l§6 아토믹 사무라이 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아토믹튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§d§7§l§6 아토믹 사무라이 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아토믹바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§4§e§7§l§6 아토믹 사무라이 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.아토믹신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§b§7§l§6 음속의 소닉 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.음속의소닉모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§c§7§l§6 음속의 소닉 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.음속의소닉튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§d§7§l§6 음속의 소닉 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.음속의소닉바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§e§7§l§6 음속의 소닉 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.음속의소닉신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§b§7§l§6 제노스 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.제노스모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§c§7§l§6 제노스 갑옷")) {
     			if (P.getInfoBoolean(p, "유니크 장비.제노스튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§d§7§l§6 제노스 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.제노스바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§4§e§7§l§6 제노스 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.제노스신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§b§7§l§6 좀비맨 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.좀비맨모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§c§7§l§6 좀비맨 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.좀비맨튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§d§7§l§6 좀비맨 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.좀비맨바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§a§e§7§l§6 좀비맨 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.좀비맨신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§b§f§7§l§6 초합금 검은빛 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§c§0§7§l§6 초합금 검은빛 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§0§d§7§l§6 초합금 검은빛 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§0§e§7§l§6 초합금 검은빛 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.초합금검은빛신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 3/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUIuGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 사이타마 장비 큐브")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§b§7§l§6 킹 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.킹모자")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§c§7§l§6 킹 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.킹튜닉")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§d§7§l§6 킹 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.킹바지")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§1§8§e§7§l§6 킹 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.킹신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§f§7§l§6 타츠마키 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.타츠마키모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§0§7§l§6 타츠마키 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.타츠마키튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§1§7§l§6 타츠마키 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.타츠마키바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§3§3§2§7§l§6 타츠마키 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.타츠마키신발")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§b§7§l§6 탱크톱 마스터 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§c§7§l§6 탱크톱 마스터 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§d§7§l§6 탱크톱 마스터 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§7§e§7§l§6 탱크톱 마스터 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.탱크톱마스터신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§b§7§l§6 포동포동 프리즈너 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.프리즈너모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§c§7§l§6 포동포동 프리즈너 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.프리즈너튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§d§7§l§6 포동포동 프리즈너 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.프리즈너바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§8§e§7§l§6 포동포동 프리즈너 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.프리즈너신발")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§b§7§l§6 후부키 모자")) {
     			if (P.getInfoBoolean(p, "유니크 장비.후부키모자")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§c§7§l§6 후부키 튜닉")) {
     			if (P.getInfoBoolean(p, "유니크 장비.후부키튜닉")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§d§7§l§6 후부키 바지")) {
     			if (P.getInfoBoolean(p, "유니크 장비.후부키바지")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§2§a§e§7§l§6 후부키 신발")) {
     			if (P.getInfoBoolean(p, "유니크 장비.후부키신발")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("룬 도감")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 히든 속성 룬")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6 히든 속성 룬 §f+10")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§0§0§7§l§c 불 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.불속성")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§5§8§7§l§b 바람 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.바람속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§2§1§7§l§d 치유 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.치유속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§2§c§7§l§7§l 어둠 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.어둠속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§3§7§7§l§a 독 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.독속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§4§2§7§l§e 부패 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.부패속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§4§d§7§l§3 얼음 속성 §6룬")) {
     			if (P.getInfoBoolean(p, "일반 룬.얼음속성")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§0§a§7§l§c 불 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.불속성")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§6§2§7§l§b 바람 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.바람속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§2§b§7§l§d 치유 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.치유속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§3§6§7§l§7§l 어둠 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.어둠속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§4§1§7§l§a 독 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.독속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§4§c§7§l§e 부패 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.부패속성")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§0§0§0§0§0§7§5§7§7§l§3 얼음 속성 §6룬 §f+10")) {
     			if (P.getInfoBoolean(p, "풀강 룬.얼음속성")) e.setCancelled(true);
     		}
     	}
     	
    	else if (e.getInventory().getName().equalsIgnoreCase("도구 도감")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f도감")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §7뒤로가기 §f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§4§l■  §d프리미엄 §6강화 주문서  §4§l■")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이")) {
     			if (P.getInfoBoolean(p, "도구.보통0")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+1")) {
     			if (P.getInfoBoolean(p, "도구.보통1")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+2")) {
     			if (P.getInfoBoolean(p, "도구.보통2")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+3")) {
     			if (P.getInfoBoolean(p, "도구.보통3")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+4")) {
     			if (P.getInfoBoolean(p, "도구.보통4")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+5")) {
     			if (P.getInfoBoolean(p, "도구.보통5")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+6")) {
     			if (P.getInfoBoolean(p, "도구.보통6")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+7")) {
     			if (P.getInfoBoolean(p, "도구.보통7")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+8")) {
     			if (P.getInfoBoolean(p, "도구.보통8")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+9")) {
     			if (P.getInfoBoolean(p, "도구.보통9")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§e보통 곡괭이 §f+10")) {
     			if (P.getInfoBoolean(p, "도구.보통10")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이")) {
     			if (P.getInfoBoolean(p, "도구.진심0")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+1")) {
     			if (P.getInfoBoolean(p, "도구.진심1")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+2")) {
     			if (P.getInfoBoolean(p, "도구.진심2")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+3")) {
     			if (P.getInfoBoolean(p, "도구.진심3")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+4")) {
     			if (P.getInfoBoolean(p, "도구.진심4")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+5")) {
     			if (P.getInfoBoolean(p, "도구.진심5")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+6")) {
     			if (P.getInfoBoolean(p, "도구.진심6")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+7")) {
     			if (P.getInfoBoolean(p, "도구.진심7")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+8")) {
     			if (P.getInfoBoolean(p, "도구.진심8")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+9")) {
     			if (P.getInfoBoolean(p, "도구.진심9")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§6진심 곡괭이 §f+10")) {
     			if (P.getInfoBoolean(p, "도구.진심10")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§b마스터리 곡괭이")) {
     			if (P.getInfoBoolean(p, "도구.마스터리")) e.setCancelled(true);
     		}
     	}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getInventory().getName().equalsIgnoreCase("도감")) {
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §c무기 §6도감 §f]")) {
     			GUIWaepon.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §f일반 §b장비 §6도감 §f]")) {
     			GUInGear.openGUI1(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §6유니크 §b장비 §6도감 §f]")) {
     			GUIuGear.openGUI1(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §a도구 §6도감 §f]")) {
     			GUITool.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("§f[ §d룬 §6도감 §f]")) {
     			GUILoon.openGUI(p);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 무기 도감")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("레이저빔")) {
     				Method.voidWaepon(p, e, str, c, "구동기사");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트")) {
     				Method.voidWaepon(p, e, str, c, "금속배트");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("숟가락")) {
     				Method.voidWaepon(p, e, str, c, "돈신");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제머신건")) {
     				Method.voidWaepon(p, e, str, c, "동제");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트미사일")) {
     				Method.voidWaepon(p, e, str, c, "메탈나이트");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨의손톱")) {
     				Method.voidWaepon(p, e, str, c, "번견맨");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시검")) {
     				Method.voidWaepon(p, e, str, c, "섬광의플래시");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("유수암쇄권")) {
     				Method.voidWaepon(p, e, str, c, "실버팽");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("꽃미남가면")) {
     				Method.voidWaepon(p, e, str, c, "아마이마스크");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이검")) {
     				Method.voidWaepon(p, e, str, c, "아토믹");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉칼")) {
     				Method.voidWaepon(p, e, str, c, "음속의소닉");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스의파츠")) {
     				Method.voidWaepon(p, e, str, c, "제노스");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨도끼")) {
     				Method.voidWaepon(p, e, str, c, "좀비맨");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("검은빛의역기")) {
     				Method.voidWaepon(p, e, str, c, "초합금검은빛");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹의게임기")) {
     				Method.voidWaepon(p, e, str, c, "킹");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키염동력")) {
     				Method.voidWaepon(p, e, str, c, "타츠마키");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터근육")) {
     				Method.voidWaepon(p, e, str, c, "탱크톱마스터");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("엔젤스타일")) {
     				Method.voidWaepon(p, e, str, c, "프리즈너");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키염동력")) {
     				Method.voidWaepon(p, e, str, c, "후부키");
     			}
     			
     			if (Method.isWaepon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마무기 give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "일반 무기.보상 획득", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("§6미강화 무기 도감을 모두 채우셨으므로 §c사이타마 장갑 §6이 지급되었습니다.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("풀강 무기 도감")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("레이저빔+10")) {
     				Method.voidXWaepon(p, e, str, c, "구동기사");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트+10")) {
     				Method.voidXWaepon(p, e, str, c, "금속배트");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("숟가락+10")) {
     				Method.voidXWaepon(p, e, str, c, "돈신");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제머신건+10")) {
     				Method.voidXWaepon(p, e, str, c, "동제");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트미사일+10")) {
     				Method.voidXWaepon(p, e, str, c, "메탈나이트");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨의손톱+10")) {
     				Method.voidXWaepon(p, e, str, c, "번견맨");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시검+10")) {
     				Method.voidXWaepon(p, e, str, c, "섬광의플래시");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("유수암쇄권+10")) {
     				Method.voidXWaepon(p, e, str, c, "실버팽");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("꽃미남가면+10")) {
     				Method.voidXWaepon(p, e, str, c, "아마이마스크");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이검+10")) {
     				Method.voidXWaepon(p, e, str, c, "아토믹");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉칼+10")) {
     				Method.voidXWaepon(p, e, str, c, "음속의소닉");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스의파츠+10")) {
     				Method.voidXWaepon(p, e, str, c, "제노스");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨도끼+10")) {
     				Method.voidXWaepon(p, e, str, c, "좀비맨");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("검은빛의역기+10")) {
     				Method.voidXWaepon(p, e, str, c, "초합금검은빛");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹의게임기+10")) {
     				Method.voidXWaepon(p, e, str, c, "킹");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키염동력+10")) {
     				Method.voidXWaepon(p, e, str, c, "타츠마키");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터근육+10")) {
     				Method.voidXWaepon(p, e, str, c, "탱크톱마스터");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("엔젤스타일+10")) {
     				Method.voidXWaepon(p, e, str, c, "프리즈너");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키염동력+10")) {
     				Method.voidXWaepon(p, e, str, c, "후부키");
     			}
     			
     			if (Method.isXWaepon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
     						}
     						
     						ItemStack item = me.espoo.upgrade.main.getUpGrade();
    						ItemMeta meta = item.getItemMeta();
    						String st = meta.getDisplayName().replaceAll("<num>", "10");
    						List<String> lore = new ArrayList<String>();
    						for (String str : item.getItemMeta().getLore()) {
    							if (str.contains("<num>")) {
    								str = str.replace("<num>", "10");
    							} lore.add(str);
    						}
    						
    						meta.setDisplayName(st);
    						meta.setLore(lore);
    						item.setItemMeta(meta);
    		                p.getInventory().addItem(new ItemStack[] { item });
     	     				P.setInfoBoolean(p, "풀강 무기.보상 획득", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("§6풀강화 무기 도감을 모두 채우셨으므로 §c10강 강화 주문서 §6가 지급되었습니다.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 1/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("일반")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사투구")) {
         				Method.voidnGear1(p, e, str, c, "구동기사모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사갑옷")) {
         				Method.voidnGear1(p, e, str, c, "구동기사튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사레깅스")) {
         				Method.voidnGear1(p, e, str, c, "구동기사바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사부츠")) {
         				Method.voidnGear1(p, e, str, c, "구동기사신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트모자")) {
         				Method.voidnGear1(p, e, str, c, "금속배트모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트튜닉")) {
         				Method.voidnGear1(p, e, str, c, "금속배트튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트바지")) {
         				Method.voidnGear1(p, e, str, c, "금속배트바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트신발")) {
         				Method.voidnGear1(p, e, str, c, "금속배트신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신모자")) {
         				Method.voidnGear1(p, e, str, c, "돈신모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신튜닉")) {
         				Method.voidnGear1(p, e, str, c, "돈신튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신바지")) {
         				Method.voidnGear1(p, e, str, c, "돈신바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신신발")) {
         				Method.voidnGear1(p, e, str, c, "돈신신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제모자")) {
         				Method.voidnGear1(p, e, str, c, "동제모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제튜닉")) {
         				Method.voidnGear1(p, e, str, c, "동제튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제바지")) {
         				Method.voidnGear1(p, e, str, c, "동제바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제신발")) {
         				Method.voidnGear1(p, e, str, c, "동제신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트투구")) {
         				Method.voidnGear1(p, e, str, c, "메탈나이트모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트갑옷")) {
         				Method.voidnGear1(p, e, str, c, "메탈나이트튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트레깅스")) {
         				Method.voidnGear1(p, e, str, c, "메탈나이트바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트부츠")) {
         				Method.voidnGear1(p, e, str, c, "메탈나이트신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨모자")) {
         				Method.voidnGear1(p, e, str, c, "번견맨모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨튜닉")) {
         				Method.voidnGear1(p, e, str, c, "번견맨튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨바지")) {
         				Method.voidnGear1(p, e, str, c, "번견맨바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨신발")) {
         				Method.voidnGear1(p, e, str, c, "번견맨신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시모자")) {
         				Method.voidnGear1(p, e, str, c, "섬광의플래시모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시튜닉")) {
         				Method.voidnGear1(p, e, str, c, "섬광의플래시튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시바지")) {
         				Method.voidnGear1(p, e, str, c, "섬광의플래시바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시신발")) {
         				Method.voidnGear1(p, e, str, c, "섬광의플래시신발");
         			}
         			

         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마일반큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "일반 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6일반 장비 도감을 모두 채우셨으므로 §c사이타마 일반 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 2/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("일반")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽모자")) {
         				Method.voidnGear2(p, e, str, c, "실버팽모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽튜닉")) {
         				Method.voidnGear2(p, e, str, c, "실버팽튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽레깅스")) {
         				Method.voidnGear2(p, e, str, c, "실버팽바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽신발")) {
         				Method.voidnGear2(p, e, str, c, "실버팽신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크모자")) {
         				Method.voidnGear2(p, e, str, c, "아마이마스크모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크튜닉")) {
         				Method.voidnGear2(p, e, str, c, "아마이마스크튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크바지")) {
         				Method.voidnGear2(p, e, str, c, "아마이마스크바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크신발")) {
         				Method.voidnGear2(p, e, str, c, "아마이마스크신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이모자")) {
         				Method.voidnGear2(p, e, str, c, "아토믹모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이튜닉")) {
         				Method.voidnGear2(p, e, str, c, "아토믹튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이바지")) {
         				Method.voidnGear2(p, e, str, c, "아토믹바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이신발")) {
         				Method.voidnGear2(p, e, str, c, "아토믹신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉모자")) {
         				Method.voidnGear2(p, e, str, c, "음속의소닉모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉튜닉")) {
         				Method.voidnGear2(p, e, str, c, "음속의소닉튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉바지")) {
         				Method.voidnGear2(p, e, str, c, "음속의소닉바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉신발")) {
         				Method.voidnGear2(p, e, str, c, "음속의소닉신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스모자")) {
         				Method.voidnGear2(p, e, str, c, "제노스모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스갑옷")) {
         				Method.voidnGear2(p, e, str, c, "제노스튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스바지")) {
         				Method.voidnGear2(p, e, str, c, "제노스바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스신발")) {
         				Method.voidnGear2(p, e, str, c, "제노스신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨모자")) {
         				Method.voidnGear2(p, e, str, c, "좀비맨모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨튜닉")) {
         				Method.voidnGear2(p, e, str, c, "좀비맨튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨바지")) {
         				Method.voidnGear2(p, e, str, c, "좀비맨바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨신발")) {
         				Method.voidnGear2(p, e, str, c, "좀비맨신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛모자")) {
         				Method.voidnGear2(p, e, str, c, "초합금검은빛모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛튜닉")) {
         				Method.voidnGear2(p, e, str, c, "초합금검은빛튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛바지")) {
         				Method.voidnGear2(p, e, str, c, "초합금검은빛바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛신발")) {
         				Method.voidnGear2(p, e, str, c, "초합금검은빛신발");
         			}
         			
         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마일반큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "일반 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6일반 장비 도감을 모두 채우셨으므로 §c사이타마 일반 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("일반 장비 도감 3/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("일반")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("킹모자")) {
         				Method.voidnGear3(p, e, str, c, "킹모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹튜닉")) {
         				Method.voidnGear3(p, e, str, c, "킹튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹바지")) {
         				Method.voidnGear3(p, e, str, c, "킹바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹신발")) {
         				Method.voidnGear3(p, e, str, c, "킹신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키모자")) {
         				Method.voidnGear3(p, e, str, c, "타츠마키모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키튜닉")) {
         				Method.voidnGear3(p, e, str, c, "타츠마키튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키바지")) {
         				Method.voidnGear3(p, e, str, c, "타츠마키바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키신발")) {
         				Method.voidnGear3(p, e, str, c, "타츠마키신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터모자")) {
         				Method.voidnGear3(p, e, str, c, "탱크톱마스터모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터튜닉")) {
         				Method.voidnGear3(p, e, str, c, "탱크톱마스터튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터바지")) {
         				Method.voidnGear3(p, e, str, c, "탱크톱마스터바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터신발")) {
         				Method.voidnGear3(p, e, str, c, "탱크톱마스터신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너모자")) {
         				Method.voidnGear3(p, e, str, c, "프리즈너모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너튜닉")) {
         				Method.voidnGear3(p, e, str, c, "프리즈너튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너바지")) {
         				Method.voidnGear3(p, e, str, c, "프리즈너바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너신발")) {
         				Method.voidnGear3(p, e, str, c, "프리즈너신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키모자")) {
         				Method.voidnGear3(p, e, str, c, "후부키모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키튜닉")) {
         				Method.voidnGear3(p, e, str, c, "후부키튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키바지")) {
         				Method.voidnGear3(p, e, str, c, "후부키바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키신발")) {
         				Method.voidnGear3(p, e, str, c, "후부키신발");
         			}         			

         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마일반큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "일반 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6일반 장비 도감을 모두 채우셨으므로 §c사이타마 일반 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 1/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("유니크")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사투구")) {
         				Method.voiduGear1(p, e, str, c, "구동기사모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사갑옷")) {
         				Method.voiduGear1(p, e, str, c, "구동기사튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사레깅스")) {
         				Method.voiduGear1(p, e, str, c, "구동기사바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("구동기사부츠")) {
         				Method.voiduGear1(p, e, str, c, "구동기사신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트모자")) {
         				Method.voiduGear1(p, e, str, c, "금속배트모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트튜닉")) {
         				Method.voiduGear1(p, e, str, c, "금속배트튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트바지")) {
         				Method.voiduGear1(p, e, str, c, "금속배트바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("금속배트신발")) {
         				Method.voiduGear1(p, e, str, c, "금속배트신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신모자")) {
         				Method.voiduGear1(p, e, str, c, "돈신모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신튜닉")) {
         				Method.voiduGear1(p, e, str, c, "돈신튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신바지")) {
         				Method.voiduGear1(p, e, str, c, "돈신바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("돈신신발")) {
         				Method.voiduGear1(p, e, str, c, "돈신신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제모자")) {
         				Method.voiduGear1(p, e, str, c, "동제모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제튜닉")) {
         				Method.voiduGear1(p, e, str, c, "동제튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제바지")) {
         				Method.voiduGear1(p, e, str, c, "동제바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("동제신발")) {
         				Method.voiduGear1(p, e, str, c, "동제신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트투구")) {
         				Method.voiduGear1(p, e, str, c, "메탈나이트모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트갑옷")) {
         				Method.voiduGear1(p, e, str, c, "메탈나이트튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트레깅스")) {
         				Method.voiduGear1(p, e, str, c, "메탈나이트바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("메탈나이트부츠")) {
         				Method.voiduGear1(p, e, str, c, "메탈나이트신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨모자")) {
         				Method.voiduGear1(p, e, str, c, "번견맨모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨튜닉")) {
         				Method.voiduGear1(p, e, str, c, "번견맨튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨바지")) {
         				Method.voiduGear1(p, e, str, c, "번견맨바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("번견맨신발")) {
         				Method.voiduGear1(p, e, str, c, "번견맨신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시모자")) {
         				Method.voiduGear1(p, e, str, c, "섬광의플래시모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시튜닉")) {
         				Method.voiduGear1(p, e, str, c, "섬광의플래시튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시바지")) {
         				Method.voiduGear1(p, e, str, c, "섬광의플래시바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("섬광의플래시신발")) {
         				Method.voiduGear1(p, e, str, c, "섬광의플래시신발");
         			}
         			

         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마유니크큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "유니크 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6유니크 장비 도감을 모두 채우셨으므로 §c사이타마 유니크 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 2/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("유니크")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽모자")) {
         				Method.voiduGear2(p, e, str, c, "실버팽모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽튜닉")) {
         				Method.voiduGear2(p, e, str, c, "실버팽튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽레깅스")) {
         				Method.voiduGear2(p, e, str, c, "실버팽바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("실버팽신발")) {
         				Method.voiduGear2(p, e, str, c, "실버팽신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크모자")) {
         				Method.voiduGear2(p, e, str, c, "아마이마스크모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크튜닉")) {
         				Method.voiduGear2(p, e, str, c, "아마이마스크튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크바지")) {
         				Method.voiduGear2(p, e, str, c, "아마이마스크바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아마이마스크신발")) {
         				Method.voiduGear2(p, e, str, c, "아마이마스크신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이모자")) {
         				Method.voiduGear2(p, e, str, c, "아토믹모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이튜닉")) {
         				Method.voiduGear2(p, e, str, c, "아토믹튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이바지")) {
         				Method.voiduGear2(p, e, str, c, "아토믹바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("아토믹사무라이신발")) {
         				Method.voiduGear2(p, e, str, c, "아토믹신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉모자")) {
         				Method.voiduGear2(p, e, str, c, "음속의소닉모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉튜닉")) {
         				Method.voiduGear2(p, e, str, c, "음속의소닉튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉바지")) {
         				Method.voiduGear2(p, e, str, c, "음속의소닉바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("음속의소닉신발")) {
         				Method.voiduGear2(p, e, str, c, "음속의소닉신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스모자")) {
         				Method.voiduGear2(p, e, str, c, "제노스모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스갑옷")) {
         				Method.voiduGear2(p, e, str, c, "제노스튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스바지")) {
         				Method.voiduGear2(p, e, str, c, "제노스바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("제노스신발")) {
         				Method.voiduGear2(p, e, str, c, "제노스신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨모자")) {
         				Method.voiduGear2(p, e, str, c, "좀비맨모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨튜닉")) {
         				Method.voiduGear2(p, e, str, c, "좀비맨튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨바지")) {
         				Method.voiduGear2(p, e, str, c, "좀비맨바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("좀비맨신발")) {
         				Method.voiduGear2(p, e, str, c, "좀비맨신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛모자")) {
         				Method.voiduGear2(p, e, str, c, "초합금검은빛모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛튜닉")) {
         				Method.voiduGear2(p, e, str, c, "초합금검은빛튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛바지")) {
         				Method.voiduGear2(p, e, str, c, "초합금검은빛바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("초합금검은빛신발")) {
         				Method.voiduGear2(p, e, str, c, "초합금검은빛신발");
         			}
         			
         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마유니크큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "유니크 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6유니크 장비 도감을 모두 채우셨으므로 §c사이타마 유니크 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("유니크 장비 도감 3/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("유니크")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("킹모자")) {
         				Method.voiduGear3(p, e, str, c, "킹모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹튜닉")) {
         				Method.voiduGear3(p, e, str, c, "킹튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹바지")) {
         				Method.voiduGear3(p, e, str, c, "킹바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("킹신발")) {
         				Method.voiduGear3(p, e, str, c, "킹신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키모자")) {
         				Method.voiduGear3(p, e, str, c, "타츠마키모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키튜닉")) {
         				Method.voiduGear3(p, e, str, c, "타츠마키튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키바지")) {
         				Method.voiduGear3(p, e, str, c, "타츠마키바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("타츠마키신발")) {
         				Method.voiduGear3(p, e, str, c, "타츠마키신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터모자")) {
         				Method.voiduGear3(p, e, str, c, "탱크톱마스터모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터튜닉")) {
         				Method.voiduGear3(p, e, str, c, "탱크톱마스터튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터바지")) {
         				Method.voiduGear3(p, e, str, c, "탱크톱마스터바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("탱크톱마스터신발")) {
         				Method.voiduGear3(p, e, str, c, "탱크톱마스터신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너모자")) {
         				Method.voiduGear3(p, e, str, c, "프리즈너모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너튜닉")) {
         				Method.voiduGear3(p, e, str, c, "프리즈너튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너바지")) {
         				Method.voiduGear3(p, e, str, c, "프리즈너바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("포동포동프리즈너신발")) {
         				Method.voiduGear3(p, e, str, c, "프리즈너신발");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키모자")) {
         				Method.voiduGear3(p, e, str, c, "후부키모자");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키튜닉")) {
         				Method.voiduGear3(p, e, str, c, "후부키튜닉");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키바지")) {
         				Method.voiduGear3(p, e, str, c, "후부키바지");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("후부키신발")) {
         				Method.voiduGear3(p, e, str, c, "후부키신발");
         			}         			

         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 사이타마유니크큐브 give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "유니크 장비.보상 획득", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("§6유니크 장비 도감을 모두 채우셨으므로 §c사이타마 유니크 장비 큐브 §6가 지급되었습니다.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("룬 도감")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("불속성룬")) {
     				Method.voidLoon(p, e, str, c, "불속성");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("바람속성룬")) {
     				Method.voidLoon(p, e, str, c, "바람속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("치유속성룬")) {
     				Method.voidLoon(p, e, str, c, "치유속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("어둠속성룬")) {
     				Method.voidLoon(p, e, str, c, "어둠속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("독속성룬")) {
     				Method.voidLoon(p, e, str, c, "독속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("부패속성룬")) {
     				Method.voidLoon(p, e, str, c, "부패속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("얼음속성룬")) {
     				Method.voidLoon(p, e, str, c, "얼음속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("불속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "불속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("바람속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "바람속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("치유속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "치유속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("어둠속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "어둠속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("독속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "독속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("부패속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "부패속성");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("얼음속성룬+10")) {
     				Method.voidXLoon(p, e, str, c, "얼음속성");
     			}

     			if (Method.isLoon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 히든속성 give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "일반 룬.보상 획득", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("§6미강화 룬 도감을 모두 채우셨으므로 §c히든 속성 룬 §6이 지급되었습니다.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     			
     			if (Method.isXLoon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 10히든속성 give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "풀강 룬.보상 획득", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("§6풀강 룬 도감을 모두 채우셨으므로 §c히든 속성 룬 +10 §6이 지급되었습니다.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("도구 도감")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" 도감", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이")) {
     				Method.voidTool(p, e, str, c, "보통0");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+1")) {
     				Method.voidTool(p, e, str, c, "보통1");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+2")) {
     				Method.voidTool(p, e, str, c, "보통2");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+3")) {
     				Method.voidTool(p, e, str, c, "보통3");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+4")) {
     				Method.voidTool(p, e, str, c, "보통4");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+5")) {
     				Method.voidTool(p, e, str, c, "보통5");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+6")) {
     				Method.voidTool(p, e, str, c, "보통6");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+7")) {
     				Method.voidTool(p, e, str, c, "보통7");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+8")) {
     				Method.voidTool(p, e, str, c, "보통8");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+9")) {
     				Method.voidTool(p, e, str, c, "보통9");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("보통곡괭이+10")) {
     				Method.voidTool(p, e, str, c, "보통10");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이")) {
     				Method.voidTool(p, e, str, c, "진심0");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+1")) {
     				Method.voidTool(p, e, str, c, "진심1");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+2")) {
     				Method.voidTool(p, e, str, c, "진심2");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+3")) {
     				Method.voidTool(p, e, str, c, "진심3");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+4")) {
     				Method.voidTool(p, e, str, c, "진심4");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+5")) {
     				Method.voidTool(p, e, str, c, "진심5");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+6")) {
     				Method.voidTool(p, e, str, c, "진심6");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+7")) {
     				Method.voidTool(p, e, str, c, "진심7");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+8")) {
     				Method.voidTool(p, e, str, c, "진심8");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+9")) {
     				Method.voidTool(p, e, str, c, "진심9");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("진심곡괭이+10")) {
     				Method.voidTool(p, e, str, c, "진심10");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("마스터리곡괭이")) {
     				Method.voidTool(p, e, str, c, "마스터리");
     			}
     			
     			if (Method.isTool(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("§c인벤토리에 공간이 부족해 인벤토리에서 랜덤으로 아이템을 떨어트리고 보상지급이 되었습니다.");
     						}
     						
     					    ItemStack item = new ItemStack(Material.PAPER);
     					    ItemMeta gm = item.getItemMeta();
     					    gm.setDisplayName("§4§l■  §d프리미엄 §6강화 주문서  §4§l■");
     					    List<String> list = new ArrayList<String>();
     					    list.add("§7§l=================");
     					    list.add("§f프리미엄 강화 주문서이다.");
     					    list.add("§f이 아이템을 들고 강화");
     					    list.add("§f하고 싶은 아이템에 올려");
     					    list.add("§f두면 그 아이템이 강화된다.");
     					    list.add("");
     					    list.add("§7특수 능력: §f강화 할 아이");
     					    list.add("§f템이 §c파괴§f되지 않고, 강화");
     					    list.add("§f성공 확률이 대폭 §b상승§f한다.");
     					    list.add("§7§l=================");
     					    
     					    gm.setLore(list);
     					    item.setAmount(50);
     					    item.setItemMeta(gm);
     						p.getInventory().addItem(item);
     						
     	     				P.setInfoBoolean(p, "도구.보상 획득", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("§6도구 도감을 모두 채우셨으므로 §c프리미엄 강화 주문서 50개 §6가 지급되었습니다.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
	}
}
