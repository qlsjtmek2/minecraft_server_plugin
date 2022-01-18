// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.server.deploy.parse.DetectScala;
import scala.Option;

public class DmlUtil
{
    private static final boolean hasScalaSupport;
    
    public static boolean isNullOrZero(final Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof Number) {
            return ((Number)value).longValue() == 0L;
        }
        return DmlUtil.hasScalaSupport && value instanceof Option && ((Option)value).isEmpty();
    }
    
    static {
        hasScalaSupport = DetectScala.hasScalaSupport();
    }
}
