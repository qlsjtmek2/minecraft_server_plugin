// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.persist.dml;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableId;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableUnidirectional;
import java.util.List;
import com.avaje.ebeaninternal.server.persist.dmlbind.BindableList;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.dbplatform.DbEncrypt;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryId;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryAssocOnes;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryVersion;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryEmbedded;
import com.avaje.ebeaninternal.server.persist.dmlbind.FactoryBaseProperties;

public class MetaFactory
{
    private final FactoryBaseProperties baseFact;
    private final FactoryEmbedded embeddedFact;
    private final FactoryVersion versionFact;
    private final FactoryAssocOnes assocOneFact;
    private final FactoryId idFact;
    private static final boolean includeLobs = true;
    private final DatabasePlatform dbPlatform;
    private final boolean emptyStringAsNull;
    
    public MetaFactory(final DatabasePlatform dbPlatform) {
        this.versionFact = new FactoryVersion();
        this.assocOneFact = new FactoryAssocOnes();
        this.idFact = new FactoryId();
        this.dbPlatform = dbPlatform;
        this.emptyStringAsNull = dbPlatform.isTreatEmptyStringsAsNull();
        final DbEncrypt dbEncrypt = dbPlatform.getDbEncrypt();
        final boolean bindEncryptDataFirst = dbEncrypt == null || dbEncrypt.isBindEncryptDataFirst();
        this.baseFact = new FactoryBaseProperties(bindEncryptDataFirst);
        this.embeddedFact = new FactoryEmbedded(bindEncryptDataFirst);
    }
    
    public UpdateMeta createUpdate(final BeanDescriptor<?> desc) {
        final List<Bindable> setList = new ArrayList<Bindable>();
        this.baseFact.create(setList, desc, DmlMode.UPDATE, true);
        this.embeddedFact.create(setList, desc, DmlMode.UPDATE, true);
        this.assocOneFact.create(setList, desc, DmlMode.UPDATE);
        final Bindable id = this.idFact.createId(desc);
        final Bindable ver = this.versionFact.create(desc);
        final List<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.WHERE, false);
        this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
        this.assocOneFact.create(allList, desc, DmlMode.WHERE);
        final Bindable setBindable = new BindableList(setList);
        final Bindable allBindable = new BindableList(allList);
        return new UpdateMeta(this.emptyStringAsNull, desc, setBindable, id, ver, allBindable);
    }
    
    public DeleteMeta createDelete(final BeanDescriptor<?> desc) {
        final Bindable id = this.idFact.createId(desc);
        final Bindable ver = this.versionFact.create(desc);
        final List<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.WHERE, false);
        this.embeddedFact.create(allList, desc, DmlMode.WHERE, false);
        this.assocOneFact.create(allList, desc, DmlMode.WHERE);
        final Bindable allBindable = new BindableList(allList);
        return new DeleteMeta(this.emptyStringAsNull, desc, id, ver, allBindable);
    }
    
    public InsertMeta createInsert(final BeanDescriptor<?> desc) {
        final BindableId id = this.idFact.createId(desc);
        final List<Bindable> allList = new ArrayList<Bindable>();
        this.baseFact.create(allList, desc, DmlMode.INSERT, true);
        this.embeddedFact.create(allList, desc, DmlMode.INSERT, true);
        this.assocOneFact.create(allList, desc, DmlMode.INSERT);
        final Bindable allBindable = new BindableList(allList);
        final BeanPropertyAssocOne<?> unidirectional = desc.getUnidirectional();
        Bindable shadowFkey;
        if (unidirectional == null) {
            shadowFkey = null;
        }
        else {
            shadowFkey = new BindableUnidirectional(desc, unidirectional);
        }
        return new InsertMeta(this.dbPlatform, desc, shadowFkey, id, allBindable);
    }
}
