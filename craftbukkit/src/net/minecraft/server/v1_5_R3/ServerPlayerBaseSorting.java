// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashMap;
import java.util.Map;

public final class ServerPlayerBaseSorting
{
    private String[] beforeLocalConstructingSuperiors;
    private String[] beforeLocalConstructingInferiors;
    private String[] afterLocalConstructingSuperiors;
    private String[] afterLocalConstructingInferiors;
    private Map<String, String[]> dynamicBeforeSuperiors;
    private Map<String, String[]> dynamicBeforeInferiors;
    private Map<String, String[]> dynamicOverrideSuperiors;
    private Map<String, String[]> dynamicOverrideInferiors;
    private Map<String, String[]> dynamicAfterSuperiors;
    private Map<String, String[]> dynamicAfterInferiors;
    private String[] beforeAddExhaustionSuperiors;
    private String[] beforeAddExhaustionInferiors;
    private String[] overrideAddExhaustionSuperiors;
    private String[] overrideAddExhaustionInferiors;
    private String[] afterAddExhaustionSuperiors;
    private String[] afterAddExhaustionInferiors;
    private String[] beforeAddExperienceSuperiors;
    private String[] beforeAddExperienceInferiors;
    private String[] overrideAddExperienceSuperiors;
    private String[] overrideAddExperienceInferiors;
    private String[] afterAddExperienceSuperiors;
    private String[] afterAddExperienceInferiors;
    private String[] beforeAddExperienceLevelSuperiors;
    private String[] beforeAddExperienceLevelInferiors;
    private String[] overrideAddExperienceLevelSuperiors;
    private String[] overrideAddExperienceLevelInferiors;
    private String[] afterAddExperienceLevelSuperiors;
    private String[] afterAddExperienceLevelInferiors;
    private String[] beforeAddMovementStatSuperiors;
    private String[] beforeAddMovementStatInferiors;
    private String[] overrideAddMovementStatSuperiors;
    private String[] overrideAddMovementStatInferiors;
    private String[] afterAddMovementStatSuperiors;
    private String[] afterAddMovementStatInferiors;
    private String[] beforeAttackEntityFromSuperiors;
    private String[] beforeAttackEntityFromInferiors;
    private String[] overrideAttackEntityFromSuperiors;
    private String[] overrideAttackEntityFromInferiors;
    private String[] afterAttackEntityFromSuperiors;
    private String[] afterAttackEntityFromInferiors;
    private String[] beforeAttackTargetEntityWithCurrentItemSuperiors;
    private String[] beforeAttackTargetEntityWithCurrentItemInferiors;
    private String[] overrideAttackTargetEntityWithCurrentItemSuperiors;
    private String[] overrideAttackTargetEntityWithCurrentItemInferiors;
    private String[] afterAttackTargetEntityWithCurrentItemSuperiors;
    private String[] afterAttackTargetEntityWithCurrentItemInferiors;
    private String[] beforeCanHarvestBlockSuperiors;
    private String[] beforeCanHarvestBlockInferiors;
    private String[] overrideCanHarvestBlockSuperiors;
    private String[] overrideCanHarvestBlockInferiors;
    private String[] afterCanHarvestBlockSuperiors;
    private String[] afterCanHarvestBlockInferiors;
    private String[] beforeCanPlayerEditSuperiors;
    private String[] beforeCanPlayerEditInferiors;
    private String[] overrideCanPlayerEditSuperiors;
    private String[] overrideCanPlayerEditInferiors;
    private String[] afterCanPlayerEditSuperiors;
    private String[] afterCanPlayerEditInferiors;
    private String[] beforeCanTriggerWalkingSuperiors;
    private String[] beforeCanTriggerWalkingInferiors;
    private String[] overrideCanTriggerWalkingSuperiors;
    private String[] overrideCanTriggerWalkingInferiors;
    private String[] afterCanTriggerWalkingSuperiors;
    private String[] afterCanTriggerWalkingInferiors;
    private String[] beforeClonePlayerSuperiors;
    private String[] beforeClonePlayerInferiors;
    private String[] overrideClonePlayerSuperiors;
    private String[] overrideClonePlayerInferiors;
    private String[] afterClonePlayerSuperiors;
    private String[] afterClonePlayerInferiors;
    private String[] beforeDamageEntitySuperiors;
    private String[] beforeDamageEntityInferiors;
    private String[] overrideDamageEntitySuperiors;
    private String[] overrideDamageEntityInferiors;
    private String[] afterDamageEntitySuperiors;
    private String[] afterDamageEntityInferiors;
    private String[] beforeDisplayGUIChestSuperiors;
    private String[] beforeDisplayGUIChestInferiors;
    private String[] overrideDisplayGUIChestSuperiors;
    private String[] overrideDisplayGUIChestInferiors;
    private String[] afterDisplayGUIChestSuperiors;
    private String[] afterDisplayGUIChestInferiors;
    private String[] beforeDisplayGUIDispenserSuperiors;
    private String[] beforeDisplayGUIDispenserInferiors;
    private String[] overrideDisplayGUIDispenserSuperiors;
    private String[] overrideDisplayGUIDispenserInferiors;
    private String[] afterDisplayGUIDispenserSuperiors;
    private String[] afterDisplayGUIDispenserInferiors;
    private String[] beforeDisplayGUIFurnaceSuperiors;
    private String[] beforeDisplayGUIFurnaceInferiors;
    private String[] overrideDisplayGUIFurnaceSuperiors;
    private String[] overrideDisplayGUIFurnaceInferiors;
    private String[] afterDisplayGUIFurnaceSuperiors;
    private String[] afterDisplayGUIFurnaceInferiors;
    private String[] beforeDisplayGUIWorkbenchSuperiors;
    private String[] beforeDisplayGUIWorkbenchInferiors;
    private String[] overrideDisplayGUIWorkbenchSuperiors;
    private String[] overrideDisplayGUIWorkbenchInferiors;
    private String[] afterDisplayGUIWorkbenchSuperiors;
    private String[] afterDisplayGUIWorkbenchInferiors;
    private String[] beforeDropOneItemSuperiors;
    private String[] beforeDropOneItemInferiors;
    private String[] overrideDropOneItemSuperiors;
    private String[] overrideDropOneItemInferiors;
    private String[] afterDropOneItemSuperiors;
    private String[] afterDropOneItemInferiors;
    private String[] beforeDropPlayerItemSuperiors;
    private String[] beforeDropPlayerItemInferiors;
    private String[] overrideDropPlayerItemSuperiors;
    private String[] overrideDropPlayerItemInferiors;
    private String[] afterDropPlayerItemSuperiors;
    private String[] afterDropPlayerItemInferiors;
    private String[] beforeFallSuperiors;
    private String[] beforeFallInferiors;
    private String[] overrideFallSuperiors;
    private String[] overrideFallInferiors;
    private String[] afterFallSuperiors;
    private String[] afterFallInferiors;
    private String[] beforeGetCurrentPlayerStrVsBlockSuperiors;
    private String[] beforeGetCurrentPlayerStrVsBlockInferiors;
    private String[] overrideGetCurrentPlayerStrVsBlockSuperiors;
    private String[] overrideGetCurrentPlayerStrVsBlockInferiors;
    private String[] afterGetCurrentPlayerStrVsBlockSuperiors;
    private String[] afterGetCurrentPlayerStrVsBlockInferiors;
    private String[] beforeGetDistanceSqSuperiors;
    private String[] beforeGetDistanceSqInferiors;
    private String[] overrideGetDistanceSqSuperiors;
    private String[] overrideGetDistanceSqInferiors;
    private String[] afterGetDistanceSqSuperiors;
    private String[] afterGetDistanceSqInferiors;
    private String[] beforeGetBrightnessSuperiors;
    private String[] beforeGetBrightnessInferiors;
    private String[] overrideGetBrightnessSuperiors;
    private String[] overrideGetBrightnessInferiors;
    private String[] afterGetBrightnessSuperiors;
    private String[] afterGetBrightnessInferiors;
    private String[] beforeGetEyeHeightSuperiors;
    private String[] beforeGetEyeHeightInferiors;
    private String[] overrideGetEyeHeightSuperiors;
    private String[] overrideGetEyeHeightInferiors;
    private String[] afterGetEyeHeightSuperiors;
    private String[] afterGetEyeHeightInferiors;
    private String[] beforeGetMaxHealthSuperiors;
    private String[] beforeGetMaxHealthInferiors;
    private String[] overrideGetMaxHealthSuperiors;
    private String[] overrideGetMaxHealthInferiors;
    private String[] afterGetMaxHealthSuperiors;
    private String[] afterGetMaxHealthInferiors;
    private String[] beforeGetSpeedModifierSuperiors;
    private String[] beforeGetSpeedModifierInferiors;
    private String[] overrideGetSpeedModifierSuperiors;
    private String[] overrideGetSpeedModifierInferiors;
    private String[] afterGetSpeedModifierSuperiors;
    private String[] afterGetSpeedModifierInferiors;
    private String[] beforeHealSuperiors;
    private String[] beforeHealInferiors;
    private String[] overrideHealSuperiors;
    private String[] overrideHealInferiors;
    private String[] afterHealSuperiors;
    private String[] afterHealInferiors;
    private String[] beforeInteractSuperiors;
    private String[] beforeInteractInferiors;
    private String[] overrideInteractSuperiors;
    private String[] overrideInteractInferiors;
    private String[] afterInteractSuperiors;
    private String[] afterInteractInferiors;
    private String[] beforeIsEntityInsideOpaqueBlockSuperiors;
    private String[] beforeIsEntityInsideOpaqueBlockInferiors;
    private String[] overrideIsEntityInsideOpaqueBlockSuperiors;
    private String[] overrideIsEntityInsideOpaqueBlockInferiors;
    private String[] afterIsEntityInsideOpaqueBlockSuperiors;
    private String[] afterIsEntityInsideOpaqueBlockInferiors;
    private String[] beforeIsInWaterSuperiors;
    private String[] beforeIsInWaterInferiors;
    private String[] overrideIsInWaterSuperiors;
    private String[] overrideIsInWaterInferiors;
    private String[] afterIsInWaterSuperiors;
    private String[] afterIsInWaterInferiors;
    private String[] beforeIsInsideOfMaterialSuperiors;
    private String[] beforeIsInsideOfMaterialInferiors;
    private String[] overrideIsInsideOfMaterialSuperiors;
    private String[] overrideIsInsideOfMaterialInferiors;
    private String[] afterIsInsideOfMaterialSuperiors;
    private String[] afterIsInsideOfMaterialInferiors;
    private String[] beforeIsOnLadderSuperiors;
    private String[] beforeIsOnLadderInferiors;
    private String[] overrideIsOnLadderSuperiors;
    private String[] overrideIsOnLadderInferiors;
    private String[] afterIsOnLadderSuperiors;
    private String[] afterIsOnLadderInferiors;
    private String[] beforeIsPlayerSleepingSuperiors;
    private String[] beforeIsPlayerSleepingInferiors;
    private String[] overrideIsPlayerSleepingSuperiors;
    private String[] overrideIsPlayerSleepingInferiors;
    private String[] afterIsPlayerSleepingSuperiors;
    private String[] afterIsPlayerSleepingInferiors;
    private String[] beforeJumpSuperiors;
    private String[] beforeJumpInferiors;
    private String[] overrideJumpSuperiors;
    private String[] overrideJumpInferiors;
    private String[] afterJumpSuperiors;
    private String[] afterJumpInferiors;
    private String[] beforeKnockBackSuperiors;
    private String[] beforeKnockBackInferiors;
    private String[] overrideKnockBackSuperiors;
    private String[] overrideKnockBackInferiors;
    private String[] afterKnockBackSuperiors;
    private String[] afterKnockBackInferiors;
    private String[] beforeMoveEntitySuperiors;
    private String[] beforeMoveEntityInferiors;
    private String[] overrideMoveEntitySuperiors;
    private String[] overrideMoveEntityInferiors;
    private String[] afterMoveEntitySuperiors;
    private String[] afterMoveEntityInferiors;
    private String[] beforeMoveEntityWithHeadingSuperiors;
    private String[] beforeMoveEntityWithHeadingInferiors;
    private String[] overrideMoveEntityWithHeadingSuperiors;
    private String[] overrideMoveEntityWithHeadingInferiors;
    private String[] afterMoveEntityWithHeadingSuperiors;
    private String[] afterMoveEntityWithHeadingInferiors;
    private String[] beforeMoveFlyingSuperiors;
    private String[] beforeMoveFlyingInferiors;
    private String[] overrideMoveFlyingSuperiors;
    private String[] overrideMoveFlyingInferiors;
    private String[] afterMoveFlyingSuperiors;
    private String[] afterMoveFlyingInferiors;
    private String[] beforeOnDeathSuperiors;
    private String[] beforeOnDeathInferiors;
    private String[] overrideOnDeathSuperiors;
    private String[] overrideOnDeathInferiors;
    private String[] afterOnDeathSuperiors;
    private String[] afterOnDeathInferiors;
    private String[] beforeOnLivingUpdateSuperiors;
    private String[] beforeOnLivingUpdateInferiors;
    private String[] overrideOnLivingUpdateSuperiors;
    private String[] overrideOnLivingUpdateInferiors;
    private String[] afterOnLivingUpdateSuperiors;
    private String[] afterOnLivingUpdateInferiors;
    private String[] beforeOnKillEntitySuperiors;
    private String[] beforeOnKillEntityInferiors;
    private String[] overrideOnKillEntitySuperiors;
    private String[] overrideOnKillEntityInferiors;
    private String[] afterOnKillEntitySuperiors;
    private String[] afterOnKillEntityInferiors;
    private String[] beforeOnStruckByLightningSuperiors;
    private String[] beforeOnStruckByLightningInferiors;
    private String[] overrideOnStruckByLightningSuperiors;
    private String[] overrideOnStruckByLightningInferiors;
    private String[] afterOnStruckByLightningSuperiors;
    private String[] afterOnStruckByLightningInferiors;
    private String[] beforeOnUpdateSuperiors;
    private String[] beforeOnUpdateInferiors;
    private String[] overrideOnUpdateSuperiors;
    private String[] overrideOnUpdateInferiors;
    private String[] afterOnUpdateSuperiors;
    private String[] afterOnUpdateInferiors;
    private String[] beforeOnUpdateEntitySuperiors;
    private String[] beforeOnUpdateEntityInferiors;
    private String[] overrideOnUpdateEntitySuperiors;
    private String[] overrideOnUpdateEntityInferiors;
    private String[] afterOnUpdateEntitySuperiors;
    private String[] afterOnUpdateEntityInferiors;
    private String[] beforeReadEntityFromNBTSuperiors;
    private String[] beforeReadEntityFromNBTInferiors;
    private String[] overrideReadEntityFromNBTSuperiors;
    private String[] overrideReadEntityFromNBTInferiors;
    private String[] afterReadEntityFromNBTSuperiors;
    private String[] afterReadEntityFromNBTInferiors;
    private String[] beforeSetDeadSuperiors;
    private String[] beforeSetDeadInferiors;
    private String[] overrideSetDeadSuperiors;
    private String[] overrideSetDeadInferiors;
    private String[] afterSetDeadSuperiors;
    private String[] afterSetDeadInferiors;
    private String[] beforeSetPositionSuperiors;
    private String[] beforeSetPositionInferiors;
    private String[] overrideSetPositionSuperiors;
    private String[] overrideSetPositionInferiors;
    private String[] afterSetPositionSuperiors;
    private String[] afterSetPositionInferiors;
    private String[] beforeSwingItemSuperiors;
    private String[] beforeSwingItemInferiors;
    private String[] overrideSwingItemSuperiors;
    private String[] overrideSwingItemInferiors;
    private String[] afterSwingItemSuperiors;
    private String[] afterSwingItemInferiors;
    private String[] beforeUpdateEntityActionStateSuperiors;
    private String[] beforeUpdateEntityActionStateInferiors;
    private String[] overrideUpdateEntityActionStateSuperiors;
    private String[] overrideUpdateEntityActionStateInferiors;
    private String[] afterUpdateEntityActionStateSuperiors;
    private String[] afterUpdateEntityActionStateInferiors;
    private String[] beforeUpdatePotionEffectsSuperiors;
    private String[] beforeUpdatePotionEffectsInferiors;
    private String[] overrideUpdatePotionEffectsSuperiors;
    private String[] overrideUpdatePotionEffectsInferiors;
    private String[] afterUpdatePotionEffectsSuperiors;
    private String[] afterUpdatePotionEffectsInferiors;
    private String[] beforeWriteEntityToNBTSuperiors;
    private String[] beforeWriteEntityToNBTInferiors;
    private String[] overrideWriteEntityToNBTSuperiors;
    private String[] overrideWriteEntityToNBTInferiors;
    private String[] afterWriteEntityToNBTSuperiors;
    private String[] afterWriteEntityToNBTInferiors;
    
