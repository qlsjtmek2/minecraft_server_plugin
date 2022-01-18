// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.jce.provider;

import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;
import java.security.Permission;
import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;

class BouncyCastleProviderConfiguration implements ProviderConfiguration
{
    private static Permission a;
    private static Permission b;
    private static Permission c;
    private static Permission d;
    private ThreadLocal e;
    private ThreadLocal f;
    
    BouncyCastleProviderConfiguration() {
        this.e = new ThreadLocal();
        this.f = new ThreadLocal();
    }
    
    static {
        BouncyCastleProviderConfiguration.a = new ProviderConfigurationPermission(BouncyCastleProvider.a, "threadLocalEcImplicitlyCa");
        BouncyCastleProviderConfiguration.b = new ProviderConfigurationPermission(BouncyCastleProvider.a, "ecImplicitlyCa");
        BouncyCastleProviderConfiguration.c = new ProviderConfigurationPermission(BouncyCastleProvider.a, "threadLocalDhDefaultParams");
        BouncyCastleProviderConfiguration.d = new ProviderConfigurationPermission(BouncyCastleProvider.a, "DhDefaultParams");
    }
}
