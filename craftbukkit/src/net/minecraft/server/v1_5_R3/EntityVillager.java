// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public class EntityVillager extends EntityAgeable implements NPC, IMerchant
{
    private int profession;
    private boolean f;
    private boolean g;
    Village village;
    private EntityHuman h;
    private MerchantRecipeList i;
    private int j;
    private boolean bK;
    private int bL;
    private String bM;
    private boolean bN;
    private float bO;
    private static final Map bP;
    private static final Map bQ;
    
    public EntityVillager(final World world) {
        this(world, 0);
    }
    
    public EntityVillager(final World world, final int profession) {
        super(world);
        this.profession = 0;
        this.f = false;
        this.g = false;
        this.village = null;
        this.setProfession(profession);
        this.texture = "/mob/villager/villager.png";
        this.bI = 0.5f;
        this.a(0.6f, 1.8f);
        this.getNavigation().b(true);
        this.getNavigation().a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalAvoidPlayer(this, EntityZombie.class, 8.0f, 0.3f, 0.35f));
        this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
        this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.3f));
        this.goalSelector.a(6, new PathfinderGoalMakeLove(this));
        this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));
        this.goalSelector.a(8, new PathfinderGoalPlay(this, 0.32f));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0f, 1.0f));
        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityVillager.class, 5.0f, 0.02f));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.3f));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityLiving.class, 8.0f));
    }
    
    public boolean bh() {
        return true;
    }
    
    protected void bp() {
        final int profession = this.profession - 1;
        this.profession = profession;
        if (profession <= 0) {
            this.world.villages.a(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ));
            this.profession = 70 + this.random.nextInt(50);
            this.village = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
            if (this.village == null) {
                this.aO();
            }
            else {
                final ChunkCoordinates center = this.village.getCenter();
                this.b(center.x, center.y, center.z, (int)(this.village.getSize() * 0.6f));
                if (this.bN) {
                    this.bN = false;
                    this.village.b(5);
                }
            }
        }
        if (!this.p() && this.j > 0) {
            --this.j;
            if (this.j <= 0) {
                if (this.bK) {
                    if (this.i.size() > 1) {
                        for (final MerchantRecipe merchantRecipe : this.i) {
                            if (merchantRecipe.g()) {
                                merchantRecipe.a(this.random.nextInt(6) + this.random.nextInt(6) + 2);
                            }
                        }
                    }
                    this.t(1);
                    this.bK = false;
                    if (this.village != null && this.bM != null) {
                        this.world.broadcastEntityEffect(this, (byte)14);
                        this.village.a(this.bM, 1);
                    }
                }
                this.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 200, 0));
            }
        }
        super.bp();
    }
    
    public boolean a_(final EntityHuman entityhuman) {
        final ItemStack itemInHand = entityhuman.inventory.getItemInHand();
        if ((itemInHand == null || itemInHand.id != Item.MONSTER_EGG.id) && this.isAlive() && !this.p() && !this.isBaby()) {
            if (!this.world.isStatic) {
                this.a(entityhuman);
                entityhuman.openTrade(this, this.getCustomName());
            }
            return true;
        }
        return super.a_(entityhuman);
    }
    
    protected void a() {
        super.a();
        this.datawatcher.a(16, (Object)0);
    }
    
    public int getMaxHealth() {
        return 20;
    }
    
    public void b(final NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("Profession", this.getProfession());
        nbttagcompound.setInt("Riches", this.bL);
        if (this.i != null) {
            nbttagcompound.setCompound("Offers", this.i.a());
        }
    }
    
    public void a(final NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setProfession(nbttagcompound.getInt("Profession"));
        this.bL = nbttagcompound.getInt("Riches");
        if (nbttagcompound.hasKey("Offers")) {
            this.i = new MerchantRecipeList(nbttagcompound.getCompound("Offers"));
        }
    }
    
    protected boolean isTypeNotPersistent() {
        return false;
    }
    
    protected String bb() {
        return "mob.villager.default";
    }
    
    protected String bc() {
        return "mob.villager.defaulthurt";
    }
    
    protected String bd() {
        return "mob.villager.defaultdeath";
    }
    
    public void setProfession(final int n) {
        this.datawatcher.watch(16, n);
    }
    
    public int getProfession() {
        return this.datawatcher.getInt(16);
    }
    
    public boolean n() {
        return this.f;
    }
    
    public void i(final boolean f) {
        this.f = f;
    }
    
    public void j(final boolean g) {
        this.g = g;
    }
    
    public boolean o() {
        return this.g;
    }
    
    public void c(final EntityLiving entityLiving) {
        super.c(entityLiving);
        if (this.village != null && entityLiving != null) {
            this.village.a(entityLiving);
            if (entityLiving instanceof EntityHuman) {
                int i = -1;
                if (this.isBaby()) {
                    i = -3;
                }
                this.village.a(((EntityHuman)entityLiving).getName(), i);
                if (this.isAlive()) {
                    this.world.broadcastEntityEffect(this, (byte)13);
                }
            }
        }
    }
    
    public void die(final DamageSource damagesource) {
        if (this.village != null) {
            final Entity entity = damagesource.getEntity();
            if (entity != null) {
                if (entity instanceof EntityHuman) {
                    this.village.a(((EntityHuman)entity).getName(), -2);
                }
                else if (entity instanceof IMonster) {
                    this.village.h();
                }
            }
            else if (entity == null && this.world.findNearbyPlayer(this, 16.0) != null) {
                this.village.h();
            }
        }
        super.die(damagesource);
    }
    
    public void a(final EntityHuman h) {
        this.h = h;
    }
    
    public EntityHuman m_() {
        return this.h;
    }
    
    public boolean p() {
        return this.h != null;
    }
    
    public void a(final MerchantRecipe merchantRecipe) {
        merchantRecipe.f();
        if (merchantRecipe.a(this.i.get(this.i.size() - 1))) {
            this.j = 40;
            this.bK = true;
            if (this.h != null) {
                this.bM = this.h.getName();
            }
            else {
                this.bM = null;
            }
        }
        if (merchantRecipe.getBuyItem1().id == Item.EMERALD.id) {
            this.bL += merchantRecipe.getBuyItem1().count;
        }
    }
    
    public MerchantRecipeList getOffers(final EntityHuman entityHuman) {
        if (this.i == null) {
            this.t(1);
        }
        return this.i;
    }
    
    private float j(final float n) {
        final float n2 = n + this.bO;
        if (n2 > 0.9f) {
            return 0.9f - (n2 - 0.9f);
        }
        return n2;
    }
    
    private void t(final int n) {
        if (this.i != null) {
            this.bO = MathHelper.c(this.i.size()) * 0.2f;
        }
        else {
            this.bO = 0.0f;
        }
        final MerchantRecipeList list = new MerchantRecipeList();
        switch (this.getProfession()) {
            case 0: {
                a(list, Item.WHEAT.id, this.random, this.j(0.9f));
                a(list, Block.WOOL.id, this.random, this.j(0.5f));
                a(list, Item.RAW_CHICKEN.id, this.random, this.j(0.5f));
                a(list, Item.COOKED_FISH.id, this.random, this.j(0.4f));
                b(list, Item.BREAD.id, this.random, this.j(0.9f));
                b(list, Item.MELON.id, this.random, this.j(0.3f));
                b(list, Item.APPLE.id, this.random, this.j(0.3f));
                b(list, Item.COOKIE.id, this.random, this.j(0.3f));
                b(list, Item.SHEARS.id, this.random, this.j(0.3f));
                b(list, Item.FLINT_AND_STEEL.id, this.random, this.j(0.3f));
                b(list, Item.COOKED_CHICKEN.id, this.random, this.j(0.3f));
                b(list, Item.ARROW.id, this.random, this.j(0.5f));
                if (this.random.nextFloat() < this.j(0.5f)) {
                    list.add(new MerchantRecipe(new ItemStack(Block.GRAVEL, 10), new ItemStack(Item.EMERALD), new ItemStack(Item.FLINT.id, 4 + this.random.nextInt(2), 0)));
                    break;
                }
                break;
            }
            case 4: {
                a(list, Item.COAL.id, this.random, this.j(0.7f));
                a(list, Item.PORK.id, this.random, this.j(0.5f));
                a(list, Item.RAW_BEEF.id, this.random, this.j(0.5f));
                b(list, Item.SADDLE.id, this.random, this.j(0.1f));
                b(list, Item.LEATHER_CHESTPLATE.id, this.random, this.j(0.3f));
                b(list, Item.LEATHER_BOOTS.id, this.random, this.j(0.3f));
                b(list, Item.LEATHER_HELMET.id, this.random, this.j(0.3f));
                b(list, Item.LEATHER_LEGGINGS.id, this.random, this.j(0.3f));
                b(list, Item.GRILLED_PORK.id, this.random, this.j(0.3f));
                b(list, Item.COOKED_BEEF.id, this.random, this.j(0.3f));
                break;
            }
            case 3: {
                a(list, Item.COAL.id, this.random, this.j(0.7f));
                a(list, Item.IRON_INGOT.id, this.random, this.j(0.5f));
                a(list, Item.GOLD_INGOT.id, this.random, this.j(0.5f));
                a(list, Item.DIAMOND.id, this.random, this.j(0.5f));
                b(list, Item.IRON_SWORD.id, this.random, this.j(0.5f));
                b(list, Item.DIAMOND_SWORD.id, this.random, this.j(0.5f));
                b(list, Item.IRON_AXE.id, this.random, this.j(0.3f));
                b(list, Item.DIAMOND_AXE.id, this.random, this.j(0.3f));
                b(list, Item.IRON_PICKAXE.id, this.random, this.j(0.5f));
                b(list, Item.DIAMOND_PICKAXE.id, this.random, this.j(0.5f));
                b(list, Item.IRON_SPADE.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_SPADE.id, this.random, this.j(0.2f));
                b(list, Item.IRON_HOE.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_HOE.id, this.random, this.j(0.2f));
                b(list, Item.IRON_BOOTS.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_BOOTS.id, this.random, this.j(0.2f));
                b(list, Item.IRON_HELMET.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_HELMET.id, this.random, this.j(0.2f));
                b(list, Item.IRON_CHESTPLATE.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_CHESTPLATE.id, this.random, this.j(0.2f));
                b(list, Item.IRON_LEGGINGS.id, this.random, this.j(0.2f));
                b(list, Item.DIAMOND_LEGGINGS.id, this.random, this.j(0.2f));
                b(list, Item.CHAINMAIL_BOOTS.id, this.random, this.j(0.1f));
                b(list, Item.CHAINMAIL_HELMET.id, this.random, this.j(0.1f));
                b(list, Item.CHAINMAIL_CHESTPLATE.id, this.random, this.j(0.1f));
                b(list, Item.CHAINMAIL_LEGGINGS.id, this.random, this.j(0.1f));
                break;
            }
            case 1: {
                a(list, Item.PAPER.id, this.random, this.j(0.8f));
                a(list, Item.BOOK.id, this.random, this.j(0.8f));
                a(list, Item.WRITTEN_BOOK.id, this.random, this.j(0.3f));
                b(list, Block.BOOKSHELF.id, this.random, this.j(0.8f));
                b(list, Block.GLASS.id, this.random, this.j(0.2f));
                b(list, Item.COMPASS.id, this.random, this.j(0.2f));
                b(list, Item.WATCH.id, this.random, this.j(0.2f));
                if (this.random.nextFloat() < this.j(0.07f)) {
                    final Enchantment enchantment = Enchantment.c[this.random.nextInt(Enchantment.c.length)];
                    final int nextInt = MathHelper.nextInt(this.random, enchantment.getStartLevel(), enchantment.getMaxLevel());
                    list.add(new MerchantRecipe(new ItemStack(Item.BOOK), new ItemStack(Item.EMERALD, 2 + this.random.nextInt(5 + nextInt * 10) + 3 * nextInt), Item.ENCHANTED_BOOK.a(new EnchantmentInstance(enchantment, nextInt))));
                    break;
                }
                break;
            }
            case 2: {
                b(list, Item.EYE_OF_ENDER.id, this.random, this.j(0.3f));
                b(list, Item.EXP_BOTTLE.id, this.random, this.j(0.2f));
                b(list, Item.REDSTONE.id, this.random, this.j(0.4f));
                b(list, Block.GLOWSTONE.id, this.random, this.j(0.3f));
                for (final int n2 : new int[] { Item.IRON_SWORD.id, Item.DIAMOND_SWORD.id, Item.IRON_CHESTPLATE.id, Item.DIAMOND_CHESTPLATE.id, Item.IRON_AXE.id, Item.DIAMOND_AXE.id, Item.IRON_PICKAXE.id, Item.DIAMOND_PICKAXE.id }) {
                    if (this.random.nextFloat() < this.j(0.05f)) {
                        list.add(new MerchantRecipe(new ItemStack(n2, 1, 0), new ItemStack(Item.EMERALD, 2 + this.random.nextInt(3), 0), EnchantmentManager.a(this.random, new ItemStack(n2, 1, 0), 5 + this.random.nextInt(15))));
                    }
                }
                break;
            }
        }
        if (list.isEmpty()) {
            a(list, Item.GOLD_INGOT.id, this.random, 1.0f);
        }
        Collections.shuffle(list);
        if (this.i == null) {
            this.i = new MerchantRecipeList();
        }
        for (int n3 = 0; n3 < n && n3 < list.size(); ++n3) {
            this.i.a(list.get(n3));
        }
    }
    
    private static void a(final MerchantRecipeList list, final int n, final Random random, final float n2) {
        if (random.nextFloat() < n2) {
            list.add(new MerchantRecipe(a(n, random), Item.EMERALD));
        }
    }
    
    private static ItemStack a(final int i, final Random random) {
        return new ItemStack(i, b(i, random), 0);
    }
    
    private static int b(final int n, final Random random) {
        final Tuple tuple = EntityVillager.bP.get(n);
        if (tuple == null) {
            return 1;
        }
        if ((int)tuple.a() >= (int)tuple.b()) {
            return (int)tuple.a();
        }
        return (int)tuple.a() + random.nextInt((int)tuple.b() - (int)tuple.a());
    }
    
    private static void b(final MerchantRecipeList list, final int n, final Random random, final float n2) {
        if (random.nextFloat() < n2) {
            final int c = c(n, random);
            ItemStack itemStack;
            ItemStack itemStack2;
            if (c < 0) {
                itemStack = new ItemStack(Item.EMERALD.id, 1, 0);
                itemStack2 = new ItemStack(n, -c, 0);
            }
            else {
                itemStack = new ItemStack(Item.EMERALD.id, c, 0);
                itemStack2 = new ItemStack(n, 1, 0);
            }
            list.add(new MerchantRecipe(itemStack, itemStack2));
        }
    }
    
    private static int c(final int n, final Random random) {
        final Tuple tuple = EntityVillager.bQ.get(n);
        if (tuple == null) {
            return 1;
        }
        if ((int)tuple.a() >= (int)tuple.b()) {
            return (int)tuple.a();
        }
        return (int)tuple.a() + random.nextInt((int)tuple.b() - (int)tuple.a());
    }
    
    public void bJ() {
        this.setProfession(this.world.random.nextInt(5));
    }
    
    public void q() {
        this.bN = true;
    }
    
    public EntityVillager b(final EntityAgeable entityAgeable) {
        final EntityVillager entityVillager = new EntityVillager(this.world);
        entityVillager.bJ();
        return entityVillager;
    }
    
    static {
        bP = new HashMap();
        bQ = new HashMap();
        EntityVillager.bP.put(Item.COAL.id, new Tuple(16, 24));
        EntityVillager.bP.put(Item.IRON_INGOT.id, new Tuple(8, 10));
        EntityVillager.bP.put(Item.GOLD_INGOT.id, new Tuple(8, 10));
        EntityVillager.bP.put(Item.DIAMOND.id, new Tuple(4, 6));
        EntityVillager.bP.put(Item.PAPER.id, new Tuple(24, 36));
        EntityVillager.bP.put(Item.BOOK.id, new Tuple(11, 13));
        EntityVillager.bP.put(Item.WRITTEN_BOOK.id, new Tuple(1, 1));
        EntityVillager.bP.put(Item.ENDER_PEARL.id, new Tuple(3, 4));
        EntityVillager.bP.put(Item.EYE_OF_ENDER.id, new Tuple(2, 3));
        EntityVillager.bP.put(Item.PORK.id, new Tuple(14, 18));
        EntityVillager.bP.put(Item.RAW_BEEF.id, new Tuple(14, 18));
        EntityVillager.bP.put(Item.RAW_CHICKEN.id, new Tuple(14, 18));
        EntityVillager.bP.put(Item.COOKED_FISH.id, new Tuple(9, 13));
        EntityVillager.bP.put(Item.SEEDS.id, new Tuple(34, 48));
        EntityVillager.bP.put(Item.MELON_SEEDS.id, new Tuple(30, 38));
        EntityVillager.bP.put(Item.PUMPKIN_SEEDS.id, new Tuple(30, 38));
        EntityVillager.bP.put(Item.WHEAT.id, new Tuple(18, 22));
        EntityVillager.bP.put(Block.WOOL.id, new Tuple(14, 22));
        EntityVillager.bP.put(Item.ROTTEN_FLESH.id, new Tuple(36, 64));
        EntityVillager.bQ.put(Item.FLINT_AND_STEEL.id, new Tuple(3, 4));
        EntityVillager.bQ.put(Item.SHEARS.id, new Tuple(3, 4));
        EntityVillager.bQ.put(Item.IRON_SWORD.id, new Tuple(7, 11));
        EntityVillager.bQ.put(Item.DIAMOND_SWORD.id, new Tuple(12, 14));
        EntityVillager.bQ.put(Item.IRON_AXE.id, new Tuple(6, 8));
        EntityVillager.bQ.put(Item.DIAMOND_AXE.id, new Tuple(9, 12));
        EntityVillager.bQ.put(Item.IRON_PICKAXE.id, new Tuple(7, 9));
        EntityVillager.bQ.put(Item.DIAMOND_PICKAXE.id, new Tuple(10, 12));
        EntityVillager.bQ.put(Item.IRON_SPADE.id, new Tuple(4, 6));
        EntityVillager.bQ.put(Item.DIAMOND_SPADE.id, new Tuple(7, 8));
        EntityVillager.bQ.put(Item.IRON_HOE.id, new Tuple(4, 6));
        EntityVillager.bQ.put(Item.DIAMOND_HOE.id, new Tuple(7, 8));
        EntityVillager.bQ.put(Item.IRON_BOOTS.id, new Tuple(4, 6));
        EntityVillager.bQ.put(Item.DIAMOND_BOOTS.id, new Tuple(7, 8));
        EntityVillager.bQ.put(Item.IRON_HELMET.id, new Tuple(4, 6));
        EntityVillager.bQ.put(Item.DIAMOND_HELMET.id, new Tuple(7, 8));
        EntityVillager.bQ.put(Item.IRON_CHESTPLATE.id, new Tuple(10, 14));
        EntityVillager.bQ.put(Item.DIAMOND_CHESTPLATE.id, new Tuple(16, 19));
        EntityVillager.bQ.put(Item.IRON_LEGGINGS.id, new Tuple(8, 10));
        EntityVillager.bQ.put(Item.DIAMOND_LEGGINGS.id, new Tuple(11, 14));
        EntityVillager.bQ.put(Item.CHAINMAIL_BOOTS.id, new Tuple(5, 7));
        EntityVillager.bQ.put(Item.CHAINMAIL_HELMET.id, new Tuple(5, 7));
        EntityVillager.bQ.put(Item.CHAINMAIL_CHESTPLATE.id, new Tuple(11, 15));
        EntityVillager.bQ.put(Item.CHAINMAIL_LEGGINGS.id, new Tuple(9, 11));
        EntityVillager.bQ.put(Item.BREAD.id, new Tuple(-4, -2));
        EntityVillager.bQ.put(Item.MELON.id, new Tuple(-8, -4));
        EntityVillager.bQ.put(Item.APPLE.id, new Tuple(-8, -4));
        EntityVillager.bQ.put(Item.COOKIE.id, new Tuple(-10, -7));
        EntityVillager.bQ.put(Block.GLASS.id, new Tuple(-5, -3));
        EntityVillager.bQ.put(Block.BOOKSHELF.id, new Tuple(3, 4));
        EntityVillager.bQ.put(Item.LEATHER_CHESTPLATE.id, new Tuple(4, 5));
        EntityVillager.bQ.put(Item.LEATHER_BOOTS.id, new Tuple(2, 4));
        EntityVillager.bQ.put(Item.LEATHER_HELMET.id, new Tuple(2, 4));
        EntityVillager.bQ.put(Item.LEATHER_LEGGINGS.id, new Tuple(2, 4));
        EntityVillager.bQ.put(Item.SADDLE.id, new Tuple(6, 8));
        EntityVillager.bQ.put(Item.EXP_BOTTLE.id, new Tuple(-4, -1));
        EntityVillager.bQ.put(Item.REDSTONE.id, new Tuple(-4, -1));
        EntityVillager.bQ.put(Item.COMPASS.id, new Tuple(10, 12));
        EntityVillager.bQ.put(Item.WATCH.id, new Tuple(10, 12));
        EntityVillager.bQ.put(Block.GLOWSTONE.id, new Tuple(-3, -1));
        EntityVillager.bQ.put(Item.GRILLED_PORK.id, new Tuple(-7, -5));
        EntityVillager.bQ.put(Item.COOKED_BEEF.id, new Tuple(-7, -5));
        EntityVillager.bQ.put(Item.COOKED_CHICKEN.id, new Tuple(-8, -6));
        EntityVillager.bQ.put(Item.EYE_OF_ENDER.id, new Tuple(7, 11));
        EntityVillager.bQ.put(Item.ARROW.id, new Tuple(-12, -8));
    }
}
