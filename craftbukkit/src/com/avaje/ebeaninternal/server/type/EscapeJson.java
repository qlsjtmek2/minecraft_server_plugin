// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

public class EscapeJson
{
    public static String escapeQuote(final String value) {
        final StringBuilder sb = new StringBuilder(value.length() + 2);
        sb.append("\"");
        escape(value, sb);
        sb.append("\"");
        return sb.toString();
    }
    
    public static String escape(final String s) {
        if (s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        escape(s, sb);
        return sb.toString();
    }
    
    public static void escape(final String s, final StringBuilder sb) {
        for (int i = 0; i < s.length(); ++i) {
            final char ch = s.charAt(i);
            switch (ch) {
                case '\"': {
                    sb.append("\\\"");
                    break;
                }
                case '\\': {
                    sb.append("\\\\");
                    break;
                }
                case '\b': {
                    sb.append("\\b");
                    break;
                }
                case '\f': {
                    sb.append("\\f");
                    break;
                }
                case '\n': {
                    sb.append("\\n");
                    break;
                }
                case '\r': {
                    sb.append("\\r");
                    break;
                }
                case '\t': {
                    sb.append("\\t");
                    break;
                }
                case '/': {
                    sb.append("\\/");
                    break;
                }
                default: {
                    if ((ch >= '\0' && ch <= '\u001f') || (ch >= '\u007f' && ch <= '\u009f') || (ch >= '\u2000' && ch <= '\u20ff')) {
                        final String hs = Integer.toHexString(ch);
                        sb.append("\\u");
                        for (int j = 0; j < 4 - hs.length(); ++j) {
                            sb.append('0');
                        }
                        sb.append(hs.toUpperCase());
                        break;
                    }
                    sb.append(ch);
                    break;
                }
            }
        }
    }
}
