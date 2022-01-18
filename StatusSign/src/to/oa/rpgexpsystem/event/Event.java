package to.oa.rpgexpsystem.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;

import to.oa.tpsw.rpgexpsystem.api.Rpg;
import to.oa.tpsw.rpgexpsystem.api.RpgCalculValue;
import to.oa.tpsw.rpgexpsystem.api.RpgCommon;
import to.oa.tpsw.rpgexpsystem.api.RpgMessage;
import to.oa.tpsw.rpgexpsystem.api.RpgPlayer;
import to.oa.tpsw.rpgexpsystem.bonusitem.BonusItem;
import to.oa.tpsw.rpgexpsystem.command.expgadmin;
import to.oa.tpsw.rpgexpsystem.command.expparty;
import to.oa.tpsw.rpgexpsystem.event.RpgPlayerLevelChangeEvent;
import to.oa.tpsw.rpgexpsystem.guild.Guild;
import to.oa.tpsw.rpgexpsystem.guild.RpgGuild;
import to.oa.tpsw.rpgexpsystem.main.ConfigData;
import to.oa.tpsw.rpgexpsystem.main.CustomConfig2;
import to.oa.tpsw.rpgexpsystem.main.Main;
import to.oa.tpsw.rpgexpsystem.main.YellowAPI;
import to.oa.tpsw.rpgexpsystem.thread.Item10Min;
import to.oa.tpsw.rpgexpsystem.thread.OnPlayerJoin;

