package me.shinkhan.BuyHouse;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	static Method E;
	static Sign sign;
		
	public mainEvent(main main)
	{
		this.M = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		final Player p = e.getPlayer();
		final String n = p.getName();
		
		File f = new File("plugins/DHBuyHouse/Player/" + p.getName() + ".yml");
		File folder = new File("plugins/DHBuyHouse");
		File folder2 = new File("plugins/DHBuyHouse/Player");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) Method.CreatePlayerInfo(p, f, folder, folder2, config);
		
		if (n.equalsIgnoreCase("shinkhan")) {
			Timer timer = new Timer();
			Date timeToRun = new Date(System.currentTimeMillis() + 400);
			timer.schedule(new TimerTask() {
				public void run() {
					p.sendMessage("§6이 서버에는 당신의 플러그인인 §cDHBuyHouse §6가 들어있습니다!");
					return;
				}
			}, timeToRun);
		}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) 
	{
		Action act = e.getAction();
		Player p = e.getPlayer();
	    
        if (act == Action.RIGHT_CLICK_BLOCK) {
            Block b = e.getClickedBlock();
            if (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN) {    
                Sign s = (Sign)b.getState();
                
                if (s.getLine(0).contains(E.getConfigString("집 구매 설정"))) {
                	if (E.getConfigInt("개인당 최대 집 구매 개수") >= 0) {
                		if (E.getInfoInt(p, "집 개수") < E.getConfigInt("개인당 최대 집 구매 개수")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("§c구매할 돈이 부족합니다.");
                                p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "집 개수") + 1;
                            E.setInfoInt(p, "집 개수", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                            s.setLine(0, E.getConfigString("Lockette 표지판"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("§c당신은 이미 집 최대 구매 한도를 초과했습니다.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("§c구매할 돈이 부족합니다.");
                            p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "집 개수") + 1;
                        E.setInfoInt(p, "집 개수", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                        s.setLine(0, E.getConfigString("Lockette 표지판"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("창고 구매 설정"))) {
                	if (E.getConfigInt("개인당 최대 창고 구매 개수") >= 0) {
                		if (E.getInfoInt(p, "창고 개수") < E.getConfigInt("개인당 최대 창고 구매 개수")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("§c구매할 돈이 부족합니다.");
                                p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "창고 개수") + 1;
                            E.setInfoInt(p, "창고 개수", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                            s.setLine(0, E.getConfigString("Lockette 표지판"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("§c당신은 이미 집 최대 구매 한도를 초과했습니다.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("§c구매할 돈이 부족합니다.");
                            p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "창고 개수") + 1;
                        E.setInfoInt(p, "창고 개수", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                        s.setLine(0, E.getConfigString("Lockette 표지판"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("모루 구매 설정"))) {
                	if (E.getConfigInt("개인당 최대 모루 구매 개수") >= 0) {
                		if (E.getInfoInt(p, "모루 개수") < E.getConfigInt("개인당 최대 모루 구매 개수")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("§c구매할 돈이 부족합니다.");
                                p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "모루 개수") + 1;
                            E.setInfoInt(p, "모루 개수", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                            s.setLine(0, E.getConfigString("Lockette 표지판"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("§c당신은 이미 집 최대 구매 한도를 초과했습니다.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("§c구매할 돈이 부족합니다.");
                            p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "모루 개수") + 1;
                        E.setInfoInt(p, "모루 개수", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                        s.setLine(0, E.getConfigString("Lockette 표지판"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("인첸트 구매 설정"))) {
                	if (E.getConfigInt("개인당 최대 인첸트 구매 개수") >= 0) {
                		if (E.getInfoInt(p, "인첸트 개수") < E.getConfigInt("개인당 최대 인첸트 구매 개수")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("§c구매할 돈이 부족합니다.");
                                p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "인첸트 개수") + 1;
                            E.setInfoInt(p, "인첸트 개수", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                            s.setLine(0, E.getConfigString("Lockette 표지판"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("§c당신은 이미 집 최대 구매 한도를 초과했습니다.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("§c구매할 돈이 부족합니다.");
                            p.sendMessage("§c가격 : §4" + sdsd + " §c내가 가진 돈 : §4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "인첸트 개수") + 1;
                        E.setInfoInt(p, "인첸트 개수", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("§6성공적으로 §c구매§6하셨습니다.");
                        s.setLine(0, E.getConfigString("Lockette 표지판"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
            }
            
            else if (b.getTypeId() == 54 || b.getTypeId() == 130 || b.getTypeId() == 116 || 
            		 b.getTypeId() == 324 || b.getTypeId() == 330 || b.getTypeId() == 64 || b.getTypeId() == 145) {
            	if (isSign(b)) {
            		e.setCancelled(true);
            	}
            }
        }
	}
	
    @SuppressWarnings("static-access")
	@EventHandler
    public void onChangeSign(SignChangeEvent e) {
        final Player p = e.getPlayer();
        if (e.getLine(0).equalsIgnoreCase(E.getConfigString("집 구매 입력")) || e.getLine(0).equalsIgnoreCase(E.getConfigString("창고 구매 입력")) 
         || e.getLine(0).equalsIgnoreCase(E.getConfigString("모루 구매 입력")) || e.getLine(0).equalsIgnoreCase(E.getConfigString("인첸트 구매 입력"))) {
            if (!p.isOp()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "당신은 표지판을 만들 권한이 없습니다.");
                return;
            }
            if (!e.getLine(1).isEmpty()) {
                try {
                	final int i = Integer.parseInt(e.getLine(1));
                	
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("집 구매 입력"))) {
                        e.setLine(0, E.getConfigString("집 구매 설정"));
                        p.sendMessage("§6자동 집 구매가 §c" + i + " §6의 가격으로 올려졌습니다!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("창고 구매 입력"))) {
                        e.setLine(0, E.getConfigString("창고 구매 설정"));
                        p.sendMessage("§6자동 창고 구매가 §c" + i + " §6의 가격으로 올려졌습니다!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("모루 구매 입력"))) {
                        e.setLine(0, E.getConfigString("모루 구매 설정"));
                        p.sendMessage("§6자동 모루 구매가 §c" + i + " §6의 가격으로 올려졌습니다!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("인첸트 구매 입력"))) {
                        e.setLine(0, E.getConfigString("인첸트 구매 설정"));
                        p.sendMessage("§6자동 인첸트 구매가 §c" + i + " §6의 가격으로 올려졌습니다!");
                    } return;
                } catch (Exception eeeee) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "가격은 숫자로 해야됩니다!");
                    return;
                }
            }
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "가격은 숫자로 해야됩니다!");
        }
    }
	
	@SuppressWarnings("static-access")
	public static boolean isSign(Block block)
	{
		Location l = block.getLocation();
		Block o = block.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ() - 1);
		Block t = block.getWorld().getBlockAt(l.getBlockX() - 1, l.getBlockY(), l.getBlockZ());
		Block th = block.getWorld().getBlockAt(l.getBlockX(), l.getBlockY(), l.getBlockZ() + 1);
		Block f = block.getWorld().getBlockAt(l.getBlockX() + 1, l.getBlockY(), l.getBlockZ());

		if (o.getType() == Material.WALL_SIGN) sign = (Sign)o.getState();
		else if (f.getType() == Material.WALL_SIGN) sign = (Sign)f.getState();
		else if (t.getType() == Material.WALL_SIGN) sign = (Sign)t.getState();
		else if (th.getType() == Material.WALL_SIGN) sign = (Sign)th.getState();
		else {
	        for (final Location la : getList(l, 2, false)) {
	        	Block lb = block.getWorld().getBlockAt(la.getBlockX(), la.getBlockY(), la.getBlockZ());
	            if (lb.getType() == Material.WALL_SIGN) {
	            	sign = (Sign)lb.getState();
	        		String text = sign.getLine(0);
	        		if (text.equals(E.getConfigString("집 구매 설정")) || text.equals(E.getConfigString("창고 구매 설정")) || text.equals(E.getConfigString("인첸트 구매 설정")) || text.equals(E.getConfigString("모루 구매 설정")))
	        			return true;
	        		else continue;
	            } continue;
	        } return false;
		}
		
		String text = sign.getLine(0);
		
		if (text.equals(E.getConfigString("집 구매 설정")) || text.equals(E.getConfigString("창고 구매 설정")) || text.equals(E.getConfigString("인첸트 구매 설정")) || text.equals(E.getConfigString("모루 구매 설정"))) {
			return true;
		} return false;
	}
	
	/* SHMB의 소스중 일부 */
    public static List<Location> getList(final Location location, final int radius, final boolean hollow) {
        final List<Location> circleBlocks = new ArrayList<Location>();
        final int bX = location.getBlockX();
        final int bY = location.getBlockY();
        final int bZ = location.getBlockZ();
        for (int x = bX - radius; x <= bX + radius; ++x) {
            for (int y = bY - radius; y <= bY + radius; ++y) {
                for (int z = bZ - radius; z <= bZ + radius; ++z) {
                    final double distance = (bX - x) * (bX - x) + (bZ - z) * (bZ - z) + (bY - y) * (bY - y);
                    if (distance < radius * radius && (!hollow || distance >= (radius - 1) * (radius - 1))) {
                        final Location l = new Location(location.getWorld(), (double)x, (double)y, (double)z);
                        circleBlocks.add(l);
                    }
                }
            }
        } return circleBlocks;
    }
}
