// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;
import think.rpgitems.data.Locale;

class ArgumentString extends CommandArgument
{
    private int maxLength;
    
    @Override
    public void init(final String a) {
        if (a.length() == 0) {
            this.maxLength = 0;
        }
        else {
            this.maxLength = Integer.parseInt(a);
        }
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        if (this.maxLength != 0 && in.length() > this.maxLength) {
            return new CommandError(String.format(Locale.get("message.error.string.length", locale), in, this.maxLength));
        }
        return in;
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        return new ArrayList<String>();
    }
    
    @Override
    public String printable(final String locale) {
        if (this.maxLength != 0) {
            return String.format(Locale.get("command.info.string.limit", locale), this.maxLength);
        }
        return Locale.get("command.info.string", locale);
    }
    
    @Override
    public Class<?> getType() {
        return String.class;
    }
}
