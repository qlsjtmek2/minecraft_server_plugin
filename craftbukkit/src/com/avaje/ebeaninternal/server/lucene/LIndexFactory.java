// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import org.apache.lucene.document.Field;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.List;
import org.apache.lucene.index.IndexWriter;
import com.avaje.ebean.config.lucene.IndexFieldDefn;
import java.util.ArrayList;
import com.avaje.ebean.config.lucene.IndexDefnBuilder;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.lucene.IndexDefn;

public class LIndexFactory
{
    private final DefaultLuceneIndexManager manager;
    
    public LIndexFactory(final DefaultLuceneIndexManager manager) {
        this.manager = manager;
    }
    
    public LIndex create(final IndexDefn<?> indexDefn, final BeanDescriptor<?> descriptor) throws IOException {
        final Analyzer defaultAnalyzer = this.manager.getDefaultAnalyzer();
        return new Factory((IndexDefn)indexDefn, (BeanDescriptor)descriptor, this.manager, defaultAnalyzer).create();
    }
    
    static class Factory
    {
        private final Analyzer defaultAnalyzer;
        private final DefaultLuceneIndexManager manager;
        private final IndexDefn<?> indexDefn;
        private final BeanDescriptor<?> descriptor;
        
        private Factory(final IndexDefn<?> indexDefn, final BeanDescriptor<?> descriptor, final DefaultLuceneIndexManager manager, final Analyzer defaultAnalyzer) {
            this.indexDefn = indexDefn;
            this.descriptor = descriptor;
            this.defaultAnalyzer = defaultAnalyzer;
            this.manager = manager;
        }
        
        public LIndex create() throws IOException {
            final LIndexFieldsBuilder helper = new LIndexFieldsBuilder(this.descriptor);
            this.indexDefn.initialise(helper);
            final List<LIndexField> definedFields = new ArrayList<LIndexField>();
            final List<IndexFieldDefn> fields = this.indexDefn.getFields();
            for (int i = 0; i < fields.size(); ++i) {
                final IndexFieldDefn fieldDefn = fields.get(i);
                final LIndexField field = this.creatField(fieldDefn);
                definedFields.add(field);
            }
            final String defaultField = this.indexDefn.getDefaultField();
            final LIndexFields fieldGroup = new LIndexFields(definedFields, this.descriptor, defaultField);
            Analyzer analyzer = this.indexDefn.getAnalyzer();
            if (analyzer == null) {
                analyzer = this.defaultAnalyzer;
            }
            IndexWriter.MaxFieldLength maxFieldLength = this.indexDefn.getMaxFieldLength();
            if (maxFieldLength == null) {
                maxFieldLength = IndexWriter.MaxFieldLength.UNLIMITED;
            }
            final String indexName = this.indexDefn.getClass().getName();
            final String indexDir = this.manager.getIndexDirectory(indexName);
            return new LIndex(this.manager, indexName, indexDir, analyzer, maxFieldLength, this.descriptor, fieldGroup, this.indexDefn.getUpdateSinceProperties());
        }
        
        private ElPropertyValue getProperty(final String name) {
            final ElPropertyValue prop = this.descriptor.getElGetValue(name);
            if (prop == null) {
                final String msg = "Property [" + name + "] not found on " + this.descriptor.getFullName();
                throw new NullPointerException(msg);
            }
            return prop;
        }
        
        private LIndexField creatField(final IndexFieldDefn fieldDefn) {
            final String fieldName = fieldDefn.getName();
            final Analyzer queryAnalyzer = this.getQueryAnalyzer(fieldDefn);
            final Field.Store store = fieldDefn.getStore();
            final Field.Index index = fieldDefn.getIndex();
            int luceneType = 0;
            final Analyzer indexAnalyzer = fieldDefn.getIndexAnalyzer();
            final float boost = fieldDefn.getBoost();
            final String[] propertyNames = fieldDefn.getPropertyNames();
            if (propertyNames != null && propertyNames.length > 0) {
                final ElPropertyValue[] props = new ElPropertyValue[propertyNames.length];
                for (int i = 0; i < props.length; ++i) {
                    props[i] = this.getProperty(propertyNames[i]);
                }
                final FieldFactory fieldFactory = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldStringConcat(queryAnalyzer, fieldName, fieldFactory, props, indexAnalyzer);
            }
            final ElPropertyValue prop = this.getProperty(fieldDefn.getPropertyName());
            final BeanProperty beanProperty = prop.getBeanProperty();
            final ScalarType<?> scalarType = beanProperty.getScalarType();
            luceneType = scalarType.getLuceneType();
            if (beanProperty.isId()) {
                final IdBinder idBinder = beanProperty.getBeanDescriptor().getIdBinder();
                final FieldFactory fieldFactory2 = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldId(queryAnalyzer, fieldName, fieldFactory2, prop, idBinder);
            }
            if (luceneType == 7) {
                final FieldFactory fieldFactory3 = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldBinary(queryAnalyzer, fieldName, fieldFactory3, prop);
            }
            if (luceneType == 0) {
                final FieldFactory fieldFactory3 = FieldFactory.normal(fieldName, store, index, boost);
                return new LIndexFieldString(queryAnalyzer, fieldName, fieldFactory3, prop, indexAnalyzer);
            }
            int precisionStep = fieldDefn.getPrecisionStep();
            if (precisionStep < 0) {
                precisionStep = 8;
            }
            final FieldFactory fieldFactory2 = FieldFactory.numeric(fieldName, store, index, boost, precisionStep);
            return new LIndexFieldNumeric(queryAnalyzer, fieldName, fieldFactory2, luceneType, prop);
        }
        
        private Analyzer getQueryAnalyzer(final IndexFieldDefn fieldDefn) {
            Analyzer analyzer = fieldDefn.getQueryAnalyzer();
            if (analyzer == null) {
                analyzer = this.indexDefn.getAnalyzer();
            }
            return (analyzer == null) ? this.defaultAnalyzer : analyzer;
        }
    }
}
