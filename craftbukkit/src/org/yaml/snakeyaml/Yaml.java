// 
// Decompiled by Procyon v0.5.30
// 

package org.yaml.snakeyaml;

import org.yaml.snakeyaml.introspector.BeanAccess;
import java.util.regex.Pattern;
import java.io.StringReader;
import org.yaml.snakeyaml.parser.Parser;
import org.yaml.snakeyaml.composer.Composer;
import org.yaml.snakeyaml.parser.ParserImpl;
import java.io.Reader;
import org.yaml.snakeyaml.reader.UnicodeReader;
import java.io.InputStream;
import org.yaml.snakeyaml.reader.StreamReader;
import org.yaml.snakeyaml.events.Event;
import java.io.IOException;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.emitter.Emitable;
import org.yaml.snakeyaml.serializer.Serializer;
import org.yaml.snakeyaml.emitter.Emitter;
import org.yaml.snakeyaml.nodes.Tag;
import java.io.Writer;
import java.io.StringWriter;
import org.yaml.snakeyaml.nodes.Node;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.resolver.Resolver;

public class Yaml
{
    protected final Resolver resolver;
    private String name;
    protected BaseConstructor constructor;
    protected Representer representer;
    protected DumperOptions dumperOptions;
    protected LoaderOptions loaderOptions;
    
    public Yaml() {
        this(new Constructor(), new LoaderOptions(), new Representer(), new DumperOptions(), new Resolver());
    }
    
    public Yaml(final LoaderOptions loaderOptions) {
        this(new Constructor(), loaderOptions, new Representer(), new DumperOptions(), new Resolver());
    }
    
    public Yaml(final DumperOptions dumperOptions) {
        this(new Constructor(), new Representer(), dumperOptions);
    }
    
    public Yaml(final Representer representer) {
        this(new Constructor(), representer);
    }
    
    public Yaml(final BaseConstructor constructor) {
        this(constructor, new Representer());
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer) {
        this(constructor, representer, new DumperOptions());
    }
    
    public Yaml(final Representer representer, final DumperOptions dumperOptions) {
        this(new Constructor(), representer, dumperOptions, new Resolver());
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer, final DumperOptions dumperOptions) {
        this(constructor, representer, dumperOptions, new Resolver());
    }
    
    public Yaml(final BaseConstructor constructor, final Representer representer, final DumperOptions dumperOptions, final Resolver resolver) {
        this(constructor, new LoaderOptions(), representer, dumperOptions, resolver);
    }
    
    public Yaml(final BaseConstructor constructor, final LoaderOptions loaderOptions, final Representer representer, final DumperOptions dumperOptions, final Resolver resolver) {
        if (!constructor.isExplicitPropertyUtils()) {
            constructor.setPropertyUtils(representer.getPropertyUtils());
        }
        else if (!representer.isExplicitPropertyUtils()) {
            representer.setPropertyUtils(constructor.getPropertyUtils());
        }
        this.constructor = constructor;
        this.loaderOptions = loaderOptions;
        representer.setDefaultFlowStyle(dumperOptions.getDefaultFlowStyle());
        representer.setDefaultScalarStyle(dumperOptions.getDefaultScalarStyle());
        representer.getPropertyUtils().setAllowReadOnlyProperties(dumperOptions.isAllowReadOnlyProperties());
        this.representer = representer;
        this.dumperOptions = dumperOptions;
        this.resolver = resolver;
        this.name = "Yaml:" + System.identityHashCode(this);
    }
    
    public String dump(final Object data) {
        final List<Object> list = new ArrayList<Object>(1);
        list.add(data);
        return this.dumpAll(list.iterator());
    }
    
    public Node represent(final Object data) {
        return this.representer.represent(data);
    }
    
    public String dumpAll(final Iterator<?> data) {
        final StringWriter buffer = new StringWriter();
        this.dumpAll(data, buffer);
        return buffer.toString();
    }
    
    public void dump(final Object data, final Writer output) {
        final List<Object> list = new ArrayList<Object>(1);
        list.add(data);
        this.dumpAll(list.iterator(), output);
    }
    
    public void dumpAll(final Iterator<?> data, final Writer output) {
        this.dumpAll(data, output, this.dumperOptions.getExplicitRoot());
    }
    
    private void dumpAll(final Iterator<?> data, final Writer output, final Tag rootTag) {
        final Serializer serializer = new Serializer(new Emitter(output, this.dumperOptions), this.resolver, this.dumperOptions, rootTag);
        try {
            serializer.open();
            while (data.hasNext()) {
                final Node node = this.representer.represent(data.next());
                serializer.serialize(node);
            }
            serializer.close();
        }
        catch (IOException e) {
            throw new YAMLException(e);
        }
    }
    
    public String dumpAs(final Object data, final Tag rootTag, final DumperOptions.FlowStyle flowStyle) {
        final DumperOptions.FlowStyle oldStyle = this.representer.getDefaultFlowStyle();
        if (flowStyle != null) {
            this.representer.setDefaultFlowStyle(flowStyle);
        }
        final List<Object> list = new ArrayList<Object>(1);
        list.add(data);
        final StringWriter buffer = new StringWriter();
        this.dumpAll(list.iterator(), buffer, rootTag);
        this.representer.setDefaultFlowStyle(oldStyle);
        return buffer.toString();
    }
    
