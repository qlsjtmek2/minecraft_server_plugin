// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import java.util.Iterator;
import org.apache.commons.lang.Validate;
import java.io.Serializable;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.List;
import com.google.common.collect.ImmutableList;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@SerializableAs("Firework")
public final class FireworkEffect implements ConfigurationSerializable
{
    private static final String FLICKER = "flicker";
    private static final String TRAIL = "trail";
    private static final String COLORS = "colors";
    private static final String FADE_COLORS = "fade-colors";
    private static final String TYPE = "type";
    private final boolean flicker;
    private final boolean trail;
    private final ImmutableList<Color> colors;
    private final ImmutableList<Color> fadeColors;
    private final Type type;
    private String string;
    
    public static Builder builder() {
        return new Builder();
    }
    
    FireworkEffect(final boolean flicker, final boolean trail, final ImmutableList<Color> colors, final ImmutableList<Color> fadeColors, final Type type) {
        this.string = null;
        if (colors.isEmpty()) {
            throw new IllegalStateException("Cannot make FireworkEffect without any color");
        }
        this.flicker = flicker;
        this.trail = trail;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.type = type;
    }
    
    public boolean hasFlicker() {
        return this.flicker;
    }
    
    public boolean hasTrail() {
        return this.trail;
    }
    
    public List<Color> getColors() {
        return this.colors;
    }
    
    public List<Color> getFadeColors() {
        return this.fadeColors;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public static ConfigurationSerializable deserialize(final Map<String, Object> map) {
        final Type type = Type.valueOf(map.get("type"));
        if (type == null) {
            throw new IllegalArgumentException(map.get("type") + " is not a valid Type");
        }
        return builder().flicker(map.get("flicker")).trail(map.get("trail")).withColor(map.get("colors")).withFade(map.get("fade-colors")).with(type).build();
    }
    
    public Map<String, Object> serialize() {
        return (Map<String, Object>)ImmutableMap.of("flicker", this.flicker, "trail", this.trail, "colors", this.colors, "fade-colors", this.fadeColors, "type", this.type.name());
    }
    
    public String toString() {
        final String string = this.string;
        if (string == null) {
            return this.string = "FireworkEffect:" + this.serialize();
        }
        return string;
    }
    
    public int hashCode() {
        final int PRIME = 31;
        final int TRUE = 1231;
        final int FALSE = 1237;
        int hash = 1;
        hash = hash * 31 + (this.flicker ? 1231 : 1237);
        hash = hash * 31 + (this.trail ? 1231 : 1237);
        hash = hash * 31 + this.type.hashCode();
        hash = hash * 31 + this.colors.hashCode();
        hash = hash * 31 + this.fadeColors.hashCode();
        return hash;
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FireworkEffect)) {
            return false;
        }
        final FireworkEffect that = (FireworkEffect)obj;
        return this.flicker == that.flicker && this.trail == that.trail && this.type == that.type && this.colors.equals(that.colors) && this.fadeColors.equals(that.fadeColors);
    }
    
    public enum Type
    {
        BALL, 
        BALL_LARGE, 
        STAR, 
        BURST, 
        CREEPER;
    }
    
    public static final class Builder
    {
        boolean flicker;
        boolean trail;
        final ImmutableList.Builder<Color> colors;
        ImmutableList.Builder<Color> fadeColors;
        Type type;
        
        Builder() {
            this.flicker = false;
            this.trail = false;
            this.colors = ImmutableList.builder();
            this.fadeColors = null;
            this.type = Type.BALL;
        }
        
        public Builder with(final Type type) throws IllegalArgumentException {
            Validate.notNull(type, "Cannot have null type");
            this.type = type;
            return this;
        }
        
        public Builder withFlicker() {
            this.flicker = true;
            return this;
        }
        
        public Builder flicker(final boolean flicker) {
            this.flicker = flicker;
            return this;
        }
        
        public Builder withTrail() {
            this.trail = true;
            return this;
        }
        
        public Builder trail(final boolean trail) {
            this.trail = trail;
            return this;
        }
        
        public Builder withColor(final Color color) throws IllegalArgumentException {
            Validate.notNull(color, "Cannot have null color");
            this.colors.add(color);
            return this;
        }
        
        public Builder withColor(final Color... colors) throws IllegalArgumentException {
            Validate.notNull(colors, "Cannot have null colors");
            if (colors.length == 0) {
                return this;
            }
            final ImmutableList.Builder<Color> list = this.colors;
            for (final Color color : colors) {
                Validate.notNull(color, "Color cannot be null");
                list.add(color);
            }
            return this;
        }
        
        public Builder withColor(final Iterable<?> colors) throws IllegalArgumentException {
            Validate.notNull(colors, "Cannot have null colors");
            final ImmutableList.Builder<Color> list = this.colors;
            for (final Object color : colors) {
                if (!(color instanceof Color)) {
                    throw new IllegalArgumentException(color + " is not a Color in " + colors);
                }
                list.add((Color)color);
            }
            return this;
        }
        
        public Builder withFade(final Color color) throws IllegalArgumentException {
            Validate.notNull(color, "Cannot have null color");
            if (this.fadeColors == null) {
                this.fadeColors = ImmutableList.builder();
            }
            this.fadeColors.add(color);
            return this;
        }
        
        public Builder withFade(final Color... colors) throws IllegalArgumentException {
            Validate.notNull(colors, "Cannot have null colors");
            if (colors.length == 0) {
                return this;
            }
            ImmutableList.Builder<Color> list = this.fadeColors;
            if (list == null) {
                final ImmutableList.Builder<Color> builder = ImmutableList.builder();
                this.fadeColors = builder;
                list = builder;
            }
            for (final Color color : colors) {
                Validate.notNull(color, "Color cannot be null");
                list.add(color);
            }
            return this;
        }
        
        public Builder withFade(final Iterable<?> colors) throws IllegalArgumentException {
            Validate.notNull(colors, "Cannot have null colors");
            ImmutableList.Builder<Color> list = this.fadeColors;
            if (list == null) {
                final ImmutableList.Builder<Color> builder = ImmutableList.builder();
                this.fadeColors = builder;
                list = builder;
            }
            for (final Object color : colors) {
                if (!(color instanceof Color)) {
                    throw new IllegalArgumentException(color + " is not a Color in " + colors);
                }
                list.add((Color)color);
            }
            return this;
        }
        
        public FireworkEffect build() {
            return new FireworkEffect(this.flicker, this.trail, this.colors.build(), (this.fadeColors == null) ? ImmutableList.of() : this.fadeColors.build(), this.type);
        }
    }
}
