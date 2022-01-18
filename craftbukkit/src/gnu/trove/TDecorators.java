// 
// Decompiled by Procyon v0.5.30
// 

package gnu.trove;

import gnu.trove.decorator.TCharListDecorator;
import gnu.trove.list.TCharList;
import gnu.trove.decorator.TShortListDecorator;
import gnu.trove.list.TShortList;
import gnu.trove.decorator.TByteListDecorator;
import gnu.trove.list.TByteList;
import gnu.trove.decorator.TLongListDecorator;
import gnu.trove.list.TLongList;
import gnu.trove.decorator.TIntListDecorator;
import gnu.trove.list.TIntList;
import gnu.trove.decorator.TFloatListDecorator;
import gnu.trove.list.TFloatList;
import gnu.trove.decorator.TDoubleListDecorator;
import java.util.List;
import gnu.trove.list.TDoubleList;
import gnu.trove.decorator.TCharSetDecorator;
import gnu.trove.set.TCharSet;
import gnu.trove.decorator.TShortSetDecorator;
import gnu.trove.set.TShortSet;
import gnu.trove.decorator.TByteSetDecorator;
import gnu.trove.set.TByteSet;
import gnu.trove.decorator.TLongSetDecorator;
import gnu.trove.set.TLongSet;
import gnu.trove.decorator.TIntSetDecorator;
import gnu.trove.set.TIntSet;
import gnu.trove.decorator.TFloatSetDecorator;
import gnu.trove.set.TFloatSet;
import gnu.trove.decorator.TDoubleSetDecorator;
import java.util.Set;
import gnu.trove.set.TDoubleSet;
import gnu.trove.decorator.TCharObjectMapDecorator;
import gnu.trove.map.TCharObjectMap;
import gnu.trove.decorator.TShortObjectMapDecorator;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.decorator.TByteObjectMapDecorator;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.decorator.TLongObjectMapDecorator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.decorator.TIntObjectMapDecorator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.decorator.TFloatObjectMapDecorator;
import gnu.trove.map.TFloatObjectMap;
import gnu.trove.decorator.TDoubleObjectMapDecorator;
import gnu.trove.map.TDoubleObjectMap;
import gnu.trove.decorator.TObjectCharMapDecorator;
import gnu.trove.map.TObjectCharMap;
import gnu.trove.decorator.TObjectShortMapDecorator;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.decorator.TObjectByteMapDecorator;
import gnu.trove.map.TObjectByteMap;
import gnu.trove.decorator.TObjectLongMapDecorator;
import gnu.trove.map.TObjectLongMap;
import gnu.trove.decorator.TObjectIntMapDecorator;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.decorator.TObjectFloatMapDecorator;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.decorator.TObjectDoubleMapDecorator;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.decorator.TCharCharMapDecorator;
import gnu.trove.map.TCharCharMap;
import gnu.trove.decorator.TCharShortMapDecorator;
import gnu.trove.map.TCharShortMap;
import gnu.trove.decorator.TCharByteMapDecorator;
import gnu.trove.map.TCharByteMap;
import gnu.trove.decorator.TCharLongMapDecorator;
import gnu.trove.map.TCharLongMap;
import gnu.trove.decorator.TCharIntMapDecorator;
import gnu.trove.map.TCharIntMap;
import gnu.trove.decorator.TCharFloatMapDecorator;
import gnu.trove.map.TCharFloatMap;
import gnu.trove.decorator.TCharDoubleMapDecorator;
import gnu.trove.map.TCharDoubleMap;
import gnu.trove.decorator.TShortCharMapDecorator;
import gnu.trove.map.TShortCharMap;
import gnu.trove.decorator.TShortShortMapDecorator;
import gnu.trove.map.TShortShortMap;
import gnu.trove.decorator.TShortByteMapDecorator;
import gnu.trove.map.TShortByteMap;
import gnu.trove.decorator.TShortLongMapDecorator;
import gnu.trove.map.TShortLongMap;
import gnu.trove.decorator.TShortIntMapDecorator;
import gnu.trove.map.TShortIntMap;
import gnu.trove.decorator.TShortFloatMapDecorator;
import gnu.trove.map.TShortFloatMap;
import gnu.trove.decorator.TShortDoubleMapDecorator;
import gnu.trove.map.TShortDoubleMap;
import gnu.trove.decorator.TByteCharMapDecorator;
import gnu.trove.map.TByteCharMap;
import gnu.trove.decorator.TByteShortMapDecorator;
import gnu.trove.map.TByteShortMap;
import gnu.trove.decorator.TByteByteMapDecorator;
import gnu.trove.map.TByteByteMap;
import gnu.trove.decorator.TByteLongMapDecorator;
import gnu.trove.map.TByteLongMap;
import gnu.trove.decorator.TByteIntMapDecorator;
import gnu.trove.map.TByteIntMap;
import gnu.trove.decorator.TByteFloatMapDecorator;
import gnu.trove.map.TByteFloatMap;
import gnu.trove.decorator.TByteDoubleMapDecorator;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.decorator.TLongCharMapDecorator;
import gnu.trove.map.TLongCharMap;
import gnu.trove.decorator.TLongShortMapDecorator;
import gnu.trove.map.TLongShortMap;
import gnu.trove.decorator.TLongByteMapDecorator;
import gnu.trove.map.TLongByteMap;
import gnu.trove.decorator.TLongLongMapDecorator;
import gnu.trove.map.TLongLongMap;
import gnu.trove.decorator.TLongIntMapDecorator;
import gnu.trove.map.TLongIntMap;
import gnu.trove.decorator.TLongFloatMapDecorator;
import gnu.trove.map.TLongFloatMap;
import gnu.trove.decorator.TLongDoubleMapDecorator;
import gnu.trove.map.TLongDoubleMap;
import gnu.trove.decorator.TIntCharMapDecorator;
import gnu.trove.map.TIntCharMap;
import gnu.trove.decorator.TIntShortMapDecorator;
import gnu.trove.map.TIntShortMap;
import gnu.trove.decorator.TIntByteMapDecorator;
import gnu.trove.map.TIntByteMap;
import gnu.trove.decorator.TIntLongMapDecorator;
import gnu.trove.map.TIntLongMap;
import gnu.trove.decorator.TIntIntMapDecorator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.decorator.TIntFloatMapDecorator;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.decorator.TIntDoubleMapDecorator;
import gnu.trove.map.TIntDoubleMap;
import gnu.trove.decorator.TFloatCharMapDecorator;
import gnu.trove.map.TFloatCharMap;
import gnu.trove.decorator.TFloatShortMapDecorator;
import gnu.trove.map.TFloatShortMap;
import gnu.trove.decorator.TFloatByteMapDecorator;
import gnu.trove.map.TFloatByteMap;
import gnu.trove.decorator.TFloatLongMapDecorator;
import gnu.trove.map.TFloatLongMap;
import gnu.trove.decorator.TFloatIntMapDecorator;
import gnu.trove.map.TFloatIntMap;
import gnu.trove.decorator.TFloatFloatMapDecorator;
import gnu.trove.map.TFloatFloatMap;
import gnu.trove.decorator.TFloatDoubleMapDecorator;
import gnu.trove.map.TFloatDoubleMap;
import gnu.trove.decorator.TDoubleCharMapDecorator;
import gnu.trove.map.TDoubleCharMap;
import gnu.trove.decorator.TDoubleShortMapDecorator;
import gnu.trove.map.TDoubleShortMap;
import gnu.trove.decorator.TDoubleByteMapDecorator;
import gnu.trove.map.TDoubleByteMap;
import gnu.trove.decorator.TDoubleLongMapDecorator;
import gnu.trove.map.TDoubleLongMap;
import gnu.trove.decorator.TDoubleIntMapDecorator;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.decorator.TDoubleFloatMapDecorator;
import gnu.trove.map.TDoubleFloatMap;
import gnu.trove.decorator.TDoubleDoubleMapDecorator;
import java.util.Map;
import gnu.trove.map.TDoubleDoubleMap;

