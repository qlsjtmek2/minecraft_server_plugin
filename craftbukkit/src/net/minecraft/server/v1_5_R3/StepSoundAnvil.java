// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class StepSoundAnvil extends StepSound
{
    StepSoundAnvil(final String s, final float n, final float n2) {
        super(s, n, n2);
    }
    
    public String getBreakSound() {
        return "dig.stone";
    }
    
    public String getPlaceSound() {
        return "random.anvil_land";
    }
}
