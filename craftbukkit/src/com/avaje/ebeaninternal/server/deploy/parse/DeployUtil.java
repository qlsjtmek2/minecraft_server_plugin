// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.parse;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyCompound;
import com.avaje.ebeaninternal.server.type.ScalarTypeEnumStandard;
import javax.persistence.EnumType;
import com.avaje.ebeaninternal.server.type.ScalarType;
import javax.persistence.Enumerated;
import com.avaje.ebean.validation.factory.Validator;
import java.util.logging.Level;
import java.lang.annotation.Annotation;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.type.DataEncryptSupport;
import com.avaje.ebean.config.EncryptDeploy;
import com.avaje.ebean.config.TableName;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.type.SimpleAesEncryptor;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.Encryptor;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.config.EncryptDeployManager;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebean.config.NamingConvention;
import java.util.logging.Logger;

public class DeployUtil
{
    private static final Logger logger;
    private static final int dbCLOBType = 2005;
    private static final int dbBLOBType = 2004;
    private final NamingConvention namingConvention;
    private final TypeManager typeManager;
    private final ValidatorFactoryManager validatorFactoryManager;
    private final String manyToManyAlias;
    private final DatabasePlatform dbPlatform;
    private final EncryptDeployManager encryptDeployManager;
    private final EncryptKeyManager encryptKeyManager;
    private final Encryptor bytesEncryptor;
    
    public DeployUtil(final TypeManager typeMgr, final ServerConfig serverConfig) {
        this.typeManager = typeMgr;
        this.namingConvention = serverConfig.getNamingConvention();
        this.dbPlatform = serverConfig.getDatabasePlatform();
        this.encryptDeployManager = serverConfig.getEncryptDeployManager();
        this.encryptKeyManager = serverConfig.getEncryptKeyManager();
        final Encryptor be = serverConfig.getEncryptor();
        this.bytesEncryptor = ((be != null) ? be : new SimpleAesEncryptor());
        this.manyToManyAlias = "zzzzzz";
        this.validatorFactoryManager = new ValidatorFactoryManager();
    }
    
    public TypeManager getTypeManager() {
        return this.typeManager;
    }
    
    public DatabasePlatform getDbPlatform() {
        return this.dbPlatform;
    }
    
    public NamingConvention getNamingConvention() {
        return this.namingConvention;
    }
    
    public void checkEncryptKeyManagerDefined(final String fullPropName) {
        if (this.encryptKeyManager == null) {
            final String msg = "Using encryption on " + fullPropName + " but no EncryptKeyManager defined!";
            throw new PersistenceException(msg);
        }
    }
    
    public EncryptDeploy getEncryptDeploy(final TableName table, final String column) {
        if (this.encryptDeployManager == null) {
            return EncryptDeploy.ANNOTATION;
        }
        return this.encryptDeployManager.getEncryptDeploy(table, column);
    }
    
    public DataEncryptSupport createDataEncryptSupport(final String table, final String column) {
        return new DataEncryptSupport(this.encryptKeyManager, this.bytesEncryptor, table, column);
    }
    
    public String getManyToManyAlias() {
        return this.manyToManyAlias;
    }
    
    public void createValidator(final DeployBeanProperty prop, final Annotation ann) {
        try {
            final Validator validator = this.validatorFactoryManager.create(ann, prop.getPropertyType());
            if (validator != null) {
                prop.addValidator(validator);
            }
        }
        catch (Exception e) {
            final String msg = "Error creating a validator on " + prop.getFullBeanName();
            DeployUtil.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    public ScalarType<?> setEnumScalarType(final Enumerated enumerated, final DeployBeanProperty prop) {
        final Class<?> enumType = prop.getPropertyType();
        if (!enumType.isEnum()) {
            throw new IllegalArgumentException("Class [" + enumType + "] is Not a Enum?");
        }
        ScalarType<?> scalarType = this.typeManager.getScalarType(enumType);
        if (scalarType == null) {
            scalarType = this.typeManager.createEnumScalarType(enumType);
            if (scalarType == null) {
                final EnumType type = (enumerated != null) ? enumerated.value() : null;
                scalarType = this.createEnumScalarTypePerSpec(enumType, type, prop.getDbType());
            }
            this.typeManager.add(scalarType);
        }
        prop.setScalarType(scalarType);
        prop.setDbType(scalarType.getJdbcType());
        return scalarType;
    }
    
    private ScalarType<?> createEnumScalarTypePerSpec(final Class<?> enumType, final EnumType type, final int dbType) {
        if (type == null) {
            return (ScalarType<?>)new ScalarTypeEnumStandard.OrdinalEnum(enumType);
        }
        if (type == EnumType.ORDINAL) {
            return (ScalarType<?>)new ScalarTypeEnumStandard.OrdinalEnum(enumType);
        }
        return (ScalarType<?>)new ScalarTypeEnumStandard.StringEnum(enumType);
    }
    
    public void setScalarType(final DeployBeanProperty property) {
        if (property.getScalarType() != null) {
            return;
        }
        if (property instanceof DeployBeanPropertyCompound) {
            return;
        }
        final ScalarType<?> scalarType = this.getScalarType(property);
        if (scalarType != null) {
            property.setDbType(scalarType.getJdbcType());
            property.setScalarType(scalarType);
        }
    }
    
    private ScalarType<?> getScalarType(final DeployBeanProperty property) {
        final Class<?> propType = property.getPropertyType();
        final ScalarType<?> scalarType = this.typeManager.getScalarType(propType, property.getDbType());
        if (scalarType != null) {
            return scalarType;
        }
        final String msg = property.getFullBeanName() + " has no ScalarType - type[" + propType.getName() + "]";
        if (!property.isTransient()) {
            throw new PersistenceException(msg);
        }
        DeployUtil.logger.finest("... transient property " + msg);
        return null;
    }
    
    public void setLobType(final DeployBeanProperty prop) {
        final Class<?> type = prop.getPropertyType();
        final int lobType = this.isClobType(type) ? 2005 : 2004;
        final ScalarType<?> scalarType = this.typeManager.getScalarType(type, lobType);
        if (scalarType == null) {
            throw new RuntimeException("No ScalarType for LOB type [" + type + "] [" + lobType + "]");
        }
        prop.setDbType(lobType);
        prop.setScalarType(scalarType);
    }
    
    public boolean isClobType(final Class<?> type) {
        return type.equals(String.class);
    }
    
    static {
        logger = Logger.getLogger(DeployUtil.class.getName());
    }
}
