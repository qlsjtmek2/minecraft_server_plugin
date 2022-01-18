// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;
import think.rpgitems.data.Locale;

class ArgumentDouble extends CommandArgument
{
    private boolean hasLimits;
    private double min;
    private double max;
    
    @Override
    public void init(final String a) {
        if (a.length() == 0) {
            this.hasLimits = false;
        }
        else {
            this.hasLimits = true;
            final String[] args = a.split(",");
            if (args.length != 2) {
                throw new RuntimeException("ArgumentDouble limits errror");
            }
            this.min = Double.parseDouble(args[0]);
            this.max = Double.parseDouble(args[1]);
        }
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        if (this.hasLimits) {
            try {
                final double i = Double.parseDouble(in);
                if (i < this.min || i > this.max) {
                    return new CommandError(String.format(Locale.get("message.error.double.limit", locale), this.min, this.max));
                }
                return i;
            }
            catch (NumberFormatException e) {
                return new CommandError(String.format(Locale.get("message.error.double.format", locale), in));
            }
        }
        try {
            final double i = Double.parseDouble(in);
            return i;
        }
        catch (NumberFormatException e) {
            return new CommandError(String.format(Locale.get("message.error.double.format", locale), in));
        }
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        return new ArrayList<String>();
    }
    
    @Override
    public String printable(final String locale) {
        if (this.hasLimits) {
            return String.format(Locale.get("command.info.double.limit", locale), this.min, this.max);
        }
        return Locale.get("command.info.double", locale);
    }
    
    @Override
    public Class<?> getType() {
        return Double.TYPE;
    }
}
