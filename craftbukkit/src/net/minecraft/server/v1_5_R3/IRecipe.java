// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;
import org.bukkit.inventory.Recipe;

public interface IRecipe
{
    boolean a(final InventoryCrafting p0, final World p1);
    
    ItemStack a(final InventoryCrafting p0);
    
    int a();
    
    ItemStack b();
    
    Recipe toBukkitRecipe();
    
    List<ItemStack> getIngredients();
}
