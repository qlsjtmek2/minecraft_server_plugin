// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.api.ClassUtil;

public class DetectLucene
{
    public static boolean isPresent() {
        return ClassUtil.isPresent("org.apache.lucene.analysis.Analyzer");
    }
}
