// 
// Decompiled by Procyon v0.5.30
// 

package GoldenMine.ChestShop;

import org.bukkit.block.Sign;
import java.util.regex.Pattern;

public class ChestShopUtil
{
    public static final byte NAME_LINE = 0;
    public static final byte QUANTITY_LINE = 1;
    public static final byte PRICE_LINE = 2;
    public static final byte ITEM_LINE = 3;
    public static final Pattern[] SHOP_SIGN_PATTERN;
    
    public static boolean isValid(final Sign sign) {
        return isValid(sign.getLines());
    }
    
    public static boolean isValid(final String[] line) {
        return isValidPreparedSign(line) && (line[2].toUpperCase().contains("B") || line[2].toUpperCase().contains("S")) && !line[0].isEmpty();
    }
    
    public static boolean isValidPreparedSign(final String[] lines) {
        for (int i = 0; i < 4; ++i) {
            if (!ChestShopUtil.SHOP_SIGN_PATTERN[i].matcher(lines[i]).matches()) {
                return false;
            }
        }
        return lines[2].indexOf(58) == lines[2].lastIndexOf(58);
    }
    
    static {
        SHOP_SIGN_PATTERN = new Pattern[] { Pattern.compile("^[\\w -.]*$"), Pattern.compile("^[1-9][0-9]*$"), Pattern.compile("(?i)^[\\d.bs(free) :]+$"), Pattern.compile("^[\\w #:-]+$") };
    }
}
