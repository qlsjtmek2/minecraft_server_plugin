// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;

@Target({ ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Warning {
    boolean value() default false;
    
    String reason() default "";
    
    public enum WarningState
    {
        ON, 
        OFF, 
        DEFAULT;
        
        private static final Map<String, WarningState> values;
        
        public boolean printFor(final Warning warning) {
            if (this == WarningState.DEFAULT) {
                return warning == null || warning.value();
            }
            return this == WarningState.ON;
        }
        
        public static WarningState value(final String value) {
            if (value == null) {
                return WarningState.DEFAULT;
            }
            final WarningState state = WarningState.values.get(value.toLowerCase());
            if (state == null) {
                return WarningState.DEFAULT;
            }
            return state;
        }
        
        static {
            values = ImmutableMap.builder().put("off", WarningState.OFF).put("false", WarningState.OFF).put("f", WarningState.OFF).put("no", WarningState.OFF).put("n", WarningState.OFF).put("on", WarningState.ON).put("true", WarningState.ON).put("t", WarningState.ON).put("yes", WarningState.ON).put("y", WarningState.ON).put("", WarningState.DEFAULT).put("d", WarningState.DEFAULT).put("default", WarningState.DEFAULT).build();
        }
    }
}