public class Event
  implements Listener
{
  public static Map<String, String> item = new HashMap();
  public static Map<String, Boolean> item2 = new HashMap();

  public Event(Main plugin)
  {
  }

  public static void callLevelChangeEvent(RpgPlayer player, int oldlevel, int newlevel, String reason)
  {
    if (oldlevel != newlevel) {
      RpgPlayerLevelChangeEvent event = new RpgPlayerLevelChangeEvent(player, oldlevel, newlevel, reason);
      Bukkit.getPluginManager().callEvent(event);
    }
  }

  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent event) {
    String label = event.getMessage().split(" ")[0];
    Player sender = event.getPlayer();
    if ((label.equalsIgnoreCase("/rpgexpsystem2")) || (label.equalsIgnoreCase("/res2")))
    {
      boolean b = Bukkit.getIp().equals("");
      RpgMessage.checkConsoleMessage(sender, "@6플러그인 버전: @c2.3.9.8");
      RpgMessage.checkConsoleMessage(sender, "@6제작자: @cTPsw");
      RpgMessage.checkConsoleMessage(sender, "@6구매자: @c" + Main.BUYER_NAME);
      RpgMessage.checkConsoleMessage(sender, "@6추가 기능 구매 여부: @ctrue");
      RpgMessage.checkConsoleMessage(sender, "@6커뮤니티 주소: @chttp://cafe.naver.com/espooservers");
      RpgMessage.checkConsoleMessage(sender, "@6기타: @c" + Bukkit.getMotd() + ", " + Bukkit.getServerName() + ", " + Bukkit.getServerId());
      RpgMessage.checkConsoleMessage(sender, "@6서버 버전: @c" + Bukkit.getVersion());
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    String name = event.getPlayer().getName();

    if (!((String)expparty.user.get(name)).equals("@null")) {
      String party = (String)expparty.user.get(name);
      if (((String)expparty.adminlists.get(party)).equals(name)) {
        for (String user : expparty.removeParty(party))
          Rpg.getRpgPlayera(user).sendMessage("§6당신의 파티는 파티장의 부재로 해체되었습니다.");
      }
      else
        expparty.kickPlayer(party, name);
    }
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Thread th = new Thread(new OnPlayerJoin(event.getPlayer()));
    th.setPriority(10);
    th.start();
  }

  public static void onPlayerJoin(Player player) {
    String name = player.getName();
    String name2 = Rpg.getRpgPlayer(name);
    if ((name2.equalsIgnoreCase(name)) && (!name2.equals(name))) {
      player.kickPlayer("이미 같은 문자를 가진 플레이어가 등록되어 있습니다.");
      if (Main.configd.setenchantlevel)
        try {
          player.setLevel(Rpg.getRpgPlayera(name2).getRpgLevel());
        }
        catch (Exception localException) {
        }
      return;
    }

    if (expparty.user.get(name) == null) {
      expparty.user.put(name, "@null");
    }
    expparty.invite.put(name, "@null");
    expparty.chat.put(name, Boolean.valueOf(false));

    item2.put(name, Boolean.valueOf(false));
    if (!Rpg.keys.contains(name)) {
      Rpg.setRpgPlayer(name);
      Rpg.getRpgPlayera(name).reset();
      Rpg.keys.add(name);
      Rpg.offline.add(name);
    }
    Rpg.online.add(name);
    Rpg.getRpgPlayera(name).updateCraftPlayer();
    if (!Rpg.isOp(name)) {
      Rpg.updatePlayerValue(player);
      Rpg.getRpgPlayera(name).updateBonusValue();
    }
  }

  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    if (!event.isCancelled()) {
      String name = event.getPlayer().getName();
      String displayname = event.getPlayer().getDisplayName();
      if ((expgadmin.map.get(name) != null) && (((Boolean)expgadmin.map.get(name)).booleanValue())) {
        event.setCancelled(true);
        if (Guild.hasGuild(name)) {
          RpgGuild guild = Guild.getUserGuild(name);
          Set lists = guild.getUsers();
          String guildname = guild.getName();
          String format1 = Main.config.getString("config.chat.guild.default").replace("{name}", displayname).replace("{message}", event.getMessage()).replace("{guild}", guildname);
          String format2 = Main.config.getString("config.chat.guild.op").replace("{name}", displayname).replace("{message}", event.getMessage()).replace("{guild}", guildname);
          for(Iterator iterator2 = lists.iterator(); iterator2.hasNext();)
          {
              String list = (String)iterator2.next();
            Player targetp = Bukkit.getPlayerExact(list);
            if ((targetp != null) && (targetp.isOnline())) {
              targetp.sendMessage(format1);
            }
          }
          for(Iterator iterator3 = Main.config.getStringList("list.op").iterator(); iterator3.hasNext();)
          {
        	  String op = (String)iterator3.next();
            Player opp = Bukkit.getPlayerExact(op);
            if ((opp != null) && (opp.isOnline()) && (!lists.contains(op))) {
              RpgMessage.sendmessage(opp, format2);
            }
          }
          Bukkit.getConsoleSender().sendMessage(format1);
        }
      } else if (((Boolean)expparty.chat.get(name)).booleanValue()) {
        event.setCancelled(true);
        String partyname = (String)expparty.user.get(name);
        List lists = (List)expparty.userlists.get(partyname);
        String format1 = Main.config.getString("config.chat.party.default").replace("{name}", displayname).replace("{message}", event.getMessage());
        String format2 = Main.config.getString("config.chat.party.op").replace("{name}", displayname).replace("{message}", event.getMessage());
        for(Iterator iterator = lists.iterator(); iterator.hasNext();)
        {
            String list = (String)iterator.next();
          Player targetp = Bukkit.getPlayer(list);
          if ((targetp != null) && (targetp.isOnline())) {
            targetp.sendMessage(format1);
          }
        }
        for(Iterator iterator1 = Main.config.getStringList("list.op").iterator(); iterator1.hasNext();)
        {
            String op = (String)iterator1.next();
          Player opp = Bukkit.getPlayer(op);
          if ((opp != null) && (opp.isOnline()) && (!lists.contains(op))) {
            RpgMessage.sendmessage(opp, format2);
          }
        }
        Bukkit.getConsoleSender().sendMessage(format1);
      } else if ((Guild.hasGuild(name)) && (Main.configd.guildchat)) {
        String format1 = Main.config.getString("config.chat.guild.public").replace("{name}", displayname).replace("{message}", event.getMessage())
          .replace("{guild}", Guild.getUserGuild(name).getName());
        event.setFormat(format1);
      }
    }
  }

  @EventHandler
  public void onBlockBreak(BlockExpEvent event)
  {
    if (Main.configd.digore)
      event.setExpToDrop(0); 
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    ItemStack is = player.getItemInHand();
    ItemMeta im = is.getItemMeta();
    if ((is.getType() != Material.AIR) && (im.getLore() != null) && (
      ((event.getAction() != Action.RIGHT_CLICK_BLOCK) && (event.getAction() != Action.LEFT_CLICK_BLOCK)) || ((!event.isCancelled()) || (event.getAction() == Action.RIGHT_CLICK_AIR) || 
      (event.getAction() == Action.LEFT_CLICK_AIR)))) {
      for (String str : im.getLore()) {
        if (RpgCommon.setNoColorCode(str).startsWith("Level.")) {
          int level = Integer.valueOf(RpgCommon.setNoColorCode(str).split(" ")[1]).intValue();
          RpgPlayer rp = Rpg.getRpgPlayera(player.getName());
          if (level > rp.getRpgLevel()) {
            if (is.getItemMeta().getDisplayName() == null)
              player.sendMessage("§6당신은 아이템 코드: §r" + is.getTypeId() + "§6(을)를 사용이 불가능합니다. 1분 후 돌려드립니다.");
            else {
              player.sendMessage("§6당신은 §r" + is.getItemMeta().getDisplayName() + "§6(을)를 사용이 불가능합니다. 1분 후 돌려드립니다.");
            }
            Item10Min i5 = new Item10Min(player);
            Thread th = new Thread(i5);
            th.start();
            event.setCancelled(true);
            return;
          }
        }
      }
    }

    if (((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (!event.isCancelled())) || ((event.getAction() == Action.RIGHT_CLICK_AIR) && 
      (is.getType() != Material.AIR) && (im.getLore() != null))) {
      List lore = im.getLore();
      for (int i = 0; i < lore.size(); i++)
        if ((((String)lore.get(i)).startsWith("§6경험치 보너스")) || (((String)lore.get(i)).startsWith("§e서버경험치 보너스"))) {
          BonusItem.openGui(player, (String)lore.get(i));
          event.setCancelled(true);
          return;
        }
    }
  }

  @EventHandler
  public void onPlayerFishing(PlayerFishEvent event)
  {
    if (Main.configd.fishexp)
      event.setExpToDrop(0);
  }

  @EventHandler
  public void onEntitydeath(EntityDeathEvent event)
  {
    if (Main.configd.entityexp) {
      event.setDroppedExp(0);
    }
    if (Main.configd.entitydeath) {
      EntityType event1 = event.getEntityType();
      LivingEntity event2 = event.getEntity();
      if ((event2.getKiller() instanceof Player)) {
        String killed = new String();

        if (event2.getCustomName() == null)
          killed = event1.getName();
        else {
          killed = event2.getCustomName();
        }
        String name = "NULL";
        if (Main.entitydeath.getString(killed + ".exp") != null) {
          int exp = 0;
          name = killed.replace("&", "§");
          exp = Integer.valueOf(Main.entitydeath.getString(killed + ".exp")).intValue();
          RpgCalculValue.calculExp(Rpg.getRpgPlayera(event2.getKiller().getName()), exp, 0, name);
        }
      }
    }
  }

  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    String name = event.getWhoClicked().getName();
    if ((((Boolean)item2.get(name)).booleanValue()) && (event.getInventory().getTitle().indexOf("경험치 알약") > 0)) {
      item2.put(name, Boolean.valueOf(false));
      String invname = event.getInventory().getName().toLowerCase();
      if ((invname.equalsIgnoreCase("§6경험치 알약")) || (invname.equalsIgnoreCase("§6서버 경험치 알약"))) {
        RpgPlayer player = Rpg.getRpgPlayera(name);
        event.setCancelled(true);
        ItemStack is = event.getCurrentItem();
        if ((is == null) || (is.getType() == Material.AIR))
          return;
        String str1;
        switch ((str1 = is.getItemMeta().getDisplayName()).hashCode()) { case 6606892:
          if (str1.equals("§6사용")) break; break;
        case 6753856:
          if (!str1.equals("§c취소")) { 

            player.getPlayer().closeInventory();
            player.removeItemInHand();
            String[] args = ((String)item.get(player.getName())).split(" ");
            BonusItem.runItem(player.getPlayer(), args[1], args[2], args[3], Double.valueOf(args[4]).doubleValue());
            return;
          } else {
            player.getPlayer().closeInventory();
            player.sendMessage("§6경험치 알약 사용을 취소했습니다.");
          } break;
        }
      }
    }
  }
}