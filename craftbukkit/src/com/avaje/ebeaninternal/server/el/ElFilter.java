// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.Filter;

public final class ElFilter<T> implements Filter<T>
{
    private final BeanDescriptor<T> beanDescriptor;
    private ArrayList<ElMatcher<T>> matches;
    private int maxRows;
    private String sortByClause;
    
    public ElFilter(final BeanDescriptor<T> beanDescriptor) {
        this.matches = new ArrayList<ElMatcher<T>>();
        this.beanDescriptor = beanDescriptor;
    }
    
    private Object convertValue(final String propertyName, final Object value) {
        final ElPropertyValue elGetValue = this.beanDescriptor.getElGetValue(propertyName);
        return elGetValue.elConvertType(value);
    }
    
    private ElComparator<T> getElComparator(final String propertyName) {
        return this.beanDescriptor.getElComparator(propertyName);
    }
    
    private ElPropertyValue getElGetValue(final String propertyName) {
        return this.beanDescriptor.getElGetValue(propertyName);
    }
    
    public Filter<T> sort(final String sortByClause) {
        this.sortByClause = sortByClause;
        return this;
    }
    
    protected boolean isMatch(final T bean) {
        for (int i = 0; i < this.matches.size(); ++i) {
            final ElMatcher<T> matcher = this.matches.get(i);
            if (!matcher.isMatch(bean)) {
                return false;
            }
        }
        return true;
    }
    
    public Filter<T> in(final String propertyName, final Set<?> matchingValues) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.InSet<T>(matchingValues, elGetValue));
        return this;
    }
    
    public Filter<T> eq(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Eq<T>(value, comparator));
        return this;
    }
    
    public Filter<T> ne(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Ne<T>(value, comparator));
        return this;
    }
    
    public Filter<T> between(final String propertyName, Object min, Object max) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        min = elGetValue.elConvertType(min);
        max = elGetValue.elConvertType(max);
        final ElComparator<T> elComparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Between<T>(min, max, elComparator));
        return this;
    }
    
    public Filter<T> gt(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Gt<T>(value, comparator));
        return this;
    }
    
    public Filter<T> ge(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Ge<T>(value, comparator));
        return this;
    }
    
    public Filter<T> ieq(final String propertyName, final String value) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.Ieq<T>(elGetValue, value));
        return this;
    }
    
    public Filter<T> isNotNull(final String propertyName) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.IsNotNull<T>(elGetValue));
        return this;
    }
    
    public Filter<T> isNull(final String propertyName) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.IsNull<T>(elGetValue));
        return this;
    }
    
    public Filter<T> le(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Le<T>(value, comparator));
        return this;
    }
    
    public Filter<T> lt(final String propertyName, Object value) {
        value = this.convertValue(propertyName, value);
        final ElComparator<T> comparator = this.getElComparator(propertyName);
        this.matches.add(new ElMatchBuilder.Lt<T>(value, comparator));
        return this;
    }
    
    public Filter<T> regex(final String propertyName, final String regEx) {
        return this.regex(propertyName, regEx, 0);
    }
    
    public Filter<T> regex(final String propertyName, final String regEx, final int options) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.RegularExpr<T>(elGetValue, regEx, options));
        return this;
    }
    
    public Filter<T> contains(final String propertyName, final String value) {
        final String quote = ".*" + Pattern.quote(value) + ".*";
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.RegularExpr<T>(elGetValue, quote, 0));
        return this;
    }
    
    public Filter<T> icontains(final String propertyName, final String value) {
        final String quote = ".*" + Pattern.quote(value) + ".*";
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.RegularExpr<T>(elGetValue, quote, 2));
        return this;
    }
    
    public Filter<T> endsWith(final String propertyName, final String value) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.EndsWith<T>(elGetValue, value));
        return this;
    }
    
    public Filter<T> startsWith(final String propertyName, final String value) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.StartsWith<T>(elGetValue, value));
        return this;
    }
    
    public Filter<T> iendsWith(final String propertyName, final String value) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.IEndsWith<T>(elGetValue, value));
        return this;
    }
    
    public Filter<T> istartsWith(final String propertyName, final String value) {
        final ElPropertyValue elGetValue = this.getElGetValue(propertyName);
        this.matches.add(new ElMatchBuilder.IStartsWith<T>(elGetValue, value));
        return this;
    }
    
    public Filter<T> maxRows(final int maxRows) {
        this.maxRows = maxRows;
        return this;
    }
    
    public List<T> filter(List<T> list) {
        if (this.sortByClause != null) {
            list = new ArrayList<T>((Collection<? extends T>)list);
            this.beanDescriptor.sort(list, this.sortByClause);
        }
        final ArrayList<T> filterList = new ArrayList<T>();
        for (int i = 0; i < list.size(); ++i) {
            final T t = list.get(i);
            if (this.isMatch(t)) {
                filterList.add(t);
                if (this.maxRows > 0 && filterList.size() >= this.maxRows) {
                    break;
                }
            }
        }
        return filterList;
    }
}