public class TDecorators
{
    public static Map<Double, Double> wrap(final TDoubleDoubleMap map) {
        return new TDoubleDoubleMapDecorator(map);
    }
    
    public static Map<Double, Float> wrap(final TDoubleFloatMap map) {
        return new TDoubleFloatMapDecorator(map);
    }
    
    public static Map<Double, Integer> wrap(final TDoubleIntMap map) {
        return new TDoubleIntMapDecorator(map);
    }
    
    public static Map<Double, Long> wrap(final TDoubleLongMap map) {
        return new TDoubleLongMapDecorator(map);
    }
    
    public static Map<Double, Byte> wrap(final TDoubleByteMap map) {
        return new TDoubleByteMapDecorator(map);
    }
    
    public static Map<Double, Short> wrap(final TDoubleShortMap map) {
        return new TDoubleShortMapDecorator(map);
    }
    
    public static Map<Double, Character> wrap(final TDoubleCharMap map) {
        return new TDoubleCharMapDecorator(map);
    }
    
    public static Map<Float, Double> wrap(final TFloatDoubleMap map) {
        return new TFloatDoubleMapDecorator(map);
    }
    
    public static Map<Float, Float> wrap(final TFloatFloatMap map) {
        return new TFloatFloatMapDecorator(map);
    }
    
    public static Map<Float, Integer> wrap(final TFloatIntMap map) {
        return new TFloatIntMapDecorator(map);
    }
    
