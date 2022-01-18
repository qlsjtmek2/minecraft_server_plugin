// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.constructor.BaseConstructor;

public final class Loader
{
    protected final BaseConstructor constructor;
    protected Resolver resolver;
    
    public Loader(final BaseConstructor constructor) {
        this.constructor = constructor;
    }
    
    public Loader() {
        this(new Constructor());
    }
}
