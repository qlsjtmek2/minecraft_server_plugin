// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public interface ICommand extends Comparable
{
    String c();
    
    String a(final ICommandListener p0);
    
    List b();
    
    void b(final ICommandListener p0, final String[] p1);
    
    boolean b(final ICommandListener p0);
    
    List a(final ICommandListener p0, final String[] p1);
    
    boolean a(final String[] p0, final int p1);
}
