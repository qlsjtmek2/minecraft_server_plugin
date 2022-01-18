// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.lib.util.DnodeReader;
import java.io.InputStream;
import com.avaje.ebeaninternal.server.lib.resource.ResourceContent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.List;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.lib.resource.ResourceSource;
import com.avaje.ebeaninternal.server.lib.util.Dnode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class DeployOrmXml
{
    private static final Logger logger;
    private final HashMap<String, DNativeQuery> nativeQueryCache;
    private final ArrayList<Dnode> ormXmlList;
    private final ResourceSource resSource;
    
    public DeployOrmXml(final ResourceSource resSource) {
        this.resSource = resSource;
        this.nativeQueryCache = new HashMap<String, DNativeQuery>();
        this.ormXmlList = this.findAllOrmXml();
        this.initialiseNativeQueries();
    }
    
    private void initialiseNativeQueries() {
        for (final Dnode ormXml : this.ormXmlList) {
            this.initialiseNativeQueries(ormXml);
        }
    }
    
    private void initialiseNativeQueries(final Dnode ormXml) {
        final Dnode entityMappings = ormXml.find("entity-mappings");
        if (entityMappings != null) {
            final List<Dnode> nq = entityMappings.findAll("named-native-query", 1);
            for (int i = 0; i < nq.size(); ++i) {
                final Dnode nqNode = nq.get(i);
                final Dnode nqQueryNode = nqNode.find("query");
                if (nqQueryNode != null) {
                    final String queryContent = nqQueryNode.getNodeContent();
                    final String queryName = nqNode.getAttribute("name");
                    if (queryName != null && queryContent != null) {
                        final DNativeQuery query = new DNativeQuery(queryContent);
                        this.nativeQueryCache.put(queryName, query);
                    }
                }
            }
        }
    }
    
    public DNativeQuery getNativeQuery(final String name) {
        return this.nativeQueryCache.get(name);
    }
    
    private ArrayList<Dnode> findAllOrmXml() {
        final ArrayList<Dnode> ormXmlList = new ArrayList<Dnode>();
        final String defaultFile = "orm.xml";
        this.readOrmXml(defaultFile, ormXmlList);
        if (!ormXmlList.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            for (final Dnode ox : ormXmlList) {
                sb.append(", ").append(ox.getAttribute("ebean.filename"));
            }
            final String loadedFiles = sb.toString().substring(2);
            DeployOrmXml.logger.info("Deployment xml [" + loadedFiles + "]  loaded.");
        }
        return ormXmlList;
    }
    
    private boolean readOrmXml(final String ormXmlName, final ArrayList<Dnode> ormXmlList) {
        try {
            Dnode ormXml = null;
            final ResourceContent content = this.resSource.getContent(ormXmlName);
            if (content != null) {
                ormXml = this.readOrmXml(content.getInputStream());
            }
            else {
                ormXml = this.readOrmXmlFromClasspath(ormXmlName);
            }
            if (ormXml != null) {
                ormXml.setAttribute("ebean.filename", ormXmlName);
                ormXmlList.add(ormXml);
                return true;
            }
            return false;
        }
        catch (IOException e) {
            DeployOrmXml.logger.log(Level.SEVERE, "error reading orm xml deployment " + ormXmlName, e);
            return false;
        }
    }
    
    private Dnode readOrmXmlFromClasspath(final String ormXmlName) throws IOException {
        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(ormXmlName);
        if (is == null) {
            return null;
        }
        return this.readOrmXml(is);
    }
    
    private Dnode readOrmXml(final InputStream in) throws IOException {
        final DnodeReader reader = new DnodeReader();
        final Dnode ormXml = reader.parseXml(in);
        in.close();
        return ormXml;
    }
    
    public Dnode findEntityDeploymentXml(final String className) {
        for (final Dnode ormXml : this.ormXmlList) {
            final Dnode entityMappings = ormXml.find("entity-mappings");
            final List<Dnode> entities = entityMappings.findAll("entity", "class", className, 1);
            if (entities.size() == 1) {
                return entities.get(0);
            }
        }
        return null;
    }
    
    static {
        logger = Logger.getLogger(DeployOrmXml.class.getName());
    }
}
