// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence.spi;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public interface ClassTransformer
{
    byte[] transform(final ClassLoader p0, final String p1, final Class<?> p2, final ProtectionDomain p3, final byte[] p4) throws IllegalClassFormatException;
}
