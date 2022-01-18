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
            		sender.sendMessage(" ��e---- ��6�ɷ��� ��ɾ� ��e-- ��6������ ��c1��6/��c1 ��e----");
                    sender.sendMessage(ChatColor.GOLD + "/va start " + ChatColor.WHITE + "- ������ ���۽�ŵ�ϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va stop " + ChatColor.WHITE + "- ������ ������ŵ�ϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va help " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va ob " + ChatColor.WHITE + "- ������ ������ �մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va uti " + ChatColor.WHITE + "- ��ƿ��Ƽ ��� ����� �����ݴϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va debug " + ChatColor.WHITE + "- ���� ��� ��� ����� �����ݴϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va go " + ChatColor.WHITE + "- �����ð��� ��ŵ�մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va inv " + ChatColor.WHITE + "- �������� ����ϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va hung " + ChatColor.WHITE + "- ������� �����մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va dura " + ChatColor.WHITE + "- ������������ �����մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va elist " + ChatColor.WHITE + "- �ɷ��� Ȯ������ ���� ������� ���ϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va alist " + ChatColor.WHITE + "- �÷��̾��� �ɷ� ����� ���ϴ�.");
            	} else {
            		sender.sendMessage(" ��e---- ��6�ɷ��� ��ɾ� ��e-- ��6������ ��c1��6/��c1 ��e----");
                    sender.sendMessage(ChatColor.GOLD + "/va help " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va ob " + ChatColor.WHITE + "- ������ ������ �մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va yes " + ChatColor.WHITE + "- �ɷ��� Ȯ���մϴ�.");
                    sender.sendMessage(ChatColor.GOLD + "/va no " + ChatColor.WHITE + "- �ɷ��� 1ȸ�ȿ� �����մϴ�.");
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
