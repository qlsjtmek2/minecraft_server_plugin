// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence;

import java.util.regex.Matcher;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Collection;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import javax.persistence.spi.PersistenceProvider;
import java.util.Set;

public class Persistence
{
    public static String PERSISTENCE_PROVIDER;
    protected static final Set<PersistenceProvider> providers;
    private static final Pattern nonCommentPattern;
    
    public static EntityManagerFactory createEntityManagerFactory(final String persistenceUnitName) {
        return createEntityManagerFactory(persistenceUnitName, null);
    }
    
    public static EntityManagerFactory createEntityManagerFactory(final String persistenceUnitName, final Map properties) {
        EntityManagerFactory emf = null;
        if (Persistence.providers.size() == 0) {
            try {
                findAllProviders();
            }
            catch (IOException ex) {}
        }
        for (final PersistenceProvider provider : Persistence.providers) {
            emf = provider.createEntityManagerFactory(persistenceUnitName, properties);
            if (emf != null) {
                break;
            }
        }
        if (emf == null) {
            throw new PersistenceException("No Persistence provider for EntityManager named " + persistenceUnitName);
        }
        return emf;
    }
    
    private static void findAllProviders() throws IOException {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        final Enumeration<URL> resources = loader.getResources("META-INF/services/" + PersistenceProvider.class.getName());
        final Set<String> names = new HashSet<String>();
        while (resources.hasMoreElements()) {
            final URL url = resources.nextElement();
            final InputStream is = url.openStream();
            try {
                names.addAll(providerNamesFromReader(new BufferedReader(new InputStreamReader(is))));
            }
            finally {
                is.close();
            }
        }
        for (final String s : names) {
            try {
                Persistence.providers.add((PersistenceProvider)loader.loadClass(s).newInstance());
            }
            catch (ClassNotFoundException exc) {}
            catch (InstantiationException exc2) {}
            catch (IllegalAccessException ex) {}
        }
    }
    
    private static Set<String> providerNamesFromReader(final BufferedReader reader) throws IOException {
        final Set<String> names = new HashSet<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            final Matcher m = Persistence.nonCommentPattern.matcher(line);
            if (m.find()) {
                names.add(m.group().trim());
            }
        }
        return names;
    }
    
    static {
        Persistence.PERSISTENCE_PROVIDER = "javax.persistence.spi.PeristenceProvider";
        providers = new HashSet<PersistenceProvider>();
        nonCommentPattern = Pattern.compile("^([^#]+)");
    }
}