    public ServerPlayerBaseSorting() {
        this.beforeLocalConstructingSuperiors = null;
        this.beforeLocalConstructingInferiors = null;
        this.afterLocalConstructingSuperiors = null;
        this.afterLocalConstructingInferiors = null;
        this.dynamicBeforeSuperiors = null;
        this.dynamicBeforeInferiors = null;
        this.dynamicOverrideSuperiors = null;
        this.dynamicOverrideInferiors = null;
        this.dynamicAfterSuperiors = null;
        this.dynamicAfterInferiors = null;
        this.beforeAddExhaustionSuperiors = null;
        this.beforeAddExhaustionInferiors = null;
        this.overrideAddExhaustionSuperiors = null;
        this.overrideAddExhaustionInferiors = null;
        this.afterAddExhaustionSuperiors = null;
        this.afterAddExhaustionInferiors = null;
        this.beforeAddExperienceSuperiors = null;
        this.beforeAddExperienceInferiors = null;
        this.overrideAddExperienceSuperiors = null;
        this.overrideAddExperienceInferiors = null;
        this.afterAddExperienceSuperiors = null;
        this.afterAddExperienceInferiors = null;
        this.beforeAddExperienceLevelSuperiors = null;
        this.beforeAddExperienceLevelInferiors = null;
        this.overrideAddExperienceLevelSuperiors = null;
        this.overrideAddExperienceLevelInferiors = null;
        this.afterAddExperienceLevelSuperiors = null;
        this.afterAddExperienceLevelInferiors = null;
        this.beforeAddMovementStatSuperiors = null;
        this.beforeAddMovementStatInferiors = null;
        this.overrideAddMovementStatSuperiors = null;
        this.overrideAddMovementStatInferiors = null;
        this.afterAddMovementStatSuperiors = null;
        this.afterAddMovementStatInferiors = null;
        this.beforeAttackEntityFromSuperiors = null;
        this.beforeAttackEntityFromInferiors = null;
        this.overrideAttackEntityFromSuperiors = null;
        this.overrideAttackEntityFromInferiors = null;
        this.afterAttackEntityFromSuperiors = null;
        this.afterAttackEntityFromInferiors = null;
        this.beforeAttackTargetEntityWithCurrentItemSuperiors = null;
        this.beforeAttackTargetEntityWithCurrentItemInferiors = null;
        this.overrideAttackTargetEntityWithCurrentItemSuperiors = null;
        this.overrideAttackTargetEntityWithCurrentItemInferiors = null;
        this.afterAttackTargetEntityWithCurrentItemSuperiors = null;
        this.afterAttackTargetEntityWithCurrentItemInferiors = null;
        this.beforeCanHarvestBlockSuperiors = null;
        this.beforeCanHarvestBlockInferiors = null;
        this.overrideCanHarvestBlockSuperiors = null;
        this.overrideCanHarvestBlockInferiors = null;
        this.afterCanHarvestBlockSuperiors = null;
        this.afterCanHarvestBlockInferiors = null;
        this.beforeCanPlayerEditSuperiors = null;
        this.beforeCanPlayerEditInferiors = null;
        this.overrideCanPlayerEditSuperiors = null;
        this.overrideCanPlayerEditInferiors = null;
        this.afterCanPlayerEditSuperiors = null;
        this.afterCanPlayerEditInferiors = null;
        this.beforeCanTriggerWalkingSuperiors = null;
        this.beforeCanTriggerWalkingInferiors = null;
        this.overrideCanTriggerWalkingSuperiors = null;
        this.overrideCanTriggerWalkingInferiors = null;
        this.afterCanTriggerWalkingSuperiors = null;
        this.afterCanTriggerWalkingInferiors = null;
        this.beforeClonePlayerSuperiors = null;
        this.beforeClonePlayerInferiors = null;
        this.overrideClonePlayerSuperiors = null;
        this.overrideClonePlayerInferiors = null;
        this.afterClonePlayerSuperiors = null;
        this.afterClonePlayerInferiors = null;
        this.beforeDamageEntitySuperiors = null;
        this.beforeDamageEntityInferiors = null;
        this.overrideDamageEntitySuperiors = null;
        this.overrideDamageEntityInferiors = null;
        this.afterDamageEntitySuperiors = null;
        this.afterDamageEntityInferiors = null;
        this.beforeDisplayGUIChestSuperiors = null;
        this.beforeDisplayGUIChestInferiors = null;
        this.overrideDisplayGUIChestSuperiors = null;
        this.overrideDisplayGUIChestInferiors = null;
        this.afterDisplayGUIChestSuperiors = null;
        this.afterDisplayGUIChestInferiors = null;
        this.beforeDisplayGUIDispenserSuperiors = null;
        this.beforeDisplayGUIDispenserInferiors = null;
        this.overrideDisplayGUIDispenserSuperiors = null;
        this.overrideDisplayGUIDispenserInferiors = null;
        this.afterDisplayGUIDispenserSuperiors = null;
        this.afterDisplayGUIDispenserInferiors = null;
        this.beforeDisplayGUIFurnaceSuperiors = null;
        this.beforeDisplayGUIFurnaceInferiors = null;
        this.overrideDisplayGUIFurnaceSuperiors = null;
        this.overrideDisplayGUIFurnaceInferiors = null;
        this.afterDisplayGUIFurnaceSuperiors = null;
        this.afterDisplayGUIFurnaceInferiors = null;
        this.beforeDisplayGUIWorkbenchSuperiors = null;
        this.beforeDisplayGUIWorkbenchInferiors = null;
        this.overrideDisplayGUIWorkbenchSuperiors = null;
        this.overrideDisplayGUIWorkbenchInferiors = null;
        this.afterDisplayGUIWorkbenchSuperiors = null;
        this.afterDisplayGUIWorkbenchInferiors = null;
        this.beforeDropOneItemSuperiors = null;
        this.beforeDropOneItemInferiors = null;
        this.overrideDropOneItemSuperiors = null;
        this.overrideDropOneItemInferiors = null;
        this.afterDropOneItemSuperiors = null;
        this.afterDropOneItemInferiors = null;
        this.beforeDropPlayerItemSuperiors = null;
        this.beforeDropPlayerItemInferiors = null;
        this.overrideDropPlayerItemSuperiors = null;
        this.overrideDropPlayerItemInferiors = null;
        this.afterDropPlayerItemSuperiors = null;
        this.afterDropPlayerItemInferiors = null;
        this.beforeFallSuperiors = null;
        this.beforeFallInferiors = null;
        this.overrideFallSuperiors = null;
        this.overrideFallInferiors = null;
        this.afterFallSuperiors = null;
        this.afterFallInferiors = null;
        this.beforeGetCurrentPlayerStrVsBlockSuperiors = null;
        this.beforeGetCurrentPlayerStrVsBlockInferiors = null;
        this.overrideGetCurrentPlayerStrVsBlockSuperiors = null;
        this.overrideGetCurrentPlayerStrVsBlockInferiors = null;
        this.afterGetCurrentPlayerStrVsBlockSuperiors = null;
        this.afterGetCurrentPlayerStrVsBlockInferiors = null;
        this.beforeGetDistanceSqSuperiors = null;
        this.beforeGetDistanceSqInferiors = null;
        this.overrideGetDistanceSqSuperiors = null;
        this.overrideGetDistanceSqInferiors = null;
        this.afterGetDistanceSqSuperiors = null;
        this.afterGetDistanceSqInferiors = null;
        this.beforeGetBrightnessSuperiors = null;
        this.beforeGetBrightnessInferiors = null;
        this.overrideGetBrightnessSuperiors = null;
        this.overrideGetBrightnessInferiors = null;
        this.afterGetBrightnessSuperiors = null;
        this.afterGetBrightnessInferiors = null;
        this.beforeGetEyeHeightSuperiors = null;
        this.beforeGetEyeHeightInferiors = null;
        this.overrideGetEyeHeightSuperiors = null;
        this.overrideGetEyeHeightInferiors = null;
        this.afterGetEyeHeightSuperiors = null;
        this.afterGetEyeHeightInferiors = null;
        this.beforeGetMaxHealthSuperiors = null;
        this.beforeGetMaxHealthInferiors = null;
        this.overrideGetMaxHealthSuperiors = null;
        this.overrideGetMaxHealthInferiors = null;
        this.afterGetMaxHealthSuperiors = null;
        this.afterGetMaxHealthInferiors = null;
        this.beforeGetSpeedModifierSuperiors = null;
        this.beforeGetSpeedModifierInferiors = null;
        this.overrideGetSpeedModifierSuperiors = null;
        this.overrideGetSpeedModifierInferiors = null;
        this.afterGetSpeedModifierSuperiors = null;
        this.afterGetSpeedModifierInferiors = null;
        this.beforeHealSuperiors = null;
        this.beforeHealInferiors = null;
        this.overrideHealSuperiors = null;
        this.overrideHealInferiors = null;
        this.afterHealSuperiors = null;
        this.afterHealInferiors = null;
        this.beforeInteractSuperiors = null;
        this.beforeInteractInferiors = null;
        this.overrideInteractSuperiors = null;
        this.overrideInteractInferiors = null;
        this.afterInteractSuperiors = null;
        this.afterInteractInferiors = null;
        this.beforeIsEntityInsideOpaqueBlockSuperiors = null;
        this.beforeIsEntityInsideOpaqueBlockInferiors = null;
        this.overrideIsEntityInsideOpaqueBlockSuperiors = null;
        this.overrideIsEntityInsideOpaqueBlockInferiors = null;
        this.afterIsEntityInsideOpaqueBlockSuperiors = null;
        this.afterIsEntityInsideOpaqueBlockInferiors = null;
        this.beforeIsInWaterSuperiors = null;
        this.beforeIsInWaterInferiors = null;
        this.overrideIsInWaterSuperiors = null;
        this.overrideIsInWaterInferiors = null;
        this.afterIsInWaterSuperiors = null;
        this.afterIsInWaterInferiors = null;
        this.beforeIsInsideOfMaterialSuperiors = null;
        this.beforeIsInsideOfMaterialInferiors = null;
        this.overrideIsInsideOfMaterialSuperiors = null;
        this.overrideIsInsideOfMaterialInferiors = null;
        this.afterIsInsideOfMaterialSuperiors = null;
        this.afterIsInsideOfMaterialInferiors = null;
        this.beforeIsOnLadderSuperiors = null;
        this.beforeIsOnLadderInferiors = null;
        this.overrideIsOnLadderSuperiors = null;
        this.overrideIsOnLadderInferiors = null;
        this.afterIsOnLadderSuperiors = null;
        this.afterIsOnLadderInferiors = null;
        this.beforeIsPlayerSleepingSuperiors = null;
        this.beforeIsPlayerSleepingInferiors = null;
        this.overrideIsPlayerSleepingSuperiors = null;
        this.overrideIsPlayerSleepingInferiors = null;
        this.afterIsPlayerSleepingSuperiors = null;
        this.afterIsPlayerSleepingInferiors = null;
        this.beforeJumpSuperiors = null;
        this.beforeJumpInferiors = null;
        this.overrideJumpSuperiors = null;
        this.overrideJumpInferiors = null;
        this.afterJumpSuperiors = null;
        this.afterJumpInferiors = null;
        this.beforeKnockBackSuperiors = null;
        this.beforeKnockBackInferiors = null;
        this.overrideKnockBackSuperiors = null;
        this.overrideKnockBackInferiors = null;
        this.afterKnockBackSuperiors = null;
        this.afterKnockBackInferiors = null;
        this.beforeMoveEntitySuperiors = null;
        this.beforeMoveEntityInferiors = null;
        this.overrideMoveEntitySuperiors = null;
        this.overrideMoveEntityInferiors = null;
        this.afterMoveEntitySuperiors = null;
        this.afterMoveEntityInferiors = null;
        this.beforeMoveEntityWithHeadingSuperiors = null;
        this.beforeMoveEntityWithHeadingInferiors = null;
        this.overrideMoveEntityWithHeadingSuperiors = null;
        this.overrideMoveEntityWithHeadingInferiors = null;
        this.afterMoveEntityWithHeadingSuperiors = null;
        this.afterMoveEntityWithHeadingInferiors = null;
        this.beforeMoveFlyingSuperiors = null;
        this.beforeMoveFlyingInferiors = null;
        this.overrideMoveFlyingSuperiors = null;
        this.overrideMoveFlyingInferiors = null;
        this.afterMoveFlyingSuperiors = null;
        this.afterMoveFlyingInferiors = null;
        this.beforeOnDeathSuperiors = null;
        this.beforeOnDeathInferiors = null;
        this.overrideOnDeathSuperiors = null;
        this.overrideOnDeathInferiors = null;
        this.afterOnDeathSuperiors = null;
        this.afterOnDeathInferiors = null;
        this.beforeOnLivingUpdateSuperiors = null;
        this.beforeOnLivingUpdateInferiors = null;
        this.overrideOnLivingUpdateSuperiors = null;
        this.overrideOnLivingUpdateInferiors = null;
        this.afterOnLivingUpdateSuperiors = null;
        this.afterOnLivingUpdateInferiors = null;
        this.beforeOnKillEntitySuperiors = null;
        this.beforeOnKillEntityInferiors = null;
        this.overrideOnKillEntitySuperiors = null;
        this.overrideOnKillEntityInferiors = null;
        this.afterOnKillEntitySuperiors = null;
        this.afterOnKillEntityInferiors = null;
        this.beforeOnStruckByLightningSuperiors = null;
        this.beforeOnStruckByLightningInferiors = null;
        this.overrideOnStruckByLightningSuperiors = null;
        this.overrideOnStruckByLightningInferiors = null;
        this.afterOnStruckByLightningSuperiors = null;
        this.afterOnStruckByLightningInferiors = null;
        this.beforeOnUpdateSuperiors = null;
        this.beforeOnUpdateInferiors = null;
        this.overrideOnUpdateSuperiors = null;
        this.overrideOnUpdateInferiors = null;
        this.afterOnUpdateSuperiors = null;
        this.afterOnUpdateInferiors = null;
        this.beforeOnUpdateEntitySuperiors = null;
        this.beforeOnUpdateEntityInferiors = null;
        this.overrideOnUpdateEntitySuperiors = null;
        this.overrideOnUpdateEntityInferiors = null;
        this.afterOnUpdateEntitySuperiors = null;
        this.afterOnUpdateEntityInferiors = null;
        this.beforeReadEntityFromNBTSuperiors = null;
        this.beforeReadEntityFromNBTInferiors = null;
        this.overrideReadEntityFromNBTSuperiors = null;
        this.overrideReadEntityFromNBTInferiors = null;
        this.afterReadEntityFromNBTSuperiors = null;
        this.afterReadEntityFromNBTInferiors = null;
        this.beforeSetDeadSuperiors = null;
        this.beforeSetDeadInferiors = null;
        this.overrideSetDeadSuperiors = null;
        this.overrideSetDeadInferiors = null;
        this.afterSetDeadSuperiors = null;
        this.afterSetDeadInferiors = null;
        this.beforeSetPositionSuperiors = null;
        this.beforeSetPositionInferiors = null;
        this.overrideSetPositionSuperiors = null;
        this.overrideSetPositionInferiors = null;
        this.afterSetPositionSuperiors = null;
        this.afterSetPositionInferiors = null;
        this.beforeSwingItemSuperiors = null;
        this.beforeSwingItemInferiors = null;
        this.overrideSwingItemSuperiors = null;
        this.overrideSwingItemInferiors = null;
        this.afterSwingItemSuperiors = null;
        this.afterSwingItemInferiors = null;
        this.beforeUpdateEntityActionStateSuperiors = null;
        this.beforeUpdateEntityActionStateInferiors = null;
        this.overrideUpdateEntityActionStateSuperiors = null;
        this.overrideUpdateEntityActionStateInferiors = null;
        this.afterUpdateEntityActionStateSuperiors = null;
        this.afterUpdateEntityActionStateInferiors = null;
        this.beforeUpdatePotionEffectsSuperiors = null;
        this.beforeUpdatePotionEffectsInferiors = null;
        this.overrideUpdatePotionEffectsSuperiors = null;
        this.overrideUpdatePotionEffectsInferiors = null;
        this.afterUpdatePotionEffectsSuperiors = null;
        this.afterUpdatePotionEffectsInferiors = null;
        this.beforeWriteEntityToNBTSuperiors = null;
        this.beforeWriteEntityToNBTInferiors = null;
        this.overrideWriteEntityToNBTSuperiors = null;
        this.overrideWriteEntityToNBTInferiors = null;
        this.afterWriteEntityToNBTSuperiors = null;
        this.afterWriteEntityToNBTInferiors = null;
    }
    
