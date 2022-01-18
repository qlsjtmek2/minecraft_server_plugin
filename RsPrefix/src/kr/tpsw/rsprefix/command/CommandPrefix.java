// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.rsprefix.command;

import kr.tpsw.rsprefix.api.PrefixPlayer;
import kr.tpsw.rsprefix.api.RanPreAPI;
import kr.tpsw.rsprefix.api.InvAPI;
import java.util.List;
import kr.tpsw.api.bukkit.DataAPI;
import kr.tpsw.rsprefix.api.API;
import kr.tpsw.rsprefix.api.FileAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandPrefix implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("\ucf58\uc194\uc740 \uc0ac\uc6a9\uc774 \ubd88\uac00\ub2a5\ud55c \uba85\ub839\uc5b4\uc785\ub2c8\ub2e4.");
            return true;
        }
        final int len = args.length;
        if (len == 0) {
            if (label.equals("prefix")) {
                sender.sendMessage("§6/prefix main <index> §f- \uba54\uc778 \uce6d\ud638 \uc124\uc815");
                sender.sendMessage("§6/prefix main §f- \uba54\uc778 \uce6d\ud638 \ubcf4\uae30");
                sender.sendMessage("§6/prefix list §f- \uce6d\ud638 \ubaa9\ub85d \ubcf4\uae30");
                sender.sendMessage("§6/prefix gui §f- \uba54\uc778 \ubaa9\ub85d gui\ub85c \ubcf4\uae30");
                if (sender.isOp()) {
                    sender.sendMessage("§6/prefix give <user> <prefix>");
                    sender.sendMessage("§6/prefix view <user>");
                    sender.sendMessage("§6/prefix remove <user> <index>");
                    sender.sendMessage("§6/prefix gui <user>");
                    sender.sendMessage("§6/prefix ranprefix (book|inst)");
                    sender.sendMessage("§6/prefix titlebook <prefix>");
                }
            }
            else {
                sender.sendMessage("§6/\uce6d\ud638 \ub300\ud45c <\ubc88\ud638> §f- \ub300\ud45c \uce6d\ud638 \uc124\uc815");
                sender.sendMessage("§6/\uce6d\ud638 \ub300\ud45c §f- \ub300\ud45c \uce6d\ud638 \ubcf4\uae30");
                sender.sendMessage("§6/\uce6d\ud638 \ubaa9\ub85d §f- \uce6d\ud638 \ubaa9\ub85d \ubcf4\uae30");
                sender.sendMessage("§6/\uce6d\ud638 gui §f- \uba54\uc778 \ubaa9\ub85d gui\ub85c \ubcf4\uae30");
                if (sender.isOp()) {
                	sender.sendMessage("§6/칭호 추가 <플레이어> <칭호>");
                    sender.sendMessage("§6/칭호 보기 <플레이어>");
                    sender.sendMessage("§6/칭호 삭제 <플레이어> <번호>");
                    sender.sendMessage("§6/칭호 gui <플레이어>");
                    sender.sendMessage("§6/칭호 랜덤칭호 (book|inst)");
                    sender.sendMessage("§6/칭호 타이틀북 <칭호>");
                }
            }
        }
        else if ((args[0].equals("main") || args[0].equals("\ub300\ud45c")) && (len == 1 || len == 2)) {
            final PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
            if (len == 2) {
                if (API.isIntegerPositive(args[1]) || args[1].equals("-1")) {
                    final int index = Integer.valueOf(args[1]);
                    if (index == -1 && sender.isOp()) {
                        final String main = "\uc5c6\uc74c";
                        pp.setMainPrefix(index);
                        sender.sendMessage("§6\ub300\ud45c \uce6d\ud638\ub97c §r<" + main + "§r>§6(\uc73c)\ub85c \uc124\uc815\ud588\uc2b5\ub2c8\ub2e4.");
                    }
                    else if (DataAPI.isListhasIndex(pp.getList(), index)) {
                        final String main = pp.getList().get(index);
                        pp.setMainPrefix(index);
                        pp.needUpdateInv = true;
                        sender.sendMessage("§6\ub300\ud45c \uce6d\ud638\ub97c §r<" + main + "§r>§6(\uc73c)\ub85c \uc124\uc815\ud588\uc2b5\ub2c8\ub2e4.");
                    }
                    else {
                        sender.sendMessage("§6\uce6d\ud638 \ubaa9\ub85d\uc744 \ubc97\uc5b4\ub09c \uc22b\uc790\uc785\ub2c8\ub2e4.");
                    }
                }
                else {
                    sender.sendMessage("§6<index>\uc5d0 \uc62c\ubc14\ub978 \uc22b\uc790\ub97c \uc785\ub825\ud558\uc2ed\uc2dc\uc624.");
                }
            }
            else {
                final String main2 = pp.getMainPrefix();
                sender.sendMessage("§6\ub300\ud45c \uce6d\ud638\ub294 §r<" + main2 + "§r>§6\uc785\ub2c8\ub2e4.");
            }
        }
        else if ((args[0].equals("list") || args[0].equals("\ubaa9\ub85d")) && (len == 2 || len == 1)) {
            final PrefixPlayer pp = FileAPI.getPrefixPlayer(sender.getName());
            int index = 1;
            if (len == 2 && API.isIntegerPositive(args[1])) {
                index = Integer.valueOf(args[1]);
            }
            API.sendMessageList(sender, pp.getList(), index, "prefix list");
        }
        else if (args[0].equals("gui") && (len == 1 || len == 2)) {
            int index2 = 1;
            if (len == 2 && API.isIntegerPositive(args[1])) {
                index2 = Integer.valueOf(args[1]);
            }
            InvAPI.viewInv(sender.getName(), (Player)sender, index2);
        }
        else if ((args[0].equals("titlebook") || args[0].equals("타이틀북")) && len == 2) {
        	if (sender instanceof Player) {
				Player p = (Player) sender;
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
					return false;
				}
				
				args[1] = args[1].replace("&", "§");
				p.sendMessage("§6성공적으로 §f<" + args[1] + "§f> §6칭호를 지급하였습니다.");
				p.getInventory().addItem(new ItemStack[] { RanPreAPI.getPrefixBook(args[1]) });
        		return true;
        	} else {
        		sender.sendMessage("콘솔에서 사용 불가능한 명령어입니다.");
        		return true;
        	}
        }
        else if ((args[0].equals("give") || args[0].equals("추가")) && (len == 3 || len == 4)) {
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
            final String target = API.getOfflinePlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§6\ud574\ub2f9 \uc774\ub984\uc73c\ub85c \uac80\uc0c9\ub41c \uc720\uc800\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            boolean isLoaded = false;
            if (!FileAPI.isLoadedPlayer(target)) {
                FileAPI.loadPlayer(target);
                isLoaded = true;
            }
            final PrefixPlayer pp = FileAPI.getPrefixPlayer(target);
            final List<String> list = pp.getList();
            args[2] = args[2].replace("&", "§");
            if (!list.contains(args[2])) {
                list.add(args[2]);
            }
            pp.needUpdateInv = true;
            if (isLoaded) {
                FileAPI.savePlayer(target);
            }
            if (Bukkit.getPlayerExact(target) != null) {
            	Bukkit.getPlayerExact(target).sendMessage("§6당신에게 " + args[2] + " §6라는 칭호가 부여되었습니다.");
            }
            if (len == 4 && args[3].equals("\ub178!")) {
                return true;
            }
            sender.sendMessage("§6" + target + " \uc720\uc800\uc5d0 " + args[2] + "§6\uce6d\ud638\ub97c \ucd94\uac00\ud588\uc2b5\ub2c8\ub2e4.");
        }
        else if ((args[0].equals("remove") || args[0].equals("삭제")) && (len == 3 || len == 4)) {
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
            final String target = API.getOfflinePlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§6\ud574\ub2f9 \uc774\ub984\uc73c\ub85c \uac80\uc0c9\ub41c \uc720\uc800\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            if (!FileAPI.isLoadedPlayer(target)) {
                sender.sendMessage("§6\uc811\uc18d\uc911\uc778 \ud50c\ub808\uc774\uc5b4\ub9cc \ubcfc \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            PrefixPlayer pp2 = FileAPI.getPrefixPlayer(target);
            int index = 1;
            if (len >= 3 && API.isIntegerPositive(args[2])) {
                index = Integer.valueOf(args[2]);
            }
            List<String> list = pp2.getList();
            if (!DataAPI.isListhasIndex(list, index)) {
                sender.sendMessage("§6\uce6d\ud638 \ubaa9\ub85d\uc744 \ubc97\uc5b4\ub09c \uc22b\uc790\uc785\ub2c8\ub2e4.");
                return true;
            }
            list.remove(index);
            if (list.isEmpty()) pp2.setMainPrefix(-1);
            pp2.needUpdateInv = true;
            if (len == 4 && args[3].equals("\ub178!")) {
                return true;
            }
            sender.sendMessage("§6" + target + " \uc720\uc800\uc758 " + args[2] + "§6\ubc88\uc9f8 \uce6d\ud638\ub97c \uc0ad\uc81c\ud588\uc2b5\ub2c8\ub2e4.");
        }
        else if ((args[0].equals("view") || args[0].equals("\ubcf4\uae30")) && (len == 3 || len == 2)) {
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
            final String target = API.getOfflinePlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§6\ud574\ub2f9 \uc774\ub984\uc73c\ub85c \uac80\uc0c9\ub41c \uc720\uc800\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            if (!FileAPI.isLoadedPlayer(target)) {
                sender.sendMessage("§6\uc811\uc18d\uc911\uc778 \ud50c\ub808\uc774\uc5b4\ub9cc \ubcfc \uc218 \uc788\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            final PrefixPlayer pp2 = FileAPI.getPrefixPlayer(target);
            int index = 1;
            if (len == 3 && API.isIntegerPositive(args[2])) {
                index = Integer.valueOf(args[2]);
            }
            API.sendMessageList(sender, pp2.getList(), index, "pradmin view");
        }
        else if (args[0].equals("gui") && (len == 2 || len == 3)) {
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
            final String target = API.getOfflinePlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§6\ud574\ub2f9 \uc774\ub984\uc73c\ub85c \uac80\uc0c9\ub41c \uc720\uc800\uac00 \uc5c6\uc2b5\ub2c8\ub2e4.");
                return true;
            }
            int index2 = 1;
            if (len == 3 && API.isIntegerPositive(args[2])) {
                index2 = Integer.valueOf(args[2]);
            }
            InvAPI.viewInv(target, (Player)sender, index2);
        }
        else if ((args[0].equals("ranprefix") || args[0].equals("\ub79c\ub364\uce6d\ud638")) && len == 2) {
			if (!sender.isOp()) {
				sender.sendMessage(ChatColor.RED + "당신은 이 명령어를 사용할 권한이 없습니다.");
				return false;
			}
			
            if (sender instanceof Player) {
                if (args[1].equals("book")) {
                    final Player p = (Player)sender;
                    p.getInventory().addItem(new ItemStack[] { RanPreAPI.ranpre });
                    sender.sendMessage("§6\ub79c\ub364 \uce6d\ud638 \ud68d\ub4dd \uc544\uc774\ud15c\uc744 \uc5bb\uc5c8\uc2b5\ub2c8\ub2e4.");
                }
                else if (args[1].equals("inst")) {
                    final Player p = (Player)sender;
                    RanPreAPI.runRandomPrefix(p);
                }
                else {
                    sender.sendMessage("§6\uc798\ubabb\ub41c \uc778\uc790\ub97c \uc785\ub825\ud588\uc2b5\ub2c8\ub2e4.");
                }
            }
            else {
                sender.sendMessage("\ucf58\uc194\uc740 \uc0ac\uc6a9\uc774 \ubd88\uac00\ub2a5\ud55c \uba85\ub839\uc5b4\uc785\ub2c8\ub2e4.");
            }
        }
        else {
            sender.sendMessage("§6\uc798\ubabb\ub41c \uba85\ub839\uc5b4 \uc778\uc790\ub97c \uc785\ub825\ud588\uc2b5\ub2c8\ub2e4.");
        }
        return true;
    }
}
