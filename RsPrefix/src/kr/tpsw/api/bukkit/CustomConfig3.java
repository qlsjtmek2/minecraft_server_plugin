// 
// Decompiled by Procyon v0.5.30
// 

package kr.tpsw.api.bukkit;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.io.Writer;
import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.jar.JarFile;
import java.util.Iterator;
import java.io.FileWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.constructor.Constructor;
import java.util.ArrayList;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;
import org.yaml.snakeyaml.DumperOptions;
import java.util.List;
import java.io.File;

public class CustomConfig3
{
    private File file;
    private List<String> comment;
    private DumperOptions yamlOptions;
    private Representer yamlRepresenter;
    private Yaml yaml;
    private Map<String, Object> map;
    
    public CustomConfig3(final String file) {
        this(new File(file));
    }
    
    public CustomConfig3(final File file) {
        this.comment = new ArrayList<String>();
        this.yamlOptions = new DumperOptions();
        this.yamlRepresenter = new Representer();
        this.yaml = new Yaml((BaseConstructor)new Constructor(), this.yamlRepresenter, this.yamlOptions);
        this.yamlOptions.setIndent(2);
        this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        this.file = file;
        if (file == null || !file.exists()) {
            this.map = new LinkedHashMap<String, Object>();
        }
        else {
            this.reloadConfig();
        }
    }
    
    public void reloadConfig() {
        try {
            this.reloadCConfig();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveConfig() {
        try {
            this.saveCConfig();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void reloadCConfig() throws IOException {
        final FileInputStream fin = new FileInputStream(this.file);
        final InputStreamReader inr = new InputStreamReader(fin);
        final BufferedReader br = new BufferedReader(inr);
        final StringBuilder sb = new StringBuilder();
        this.comment.clear();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    this.comment.add(line);
                }
                else {
                    sb.append(line).append("\n");
                }
            }
        }
        finally {
            br.close();
            inr.close();
            fin.close();
        }
        br.close();
        inr.close();
        fin.close();
        Map<String, Object> map = null;
        try {
            map = (Map<String, Object>)this.yaml.load(sb.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if (map != null) {
            this.map = map;
        }
        else {
            this.map = new LinkedHashMap<String, Object>();
        }
    }
    
    private void saveCConfig() throws IOException {
        final File pf = this.file.getParentFile();
        if (pf != null && !pf.isDirectory()) {
            pf.mkdirs();
        }
        final StringBuilder sb = new StringBuilder();
        for (final String comment : this.comment) {
            sb.append(comment).append("\n");
        }
        final String data = this.yaml.dump((Object)this.map);
        if (!data.equals("{}\n")) {
            sb.append(data);
        }
        final FileWriter fw = new FileWriter(this.file);
        try {
            fw.write(sb.toString());
        }
        finally {
            fw.close();
        }
        fw.close();
    }
    
    public static void getResourceInJar(final File jarfile, final String entryname, final File output) {
        try {
            final JarFile jfile = new JarFile(jarfile);
            final JarEntry jentry = jfile.getJarEntry(entryname);
            if (jentry == null) {
                jfile.close();
                throw new IOException("Can't find entryfile");
            }
            final InputStream instream = jfile.getInputStream(jentry);
            final Reader reader = new InputStreamReader(instream, Charset.forName("UTF-8"));
            final BufferedReader breader = new BufferedReader(reader);
            final FileWriter filew = new FileWriter(output);
            final BufferedWriter bw = new BufferedWriter(filew);
            String s;
            while ((s = breader.readLine()) != null) {
                bw.append((CharSequence)s).append('\n');
            }
            bw.close();
            filew.close();
            breader.close();
            reader.close();
            instream.close();
            jfile.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addComment(final String comment) {
        this.comment.add(comment);
    }
    
    public void removeComment(final int index) {
        this.comment.remove(index);
    }
    
    public void removeComment(final String comment) {
        this.comment.remove(comment);
    }
    
    public List<String> getComment() {
        return this.comment;
    }
    
    public Object get(final String key) {
        final String[] args = key.replace(".", " ").split(" ");
        Map<String, Object> map = this.map;
        Object o = null;
        for (int i = 0; i < args.length; ++i) {
            o = map.get(args[i]);
            if (o == null) {
                return null;
            }
            if (o instanceof Map) {
                map = (Map<String, Object>)o;
            }
        }
        return o;
    }
    
    public void set(final String key, final Object value) {
        final String[] args = key.replace(".", " ").split(" ");
        Map<String, Object> map = this.map;
        for (int i = 0; i < args.length; ++i) {
            final Object o = map.get(args[i]);
            if (i + 1 == args.length) {
                if (o instanceof Map) {
                    map = (Map<String, Object>)o;
                }
                if (value == null) {
                    map.remove(args[i]);
                }
                else {
                    map.put(args[i], value);
                }
            }
            else if (o instanceof Map) {
                map = (Map<String, Object>)o;
            }
            else {
                final Map<String, Object> m = new LinkedHashMap<String, Object>();
                map.put(args[i], m);
                map = m;
            }
        }
    }
    
    private void mapKeys(final Map<String, Object> map, final Set<String> set, final String first) {
        for (final String key : map.keySet()) {
            final Object o = map.get(key);
            final String s = String.valueOf(first) + key;
            set.add(s);
            if (o instanceof Map) {
                this.mapKeys((Map<String, Object>)o, set, String.valueOf(s) + ".");
            }
        }
    }
    
    public Set<String> getKeys(final boolean deep) {
        if (deep) {
            final Set<String> set = new LinkedHashSet<String>();
            this.mapKeys(this.map, set, "");
            return set;
        }
        return this.map.keySet();
    }
    
    public int getInt(final String key) {
        final Object va = this.get(key);
        return (int)((va instanceof Integer) ? va : 0);
    }
    
    public long getLong(final String key) {
        final Object va = this.get(key);
        return (long)((va instanceof Long) ? va : 0L);
    }
    
    public double getDouble(final String key) {
        final Object va = this.get(key);
        return (double)((va instanceof Double) ? va : 0.0);
    }
    
    public float getFloat(final String key) {
        final Object va = this.get(key);
        return (float)((va instanceof Float) ? va : 0.0f);
    }
    
    public boolean getBoolean(final String key) {
        final Object va = this.get(key);
        return va instanceof Boolean && (boolean)va;
    }
    
    public String getString(final String key) {
        final Object va = this.get(key);
        return (va instanceof String) ? ((String)va) : null;
    }
    
    public List<?> getList(final String key) {
        final Object va = this.get(key);
        return (List<?>)((va instanceof List) ? ((List)va) : null);
    }
    
    public List<String> getStringList(final String key) {
        final Object va = this.get(key);
        return (List<String>)((va instanceof List) ? ((List)va) : new ArrayList<String>());
    }
    
    public Map<?, ?> getMap(final String key) {
        final Object va = this.get(key);
        return (Map<?, ?>)((va instanceof Map) ? ((Map)va) : new HashMap<Object, Object>());
    }
    
    public boolean isInt(final String key) {
        return this.get(key) instanceof Integer;
    }
    
    public boolean isLong(final String key) {
        return this.get(key) instanceof Long;
    }
    
    public boolean isFloat(final String key) {
        return this.get(key) instanceof Float;
    }
    
    public boolean isDouble(final String key) {
        return this.get(key) instanceof Double;
    }
    
    public boolean isList(final String key) {
        return this.get(key) instanceof List;
    }
    
    public boolean isBoolean(final String key) {
        return this.get(key) instanceof Boolean;
    }
    
    public boolean isMap(final String key) {
        return this.get(key) instanceof Map;
    }
}
