// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.ldap.expression;

public class LdEscape
{
    public static String forLike(final String source) {
        final StringBuilder sb = new StringBuilder(source.length() + 5);
        for (int len = source.length(), i = 0; i < len; ++i) {
            final char ch = source.charAt(i);
            switch (ch) {
                case '(': {
                    sb.append("\\\\28");
                    break;
                }
                case ')': {
                    sb.append("\\\\29");
                    break;
                }
                case '\\': {
                    sb.append("\\\\5c");
                    break;
                }
                case '/': {
                    sb.append("\\\\2f");
                    break;
                }
                case '\0': {
                    sb.append("\\\\0");
                    break;
                }
                default: {
                    sb.append(ch);
                    break;
                }
            }
        }
        return sb.toString();
    }
}
