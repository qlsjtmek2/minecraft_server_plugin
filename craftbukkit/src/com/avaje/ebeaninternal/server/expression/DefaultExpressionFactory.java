// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Junction;
import java.util.Map;
import java.util.List;
import java.util.Collection;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.Query;
import com.avaje.ebean.LikeType;
import com.avaje.ebean.ExampleExpression;
import com.avaje.ebean.Expression;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.api.SpiExpressionFactory;

public class DefaultExpressionFactory implements SpiExpressionFactory
{
    private static final Object[] EMPTY_ARRAY;
    private final FilterExprPath prefix;
    
    public DefaultExpressionFactory() {
        this(null);
    }
    
    public DefaultExpressionFactory(final FilterExprPath prefix) {
        this.prefix = prefix;
    }
    
    public ExpressionFactory createExpressionFactory(final FilterExprPath prefix) {
        return new DefaultExpressionFactory(prefix);
    }
    
    public String getLang() {
        return "sql";
    }
    
    public Expression eq(final String propertyName, final Object value) {
        if (value == null) {
            return this.isNull(propertyName);
        }
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.EQ, value);
    }
    
    public Expression ne(final String propertyName, final Object value) {
        if (value == null) {
            return this.isNotNull(propertyName);
        }
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.NOT_EQ, value);
    }
    
    public Expression ieq(final String propertyName, final String value) {
        if (value == null) {
            return this.isNull(propertyName);
        }
        return new CaseInsensitiveEqualExpression(this.prefix, propertyName, value);
    }
    
    public Expression between(final String propertyName, final Object value1, final Object value2) {
        return new BetweenExpression(this.prefix, propertyName, value1, value2);
    }
    
    public Expression betweenProperties(final String lowProperty, final String highProperty, final Object value) {
        return new BetweenPropertyExpression(this.prefix, lowProperty, highProperty, value);
    }
    
    public Expression gt(final String propertyName, final Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT, value);
    }
    
    public Expression ge(final String propertyName, final Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.GT_EQ, value);
    }
    
    public Expression lt(final String propertyName, final Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT, value);
    }
    
    public Expression le(final String propertyName, final Object value) {
        return new SimpleExpression(this.prefix, propertyName, SimpleExpression.Op.LT_EQ, value);
    }
    
    public Expression isNull(final String propertyName) {
        return new NullExpression(this.prefix, propertyName, false);
    }
    
    public Expression isNotNull(final String propertyName) {
        return new NullExpression(this.prefix, propertyName, true);
    }
    
    public ExampleExpression iexampleLike(final Object example) {
        return new DefaultExampleExpression(this.prefix, example, true, LikeType.RAW);
    }
    
    public ExampleExpression exampleLike(final Object example) {
        return new DefaultExampleExpression(this.prefix, example, false, LikeType.RAW);
    }
    
    public ExampleExpression exampleLike(final Object example, final boolean caseInsensitive, final LikeType likeType) {
        return new DefaultExampleExpression(this.prefix, example, caseInsensitive, likeType);
    }
    
    public Expression like(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.RAW);
    }
    
    public Expression ilike(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.RAW);
    }
    
    public Expression startsWith(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.STARTS_WITH);
    }
    
    public Expression istartsWith(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.STARTS_WITH);
    }
    
    public Expression endsWith(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.ENDS_WITH);
    }
    
    public Expression iendsWith(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.ENDS_WITH);
    }
    
    public Expression contains(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, false, LikeType.CONTAINS);
    }
    
    public Expression lucene(final String propertyName, final String value) {
        return new LuceneExpression(this.prefix, propertyName, value, true);
    }
    
    public Expression lucene(final String value) {
        return new LuceneExpression(this.prefix, null, value, true);
    }
    
    public Expression icontains(final String propertyName, final String value) {
        return new LikeExpression(this.prefix, propertyName, value, true, LikeType.CONTAINS);
    }
    
    public Expression in(final String propertyName, final Object[] values) {
        return new InExpression(this.prefix, propertyName, values);
    }
    
    public Expression in(final String propertyName, final Query<?> subQuery) {
        return new InQueryExpression(this.prefix, propertyName, (SpiQuery)subQuery);
    }
    
    public Expression in(final String propertyName, final Collection<?> values) {
        return new InExpression(this.prefix, propertyName, values);
    }
    
    public Expression idEq(final Object value) {
        return new IdExpression(value);
    }
    
    public Expression idIn(final List<?> idList) {
        return new IdInExpression(idList);
    }
    
    public Expression allEq(final Map<String, Object> propertyMap) {
        return new AllEqualsExpression(this.prefix, propertyMap);
    }
    
    public Expression raw(final String raw, final Object value) {
        return new RawExpression(raw, new Object[] { value });
    }
    
    public Expression raw(final String raw, final Object[] values) {
        return new RawExpression(raw, values);
    }
    
    public Expression raw(final String raw) {
        return new RawExpression(raw, DefaultExpressionFactory.EMPTY_ARRAY);
    }
    
    public Expression and(final Expression expOne, final Expression expTwo) {
        return new LogicExpression.And(expOne, expTwo);
    }
    
    public Expression or(final Expression expOne, final Expression expTwo) {
        return new LogicExpression.Or(expOne, expTwo);
    }
    
    public Expression not(final Expression exp) {
        return new NotExpression(exp);
    }
    
    public <T> Junction<T> conjunction(final Query<T> query) {
        return new JunctionExpression.Conjunction<T>(query, query.where());
    }
    
    public <T> Junction<T> disjunction(final Query<T> query) {
        return new JunctionExpression.Disjunction<T>(query, query.where());
    }
    
    public <T> Junction<T> conjunction(final Query<T> query, final ExpressionList<T> parent) {
        return new JunctionExpression.Conjunction<T>(query, parent);
    }
    
    public <T> Junction<T> disjunction(final Query<T> query, final ExpressionList<T> parent) {
        return new JunctionExpression.Disjunction<T>(query, parent);
    }
    
    static {
        EMPTY_ARRAY = new Object[0];
    }
}
