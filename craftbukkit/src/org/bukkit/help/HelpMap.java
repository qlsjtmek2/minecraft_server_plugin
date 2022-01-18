// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.help;

import java.util.List;
import java.util.Collection;

public interface HelpMap
{
    HelpTopic getHelpTopic(final String p0);
    
    Collection<HelpTopic> getHelpTopics();
    
    void addTopic(final HelpTopic p0);
    
    void clear();
    
    void registerHelpTopicFactory(final Class<?> p0, final HelpTopicFactory<?> p1);
    
    List<String> getIgnoredPlugins();
}
