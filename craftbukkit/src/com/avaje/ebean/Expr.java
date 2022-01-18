// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Map;
import java.util.Collection;

public class Expr
{
    public static Expression eq(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().eq(propertyName, value);
    }
    
    public static Expression ne(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().ne(propertyName, value);
    }
    
    public static Expression ieq(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().ieq(propertyName, value);
    }
    
    public static Expression between(final String propertyName, final Object value1, final Object value2) {
        return Ebean.getExpressionFactory().between(propertyName, value1, value2);
    }
    
    public static Expression gt(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().gt(propertyName, value);
    }
    
    public static Expression ge(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().ge(propertyName, value);
    }
    
    public static Expression lt(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().lt(propertyName, value);
    }
    
    public static Expression le(final String propertyName, final Object value) {
        return Ebean.getExpressionFactory().le(propertyName, value);
    }
    
    public static Expression isNull(final String propertyName) {
        return Ebean.getExpressionFactory().isNull(propertyName);
    }
    
    public static Expression isNotNull(final String propertyName) {
        return Ebean.getExpressionFactory().isNotNull(propertyName);
    }
    
    public static ExampleExpression iexampleLike(final Object example) {
        return Ebean.getExpressionFactory().iexampleLike(example);
    }
    
    public static ExampleExpression exampleLike(final Object example) {
        return Ebean.getExpressionFactory().exampleLike(example);
    }
    
    public static ExampleExpression exampleLike(final Object example, final boolean caseInsensitive, final LikeType likeType) {
        return Ebean.getExpressionFactory().exampleLike(example, caseInsensitive, likeType);
    }
    
    public static Expression like(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().like(propertyName, value);
    }
    
    public static Expression ilike(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().ilike(propertyName, value);
    }
    
    public static Expression startsWith(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().startsWith(propertyName, value);
    }
    
    public static Expression istartsWith(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().istartsWith(propertyName, value);
    }
    
    public static Expression endsWith(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().endsWith(propertyName, value);
    }
    
    public static Expression iendsWith(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().iendsWith(propertyName, value);
    }
    
    public static Expression contains(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().contains(propertyName, value);
    }
    
    public static Expression icontains(final String propertyName, final String value) {
        return Ebean.getExpressionFactory().icontains(propertyName, value);
    }
    
    public static Expression in(final String propertyName, final Object[] values) {
        return Ebean.getExpressionFactory().in(propertyName, values);
    }
    
    public static Expression in(final String propertyName, final Query<?> subQuery) {
        return Ebean.getExpressionFactory().in(propertyName, subQuery);
    }
    
    public static Expression in(final String propertyName, final Collection<?> values) {
        return Ebean.getExpressionFactory().in(propertyName, values);
    }
    
    public static Expression idEq(final Object value) {
        return Ebean.getExpressionFactory().idEq(value);
    }
    
    public static Expression allEq(final Map<String, Object> propertyMap) {
        return Ebean.getExpressionFactory().allEq(propertyMap);
    }
    
    public static Expression raw(final String raw, final Object value) {
        return Ebean.getExpressionFactory().raw(raw, value);
    }
    
    public static Expression raw(final String raw, final Object[] values) {
        return Ebean.getExpressionFactory().raw(raw, values);
    }
    
    public static Expression raw(final String raw) {
        return Ebean.getExpressionFactory().raw(raw);
    }
    
    public static Expression and(final Expression expOne, final Expression expTwo) {
        return Ebean.getExpressionFactory().and(expOne, expTwo);
    }
    
    public static Expression or(final Expression expOne, final Expression expTwo) {
        return Ebean.getExpressionFactory().or(expOne, expTwo);
    }
    
    public static Expression not(final Expression exp) {
        return Ebean.getExpressionFactory().not(exp);
    }
    
    public static <T> Junction<T> conjunction(final Query<T> query) {
        return Ebean.getExpressionFactory().conjunction(query);
    }
    
    public static <T> Junction<T> disjunction(final Query<T> query) {
        return Ebean.getExpressionFactory().disjunction(query);
    }
}
