// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandEnchant extends CommandAbstract
{
    public String c() {
        return "enchant";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.enchant.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length < 2) {
            throw new ExceptionUsage("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayer c = CommandAbstract.c(commandListener, array[0]);
        final int a = CommandAbstract.a(commandListener, array[1], 0, Enchantment.byId.length - 1);
        int a2 = 1;
        final ItemStack cd = c.cd();
        if (cd == null) {
            CommandAbstract.a(commandListener, "commands.enchant.noItem", new Object[0]);
            return;
        }
        final Enchantment enchantment = Enchantment.byId[a];
        if (enchantment == null) {
            throw new ExceptionInvalidNumber("commands.enchant.notFound", new Object[] { a });
        }
        if (!enchantment.canEnchant(cd)) {
            CommandAbstract.a(commandListener, "commands.enchant.cantEnchant", new Object[0]);
            return;
        }
        if (array.length >= 3) {
            a2 = CommandAbstract.a(commandListener, array[2], enchantment.getStartLevel(), enchantment.getMaxLevel());
        }
        if (cd.hasTag()) {
            final NBTTagList enchantments = cd.getEnchantments();
            if (enchantments != null) {
                for (int i = 0; i < enchantments.size(); ++i) {
                    final short short1 = ((NBTTagCompound)enchantments.get(i)).getShort("id");
                    if (Enchantment.byId[short1] != null) {
                        final Enchantment enchantment2 = Enchantment.byId[short1];
                        if (!enchantment2.a(enchantment)) {
                            CommandAbstract.a(commandListener, "commands.enchant.cantCombine", enchantment.c(a2), enchantment2.c(((NBTTagCompound)enchantments.get(i)).getShort("lvl")));
                            return;
                        }
                    }
                }
            }
        }
        cd.addEnchantment(enchantment, a2);
        CommandAbstract.a(commandListener, "commands.enchant.success", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, this.d());
        }
        return null;
    }
    
    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 0;
    }
}
