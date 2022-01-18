// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

public interface Villager extends Ageable, NPC
{
    Profession getProfession();
    
    void setProfession(final Profession p0);
    
    public enum Profession
    {
        FARMER(0), 
        LIBRARIAN(1), 
        PRIEST(2), 
        BLACKSMITH(3), 
        BUTCHER(4);
        
        private static final Profession[] professions;
        private final int id;
        
        private Profession(final int id) {
            this.id = id;
        }
        
        public int getId() {
            return this.id;
        }
        
        public static Profession getProfession(final int id) {
            return (id >= Profession.professions.length) ? null : Profession.professions[id];
        }
        
        static {
            professions = new Profession[values().length];
            for (final Profession type : values()) {
                Profession.professions[type.getId()] = type;
            }
        }
    }
}