    public static Map<Float, Long> wrap(final TFloatLongMap map) {
        return new TFloatLongMapDecorator(map);
    }
    
    public static Map<Float, Byte> wrap(final TFloatByteMap map) {
        return new TFloatByteMapDecorator(map);
    }
    
    public static Map<Float, Short> wrap(final TFloatShortMap map) {
        return new TFloatShortMapDecorator(map);
    }
    
    public static Map<Float, Character> wrap(final TFloatCharMap map) {
        return new TFloatCharMapDecorator(map);
    }
    
    public static Map<Integer, Double> wrap(final TIntDoubleMap map) {
        return new TIntDoubleMapDecorator(map);
    }
    
    public static Map<Integer, Float> wrap(final TIntFloatMap map) {
        return new TIntFloatMapDecorator(map);
    }
    
    public static Map<Integer, Integer> wrap(final TIntIntMap map) {
        return new TIntIntMapDecorator(map);
    }
    
    public static Map<Integer, Long> wrap(final TIntLongMap map) {
        return new TIntLongMapDecorator(map);
    }
    
    public static Map<Integer, Byte> wrap(final TIntByteMap map) {
        return new TIntByteMapDecorator(map);
    }
    
    public static Map<Integer, Short> wrap(final TIntShortMap map) {
        return new TIntShortMapDecorator(map);
    }
    
    public static Map<Integer, Character> wrap(final TIntCharMap map) {
        return new TIntCharMapDecorator(map);
    }
    
    public static Map<Long, Double> wrap(final TLongDoubleMap map) {
        return new TLongDoubleMapDecorator(map);
    }
    
    public static Map<Long, Float> wrap(final TLongFloatMap map) {
        return new TLongFloatMapDecorator(map);
    }
    
    public static Map<Long, Integer> wrap(final TLongIntMap map) {
        return new TLongIntMapDecorator(map);
    }
    
    public static Map<Long, Long> wrap(final TLongLongMap map) {
        return new TLongLongMapDecorator(map);
    }
    
    public static Map<Long, Byte> wrap(final TLongByteMap map) {
        return new TLongByteMapDecorator(map);
    }
    
    public static Map<Long, Short> wrap(final TLongShortMap map) {
        return new TLongShortMapDecorator(map);
    }
    
    public static Map<Long, Character> wrap(final TLongCharMap map) {
        return new TLongCharMapDecorator(map);
    }
    
    public static Map<Byte, Double> wrap(final TByteDoubleMap map) {
        return new TByteDoubleMapDecorator(map);
    }
    
    public static Map<Byte, Float> wrap(final TByteFloatMap map) {
        return new TByteFloatMapDecorator(map);
    }
    
    public static Map<Byte, Integer> wrap(final TByteIntMap map) {
        return new TByteIntMapDecorator(map);
    }
    
    public static Map<Byte, Long> wrap(final TByteLongMap map) {
        return new TByteLongMapDecorator(map);
    }
    
    public static Map<Byte, Byte> wrap(final TByteByteMap map) {
        return new TByteByteMapDecorator(map);
    }
    
    public static Map<Byte, Short> wrap(final TByteShortMap map) {
        return new TByteShortMapDecorator(map);
    }
    
    public static Map<Byte, Character> wrap(final TByteCharMap map) {
        return new TByteCharMapDecorator(map);
    }
    
    public static Map<Short, Double> wrap(final TShortDoubleMap map) {
        return new TShortDoubleMapDecorator(map);
    }
    
    public static Map<Short, Float> wrap(final TShortFloatMap map) {
        return new TShortFloatMapDecorator(map);
    }
    
    public static Map<Short, Integer> wrap(final TShortIntMap map) {
        return new TShortIntMapDecorator(map);
    }
    
    public static Map<Short, Long> wrap(final TShortLongMap map) {
        return new TShortLongMapDecorator(map);
    }
    
    public static Map<Short, Byte> wrap(final TShortByteMap map) {
        return new TShortByteMapDecorator(map);
    }
    
    public static Map<Short, Short> wrap(final TShortShortMap map) {
        return new TShortShortMapDecorator(map);
    }
    
    public static Map<Short, Character> wrap(final TShortCharMap map) {
        return new TShortCharMapDecorator(map);
    }
    
    public static Map<Character, Double> wrap(final TCharDoubleMap map) {
        return new TCharDoubleMapDecorator(map);
    }
    
    public static Map<Character, Float> wrap(final TCharFloatMap map) {
        return new TCharFloatMapDecorator(map);
    }
    
    public static Map<Character, Integer> wrap(final TCharIntMap map) {
        return new TCharIntMapDecorator(map);
    }
    
    public static Map<Character, Long> wrap(final TCharLongMap map) {
        return new TCharLongMapDecorator(map);
    }
    
