// 
// Decompiled by Procyon v0.5.30
// 

package javax.persistence.spi;

import java.util.Properties;
import java.net.URL;
import java.util.List;
import javax.sql.DataSource;

public interface PersistenceUnitInfo
{
    String getPersistenceUnitName();
    
    String getPersistenceProviderClassName();
    
    PersistenceUnitTransactionType getTransactionType();
    
    DataSource getJtaDataSource();
    
    DataSource getNonJtaDataSource();
    
    List<String> getMappingFileNames();
    
    List<URL> getJarFileUrls();
    
    URL getPersistenceUnitRootUrl();
    
    List<String> getManagedClassNames();
    
    boolean excludeUnlistedClasses();
    
    Properties getProperties();
    
    ClassLoader getClassLoader();
    
    void addTransformer(final ClassTransformer p0);
    
    ClassLoader getNewTempClassLoader();
}
