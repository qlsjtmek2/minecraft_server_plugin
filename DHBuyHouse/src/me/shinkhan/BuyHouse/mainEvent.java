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
					p.sendMessage("��6�� �������� ����� �÷������� ��cDHBuyHouse ��6�� ����ֽ��ϴ�!");
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
                
                if (s.getLine(0).contains(E.getConfigString("�� ���� ����"))) {
                	if (E.getConfigInt("���δ� �ִ� �� ���� ����") >= 0) {
                		if (E.getInfoInt(p, "�� ����") < E.getConfigInt("���δ� �ִ� �� ���� ����")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("��c������ ���� �����մϴ�.");
                                p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "�� ����") + 1;
                            E.setInfoInt(p, "�� ����", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                            s.setLine(0, E.getConfigString("Lockette ǥ����"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("��c����� �̹� �� �ִ� ���� �ѵ��� �ʰ��߽��ϴ�.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("��c������ ���� �����մϴ�.");
                            p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "�� ����") + 1;
                        E.setInfoInt(p, "�� ����", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                        s.setLine(0, E.getConfigString("Lockette ǥ����"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("â�� ���� ����"))) {
                	if (E.getConfigInt("���δ� �ִ� â�� ���� ����") >= 0) {
                		if (E.getInfoInt(p, "â�� ����") < E.getConfigInt("���δ� �ִ� â�� ���� ����")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("��c������ ���� �����մϴ�.");
                                p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "â�� ����") + 1;
                            E.setInfoInt(p, "â�� ����", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                            s.setLine(0, E.getConfigString("Lockette ǥ����"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("��c����� �̹� �� �ִ� ���� �ѵ��� �ʰ��߽��ϴ�.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("��c������ ���� �����մϴ�.");
                            p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "â�� ����") + 1;
                        E.setInfoInt(p, "â�� ����", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                        s.setLine(0, E.getConfigString("Lockette ǥ����"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("��� ���� ����"))) {
                	if (E.getConfigInt("���δ� �ִ� ��� ���� ����") >= 0) {
                		if (E.getInfoInt(p, "��� ����") < E.getConfigInt("���δ� �ִ� ��� ���� ����")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("��c������ ���� �����մϴ�.");
                                p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "��� ����") + 1;
                            E.setInfoInt(p, "��� ����", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                            s.setLine(0, E.getConfigString("Lockette ǥ����"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("��c����� �̹� �� �ִ� ���� �ѵ��� �ʰ��߽��ϴ�.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("��c������ ���� �����մϴ�.");
                            p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "��� ����") + 1;
                        E.setInfoInt(p, "��� ����", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                        s.setLine(0, E.getConfigString("Lockette ǥ����"));
                        s.setLine(1, p.getName());
                        s.setLine(2, "");
                        s.setLine(3, "");
                        s.update();
                	}
                }
                
                else if (s.getLine(0).contains(E.getConfigString("��þƮ ���� ����"))) {
                	if (E.getConfigInt("���δ� �ִ� ��þƮ ���� ����") >= 0) {
                		if (E.getInfoInt(p, "��þƮ ����") < E.getConfigInt("���δ� �ִ� ��þƮ ���� ����")) {
                           	double dds = main.economy.getBalance(p.getName());
                           	int sdsd = Integer.parseInt(s.getLine(1));
                            if (dds < sdsd) {
                                p.sendMessage("��c������ ���� �����մϴ�.");
                                p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                                return;
                            }
                            
                            int i = E.getInfoInt(p, "��þƮ ����") + 1;
                            E.setInfoInt(p, "��þƮ ����", i);
                            main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                            p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                            s.setLine(0, E.getConfigString("Lockette ǥ����"));
                            s.setLine(1, p.getName());
                            s.setLine(2, "");
                            s.setLine(3, "");
                            s.update();
                		} else {
                			p.sendMessage("��c����� �̹� �� �ִ� ���� �ѵ��� �ʰ��߽��ϴ�.");
                			return;
                		}
                	} else {
                       	double dds = main.economy.getBalance(p.getName());
                       	int sdsd = Integer.parseInt(s.getLine(1));
                        if (dds < sdsd) {
                            p.sendMessage("��c������ ���� �����մϴ�.");
                            p.sendMessage("��c���� : ��4" + sdsd + " ��c���� ���� �� : ��4" + (int)dds);
                            return;
                        }
                        
                        int i = E.getInfoInt(p, "��þƮ ����") + 1;
                        E.setInfoInt(p, "��þƮ ����", i);
                        main.economy.withdrawPlayer(p.getName(), (double)sdsd);
                        p.sendMessage("��6���������� ��c���š�6�ϼ̽��ϴ�.");
                        s.setLine(0, E.getConfigString("Lockette ǥ����"));
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
        if (e.getLine(0).equalsIgnoreCase(E.getConfigString("�� ���� �Է�")) || e.getLine(0).equalsIgnoreCase(E.getConfigString("â�� ���� �Է�")) 
         || e.getLine(0).equalsIgnoreCase(E.getConfigString("��� ���� �Է�")) || e.getLine(0).equalsIgnoreCase(E.getConfigString("��þƮ ���� �Է�"))) {
            if (!p.isOp()) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "����� ǥ������ ���� ������ �����ϴ�.");
                return;
            }
            if (!e.getLine(1).isEmpty()) {
                try {
                	final int i = Integer.parseInt(e.getLine(1));
                	
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("�� ���� �Է�"))) {
                        e.setLine(0, E.getConfigString("�� ���� ����"));
                        p.sendMessage("��6�ڵ� �� ���Ű� ��c" + i + " ��6�� �������� �÷������ϴ�!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("â�� ���� �Է�"))) {
                        e.setLine(0, E.getConfigString("â�� ���� ����"));
                        p.sendMessage("��6�ڵ� â�� ���Ű� ��c" + i + " ��6�� �������� �÷������ϴ�!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("��� ���� �Է�"))) {
                        e.setLine(0, E.getConfigString("��� ���� ����"));
                        p.sendMessage("��6�ڵ� ��� ���Ű� ��c" + i + " ��6�� �������� �÷������ϴ�!");
                    }
                    if (e.getLine(0).equalsIgnoreCase(E.getConfigString("��þƮ ���� �Է�"))) {
                        e.setLine(0, E.getConfigString("��þƮ ���� ����"));
                        p.sendMessage("��6�ڵ� ��þƮ ���Ű� ��c" + i + " ��6�� �������� �÷������ϴ�!");
                    } return;
                } catch (Exception eeeee) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "������ ���ڷ� �ؾߵ˴ϴ�!");
                    return;
                }
            }
            e.setCancelled(true);
            p.sendMessage(ChatColor.RED + "������ ���ڷ� �ؾߵ˴ϴ�!");
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
	        		if (text.equals(E.getConfigString("�� ���� ����")) || text.equals(E.getConfigString("â�� ���� ����")) || text.equals(E.getConfigString("��þƮ ���� ����")) || text.equals(E.getConfigString("��� ���� ����")))
	        			return true;
	        		else continue;
	            } continue;
	        } return false;
		}
		
		String text = sign.getLine(0);
		
		if (text.equals(E.getConfigString("�� ���� ����")) || text.equals(E.getConfigString("â�� ���� ����")) || text.equals(E.getConfigString("��þƮ ���� ����")) || text.equals(E.getConfigString("��� ���� ����"))) {
			return true;
		} return false;
	}
	
	/* SHMB�� �ҽ��� �Ϻ� */
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
