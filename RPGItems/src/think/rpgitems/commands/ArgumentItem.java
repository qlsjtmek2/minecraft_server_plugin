// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import think.rpgitems.item.RPGItem;
import think.rpgitems.data.Locale;
import think.rpgitems.item.ItemManager;

class ArgumentItem extends CommandArgument
{
    @Override
    public void init(final String a) {
    }
    
    @Override
    public Object parse(String in, final String locale) {
        in = in.toLowerCase();
        final RPGItem item = ItemManager.getItemByName(in);
        if (item == null) {
            return new CommandError(String.format(Locale.get("message.error.item", locale), in));
        }
        return item;
    }
    
    @Override
    public List<String> tabComplete(String in) {
        in = in.toLowerCase();
        final ArrayList<String> out = new ArrayList<String>();
        for (final String i : ItemManager.itemByName.keySet()) {
            if (i.startsWith(in)) {
                out.add(i);
            }
        }
        return out;
    }
    
    @Override
    public String printable(final String locale) {
        return Locale.get("command.info.item", locale);
    }
    
    @Override
    public Class<?> getType() {
        return RPGItem.class;
    }
}
