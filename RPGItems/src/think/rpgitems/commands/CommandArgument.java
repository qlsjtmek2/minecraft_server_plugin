// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.List;

abstract class CommandArgument
{
    public String name;
    
    CommandArgument() {
        this.name = "";
    }
    
    public abstract void init(final String p0);
    
    public abstract Object parse(final String p0, final String p1);
    
    public abstract List<String> tabComplete(final String p0);
    
    public abstract String printable(final String p0);
    
    public abstract Class<?> getType();
    
    public boolean isConst() {
        return false;
    }
}
