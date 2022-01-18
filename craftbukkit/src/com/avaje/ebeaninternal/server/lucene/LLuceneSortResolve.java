// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.Sort;
import com.avaje.ebean.OrderBy;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;

public class LLuceneSortResolve
{
    private final LuceneResolvableRequest req;
    private final OrderBy<?> orderBy;
    private Sort sort;
    private boolean isResolved;
    private String unsortableField;
    
    public LLuceneSortResolve(final LuceneResolvableRequest req, final OrderBy<?> orderBy) {
        this.req = req;
        this.orderBy = orderBy;
        this.isResolved = this.resolve();
    }
    
    public boolean isResolved() {
        return this.isResolved;
    }
    
    public Sort getSort() {
        return this.sort;
    }
    
    public String getUnsortableField() {
        return this.unsortableField;
    }
    
    private boolean resolve() {
        final BeanDescriptor<?> beanDescriptor = this.req.getBeanDescriptor();
        if (this.orderBy != null) {
            final List<OrderBy.Property> properties = this.orderBy.getProperties();
            final SortField[] sortFields = new SortField[properties.size()];
            for (int i = 0; i < properties.size(); ++i) {
                final OrderBy.Property property = properties.get(i);
                final SortField sf = this.createSortField(property, beanDescriptor);
                if (sf == null) {
                    this.unsortableField = property.getProperty();
                    return false;
                }
                sortFields[i] = sf;
            }
            this.sort = new Sort(sortFields);
        }
        return true;
    }
    
    private SortField createSortField(final OrderBy.Property property, final BeanDescriptor<?> beanDescriptor) {
        final String propName = property.getProperty();
        final LIndexField sortField = this.req.getSortableProperty(propName);
        if (sortField == null) {
            return null;
        }
        final int sortType = sortField.getSortType();
        return (sortType == -1) ? null : new SortField(sortField.getName(), sortType, !property.isAscending());
    }
}
