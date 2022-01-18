// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Map;
import java.util.List;
import java.util.Collection;

public interface ExpressionFactory
{
    String getLang();
    
    Expression eq(final String p0, final Object p1);
    
    Expression ne(final String p0, final Object p1);
    
    Expression ieq(final String p0, final String p1);
    
    Expression between(final String p0, final Object p1, final Object p2);
    
    Expression betweenProperties(final String p0, final String p1, final Object p2);
    
    Expression gt(final String p0, final Object p1);
    
    Expression ge(final String p0, final Object p1);
    
    Expression lt(final String p0, final Object p1);
    
    Expression le(final String p0, final Object p1);
    
    Expression isNull(final String p0);
    
    Expression isNotNull(final String p0);
    
    ExampleExpression iexampleLike(final Object p0);
    
    ExampleExpression exampleLike(final Object p0);
    
    ExampleExpression exampleLike(final Object p0, final boolean p1, final LikeType p2);
    
    Expression like(final String p0, final String p1);
    
    Expression ilike(final String p0, final String p1);
    
    Expression startsWith(final String p0, final String p1);
    
    Expression istartsWith(final String p0, final String p1);
    
    Expression endsWith(final String p0, final String p1);
    
    Expression iendsWith(final String p0, final String p1);
    
    Expression contains(final String p0, final String p1);
    
    Expression lucene(final String p0);
    
    Expression lucene(final String p0, final String p1);
    
    Expression icontains(final String p0, final String p1);
    
    Expression in(final String p0, final Object[] p1);
    
    Expression in(final String p0, final Query<?> p1);
    
    Expression in(final String p0, final Collection<?> p1);
    
    Expression idEq(final Object p0);
    
    Expression idIn(final List<?> p0);
    
    Expression allEq(final Map<String, Object> p0);
    
    Expression raw(final String p0, final Object p1);
    
    Expression raw(final String p0, final Object[] p1);
    
    Expression raw(final String p0);
    
    Expression and(final Expression p0, final Expression p1);
    
    Expression or(final Expression p0, final Expression p1);
    
    Expression not(final Expression p0);
    
     <T> Junction<T> conjunction(final Query<T> p0);
    
     <T> Junction<T> disjunction(final Query<T> p0);
    
     <T> Junction<T> conjunction(final Query<T> p0, final ExpressionList<T> p1);
    
     <T> Junction<T> disjunction(final Query<T> p0, final ExpressionList<T> p1);
}
