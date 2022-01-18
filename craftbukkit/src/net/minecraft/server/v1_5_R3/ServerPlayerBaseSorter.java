// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Iterator;
import java.util.HashSet;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Map;

public final class ServerPlayerBaseSorter
{
    private Map<String, Set<String>> explicitInferiors;
    private Map<String, Set<String>> explicitSuperiors;
    private Map<String, Set<String>> directInferiorsMap;
    private Map<String, Set<String>> allInferiors;
    private List<String> withoutSuperiors;
    private final List<String> list;
    private final Map<String, String[]> allBaseSuperiors;
    private final Map<String, String[]> allBaseInferiors;
    private final String methodName;
    private static final Set<String> Empty;
    
    public ServerPlayerBaseSorter(final List<String> list, final Map<String, String[]> allBaseSuperiors, final Map<String, String[]> allBaseInferiors, final String methodName) {
        this.list = list;
        this.allBaseSuperiors = allBaseSuperiors;
        this.allBaseInferiors = allBaseInferiors;
        this.methodName = methodName;
    }
    
    public void Sort() {
        if (this.list.size() <= 1) {
            return;
        }
        if (this.explicitInferiors != null) {
            this.explicitInferiors.clear();
        }
        if (this.explicitSuperiors != null) {
            this.explicitSuperiors.clear();
        }
        if (this.directInferiorsMap != null) {
            this.directInferiorsMap.clear();
        }
        if (this.allInferiors != null) {
            this.allInferiors.clear();
        }
        for (int i = 0; i < this.list.size(); ++i) {
            final String s = this.list.get(i);
            final String[] array = this.allBaseInferiors.get(s);
            final boolean b = array != null && array.length > 0;
            final String[] array2 = this.allBaseSuperiors.get(s);
            final boolean b2 = array2 != null && array2.length > 0;
            if ((b || b2) && this.directInferiorsMap == null) {
                this.directInferiorsMap = new Hashtable<String, Set<String>>();
            }
            if (b) {
                this.explicitInferiors = build(s, this.explicitInferiors, this.directInferiorsMap, null, array);
            }
            if (b2) {
                this.explicitSuperiors = build(s, this.explicitSuperiors, null, this.directInferiorsMap, array2);
            }
        }
        if (this.directInferiorsMap != null) {
            for (int j = 0; j < this.list.size() - 1; ++j) {
                for (int k = j + 1; k < this.list.size(); ++k) {
                    final String s2 = this.list.get(j);
                    final String s3 = this.list.get(k);
                    Set<String> set = null;
                    Set<String> set2 = null;
                    if (this.explicitInferiors != null) {
                        set = this.explicitInferiors.get(s2);
                        set2 = this.explicitInferiors.get(s3);
                    }
                    Set<String> set3 = null;
                    Set<String> set4 = null;
                    if (this.explicitSuperiors != null) {
                        set3 = this.explicitSuperiors.get(s2);
                        set4 = this.explicitSuperiors.get(s3);
                    }
                    final boolean b3 = set3 != null && set3.contains(s3);
                    final boolean b4 = set != null && set.contains(s3);
                    final boolean b5 = set4 != null && set4.contains(s2);
                    final boolean b6 = set2 != null && set2.contains(s2);
                    if (b3 && b5) {
                        throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. '" + s2 + "' wants to be inferior to '" + s3 + "' and '" + s3 + "' wants to be inferior to '" + s2 + "'");
                    }
                    if (b4 && b6) {
                        throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. '" + s2 + "' wants to be superior to '" + s3 + "' and '" + s3 + "' wants to be superior to '" + s2 + "'");
                    }
                    if (b3 && b4) {
                        throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. '" + s2 + "' wants to be superior and inferior to '" + s3 + "'");
                    }
                    if (b5 && b6) {
                        throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. '" + s3 + "' wants to be superior and inferior to '" + s2 + "'");
                    }
                }
            }
            if (this.allInferiors == null) {
                this.allInferiors = new Hashtable<String, Set<String>>();
            }
            for (int l = 0; l < this.list.size(); ++l) {
                this.build(this.list.get(l), null);
            }
        }
        if (this.withoutSuperiors == null) {
            this.withoutSuperiors = new LinkedList<String>();
        }
        int n = 0;
        int size = this.list.size();
        while (size > 1) {
            this.withoutSuperiors.clear();
            for (int n2 = n; n2 < n + size; ++n2) {
                this.withoutSuperiors.add(this.list.get(n2));
            }
            if (this.allInferiors != null) {
                for (int n3 = n; n3 < n + size; ++n3) {
                    final Set<String> set5 = this.allInferiors.get(this.list.get(n3));
                    if (set5 != null) {
                        this.withoutSuperiors.removeAll(set5);
                    }
                }
            }
            int n4 = 1;
            for (int n5 = n; n5 < n + size; ++n5) {
                final String s4 = this.list.get(n5);
                if (this.withoutSuperiors.contains(s4)) {
                    if (n4 != 0) {
                        Set<String> set6 = null;
                        if (this.allInferiors != null) {
                            set6 = this.allInferiors.get(s4);
                        }
                        if (set6 == null || set6.isEmpty()) {
                            this.withoutSuperiors.remove(s4);
                            --size;
                            ++n;
                            continue;
                        }
                    }
                    this.list.remove(n5--);
                    --size;
                }
                n4 = 0;
            }
            this.list.addAll(n + size, this.withoutSuperiors);
        }
    }
    
