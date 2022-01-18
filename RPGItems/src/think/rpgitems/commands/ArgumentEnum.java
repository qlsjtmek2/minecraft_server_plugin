// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ArgumentEnum extends CommandArgument
{
    private Class<?> e;
    private List<?> enumConsts;
    
    @Override
    public void init(final String a) {
        try {
            this.e = Class.forName(a);
            if (!this.e.isEnum()) {
                throw new RuntimeException(String.valueOf(a) + " is not an enum");
            }
            this.enumConsts = Arrays.asList(this.e.getEnumConstants());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        Enum<?> en = null;
        try {
            en = Enum.valueOf(this.e, in.toUpperCase());
        }
        catch (IllegalArgumentException ex) {
            return new CommandError(String.format("%s is not a %s", in, this.e.getSimpleName()));
        }
        return en;
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        final ArrayList<String> out = new ArrayList<String>();
        final String it = in.toUpperCase();
        for (final Object en : this.enumConsts) {
            if (en.toString().startsWith(it)) {
                out.add(en.toString());
            }
        }
        return out;
    }
    
    @Override
    public String printable(final String locale) {
        return "[" + this.e.getSimpleName() + "]";
    }
    
    @Override
    public Class<?> getType() {
        return this.e;
    }
}
