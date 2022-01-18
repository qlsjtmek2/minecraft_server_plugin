// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.enhance.agent;

import java.util.HashMap;

public class ArgParser
{
    public static HashMap<String, String> parse(final String args) {
        final HashMap<String, String> map = new HashMap<String, String>();
        if (args != null) {
            final String[] arr$;
            final String[] split = arr$ = args.split(";");
            for (final String nameValuePair : arr$) {
                final String[] nameValue = nameValuePair.split("=");
                if (nameValue.length == 2) {
                    map.put(nameValue[0].toLowerCase(), nameValue[1]);
                }
            }
        }
        return map;
    }
}
