// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;
import think.rpgitems.data.Locale;
import org.bukkit.Material;

class ArgumentMaterial extends CommandArgument
{
    @Override
    public void init(final String a) {
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        final Material mat = Material.matchMaterial(in);
        if (mat == null) {
            return new CommandError(String.format(Locale.get("message.error.material", locale), in));
        }
        return mat;
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        final ArrayList<String> out = new ArrayList<String>();
        final String it = in.toUpperCase();
        Material[] values;
        for (int length = (values = Material.values()).length, i = 0; i < length; ++i) {
            final Material m = values[i];
            if (m.toString().startsWith(it)) {
                out.add(m.toString());
            }
        }
        return out;
    }
    
    @Override
    public String printable(final String locale) {
        return Locale.get("command.info.material", locale);
    }
    
    @Override
    public Class<?> getType() {
        return Material.class;
    }
}
