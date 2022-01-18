// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.security.Provider;
import java.security.Security;
import net.minecraft.v1_5_R3.org.bouncycastle.jce.provider.BouncyCastleProvider;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.io.CipherInputStream;
import java.io.InputStream;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.io.CipherOutputStream;
import java.io.OutputStream;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.CipherParameters;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.params.ParametersWithIV;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.params.KeyParameter;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.BlockCipher;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.modes.CFBBlockCipher;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.engines.AESFastEngine;
import net.minecraft.v1_5_R3.org.bouncycastle.crypto.BufferedBlockCipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.nio.charset.Charset;

public class MinecraftEncryption
{
    public static final Charset a;
    
    public static KeyPair b() {
        try {
            final KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
            instance.initialize(1024);
            return instance.generateKeyPair();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            System.err.println("Key pair generation failed!");
            return null;
        }
    }
    
    public static byte[] a(final String s, final PublicKey publicKey, final SecretKey secretKey) {
        try {
            return a("SHA-1", new byte[][] { s.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    private static byte[] a(final String s, final byte[]... array) {
        try {
            final MessageDigest instance = MessageDigest.getInstance(s);
            for (int length = array.length, i = 0; i < length; ++i) {
                instance.update(array[i]);
            }
            return instance.digest();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey a(final byte[] array) {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(array));
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        catch (InvalidKeySpecException ex2) {
            ex2.printStackTrace();
        }
        System.err.println("Public key reconstitute failed!");
        return null;
    }
    
    public static SecretKey a(final PrivateKey privateKey, final byte[] array) {
        return new SecretKeySpec(b(privateKey, array), "AES");
    }
    
    public static byte[] b(final Key key, final byte[] array) {
        return a(2, key, array);
    }
    
    private static byte[] a(final int n, final Key key, final byte[] array) {
        try {
            return a(n, key.getAlgorithm(), key).doFinal(array);
        }
        catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
        catch (BadPaddingException ex2) {
            ex2.printStackTrace();
        }
        System.err.println("Cipher data failed!");
        return null;
    }
    
    private static Cipher a(final int n, final String s, final Key key) {
        try {
            final Cipher instance = Cipher.getInstance(s);
            instance.init(n, key);
            return instance;
        }
        catch (InvalidKeyException ex) {
            ex.printStackTrace();
        }
        catch (NoSuchAlgorithmException ex2) {
            ex2.printStackTrace();
        }
        catch (NoSuchPaddingException ex3) {
            ex3.printStackTrace();
        }
        System.err.println("Cipher creation failed!");
        return null;
    }
    
    private static BufferedBlockCipher a(final boolean b, final Key key) {
        final BufferedBlockCipher bufferedBlockCipher = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
        bufferedBlockCipher.a(b, new ParametersWithIV(new KeyParameter(key.getEncoded()), key.getEncoded(), 0, 16));
        return bufferedBlockCipher;
    }
    
    public static OutputStream a(final SecretKey secretKey, final OutputStream outputStream) {
        return new CipherOutputStream(outputStream, a(true, secretKey));
    }
    
    public static InputStream a(final SecretKey secretKey, final InputStream inputStream) {
        return new CipherInputStream(inputStream, a(false, secretKey));
    }
    
    static {
        a = Charset.forName("ISO_8859_1");
        Security.addProvider(new BouncyCastleProvider());
    }
}
