// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Skeleton extends Monster
{
    SkeletonType getSkeletonType();
    
    void setSkeletonType(final SkeletonType p0);
    
    public enum SkeletonType
    {
        NORMAL(0), 
        WITHER(1);
        
        private static final SkeletonType[] types;
        private final int id;
        
        private SkeletonType(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static SkeletonType getType(final int id) {
            return (id >= SkeletonType.types.length) ? null : SkeletonType.types[id];
        }
        
        static {
            types = new SkeletonType[values().length];
            for (final SkeletonType type : values()) {
                SkeletonType.types[type.getId()] = type;
            }
        }
    }
}