    public static Map<Character, Byte> wrap(final TCharByteMap map) {
        return new TCharByteMapDecorator(map);
    }
    
    public static Map<Character, Short> wrap(final TCharShortMap map) {
        return new TCharShortMapDecorator(map);
    }
    
    public static Map<Character, Character> wrap(final TCharCharMap map) {
        return new TCharCharMapDecorator(map);
    }
    
    public static <T> Map<T, Double> wrap(final TObjectDoubleMap<T> map) {
        return (Map<T, Double>)new TObjectDoubleMapDecorator((TObjectDoubleMap<Object>)map);
    }
    
    public static <T> Map<T, Float> wrap(final TObjectFloatMap<T> map) {
        return (Map<T, Float>)new TObjectFloatMapDecorator((TObjectFloatMap<Object>)map);
    }
    
    public static <T> Map<T, Integer> wrap(final TObjectIntMap<T> map) {
        return (Map<T, Integer>)new TObjectIntMapDecorator((TObjectIntMap<Object>)map);
    }
    
    public static <T> Map<T, Long> wrap(final TObjectLongMap<T> map) {
        return (Map<T, Long>)new TObjectLongMapDecorator((TObjectLongMap<Object>)map);
    }
    
    public static <T> Map<T, Byte> wrap(final TObjectByteMap<T> map) {
        return (Map<T, Byte>)new TObjectByteMapDecorator((TObjectByteMap<Object>)map);
    }
    
    public static <T> Map<T, Short> wrap(final TObjectShortMap<T> map) {
        return (Map<T, Short>)new TObjectShortMapDecorator((TObjectShortMap<Object>)map);
    }
    
    public static <T> Map<T, Character> wrap(final TObjectCharMap<T> map) {
        return (Map<T, Character>)new TObjectCharMapDecorator((TObjectCharMap<Object>)map);
    }
    
    public static <T> Map<Double, T> wrap(final TDoubleObjectMap<T> map) {
        return (Map<Double, T>)new TDoubleObjectMapDecorator((TDoubleObjectMap<Object>)map);
    }
    
    public static <T> Map<Float, T> wrap(final TFloatObjectMap<T> map) {
        return (Map<Float, T>)new TFloatObjectMapDecorator((TFloatObjectMap<Object>)map);
    }
    
    public static <T> Map<Integer, T> wrap(final TIntObjectMap<T> map) {
        return (Map<Integer, T>)new TIntObjectMapDecorator((TIntObjectMap<Object>)map);
    }
    
    public static <T> Map<Long, T> wrap(final TLongObjectMap<T> map) {
        return (Map<Long, T>)new TLongObjectMapDecorator((TLongObjectMap<Object>)map);
    }
    
    public static <T> Map<Byte, T> wrap(final TByteObjectMap<T> map) {
        return (Map<Byte, T>)new TByteObjectMapDecorator((TByteObjectMap<Object>)map);
    }
    
    public static <T> Map<Short, T> wrap(final TShortObjectMap<T> map) {
        return (Map<Short, T>)new TShortObjectMapDecorator((TShortObjectMap<Object>)map);
    }
    
    public static <T> Map<Character, T> wrap(final TCharObjectMap<T> map) {
        return (Map<Character, T>)new TCharObjectMapDecorator((TCharObjectMap<Object>)map);
    }
    
    public static Set<Double> wrap(final TDoubleSet set) {
        return new TDoubleSetDecorator(set);
    }
    
    public static Set<Float> wrap(final TFloatSet set) {
        return new TFloatSetDecorator(set);
    }
    
    public static Set<Integer> wrap(final TIntSet set) {
        return new TIntSetDecorator(set);
    }
    
    public static Set<Long> wrap(final TLongSet set) {
        return new TLongSetDecorator(set);
    }
    
    public static Set<Byte> wrap(final TByteSet set) {
        return new TByteSetDecorator(set);
    }
    
    public static Set<Short> wrap(final TShortSet set) {
        return new TShortSetDecorator(set);
    }
    
    public static Set<Character> wrap(final TCharSet set) {
        return new TCharSetDecorator(set);
    }
    
    public static List<Double> wrap(final TDoubleList list) {
        return new TDoubleListDecorator(list);
    }
    
    public static List<Float> wrap(final TFloatList list) {
        return new TFloatListDecorator(list);
    }
    
    public static List<Integer> wrap(final TIntList list) {
        return new TIntListDecorator(list);
    }
    
    public static List<Long> wrap(final TLongList list) {
        return new TLongListDecorator(list);
    }
    
    public static List<Byte> wrap(final TByteList list) {
        return new TByteListDecorator(list);
    }
    
    public static List<Short> wrap(final TShortList list) {
        return new TShortListDecorator(list);
    }
    
    public static List<Character> wrap(final TCharList list) {
        return new TCharListDecorator(list);
    }
}
