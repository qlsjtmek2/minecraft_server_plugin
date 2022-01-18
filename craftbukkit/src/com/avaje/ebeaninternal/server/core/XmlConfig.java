// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.lib.util.Dnode;
import java.util.List;

public class XmlConfig
{
    private final List<Dnode> ebeanOrmXml;
    private final List<Dnode> ormXml;
    private final List<Dnode> allXml;
    
    public XmlConfig(final List<Dnode> ormXml, final List<Dnode> ebeanOrmXml) {
        this.ormXml = ormXml;
        this.ebeanOrmXml = ebeanOrmXml;
        (this.allXml = new ArrayList<Dnode>(ormXml.size() + ebeanOrmXml.size())).addAll(ormXml);
        this.allXml.addAll(ebeanOrmXml);
    }
    
    public List<Dnode> getEbeanOrmXml() {
        return this.ebeanOrmXml;
    }
    
    public List<Dnode> getOrmXml() {
        return this.ormXml;
    }
    
    public List<Dnode> find(final List<Dnode> entityXml, final String element) {
        final ArrayList<Dnode> hits = new ArrayList<Dnode>();
        for (int i = 0; i < entityXml.size(); ++i) {
            hits.addAll(entityXml.get(i).findAll(element, 1));
        }
        return hits;
    }
    
    public List<Dnode> findEntityXml(final String className) {
        final ArrayList<Dnode> hits = new ArrayList<Dnode>(2);
        for (final Dnode ormXml : this.allXml) {
            final Dnode entityMappings = ormXml.find("entity-mappings");
            final List<Dnode> entities = entityMappings.findAll("entity", "class", className, 1);
            if (entities.size() == 1) {
                hits.add(entities.get(0));
            }
        }
        return hits;
    }
}
