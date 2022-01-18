// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.lang.reflect.Method;

public abstract class ServerPlayerBase
{
    protected final EntityPlayer player;
    private final ServerPlayerAPI playerAPI;
    private Method[] methods;
    
    public ServerPlayerBase(final ServerPlayerAPI playerAPI) {
        this.playerAPI = playerAPI;
        this.player = playerAPI.player;
    }
    
    public void beforeBaseAttach(final boolean b) {
    }
    
    public void afterBaseAttach(final boolean b) {
    }
    
    public void beforeLocalConstructing(final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
    }
    
    public void afterLocalConstructing(final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
    }
    
    public void beforeBaseDetach(final boolean b) {
    }
    
    public void afterBaseDetach(final boolean b) {
    }
    
    public Object dynamic(final String s, final Object[] array) {
        return this.playerAPI.dynamicOverwritten(s, array, this);
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    public void beforeAddExhaustion(final float n) {
    }
    
    public void addExhaustion(final float n) {
        final ServerPlayerBase getOverwrittenAddExhaustion = this.playerAPI.GetOverwrittenAddExhaustion(this);
        if (getOverwrittenAddExhaustion == null) {
            this.player.localAddExhaustion(n);
        }
        else if (getOverwrittenAddExhaustion != this) {
            getOverwrittenAddExhaustion.addExhaustion(n);
        }
    }
    
    public void afterAddExhaustion(final float n) {
    }
    
    public void beforeAddExperience(final int n) {
    }
    
    public void addExperience(final int n) {
        final ServerPlayerBase getOverwrittenAddExperience = this.playerAPI.GetOverwrittenAddExperience(this);
        if (getOverwrittenAddExperience == null) {
            this.player.localAddExperience(n);
        }
        else if (getOverwrittenAddExperience != this) {
            getOverwrittenAddExperience.addExperience(n);
        }
    }
    
    public void afterAddExperience(final int n) {
    }
    
    public void beforeAddExperienceLevel(final int n) {
    }
    
    public void addExperienceLevel(final int n) {
        final ServerPlayerBase getOverwrittenAddExperienceLevel = this.playerAPI.GetOverwrittenAddExperienceLevel(this);
        if (getOverwrittenAddExperienceLevel == null) {
            this.player.localAddExperienceLevel(n);
        }
        else if (getOverwrittenAddExperienceLevel != this) {
            getOverwrittenAddExperienceLevel.addExperienceLevel(n);
        }
    }
    
    public void afterAddExperienceLevel(final int n) {
    }
    
    public void beforeAddMovementStat(final double n, final double n2, final double n3) {
    }
    
    public void addMovementStat(final double n, final double n2, final double n3) {
        final ServerPlayerBase getOverwrittenAddMovementStat = this.playerAPI.GetOverwrittenAddMovementStat(this);
        if (getOverwrittenAddMovementStat == null) {
            this.player.localAddMovementStat(n, n2, n3);
        }
        else if (getOverwrittenAddMovementStat != this) {
            getOverwrittenAddMovementStat.addMovementStat(n, n2, n3);
        }
    }
    
    public void afterAddMovementStat(final double n, final double n2, final double n3) {
    }
    
    public void beforeAttackEntityFrom(final DamageSource damageSource, final int n) {
    }
    
    public boolean attackEntityFrom(final DamageSource damageSource, final int n) {
        final ServerPlayerBase getOverwrittenAttackEntity = this.playerAPI.GetOverwrittenAttackEntityFrom(this);
        boolean localAttackEntity;
        if (getOverwrittenAttackEntity == null) {
            localAttackEntity = this.player.localAttackEntityFrom(damageSource, n);
        }
        else {
            localAttackEntity = (getOverwrittenAttackEntity != this && getOverwrittenAttackEntity.attackEntityFrom(damageSource, n));
        }
        return localAttackEntity;
    }
    
    public void afterAttackEntityFrom(final DamageSource damageSource, final int n) {
    }
    
    public void beforeAttackTargetEntityWithCurrentItem(final Entity entity) {
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity entity) {
        final ServerPlayerBase getOverwrittenAttackTargetEntityWithCurrentItem = this.playerAPI.GetOverwrittenAttackTargetEntityWithCurrentItem(this);
        if (getOverwrittenAttackTargetEntityWithCurrentItem == null) {
            this.player.localAttackTargetEntityWithCurrentItem(entity);
        }
        else if (getOverwrittenAttackTargetEntityWithCurrentItem != this) {
            getOverwrittenAttackTargetEntityWithCurrentItem.attackTargetEntityWithCurrentItem(entity);
        }
    }
    
    public void afterAttackTargetEntityWithCurrentItem(final Entity entity) {
    }
    
    public void beforeCanHarvestBlock(final Block block) {
    }
    
    public boolean canHarvestBlock(final Block block) {
        final ServerPlayerBase getOverwrittenCanHarvestBlock = this.playerAPI.GetOverwrittenCanHarvestBlock(this);
        boolean localCanHarvestBlock;
        if (getOverwrittenCanHarvestBlock == null) {
            localCanHarvestBlock = this.player.localCanHarvestBlock(block);
        }
        else {
            localCanHarvestBlock = (getOverwrittenCanHarvestBlock != this && getOverwrittenCanHarvestBlock.canHarvestBlock(block));
        }
        return localCanHarvestBlock;
    }
    
    public void afterCanHarvestBlock(final Block block) {
    }
    
    public void beforeCanPlayerEdit(final int n, final int n2, final int n3, final int n4, final ItemStack itemStack) {
    }
    
    public boolean canPlayerEdit(final int n, final int n2, final int n3, final int n4, final ItemStack itemStack) {
        final ServerPlayerBase getOverwrittenCanPlayerEdit = this.playerAPI.GetOverwrittenCanPlayerEdit(this);
        boolean localCanPlayerEdit;
        if (getOverwrittenCanPlayerEdit == null) {
            localCanPlayerEdit = this.player.localCanPlayerEdit(n, n2, n3, n4, itemStack);
        }
        else {
            localCanPlayerEdit = (getOverwrittenCanPlayerEdit != this && getOverwrittenCanPlayerEdit.canPlayerEdit(n, n2, n3, n4, itemStack));
        }
        return localCanPlayerEdit;
    }
    
    public void afterCanPlayerEdit(final int n, final int n2, final int n3, final int n4, final ItemStack itemStack) {
    }
    
    public void beforeCanTriggerWalking() {
    }
    
    public boolean canTriggerWalking() {
        final ServerPlayerBase getOverwrittenCanTriggerWalking = this.playerAPI.GetOverwrittenCanTriggerWalking(this);
        boolean localCanTriggerWalking;
        if (getOverwrittenCanTriggerWalking == null) {
            localCanTriggerWalking = this.player.localCanTriggerWalking();
        }
        else {
            localCanTriggerWalking = (getOverwrittenCanTriggerWalking != this && getOverwrittenCanTriggerWalking.canTriggerWalking());
        }
        return localCanTriggerWalking;
    }
    
    public void afterCanTriggerWalking() {
    }
    
    public void beforeClonePlayer(final EntityHuman entityHuman, final boolean b) {
    }
    
    public void clonePlayer(final EntityHuman entityHuman, final boolean b) {
        final ServerPlayerBase getOverwrittenClonePlayer = this.playerAPI.GetOverwrittenClonePlayer(this);
        if (getOverwrittenClonePlayer == null) {
            this.player.localClonePlayer(entityHuman, b);
        }
        else if (getOverwrittenClonePlayer != this) {
            getOverwrittenClonePlayer.clonePlayer(entityHuman, b);
        }
    }
    
    public void afterClonePlayer(final EntityHuman entityHuman, final boolean b) {
    }
    
    public void beforeDamageEntity(final DamageSource damageSource, final int n) {
    }
    
    public void damageEntity(final DamageSource damageSource, final int n) {
        final ServerPlayerBase getOverwrittenDamageEntity = this.playerAPI.GetOverwrittenDamageEntity(this);
        if (getOverwrittenDamageEntity == null) {
            this.player.localDamageEntity(damageSource, n);
        }
        else if (getOverwrittenDamageEntity != this) {
            getOverwrittenDamageEntity.damageEntity(damageSource, n);
        }
    }
    
    public void afterDamageEntity(final DamageSource damageSource, final int n) {
    }
    
    public void beforeDisplayGUIChest(final IInventory inventory) {
    }
    
    public void displayGUIChest(final IInventory inventory) {
        final ServerPlayerBase getOverwrittenDisplayGUIChest = this.playerAPI.GetOverwrittenDisplayGUIChest(this);
        if (getOverwrittenDisplayGUIChest == null) {
            this.player.localDisplayGUIChest(inventory);
        }
        else if (getOverwrittenDisplayGUIChest != this) {
            getOverwrittenDisplayGUIChest.displayGUIChest(inventory);
        }
    }
    
    public void afterDisplayGUIChest(final IInventory inventory) {
    }
    
    public void beforeDisplayGUIDispenser(final TileEntityDispenser tileEntityDispenser) {
    }
    
    public void displayGUIDispenser(final TileEntityDispenser tileEntityDispenser) {
        final ServerPlayerBase getOverwrittenDisplayGUIDispenser = this.playerAPI.GetOverwrittenDisplayGUIDispenser(this);
        if (getOverwrittenDisplayGUIDispenser == null) {
            this.player.localDisplayGUIDispenser(tileEntityDispenser);
        }
        else if (getOverwrittenDisplayGUIDispenser != this) {
            getOverwrittenDisplayGUIDispenser.displayGUIDispenser(tileEntityDispenser);
        }
    }
    
    public void afterDisplayGUIDispenser(final TileEntityDispenser tileEntityDispenser) {
    }
    
    public void beforeDisplayGUIFurnace(final TileEntityFurnace tileEntityFurnace) {
    }
    
    public void displayGUIFurnace(final TileEntityFurnace tileEntityFurnace) {
        final ServerPlayerBase getOverwrittenDisplayGUIFurnace = this.playerAPI.GetOverwrittenDisplayGUIFurnace(this);
        if (getOverwrittenDisplayGUIFurnace == null) {
            this.player.localDisplayGUIFurnace(tileEntityFurnace);
        }
        else if (getOverwrittenDisplayGUIFurnace != this) {
            getOverwrittenDisplayGUIFurnace.displayGUIFurnace(tileEntityFurnace);
        }
    }
    
    public void afterDisplayGUIFurnace(final TileEntityFurnace tileEntityFurnace) {
    }
    
    public void beforeDisplayGUIWorkbench(final int n, final int n2, final int n3) {
    }
    
    public void displayGUIWorkbench(final int n, final int n2, final int n3) {
        final ServerPlayerBase getOverwrittenDisplayGUIWorkbench = this.playerAPI.GetOverwrittenDisplayGUIWorkbench(this);
        if (getOverwrittenDisplayGUIWorkbench == null) {
            this.player.localDisplayGUIWorkbench(n, n2, n3);
        }
        else if (getOverwrittenDisplayGUIWorkbench != this) {
            getOverwrittenDisplayGUIWorkbench.displayGUIWorkbench(n, n2, n3);
        }
    }
    
    public void afterDisplayGUIWorkbench(final int n, final int n2, final int n3) {
    }
    
    public void beforeDropOneItem(final boolean b) {
    }
    
    public EntityItem dropOneItem(final boolean b) {
        final ServerPlayerBase getOverwrittenDropOneItem = this.playerAPI.GetOverwrittenDropOneItem(this);
        EntityItem entityItem;
        if (getOverwrittenDropOneItem == null) {
            entityItem = this.player.localDropOneItem(b);
        }
        else if (getOverwrittenDropOneItem != this) {
            entityItem = getOverwrittenDropOneItem.dropOneItem(b);
        }
        else {
            entityItem = null;
        }
        return entityItem;
    }
    
    public void afterDropOneItem(final boolean b) {
    }
    
    public void beforeDropPlayerItem(final ItemStack itemStack) {
    }
    
    public EntityItem dropPlayerItem(final ItemStack itemStack) {
        final ServerPlayerBase getOverwrittenDropPlayerItem = this.playerAPI.GetOverwrittenDropPlayerItem(this);
        EntityItem entityItem;
        if (getOverwrittenDropPlayerItem == null) {
            entityItem = this.player.localDropPlayerItem(itemStack);
        }
        else if (getOverwrittenDropPlayerItem != this) {
            entityItem = getOverwrittenDropPlayerItem.dropPlayerItem(itemStack);
        }
        else {
            entityItem = null;
        }
        return entityItem;
    }
    
    public void afterDropPlayerItem(final ItemStack itemStack) {
    }
    
    public void beforeFall(final float n) {
    }
    
    public void fall(final float n) {
        final ServerPlayerBase getOverwrittenFall = this.playerAPI.GetOverwrittenFall(this);
        if (getOverwrittenFall == null) {
            this.player.localFall(n);
        }
        else if (getOverwrittenFall != this) {
            getOverwrittenFall.fall(n);
        }
    }
    
    public void afterFall(final float n) {
    }
    
    public void beforeGetCurrentPlayerStrVsBlock(final Block block, final boolean b) {
    }
    
    public float getCurrentPlayerStrVsBlock(final Block block, final boolean b) {
        final ServerPlayerBase getOverwrittenGetCurrentPlayerStrVsBlock = this.playerAPI.GetOverwrittenGetCurrentPlayerStrVsBlock(this);
        float n;
        if (getOverwrittenGetCurrentPlayerStrVsBlock == null) {
            n = this.player.localGetCurrentPlayerStrVsBlock(block, b);
        }
        else if (getOverwrittenGetCurrentPlayerStrVsBlock != this) {
            n = getOverwrittenGetCurrentPlayerStrVsBlock.getCurrentPlayerStrVsBlock(block, b);
        }
        else {
            n = 0.0f;
        }
        return n;
    }
    
    public void afterGetCurrentPlayerStrVsBlock(final Block block, final boolean b) {
    }
    
    public void beforeGetDistanceSq(final double n, final double n2, final double n3) {
    }
    
    public double getDistanceSq(final double n, final double n2, final double n3) {
        final ServerPlayerBase getOverwrittenGetDistanceSq = this.playerAPI.GetOverwrittenGetDistanceSq(this);
        double n4;
        if (getOverwrittenGetDistanceSq == null) {
            n4 = this.player.localGetDistanceSq(n, n2, n3);
        }
        else if (getOverwrittenGetDistanceSq != this) {
            n4 = getOverwrittenGetDistanceSq.getDistanceSq(n, n2, n3);
        }
        else {
            n4 = 0.0;
        }
        return n4;
    }
    
    public void afterGetDistanceSq(final double n, final double n2, final double n3) {
    }
    
    public void beforeGetBrightness(final float n) {
    }
    
    public float getBrightness(final float n) {
        final ServerPlayerBase getOverwrittenGetBrightness = this.playerAPI.GetOverwrittenGetBrightness(this);
        float n2;
        if (getOverwrittenGetBrightness == null) {
            n2 = this.player.localGetBrightness(n);
        }
        else if (getOverwrittenGetBrightness != this) {
            n2 = getOverwrittenGetBrightness.getBrightness(n);
        }
        else {
            n2 = 0.0f;
        }
        return n2;
    }
    
    public void afterGetBrightness(final float n) {
    }
    
    public void beforeGetEyeHeight() {
    }
    
    public float getEyeHeight() {
        final ServerPlayerBase getOverwrittenGetEyeHeight = this.playerAPI.GetOverwrittenGetEyeHeight(this);
        float n;
        if (getOverwrittenGetEyeHeight == null) {
            n = this.player.localGetEyeHeight();
        }
        else if (getOverwrittenGetEyeHeight != this) {
            n = getOverwrittenGetEyeHeight.getEyeHeight();
        }
        else {
            n = 0.0f;
        }
        return n;
    }
    
    public void afterGetEyeHeight() {
    }
    
    public void beforeGetMaxHealth() {
    }
    
    public int getMaxHealth() {
        final ServerPlayerBase getOverwrittenGetMaxHealth = this.playerAPI.GetOverwrittenGetMaxHealth(this);
        int n;
        if (getOverwrittenGetMaxHealth == null) {
            n = this.player.localGetMaxHealth();
        }
        else if (getOverwrittenGetMaxHealth != this) {
            n = getOverwrittenGetMaxHealth.getMaxHealth();
        }
        else {
            n = 0;
        }
        return n;
    }
    
    public void afterGetMaxHealth() {
    }
    
    public void beforeGetSpeedModifier() {
    }
    
    public float getSpeedModifier() {
        final ServerPlayerBase getOverwrittenGetSpeedModifier = this.playerAPI.GetOverwrittenGetSpeedModifier(this);
        float n;
        if (getOverwrittenGetSpeedModifier == null) {
            n = this.player.localGetSpeedModifier();
        }
        else if (getOverwrittenGetSpeedModifier != this) {
            n = getOverwrittenGetSpeedModifier.getSpeedModifier();
        }
        else {
            n = 0.0f;
        }
        return n;
    }
    
    public void afterGetSpeedModifier() {
    }
    
    public void beforeHeal(final int n) {
    }
    
    public void heal(final int n) {
        final ServerPlayerBase getOverwrittenHeal = this.playerAPI.GetOverwrittenHeal(this);
        if (getOverwrittenHeal == null) {
            this.player.localHeal(n);
        }
        else if (getOverwrittenHeal != this) {
            getOverwrittenHeal.heal(n);
        }
    }
    
    public void afterHeal(final int n) {
    }
    
    public void beforeInteract(final EntityHuman entityHuman) {
    }
    
    public boolean interact(final EntityHuman entityHuman) {
        final ServerPlayerBase getOverwrittenInteract = this.playerAPI.GetOverwrittenInteract(this);
        boolean localInteract;
        if (getOverwrittenInteract == null) {
            localInteract = this.player.localInteract(entityHuman);
        }
        else {
            localInteract = (getOverwrittenInteract != this && getOverwrittenInteract.interact(entityHuman));
        }
        return localInteract;
    }
    
    public void afterInteract(final EntityHuman entityHuman) {
    }
    
    public void beforeIsEntityInsideOpaqueBlock() {
    }
    
    public boolean isEntityInsideOpaqueBlock() {
        final ServerPlayerBase getOverwrittenIsEntityInsideOpaqueBlock = this.playerAPI.GetOverwrittenIsEntityInsideOpaqueBlock(this);
        boolean localIsEntityInsideOpaqueBlock;
        if (getOverwrittenIsEntityInsideOpaqueBlock == null) {
            localIsEntityInsideOpaqueBlock = this.player.localIsEntityInsideOpaqueBlock();
        }
        else {
            localIsEntityInsideOpaqueBlock = (getOverwrittenIsEntityInsideOpaqueBlock != this && getOverwrittenIsEntityInsideOpaqueBlock.isEntityInsideOpaqueBlock());
        }
        return localIsEntityInsideOpaqueBlock;
    }
    
    public void afterIsEntityInsideOpaqueBlock() {
    }
    
    public void beforeIsInWater() {
    }
    
    public boolean isInWater() {
        final ServerPlayerBase getOverwrittenIsInWater = this.playerAPI.GetOverwrittenIsInWater(this);
        boolean localIsInWater;
        if (getOverwrittenIsInWater == null) {
            localIsInWater = this.player.localIsInWater();
        }
        else {
            localIsInWater = (getOverwrittenIsInWater != this && getOverwrittenIsInWater.isInWater());
        }
        return localIsInWater;
    }
    
    public void afterIsInWater() {
    }
    
    public void beforeIsInsideOfMaterial(final Material material) {
    }
    
    public boolean isInsideOfMaterial(final Material material) {
        final ServerPlayerBase getOverwrittenIsInsideOfMaterial = this.playerAPI.GetOverwrittenIsInsideOfMaterial(this);
        boolean localIsInsideOfMaterial;
        if (getOverwrittenIsInsideOfMaterial == null) {
            localIsInsideOfMaterial = this.player.localIsInsideOfMaterial(material);
        }
        else {
            localIsInsideOfMaterial = (getOverwrittenIsInsideOfMaterial != this && getOverwrittenIsInsideOfMaterial.isInsideOfMaterial(material));
        }
        return localIsInsideOfMaterial;
    }
    
    public void afterIsInsideOfMaterial(final Material material) {
    }
    
    public void beforeIsOnLadder() {
    }
    
    public boolean isOnLadder() {
        final ServerPlayerBase getOverwrittenIsOnLadder = this.playerAPI.GetOverwrittenIsOnLadder(this);
        boolean localIsOnLadder;
        if (getOverwrittenIsOnLadder == null) {
            localIsOnLadder = this.player.localIsOnLadder();
        }
        else {
            localIsOnLadder = (getOverwrittenIsOnLadder != this && getOverwrittenIsOnLadder.isOnLadder());
        }
        return localIsOnLadder;
    }
    
    public void afterIsOnLadder() {
    }
    
    public void beforeIsPlayerSleeping() {
    }
    
    public boolean isPlayerSleeping() {
        final ServerPlayerBase getOverwrittenIsPlayerSleeping = this.playerAPI.GetOverwrittenIsPlayerSleeping(this);
        boolean localIsPlayerSleeping;
        if (getOverwrittenIsPlayerSleeping == null) {
            localIsPlayerSleeping = this.player.localIsPlayerSleeping();
        }
        else {
            localIsPlayerSleeping = (getOverwrittenIsPlayerSleeping != this && getOverwrittenIsPlayerSleeping.isPlayerSleeping());
        }
        return localIsPlayerSleeping;
    }
    
    public void afterIsPlayerSleeping() {
    }
    
    public void beforeJump() {
    }
    
    public void jump() {
        final ServerPlayerBase getOverwrittenJump = this.playerAPI.GetOverwrittenJump(this);
        if (getOverwrittenJump == null) {
            this.player.localJump();
        }
        else if (getOverwrittenJump != this) {
            getOverwrittenJump.jump();
        }
    }
    
    public void afterJump() {
    }
    
    public void beforeKnockBack(final Entity entity, final int n, final double n2, final double n3) {
    }
    
    public void knockBack(final Entity entity, final int n, final double n2, final double n3) {
        final ServerPlayerBase getOverwrittenKnockBack = this.playerAPI.GetOverwrittenKnockBack(this);
        if (getOverwrittenKnockBack == null) {
            this.player.localKnockBack(entity, n, n2, n3);
        }
        else if (getOverwrittenKnockBack != this) {
            getOverwrittenKnockBack.knockBack(entity, n, n2, n3);
        }
    }
    
    public void afterKnockBack(final Entity entity, final int n, final double n2, final double n3) {
    }
    
    public void beforeMoveEntity(final double n, final double n2, final double n3) {
    }
    
    public void moveEntity(final double n, final double n2, final double n3) {
        final ServerPlayerBase getOverwrittenMoveEntity = this.playerAPI.GetOverwrittenMoveEntity(this);
        if (getOverwrittenMoveEntity == null) {
            this.player.localMoveEntity(n, n2, n3);
        }
        else if (getOverwrittenMoveEntity != this) {
            getOverwrittenMoveEntity.moveEntity(n, n2, n3);
        }
    }
    
    public void afterMoveEntity(final double n, final double n2, final double n3) {
    }
    
    public void beforeMoveEntityWithHeading(final float n, final float n2) {
    }
    
    public void moveEntityWithHeading(final float n, final float n2) {
        final ServerPlayerBase getOverwrittenMoveEntityWithHeading = this.playerAPI.GetOverwrittenMoveEntityWithHeading(this);
        if (getOverwrittenMoveEntityWithHeading == null) {
            this.player.localMoveEntityWithHeading(n, n2);
        }
        else if (getOverwrittenMoveEntityWithHeading != this) {
            getOverwrittenMoveEntityWithHeading.moveEntityWithHeading(n, n2);
        }
    }
    
    public void afterMoveEntityWithHeading(final float n, final float n2) {
    }
    
    public void beforeMoveFlying(final float n, final float n2, final float n3) {
    }
    
    public void moveFlying(final float n, final float n2, final float n3) {
        final ServerPlayerBase getOverwrittenMoveFlying = this.playerAPI.GetOverwrittenMoveFlying(this);
        if (getOverwrittenMoveFlying == null) {
            this.player.localMoveFlying(n, n2, n3);
        }
        else if (getOverwrittenMoveFlying != this) {
            getOverwrittenMoveFlying.moveFlying(n, n2, n3);
        }
    }
    
    public void afterMoveFlying(final float n, final float n2, final float n3) {
    }
    
    public void beforeOnDeath(final DamageSource damageSource) {
    }
    
    public void onDeath(final DamageSource damageSource) {
        final ServerPlayerBase getOverwrittenOnDeath = this.playerAPI.GetOverwrittenOnDeath(this);
        if (getOverwrittenOnDeath == null) {
            this.player.localOnDeath(damageSource);
        }
        else if (getOverwrittenOnDeath != this) {
            getOverwrittenOnDeath.onDeath(damageSource);
        }
    }
    
    public void afterOnDeath(final DamageSource damageSource) {
    }
    
    public void beforeOnLivingUpdate() {
    }
    
    public void onLivingUpdate() {
        final ServerPlayerBase getOverwrittenOnLivingUpdate = this.playerAPI.GetOverwrittenOnLivingUpdate(this);
        if (getOverwrittenOnLivingUpdate == null) {
            this.player.localOnLivingUpdate();
        }
        else if (getOverwrittenOnLivingUpdate != this) {
            getOverwrittenOnLivingUpdate.onLivingUpdate();
        }
    }
    
    public void afterOnLivingUpdate() {
    }
    
    public void beforeOnKillEntity(final EntityLiving entityLiving) {
    }
    
    public void onKillEntity(final EntityLiving entityLiving) {
        final ServerPlayerBase getOverwrittenOnKillEntity = this.playerAPI.GetOverwrittenOnKillEntity(this);
        if (getOverwrittenOnKillEntity == null) {
            this.player.localOnKillEntity(entityLiving);
        }
        else if (getOverwrittenOnKillEntity != this) {
            getOverwrittenOnKillEntity.onKillEntity(entityLiving);
        }
    }
    
    public void afterOnKillEntity(final EntityLiving entityLiving) {
    }
    
    public void beforeOnStruckByLightning(final EntityLightning entityLightning) {
    }
    
    public void onStruckByLightning(final EntityLightning entityLightning) {
        final ServerPlayerBase getOverwrittenOnStruckByLightning = this.playerAPI.GetOverwrittenOnStruckByLightning(this);
        if (getOverwrittenOnStruckByLightning == null) {
            this.player.localOnStruckByLightning(entityLightning);
        }
        else if (getOverwrittenOnStruckByLightning != this) {
            getOverwrittenOnStruckByLightning.onStruckByLightning(entityLightning);
        }
    }
    
    public void afterOnStruckByLightning(final EntityLightning entityLightning) {
    }
    
    public void beforeOnUpdate() {
    }
    
    public void onUpdate() {
        final ServerPlayerBase getOverwrittenOnUpdate = this.playerAPI.GetOverwrittenOnUpdate(this);
        if (getOverwrittenOnUpdate == null) {
            this.player.localOnUpdate();
        }
        else if (getOverwrittenOnUpdate != this) {
            getOverwrittenOnUpdate.onUpdate();
        }
    }
    
    public void afterOnUpdate() {
    }
    
    public void beforeOnUpdateEntity() {
    }
    
    public void onUpdateEntity() {
        final ServerPlayerBase getOverwrittenOnUpdateEntity = this.playerAPI.GetOverwrittenOnUpdateEntity(this);
        if (getOverwrittenOnUpdateEntity == null) {
            this.player.localOnUpdateEntity();
        }
        else if (getOverwrittenOnUpdateEntity != this) {
            getOverwrittenOnUpdateEntity.onUpdateEntity();
        }
    }
    
    public void afterOnUpdateEntity() {
    }
    
    public void beforeReadEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        final ServerPlayerBase getOverwrittenReadEntityFromNBT = this.playerAPI.GetOverwrittenReadEntityFromNBT(this);
        if (getOverwrittenReadEntityFromNBT == null) {
            this.player.localReadEntityFromNBT(nbtTagCompound);
        }
        else if (getOverwrittenReadEntityFromNBT != this) {
            getOverwrittenReadEntityFromNBT.readEntityFromNBT(nbtTagCompound);
        }
    }
    
