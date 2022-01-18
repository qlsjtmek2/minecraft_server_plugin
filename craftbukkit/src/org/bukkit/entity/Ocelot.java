// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Ocelot extends Animals, Tameable
{
    Type getCatType();
    
    void setCatType(final Type p0);
    
    boolean isSitting();
    
    void setSitting(final boolean p0);
    
    public enum Type
    {
        WILD_OCELOT(0), 
        BLACK_CAT(1), 
        RED_CAT(2), 
        SIAMESE_CAT(3);
        
        private static final Type[] types;
        private final int id;
        
        private Type(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static Type getType(final int id) {
            return (id >= Type.types.length) ? null : Type.types[id];
        }
        
        static {
            types = new Type[values().length];
            for (final Type type : values()) {
                Type.types[type.getId()] = type;
            }
        }
    }
}
