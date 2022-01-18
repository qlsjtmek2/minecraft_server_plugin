// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.config.lucene.LuceneConfig;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebeaninternal.server.lucene.DefaultLuceneIndexManager;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.Query;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;

public class LuceneManagerFactory
{
    public static LuceneIndexManager createLuceneManager(final ClusterManager clusterManager, final BackgroundExecutor executor, final ServerConfig serverConfig) {
        Analyzer defaultAnalyzer = null;
        String baseDir = null;
        Query.UseIndex defaultUseIndex = null;
        final LuceneConfig luceneConfig = serverConfig.getLuceneConfig();
        if (luceneConfig != null) {
            defaultAnalyzer = null;
            baseDir = luceneConfig.getBaseDirectory();
            defaultUseIndex = luceneConfig.getDefaultUseIndex();
        }
        if (defaultAnalyzer == null) {
            defaultAnalyzer = (Analyzer)new StandardAnalyzer(Version.LUCENE_30);
        }
        if (defaultUseIndex == null) {
            defaultUseIndex = Query.UseIndex.NO;
        }
        if (baseDir == null) {
            baseDir = serverConfig.getPropertySource().get("lucene.baseDirectory", "lucene");
        }
        baseDir = GlobalProperties.evaluateExpressions(baseDir);
        return new DefaultLuceneIndexManager(clusterManager, executor, defaultAnalyzer, baseDir, serverConfig.getName(), defaultUseIndex);
    }
}
