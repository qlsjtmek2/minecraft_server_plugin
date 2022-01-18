// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove;

public class Version
{
    public static void main(final String[] args) {
        System.out.println(getVersion());
    }
    
    public static String getVersion() {
        final String version = Version.class.getPackage().getImplementationVersion();
        if (version != null) {
            return "trove4j version " + version;
        }
        return "Sorry no Implementation-Version manifest attribute available";
    }
}
