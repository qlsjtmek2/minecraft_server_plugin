// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml;

import org.yaml.snakeyaml.reader.UnicodeReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.resolver.Resolver;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

public class JavaBeanLoader<T>
{
    private Yaml loader;
    
    public JavaBeanLoader(final TypeDescription typeDescription) {
        this(typeDescription, BeanAccess.DEFAULT);
    }
    
    public JavaBeanLoader(final TypeDescription typeDescription, final BeanAccess beanAccess) {
        this(new LoaderOptions(typeDescription), beanAccess);
    }
    
    public JavaBeanLoader(final LoaderOptions options, final BeanAccess beanAccess) {
        if (options == null) {
            throw new NullPointerException("LoaderOptions must be provided.");
        }
        if (options.getRootTypeDescription() == null) {
            throw new NullPointerException("TypeDescription must be provided.");
        }
        final Constructor constructor = new Constructor(options.getRootTypeDescription());
        (this.loader = new Yaml(constructor, options, new Representer(), new DumperOptions(), new Resolver())).setBeanAccess(beanAccess);
    }
    
    public JavaBeanLoader(final Class<S> clazz, final BeanAccess beanAccess) {
        this(new TypeDescription(clazz), beanAccess);
    }
    
    public JavaBeanLoader(final Class<S> clazz) {
        this(clazz, BeanAccess.DEFAULT);
    }
    
    public T load(final String yaml) {
        return (T)this.loader.load(new StringReader(yaml));
    }
    
    public T load(final InputStream io) {
        return (T)this.loader.load(new UnicodeReader(io));
    }
    
    public T load(final Reader io) {
        return (T)this.loader.load(io);
    }
}
