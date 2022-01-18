// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.logging.Logger;

final class PropertyExpression
{
    private static final Logger logger;
    private static final String JAVA_COMP_ENV = "java:comp/env/";
    private static String START;
    private static String END;
    
    static String eval(final String val, final PropertyMap map) {
        if (val == null) {
            return null;
        }
        final int sp = val.indexOf(PropertyExpression.START);
        if (sp > -1) {
            final int ep = val.indexOf(PropertyExpression.END, sp + 1);
            if (ep > -1) {
                return eval(val, sp, ep, map);
            }
        }
        return val;
    }
    
    private static String evaluateExpression(final String exp, final PropertyMap map) {
        if (isJndiExpression(exp)) {
            final String val = getJndiProperty(exp);
            if (val != null) {
                return val;
            }
        }
        String val = System.getenv(exp);
        if (val == null) {
            val = System.getProperty(exp);
        }
        if (val == null && map != null) {
            val = map.get(exp);
        }
        if (val != null) {
            return val;
        }
        PropertyExpression.logger.fine("Unable to evaluate expression [" + exp + "]");
        return null;
    }
    
    private static String eval(final String val, final int sp, final int ep, final PropertyMap map) {
        final StringBuilder sb = new StringBuilder();
        sb.append(val.substring(0, sp));
        final String cal = evalExpression(val, sp, ep, map);
        sb.append(cal);
        eval(val, ep + 1, sb, map);
        return sb.toString();
    }
    
    private static void eval(final String val, final int startPos, final StringBuilder sb, final PropertyMap map) {
        if (startPos < val.length()) {
            final int sp = val.indexOf(PropertyExpression.START, startPos);
            if (sp > -1) {
                sb.append(val.substring(startPos, sp));
                final int ep = val.indexOf(PropertyExpression.END, sp + 1);
                if (ep > -1) {
                    final String cal = evalExpression(val, sp, ep, map);
                    sb.append(cal);
                    eval(val, ep + 1, sb, map);
                    return;
                }
            }
        }
        sb.append(val.substring(startPos));
    }
    
    private static String evalExpression(final String val, final int sp, final int ep, final PropertyMap map) {
        final String exp = val.substring(sp + PropertyExpression.START.length(), ep);
        final String evaled = evaluateExpression(exp, map);
        if (evaled != null) {
            return evaled;
        }
        return PropertyExpression.START + exp + PropertyExpression.END;
    }
    
    private static boolean isJndiExpression(final String exp) {
        return exp.startsWith("JNDI:") || exp.startsWith("jndi:");
    }
    
    private static String getJndiProperty(String key) {
        try {
            key = key.substring(5);
            return (String)getJndiObject(key);
        }
        catch (NamingException ex) {
            return null;
        }
    }
    
    private static Object getJndiObject(final String key) throws NamingException {
        final InitialContext ctx = new InitialContext();
        return ctx.lookup("java:comp/env/" + key);
    }
    
    static {
        logger = Logger.getLogger(PropertyExpression.class.getName());
        PropertyExpression.START = "${";
        PropertyExpression.END = "}";
    }
}