    public String[] getBeforeLocalConstructingSuperiors() {
        return this.beforeLocalConstructingSuperiors;
    }
    
    public String[] getBeforeLocalConstructingInferiors() {
        return this.beforeLocalConstructingInferiors;
    }
    
    public String[] getAfterLocalConstructingSuperiors() {
        return this.afterLocalConstructingSuperiors;
    }
    
    public String[] getAfterLocalConstructingInferiors() {
        return this.afterLocalConstructingInferiors;
    }
    
    public void setBeforeLocalConstructingSuperiors(final String[] beforeLocalConstructingSuperiors) {
        this.beforeLocalConstructingSuperiors = beforeLocalConstructingSuperiors;
    }
    
    public void setBeforeLocalConstructingInferiors(final String[] beforeLocalConstructingInferiors) {
        this.beforeLocalConstructingInferiors = beforeLocalConstructingInferiors;
    }
    
    public void setAfterLocalConstructingSuperiors(final String[] afterLocalConstructingSuperiors) {
        this.afterLocalConstructingSuperiors = afterLocalConstructingSuperiors;
    }
    
    public void setAfterLocalConstructingInferiors(final String[] afterLocalConstructingInferiors) {
        this.afterLocalConstructingInferiors = afterLocalConstructingInferiors;
    }
    
    public Map<String, String[]> getDynamicBeforeSuperiors() {
        return this.dynamicBeforeSuperiors;
    }
    
    public Map<String, String[]> getDynamicBeforeInferiors() {
        return this.dynamicBeforeInferiors;
    }
    
    public Map<String, String[]> getDynamicOverrideSuperiors() {
        return this.dynamicOverrideSuperiors;
    }
    
    public Map<String, String[]> getDynamicOverrideInferiors() {
        return this.dynamicOverrideInferiors;
    }
    
    public Map<String, String[]> getDynamicAfterSuperiors() {
        return this.dynamicAfterSuperiors;
    }
    
    public Map<String, String[]> getDynamicAfterInferiors() {
        return this.dynamicAfterInferiors;
    }
    
    public void setDynamicBeforeSuperiors(final String s, final String[] array) {
        this.dynamicBeforeSuperiors = this.setDynamic(s, array, this.dynamicBeforeSuperiors);
    }
    
    public void setDynamicBeforeInferiors(final String s, final String[] array) {
        this.dynamicBeforeInferiors = this.setDynamic(s, array, this.dynamicBeforeInferiors);
    }
    
    public void setDynamicOverrideSuperiors(final String s, final String[] array) {
        this.dynamicOverrideSuperiors = this.setDynamic(s, array, this.dynamicOverrideSuperiors);
    }
    
    public void setDynamicOverrideInferiors(final String s, final String[] array) {
        this.dynamicOverrideInferiors = this.setDynamic(s, array, this.dynamicOverrideInferiors);
    }
    
    public void setDynamicAfterSuperiors(final String s, final String[] array) {
        this.dynamicAfterSuperiors = this.setDynamic(s, array, this.dynamicAfterSuperiors);
    }
    
    public void setDynamicAfterInferiors(final String s, final String[] array) {
        this.dynamicAfterInferiors = this.setDynamic(s, array, this.dynamicAfterInferiors);
    }
    
    private Map<String, String[]> setDynamic(final String s, final String[] array, Map<String, String[]> hashMap) {
        if (s == null) {
            throw new IllegalArgumentException("Parameter 'name' may not be null");
        }
        if (array == null) {
            if (hashMap != null) {
                hashMap.remove(s);
            }
            return hashMap;
        }
        if (hashMap == null) {
            hashMap = new HashMap<String, String[]>();
        }
        hashMap.put(s, array);
        return hashMap;
    }
    
    public String[] getBeforeAddExhaustionSuperiors() {
        return this.beforeAddExhaustionSuperiors;
    }
    
    public String[] getBeforeAddExhaustionInferiors() {
        return this.beforeAddExhaustionInferiors;
    }
    
    public String[] getOverrideAddExhaustionSuperiors() {
        return this.overrideAddExhaustionSuperiors;
    }
    
    public String[] getOverrideAddExhaustionInferiors() {
        return this.overrideAddExhaustionInferiors;
    }
    
    public String[] getAfterAddExhaustionSuperiors() {
        return this.afterAddExhaustionSuperiors;
    }
    
    public String[] getAfterAddExhaustionInferiors() {
        return this.afterAddExhaustionInferiors;
    }
    
    public void setBeforeAddExhaustionSuperiors(final String[] beforeAddExhaustionSuperiors) {
        this.beforeAddExhaustionSuperiors = beforeAddExhaustionSuperiors;
    }
    
    public void setBeforeAddExhaustionInferiors(final String[] beforeAddExhaustionInferiors) {
        this.beforeAddExhaustionInferiors = beforeAddExhaustionInferiors;
    }
    
    public void setOverrideAddExhaustionSuperiors(final String[] overrideAddExhaustionSuperiors) {
        this.overrideAddExhaustionSuperiors = overrideAddExhaustionSuperiors;
    }
    
    public void setOverrideAddExhaustionInferiors(final String[] overrideAddExhaustionInferiors) {
        this.overrideAddExhaustionInferiors = overrideAddExhaustionInferiors;
    }
    
    public void setAfterAddExhaustionSuperiors(final String[] afterAddExhaustionSuperiors) {
        this.afterAddExhaustionSuperiors = afterAddExhaustionSuperiors;
    }
    
    public void setAfterAddExhaustionInferiors(final String[] afterAddExhaustionInferiors) {
        this.afterAddExhaustionInferiors = afterAddExhaustionInferiors;
    }
    
    public String[] getBeforeAddExperienceSuperiors() {
        return this.beforeAddExperienceSuperiors;
    }
    
    public String[] getBeforeAddExperienceInferiors() {
        return this.beforeAddExperienceInferiors;
    }
    
    public String[] getOverrideAddExperienceSuperiors() {
        return this.overrideAddExperienceSuperiors;
    }
    
    public String[] getOverrideAddExperienceInferiors() {
        return this.overrideAddExperienceInferiors;
    }
    
    public String[] getAfterAddExperienceSuperiors() {
        return this.afterAddExperienceSuperiors;
    }
    
    public String[] getAfterAddExperienceInferiors() {
        return this.afterAddExperienceInferiors;
    }
    
    public void setBeforeAddExperienceSuperiors(final String[] beforeAddExperienceSuperiors) {
        this.beforeAddExperienceSuperiors = beforeAddExperienceSuperiors;
    }
    
    public void setBeforeAddExperienceInferiors(final String[] beforeAddExperienceInferiors) {
        this.beforeAddExperienceInferiors = beforeAddExperienceInferiors;
    }
    
    public void setOverrideAddExperienceSuperiors(final String[] overrideAddExperienceSuperiors) {
        this.overrideAddExperienceSuperiors = overrideAddExperienceSuperiors;
    }
    
    public void setOverrideAddExperienceInferiors(final String[] overrideAddExperienceInferiors) {
        this.overrideAddExperienceInferiors = overrideAddExperienceInferiors;
    }
    
    public void setAfterAddExperienceSuperiors(final String[] afterAddExperienceSuperiors) {
        this.afterAddExperienceSuperiors = afterAddExperienceSuperiors;
    }
    
    public void setAfterAddExperienceInferiors(final String[] afterAddExperienceInferiors) {
        this.afterAddExperienceInferiors = afterAddExperienceInferiors;
    }
    
    public String[] getBeforeAddExperienceLevelSuperiors() {
        return this.beforeAddExperienceLevelSuperiors;
    }
    
    public String[] getBeforeAddExperienceLevelInferiors() {
        return this.beforeAddExperienceLevelInferiors;
    }
    
    public String[] getOverrideAddExperienceLevelSuperiors() {
        return this.overrideAddExperienceLevelSuperiors;
    }
    
    public String[] getOverrideAddExperienceLevelInferiors() {
        return this.overrideAddExperienceLevelInferiors;
    }
    
    public String[] getAfterAddExperienceLevelSuperiors() {
        return this.afterAddExperienceLevelSuperiors;
    }
    
    public String[] getAfterAddExperienceLevelInferiors() {
        return this.afterAddExperienceLevelInferiors;
    }
    
    public void setBeforeAddExperienceLevelSuperiors(final String[] beforeAddExperienceLevelSuperiors) {
        this.beforeAddExperienceLevelSuperiors = beforeAddExperienceLevelSuperiors;
    }
    
    public void setBeforeAddExperienceLevelInferiors(final String[] beforeAddExperienceLevelInferiors) {
        this.beforeAddExperienceLevelInferiors = beforeAddExperienceLevelInferiors;
    }
    
    public void setOverrideAddExperienceLevelSuperiors(final String[] overrideAddExperienceLevelSuperiors) {
        this.overrideAddExperienceLevelSuperiors = overrideAddExperienceLevelSuperiors;
    }
    
    public void setOverrideAddExperienceLevelInferiors(final String[] overrideAddExperienceLevelInferiors) {
        this.overrideAddExperienceLevelInferiors = overrideAddExperienceLevelInferiors;
    }
    
    public void setAfterAddExperienceLevelSuperiors(final String[] afterAddExperienceLevelSuperiors) {
        this.afterAddExperienceLevelSuperiors = afterAddExperienceLevelSuperiors;
    }
    
    public void setAfterAddExperienceLevelInferiors(final String[] afterAddExperienceLevelInferiors) {
        this.afterAddExperienceLevelInferiors = afterAddExperienceLevelInferiors;
    }
    
    public String[] getBeforeAddMovementStatSuperiors() {
        return this.beforeAddMovementStatSuperiors;
    }
    
    public String[] getBeforeAddMovementStatInferiors() {
        return this.beforeAddMovementStatInferiors;
    }
    
    public String[] getOverrideAddMovementStatSuperiors() {
        return this.overrideAddMovementStatSuperiors;
    }
    
    public String[] getOverrideAddMovementStatInferiors() {
        return this.overrideAddMovementStatInferiors;
    }
    
    public String[] getAfterAddMovementStatSuperiors() {
        return this.afterAddMovementStatSuperiors;
    }
    
    public String[] getAfterAddMovementStatInferiors() {
        return this.afterAddMovementStatInferiors;
    }
    
    public void setBeforeAddMovementStatSuperiors(final String[] beforeAddMovementStatSuperiors) {
        this.beforeAddMovementStatSuperiors = beforeAddMovementStatSuperiors;
    }
    
    public void setBeforeAddMovementStatInferiors(final String[] beforeAddMovementStatInferiors) {
        this.beforeAddMovementStatInferiors = beforeAddMovementStatInferiors;
    }
    
    public void setOverrideAddMovementStatSuperiors(final String[] overrideAddMovementStatSuperiors) {
        this.overrideAddMovementStatSuperiors = overrideAddMovementStatSuperiors;
    }
    
    public void setOverrideAddMovementStatInferiors(final String[] overrideAddMovementStatInferiors) {
        this.overrideAddMovementStatInferiors = overrideAddMovementStatInferiors;
    }
    
    public void setAfterAddMovementStatSuperiors(final String[] afterAddMovementStatSuperiors) {
        this.afterAddMovementStatSuperiors = afterAddMovementStatSuperiors;
    }
    
    public void setAfterAddMovementStatInferiors(final String[] afterAddMovementStatInferiors) {
        this.afterAddMovementStatInferiors = afterAddMovementStatInferiors;
    }
    
    public String[] getBeforeAttackEntityFromSuperiors() {
        return this.beforeAttackEntityFromSuperiors;
    }
    
    public String[] getBeforeAttackEntityFromInferiors() {
        return this.beforeAttackEntityFromInferiors;
    }
    
    public String[] getOverrideAttackEntityFromSuperiors() {
        return this.overrideAttackEntityFromSuperiors;
    }
    
    public String[] getOverrideAttackEntityFromInferiors() {
        return this.overrideAttackEntityFromInferiors;
    }
    
    public String[] getAfterAttackEntityFromSuperiors() {
        return this.afterAttackEntityFromSuperiors;
    }
    
    public String[] getAfterAttackEntityFromInferiors() {
        return this.afterAttackEntityFromInferiors;
    }
    
    public void setBeforeAttackEntityFromSuperiors(final String[] beforeAttackEntityFromSuperiors) {
        this.beforeAttackEntityFromSuperiors = beforeAttackEntityFromSuperiors;
    }
    
    public void setBeforeAttackEntityFromInferiors(final String[] beforeAttackEntityFromInferiors) {
        this.beforeAttackEntityFromInferiors = beforeAttackEntityFromInferiors;
    }
    
    public void setOverrideAttackEntityFromSuperiors(final String[] overrideAttackEntityFromSuperiors) {
        this.overrideAttackEntityFromSuperiors = overrideAttackEntityFromSuperiors;
    }
    
    public void setOverrideAttackEntityFromInferiors(final String[] overrideAttackEntityFromInferiors) {
        this.overrideAttackEntityFromInferiors = overrideAttackEntityFromInferiors;
    }
    
    public void setAfterAttackEntityFromSuperiors(final String[] afterAttackEntityFromSuperiors) {
        this.afterAttackEntityFromSuperiors = afterAttackEntityFromSuperiors;
    }
    
    public void setAfterAttackEntityFromInferiors(final String[] afterAttackEntityFromInferiors) {
        this.afterAttackEntityFromInferiors = afterAttackEntityFromInferiors;
    }
    