    private Set<String> build(final String s, final String s2) {
        Set<String> set = this.allInferiors.get(s);
        if (set == null) {
            set = this.build(s, null, (s2 != null) ? s2 : s);
            if (set == null) {
                set = ServerPlayerBaseSorter.Empty;
            }
            this.allInferiors.put(s, set);
        }
        return set;
    }
    
    private Set<String> build(final String s, Set<String> set, final String s2) {
        final Set<String> set2 = this.directInferiorsMap.get(s);
        if (set2 == null) {
            return set;
        }
        if (set == null) {
            set = new HashSet<String>();
        }
        for (final String s3 : set2) {
            if (s3 == s2) {
                throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. Circular superiosity found including '" + s2 + "'");
            }
            if (this.list.contains(s3)) {
                set.add(s3);
            }
            Set<String> build;
            try {
                build = this.build(s3, s2);
            }
            catch (UnsupportedOperationException ex) {
                throw new UnsupportedOperationException("Can not sort ServerPlayerBase classes for method '" + this.methodName + "'. Circular superiosity found including '" + s3 + "'", ex);
            }
            if (build == ServerPlayerBaseSorter.Empty) {
                continue;
            }
            set.addAll((Collection<?>)build);
        }
        return set;
    }
    
    private static Map<String, Set<String>> build(final String s, Map<String, Set<String>> hashtable, final Map<String, Set<String>> map, final Map<String, Set<String>> map2, final String[] array) {
        if (hashtable == null) {
            hashtable = new Hashtable<String, HashSet<String>>();
        }
        final HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                set.add(array[i]);
            }
        }
        if (map != null) {
            getOrCreateSet(map, s).addAll(set);
        }
        if (map2 != null) {
            final Iterator<Object> iterator = set.iterator();
            while (iterator.hasNext()) {
                getOrCreateSet(map2, iterator.next()).add(s);
            }
        }
        hashtable.put(s, set);
        return (Map<String, Set<String>>)hashtable;
    }
    
    private static Set<String> getOrCreateSet(final Map<String, Set<String>> map, final String s) {
        HashSet<String> set = map.get(s);
        if (set == null) {
            map.put(s, set = new HashSet<String>());
        }
        return set;
    }
    
    static {
        Empty = new HashSet<String>();
    }
}
