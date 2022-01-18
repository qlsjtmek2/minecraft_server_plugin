// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;
import think.rpgitems.data.Locale;

class ArgumentOption extends CommandArgument
{
    private String[] options;
    private String shortVersion;
    
    ArgumentOption() {
        this.shortVersion = "";
    }
    
    @Override
    public void init(String a) {
        if (a.contains("@")) {
            final String[] args = a.split("@");
            this.shortVersion = args[0];
            a = args[1];
        }
        this.options = a.split(",");
        for (int i = 0; i < this.options.length; ++i) {
            this.options[i] = this.options[i].trim();
        }
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        String[] options;
        for (int length = (options = this.options).length, i = 0; i < length; ++i) {
            final String o = options[i];
            if (o.equalsIgnoreCase(in)) {
                return o;
            }
        }
        return new CommandError(String.format(Locale.get("message.error.option", locale), in));
    }
    
    @Override
    public List<String> tabComplete(String in) {
        final ArrayList<String> out = new ArrayList<String>();
        in = in.toLowerCase();
        String[] options;
        for (int length = (options = this.options).length, i = 0; i < length; ++i) {
            final String o = options[i];
            if (o.startsWith(in)) {
                out.add(o);
            }
        }
        return out;
    }
    
    @Override
    public String printable(final String locale) {
        if (this.shortVersion.length() == 0) {
            final StringBuilder out = new StringBuilder();
            out.append('[');
            for (int i = 0; i < this.options.length; ++i) {
                out.append(this.options[i]).append((i == this.options.length - 1) ? ']' : ',');
            }
            return out.toString();
        }
        return "[" + this.shortVersion + "]";
    }
    
    @Override
    public Class<?> getType() {
        return String.class;
    }
}
