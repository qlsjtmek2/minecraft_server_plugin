package me.espoo.part;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class main extends JavaPlugin implements Listener{
	public static HashMap<String, ArrayList<Byte>> effect = new HashMap<String, ArrayList<Byte>>();
	public static HashMap<String, Byte> noweffect = new HashMap<String, Byte>();
	public static HashMap<String, Boolean> effectup = new HashMap<String, Boolean>();
	public HashMap<String, Double> d = new HashMap<String, Double>();
	public static Timer effectTimer = new Timer();
	public boolean guitwoopen = false;
	final double max = 6.3;
	final double min = 0.3;
	SHUti util;
	
	static String pr = ChatColor.WHITE + "[" + ChatColor.BLUE + "ġ��" + ChatColor.WHITE + "] ";
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this,this);
		for(Player p : Bukkit.getOnlinePlayers()){
			Load(p.getName());
			if(noweffect.get(p.getName()) == null){
				noweffect.put(p.getName(), (byte)0);
			}
		}
		if(!(this.getServer().getVersion().contains("1.5")
				||this.getServer().getVersion().contains("1.4"))){
			guitwoopen = true;
		}
		util = new SHUti(this.getDescription());
		effectTimer.schedule(new Runa(this), 50L, 50L);
	}
	@Override
	public void onDisable(){
		for(Player p : Bukkit.getOnlinePlayers())
			Save(p.getName());
		effectTimer.cancel();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Load(e.getPlayer().getName());
		if(effect.get(e.getPlayer().getName()) == null){
			effect.put(e.getPlayer().getName(), new ArrayList<Byte>());
		}
		if(noweffect.get(e.getPlayer().getName()) == null){
			noweffect.put(e.getPlayer().getName(), (byte)0);
		}
		Save(e.getPlayer().getName());
	}
	@EventHandler
	public void quit(PlayerQuitEvent e){
		Save(e.getPlayer().getName());
	}
	

	@EventHandler
	public void onGo(PlayerInteractEvent e){
		if(e.getAction() == Action.PHYSICAL){return;}
		Player p = e.getPlayer();
		if(p.getItemInHand() != null){
			try{
				if(p.getItemInHand().getType() == Material.ENCHANTED_BOOK && p.getItemInHand().getItemMeta().getDisplayName().equals(pr + ChatColor.GREEN + "����Ʈ ����")){
					String n = p.getItemInHand().getItemMeta().getLore().get(0);
					if(effect.get(p.getName()) == null){
						effect.put(p.getName(), new ArrayList<Byte>());
					}
					addEffect(p.getName(), n);
					p.setItemInHand(new ItemStack(Material.AIR));
				}
			}catch(Exception ex){}
		}
	}
	private void addEffect(String name, String n) {
		byte b = (byte)0;
		ArrayList<Byte> list = effect.get(name);
		if(n.equals(ChatColor.GOLD + "ANGRY_VILLAGER")){
			b = (byte)1;
		}else if(n.equals(ChatColor.GOLD + "ENCHANTMENT_TABLE")){
			b = (byte)2;
		}else if(n.equals(ChatColor.GOLD + "HAPPY_VILLAGER")){
			b = (byte)3;
		}else if(n.equals(ChatColor.GOLD + "HEART")){
			b = (byte)4;
		}else if(n.equals(ChatColor.GOLD + "INSTANT_SPELL")){
			b = (byte)5;
		}else if(n.equals(ChatColor.GOLD + "LAVA")){
			b = (byte)6;
		}else if(n.equals(ChatColor.GOLD + "MAGIC_CIRT")){
			b = (byte)7;
		}else if(n.equals(ChatColor.GOLD + "MOB_SPELL")){
			b = (byte)8;
		}else if(n.equals(ChatColor.GOLD + "MOB_SPELL_AMBIENT")){
			b = (byte)9;
		}else if(n.equals(ChatColor.GOLD + "NOTE")){
			b = (byte)10;
		}else if(n.equals(ChatColor.GOLD + "RED_DUST")){
			b = (byte)11;
		}else if(n.equals(ChatColor.GOLD + "SLIME")){
			b = (byte)12;
		}else if(n.equals(ChatColor.GOLD + "SNOWBALL_POOF")){
			b = (byte)13;
		}else if(n.equals(ChatColor.GOLD + "SPELL")){
			b = (byte)14;
		}else if(n.equals(ChatColor.GOLD + "SPLASH")){
			b = (byte)15;
		}else if(n.equals(ChatColor.GOLD + "WITCH_MAGIC")){
			b = (byte)16;
		}
		if(!list.contains(b)){
			list.add(b);
		}
		effect.put(name, list);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		if(e.getInventory().getName().equals(pr + "��0��ƼŬ ����")){
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			try{
				if(e.getCurrentItem().getType() == Material.ENCHANTED_BOOK && e.getCurrentItem().getItemMeta().getDisplayName().equals(pr + ChatColor.GREEN + "����Ʈ ����")){
					String n = e.getCurrentItem().getItemMeta().getLore().get(0);
					setEffect(p.getName(), n);
					p.closeInventory();
					if(!guitwoopen){
						util.msg(p, "����Ʈ�� �����Ǿ����ϴ�!");
						return;
					}
					Inventory inv = Bukkit.createInventory(null, 9, pr + "��ƼŬ ��ġ ����");
					ItemStack i = new ItemStack(Material.PISTON_MOVING_PIECE);
					ItemStack up = util.createItem(Material.SKULL_ITEM, 1, ChatColor.YELLOW + "�Ӹ� ��");
					ItemStack down = util.createItem(Material.DIAMOND_CHESTPLATE, 1, ChatColor.GREEN + "�� ����");
					inv.setContents(new ItemStack[]{i,i,i,up,i,down,i,i,i});
					p.openInventory(inv);
				}else if(e.getCurrentItem().getType() == Material.FIRE && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "����")){
					noweffect.put(p.getName(), (byte)0);
					util.msg(p,"����Ʈ�� ���ŵǾ����ϴ�.");
					p.closeInventory();
				}
			}catch(Exception ex){}
		}else if(e.getInventory().getName().equals(pr + "��ƼŬ ��ġ ����")){
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			try{
				if(e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "�� ����")){
					effectup.put(p.getName(), false);
				}else if(e.getCurrentItem().getType() == Material.SKULL_ITEM && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "�Ӹ� ��")){
					effectup.put(p.getName(), true);
				}else{
					return;
				}
				util.msg(p, "����Ʈ�� �����Ǿ����ϴ�!");
				p.closeInventory();
				return;
			}catch(Exception ex){}
		}
	}
	private void setEffect(String name, String n) {
		byte b = (byte)0;
		if(n.equals(ChatColor.GOLD + "ANGRY_VILLAGER")){
			b = (byte)1;
		}else if(n.equals(ChatColor.GOLD + "ENCHANTMENT_TABLE")){
			b = (byte)2;
		}else if(n.equals(ChatColor.GOLD + "HAPPY_VILLAGER")){
			b = (byte)3;
		}else if(n.equals(ChatColor.GOLD + "HEART")){
			b = (byte)4;
		}else if(n.equals(ChatColor.GOLD + "INSTANT_SPELL")){
			b = (byte)5;
		}else if(n.equals(ChatColor.GOLD + "LAVA")){
			b = (byte)6;
		}else if(n.equals(ChatColor.GOLD + "MAGIC_CIRT")){
			b = (byte)7;
		}else if(n.equals(ChatColor.GOLD + "MOB_SPELL")){
			b = (byte)8;
		}else if(n.equals(ChatColor.GOLD + "MOB_SPELL_AMBIENT")){
			b = (byte)9;
		}else if(n.equals(ChatColor.GOLD + "NOTE")){
			b = (byte)10;
		}else if(n.equals(ChatColor.GOLD + "RED_DUST")){
			b = (byte)11;
		}else if(n.equals(ChatColor.GOLD + "SLIME")){
			b = (byte)12;
		}else if(n.equals(ChatColor.GOLD + "SNOWBALL_POOF")){
			b = (byte)13;
		}else if(n.equals(ChatColor.GOLD + "SPELL")){
			b = (byte)14;
		}else if(n.equals(ChatColor.GOLD + "SPLASH")){
			b = (byte)15;
		}else if(n.equals(ChatColor.GOLD + "WITCH_MAGIC")){
			b = (byte)16;
		}else{
			return;
		}
		if(!effectup.containsKey(name)){
			effectup.put(name, false);
		}
		noweffect.put(name, b);
	}
	public static void Load(String n){
		File f = new File("plugins\\ParticleDH\\data\\" + n + ".dat");
		try {
			if (!f.exists()) {
				new File("plugins\\ParticleDH\\data\\").mkdirs();
				f.createNewFile();
				ArrayList<Byte> list = new ArrayList<Byte>();
				list.add((byte)0);
				effect.put(n, list);
				return;
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			ArrayList<Byte> list = new ArrayList<Byte>();
			while ((s=br.readLine())!=null) {
				try{
					list.add(Byte.parseByte(s));
				}
				catch(NumberFormatException e){
					list.add((byte)0);
				}
			}
			effect.put(n, list);
			br.close();
		}
		catch (IOException localIOException) {
	    }
	}

	public static void Save(String n){
		File f = new File("plugins\\ParticleDH\\data\\" + n + ".dat");
		File folder = new File("plugins\\ParticleDH\\data\\");
		try {
			if (!f.exists()) {
				folder.mkdirs();
				f.createNewFile();	
			}
			BufferedWriter BW = new BufferedWriter(new FileWriter(f));
			String to = "";
			if(effect.get(n) == null){
				to = "0";
			}else{
				boolean bb = true;
				for(Byte b : effect.get(n)){
					if(bb){
						to = Byte.toString(b);
						bb = false;
					}else{
						to += "\n" + Byte.toString(b);
					}
				}
			}
			BW.append(to);
			BW.flush();
			BW.close();
		}catch (Exception Exception) {
			System.out.println(Exception);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if(label.equalsIgnoreCase("ġ��") && args.length == 1){
			if(sender instanceof Player){
				if(args[0].equalsIgnoreCase("head")
						||args[0].equalsIgnoreCase("0")
						||args[0].equalsIgnoreCase("h")
						||args[0].equalsIgnoreCase("�Ӹ�")){
					util.msg(sender, "���� ����� ����Ʈ�� �Ӹ� ���� �ֽ��ϴ�.");
					effectup.put(((Player)sender).getName(), true);
				}else if(args[0].equalsIgnoreCase("body")
						||args[0].equalsIgnoreCase("1")
						||args[0].equalsIgnoreCase("b")
						||args[0].equalsIgnoreCase("����")){
					util.msg(sender, "���� ����� ����Ʈ�� �� �ֺ��� �ֽ��ϴ�.");
					effectup.put(((Player)sender).getName(), false);
				}else{
					util.msg(sender, "��ɾ� ���");
					util.msg(sender, " /ġ��");
					util.msg(sender, " /ġ�� [head|0|h|�Ӹ�]");
					util.msg(sender, " /ġ�� [body|1|b|����]");
				}
				return true;
			}else{
				util.msg(sender, "�÷��̾ ����� �� �ֽ��ϴ�.");
				return true;
			}
		}else if(label.equalsIgnoreCase("ġ��")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				Inventory i = Bukkit.createInventory(null, 18, pr + "��0��ƼŬ ����");
				int now = 0;
				if(effect.get(p.getName()) == null){
					Load(p.getName());
					if(effect.get(p.getName()) == null){
						effect.put(p.getName(), new ArrayList<Byte>());
					}
				}
				for(Byte b : effect.get(p.getName())){
					String n = "";
					if(b == 0){
						continue;
					}else{
						if(b == 1){
							n = "ANGRY_VILLAGER";
						}else if(b == 2){
							n = "ENCHANTMENT_TABLE";
						}else if(b == 3){
							n = "HAPPY_VILLAGER";
						}else if(b == 4){
							n = "HEART";
						}else if(b == 5){
							n = "INSTANT_SPELL";
						}else if(b == 6){
							n = "LAVA";
						}else if(b == 7){
							n = "MAGIC_CIRT";
						}else if(b == 8){
							n = "MOB_SPELL";
						}else if(b == 9){
							n = "MOB_SPELL_AMBIENT";
						}else if(b == 10){
							n = "NOTE";
						}else if(b == 11){
							n = "RED_DUST";
						}else if(b == 12){
							n = "SLIME";
						}else if(b == 13){
							n = "SNOWBALL_POOF";
						}else if(b == 14){
							n = "SPELL";
						}else if(b == 15){
							n = "SPLASH";
						}else if(b == 16){
							n = "WITCH_MAGIC";
						}
						ArrayList<String> lst = new ArrayList<String>();
						lst.add(ChatColor.GOLD + n);
						i.setItem(now, util.createItem(Material.ENCHANTED_BOOK, 1, pr + ChatColor.GREEN + "����Ʈ ����", lst));
						now += 1;
					}
				}
				i.setItem(now, util.createItem(Material.FIRE, 1, ChatColor.RED + "����"));
				p.openInventory(i);
			}else{
				util.msg(sender, "�÷��̾ ����� �� �ֽ��ϴ�.");
				return true;
			}
			return true;
		}
		if(!sender.isOp()){
			util.msg(sender, "������ �����ϴ�.");
			return true;
		}
		if(args.length != 2 && args.length != 1  && args.length != 0){
			util.msg(sender, "��ɾ �ùٸ��� �ʽ��ϴ�.");
			return true;
		}
		if(!sender.isOp()){
			util.msg(sender, "������ �����ϴ�.");
			return true;
		}
		if(args.length == 0 || args[0].equals("����")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				util.msg(p, "/SHParticle <����Ʈ>");
				util.msg(p, "/SHParticle <����Ʈ> <�÷��̾�>");
				util.msg(p, "����Ʈ ���");
				util.msg(p, "ANGRY_VILLAGER, ENCHANTMENT_TABLE, HAPPY_VILLAGER, HEART");
				util.msg(p, "INSTANT_SPELL, LAVA, MAGIC_CIRT, MOB_SPELL");
				util.msg(p, "MOB_SPELL_AMBIENT, NOTE, RED_DUST, SLIME");
				util.msg(p, "SNOWBALL_POOF, SPELL, SPLASH, WITCH_MAGIC");
				return true;
			}else{
				util.msg(sender, "/SHParticle <����Ʈ> <�÷��̾�>");
				util.msg(sender, "����Ʈ ���");
				util.msg(sender, "ANGRY_VILLAGER, ENCHANTMENT_TABLE, HAPPY_VILLAGER, HEART");
				util.msg(sender, "INSTANT_SPELL, LAVA, MAGIC_CIRT, MOB_SPELL");
				util.msg(sender, "MOB_SPELL_AMBIENT, NOTE, RED_DUST, SLIME");
				util.msg(sender, "SNOWBALL_POOF, SPELL, SPLASH, WITCH_MAGIC");
				return true;
			}
		}else if(args.length == 1){
			if(!(sender instanceof Player)){
				util.msg(sender, "�÷��̾ ����� �� �ֽ��ϴ�.");
				return true;
			}
			ArrayList<String> lst = new ArrayList<String>();
			Player p = (Player) sender;
			if(args[0].equals("ANGRY_VILLAGER") || args[0].equals("ENCHANTMENT_TABLE") || args[0].equals("HAPPY_VILLAGER") || args[0].equals("HEART") || args[0].equals("INSTANT_SPELL") || args[0].equals("LAVA") || args[0].equals("MAGIC_CIRT") || args[0].equals("MOB_SPELL") || args[0].equals("MOB_SPELL_AMBIENT") || args[0].equals("NOTE") || args[0].equals("RED_DUST") || args[0].equals("SLIME") || args[0].equals("SNOWBALL_POOF") || args[0].equals("SPELL") || args[0].equals("SPLASH") || args[0].equals("WITCH_MAGIC")){
				lst.add(ChatColor.GOLD + args[0]);
				ItemStack i = util.createItem(Material.ENCHANTED_BOOK, 1, pr + ChatColor.GREEN + "����Ʈ ����", lst);
				p.getInventory().addItem(i);
				util.upInv(p);
				util.msg(p, args[0] + " ����Ʈ�� ������ �Ϸ�Ǿ����ϴ�.");
			}else{
				util.msg(p, "����Ʈ�ڵ尡 �ùٸ��� �ʽ��ϴ�.");
			}
			return true;
		}else if(args.length == 2 && this.getServer().getPlayerExact(args[1]) != null){
			ArrayList<String> lst = new ArrayList<String>();
			Player p = this.getServer().getPlayerExact(args[1]);
			if(args[0].equals("ANGRY_VILLAGER") || args[0].equals("ENCHANTMENT_TABLE") || args[0].equals("HAPPY_VILLAGER") || args[0].equals("HEART") || args[0].equals("INSTANT_SPELL") || args[0].equals("LAVA") || args[0].equals("MAGIC_CIRT") || args[0].equals("MOB_SPELL") || args[0].equals("MOB_SPELL_AMBIENT") || args[0].equals("NOTE") || args[0].equals("RED_DUST") || args[0].equals("SLIME") || args[0].equals("SNOWBALL_POOF") || args[0].equals("SPELL") || args[0].equals("SPLASH") || args[0].equals("WITCH_MAGIC")){
				lst.add(ChatColor.GOLD + args[0]);
				ItemStack i = util.createItem(Material.ENCHANTED_BOOK, 1, pr + ChatColor.GREEN + "����Ʈ ����", lst);
				p.getInventory().addItem(i);
				util.upInv(p);
				util.msg(p ,args[0] + " ����Ʈ���� ���޵Ǿ����ϴ�.");
				util.msg(sender, args[0] + " ����Ʈ�� ������ �Ϸ�Ǿ����ϴ�.");
			}else{
				util.msg(p, "����Ʈ�� �ùٸ��� �ʽ��ϴ�.");
			}
		}else{
			sender.sendMessage("��ɾ �ùٸ��� �ʽ��ϴ�.");
			sender.sendMessage("/SHParticle ����");
		}
		return true;
	}
}

