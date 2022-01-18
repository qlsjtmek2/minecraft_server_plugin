// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.map;

public final class MapCursor
{
    private byte x;
    private byte y;
    private byte direction;
    private byte type;
    private boolean visible;
    
    public MapCursor(final byte x, final byte y, final byte direction, final byte type, final boolean visible) {
        this.x = x;
        this.y = y;
        this.setDirection(direction);
        this.setRawType(type);
        this.visible = visible;
    }
    
    public byte getX() {
        return this.x;
    }
    
    public byte getY() {
        return this.y;
    }
    
    public byte getDirection() {
        return this.direction;
    }
    
    public Type getType() {
        return Type.byValue(this.type);
    }
    
    public byte getRawType() {
        return this.type;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setX(final byte x) {
        this.x = x;
    }
    
    public void setY(final byte y) {
        this.y = y;
    }
    
    public void setDirection(final byte direction) {
        if (direction < 0 || direction > 15) {
            throw new IllegalArgumentException("Direction must be in the range 0-15");
        }
        this.direction = direction;
    }
    
    public void setType(final Type type) {
        this.setRawType(type.value);
    }
    
    public void setRawType(final byte type) {
        if (type < 0 || type > 15) {
            throw new IllegalArgumentException("Type must be in the range 0-15");
        }
        this.type = type;
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public enum Type
    {
        WHITE_POINTER(0), 
        GREEN_POINTER(1), 
        RED_POINTER(2), 
        BLUE_POINTER(3), 
        WHITE_CROSS(4);
        
        private byte value;
        
        private Type(final int value) {
            this.value = (byte)value;
        }
        
        public byte getValue() {
            return this.value;
        }
        
        public static Type byValue(final byte value) {
            for (final Type t : values()) {
                if (t.value == value) {
                    return t;
                }
            }
            return null;
        }
    }
}
