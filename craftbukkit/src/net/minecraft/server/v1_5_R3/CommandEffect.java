// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandEffect extends CommandAbstract
{
    public String c() {
        return "effect";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.effect.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length < 2) {
            throw new ExceptionUsage("commands.effect.usage", new Object[0]);
        }
        final EntityPlayer c = CommandAbstract.c(commandListener, array[0]);
        final int a = CommandAbstract.a(commandListener, array[1], 1);
        int n = 600;
        int a2 = 30;
        int a3 = 0;
        if (a < 0 || a >= MobEffectList.byId.length || MobEffectList.byId[a] == null) {
            throw new ExceptionInvalidNumber("commands.effect.notFound", new Object[] { a });
        }
        if (array.length >= 3) {
            a2 = CommandAbstract.a(commandListener, array[2], 0, 1000000);
            if (MobEffectList.byId[a].isInstant()) {
                n = a2;
            }
            else {
                n = a2 * 20;
            }
        }
        else if (MobEffectList.byId[a].isInstant()) {
            n = 1;
        }
        if (array.length >= 4) {
            a3 = CommandAbstract.a(commandListener, array[3], 0, 255);
        }
        if (a2 == 0) {
            if (!c.hasEffect(a)) {
                throw new CommandException("commands.effect.failure.notActive", new Object[] { LocaleI18n.get(MobEffectList.byId[a].a()), c.getLocalizedName() });
            }
            c.o(a);
            CommandAbstract.a(commandListener, "commands.effect.success.removed", LocaleI18n.get(MobEffectList.byId[a].a()), c.getLocalizedName());
        }
        else {
            final MobEffect mobeffect = new MobEffect(a, n, a3);
            c.addEffect(mobeffect);
            CommandAbstract.a(commandListener, "commands.effect.success", LocaleI18n.get(mobeffect.f()), a, a3, c.getLocalizedName(), a2);
        }
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
