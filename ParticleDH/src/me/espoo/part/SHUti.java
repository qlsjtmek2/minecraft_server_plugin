package me.espoo.part;

import java.io.*;
import java.net.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.plugin.*;

public class SHUti {
	public ArrayList<String> SHPluginsCannotUseThisPluginlist = new ArrayList<String>();
	public String pluginname;
	public String version;
	public String gong;

	public SHUti(PluginDescriptionFile description) {
		this.pluginname = description.getName();
		this.version = description.getVersion();
		if(org.bukkit.Bukkit.getVersion().contains("1.5")){
			this.bc(this.getPerfix() + "1.6 이상의 신버젼을 애용해주세요!");
		}
		try {
			URL SHPluginsBlacklistGetURL = new URL("http://shplugin.dothome.co.kr/blacklist.html");
			SHPlsHttpURLConnection = (HttpURLConnection)SHPluginsBlacklistGetURL.openConnection();
			SHPlsHttpURLConnection.setRequestMethod("GET");
			SHPlsHttpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader SHPluginsBufferReader = new BufferedReader(new InputStreamReader(SHPlsHttpURLConnection.getInputStream()));
			String SHPlsHttpURLConnectionsReadline;
			while((SHPlsHttpURLConnectionsReadline = SHPluginsBufferReader.readLine()) != null){
				SHPluginsCannotUseThisPluginlist.add(SHPlsHttpURLConnectionsReadline);
			}
			SHPluginsBufferReader.close();
		} catch (Exception e) {}
	}
	public Integer rand(Integer i){
		return new Random().nextInt(i);
	}
	@SuppressWarnings("deprecation")
	public void upInv(Player player){
		player.updateInventory();
	}
	public String getPerfix(){
		return "" + ChatColor.RED + ChatColor.BOLD + "<< " + ChatColor.YELLOW + this.pluginname + ChatColor.RED + ChatColor.BOLD +  " >> " + ChatColor.WHITE;
	}
	public ItemStack createItem(int typeId, int amount, String name, ArrayList<String> lore){
		ItemStack i = new ItemStack(typeId, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	public ItemStack createItem(int typeId, int amount, String name){
		ItemStack i = new ItemStack(typeId, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
	public ItemStack createItem(Material type, int amount, String name, ArrayList<String> lore){
		ItemStack i = new ItemStack(type, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	public ItemStack createItem(Material type, int amount, String name){
		ItemStack i = new ItemStack(type, amount);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
	public String getEntityName(Entity entity){
		String name = "";
		if(entity instanceof Chicken){
			name = "닭";
		}else if(entity instanceof Pig){
			name = "돼지";
		}else if(entity instanceof Cow){
			name = "소";
		}else if(entity instanceof Sheep){
			name = "양";
		}else if(entity instanceof Squid){
			name = "오징어";
		}else if(entity instanceof Wolf){
			name = "늑대";
		}else if(entity instanceof Spider){
			name = "거미";
		}else if(entity instanceof Skeleton){
			name = "스켈레톤";
		}else if(entity instanceof Zombie){
			name = "좀비";
		}else if(entity instanceof Creeper){
			name = "크리퍼";
		}else if(entity instanceof Ghast){
			name = "가스트";
		}else if(entity instanceof IronGolem){
			name = "철골램";
		}else if(entity instanceof Slime){
			name = "슬라임";
		}else if(entity instanceof PigZombie){
			name = "좀비피그맨";
		}else if(entity instanceof Villager){
			name = "주민";
		}else if(entity instanceof Enderman){
			name = "엔더맨";
		}else if(entity instanceof EnderDragon){
			name = "엔더드래곤";
		}else if(entity instanceof Witch){
			name = "마녀";
		}else if(entity instanceof Bat){
			name = "박쥐";
		}else if(entity instanceof Player){
			name = ((Player) entity).getName();
		}else{
			name = entity.getType().toString();
		}
		return name;
	}
	public boolean isLastVersion(String pluginName, String nowVersion){
		try{
			URL myurl = new URL("http://shplugin.dothome.co.kr/version.html");
			HashMap<String, String> list = new HashMap<String, String>();
			HttpURLConnection con;
			con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while((line = br.readLine()) != null){
				list.put(line.split(":")[0], line.split(":")[1]);
			}
			if(list.get(pluginName).equals(nowVersion)){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			
			System.out.println("오류");
			return true;
		}
	}
	public void RandomFirework(Location l){
	      Firework fw = (Firework)l.getWorld().spawnEntity(l, EntityType.FIREWORK);
	      FireworkMeta fm = fw.getFireworkMeta();
	      Random r = new Random();
	      int fType = r.nextInt(5) + 1;
	      FireworkEffect.Type type = FireworkEffect.Type.BALL;
	      switch (fType) {
	      case 1:
	      default:
	        type = FireworkEffect.Type.BALL;
	        break;
	      case 2:
	        type = FireworkEffect.Type.BURST;
	        break;
	      case 3:
	        type = FireworkEffect.Type.CREEPER;
	        break;
	      case 4:
	        type = FireworkEffect.Type.STAR;
	        break;
	      case 5:
	        type = FireworkEffect.Type.BALL_LARGE;
	      }
	      FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(Color.RED).with(type).trail(r.nextBoolean()).build();
	      fm.addEffect(effect);
	      int power = r.nextInt(2) + 1;
	      fm.setPower(power);
	      fw.setFireworkMeta(fm);
	}
	
	public String getServerAddress(){
		try {
			String ip = "";
			URL myurl = new URL("http://shplugin.dothome.co.kr/getIP.php");
			HttpURLConnection con;
			con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while((line = br.readLine()) != null){
				ip = line;
			}
			br.close();
			return ip;
		} catch (Exception e) {
			return null;
		}
	}
	public boolean isOp(Player p){
		return p.isOp();
	}
	public boolean hasPerm(Player p, String permissions){
		return p.hasPermission(permissions);
	}
	public String addColor(String content){
		return content.replace("&", "§");
	}
	public void msg(Player player, String content){
		player.sendMessage(main.pr + content);
		return;
	}
	public void msg(CommandSender sender, String content){
		sender.sendMessage(main.pr + content);
		return;
	}
	public void teleport(Player player, Location loc){
		player.teleport(loc);
		return;
	}
	public void teleport(Player player, Entity target){
		player.teleport(target);
		return;
	}
	public void print(String content){
		Bukkit.getConsoleSender().sendMessage(content);
		return;
	}
	public void bc(String content){
		Bukkit.broadcastMessage(content);
		return;
	}
	public void fileSave(String Folder, String File, String content){
		   File f = new File(File);
		   File folder = new File(Folder);
		   try {
			   if (!f.exists()) {
				   folder.mkdirs();
				   f.createNewFile();
			   }
			   BufferedWriter BW = new BufferedWriter(new FileWriter(f));
			   BW.append(content);	  
			   BW.flush();
			   BW.close();
		   }
		   catch (IOException localIOException) {
		   }
	}
	public String fileLoad(String Filename){
		File f = new File(Filename);
		String content = "";
		try {
			if (!f.exists()) {
				return "";
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			boolean a = true;
			while ((s=br.readLine())!=null) {
				if(a){
					content = s;
					a = false;
					continue;
				}
				content += "\n" + s;
			}
			br.close();
		}
		catch (IOException localIOException) {
		}
		return content;
	}
	HttpURLConnection SHPlsHttpURLConnection = null;
	}
