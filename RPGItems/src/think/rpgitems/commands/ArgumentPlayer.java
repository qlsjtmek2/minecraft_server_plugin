// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import think.rpgitems.data.Locale;
import org.bukkit.Bukkit;

class ArgumentPlayer extends CommandArgument
{
    @Override
    public void init(final String a) {
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        final Player player = Bukkit.getPlayer(in);
        if (player == null) {
            return new CommandError(String.format(Locale.get("message.error.player", locale), in));
        }
        return player;
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        final List<Player> players = (List<Player>)Bukkit.matchPlayer(in);
        final ArrayList<String> out = new ArrayList<String>();
        for (final Player player : players) {
            out.add(player.getName());
        }
        return out;
    }
    
    @Override
    public String printable(final String locale) {
        return Locale.get("command.info.player", locale);
    }
    
    @Override
    public Class<?> getType() {
        return Player.class;
    }
}
