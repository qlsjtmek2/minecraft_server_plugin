// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

final class StepSoundStone extends StepSound
{
    StepSoundStone(final String s, final float n, final float n2) {
        super(s, n, n2);
    }
    
    public String getBreakSound() {
        return "random.glass";
    }
    
    public String getPlaceSound() {
        return "step.stone";
    }
}
