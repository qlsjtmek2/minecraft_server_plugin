// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;

public final class MapPalette
{
    private static final Color[] colors;
    public static final byte TRANSPARENT = 0;
    public static final byte LIGHT_GREEN = 4;
    public static final byte LIGHT_BROWN = 8;
    public static final byte GRAY_1 = 12;
    public static final byte RED = 16;
    public static final byte PALE_BLUE = 20;
    public static final byte GRAY_2 = 24;
    public static final byte DARK_GREEN = 28;
    public static final byte WHITE = 32;
    public static final byte LIGHT_GRAY = 36;
    public static final byte BROWN = 40;
    public static final byte DARK_GRAY = 44;
    public static final byte BLUE = 48;
    public static final byte DARK_BROWN = 52;
    
    private static Color c(final int r, final int g, final int b) {
        return new Color(r, g, b);
    }
    
    private static double getDistance(final Color c1, final Color c2) {
        final double rmean = (c1.getRed() + c2.getRed()) / 2.0;
        final double r = c1.getRed() - c2.getRed();
        final double g = c1.getGreen() - c2.getGreen();
        final int b = c1.getBlue() - c2.getBlue();
        final double weightR = 2.0 + rmean / 256.0;
        final double weightG = 4.0;
        final double weightB = 2.0 + (255.0 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }
    
    public static BufferedImage resizeImage(final Image image) {
        final BufferedImage result = new BufferedImage(128, 128, 2);
        final Graphics2D graphics = result.createGraphics();
        graphics.drawImage(image, 0, 0, 128, 128, null);
        graphics.dispose();
        return result;
    }
    
    public static byte[] imageToBytes(final Image image) {
        final BufferedImage temp = new BufferedImage(image.getWidth(null), image.getHeight(null), 2);
        final Graphics2D graphics = temp.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        final int[] pixels = new int[temp.getWidth() * temp.getHeight()];
        temp.getRGB(0, 0, temp.getWidth(), temp.getHeight(), pixels, 0, temp.getWidth());
        final byte[] result = new byte[temp.getWidth() * temp.getHeight()];
        for (int i = 0; i < pixels.length; ++i) {
            result[i] = matchColor(new Color(pixels[i], true));
        }
        return result;
    }
    
    public static byte matchColor(final int r, final int g, final int b) {
        return matchColor(new Color(r, g, b));
    }
    
    public static byte matchColor(final Color color) {
        if (color.getAlpha() < 128) {
            return 0;
        }
        int index = 0;
        double best = -1.0;
        for (int i = 4; i < MapPalette.colors.length; ++i) {
            final double distance = getDistance(color, MapPalette.colors[i]);
            if (distance < best || best == -1.0) {
                best = distance;
                index = i;
            }
        }
        return (byte)index;
    }
    
    public static Color getColor(final byte index) {
        if (index < 0 || index >= MapPalette.colors.length) {
            throw new IndexOutOfBoundsException();
        }
        return MapPalette.colors[index];
    }
    
    static {
        colors = new Color[] { new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), c(89, 125, 39), c(109, 153, 48), c(27, 178, 56), c(109, 153, 48), c(174, 164, 115), c(213, 201, 140), c(247, 233, 163), c(213, 201, 140), c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(144, 144, 144), c(180, 0, 0), c(220, 0, 0), c(255, 0, 0), c(220, 0, 0), c(112, 112, 180), c(138, 138, 220), c(160, 160, 255), c(138, 138, 220), c(117, 117, 117), c(144, 144, 144), c(167, 167, 167), c(144, 144, 144), c(0, 87, 0), c(0, 106, 0), c(0, 124, 0), c(0, 106, 0), c(180, 180, 180), c(220, 220, 220), c(255, 255, 255), c(220, 220, 220), c(115, 118, 129), c(141, 144, 158), c(164, 168, 184), c(141, 144, 158), c(129, 74, 33), c(157, 91, 40), c(183, 106, 47), c(157, 91, 40), c(79, 79, 79), c(96, 96, 96), c(112, 112, 112), c(96, 96, 96), c(45, 45, 180), c(55, 55, 220), c(64, 64, 255), c(55, 55, 220), c(73, 58, 35), c(89, 71, 43), c(104, 83, 50), c(89, 71, 43) };
    }
}