    public void afterReadEntityFromNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void beforeSetDead() {
    }
    
    public void setDead() {
        final ServerPlayerBase getOverwrittenSetDead = this.playerAPI.GetOverwrittenSetDead(this);
        if (getOverwrittenSetDead == null) {
            this.player.localSetDead();
        }
        else if (getOverwrittenSetDead != this) {
            getOverwrittenSetDead.setDead();
        }
    }
    
    public void afterSetDead() {
    }
    
    public void beforeSetPosition(final double n, final double n2, final double n3) {
    }
    
    public void setPosition(final double n, final double n2, final double n3) {
        final ServerPlayerBase getOverwrittenSetPosition = this.playerAPI.GetOverwrittenSetPosition(this);
        if (getOverwrittenSetPosition == null) {
            this.player.localSetPosition(n, n2, n3);
        }
        else if (getOverwrittenSetPosition != this) {
            getOverwrittenSetPosition.setPosition(n, n2, n3);
        }
    }
    
    public void afterSetPosition(final double n, final double n2, final double n3) {
    }
    
    public void beforeSwingItem() {
    }
    
    public void swingItem() {
        final ServerPlayerBase getOverwrittenSwingItem = this.playerAPI.GetOverwrittenSwingItem(this);
        if (getOverwrittenSwingItem == null) {
            this.player.localSwingItem();
        }
        else if (getOverwrittenSwingItem != this) {
            getOverwrittenSwingItem.swingItem();
        }
    }
    
