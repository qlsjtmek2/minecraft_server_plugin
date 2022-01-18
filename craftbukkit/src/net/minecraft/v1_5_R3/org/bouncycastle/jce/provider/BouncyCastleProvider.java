// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.v1_5_R3.org.bouncycastle.jce.provider;

import java.util.HashMap;
import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import net.minecraft.v1_5_R3.org.bouncycastle.asn1.bc.BCObjectIdentifiers;
import net.minecraft.v1_5_R3.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import java.security.PrivilegedAction;
import java.security.AccessController;
import java.util.Map;
import net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import net.minecraft.server.v1_5_R3.IEmptyInterface;
import java.security.Provider;

public final class BouncyCastleProvider extends Provider implements IEmptyInterface
{
    private static String c;
    public static String a;
    public static final ProviderConfiguration b;
    private static final Map d;
    private static final String[] e;
    private static final String[] f;
    private static final String[] g;
    private static final String[] h;
    
    public BouncyCastleProvider() {
        super(BouncyCastleProvider.a, 1.47, BouncyCastleProvider.c);
        AccessController.doPrivileged((PrivilegedAction<Object>)new BouncyCastleProvider$1(this));
    }
    
    private void a() {
        this.a("net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.digest.", BouncyCastleProvider.h);
        this.a("net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.symmetric.", BouncyCastleProvider.e);
        this.a("net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.asymmetric.", BouncyCastleProvider.f);
        this.a("net.minecraft.v1_5_R3.org.bouncycastle.jcajce.provider.asymmetric.", BouncyCastleProvider.g);
        this.put("X509Store.CERTIFICATE/COLLECTION", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreCertCollection");
        this.put("X509Store.ATTRIBUTECERTIFICATE/COLLECTION", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreAttrCertCollection");
        this.put("X509Store.CRL/COLLECTION", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreCRLCollection");
        this.put("X509Store.CERTIFICATEPAIR/COLLECTION", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreCertPairCollection");
        this.put("X509Store.CERTIFICATE/LDAP", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreLDAPCerts");
        this.put("X509Store.CRL/LDAP", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreLDAPCRLs");
        this.put("X509Store.ATTRIBUTECERTIFICATE/LDAP", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreLDAPAttrCerts");
        this.put("X509Store.CERTIFICATEPAIR/LDAP", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509StoreLDAPCertPairs");
        this.put("X509StreamParser.CERTIFICATE", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509CertParser");
        this.put("X509StreamParser.ATTRIBUTECERTIFICATE", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509AttrCertParser");
        this.put("X509StreamParser.CRL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509CRLParser");
        this.put("X509StreamParser.CERTIFICATEPAIR", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509CertPairParser");
        this.put("KeyStore.BKS", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKKeyStore");
        this.put("KeyStore.BouncyCastle", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKKeyStore$BouncyCastleStore");
        this.put("KeyStore.PKCS12", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$BCPKCS12KeyStore");
        this.put("KeyStore.BCPKCS12", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$BCPKCS12KeyStore");
        this.put("KeyStore.PKCS12-DEF", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$DefPKCS12KeyStore");
        this.put("KeyStore.PKCS12-3DES-40RC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$BCPKCS12KeyStore");
        this.put("KeyStore.PKCS12-3DES-3DES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$BCPKCS12KeyStore3DES");
        this.put("KeyStore.PKCS12-DEF-3DES-40RC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$DefPKCS12KeyStore");
        this.put("KeyStore.PKCS12-DEF-3DES-3DES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKPKCS12KeyStore$DefPKCS12KeyStore3DES");
        this.put("Alg.Alias.KeyStore.UBER", "BouncyCastle");
        this.put("Alg.Alias.KeyStore.BOUNCYCASTLE", "BouncyCastle");
        this.put("Alg.Alias.KeyStore.bouncycastle", "BouncyCastle");
        this.put("AlgorithmParameters.IES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKAlgorithmParameters$IES");
        this.put("AlgorithmParameters.PKCS12PBE", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKAlgorithmParameters$PKCS12PBE");
        this.put("AlgorithmParameters." + PKCSObjectIdentifiers.z, "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKAlgorithmParameters$PBKDF2");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC2", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDRC4", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1ANDRC2-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES3KEY-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDDES2KEY-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC2-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND40BITRC4", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC2-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITRC4", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAANDTWOFISH-CBC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.1", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.2", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.3", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.4", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.5", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.1.2.840.113549.1.12.1.6", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWithSHAAnd3KeyTripleDES", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.l.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.m.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.n.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.o.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.p.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters." + BCObjectIdentifiers.q.b(), "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND128BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND192BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHAAND256BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND128BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND192BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA256AND256BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND128BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND192BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA1AND256BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND128BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND192BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-1AND256BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND128BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND192BITAES-CBC-BC", "PKCS12PBE");
        this.put("Alg.Alias.AlgorithmParameters.PBEWITHSHA-256AND256BITAES-CBC-BC", "PKCS12PBE");
        this.put("AlgorithmParameters.SHA1WITHECDSA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKECDSAAlgParameters$SigAlgParameters");
        this.put("AlgorithmParameters.SHA224WITHECDSA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKECDSAAlgParameters$SigAlgParameters");
        this.put("AlgorithmParameters.SHA256WITHECDSA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKECDSAAlgParameters$SigAlgParameters");
        this.put("AlgorithmParameters.SHA384WITHECDSA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKECDSAAlgParameters$SigAlgParameters");
        this.put("AlgorithmParameters.SHA512WITHECDSA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JDKECDSAAlgParameters$SigAlgParameters");
        this.put("Alg.Alias.Cipher.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        this.put("Cipher.ECIES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEIESCipher$ECIES");
        this.put("Cipher.BrokenECIES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEIESCipher$BrokenECIES");
        this.put("Cipher.IES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEIESCipher$IES");
        this.put("Cipher.BrokenIES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEIESCipher$BrokenIES");
        this.put("Cipher.PBEWITHMD5ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithMD5AndDES");
        this.put("Cipher.BROKENPBEWITHMD5ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithMD5AndDES");
        this.put("Cipher.PBEWITHMD5ANDRC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithMD5AndRC2");
        this.put("Cipher.PBEWITHSHA1ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithSHA1AndDES");
        this.put("Cipher.BROKENPBEWITHSHA1ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.BrokenJCEBlockCipher$BrokePBEWithSHA1AndDES");
        this.put("Cipher.PBEWITHSHA1ANDRC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithSHA1AndRC2");
        this.put("Cipher.PBEWITHSHAAND128BITRC2-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithSHAAnd128BitRC2");
        this.put("Cipher.PBEWITHSHAAND40BITRC2-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithSHAAnd40BitRC2");
        this.put("Cipher.PBEWITHSHAAND128BITRC4", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEStreamCipher$PBEWithSHAAnd128BitRC4");
        this.put("Cipher.PBEWITHSHAAND40BITRC4", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEStreamCipher$PBEWithSHAAnd40BitRC4");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC2-CBC", "PBEWITHSHAAND128BITRC2-CBC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC2-CBC", "PBEWITHSHAAND40BITRC2-CBC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITRC4", "PBEWITHSHAAND128BITRC4");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND40BITRC4", "PBEWITHSHAAND40BITRC4");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.l.b(), "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.m.b(), "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.n.b(), "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.o.b(), "PBEWITHSHA256AND128BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.p.b(), "PBEWITHSHA256AND192BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher." + BCObjectIdentifiers.q.b(), "PBEWITHSHA256AND256BITAES-CBC-BC");
        this.put("Cipher.PBEWITHSHAAND128BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHAAND192BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHAAND256BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHA256AND128BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHA256AND192BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHA256AND256BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
        this.put("Alg.Alias.Cipher.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
        this.put("Cipher.PBEWITHMD5AND128BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHMD5AND192BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHMD5AND256BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithAESCBC");
        this.put("Cipher.PBEWITHSHAANDTWOFISH-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEBlockCipher$PBEWithSHAAndTwofish");
        this.put("Cipher.OLDPBEWITHSHAANDTWOFISH-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.BrokenJCEBlockCipher$OldPBEWithSHAAndTwofish");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.s, "PBEWITHMD2ANDDES");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.t, "PBEWITHMD2ANDRC2");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.u, "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.v, "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.w, "PBEWITHSHA1ANDDES");
        this.put("Alg.Alias.Cipher." + PKCSObjectIdentifiers.x, "PBEWITHSHA1ANDRC2");
        this.put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.1", "PBEWITHSHAAND128BITRC4");
        this.put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.2", "PBEWITHSHAAND40BITRC4");
        this.put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.5", "PBEWITHSHAAND128BITRC2-CBC");
        this.put("Alg.Alias.Cipher.1.2.840.113549.1.12.1.6", "PBEWITHSHAAND40BITRC2-CBC");
        this.put("SecretKeyFactory.PBEWITHMD2ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD2AndDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.s, "PBEWITHMD2ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.t, "PBEWITHMD2ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.u, "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.v, "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.w, "PBEWITHSHA1ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.x, "PBEWITHSHA1ANDRC2");
        this.put("SecretKeyFactory.PBEWITHMD2ANDRC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD2AndRC2");
        this.put("SecretKeyFactory.PBEWITHMD5ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD5AndDES");
        this.put("SecretKeyFactory.PBEWITHMD5ANDRC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD5AndRC2");
        this.put("SecretKeyFactory.PBEWITHSHA1ANDDES", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA1AndDES");
        this.put("SecretKeyFactory.PBEWITHSHA1ANDRC2", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA1AndRC2");
        this.put("SecretKeyFactory.PBEWITHSHAAND3-KEYTRIPLEDES-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAndDES3Key");
        this.put("SecretKeyFactory.PBEWITHSHAAND2-KEYTRIPLEDES-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAndDES2Key");
        this.put("SecretKeyFactory.PBEWITHSHAAND128BITRC4", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd128BitRC4");
        this.put("SecretKeyFactory.PBEWITHSHAAND40BITRC4", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd40BitRC4");
        this.put("SecretKeyFactory.PBEWITHSHAAND128BITRC2-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd128BitRC2");
        this.put("SecretKeyFactory.PBEWITHSHAAND40BITRC2-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd40BitRC2");
        this.put("SecretKeyFactory.PBEWITHSHAANDTWOFISH-CBC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAndTwofish");
        this.put("SecretKeyFactory.PBEWITHHMACRIPEMD160", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithRIPEMD160");
        this.put("SecretKeyFactory.PBEWITHHMACSHA1", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA");
        this.put("SecretKeyFactory.PBEWITHHMACTIGER", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithTiger");
        this.put("SecretKeyFactory.PBEWITHMD5AND128BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD5And128BitAESCBCOpenSSL");
        this.put("SecretKeyFactory.PBEWITHMD5AND192BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD5And192BitAESCBCOpenSSL");
        this.put("SecretKeyFactory.PBEWITHMD5AND256BITAES-CBC-OPENSSL", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithMD5And256BitAESCBCOpenSSL");
        this.put("Alg.Alias.SecretKeyFactory.PBE", "PBE/PKCS5");
        this.put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHMD5ANDDES", "PBE/PKCS5");
        this.put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHA1ANDDES", "PBE/PKCS5");
        this.put("Alg.Alias.SecretKeyFactory.OLDPBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        this.put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHAAND3-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        this.put("Alg.Alias.SecretKeyFactory.BROKENPBEWITHSHAAND2-KEYTRIPLEDES-CBC", "PBE/PKCS12");
        this.put("Alg.Alias.SecretKeyFactory.OLDPBEWITHSHAANDTWOFISH-CBC", "PBE/PKCS12");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHMD2ANDDES-CBC", "PBEWITHMD2ANDDES");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHMD2ANDRC2-CBC", "PBEWITHMD2ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDDES-CBC", "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHMD5ANDRC2-CBC", "PBEWITHMD5ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDDES-CBC", "PBEWITHSHA1ANDDES");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1ANDRC2-CBC", "PBEWITHSHA1ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.s, "PBEWITHMD2ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.t, "PBEWITHMD2ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.u, "PBEWITHMD5ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.v, "PBEWITHMD5ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.w, "PBEWITHSHA1ANDDES");
        this.put("Alg.Alias.SecretKeyFactory." + PKCSObjectIdentifiers.x, "PBEWITHSHA1ANDRC2");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.1", "PBEWITHSHAAND128BITRC4");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.2", "PBEWITHSHAAND40BITRC4");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.3", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.4", "PBEWITHSHAAND2-KEYTRIPLEDES-CBC");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.5", "PBEWITHSHAAND128BITRC2-CBC");
        this.put("Alg.Alias.SecretKeyFactory.1.2.840.113549.1.12.1.6", "PBEWITHSHAAND40BITRC2-CBC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHHMACSHA", "PBEWITHHMACSHA1");
        this.put("Alg.Alias.SecretKeyFactory.1.3.14.3.2.26", "PBEWITHHMACSHA1");
        this.put("Alg.Alias.SecretKeyFactory.PBEWithSHAAnd3KeyTripleDES", "PBEWITHSHAAND3-KEYTRIPLEDES-CBC");
        this.put("SecretKeyFactory.PBEWITHSHAAND128BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd128BitAESBC");
        this.put("SecretKeyFactory.PBEWITHSHAAND192BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd192BitAESBC");
        this.put("SecretKeyFactory.PBEWITHSHAAND256BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHAAnd256BitAESBC");
        this.put("SecretKeyFactory.PBEWITHSHA256AND128BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA256And128BitAESBC");
        this.put("SecretKeyFactory.PBEWITHSHA256AND192BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA256And192BitAESBC");
        this.put("SecretKeyFactory.PBEWITHSHA256AND256BITAES-CBC-BC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCESecretKeyFactory$PBEWithSHA256And256BitAESBC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND128BITAES-CBC-BC", "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND192BITAES-CBC-BC", "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-1AND256BITAES-CBC-BC", "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND128BITAES-CBC-BC", "PBEWITHSHA256AND128BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND192BITAES-CBC-BC", "PBEWITHSHA256AND192BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory.PBEWITHSHA-256AND256BITAES-CBC-BC", "PBEWITHSHA256AND256BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.l.b(), "PBEWITHSHAAND128BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.m.b(), "PBEWITHSHAAND192BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.n.b(), "PBEWITHSHAAND256BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.o.b(), "PBEWITHSHA256AND128BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.p.b(), "PBEWITHSHA256AND192BITAES-CBC-BC");
        this.put("Alg.Alias.SecretKeyFactory." + BCObjectIdentifiers.q.b(), "PBEWITHSHA256AND256BITAES-CBC-BC");
        this.b();
        this.put("CertPathValidator.RFC3281", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXAttrCertPathValidatorSpi");
        this.put("CertPathBuilder.RFC3281", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXAttrCertPathBuilderSpi");
        this.put("CertPathValidator.RFC3280", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        this.put("CertPathBuilder.RFC3280", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        this.put("CertPathValidator.PKIX", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXCertPathValidatorSpi");
        this.put("CertPathBuilder.PKIX", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.PKIXCertPathBuilderSpi");
        this.put("CertStore.Collection", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.CertStoreCollectionSpi");
        this.put("CertStore.LDAP", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.X509LDAPCertStoreSpi");
        this.put("CertStore.Multi", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.MultiCertStoreSpi");
        this.put("Alg.Alias.CertStore.X509LDAP", "LDAP");
    }
    
    private void a(final String s, final String[] array) {
        for (int i = 0; i != array.length; ++i) {
            Class<?> clazz = null;
            try {
                final ClassLoader classLoader = this.getClass().getClassLoader();
                if (classLoader != null) {
                    clazz = classLoader.loadClass(s + array[i] + "$Mappings");
                }
                else {
                    clazz = Class.forName(s + array[i] + "$Mappings");
                }
            }
            catch (ClassNotFoundException ex2) {}
            if (clazz != null) {
                try {
                    ((AlgorithmProvider)clazz.newInstance()).a(this);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    throw new InternalError("cannot create instance of " + s + array[i] + "$Mappings : " + ex);
                }
            }
        }
    }
    
    private void b() {
        this.put("Mac.DESWITHISO9797", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$DES9797Alg3");
        this.put("Alg.Alias.Mac.DESISO9797MAC", "DESWITHISO9797");
        this.put("Mac.ISO9797ALG3MAC", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$DES9797Alg3");
        this.put("Alg.Alias.Mac.ISO9797ALG3", "ISO9797ALG3MAC");
        this.put("Mac.ISO9797ALG3WITHISO7816-4PADDING", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$DES9797Alg3with7816d4");
        this.put("Alg.Alias.Mac.ISO9797ALG3MACWITHISO7816-4PADDING", "ISO9797ALG3WITHISO7816-4PADDING");
        this.put("Mac.OLDHMACSHA384", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$OldSHA384");
        this.put("Mac.OLDHMACSHA512", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$OldSHA512");
        this.put("Mac.PBEWITHHMACSHA", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$PBEWithSHA");
        this.put("Mac.PBEWITHHMACSHA1", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$PBEWithSHA");
        this.put("Mac.PBEWITHHMACRIPEMD160", "net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.JCEMac$PBEWithRIPEMD160");
        this.put("Alg.Alias.Mac.1.3.14.3.2.26", "PBEWITHHMACSHA");
    }
    
    static {
        BouncyCastleProvider.c = "BouncyCastle Security Provider v1.47";
        BouncyCastleProvider.a = "BC";
        b = new BouncyCastleProviderConfiguration();
        d = new HashMap();
        e = new String[] { "AES", "ARC4", "Blowfish", "Camellia", "CAST5", "CAST6", "DES", "DESede", "GOST28147", "Grainv1", "Grain128", "HC128", "HC256", "IDEA", "Noekeon", "RC2", "RC5", "RC6", "Rijndael", "Salsa20", "SEED", "Serpent", "Skipjack", "TEA", "Twofish", "VMPC", "VMPCKSA3", "XTEA" };
        f = new String[] { "X509" };
        g = new String[] { "DSA", "DH", "EC", "RSA", "GOST", "ECGOST", "ElGamal" };
        h = new String[] { "GOST3411", "MD2", "MD4", "MD5", "SHA1", "RIPEMD128", "RIPEMD160", "RIPEMD256", "RIPEMD320", "SHA224", "SHA256", "SHA384", "SHA512", "Tiger", "Whirlpool" };
    }
}
