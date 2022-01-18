// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import java.util.Collection;
import java.util.List;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import java.util.Iterator;
import com.avaje.ebean.Junction;
import com.avaje.ebean.Expression;
import java.util.Map;
import com.avaje.ebean.ExpressionFactory;

public class LdapExpressionFactory implements ExpressionFactory
{
    public String getLang() {
        return "ldap";
    }
    
    public ExpressionFactory createExpressionFactory(final String path) {
        return new LdapExpressionFactory();
    }
    
    public Expression allEq(final Map<String, Object> propertyMap) {
        final Junction conjunction = new LdJunctionExpression.Conjunction(this);
        for (final Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            conjunction.add(this.eq(entry.getKey(), entry.getValue()));
        }
        return conjunction;
    }
    
    public Expression and(final Expression expOne, final Expression expTwo) {
        return new LdLogicExpression.And(expOne, expTwo);
    }
    
    public Expression between(final String propertyName, final Object value1, final Object value2) {
        final Expression e1 = this.gt(propertyName, value1);
        final Expression e2 = this.lt(propertyName, value2);
        return this.and(e1, e2);
    }
    
    public Expression betweenProperties(final String lowProperty, final String highProperty, final Object value) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression contains(final String propertyName, String value) {
        if (!value.endsWith("*")) {
            value = "*" + value + "*";
        }
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
    }
    
    public <T> Junction<T> conjunction(final Query<T> query) {
        return new LdJunctionExpression.Conjunction<T>(query, query.where());
    }
    
    public <T> Junction<T> disjunction(final Query<T> query) {
        return new LdJunctionExpression.Disjunction<T>(query, query.where());
    }
    
    public <T> Junction<T> conjunction(final Query<T> query, final ExpressionList<T> parent) {
        return new LdJunctionExpression.Conjunction<T>(query, parent);
    }
    
    public <T> Junction<T> disjunction(final Query<T> query, final ExpressionList<T> parent) {
        return new LdJunctionExpression.Disjunction<T>(query, parent);
    }
    
    public Expression endsWith(final String propertyName, String value) {
        if (!value.startsWith("*")) {
            value = "*" + value;
        }
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression eq(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
    }
    
    public Expression lucene(final String propertyName, final String value) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression lucene(final String value) {
        throw new RuntimeException("Not Implemented");
    }
    
    public ExampleExpression exampleLike(final Object example, final boolean caseInsensitive, final LikeType likeType) {
        throw new RuntimeException("Not Implemented");
    }
    
    public ExampleExpression exampleLike(final Object example) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression ge(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT_EQ, value);
    }
    
    public Expression gt(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.GT, value);
    }
    
    public Expression icontains(final String propertyName, String value) {
        if (!value.endsWith("*")) {
            value = "*" + value + "*";
        }
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression idEq(final Object value) {
        return null;
    }
    
    public Expression idIn(final List<?> idList) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression iendsWith(final String propertyName, String value) {
        if (!value.startsWith("*")) {
            value = "*" + value;
        }
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression ieq(final String propertyName, final String value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.EQ, value);
    }
    
    public ExampleExpression iexampleLike(final Object example) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression ilike(final String propertyName, final String value) {
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression in(final String propertyName, final Collection<?> values) {
        if (values == null || values.isEmpty()) {
            throw new LdapPersistenceException("collection can't be empty for Ldap");
        }
        final Junction disjunction = new LdJunctionExpression.Disjunction(this);
        for (final Object v : values) {
            disjunction.add(this.eq(propertyName, v));
        }
        return disjunction;
    }
    
    public Expression in(final String propertyName, final Object[] values) {
        if (values == null || values.length == 0) {
            throw new LdapPersistenceException("values can't be empty for Ldap");
        }
        final Junction disjunction = new LdJunctionExpression.Disjunction(this);
        for (final Object v : values) {
            disjunction.add(this.eq(propertyName, v));
        }
        return disjunction;
    }
    
    public Expression in(final String propertyName, final Query<?> subQuery) {
        throw new RuntimeException("Not Implemented");
    }
    
    public Expression isNotNull(final String propertyName) {
        return new LdPresentExpression(propertyName);
    }
    
    public Expression isNull(final String propertyName) {
        final LdPresentExpression exp = new LdPresentExpression(propertyName);
        return new LdNotExpression(exp);
    }
    
    public Expression istartsWith(final String propertyName, String value) {
        if (!value.endsWith("*")) {
            value += "*";
        }
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression le(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT_EQ, value);
    }
    
    public Expression like(final String propertyName, final String value) {
        return new LdLikeExpression(propertyName, value);
    }
    
    public Expression lt(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.LT, value);
    }
    
    public Expression ne(final String propertyName, final Object value) {
        return new LdSimpleExpression(propertyName, LdSimpleExpression.Op.NOT_EQ, value);
    }
    
    public Expression not(final Expression exp) {
        return new LdNotExpression(exp);
    }
    
    public Expression or(final Expression expOne, final Expression expTwo) {
        return new LdLogicExpression.Or(expOne, expTwo);
    }
    
    public Expression raw(final String raw, final Object value) {
        if (value != null) {
            return new LdRawExpression(raw, new Object[] { value });
        }
        return new LdRawExpression(raw, null);
    }
    
    public Expression raw(final String raw, final Object[] values) {
        return new LdRawExpression(raw, values);
    }
    
    public Expression raw(final String raw) {
        return new LdRawExpression(raw, null);
    }
    
    public Expression startsWith(final String propertyName, String value) {
        if (!value.endsWith("*")) {
            value += "*";
        }
        return new LdLikeExpression(propertyName, value);
    }
}
