// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.file;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import java.util.LinkedHashMap;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.representer.SafeRepresenter;
import org.yaml.snakeyaml.nodes.Tag;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.ConfigurationSection;
import org.yaml.snakeyaml.representer.Representer;

public class YamlRepresenter extends Representer
{
    public YamlRepresenter() {
        this.multiRepresenters.put(ConfigurationSection.class, new RepresentConfigurationSection());
        this.multiRepresenters.put(ConfigurationSerializable.class, new RepresentConfigurationSerializable());
    }
    
    private class RepresentConfigurationSection extends RepresentMap
    {
        public Node representData(final Object data) {
            return super.representData(((ConfigurationSection)data).getValues(false));
        }
    }
    
    private class RepresentConfigurationSerializable extends RepresentMap
    {
        public Node representData(final Object data) {
            final ConfigurationSerializable serializable = (ConfigurationSerializable)data;
            final Map<String, Object> values = new LinkedHashMap<String, Object>();
            values.put("==", ConfigurationSerialization.getAlias(serializable.getClass()));
            values.putAll(serializable.serialize());
            return super.representData(values);
        }
    }
}
