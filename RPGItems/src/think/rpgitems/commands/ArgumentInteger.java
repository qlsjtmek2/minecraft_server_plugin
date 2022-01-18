// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;
import think.rpgitems.data.Locale;

class ArgumentInteger extends CommandArgument
{
    private boolean hasLimits;
    private int min;
    private int max;
    
    @Override
    public void init(final String a) {
        if (a.length() == 0) {
            this.hasLimits = false;
        }
        else {
            this.hasLimits = true;
            final String[] args = a.split(",");
            if (args.length != 2) {
                throw new RuntimeException("ArgumentInteger limits errror");
            }
            this.min = Integer.parseInt(args[0]);
            this.max = Integer.parseInt(args[1]);
        }
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        if (this.hasLimits) {
            try {
                final int i = Integer.parseInt(in);
                if (i < this.min || i > this.max) {
                    return new CommandError(String.format(Locale.get("message.error.integer.limit", locale), this.min, this.max));
                }
                return i;
            }
            catch (NumberFormatException e) {
                return new CommandError(String.format(Locale.get("message.error.integer.format", locale), in));
            }
        }
        try {
            final int i = Integer.parseInt(in);
            return i;
        }
        catch (NumberFormatException e) {
            return new CommandError(String.format(Locale.get("message.error.integer.format", locale), in));
        }
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        return new ArrayList<String>();
    }
    
    @Override
    public String printable(final String locale) {
        if (this.hasLimits) {
            return String.format(Locale.get("command.info.integer.limit", locale), this.min, this.max);
        }
        return Locale.get("command.info.integer", locale);
    }
    
    @Override
    public Class<?> getType() {
        return Integer.TYPE;
    }
}
