// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.autofetch;

import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.meta.MetaAutoFetchTunedQueryInfo;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import java.io.Serializable;

public class TunedQueryInfo implements Serializable
{
    private static final long serialVersionUID = 7381493228797997282L;
    private final ObjectGraphOrigin origin;
    private OrmQueryDetail tunedDetail;
    private int profileCount;
    private Long lastTuneTime;
    private final String rateMonitor;
    private transient int tunedCount;
    private transient int rateTotal;
    private transient int rateHits;
    private transient double lastRate;
    
    public TunedQueryInfo(final ObjectGraphOrigin queryPoint, final OrmQueryDetail tunedDetail, final int profileCount) {
        this.lastTuneTime = 0L;
        this.rateMonitor = new String();
        this.origin = queryPoint;
        this.tunedDetail = tunedDetail;
        this.profileCount = profileCount;
    }
    
    public boolean isPercentageProfile(final double rate) {
        synchronized (this.rateMonitor) {
            if (this.lastRate != rate) {
                this.lastRate = rate;
                this.rateTotal = 0;
                this.rateHits = 0;
            }
            ++this.rateTotal;
            if (rate > this.rateHits / this.rateTotal) {
                ++this.rateHits;
                return true;
            }
            return false;
        }
    }
    
    public MetaAutoFetchTunedQueryInfo createPublicMeta() {
        return new MetaAutoFetchTunedQueryInfo(this.origin, this.tunedDetail.toString(), this.profileCount, this.tunedCount, this.lastTuneTime);
    }
    
    public void setProfileCount(final int profileCount) {
        this.profileCount = profileCount;
    }
    
    public void setTunedDetail(final OrmQueryDetail tunedDetail) {
        this.tunedDetail = tunedDetail;
        this.lastTuneTime = System.currentTimeMillis();
    }
    
    public boolean isSame(final OrmQueryDetail newQueryDetail) {
        return this.tunedDetail != null && this.tunedDetail.isAutoFetchEqual(newQueryDetail);
    }
    
    public boolean autoFetchTune(final SpiQuery<?> query) {
        if (this.tunedDetail == null) {
            return false;
        }
        boolean tuned = false;
        if (query.isDetailEmpty()) {
            tuned = true;
            query.setDetail(this.tunedDetail.copy());
        }
        else {
            tuned = query.tuneFetchProperties(this.tunedDetail);
        }
        if (tuned) {
            query.setAutoFetchTuned(true);
            ++this.tunedCount;
        }
        return tuned;
    }
    
    public Long getLastTuneTime() {
        return this.lastTuneTime;
    }
    
    public int getTunedCount() {
        return this.tunedCount;
    }
    
    public int getProfileCount() {
        return this.profileCount;
    }
    
    public OrmQueryDetail getTunedDetail() {
        return this.tunedDetail;
    }
    
    public ObjectGraphOrigin getOrigin() {
        return this.origin;
    }
    
    public String getLogOutput(final OrmQueryDetail newQueryDetail) {
        final boolean changed = newQueryDetail != null;
        final StringBuilder sb = new StringBuilder(150);
        sb.append(changed ? "\"Changed\"," : "\"New\",");
        sb.append("\"").append(this.origin.getBeanType()).append("\",");
        sb.append("\"").append(this.origin.getKey()).append("\",");
        if (changed) {
            sb.append("\"to: ").append(newQueryDetail.toString()).append("\",");
            sb.append("\"from: ").append(this.tunedDetail.toString()).append("\",");
        }
        else {
            sb.append("\"to: ").append(this.tunedDetail.toString()).append("\",");
            sb.append("\"\",");
        }
        sb.append("\"").append(this.origin.getFirstStackElement()).append("\"");
        return sb.toString();
    }
    
    public String toString() {
        return this.origin.getBeanType() + " " + this.origin.getKey() + " " + this.tunedDetail;
    }
}
