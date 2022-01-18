// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.lang.reflect.Method;

class CommandDef implements Comparable<CommandDef>
{
    public boolean handlePermissions;
    public String commandString;
    public CommandHandler handler;
    public Method method;
    public CommandArgument[] arguments;
    public String documentation;
    public String sortKey;
    
    @Override
    public int compareTo(final CommandDef o) {
        return this.sortKey.compareToIgnoreCase(o.sortKey);
    }
}
