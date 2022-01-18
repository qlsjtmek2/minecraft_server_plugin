// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.codec.serialization;

class ClassLoaderClassResolver implements ClassResolver
{
    private final ClassLoader classLoader;
    
    ClassLoaderClassResolver(final ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    @Override
    public Class<?> resolve(final String className) throws ClassNotFoundException {
        try {
            return this.classLoader.loadClass(className);
        }
        catch (ClassNotFoundException e) {
            return Class.forName(className, false, this.classLoader);
        }
    }
}
