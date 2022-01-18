// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

@SerializableAs("Color")
public final class Color implements ConfigurationSerializable
{
    private static final int BIT_MASK = 255;
    public static final Color WHITE;
    public static final Color SILVER;
    public static final Color GRAY;
    public static final Color BLACK;
    public static final Color RED;
    public static final Color MAROON;
    public static final Color YELLOW;
    public static final Color OLIVE;
    public static final Color LIME;
    public static final Color GREEN;
    public static final Color AQUA;
    public static final Color TEAL;
    public static final Color BLUE;
    public static final Color NAVY;
    public static final Color FUCHSIA;
    public static final Color PURPLE;
    public static final Color ORANGE;
    private final byte red;
    private final byte green;
    private final byte blue;
    
    public static Color fromRGB(final int red, final int green, final int blue) throws IllegalArgumentException {
        return new Color(red, green, blue);
    }
    
    public static Color fromBGR(final int blue, final int green, final int red) throws IllegalArgumentException {
        return new Color(red, green, blue);
    }
    
    public static Color fromRGB(final int rgb) throws IllegalArgumentException {
        Validate.isTrue(rgb >> 24 == 0, "Extrenuous data in: ", rgb);
        return fromRGB(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb >> 0 & 0xFF);
    }
    
    public static Color fromBGR(final int bgr) throws IllegalArgumentException {
        Validate.isTrue(bgr >> 24 == 0, "Extrenuous data in: ", bgr);
        return fromBGR(bgr >> 16 & 0xFF, bgr >> 8 & 0xFF, bgr >> 0 & 0xFF);
    }
    
    private Color(final int red, final int green, final int blue) {
        Validate.isTrue(red >= 0 && red <= 255, "Red is not between 0-255: ", red);
        Validate.isTrue(green >= 0 && green <= 255, "Green is not between 0-255: ", green);
        Validate.isTrue(blue >= 0 && blue <= 255, "Blue is not between 0-255: ", blue);
        this.red = (byte)red;
        this.green = (byte)green;
        this.blue = (byte)blue;
    }
    
    public int getRed() {
        return 0xFF & this.red;
    }
    
    public Color setRed(final int red) {
        return fromRGB(red, this.getGreen(), this.getBlue());
    }
    
    public int getGreen() {
        return 0xFF & this.green;
    }
    
    public Color setGreen(final int green) {
        return fromRGB(this.getRed(), green, this.getBlue());
    }
    
    public int getBlue() {
        return 0xFF & this.blue;
    }
    
    public Color setBlue(final int blue) {
        return fromRGB(this.getRed(), this.getGreen(), blue);
    }
    
    public int asRGB() {
        return this.getRed() << 16 | this.getGreen() << 8 | this.getBlue() << 0;
    }
    
    public int asBGR() {
        return this.getBlue() << 16 | this.getGreen() << 8 | this.getRed() << 0;
    }
    
    public Color mixDyes(final DyeColor... colors) {
        Validate.noNullElements(colors, "Colors cannot be null");
        final Color[] toPass = new Color[colors.length];
        for (int i = 0; i < colors.length; ++i) {
            toPass[i] = colors[i].getColor();
        }
        return this.mixColors(toPass);
    }
    
    public Color mixColors(final Color... colors) {
        Validate.noNullElements(colors, "Colors cannot be null");
        int totalRed = this.getRed();
        int totalGreen = this.getGreen();
        int totalBlue = this.getBlue();
        int totalMax = Math.max(Math.max(totalRed, totalGreen), totalBlue);
        for (final Color color : colors) {
            totalRed += color.getRed();
            totalGreen += color.getGreen();
            totalBlue += color.getBlue();
            totalMax += Math.max(Math.max(color.getRed(), color.getGreen()), color.getBlue());
        }
        final float averageRed = totalRed / (colors.length + 1);
        final float averageGreen = totalGreen / (colors.length + 1);
        final float averageBlue = totalBlue / (colors.length + 1);
        final float averageMax = totalMax / (colors.length + 1);
        final float maximumOfAverages = Math.max(Math.max(averageRed, averageGreen), averageBlue);
        final float gainFactor = averageMax / maximumOfAverages;
        return fromRGB((int)(averageRed * gainFactor), (int)(averageGreen * gainFactor), (int)(averageBlue * gainFactor));
    }
    
    public boolean equals(final Object o) {
        if (!(o instanceof Color)) {
            return false;
        }
        final Color that = (Color)o;
        return this.blue == that.blue && this.green == that.green && this.red == that.red;
    }
    
    public int hashCode() {
        return this.asRGB() ^ Color.class.hashCode();
    }
    
    public Map<String, Object> serialize() {
        return (Map<String, Object>)ImmutableMap.of("RED", this.getRed(), "BLUE", this.getBlue(), "GREEN", this.getGreen());
    }
    
    public static Color deserialize(final Map<String, Object> map) {
        return fromRGB(asInt("RED", map), asInt("GREEN", map), asInt("BLUE", map));
    }
    
    private static int asInt(final String string, final Map<String, Object> map) {
        final Object value = map.get(string);
        if (value == null) {
            throw new IllegalArgumentException(string + " not in map " + map);
        }
        if (!(value instanceof Number)) {
            throw new IllegalArgumentException(string + '(' + value + ") is not a number");
        }
        return ((Number)value).intValue();
    }
    
    public String toString() {
        return "Color:[rgb0x" + Integer.toHexString(this.getRed()).toUpperCase() + Integer.toHexString(this.getGreen()).toUpperCase() + Integer.toHexString(this.getBlue()).toUpperCase() + "]";
    }
    
    static {
        WHITE = fromRGB(16777215);
        SILVER = fromRGB(12632256);
        GRAY = fromRGB(8421504);
        BLACK = fromRGB(0);
        RED = fromRGB(16711680);
        MAROON = fromRGB(8388608);
        YELLOW = fromRGB(16776960);
        OLIVE = fromRGB(8421376);
        LIME = fromRGB(65280);
        GREEN = fromRGB(32768);
        AQUA = fromRGB(65535);
        TEAL = fromRGB(32896);
        BLUE = fromRGB(255);
        NAVY = fromRGB(128);
        FUCHSIA = fromRGB(16711935);
        PURPLE = fromRGB(8388736);
        ORANGE = fromRGB(16753920);
    }
}
