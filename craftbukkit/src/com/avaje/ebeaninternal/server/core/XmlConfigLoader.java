// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.jar.JarFile;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import com.avaje.ebeaninternal.server.lib.util.DnodeReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.net.URLDecoder;
import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.lib.util.Dnode;
import java.util.List;
import com.avaje.ebeaninternal.server.util.DefaultClassPathReader;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.util.ClassPathReader;
import java.util.logging.Logger;

public class XmlConfigLoader
{
    private static final Logger logger;
    private final ClassPathReader classPathReader;
    private final Object[] classPaths;
    
    public XmlConfigLoader(ClassLoader classLoader) {
        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }
        final String cn = GlobalProperties.get("ebean.classpathreader", null);
        if (cn != null) {
            XmlConfigLoader.logger.info("Using [" + cn + "] to read the searchable class path");
            this.classPathReader = (ClassPathReader)ClassUtil.newInstance(cn, this.getClass());
        }
        else {
            this.classPathReader = new DefaultClassPathReader();
        }
        this.classPaths = this.classPathReader.readPath(classLoader);
    }
    
    public XmlConfig load() {
        final List<Dnode> ormXml = this.search("META-INF/orm.xml");
        final List<Dnode> ebeanOrmXml = this.search("META-INF/ebean-orm.xml");
        return new XmlConfig(ormXml, ebeanOrmXml);
    }
    
    public List<Dnode> search(final String searchFor) {
        final ArrayList<Dnode> xmlList = new ArrayList<Dnode>();
        final String charsetName = Charset.defaultCharset().name();
        for (int h = 0; h < this.classPaths.length; ++h) {
            try {
                File classPath;
                if (URL.class.isInstance(this.classPaths[h])) {
                    classPath = new File(((URL)this.classPaths[h]).getFile());
                }
                else {
                    classPath = new File(this.classPaths[h].toString());
                }
                final String path = URLDecoder.decode(classPath.getAbsolutePath(), charsetName);
                classPath = new File(path);
                if (classPath.isDirectory()) {
                    this.checkDir(searchFor, xmlList, classPath);
                }
                else if (classPath.getName().endsWith(".jar")) {
                    this.checkJar(searchFor, xmlList, classPath);
                }
                else {
                    final String msg = "Not a Jar or Directory? " + classPath.getAbsolutePath();
                    XmlConfigLoader.logger.log(Level.SEVERE, msg);
                }
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            catch (IOException e2) {
                throw new RuntimeException(e2);
            }
        }
        return xmlList;
    }
    
    private void processInputStream(final ArrayList<Dnode> xmlList, final InputStream is) throws IOException {
        final DnodeReader reader = new DnodeReader();
        final Dnode xmlDoc = reader.parseXml(is);
        is.close();
        xmlList.add(xmlDoc);
    }
    
    private void checkFile(final String searchFor, final ArrayList<Dnode> xmlList, final File dir) throws IOException {
        final File f = new File(dir, searchFor);
        if (f.exists()) {
            final FileInputStream fis = new FileInputStream(f);
            final BufferedInputStream is = new BufferedInputStream(fis);
            this.processInputStream(xmlList, is);
        }
    }
    
    private void checkDir(final String searchFor, final ArrayList<Dnode> xmlList, final File dir) throws IOException {
        this.checkFile(searchFor, xmlList, dir);
        if (dir.getPath().endsWith("classes")) {
            File parent = dir.getParentFile();
            if (parent != null && parent.getPath().endsWith("WEB-INF")) {
                parent = parent.getParentFile();
                if (parent != null) {
                    final File metaInf = new File(parent, "META-INF");
                    if (metaInf.exists()) {
                        this.checkFile(searchFor, xmlList, metaInf);
                    }
                }
            }
        }
    }
    
    private void checkJar(final String searchFor, final ArrayList<Dnode> xmlList, final File classPath) throws IOException {
        final String fileName = classPath.getName();
        if (fileName.toLowerCase().startsWith("surefire")) {
            return;
        }
        JarFile module = null;
        try {
            module = new JarFile(classPath);
            final ZipEntry entry = module.getEntry(searchFor);
            if (entry != null) {
                final InputStream is = module.getInputStream(entry);
                this.processInputStream(xmlList, is);
            }
        }
        catch (ZipException e) {
            XmlConfigLoader.logger.info("Unable to check jar file " + fileName + " for ebean-orm.xml");
        }
        finally {
            if (module != null) {
                module.close();
            }
        }
    }
    
    static {
        logger = Logger.getLogger(XmlConfigLoader.class.getName());
    }
}
