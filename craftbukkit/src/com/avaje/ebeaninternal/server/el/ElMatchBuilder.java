// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.el;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

class ElMatchBuilder
{
    static class RegularExpr<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        final String value;
        final Pattern pattern;
        
        RegularExpr(final ElPropertyValue elGetValue, final String value, final int options) {
            this.elGetValue = elGetValue;
            this.value = value;
            this.pattern = Pattern.compile(value, options);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.pattern.matcher(v).matches();
        }
    }
    
    abstract static class BaseString<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        final String value;
        
        public BaseString(final ElPropertyValue elGetValue, final String value) {
            this.elGetValue = elGetValue;
            this.value = value;
        }
        
        public abstract boolean isMatch(final T p0);
    }
    
    static class Ieq<T> extends BaseString<T>
    {
        Ieq(final ElPropertyValue elGetValue, final String value) {
            super(elGetValue, value);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.value.equalsIgnoreCase(v);
        }
    }
    
    static class IStartsWith<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        final CharMatch charMatch;
        
        IStartsWith(final ElPropertyValue elGetValue, final String value) {
            this.elGetValue = elGetValue;
            this.charMatch = new CharMatch(value);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.charMatch.startsWith(v);
        }
    }
    
    static class IEndsWith<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        final CharMatch charMatch;
        
        IEndsWith(final ElPropertyValue elGetValue, final String value) {
            this.elGetValue = elGetValue;
            this.charMatch = new CharMatch(value);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.charMatch.endsWith(v);
        }
    }
    
    static class StartsWith<T> extends BaseString<T>
    {
        StartsWith(final ElPropertyValue elGetValue, final String value) {
            super(elGetValue, value);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.value.startsWith(v);
        }
    }
    
    static class EndsWith<T> extends BaseString<T>
    {
        EndsWith(final ElPropertyValue elGetValue, final String value) {
            super(elGetValue, value);
        }
        
        public boolean isMatch(final T bean) {
            final String v = (String)this.elGetValue.elGetValue(bean);
            return this.value.endsWith(v);
        }
    }
    
    static class IsNull<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        
        public IsNull(final ElPropertyValue elGetValue) {
            this.elGetValue = elGetValue;
        }
        
        public boolean isMatch(final T bean) {
            return null == this.elGetValue.elGetValue(bean);
        }
    }
    
    static class IsNotNull<T> implements ElMatcher<T>
    {
        final ElPropertyValue elGetValue;
        
        public IsNotNull(final ElPropertyValue elGetValue) {
            this.elGetValue = elGetValue;
        }
        
        public boolean isMatch(final T bean) {
            return null != this.elGetValue.elGetValue(bean);
        }
    }
    
    abstract static class Base<T> implements ElMatcher<T>
    {
        final Object filterValue;
        final ElComparator<T> comparator;
        
        public Base(final Object filterValue, final ElComparator<T> comparator) {
            this.filterValue = filterValue;
            this.comparator = comparator;
        }
        
        public abstract boolean isMatch(final T p0);
    }
    
    static class InSet<T> implements ElMatcher<T>
    {
        final Set<?> set;
        final ElPropertyValue elGetValue;
        
        public InSet(final Set<?> set, final ElPropertyValue elGetValue) {
            this.set = new HashSet<Object>(set);
            this.elGetValue = elGetValue;
        }
        
        public boolean isMatch(final T bean) {
            final Object value = this.elGetValue.elGetValue(bean);
            return value != null && this.set.contains(value);
        }
    }
    
    static class Eq<T> extends Base<T>
    {
        public Eq(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) == 0;
        }
    }
    
    static class Ne<T> extends Base<T>
    {
        public Ne(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) != 0;
        }
    }
    
    static class Between<T> implements ElMatcher<T>
    {
        final Object min;
        final Object max;
        final ElComparator<T> comparator;
        
        Between(final Object min, final Object max, final ElComparator<T> comparator) {
            this.min = min;
            this.max = max;
            this.comparator = comparator;
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.min, value) <= 0 && this.comparator.compareValue(this.max, value) >= 0;
        }
    }
    
    static class Gt<T> extends Base<T>
    {
        Gt(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) == -1;
        }
    }
    
    static class Ge<T> extends Base<T>
    {
        Ge(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) >= 0;
        }
    }
    
    static class Le<T> extends Base<T>
    {
        Le(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) <= 0;
        }
    }
    
    static class Lt<T> extends Base<T>
    {
        Lt(final Object filterValue, final ElComparator<T> comparator) {
            super(filterValue, comparator);
        }
        
        public boolean isMatch(final T value) {
            return this.comparator.compareValue(this.filterValue, value) == 1;
        }
    }
}
