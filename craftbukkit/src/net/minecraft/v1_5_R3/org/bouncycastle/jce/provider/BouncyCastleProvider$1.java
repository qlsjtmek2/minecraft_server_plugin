// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.jce.provider;

import java.util.HashMap;
import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import net.minecraft.v1_5_R3.org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import net.minecraft.v1_5_R3.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.security.AccessController;
import java.util.Map;
import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import net.minecraft.server.v1_5_R3.IEmptyInterface;
import java.security.Provider;
import java.security.PrivilegedAction;

class BouncyCastleProvider$1 implements PrivilegedAction
{
    final /* synthetic */ BouncyCastleProvider a;
    
    BouncyCastleProvider$1(final BouncyCastleProvider a) {
        this.a = a;
    }
    
    public Object run() {
        this.a.a();
        return null;
    }
}