    public String dumpAsMap(final Object data) {
        return this.dumpAs(data, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
    }
    
    public List<Event> serialize(final Node data) {
        final SilentEmitter emitter = new SilentEmitter();
        final Serializer serializer = new Serializer(emitter, this.resolver, this.dumperOptions, this.dumperOptions.getExplicitRoot());
        try {
            serializer.open();
            serializer.serialize(data);
            serializer.close();
        }
        catch (IOException e) {
            throw new YAMLException(e);
        }
        return emitter.getEvents();
    }
    
    public Object load(final String yaml) {
        return this.loadFromReader(new StreamReader(yaml), Object.class);
    }
    
    public Object load(final InputStream io) {
        return this.loadFromReader(new StreamReader(new UnicodeReader(io)), Object.class);
    }
    
    public Object load(final Reader io) {
        return this.loadFromReader(new StreamReader(io), Object.class);
    }
    
    public <T> T loadAs(final Reader io, final Class<T> type) {
        return (T)this.loadFromReader(new StreamReader(io), type);
    }
    
    public <T> T loadAs(final String yaml, final Class<T> type) {
        return (T)this.loadFromReader(new StreamReader(yaml), type);
    }
    
    public <T> T loadAs(final InputStream input, final Class<T> type) {
        return (T)this.loadFromReader(new StreamReader(new UnicodeReader(input)), type);
    }
    
    private Object loadFromReader(final StreamReader sreader, final Class<?> type) {
        final Composer composer = new Composer(new ParserImpl(sreader), this.resolver);
        this.constructor.setComposer(composer);
        return this.constructor.getSingleData(type);
    }
    
    public Iterable<Object> loadAll(final Reader yaml) {
        final Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        final Iterator<Object> result = new Iterator<Object>() {
            public boolean hasNext() {
                return Yaml.this.constructor.checkData();
            }
            
            public Object next() {
                return Yaml.this.constructor.getData();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return new YamlIterable(result);
    }
    
    public Iterable<Object> loadAll(final String yaml) {
        return this.loadAll(new StringReader(yaml));
    }
    
    public Iterable<Object> loadAll(final InputStream yaml) {
        return this.loadAll(new UnicodeReader(yaml));
    }
    
    public Node compose(final Reader yaml) {
        final Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        return composer.getSingleNode();
    }
    
    public Iterable<Node> composeAll(final Reader yaml) {
        final Composer composer = new Composer(new ParserImpl(new StreamReader(yaml)), this.resolver);
        this.constructor.setComposer(composer);
        final Iterator<Node> result = new Iterator<Node>() {
            public boolean hasNext() {
                return composer.checkNode();
            }
            
            public Node next() {
                return composer.getNode();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return new NodeIterable(result);
    }
    
    public void addImplicitResolver(final String tag, final Pattern regexp, final String first) {
        this.addImplicitResolver(new Tag(tag), regexp, first);
    }
    
    public void addImplicitResolver(final Tag tag, final Pattern regexp, final String first) {
        this.resolver.addImplicitResolver(tag, regexp, first);
    }
    
    public String toString() {
        return this.name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public Iterable<Event> parse(final Reader yaml) {
        final Parser parser = new ParserImpl(new StreamReader(yaml));
        final Iterator<Event> result = new Iterator<Event>() {
            public boolean hasNext() {
                return parser.peekEvent() != null;
            }
            
            public Event next() {
                return parser.getEvent();
            }
            
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return new EventIterable(result);
    }
    
    public void setBeanAccess(final BeanAccess beanAccess) {
        this.constructor.getPropertyUtils().setBeanAccess(beanAccess);
        this.representer.getPropertyUtils().setBeanAccess(beanAccess);
    }
    
    public Yaml(final Loader loader) {
        this(loader, new Dumper(new DumperOptions()));
    }
    
    public Yaml(final Loader loader, final Dumper dumper) {
        this(loader, dumper, new Resolver());
    }
    
    public Yaml(final Loader loader, final Dumper dumper, final Resolver resolver) {
        this(loader.constructor, dumper.representer, dumper.options, resolver);
    }
    
    public Yaml(final Dumper dumper) {
        this(new Constructor(), dumper.representer, dumper.options);
    }
    
    private class SilentEmitter implements Emitable
    {
        private List<Event> events;
        
        private SilentEmitter() {
            this.events = new ArrayList<Event>(100);
        }
        
        public List<Event> getEvents() {
            return this.events;
        }
        
        public void emit(final Event event) throws IOException {
            this.events.add(event);
        }
    }
    
    private class YamlIterable implements Iterable<Object>
    {
        private Iterator<Object> iterator;
        
        public YamlIterable(final Iterator<Object> iterator) {
            this.iterator = iterator;
        }
        
        public Iterator<Object> iterator() {
            return this.iterator;
        }
    }
    
    private class NodeIterable implements Iterable<Node>
    {
        private Iterator<Node> iterator;
        
        public NodeIterable(final Iterator<Node> iterator) {
            this.iterator = iterator;
        }
        
        public Iterator<Node> iterator() {
            return this.iterator;
        }
    }
    
    private class EventIterable implements Iterable<Event>
    {
        private Iterator<Event> iterator;
        
        public EventIterable(final Iterator<Event> iterator) {
            this.iterator = iterator;
        }
        
        public Iterator<Event> iterator() {
            return this.iterator;
        }
    }
}
