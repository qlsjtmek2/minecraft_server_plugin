// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.data;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import think.rpgitems.lib.gnu.trove.iterator.TIntObjectIterator;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import think.rpgitems.lib.gnu.trove.map.hash.TIntObjectHashMap;

public class RPGMetadata extends TIntObjectHashMap<Object>
{
    private static final String METADATA_PREFIX = "§c§a§f§e";
    public static final int DURABILITY = 0;
    
    public static boolean hasRPGMetadata(final ItemStack item) {
        if (!item.hasItemMeta()) {
            return false;
        }
        final ItemMeta meta = item.getItemMeta();
        if (!meta.hasLore()) {
            return false;
        }
        final List<String> lore = (List<String>)meta.getLore();
        return lore.size() != 0 && lore.get(0).contains("§c§a§f§e");
    }
    
    public static RPGMetadata parseLoreline(final String lore) {
        final RPGMetadata meta = new RPGMetadata();
        final int pos = lore.indexOf("§c§a§f§e");
        if (pos == -1) {
            return meta;
        }
        final String lenStr = lore.substring(pos + "§c§a§f§e".length(), pos + "§c§a§f§e".length() + 8);
        final int length = parseShort(lenStr, 0);
        final String data = lore.substring(pos + "§c§a§f§e".length() + 8, pos + "§c§a§f§e".length() + 8 + length);
        int off = 0;
        while (off < length) {
            final int index = parseByte(data, off);
            off += 4;
            final int key = index & 0x1F;
            final int type = index >> 5;
            switch (type) {
                default: {
                    continue;
                }
                case 0: {
                    final int byteValue = parseByte(data, off);
                    off += 4;
                    ((TIntObjectHashMap<Byte>)meta).put(key, (byte)byteValue);
                    continue;
                }
                case 1: {
                    final int shortValue = parseShort(data, off);
                    off += 8;
                    ((TIntObjectHashMap<Short>)meta).put(key, (short)shortValue);
                    continue;
                }
                case 2: {
                    final int intValue = parseInt(data, off);
                    off += 16;
                    ((TIntObjectHashMap<Integer>)meta).put(key, intValue);
                    continue;
                }
                case 3: {
                    final int floatValueBits = parseInt(data, off);
                    off += 16;
                    ((TIntObjectHashMap<Float>)meta).put(key, Float.intBitsToFloat(floatValueBits));
                    continue;
                }
                case 4: {
                    final int stringLength = parseShort(data, off);
                    off += 8;
                    final byte[] bytes = new byte[stringLength];
                    for (int i = 0; i < stringLength; ++i) {
                        bytes[i] = (byte)parseByte(data, off);
                        off += 4;
                    }
                    try {
                        ((TIntObjectHashMap<String>)meta).put(key, new String(bytes, "UTF-8"));
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
            }
        }
        return meta;
    }
    
    private static int parseInt(final String iStr, final int off) {
        return parseShort(iStr, off + 0) << 16 | parseShort(iStr, off + 8);
    }
    
    private static int parseShort(final String sStr, final int off) {
        return parseByte(sStr, off + 0) << 8 | parseByte(sStr, off + 4);
    }
    
    private static int parseByte(final String bStr, final int off) {
        return Integer.parseInt(new StringBuilder().append(bStr.charAt(off + 1)).append(bStr.charAt(off + 3)).toString(), 16);
    }
    
    public String toMCString() {
        final StringBuilder out = new StringBuilder();
        out.append("§c§a§f§e");
        final TIntObjectIterator<Object> it = this.iterator();
        while (it.hasNext()) {
            it.advance();
            final int key = it.key();
            final Object value = it.value();
            int index = key & 0x1F;
            if (value instanceof Byte) {
                index |= 0x0;
                this.writeByte(out, index);
                this.writeByte(out, (int)value);
            }
            else if (value instanceof Short) {
                index |= 0x20;
                this.writeByte(out, index);
                this.writeShort(out, (int)value);
            }
            else if (value instanceof Integer) {
                index |= 0x40;
                this.writeByte(out, index);
                this.writeInt(out, (int)value);
            }
            else if (value instanceof Float) {
                index |= 0x60;
                this.writeByte(out, index);
                this.writeInt(out, Float.floatToIntBits((float)value));
            }
            else {
                if (!(value instanceof String)) {
                    continue;
                }
                index |= 0x80;
                this.writeByte(out, index);
                this.writeString(out, (String)value);
            }
        }
        final int size = out.length() - "§c§a§f§e".length();
        this.insertShort(out, "§c§a§f§e".length(), size);
        return out.toString();
    }
    
    private void writeString(final StringBuilder out, final String value) {
        try {
            final byte[] data = value.getBytes("UTF-8");
            this.writeShort(out, data.length);
            byte[] array;
            for (int length = (array = data).length, i = 0; i < length; ++i) {
                final byte b = array[i];
                this.writeByte(out, b);
            }
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    private void writeByte(final StringBuilder out, final int b) {
        final String hex = Integer.toString(b, 16);
        out.append('§');
        out.append((hex.length() == 1) ? "0" : hex.charAt(0));
        out.append('§');
        out.append(hex.charAt(hex.length() - 1));
    }
    
    private void writeShort(final StringBuilder out, final int s) {
        this.writeByte(out, s >> 8);
        this.writeByte(out, s & 0xFF);
    }
    
    private void writeInt(final StringBuilder out, final int s) {
        this.writeShort(out, s >> 16);
        this.writeShort(out, s & 0xFFFF);
    }
    
    private void insertByte(final StringBuilder out, final int offset, final int b) {
        final String hex = Integer.toString(b, 16);
        out.insert(offset, '§');
        out.insert(offset + 1, (hex.length() == 1) ? "0" : hex.charAt(0));
        out.insert(offset + 2, '§');
        out.insert(offset + 3, hex.charAt(hex.length() - 1));
    }
    
    private void insertShort(final StringBuilder out, final int offset, final int s) {
        this.insertByte(out, offset, s >> 8);
        this.insertByte(out, offset + 4, s & 0xFF);
    }
    
    private void insertInt(final StringBuilder out, final int offset, final int s) {
        this.insertShort(out, offset, s >> 16);
        this.insertShort(out, offset + 8, s & 0xFFFF);
    }
}