class Runa extends TimerTask{
	main main;
	public Runa(main main){
		this.main = main;
	}
	@SuppressWarnings("static-access")
	public void run(){
		for(Player p:Bukkit.getOnlinePlayers()){
			if(main.noweffect.containsKey(p.getName()) && main.effectup.containsKey(p.getName())){
				if(main.effectup.get(p.getName())){
					switch(main.noweffect.get(p.getName())){
					case (byte)1:
						try {
							Particle.sendToLocation(Particle.ANGRY_VILLAGER, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)2:
						try {
							Particle.sendToLocation(Particle.ENCHANTMENT_TABLE,  p.getLocation().add(0D, 1D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 10, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)3:
						try {
							Particle.sendToLocation(Particle.HAPPY_VILLAGER, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)4:
						try {
							Particle.sendToLocation(Particle.HEART, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)5:
						try {
							Particle.sendToLocation(Particle.INSTANT_SPELL, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)6:
						try {
							if(new Random().nextInt(5) == 1)
								Particle.sendToLocation(Particle.LAVA, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)7:
						try {
							Particle.sendToLocation(Particle.MAGIC_CRIT, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)8:
						try {
							Particle.sendToLocation(Particle.MOB_SPELL, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)9:
						try {
							Particle.sendToLocation(Particle.MOB_SPELL_AMBIENT, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)10:
						try {
							Particle.sendToLocation(Particle.NOTE, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)11:
						try {
							Particle.sendToLocation(Particle.RED_DUST, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)12:
						try {
							Particle.sendToLocation(Particle.SLIME, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)13:
						try {
							Particle.sendToLocation(Particle.SNOWBALL_POOF, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)14:
						try {
							Particle.sendToLocation(Particle.SPELL, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)15:
						try {
							Particle.sendToLocation(Particle.SPLASH, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					case (byte)16:
						try {
							Particle.sendToLocation(Particle.WITCH_MAGIC, p.getLocation().add(0D, 2D,0D), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
						} catch (Exception e1) {}
						break;
					default:
						break;
					}
				}else{
					 if(main.d.get(p.getName()) == null){
						 main.d.put(p.getName(), main.min);
					 }
					 try {
						 main.d.put(p.getName(), (main.d.get(p.getName()) + main.min));
						 if (main.d.get(p.getName()) > main.max){
							 main.d.put(p.getName(), (main.d.get(p.getName()) + main.max));
						 }
						 switch(main.noweffect.get(p.getName())){
						 case (byte)1:
							try {
								Particle.sendToLocation(Particle.ANGRY_VILLAGER, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
						 	break;
						 case (byte)2:
							try {
								Particle.sendToLocation(Particle.ENCHANTMENT_TABLE, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
						 	break;
						 case (byte)3:
							try {
								Particle.sendToLocation(Particle.HAPPY_VILLAGER, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)4:
							try {
								Particle.sendToLocation(Particle.HEART, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)5:
							try {
								Particle.sendToLocation(Particle.INSTANT_SPELL, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)6:
							try {
								if(new Random().nextInt(5) == 1)
									Particle.sendToLocation(Particle.LAVA, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)7:
							try {
								Particle.sendToLocation(Particle.MAGIC_CRIT, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
								break;
						case (byte)8:
							try {
								Particle.sendToLocation(Particle.MOB_SPELL, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)9:
							try {
								Particle.sendToLocation(Particle.MOB_SPELL_AMBIENT, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)10:
							try {
								Particle.sendToLocation(Particle.NOTE, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)11:
							try {
								Particle.sendToLocation(Particle.RED_DUST, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)12:
							try {
								Particle.sendToLocation(Particle.SLIME, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)13:
							try {
								Particle.sendToLocation(Particle.SNOWBALL_POOF, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)14:
							try {
								Particle.sendToLocation(Particle.SPLASH, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.3F, 0.3F, 0.3F, 2.0F, 20, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)15:
							try {
								Particle.sendToLocation(Particle.SPELL, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						case (byte)16:
							try {
								Particle.sendToLocation(Particle.WITCH_MAGIC, p.getLocation().add(new Vector(Math.cos(main.d.get(p.getName())),1.0,Math.sin(main.d.get(p.getName())))), 0.0F, 0.0F, 0.0F, 2.0F, 1, main.getServer().getVersion());
							} catch (Exception e1) {}
							break;
						default:
							break;
						}
					 } catch (Exception e1) {}
				}
			}
		}
	}
}
