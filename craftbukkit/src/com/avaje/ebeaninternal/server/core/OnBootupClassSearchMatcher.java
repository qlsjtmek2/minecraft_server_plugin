// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.server.util.ClassPathSearchMatcher;

public class OnBootupClassSearchMatcher implements ClassPathSearchMatcher
{
    BootupClasses classes;
    
    public OnBootupClassSearchMatcher() {
        this.classes = new BootupClasses();
    }
    
    public boolean isMatch(final Class<?> cls) {
        return this.classes.isMatch(cls);
    }
    
    public BootupClasses getOnBootupClasses() {
        return this.classes;
    }
}
