// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

import java.util.HashMap;

public class MapFont
{
    private final HashMap<Character, CharacterSprite> chars;
    private int height;
    protected boolean malleable;
    
    public MapFont() {
        this.chars = new HashMap<Character, CharacterSprite>();
        this.height = 0;
        this.malleable = true;
    }
    
    public void setChar(final char ch, final CharacterSprite sprite) {
        if (!this.malleable) {
            throw new IllegalStateException("this font is not malleable");
        }
        this.chars.put(ch, sprite);
        if (sprite.getHeight() > this.height) {
            this.height = sprite.getHeight();
        }
    }
    
    public CharacterSprite getChar(final char ch) {
        return this.chars.get(ch);
    }
    
    public int getWidth(final String text) {
        if (!this.isValid(text)) {
            throw new IllegalArgumentException("text contains invalid characters");
        }
        int result = 0;
        for (int i = 0; i < text.length(); ++i) {
            result += this.chars.get(text.charAt(i)).getWidth();
        }
        return result;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean isValid(final String text) {
        for (int i = 0; i < text.length(); ++i) {
            final char ch = text.charAt(i);
            if (ch != '§') {
                if (ch != '\n') {
                    if (this.chars.get(ch) == null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static class CharacterSprite
    {
        private final int width;
        private final int height;
        private final boolean[] data;
        
        public CharacterSprite(final int width, final int height, final boolean[] data) {
            this.width = width;
            this.height = height;
            this.data = data;
            if (data.length != width * height) {
                throw new IllegalArgumentException("size of data does not match dimensions");
            }
        }
        
        public boolean get(final int row, final int col) {
            return row >= 0 && col >= 0 && row < this.height && col < this.width && this.data[row * this.width + col];
        }
        
        public int getWidth() {
            return this.width;
        }
        
        public int getHeight() {
            return this.height;
        }
    }
}
