// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanPropertySimpleCollection;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.validation.factory.Validator;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import java.util.List;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyCompound;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class DeployBeanPropertyLists
{
    private BeanProperty derivedFirstVersionProp;
    private final BeanDescriptor<?> desc;
    private final LinkedHashMap<String, BeanProperty> propertyMap;
    private final ArrayList<BeanProperty> ids;
    private final ArrayList<BeanProperty> version;
    private final ArrayList<BeanProperty> local;
    private final ArrayList<BeanProperty> manys;
    private final ArrayList<BeanProperty> ones;
    private final ArrayList<BeanProperty> onesExported;
    private final ArrayList<BeanProperty> onesImported;
    private final ArrayList<BeanProperty> embedded;
    private final ArrayList<BeanProperty> baseScalar;
    private final ArrayList<BeanPropertyCompound> baseCompound;
    private final ArrayList<BeanProperty> transients;
    private final ArrayList<BeanProperty> nonTransients;
    private final TableJoin[] tableJoins;
    private final BeanPropertyAssocOne<?> unidirectional;
    
    public DeployBeanPropertyLists(final BeanDescriptorMap owner, final BeanDescriptor<?> desc, final DeployBeanDescriptor<?> deploy) {
        this.ids = new ArrayList<BeanProperty>();
        this.version = new ArrayList<BeanProperty>();
        this.local = new ArrayList<BeanProperty>();
        this.manys = new ArrayList<BeanProperty>();
        this.ones = new ArrayList<BeanProperty>();
        this.onesExported = new ArrayList<BeanProperty>();
        this.onesImported = new ArrayList<BeanProperty>();
        this.embedded = new ArrayList<BeanProperty>();
        this.baseScalar = new ArrayList<BeanProperty>();
        this.baseCompound = new ArrayList<BeanPropertyCompound>();
        this.transients = new ArrayList<BeanProperty>();
        this.nonTransients = new ArrayList<BeanProperty>();
        this.desc = desc;
        final DeployBeanPropertyAssocOne<?> deployUnidirectional = deploy.getUnidirectional();
        if (deployUnidirectional == null) {
            this.unidirectional = null;
        }
        else {
            this.unidirectional = new BeanPropertyAssocOne<Object>(owner, desc, deployUnidirectional);
        }
        this.propertyMap = new LinkedHashMap<String, BeanProperty>();
        final Iterator<DeployBeanProperty> deployIt = deploy.propertiesAll();
        while (deployIt.hasNext()) {
            final DeployBeanProperty deployProp = deployIt.next();
            final BeanProperty beanProp = this.createBeanProperty(owner, deployProp);
            this.propertyMap.put(beanProp.getName(), beanProp);
        }
        final Iterator<BeanProperty> it = this.propertyMap.values().iterator();
        int order = 0;
        while (it.hasNext()) {
            final BeanProperty prop = it.next();
            prop.setDeployOrder(order++);
            this.allocateToList(prop);
        }
        final List<DeployTableJoin> deployTableJoins = deploy.getTableJoins();
        this.tableJoins = new TableJoin[deployTableJoins.size()];
        for (int i = 0; i < deployTableJoins.size(); ++i) {
            this.tableJoins[i] = new TableJoin(deployTableJoins.get(i), this.propertyMap);
        }
    }
    
    public BeanPropertyAssocOne<?> getUnidirectional() {
        return this.unidirectional;
    }
    
    private void allocateToList(final BeanProperty prop) {
        if (prop.isTransient()) {
            this.transients.add(prop);
            return;
        }
        if (prop.isId()) {
            this.ids.add(prop);
            return;
        }
        this.nonTransients.add(prop);
        if (this.desc.getInheritInfo() != null && prop.isLocal()) {
            this.local.add(prop);
        }
        if (prop instanceof BeanPropertyAssocMany) {
            this.manys.add(prop);
        }
        else if (prop instanceof BeanPropertyAssocOne) {
            if (prop.isEmbedded()) {
                this.embedded.add(prop);
            }
            else {
                this.ones.add(prop);
                final BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>)prop;
                if (assocOne.isOneToOneExported()) {
                    this.onesExported.add(prop);
                }
                else {
                    this.onesImported.add(prop);
                }
            }
        }
        else {
            if (prop.isVersion()) {
                this.version.add(prop);
                if (this.derivedFirstVersionProp == null) {
                    this.derivedFirstVersionProp = prop;
                }
            }
            if (prop instanceof BeanPropertyCompound) {
                this.baseCompound.add((BeanPropertyCompound)prop);
            }
            else {
                this.baseScalar.add(prop);
            }
        }
    }
    
    public BeanProperty getFirstVersion() {
        return this.derivedFirstVersionProp;
    }
    
    public BeanProperty[] getPropertiesWithValidators(final boolean recurse) {
        final ArrayList<BeanProperty> list = new ArrayList<BeanProperty>();
        for (final BeanProperty property : this.propertyMap.values()) {
            if (property.hasValidationRules(recurse)) {
                list.add(property);
            }
        }
        return list.toArray(new BeanProperty[list.size()]);
    }
    
    public Validator[] getBeanValidators() {
        return new Validator[0];
    }
    
    public LinkedHashMap<String, BeanProperty> getPropertyMap() {
        return this.propertyMap;
    }
    
    public TableJoin[] getTableJoin() {
        return this.tableJoins;
    }
    
    public BeanProperty[] getBaseScalar() {
        return this.baseScalar.toArray(new BeanProperty[this.baseScalar.size()]);
    }
    
    public BeanPropertyCompound[] getBaseCompound() {
        return this.baseCompound.toArray(new BeanPropertyCompound[this.baseCompound.size()]);
    }
    
    public BeanProperty[] getId() {
        return this.ids.toArray(new BeanProperty[this.ids.size()]);
    }
    
    public BeanProperty[] getNonTransients() {
        return this.nonTransients.toArray(new BeanProperty[this.nonTransients.size()]);
    }
    
    public BeanProperty[] getTransients() {
        return this.transients.toArray(new BeanProperty[this.transients.size()]);
    }
    
    public BeanProperty[] getVersion() {
        return this.version.toArray(new BeanProperty[this.version.size()]);
    }
    
    public BeanProperty[] getLocal() {
        return this.local.toArray(new BeanProperty[this.local.size()]);
    }
    
    public BeanPropertyAssocOne<?>[] getEmbedded() {
        return this.embedded.toArray(new BeanPropertyAssocOne[this.embedded.size()]);
    }
    
    public BeanPropertyAssocOne<?>[] getOneExported() {
        return this.onesExported.toArray(new BeanPropertyAssocOne[this.onesExported.size()]);
    }
    
    public BeanPropertyAssocOne<?>[] getOneImported() {
        return this.onesImported.toArray(new BeanPropertyAssocOne[this.onesImported.size()]);
    }
    
    public BeanPropertyAssocOne<?>[] getOnes() {
        return this.ones.toArray(new BeanPropertyAssocOne[this.ones.size()]);
    }
    
    public BeanPropertyAssocOne<?>[] getOneExportedSave() {
        return this.getOne(false, Mode.Save);
    }
    
    public BeanPropertyAssocOne<?>[] getOneExportedDelete() {
        return this.getOne(false, Mode.Delete);
    }
    
    public BeanPropertyAssocOne<?>[] getOneImportedSave() {
        return this.getOne(true, Mode.Save);
    }
    
    public BeanPropertyAssocOne<?>[] getOneImportedDelete() {
        return this.getOne(true, Mode.Delete);
    }
    
    public BeanPropertyAssocMany<?>[] getMany() {
        return this.manys.toArray(new BeanPropertyAssocMany[this.manys.size()]);
    }
    
    public BeanPropertyAssocMany<?>[] getManySave() {
        return this.getMany(Mode.Save);
    }
    
    public BeanPropertyAssocMany<?>[] getManyDelete() {
        return this.getMany(Mode.Delete);
    }
    
    public BeanPropertyAssocMany<?>[] getManyToMany() {
        return this.getMany2Many();
    }
    
    private BeanPropertyAssocOne<?>[] getOne(final boolean imported, final Mode mode) {
        final ArrayList<BeanPropertyAssocOne<?>> list = new ArrayList<BeanPropertyAssocOne<?>>();
        for (int i = 0; i < this.ones.size(); ++i) {
            final BeanPropertyAssocOne<?> prop = this.ones.get(i);
            if (imported != prop.isOneToOneExported()) {
                switch (mode) {
                    case Save: {
                        if (prop.getCascadeInfo().isSave()) {
                            list.add(prop);
                            break;
                        }
                        break;
                    }
                    case Delete: {
                        if (prop.getCascadeInfo().isDelete()) {
                            list.add(prop);
                            break;
                        }
                        break;
                    }
                    case Validate: {
                        if (prop.getCascadeInfo().isValidate()) {
                            list.add(prop);
                            break;
                        }
                        break;
                    }
                }
            }
        }
        return list.toArray(new BeanPropertyAssocOne[list.size()]);
    }
    
    private BeanPropertyAssocMany<?>[] getMany2Many() {
        final ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
        for (int i = 0; i < this.manys.size(); ++i) {
            final BeanPropertyAssocMany<?> prop = this.manys.get(i);
            if (prop.isManyToMany()) {
                list.add(prop);
            }
        }
        return list.toArray(new BeanPropertyAssocMany[list.size()]);
    }
    
    private BeanPropertyAssocMany<?>[] getMany(final Mode mode) {
        final ArrayList<BeanPropertyAssocMany<?>> list = new ArrayList<BeanPropertyAssocMany<?>>();
        for (int i = 0; i < this.manys.size(); ++i) {
            final BeanPropertyAssocMany<?> prop = this.manys.get(i);
            switch (mode) {
                case Save: {
                    if (prop.getCascadeInfo().isSave() || prop.isManyToMany() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode())) {
                        list.add(prop);
                        break;
                    }
                    break;
                }
                case Delete: {
                    if (prop.getCascadeInfo().isDelete() || BeanCollection.ModifyListenMode.REMOVALS.equals(prop.getModifyListenMode())) {
                        list.add(prop);
                        break;
                    }
                    break;
                }
                case Validate: {
                    if (prop.getCascadeInfo().isValidate()) {
                        list.add(prop);
                        break;
                    }
                    break;
                }
            }
        }
        return list.toArray(new BeanPropertyAssocMany[list.size()]);
    }
    
    private BeanProperty createBeanProperty(final BeanDescriptorMap owner, final DeployBeanProperty deployProp) {
        if (deployProp instanceof DeployBeanPropertyAssocOne) {
            return new BeanPropertyAssocOne<Object>(owner, this.desc, (DeployBeanPropertyAssocOne<?>)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertySimpleCollection) {
            return new BeanPropertySimpleCollection<Object>(owner, this.desc, (DeployBeanPropertySimpleCollection<?>)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertyAssocMany) {
            return new BeanPropertyAssocMany<Object>(owner, this.desc, (DeployBeanPropertyAssocMany<?>)deployProp);
        }
        if (deployProp instanceof DeployBeanPropertyCompound) {
            return new BeanPropertyCompound(owner, this.desc, (DeployBeanPropertyCompound)deployProp);
        }
        return new BeanProperty(owner, this.desc, deployProp);
    }
    
    private enum Mode
    {
        Save, 
        Delete, 
        Validate;
    }
}
