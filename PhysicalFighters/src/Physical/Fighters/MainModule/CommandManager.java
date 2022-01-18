// 
// Decompiled by Procyon v0.5.30
// 

package Physical.Fighters.MainModule;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Physical.Fighters.PhysicalFighters;
import Physical.Fighters.MinerModule.CommandInterface;

public class CommandManager implements CommandExecutor
{
    private LinkedList<CommandInterface> CommandEventHandler;
    
    public CommandManager(final PhysicalFighters va) {
        this.CommandEventHandler = new LinkedList<CommandInterface>();
        va.getCommand("va").setExecutor((CommandExecutor)this);
    }
    
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] data) {
        if (command.getName().equals("va")) {
            if (data.length == 0) {
            	if (sender.isOp()) {
            		sender.sendMessage(" §e---- §6능력자 명령어 §e-- §6페이지 §c1§6/§c1 §e----");
                    sender.sendMessage(ChatColor.GOLD + "/va start " + ChatColor.WHITE + "- 게임을 시작시킵니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va stop " + ChatColor.WHITE + "- 게임을 중지시킵니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va help " + ChatColor.WHITE + "- 능력을 확인합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va ob " + ChatColor.WHITE + "- 옵저버 설정을 합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va uti " + ChatColor.WHITE + "- 유틸리티 명령 목록을 보여줍니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va debug " + ChatColor.WHITE + "- 오류 방어 명령 목록을 보여줍니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va go " + ChatColor.WHITE + "- 무적시간을 스킵합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va inv " + ChatColor.WHITE + "- 무적으로 만듭니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va hung " + ChatColor.WHITE + "- 배고픔을 설정합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va dura " + ChatColor.WHITE + "- 내구도무한을 설정합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va elist " + ChatColor.WHITE + "- 능력을 확정하지 않은 사람들을 봅니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va alist " + ChatColor.WHITE + "- 플레이어의 능력 목록을 봅니다.");
            	} else {
            		sender.sendMessage(" §e---- §6능력자 명령어 §e-- §6페이지 §c1§6/§c1 §e----");
                    sender.sendMessage(ChatColor.GOLD + "/va help " + ChatColor.WHITE + "- 능력을 확인합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va ob " + ChatColor.WHITE + "- 옵저버 설정을 합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va yes " + ChatColor.WHITE + "- 능력을 확정합니다.");
                    sender.sendMessage(ChatColor.GOLD + "/va no " + ChatColor.WHITE + "- 능력을 1회안에 변경합니다.");
            	}
                return true;
            }
            for (final CommandInterface handler : this.CommandEventHandler) {
                if (handler.onCommandEvent(sender, command, label, data)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void RegisterCommand(final CommandInterface EventHandler) {
        this.CommandEventHandler.add(EventHandler);
    }
}
