package Main;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
	public void onEnable()
    {
	  getServer().getPluginManager().registerEvents(this, this);
    }
	
	@EventHandler
	public void ArmorTookUpEvent(InventoryClickEvent event)
	{
		int slotid = event.getSlot();
		Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
		if(!player.isOp())
		{
			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
			{
				if(slotid == 39)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
			if(player.getGameMode() == GameMode.CREATIVE)
			{
				if(slotid == 5)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(!player.isOp())
		{
			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
			{
				if(slotid == 38)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
			if(player.getGameMode() == GameMode.CREATIVE)
			{
				if(slotid == 6)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
			
		}
		if(!player.isOp())
		{
			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
			{
				if(slotid == 37)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
			if(player.getGameMode() == GameMode.CREATIVE)
			{
				if(slotid == 7)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
		}
		if(!player.isOp())
		{
			if(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE)
			{
				if(slotid == 36)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
			if(player.getGameMode() == GameMode.CREATIVE)
			{
				if(slotid == 8)
				{
					player.sendMessage("§f[§a안내§f] §c이 아이템은 클릭하실 수 없습니다.");
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
}