    public String[] getBeforeAttackTargetEntityWithCurrentItemSuperiors() {
        return this.beforeAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public String[] getBeforeAttackTargetEntityWithCurrentItemInferiors() {
        return this.beforeAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public String[] getOverrideAttackTargetEntityWithCurrentItemSuperiors() {
        return this.overrideAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public String[] getOverrideAttackTargetEntityWithCurrentItemInferiors() {
        return this.overrideAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public String[] getAfterAttackTargetEntityWithCurrentItemSuperiors() {
        return this.afterAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public String[] getAfterAttackTargetEntityWithCurrentItemInferiors() {
        return this.afterAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public void setBeforeAttackTargetEntityWithCurrentItemSuperiors(final String[] beforeAttackTargetEntityWithCurrentItemSuperiors) {
        this.beforeAttackTargetEntityWithCurrentItemSuperiors = beforeAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public void setBeforeAttackTargetEntityWithCurrentItemInferiors(final String[] beforeAttackTargetEntityWithCurrentItemInferiors) {
        this.beforeAttackTargetEntityWithCurrentItemInferiors = beforeAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public void setOverrideAttackTargetEntityWithCurrentItemSuperiors(final String[] overrideAttackTargetEntityWithCurrentItemSuperiors) {
        this.overrideAttackTargetEntityWithCurrentItemSuperiors = overrideAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public void setOverrideAttackTargetEntityWithCurrentItemInferiors(final String[] overrideAttackTargetEntityWithCurrentItemInferiors) {
        this.overrideAttackTargetEntityWithCurrentItemInferiors = overrideAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public void setAfterAttackTargetEntityWithCurrentItemSuperiors(final String[] afterAttackTargetEntityWithCurrentItemSuperiors) {
        this.afterAttackTargetEntityWithCurrentItemSuperiors = afterAttackTargetEntityWithCurrentItemSuperiors;
    }
    
    public void setAfterAttackTargetEntityWithCurrentItemInferiors(final String[] afterAttackTargetEntityWithCurrentItemInferiors) {
        this.afterAttackTargetEntityWithCurrentItemInferiors = afterAttackTargetEntityWithCurrentItemInferiors;
    }
    
    public String[] getBeforeCanHarvestBlockSuperiors() {
        return this.beforeCanHarvestBlockSuperiors;
    }
    
    public String[] getBeforeCanHarvestBlockInferiors() {
        return this.beforeCanHarvestBlockInferiors;
    }
    
    public String[] getOverrideCanHarvestBlockSuperiors() {
        return this.overrideCanHarvestBlockSuperiors;
    }
    
    public String[] getOverrideCanHarvestBlockInferiors() {
        return this.overrideCanHarvestBlockInferiors;
    }
    
    public String[] getAfterCanHarvestBlockSuperiors() {
        return this.afterCanHarvestBlockSuperiors;
    }
    
    public String[] getAfterCanHarvestBlockInferiors() {
        return this.afterCanHarvestBlockInferiors;
    }
    
    public void setBeforeCanHarvestBlockSuperiors(final String[] beforeCanHarvestBlockSuperiors) {
        this.beforeCanHarvestBlockSuperiors = beforeCanHarvestBlockSuperiors;
    }
    
    public void setBeforeCanHarvestBlockInferiors(final String[] beforeCanHarvestBlockInferiors) {
        this.beforeCanHarvestBlockInferiors = beforeCanHarvestBlockInferiors;
    }
    
    public void setOverrideCanHarvestBlockSuperiors(final String[] overrideCanHarvestBlockSuperiors) {
        this.overrideCanHarvestBlockSuperiors = overrideCanHarvestBlockSuperiors;
    }
    
    public void setOverrideCanHarvestBlockInferiors(final String[] overrideCanHarvestBlockInferiors) {
        this.overrideCanHarvestBlockInferiors = overrideCanHarvestBlockInferiors;
    }
    
    public void setAfterCanHarvestBlockSuperiors(final String[] afterCanHarvestBlockSuperiors) {
        this.afterCanHarvestBlockSuperiors = afterCanHarvestBlockSuperiors;
    }
    
    public void setAfterCanHarvestBlockInferiors(final String[] afterCanHarvestBlockInferiors) {
        this.afterCanHarvestBlockInferiors = afterCanHarvestBlockInferiors;
    }
    
    public String[] getBeforeCanPlayerEditSuperiors() {
        return this.beforeCanPlayerEditSuperiors;
    }
    
    public String[] getBeforeCanPlayerEditInferiors() {
        return this.beforeCanPlayerEditInferiors;
    }
    
    public String[] getOverrideCanPlayerEditSuperiors() {
        return this.overrideCanPlayerEditSuperiors;
    }
    
    public String[] getOverrideCanPlayerEditInferiors() {
        return this.overrideCanPlayerEditInferiors;
    }
    
    public String[] getAfterCanPlayerEditSuperiors() {
        return this.afterCanPlayerEditSuperiors;
    }
    
    public String[] getAfterCanPlayerEditInferiors() {
        return this.afterCanPlayerEditInferiors;
    }
    
    public void setBeforeCanPlayerEditSuperiors(final String[] beforeCanPlayerEditSuperiors) {
        this.beforeCanPlayerEditSuperiors = beforeCanPlayerEditSuperiors;
    }
    
    public void setBeforeCanPlayerEditInferiors(final String[] beforeCanPlayerEditInferiors) {
        this.beforeCanPlayerEditInferiors = beforeCanPlayerEditInferiors;
    }
    
    public void setOverrideCanPlayerEditSuperiors(final String[] overrideCanPlayerEditSuperiors) {
        this.overrideCanPlayerEditSuperiors = overrideCanPlayerEditSuperiors;
    }
    
    public void setOverrideCanPlayerEditInferiors(final String[] overrideCanPlayerEditInferiors) {
        this.overrideCanPlayerEditInferiors = overrideCanPlayerEditInferiors;
    }
    
    public void setAfterCanPlayerEditSuperiors(final String[] afterCanPlayerEditSuperiors) {
        this.afterCanPlayerEditSuperiors = afterCanPlayerEditSuperiors;
    }
    
    public void setAfterCanPlayerEditInferiors(final String[] afterCanPlayerEditInferiors) {
        this.afterCanPlayerEditInferiors = afterCanPlayerEditInferiors;
    }
    
    public String[] getBeforeCanTriggerWalkingSuperiors() {
        return this.beforeCanTriggerWalkingSuperiors;
    }
    
    public String[] getBeforeCanTriggerWalkingInferiors() {
        return this.beforeCanTriggerWalkingInferiors;
    }
    
    public String[] getOverrideCanTriggerWalkingSuperiors() {
        return this.overrideCanTriggerWalkingSuperiors;
    }
    
    public String[] getOverrideCanTriggerWalkingInferiors() {
        return this.overrideCanTriggerWalkingInferiors;
    }
    
    public String[] getAfterCanTriggerWalkingSuperiors() {
        return this.afterCanTriggerWalkingSuperiors;
    }
    
    public String[] getAfterCanTriggerWalkingInferiors() {
        return this.afterCanTriggerWalkingInferiors;
    }
    
    public void setBeforeCanTriggerWalkingSuperiors(final String[] beforeCanTriggerWalkingSuperiors) {
        this.beforeCanTriggerWalkingSuperiors = beforeCanTriggerWalkingSuperiors;
    }
    
    public void setBeforeCanTriggerWalkingInferiors(final String[] beforeCanTriggerWalkingInferiors) {
        this.beforeCanTriggerWalkingInferiors = beforeCanTriggerWalkingInferiors;
    }
    
    public void setOverrideCanTriggerWalkingSuperiors(final String[] overrideCanTriggerWalkingSuperiors) {
        this.overrideCanTriggerWalkingSuperiors = overrideCanTriggerWalkingSuperiors;
    }
    
    public void setOverrideCanTriggerWalkingInferiors(final String[] overrideCanTriggerWalkingInferiors) {
        this.overrideCanTriggerWalkingInferiors = overrideCanTriggerWalkingInferiors;
    }
    
    public void setAfterCanTriggerWalkingSuperiors(final String[] afterCanTriggerWalkingSuperiors) {
        this.afterCanTriggerWalkingSuperiors = afterCanTriggerWalkingSuperiors;
    }
    
    public void setAfterCanTriggerWalkingInferiors(final String[] afterCanTriggerWalkingInferiors) {
        this.afterCanTriggerWalkingInferiors = afterCanTriggerWalkingInferiors;
    }
    
    public String[] getBeforeClonePlayerSuperiors() {
        return this.beforeClonePlayerSuperiors;
    }
    
    public String[] getBeforeClonePlayerInferiors() {
        return this.beforeClonePlayerInferiors;
    }
    
    public String[] getOverrideClonePlayerSuperiors() {
        return this.overrideClonePlayerSuperiors;
    }
    
    public String[] getOverrideClonePlayerInferiors() {
        return this.overrideClonePlayerInferiors;
    }
    
    public String[] getAfterClonePlayerSuperiors() {
        return this.afterClonePlayerSuperiors;
    }
    
    public String[] getAfterClonePlayerInferiors() {
        return this.afterClonePlayerInferiors;
    }
    
    public void setBeforeClonePlayerSuperiors(final String[] beforeClonePlayerSuperiors) {
        this.beforeClonePlayerSuperiors = beforeClonePlayerSuperiors;
    }
    
    public void setBeforeClonePlayerInferiors(final String[] beforeClonePlayerInferiors) {
        this.beforeClonePlayerInferiors = beforeClonePlayerInferiors;
    }
    
    public void setOverrideClonePlayerSuperiors(final String[] overrideClonePlayerSuperiors) {
        this.overrideClonePlayerSuperiors = overrideClonePlayerSuperiors;
    }
    
    public void setOverrideClonePlayerInferiors(final String[] overrideClonePlayerInferiors) {
        this.overrideClonePlayerInferiors = overrideClonePlayerInferiors;
    }
    
    public void setAfterClonePlayerSuperiors(final String[] afterClonePlayerSuperiors) {
        this.afterClonePlayerSuperiors = afterClonePlayerSuperiors;
    }
    
    public void setAfterClonePlayerInferiors(final String[] afterClonePlayerInferiors) {
        this.afterClonePlayerInferiors = afterClonePlayerInferiors;
    }
    
    public String[] getBeforeDamageEntitySuperiors() {
        return this.beforeDamageEntitySuperiors;
    }
    
    public String[] getBeforeDamageEntityInferiors() {
        return this.beforeDamageEntityInferiors;
    }
    
    public String[] getOverrideDamageEntitySuperiors() {
        return this.overrideDamageEntitySuperiors;
    }
    
    public String[] getOverrideDamageEntityInferiors() {
        return this.overrideDamageEntityInferiors;
    }
    
    public String[] getAfterDamageEntitySuperiors() {
        return this.afterDamageEntitySuperiors;
    }
    
    public String[] getAfterDamageEntityInferiors() {
        return this.afterDamageEntityInferiors;
    }
    
    public void setBeforeDamageEntitySuperiors(final String[] beforeDamageEntitySuperiors) {
        this.beforeDamageEntitySuperiors = beforeDamageEntitySuperiors;
    }
    
    public void setBeforeDamageEntityInferiors(final String[] beforeDamageEntityInferiors) {
        this.beforeDamageEntityInferiors = beforeDamageEntityInferiors;
    }
    
    public void setOverrideDamageEntitySuperiors(final String[] overrideDamageEntitySuperiors) {
        this.overrideDamageEntitySuperiors = overrideDamageEntitySuperiors;
    }
    
    public void setOverrideDamageEntityInferiors(final String[] overrideDamageEntityInferiors) {
        this.overrideDamageEntityInferiors = overrideDamageEntityInferiors;
    }
    
    public void setAfterDamageEntitySuperiors(final String[] afterDamageEntitySuperiors) {
        this.afterDamageEntitySuperiors = afterDamageEntitySuperiors;
    }
    
    public void setAfterDamageEntityInferiors(final String[] afterDamageEntityInferiors) {
        this.afterDamageEntityInferiors = afterDamageEntityInferiors;
    }
    
    public String[] getBeforeDisplayGUIChestSuperiors() {
        return this.beforeDisplayGUIChestSuperiors;
    }
    
    public String[] getBeforeDisplayGUIChestInferiors() {
        return this.beforeDisplayGUIChestInferiors;
    }
    
    public String[] getOverrideDisplayGUIChestSuperiors() {
        return this.overrideDisplayGUIChestSuperiors;
    }
    
    public String[] getOverrideDisplayGUIChestInferiors() {
        return this.overrideDisplayGUIChestInferiors;
    }
    
    public String[] getAfterDisplayGUIChestSuperiors() {
        return this.afterDisplayGUIChestSuperiors;
    }
    
    public String[] getAfterDisplayGUIChestInferiors() {
        return this.afterDisplayGUIChestInferiors;
    }
    
    public void setBeforeDisplayGUIChestSuperiors(final String[] beforeDisplayGUIChestSuperiors) {
        this.beforeDisplayGUIChestSuperiors = beforeDisplayGUIChestSuperiors;
    }
    
    public void setBeforeDisplayGUIChestInferiors(final String[] beforeDisplayGUIChestInferiors) {
        this.beforeDisplayGUIChestInferiors = beforeDisplayGUIChestInferiors;
    }
    
    public void setOverrideDisplayGUIChestSuperiors(final String[] overrideDisplayGUIChestSuperiors) {
        this.overrideDisplayGUIChestSuperiors = overrideDisplayGUIChestSuperiors;
    }
    
    public void setOverrideDisplayGUIChestInferiors(final String[] overrideDisplayGUIChestInferiors) {
        this.overrideDisplayGUIChestInferiors = overrideDisplayGUIChestInferiors;
    }
    
    public void setAfterDisplayGUIChestSuperiors(final String[] afterDisplayGUIChestSuperiors) {
        this.afterDisplayGUIChestSuperiors = afterDisplayGUIChestSuperiors;
    }
    
    public void setAfterDisplayGUIChestInferiors(final String[] afterDisplayGUIChestInferiors) {
        this.afterDisplayGUIChestInferiors = afterDisplayGUIChestInferiors;
    }
    
    public String[] getBeforeDisplayGUIDispenserSuperiors() {
        return this.beforeDisplayGUIDispenserSuperiors;
    }
    
    public String[] getBeforeDisplayGUIDispenserInferiors() {
        return this.beforeDisplayGUIDispenserInferiors;
    }
    
    public String[] getOverrideDisplayGUIDispenserSuperiors() {
        return this.overrideDisplayGUIDispenserSuperiors;
    }
    
    public String[] getOverrideDisplayGUIDispenserInferiors() {
        return this.overrideDisplayGUIDispenserInferiors;
    }
    
    public String[] getAfterDisplayGUIDispenserSuperiors() {
        return this.afterDisplayGUIDispenserSuperiors;
    }
    
    public String[] getAfterDisplayGUIDispenserInferiors() {
        return this.afterDisplayGUIDispenserInferiors;
    }
    
    public void setBeforeDisplayGUIDispenserSuperiors(final String[] beforeDisplayGUIDispenserSuperiors) {
        this.beforeDisplayGUIDispenserSuperiors = beforeDisplayGUIDispenserSuperiors;
    }
    
    public void setBeforeDisplayGUIDispenserInferiors(final String[] beforeDisplayGUIDispenserInferiors) {
        this.beforeDisplayGUIDispenserInferiors = beforeDisplayGUIDispenserInferiors;
    }
    
    public void setOverrideDisplayGUIDispenserSuperiors(final String[] overrideDisplayGUIDispenserSuperiors) {
        this.overrideDisplayGUIDispenserSuperiors = overrideDisplayGUIDispenserSuperiors;
    }
    
    public void setOverrideDisplayGUIDispenserInferiors(final String[] overrideDisplayGUIDispenserInferiors) {
        this.overrideDisplayGUIDispenserInferiors = overrideDisplayGUIDispenserInferiors;
    }
    
    public void setAfterDisplayGUIDispenserSuperiors(final String[] afterDisplayGUIDispenserSuperiors) {
        this.afterDisplayGUIDispenserSuperiors = afterDisplayGUIDispenserSuperiors;
    }
    
    public void setAfterDisplayGUIDispenserInferiors(final String[] afterDisplayGUIDispenserInferiors) {
        this.afterDisplayGUIDispenserInferiors = afterDisplayGUIDispenserInferiors;
    }
    
    public String[] getBeforeDisplayGUIFurnaceSuperiors() {
        return this.beforeDisplayGUIFurnaceSuperiors;
    }
    
    public String[] getBeforeDisplayGUIFurnaceInferiors() {
        return this.beforeDisplayGUIFurnaceInferiors;
    }
    
    public String[] getOverrideDisplayGUIFurnaceSuperiors() {
        return this.overrideDisplayGUIFurnaceSuperiors;
    }
    
    public String[] getOverrideDisplayGUIFurnaceInferiors() {
        return this.overrideDisplayGUIFurnaceInferiors;
    }
    
    public String[] getAfterDisplayGUIFurnaceSuperiors() {
        return this.afterDisplayGUIFurnaceSuperiors;
    }
    
    public String[] getAfterDisplayGUIFurnaceInferiors() {
        return this.afterDisplayGUIFurnaceInferiors;
    }
    
    public void setBeforeDisplayGUIFurnaceSuperiors(final String[] beforeDisplayGUIFurnaceSuperiors) {
        this.beforeDisplayGUIFurnaceSuperiors = beforeDisplayGUIFurnaceSuperiors;
    }
    
    public void setBeforeDisplayGUIFurnaceInferiors(final String[] beforeDisplayGUIFurnaceInferiors) {
        this.beforeDisplayGUIFurnaceInferiors = beforeDisplayGUIFurnaceInferiors;
    }
    
    public void setOverrideDisplayGUIFurnaceSuperiors(final String[] overrideDisplayGUIFurnaceSuperiors) {
        this.overrideDisplayGUIFurnaceSuperiors = overrideDisplayGUIFurnaceSuperiors;
    }
    
    public void setOverrideDisplayGUIFurnaceInferiors(final String[] overrideDisplayGUIFurnaceInferiors) {
        this.overrideDisplayGUIFurnaceInferiors = overrideDisplayGUIFurnaceInferiors;
    }
    
    public void setAfterDisplayGUIFurnaceSuperiors(final String[] afterDisplayGUIFurnaceSuperiors) {
        this.afterDisplayGUIFurnaceSuperiors = afterDisplayGUIFurnaceSuperiors;
    }
    
    public void setAfterDisplayGUIFurnaceInferiors(final String[] afterDisplayGUIFurnaceInferiors) {
        this.afterDisplayGUIFurnaceInferiors = afterDisplayGUIFurnaceInferiors;
    }
    
    public String[] getBeforeDisplayGUIWorkbenchSuperiors() {
        return this.beforeDisplayGUIWorkbenchSuperiors;
    }
    
    public String[] getBeforeDisplayGUIWorkbenchInferiors() {
        return this.beforeDisplayGUIWorkbenchInferiors;
    }
    
    public String[] getOverrideDisplayGUIWorkbenchSuperiors() {
        return this.overrideDisplayGUIWorkbenchSuperiors;
    }
    
    public String[] getOverrideDisplayGUIWorkbenchInferiors() {
        return this.overrideDisplayGUIWorkbenchInferiors;
    }
    
    public String[] getAfterDisplayGUIWorkbenchSuperiors() {
        return this.afterDisplayGUIWorkbenchSuperiors;
    }
    
    public String[] getAfterDisplayGUIWorkbenchInferiors() {
        return this.afterDisplayGUIWorkbenchInferiors;
    }
    
    public void setBeforeDisplayGUIWorkbenchSuperiors(final String[] beforeDisplayGUIWorkbenchSuperiors) {
        this.beforeDisplayGUIWorkbenchSuperiors = beforeDisplayGUIWorkbenchSuperiors;
    }
    
    public void setBeforeDisplayGUIWorkbenchInferiors(final String[] beforeDisplayGUIWorkbenchInferiors) {
        this.beforeDisplayGUIWorkbenchInferiors = beforeDisplayGUIWorkbenchInferiors;
    }
    
    public void setOverrideDisplayGUIWorkbenchSuperiors(final String[] overrideDisplayGUIWorkbenchSuperiors) {
        this.overrideDisplayGUIWorkbenchSuperiors = overrideDisplayGUIWorkbenchSuperiors;
    }
    
    public void setOverrideDisplayGUIWorkbenchInferiors(final String[] overrideDisplayGUIWorkbenchInferiors) {
        this.overrideDisplayGUIWorkbenchInferiors = overrideDisplayGUIWorkbenchInferiors;
    }
    
    public void setAfterDisplayGUIWorkbenchSuperiors(final String[] afterDisplayGUIWorkbenchSuperiors) {
        this.afterDisplayGUIWorkbenchSuperiors = afterDisplayGUIWorkbenchSuperiors;
    }
    
    public void setAfterDisplayGUIWorkbenchInferiors(final String[] afterDisplayGUIWorkbenchInferiors) {
        this.afterDisplayGUIWorkbenchInferiors = afterDisplayGUIWorkbenchInferiors;
    }
    
    public String[] getBeforeDropOneItemSuperiors() {
        return this.beforeDropOneItemSuperiors;
    }
    
    public String[] getBeforeDropOneItemInferiors() {
        return this.beforeDropOneItemInferiors;
    }
    
    public String[] getOverrideDropOneItemSuperiors() {
        return this.overrideDropOneItemSuperiors;
    }
    
    public String[] getOverrideDropOneItemInferiors() {
        return this.overrideDropOneItemInferiors;
    }
    
    public String[] getAfterDropOneItemSuperiors() {
        return this.afterDropOneItemSuperiors;
    }
    
    public String[] getAfterDropOneItemInferiors() {
        return this.afterDropOneItemInferiors;
    }
    
    public void setBeforeDropOneItemSuperiors(final String[] beforeDropOneItemSuperiors) {
        this.beforeDropOneItemSuperiors = beforeDropOneItemSuperiors;
    }
    
    public void setBeforeDropOneItemInferiors(final String[] beforeDropOneItemInferiors) {
        this.beforeDropOneItemInferiors = beforeDropOneItemInferiors;
    }
    
    public void setOverrideDropOneItemSuperiors(final String[] overrideDropOneItemSuperiors) {
        this.overrideDropOneItemSuperiors = overrideDropOneItemSuperiors;
    }
    
    public void setOverrideDropOneItemInferiors(final String[] overrideDropOneItemInferiors) {
        this.overrideDropOneItemInferiors = overrideDropOneItemInferiors;
    }
    
    public void setAfterDropOneItemSuperiors(final String[] afterDropOneItemSuperiors) {
        this.afterDropOneItemSuperiors = afterDropOneItemSuperiors;
    }
    
    public void setAfterDropOneItemInferiors(final String[] afterDropOneItemInferiors) {
        this.afterDropOneItemInferiors = afterDropOneItemInferiors;
    }
    
    public String[] getBeforeDropPlayerItemSuperiors() {
        return this.beforeDropPlayerItemSuperiors;
    }
    
    public String[] getBeforeDropPlayerItemInferiors() {
        return this.beforeDropPlayerItemInferiors;
    }
    
    public String[] getOverrideDropPlayerItemSuperiors() {
        return this.overrideDropPlayerItemSuperiors;
    }
    
    public String[] getOverrideDropPlayerItemInferiors() {
        return this.overrideDropPlayerItemInferiors;
    }
    
    public String[] getAfterDropPlayerItemSuperiors() {
        return this.afterDropPlayerItemSuperiors;
    }
    
    public String[] getAfterDropPlayerItemInferiors() {
        return this.afterDropPlayerItemInferiors;
    }
    
    public void setBeforeDropPlayerItemSuperiors(final String[] beforeDropPlayerItemSuperiors) {
        this.beforeDropPlayerItemSuperiors = beforeDropPlayerItemSuperiors;
    }
    
    public void setBeforeDropPlayerItemInferiors(final String[] beforeDropPlayerItemInferiors) {
        this.beforeDropPlayerItemInferiors = beforeDropPlayerItemInferiors;
    }
    
    public void setOverrideDropPlayerItemSuperiors(final String[] overrideDropPlayerItemSuperiors) {
        this.overrideDropPlayerItemSuperiors = overrideDropPlayerItemSuperiors;
    }
    
    public void setOverrideDropPlayerItemInferiors(final String[] overrideDropPlayerItemInferiors) {
        this.overrideDropPlayerItemInferiors = overrideDropPlayerItemInferiors;
    }
    
    public void setAfterDropPlayerItemSuperiors(final String[] afterDropPlayerItemSuperiors) {
        this.afterDropPlayerItemSuperiors = afterDropPlayerItemSuperiors;
    }
    
    public void setAfterDropPlayerItemInferiors(final String[] afterDropPlayerItemInferiors) {
        this.afterDropPlayerItemInferiors = afterDropPlayerItemInferiors;
    }
    
    public String[] getBeforeFallSuperiors() {
        return this.beforeFallSuperiors;
    }
    
    public String[] getBeforeFallInferiors() {
        return this.beforeFallInferiors;
    }
    
    public String[] getOverrideFallSuperiors() {
        return this.overrideFallSuperiors;
    }
    
    public String[] getOverrideFallInferiors() {
        return this.overrideFallInferiors;
    }
    
    public String[] getAfterFallSuperiors() {
        return this.afterFallSuperiors;
    }
    
    public String[] getAfterFallInferiors() {
        return this.afterFallInferiors;
    }
    
    public void setBeforeFallSuperiors(final String[] beforeFallSuperiors) {
        this.beforeFallSuperiors = beforeFallSuperiors;
    }
    
    public void setBeforeFallInferiors(final String[] beforeFallInferiors) {
        this.beforeFallInferiors = beforeFallInferiors;
    }
    
    public void setOverrideFallSuperiors(final String[] overrideFallSuperiors) {
        this.overrideFallSuperiors = overrideFallSuperiors;
    }
    
    public void setOverrideFallInferiors(final String[] overrideFallInferiors) {
        this.overrideFallInferiors = overrideFallInferiors;
    }
    
    public void setAfterFallSuperiors(final String[] afterFallSuperiors) {
        this.afterFallSuperiors = afterFallSuperiors;
    }
    
    public void setAfterFallInferiors(final String[] afterFallInferiors) {
        this.afterFallInferiors = afterFallInferiors;
    }
    
    public String[] getBeforeGetCurrentPlayerStrVsBlockSuperiors() {
        return this.beforeGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public String[] getBeforeGetCurrentPlayerStrVsBlockInferiors() {
        return this.beforeGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public String[] getOverrideGetCurrentPlayerStrVsBlockSuperiors() {
        return this.overrideGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public String[] getOverrideGetCurrentPlayerStrVsBlockInferiors() {
        return this.overrideGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public String[] getAfterGetCurrentPlayerStrVsBlockSuperiors() {
        return this.afterGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public String[] getAfterGetCurrentPlayerStrVsBlockInferiors() {
        return this.afterGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public void setBeforeGetCurrentPlayerStrVsBlockSuperiors(final String[] beforeGetCurrentPlayerStrVsBlockSuperiors) {
        this.beforeGetCurrentPlayerStrVsBlockSuperiors = beforeGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public void setBeforeGetCurrentPlayerStrVsBlockInferiors(final String[] beforeGetCurrentPlayerStrVsBlockInferiors) {
        this.beforeGetCurrentPlayerStrVsBlockInferiors = beforeGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public void setOverrideGetCurrentPlayerStrVsBlockSuperiors(final String[] overrideGetCurrentPlayerStrVsBlockSuperiors) {
        this.overrideGetCurrentPlayerStrVsBlockSuperiors = overrideGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public void setOverrideGetCurrentPlayerStrVsBlockInferiors(final String[] overrideGetCurrentPlayerStrVsBlockInferiors) {
        this.overrideGetCurrentPlayerStrVsBlockInferiors = overrideGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public void setAfterGetCurrentPlayerStrVsBlockSuperiors(final String[] afterGetCurrentPlayerStrVsBlockSuperiors) {
        this.afterGetCurrentPlayerStrVsBlockSuperiors = afterGetCurrentPlayerStrVsBlockSuperiors;
    }
    
    public void setAfterGetCurrentPlayerStrVsBlockInferiors(final String[] afterGetCurrentPlayerStrVsBlockInferiors) {
        this.afterGetCurrentPlayerStrVsBlockInferiors = afterGetCurrentPlayerStrVsBlockInferiors;
    }
    
    public String[] getBeforeGetDistanceSqSuperiors() {
        return this.beforeGetDistanceSqSuperiors;
    }
    
    public String[] getBeforeGetDistanceSqInferiors() {
        return this.beforeGetDistanceSqInferiors;
    }
    
    public String[] getOverrideGetDistanceSqSuperiors() {
        return this.overrideGetDistanceSqSuperiors;
    }
    
    public String[] getOverrideGetDistanceSqInferiors() {
        return this.overrideGetDistanceSqInferiors;
    }
    
    public String[] getAfterGetDistanceSqSuperiors() {
        return this.afterGetDistanceSqSuperiors;
    }
    
    public String[] getAfterGetDistanceSqInferiors() {
        return this.afterGetDistanceSqInferiors;
    }
    
    public void setBeforeGetDistanceSqSuperiors(final String[] beforeGetDistanceSqSuperiors) {
        this.beforeGetDistanceSqSuperiors = beforeGetDistanceSqSuperiors;
    }
    
    public void setBeforeGetDistanceSqInferiors(final String[] beforeGetDistanceSqInferiors) {
        this.beforeGetDistanceSqInferiors = beforeGetDistanceSqInferiors;
    }
    
    public void setOverrideGetDistanceSqSuperiors(final String[] overrideGetDistanceSqSuperiors) {
        this.overrideGetDistanceSqSuperiors = overrideGetDistanceSqSuperiors;
    }
    
    public void setOverrideGetDistanceSqInferiors(final String[] overrideGetDistanceSqInferiors) {
        this.overrideGetDistanceSqInferiors = overrideGetDistanceSqInferiors;
    }
    
    public void setAfterGetDistanceSqSuperiors(final String[] afterGetDistanceSqSuperiors) {
        this.afterGetDistanceSqSuperiors = afterGetDistanceSqSuperiors;
    }
    
    public void setAfterGetDistanceSqInferiors(final String[] afterGetDistanceSqInferiors) {
        this.afterGetDistanceSqInferiors = afterGetDistanceSqInferiors;
    }
    
    public String[] getBeforeGetBrightnessSuperiors() {
        return this.beforeGetBrightnessSuperiors;
    }
    
    public String[] getBeforeGetBrightnessInferiors() {
        return this.beforeGetBrightnessInferiors;
    }
    
    public String[] getOverrideGetBrightnessSuperiors() {
        return this.overrideGetBrightnessSuperiors;
    }
    
    public String[] getOverrideGetBrightnessInferiors() {
        return this.overrideGetBrightnessInferiors;
    }
    
    public String[] getAfterGetBrightnessSuperiors() {
        return this.afterGetBrightnessSuperiors;
    }
    
    public String[] getAfterGetBrightnessInferiors() {
        return this.afterGetBrightnessInferiors;
    }
    
    public void setBeforeGetBrightnessSuperiors(final String[] beforeGetBrightnessSuperiors) {
        this.beforeGetBrightnessSuperiors = beforeGetBrightnessSuperiors;
    }
    
    public void setBeforeGetBrightnessInferiors(final String[] beforeGetBrightnessInferiors) {
        this.beforeGetBrightnessInferiors = beforeGetBrightnessInferiors;
    }
    
    public void setOverrideGetBrightnessSuperiors(final String[] overrideGetBrightnessSuperiors) {
        this.overrideGetBrightnessSuperiors = overrideGetBrightnessSuperiors;
    }
    
    public void setOverrideGetBrightnessInferiors(final String[] overrideGetBrightnessInferiors) {
        this.overrideGetBrightnessInferiors = overrideGetBrightnessInferiors;
    }
    
    public void setAfterGetBrightnessSuperiors(final String[] afterGetBrightnessSuperiors) {
        this.afterGetBrightnessSuperiors = afterGetBrightnessSuperiors;
    }
    
    public void setAfterGetBrightnessInferiors(final String[] afterGetBrightnessInferiors) {
        this.afterGetBrightnessInferiors = afterGetBrightnessInferiors;
    }
    
    public String[] getBeforeGetEyeHeightSuperiors() {
        return this.beforeGetEyeHeightSuperiors;
    }
    
    public String[] getBeforeGetEyeHeightInferiors() {
        return this.beforeGetEyeHeightInferiors;
    }
    
    public String[] getOverrideGetEyeHeightSuperiors() {
        return this.overrideGetEyeHeightSuperiors;
    }
    
    public String[] getOverrideGetEyeHeightInferiors() {
        return this.overrideGetEyeHeightInferiors;
    }
    
    public String[] getAfterGetEyeHeightSuperiors() {
        return this.afterGetEyeHeightSuperiors;
    }
    
    public String[] getAfterGetEyeHeightInferiors() {
        return this.afterGetEyeHeightInferiors;
    }
    
    public void setBeforeGetEyeHeightSuperiors(final String[] beforeGetEyeHeightSuperiors) {
        this.beforeGetEyeHeightSuperiors = beforeGetEyeHeightSuperiors;
    }
    
    public void setBeforeGetEyeHeightInferiors(final String[] beforeGetEyeHeightInferiors) {
        this.beforeGetEyeHeightInferiors = beforeGetEyeHeightInferiors;
    }
    
    public void setOverrideGetEyeHeightSuperiors(final String[] overrideGetEyeHeightSuperiors) {
        this.overrideGetEyeHeightSuperiors = overrideGetEyeHeightSuperiors;
    }
    
    public void setOverrideGetEyeHeightInferiors(final String[] overrideGetEyeHeightInferiors) {
        this.overrideGetEyeHeightInferiors = overrideGetEyeHeightInferiors;
    }
    
    public void setAfterGetEyeHeightSuperiors(final String[] afterGetEyeHeightSuperiors) {
        this.afterGetEyeHeightSuperiors = afterGetEyeHeightSuperiors;
    }
    
    public void setAfterGetEyeHeightInferiors(final String[] afterGetEyeHeightInferiors) {
        this.afterGetEyeHeightInferiors = afterGetEyeHeightInferiors;
    }
    
    public String[] getBeforeGetMaxHealthSuperiors() {
        return this.beforeGetMaxHealthSuperiors;
    }
    
    public String[] getBeforeGetMaxHealthInferiors() {
        return this.beforeGetMaxHealthInferiors;
    }
    
    public String[] getOverrideGetMaxHealthSuperiors() {
        return this.overrideGetMaxHealthSuperiors;
    }
    
    public String[] getOverrideGetMaxHealthInferiors() {
        return this.overrideGetMaxHealthInferiors;
    }
    
    public String[] getAfterGetMaxHealthSuperiors() {
        return this.afterGetMaxHealthSuperiors;
    }
    
    public String[] getAfterGetMaxHealthInferiors() {
        return this.afterGetMaxHealthInferiors;
    }
    
    public void setBeforeGetMaxHealthSuperiors(final String[] beforeGetMaxHealthSuperiors) {
        this.beforeGetMaxHealthSuperiors = beforeGetMaxHealthSuperiors;
    }
    
    public void setBeforeGetMaxHealthInferiors(final String[] beforeGetMaxHealthInferiors) {
        this.beforeGetMaxHealthInferiors = beforeGetMaxHealthInferiors;
    }
    
    public void setOverrideGetMaxHealthSuperiors(final String[] overrideGetMaxHealthSuperiors) {
        this.overrideGetMaxHealthSuperiors = overrideGetMaxHealthSuperiors;
    }
    
    public void setOverrideGetMaxHealthInferiors(final String[] overrideGetMaxHealthInferiors) {
        this.overrideGetMaxHealthInferiors = overrideGetMaxHealthInferiors;
    }
    
    public void setAfterGetMaxHealthSuperiors(final String[] afterGetMaxHealthSuperiors) {
        this.afterGetMaxHealthSuperiors = afterGetMaxHealthSuperiors;
    }
    
    public void setAfterGetMaxHealthInferiors(final String[] afterGetMaxHealthInferiors) {
        this.afterGetMaxHealthInferiors = afterGetMaxHealthInferiors;
    }
    
    public String[] getBeforeGetSpeedModifierSuperiors() {
        return this.beforeGetSpeedModifierSuperiors;
    }
    
    public String[] getBeforeGetSpeedModifierInferiors() {
        return this.beforeGetSpeedModifierInferiors;
    }
    
    public String[] getOverrideGetSpeedModifierSuperiors() {
        return this.overrideGetSpeedModifierSuperiors;
    }
    
    public String[] getOverrideGetSpeedModifierInferiors() {
        return this.overrideGetSpeedModifierInferiors;
    }
    
    public String[] getAfterGetSpeedModifierSuperiors() {
        return this.afterGetSpeedModifierSuperiors;
    }
    
    public String[] getAfterGetSpeedModifierInferiors() {
        return this.afterGetSpeedModifierInferiors;
    }
    
    public void setBeforeGetSpeedModifierSuperiors(final String[] beforeGetSpeedModifierSuperiors) {
        this.beforeGetSpeedModifierSuperiors = beforeGetSpeedModifierSuperiors;
    }
    
    public void setBeforeGetSpeedModifierInferiors(final String[] beforeGetSpeedModifierInferiors) {
        this.beforeGetSpeedModifierInferiors = beforeGetSpeedModifierInferiors;
    }
    
    public void setOverrideGetSpeedModifierSuperiors(final String[] overrideGetSpeedModifierSuperiors) {
        this.overrideGetSpeedModifierSuperiors = overrideGetSpeedModifierSuperiors;
    }
    
    public void setOverrideGetSpeedModifierInferiors(final String[] overrideGetSpeedModifierInferiors) {
        this.overrideGetSpeedModifierInferiors = overrideGetSpeedModifierInferiors;
    }
    
    public void setAfterGetSpeedModifierSuperiors(final String[] afterGetSpeedModifierSuperiors) {
        this.afterGetSpeedModifierSuperiors = afterGetSpeedModifierSuperiors;
    }
    
    public void setAfterGetSpeedModifierInferiors(final String[] afterGetSpeedModifierInferiors) {
        this.afterGetSpeedModifierInferiors = afterGetSpeedModifierInferiors;
    }
    
    public String[] getBeforeHealSuperiors() {
        return this.beforeHealSuperiors;
    }
    
    public String[] getBeforeHealInferiors() {
        return this.beforeHealInferiors;
    }
    
    public String[] getOverrideHealSuperiors() {
        return this.overrideHealSuperiors;
    }
    
    public String[] getOverrideHealInferiors() {
        return this.overrideHealInferiors;
    }
    
    public String[] getAfterHealSuperiors() {
        return this.afterHealSuperiors;
    }
    
    public String[] getAfterHealInferiors() {
        return this.afterHealInferiors;
    }
    
    public void setBeforeHealSuperiors(final String[] beforeHealSuperiors) {
        this.beforeHealSuperiors = beforeHealSuperiors;
    }
    
    public void setBeforeHealInferiors(final String[] beforeHealInferiors) {
        this.beforeHealInferiors = beforeHealInferiors;
    }
    
    public void setOverrideHealSuperiors(final String[] overrideHealSuperiors) {
        this.overrideHealSuperiors = overrideHealSuperiors;
    }
    
    public void setOverrideHealInferiors(final String[] overrideHealInferiors) {
        this.overrideHealInferiors = overrideHealInferiors;
    }
    
    public void setAfterHealSuperiors(final String[] afterHealSuperiors) {
        this.afterHealSuperiors = afterHealSuperiors;
    }
    
    public void setAfterHealInferiors(final String[] afterHealInferiors) {
        this.afterHealInferiors = afterHealInferiors;
    }
    
    public String[] getBeforeInteractSuperiors() {
        return this.beforeInteractSuperiors;
    }
    
    public String[] getBeforeInteractInferiors() {
        return this.beforeInteractInferiors;
    }
    
    public String[] getOverrideInteractSuperiors() {
        return this.overrideInteractSuperiors;
    }
    
    public String[] getOverrideInteractInferiors() {
        return this.overrideInteractInferiors;
    }
    
    public String[] getAfterInteractSuperiors() {
        return this.afterInteractSuperiors;
    }
    
    public String[] getAfterInteractInferiors() {
        return this.afterInteractInferiors;
    }
    
    public void setBeforeInteractSuperiors(final String[] beforeInteractSuperiors) {
        this.beforeInteractSuperiors = beforeInteractSuperiors;
    }
    
    public void setBeforeInteractInferiors(final String[] beforeInteractInferiors) {
        this.beforeInteractInferiors = beforeInteractInferiors;
    }
    
    public void setOverrideInteractSuperiors(final String[] overrideInteractSuperiors) {
        this.overrideInteractSuperiors = overrideInteractSuperiors;
    }
    
    public void setOverrideInteractInferiors(final String[] overrideInteractInferiors) {
        this.overrideInteractInferiors = overrideInteractInferiors;
    }
    
    public void setAfterInteractSuperiors(final String[] afterInteractSuperiors) {
        this.afterInteractSuperiors = afterInteractSuperiors;
    }
    
    public void setAfterInteractInferiors(final String[] afterInteractInferiors) {
        this.afterInteractInferiors = afterInteractInferiors;
    }
    
    public String[] getBeforeIsEntityInsideOpaqueBlockSuperiors() {
        return this.beforeIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public String[] getBeforeIsEntityInsideOpaqueBlockInferiors() {
        return this.beforeIsEntityInsideOpaqueBlockInferiors;
    }
    
    public String[] getOverrideIsEntityInsideOpaqueBlockSuperiors() {
        return this.overrideIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public String[] getOverrideIsEntityInsideOpaqueBlockInferiors() {
        return this.overrideIsEntityInsideOpaqueBlockInferiors;
    }
    
    public String[] getAfterIsEntityInsideOpaqueBlockSuperiors() {
        return this.afterIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public String[] getAfterIsEntityInsideOpaqueBlockInferiors() {
        return this.afterIsEntityInsideOpaqueBlockInferiors;
    }
    
    public void setBeforeIsEntityInsideOpaqueBlockSuperiors(final String[] beforeIsEntityInsideOpaqueBlockSuperiors) {
        this.beforeIsEntityInsideOpaqueBlockSuperiors = beforeIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public void setBeforeIsEntityInsideOpaqueBlockInferiors(final String[] beforeIsEntityInsideOpaqueBlockInferiors) {
        this.beforeIsEntityInsideOpaqueBlockInferiors = beforeIsEntityInsideOpaqueBlockInferiors;
    }
    
    public void setOverrideIsEntityInsideOpaqueBlockSuperiors(final String[] overrideIsEntityInsideOpaqueBlockSuperiors) {
        this.overrideIsEntityInsideOpaqueBlockSuperiors = overrideIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public void setOverrideIsEntityInsideOpaqueBlockInferiors(final String[] overrideIsEntityInsideOpaqueBlockInferiors) {
        this.overrideIsEntityInsideOpaqueBlockInferiors = overrideIsEntityInsideOpaqueBlockInferiors;
    }
    
    public void setAfterIsEntityInsideOpaqueBlockSuperiors(final String[] afterIsEntityInsideOpaqueBlockSuperiors) {
        this.afterIsEntityInsideOpaqueBlockSuperiors = afterIsEntityInsideOpaqueBlockSuperiors;
    }
    
    public void setAfterIsEntityInsideOpaqueBlockInferiors(final String[] afterIsEntityInsideOpaqueBlockInferiors) {
        this.afterIsEntityInsideOpaqueBlockInferiors = afterIsEntityInsideOpaqueBlockInferiors;
    }
    
    public String[] getBeforeIsInWaterSuperiors() {
        return this.beforeIsInWaterSuperiors;
    }
    
    public String[] getBeforeIsInWaterInferiors() {
        return this.beforeIsInWaterInferiors;
    }
    
    public String[] getOverrideIsInWaterSuperiors() {
        return this.overrideIsInWaterSuperiors;
    }
    
    public String[] getOverrideIsInWaterInferiors() {
        return this.overrideIsInWaterInferiors;
    }
    
    public String[] getAfterIsInWaterSuperiors() {
        return this.afterIsInWaterSuperiors;
    }
    
    public String[] getAfterIsInWaterInferiors() {
        return this.afterIsInWaterInferiors;
    }
    
    public void setBeforeIsInWaterSuperiors(final String[] beforeIsInWaterSuperiors) {
        this.beforeIsInWaterSuperiors = beforeIsInWaterSuperiors;
    }
    
    public void setBeforeIsInWaterInferiors(final String[] beforeIsInWaterInferiors) {
        this.beforeIsInWaterInferiors = beforeIsInWaterInferiors;
    }
    
    public void setOverrideIsInWaterSuperiors(final String[] overrideIsInWaterSuperiors) {
        this.overrideIsInWaterSuperiors = overrideIsInWaterSuperiors;
    }
    
    public void setOverrideIsInWaterInferiors(final String[] overrideIsInWaterInferiors) {
        this.overrideIsInWaterInferiors = overrideIsInWaterInferiors;
    }
    
    public void setAfterIsInWaterSuperiors(final String[] afterIsInWaterSuperiors) {
        this.afterIsInWaterSuperiors = afterIsInWaterSuperiors;
    }
    
    public void setAfterIsInWaterInferiors(final String[] afterIsInWaterInferiors) {
        this.afterIsInWaterInferiors = afterIsInWaterInferiors;
    }
    
    public String[] getBeforeIsInsideOfMaterialSuperiors() {
        return this.beforeIsInsideOfMaterialSuperiors;
    }
    
    public String[] getBeforeIsInsideOfMaterialInferiors() {
        return this.beforeIsInsideOfMaterialInferiors;
    }
    
    public String[] getOverrideIsInsideOfMaterialSuperiors() {
        return this.overrideIsInsideOfMaterialSuperiors;
    }
    
    public String[] getOverrideIsInsideOfMaterialInferiors() {
        return this.overrideIsInsideOfMaterialInferiors;
    }
    
    public String[] getAfterIsInsideOfMaterialSuperiors() {
        return this.afterIsInsideOfMaterialSuperiors;
    }
    
    public String[] getAfterIsInsideOfMaterialInferiors() {
        return this.afterIsInsideOfMaterialInferiors;
    }
    
    public void setBeforeIsInsideOfMaterialSuperiors(final String[] beforeIsInsideOfMaterialSuperiors) {
        this.beforeIsInsideOfMaterialSuperiors = beforeIsInsideOfMaterialSuperiors;
    }
    
    public void setBeforeIsInsideOfMaterialInferiors(final String[] beforeIsInsideOfMaterialInferiors) {
        this.beforeIsInsideOfMaterialInferiors = beforeIsInsideOfMaterialInferiors;
    }
    
    public void setOverrideIsInsideOfMaterialSuperiors(final String[] overrideIsInsideOfMaterialSuperiors) {
        this.overrideIsInsideOfMaterialSuperiors = overrideIsInsideOfMaterialSuperiors;
    }
    
    public void setOverrideIsInsideOfMaterialInferiors(final String[] overrideIsInsideOfMaterialInferiors) {
        this.overrideIsInsideOfMaterialInferiors = overrideIsInsideOfMaterialInferiors;
    }
    
    public void setAfterIsInsideOfMaterialSuperiors(final String[] afterIsInsideOfMaterialSuperiors) {
        this.afterIsInsideOfMaterialSuperiors = afterIsInsideOfMaterialSuperiors;
    }
    
    public void setAfterIsInsideOfMaterialInferiors(final String[] afterIsInsideOfMaterialInferiors) {
        this.afterIsInsideOfMaterialInferiors = afterIsInsideOfMaterialInferiors;
    }
    
    public String[] getBeforeIsOnLadderSuperiors() {
        return this.beforeIsOnLadderSuperiors;
    }
    
    public String[] getBeforeIsOnLadderInferiors() {
        return this.beforeIsOnLadderInferiors;
    }
    
    public String[] getOverrideIsOnLadderSuperiors() {
        return this.overrideIsOnLadderSuperiors;
    }
    
    public String[] getOverrideIsOnLadderInferiors() {
        return this.overrideIsOnLadderInferiors;
    }
    
    public String[] getAfterIsOnLadderSuperiors() {
        return this.afterIsOnLadderSuperiors;
    }
    
    public String[] getAfterIsOnLadderInferiors() {
        return this.afterIsOnLadderInferiors;
    }
    
    public void setBeforeIsOnLadderSuperiors(final String[] beforeIsOnLadderSuperiors) {
        this.beforeIsOnLadderSuperiors = beforeIsOnLadderSuperiors;
    }
    
    public void setBeforeIsOnLadderInferiors(final String[] beforeIsOnLadderInferiors) {
        this.beforeIsOnLadderInferiors = beforeIsOnLadderInferiors;
    }
    
    public void setOverrideIsOnLadderSuperiors(final String[] overrideIsOnLadderSuperiors) {
        this.overrideIsOnLadderSuperiors = overrideIsOnLadderSuperiors;
    }
    
    public void setOverrideIsOnLadderInferiors(final String[] overrideIsOnLadderInferiors) {
        this.overrideIsOnLadderInferiors = overrideIsOnLadderInferiors;
    }
    
    public void setAfterIsOnLadderSuperiors(final String[] afterIsOnLadderSuperiors) {
        this.afterIsOnLadderSuperiors = afterIsOnLadderSuperiors;
    }
    
    public void setAfterIsOnLadderInferiors(final String[] afterIsOnLadderInferiors) {
        this.afterIsOnLadderInferiors = afterIsOnLadderInferiors;
    }
    
    public String[] getBeforeIsPlayerSleepingSuperiors() {
        return this.beforeIsPlayerSleepingSuperiors;
    }
    
    public String[] getBeforeIsPlayerSleepingInferiors() {
        return this.beforeIsPlayerSleepingInferiors;
    }
    
    public String[] getOverrideIsPlayerSleepingSuperiors() {
        return this.overrideIsPlayerSleepingSuperiors;
    }
    
    public String[] getOverrideIsPlayerSleepingInferiors() {
        return this.overrideIsPlayerSleepingInferiors;
    }
    
    public String[] getAfterIsPlayerSleepingSuperiors() {
        return this.afterIsPlayerSleepingSuperiors;
    }
    
    public String[] getAfterIsPlayerSleepingInferiors() {
        return this.afterIsPlayerSleepingInferiors;
    }
    
    public void setBeforeIsPlayerSleepingSuperiors(final String[] beforeIsPlayerSleepingSuperiors) {
        this.beforeIsPlayerSleepingSuperiors = beforeIsPlayerSleepingSuperiors;
    }
    
    public void setBeforeIsPlayerSleepingInferiors(final String[] beforeIsPlayerSleepingInferiors) {
        this.beforeIsPlayerSleepingInferiors = beforeIsPlayerSleepingInferiors;
    }
    
    public void setOverrideIsPlayerSleepingSuperiors(final String[] overrideIsPlayerSleepingSuperiors) {
        this.overrideIsPlayerSleepingSuperiors = overrideIsPlayerSleepingSuperiors;
    }
    
    public void setOverrideIsPlayerSleepingInferiors(final String[] overrideIsPlayerSleepingInferiors) {
        this.overrideIsPlayerSleepingInferiors = overrideIsPlayerSleepingInferiors;
    }
    
    public void setAfterIsPlayerSleepingSuperiors(final String[] afterIsPlayerSleepingSuperiors) {
        this.afterIsPlayerSleepingSuperiors = afterIsPlayerSleepingSuperiors;
    }
    
    public void setAfterIsPlayerSleepingInferiors(final String[] afterIsPlayerSleepingInferiors) {
        this.afterIsPlayerSleepingInferiors = afterIsPlayerSleepingInferiors;
    }
    
    public String[] getBeforeJumpSuperiors() {
        return this.beforeJumpSuperiors;
    }
    
    public String[] getBeforeJumpInferiors() {
        return this.beforeJumpInferiors;
    }
    
    public String[] getOverrideJumpSuperiors() {
        return this.overrideJumpSuperiors;
    }
    
    public String[] getOverrideJumpInferiors() {
        return this.overrideJumpInferiors;
    }
    
    public String[] getAfterJumpSuperiors() {
        return this.afterJumpSuperiors;
    }
    
    public String[] getAfterJumpInferiors() {
        return this.afterJumpInferiors;
    }
    
    public void setBeforeJumpSuperiors(final String[] beforeJumpSuperiors) {
        this.beforeJumpSuperiors = beforeJumpSuperiors;
    }
    
    public void setBeforeJumpInferiors(final String[] beforeJumpInferiors) {
        this.beforeJumpInferiors = beforeJumpInferiors;
    }
    
    public void setOverrideJumpSuperiors(final String[] overrideJumpSuperiors) {
        this.overrideJumpSuperiors = overrideJumpSuperiors;
    }
    
    public void setOverrideJumpInferiors(final String[] overrideJumpInferiors) {
        this.overrideJumpInferiors = overrideJumpInferiors;
    }
    
    public void setAfterJumpSuperiors(final String[] afterJumpSuperiors) {
        this.afterJumpSuperiors = afterJumpSuperiors;
    }
    
    public void setAfterJumpInferiors(final String[] afterJumpInferiors) {
        this.afterJumpInferiors = afterJumpInferiors;
    }
    
    public String[] getBeforeKnockBackSuperiors() {
        return this.beforeKnockBackSuperiors;
    }
    
    public String[] getBeforeKnockBackInferiors() {
        return this.beforeKnockBackInferiors;
    }
    
    public String[] getOverrideKnockBackSuperiors() {
        return this.overrideKnockBackSuperiors;
    }
    
    public String[] getOverrideKnockBackInferiors() {
        return this.overrideKnockBackInferiors;
    }
    
    public String[] getAfterKnockBackSuperiors() {
        return this.afterKnockBackSuperiors;
    }
    
    public String[] getAfterKnockBackInferiors() {
        return this.afterKnockBackInferiors;
    }
    
    public void setBeforeKnockBackSuperiors(final String[] beforeKnockBackSuperiors) {
        this.beforeKnockBackSuperiors = beforeKnockBackSuperiors;
    }
    
    public void setBeforeKnockBackInferiors(final String[] beforeKnockBackInferiors) {
        this.beforeKnockBackInferiors = beforeKnockBackInferiors;
    }
    
    public void setOverrideKnockBackSuperiors(final String[] overrideKnockBackSuperiors) {
        this.overrideKnockBackSuperiors = overrideKnockBackSuperiors;
    }
    
    public void setOverrideKnockBackInferiors(final String[] overrideKnockBackInferiors) {
        this.overrideKnockBackInferiors = overrideKnockBackInferiors;
    }
    
    public void setAfterKnockBackSuperiors(final String[] afterKnockBackSuperiors) {
        this.afterKnockBackSuperiors = afterKnockBackSuperiors;
    }
    
    public void setAfterKnockBackInferiors(final String[] afterKnockBackInferiors) {
        this.afterKnockBackInferiors = afterKnockBackInferiors;
    }
    
    public String[] getBeforeMoveEntitySuperiors() {
        return this.beforeMoveEntitySuperiors;
    }
    
    public String[] getBeforeMoveEntityInferiors() {
        return this.beforeMoveEntityInferiors;
    }
    
    public String[] getOverrideMoveEntitySuperiors() {
        return this.overrideMoveEntitySuperiors;
    }
    
    public String[] getOverrideMoveEntityInferiors() {
        return this.overrideMoveEntityInferiors;
    }
    
    public String[] getAfterMoveEntitySuperiors() {
        return this.afterMoveEntitySuperiors;
    }
    
    public String[] getAfterMoveEntityInferiors() {
        return this.afterMoveEntityInferiors;
    }
    
    public void setBeforeMoveEntitySuperiors(final String[] beforeMoveEntitySuperiors) {
        this.beforeMoveEntitySuperiors = beforeMoveEntitySuperiors;
    }
    
    public void setBeforeMoveEntityInferiors(final String[] beforeMoveEntityInferiors) {
        this.beforeMoveEntityInferiors = beforeMoveEntityInferiors;
    }
    
    public void setOverrideMoveEntitySuperiors(final String[] overrideMoveEntitySuperiors) {
        this.overrideMoveEntitySuperiors = overrideMoveEntitySuperiors;
    }
    
    public void setOverrideMoveEntityInferiors(final String[] overrideMoveEntityInferiors) {
        this.overrideMoveEntityInferiors = overrideMoveEntityInferiors;
    }
    
    public void setAfterMoveEntitySuperiors(final String[] afterMoveEntitySuperiors) {
        this.afterMoveEntitySuperiors = afterMoveEntitySuperiors;
    }
    
    public void setAfterMoveEntityInferiors(final String[] afterMoveEntityInferiors) {
        this.afterMoveEntityInferiors = afterMoveEntityInferiors;
    }
    
    public String[] getBeforeMoveEntityWithHeadingSuperiors() {
        return this.beforeMoveEntityWithHeadingSuperiors;
    }
    
    public String[] getBeforeMoveEntityWithHeadingInferiors() {
        return this.beforeMoveEntityWithHeadingInferiors;
    }
    
    public String[] getOverrideMoveEntityWithHeadingSuperiors() {
        return this.overrideMoveEntityWithHeadingSuperiors;
    }
    
    public String[] getOverrideMoveEntityWithHeadingInferiors() {
        return this.overrideMoveEntityWithHeadingInferiors;
    }
    
    public String[] getAfterMoveEntityWithHeadingSuperiors() {
        return this.afterMoveEntityWithHeadingSuperiors;
    }
    
    public String[] getAfterMoveEntityWithHeadingInferiors() {
        return this.afterMoveEntityWithHeadingInferiors;
    }
    
    public void setBeforeMoveEntityWithHeadingSuperiors(final String[] beforeMoveEntityWithHeadingSuperiors) {
        this.beforeMoveEntityWithHeadingSuperiors = beforeMoveEntityWithHeadingSuperiors;
    }
    
    public void setBeforeMoveEntityWithHeadingInferiors(final String[] beforeMoveEntityWithHeadingInferiors) {
        this.beforeMoveEntityWithHeadingInferiors = beforeMoveEntityWithHeadingInferiors;
    }
    
    public void setOverrideMoveEntityWithHeadingSuperiors(final String[] overrideMoveEntityWithHeadingSuperiors) {
        this.overrideMoveEntityWithHeadingSuperiors = overrideMoveEntityWithHeadingSuperiors;
    }
    
    public void setOverrideMoveEntityWithHeadingInferiors(final String[] overrideMoveEntityWithHeadingInferiors) {
        this.overrideMoveEntityWithHeadingInferiors = overrideMoveEntityWithHeadingInferiors;
    }
    
    public void setAfterMoveEntityWithHeadingSuperiors(final String[] afterMoveEntityWithHeadingSuperiors) {
        this.afterMoveEntityWithHeadingSuperiors = afterMoveEntityWithHeadingSuperiors;
    }
    
    public void setAfterMoveEntityWithHeadingInferiors(final String[] afterMoveEntityWithHeadingInferiors) {
        this.afterMoveEntityWithHeadingInferiors = afterMoveEntityWithHeadingInferiors;
    }
    
    public String[] getBeforeMoveFlyingSuperiors() {
        return this.beforeMoveFlyingSuperiors;
    }
    
    public String[] getBeforeMoveFlyingInferiors() {
        return this.beforeMoveFlyingInferiors;
    }
    
    public String[] getOverrideMoveFlyingSuperiors() {
        return this.overrideMoveFlyingSuperiors;
    }
    
    public String[] getOverrideMoveFlyingInferiors() {
        return this.overrideMoveFlyingInferiors;
    }
    
    public String[] getAfterMoveFlyingSuperiors() {
        return this.afterMoveFlyingSuperiors;
    }
    
    public String[] getAfterMoveFlyingInferiors() {
        return this.afterMoveFlyingInferiors;
    }
    
    public void setBeforeMoveFlyingSuperiors(final String[] beforeMoveFlyingSuperiors) {
        this.beforeMoveFlyingSuperiors = beforeMoveFlyingSuperiors;
    }
    
    public void setBeforeMoveFlyingInferiors(final String[] beforeMoveFlyingInferiors) {
        this.beforeMoveFlyingInferiors = beforeMoveFlyingInferiors;
    }
    
    public void setOverrideMoveFlyingSuperiors(final String[] overrideMoveFlyingSuperiors) {
        this.overrideMoveFlyingSuperiors = overrideMoveFlyingSuperiors;
    }
    
    public void setOverrideMoveFlyingInferiors(final String[] overrideMoveFlyingInferiors) {
        this.overrideMoveFlyingInferiors = overrideMoveFlyingInferiors;
    }
    
    public void setAfterMoveFlyingSuperiors(final String[] afterMoveFlyingSuperiors) {
        this.afterMoveFlyingSuperiors = afterMoveFlyingSuperiors;
    }
    
    public void setAfterMoveFlyingInferiors(final String[] afterMoveFlyingInferiors) {
        this.afterMoveFlyingInferiors = afterMoveFlyingInferiors;
    }
    
    public String[] getBeforeOnDeathSuperiors() {
        return this.beforeOnDeathSuperiors;
    }
    
    public String[] getBeforeOnDeathInferiors() {
        return this.beforeOnDeathInferiors;
    }
    
    public String[] getOverrideOnDeathSuperiors() {
        return this.overrideOnDeathSuperiors;
    }
    
    public String[] getOverrideOnDeathInferiors() {
        return this.overrideOnDeathInferiors;
    }
    
    public String[] getAfterOnDeathSuperiors() {
        return this.afterOnDeathSuperiors;
    }
    
    public String[] getAfterOnDeathInferiors() {
        return this.afterOnDeathInferiors;
    }
    
    public void setBeforeOnDeathSuperiors(final String[] beforeOnDeathSuperiors) {
        this.beforeOnDeathSuperiors = beforeOnDeathSuperiors;
    }
    
    public void setBeforeOnDeathInferiors(final String[] beforeOnDeathInferiors) {
        this.beforeOnDeathInferiors = beforeOnDeathInferiors;
    }
    
    public void setOverrideOnDeathSuperiors(final String[] overrideOnDeathSuperiors) {
        this.overrideOnDeathSuperiors = overrideOnDeathSuperiors;
    }
    
    public void setOverrideOnDeathInferiors(final String[] overrideOnDeathInferiors) {
        this.overrideOnDeathInferiors = overrideOnDeathInferiors;
    }
    
    public void setAfterOnDeathSuperiors(final String[] afterOnDeathSuperiors) {
        this.afterOnDeathSuperiors = afterOnDeathSuperiors;
    }
    
    public void setAfterOnDeathInferiors(final String[] afterOnDeathInferiors) {
        this.afterOnDeathInferiors = afterOnDeathInferiors;
    }
    
    public String[] getBeforeOnLivingUpdateSuperiors() {
        return this.beforeOnLivingUpdateSuperiors;
    }
    
    public String[] getBeforeOnLivingUpdateInferiors() {
        return this.beforeOnLivingUpdateInferiors;
    }
    
    public String[] getOverrideOnLivingUpdateSuperiors() {
        return this.overrideOnLivingUpdateSuperiors;
    }
    
    public String[] getOverrideOnLivingUpdateInferiors() {
        return this.overrideOnLivingUpdateInferiors;
    }
    
    public String[] getAfterOnLivingUpdateSuperiors() {
        return this.afterOnLivingUpdateSuperiors;
    }
    
    public String[] getAfterOnLivingUpdateInferiors() {
        return this.afterOnLivingUpdateInferiors;
    }
    
    public void setBeforeOnLivingUpdateSuperiors(final String[] beforeOnLivingUpdateSuperiors) {
        this.beforeOnLivingUpdateSuperiors = beforeOnLivingUpdateSuperiors;
    }
    
    public void setBeforeOnLivingUpdateInferiors(final String[] beforeOnLivingUpdateInferiors) {
        this.beforeOnLivingUpdateInferiors = beforeOnLivingUpdateInferiors;
    }
    
    public void setOverrideOnLivingUpdateSuperiors(final String[] overrideOnLivingUpdateSuperiors) {
        this.overrideOnLivingUpdateSuperiors = overrideOnLivingUpdateSuperiors;
    }
    
    public void setOverrideOnLivingUpdateInferiors(final String[] overrideOnLivingUpdateInferiors) {
        this.overrideOnLivingUpdateInferiors = overrideOnLivingUpdateInferiors;
    }
    
    public void setAfterOnLivingUpdateSuperiors(final String[] afterOnLivingUpdateSuperiors) {
        this.afterOnLivingUpdateSuperiors = afterOnLivingUpdateSuperiors;
    }
    
    public void setAfterOnLivingUpdateInferiors(final String[] afterOnLivingUpdateInferiors) {
        this.afterOnLivingUpdateInferiors = afterOnLivingUpdateInferiors;
    }
    
    public String[] getBeforeOnKillEntitySuperiors() {
        return this.beforeOnKillEntitySuperiors;
    }
    
    public String[] getBeforeOnKillEntityInferiors() {
        return this.beforeOnKillEntityInferiors;
    }
    
    public String[] getOverrideOnKillEntitySuperiors() {
        return this.overrideOnKillEntitySuperiors;
    }
    
    public String[] getOverrideOnKillEntityInferiors() {
        return this.overrideOnKillEntityInferiors;
    }
    
    public String[] getAfterOnKillEntitySuperiors() {
        return this.afterOnKillEntitySuperiors;
    }
    
    public String[] getAfterOnKillEntityInferiors() {
        return this.afterOnKillEntityInferiors;
    }
    
    public void setBeforeOnKillEntitySuperiors(final String[] beforeOnKillEntitySuperiors) {
        this.beforeOnKillEntitySuperiors = beforeOnKillEntitySuperiors;
    }
    
    public void setBeforeOnKillEntityInferiors(final String[] beforeOnKillEntityInferiors) {
        this.beforeOnKillEntityInferiors = beforeOnKillEntityInferiors;
    }
    
    public void setOverrideOnKillEntitySuperiors(final String[] overrideOnKillEntitySuperiors) {
        this.overrideOnKillEntitySuperiors = overrideOnKillEntitySuperiors;
    }
    
    public void setOverrideOnKillEntityInferiors(final String[] overrideOnKillEntityInferiors) {
        this.overrideOnKillEntityInferiors = overrideOnKillEntityInferiors;
    }
    
    public void setAfterOnKillEntitySuperiors(final String[] afterOnKillEntitySuperiors) {
        this.afterOnKillEntitySuperiors = afterOnKillEntitySuperiors;
    }
    
    public void setAfterOnKillEntityInferiors(final String[] afterOnKillEntityInferiors) {
        this.afterOnKillEntityInferiors = afterOnKillEntityInferiors;
    }
    
    public String[] getBeforeOnStruckByLightningSuperiors() {
        return this.beforeOnStruckByLightningSuperiors;
    }
    
    public String[] getBeforeOnStruckByLightningInferiors() {
        return this.beforeOnStruckByLightningInferiors;
    }
    
    public String[] getOverrideOnStruckByLightningSuperiors() {
        return this.overrideOnStruckByLightningSuperiors;
    }
    
    public String[] getOverrideOnStruckByLightningInferiors() {
        return this.overrideOnStruckByLightningInferiors;
    }
    
    public String[] getAfterOnStruckByLightningSuperiors() {
        return this.afterOnStruckByLightningSuperiors;
    }
    
    public String[] getAfterOnStruckByLightningInferiors() {
        return this.afterOnStruckByLightningInferiors;
    }
    
    public void setBeforeOnStruckByLightningSuperiors(final String[] beforeOnStruckByLightningSuperiors) {
        this.beforeOnStruckByLightningSuperiors = beforeOnStruckByLightningSuperiors;
    }
    
    public void setBeforeOnStruckByLightningInferiors(final String[] beforeOnStruckByLightningInferiors) {
        this.beforeOnStruckByLightningInferiors = beforeOnStruckByLightningInferiors;
    }
    
    public void setOverrideOnStruckByLightningSuperiors(final String[] overrideOnStruckByLightningSuperiors) {
        this.overrideOnStruckByLightningSuperiors = overrideOnStruckByLightningSuperiors;
    }
    
    public void setOverrideOnStruckByLightningInferiors(final String[] overrideOnStruckByLightningInferiors) {
        this.overrideOnStruckByLightningInferiors = overrideOnStruckByLightningInferiors;
    }
    
    public void setAfterOnStruckByLightningSuperiors(final String[] afterOnStruckByLightningSuperiors) {
        this.afterOnStruckByLightningSuperiors = afterOnStruckByLightningSuperiors;
    }
    
    public void setAfterOnStruckByLightningInferiors(final String[] afterOnStruckByLightningInferiors) {
        this.afterOnStruckByLightningInferiors = afterOnStruckByLightningInferiors;
    }
    
    public String[] getBeforeOnUpdateSuperiors() {
        return this.beforeOnUpdateSuperiors;
    }
    
    public String[] getBeforeOnUpdateInferiors() {
        return this.beforeOnUpdateInferiors;
    }
    
    public String[] getOverrideOnUpdateSuperiors() {
        return this.overrideOnUpdateSuperiors;
    }
    
    public String[] getOverrideOnUpdateInferiors() {
        return this.overrideOnUpdateInferiors;
    }
    
    public String[] getAfterOnUpdateSuperiors() {
        return this.afterOnUpdateSuperiors;
    }
    
    public String[] getAfterOnUpdateInferiors() {
        return this.afterOnUpdateInferiors;
    }
    
    public void setBeforeOnUpdateSuperiors(final String[] beforeOnUpdateSuperiors) {
        this.beforeOnUpdateSuperiors = beforeOnUpdateSuperiors;
    }
    
    public void setBeforeOnUpdateInferiors(final String[] beforeOnUpdateInferiors) {
        this.beforeOnUpdateInferiors = beforeOnUpdateInferiors;
    }
    
    public void setOverrideOnUpdateSuperiors(final String[] overrideOnUpdateSuperiors) {
        this.overrideOnUpdateSuperiors = overrideOnUpdateSuperiors;
    }
    
    public void setOverrideOnUpdateInferiors(final String[] overrideOnUpdateInferiors) {
        this.overrideOnUpdateInferiors = overrideOnUpdateInferiors;
    }
    
    public void setAfterOnUpdateSuperiors(final String[] afterOnUpdateSuperiors) {
        this.afterOnUpdateSuperiors = afterOnUpdateSuperiors;
    }
    
    public void setAfterOnUpdateInferiors(final String[] afterOnUpdateInferiors) {
        this.afterOnUpdateInferiors = afterOnUpdateInferiors;
    }
    
    public String[] getBeforeOnUpdateEntitySuperiors() {
        return this.beforeOnUpdateEntitySuperiors;
    }
    
    public String[] getBeforeOnUpdateEntityInferiors() {
        return this.beforeOnUpdateEntityInferiors;
    }
    
    public String[] getOverrideOnUpdateEntitySuperiors() {
        return this.overrideOnUpdateEntitySuperiors;
    }
    
    public String[] getOverrideOnUpdateEntityInferiors() {
        return this.overrideOnUpdateEntityInferiors;
    }
    
    public String[] getAfterOnUpdateEntitySuperiors() {
        return this.afterOnUpdateEntitySuperiors;
    }
    
    public String[] getAfterOnUpdateEntityInferiors() {
        return this.afterOnUpdateEntityInferiors;
    }
    
    public void setBeforeOnUpdateEntitySuperiors(final String[] beforeOnUpdateEntitySuperiors) {
        this.beforeOnUpdateEntitySuperiors = beforeOnUpdateEntitySuperiors;
    }
    
    public void setBeforeOnUpdateEntityInferiors(final String[] beforeOnUpdateEntityInferiors) {
        this.beforeOnUpdateEntityInferiors = beforeOnUpdateEntityInferiors;
    }
    
    public void setOverrideOnUpdateEntitySuperiors(final String[] overrideOnUpdateEntitySuperiors) {
        this.overrideOnUpdateEntitySuperiors = overrideOnUpdateEntitySuperiors;
    }
    
    public void setOverrideOnUpdateEntityInferiors(final String[] overrideOnUpdateEntityInferiors) {
        this.overrideOnUpdateEntityInferiors = overrideOnUpdateEntityInferiors;
    }
    
    public void setAfterOnUpdateEntitySuperiors(final String[] afterOnUpdateEntitySuperiors) {
        this.afterOnUpdateEntitySuperiors = afterOnUpdateEntitySuperiors;
    }
    
    public void setAfterOnUpdateEntityInferiors(final String[] afterOnUpdateEntityInferiors) {
        this.afterOnUpdateEntityInferiors = afterOnUpdateEntityInferiors;
    }
    
    public String[] getBeforeReadEntityFromNBTSuperiors() {
        return this.beforeReadEntityFromNBTSuperiors;
    }
    
    public String[] getBeforeReadEntityFromNBTInferiors() {
        return this.beforeReadEntityFromNBTInferiors;
    }
    
    public String[] getOverrideReadEntityFromNBTSuperiors() {
        return this.overrideReadEntityFromNBTSuperiors;
    }
    
    public String[] getOverrideReadEntityFromNBTInferiors() {
        return this.overrideReadEntityFromNBTInferiors;
    }
    
    public String[] getAfterReadEntityFromNBTSuperiors() {
        return this.afterReadEntityFromNBTSuperiors;
    }
    
    public String[] getAfterReadEntityFromNBTInferiors() {
        return this.afterReadEntityFromNBTInferiors;
    }
    
    public void setBeforeReadEntityFromNBTSuperiors(final String[] beforeReadEntityFromNBTSuperiors) {
        this.beforeReadEntityFromNBTSuperiors = beforeReadEntityFromNBTSuperiors;
    }
    
    public void setBeforeReadEntityFromNBTInferiors(final String[] beforeReadEntityFromNBTInferiors) {
        this.beforeReadEntityFromNBTInferiors = beforeReadEntityFromNBTInferiors;
    }
    
    public void setOverrideReadEntityFromNBTSuperiors(final String[] overrideReadEntityFromNBTSuperiors) {
        this.overrideReadEntityFromNBTSuperiors = overrideReadEntityFromNBTSuperiors;
    }
    
    public void setOverrideReadEntityFromNBTInferiors(final String[] overrideReadEntityFromNBTInferiors) {
        this.overrideReadEntityFromNBTInferiors = overrideReadEntityFromNBTInferiors;
    }
    
    public void setAfterReadEntityFromNBTSuperiors(final String[] afterReadEntityFromNBTSuperiors) {
        this.afterReadEntityFromNBTSuperiors = afterReadEntityFromNBTSuperiors;
    }
    
    public void setAfterReadEntityFromNBTInferiors(final String[] afterReadEntityFromNBTInferiors) {
        this.afterReadEntityFromNBTInferiors = afterReadEntityFromNBTInferiors;
    }
    
    public String[] getBeforeSetDeadSuperiors() {
        return this.beforeSetDeadSuperiors;
    }
    
    public String[] getBeforeSetDeadInferiors() {
        return this.beforeSetDeadInferiors;
    }
    
    public String[] getOverrideSetDeadSuperiors() {
        return this.overrideSetDeadSuperiors;
    }
    
    public String[] getOverrideSetDeadInferiors() {
        return this.overrideSetDeadInferiors;
    }
    
    public String[] getAfterSetDeadSuperiors() {
        return this.afterSetDeadSuperiors;
    }
    
    public String[] getAfterSetDeadInferiors() {
        return this.afterSetDeadInferiors;
    }
    
    public void setBeforeSetDeadSuperiors(final String[] beforeSetDeadSuperiors) {
        this.beforeSetDeadSuperiors = beforeSetDeadSuperiors;
    }
    
    public void setBeforeSetDeadInferiors(final String[] beforeSetDeadInferiors) {
        this.beforeSetDeadInferiors = beforeSetDeadInferiors;
    }
    
    public void setOverrideSetDeadSuperiors(final String[] overrideSetDeadSuperiors) {
        this.overrideSetDeadSuperiors = overrideSetDeadSuperiors;
    }
    
    public void setOverrideSetDeadInferiors(final String[] overrideSetDeadInferiors) {
        this.overrideSetDeadInferiors = overrideSetDeadInferiors;
    }
    
    public void setAfterSetDeadSuperiors(final String[] afterSetDeadSuperiors) {
        this.afterSetDeadSuperiors = afterSetDeadSuperiors;
    }
    
    public void setAfterSetDeadInferiors(final String[] afterSetDeadInferiors) {
        this.afterSetDeadInferiors = afterSetDeadInferiors;
    }
    
    public String[] getBeforeSetPositionSuperiors() {
        return this.beforeSetPositionSuperiors;
    }
    
    public String[] getBeforeSetPositionInferiors() {
        return this.beforeSetPositionInferiors;
    }
    
    public String[] getOverrideSetPositionSuperiors() {
        return this.overrideSetPositionSuperiors;
    }
    
    public String[] getOverrideSetPositionInferiors() {
        return this.overrideSetPositionInferiors;
    }
    
    public String[] getAfterSetPositionSuperiors() {
        return this.afterSetPositionSuperiors;
    }
    
    public String[] getAfterSetPositionInferiors() {
        return this.afterSetPositionInferiors;
    }
    
    public void setBeforeSetPositionSuperiors(final String[] beforeSetPositionSuperiors) {
        this.beforeSetPositionSuperiors = beforeSetPositionSuperiors;
    }
    
    public void setBeforeSetPositionInferiors(final String[] beforeSetPositionInferiors) {
        this.beforeSetPositionInferiors = beforeSetPositionInferiors;
    }
    
    public void setOverrideSetPositionSuperiors(final String[] overrideSetPositionSuperiors) {
        this.overrideSetPositionSuperiors = overrideSetPositionSuperiors;
    }
    
    public void setOverrideSetPositionInferiors(final String[] overrideSetPositionInferiors) {
        this.overrideSetPositionInferiors = overrideSetPositionInferiors;
    }
    
    public void setAfterSetPositionSuperiors(final String[] afterSetPositionSuperiors) {
        this.afterSetPositionSuperiors = afterSetPositionSuperiors;
    }
    
    public void setAfterSetPositionInferiors(final String[] afterSetPositionInferiors) {
        this.afterSetPositionInferiors = afterSetPositionInferiors;
    }
    
    public String[] getBeforeSwingItemSuperiors() {
        return this.beforeSwingItemSuperiors;
    }
    
    public String[] getBeforeSwingItemInferiors() {
        return this.beforeSwingItemInferiors;
    }
    
    public String[] getOverrideSwingItemSuperiors() {
        return this.overrideSwingItemSuperiors;
    }
    
    public String[] getOverrideSwingItemInferiors() {
        return this.overrideSwingItemInferiors;
    }
    
    public String[] getAfterSwingItemSuperiors() {
        return this.afterSwingItemSuperiors;
    }
    
    public String[] getAfterSwingItemInferiors() {
        return this.afterSwingItemInferiors;
    }
    
    public void setBeforeSwingItemSuperiors(final String[] beforeSwingItemSuperiors) {
        this.beforeSwingItemSuperiors = beforeSwingItemSuperiors;
    }
    
    public void setBeforeSwingItemInferiors(final String[] beforeSwingItemInferiors) {
        this.beforeSwingItemInferiors = beforeSwingItemInferiors;
    }
    
    public void setOverrideSwingItemSuperiors(final String[] overrideSwingItemSuperiors) {
        this.overrideSwingItemSuperiors = overrideSwingItemSuperiors;
    }
    
    public void setOverrideSwingItemInferiors(final String[] overrideSwingItemInferiors) {
        this.overrideSwingItemInferiors = overrideSwingItemInferiors;
    }
    
    public void setAfterSwingItemSuperiors(final String[] afterSwingItemSuperiors) {
        this.afterSwingItemSuperiors = afterSwingItemSuperiors;
    }
    
    public void setAfterSwingItemInferiors(final String[] afterSwingItemInferiors) {
        this.afterSwingItemInferiors = afterSwingItemInferiors;
    }
    
    public String[] getBeforeUpdateEntityActionStateSuperiors() {
        return this.beforeUpdateEntityActionStateSuperiors;
    }
    
    public String[] getBeforeUpdateEntityActionStateInferiors() {
        return this.beforeUpdateEntityActionStateInferiors;
    }
    
    public String[] getOverrideUpdateEntityActionStateSuperiors() {
        return this.overrideUpdateEntityActionStateSuperiors;
    }
    
    public String[] getOverrideUpdateEntityActionStateInferiors() {
        return this.overrideUpdateEntityActionStateInferiors;
    }
    
    public String[] getAfterUpdateEntityActionStateSuperiors() {
        return this.afterUpdateEntityActionStateSuperiors;
    }
    
    public String[] getAfterUpdateEntityActionStateInferiors() {
        return this.afterUpdateEntityActionStateInferiors;
    }
    
    public void setBeforeUpdateEntityActionStateSuperiors(final String[] beforeUpdateEntityActionStateSuperiors) {
        this.beforeUpdateEntityActionStateSuperiors = beforeUpdateEntityActionStateSuperiors;
    }
    
    public void setBeforeUpdateEntityActionStateInferiors(final String[] beforeUpdateEntityActionStateInferiors) {
        this.beforeUpdateEntityActionStateInferiors = beforeUpdateEntityActionStateInferiors;
    }
    
    public void setOverrideUpdateEntityActionStateSuperiors(final String[] overrideUpdateEntityActionStateSuperiors) {
        this.overrideUpdateEntityActionStateSuperiors = overrideUpdateEntityActionStateSuperiors;
    }
    
    public void setOverrideUpdateEntityActionStateInferiors(final String[] overrideUpdateEntityActionStateInferiors) {
        this.overrideUpdateEntityActionStateInferiors = overrideUpdateEntityActionStateInferiors;
    }
    
    public void setAfterUpdateEntityActionStateSuperiors(final String[] afterUpdateEntityActionStateSuperiors) {
        this.afterUpdateEntityActionStateSuperiors = afterUpdateEntityActionStateSuperiors;
    }
    
    public void setAfterUpdateEntityActionStateInferiors(final String[] afterUpdateEntityActionStateInferiors) {
        this.afterUpdateEntityActionStateInferiors = afterUpdateEntityActionStateInferiors;
    }
    
    public String[] getBeforeUpdatePotionEffectsSuperiors() {
        return this.beforeUpdatePotionEffectsSuperiors;
    }
    
    public String[] getBeforeUpdatePotionEffectsInferiors() {
        return this.beforeUpdatePotionEffectsInferiors;
    }
    
    public String[] getOverrideUpdatePotionEffectsSuperiors() {
        return this.overrideUpdatePotionEffectsSuperiors;
    }
    
    public String[] getOverrideUpdatePotionEffectsInferiors() {
        return this.overrideUpdatePotionEffectsInferiors;
    }
    
    public String[] getAfterUpdatePotionEffectsSuperiors() {
        return this.afterUpdatePotionEffectsSuperiors;
    }
    
    public String[] getAfterUpdatePotionEffectsInferiors() {
        return this.afterUpdatePotionEffectsInferiors;
    }
    
    public void setBeforeUpdatePotionEffectsSuperiors(final String[] beforeUpdatePotionEffectsSuperiors) {
        this.beforeUpdatePotionEffectsSuperiors = beforeUpdatePotionEffectsSuperiors;
    }
    
    public void setBeforeUpdatePotionEffectsInferiors(final String[] beforeUpdatePotionEffectsInferiors) {
        this.beforeUpdatePotionEffectsInferiors = beforeUpdatePotionEffectsInferiors;
    }
    
    public void setOverrideUpdatePotionEffectsSuperiors(final String[] overrideUpdatePotionEffectsSuperiors) {
        this.overrideUpdatePotionEffectsSuperiors = overrideUpdatePotionEffectsSuperiors;
    }
    
    public void setOverrideUpdatePotionEffectsInferiors(final String[] overrideUpdatePotionEffectsInferiors) {
        this.overrideUpdatePotionEffectsInferiors = overrideUpdatePotionEffectsInferiors;
    }
    
    public void setAfterUpdatePotionEffectsSuperiors(final String[] afterUpdatePotionEffectsSuperiors) {
        this.afterUpdatePotionEffectsSuperiors = afterUpdatePotionEffectsSuperiors;
    }
    
    public void setAfterUpdatePotionEffectsInferiors(final String[] afterUpdatePotionEffectsInferiors) {
        this.afterUpdatePotionEffectsInferiors = afterUpdatePotionEffectsInferiors;
    }
    
    public String[] getBeforeWriteEntityToNBTSuperiors() {
        return this.beforeWriteEntityToNBTSuperiors;
    }
    
    public String[] getBeforeWriteEntityToNBTInferiors() {
        return this.beforeWriteEntityToNBTInferiors;
    }
    
    public String[] getOverrideWriteEntityToNBTSuperiors() {
        return this.overrideWriteEntityToNBTSuperiors;
    }
    
    public String[] getOverrideWriteEntityToNBTInferiors() {
        return this.overrideWriteEntityToNBTInferiors;
    }
    
    public String[] getAfterWriteEntityToNBTSuperiors() {
        return this.afterWriteEntityToNBTSuperiors;
    }
    
    public String[] getAfterWriteEntityToNBTInferiors() {
        return this.afterWriteEntityToNBTInferiors;
    }
    
    public void setBeforeWriteEntityToNBTSuperiors(final String[] beforeWriteEntityToNBTSuperiors) {
        this.beforeWriteEntityToNBTSuperiors = beforeWriteEntityToNBTSuperiors;
    }
    
    public void setBeforeWriteEntityToNBTInferiors(final String[] beforeWriteEntityToNBTInferiors) {
        this.beforeWriteEntityToNBTInferiors = beforeWriteEntityToNBTInferiors;
    }
    
    public void setOverrideWriteEntityToNBTSuperiors(final String[] overrideWriteEntityToNBTSuperiors) {
        this.overrideWriteEntityToNBTSuperiors = overrideWriteEntityToNBTSuperiors;
    }
    
    public void setOverrideWriteEntityToNBTInferiors(final String[] overrideWriteEntityToNBTInferiors) {
        this.overrideWriteEntityToNBTInferiors = overrideWriteEntityToNBTInferiors;
    }
    
    public void setAfterWriteEntityToNBTSuperiors(final String[] afterWriteEntityToNBTSuperiors) {
        this.afterWriteEntityToNBTSuperiors = afterWriteEntityToNBTSuperiors;
    }
    
    public void setAfterWriteEntityToNBTInferiors(final String[] afterWriteEntityToNBTInferiors) {
        this.afterWriteEntityToNBTInferiors = afterWriteEntityToNBTInferiors;
    }
}
