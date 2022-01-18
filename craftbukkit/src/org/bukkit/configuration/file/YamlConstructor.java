// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.configuration.file;

import java.util.Iterator;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class YamlConstructor extends SafeConstructor
{
    public YamlConstructor() {
        this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
    }
    
    private class ConstructCustomObject extends ConstructYamlMap
    {
        public Object construct(final Node node) {
            if (node.isTwoStepsConstruction()) {
                throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
            }
            final Map<?, ?> raw = (Map<?, ?>)super.construct(node);
            if (raw.containsKey("==")) {
                final Map<String, Object> typed = new LinkedHashMap<String, Object>(raw.size());
                for (final Map.Entry<?, ?> entry : raw.entrySet()) {
                    typed.put(entry.getKey().toString(), entry.getValue());
                }
                try {
                    return ConfigurationSerialization.deserializeObject(typed);
                }
                catch (IllegalArgumentException ex) {
                    throw new YAMLException("Could not deserialize object", ex);
                }
            }
            return raw;
        }
        
        public void construct2ndStep(final Node node, final Object object) {
            throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
        }
    }
}
