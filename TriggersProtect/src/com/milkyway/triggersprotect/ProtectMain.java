package com.milkyway.triggersprotect;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ProtectMain extends JavaPlugin implements Listener {
	public static String[] cantuse = null;
	Economy eco;
	public static String prefix = ChatColor.WHITE + "[" + ChatColor.DARK_RED + "�˸�" + ChatColor.WHITE + "] ";
	
	public void onEnable() { 
		Bukkit.getPluginManager().registerEvents(this, this);
		SetupEconomy();
		protectfile(); 
	}

	@EventHandler(priority=EventPriority.HIGH)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!e.getPlayer().isOp())
		{
			for (String k : cantuse)
			{
				if ((!e.getMessage().startsWith("/")) || (!e.getMessage().contains("<")) || (!e.getMessage().contains(">")) || (!e.getMessage().contains(k)))
					continue;
				for (Player p : Bukkit.getOnlinePlayers())
				{
					if (p.isOp()) {
						p.sendMessage(prefix + "��c" + e.getPlayer().getName() + "��6���� Ʈ���� ���׻���� �õ��Ͽ����ϴ�! ��ɾ� : ��c" + e.getMessage());
					}
				}
				
				e.getPlayer().kickPlayer(prefix + "��fƮ���� ���׻�� ���Ƿ� ��Ǿ����ϴ�. ��e������ :: milkwyay0308");
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "ban " + e.getPlayer().getName() + " &cƮ���� ���� �ǿ� &f/ �� ��ü ���Ǵ� ī���");
				String g = e.getMessage().replace("<playername>", e.getPlayer().getName());
				String[] b = g.split(" ");
				for (String f : b) {
					try {
						if (!f.contains("givemoney"))
							continue;
						String[] a = b[1].split("<");
						a[1] = a[1].replace(">", "");
						String[] d = a[1].split(":");
						this.eco.withdrawPlayer(d[1], Double.parseDouble(d[2]));
					} catch (Exception localException) {}
					
					try {
						if(f.contains("takitem"))
						{
							String[] a = b[1].split("<");
							a[1] = a[1].replace(">", "");
							String[] d = a[1].split(":");
							Bukkit.getPlayer(d[1]).getInventory().clear(Integer.parseInt(d[2]), Integer.parseInt(d[3]));
						}
					} catch(Exception ex) {}
				}
			}
		}
	}

	public static void protectfile()
	{
		String a = "this,playername,playerdisplayname,playerlistname,playerprefix,playersuffix,helditemname,";
		a = a + "helditemdisplayname,itemid,playerloc,triggerloc,issneaking,issprinting,health,worldname,";
	    a = a + "biome,gamemode,cmdname,cmdargcount,cmdline,cmdarg,whodied,killedbyplayer,killername,blockid,";
	    a = a + "blockdata,blocktype,entitytype,entityname,weather,worldto,worldfrom,sneaking,sprinting,";
	    a = a + "flying,areaentered,areaexited,chatline,chatwordcount,onlineplayeramount,chatword,";
	    a = a + "haspermission,haspotioneffect,currentloc,random0to,random1to,health,issneaking,";
	    a = a + "issprinting,totalexp,relativeloc,hasmoney,givemoney,takemoney,isblocktype,";
	    a = a + "distance,startswith,endswith,direction,secondticks,getarea,hour,min,getblocklos,var,";
	    a = a + "getchar,hasitem,takeitem,giveitem,uuid,food,saturation,playeruuid,playerloc,holdingitem,";
	    a = a + "clickedslot,clickeditem,clickeditemname,inventorytitle,clickeditemlore,eval,systemtime,signtext,sn";
	    Bukkit.getConsoleSender().sendMessage(prefix + "��c������ ������ �����Ǿ����ϴ�.");
	    a = a.replace("null", "");
	    cantuse = a.split(",");
	}

	private boolean SetupEconomy()
	{
		if (getServer().getPluginManager().getPlugin("Vault") == null)
		{
			Bukkit.getConsoleSender().sendMessage(prefix + " ��cSowwy :(��d Vault �÷������� �˻����� �ʾҽ��ϴ�..�� �÷������� Vault�� �ʼ��� �մϴ�.");
			Bukkit.shutdown();
			return false;
		}
		
		Bukkit.getConsoleSender().sendMessage(prefix + "��a��lVault ��a�÷������� �ν� �Ǿ����ϴ�.");
		RegisteredServiceProvider<Economy> EconomyProvider = getServer().getServicesManager().getRegistration(Economy.class);
		if (EconomyProvider != null) {
			this.eco = ((Economy)EconomyProvider.getProvider());
		}
		return this.eco != null;
	}
}