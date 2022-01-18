// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.query.SplitName;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import com.avaje.ebean.config.lucene.LuceneIndex;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.HashMap;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.logging.Logger;

public class LIndexFields
{
    private static final Logger logger;
    private final String defaultFieldName;
    private final BeanDescriptor<?> descriptor;
    private final LIndexField[] fields;
    private final LIndexField[] readFields;
    private final PathProperties pathProperties;
    private final OrmQueryDetail ormQueryDetail;
    private final HashMap<String, LIndexField> fieldMap;
    private final HashMap<String, LIndexField> sortableExpressionMap;
    private final LinkedHashSet<String> requiredPropertyNames;
    private final LinkedHashSet<String> resolvePropertyNames;
    private final LinkedHashSet<String> restorePropertyNames;
    private final Set<String> nonRestorablePropertyNames;
    private final LIndexFieldId idField;
    
    public LIndexFields(final List<LIndexField> definedFields, final BeanDescriptor<?> descriptor, final String defaultFieldName) {
        this.fieldMap = new HashMap<String, LIndexField>();
        this.sortableExpressionMap = new HashMap<String, LIndexField>();
        this.requiredPropertyNames = new LinkedHashSet<String>();
        this.resolvePropertyNames = new LinkedHashSet<String>();
        this.restorePropertyNames = new LinkedHashSet<String>();
        this.descriptor = descriptor;
        this.defaultFieldName = defaultFieldName;
        this.fields = definedFields.toArray(new LIndexField[definedFields.size()]);
        LIndexFieldId tempIdField = null;
        for (final LIndexField field : this.fields) {
            final String fieldName = field.getName();
            if (field instanceof LIndexFieldId && fieldName.indexOf(46) == -1) {
                tempIdField = (LIndexFieldId)field;
            }
            this.fieldMap.put(fieldName, field);
            field.addIndexRequiredPropertyNames(this.requiredPropertyNames);
            field.addIndexResolvePropertyNames(this.resolvePropertyNames);
            field.addIndexRestorePropertyNames(this.restorePropertyNames);
            final String sortableProperty = field.getSortableProperty();
            if (sortableProperty != null) {
                this.sortableExpressionMap.put(sortableProperty, field);
            }
        }
        this.idField = tempIdField;
        this.nonRestorablePropertyNames = this.getNonRestorableProperties();
        if (!this.nonRestorablePropertyNames.isEmpty()) {
            LIndexFields.logger.info("Index has properties [" + this.nonRestorablePropertyNames + "] that are not stored");
        }
        this.readFields = this.createReadFields();
        this.pathProperties = this.createPathProperties();
        this.ormQueryDetail = this.createOrmQueryDetail();
    }
    
    public LIndexFieldId getIdField() {
        return this.idField;
    }
    
    public void registerIndexWithProperties(final LuceneIndex luceneIndex) {
        final IndexInvalidate invalidate = new IndexInvalidate(this.descriptor.getBeanType().getName());
        for (final String prop : this.restorePropertyNames) {
            final ElPropertyValue elProp = this.descriptor.getElGetValue(prop);
            if (elProp.isAssocProperty()) {
                elProp.getBeanProperty().getBeanDescriptor().addIndexInvalidate(invalidate);
            }
            else {
                elProp.getBeanProperty().registerLuceneIndex(luceneIndex);
            }
        }
    }
    
    private Set<String> getNonRestorableProperties() {
        final HashSet<String> nonRestoreable = new HashSet<String>();
        for (final String reqrProp : this.requiredPropertyNames) {
            if (!this.restorePropertyNames.contains(reqrProp)) {
                nonRestoreable.add(reqrProp);
            }
        }
        return nonRestoreable;
    }
    
    public LIndexField getSortableField(final String propertyName) {
        return this.sortableExpressionMap.get(propertyName);
    }
    
    public QueryParser createQueryParser(String fieldName) {
        if (fieldName == null) {
            fieldName = this.defaultFieldName;
        }
        final LIndexField fld = this.fieldMap.get(fieldName);
        if (fld == null) {
            throw new NullPointerException("fieldName [" + fieldName + "] not in index?");
        }
        return fld.createQueryParser();
    }
    
    public OrmQueryDetail getOrmQueryDetail() {
        return this.ormQueryDetail;
    }
    
    public Set<String> getResolvePropertyNames() {
        return this.resolvePropertyNames;
    }
    
    public LinkedHashSet<String> getRequiredPropertyNames() {
        return this.requiredPropertyNames;
    }
    
    public DocFieldWriter createDocFieldWriter() {
        final DocFieldWriter[] dw = new DocFieldWriter[this.fields.length];
        for (int j = 0; j < dw.length; ++j) {
            dw[j] = this.fields[j].createDocFieldWriter();
        }
        return new Writer(dw);
    }
    
    public void readDocument(final Document doc, final Object bean) {
        for (final LIndexField indexField : this.readFields) {
            indexField.readValue(doc, bean);
        }
    }
    
    public LIndexField[] getReadFields() {
        return this.readFields;
    }
    
    private LIndexField[] createReadFields() {
        final ArrayList<LIndexField> readFieldList = new ArrayList<LIndexField>();
        for (int i = 0; i < this.fields.length; ++i) {
            final LIndexField field = this.fields[i];
            if (field.isStored() && field.isBeanProperty()) {
                readFieldList.add(field);
            }
        }
        Collections.sort(readFieldList, new Comparator<LIndexField>() {
            public int compare(final LIndexField o1, final LIndexField o2) {
                final int v1 = o1.getPropertyOrder();
                final int v2 = o2.getPropertyOrder();
                return (v1 < v2) ? -1 : ((v1 == v2) ? 0 : 1);
            }
        });
        return readFieldList.toArray(new LIndexField[readFieldList.size()]);
    }
    
    private PathProperties createPathProperties() {
        final PathProperties pathProps = new PathProperties();
        for (int i = 0; i < this.readFields.length; ++i) {
            final LIndexField field = this.readFields[i];
            String propertyName = field.getName();
            final ElPropertyValue el = field.getElBeanProperty();
            if (el.getBeanProperty().isId()) {
                propertyName = SplitName.parent(propertyName);
            }
            if (propertyName != null) {
                final String[] pathProp = SplitName.split(propertyName);
                pathProps.addToPath(pathProp[0], pathProp[1]);
            }
        }
        return pathProps;
    }
    
    private OrmQueryDetail createOrmQueryDetail() {
        final OrmQueryDetail detail = new OrmQueryDetail();
        for (final String path : this.pathProperties.getPaths()) {
            final Set<String> props = this.pathProperties.get(path);
            detail.getChunk(path, true).setDefaultProperties(null, props);
        }
        return detail;
    }
    
    static {
        logger = Logger.getLogger(LIndexFields.class.getName());
    }
    
    static class Writer implements DocFieldWriter
    {
        private final DocFieldWriter[] dw;
        
        private Writer(final DocFieldWriter[] dw) {
            this.dw = dw;
        }
        
        public void writeValue(final Object bean, final Document document) {
            for (final DocFieldWriter w : this.dw) {
                w.writeValue(bean, document);
            }
        }
    }
}
