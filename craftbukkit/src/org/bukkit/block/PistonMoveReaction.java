// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import java.util.HashMap;
import java.util.Map;

public enum PistonMoveReaction
{
    MOVE(0), 
    BREAK(1), 
    BLOCK(2);
    
    private int id;
    private static Map<Integer, PistonMoveReaction> byId;
    
    private PistonMoveReaction(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static PistonMoveReaction getById(final int id) {
        return PistonMoveReaction.byId.get(id);
    }
    
    static {
        PistonMoveReaction.byId = new HashMap<Integer, PistonMoveReaction>();
        for (final PistonMoveReaction reaction : values()) {
            PistonMoveReaction.byId.put(reaction.id, reaction);
        }
    }
}
