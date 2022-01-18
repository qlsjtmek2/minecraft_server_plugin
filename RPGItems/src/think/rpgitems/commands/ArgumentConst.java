// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.commands;

import java.util.ArrayList;
import java.util.List;

class ArgumentConst extends CommandArgument
{
    public String value;
    
    public ArgumentConst(final String v) {
        this.value = v;
    }
    
    @Override
    public void init(final String a) {
        throw new RuntimeException("Const cannot be init'ed");
    }
    
    @Override
    public Object parse(final String in, final String locale) {
        return null;
    }
    
    @Override
    public List<String> tabComplete(final String in) {
        final ArrayList<String> a = new ArrayList<String>();
        final String lValue = this.value;
        if (lValue.startsWith(in)) {
            a.add(lValue);
        }
        return a;
    }
    
    @Override
    public String printable(final String locale) {
        return this.value;
    }
    
    @Override
    public boolean isConst() {
        return true;
    }
    
    @Override
    public Class<?> getType() {
        return null;
    }
}