    public void afterSwingItem() {
    }
    
    public void beforeUpdateEntityActionState() {
    }
    
    public void updateEntityActionState() {
        final ServerPlayerBase getOverwrittenUpdateEntityActionState = this.playerAPI.GetOverwrittenUpdateEntityActionState(this);
        if (getOverwrittenUpdateEntityActionState == null) {
            this.player.localUpdateEntityActionState();
        }
        else if (getOverwrittenUpdateEntityActionState != this) {
            getOverwrittenUpdateEntityActionState.updateEntityActionState();
        }
    }
    
    public void afterUpdateEntityActionState() {
    }
    
    public void beforeUpdatePotionEffects() {
    }
    
    public void updatePotionEffects() {
        final ServerPlayerBase getOverwrittenUpdatePotionEffects = this.playerAPI.GetOverwrittenUpdatePotionEffects(this);
        if (getOverwrittenUpdatePotionEffects == null) {
            this.player.localUpdatePotionEffects();
        }
        else if (getOverwrittenUpdatePotionEffects != this) {
            getOverwrittenUpdatePotionEffects.updatePotionEffects();
        }
    }
    
    public void afterUpdatePotionEffects() {
    }
    
    public void beforeWriteEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        final ServerPlayerBase getOverwrittenWriteEntityToNBT = this.playerAPI.GetOverwrittenWriteEntityToNBT(this);
        if (getOverwrittenWriteEntityToNBT == null) {
            this.player.localWriteEntityToNBT(nbtTagCompound);
        }
        else if (getOverwrittenWriteEntityToNBT != this) {
            getOverwrittenWriteEntityToNBT.writeEntityToNBT(nbtTagCompound);
        }
    }
    
    public void afterWriteEntityToNBT(final NBTTagCompound nbtTagCompound) {
    }
}
