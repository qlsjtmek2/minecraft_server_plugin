// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import java.util.HashSet;
import java.util.Collection;
import com.avaje.ebean.bean.NodeUsageCollector;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.SplitName;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.io.Serializable;

public class StatisticsNodeUsage implements Serializable
{
    private static final long serialVersionUID = -1663951463963779547L;
    private static final Logger logger;
    private final String monitor;
    private final String path;
    private final boolean queryTuningAddVersion;
    private int profileCount;
    private int profileUsedCount;
    private boolean modified;
    private Set<String> aggregateUsed;
    
    public StatisticsNodeUsage(final String path, final boolean queryTuningAddVersion) {
        this.monitor = new String();
        this.aggregateUsed = new LinkedHashSet<String>();
        this.path = path;
        this.queryTuningAddVersion = queryTuningAddVersion;
    }
    
    public MetaAutoFetchStatistic.NodeUsageStats createPublicMeta() {
        synchronized (this.monitor) {
            final String[] usedProps = this.aggregateUsed.toArray(new String[this.aggregateUsed.size()]);
            return new MetaAutoFetchStatistic.NodeUsageStats(this.path, this.profileCount, this.profileUsedCount, usedProps);
        }
    }
    
    public void buildTunedFetch(final PathProperties pathProps, final BeanDescriptor<?> rootDesc) {
        synchronized (this.monitor) {
            BeanDescriptor<?> desc = rootDesc;
            if (this.path != null) {
                final ElPropertyValue elGetValue = rootDesc.getElGetValue(this.path);
                if (elGetValue == null) {
                    desc = null;
                    StatisticsNodeUsage.logger.warning("Autofetch: Can't find join for path[" + this.path + "] for " + rootDesc.getName());
                }
                else {
                    final BeanProperty beanProperty = elGetValue.getBeanProperty();
                    if (beanProperty instanceof BeanPropertyAssoc) {
                        desc = ((BeanPropertyAssoc)beanProperty).getTargetDescriptor();
                    }
                }
            }
            for (final String propName : this.aggregateUsed) {
                final BeanProperty beanProp = desc.getBeanPropertyFromPath(propName);
                if (beanProp == null) {
                    StatisticsNodeUsage.logger.warning("Autofetch: Can't find property[" + propName + "] for " + desc.getName());
                }
                else if (beanProp instanceof BeanPropertyAssoc) {
                    final BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc<?>)beanProp;
                    final String targetIdProp = assocProp.getTargetIdProperty();
                    final String manyPath = SplitName.add(this.path, assocProp.getName());
                    pathProps.addToPath(manyPath, targetIdProp);
                }
                else {
                    if (beanProp.isLob() && !beanProp.isFetchEager()) {
                        continue;
                    }
                    pathProps.addToPath(this.path, beanProp.getName());
                }
            }
            if ((this.modified || this.queryTuningAddVersion) && desc != null) {
                final BeanProperty[] versionProps = desc.propertiesVersion();
                if (versionProps.length > 0) {
                    pathProps.addToPath(this.path, versionProps[0].getName());
                }
            }
        }
    }
    
    public void publish(final NodeUsageCollector profile) {
        synchronized (this.monitor) {
            final HashSet<String> used = profile.getUsed();
            ++this.profileCount;
            if (!used.isEmpty()) {
                ++this.profileUsedCount;
                this.aggregateUsed.addAll(used);
            }
            if (profile.isModified()) {
                this.modified = true;
            }
        }
    }
    
    public String toString() {
        return "path[" + this.path + "] profileCount[" + this.profileCount + "] used[" + this.profileUsedCount + "] props" + this.aggregateUsed;
    }
    
    static {
        logger = Logger.getLogger(StatisticsNodeUsage.class.getName());
    }
}
