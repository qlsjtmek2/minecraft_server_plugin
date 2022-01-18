// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3;

import org.bukkit.block.BlockFace;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.potion.Potion;
import org.bukkit.Effect;

public class CraftEffect
{
    public static <T> int getDataValue(final Effect effect, final T data) {
        switch (effect) {
            case POTION_BREAK: {
                final int datavalue = ((Potion)data).toDamageValue() & 0x3F;
                return datavalue;
            }
            case RECORD_PLAY: {
                Validate.isTrue(((Material)data).isRecord(), "Invalid record type!");
                final int datavalue = ((Material)data).getId();
                return datavalue;
            }
            case SMOKE: {
                switch ((BlockFace)data) {
                    case SOUTH_EAST: {
                        final int datavalue = 0;
                        return datavalue;
                    }
                    case SOUTH: {
                        final int datavalue = 1;
                        return datavalue;
                    }
                    case SOUTH_WEST: {
                        final int datavalue = 2;
                        return datavalue;
                    }
                    case EAST: {
                        final int datavalue = 3;
                        return datavalue;
                    }
                    case UP:
                    case SELF: {
                        final int datavalue = 4;
                        return datavalue;
                    }
                    case WEST: {
                        final int datavalue = 5;
                        return datavalue;
                    }
                    case NORTH_EAST: {
                        final int datavalue = 6;
                        return datavalue;
                    }
                    case NORTH: {
                        final int datavalue = 7;
                        return datavalue;
                    }
                    case NORTH_WEST: {
                        final int datavalue = 8;
                        return datavalue;
                    }
                    default: {
                        throw new IllegalArgumentException("Bad smoke direction!");
                    }
                }
                break;
            }
            case STEP_SOUND: {
                Validate.isTrue(((Material)data).isBlock(), "Material is not a block!");
                final int datavalue = ((Material)data).getId();
                return datavalue;
            }
            case ITEM_BREAK: {
                final int datavalue = ((Material)data).getId();
                break;
            }
        }
        final int datavalue = 0;
        return datavalue;
    }
}
