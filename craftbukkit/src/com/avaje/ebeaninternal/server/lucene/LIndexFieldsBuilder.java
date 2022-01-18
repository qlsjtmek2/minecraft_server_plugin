// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import org.apache.lucene.document.Field;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class LIndexFieldsBuilder implements SpiIndexDefnHelper
{
    private final BeanDescriptor<?> desc;
    private final LinkedHashMap<String, IndexFieldDefn> fieldDefnMap;
    
    public LIndexFieldsBuilder(final BeanDescriptor<?> desc) {
        this.fieldDefnMap = new LinkedHashMap<String, IndexFieldDefn>();
        this.desc = desc;
    }
    
    public Nested assocOne(final String propertyName) {
        final BeanProperty beanProperty = this.desc.getBeanProperty(propertyName);
        if (beanProperty instanceof BeanPropertyAssocOne) {
            final BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>)beanProperty;
            final BeanDescriptor<?> targetDescriptor = assocOne.getTargetDescriptor();
            return new Nested(this, propertyName, targetDescriptor);
        }
        throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.desc.getFullName());
    }
    
    public void addAllFields() {
        final Iterator<BeanProperty> it = this.desc.propertiesAll();
        while (it.hasNext()) {
            final BeanProperty beanProperty = it.next();
            if (beanProperty instanceof BeanPropertyAssocMany) {
                continue;
            }
            if (beanProperty instanceof BeanPropertyAssocOne) {
                continue;
            }
            this.addField(beanProperty.getName());
        }
    }
    
    public IndexFieldDefn addField(final String propertyName) {
        return this.addField(propertyName, null);
    }
    
    public IndexFieldDefn addField(final String propertyName, final IndexFieldDefn.Sortable sortable) {
        return this.addField(propertyName, null, null, sortable);
    }
    
    public IndexFieldDefn addFieldConcat(final String fieldName, final String... propertyNames) {
        return this.addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames);
    }
    
    public IndexFieldDefn addFieldConcat(final String fieldName, final Field.Store store, final Field.Index index, final String... propertyNames) {
        return this.addPrefixFieldConcat(null, fieldName, store, index, propertyNames);
    }
    
    public IndexFieldDefn addPrefixField(final String prefix, final String propertyName, final Field.Store store, final Field.Index index, final IndexFieldDefn.Sortable sortable) {
        final String fullPath = prefix + "." + propertyName;
        return this.addField(fullPath, store, index, sortable);
    }
    
    public IndexFieldDefn addPrefixFieldConcat(final String prefix, final String fieldName, final Field.Store store, final Field.Index index, final String[] propertyNames) {
        if (prefix != null) {
            for (int i = 0; i < propertyNames.length; ++i) {
                propertyNames[i] = prefix + "." + propertyNames;
            }
        }
        final IndexFieldDefn fieldDefn = new IndexFieldDefn(fieldName, store, index, null);
        fieldDefn.setPropertyNames(propertyNames);
        this.fieldDefnMap.put(fieldName, fieldDefn);
        return fieldDefn;
    }
    
    public IndexFieldDefn addField(final IndexFieldDefn fieldDefn) {
        this.fieldDefnMap.put(fieldDefn.getName(), fieldDefn);
        return fieldDefn;
    }
    
    public IndexFieldDefn addField(final String propertyName, Field.Store store, Field.Index index, final IndexFieldDefn.Sortable sortable) {
        final ElPropertyValue prop = this.desc.getElGetValue(propertyName);
        if (prop == null) {
            final String msg = "Property [" + propertyName + "] not found on " + this.desc.getFullName();
            throw new NullPointerException(msg);
        }
        final BeanProperty beanProperty = prop.getBeanProperty();
        final ScalarType<?> scalarType = beanProperty.getScalarType();
        if (store == null) {
            store = (this.isLob(scalarType) ? Field.Store.NO : Field.Store.YES);
        }
        final boolean luceneStringType = beanProperty.isId() || this.isLuceneString(scalarType.getLuceneType());
        if (index == null) {
            if (beanProperty.isId() || !luceneStringType) {
                index = Field.Index.NOT_ANALYZED;
            }
            else {
                index = Field.Index.ANALYZED;
            }
        }
        final IndexFieldDefn fieldDefn = new IndexFieldDefn(propertyName, store, index, sortable);
        this.fieldDefnMap.put(propertyName, fieldDefn);
        if (luceneStringType && index.isAnalyzed() && IndexFieldDefn.Sortable.YES.equals(sortable)) {
            final IndexFieldDefn extraFieldDefn = new IndexFieldDefn(propertyName + "_sortonly", Field.Store.NO, Field.Index.NOT_ANALYZED, IndexFieldDefn.Sortable.YES);
            extraFieldDefn.setPropertyName(propertyName);
            this.fieldDefnMap.put(extraFieldDefn.getName(), extraFieldDefn);
        }
        return fieldDefn;
    }
    
    public IndexFieldDefn getField(final String fieldName) {
        return this.fieldDefnMap.get(fieldName);
    }
    
    private boolean isLuceneString(final int luceneType) {
        return 0 == luceneType;
    }
    
    private boolean isLob(final ScalarType<?> scalarType) {
        final int jdbcType = scalarType.getJdbcType();
        switch (jdbcType) {
            case 2005: {
                return true;
            }
            case 2004: {
                return true;
            }
            case -1: {
                return true;
            }
            case -4: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public List<IndexFieldDefn> getFields() {
        final ArrayList<IndexFieldDefn> fields = new ArrayList<IndexFieldDefn>();
        fields.addAll(this.fieldDefnMap.values());
        return fields;
    }
    
    private static class Nested implements SpiIndexDefnHelper
    {
        private final String path;
        private final BeanDescriptor<?> targetDescriptor;
        private final SpiIndexDefnHelper parent;
        
        Nested(final SpiIndexDefnHelper parent, final String path, final BeanDescriptor<?> targetDescriptor) {
            this.parent = parent;
            this.path = path;
            this.targetDescriptor = targetDescriptor;
        }
        
        public IndexDefnBuilder assocOne(final String propertyName) {
            final BeanProperty beanProperty = this.targetDescriptor.getBeanProperty(propertyName);
            if (beanProperty instanceof BeanPropertyAssocOne) {
                final BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>)beanProperty;
                final BeanDescriptor<?> targetDescriptor = assocOne.getTargetDescriptor();
                return new Nested(this, propertyName, targetDescriptor);
            }
            throw new IllegalArgumentException("Expecing " + propertyName + " to be an AssocOne property of " + this.targetDescriptor.getFullName());
        }
        
        public void addAllFields() {
            final Iterator<BeanProperty> it = this.targetDescriptor.propertiesAll();
            while (it.hasNext()) {
                final BeanProperty beanProperty = it.next();
                if (beanProperty instanceof BeanPropertyAssocMany) {
                    continue;
                }
                if (beanProperty instanceof BeanPropertyAssocOne) {
                    continue;
                }
                if (beanProperty.isTransient()) {
                    continue;
                }
                this.addField(beanProperty.getName());
            }
        }
        
        public IndexFieldDefn addField(final IndexFieldDefn fieldDefn) {
            this.parent.addField(fieldDefn);
            return fieldDefn;
        }
        
        public IndexFieldDefn addField(final String propertyName) {
            return this.addField(propertyName, null);
        }
        
        public IndexFieldDefn addField(final String propertyName, final IndexFieldDefn.Sortable sortable) {
            return this.addField(propertyName, null, null, sortable);
        }
        
        public IndexFieldDefn addField(final String propertyName, final Field.Store store, final Field.Index index, final IndexFieldDefn.Sortable sortable) {
            return this.parent.addPrefixField(this.path, propertyName, store, index, sortable);
        }
        
        public IndexFieldDefn addFieldConcat(final String fieldName, final String... propertyNames) {
            return this.addFieldConcat(fieldName, Field.Store.NO, Field.Index.ANALYZED, propertyNames);
        }
        
        public IndexFieldDefn addFieldConcat(final String fieldName, final Field.Store store, final Field.Index index, final String... propertyNames) {
            return this.parent.addPrefixFieldConcat(this.path, fieldName, store, index, propertyNames);
        }
        
        public IndexFieldDefn addPrefixFieldConcat(final String prefix, final String fieldName, final Field.Store store, final Field.Index index, final String[] propertyNames) {
            final String nestedPath = this.path + "." + prefix;
            return this.parent.addPrefixFieldConcat(nestedPath, fieldName, store, index, propertyNames);
        }
        
        public IndexFieldDefn addPrefixField(final String prefix, final String propertyName, final Field.Store store, final Field.Index index, final IndexFieldDefn.Sortable sortable) {
            final String nestedPath = prefix + "." + propertyName;
            return this.addField(nestedPath, store, index, sortable);
        }
        
        public IndexFieldDefn getField(final String fieldName) {
            return this.parent.getField(fieldName);
        }
        
        public List<IndexFieldDefn> getFields() {
            return this.parent.getFields();
        }
    }
}
