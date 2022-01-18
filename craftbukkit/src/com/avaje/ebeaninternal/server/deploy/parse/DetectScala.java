// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.logging.Logger;

public class DetectScala
{
    private static final Logger logger;
    private static Class<?> scalaOptionClass;
    private static boolean hasScalaSupport;
    
    private static Class<?> initScalaOptionClass() {
        try {
            return ClassUtil.forName("scala.Option");
        }
        catch (ClassNotFoundException e) {
            DetectScala.logger.fine("Scala type 'scala.Option' not found. Scala Support disabled.");
            return null;
        }
    }
    
    public static boolean hasScalaSupport() {
        return DetectScala.hasScalaSupport;
    }
    
    public static Class<?> getScalaOptionClass() {
        return DetectScala.scalaOptionClass;
    }
    
    static {
        logger = Logger.getLogger(DetectScala.class.getName());
        DetectScala.scalaOptionClass = initScalaOptionClass();
        DetectScala.hasScalaSupport = (DetectScala.scalaOptionClass != null);
    }
}
