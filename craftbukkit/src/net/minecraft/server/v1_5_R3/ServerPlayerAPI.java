// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.ArrayList;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import org.bukkit.entity.Player;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.Bukkit;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public final class ServerPlayerAPI
{
    private static final Class<?>[] Class;
    private static final Class<?>[] Classes;
    private static boolean isCreated;
    private static final Logger logger;
    private static final Map<String, String[]> EmptySortMap;
    private static final Object[] initializer;
    private static final Object[] initializers;
    private static final List<String> beforeAddExhaustionHookTypes;
    private static final List<String> overrideAddExhaustionHookTypes;
    private static final List<String> afterAddExhaustionHookTypes;
    private ServerPlayerBase[] beforeAddExhaustionHooks;
    private ServerPlayerBase[] overrideAddExhaustionHooks;
    private ServerPlayerBase[] afterAddExhaustionHooks;
    public boolean isAddExhaustionModded;
    private static final Map<String, String[]> allBaseBeforeAddExhaustionSuperiors;
    private static final Map<String, String[]> allBaseBeforeAddExhaustionInferiors;
    private static final Map<String, String[]> allBaseOverrideAddExhaustionSuperiors;
    private static final Map<String, String[]> allBaseOverrideAddExhaustionInferiors;
    private static final Map<String, String[]> allBaseAfterAddExhaustionSuperiors;
    private static final Map<String, String[]> allBaseAfterAddExhaustionInferiors;
    private static final List<String> beforeAddExperienceHookTypes;
    private static final List<String> overrideAddExperienceHookTypes;
    private static final List<String> afterAddExperienceHookTypes;
    private ServerPlayerBase[] beforeAddExperienceHooks;
    private ServerPlayerBase[] overrideAddExperienceHooks;
    private ServerPlayerBase[] afterAddExperienceHooks;
    public boolean isAddExperienceModded;
    private static final Map<String, String[]> allBaseBeforeAddExperienceSuperiors;
    private static final Map<String, String[]> allBaseBeforeAddExperienceInferiors;
    private static final Map<String, String[]> allBaseOverrideAddExperienceSuperiors;
    private static final Map<String, String[]> allBaseOverrideAddExperienceInferiors;
    private static final Map<String, String[]> allBaseAfterAddExperienceSuperiors;
    private static final Map<String, String[]> allBaseAfterAddExperienceInferiors;
    private static final List<String> beforeAddExperienceLevelHookTypes;
    private static final List<String> overrideAddExperienceLevelHookTypes;
    private static final List<String> afterAddExperienceLevelHookTypes;
    private ServerPlayerBase[] beforeAddExperienceLevelHooks;
    private ServerPlayerBase[] overrideAddExperienceLevelHooks;
    private ServerPlayerBase[] afterAddExperienceLevelHooks;
    public boolean isAddExperienceLevelModded;
    private static final Map<String, String[]> allBaseBeforeAddExperienceLevelSuperiors;
    private static final Map<String, String[]> allBaseBeforeAddExperienceLevelInferiors;
    private static final Map<String, String[]> allBaseOverrideAddExperienceLevelSuperiors;
    private static final Map<String, String[]> allBaseOverrideAddExperienceLevelInferiors;
    private static final Map<String, String[]> allBaseAfterAddExperienceLevelSuperiors;
    private static final Map<String, String[]> allBaseAfterAddExperienceLevelInferiors;
    private static final List<String> beforeAddMovementStatHookTypes;
    private static final List<String> overrideAddMovementStatHookTypes;
    private static final List<String> afterAddMovementStatHookTypes;
    private ServerPlayerBase[] beforeAddMovementStatHooks;
    private ServerPlayerBase[] overrideAddMovementStatHooks;
    private ServerPlayerBase[] afterAddMovementStatHooks;
    public boolean isAddMovementStatModded;
    private static final Map<String, String[]> allBaseBeforeAddMovementStatSuperiors;
    private static final Map<String, String[]> allBaseBeforeAddMovementStatInferiors;
    private static final Map<String, String[]> allBaseOverrideAddMovementStatSuperiors;
    private static final Map<String, String[]> allBaseOverrideAddMovementStatInferiors;
    private static final Map<String, String[]> allBaseAfterAddMovementStatSuperiors;
    private static final Map<String, String[]> allBaseAfterAddMovementStatInferiors;
    private static final List<String> beforeAttackEntityFromHookTypes;
    private static final List<String> overrideAttackEntityFromHookTypes;
    private static final List<String> afterAttackEntityFromHookTypes;
    private ServerPlayerBase[] beforeAttackEntityFromHooks;
    private ServerPlayerBase[] overrideAttackEntityFromHooks;
    private ServerPlayerBase[] afterAttackEntityFromHooks;
    public boolean isAttackEntityFromModded;
    private static final Map<String, String[]> allBaseBeforeAttackEntityFromSuperiors;
    private static final Map<String, String[]> allBaseBeforeAttackEntityFromInferiors;
    private static final Map<String, String[]> allBaseOverrideAttackEntityFromSuperiors;
    private static final Map<String, String[]> allBaseOverrideAttackEntityFromInferiors;
    private static final Map<String, String[]> allBaseAfterAttackEntityFromSuperiors;
    private static final Map<String, String[]> allBaseAfterAttackEntityFromInferiors;
    private static final List<String> beforeAttackTargetEntityWithCurrentItemHookTypes;
    private static final List<String> overrideAttackTargetEntityWithCurrentItemHookTypes;
    private static final List<String> afterAttackTargetEntityWithCurrentItemHookTypes;
    private ServerPlayerBase[] beforeAttackTargetEntityWithCurrentItemHooks;
    private ServerPlayerBase[] overrideAttackTargetEntityWithCurrentItemHooks;
    private ServerPlayerBase[] afterAttackTargetEntityWithCurrentItemHooks;
    public boolean isAttackTargetEntityWithCurrentItemModded;
    private static final Map<String, String[]> allBaseBeforeAttackTargetEntityWithCurrentItemSuperiors;
    private static final Map<String, String[]> allBaseBeforeAttackTargetEntityWithCurrentItemInferiors;
    private static final Map<String, String[]> allBaseOverrideAttackTargetEntityWithCurrentItemSuperiors;
    private static final Map<String, String[]> allBaseOverrideAttackTargetEntityWithCurrentItemInferiors;
    private static final Map<String, String[]> allBaseAfterAttackTargetEntityWithCurrentItemSuperiors;
    private static final Map<String, String[]> allBaseAfterAttackTargetEntityWithCurrentItemInferiors;
    private static final List<String> beforeCanHarvestBlockHookTypes;
    private static final List<String> overrideCanHarvestBlockHookTypes;
    private static final List<String> afterCanHarvestBlockHookTypes;
    private ServerPlayerBase[] beforeCanHarvestBlockHooks;
    private ServerPlayerBase[] overrideCanHarvestBlockHooks;
    private ServerPlayerBase[] afterCanHarvestBlockHooks;
    public boolean isCanHarvestBlockModded;
    private static final Map<String, String[]> allBaseBeforeCanHarvestBlockSuperiors;
    private static final Map<String, String[]> allBaseBeforeCanHarvestBlockInferiors;
    private static final Map<String, String[]> allBaseOverrideCanHarvestBlockSuperiors;
    private static final Map<String, String[]> allBaseOverrideCanHarvestBlockInferiors;
    private static final Map<String, String[]> allBaseAfterCanHarvestBlockSuperiors;
    private static final Map<String, String[]> allBaseAfterCanHarvestBlockInferiors;
    private static final List<String> beforeCanPlayerEditHookTypes;
    private static final List<String> overrideCanPlayerEditHookTypes;
    private static final List<String> afterCanPlayerEditHookTypes;
    private ServerPlayerBase[] beforeCanPlayerEditHooks;
    private ServerPlayerBase[] overrideCanPlayerEditHooks;
    private ServerPlayerBase[] afterCanPlayerEditHooks;
    public boolean isCanPlayerEditModded;
    private static final Map<String, String[]> allBaseBeforeCanPlayerEditSuperiors;
    private static final Map<String, String[]> allBaseBeforeCanPlayerEditInferiors;
    private static final Map<String, String[]> allBaseOverrideCanPlayerEditSuperiors;
    private static final Map<String, String[]> allBaseOverrideCanPlayerEditInferiors;
    private static final Map<String, String[]> allBaseAfterCanPlayerEditSuperiors;
    private static final Map<String, String[]> allBaseAfterCanPlayerEditInferiors;
    private static final List<String> beforeCanTriggerWalkingHookTypes;
    private static final List<String> overrideCanTriggerWalkingHookTypes;
    private static final List<String> afterCanTriggerWalkingHookTypes;
    private ServerPlayerBase[] beforeCanTriggerWalkingHooks;
    private ServerPlayerBase[] overrideCanTriggerWalkingHooks;
    private ServerPlayerBase[] afterCanTriggerWalkingHooks;
    public boolean isCanTriggerWalkingModded;
    private static final Map<String, String[]> allBaseBeforeCanTriggerWalkingSuperiors;
    private static final Map<String, String[]> allBaseBeforeCanTriggerWalkingInferiors;
    private static final Map<String, String[]> allBaseOverrideCanTriggerWalkingSuperiors;
    private static final Map<String, String[]> allBaseOverrideCanTriggerWalkingInferiors;
    private static final Map<String, String[]> allBaseAfterCanTriggerWalkingSuperiors;
    private static final Map<String, String[]> allBaseAfterCanTriggerWalkingInferiors;
    private static final List<String> beforeClonePlayerHookTypes;
    private static final List<String> overrideClonePlayerHookTypes;
    private static final List<String> afterClonePlayerHookTypes;
    private ServerPlayerBase[] beforeClonePlayerHooks;
    private ServerPlayerBase[] overrideClonePlayerHooks;
    private ServerPlayerBase[] afterClonePlayerHooks;
    public boolean isClonePlayerModded;
    private static final Map<String, String[]> allBaseBeforeClonePlayerSuperiors;
    private static final Map<String, String[]> allBaseBeforeClonePlayerInferiors;
    private static final Map<String, String[]> allBaseOverrideClonePlayerSuperiors;
    private static final Map<String, String[]> allBaseOverrideClonePlayerInferiors;
    private static final Map<String, String[]> allBaseAfterClonePlayerSuperiors;
    private static final Map<String, String[]> allBaseAfterClonePlayerInferiors;
    private static final List<String> beforeDamageEntityHookTypes;
    private static final List<String> overrideDamageEntityHookTypes;
    private static final List<String> afterDamageEntityHookTypes;
    private ServerPlayerBase[] beforeDamageEntityHooks;
    private ServerPlayerBase[] overrideDamageEntityHooks;
    private ServerPlayerBase[] afterDamageEntityHooks;
    public boolean isDamageEntityModded;
    private static final Map<String, String[]> allBaseBeforeDamageEntitySuperiors;
    private static final Map<String, String[]> allBaseBeforeDamageEntityInferiors;
    private static final Map<String, String[]> allBaseOverrideDamageEntitySuperiors;
    private static final Map<String, String[]> allBaseOverrideDamageEntityInferiors;
    private static final Map<String, String[]> allBaseAfterDamageEntitySuperiors;
    private static final Map<String, String[]> allBaseAfterDamageEntityInferiors;
    private static final List<String> beforeDisplayGUIChestHookTypes;
    private static final List<String> overrideDisplayGUIChestHookTypes;
    private static final List<String> afterDisplayGUIChestHookTypes;
    private ServerPlayerBase[] beforeDisplayGUIChestHooks;
    private ServerPlayerBase[] overrideDisplayGUIChestHooks;
    private ServerPlayerBase[] afterDisplayGUIChestHooks;
    public boolean isDisplayGUIChestModded;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIChestSuperiors;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIChestInferiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIChestSuperiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIChestInferiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIChestSuperiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIChestInferiors;
    private static final List<String> beforeDisplayGUIDispenserHookTypes;
    private static final List<String> overrideDisplayGUIDispenserHookTypes;
    private static final List<String> afterDisplayGUIDispenserHookTypes;
    private ServerPlayerBase[] beforeDisplayGUIDispenserHooks;
    private ServerPlayerBase[] overrideDisplayGUIDispenserHooks;
    private ServerPlayerBase[] afterDisplayGUIDispenserHooks;
    public boolean isDisplayGUIDispenserModded;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIDispenserSuperiors;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIDispenserInferiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIDispenserSuperiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIDispenserInferiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIDispenserSuperiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIDispenserInferiors;
    private static final List<String> beforeDisplayGUIFurnaceHookTypes;
    private static final List<String> overrideDisplayGUIFurnaceHookTypes;
    private static final List<String> afterDisplayGUIFurnaceHookTypes;
    private ServerPlayerBase[] beforeDisplayGUIFurnaceHooks;
    private ServerPlayerBase[] overrideDisplayGUIFurnaceHooks;
    private ServerPlayerBase[] afterDisplayGUIFurnaceHooks;
    public boolean isDisplayGUIFurnaceModded;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIFurnaceSuperiors;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIFurnaceInferiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIFurnaceSuperiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIFurnaceInferiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIFurnaceSuperiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIFurnaceInferiors;
    private static final List<String> beforeDisplayGUIWorkbenchHookTypes;
    private static final List<String> overrideDisplayGUIWorkbenchHookTypes;
    private static final List<String> afterDisplayGUIWorkbenchHookTypes;
    private ServerPlayerBase[] beforeDisplayGUIWorkbenchHooks;
    private ServerPlayerBase[] overrideDisplayGUIWorkbenchHooks;
    private ServerPlayerBase[] afterDisplayGUIWorkbenchHooks;
    public boolean isDisplayGUIWorkbenchModded;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIWorkbenchSuperiors;
    private static final Map<String, String[]> allBaseBeforeDisplayGUIWorkbenchInferiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIWorkbenchSuperiors;
    private static final Map<String, String[]> allBaseOverrideDisplayGUIWorkbenchInferiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIWorkbenchSuperiors;
    private static final Map<String, String[]> allBaseAfterDisplayGUIWorkbenchInferiors;
    private static final List<String> beforeDropOneItemHookTypes;
    private static final List<String> overrideDropOneItemHookTypes;
    private static final List<String> afterDropOneItemHookTypes;
    private ServerPlayerBase[] beforeDropOneItemHooks;
    private ServerPlayerBase[] overrideDropOneItemHooks;
    private ServerPlayerBase[] afterDropOneItemHooks;
    public boolean isDropOneItemModded;
    private static final Map<String, String[]> allBaseBeforeDropOneItemSuperiors;
    private static final Map<String, String[]> allBaseBeforeDropOneItemInferiors;
    private static final Map<String, String[]> allBaseOverrideDropOneItemSuperiors;
    private static final Map<String, String[]> allBaseOverrideDropOneItemInferiors;
    private static final Map<String, String[]> allBaseAfterDropOneItemSuperiors;
    private static final Map<String, String[]> allBaseAfterDropOneItemInferiors;
    private static final List<String> beforeDropPlayerItemHookTypes;
    private static final List<String> overrideDropPlayerItemHookTypes;
    private static final List<String> afterDropPlayerItemHookTypes;
    private ServerPlayerBase[] beforeDropPlayerItemHooks;
    private ServerPlayerBase[] overrideDropPlayerItemHooks;
    private ServerPlayerBase[] afterDropPlayerItemHooks;
    public boolean isDropPlayerItemModded;
    private static final Map<String, String[]> allBaseBeforeDropPlayerItemSuperiors;
    private static final Map<String, String[]> allBaseBeforeDropPlayerItemInferiors;
    private static final Map<String, String[]> allBaseOverrideDropPlayerItemSuperiors;
    private static final Map<String, String[]> allBaseOverrideDropPlayerItemInferiors;
    private static final Map<String, String[]> allBaseAfterDropPlayerItemSuperiors;
    private static final Map<String, String[]> allBaseAfterDropPlayerItemInferiors;
    private static final List<String> beforeFallHookTypes;
    private static final List<String> overrideFallHookTypes;
    private static final List<String> afterFallHookTypes;
    private ServerPlayerBase[] beforeFallHooks;
    private ServerPlayerBase[] overrideFallHooks;
    private ServerPlayerBase[] afterFallHooks;
    public boolean isFallModded;
    private static final Map<String, String[]> allBaseBeforeFallSuperiors;
    private static final Map<String, String[]> allBaseBeforeFallInferiors;
    private static final Map<String, String[]> allBaseOverrideFallSuperiors;
    private static final Map<String, String[]> allBaseOverrideFallInferiors;
    private static final Map<String, String[]> allBaseAfterFallSuperiors;
    private static final Map<String, String[]> allBaseAfterFallInferiors;
    private static final List<String> beforeGetCurrentPlayerStrVsBlockHookTypes;
    private static final List<String> overrideGetCurrentPlayerStrVsBlockHookTypes;
    private static final List<String> afterGetCurrentPlayerStrVsBlockHookTypes;
    private ServerPlayerBase[] beforeGetCurrentPlayerStrVsBlockHooks;
    private ServerPlayerBase[] overrideGetCurrentPlayerStrVsBlockHooks;
    private ServerPlayerBase[] afterGetCurrentPlayerStrVsBlockHooks;
    public boolean isGetCurrentPlayerStrVsBlockModded;
    private static final Map<String, String[]> allBaseBeforeGetCurrentPlayerStrVsBlockSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetCurrentPlayerStrVsBlockInferiors;
    private static final Map<String, String[]> allBaseOverrideGetCurrentPlayerStrVsBlockSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetCurrentPlayerStrVsBlockInferiors;
    private static final Map<String, String[]> allBaseAfterGetCurrentPlayerStrVsBlockSuperiors;
    private static final Map<String, String[]> allBaseAfterGetCurrentPlayerStrVsBlockInferiors;
    private static final List<String> beforeGetDistanceSqHookTypes;
    private static final List<String> overrideGetDistanceSqHookTypes;
    private static final List<String> afterGetDistanceSqHookTypes;
    private ServerPlayerBase[] beforeGetDistanceSqHooks;
    private ServerPlayerBase[] overrideGetDistanceSqHooks;
    private ServerPlayerBase[] afterGetDistanceSqHooks;
    public boolean isGetDistanceSqModded;
    private static final Map<String, String[]> allBaseBeforeGetDistanceSqSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetDistanceSqInferiors;
    private static final Map<String, String[]> allBaseOverrideGetDistanceSqSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetDistanceSqInferiors;
    private static final Map<String, String[]> allBaseAfterGetDistanceSqSuperiors;
    private static final Map<String, String[]> allBaseAfterGetDistanceSqInferiors;
    private static final List<String> beforeGetBrightnessHookTypes;
    private static final List<String> overrideGetBrightnessHookTypes;
    private static final List<String> afterGetBrightnessHookTypes;
    private ServerPlayerBase[] beforeGetBrightnessHooks;
    private ServerPlayerBase[] overrideGetBrightnessHooks;
    private ServerPlayerBase[] afterGetBrightnessHooks;
    public boolean isGetBrightnessModded;
    private static final Map<String, String[]> allBaseBeforeGetBrightnessSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetBrightnessInferiors;
    private static final Map<String, String[]> allBaseOverrideGetBrightnessSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetBrightnessInferiors;
    private static final Map<String, String[]> allBaseAfterGetBrightnessSuperiors;
    private static final Map<String, String[]> allBaseAfterGetBrightnessInferiors;
    private static final List<String> beforeGetEyeHeightHookTypes;
    private static final List<String> overrideGetEyeHeightHookTypes;
    private static final List<String> afterGetEyeHeightHookTypes;
    private ServerPlayerBase[] beforeGetEyeHeightHooks;
    private ServerPlayerBase[] overrideGetEyeHeightHooks;
    private ServerPlayerBase[] afterGetEyeHeightHooks;
    public boolean isGetEyeHeightModded;
    private static final Map<String, String[]> allBaseBeforeGetEyeHeightSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetEyeHeightInferiors;
    private static final Map<String, String[]> allBaseOverrideGetEyeHeightSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetEyeHeightInferiors;
    private static final Map<String, String[]> allBaseAfterGetEyeHeightSuperiors;
    private static final Map<String, String[]> allBaseAfterGetEyeHeightInferiors;
    private static final List<String> beforeGetMaxHealthHookTypes;
    private static final List<String> overrideGetMaxHealthHookTypes;
    private static final List<String> afterGetMaxHealthHookTypes;
    private ServerPlayerBase[] beforeGetMaxHealthHooks;
    private ServerPlayerBase[] overrideGetMaxHealthHooks;
    private ServerPlayerBase[] afterGetMaxHealthHooks;
    public boolean isGetMaxHealthModded;
    private static final Map<String, String[]> allBaseBeforeGetMaxHealthSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetMaxHealthInferiors;
    private static final Map<String, String[]> allBaseOverrideGetMaxHealthSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetMaxHealthInferiors;
    private static final Map<String, String[]> allBaseAfterGetMaxHealthSuperiors;
    private static final Map<String, String[]> allBaseAfterGetMaxHealthInferiors;
    private static final List<String> beforeGetSpeedModifierHookTypes;
    private static final List<String> overrideGetSpeedModifierHookTypes;
    private static final List<String> afterGetSpeedModifierHookTypes;
    private ServerPlayerBase[] beforeGetSpeedModifierHooks;
    private ServerPlayerBase[] overrideGetSpeedModifierHooks;
    private ServerPlayerBase[] afterGetSpeedModifierHooks;
    public boolean isGetSpeedModifierModded;
    private static final Map<String, String[]> allBaseBeforeGetSpeedModifierSuperiors;
    private static final Map<String, String[]> allBaseBeforeGetSpeedModifierInferiors;
    private static final Map<String, String[]> allBaseOverrideGetSpeedModifierSuperiors;
    private static final Map<String, String[]> allBaseOverrideGetSpeedModifierInferiors;
    private static final Map<String, String[]> allBaseAfterGetSpeedModifierSuperiors;
    private static final Map<String, String[]> allBaseAfterGetSpeedModifierInferiors;
    private static final List<String> beforeHealHookTypes;
    private static final List<String> overrideHealHookTypes;
    private static final List<String> afterHealHookTypes;
    private ServerPlayerBase[] beforeHealHooks;
    private ServerPlayerBase[] overrideHealHooks;
    private ServerPlayerBase[] afterHealHooks;
    public boolean isHealModded;
    private static final Map<String, String[]> allBaseBeforeHealSuperiors;
    private static final Map<String, String[]> allBaseBeforeHealInferiors;
    private static final Map<String, String[]> allBaseOverrideHealSuperiors;
    private static final Map<String, String[]> allBaseOverrideHealInferiors;
    private static final Map<String, String[]> allBaseAfterHealSuperiors;
    private static final Map<String, String[]> allBaseAfterHealInferiors;
    private static final List<String> beforeInteractHookTypes;
    private static final List<String> overrideInteractHookTypes;
    private static final List<String> afterInteractHookTypes;
    private ServerPlayerBase[] beforeInteractHooks;
    private ServerPlayerBase[] overrideInteractHooks;
    private ServerPlayerBase[] afterInteractHooks;
    public boolean isInteractModded;
    private static final Map<String, String[]> allBaseBeforeInteractSuperiors;
    private static final Map<String, String[]> allBaseBeforeInteractInferiors;
    private static final Map<String, String[]> allBaseOverrideInteractSuperiors;
    private static final Map<String, String[]> allBaseOverrideInteractInferiors;
    private static final Map<String, String[]> allBaseAfterInteractSuperiors;
    private static final Map<String, String[]> allBaseAfterInteractInferiors;
    private static final List<String> beforeIsEntityInsideOpaqueBlockHookTypes;
    private static final List<String> overrideIsEntityInsideOpaqueBlockHookTypes;
    private static final List<String> afterIsEntityInsideOpaqueBlockHookTypes;
    private ServerPlayerBase[] beforeIsEntityInsideOpaqueBlockHooks;
    private ServerPlayerBase[] overrideIsEntityInsideOpaqueBlockHooks;
    private ServerPlayerBase[] afterIsEntityInsideOpaqueBlockHooks;
    public boolean isIsEntityInsideOpaqueBlockModded;
    private static final Map<String, String[]> allBaseBeforeIsEntityInsideOpaqueBlockSuperiors;
    private static final Map<String, String[]> allBaseBeforeIsEntityInsideOpaqueBlockInferiors;
    private static final Map<String, String[]> allBaseOverrideIsEntityInsideOpaqueBlockSuperiors;
    private static final Map<String, String[]> allBaseOverrideIsEntityInsideOpaqueBlockInferiors;
    private static final Map<String, String[]> allBaseAfterIsEntityInsideOpaqueBlockSuperiors;
    private static final Map<String, String[]> allBaseAfterIsEntityInsideOpaqueBlockInferiors;
    private static final List<String> beforeIsInWaterHookTypes;
    private static final List<String> overrideIsInWaterHookTypes;
    private static final List<String> afterIsInWaterHookTypes;
    private ServerPlayerBase[] beforeIsInWaterHooks;
    private ServerPlayerBase[] overrideIsInWaterHooks;
    private ServerPlayerBase[] afterIsInWaterHooks;
    public boolean isIsInWaterModded;
    private static final Map<String, String[]> allBaseBeforeIsInWaterSuperiors;
    private static final Map<String, String[]> allBaseBeforeIsInWaterInferiors;
    private static final Map<String, String[]> allBaseOverrideIsInWaterSuperiors;
    private static final Map<String, String[]> allBaseOverrideIsInWaterInferiors;
    private static final Map<String, String[]> allBaseAfterIsInWaterSuperiors;
    private static final Map<String, String[]> allBaseAfterIsInWaterInferiors;
    private static final List<String> beforeIsInsideOfMaterialHookTypes;
    private static final List<String> overrideIsInsideOfMaterialHookTypes;
    private static final List<String> afterIsInsideOfMaterialHookTypes;
    private ServerPlayerBase[] beforeIsInsideOfMaterialHooks;
    private ServerPlayerBase[] overrideIsInsideOfMaterialHooks;
    private ServerPlayerBase[] afterIsInsideOfMaterialHooks;
    public boolean isIsInsideOfMaterialModded;
    private static final Map<String, String[]> allBaseBeforeIsInsideOfMaterialSuperiors;
    private static final Map<String, String[]> allBaseBeforeIsInsideOfMaterialInferiors;
    private static final Map<String, String[]> allBaseOverrideIsInsideOfMaterialSuperiors;
    private static final Map<String, String[]> allBaseOverrideIsInsideOfMaterialInferiors;
    private static final Map<String, String[]> allBaseAfterIsInsideOfMaterialSuperiors;
    private static final Map<String, String[]> allBaseAfterIsInsideOfMaterialInferiors;
    private static final List<String> beforeIsOnLadderHookTypes;
    private static final List<String> overrideIsOnLadderHookTypes;
    private static final List<String> afterIsOnLadderHookTypes;
    private ServerPlayerBase[] beforeIsOnLadderHooks;
    private ServerPlayerBase[] overrideIsOnLadderHooks;
    private ServerPlayerBase[] afterIsOnLadderHooks;
    public boolean isIsOnLadderModded;
    private static final Map<String, String[]> allBaseBeforeIsOnLadderSuperiors;
    private static final Map<String, String[]> allBaseBeforeIsOnLadderInferiors;
    private static final Map<String, String[]> allBaseOverrideIsOnLadderSuperiors;
    private static final Map<String, String[]> allBaseOverrideIsOnLadderInferiors;
    private static final Map<String, String[]> allBaseAfterIsOnLadderSuperiors;
    private static final Map<String, String[]> allBaseAfterIsOnLadderInferiors;
    private static final List<String> beforeIsPlayerSleepingHookTypes;
    private static final List<String> overrideIsPlayerSleepingHookTypes;
    private static final List<String> afterIsPlayerSleepingHookTypes;
    private ServerPlayerBase[] beforeIsPlayerSleepingHooks;
    private ServerPlayerBase[] overrideIsPlayerSleepingHooks;
    private ServerPlayerBase[] afterIsPlayerSleepingHooks;
    public boolean isIsPlayerSleepingModded;
    private static final Map<String, String[]> allBaseBeforeIsPlayerSleepingSuperiors;
    private static final Map<String, String[]> allBaseBeforeIsPlayerSleepingInferiors;
    private static final Map<String, String[]> allBaseOverrideIsPlayerSleepingSuperiors;
    private static final Map<String, String[]> allBaseOverrideIsPlayerSleepingInferiors;
    private static final Map<String, String[]> allBaseAfterIsPlayerSleepingSuperiors;
    private static final Map<String, String[]> allBaseAfterIsPlayerSleepingInferiors;
    private static final List<String> beforeJumpHookTypes;
    private static final List<String> overrideJumpHookTypes;
    private static final List<String> afterJumpHookTypes;
    private ServerPlayerBase[] beforeJumpHooks;
    private ServerPlayerBase[] overrideJumpHooks;
    private ServerPlayerBase[] afterJumpHooks;
    public boolean isJumpModded;
    private static final Map<String, String[]> allBaseBeforeJumpSuperiors;
    private static final Map<String, String[]> allBaseBeforeJumpInferiors;
    private static final Map<String, String[]> allBaseOverrideJumpSuperiors;
    private static final Map<String, String[]> allBaseOverrideJumpInferiors;
    private static final Map<String, String[]> allBaseAfterJumpSuperiors;
    private static final Map<String, String[]> allBaseAfterJumpInferiors;
    private static final List<String> beforeKnockBackHookTypes;
    private static final List<String> overrideKnockBackHookTypes;
    private static final List<String> afterKnockBackHookTypes;
    private ServerPlayerBase[] beforeKnockBackHooks;
    private ServerPlayerBase[] overrideKnockBackHooks;
    private ServerPlayerBase[] afterKnockBackHooks;
    public boolean isKnockBackModded;
    private static final Map<String, String[]> allBaseBeforeKnockBackSuperiors;
    private static final Map<String, String[]> allBaseBeforeKnockBackInferiors;
    private static final Map<String, String[]> allBaseOverrideKnockBackSuperiors;
    private static final Map<String, String[]> allBaseOverrideKnockBackInferiors;
    private static final Map<String, String[]> allBaseAfterKnockBackSuperiors;
    private static final Map<String, String[]> allBaseAfterKnockBackInferiors;
    private static final List<String> beforeMoveEntityHookTypes;
    private static final List<String> overrideMoveEntityHookTypes;
    private static final List<String> afterMoveEntityHookTypes;
    private ServerPlayerBase[] beforeMoveEntityHooks;
    private ServerPlayerBase[] overrideMoveEntityHooks;
    private ServerPlayerBase[] afterMoveEntityHooks;
    public boolean isMoveEntityModded;
    private static final Map<String, String[]> allBaseBeforeMoveEntitySuperiors;
    private static final Map<String, String[]> allBaseBeforeMoveEntityInferiors;
    private static final Map<String, String[]> allBaseOverrideMoveEntitySuperiors;
    private static final Map<String, String[]> allBaseOverrideMoveEntityInferiors;
    private static final Map<String, String[]> allBaseAfterMoveEntitySuperiors;
    private static final Map<String, String[]> allBaseAfterMoveEntityInferiors;
    private static final List<String> beforeMoveEntityWithHeadingHookTypes;
    private static final List<String> overrideMoveEntityWithHeadingHookTypes;
    private static final List<String> afterMoveEntityWithHeadingHookTypes;
    private ServerPlayerBase[] beforeMoveEntityWithHeadingHooks;
    private ServerPlayerBase[] overrideMoveEntityWithHeadingHooks;
    private ServerPlayerBase[] afterMoveEntityWithHeadingHooks;
    public boolean isMoveEntityWithHeadingModded;
    private static final Map<String, String[]> allBaseBeforeMoveEntityWithHeadingSuperiors;
    private static final Map<String, String[]> allBaseBeforeMoveEntityWithHeadingInferiors;
    private static final Map<String, String[]> allBaseOverrideMoveEntityWithHeadingSuperiors;
    private static final Map<String, String[]> allBaseOverrideMoveEntityWithHeadingInferiors;
    private static final Map<String, String[]> allBaseAfterMoveEntityWithHeadingSuperiors;
    private static final Map<String, String[]> allBaseAfterMoveEntityWithHeadingInferiors;
    private static final List<String> beforeMoveFlyingHookTypes;
    private static final List<String> overrideMoveFlyingHookTypes;
    private static final List<String> afterMoveFlyingHookTypes;
    private ServerPlayerBase[] beforeMoveFlyingHooks;
    private ServerPlayerBase[] overrideMoveFlyingHooks;
    private ServerPlayerBase[] afterMoveFlyingHooks;
    public boolean isMoveFlyingModded;
    private static final Map<String, String[]> allBaseBeforeMoveFlyingSuperiors;
    private static final Map<String, String[]> allBaseBeforeMoveFlyingInferiors;
    private static final Map<String, String[]> allBaseOverrideMoveFlyingSuperiors;
    private static final Map<String, String[]> allBaseOverrideMoveFlyingInferiors;
    private static final Map<String, String[]> allBaseAfterMoveFlyingSuperiors;
    private static final Map<String, String[]> allBaseAfterMoveFlyingInferiors;
    private static final List<String> beforeOnDeathHookTypes;
    private static final List<String> overrideOnDeathHookTypes;
    private static final List<String> afterOnDeathHookTypes;
    private ServerPlayerBase[] beforeOnDeathHooks;
    private ServerPlayerBase[] overrideOnDeathHooks;
    private ServerPlayerBase[] afterOnDeathHooks;
    public boolean isOnDeathModded;
    private static final Map<String, String[]> allBaseBeforeOnDeathSuperiors;
    private static final Map<String, String[]> allBaseBeforeOnDeathInferiors;
    private static final Map<String, String[]> allBaseOverrideOnDeathSuperiors;
    private static final Map<String, String[]> allBaseOverrideOnDeathInferiors;
    private static final Map<String, String[]> allBaseAfterOnDeathSuperiors;
    private static final Map<String, String[]> allBaseAfterOnDeathInferiors;
    private static final List<String> beforeOnLivingUpdateHookTypes;
    private static final List<String> overrideOnLivingUpdateHookTypes;
    private static final List<String> afterOnLivingUpdateHookTypes;
    private ServerPlayerBase[] beforeOnLivingUpdateHooks;
    private ServerPlayerBase[] overrideOnLivingUpdateHooks;
    private ServerPlayerBase[] afterOnLivingUpdateHooks;
    public boolean isOnLivingUpdateModded;
    private static final Map<String, String[]> allBaseBeforeOnLivingUpdateSuperiors;
    private static final Map<String, String[]> allBaseBeforeOnLivingUpdateInferiors;
    private static final Map<String, String[]> allBaseOverrideOnLivingUpdateSuperiors;
    private static final Map<String, String[]> allBaseOverrideOnLivingUpdateInferiors;
    private static final Map<String, String[]> allBaseAfterOnLivingUpdateSuperiors;
    private static final Map<String, String[]> allBaseAfterOnLivingUpdateInferiors;
    private static final List<String> beforeOnKillEntityHookTypes;
    private static final List<String> overrideOnKillEntityHookTypes;
    private static final List<String> afterOnKillEntityHookTypes;
    private ServerPlayerBase[] beforeOnKillEntityHooks;
    private ServerPlayerBase[] overrideOnKillEntityHooks;
    private ServerPlayerBase[] afterOnKillEntityHooks;
    public boolean isOnKillEntityModded;
    private static final Map<String, String[]> allBaseBeforeOnKillEntitySuperiors;
    private static final Map<String, String[]> allBaseBeforeOnKillEntityInferiors;
    private static final Map<String, String[]> allBaseOverrideOnKillEntitySuperiors;
    private static final Map<String, String[]> allBaseOverrideOnKillEntityInferiors;
    private static final Map<String, String[]> allBaseAfterOnKillEntitySuperiors;
    private static final Map<String, String[]> allBaseAfterOnKillEntityInferiors;
    private static final List<String> beforeOnStruckByLightningHookTypes;
    private static final List<String> overrideOnStruckByLightningHookTypes;
    private static final List<String> afterOnStruckByLightningHookTypes;
    private ServerPlayerBase[] beforeOnStruckByLightningHooks;
    private ServerPlayerBase[] overrideOnStruckByLightningHooks;
    private ServerPlayerBase[] afterOnStruckByLightningHooks;
    public boolean isOnStruckByLightningModded;
    private static final Map<String, String[]> allBaseBeforeOnStruckByLightningSuperiors;
    private static final Map<String, String[]> allBaseBeforeOnStruckByLightningInferiors;
    private static final Map<String, String[]> allBaseOverrideOnStruckByLightningSuperiors;
    private static final Map<String, String[]> allBaseOverrideOnStruckByLightningInferiors;
    private static final Map<String, String[]> allBaseAfterOnStruckByLightningSuperiors;
    private static final Map<String, String[]> allBaseAfterOnStruckByLightningInferiors;
    private static final List<String> beforeOnUpdateHookTypes;
    private static final List<String> overrideOnUpdateHookTypes;
    private static final List<String> afterOnUpdateHookTypes;
    private ServerPlayerBase[] beforeOnUpdateHooks;
    private ServerPlayerBase[] overrideOnUpdateHooks;
    private ServerPlayerBase[] afterOnUpdateHooks;
    public boolean isOnUpdateModded;
    private static final Map<String, String[]> allBaseBeforeOnUpdateSuperiors;
    private static final Map<String, String[]> allBaseBeforeOnUpdateInferiors;
    private static final Map<String, String[]> allBaseOverrideOnUpdateSuperiors;
    private static final Map<String, String[]> allBaseOverrideOnUpdateInferiors;
    private static final Map<String, String[]> allBaseAfterOnUpdateSuperiors;
    private static final Map<String, String[]> allBaseAfterOnUpdateInferiors;
    private static final List<String> beforeOnUpdateEntityHookTypes;
    private static final List<String> overrideOnUpdateEntityHookTypes;
    private static final List<String> afterOnUpdateEntityHookTypes;
    private ServerPlayerBase[] beforeOnUpdateEntityHooks;
    private ServerPlayerBase[] overrideOnUpdateEntityHooks;
    private ServerPlayerBase[] afterOnUpdateEntityHooks;
    public boolean isOnUpdateEntityModded;
    private static final Map<String, String[]> allBaseBeforeOnUpdateEntitySuperiors;
    private static final Map<String, String[]> allBaseBeforeOnUpdateEntityInferiors;
    private static final Map<String, String[]> allBaseOverrideOnUpdateEntitySuperiors;
    private static final Map<String, String[]> allBaseOverrideOnUpdateEntityInferiors;
    private static final Map<String, String[]> allBaseAfterOnUpdateEntitySuperiors;
    private static final Map<String, String[]> allBaseAfterOnUpdateEntityInferiors;
    private static final List<String> beforeReadEntityFromNBTHookTypes;
    private static final List<String> overrideReadEntityFromNBTHookTypes;
    private static final List<String> afterReadEntityFromNBTHookTypes;
    private ServerPlayerBase[] beforeReadEntityFromNBTHooks;
    private ServerPlayerBase[] overrideReadEntityFromNBTHooks;
    private ServerPlayerBase[] afterReadEntityFromNBTHooks;
    public boolean isReadEntityFromNBTModded;
    private static final Map<String, String[]> allBaseBeforeReadEntityFromNBTSuperiors;
    private static final Map<String, String[]> allBaseBeforeReadEntityFromNBTInferiors;
    private static final Map<String, String[]> allBaseOverrideReadEntityFromNBTSuperiors;
    private static final Map<String, String[]> allBaseOverrideReadEntityFromNBTInferiors;
    private static final Map<String, String[]> allBaseAfterReadEntityFromNBTSuperiors;
    private static final Map<String, String[]> allBaseAfterReadEntityFromNBTInferiors;
    private static final List<String> beforeSetDeadHookTypes;
    private static final List<String> overrideSetDeadHookTypes;
    private static final List<String> afterSetDeadHookTypes;
    private ServerPlayerBase[] beforeSetDeadHooks;
    private ServerPlayerBase[] overrideSetDeadHooks;
    private ServerPlayerBase[] afterSetDeadHooks;
    public boolean isSetDeadModded;
    private static final Map<String, String[]> allBaseBeforeSetDeadSuperiors;
    private static final Map<String, String[]> allBaseBeforeSetDeadInferiors;
    private static final Map<String, String[]> allBaseOverrideSetDeadSuperiors;
    private static final Map<String, String[]> allBaseOverrideSetDeadInferiors;
    private static final Map<String, String[]> allBaseAfterSetDeadSuperiors;
    private static final Map<String, String[]> allBaseAfterSetDeadInferiors;
    private static final List<String> beforeSetPositionHookTypes;
    private static final List<String> overrideSetPositionHookTypes;
    private static final List<String> afterSetPositionHookTypes;
    private ServerPlayerBase[] beforeSetPositionHooks;
    private ServerPlayerBase[] overrideSetPositionHooks;
    private ServerPlayerBase[] afterSetPositionHooks;
    public boolean isSetPositionModded;
    private static final Map<String, String[]> allBaseBeforeSetPositionSuperiors;
    private static final Map<String, String[]> allBaseBeforeSetPositionInferiors;
    private static final Map<String, String[]> allBaseOverrideSetPositionSuperiors;
    private static final Map<String, String[]> allBaseOverrideSetPositionInferiors;
    private static final Map<String, String[]> allBaseAfterSetPositionSuperiors;
    private static final Map<String, String[]> allBaseAfterSetPositionInferiors;
    private static final List<String> beforeSwingItemHookTypes;
    private static final List<String> overrideSwingItemHookTypes;
    private static final List<String> afterSwingItemHookTypes;
    private ServerPlayerBase[] beforeSwingItemHooks;
    private ServerPlayerBase[] overrideSwingItemHooks;
    private ServerPlayerBase[] afterSwingItemHooks;
    public boolean isSwingItemModded;
    private static final Map<String, String[]> allBaseBeforeSwingItemSuperiors;
    private static final Map<String, String[]> allBaseBeforeSwingItemInferiors;
    private static final Map<String, String[]> allBaseOverrideSwingItemSuperiors;
    private static final Map<String, String[]> allBaseOverrideSwingItemInferiors;
    private static final Map<String, String[]> allBaseAfterSwingItemSuperiors;
    private static final Map<String, String[]> allBaseAfterSwingItemInferiors;
    private static final List<String> beforeUpdateEntityActionStateHookTypes;
    private static final List<String> overrideUpdateEntityActionStateHookTypes;
    private static final List<String> afterUpdateEntityActionStateHookTypes;
    private ServerPlayerBase[] beforeUpdateEntityActionStateHooks;
    private ServerPlayerBase[] overrideUpdateEntityActionStateHooks;
    private ServerPlayerBase[] afterUpdateEntityActionStateHooks;
    public boolean isUpdateEntityActionStateModded;
    private static final Map<String, String[]> allBaseBeforeUpdateEntityActionStateSuperiors;
    private static final Map<String, String[]> allBaseBeforeUpdateEntityActionStateInferiors;
    private static final Map<String, String[]> allBaseOverrideUpdateEntityActionStateSuperiors;
    private static final Map<String, String[]> allBaseOverrideUpdateEntityActionStateInferiors;
    private static final Map<String, String[]> allBaseAfterUpdateEntityActionStateSuperiors;
    private static final Map<String, String[]> allBaseAfterUpdateEntityActionStateInferiors;
    private static final List<String> beforeUpdatePotionEffectsHookTypes;
    private static final List<String> overrideUpdatePotionEffectsHookTypes;
    private static final List<String> afterUpdatePotionEffectsHookTypes;
    private ServerPlayerBase[] beforeUpdatePotionEffectsHooks;
    private ServerPlayerBase[] overrideUpdatePotionEffectsHooks;
    private ServerPlayerBase[] afterUpdatePotionEffectsHooks;
    public boolean isUpdatePotionEffectsModded;
    private static final Map<String, String[]> allBaseBeforeUpdatePotionEffectsSuperiors;
    private static final Map<String, String[]> allBaseBeforeUpdatePotionEffectsInferiors;
    private static final Map<String, String[]> allBaseOverrideUpdatePotionEffectsSuperiors;
    private static final Map<String, String[]> allBaseOverrideUpdatePotionEffectsInferiors;
    private static final Map<String, String[]> allBaseAfterUpdatePotionEffectsSuperiors;
    private static final Map<String, String[]> allBaseAfterUpdatePotionEffectsInferiors;
    private static final List<String> beforeWriteEntityToNBTHookTypes;
    private static final List<String> overrideWriteEntityToNBTHookTypes;
    private static final List<String> afterWriteEntityToNBTHookTypes;
    private ServerPlayerBase[] beforeWriteEntityToNBTHooks;
    private ServerPlayerBase[] overrideWriteEntityToNBTHooks;
    private ServerPlayerBase[] afterWriteEntityToNBTHooks;
    public boolean isWriteEntityToNBTModded;
    private static final Map<String, String[]> allBaseBeforeWriteEntityToNBTSuperiors;
    private static final Map<String, String[]> allBaseBeforeWriteEntityToNBTInferiors;
    private static final Map<String, String[]> allBaseOverrideWriteEntityToNBTSuperiors;
    private static final Map<String, String[]> allBaseOverrideWriteEntityToNBTInferiors;
    private static final Map<String, String[]> allBaseAfterWriteEntityToNBTSuperiors;
    private static final Map<String, String[]> allBaseAfterWriteEntityToNBTInferiors;
    protected final EntityPlayer player;
    private static final Set<String> keys;
    private static final Map<String, String> keysToVirtualIds;
    private static final Set<Class<?>> dynamicTypes;
    private static final Map<Class<?>, Map<String, Method>> virtualDynamicHookMethods;
    private static final Map<Class<?>, Map<String, Method>> beforeDynamicHookMethods;
    private static final Map<Class<?>, Map<String, Method>> overrideDynamicHookMethods;
    private static final Map<Class<?>, Map<String, Method>> afterDynamicHookMethods;
    private static final List<String> beforeLocalConstructingHookTypes;
    private static final List<String> afterLocalConstructingHookTypes;
    private static final Map<String, List<String>> beforeDynamicHookTypes;
    private static final Map<String, List<String>> overrideDynamicHookTypes;
    private static final Map<String, List<String>> afterDynamicHookTypes;
    private ServerPlayerBase[] beforeLocalConstructingHooks;
    private ServerPlayerBase[] afterLocalConstructingHooks;
    private final Map<ServerPlayerBase, String> baseObjectsToId;
    private final Map<String, ServerPlayerBase> allBaseObjects;
    private final Set<String> unmodifiableAllBaseIds;
    private static final Map<String, Constructor<?>> allBaseConstructors;
    private static final Set<String> unmodifiableAllIds;
    private static final Map<String, String[]> allBaseBeforeLocalConstructingSuperiors;
    private static final Map<String, String[]> allBaseBeforeLocalConstructingInferiors;
    private static final Map<String, String[]> allBaseAfterLocalConstructingSuperiors;
    private static final Map<String, String[]> allBaseAfterLocalConstructingInferiors;
    private static final Map<String, Map<String, String[]>> allBaseBeforeDynamicSuperiors;
    private static final Map<String, Map<String, String[]>> allBaseBeforeDynamicInferiors;
    private static final Map<String, Map<String, String[]>> allBaseOverrideDynamicSuperiors;
    private static final Map<String, Map<String, String[]>> allBaseOverrideDynamicInferiors;
    private static final Map<String, Map<String, String[]>> allBaseAfterDynamicSuperiors;
    private static final Map<String, Map<String, String[]>> allBaseAfterDynamicInferiors;
    private static boolean initialized;
    
    private static void log(final String s) {
        System.out.println(s);
        ServerPlayerAPI.logger.fine(s);
    }
    
    private static String error(final String s) {
        ServerPlayerAPI.logger.severe(s);
        return s;
    }
    
    public static void register(final String s, final Class<?> clazz) {
        register(s, clazz, null);
    }
    
    public static void register(final String s, final Class<?> clazz, final ServerPlayerBaseSorting serverPlayerBaseSorting) {
        try {
            register(clazz, s, serverPlayerBaseSorting);
        }
        catch (RuntimeException ex) {
            if (s != null) {
                log("Bukkit Player API: failed to register id '" + s + "'");
            }
            else {
                log("Bukkit Player API: failed to register ServerPlayerBase");
            }
            throw ex;
        }
    }
    
    private static void register(final Class<?> clazz, final String s, final ServerPlayerBaseSorting serverPlayerBaseSorting) {
        if (!ServerPlayerAPI.isCreated) {
            try {
                if (EntityPlayer.class.getMethod("getServerPlayerBase", String.class).getReturnType() != ServerPlayerBase.class) {
                    throw new NoSuchMethodException(ServerPlayerBase.class.getName() + " " + EntityPlayer.class.getName() + ".getServerPlayerBase(" + String.class.getName() + ")");
                }
            }
            catch (NoSuchMethodException ex) {
                final String[] array2;
                final String[] array = array2 = new String[] { "========================================", "Bukkit Player API 1.1 can not be created!", "----------------------------------------", "Mandatory member method \"" + ServerPlayerBase.class.getName() + " getServerPlayerBase(" + String.class.getName() + ")\" not found in class \"" + EntityPlayer.class.getName() + "\".", "There are two scenarios this can happen:", "* The file \"" + EntityPlayer.class.getName().replace(".", File.separator) + ".class\" of Player API bukkit has been replaced by a file of the same name but a different source.", "  Install Player API bukkit again to fix this specific problem.", "* Player API bukkit has been installed in the \"mods\" folder.", "  Deinstall Player API bukkit and install it again following the installation instructions in the readme file.", "========================================" };
                for (int length = array2.length, i = 0; i < length; ++i) {
                    ServerPlayerAPI.logger.severe(array2[i]);
                }
                final String[] array3 = array;
                for (int length2 = array3.length, j = 0; j < length2; ++j) {
                    System.err.println(array3[j]);
                }
                String string = "\n\n";
                final String[] array4 = array;
                for (int length3 = array4.length, k = 0; k < length3; ++k) {
                    string = string + "\t" + array4[k] + "\n";
                }
                throw new RuntimeException(string, ex);
            }
            log("Bukkit Player API 1.1 Created");
            ServerPlayerAPI.isCreated = true;
        }
        if (s == null) {
            throw new NullPointerException("Argument 'id' can not be null");
        }
        if (clazz == null) {
            throw new NullPointerException("Argument 'baseClass' can not be null");
        }
        final Constructor<?> constructor = ServerPlayerAPI.allBaseConstructors.get(s);
        if (constructor != null) {
            throw new IllegalArgumentException("The class '" + clazz.getName() + "' can not be registered with the id '" + s + "' because the class '" + constructor.getDeclaringClass().getName() + "' has allready been registered with the same id");
        }
        Constructor<?> constructor2;
        try {
            constructor2 = clazz.getDeclaredConstructor(ServerPlayerAPI.Classes);
        }
        catch (Throwable t) {
            try {
                constructor2 = clazz.getDeclaredConstructor(ServerPlayerAPI.Class);
            }
            catch (Throwable t2) {
                throw new IllegalArgumentException("Can not find necessary constructor with one argument of type '" + ServerPlayerAPI.class.getName() + "' and eventually a second argument of type 'String' in the class '" + clazz.getName() + "'", t);
            }
        }
        ServerPlayerAPI.allBaseConstructors.put(s, constructor2);
        if (serverPlayerBaseSorting != null) {
            addSorting(s, ServerPlayerAPI.allBaseBeforeLocalConstructingSuperiors, serverPlayerBaseSorting.getBeforeLocalConstructingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeLocalConstructingInferiors, serverPlayerBaseSorting.getBeforeLocalConstructingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterLocalConstructingSuperiors, serverPlayerBaseSorting.getAfterLocalConstructingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterLocalConstructingInferiors, serverPlayerBaseSorting.getAfterLocalConstructingInferiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseBeforeDynamicSuperiors, serverPlayerBaseSorting.getDynamicBeforeSuperiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseBeforeDynamicInferiors, serverPlayerBaseSorting.getDynamicBeforeInferiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseOverrideDynamicSuperiors, serverPlayerBaseSorting.getDynamicOverrideSuperiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseOverrideDynamicInferiors, serverPlayerBaseSorting.getDynamicOverrideInferiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseAfterDynamicSuperiors, serverPlayerBaseSorting.getDynamicAfterSuperiors());
            addDynamicSorting(s, ServerPlayerAPI.allBaseAfterDynamicInferiors, serverPlayerBaseSorting.getDynamicAfterInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExhaustionSuperiors, serverPlayerBaseSorting.getBeforeAddExhaustionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExhaustionInferiors, serverPlayerBaseSorting.getBeforeAddExhaustionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExhaustionSuperiors, serverPlayerBaseSorting.getOverrideAddExhaustionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExhaustionInferiors, serverPlayerBaseSorting.getOverrideAddExhaustionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExhaustionSuperiors, serverPlayerBaseSorting.getAfterAddExhaustionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExhaustionInferiors, serverPlayerBaseSorting.getAfterAddExhaustionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExperienceSuperiors, serverPlayerBaseSorting.getBeforeAddExperienceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExperienceInferiors, serverPlayerBaseSorting.getBeforeAddExperienceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExperienceSuperiors, serverPlayerBaseSorting.getOverrideAddExperienceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExperienceInferiors, serverPlayerBaseSorting.getOverrideAddExperienceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExperienceSuperiors, serverPlayerBaseSorting.getAfterAddExperienceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExperienceInferiors, serverPlayerBaseSorting.getAfterAddExperienceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExperienceLevelSuperiors, serverPlayerBaseSorting.getBeforeAddExperienceLevelSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddExperienceLevelInferiors, serverPlayerBaseSorting.getBeforeAddExperienceLevelInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExperienceLevelSuperiors, serverPlayerBaseSorting.getOverrideAddExperienceLevelSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddExperienceLevelInferiors, serverPlayerBaseSorting.getOverrideAddExperienceLevelInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExperienceLevelSuperiors, serverPlayerBaseSorting.getAfterAddExperienceLevelSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddExperienceLevelInferiors, serverPlayerBaseSorting.getAfterAddExperienceLevelInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddMovementStatSuperiors, serverPlayerBaseSorting.getBeforeAddMovementStatSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAddMovementStatInferiors, serverPlayerBaseSorting.getBeforeAddMovementStatInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddMovementStatSuperiors, serverPlayerBaseSorting.getOverrideAddMovementStatSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAddMovementStatInferiors, serverPlayerBaseSorting.getOverrideAddMovementStatInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddMovementStatSuperiors, serverPlayerBaseSorting.getAfterAddMovementStatSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAddMovementStatInferiors, serverPlayerBaseSorting.getAfterAddMovementStatInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAttackEntityFromSuperiors, serverPlayerBaseSorting.getBeforeAttackEntityFromSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAttackEntityFromInferiors, serverPlayerBaseSorting.getBeforeAttackEntityFromInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAttackEntityFromSuperiors, serverPlayerBaseSorting.getOverrideAttackEntityFromSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAttackEntityFromInferiors, serverPlayerBaseSorting.getOverrideAttackEntityFromInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAttackEntityFromSuperiors, serverPlayerBaseSorting.getAfterAttackEntityFromSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAttackEntityFromInferiors, serverPlayerBaseSorting.getAfterAttackEntityFromInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemSuperiors, serverPlayerBaseSorting.getBeforeAttackTargetEntityWithCurrentItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemInferiors, serverPlayerBaseSorting.getBeforeAttackTargetEntityWithCurrentItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemSuperiors, serverPlayerBaseSorting.getOverrideAttackTargetEntityWithCurrentItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemInferiors, serverPlayerBaseSorting.getOverrideAttackTargetEntityWithCurrentItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemSuperiors, serverPlayerBaseSorting.getAfterAttackTargetEntityWithCurrentItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemInferiors, serverPlayerBaseSorting.getAfterAttackTargetEntityWithCurrentItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanHarvestBlockSuperiors, serverPlayerBaseSorting.getBeforeCanHarvestBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanHarvestBlockInferiors, serverPlayerBaseSorting.getBeforeCanHarvestBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanHarvestBlockSuperiors, serverPlayerBaseSorting.getOverrideCanHarvestBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanHarvestBlockInferiors, serverPlayerBaseSorting.getOverrideCanHarvestBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanHarvestBlockSuperiors, serverPlayerBaseSorting.getAfterCanHarvestBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanHarvestBlockInferiors, serverPlayerBaseSorting.getAfterCanHarvestBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanPlayerEditSuperiors, serverPlayerBaseSorting.getBeforeCanPlayerEditSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanPlayerEditInferiors, serverPlayerBaseSorting.getBeforeCanPlayerEditInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanPlayerEditSuperiors, serverPlayerBaseSorting.getOverrideCanPlayerEditSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanPlayerEditInferiors, serverPlayerBaseSorting.getOverrideCanPlayerEditInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanPlayerEditSuperiors, serverPlayerBaseSorting.getAfterCanPlayerEditSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanPlayerEditInferiors, serverPlayerBaseSorting.getAfterCanPlayerEditInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanTriggerWalkingSuperiors, serverPlayerBaseSorting.getBeforeCanTriggerWalkingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeCanTriggerWalkingInferiors, serverPlayerBaseSorting.getBeforeCanTriggerWalkingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanTriggerWalkingSuperiors, serverPlayerBaseSorting.getOverrideCanTriggerWalkingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideCanTriggerWalkingInferiors, serverPlayerBaseSorting.getOverrideCanTriggerWalkingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanTriggerWalkingSuperiors, serverPlayerBaseSorting.getAfterCanTriggerWalkingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterCanTriggerWalkingInferiors, serverPlayerBaseSorting.getAfterCanTriggerWalkingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeClonePlayerSuperiors, serverPlayerBaseSorting.getBeforeClonePlayerSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeClonePlayerInferiors, serverPlayerBaseSorting.getBeforeClonePlayerInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideClonePlayerSuperiors, serverPlayerBaseSorting.getOverrideClonePlayerSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideClonePlayerInferiors, serverPlayerBaseSorting.getOverrideClonePlayerInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterClonePlayerSuperiors, serverPlayerBaseSorting.getAfterClonePlayerSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterClonePlayerInferiors, serverPlayerBaseSorting.getAfterClonePlayerInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDamageEntitySuperiors, serverPlayerBaseSorting.getBeforeDamageEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDamageEntityInferiors, serverPlayerBaseSorting.getBeforeDamageEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDamageEntitySuperiors, serverPlayerBaseSorting.getOverrideDamageEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDamageEntityInferiors, serverPlayerBaseSorting.getOverrideDamageEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDamageEntitySuperiors, serverPlayerBaseSorting.getAfterDamageEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDamageEntityInferiors, serverPlayerBaseSorting.getAfterDamageEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIChestSuperiors, serverPlayerBaseSorting.getBeforeDisplayGUIChestSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIChestInferiors, serverPlayerBaseSorting.getBeforeDisplayGUIChestInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIChestSuperiors, serverPlayerBaseSorting.getOverrideDisplayGUIChestSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIChestInferiors, serverPlayerBaseSorting.getOverrideDisplayGUIChestInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIChestSuperiors, serverPlayerBaseSorting.getAfterDisplayGUIChestSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIChestInferiors, serverPlayerBaseSorting.getAfterDisplayGUIChestInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserSuperiors, serverPlayerBaseSorting.getBeforeDisplayGUIDispenserSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserInferiors, serverPlayerBaseSorting.getBeforeDisplayGUIDispenserInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserSuperiors, serverPlayerBaseSorting.getOverrideDisplayGUIDispenserSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserInferiors, serverPlayerBaseSorting.getOverrideDisplayGUIDispenserInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIDispenserSuperiors, serverPlayerBaseSorting.getAfterDisplayGUIDispenserSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIDispenserInferiors, serverPlayerBaseSorting.getAfterDisplayGUIDispenserInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceSuperiors, serverPlayerBaseSorting.getBeforeDisplayGUIFurnaceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceInferiors, serverPlayerBaseSorting.getBeforeDisplayGUIFurnaceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceSuperiors, serverPlayerBaseSorting.getOverrideDisplayGUIFurnaceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceInferiors, serverPlayerBaseSorting.getOverrideDisplayGUIFurnaceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceSuperiors, serverPlayerBaseSorting.getAfterDisplayGUIFurnaceSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceInferiors, serverPlayerBaseSorting.getAfterDisplayGUIFurnaceInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchSuperiors, serverPlayerBaseSorting.getBeforeDisplayGUIWorkbenchSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchInferiors, serverPlayerBaseSorting.getBeforeDisplayGUIWorkbenchInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchSuperiors, serverPlayerBaseSorting.getOverrideDisplayGUIWorkbenchSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchInferiors, serverPlayerBaseSorting.getOverrideDisplayGUIWorkbenchInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchSuperiors, serverPlayerBaseSorting.getAfterDisplayGUIWorkbenchSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchInferiors, serverPlayerBaseSorting.getAfterDisplayGUIWorkbenchInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDropOneItemSuperiors, serverPlayerBaseSorting.getBeforeDropOneItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDropOneItemInferiors, serverPlayerBaseSorting.getBeforeDropOneItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDropOneItemSuperiors, serverPlayerBaseSorting.getOverrideDropOneItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDropOneItemInferiors, serverPlayerBaseSorting.getOverrideDropOneItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDropOneItemSuperiors, serverPlayerBaseSorting.getAfterDropOneItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDropOneItemInferiors, serverPlayerBaseSorting.getAfterDropOneItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDropPlayerItemSuperiors, serverPlayerBaseSorting.getBeforeDropPlayerItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeDropPlayerItemInferiors, serverPlayerBaseSorting.getBeforeDropPlayerItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDropPlayerItemSuperiors, serverPlayerBaseSorting.getOverrideDropPlayerItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideDropPlayerItemInferiors, serverPlayerBaseSorting.getOverrideDropPlayerItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDropPlayerItemSuperiors, serverPlayerBaseSorting.getAfterDropPlayerItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterDropPlayerItemInferiors, serverPlayerBaseSorting.getAfterDropPlayerItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeFallSuperiors, serverPlayerBaseSorting.getBeforeFallSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeFallInferiors, serverPlayerBaseSorting.getBeforeFallInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideFallSuperiors, serverPlayerBaseSorting.getOverrideFallSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideFallInferiors, serverPlayerBaseSorting.getOverrideFallInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterFallSuperiors, serverPlayerBaseSorting.getAfterFallSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterFallInferiors, serverPlayerBaseSorting.getAfterFallInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockSuperiors, serverPlayerBaseSorting.getBeforeGetCurrentPlayerStrVsBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockInferiors, serverPlayerBaseSorting.getBeforeGetCurrentPlayerStrVsBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockSuperiors, serverPlayerBaseSorting.getOverrideGetCurrentPlayerStrVsBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockInferiors, serverPlayerBaseSorting.getOverrideGetCurrentPlayerStrVsBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockSuperiors, serverPlayerBaseSorting.getAfterGetCurrentPlayerStrVsBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockInferiors, serverPlayerBaseSorting.getAfterGetCurrentPlayerStrVsBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetDistanceSqSuperiors, serverPlayerBaseSorting.getBeforeGetDistanceSqSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetDistanceSqInferiors, serverPlayerBaseSorting.getBeforeGetDistanceSqInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetDistanceSqSuperiors, serverPlayerBaseSorting.getOverrideGetDistanceSqSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetDistanceSqInferiors, serverPlayerBaseSorting.getOverrideGetDistanceSqInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetDistanceSqSuperiors, serverPlayerBaseSorting.getAfterGetDistanceSqSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetDistanceSqInferiors, serverPlayerBaseSorting.getAfterGetDistanceSqInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetBrightnessSuperiors, serverPlayerBaseSorting.getBeforeGetBrightnessSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetBrightnessInferiors, serverPlayerBaseSorting.getBeforeGetBrightnessInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetBrightnessSuperiors, serverPlayerBaseSorting.getOverrideGetBrightnessSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetBrightnessInferiors, serverPlayerBaseSorting.getOverrideGetBrightnessInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetBrightnessSuperiors, serverPlayerBaseSorting.getAfterGetBrightnessSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetBrightnessInferiors, serverPlayerBaseSorting.getAfterGetBrightnessInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetEyeHeightSuperiors, serverPlayerBaseSorting.getBeforeGetEyeHeightSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetEyeHeightInferiors, serverPlayerBaseSorting.getBeforeGetEyeHeightInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetEyeHeightSuperiors, serverPlayerBaseSorting.getOverrideGetEyeHeightSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetEyeHeightInferiors, serverPlayerBaseSorting.getOverrideGetEyeHeightInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetEyeHeightSuperiors, serverPlayerBaseSorting.getAfterGetEyeHeightSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetEyeHeightInferiors, serverPlayerBaseSorting.getAfterGetEyeHeightInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetMaxHealthSuperiors, serverPlayerBaseSorting.getBeforeGetMaxHealthSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetMaxHealthInferiors, serverPlayerBaseSorting.getBeforeGetMaxHealthInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetMaxHealthSuperiors, serverPlayerBaseSorting.getOverrideGetMaxHealthSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetMaxHealthInferiors, serverPlayerBaseSorting.getOverrideGetMaxHealthInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetMaxHealthSuperiors, serverPlayerBaseSorting.getAfterGetMaxHealthSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetMaxHealthInferiors, serverPlayerBaseSorting.getAfterGetMaxHealthInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetSpeedModifierSuperiors, serverPlayerBaseSorting.getBeforeGetSpeedModifierSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeGetSpeedModifierInferiors, serverPlayerBaseSorting.getBeforeGetSpeedModifierInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetSpeedModifierSuperiors, serverPlayerBaseSorting.getOverrideGetSpeedModifierSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideGetSpeedModifierInferiors, serverPlayerBaseSorting.getOverrideGetSpeedModifierInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetSpeedModifierSuperiors, serverPlayerBaseSorting.getAfterGetSpeedModifierSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterGetSpeedModifierInferiors, serverPlayerBaseSorting.getAfterGetSpeedModifierInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeHealSuperiors, serverPlayerBaseSorting.getBeforeHealSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeHealInferiors, serverPlayerBaseSorting.getBeforeHealInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideHealSuperiors, serverPlayerBaseSorting.getOverrideHealSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideHealInferiors, serverPlayerBaseSorting.getOverrideHealInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterHealSuperiors, serverPlayerBaseSorting.getAfterHealSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterHealInferiors, serverPlayerBaseSorting.getAfterHealInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeInteractSuperiors, serverPlayerBaseSorting.getBeforeInteractSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeInteractInferiors, serverPlayerBaseSorting.getBeforeInteractInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideInteractSuperiors, serverPlayerBaseSorting.getOverrideInteractSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideInteractInferiors, serverPlayerBaseSorting.getOverrideInteractInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterInteractSuperiors, serverPlayerBaseSorting.getAfterInteractSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterInteractInferiors, serverPlayerBaseSorting.getAfterInteractInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockSuperiors, serverPlayerBaseSorting.getBeforeIsEntityInsideOpaqueBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockInferiors, serverPlayerBaseSorting.getBeforeIsEntityInsideOpaqueBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockSuperiors, serverPlayerBaseSorting.getOverrideIsEntityInsideOpaqueBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockInferiors, serverPlayerBaseSorting.getOverrideIsEntityInsideOpaqueBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockSuperiors, serverPlayerBaseSorting.getAfterIsEntityInsideOpaqueBlockSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockInferiors, serverPlayerBaseSorting.getAfterIsEntityInsideOpaqueBlockInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsInWaterSuperiors, serverPlayerBaseSorting.getBeforeIsInWaterSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsInWaterInferiors, serverPlayerBaseSorting.getBeforeIsInWaterInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsInWaterSuperiors, serverPlayerBaseSorting.getOverrideIsInWaterSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsInWaterInferiors, serverPlayerBaseSorting.getOverrideIsInWaterInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsInWaterSuperiors, serverPlayerBaseSorting.getAfterIsInWaterSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsInWaterInferiors, serverPlayerBaseSorting.getAfterIsInWaterInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialSuperiors, serverPlayerBaseSorting.getBeforeIsInsideOfMaterialSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialInferiors, serverPlayerBaseSorting.getBeforeIsInsideOfMaterialInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialSuperiors, serverPlayerBaseSorting.getOverrideIsInsideOfMaterialSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialInferiors, serverPlayerBaseSorting.getOverrideIsInsideOfMaterialInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsInsideOfMaterialSuperiors, serverPlayerBaseSorting.getAfterIsInsideOfMaterialSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsInsideOfMaterialInferiors, serverPlayerBaseSorting.getAfterIsInsideOfMaterialInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsOnLadderSuperiors, serverPlayerBaseSorting.getBeforeIsOnLadderSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsOnLadderInferiors, serverPlayerBaseSorting.getBeforeIsOnLadderInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsOnLadderSuperiors, serverPlayerBaseSorting.getOverrideIsOnLadderSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsOnLadderInferiors, serverPlayerBaseSorting.getOverrideIsOnLadderInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsOnLadderSuperiors, serverPlayerBaseSorting.getAfterIsOnLadderSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsOnLadderInferiors, serverPlayerBaseSorting.getAfterIsOnLadderInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsPlayerSleepingSuperiors, serverPlayerBaseSorting.getBeforeIsPlayerSleepingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeIsPlayerSleepingInferiors, serverPlayerBaseSorting.getBeforeIsPlayerSleepingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsPlayerSleepingSuperiors, serverPlayerBaseSorting.getOverrideIsPlayerSleepingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideIsPlayerSleepingInferiors, serverPlayerBaseSorting.getOverrideIsPlayerSleepingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsPlayerSleepingSuperiors, serverPlayerBaseSorting.getAfterIsPlayerSleepingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterIsPlayerSleepingInferiors, serverPlayerBaseSorting.getAfterIsPlayerSleepingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeJumpSuperiors, serverPlayerBaseSorting.getBeforeJumpSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeJumpInferiors, serverPlayerBaseSorting.getBeforeJumpInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideJumpSuperiors, serverPlayerBaseSorting.getOverrideJumpSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideJumpInferiors, serverPlayerBaseSorting.getOverrideJumpInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterJumpSuperiors, serverPlayerBaseSorting.getAfterJumpSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterJumpInferiors, serverPlayerBaseSorting.getAfterJumpInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeKnockBackSuperiors, serverPlayerBaseSorting.getBeforeKnockBackSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeKnockBackInferiors, serverPlayerBaseSorting.getBeforeKnockBackInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideKnockBackSuperiors, serverPlayerBaseSorting.getOverrideKnockBackSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideKnockBackInferiors, serverPlayerBaseSorting.getOverrideKnockBackInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterKnockBackSuperiors, serverPlayerBaseSorting.getAfterKnockBackSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterKnockBackInferiors, serverPlayerBaseSorting.getAfterKnockBackInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveEntitySuperiors, serverPlayerBaseSorting.getBeforeMoveEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveEntityInferiors, serverPlayerBaseSorting.getBeforeMoveEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveEntitySuperiors, serverPlayerBaseSorting.getOverrideMoveEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveEntityInferiors, serverPlayerBaseSorting.getOverrideMoveEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveEntitySuperiors, serverPlayerBaseSorting.getAfterMoveEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveEntityInferiors, serverPlayerBaseSorting.getAfterMoveEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingSuperiors, serverPlayerBaseSorting.getBeforeMoveEntityWithHeadingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingInferiors, serverPlayerBaseSorting.getBeforeMoveEntityWithHeadingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingSuperiors, serverPlayerBaseSorting.getOverrideMoveEntityWithHeadingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingInferiors, serverPlayerBaseSorting.getOverrideMoveEntityWithHeadingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingSuperiors, serverPlayerBaseSorting.getAfterMoveEntityWithHeadingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingInferiors, serverPlayerBaseSorting.getAfterMoveEntityWithHeadingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveFlyingSuperiors, serverPlayerBaseSorting.getBeforeMoveFlyingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeMoveFlyingInferiors, serverPlayerBaseSorting.getBeforeMoveFlyingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveFlyingSuperiors, serverPlayerBaseSorting.getOverrideMoveFlyingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideMoveFlyingInferiors, serverPlayerBaseSorting.getOverrideMoveFlyingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveFlyingSuperiors, serverPlayerBaseSorting.getAfterMoveFlyingSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterMoveFlyingInferiors, serverPlayerBaseSorting.getAfterMoveFlyingInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnDeathSuperiors, serverPlayerBaseSorting.getBeforeOnDeathSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnDeathInferiors, serverPlayerBaseSorting.getBeforeOnDeathInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnDeathSuperiors, serverPlayerBaseSorting.getOverrideOnDeathSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnDeathInferiors, serverPlayerBaseSorting.getOverrideOnDeathInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnDeathSuperiors, serverPlayerBaseSorting.getAfterOnDeathSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnDeathInferiors, serverPlayerBaseSorting.getAfterOnDeathInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnLivingUpdateSuperiors, serverPlayerBaseSorting.getBeforeOnLivingUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnLivingUpdateInferiors, serverPlayerBaseSorting.getBeforeOnLivingUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnLivingUpdateSuperiors, serverPlayerBaseSorting.getOverrideOnLivingUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnLivingUpdateInferiors, serverPlayerBaseSorting.getOverrideOnLivingUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnLivingUpdateSuperiors, serverPlayerBaseSorting.getAfterOnLivingUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnLivingUpdateInferiors, serverPlayerBaseSorting.getAfterOnLivingUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnKillEntitySuperiors, serverPlayerBaseSorting.getBeforeOnKillEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnKillEntityInferiors, serverPlayerBaseSorting.getBeforeOnKillEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnKillEntitySuperiors, serverPlayerBaseSorting.getOverrideOnKillEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnKillEntityInferiors, serverPlayerBaseSorting.getOverrideOnKillEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnKillEntitySuperiors, serverPlayerBaseSorting.getAfterOnKillEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnKillEntityInferiors, serverPlayerBaseSorting.getAfterOnKillEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnStruckByLightningSuperiors, serverPlayerBaseSorting.getBeforeOnStruckByLightningSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnStruckByLightningInferiors, serverPlayerBaseSorting.getBeforeOnStruckByLightningInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnStruckByLightningSuperiors, serverPlayerBaseSorting.getOverrideOnStruckByLightningSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnStruckByLightningInferiors, serverPlayerBaseSorting.getOverrideOnStruckByLightningInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnStruckByLightningSuperiors, serverPlayerBaseSorting.getAfterOnStruckByLightningSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnStruckByLightningInferiors, serverPlayerBaseSorting.getAfterOnStruckByLightningInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnUpdateSuperiors, serverPlayerBaseSorting.getBeforeOnUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnUpdateInferiors, serverPlayerBaseSorting.getBeforeOnUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnUpdateSuperiors, serverPlayerBaseSorting.getOverrideOnUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnUpdateInferiors, serverPlayerBaseSorting.getOverrideOnUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnUpdateSuperiors, serverPlayerBaseSorting.getAfterOnUpdateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnUpdateInferiors, serverPlayerBaseSorting.getAfterOnUpdateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnUpdateEntitySuperiors, serverPlayerBaseSorting.getBeforeOnUpdateEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeOnUpdateEntityInferiors, serverPlayerBaseSorting.getBeforeOnUpdateEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnUpdateEntitySuperiors, serverPlayerBaseSorting.getOverrideOnUpdateEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideOnUpdateEntityInferiors, serverPlayerBaseSorting.getOverrideOnUpdateEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnUpdateEntitySuperiors, serverPlayerBaseSorting.getAfterOnUpdateEntitySuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterOnUpdateEntityInferiors, serverPlayerBaseSorting.getAfterOnUpdateEntityInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeReadEntityFromNBTSuperiors, serverPlayerBaseSorting.getBeforeReadEntityFromNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeReadEntityFromNBTInferiors, serverPlayerBaseSorting.getBeforeReadEntityFromNBTInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideReadEntityFromNBTSuperiors, serverPlayerBaseSorting.getOverrideReadEntityFromNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideReadEntityFromNBTInferiors, serverPlayerBaseSorting.getOverrideReadEntityFromNBTInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterReadEntityFromNBTSuperiors, serverPlayerBaseSorting.getAfterReadEntityFromNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterReadEntityFromNBTInferiors, serverPlayerBaseSorting.getAfterReadEntityFromNBTInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSetDeadSuperiors, serverPlayerBaseSorting.getBeforeSetDeadSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSetDeadInferiors, serverPlayerBaseSorting.getBeforeSetDeadInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSetDeadSuperiors, serverPlayerBaseSorting.getOverrideSetDeadSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSetDeadInferiors, serverPlayerBaseSorting.getOverrideSetDeadInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSetDeadSuperiors, serverPlayerBaseSorting.getAfterSetDeadSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSetDeadInferiors, serverPlayerBaseSorting.getAfterSetDeadInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSetPositionSuperiors, serverPlayerBaseSorting.getBeforeSetPositionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSetPositionInferiors, serverPlayerBaseSorting.getBeforeSetPositionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSetPositionSuperiors, serverPlayerBaseSorting.getOverrideSetPositionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSetPositionInferiors, serverPlayerBaseSorting.getOverrideSetPositionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSetPositionSuperiors, serverPlayerBaseSorting.getAfterSetPositionSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSetPositionInferiors, serverPlayerBaseSorting.getAfterSetPositionInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSwingItemSuperiors, serverPlayerBaseSorting.getBeforeSwingItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeSwingItemInferiors, serverPlayerBaseSorting.getBeforeSwingItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSwingItemSuperiors, serverPlayerBaseSorting.getOverrideSwingItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideSwingItemInferiors, serverPlayerBaseSorting.getOverrideSwingItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSwingItemSuperiors, serverPlayerBaseSorting.getAfterSwingItemSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterSwingItemInferiors, serverPlayerBaseSorting.getAfterSwingItemInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateSuperiors, serverPlayerBaseSorting.getBeforeUpdateEntityActionStateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateInferiors, serverPlayerBaseSorting.getBeforeUpdateEntityActionStateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateSuperiors, serverPlayerBaseSorting.getOverrideUpdateEntityActionStateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateInferiors, serverPlayerBaseSorting.getOverrideUpdateEntityActionStateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterUpdateEntityActionStateSuperiors, serverPlayerBaseSorting.getAfterUpdateEntityActionStateSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterUpdateEntityActionStateInferiors, serverPlayerBaseSorting.getAfterUpdateEntityActionStateInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsSuperiors, serverPlayerBaseSorting.getBeforeUpdatePotionEffectsSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsInferiors, serverPlayerBaseSorting.getBeforeUpdatePotionEffectsInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsSuperiors, serverPlayerBaseSorting.getOverrideUpdatePotionEffectsSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsInferiors, serverPlayerBaseSorting.getOverrideUpdatePotionEffectsInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterUpdatePotionEffectsSuperiors, serverPlayerBaseSorting.getAfterUpdatePotionEffectsSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterUpdatePotionEffectsInferiors, serverPlayerBaseSorting.getAfterUpdatePotionEffectsInferiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeWriteEntityToNBTSuperiors, serverPlayerBaseSorting.getBeforeWriteEntityToNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseBeforeWriteEntityToNBTInferiors, serverPlayerBaseSorting.getBeforeWriteEntityToNBTInferiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideWriteEntityToNBTSuperiors, serverPlayerBaseSorting.getOverrideWriteEntityToNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseOverrideWriteEntityToNBTInferiors, serverPlayerBaseSorting.getOverrideWriteEntityToNBTInferiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterWriteEntityToNBTSuperiors, serverPlayerBaseSorting.getAfterWriteEntityToNBTSuperiors());
            addSorting(s, ServerPlayerAPI.allBaseAfterWriteEntityToNBTInferiors, serverPlayerBaseSorting.getAfterWriteEntityToNBTInferiors());
        }
        addMethod(s, clazz, ServerPlayerAPI.beforeLocalConstructingHookTypes, "beforeLocalConstructing", MinecraftServer.class, World.class, String.class, PlayerInteractManager.class);
        addMethod(s, clazz, ServerPlayerAPI.afterLocalConstructingHookTypes, "afterLocalConstructing", MinecraftServer.class, World.class, String.class, PlayerInteractManager.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeAddExhaustionHookTypes, "beforeAddExhaustion", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideAddExhaustionHookTypes, "addExhaustion", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterAddExhaustionHookTypes, "afterAddExhaustion", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeAddExperienceHookTypes, "beforeAddExperience", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideAddExperienceHookTypes, "addExperience", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterAddExperienceHookTypes, "afterAddExperience", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeAddExperienceLevelHookTypes, "beforeAddExperienceLevel", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideAddExperienceLevelHookTypes, "addExperienceLevel", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterAddExperienceLevelHookTypes, "afterAddExperienceLevel", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeAddMovementStatHookTypes, "beforeAddMovementStat", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideAddMovementStatHookTypes, "addMovementStat", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterAddMovementStatHookTypes, "afterAddMovementStat", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeAttackEntityFromHookTypes, "beforeAttackEntityFrom", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideAttackEntityFromHookTypes, "attackEntityFrom", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterAttackEntityFromHookTypes, "afterAttackEntityFrom", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeAttackTargetEntityWithCurrentItemHookTypes, "beforeAttackTargetEntityWithCurrentItem", Entity.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideAttackTargetEntityWithCurrentItemHookTypes, "attackTargetEntityWithCurrentItem", Entity.class);
        addMethod(s, clazz, ServerPlayerAPI.afterAttackTargetEntityWithCurrentItemHookTypes, "afterAttackTargetEntityWithCurrentItem", Entity.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeCanHarvestBlockHookTypes, "beforeCanHarvestBlock", Block.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideCanHarvestBlockHookTypes, "canHarvestBlock", Block.class);
        addMethod(s, clazz, ServerPlayerAPI.afterCanHarvestBlockHookTypes, "afterCanHarvestBlock", Block.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeCanPlayerEditHookTypes, "beforeCanPlayerEdit", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideCanPlayerEditHookTypes, "canPlayerEdit", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.afterCanPlayerEditHookTypes, "afterCanPlayerEdit", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeCanTriggerWalkingHookTypes, "beforeCanTriggerWalking", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideCanTriggerWalkingHookTypes, "canTriggerWalking", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterCanTriggerWalkingHookTypes, "afterCanTriggerWalking", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeClonePlayerHookTypes, "beforeClonePlayer", EntityHuman.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideClonePlayerHookTypes, "clonePlayer", EntityHuman.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterClonePlayerHookTypes, "afterClonePlayer", EntityHuman.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeDamageEntityHookTypes, "beforeDamageEntity", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideDamageEntityHookTypes, "damageEntity", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterDamageEntityHookTypes, "afterDamageEntity", DamageSource.class, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeDisplayGUIChestHookTypes, "beforeDisplayGUIChest", IInventory.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideDisplayGUIChestHookTypes, "displayGUIChest", IInventory.class);
        addMethod(s, clazz, ServerPlayerAPI.afterDisplayGUIChestHookTypes, "afterDisplayGUIChest", IInventory.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeDisplayGUIDispenserHookTypes, "beforeDisplayGUIDispenser", TileEntityDispenser.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideDisplayGUIDispenserHookTypes, "displayGUIDispenser", TileEntityDispenser.class);
        addMethod(s, clazz, ServerPlayerAPI.afterDisplayGUIDispenserHookTypes, "afterDisplayGUIDispenser", TileEntityDispenser.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeDisplayGUIFurnaceHookTypes, "beforeDisplayGUIFurnace", TileEntityFurnace.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideDisplayGUIFurnaceHookTypes, "displayGUIFurnace", TileEntityFurnace.class);
        addMethod(s, clazz, ServerPlayerAPI.afterDisplayGUIFurnaceHookTypes, "afterDisplayGUIFurnace", TileEntityFurnace.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeDisplayGUIWorkbenchHookTypes, "beforeDisplayGUIWorkbench", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideDisplayGUIWorkbenchHookTypes, "displayGUIWorkbench", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterDisplayGUIWorkbenchHookTypes, "afterDisplayGUIWorkbench", Integer.TYPE, Integer.TYPE, Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeDropOneItemHookTypes, "beforeDropOneItem", Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideDropOneItemHookTypes, "dropOneItem", Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterDropOneItemHookTypes, "afterDropOneItem", Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeDropPlayerItemHookTypes, "beforeDropPlayerItem", ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideDropPlayerItemHookTypes, "dropPlayerItem", ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.afterDropPlayerItemHookTypes, "afterDropPlayerItem", ItemStack.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeFallHookTypes, "beforeFall", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideFallHookTypes, "fall", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterFallHookTypes, "afterFall", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetCurrentPlayerStrVsBlockHookTypes, "beforeGetCurrentPlayerStrVsBlock", Block.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetCurrentPlayerStrVsBlockHookTypes, "getCurrentPlayerStrVsBlock", Block.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterGetCurrentPlayerStrVsBlockHookTypes, "afterGetCurrentPlayerStrVsBlock", Block.class, Boolean.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetDistanceSqHookTypes, "beforeGetDistanceSq", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetDistanceSqHookTypes, "getDistanceSq", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterGetDistanceSqHookTypes, "afterGetDistanceSq", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetBrightnessHookTypes, "beforeGetBrightness", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetBrightnessHookTypes, "getBrightness", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterGetBrightnessHookTypes, "afterGetBrightness", Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetEyeHeightHookTypes, "beforeGetEyeHeight", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetEyeHeightHookTypes, "getEyeHeight", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterGetEyeHeightHookTypes, "afterGetEyeHeight", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetMaxHealthHookTypes, "beforeGetMaxHealth", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetMaxHealthHookTypes, "getMaxHealth", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterGetMaxHealthHookTypes, "afterGetMaxHealth", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeGetSpeedModifierHookTypes, "beforeGetSpeedModifier", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideGetSpeedModifierHookTypes, "getSpeedModifier", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterGetSpeedModifierHookTypes, "afterGetSpeedModifier", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeHealHookTypes, "beforeHeal", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideHealHookTypes, "heal", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterHealHookTypes, "afterHeal", Integer.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeInteractHookTypes, "beforeInteract", EntityHuman.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideInteractHookTypes, "interact", EntityHuman.class);
        addMethod(s, clazz, ServerPlayerAPI.afterInteractHookTypes, "afterInteract", EntityHuman.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeIsEntityInsideOpaqueBlockHookTypes, "beforeIsEntityInsideOpaqueBlock", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideIsEntityInsideOpaqueBlockHookTypes, "isEntityInsideOpaqueBlock", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterIsEntityInsideOpaqueBlockHookTypes, "afterIsEntityInsideOpaqueBlock", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeIsInWaterHookTypes, "beforeIsInWater", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideIsInWaterHookTypes, "isInWater", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterIsInWaterHookTypes, "afterIsInWater", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeIsInsideOfMaterialHookTypes, "beforeIsInsideOfMaterial", Material.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideIsInsideOfMaterialHookTypes, "isInsideOfMaterial", Material.class);
        addMethod(s, clazz, ServerPlayerAPI.afterIsInsideOfMaterialHookTypes, "afterIsInsideOfMaterial", Material.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeIsOnLadderHookTypes, "beforeIsOnLadder", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideIsOnLadderHookTypes, "isOnLadder", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterIsOnLadderHookTypes, "afterIsOnLadder", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeIsPlayerSleepingHookTypes, "beforeIsPlayerSleeping", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideIsPlayerSleepingHookTypes, "isPlayerSleeping", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterIsPlayerSleepingHookTypes, "afterIsPlayerSleeping", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeJumpHookTypes, "beforeJump", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideJumpHookTypes, "jump", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterJumpHookTypes, "afterJump", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeKnockBackHookTypes, "beforeKnockBack", Entity.class, Integer.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideKnockBackHookTypes, "knockBack", Entity.class, Integer.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterKnockBackHookTypes, "afterKnockBack", Entity.class, Integer.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeMoveEntityHookTypes, "beforeMoveEntity", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideMoveEntityHookTypes, "moveEntity", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterMoveEntityHookTypes, "afterMoveEntity", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeMoveEntityWithHeadingHookTypes, "beforeMoveEntityWithHeading", Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideMoveEntityWithHeadingHookTypes, "moveEntityWithHeading", Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterMoveEntityWithHeadingHookTypes, "afterMoveEntityWithHeading", Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeMoveFlyingHookTypes, "beforeMoveFlying", Float.TYPE, Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideMoveFlyingHookTypes, "moveFlying", Float.TYPE, Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterMoveFlyingHookTypes, "afterMoveFlying", Float.TYPE, Float.TYPE, Float.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnDeathHookTypes, "beforeOnDeath", DamageSource.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnDeathHookTypes, "onDeath", DamageSource.class);
        addMethod(s, clazz, ServerPlayerAPI.afterOnDeathHookTypes, "afterOnDeath", DamageSource.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnLivingUpdateHookTypes, "beforeOnLivingUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnLivingUpdateHookTypes, "onLivingUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterOnLivingUpdateHookTypes, "afterOnLivingUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnKillEntityHookTypes, "beforeOnKillEntity", EntityLiving.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnKillEntityHookTypes, "onKillEntity", EntityLiving.class);
        addMethod(s, clazz, ServerPlayerAPI.afterOnKillEntityHookTypes, "afterOnKillEntity", EntityLiving.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnStruckByLightningHookTypes, "beforeOnStruckByLightning", EntityLightning.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnStruckByLightningHookTypes, "onStruckByLightning", EntityLightning.class);
        addMethod(s, clazz, ServerPlayerAPI.afterOnStruckByLightningHookTypes, "afterOnStruckByLightning", EntityLightning.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnUpdateHookTypes, "beforeOnUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnUpdateHookTypes, "onUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterOnUpdateHookTypes, "afterOnUpdate", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeOnUpdateEntityHookTypes, "beforeOnUpdateEntity", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideOnUpdateEntityHookTypes, "onUpdateEntity", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterOnUpdateEntityHookTypes, "afterOnUpdateEntity", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeReadEntityFromNBTHookTypes, "beforeReadEntityFromNBT", NBTTagCompound.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideReadEntityFromNBTHookTypes, "readEntityFromNBT", NBTTagCompound.class);
        addMethod(s, clazz, ServerPlayerAPI.afterReadEntityFromNBTHookTypes, "afterReadEntityFromNBT", NBTTagCompound.class);
        addMethod(s, clazz, ServerPlayerAPI.beforeSetDeadHookTypes, "beforeSetDead", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideSetDeadHookTypes, "setDead", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterSetDeadHookTypes, "afterSetDead", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeSetPositionHookTypes, "beforeSetPosition", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.overrideSetPositionHookTypes, "setPosition", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.afterSetPositionHookTypes, "afterSetPosition", Double.TYPE, Double.TYPE, Double.TYPE);
        addMethod(s, clazz, ServerPlayerAPI.beforeSwingItemHookTypes, "beforeSwingItem", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideSwingItemHookTypes, "swingItem", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterSwingItemHookTypes, "afterSwingItem", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeUpdateEntityActionStateHookTypes, "beforeUpdateEntityActionState", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideUpdateEntityActionStateHookTypes, "updateEntityActionState", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterUpdateEntityActionStateHookTypes, "afterUpdateEntityActionState", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeUpdatePotionEffectsHookTypes, "beforeUpdatePotionEffects", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.overrideUpdatePotionEffectsHookTypes, "updatePotionEffects", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.afterUpdatePotionEffectsHookTypes, "afterUpdatePotionEffects", (Class<?>[])new Class[0]);
        addMethod(s, clazz, ServerPlayerAPI.beforeWriteEntityToNBTHookTypes, "beforeWriteEntityToNBT", NBTTagCompound.class);
        addMethod(s, clazz, ServerPlayerAPI.overrideWriteEntityToNBTHookTypes, "writeEntityToNBT", NBTTagCompound.class);
        addMethod(s, clazz, ServerPlayerAPI.afterWriteEntityToNBTHookTypes, "afterWriteEntityToNBT", NBTTagCompound.class);
        addDynamicMethods(s, clazz);
        addDynamicKeys(s, clazz, ServerPlayerAPI.beforeDynamicHookMethods, ServerPlayerAPI.beforeDynamicHookTypes);
        addDynamicKeys(s, clazz, ServerPlayerAPI.overrideDynamicHookMethods, ServerPlayerAPI.overrideDynamicHookTypes);
        addDynamicKeys(s, clazz, ServerPlayerAPI.afterDynamicHookMethods, ServerPlayerAPI.afterDynamicHookTypes);
        initialize();
        final Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (int length4 = onlinePlayers.length, l = 0; l < length4; ++l) {
            ((ServerPlayerAPI) ((CraftPlayer)onlinePlayers[l]).getHandle().serverPlayerAPI).attachServerPlayerBase(s);
        }
        System.out.println("Bukkit Player API: registered " + s);
        ServerPlayerAPI.logger.fine("Bukkit Player API: registered class '" + clazz.getName() + "' with id '" + s + "'");
        ServerPlayerAPI.initialized = false;
    }
    
    public static boolean unregister(final String s) {
        if (s == null) {
            return false;
        }
        final Constructor<?> constructor = ServerPlayerAPI.allBaseConstructors.remove(s);
        if (constructor == null) {
            return false;
        }
        final Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
        for (int length = onlinePlayers.length, i = 0; i < length; ++i) {
            ((ServerPlayerAPI) ((CraftPlayer)onlinePlayers[i]).getHandle().serverPlayerAPI).detachServerPlayerBase(s);
        }
        ServerPlayerAPI.beforeLocalConstructingHookTypes.remove(s);
        ServerPlayerAPI.afterLocalConstructingHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExhaustionSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExhaustionInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExhaustionSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExhaustionInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExhaustionSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExhaustionInferiors.remove(s);
        ServerPlayerAPI.beforeAddExhaustionHookTypes.remove(s);
        ServerPlayerAPI.overrideAddExhaustionHookTypes.remove(s);
        ServerPlayerAPI.afterAddExhaustionHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExperienceSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExperienceInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExperienceSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExperienceInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExperienceSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExperienceInferiors.remove(s);
        ServerPlayerAPI.beforeAddExperienceHookTypes.remove(s);
        ServerPlayerAPI.overrideAddExperienceHookTypes.remove(s);
        ServerPlayerAPI.afterAddExperienceHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExperienceLevelSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAddExperienceLevelInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExperienceLevelSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddExperienceLevelInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExperienceLevelSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddExperienceLevelInferiors.remove(s);
        ServerPlayerAPI.beforeAddExperienceLevelHookTypes.remove(s);
        ServerPlayerAPI.overrideAddExperienceLevelHookTypes.remove(s);
        ServerPlayerAPI.afterAddExperienceLevelHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAddMovementStatSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAddMovementStatInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddMovementStatSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAddMovementStatInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddMovementStatSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAddMovementStatInferiors.remove(s);
        ServerPlayerAPI.beforeAddMovementStatHookTypes.remove(s);
        ServerPlayerAPI.overrideAddMovementStatHookTypes.remove(s);
        ServerPlayerAPI.afterAddMovementStatHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAttackEntityFromSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAttackEntityFromInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAttackEntityFromSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAttackEntityFromInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAttackEntityFromSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAttackEntityFromInferiors.remove(s);
        ServerPlayerAPI.beforeAttackEntityFromHookTypes.remove(s);
        ServerPlayerAPI.overrideAttackEntityFromHookTypes.remove(s);
        ServerPlayerAPI.afterAttackEntityFromHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemInferiors.remove(s);
        ServerPlayerAPI.beforeAttackTargetEntityWithCurrentItemHookTypes.remove(s);
        ServerPlayerAPI.overrideAttackTargetEntityWithCurrentItemHookTypes.remove(s);
        ServerPlayerAPI.afterAttackTargetEntityWithCurrentItemHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeCanHarvestBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeCanHarvestBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanHarvestBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanHarvestBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanHarvestBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanHarvestBlockInferiors.remove(s);
        ServerPlayerAPI.beforeCanHarvestBlockHookTypes.remove(s);
        ServerPlayerAPI.overrideCanHarvestBlockHookTypes.remove(s);
        ServerPlayerAPI.afterCanHarvestBlockHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeCanPlayerEditSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeCanPlayerEditInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanPlayerEditSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanPlayerEditInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanPlayerEditSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanPlayerEditInferiors.remove(s);
        ServerPlayerAPI.beforeCanPlayerEditHookTypes.remove(s);
        ServerPlayerAPI.overrideCanPlayerEditHookTypes.remove(s);
        ServerPlayerAPI.afterCanPlayerEditHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeCanTriggerWalkingSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeCanTriggerWalkingInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanTriggerWalkingSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideCanTriggerWalkingInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanTriggerWalkingSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterCanTriggerWalkingInferiors.remove(s);
        ServerPlayerAPI.beforeCanTriggerWalkingHookTypes.remove(s);
        ServerPlayerAPI.overrideCanTriggerWalkingHookTypes.remove(s);
        ServerPlayerAPI.afterCanTriggerWalkingHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeClonePlayerSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeClonePlayerInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideClonePlayerSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideClonePlayerInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterClonePlayerSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterClonePlayerInferiors.remove(s);
        ServerPlayerAPI.beforeClonePlayerHookTypes.remove(s);
        ServerPlayerAPI.overrideClonePlayerHookTypes.remove(s);
        ServerPlayerAPI.afterClonePlayerHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDamageEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDamageEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDamageEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDamageEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDamageEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDamageEntityInferiors.remove(s);
        ServerPlayerAPI.beforeDamageEntityHookTypes.remove(s);
        ServerPlayerAPI.overrideDamageEntityHookTypes.remove(s);
        ServerPlayerAPI.afterDamageEntityHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIChestSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIChestInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIChestSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIChestInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIChestSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIChestInferiors.remove(s);
        ServerPlayerAPI.beforeDisplayGUIChestHookTypes.remove(s);
        ServerPlayerAPI.overrideDisplayGUIChestHookTypes.remove(s);
        ServerPlayerAPI.afterDisplayGUIChestHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIDispenserSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIDispenserInferiors.remove(s);
        ServerPlayerAPI.beforeDisplayGUIDispenserHookTypes.remove(s);
        ServerPlayerAPI.overrideDisplayGUIDispenserHookTypes.remove(s);
        ServerPlayerAPI.afterDisplayGUIDispenserHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceInferiors.remove(s);
        ServerPlayerAPI.beforeDisplayGUIFurnaceHookTypes.remove(s);
        ServerPlayerAPI.overrideDisplayGUIFurnaceHookTypes.remove(s);
        ServerPlayerAPI.afterDisplayGUIFurnaceHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchInferiors.remove(s);
        ServerPlayerAPI.beforeDisplayGUIWorkbenchHookTypes.remove(s);
        ServerPlayerAPI.overrideDisplayGUIWorkbenchHookTypes.remove(s);
        ServerPlayerAPI.afterDisplayGUIWorkbenchHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDropOneItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDropOneItemInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDropOneItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDropOneItemInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDropOneItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDropOneItemInferiors.remove(s);
        ServerPlayerAPI.beforeDropOneItemHookTypes.remove(s);
        ServerPlayerAPI.overrideDropOneItemHookTypes.remove(s);
        ServerPlayerAPI.afterDropOneItemHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeDropPlayerItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDropPlayerItemInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDropPlayerItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDropPlayerItemInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDropPlayerItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDropPlayerItemInferiors.remove(s);
        ServerPlayerAPI.beforeDropPlayerItemHookTypes.remove(s);
        ServerPlayerAPI.overrideDropPlayerItemHookTypes.remove(s);
        ServerPlayerAPI.afterDropPlayerItemHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeFallSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeFallInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideFallSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideFallInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterFallSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterFallInferiors.remove(s);
        ServerPlayerAPI.beforeFallHookTypes.remove(s);
        ServerPlayerAPI.overrideFallHookTypes.remove(s);
        ServerPlayerAPI.afterFallHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockInferiors.remove(s);
        ServerPlayerAPI.beforeGetCurrentPlayerStrVsBlockHookTypes.remove(s);
        ServerPlayerAPI.overrideGetCurrentPlayerStrVsBlockHookTypes.remove(s);
        ServerPlayerAPI.afterGetCurrentPlayerStrVsBlockHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetDistanceSqSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetDistanceSqInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetDistanceSqSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetDistanceSqInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetDistanceSqSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetDistanceSqInferiors.remove(s);
        ServerPlayerAPI.beforeGetDistanceSqHookTypes.remove(s);
        ServerPlayerAPI.overrideGetDistanceSqHookTypes.remove(s);
        ServerPlayerAPI.afterGetDistanceSqHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetBrightnessSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetBrightnessInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetBrightnessSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetBrightnessInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetBrightnessSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetBrightnessInferiors.remove(s);
        ServerPlayerAPI.beforeGetBrightnessHookTypes.remove(s);
        ServerPlayerAPI.overrideGetBrightnessHookTypes.remove(s);
        ServerPlayerAPI.afterGetBrightnessHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetEyeHeightSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetEyeHeightInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetEyeHeightSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetEyeHeightInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetEyeHeightSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetEyeHeightInferiors.remove(s);
        ServerPlayerAPI.beforeGetEyeHeightHookTypes.remove(s);
        ServerPlayerAPI.overrideGetEyeHeightHookTypes.remove(s);
        ServerPlayerAPI.afterGetEyeHeightHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetMaxHealthSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetMaxHealthInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetMaxHealthSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetMaxHealthInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetMaxHealthSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetMaxHealthInferiors.remove(s);
        ServerPlayerAPI.beforeGetMaxHealthHookTypes.remove(s);
        ServerPlayerAPI.overrideGetMaxHealthHookTypes.remove(s);
        ServerPlayerAPI.afterGetMaxHealthHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeGetSpeedModifierSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeGetSpeedModifierInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetSpeedModifierSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideGetSpeedModifierInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetSpeedModifierSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterGetSpeedModifierInferiors.remove(s);
        ServerPlayerAPI.beforeGetSpeedModifierHookTypes.remove(s);
        ServerPlayerAPI.overrideGetSpeedModifierHookTypes.remove(s);
        ServerPlayerAPI.afterGetSpeedModifierHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeHealSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeHealInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideHealSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideHealInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterHealSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterHealInferiors.remove(s);
        ServerPlayerAPI.beforeHealHookTypes.remove(s);
        ServerPlayerAPI.overrideHealHookTypes.remove(s);
        ServerPlayerAPI.afterHealHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeInteractSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeInteractInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideInteractSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideInteractInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterInteractSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterInteractInferiors.remove(s);
        ServerPlayerAPI.beforeInteractHookTypes.remove(s);
        ServerPlayerAPI.overrideInteractHookTypes.remove(s);
        ServerPlayerAPI.afterInteractHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockInferiors.remove(s);
        ServerPlayerAPI.beforeIsEntityInsideOpaqueBlockHookTypes.remove(s);
        ServerPlayerAPI.overrideIsEntityInsideOpaqueBlockHookTypes.remove(s);
        ServerPlayerAPI.afterIsEntityInsideOpaqueBlockHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeIsInWaterSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeIsInWaterInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsInWaterSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsInWaterInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsInWaterSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsInWaterInferiors.remove(s);
        ServerPlayerAPI.beforeIsInWaterHookTypes.remove(s);
        ServerPlayerAPI.overrideIsInWaterHookTypes.remove(s);
        ServerPlayerAPI.afterIsInWaterHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsInsideOfMaterialSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsInsideOfMaterialInferiors.remove(s);
        ServerPlayerAPI.beforeIsInsideOfMaterialHookTypes.remove(s);
        ServerPlayerAPI.overrideIsInsideOfMaterialHookTypes.remove(s);
        ServerPlayerAPI.afterIsInsideOfMaterialHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeIsOnLadderSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeIsOnLadderInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsOnLadderSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsOnLadderInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsOnLadderSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsOnLadderInferiors.remove(s);
        ServerPlayerAPI.beforeIsOnLadderHookTypes.remove(s);
        ServerPlayerAPI.overrideIsOnLadderHookTypes.remove(s);
        ServerPlayerAPI.afterIsOnLadderHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeIsPlayerSleepingSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeIsPlayerSleepingInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsPlayerSleepingSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideIsPlayerSleepingInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsPlayerSleepingSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterIsPlayerSleepingInferiors.remove(s);
        ServerPlayerAPI.beforeIsPlayerSleepingHookTypes.remove(s);
        ServerPlayerAPI.overrideIsPlayerSleepingHookTypes.remove(s);
        ServerPlayerAPI.afterIsPlayerSleepingHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeJumpSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeJumpInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideJumpSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideJumpInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterJumpSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterJumpInferiors.remove(s);
        ServerPlayerAPI.beforeJumpHookTypes.remove(s);
        ServerPlayerAPI.overrideJumpHookTypes.remove(s);
        ServerPlayerAPI.afterJumpHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeKnockBackSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeKnockBackInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideKnockBackSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideKnockBackInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterKnockBackSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterKnockBackInferiors.remove(s);
        ServerPlayerAPI.beforeKnockBackHookTypes.remove(s);
        ServerPlayerAPI.overrideKnockBackHookTypes.remove(s);
        ServerPlayerAPI.afterKnockBackHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveEntityInferiors.remove(s);
        ServerPlayerAPI.beforeMoveEntityHookTypes.remove(s);
        ServerPlayerAPI.overrideMoveEntityHookTypes.remove(s);
        ServerPlayerAPI.afterMoveEntityHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingInferiors.remove(s);
        ServerPlayerAPI.beforeMoveEntityWithHeadingHookTypes.remove(s);
        ServerPlayerAPI.overrideMoveEntityWithHeadingHookTypes.remove(s);
        ServerPlayerAPI.afterMoveEntityWithHeadingHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveFlyingSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeMoveFlyingInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveFlyingSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideMoveFlyingInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveFlyingSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterMoveFlyingInferiors.remove(s);
        ServerPlayerAPI.beforeMoveFlyingHookTypes.remove(s);
        ServerPlayerAPI.overrideMoveFlyingHookTypes.remove(s);
        ServerPlayerAPI.afterMoveFlyingHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnDeathSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnDeathInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnDeathSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnDeathInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnDeathSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnDeathInferiors.remove(s);
        ServerPlayerAPI.beforeOnDeathHookTypes.remove(s);
        ServerPlayerAPI.overrideOnDeathHookTypes.remove(s);
        ServerPlayerAPI.afterOnDeathHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnLivingUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnLivingUpdateInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnLivingUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnLivingUpdateInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnLivingUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnLivingUpdateInferiors.remove(s);
        ServerPlayerAPI.beforeOnLivingUpdateHookTypes.remove(s);
        ServerPlayerAPI.overrideOnLivingUpdateHookTypes.remove(s);
        ServerPlayerAPI.afterOnLivingUpdateHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnKillEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnKillEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnKillEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnKillEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnKillEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnKillEntityInferiors.remove(s);
        ServerPlayerAPI.beforeOnKillEntityHookTypes.remove(s);
        ServerPlayerAPI.overrideOnKillEntityHookTypes.remove(s);
        ServerPlayerAPI.afterOnKillEntityHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnStruckByLightningSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnStruckByLightningInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnStruckByLightningSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnStruckByLightningInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnStruckByLightningSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnStruckByLightningInferiors.remove(s);
        ServerPlayerAPI.beforeOnStruckByLightningHookTypes.remove(s);
        ServerPlayerAPI.overrideOnStruckByLightningHookTypes.remove(s);
        ServerPlayerAPI.afterOnStruckByLightningHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnUpdateInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnUpdateInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnUpdateSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnUpdateInferiors.remove(s);
        ServerPlayerAPI.beforeOnUpdateHookTypes.remove(s);
        ServerPlayerAPI.overrideOnUpdateHookTypes.remove(s);
        ServerPlayerAPI.afterOnUpdateHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeOnUpdateEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeOnUpdateEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnUpdateEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideOnUpdateEntityInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnUpdateEntitySuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterOnUpdateEntityInferiors.remove(s);
        ServerPlayerAPI.beforeOnUpdateEntityHookTypes.remove(s);
        ServerPlayerAPI.overrideOnUpdateEntityHookTypes.remove(s);
        ServerPlayerAPI.afterOnUpdateEntityHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeReadEntityFromNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeReadEntityFromNBTInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideReadEntityFromNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideReadEntityFromNBTInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterReadEntityFromNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterReadEntityFromNBTInferiors.remove(s);
        ServerPlayerAPI.beforeReadEntityFromNBTHookTypes.remove(s);
        ServerPlayerAPI.overrideReadEntityFromNBTHookTypes.remove(s);
        ServerPlayerAPI.afterReadEntityFromNBTHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeSetDeadSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeSetDeadInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSetDeadSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSetDeadInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterSetDeadSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterSetDeadInferiors.remove(s);
        ServerPlayerAPI.beforeSetDeadHookTypes.remove(s);
        ServerPlayerAPI.overrideSetDeadHookTypes.remove(s);
        ServerPlayerAPI.afterSetDeadHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeSetPositionSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeSetPositionInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSetPositionSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSetPositionInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterSetPositionSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterSetPositionInferiors.remove(s);
        ServerPlayerAPI.beforeSetPositionHookTypes.remove(s);
        ServerPlayerAPI.overrideSetPositionHookTypes.remove(s);
        ServerPlayerAPI.afterSetPositionHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeSwingItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeSwingItemInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSwingItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideSwingItemInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterSwingItemSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterSwingItemInferiors.remove(s);
        ServerPlayerAPI.beforeSwingItemHookTypes.remove(s);
        ServerPlayerAPI.overrideSwingItemHookTypes.remove(s);
        ServerPlayerAPI.afterSwingItemHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterUpdateEntityActionStateSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterUpdateEntityActionStateInferiors.remove(s);
        ServerPlayerAPI.beforeUpdateEntityActionStateHookTypes.remove(s);
        ServerPlayerAPI.overrideUpdateEntityActionStateHookTypes.remove(s);
        ServerPlayerAPI.afterUpdateEntityActionStateHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterUpdatePotionEffectsSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterUpdatePotionEffectsInferiors.remove(s);
        ServerPlayerAPI.beforeUpdatePotionEffectsHookTypes.remove(s);
        ServerPlayerAPI.overrideUpdatePotionEffectsHookTypes.remove(s);
        ServerPlayerAPI.afterUpdatePotionEffectsHookTypes.remove(s);
        ServerPlayerAPI.allBaseBeforeWriteEntityToNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeWriteEntityToNBTInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideWriteEntityToNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideWriteEntityToNBTInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterWriteEntityToNBTSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterWriteEntityToNBTInferiors.remove(s);
        ServerPlayerAPI.beforeWriteEntityToNBTHookTypes.remove(s);
        ServerPlayerAPI.overrideWriteEntityToNBTHookTypes.remove(s);
        ServerPlayerAPI.afterWriteEntityToNBTHookTypes.remove(s);
        for (final String s2 : ServerPlayerAPI.keysToVirtualIds.keySet()) {
            if (ServerPlayerAPI.keysToVirtualIds.get(s2).equals(s)) {
                ServerPlayerAPI.keysToVirtualIds.remove(s2);
            }
        }
        boolean b = false;
        final Class<?> declaringClass = constructor.getDeclaringClass();
        for (final String s3 : ServerPlayerAPI.allBaseConstructors.keySet()) {
            final Class<?> declaringClass2 = ServerPlayerAPI.allBaseConstructors.get(s3).getDeclaringClass();
            if (!s3.equals(s) && declaringClass2.equals(declaringClass)) {
                b = true;
                break;
            }
        }
        if (!b) {
            ServerPlayerAPI.dynamicTypes.remove(declaringClass);
            ServerPlayerAPI.virtualDynamicHookMethods.remove(declaringClass);
            ServerPlayerAPI.beforeDynamicHookMethods.remove(declaringClass);
            ServerPlayerAPI.overrideDynamicHookMethods.remove(declaringClass);
            ServerPlayerAPI.afterDynamicHookMethods.remove(declaringClass);
        }
        removeDynamicHookTypes(s, ServerPlayerAPI.beforeDynamicHookTypes);
        removeDynamicHookTypes(s, ServerPlayerAPI.overrideDynamicHookTypes);
        removeDynamicHookTypes(s, ServerPlayerAPI.afterDynamicHookTypes);
        ServerPlayerAPI.allBaseBeforeDynamicSuperiors.remove(s);
        ServerPlayerAPI.allBaseBeforeDynamicInferiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDynamicSuperiors.remove(s);
        ServerPlayerAPI.allBaseOverrideDynamicInferiors.remove(s);
        ServerPlayerAPI.allBaseAfterDynamicSuperiors.remove(s);
        ServerPlayerAPI.allBaseAfterDynamicInferiors.remove(s);
        log("ServerPlayerAPI: unregistered id '" + s + "'");
        return true;
    }
    
    public static void removeDynamicHookTypes(final String s, final Map<String, List<String>> map) {
        final Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            map.get(iterator.next()).remove(s);
        }
    }
    
    public static Set<String> getRegisteredIds() {
        return ServerPlayerAPI.unmodifiableAllIds;
    }
    
    private static void addSorting(final String s, final Map<String, String[]> map, final String[] array) {
        if (array != null && array.length > 0) {
            map.put(s, array);
        }
    }
    
    private static void addDynamicSorting(final String s, final Map<String, Map<String, String[]>> map, final Map<String, String[]> map2) {
        if (map2 != null && map2.size() > 0) {
            map.put(s, map2);
        }
    }
    
    private static boolean addMethod(final String s, final Class<?> clazz, final List<String> list, final String s2, final Class<?>... array) {
        try {
            final boolean b = clazz.getMethod(s2, (Class[])array).getDeclaringClass() != ServerPlayerBase.class;
            if (b) {
                list.add(s);
            }
            return b;
        }
        catch (Exception ex) {
            throw new RuntimeException("Can not reflect method '" + s2 + "' of class '" + clazz.getName() + "'", ex);
        }
    }
    
    private static void addDynamicMethods(final String s, final Class<?> clazz) {
        if (!ServerPlayerAPI.dynamicTypes.add(clazz)) {
            return;
        }
        Map<String, Method> addDynamicMethod = null;
        Map<String, Method> addDynamicMethod2 = null;
        Map<String, Method> addDynamicMethod3 = null;
        Map<String, Method> addDynamicMethod4 = null;
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; ++i) {
            final Method method = declaredMethods[i];
            if (method.getDeclaringClass() == clazz) {
                final int modifiers = method.getModifiers();
                if (!Modifier.isAbstract(modifiers)) {
                    if (!Modifier.isStatic(modifiers)) {
                        final String name = method.getName();
                        if (name.length() >= 7) {
                            if (name.substring(0, 7).equalsIgnoreCase("dynamic")) {
                                String s2;
                                for (s2 = name.substring(7); s2.charAt(0) == '_'; s2 = s2.substring(1)) {}
                                boolean b = false;
                                boolean b2 = false;
                                boolean b3 = false;
                                boolean b4 = false;
                                if (s2.substring(0, 7).equalsIgnoreCase("virtual")) {
                                    b2 = true;
                                    s2 = s2.substring(7);
                                }
                                else if (s2.length() >= 8 && s2.substring(0, 8).equalsIgnoreCase("override")) {
                                    s2 = s2.substring(8);
                                    b3 = true;
                                }
                                else if (s2.length() >= 6 && s2.substring(0, 6).equalsIgnoreCase("before")) {
                                    b = true;
                                    s2 = s2.substring(6);
                                }
                                else if (s2.length() >= 5 && s2.substring(0, 5).equalsIgnoreCase("after")) {
                                    b4 = true;
                                    s2 = s2.substring(5);
                                }
                                if (s2.length() >= 1 && (b || b2 || b3 || b4)) {
                                    s2 = s2.substring(0, 1).toLowerCase() + s2.substring(1);
                                }
                                while (s2.charAt(0) == '_') {
                                    s2 = s2.substring(1);
                                }
                                if (s2.length() == 0) {
                                    throw new RuntimeException("Can not process dynamic hook method with no key");
                                }
                                ServerPlayerAPI.keys.add(s2);
                                if (b2) {
                                    if (ServerPlayerAPI.keysToVirtualIds.containsKey(s2)) {
                                        throw new RuntimeException("Can not process more than one dynamic virtual method");
                                    }
                                    ServerPlayerAPI.keysToVirtualIds.put(s2, s);
                                    addDynamicMethod = addDynamicMethod(s2, method, addDynamicMethod);
                                }
                                else if (b) {
                                    addDynamicMethod2 = addDynamicMethod(s2, method, addDynamicMethod2);
                                }
                                else if (b4) {
                                    addDynamicMethod4 = addDynamicMethod(s2, method, addDynamicMethod4);
                                }
                                else {
                                    addDynamicMethod3 = addDynamicMethod(s2, method, addDynamicMethod3);
                                }
                            }
                        }
                    }
                }
            }
        }
        if (addDynamicMethod != null) {
            ServerPlayerAPI.virtualDynamicHookMethods.put(clazz, addDynamicMethod);
        }
        if (addDynamicMethod2 != null) {
            ServerPlayerAPI.beforeDynamicHookMethods.put(clazz, addDynamicMethod2);
        }
        if (addDynamicMethod3 != null) {
            ServerPlayerAPI.overrideDynamicHookMethods.put(clazz, addDynamicMethod3);
        }
        if (addDynamicMethod4 != null) {
            ServerPlayerAPI.afterDynamicHookMethods.put(clazz, addDynamicMethod4);
        }
    }
    
    private static void addDynamicKeys(final String s, final Class<?> clazz, final Map<Class<?>, Map<String, Method>> map, final Map<String, List<String>> map2) {
        final Map<String, Method> map3 = map.get(clazz);
        if (map3 == null || map3.size() == 0) {
            return;
        }
        for (final String s2 : map3.keySet()) {
            if (!map2.containsKey(s2)) {
                map2.put(s2, new ArrayList<String>(1));
            }
            map2.get(s2).add(s);
        }
    }
    
    private static Map<String, Method> addDynamicMethod(final String s, final Method method, Map<String, Method> hashMap) {
        if (hashMap == null) {
            hashMap = new HashMap<String, Method>();
        }
        if (hashMap.containsKey(s)) {
            throw new RuntimeException("method with key '" + s + "' allready exists");
        }
        hashMap.put(s, method);
        return hashMap;
    }
    
    public static ServerPlayerAPI create(final EntityPlayer entityPlayer) {
        if (ServerPlayerAPI.allBaseConstructors.size() > 0 && !ServerPlayerAPI.initialized) {
            initialize();
        }
        return new ServerPlayerAPI(entityPlayer);
    }
    
    private static void initialize() {
        sortBases(ServerPlayerAPI.beforeLocalConstructingHookTypes, ServerPlayerAPI.allBaseBeforeLocalConstructingSuperiors, ServerPlayerAPI.allBaseBeforeLocalConstructingInferiors, "beforeLocalConstructing");
        sortBases(ServerPlayerAPI.afterLocalConstructingHookTypes, ServerPlayerAPI.allBaseAfterLocalConstructingSuperiors, ServerPlayerAPI.allBaseAfterLocalConstructingInferiors, "afterLocalConstructing");
        for (final String s : ServerPlayerAPI.keys) {
            sortDynamicBases(ServerPlayerAPI.beforeDynamicHookTypes, ServerPlayerAPI.allBaseBeforeDynamicSuperiors, ServerPlayerAPI.allBaseBeforeDynamicInferiors, s);
            sortDynamicBases(ServerPlayerAPI.overrideDynamicHookTypes, ServerPlayerAPI.allBaseOverrideDynamicSuperiors, ServerPlayerAPI.allBaseOverrideDynamicInferiors, s);
            sortDynamicBases(ServerPlayerAPI.afterDynamicHookTypes, ServerPlayerAPI.allBaseAfterDynamicSuperiors, ServerPlayerAPI.allBaseAfterDynamicInferiors, s);
        }
        sortBases(ServerPlayerAPI.beforeAddExhaustionHookTypes, ServerPlayerAPI.allBaseBeforeAddExhaustionSuperiors, ServerPlayerAPI.allBaseBeforeAddExhaustionInferiors, "beforeAddExhaustion");
        sortBases(ServerPlayerAPI.overrideAddExhaustionHookTypes, ServerPlayerAPI.allBaseOverrideAddExhaustionSuperiors, ServerPlayerAPI.allBaseOverrideAddExhaustionInferiors, "overrideAddExhaustion");
        sortBases(ServerPlayerAPI.afterAddExhaustionHookTypes, ServerPlayerAPI.allBaseAfterAddExhaustionSuperiors, ServerPlayerAPI.allBaseAfterAddExhaustionInferiors, "afterAddExhaustion");
        sortBases(ServerPlayerAPI.beforeAddExperienceHookTypes, ServerPlayerAPI.allBaseBeforeAddExperienceSuperiors, ServerPlayerAPI.allBaseBeforeAddExperienceInferiors, "beforeAddExperience");
        sortBases(ServerPlayerAPI.overrideAddExperienceHookTypes, ServerPlayerAPI.allBaseOverrideAddExperienceSuperiors, ServerPlayerAPI.allBaseOverrideAddExperienceInferiors, "overrideAddExperience");
        sortBases(ServerPlayerAPI.afterAddExperienceHookTypes, ServerPlayerAPI.allBaseAfterAddExperienceSuperiors, ServerPlayerAPI.allBaseAfterAddExperienceInferiors, "afterAddExperience");
        sortBases(ServerPlayerAPI.beforeAddExperienceLevelHookTypes, ServerPlayerAPI.allBaseBeforeAddExperienceLevelSuperiors, ServerPlayerAPI.allBaseBeforeAddExperienceLevelInferiors, "beforeAddExperienceLevel");
        sortBases(ServerPlayerAPI.overrideAddExperienceLevelHookTypes, ServerPlayerAPI.allBaseOverrideAddExperienceLevelSuperiors, ServerPlayerAPI.allBaseOverrideAddExperienceLevelInferiors, "overrideAddExperienceLevel");
        sortBases(ServerPlayerAPI.afterAddExperienceLevelHookTypes, ServerPlayerAPI.allBaseAfterAddExperienceLevelSuperiors, ServerPlayerAPI.allBaseAfterAddExperienceLevelInferiors, "afterAddExperienceLevel");
        sortBases(ServerPlayerAPI.beforeAddMovementStatHookTypes, ServerPlayerAPI.allBaseBeforeAddMovementStatSuperiors, ServerPlayerAPI.allBaseBeforeAddMovementStatInferiors, "beforeAddMovementStat");
        sortBases(ServerPlayerAPI.overrideAddMovementStatHookTypes, ServerPlayerAPI.allBaseOverrideAddMovementStatSuperiors, ServerPlayerAPI.allBaseOverrideAddMovementStatInferiors, "overrideAddMovementStat");
        sortBases(ServerPlayerAPI.afterAddMovementStatHookTypes, ServerPlayerAPI.allBaseAfterAddMovementStatSuperiors, ServerPlayerAPI.allBaseAfterAddMovementStatInferiors, "afterAddMovementStat");
        sortBases(ServerPlayerAPI.beforeAttackEntityFromHookTypes, ServerPlayerAPI.allBaseBeforeAttackEntityFromSuperiors, ServerPlayerAPI.allBaseBeforeAttackEntityFromInferiors, "beforeAttackEntityFrom");
        sortBases(ServerPlayerAPI.overrideAttackEntityFromHookTypes, ServerPlayerAPI.allBaseOverrideAttackEntityFromSuperiors, ServerPlayerAPI.allBaseOverrideAttackEntityFromInferiors, "overrideAttackEntityFrom");
        sortBases(ServerPlayerAPI.afterAttackEntityFromHookTypes, ServerPlayerAPI.allBaseAfterAttackEntityFromSuperiors, ServerPlayerAPI.allBaseAfterAttackEntityFromInferiors, "afterAttackEntityFrom");
        sortBases(ServerPlayerAPI.beforeAttackTargetEntityWithCurrentItemHookTypes, ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemSuperiors, ServerPlayerAPI.allBaseBeforeAttackTargetEntityWithCurrentItemInferiors, "beforeAttackTargetEntityWithCurrentItem");
        sortBases(ServerPlayerAPI.overrideAttackTargetEntityWithCurrentItemHookTypes, ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemSuperiors, ServerPlayerAPI.allBaseOverrideAttackTargetEntityWithCurrentItemInferiors, "overrideAttackTargetEntityWithCurrentItem");
        sortBases(ServerPlayerAPI.afterAttackTargetEntityWithCurrentItemHookTypes, ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemSuperiors, ServerPlayerAPI.allBaseAfterAttackTargetEntityWithCurrentItemInferiors, "afterAttackTargetEntityWithCurrentItem");
        sortBases(ServerPlayerAPI.beforeCanHarvestBlockHookTypes, ServerPlayerAPI.allBaseBeforeCanHarvestBlockSuperiors, ServerPlayerAPI.allBaseBeforeCanHarvestBlockInferiors, "beforeCanHarvestBlock");
        sortBases(ServerPlayerAPI.overrideCanHarvestBlockHookTypes, ServerPlayerAPI.allBaseOverrideCanHarvestBlockSuperiors, ServerPlayerAPI.allBaseOverrideCanHarvestBlockInferiors, "overrideCanHarvestBlock");
        sortBases(ServerPlayerAPI.afterCanHarvestBlockHookTypes, ServerPlayerAPI.allBaseAfterCanHarvestBlockSuperiors, ServerPlayerAPI.allBaseAfterCanHarvestBlockInferiors, "afterCanHarvestBlock");
        sortBases(ServerPlayerAPI.beforeCanPlayerEditHookTypes, ServerPlayerAPI.allBaseBeforeCanPlayerEditSuperiors, ServerPlayerAPI.allBaseBeforeCanPlayerEditInferiors, "beforeCanPlayerEdit");
        sortBases(ServerPlayerAPI.overrideCanPlayerEditHookTypes, ServerPlayerAPI.allBaseOverrideCanPlayerEditSuperiors, ServerPlayerAPI.allBaseOverrideCanPlayerEditInferiors, "overrideCanPlayerEdit");
        sortBases(ServerPlayerAPI.afterCanPlayerEditHookTypes, ServerPlayerAPI.allBaseAfterCanPlayerEditSuperiors, ServerPlayerAPI.allBaseAfterCanPlayerEditInferiors, "afterCanPlayerEdit");
        sortBases(ServerPlayerAPI.beforeCanTriggerWalkingHookTypes, ServerPlayerAPI.allBaseBeforeCanTriggerWalkingSuperiors, ServerPlayerAPI.allBaseBeforeCanTriggerWalkingInferiors, "beforeCanTriggerWalking");
        sortBases(ServerPlayerAPI.overrideCanTriggerWalkingHookTypes, ServerPlayerAPI.allBaseOverrideCanTriggerWalkingSuperiors, ServerPlayerAPI.allBaseOverrideCanTriggerWalkingInferiors, "overrideCanTriggerWalking");
        sortBases(ServerPlayerAPI.afterCanTriggerWalkingHookTypes, ServerPlayerAPI.allBaseAfterCanTriggerWalkingSuperiors, ServerPlayerAPI.allBaseAfterCanTriggerWalkingInferiors, "afterCanTriggerWalking");
        sortBases(ServerPlayerAPI.beforeClonePlayerHookTypes, ServerPlayerAPI.allBaseBeforeClonePlayerSuperiors, ServerPlayerAPI.allBaseBeforeClonePlayerInferiors, "beforeClonePlayer");
        sortBases(ServerPlayerAPI.overrideClonePlayerHookTypes, ServerPlayerAPI.allBaseOverrideClonePlayerSuperiors, ServerPlayerAPI.allBaseOverrideClonePlayerInferiors, "overrideClonePlayer");
        sortBases(ServerPlayerAPI.afterClonePlayerHookTypes, ServerPlayerAPI.allBaseAfterClonePlayerSuperiors, ServerPlayerAPI.allBaseAfterClonePlayerInferiors, "afterClonePlayer");
        sortBases(ServerPlayerAPI.beforeDamageEntityHookTypes, ServerPlayerAPI.allBaseBeforeDamageEntitySuperiors, ServerPlayerAPI.allBaseBeforeDamageEntityInferiors, "beforeDamageEntity");
        sortBases(ServerPlayerAPI.overrideDamageEntityHookTypes, ServerPlayerAPI.allBaseOverrideDamageEntitySuperiors, ServerPlayerAPI.allBaseOverrideDamageEntityInferiors, "overrideDamageEntity");
        sortBases(ServerPlayerAPI.afterDamageEntityHookTypes, ServerPlayerAPI.allBaseAfterDamageEntitySuperiors, ServerPlayerAPI.allBaseAfterDamageEntityInferiors, "afterDamageEntity");
        sortBases(ServerPlayerAPI.beforeDisplayGUIChestHookTypes, ServerPlayerAPI.allBaseBeforeDisplayGUIChestSuperiors, ServerPlayerAPI.allBaseBeforeDisplayGUIChestInferiors, "beforeDisplayGUIChest");
        sortBases(ServerPlayerAPI.overrideDisplayGUIChestHookTypes, ServerPlayerAPI.allBaseOverrideDisplayGUIChestSuperiors, ServerPlayerAPI.allBaseOverrideDisplayGUIChestInferiors, "overrideDisplayGUIChest");
        sortBases(ServerPlayerAPI.afterDisplayGUIChestHookTypes, ServerPlayerAPI.allBaseAfterDisplayGUIChestSuperiors, ServerPlayerAPI.allBaseAfterDisplayGUIChestInferiors, "afterDisplayGUIChest");
        sortBases(ServerPlayerAPI.beforeDisplayGUIDispenserHookTypes, ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserSuperiors, ServerPlayerAPI.allBaseBeforeDisplayGUIDispenserInferiors, "beforeDisplayGUIDispenser");
        sortBases(ServerPlayerAPI.overrideDisplayGUIDispenserHookTypes, ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserSuperiors, ServerPlayerAPI.allBaseOverrideDisplayGUIDispenserInferiors, "overrideDisplayGUIDispenser");
        sortBases(ServerPlayerAPI.afterDisplayGUIDispenserHookTypes, ServerPlayerAPI.allBaseAfterDisplayGUIDispenserSuperiors, ServerPlayerAPI.allBaseAfterDisplayGUIDispenserInferiors, "afterDisplayGUIDispenser");
        sortBases(ServerPlayerAPI.beforeDisplayGUIFurnaceHookTypes, ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceSuperiors, ServerPlayerAPI.allBaseBeforeDisplayGUIFurnaceInferiors, "beforeDisplayGUIFurnace");
        sortBases(ServerPlayerAPI.overrideDisplayGUIFurnaceHookTypes, ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceSuperiors, ServerPlayerAPI.allBaseOverrideDisplayGUIFurnaceInferiors, "overrideDisplayGUIFurnace");
        sortBases(ServerPlayerAPI.afterDisplayGUIFurnaceHookTypes, ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceSuperiors, ServerPlayerAPI.allBaseAfterDisplayGUIFurnaceInferiors, "afterDisplayGUIFurnace");
        sortBases(ServerPlayerAPI.beforeDisplayGUIWorkbenchHookTypes, ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchSuperiors, ServerPlayerAPI.allBaseBeforeDisplayGUIWorkbenchInferiors, "beforeDisplayGUIWorkbench");
        sortBases(ServerPlayerAPI.overrideDisplayGUIWorkbenchHookTypes, ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchSuperiors, ServerPlayerAPI.allBaseOverrideDisplayGUIWorkbenchInferiors, "overrideDisplayGUIWorkbench");
        sortBases(ServerPlayerAPI.afterDisplayGUIWorkbenchHookTypes, ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchSuperiors, ServerPlayerAPI.allBaseAfterDisplayGUIWorkbenchInferiors, "afterDisplayGUIWorkbench");
        sortBases(ServerPlayerAPI.beforeDropOneItemHookTypes, ServerPlayerAPI.allBaseBeforeDropOneItemSuperiors, ServerPlayerAPI.allBaseBeforeDropOneItemInferiors, "beforeDropOneItem");
        sortBases(ServerPlayerAPI.overrideDropOneItemHookTypes, ServerPlayerAPI.allBaseOverrideDropOneItemSuperiors, ServerPlayerAPI.allBaseOverrideDropOneItemInferiors, "overrideDropOneItem");
        sortBases(ServerPlayerAPI.afterDropOneItemHookTypes, ServerPlayerAPI.allBaseAfterDropOneItemSuperiors, ServerPlayerAPI.allBaseAfterDropOneItemInferiors, "afterDropOneItem");
        sortBases(ServerPlayerAPI.beforeDropPlayerItemHookTypes, ServerPlayerAPI.allBaseBeforeDropPlayerItemSuperiors, ServerPlayerAPI.allBaseBeforeDropPlayerItemInferiors, "beforeDropPlayerItem");
        sortBases(ServerPlayerAPI.overrideDropPlayerItemHookTypes, ServerPlayerAPI.allBaseOverrideDropPlayerItemSuperiors, ServerPlayerAPI.allBaseOverrideDropPlayerItemInferiors, "overrideDropPlayerItem");
        sortBases(ServerPlayerAPI.afterDropPlayerItemHookTypes, ServerPlayerAPI.allBaseAfterDropPlayerItemSuperiors, ServerPlayerAPI.allBaseAfterDropPlayerItemInferiors, "afterDropPlayerItem");
        sortBases(ServerPlayerAPI.beforeFallHookTypes, ServerPlayerAPI.allBaseBeforeFallSuperiors, ServerPlayerAPI.allBaseBeforeFallInferiors, "beforeFall");
        sortBases(ServerPlayerAPI.overrideFallHookTypes, ServerPlayerAPI.allBaseOverrideFallSuperiors, ServerPlayerAPI.allBaseOverrideFallInferiors, "overrideFall");
        sortBases(ServerPlayerAPI.afterFallHookTypes, ServerPlayerAPI.allBaseAfterFallSuperiors, ServerPlayerAPI.allBaseAfterFallInferiors, "afterFall");
        sortBases(ServerPlayerAPI.beforeGetCurrentPlayerStrVsBlockHookTypes, ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockSuperiors, ServerPlayerAPI.allBaseBeforeGetCurrentPlayerStrVsBlockInferiors, "beforeGetCurrentPlayerStrVsBlock");
        sortBases(ServerPlayerAPI.overrideGetCurrentPlayerStrVsBlockHookTypes, ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockSuperiors, ServerPlayerAPI.allBaseOverrideGetCurrentPlayerStrVsBlockInferiors, "overrideGetCurrentPlayerStrVsBlock");
        sortBases(ServerPlayerAPI.afterGetCurrentPlayerStrVsBlockHookTypes, ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockSuperiors, ServerPlayerAPI.allBaseAfterGetCurrentPlayerStrVsBlockInferiors, "afterGetCurrentPlayerStrVsBlock");
        sortBases(ServerPlayerAPI.beforeGetDistanceSqHookTypes, ServerPlayerAPI.allBaseBeforeGetDistanceSqSuperiors, ServerPlayerAPI.allBaseBeforeGetDistanceSqInferiors, "beforeGetDistanceSq");
        sortBases(ServerPlayerAPI.overrideGetDistanceSqHookTypes, ServerPlayerAPI.allBaseOverrideGetDistanceSqSuperiors, ServerPlayerAPI.allBaseOverrideGetDistanceSqInferiors, "overrideGetDistanceSq");
        sortBases(ServerPlayerAPI.afterGetDistanceSqHookTypes, ServerPlayerAPI.allBaseAfterGetDistanceSqSuperiors, ServerPlayerAPI.allBaseAfterGetDistanceSqInferiors, "afterGetDistanceSq");
        sortBases(ServerPlayerAPI.beforeGetBrightnessHookTypes, ServerPlayerAPI.allBaseBeforeGetBrightnessSuperiors, ServerPlayerAPI.allBaseBeforeGetBrightnessInferiors, "beforeGetBrightness");
        sortBases(ServerPlayerAPI.overrideGetBrightnessHookTypes, ServerPlayerAPI.allBaseOverrideGetBrightnessSuperiors, ServerPlayerAPI.allBaseOverrideGetBrightnessInferiors, "overrideGetBrightness");
        sortBases(ServerPlayerAPI.afterGetBrightnessHookTypes, ServerPlayerAPI.allBaseAfterGetBrightnessSuperiors, ServerPlayerAPI.allBaseAfterGetBrightnessInferiors, "afterGetBrightness");
        sortBases(ServerPlayerAPI.beforeGetEyeHeightHookTypes, ServerPlayerAPI.allBaseBeforeGetEyeHeightSuperiors, ServerPlayerAPI.allBaseBeforeGetEyeHeightInferiors, "beforeGetEyeHeight");
        sortBases(ServerPlayerAPI.overrideGetEyeHeightHookTypes, ServerPlayerAPI.allBaseOverrideGetEyeHeightSuperiors, ServerPlayerAPI.allBaseOverrideGetEyeHeightInferiors, "overrideGetEyeHeight");
        sortBases(ServerPlayerAPI.afterGetEyeHeightHookTypes, ServerPlayerAPI.allBaseAfterGetEyeHeightSuperiors, ServerPlayerAPI.allBaseAfterGetEyeHeightInferiors, "afterGetEyeHeight");
        sortBases(ServerPlayerAPI.beforeGetMaxHealthHookTypes, ServerPlayerAPI.allBaseBeforeGetMaxHealthSuperiors, ServerPlayerAPI.allBaseBeforeGetMaxHealthInferiors, "beforeGetMaxHealth");
        sortBases(ServerPlayerAPI.overrideGetMaxHealthHookTypes, ServerPlayerAPI.allBaseOverrideGetMaxHealthSuperiors, ServerPlayerAPI.allBaseOverrideGetMaxHealthInferiors, "overrideGetMaxHealth");
        sortBases(ServerPlayerAPI.afterGetMaxHealthHookTypes, ServerPlayerAPI.allBaseAfterGetMaxHealthSuperiors, ServerPlayerAPI.allBaseAfterGetMaxHealthInferiors, "afterGetMaxHealth");
        sortBases(ServerPlayerAPI.beforeGetSpeedModifierHookTypes, ServerPlayerAPI.allBaseBeforeGetSpeedModifierSuperiors, ServerPlayerAPI.allBaseBeforeGetSpeedModifierInferiors, "beforeGetSpeedModifier");
        sortBases(ServerPlayerAPI.overrideGetSpeedModifierHookTypes, ServerPlayerAPI.allBaseOverrideGetSpeedModifierSuperiors, ServerPlayerAPI.allBaseOverrideGetSpeedModifierInferiors, "overrideGetSpeedModifier");
        sortBases(ServerPlayerAPI.afterGetSpeedModifierHookTypes, ServerPlayerAPI.allBaseAfterGetSpeedModifierSuperiors, ServerPlayerAPI.allBaseAfterGetSpeedModifierInferiors, "afterGetSpeedModifier");
        sortBases(ServerPlayerAPI.beforeHealHookTypes, ServerPlayerAPI.allBaseBeforeHealSuperiors, ServerPlayerAPI.allBaseBeforeHealInferiors, "beforeHeal");
        sortBases(ServerPlayerAPI.overrideHealHookTypes, ServerPlayerAPI.allBaseOverrideHealSuperiors, ServerPlayerAPI.allBaseOverrideHealInferiors, "overrideHeal");
        sortBases(ServerPlayerAPI.afterHealHookTypes, ServerPlayerAPI.allBaseAfterHealSuperiors, ServerPlayerAPI.allBaseAfterHealInferiors, "afterHeal");
        sortBases(ServerPlayerAPI.beforeInteractHookTypes, ServerPlayerAPI.allBaseBeforeInteractSuperiors, ServerPlayerAPI.allBaseBeforeInteractInferiors, "beforeInteract");
        sortBases(ServerPlayerAPI.overrideInteractHookTypes, ServerPlayerAPI.allBaseOverrideInteractSuperiors, ServerPlayerAPI.allBaseOverrideInteractInferiors, "overrideInteract");
        sortBases(ServerPlayerAPI.afterInteractHookTypes, ServerPlayerAPI.allBaseAfterInteractSuperiors, ServerPlayerAPI.allBaseAfterInteractInferiors, "afterInteract");
        sortBases(ServerPlayerAPI.beforeIsEntityInsideOpaqueBlockHookTypes, ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockSuperiors, ServerPlayerAPI.allBaseBeforeIsEntityInsideOpaqueBlockInferiors, "beforeIsEntityInsideOpaqueBlock");
        sortBases(ServerPlayerAPI.overrideIsEntityInsideOpaqueBlockHookTypes, ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockSuperiors, ServerPlayerAPI.allBaseOverrideIsEntityInsideOpaqueBlockInferiors, "overrideIsEntityInsideOpaqueBlock");
        sortBases(ServerPlayerAPI.afterIsEntityInsideOpaqueBlockHookTypes, ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockSuperiors, ServerPlayerAPI.allBaseAfterIsEntityInsideOpaqueBlockInferiors, "afterIsEntityInsideOpaqueBlock");
        sortBases(ServerPlayerAPI.beforeIsInWaterHookTypes, ServerPlayerAPI.allBaseBeforeIsInWaterSuperiors, ServerPlayerAPI.allBaseBeforeIsInWaterInferiors, "beforeIsInWater");
        sortBases(ServerPlayerAPI.overrideIsInWaterHookTypes, ServerPlayerAPI.allBaseOverrideIsInWaterSuperiors, ServerPlayerAPI.allBaseOverrideIsInWaterInferiors, "overrideIsInWater");
        sortBases(ServerPlayerAPI.afterIsInWaterHookTypes, ServerPlayerAPI.allBaseAfterIsInWaterSuperiors, ServerPlayerAPI.allBaseAfterIsInWaterInferiors, "afterIsInWater");
        sortBases(ServerPlayerAPI.beforeIsInsideOfMaterialHookTypes, ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialSuperiors, ServerPlayerAPI.allBaseBeforeIsInsideOfMaterialInferiors, "beforeIsInsideOfMaterial");
        sortBases(ServerPlayerAPI.overrideIsInsideOfMaterialHookTypes, ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialSuperiors, ServerPlayerAPI.allBaseOverrideIsInsideOfMaterialInferiors, "overrideIsInsideOfMaterial");
        sortBases(ServerPlayerAPI.afterIsInsideOfMaterialHookTypes, ServerPlayerAPI.allBaseAfterIsInsideOfMaterialSuperiors, ServerPlayerAPI.allBaseAfterIsInsideOfMaterialInferiors, "afterIsInsideOfMaterial");
        sortBases(ServerPlayerAPI.beforeIsOnLadderHookTypes, ServerPlayerAPI.allBaseBeforeIsOnLadderSuperiors, ServerPlayerAPI.allBaseBeforeIsOnLadderInferiors, "beforeIsOnLadder");
        sortBases(ServerPlayerAPI.overrideIsOnLadderHookTypes, ServerPlayerAPI.allBaseOverrideIsOnLadderSuperiors, ServerPlayerAPI.allBaseOverrideIsOnLadderInferiors, "overrideIsOnLadder");
        sortBases(ServerPlayerAPI.afterIsOnLadderHookTypes, ServerPlayerAPI.allBaseAfterIsOnLadderSuperiors, ServerPlayerAPI.allBaseAfterIsOnLadderInferiors, "afterIsOnLadder");
        sortBases(ServerPlayerAPI.beforeIsPlayerSleepingHookTypes, ServerPlayerAPI.allBaseBeforeIsPlayerSleepingSuperiors, ServerPlayerAPI.allBaseBeforeIsPlayerSleepingInferiors, "beforeIsPlayerSleeping");
        sortBases(ServerPlayerAPI.overrideIsPlayerSleepingHookTypes, ServerPlayerAPI.allBaseOverrideIsPlayerSleepingSuperiors, ServerPlayerAPI.allBaseOverrideIsPlayerSleepingInferiors, "overrideIsPlayerSleeping");
        sortBases(ServerPlayerAPI.afterIsPlayerSleepingHookTypes, ServerPlayerAPI.allBaseAfterIsPlayerSleepingSuperiors, ServerPlayerAPI.allBaseAfterIsPlayerSleepingInferiors, "afterIsPlayerSleeping");
        sortBases(ServerPlayerAPI.beforeJumpHookTypes, ServerPlayerAPI.allBaseBeforeJumpSuperiors, ServerPlayerAPI.allBaseBeforeJumpInferiors, "beforeJump");
        sortBases(ServerPlayerAPI.overrideJumpHookTypes, ServerPlayerAPI.allBaseOverrideJumpSuperiors, ServerPlayerAPI.allBaseOverrideJumpInferiors, "overrideJump");
        sortBases(ServerPlayerAPI.afterJumpHookTypes, ServerPlayerAPI.allBaseAfterJumpSuperiors, ServerPlayerAPI.allBaseAfterJumpInferiors, "afterJump");
        sortBases(ServerPlayerAPI.beforeKnockBackHookTypes, ServerPlayerAPI.allBaseBeforeKnockBackSuperiors, ServerPlayerAPI.allBaseBeforeKnockBackInferiors, "beforeKnockBack");
        sortBases(ServerPlayerAPI.overrideKnockBackHookTypes, ServerPlayerAPI.allBaseOverrideKnockBackSuperiors, ServerPlayerAPI.allBaseOverrideKnockBackInferiors, "overrideKnockBack");
        sortBases(ServerPlayerAPI.afterKnockBackHookTypes, ServerPlayerAPI.allBaseAfterKnockBackSuperiors, ServerPlayerAPI.allBaseAfterKnockBackInferiors, "afterKnockBack");
        sortBases(ServerPlayerAPI.beforeMoveEntityHookTypes, ServerPlayerAPI.allBaseBeforeMoveEntitySuperiors, ServerPlayerAPI.allBaseBeforeMoveEntityInferiors, "beforeMoveEntity");
        sortBases(ServerPlayerAPI.overrideMoveEntityHookTypes, ServerPlayerAPI.allBaseOverrideMoveEntitySuperiors, ServerPlayerAPI.allBaseOverrideMoveEntityInferiors, "overrideMoveEntity");
        sortBases(ServerPlayerAPI.afterMoveEntityHookTypes, ServerPlayerAPI.allBaseAfterMoveEntitySuperiors, ServerPlayerAPI.allBaseAfterMoveEntityInferiors, "afterMoveEntity");
        sortBases(ServerPlayerAPI.beforeMoveEntityWithHeadingHookTypes, ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingSuperiors, ServerPlayerAPI.allBaseBeforeMoveEntityWithHeadingInferiors, "beforeMoveEntityWithHeading");
        sortBases(ServerPlayerAPI.overrideMoveEntityWithHeadingHookTypes, ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingSuperiors, ServerPlayerAPI.allBaseOverrideMoveEntityWithHeadingInferiors, "overrideMoveEntityWithHeading");
        sortBases(ServerPlayerAPI.afterMoveEntityWithHeadingHookTypes, ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingSuperiors, ServerPlayerAPI.allBaseAfterMoveEntityWithHeadingInferiors, "afterMoveEntityWithHeading");
        sortBases(ServerPlayerAPI.beforeMoveFlyingHookTypes, ServerPlayerAPI.allBaseBeforeMoveFlyingSuperiors, ServerPlayerAPI.allBaseBeforeMoveFlyingInferiors, "beforeMoveFlying");
        sortBases(ServerPlayerAPI.overrideMoveFlyingHookTypes, ServerPlayerAPI.allBaseOverrideMoveFlyingSuperiors, ServerPlayerAPI.allBaseOverrideMoveFlyingInferiors, "overrideMoveFlying");
        sortBases(ServerPlayerAPI.afterMoveFlyingHookTypes, ServerPlayerAPI.allBaseAfterMoveFlyingSuperiors, ServerPlayerAPI.allBaseAfterMoveFlyingInferiors, "afterMoveFlying");
        sortBases(ServerPlayerAPI.beforeOnDeathHookTypes, ServerPlayerAPI.allBaseBeforeOnDeathSuperiors, ServerPlayerAPI.allBaseBeforeOnDeathInferiors, "beforeOnDeath");
        sortBases(ServerPlayerAPI.overrideOnDeathHookTypes, ServerPlayerAPI.allBaseOverrideOnDeathSuperiors, ServerPlayerAPI.allBaseOverrideOnDeathInferiors, "overrideOnDeath");
        sortBases(ServerPlayerAPI.afterOnDeathHookTypes, ServerPlayerAPI.allBaseAfterOnDeathSuperiors, ServerPlayerAPI.allBaseAfterOnDeathInferiors, "afterOnDeath");
        sortBases(ServerPlayerAPI.beforeOnLivingUpdateHookTypes, ServerPlayerAPI.allBaseBeforeOnLivingUpdateSuperiors, ServerPlayerAPI.allBaseBeforeOnLivingUpdateInferiors, "beforeOnLivingUpdate");
        sortBases(ServerPlayerAPI.overrideOnLivingUpdateHookTypes, ServerPlayerAPI.allBaseOverrideOnLivingUpdateSuperiors, ServerPlayerAPI.allBaseOverrideOnLivingUpdateInferiors, "overrideOnLivingUpdate");
        sortBases(ServerPlayerAPI.afterOnLivingUpdateHookTypes, ServerPlayerAPI.allBaseAfterOnLivingUpdateSuperiors, ServerPlayerAPI.allBaseAfterOnLivingUpdateInferiors, "afterOnLivingUpdate");
        sortBases(ServerPlayerAPI.beforeOnKillEntityHookTypes, ServerPlayerAPI.allBaseBeforeOnKillEntitySuperiors, ServerPlayerAPI.allBaseBeforeOnKillEntityInferiors, "beforeOnKillEntity");
        sortBases(ServerPlayerAPI.overrideOnKillEntityHookTypes, ServerPlayerAPI.allBaseOverrideOnKillEntitySuperiors, ServerPlayerAPI.allBaseOverrideOnKillEntityInferiors, "overrideOnKillEntity");
        sortBases(ServerPlayerAPI.afterOnKillEntityHookTypes, ServerPlayerAPI.allBaseAfterOnKillEntitySuperiors, ServerPlayerAPI.allBaseAfterOnKillEntityInferiors, "afterOnKillEntity");
        sortBases(ServerPlayerAPI.beforeOnStruckByLightningHookTypes, ServerPlayerAPI.allBaseBeforeOnStruckByLightningSuperiors, ServerPlayerAPI.allBaseBeforeOnStruckByLightningInferiors, "beforeOnStruckByLightning");
        sortBases(ServerPlayerAPI.overrideOnStruckByLightningHookTypes, ServerPlayerAPI.allBaseOverrideOnStruckByLightningSuperiors, ServerPlayerAPI.allBaseOverrideOnStruckByLightningInferiors, "overrideOnStruckByLightning");
        sortBases(ServerPlayerAPI.afterOnStruckByLightningHookTypes, ServerPlayerAPI.allBaseAfterOnStruckByLightningSuperiors, ServerPlayerAPI.allBaseAfterOnStruckByLightningInferiors, "afterOnStruckByLightning");
        sortBases(ServerPlayerAPI.beforeOnUpdateHookTypes, ServerPlayerAPI.allBaseBeforeOnUpdateSuperiors, ServerPlayerAPI.allBaseBeforeOnUpdateInferiors, "beforeOnUpdate");
        sortBases(ServerPlayerAPI.overrideOnUpdateHookTypes, ServerPlayerAPI.allBaseOverrideOnUpdateSuperiors, ServerPlayerAPI.allBaseOverrideOnUpdateInferiors, "overrideOnUpdate");
        sortBases(ServerPlayerAPI.afterOnUpdateHookTypes, ServerPlayerAPI.allBaseAfterOnUpdateSuperiors, ServerPlayerAPI.allBaseAfterOnUpdateInferiors, "afterOnUpdate");
        sortBases(ServerPlayerAPI.beforeOnUpdateEntityHookTypes, ServerPlayerAPI.allBaseBeforeOnUpdateEntitySuperiors, ServerPlayerAPI.allBaseBeforeOnUpdateEntityInferiors, "beforeOnUpdateEntity");
        sortBases(ServerPlayerAPI.overrideOnUpdateEntityHookTypes, ServerPlayerAPI.allBaseOverrideOnUpdateEntitySuperiors, ServerPlayerAPI.allBaseOverrideOnUpdateEntityInferiors, "overrideOnUpdateEntity");
        sortBases(ServerPlayerAPI.afterOnUpdateEntityHookTypes, ServerPlayerAPI.allBaseAfterOnUpdateEntitySuperiors, ServerPlayerAPI.allBaseAfterOnUpdateEntityInferiors, "afterOnUpdateEntity");
        sortBases(ServerPlayerAPI.beforeReadEntityFromNBTHookTypes, ServerPlayerAPI.allBaseBeforeReadEntityFromNBTSuperiors, ServerPlayerAPI.allBaseBeforeReadEntityFromNBTInferiors, "beforeReadEntityFromNBT");
        sortBases(ServerPlayerAPI.overrideReadEntityFromNBTHookTypes, ServerPlayerAPI.allBaseOverrideReadEntityFromNBTSuperiors, ServerPlayerAPI.allBaseOverrideReadEntityFromNBTInferiors, "overrideReadEntityFromNBT");
        sortBases(ServerPlayerAPI.afterReadEntityFromNBTHookTypes, ServerPlayerAPI.allBaseAfterReadEntityFromNBTSuperiors, ServerPlayerAPI.allBaseAfterReadEntityFromNBTInferiors, "afterReadEntityFromNBT");
        sortBases(ServerPlayerAPI.beforeSetDeadHookTypes, ServerPlayerAPI.allBaseBeforeSetDeadSuperiors, ServerPlayerAPI.allBaseBeforeSetDeadInferiors, "beforeSetDead");
        sortBases(ServerPlayerAPI.overrideSetDeadHookTypes, ServerPlayerAPI.allBaseOverrideSetDeadSuperiors, ServerPlayerAPI.allBaseOverrideSetDeadInferiors, "overrideSetDead");
        sortBases(ServerPlayerAPI.afterSetDeadHookTypes, ServerPlayerAPI.allBaseAfterSetDeadSuperiors, ServerPlayerAPI.allBaseAfterSetDeadInferiors, "afterSetDead");
        sortBases(ServerPlayerAPI.beforeSetPositionHookTypes, ServerPlayerAPI.allBaseBeforeSetPositionSuperiors, ServerPlayerAPI.allBaseBeforeSetPositionInferiors, "beforeSetPosition");
        sortBases(ServerPlayerAPI.overrideSetPositionHookTypes, ServerPlayerAPI.allBaseOverrideSetPositionSuperiors, ServerPlayerAPI.allBaseOverrideSetPositionInferiors, "overrideSetPosition");
        sortBases(ServerPlayerAPI.afterSetPositionHookTypes, ServerPlayerAPI.allBaseAfterSetPositionSuperiors, ServerPlayerAPI.allBaseAfterSetPositionInferiors, "afterSetPosition");
        sortBases(ServerPlayerAPI.beforeSwingItemHookTypes, ServerPlayerAPI.allBaseBeforeSwingItemSuperiors, ServerPlayerAPI.allBaseBeforeSwingItemInferiors, "beforeSwingItem");
        sortBases(ServerPlayerAPI.overrideSwingItemHookTypes, ServerPlayerAPI.allBaseOverrideSwingItemSuperiors, ServerPlayerAPI.allBaseOverrideSwingItemInferiors, "overrideSwingItem");
        sortBases(ServerPlayerAPI.afterSwingItemHookTypes, ServerPlayerAPI.allBaseAfterSwingItemSuperiors, ServerPlayerAPI.allBaseAfterSwingItemInferiors, "afterSwingItem");
        sortBases(ServerPlayerAPI.beforeUpdateEntityActionStateHookTypes, ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateSuperiors, ServerPlayerAPI.allBaseBeforeUpdateEntityActionStateInferiors, "beforeUpdateEntityActionState");
        sortBases(ServerPlayerAPI.overrideUpdateEntityActionStateHookTypes, ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateSuperiors, ServerPlayerAPI.allBaseOverrideUpdateEntityActionStateInferiors, "overrideUpdateEntityActionState");
        sortBases(ServerPlayerAPI.afterUpdateEntityActionStateHookTypes, ServerPlayerAPI.allBaseAfterUpdateEntityActionStateSuperiors, ServerPlayerAPI.allBaseAfterUpdateEntityActionStateInferiors, "afterUpdateEntityActionState");
        sortBases(ServerPlayerAPI.beforeUpdatePotionEffectsHookTypes, ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsSuperiors, ServerPlayerAPI.allBaseBeforeUpdatePotionEffectsInferiors, "beforeUpdatePotionEffects");
        sortBases(ServerPlayerAPI.overrideUpdatePotionEffectsHookTypes, ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsSuperiors, ServerPlayerAPI.allBaseOverrideUpdatePotionEffectsInferiors, "overrideUpdatePotionEffects");
        sortBases(ServerPlayerAPI.afterUpdatePotionEffectsHookTypes, ServerPlayerAPI.allBaseAfterUpdatePotionEffectsSuperiors, ServerPlayerAPI.allBaseAfterUpdatePotionEffectsInferiors, "afterUpdatePotionEffects");
        sortBases(ServerPlayerAPI.beforeWriteEntityToNBTHookTypes, ServerPlayerAPI.allBaseBeforeWriteEntityToNBTSuperiors, ServerPlayerAPI.allBaseBeforeWriteEntityToNBTInferiors, "beforeWriteEntityToNBT");
        sortBases(ServerPlayerAPI.overrideWriteEntityToNBTHookTypes, ServerPlayerAPI.allBaseOverrideWriteEntityToNBTSuperiors, ServerPlayerAPI.allBaseOverrideWriteEntityToNBTInferiors, "overrideWriteEntityToNBT");
        sortBases(ServerPlayerAPI.afterWriteEntityToNBTHookTypes, ServerPlayerAPI.allBaseAfterWriteEntityToNBTSuperiors, ServerPlayerAPI.allBaseAfterWriteEntityToNBTInferiors, "afterWriteEntityToNBT");
        ServerPlayerAPI.initialized = true;
    }
    
    public static void beforeLocalConstructing(final EntityPlayer entityPlayer, final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
        if (entityPlayer.serverPlayerAPI != null) {
            ((ServerPlayerAPI) entityPlayer.serverPlayerAPI).beforeLocalConstructing(minecraftServer, world, s, playerInteractManager);
        }
    }
    
    public static void afterLocalConstructing(final EntityPlayer entityPlayer, final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
        if (entityPlayer.serverPlayerAPI != null) {
            ((ServerPlayerAPI) entityPlayer.serverPlayerAPI).afterLocalConstructing(minecraftServer, world, s, playerInteractManager);
        }
    }
    
    private static void sortBases(final List<String> list, final Map<String, String[]> map, final Map<String, String[]> map2, final String s) {
        new ServerPlayerBaseSorter(list, map, map2, s).Sort();
    }
    
    private static void sortDynamicBases(final Map<String, List<String>> map, final Map<String, Map<String, String[]>> map2, final Map<String, Map<String, String[]>> map3, final String s) {
        final List<String> list = map.get(s);
        if (list != null && list.size() > 1) {
            sortBases(list, getDynamicSorters(s, list, map2), getDynamicSorters(s, list, map3), s);
        }
    }
    
    private static Map<String, String[]> getDynamicSorters(final String s, final List<String> list, final Map<String, Map<String, String[]>> map) {
        Map<String, String[]> map2 = null;
        for (final String s2 : list) {
            final Map<String, String[]> map3 = map.get(s2);
            if (map3 == null) {
                continue;
            }
            final String[] array = map3.get(s);
            if (array == null || array.length <= 0) {
                continue;
            }
            if (map2 == null) {
                map2 = new HashMap<String, String[]>(1);
            }
            map2.put(s2, array);
        }
        return (map2 != null) ? map2 : ServerPlayerAPI.EmptySortMap;
    }
    
    private ServerPlayerAPI(final EntityPlayer player) {
        this.baseObjectsToId = new Hashtable<ServerPlayerBase, String>();
        this.allBaseObjects = new Hashtable<String, ServerPlayerBase>();
        this.unmodifiableAllBaseIds = Collections.unmodifiableSet((Set<? extends String>)this.allBaseObjects.keySet());
        this.player = player;
        ServerPlayerAPI.initializer[0] = this;
        ServerPlayerAPI.initializers[0] = this;
        for (final String s : ServerPlayerAPI.allBaseConstructors.keySet()) {
            final ServerPlayerBase serverPlayerBase = this.createServerPlayerBase(s);
            serverPlayerBase.beforeBaseAttach(false);
            this.allBaseObjects.put(s, serverPlayerBase);
            this.baseObjectsToId.put(serverPlayerBase, s);
        }
        this.beforeLocalConstructingHooks = this.create(ServerPlayerAPI.beforeLocalConstructingHookTypes);
        this.afterLocalConstructingHooks = this.create(ServerPlayerAPI.afterLocalConstructingHookTypes);
        this.updateServerPlayerBases();
        final Iterator<String> iterator2 = this.allBaseObjects.keySet().iterator();
        while (iterator2.hasNext()) {
            this.allBaseObjects.get(iterator2.next()).afterBaseAttach(false);
        }
    }
    
    private ServerPlayerBase createServerPlayerBase(final String s) {
        final Constructor<?> constructor = ServerPlayerAPI.allBaseConstructors.get(s);
        ServerPlayerAPI.initializers[1] = s;
        ServerPlayerBase serverPlayerBase;
        try {
            if (constructor.getParameterTypes().length == 1) {
                serverPlayerBase = (ServerPlayerBase)constructor.newInstance(ServerPlayerAPI.initializer);
            }
            else {
                serverPlayerBase = (ServerPlayerBase)constructor.newInstance(ServerPlayerAPI.initializers);
            }
        }
        catch (Exception ex) {
            throw new RuntimeException("Exception while creating a ServerPlayerBase of type '" + constructor.getDeclaringClass() + "'", ex);
        }
        return serverPlayerBase;
    }
    
    private void updateServerPlayerBases() {
        this.beforeAddExhaustionHooks = this.create(ServerPlayerAPI.beforeAddExhaustionHookTypes);
        this.overrideAddExhaustionHooks = this.create(ServerPlayerAPI.overrideAddExhaustionHookTypes);
        this.afterAddExhaustionHooks = this.create(ServerPlayerAPI.afterAddExhaustionHookTypes);
        this.isAddExhaustionModded = (this.beforeAddExhaustionHooks != null || this.overrideAddExhaustionHooks != null || this.afterAddExhaustionHooks != null);
        this.beforeAddExperienceHooks = this.create(ServerPlayerAPI.beforeAddExperienceHookTypes);
        this.overrideAddExperienceHooks = this.create(ServerPlayerAPI.overrideAddExperienceHookTypes);
        this.afterAddExperienceHooks = this.create(ServerPlayerAPI.afterAddExperienceHookTypes);
        this.isAddExperienceModded = (this.beforeAddExperienceHooks != null || this.overrideAddExperienceHooks != null || this.afterAddExperienceHooks != null);
        this.beforeAddExperienceLevelHooks = this.create(ServerPlayerAPI.beforeAddExperienceLevelHookTypes);
        this.overrideAddExperienceLevelHooks = this.create(ServerPlayerAPI.overrideAddExperienceLevelHookTypes);
        this.afterAddExperienceLevelHooks = this.create(ServerPlayerAPI.afterAddExperienceLevelHookTypes);
        this.isAddExperienceLevelModded = (this.beforeAddExperienceLevelHooks != null || this.overrideAddExperienceLevelHooks != null || this.afterAddExperienceLevelHooks != null);
        this.beforeAddMovementStatHooks = this.create(ServerPlayerAPI.beforeAddMovementStatHookTypes);
        this.overrideAddMovementStatHooks = this.create(ServerPlayerAPI.overrideAddMovementStatHookTypes);
        this.afterAddMovementStatHooks = this.create(ServerPlayerAPI.afterAddMovementStatHookTypes);
        this.isAddMovementStatModded = (this.beforeAddMovementStatHooks != null || this.overrideAddMovementStatHooks != null || this.afterAddMovementStatHooks != null);
        this.beforeAttackEntityFromHooks = this.create(ServerPlayerAPI.beforeAttackEntityFromHookTypes);
        this.overrideAttackEntityFromHooks = this.create(ServerPlayerAPI.overrideAttackEntityFromHookTypes);
        this.afterAttackEntityFromHooks = this.create(ServerPlayerAPI.afterAttackEntityFromHookTypes);
        this.isAttackEntityFromModded = (this.beforeAttackEntityFromHooks != null || this.overrideAttackEntityFromHooks != null || this.afterAttackEntityFromHooks != null);
        this.beforeAttackTargetEntityWithCurrentItemHooks = this.create(ServerPlayerAPI.beforeAttackTargetEntityWithCurrentItemHookTypes);
        this.overrideAttackTargetEntityWithCurrentItemHooks = this.create(ServerPlayerAPI.overrideAttackTargetEntityWithCurrentItemHookTypes);
        this.afterAttackTargetEntityWithCurrentItemHooks = this.create(ServerPlayerAPI.afterAttackTargetEntityWithCurrentItemHookTypes);
        this.isAttackTargetEntityWithCurrentItemModded = (this.beforeAttackTargetEntityWithCurrentItemHooks != null || this.overrideAttackTargetEntityWithCurrentItemHooks != null || this.afterAttackTargetEntityWithCurrentItemHooks != null);
        this.beforeCanHarvestBlockHooks = this.create(ServerPlayerAPI.beforeCanHarvestBlockHookTypes);
        this.overrideCanHarvestBlockHooks = this.create(ServerPlayerAPI.overrideCanHarvestBlockHookTypes);
        this.afterCanHarvestBlockHooks = this.create(ServerPlayerAPI.afterCanHarvestBlockHookTypes);
        this.isCanHarvestBlockModded = (this.beforeCanHarvestBlockHooks != null || this.overrideCanHarvestBlockHooks != null || this.afterCanHarvestBlockHooks != null);
        this.beforeCanPlayerEditHooks = this.create(ServerPlayerAPI.beforeCanPlayerEditHookTypes);
        this.overrideCanPlayerEditHooks = this.create(ServerPlayerAPI.overrideCanPlayerEditHookTypes);
        this.afterCanPlayerEditHooks = this.create(ServerPlayerAPI.afterCanPlayerEditHookTypes);
        this.isCanPlayerEditModded = (this.beforeCanPlayerEditHooks != null || this.overrideCanPlayerEditHooks != null || this.afterCanPlayerEditHooks != null);
        this.beforeCanTriggerWalkingHooks = this.create(ServerPlayerAPI.beforeCanTriggerWalkingHookTypes);
        this.overrideCanTriggerWalkingHooks = this.create(ServerPlayerAPI.overrideCanTriggerWalkingHookTypes);
        this.afterCanTriggerWalkingHooks = this.create(ServerPlayerAPI.afterCanTriggerWalkingHookTypes);
        this.isCanTriggerWalkingModded = (this.beforeCanTriggerWalkingHooks != null || this.overrideCanTriggerWalkingHooks != null || this.afterCanTriggerWalkingHooks != null);
        this.beforeClonePlayerHooks = this.create(ServerPlayerAPI.beforeClonePlayerHookTypes);
        this.overrideClonePlayerHooks = this.create(ServerPlayerAPI.overrideClonePlayerHookTypes);
        this.afterClonePlayerHooks = this.create(ServerPlayerAPI.afterClonePlayerHookTypes);
        this.isClonePlayerModded = (this.beforeClonePlayerHooks != null || this.overrideClonePlayerHooks != null || this.afterClonePlayerHooks != null);
        this.beforeDamageEntityHooks = this.create(ServerPlayerAPI.beforeDamageEntityHookTypes);
        this.overrideDamageEntityHooks = this.create(ServerPlayerAPI.overrideDamageEntityHookTypes);
        this.afterDamageEntityHooks = this.create(ServerPlayerAPI.afterDamageEntityHookTypes);
        this.isDamageEntityModded = (this.beforeDamageEntityHooks != null || this.overrideDamageEntityHooks != null || this.afterDamageEntityHooks != null);
        this.beforeDisplayGUIChestHooks = this.create(ServerPlayerAPI.beforeDisplayGUIChestHookTypes);
        this.overrideDisplayGUIChestHooks = this.create(ServerPlayerAPI.overrideDisplayGUIChestHookTypes);
        this.afterDisplayGUIChestHooks = this.create(ServerPlayerAPI.afterDisplayGUIChestHookTypes);
        this.isDisplayGUIChestModded = (this.beforeDisplayGUIChestHooks != null || this.overrideDisplayGUIChestHooks != null || this.afterDisplayGUIChestHooks != null);
        this.beforeDisplayGUIDispenserHooks = this.create(ServerPlayerAPI.beforeDisplayGUIDispenserHookTypes);
        this.overrideDisplayGUIDispenserHooks = this.create(ServerPlayerAPI.overrideDisplayGUIDispenserHookTypes);
        this.afterDisplayGUIDispenserHooks = this.create(ServerPlayerAPI.afterDisplayGUIDispenserHookTypes);
        this.isDisplayGUIDispenserModded = (this.beforeDisplayGUIDispenserHooks != null || this.overrideDisplayGUIDispenserHooks != null || this.afterDisplayGUIDispenserHooks != null);
        this.beforeDisplayGUIFurnaceHooks = this.create(ServerPlayerAPI.beforeDisplayGUIFurnaceHookTypes);
        this.overrideDisplayGUIFurnaceHooks = this.create(ServerPlayerAPI.overrideDisplayGUIFurnaceHookTypes);
        this.afterDisplayGUIFurnaceHooks = this.create(ServerPlayerAPI.afterDisplayGUIFurnaceHookTypes);
        this.isDisplayGUIFurnaceModded = (this.beforeDisplayGUIFurnaceHooks != null || this.overrideDisplayGUIFurnaceHooks != null || this.afterDisplayGUIFurnaceHooks != null);
        this.beforeDisplayGUIWorkbenchHooks = this.create(ServerPlayerAPI.beforeDisplayGUIWorkbenchHookTypes);
        this.overrideDisplayGUIWorkbenchHooks = this.create(ServerPlayerAPI.overrideDisplayGUIWorkbenchHookTypes);
        this.afterDisplayGUIWorkbenchHooks = this.create(ServerPlayerAPI.afterDisplayGUIWorkbenchHookTypes);
        this.isDisplayGUIWorkbenchModded = (this.beforeDisplayGUIWorkbenchHooks != null || this.overrideDisplayGUIWorkbenchHooks != null || this.afterDisplayGUIWorkbenchHooks != null);
        this.beforeDropOneItemHooks = this.create(ServerPlayerAPI.beforeDropOneItemHookTypes);
        this.overrideDropOneItemHooks = this.create(ServerPlayerAPI.overrideDropOneItemHookTypes);
        this.afterDropOneItemHooks = this.create(ServerPlayerAPI.afterDropOneItemHookTypes);
        this.isDropOneItemModded = (this.beforeDropOneItemHooks != null || this.overrideDropOneItemHooks != null || this.afterDropOneItemHooks != null);
        this.beforeDropPlayerItemHooks = this.create(ServerPlayerAPI.beforeDropPlayerItemHookTypes);
        this.overrideDropPlayerItemHooks = this.create(ServerPlayerAPI.overrideDropPlayerItemHookTypes);
        this.afterDropPlayerItemHooks = this.create(ServerPlayerAPI.afterDropPlayerItemHookTypes);
        this.isDropPlayerItemModded = (this.beforeDropPlayerItemHooks != null || this.overrideDropPlayerItemHooks != null || this.afterDropPlayerItemHooks != null);
        this.beforeFallHooks = this.create(ServerPlayerAPI.beforeFallHookTypes);
        this.overrideFallHooks = this.create(ServerPlayerAPI.overrideFallHookTypes);
        this.afterFallHooks = this.create(ServerPlayerAPI.afterFallHookTypes);
        this.isFallModded = (this.beforeFallHooks != null || this.overrideFallHooks != null || this.afterFallHooks != null);
        this.beforeGetCurrentPlayerStrVsBlockHooks = this.create(ServerPlayerAPI.beforeGetCurrentPlayerStrVsBlockHookTypes);
        this.overrideGetCurrentPlayerStrVsBlockHooks = this.create(ServerPlayerAPI.overrideGetCurrentPlayerStrVsBlockHookTypes);
        this.afterGetCurrentPlayerStrVsBlockHooks = this.create(ServerPlayerAPI.afterGetCurrentPlayerStrVsBlockHookTypes);
        this.isGetCurrentPlayerStrVsBlockModded = (this.beforeGetCurrentPlayerStrVsBlockHooks != null || this.overrideGetCurrentPlayerStrVsBlockHooks != null || this.afterGetCurrentPlayerStrVsBlockHooks != null);
        this.beforeGetDistanceSqHooks = this.create(ServerPlayerAPI.beforeGetDistanceSqHookTypes);
        this.overrideGetDistanceSqHooks = this.create(ServerPlayerAPI.overrideGetDistanceSqHookTypes);
        this.afterGetDistanceSqHooks = this.create(ServerPlayerAPI.afterGetDistanceSqHookTypes);
        this.isGetDistanceSqModded = (this.beforeGetDistanceSqHooks != null || this.overrideGetDistanceSqHooks != null || this.afterGetDistanceSqHooks != null);
        this.beforeGetBrightnessHooks = this.create(ServerPlayerAPI.beforeGetBrightnessHookTypes);
        this.overrideGetBrightnessHooks = this.create(ServerPlayerAPI.overrideGetBrightnessHookTypes);
        this.afterGetBrightnessHooks = this.create(ServerPlayerAPI.afterGetBrightnessHookTypes);
        this.isGetBrightnessModded = (this.beforeGetBrightnessHooks != null || this.overrideGetBrightnessHooks != null || this.afterGetBrightnessHooks != null);
        this.beforeGetEyeHeightHooks = this.create(ServerPlayerAPI.beforeGetEyeHeightHookTypes);
        this.overrideGetEyeHeightHooks = this.create(ServerPlayerAPI.overrideGetEyeHeightHookTypes);
        this.afterGetEyeHeightHooks = this.create(ServerPlayerAPI.afterGetEyeHeightHookTypes);
        this.isGetEyeHeightModded = (this.beforeGetEyeHeightHooks != null || this.overrideGetEyeHeightHooks != null || this.afterGetEyeHeightHooks != null);
        this.beforeGetMaxHealthHooks = this.create(ServerPlayerAPI.beforeGetMaxHealthHookTypes);
        this.overrideGetMaxHealthHooks = this.create(ServerPlayerAPI.overrideGetMaxHealthHookTypes);
        this.afterGetMaxHealthHooks = this.create(ServerPlayerAPI.afterGetMaxHealthHookTypes);
        this.isGetMaxHealthModded = (this.beforeGetMaxHealthHooks != null || this.overrideGetMaxHealthHooks != null || this.afterGetMaxHealthHooks != null);
        this.beforeGetSpeedModifierHooks = this.create(ServerPlayerAPI.beforeGetSpeedModifierHookTypes);
        this.overrideGetSpeedModifierHooks = this.create(ServerPlayerAPI.overrideGetSpeedModifierHookTypes);
        this.afterGetSpeedModifierHooks = this.create(ServerPlayerAPI.afterGetSpeedModifierHookTypes);
        this.isGetSpeedModifierModded = (this.beforeGetSpeedModifierHooks != null || this.overrideGetSpeedModifierHooks != null || this.afterGetSpeedModifierHooks != null);
        this.beforeHealHooks = this.create(ServerPlayerAPI.beforeHealHookTypes);
        this.overrideHealHooks = this.create(ServerPlayerAPI.overrideHealHookTypes);
        this.afterHealHooks = this.create(ServerPlayerAPI.afterHealHookTypes);
        this.isHealModded = (this.beforeHealHooks != null || this.overrideHealHooks != null || this.afterHealHooks != null);
        this.beforeInteractHooks = this.create(ServerPlayerAPI.beforeInteractHookTypes);
        this.overrideInteractHooks = this.create(ServerPlayerAPI.overrideInteractHookTypes);
        this.afterInteractHooks = this.create(ServerPlayerAPI.afterInteractHookTypes);
        this.isInteractModded = (this.beforeInteractHooks != null || this.overrideInteractHooks != null || this.afterInteractHooks != null);
        this.beforeIsEntityInsideOpaqueBlockHooks = this.create(ServerPlayerAPI.beforeIsEntityInsideOpaqueBlockHookTypes);
        this.overrideIsEntityInsideOpaqueBlockHooks = this.create(ServerPlayerAPI.overrideIsEntityInsideOpaqueBlockHookTypes);
        this.afterIsEntityInsideOpaqueBlockHooks = this.create(ServerPlayerAPI.afterIsEntityInsideOpaqueBlockHookTypes);
        this.isIsEntityInsideOpaqueBlockModded = (this.beforeIsEntityInsideOpaqueBlockHooks != null || this.overrideIsEntityInsideOpaqueBlockHooks != null || this.afterIsEntityInsideOpaqueBlockHooks != null);
        this.beforeIsInWaterHooks = this.create(ServerPlayerAPI.beforeIsInWaterHookTypes);
        this.overrideIsInWaterHooks = this.create(ServerPlayerAPI.overrideIsInWaterHookTypes);
        this.afterIsInWaterHooks = this.create(ServerPlayerAPI.afterIsInWaterHookTypes);
        this.isIsInWaterModded = (this.beforeIsInWaterHooks != null || this.overrideIsInWaterHooks != null || this.afterIsInWaterHooks != null);
        this.beforeIsInsideOfMaterialHooks = this.create(ServerPlayerAPI.beforeIsInsideOfMaterialHookTypes);
        this.overrideIsInsideOfMaterialHooks = this.create(ServerPlayerAPI.overrideIsInsideOfMaterialHookTypes);
        this.afterIsInsideOfMaterialHooks = this.create(ServerPlayerAPI.afterIsInsideOfMaterialHookTypes);
        this.isIsInsideOfMaterialModded = (this.beforeIsInsideOfMaterialHooks != null || this.overrideIsInsideOfMaterialHooks != null || this.afterIsInsideOfMaterialHooks != null);
        this.beforeIsOnLadderHooks = this.create(ServerPlayerAPI.beforeIsOnLadderHookTypes);
        this.overrideIsOnLadderHooks = this.create(ServerPlayerAPI.overrideIsOnLadderHookTypes);
        this.afterIsOnLadderHooks = this.create(ServerPlayerAPI.afterIsOnLadderHookTypes);
        this.isIsOnLadderModded = (this.beforeIsOnLadderHooks != null || this.overrideIsOnLadderHooks != null || this.afterIsOnLadderHooks != null);
        this.beforeIsPlayerSleepingHooks = this.create(ServerPlayerAPI.beforeIsPlayerSleepingHookTypes);
        this.overrideIsPlayerSleepingHooks = this.create(ServerPlayerAPI.overrideIsPlayerSleepingHookTypes);
        this.afterIsPlayerSleepingHooks = this.create(ServerPlayerAPI.afterIsPlayerSleepingHookTypes);
        this.isIsPlayerSleepingModded = (this.beforeIsPlayerSleepingHooks != null || this.overrideIsPlayerSleepingHooks != null || this.afterIsPlayerSleepingHooks != null);
        this.beforeJumpHooks = this.create(ServerPlayerAPI.beforeJumpHookTypes);
        this.overrideJumpHooks = this.create(ServerPlayerAPI.overrideJumpHookTypes);
        this.afterJumpHooks = this.create(ServerPlayerAPI.afterJumpHookTypes);
        this.isJumpModded = (this.beforeJumpHooks != null || this.overrideJumpHooks != null || this.afterJumpHooks != null);
        this.beforeKnockBackHooks = this.create(ServerPlayerAPI.beforeKnockBackHookTypes);
        this.overrideKnockBackHooks = this.create(ServerPlayerAPI.overrideKnockBackHookTypes);
        this.afterKnockBackHooks = this.create(ServerPlayerAPI.afterKnockBackHookTypes);
        this.isKnockBackModded = (this.beforeKnockBackHooks != null || this.overrideKnockBackHooks != null || this.afterKnockBackHooks != null);
        this.beforeMoveEntityHooks = this.create(ServerPlayerAPI.beforeMoveEntityHookTypes);
        this.overrideMoveEntityHooks = this.create(ServerPlayerAPI.overrideMoveEntityHookTypes);
        this.afterMoveEntityHooks = this.create(ServerPlayerAPI.afterMoveEntityHookTypes);
        this.isMoveEntityModded = (this.beforeMoveEntityHooks != null || this.overrideMoveEntityHooks != null || this.afterMoveEntityHooks != null);
        this.beforeMoveEntityWithHeadingHooks = this.create(ServerPlayerAPI.beforeMoveEntityWithHeadingHookTypes);
        this.overrideMoveEntityWithHeadingHooks = this.create(ServerPlayerAPI.overrideMoveEntityWithHeadingHookTypes);
        this.afterMoveEntityWithHeadingHooks = this.create(ServerPlayerAPI.afterMoveEntityWithHeadingHookTypes);
        this.isMoveEntityWithHeadingModded = (this.beforeMoveEntityWithHeadingHooks != null || this.overrideMoveEntityWithHeadingHooks != null || this.afterMoveEntityWithHeadingHooks != null);
        this.beforeMoveFlyingHooks = this.create(ServerPlayerAPI.beforeMoveFlyingHookTypes);
        this.overrideMoveFlyingHooks = this.create(ServerPlayerAPI.overrideMoveFlyingHookTypes);
        this.afterMoveFlyingHooks = this.create(ServerPlayerAPI.afterMoveFlyingHookTypes);
        this.isMoveFlyingModded = (this.beforeMoveFlyingHooks != null || this.overrideMoveFlyingHooks != null || this.afterMoveFlyingHooks != null);
        this.beforeOnDeathHooks = this.create(ServerPlayerAPI.beforeOnDeathHookTypes);
        this.overrideOnDeathHooks = this.create(ServerPlayerAPI.overrideOnDeathHookTypes);
        this.afterOnDeathHooks = this.create(ServerPlayerAPI.afterOnDeathHookTypes);
        this.isOnDeathModded = (this.beforeOnDeathHooks != null || this.overrideOnDeathHooks != null || this.afterOnDeathHooks != null);
        this.beforeOnLivingUpdateHooks = this.create(ServerPlayerAPI.beforeOnLivingUpdateHookTypes);
        this.overrideOnLivingUpdateHooks = this.create(ServerPlayerAPI.overrideOnLivingUpdateHookTypes);
        this.afterOnLivingUpdateHooks = this.create(ServerPlayerAPI.afterOnLivingUpdateHookTypes);
        this.isOnLivingUpdateModded = (this.beforeOnLivingUpdateHooks != null || this.overrideOnLivingUpdateHooks != null || this.afterOnLivingUpdateHooks != null);
        this.beforeOnKillEntityHooks = this.create(ServerPlayerAPI.beforeOnKillEntityHookTypes);
        this.overrideOnKillEntityHooks = this.create(ServerPlayerAPI.overrideOnKillEntityHookTypes);
        this.afterOnKillEntityHooks = this.create(ServerPlayerAPI.afterOnKillEntityHookTypes);
        this.isOnKillEntityModded = (this.beforeOnKillEntityHooks != null || this.overrideOnKillEntityHooks != null || this.afterOnKillEntityHooks != null);
        this.beforeOnStruckByLightningHooks = this.create(ServerPlayerAPI.beforeOnStruckByLightningHookTypes);
        this.overrideOnStruckByLightningHooks = this.create(ServerPlayerAPI.overrideOnStruckByLightningHookTypes);
        this.afterOnStruckByLightningHooks = this.create(ServerPlayerAPI.afterOnStruckByLightningHookTypes);
        this.isOnStruckByLightningModded = (this.beforeOnStruckByLightningHooks != null || this.overrideOnStruckByLightningHooks != null || this.afterOnStruckByLightningHooks != null);
        this.beforeOnUpdateHooks = this.create(ServerPlayerAPI.beforeOnUpdateHookTypes);
        this.overrideOnUpdateHooks = this.create(ServerPlayerAPI.overrideOnUpdateHookTypes);
        this.afterOnUpdateHooks = this.create(ServerPlayerAPI.afterOnUpdateHookTypes);
        this.isOnUpdateModded = (this.beforeOnUpdateHooks != null || this.overrideOnUpdateHooks != null || this.afterOnUpdateHooks != null);
        this.beforeOnUpdateEntityHooks = this.create(ServerPlayerAPI.beforeOnUpdateEntityHookTypes);
        this.overrideOnUpdateEntityHooks = this.create(ServerPlayerAPI.overrideOnUpdateEntityHookTypes);
        this.afterOnUpdateEntityHooks = this.create(ServerPlayerAPI.afterOnUpdateEntityHookTypes);
        this.isOnUpdateEntityModded = (this.beforeOnUpdateEntityHooks != null || this.overrideOnUpdateEntityHooks != null || this.afterOnUpdateEntityHooks != null);
        this.beforeReadEntityFromNBTHooks = this.create(ServerPlayerAPI.beforeReadEntityFromNBTHookTypes);
        this.overrideReadEntityFromNBTHooks = this.create(ServerPlayerAPI.overrideReadEntityFromNBTHookTypes);
        this.afterReadEntityFromNBTHooks = this.create(ServerPlayerAPI.afterReadEntityFromNBTHookTypes);
        this.isReadEntityFromNBTModded = (this.beforeReadEntityFromNBTHooks != null || this.overrideReadEntityFromNBTHooks != null || this.afterReadEntityFromNBTHooks != null);
        this.beforeSetDeadHooks = this.create(ServerPlayerAPI.beforeSetDeadHookTypes);
        this.overrideSetDeadHooks = this.create(ServerPlayerAPI.overrideSetDeadHookTypes);
        this.afterSetDeadHooks = this.create(ServerPlayerAPI.afterSetDeadHookTypes);
        this.isSetDeadModded = (this.beforeSetDeadHooks != null || this.overrideSetDeadHooks != null || this.afterSetDeadHooks != null);
        this.beforeSetPositionHooks = this.create(ServerPlayerAPI.beforeSetPositionHookTypes);
        this.overrideSetPositionHooks = this.create(ServerPlayerAPI.overrideSetPositionHookTypes);
        this.afterSetPositionHooks = this.create(ServerPlayerAPI.afterSetPositionHookTypes);
        this.isSetPositionModded = (this.beforeSetPositionHooks != null || this.overrideSetPositionHooks != null || this.afterSetPositionHooks != null);
        this.beforeSwingItemHooks = this.create(ServerPlayerAPI.beforeSwingItemHookTypes);
        this.overrideSwingItemHooks = this.create(ServerPlayerAPI.overrideSwingItemHookTypes);
        this.afterSwingItemHooks = this.create(ServerPlayerAPI.afterSwingItemHookTypes);
        this.isSwingItemModded = (this.beforeSwingItemHooks != null || this.overrideSwingItemHooks != null || this.afterSwingItemHooks != null);
        this.beforeUpdateEntityActionStateHooks = this.create(ServerPlayerAPI.beforeUpdateEntityActionStateHookTypes);
        this.overrideUpdateEntityActionStateHooks = this.create(ServerPlayerAPI.overrideUpdateEntityActionStateHookTypes);
        this.afterUpdateEntityActionStateHooks = this.create(ServerPlayerAPI.afterUpdateEntityActionStateHookTypes);
        this.isUpdateEntityActionStateModded = (this.beforeUpdateEntityActionStateHooks != null || this.overrideUpdateEntityActionStateHooks != null || this.afterUpdateEntityActionStateHooks != null);
        this.beforeUpdatePotionEffectsHooks = this.create(ServerPlayerAPI.beforeUpdatePotionEffectsHookTypes);
        this.overrideUpdatePotionEffectsHooks = this.create(ServerPlayerAPI.overrideUpdatePotionEffectsHookTypes);
        this.afterUpdatePotionEffectsHooks = this.create(ServerPlayerAPI.afterUpdatePotionEffectsHookTypes);
        this.isUpdatePotionEffectsModded = (this.beforeUpdatePotionEffectsHooks != null || this.overrideUpdatePotionEffectsHooks != null || this.afterUpdatePotionEffectsHooks != null);
        this.beforeWriteEntityToNBTHooks = this.create(ServerPlayerAPI.beforeWriteEntityToNBTHookTypes);
        this.overrideWriteEntityToNBTHooks = this.create(ServerPlayerAPI.overrideWriteEntityToNBTHookTypes);
        this.afterWriteEntityToNBTHooks = this.create(ServerPlayerAPI.afterWriteEntityToNBTHookTypes);
        this.isWriteEntityToNBTModded = (this.beforeWriteEntityToNBTHooks != null || this.overrideWriteEntityToNBTHooks != null || this.afterWriteEntityToNBTHooks != null);
    }
    
    private void attachServerPlayerBase(final String s) {
        ServerPlayerAPI.initializer[0] = this;
        ServerPlayerAPI.initializers[0] = this;
        final ServerPlayerBase serverPlayerBase = this.createServerPlayerBase(s);
        serverPlayerBase.beforeBaseAttach(true);
        this.allBaseObjects.put(s, serverPlayerBase);
        this.updateServerPlayerBases();
        serverPlayerBase.afterBaseAttach(true);
    }
    
    private void detachServerPlayerBase(final String s) {
        final ServerPlayerBase serverPlayerBase = this.allBaseObjects.get(s);
        serverPlayerBase.beforeBaseDetach(true);
        this.allBaseObjects.remove(s);
        this.updateServerPlayerBases();
        serverPlayerBase.afterBaseDetach(true);
    }
    
    private ServerPlayerBase[] create(final List<String> list) {
        if (list.isEmpty()) {
            return null;
        }
        final ServerPlayerBase[] array = new ServerPlayerBase[list.size()];
        for (int i = 0; i < array.length; ++i) {
            array[i] = this.getServerPlayerBase(list.get(i));
        }
        return array;
    }
    
    private void beforeLocalConstructing(final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
        if (this.beforeLocalConstructingHooks != null) {
            for (int i = this.beforeLocalConstructingHooks.length - 1; i >= 0; --i) {
                this.beforeLocalConstructingHooks[i].beforeLocalConstructing(minecraftServer, world, s, playerInteractManager);
            }
        }
        this.beforeLocalConstructingHooks = null;
    }
    
    private void afterLocalConstructing(final MinecraftServer minecraftServer, final World world, final String s, final PlayerInteractManager playerInteractManager) {
        if (this.afterLocalConstructingHooks != null) {
            for (int i = 0; i < this.afterLocalConstructingHooks.length; ++i) {
                this.afterLocalConstructingHooks[i].afterLocalConstructing(minecraftServer, world, s, playerInteractManager);
            }
        }
        this.afterLocalConstructingHooks = null;
    }
    
    public ServerPlayerBase getServerPlayerBase(final String s) {
        return this.allBaseObjects.get(s);
    }
    
    public Set<String> getServerPlayerBaseIds() {
        return this.unmodifiableAllBaseIds;
    }
    
    public Object dynamic(String replace, final Object[] array) {
        replace = replace.replace('.', '_').replace(' ', '_');
        this.executeAll(replace, array, ServerPlayerAPI.beforeDynamicHookTypes, ServerPlayerAPI.beforeDynamicHookMethods, true);
        final Object dynamicOverwritten = this.dynamicOverwritten(replace, array, null);
        this.executeAll(replace, array, ServerPlayerAPI.afterDynamicHookTypes, ServerPlayerAPI.afterDynamicHookMethods, false);
        return dynamicOverwritten;
    }
    
    public Object dynamicOverwritten(final String s, final Object[] array, final ServerPlayerBase serverPlayerBase) {
        final List<String> list = ServerPlayerAPI.overrideDynamicHookTypes.get(s);
        String s2 = null;
        if (list != null) {
            if (serverPlayerBase != null) {
                final int index = list.indexOf(this.baseObjectsToId.get(serverPlayerBase));
                if (index > 0) {
                    s2 = list.get(index - 1);
                }
                else {
                    s2 = null;
                }
            }
            else if (list.size() > 0) {
                s2 = list.get(list.size() - 1);
            }
        }
        Map<Class<?>, Map<String, Method>> map;
        if (s2 == null) {
            s2 = ServerPlayerAPI.keysToVirtualIds.get(s);
            if (s2 == null) {
                return null;
            }
            map = ServerPlayerAPI.virtualDynamicHookMethods;
        }
        else {
            map = ServerPlayerAPI.overrideDynamicHookMethods;
        }
        final Map<String, Method> map2 = map.get(ServerPlayerAPI.allBaseConstructors.get(s2).getDeclaringClass());
        if (map2 == null) {
            return null;
        }
        final Method method = map2.get(s);
        if (map2 == null) {
            return null;
        }
        return this.execute(this.getServerPlayerBase(s2), method, array);
    }
    
    private void executeAll(final String s, final Object[] array, final Map<String, List<String>> map, final Map<Class<?>, Map<String, Method>> map2, final boolean b) {
        final List<String> list = map.get(s);
        if (list == null) {
            return;
        }
        int n = b ? (list.size() - 1) : 0;
        while (true) {
            if (b) {
                if (n < 0) {
                    break;
                }
            }
            else if (n >= list.size()) {
                break;
            }
            final ServerPlayerBase serverPlayerBase = this.getServerPlayerBase(list.get(n));
            final Map<String, Method> map3 = map2.get(serverPlayerBase.getClass());
            if (map3 != null) {
                final Method method = map3.get(s);
                if (method != null) {
                    this.execute(serverPlayerBase, method, array);
                }
            }
            n += (b ? -1 : 1);
        }
    }
    
    private Object execute(final ServerPlayerBase serverPlayerBase, final Method method, final Object[] array) {
        try {
            return method.invoke(serverPlayerBase, array);
        }
        catch (Exception ex) {
            throw new RuntimeException("Exception while invoking dynamic method", ex);
        }
    }
    
    public static void addExhaustion(final EntityPlayer entityPlayer, final float n) {
        if (entityPlayer.serverPlayerAPI != null) {
            ((ServerPlayerAPI) entityPlayer.serverPlayerAPI).addExhaustion(n);
        }
        else {
            entityPlayer.localAddExhaustion(n);
        }
    }
    
    private void addExhaustion(final float n) {
        if (this.beforeAddExhaustionHooks != null) {
            for (int i = this.beforeAddExhaustionHooks.length - 1; i >= 0; --i) {
                this.beforeAddExhaustionHooks[i].beforeAddExhaustion(n);
            }
        }
        if (this.overrideAddExhaustionHooks != null) {
            this.overrideAddExhaustionHooks[this.overrideAddExhaustionHooks.length - 1].addExhaustion(n);
        }
        else {
            this.player.localAddExhaustion(n);
        }
        if (this.afterAddExhaustionHooks != null) {
            for (int j = 0; j < this.afterAddExhaustionHooks.length; ++j) {
                this.afterAddExhaustionHooks[j].afterAddExhaustion(n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenAddExhaustion(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAddExhaustionHooks.length) {
            if (this.overrideAddExhaustionHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAddExhaustionHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void addExperience(final EntityPlayer entityPlayer, final int n) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.addExperience(n);
        }
        else {
            entityPlayer.localAddExperience(n);
        }
    }
    
    private void addExperience(final int n) {
        if (this.beforeAddExperienceHooks != null) {
            for (int i = this.beforeAddExperienceHooks.length - 1; i >= 0; --i) {
                this.beforeAddExperienceHooks[i].beforeAddExperience(n);
            }
        }
        if (this.overrideAddExperienceHooks != null) {
            this.overrideAddExperienceHooks[this.overrideAddExperienceHooks.length - 1].addExperience(n);
        }
        else {
            this.player.localAddExperience(n);
        }
        if (this.afterAddExperienceHooks != null) {
            for (int j = 0; j < this.afterAddExperienceHooks.length; ++j) {
                this.afterAddExperienceHooks[j].afterAddExperience(n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenAddExperience(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAddExperienceHooks.length) {
            if (this.overrideAddExperienceHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAddExperienceHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void addExperienceLevel(final EntityPlayer entityPlayer, final int n) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.addExperienceLevel(n);
        }
        else {
            entityPlayer.localAddExperienceLevel(n);
        }
    }
    
    private void addExperienceLevel(final int n) {
        if (this.beforeAddExperienceLevelHooks != null) {
            for (int i = this.beforeAddExperienceLevelHooks.length - 1; i >= 0; --i) {
                this.beforeAddExperienceLevelHooks[i].beforeAddExperienceLevel(n);
            }
        }
        if (this.overrideAddExperienceLevelHooks != null) {
            this.overrideAddExperienceLevelHooks[this.overrideAddExperienceLevelHooks.length - 1].addExperienceLevel(n);
        }
        else {
            this.player.localAddExperienceLevel(n);
        }
        if (this.afterAddExperienceLevelHooks != null) {
            for (int j = 0; j < this.afterAddExperienceLevelHooks.length; ++j) {
                this.afterAddExperienceLevelHooks[j].afterAddExperienceLevel(n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenAddExperienceLevel(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAddExperienceLevelHooks.length) {
            if (this.overrideAddExperienceLevelHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAddExperienceLevelHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void addMovementStat(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.addMovementStat(n, n2, n3);
        }
        else {
            entityPlayer.localAddMovementStat(n, n2, n3);
        }
    }
    
    private void addMovementStat(final double n, final double n2, final double n3) {
        if (this.beforeAddMovementStatHooks != null) {
            for (int i = this.beforeAddMovementStatHooks.length - 1; i >= 0; --i) {
                this.beforeAddMovementStatHooks[i].beforeAddMovementStat(n, n2, n3);
            }
        }
        if (this.overrideAddMovementStatHooks != null) {
            this.overrideAddMovementStatHooks[this.overrideAddMovementStatHooks.length - 1].addMovementStat(n, n2, n3);
        }
        else {
            this.player.localAddMovementStat(n, n2, n3);
        }
        if (this.afterAddMovementStatHooks != null) {
            for (int j = 0; j < this.afterAddMovementStatHooks.length; ++j) {
                this.afterAddMovementStatHooks[j].afterAddMovementStat(n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenAddMovementStat(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAddMovementStatHooks.length) {
            if (this.overrideAddMovementStatHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAddMovementStatHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean attackEntityFrom(final EntityPlayer entityPlayer, final DamageSource damageSource, final int n) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.attackEntityFrom(damageSource, n);
        }
        else {
            b = entityPlayer.localAttackEntityFrom(damageSource, n);
        }
        return b;
    }
    
    private boolean attackEntityFrom(final DamageSource damageSource, final int n) {
        if (this.beforeAttackEntityFromHooks != null) {
            for (int i = this.beforeAttackEntityFromHooks.length - 1; i >= 0; --i) {
                this.beforeAttackEntityFromHooks[i].beforeAttackEntityFrom(damageSource, n);
            }
        }
        boolean b;
        if (this.overrideAttackEntityFromHooks != null) {
            b = this.overrideAttackEntityFromHooks[this.overrideAttackEntityFromHooks.length - 1].attackEntityFrom(damageSource, n);
        }
        else {
            b = this.player.localAttackEntityFrom(damageSource, n);
        }
        if (this.afterAttackEntityFromHooks != null) {
            for (int j = 0; j < this.afterAttackEntityFromHooks.length; ++j) {
                this.afterAttackEntityFromHooks[j].afterAttackEntityFrom(damageSource, n);
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenAttackEntityFrom(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAttackEntityFromHooks.length) {
            if (this.overrideAttackEntityFromHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAttackEntityFromHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void attackTargetEntityWithCurrentItem(final EntityPlayer entityPlayer, final Entity entity) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.attackTargetEntityWithCurrentItem(entity);
        }
        else {
            entityPlayer.localAttackTargetEntityWithCurrentItem(entity);
        }
    }
    
    private void attackTargetEntityWithCurrentItem(final Entity entity) {
        if (this.beforeAttackTargetEntityWithCurrentItemHooks != null) {
            for (int i = this.beforeAttackTargetEntityWithCurrentItemHooks.length - 1; i >= 0; --i) {
                this.beforeAttackTargetEntityWithCurrentItemHooks[i].beforeAttackTargetEntityWithCurrentItem(entity);
            }
        }
        if (this.overrideAttackTargetEntityWithCurrentItemHooks != null) {
            this.overrideAttackTargetEntityWithCurrentItemHooks[this.overrideAttackTargetEntityWithCurrentItemHooks.length - 1].attackTargetEntityWithCurrentItem(entity);
        }
        else {
            this.player.localAttackTargetEntityWithCurrentItem(entity);
        }
        if (this.afterAttackTargetEntityWithCurrentItemHooks != null) {
            for (int j = 0; j < this.afterAttackTargetEntityWithCurrentItemHooks.length; ++j) {
                this.afterAttackTargetEntityWithCurrentItemHooks[j].afterAttackTargetEntityWithCurrentItem(entity);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenAttackTargetEntityWithCurrentItem(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideAttackTargetEntityWithCurrentItemHooks.length) {
            if (this.overrideAttackTargetEntityWithCurrentItemHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideAttackTargetEntityWithCurrentItemHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean canHarvestBlock(final EntityPlayer entityPlayer, final Block block) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.canHarvestBlock(block);
        }
        else {
            b = entityPlayer.localCanHarvestBlock(block);
        }
        return b;
    }
    
    private boolean canHarvestBlock(final Block block) {
        if (this.beforeCanHarvestBlockHooks != null) {
            for (int i = this.beforeCanHarvestBlockHooks.length - 1; i >= 0; --i) {
                this.beforeCanHarvestBlockHooks[i].beforeCanHarvestBlock(block);
            }
        }
        boolean b;
        if (this.overrideCanHarvestBlockHooks != null) {
            b = this.overrideCanHarvestBlockHooks[this.overrideCanHarvestBlockHooks.length - 1].canHarvestBlock(block);
        }
        else {
            b = this.player.localCanHarvestBlock(block);
        }
        if (this.afterCanHarvestBlockHooks != null) {
            for (int j = 0; j < this.afterCanHarvestBlockHooks.length; ++j) {
                this.afterCanHarvestBlockHooks[j].afterCanHarvestBlock(block);
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenCanHarvestBlock(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideCanHarvestBlockHooks.length) {
            if (this.overrideCanHarvestBlockHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideCanHarvestBlockHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean canPlayerEdit(final EntityPlayer entityPlayer, final int n, final int n2, final int n3, final int n4, final ItemStack itemStack) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.canPlayerEdit(n, n2, n3, n4, itemStack);
        }
        else {
            b = entityPlayer.localCanPlayerEdit(n, n2, n3, n4, itemStack);
        }
        return b;
    }
    
    private boolean canPlayerEdit(final int n, final int n2, final int n3, final int n4, final ItemStack itemStack) {
        if (this.beforeCanPlayerEditHooks != null) {
            for (int i = this.beforeCanPlayerEditHooks.length - 1; i >= 0; --i) {
                this.beforeCanPlayerEditHooks[i].beforeCanPlayerEdit(n, n2, n3, n4, itemStack);
            }
        }
        boolean b;
        if (this.overrideCanPlayerEditHooks != null) {
            b = this.overrideCanPlayerEditHooks[this.overrideCanPlayerEditHooks.length - 1].canPlayerEdit(n, n2, n3, n4, itemStack);
        }
        else {
            b = this.player.localCanPlayerEdit(n, n2, n3, n4, itemStack);
        }
        if (this.afterCanPlayerEditHooks != null) {
            for (int j = 0; j < this.afterCanPlayerEditHooks.length; ++j) {
                this.afterCanPlayerEditHooks[j].afterCanPlayerEdit(n, n2, n3, n4, itemStack);
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenCanPlayerEdit(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideCanPlayerEditHooks.length) {
            if (this.overrideCanPlayerEditHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideCanPlayerEditHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean canTriggerWalking(final EntityPlayer entityPlayer) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.canTriggerWalking();
        }
        else {
            b = entityPlayer.localCanTriggerWalking();
        }
        return b;
    }
    
    private boolean canTriggerWalking() {
        if (this.beforeCanTriggerWalkingHooks != null) {
            for (int i = this.beforeCanTriggerWalkingHooks.length - 1; i >= 0; --i) {
                this.beforeCanTriggerWalkingHooks[i].beforeCanTriggerWalking();
            }
        }
        boolean b;
        if (this.overrideCanTriggerWalkingHooks != null) {
            b = this.overrideCanTriggerWalkingHooks[this.overrideCanTriggerWalkingHooks.length - 1].canTriggerWalking();
        }
        else {
            b = this.player.localCanTriggerWalking();
        }
        if (this.afterCanTriggerWalkingHooks != null) {
            for (int j = 0; j < this.afterCanTriggerWalkingHooks.length; ++j) {
                this.afterCanTriggerWalkingHooks[j].afterCanTriggerWalking();
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenCanTriggerWalking(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideCanTriggerWalkingHooks.length) {
            if (this.overrideCanTriggerWalkingHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideCanTriggerWalkingHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void clonePlayer(final EntityPlayer entityPlayer, final EntityHuman entityHuman, final boolean b) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.clonePlayer(entityHuman, b);
        }
        else {
            entityPlayer.localClonePlayer(entityHuman, b);
        }
    }
    
    private void clonePlayer(final EntityHuman entityHuman, final boolean b) {
        if (this.beforeClonePlayerHooks != null) {
            for (int i = this.beforeClonePlayerHooks.length - 1; i >= 0; --i) {
                this.beforeClonePlayerHooks[i].beforeClonePlayer(entityHuman, b);
            }
        }
        if (this.overrideClonePlayerHooks != null) {
            this.overrideClonePlayerHooks[this.overrideClonePlayerHooks.length - 1].clonePlayer(entityHuman, b);
        }
        else {
            this.player.localClonePlayer(entityHuman, b);
        }
        if (this.afterClonePlayerHooks != null) {
            for (int j = 0; j < this.afterClonePlayerHooks.length; ++j) {
                this.afterClonePlayerHooks[j].afterClonePlayer(entityHuman, b);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenClonePlayer(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideClonePlayerHooks.length) {
            if (this.overrideClonePlayerHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideClonePlayerHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void damageEntity(final EntityPlayer entityPlayer, final DamageSource damageSource, final int n) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.damageEntity(damageSource, n);
        }
        else {
            entityPlayer.localDamageEntity(damageSource, n);
        }
    }
    
    private void damageEntity(final DamageSource damageSource, final int n) {
        if (this.beforeDamageEntityHooks != null) {
            for (int i = this.beforeDamageEntityHooks.length - 1; i >= 0; --i) {
                this.beforeDamageEntityHooks[i].beforeDamageEntity(damageSource, n);
            }
        }
        if (this.overrideDamageEntityHooks != null) {
            this.overrideDamageEntityHooks[this.overrideDamageEntityHooks.length - 1].damageEntity(damageSource, n);
        }
        else {
            this.player.localDamageEntity(damageSource, n);
        }
        if (this.afterDamageEntityHooks != null) {
            for (int j = 0; j < this.afterDamageEntityHooks.length; ++j) {
                this.afterDamageEntityHooks[j].afterDamageEntity(damageSource, n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenDamageEntity(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDamageEntityHooks.length) {
            if (this.overrideDamageEntityHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDamageEntityHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void displayGUIChest(final EntityPlayer entityPlayer, final IInventory inventory) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.displayGUIChest(inventory);
        }
        else {
            entityPlayer.localDisplayGUIChest(inventory);
        }
    }
    
    private void displayGUIChest(final IInventory inventory) {
        if (this.beforeDisplayGUIChestHooks != null) {
            for (int i = this.beforeDisplayGUIChestHooks.length - 1; i >= 0; --i) {
                this.beforeDisplayGUIChestHooks[i].beforeDisplayGUIChest(inventory);
            }
        }
        if (this.overrideDisplayGUIChestHooks != null) {
            this.overrideDisplayGUIChestHooks[this.overrideDisplayGUIChestHooks.length - 1].displayGUIChest(inventory);
        }
        else {
            this.player.localDisplayGUIChest(inventory);
        }
        if (this.afterDisplayGUIChestHooks != null) {
            for (int j = 0; j < this.afterDisplayGUIChestHooks.length; ++j) {
                this.afterDisplayGUIChestHooks[j].afterDisplayGUIChest(inventory);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenDisplayGUIChest(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDisplayGUIChestHooks.length) {
            if (this.overrideDisplayGUIChestHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDisplayGUIChestHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void displayGUIDispenser(final EntityPlayer entityPlayer, final TileEntityDispenser tileEntityDispenser) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.displayGUIDispenser(tileEntityDispenser);
        }
        else {
            entityPlayer.localDisplayGUIDispenser(tileEntityDispenser);
        }
    }
    
    private void displayGUIDispenser(final TileEntityDispenser tileEntityDispenser) {
        if (this.beforeDisplayGUIDispenserHooks != null) {
            for (int i = this.beforeDisplayGUIDispenserHooks.length - 1; i >= 0; --i) {
                this.beforeDisplayGUIDispenserHooks[i].beforeDisplayGUIDispenser(tileEntityDispenser);
            }
        }
        if (this.overrideDisplayGUIDispenserHooks != null) {
            this.overrideDisplayGUIDispenserHooks[this.overrideDisplayGUIDispenserHooks.length - 1].displayGUIDispenser(tileEntityDispenser);
        }
        else {
            this.player.localDisplayGUIDispenser(tileEntityDispenser);
        }
        if (this.afterDisplayGUIDispenserHooks != null) {
            for (int j = 0; j < this.afterDisplayGUIDispenserHooks.length; ++j) {
                this.afterDisplayGUIDispenserHooks[j].afterDisplayGUIDispenser(tileEntityDispenser);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenDisplayGUIDispenser(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDisplayGUIDispenserHooks.length) {
            if (this.overrideDisplayGUIDispenserHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDisplayGUIDispenserHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void displayGUIFurnace(final EntityPlayer entityPlayer, final TileEntityFurnace tileEntityFurnace) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.displayGUIFurnace(tileEntityFurnace);
        }
        else {
            entityPlayer.localDisplayGUIFurnace(tileEntityFurnace);
        }
    }
    
    private void displayGUIFurnace(final TileEntityFurnace tileEntityFurnace) {
        if (this.beforeDisplayGUIFurnaceHooks != null) {
            for (int i = this.beforeDisplayGUIFurnaceHooks.length - 1; i >= 0; --i) {
                this.beforeDisplayGUIFurnaceHooks[i].beforeDisplayGUIFurnace(tileEntityFurnace);
            }
        }
        if (this.overrideDisplayGUIFurnaceHooks != null) {
            this.overrideDisplayGUIFurnaceHooks[this.overrideDisplayGUIFurnaceHooks.length - 1].displayGUIFurnace(tileEntityFurnace);
        }
        else {
            this.player.localDisplayGUIFurnace(tileEntityFurnace);
        }
        if (this.afterDisplayGUIFurnaceHooks != null) {
            for (int j = 0; j < this.afterDisplayGUIFurnaceHooks.length; ++j) {
                this.afterDisplayGUIFurnaceHooks[j].afterDisplayGUIFurnace(tileEntityFurnace);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenDisplayGUIFurnace(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDisplayGUIFurnaceHooks.length) {
            if (this.overrideDisplayGUIFurnaceHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDisplayGUIFurnaceHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void displayGUIWorkbench(final EntityPlayer entityPlayer, final int n, final int n2, final int n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.displayGUIWorkbench(n, n2, n3);
        }
        else {
            entityPlayer.localDisplayGUIWorkbench(n, n2, n3);
        }
    }
    
    private void displayGUIWorkbench(final int n, final int n2, final int n3) {
        if (this.beforeDisplayGUIWorkbenchHooks != null) {
            for (int i = this.beforeDisplayGUIWorkbenchHooks.length - 1; i >= 0; --i) {
                this.beforeDisplayGUIWorkbenchHooks[i].beforeDisplayGUIWorkbench(n, n2, n3);
            }
        }
        if (this.overrideDisplayGUIWorkbenchHooks != null) {
            this.overrideDisplayGUIWorkbenchHooks[this.overrideDisplayGUIWorkbenchHooks.length - 1].displayGUIWorkbench(n, n2, n3);
        }
        else {
            this.player.localDisplayGUIWorkbench(n, n2, n3);
        }
        if (this.afterDisplayGUIWorkbenchHooks != null) {
            for (int j = 0; j < this.afterDisplayGUIWorkbenchHooks.length; ++j) {
                this.afterDisplayGUIWorkbenchHooks[j].afterDisplayGUIWorkbench(n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenDisplayGUIWorkbench(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDisplayGUIWorkbenchHooks.length) {
            if (this.overrideDisplayGUIWorkbenchHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDisplayGUIWorkbenchHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static EntityItem dropOneItem(final EntityPlayer entityPlayer, final boolean b) {
        EntityItem entityItem;
        if (entityPlayer.serverPlayerAPI != null) {
            entityItem = entityPlayer.serverPlayerAPI.dropOneItem(b);
        }
        else {
            entityItem = entityPlayer.localDropOneItem(b);
        }
        return entityItem;
    }
    
    private EntityItem dropOneItem(final boolean b) {
        if (this.beforeDropOneItemHooks != null) {
            for (int i = this.beforeDropOneItemHooks.length - 1; i >= 0; --i) {
                this.beforeDropOneItemHooks[i].beforeDropOneItem(b);
            }
        }
        EntityItem entityItem;
        if (this.overrideDropOneItemHooks != null) {
            entityItem = this.overrideDropOneItemHooks[this.overrideDropOneItemHooks.length - 1].dropOneItem(b);
        }
        else {
            entityItem = this.player.localDropOneItem(b);
        }
        if (this.afterDropOneItemHooks != null) {
            for (int j = 0; j < this.afterDropOneItemHooks.length; ++j) {
                this.afterDropOneItemHooks[j].afterDropOneItem(b);
            }
        }
        return entityItem;
    }
    
    protected ServerPlayerBase GetOverwrittenDropOneItem(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDropOneItemHooks.length) {
            if (this.overrideDropOneItemHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDropOneItemHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static EntityItem dropPlayerItem(final EntityPlayer entityPlayer, final ItemStack itemStack) {
        EntityItem entityItem;
        if (entityPlayer.serverPlayerAPI != null) {
            entityItem = entityPlayer.serverPlayerAPI.dropPlayerItem(itemStack);
        }
        else {
            entityItem = entityPlayer.localDropPlayerItem(itemStack);
        }
        return entityItem;
    }
    
    private EntityItem dropPlayerItem(final ItemStack itemStack) {
        if (this.beforeDropPlayerItemHooks != null) {
            for (int i = this.beforeDropPlayerItemHooks.length - 1; i >= 0; --i) {
                this.beforeDropPlayerItemHooks[i].beforeDropPlayerItem(itemStack);
            }
        }
        EntityItem entityItem;
        if (this.overrideDropPlayerItemHooks != null) {
            entityItem = this.overrideDropPlayerItemHooks[this.overrideDropPlayerItemHooks.length - 1].dropPlayerItem(itemStack);
        }
        else {
            entityItem = this.player.localDropPlayerItem(itemStack);
        }
        if (this.afterDropPlayerItemHooks != null) {
            for (int j = 0; j < this.afterDropPlayerItemHooks.length; ++j) {
                this.afterDropPlayerItemHooks[j].afterDropPlayerItem(itemStack);
            }
        }
        return entityItem;
    }
    
    protected ServerPlayerBase GetOverwrittenDropPlayerItem(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideDropPlayerItemHooks.length) {
            if (this.overrideDropPlayerItemHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideDropPlayerItemHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void fall(final EntityPlayer entityPlayer, final float n) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.fall(n);
        }
        else {
            entityPlayer.localFall(n);
        }
    }
    
    private void fall(final float n) {
        if (this.beforeFallHooks != null) {
            for (int i = this.beforeFallHooks.length - 1; i >= 0; --i) {
                this.beforeFallHooks[i].beforeFall(n);
            }
        }
        if (this.overrideFallHooks != null) {
            this.overrideFallHooks[this.overrideFallHooks.length - 1].fall(n);
        }
        else {
            this.player.localFall(n);
        }
        if (this.afterFallHooks != null) {
            for (int j = 0; j < this.afterFallHooks.length; ++j) {
                this.afterFallHooks[j].afterFall(n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenFall(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideFallHooks.length) {
            if (this.overrideFallHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideFallHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static float getCurrentPlayerStrVsBlock(final EntityPlayer entityPlayer, final Block block, final boolean b) {
        float n;
        if (entityPlayer.serverPlayerAPI != null) {
            n = entityPlayer.serverPlayerAPI.getCurrentPlayerStrVsBlock(block, b);
        }
        else {
            n = entityPlayer.localGetCurrentPlayerStrVsBlock(block, b);
        }
        return n;
    }
    
    private float getCurrentPlayerStrVsBlock(final Block block, final boolean b) {
        if (this.beforeGetCurrentPlayerStrVsBlockHooks != null) {
            for (int i = this.beforeGetCurrentPlayerStrVsBlockHooks.length - 1; i >= 0; --i) {
                this.beforeGetCurrentPlayerStrVsBlockHooks[i].beforeGetCurrentPlayerStrVsBlock(block, b);
            }
        }
        float n;
        if (this.overrideGetCurrentPlayerStrVsBlockHooks != null) {
            n = this.overrideGetCurrentPlayerStrVsBlockHooks[this.overrideGetCurrentPlayerStrVsBlockHooks.length - 1].getCurrentPlayerStrVsBlock(block, b);
        }
        else {
            n = this.player.localGetCurrentPlayerStrVsBlock(block, b);
        }
        if (this.afterGetCurrentPlayerStrVsBlockHooks != null) {
            for (int j = 0; j < this.afterGetCurrentPlayerStrVsBlockHooks.length; ++j) {
                this.afterGetCurrentPlayerStrVsBlockHooks[j].afterGetCurrentPlayerStrVsBlock(block, b);
            }
        }
        return n;
    }
    
    protected ServerPlayerBase GetOverwrittenGetCurrentPlayerStrVsBlock(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetCurrentPlayerStrVsBlockHooks.length) {
            if (this.overrideGetCurrentPlayerStrVsBlockHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetCurrentPlayerStrVsBlockHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static double getDistanceSq(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        double n4;
        if (entityPlayer.serverPlayerAPI != null) {
            n4 = entityPlayer.serverPlayerAPI.getDistanceSq(n, n2, n3);
        }
        else {
            n4 = entityPlayer.localGetDistanceSq(n, n2, n3);
        }
        return n4;
    }
    
    private double getDistanceSq(final double n, final double n2, final double n3) {
        if (this.beforeGetDistanceSqHooks != null) {
            for (int i = this.beforeGetDistanceSqHooks.length - 1; i >= 0; --i) {
                this.beforeGetDistanceSqHooks[i].beforeGetDistanceSq(n, n2, n3);
            }
        }
        double n4;
        if (this.overrideGetDistanceSqHooks != null) {
            n4 = this.overrideGetDistanceSqHooks[this.overrideGetDistanceSqHooks.length - 1].getDistanceSq(n, n2, n3);
        }
        else {
            n4 = this.player.localGetDistanceSq(n, n2, n3);
        }
        if (this.afterGetDistanceSqHooks != null) {
            for (int j = 0; j < this.afterGetDistanceSqHooks.length; ++j) {
                this.afterGetDistanceSqHooks[j].afterGetDistanceSq(n, n2, n3);
            }
        }
        return n4;
    }
    
    protected ServerPlayerBase GetOverwrittenGetDistanceSq(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetDistanceSqHooks.length) {
            if (this.overrideGetDistanceSqHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetDistanceSqHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static float getBrightness(final EntityPlayer entityPlayer, final float n) {
        float n2;
        if (entityPlayer.serverPlayerAPI != null) {
            n2 = entityPlayer.serverPlayerAPI.getBrightness(n);
        }
        else {
            n2 = entityPlayer.localGetBrightness(n);
        }
        return n2;
    }
    
    private float getBrightness(final float n) {
        if (this.beforeGetBrightnessHooks != null) {
            for (int i = this.beforeGetBrightnessHooks.length - 1; i >= 0; --i) {
                this.beforeGetBrightnessHooks[i].beforeGetBrightness(n);
            }
        }
        float n2;
        if (this.overrideGetBrightnessHooks != null) {
            n2 = this.overrideGetBrightnessHooks[this.overrideGetBrightnessHooks.length - 1].getBrightness(n);
        }
        else {
            n2 = this.player.localGetBrightness(n);
        }
        if (this.afterGetBrightnessHooks != null) {
            for (int j = 0; j < this.afterGetBrightnessHooks.length; ++j) {
                this.afterGetBrightnessHooks[j].afterGetBrightness(n);
            }
        }
        return n2;
    }
    
    protected ServerPlayerBase GetOverwrittenGetBrightness(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetBrightnessHooks.length) {
            if (this.overrideGetBrightnessHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetBrightnessHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static float getEyeHeight(final EntityPlayer entityPlayer) {
        float n;
        if (entityPlayer.serverPlayerAPI != null) {
            n = entityPlayer.serverPlayerAPI.getEyeHeight();
        }
        else {
            n = entityPlayer.localGetEyeHeight();
        }
        return n;
    }
    
    private float getEyeHeight() {
        if (this.beforeGetEyeHeightHooks != null) {
            for (int i = this.beforeGetEyeHeightHooks.length - 1; i >= 0; --i) {
                this.beforeGetEyeHeightHooks[i].beforeGetEyeHeight();
            }
        }
        float n;
        if (this.overrideGetEyeHeightHooks != null) {
            n = this.overrideGetEyeHeightHooks[this.overrideGetEyeHeightHooks.length - 1].getEyeHeight();
        }
        else {
            n = this.player.localGetEyeHeight();
        }
        if (this.afterGetEyeHeightHooks != null) {
            for (int j = 0; j < this.afterGetEyeHeightHooks.length; ++j) {
                this.afterGetEyeHeightHooks[j].afterGetEyeHeight();
            }
        }
        return n;
    }
    
    protected ServerPlayerBase GetOverwrittenGetEyeHeight(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetEyeHeightHooks.length) {
            if (this.overrideGetEyeHeightHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetEyeHeightHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static int getMaxHealth(final EntityPlayer entityPlayer) {
        int n;
        if (entityPlayer.serverPlayerAPI != null) {
            n = entityPlayer.serverPlayerAPI.getMaxHealth();
        }
        else {
            n = entityPlayer.localGetMaxHealth();
        }
        return n;
    }
    
    private int getMaxHealth() {
        if (this.beforeGetMaxHealthHooks != null) {
            for (int i = this.beforeGetMaxHealthHooks.length - 1; i >= 0; --i) {
                this.beforeGetMaxHealthHooks[i].beforeGetMaxHealth();
            }
        }
        int n;
        if (this.overrideGetMaxHealthHooks != null) {
            n = this.overrideGetMaxHealthHooks[this.overrideGetMaxHealthHooks.length - 1].getMaxHealth();
        }
        else {
            n = this.player.localGetMaxHealth();
        }
        if (this.afterGetMaxHealthHooks != null) {
            for (int j = 0; j < this.afterGetMaxHealthHooks.length; ++j) {
                this.afterGetMaxHealthHooks[j].afterGetMaxHealth();
            }
        }
        return n;
    }
    
    protected ServerPlayerBase GetOverwrittenGetMaxHealth(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetMaxHealthHooks.length) {
            if (this.overrideGetMaxHealthHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetMaxHealthHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static float getSpeedModifier(final EntityPlayer entityPlayer) {
        float n;
        if (entityPlayer.serverPlayerAPI != null) {
            n = entityPlayer.serverPlayerAPI.getSpeedModifier();
        }
        else {
            n = entityPlayer.localGetSpeedModifier();
        }
        return n;
    }
    
    private float getSpeedModifier() {
        if (this.beforeGetSpeedModifierHooks != null) {
            for (int i = this.beforeGetSpeedModifierHooks.length - 1; i >= 0; --i) {
                this.beforeGetSpeedModifierHooks[i].beforeGetSpeedModifier();
            }
        }
        float n;
        if (this.overrideGetSpeedModifierHooks != null) {
            n = this.overrideGetSpeedModifierHooks[this.overrideGetSpeedModifierHooks.length - 1].getSpeedModifier();
        }
        else {
            n = this.player.localGetSpeedModifier();
        }
        if (this.afterGetSpeedModifierHooks != null) {
            for (int j = 0; j < this.afterGetSpeedModifierHooks.length; ++j) {
                this.afterGetSpeedModifierHooks[j].afterGetSpeedModifier();
            }
        }
        return n;
    }
    
    protected ServerPlayerBase GetOverwrittenGetSpeedModifier(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideGetSpeedModifierHooks.length) {
            if (this.overrideGetSpeedModifierHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideGetSpeedModifierHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void heal(final EntityPlayer entityPlayer, final int n) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.heal(n);
        }
        else {
            entityPlayer.localHeal(n);
        }
    }
    
    private void heal(final int n) {
        if (this.beforeHealHooks != null) {
            for (int i = this.beforeHealHooks.length - 1; i >= 0; --i) {
                this.beforeHealHooks[i].beforeHeal(n);
            }
        }
        if (this.overrideHealHooks != null) {
            this.overrideHealHooks[this.overrideHealHooks.length - 1].heal(n);
        }
        else {
            this.player.localHeal(n);
        }
        if (this.afterHealHooks != null) {
            for (int j = 0; j < this.afterHealHooks.length; ++j) {
                this.afterHealHooks[j].afterHeal(n);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenHeal(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideHealHooks.length) {
            if (this.overrideHealHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideHealHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean interact(final EntityPlayer entityPlayer, final EntityHuman entityHuman) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.interact(entityHuman);
        }
        else {
            b = entityPlayer.localInteract(entityHuman);
        }
        return b;
    }
    
    private boolean interact(final EntityHuman entityHuman) {
        if (this.beforeInteractHooks != null) {
            for (int i = this.beforeInteractHooks.length - 1; i >= 0; --i) {
                this.beforeInteractHooks[i].beforeInteract(entityHuman);
            }
        }
        boolean b;
        if (this.overrideInteractHooks != null) {
            b = this.overrideInteractHooks[this.overrideInteractHooks.length - 1].interact(entityHuman);
        }
        else {
            b = this.player.localInteract(entityHuman);
        }
        if (this.afterInteractHooks != null) {
            for (int j = 0; j < this.afterInteractHooks.length; ++j) {
                this.afterInteractHooks[j].afterInteract(entityHuman);
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenInteract(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideInteractHooks.length) {
            if (this.overrideInteractHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideInteractHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean isEntityInsideOpaqueBlock(final EntityPlayer entityPlayer) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.isEntityInsideOpaqueBlock();
        }
        else {
            b = entityPlayer.localIsEntityInsideOpaqueBlock();
        }
        return b;
    }
    
    private boolean isEntityInsideOpaqueBlock() {
        if (this.beforeIsEntityInsideOpaqueBlockHooks != null) {
            for (int i = this.beforeIsEntityInsideOpaqueBlockHooks.length - 1; i >= 0; --i) {
                this.beforeIsEntityInsideOpaqueBlockHooks[i].beforeIsEntityInsideOpaqueBlock();
            }
        }
        boolean b;
        if (this.overrideIsEntityInsideOpaqueBlockHooks != null) {
            b = this.overrideIsEntityInsideOpaqueBlockHooks[this.overrideIsEntityInsideOpaqueBlockHooks.length - 1].isEntityInsideOpaqueBlock();
        }
        else {
            b = this.player.localIsEntityInsideOpaqueBlock();
        }
        if (this.afterIsEntityInsideOpaqueBlockHooks != null) {
            for (int j = 0; j < this.afterIsEntityInsideOpaqueBlockHooks.length; ++j) {
                this.afterIsEntityInsideOpaqueBlockHooks[j].afterIsEntityInsideOpaqueBlock();
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenIsEntityInsideOpaqueBlock(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideIsEntityInsideOpaqueBlockHooks.length) {
            if (this.overrideIsEntityInsideOpaqueBlockHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideIsEntityInsideOpaqueBlockHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean isInWater(final EntityPlayer entityPlayer) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.isInWater();
        }
        else {
            b = entityPlayer.localIsInWater();
        }
        return b;
    }
    
    private boolean isInWater() {
        if (this.beforeIsInWaterHooks != null) {
            for (int i = this.beforeIsInWaterHooks.length - 1; i >= 0; --i) {
                this.beforeIsInWaterHooks[i].beforeIsInWater();
            }
        }
        boolean b;
        if (this.overrideIsInWaterHooks != null) {
            b = this.overrideIsInWaterHooks[this.overrideIsInWaterHooks.length - 1].isInWater();
        }
        else {
            b = this.player.localIsInWater();
        }
        if (this.afterIsInWaterHooks != null) {
            for (int j = 0; j < this.afterIsInWaterHooks.length; ++j) {
                this.afterIsInWaterHooks[j].afterIsInWater();
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenIsInWater(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideIsInWaterHooks.length) {
            if (this.overrideIsInWaterHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideIsInWaterHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean isInsideOfMaterial(final EntityPlayer entityPlayer, final Material material) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.isInsideOfMaterial(material);
        }
        else {
            b = entityPlayer.localIsInsideOfMaterial(material);
        }
        return b;
    }
    
    private boolean isInsideOfMaterial(final Material material) {
        if (this.beforeIsInsideOfMaterialHooks != null) {
            for (int i = this.beforeIsInsideOfMaterialHooks.length - 1; i >= 0; --i) {
                this.beforeIsInsideOfMaterialHooks[i].beforeIsInsideOfMaterial(material);
            }
        }
        boolean b;
        if (this.overrideIsInsideOfMaterialHooks != null) {
            b = this.overrideIsInsideOfMaterialHooks[this.overrideIsInsideOfMaterialHooks.length - 1].isInsideOfMaterial(material);
        }
        else {
            b = this.player.localIsInsideOfMaterial(material);
        }
        if (this.afterIsInsideOfMaterialHooks != null) {
            for (int j = 0; j < this.afterIsInsideOfMaterialHooks.length; ++j) {
                this.afterIsInsideOfMaterialHooks[j].afterIsInsideOfMaterial(material);
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenIsInsideOfMaterial(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideIsInsideOfMaterialHooks.length) {
            if (this.overrideIsInsideOfMaterialHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideIsInsideOfMaterialHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean isOnLadder(final EntityPlayer entityPlayer) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.isOnLadder();
        }
        else {
            b = entityPlayer.localIsOnLadder();
        }
        return b;
    }
    
    private boolean isOnLadder() {
        if (this.beforeIsOnLadderHooks != null) {
            for (int i = this.beforeIsOnLadderHooks.length - 1; i >= 0; --i) {
                this.beforeIsOnLadderHooks[i].beforeIsOnLadder();
            }
        }
        boolean b;
        if (this.overrideIsOnLadderHooks != null) {
            b = this.overrideIsOnLadderHooks[this.overrideIsOnLadderHooks.length - 1].isOnLadder();
        }
        else {
            b = this.player.localIsOnLadder();
        }
        if (this.afterIsOnLadderHooks != null) {
            for (int j = 0; j < this.afterIsOnLadderHooks.length; ++j) {
                this.afterIsOnLadderHooks[j].afterIsOnLadder();
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenIsOnLadder(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideIsOnLadderHooks.length) {
            if (this.overrideIsOnLadderHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideIsOnLadderHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static boolean isPlayerSleeping(final EntityPlayer entityPlayer) {
        boolean b;
        if (entityPlayer.serverPlayerAPI != null) {
            b = entityPlayer.serverPlayerAPI.isPlayerSleeping();
        }
        else {
            b = entityPlayer.localIsPlayerSleeping();
        }
        return b;
    }
    
    private boolean isPlayerSleeping() {
        if (this.beforeIsPlayerSleepingHooks != null) {
            for (int i = this.beforeIsPlayerSleepingHooks.length - 1; i >= 0; --i) {
                this.beforeIsPlayerSleepingHooks[i].beforeIsPlayerSleeping();
            }
        }
        boolean b;
        if (this.overrideIsPlayerSleepingHooks != null) {
            b = this.overrideIsPlayerSleepingHooks[this.overrideIsPlayerSleepingHooks.length - 1].isPlayerSleeping();
        }
        else {
            b = this.player.localIsPlayerSleeping();
        }
        if (this.afterIsPlayerSleepingHooks != null) {
            for (int j = 0; j < this.afterIsPlayerSleepingHooks.length; ++j) {
                this.afterIsPlayerSleepingHooks[j].afterIsPlayerSleeping();
            }
        }
        return b;
    }
    
    protected ServerPlayerBase GetOverwrittenIsPlayerSleeping(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideIsPlayerSleepingHooks.length) {
            if (this.overrideIsPlayerSleepingHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideIsPlayerSleepingHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void jump(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.jump();
        }
        else {
            entityPlayer.localJump();
        }
    }
    
    private void jump() {
        if (this.beforeJumpHooks != null) {
            for (int i = this.beforeJumpHooks.length - 1; i >= 0; --i) {
                this.beforeJumpHooks[i].beforeJump();
            }
        }
        if (this.overrideJumpHooks != null) {
            this.overrideJumpHooks[this.overrideJumpHooks.length - 1].jump();
        }
        else {
            this.player.localJump();
        }
        if (this.afterJumpHooks != null) {
            for (int j = 0; j < this.afterJumpHooks.length; ++j) {
                this.afterJumpHooks[j].afterJump();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenJump(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideJumpHooks.length) {
            if (this.overrideJumpHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideJumpHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void knockBack(final EntityPlayer entityPlayer, final Entity entity, final int n, final double n2, final double n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.knockBack(entity, n, n2, n3);
        }
        else {
            entityPlayer.localKnockBack(entity, n, n2, n3);
        }
    }
    
    private void knockBack(final Entity entity, final int n, final double n2, final double n3) {
        if (this.beforeKnockBackHooks != null) {
            for (int i = this.beforeKnockBackHooks.length - 1; i >= 0; --i) {
                this.beforeKnockBackHooks[i].beforeKnockBack(entity, n, n2, n3);
            }
        }
        if (this.overrideKnockBackHooks != null) {
            this.overrideKnockBackHooks[this.overrideKnockBackHooks.length - 1].knockBack(entity, n, n2, n3);
        }
        else {
            this.player.localKnockBack(entity, n, n2, n3);
        }
        if (this.afterKnockBackHooks != null) {
            for (int j = 0; j < this.afterKnockBackHooks.length; ++j) {
                this.afterKnockBackHooks[j].afterKnockBack(entity, n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenKnockBack(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideKnockBackHooks.length) {
            if (this.overrideKnockBackHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideKnockBackHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void moveEntity(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.moveEntity(n, n2, n3);
        }
        else {
            entityPlayer.localMoveEntity(n, n2, n3);
        }
    }
    
    private void moveEntity(final double n, final double n2, final double n3) {
        if (this.beforeMoveEntityHooks != null) {
            for (int i = this.beforeMoveEntityHooks.length - 1; i >= 0; --i) {
                this.beforeMoveEntityHooks[i].beforeMoveEntity(n, n2, n3);
            }
        }
        if (this.overrideMoveEntityHooks != null) {
            this.overrideMoveEntityHooks[this.overrideMoveEntityHooks.length - 1].moveEntity(n, n2, n3);
        }
        else {
            this.player.localMoveEntity(n, n2, n3);
        }
        if (this.afterMoveEntityHooks != null) {
            for (int j = 0; j < this.afterMoveEntityHooks.length; ++j) {
                this.afterMoveEntityHooks[j].afterMoveEntity(n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenMoveEntity(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideMoveEntityHooks.length) {
            if (this.overrideMoveEntityHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideMoveEntityHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void moveEntityWithHeading(final EntityPlayer entityPlayer, final float n, final float n2) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.moveEntityWithHeading(n, n2);
        }
        else {
            entityPlayer.localMoveEntityWithHeading(n, n2);
        }
    }
    
    private void moveEntityWithHeading(final float n, final float n2) {
        if (this.beforeMoveEntityWithHeadingHooks != null) {
            for (int i = this.beforeMoveEntityWithHeadingHooks.length - 1; i >= 0; --i) {
                this.beforeMoveEntityWithHeadingHooks[i].beforeMoveEntityWithHeading(n, n2);
            }
        }
        if (this.overrideMoveEntityWithHeadingHooks != null) {
            this.overrideMoveEntityWithHeadingHooks[this.overrideMoveEntityWithHeadingHooks.length - 1].moveEntityWithHeading(n, n2);
        }
        else {
            this.player.localMoveEntityWithHeading(n, n2);
        }
        if (this.afterMoveEntityWithHeadingHooks != null) {
            for (int j = 0; j < this.afterMoveEntityWithHeadingHooks.length; ++j) {
                this.afterMoveEntityWithHeadingHooks[j].afterMoveEntityWithHeading(n, n2);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenMoveEntityWithHeading(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideMoveEntityWithHeadingHooks.length) {
            if (this.overrideMoveEntityWithHeadingHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideMoveEntityWithHeadingHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void moveFlying(final EntityPlayer entityPlayer, final float n, final float n2, final float n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.moveFlying(n, n2, n3);
        }
        else {
            entityPlayer.localMoveFlying(n, n2, n3);
        }
    }
    
    private void moveFlying(final float n, final float n2, final float n3) {
        if (this.beforeMoveFlyingHooks != null) {
            for (int i = this.beforeMoveFlyingHooks.length - 1; i >= 0; --i) {
                this.beforeMoveFlyingHooks[i].beforeMoveFlying(n, n2, n3);
            }
        }
        if (this.overrideMoveFlyingHooks != null) {
            this.overrideMoveFlyingHooks[this.overrideMoveFlyingHooks.length - 1].moveFlying(n, n2, n3);
        }
        else {
            this.player.localMoveFlying(n, n2, n3);
        }
        if (this.afterMoveFlyingHooks != null) {
            for (int j = 0; j < this.afterMoveFlyingHooks.length; ++j) {
                this.afterMoveFlyingHooks[j].afterMoveFlying(n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenMoveFlying(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideMoveFlyingHooks.length) {
            if (this.overrideMoveFlyingHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideMoveFlyingHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onDeath(final EntityPlayer entityPlayer, final DamageSource damageSource) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onDeath(damageSource);
        }
        else {
            entityPlayer.localOnDeath(damageSource);
        }
    }
    
    private void onDeath(final DamageSource damageSource) {
        if (this.beforeOnDeathHooks != null) {
            for (int i = this.beforeOnDeathHooks.length - 1; i >= 0; --i) {
                this.beforeOnDeathHooks[i].beforeOnDeath(damageSource);
            }
        }
        if (this.overrideOnDeathHooks != null) {
            this.overrideOnDeathHooks[this.overrideOnDeathHooks.length - 1].onDeath(damageSource);
        }
        else {
            this.player.localOnDeath(damageSource);
        }
        if (this.afterOnDeathHooks != null) {
            for (int j = 0; j < this.afterOnDeathHooks.length; ++j) {
                this.afterOnDeathHooks[j].afterOnDeath(damageSource);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnDeath(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnDeathHooks.length) {
            if (this.overrideOnDeathHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnDeathHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onLivingUpdate(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onLivingUpdate();
        }
        else {
            entityPlayer.localOnLivingUpdate();
        }
    }
    
    private void onLivingUpdate() {
        if (this.beforeOnLivingUpdateHooks != null) {
            for (int i = this.beforeOnLivingUpdateHooks.length - 1; i >= 0; --i) {
                this.beforeOnLivingUpdateHooks[i].beforeOnLivingUpdate();
            }
        }
        if (this.overrideOnLivingUpdateHooks != null) {
            this.overrideOnLivingUpdateHooks[this.overrideOnLivingUpdateHooks.length - 1].onLivingUpdate();
        }
        else {
            this.player.localOnLivingUpdate();
        }
        if (this.afterOnLivingUpdateHooks != null) {
            for (int j = 0; j < this.afterOnLivingUpdateHooks.length; ++j) {
                this.afterOnLivingUpdateHooks[j].afterOnLivingUpdate();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnLivingUpdate(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnLivingUpdateHooks.length) {
            if (this.overrideOnLivingUpdateHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnLivingUpdateHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onKillEntity(final EntityPlayer entityPlayer, final EntityLiving entityLiving) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onKillEntity(entityLiving);
        }
        else {
            entityPlayer.localOnKillEntity(entityLiving);
        }
    }
    
    private void onKillEntity(final EntityLiving entityLiving) {
        if (this.beforeOnKillEntityHooks != null) {
            for (int i = this.beforeOnKillEntityHooks.length - 1; i >= 0; --i) {
                this.beforeOnKillEntityHooks[i].beforeOnKillEntity(entityLiving);
            }
        }
        if (this.overrideOnKillEntityHooks != null) {
            this.overrideOnKillEntityHooks[this.overrideOnKillEntityHooks.length - 1].onKillEntity(entityLiving);
        }
        else {
            this.player.localOnKillEntity(entityLiving);
        }
        if (this.afterOnKillEntityHooks != null) {
            for (int j = 0; j < this.afterOnKillEntityHooks.length; ++j) {
                this.afterOnKillEntityHooks[j].afterOnKillEntity(entityLiving);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnKillEntity(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnKillEntityHooks.length) {
            if (this.overrideOnKillEntityHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnKillEntityHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onStruckByLightning(final EntityPlayer entityPlayer, final EntityLightning entityLightning) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onStruckByLightning(entityLightning);
        }
        else {
            entityPlayer.localOnStruckByLightning(entityLightning);
        }
    }
    
    private void onStruckByLightning(final EntityLightning entityLightning) {
        if (this.beforeOnStruckByLightningHooks != null) {
            for (int i = this.beforeOnStruckByLightningHooks.length - 1; i >= 0; --i) {
                this.beforeOnStruckByLightningHooks[i].beforeOnStruckByLightning(entityLightning);
            }
        }
        if (this.overrideOnStruckByLightningHooks != null) {
            this.overrideOnStruckByLightningHooks[this.overrideOnStruckByLightningHooks.length - 1].onStruckByLightning(entityLightning);
        }
        else {
            this.player.localOnStruckByLightning(entityLightning);
        }
        if (this.afterOnStruckByLightningHooks != null) {
            for (int j = 0; j < this.afterOnStruckByLightningHooks.length; ++j) {
                this.afterOnStruckByLightningHooks[j].afterOnStruckByLightning(entityLightning);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnStruckByLightning(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnStruckByLightningHooks.length) {
            if (this.overrideOnStruckByLightningHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnStruckByLightningHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onUpdate(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onUpdate();
        }
        else {
            entityPlayer.localOnUpdate();
        }
    }
    
    private void onUpdate() {
        if (this.beforeOnUpdateHooks != null) {
            for (int i = this.beforeOnUpdateHooks.length - 1; i >= 0; --i) {
                this.beforeOnUpdateHooks[i].beforeOnUpdate();
            }
        }
        if (this.overrideOnUpdateHooks != null) {
            this.overrideOnUpdateHooks[this.overrideOnUpdateHooks.length - 1].onUpdate();
        }
        else {
            this.player.localOnUpdate();
        }
        if (this.afterOnUpdateHooks != null) {
            for (int j = 0; j < this.afterOnUpdateHooks.length; ++j) {
                this.afterOnUpdateHooks[j].afterOnUpdate();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnUpdate(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnUpdateHooks.length) {
            if (this.overrideOnUpdateHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnUpdateHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void onUpdateEntity(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.onUpdateEntity();
        }
        else {
            entityPlayer.localOnUpdateEntity();
        }
    }
    
    private void onUpdateEntity() {
        if (this.beforeOnUpdateEntityHooks != null) {
            for (int i = this.beforeOnUpdateEntityHooks.length - 1; i >= 0; --i) {
                this.beforeOnUpdateEntityHooks[i].beforeOnUpdateEntity();
            }
        }
        if (this.overrideOnUpdateEntityHooks != null) {
            this.overrideOnUpdateEntityHooks[this.overrideOnUpdateEntityHooks.length - 1].onUpdateEntity();
        }
        else {
            this.player.localOnUpdateEntity();
        }
        if (this.afterOnUpdateEntityHooks != null) {
            for (int j = 0; j < this.afterOnUpdateEntityHooks.length; ++j) {
                this.afterOnUpdateEntityHooks[j].afterOnUpdateEntity();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenOnUpdateEntity(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideOnUpdateEntityHooks.length) {
            if (this.overrideOnUpdateEntityHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideOnUpdateEntityHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void readEntityFromNBT(final EntityPlayer entityPlayer, final NBTTagCompound nbtTagCompound) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.readEntityFromNBT(nbtTagCompound);
        }
        else {
            entityPlayer.localReadEntityFromNBT(nbtTagCompound);
        }
    }
    
    private void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        if (this.beforeReadEntityFromNBTHooks != null) {
            for (int i = this.beforeReadEntityFromNBTHooks.length - 1; i >= 0; --i) {
                this.beforeReadEntityFromNBTHooks[i].beforeReadEntityFromNBT(nbtTagCompound);
            }
        }
        if (this.overrideReadEntityFromNBTHooks != null) {
            this.overrideReadEntityFromNBTHooks[this.overrideReadEntityFromNBTHooks.length - 1].readEntityFromNBT(nbtTagCompound);
        }
        else {
            this.player.localReadEntityFromNBT(nbtTagCompound);
        }
        if (this.afterReadEntityFromNBTHooks != null) {
            for (int j = 0; j < this.afterReadEntityFromNBTHooks.length; ++j) {
                this.afterReadEntityFromNBTHooks[j].afterReadEntityFromNBT(nbtTagCompound);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenReadEntityFromNBT(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideReadEntityFromNBTHooks.length) {
            if (this.overrideReadEntityFromNBTHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideReadEntityFromNBTHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void setDead(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.setDead();
        }
        else {
            entityPlayer.localSetDead();
        }
    }
    
    private void setDead() {
        if (this.beforeSetDeadHooks != null) {
            for (int i = this.beforeSetDeadHooks.length - 1; i >= 0; --i) {
                this.beforeSetDeadHooks[i].beforeSetDead();
            }
        }
        if (this.overrideSetDeadHooks != null) {
            this.overrideSetDeadHooks[this.overrideSetDeadHooks.length - 1].setDead();
        }
        else {
            this.player.localSetDead();
        }
        if (this.afterSetDeadHooks != null) {
            for (int j = 0; j < this.afterSetDeadHooks.length; ++j) {
                this.afterSetDeadHooks[j].afterSetDead();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenSetDead(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideSetDeadHooks.length) {
            if (this.overrideSetDeadHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideSetDeadHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void setPosition(final EntityPlayer entityPlayer, final double n, final double n2, final double n3) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.setPosition(n, n2, n3);
        }
        else {
            entityPlayer.localSetPosition(n, n2, n3);
        }
    }
    
    private void setPosition(final double n, final double n2, final double n3) {
        if (this.beforeSetPositionHooks != null) {
            for (int i = this.beforeSetPositionHooks.length - 1; i >= 0; --i) {
                this.beforeSetPositionHooks[i].beforeSetPosition(n, n2, n3);
            }
        }
        if (this.overrideSetPositionHooks != null) {
            this.overrideSetPositionHooks[this.overrideSetPositionHooks.length - 1].setPosition(n, n2, n3);
        }
        else {
            this.player.localSetPosition(n, n2, n3);
        }
        if (this.afterSetPositionHooks != null) {
            for (int j = 0; j < this.afterSetPositionHooks.length; ++j) {
                this.afterSetPositionHooks[j].afterSetPosition(n, n2, n3);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenSetPosition(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideSetPositionHooks.length) {
            if (this.overrideSetPositionHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideSetPositionHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void swingItem(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.swingItem();
        }
        else {
            entityPlayer.localSwingItem();
        }
    }
    
    private void swingItem() {
        if (this.beforeSwingItemHooks != null) {
            for (int i = this.beforeSwingItemHooks.length - 1; i >= 0; --i) {
                this.beforeSwingItemHooks[i].beforeSwingItem();
            }
        }
        if (this.overrideSwingItemHooks != null) {
            this.overrideSwingItemHooks[this.overrideSwingItemHooks.length - 1].swingItem();
        }
        else {
            this.player.localSwingItem();
        }
        if (this.afterSwingItemHooks != null) {
            for (int j = 0; j < this.afterSwingItemHooks.length; ++j) {
                this.afterSwingItemHooks[j].afterSwingItem();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenSwingItem(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideSwingItemHooks.length) {
            if (this.overrideSwingItemHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideSwingItemHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void updateEntityActionState(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.updateEntityActionState();
        }
        else {
            entityPlayer.localUpdateEntityActionState();
        }
    }
    
    private void updateEntityActionState() {
        if (this.beforeUpdateEntityActionStateHooks != null) {
            for (int i = this.beforeUpdateEntityActionStateHooks.length - 1; i >= 0; --i) {
                this.beforeUpdateEntityActionStateHooks[i].beforeUpdateEntityActionState();
            }
        }
        if (this.overrideUpdateEntityActionStateHooks != null) {
            this.overrideUpdateEntityActionStateHooks[this.overrideUpdateEntityActionStateHooks.length - 1].updateEntityActionState();
        }
        else {
            this.player.localUpdateEntityActionState();
        }
        if (this.afterUpdateEntityActionStateHooks != null) {
            for (int j = 0; j < this.afterUpdateEntityActionStateHooks.length; ++j) {
                this.afterUpdateEntityActionStateHooks[j].afterUpdateEntityActionState();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenUpdateEntityActionState(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideUpdateEntityActionStateHooks.length) {
            if (this.overrideUpdateEntityActionStateHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideUpdateEntityActionStateHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void updatePotionEffects(final EntityPlayer entityPlayer) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.updatePotionEffects();
        }
        else {
            entityPlayer.localUpdatePotionEffects();
        }
    }
    
    private void updatePotionEffects() {
        if (this.beforeUpdatePotionEffectsHooks != null) {
            for (int i = this.beforeUpdatePotionEffectsHooks.length - 1; i >= 0; --i) {
                this.beforeUpdatePotionEffectsHooks[i].beforeUpdatePotionEffects();
            }
        }
        if (this.overrideUpdatePotionEffectsHooks != null) {
            this.overrideUpdatePotionEffectsHooks[this.overrideUpdatePotionEffectsHooks.length - 1].updatePotionEffects();
        }
        else {
            this.player.localUpdatePotionEffects();
        }
        if (this.afterUpdatePotionEffectsHooks != null) {
            for (int j = 0; j < this.afterUpdatePotionEffectsHooks.length; ++j) {
                this.afterUpdatePotionEffectsHooks[j].afterUpdatePotionEffects();
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenUpdatePotionEffects(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideUpdatePotionEffectsHooks.length) {
            if (this.overrideUpdatePotionEffectsHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideUpdatePotionEffectsHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    public static void writeEntityToNBT(final EntityPlayer entityPlayer, final NBTTagCompound nbtTagCompound) {
        if (entityPlayer.serverPlayerAPI != null) {
            entityPlayer.serverPlayerAPI.writeEntityToNBT(nbtTagCompound);
        }
        else {
            entityPlayer.localWriteEntityToNBT(nbtTagCompound);
        }
    }
    
    private void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        if (this.beforeWriteEntityToNBTHooks != null) {
            for (int i = this.beforeWriteEntityToNBTHooks.length - 1; i >= 0; --i) {
                this.beforeWriteEntityToNBTHooks[i].beforeWriteEntityToNBT(nbtTagCompound);
            }
        }
        if (this.overrideWriteEntityToNBTHooks != null) {
            this.overrideWriteEntityToNBTHooks[this.overrideWriteEntityToNBTHooks.length - 1].writeEntityToNBT(nbtTagCompound);
        }
        else {
            this.player.localWriteEntityToNBT(nbtTagCompound);
        }
        if (this.afterWriteEntityToNBTHooks != null) {
            for (int j = 0; j < this.afterWriteEntityToNBTHooks.length; ++j) {
                this.afterWriteEntityToNBTHooks[j].afterWriteEntityToNBT(nbtTagCompound);
            }
        }
    }
    
    protected ServerPlayerBase GetOverwrittenWriteEntityToNBT(final ServerPlayerBase serverPlayerBase) {
        int i = 0;
        while (i < this.overrideWriteEntityToNBTHooks.length) {
            if (this.overrideWriteEntityToNBTHooks[i] == serverPlayerBase) {
                if (i == 0) {
                    return null;
                }
                return this.overrideWriteEntityToNBTHooks[i - 1];
            }
            else {
                ++i;
            }
        }
        return serverPlayerBase;
    }
    
    static {
        Class = new Class[] { ServerPlayerAPI.class };
        Classes = new Class[] { ServerPlayerAPI.class, String.class };
        logger = Logger.getLogger("ServerPlayerAPI");
        EmptySortMap = Collections.unmodifiableMap((Map<? extends String, ? extends String[]>)new HashMap<String, String[]>());
        initializer = new Object[] { null };
        initializers = new Object[] { null, null };
        beforeAddExhaustionHookTypes = new LinkedList<String>();
        overrideAddExhaustionHookTypes = new LinkedList<String>();
        afterAddExhaustionHookTypes = new LinkedList<String>();
        allBaseBeforeAddExhaustionSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAddExhaustionInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExhaustionSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExhaustionInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExhaustionSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExhaustionInferiors = new Hashtable<String, String[]>(0);
        beforeAddExperienceHookTypes = new LinkedList<String>();
        overrideAddExperienceHookTypes = new LinkedList<String>();
        afterAddExperienceHookTypes = new LinkedList<String>();
        allBaseBeforeAddExperienceSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAddExperienceInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExperienceSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExperienceInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExperienceSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExperienceInferiors = new Hashtable<String, String[]>(0);
        beforeAddExperienceLevelHookTypes = new LinkedList<String>();
        overrideAddExperienceLevelHookTypes = new LinkedList<String>();
        afterAddExperienceLevelHookTypes = new LinkedList<String>();
        allBaseBeforeAddExperienceLevelSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAddExperienceLevelInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExperienceLevelSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddExperienceLevelInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExperienceLevelSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddExperienceLevelInferiors = new Hashtable<String, String[]>(0);
        beforeAddMovementStatHookTypes = new LinkedList<String>();
        overrideAddMovementStatHookTypes = new LinkedList<String>();
        afterAddMovementStatHookTypes = new LinkedList<String>();
        allBaseBeforeAddMovementStatSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAddMovementStatInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddMovementStatSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAddMovementStatInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddMovementStatSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAddMovementStatInferiors = new Hashtable<String, String[]>(0);
        beforeAttackEntityFromHookTypes = new LinkedList<String>();
        overrideAttackEntityFromHookTypes = new LinkedList<String>();
        afterAttackEntityFromHookTypes = new LinkedList<String>();
        allBaseBeforeAttackEntityFromSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAttackEntityFromInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAttackEntityFromSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAttackEntityFromInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAttackEntityFromSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAttackEntityFromInferiors = new Hashtable<String, String[]>(0);
        beforeAttackTargetEntityWithCurrentItemHookTypes = new LinkedList<String>();
        overrideAttackTargetEntityWithCurrentItemHookTypes = new LinkedList<String>();
        afterAttackTargetEntityWithCurrentItemHookTypes = new LinkedList<String>();
        allBaseBeforeAttackTargetEntityWithCurrentItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeAttackTargetEntityWithCurrentItemInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAttackTargetEntityWithCurrentItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideAttackTargetEntityWithCurrentItemInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterAttackTargetEntityWithCurrentItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterAttackTargetEntityWithCurrentItemInferiors = new Hashtable<String, String[]>(0);
        beforeCanHarvestBlockHookTypes = new LinkedList<String>();
        overrideCanHarvestBlockHookTypes = new LinkedList<String>();
        afterCanHarvestBlockHookTypes = new LinkedList<String>();
        allBaseBeforeCanHarvestBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeCanHarvestBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanHarvestBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanHarvestBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanHarvestBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanHarvestBlockInferiors = new Hashtable<String, String[]>(0);
        beforeCanPlayerEditHookTypes = new LinkedList<String>();
        overrideCanPlayerEditHookTypes = new LinkedList<String>();
        afterCanPlayerEditHookTypes = new LinkedList<String>();
        allBaseBeforeCanPlayerEditSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeCanPlayerEditInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanPlayerEditSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanPlayerEditInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanPlayerEditSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanPlayerEditInferiors = new Hashtable<String, String[]>(0);
        beforeCanTriggerWalkingHookTypes = new LinkedList<String>();
        overrideCanTriggerWalkingHookTypes = new LinkedList<String>();
        afterCanTriggerWalkingHookTypes = new LinkedList<String>();
        allBaseBeforeCanTriggerWalkingSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeCanTriggerWalkingInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanTriggerWalkingSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideCanTriggerWalkingInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanTriggerWalkingSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterCanTriggerWalkingInferiors = new Hashtable<String, String[]>(0);
        beforeClonePlayerHookTypes = new LinkedList<String>();
        overrideClonePlayerHookTypes = new LinkedList<String>();
        afterClonePlayerHookTypes = new LinkedList<String>();
        allBaseBeforeClonePlayerSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeClonePlayerInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideClonePlayerSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideClonePlayerInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterClonePlayerSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterClonePlayerInferiors = new Hashtable<String, String[]>(0);
        beforeDamageEntityHookTypes = new LinkedList<String>();
        overrideDamageEntityHookTypes = new LinkedList<String>();
        afterDamageEntityHookTypes = new LinkedList<String>();
        allBaseBeforeDamageEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDamageEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDamageEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDamageEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDamageEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDamageEntityInferiors = new Hashtable<String, String[]>(0);
        beforeDisplayGUIChestHookTypes = new LinkedList<String>();
        overrideDisplayGUIChestHookTypes = new LinkedList<String>();
        afterDisplayGUIChestHookTypes = new LinkedList<String>();
        allBaseBeforeDisplayGUIChestSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDisplayGUIChestInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIChestSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIChestInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIChestSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIChestInferiors = new Hashtable<String, String[]>(0);
        beforeDisplayGUIDispenserHookTypes = new LinkedList<String>();
        overrideDisplayGUIDispenserHookTypes = new LinkedList<String>();
        afterDisplayGUIDispenserHookTypes = new LinkedList<String>();
        allBaseBeforeDisplayGUIDispenserSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDisplayGUIDispenserInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIDispenserSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIDispenserInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIDispenserSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIDispenserInferiors = new Hashtable<String, String[]>(0);
        beforeDisplayGUIFurnaceHookTypes = new LinkedList<String>();
        overrideDisplayGUIFurnaceHookTypes = new LinkedList<String>();
        afterDisplayGUIFurnaceHookTypes = new LinkedList<String>();
        allBaseBeforeDisplayGUIFurnaceSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDisplayGUIFurnaceInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIFurnaceSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIFurnaceInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIFurnaceSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIFurnaceInferiors = new Hashtable<String, String[]>(0);
        beforeDisplayGUIWorkbenchHookTypes = new LinkedList<String>();
        overrideDisplayGUIWorkbenchHookTypes = new LinkedList<String>();
        afterDisplayGUIWorkbenchHookTypes = new LinkedList<String>();
        allBaseBeforeDisplayGUIWorkbenchSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDisplayGUIWorkbenchInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIWorkbenchSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDisplayGUIWorkbenchInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIWorkbenchSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDisplayGUIWorkbenchInferiors = new Hashtable<String, String[]>(0);
        beforeDropOneItemHookTypes = new LinkedList<String>();
        overrideDropOneItemHookTypes = new LinkedList<String>();
        afterDropOneItemHookTypes = new LinkedList<String>();
        allBaseBeforeDropOneItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDropOneItemInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDropOneItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDropOneItemInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDropOneItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDropOneItemInferiors = new Hashtable<String, String[]>(0);
        beforeDropPlayerItemHookTypes = new LinkedList<String>();
        overrideDropPlayerItemHookTypes = new LinkedList<String>();
        afterDropPlayerItemHookTypes = new LinkedList<String>();
        allBaseBeforeDropPlayerItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDropPlayerItemInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDropPlayerItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideDropPlayerItemInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterDropPlayerItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterDropPlayerItemInferiors = new Hashtable<String, String[]>(0);
        beforeFallHookTypes = new LinkedList<String>();
        overrideFallHookTypes = new LinkedList<String>();
        afterFallHookTypes = new LinkedList<String>();
        allBaseBeforeFallSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeFallInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideFallSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideFallInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterFallSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterFallInferiors = new Hashtable<String, String[]>(0);
        beforeGetCurrentPlayerStrVsBlockHookTypes = new LinkedList<String>();
        overrideGetCurrentPlayerStrVsBlockHookTypes = new LinkedList<String>();
        afterGetCurrentPlayerStrVsBlockHookTypes = new LinkedList<String>();
        allBaseBeforeGetCurrentPlayerStrVsBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetCurrentPlayerStrVsBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetCurrentPlayerStrVsBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetCurrentPlayerStrVsBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetCurrentPlayerStrVsBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetCurrentPlayerStrVsBlockInferiors = new Hashtable<String, String[]>(0);
        beforeGetDistanceSqHookTypes = new LinkedList<String>();
        overrideGetDistanceSqHookTypes = new LinkedList<String>();
        afterGetDistanceSqHookTypes = new LinkedList<String>();
        allBaseBeforeGetDistanceSqSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetDistanceSqInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetDistanceSqSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetDistanceSqInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetDistanceSqSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetDistanceSqInferiors = new Hashtable<String, String[]>(0);
        beforeGetBrightnessHookTypes = new LinkedList<String>();
        overrideGetBrightnessHookTypes = new LinkedList<String>();
        afterGetBrightnessHookTypes = new LinkedList<String>();
        allBaseBeforeGetBrightnessSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetBrightnessInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetBrightnessSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetBrightnessInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetBrightnessSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetBrightnessInferiors = new Hashtable<String, String[]>(0);
        beforeGetEyeHeightHookTypes = new LinkedList<String>();
        overrideGetEyeHeightHookTypes = new LinkedList<String>();
        afterGetEyeHeightHookTypes = new LinkedList<String>();
        allBaseBeforeGetEyeHeightSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetEyeHeightInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetEyeHeightSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetEyeHeightInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetEyeHeightSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetEyeHeightInferiors = new Hashtable<String, String[]>(0);
        beforeGetMaxHealthHookTypes = new LinkedList<String>();
        overrideGetMaxHealthHookTypes = new LinkedList<String>();
        afterGetMaxHealthHookTypes = new LinkedList<String>();
        allBaseBeforeGetMaxHealthSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetMaxHealthInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetMaxHealthSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetMaxHealthInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetMaxHealthSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetMaxHealthInferiors = new Hashtable<String, String[]>(0);
        beforeGetSpeedModifierHookTypes = new LinkedList<String>();
        overrideGetSpeedModifierHookTypes = new LinkedList<String>();
        afterGetSpeedModifierHookTypes = new LinkedList<String>();
        allBaseBeforeGetSpeedModifierSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeGetSpeedModifierInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetSpeedModifierSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideGetSpeedModifierInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetSpeedModifierSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterGetSpeedModifierInferiors = new Hashtable<String, String[]>(0);
        beforeHealHookTypes = new LinkedList<String>();
        overrideHealHookTypes = new LinkedList<String>();
        afterHealHookTypes = new LinkedList<String>();
        allBaseBeforeHealSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeHealInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideHealSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideHealInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterHealSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterHealInferiors = new Hashtable<String, String[]>(0);
        beforeInteractHookTypes = new LinkedList<String>();
        overrideInteractHookTypes = new LinkedList<String>();
        afterInteractHookTypes = new LinkedList<String>();
        allBaseBeforeInteractSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeInteractInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideInteractSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideInteractInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterInteractSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterInteractInferiors = new Hashtable<String, String[]>(0);
        beforeIsEntityInsideOpaqueBlockHookTypes = new LinkedList<String>();
        overrideIsEntityInsideOpaqueBlockHookTypes = new LinkedList<String>();
        afterIsEntityInsideOpaqueBlockHookTypes = new LinkedList<String>();
        allBaseBeforeIsEntityInsideOpaqueBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeIsEntityInsideOpaqueBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsEntityInsideOpaqueBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsEntityInsideOpaqueBlockInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsEntityInsideOpaqueBlockSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsEntityInsideOpaqueBlockInferiors = new Hashtable<String, String[]>(0);
        beforeIsInWaterHookTypes = new LinkedList<String>();
        overrideIsInWaterHookTypes = new LinkedList<String>();
        afterIsInWaterHookTypes = new LinkedList<String>();
        allBaseBeforeIsInWaterSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeIsInWaterInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsInWaterSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsInWaterInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsInWaterSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsInWaterInferiors = new Hashtable<String, String[]>(0);
        beforeIsInsideOfMaterialHookTypes = new LinkedList<String>();
        overrideIsInsideOfMaterialHookTypes = new LinkedList<String>();
        afterIsInsideOfMaterialHookTypes = new LinkedList<String>();
        allBaseBeforeIsInsideOfMaterialSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeIsInsideOfMaterialInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsInsideOfMaterialSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsInsideOfMaterialInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsInsideOfMaterialSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsInsideOfMaterialInferiors = new Hashtable<String, String[]>(0);
        beforeIsOnLadderHookTypes = new LinkedList<String>();
        overrideIsOnLadderHookTypes = new LinkedList<String>();
        afterIsOnLadderHookTypes = new LinkedList<String>();
        allBaseBeforeIsOnLadderSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeIsOnLadderInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsOnLadderSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsOnLadderInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsOnLadderSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsOnLadderInferiors = new Hashtable<String, String[]>(0);
        beforeIsPlayerSleepingHookTypes = new LinkedList<String>();
        overrideIsPlayerSleepingHookTypes = new LinkedList<String>();
        afterIsPlayerSleepingHookTypes = new LinkedList<String>();
        allBaseBeforeIsPlayerSleepingSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeIsPlayerSleepingInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsPlayerSleepingSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideIsPlayerSleepingInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsPlayerSleepingSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterIsPlayerSleepingInferiors = new Hashtable<String, String[]>(0);
        beforeJumpHookTypes = new LinkedList<String>();
        overrideJumpHookTypes = new LinkedList<String>();
        afterJumpHookTypes = new LinkedList<String>();
        allBaseBeforeJumpSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeJumpInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideJumpSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideJumpInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterJumpSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterJumpInferiors = new Hashtable<String, String[]>(0);
        beforeKnockBackHookTypes = new LinkedList<String>();
        overrideKnockBackHookTypes = new LinkedList<String>();
        afterKnockBackHookTypes = new LinkedList<String>();
        allBaseBeforeKnockBackSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeKnockBackInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideKnockBackSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideKnockBackInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterKnockBackSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterKnockBackInferiors = new Hashtable<String, String[]>(0);
        beforeMoveEntityHookTypes = new LinkedList<String>();
        overrideMoveEntityHookTypes = new LinkedList<String>();
        afterMoveEntityHookTypes = new LinkedList<String>();
        allBaseBeforeMoveEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeMoveEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveEntityInferiors = new Hashtable<String, String[]>(0);
        beforeMoveEntityWithHeadingHookTypes = new LinkedList<String>();
        overrideMoveEntityWithHeadingHookTypes = new LinkedList<String>();
        afterMoveEntityWithHeadingHookTypes = new LinkedList<String>();
        allBaseBeforeMoveEntityWithHeadingSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeMoveEntityWithHeadingInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveEntityWithHeadingSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveEntityWithHeadingInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveEntityWithHeadingSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveEntityWithHeadingInferiors = new Hashtable<String, String[]>(0);
        beforeMoveFlyingHookTypes = new LinkedList<String>();
        overrideMoveFlyingHookTypes = new LinkedList<String>();
        afterMoveFlyingHookTypes = new LinkedList<String>();
        allBaseBeforeMoveFlyingSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeMoveFlyingInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveFlyingSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideMoveFlyingInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveFlyingSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterMoveFlyingInferiors = new Hashtable<String, String[]>(0);
        beforeOnDeathHookTypes = new LinkedList<String>();
        overrideOnDeathHookTypes = new LinkedList<String>();
        afterOnDeathHookTypes = new LinkedList<String>();
        allBaseBeforeOnDeathSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnDeathInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnDeathSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnDeathInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnDeathSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnDeathInferiors = new Hashtable<String, String[]>(0);
        beforeOnLivingUpdateHookTypes = new LinkedList<String>();
        overrideOnLivingUpdateHookTypes = new LinkedList<String>();
        afterOnLivingUpdateHookTypes = new LinkedList<String>();
        allBaseBeforeOnLivingUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnLivingUpdateInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnLivingUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnLivingUpdateInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnLivingUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnLivingUpdateInferiors = new Hashtable<String, String[]>(0);
        beforeOnKillEntityHookTypes = new LinkedList<String>();
        overrideOnKillEntityHookTypes = new LinkedList<String>();
        afterOnKillEntityHookTypes = new LinkedList<String>();
        allBaseBeforeOnKillEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnKillEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnKillEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnKillEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnKillEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnKillEntityInferiors = new Hashtable<String, String[]>(0);
        beforeOnStruckByLightningHookTypes = new LinkedList<String>();
        overrideOnStruckByLightningHookTypes = new LinkedList<String>();
        afterOnStruckByLightningHookTypes = new LinkedList<String>();
        allBaseBeforeOnStruckByLightningSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnStruckByLightningInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnStruckByLightningSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnStruckByLightningInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnStruckByLightningSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnStruckByLightningInferiors = new Hashtable<String, String[]>(0);
        beforeOnUpdateHookTypes = new LinkedList<String>();
        overrideOnUpdateHookTypes = new LinkedList<String>();
        afterOnUpdateHookTypes = new LinkedList<String>();
        allBaseBeforeOnUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnUpdateInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnUpdateInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnUpdateSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnUpdateInferiors = new Hashtable<String, String[]>(0);
        beforeOnUpdateEntityHookTypes = new LinkedList<String>();
        overrideOnUpdateEntityHookTypes = new LinkedList<String>();
        afterOnUpdateEntityHookTypes = new LinkedList<String>();
        allBaseBeforeOnUpdateEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeOnUpdateEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnUpdateEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideOnUpdateEntityInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnUpdateEntitySuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterOnUpdateEntityInferiors = new Hashtable<String, String[]>(0);
        beforeReadEntityFromNBTHookTypes = new LinkedList<String>();
        overrideReadEntityFromNBTHookTypes = new LinkedList<String>();
        afterReadEntityFromNBTHookTypes = new LinkedList<String>();
        allBaseBeforeReadEntityFromNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeReadEntityFromNBTInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideReadEntityFromNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideReadEntityFromNBTInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterReadEntityFromNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterReadEntityFromNBTInferiors = new Hashtable<String, String[]>(0);
        beforeSetDeadHookTypes = new LinkedList<String>();
        overrideSetDeadHookTypes = new LinkedList<String>();
        afterSetDeadHookTypes = new LinkedList<String>();
        allBaseBeforeSetDeadSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeSetDeadInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSetDeadSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSetDeadInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterSetDeadSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterSetDeadInferiors = new Hashtable<String, String[]>(0);
        beforeSetPositionHookTypes = new LinkedList<String>();
        overrideSetPositionHookTypes = new LinkedList<String>();
        afterSetPositionHookTypes = new LinkedList<String>();
        allBaseBeforeSetPositionSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeSetPositionInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSetPositionSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSetPositionInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterSetPositionSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterSetPositionInferiors = new Hashtable<String, String[]>(0);
        beforeSwingItemHookTypes = new LinkedList<String>();
        overrideSwingItemHookTypes = new LinkedList<String>();
        afterSwingItemHookTypes = new LinkedList<String>();
        allBaseBeforeSwingItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeSwingItemInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSwingItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideSwingItemInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterSwingItemSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterSwingItemInferiors = new Hashtable<String, String[]>(0);
        beforeUpdateEntityActionStateHookTypes = new LinkedList<String>();
        overrideUpdateEntityActionStateHookTypes = new LinkedList<String>();
        afterUpdateEntityActionStateHookTypes = new LinkedList<String>();
        allBaseBeforeUpdateEntityActionStateSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeUpdateEntityActionStateInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideUpdateEntityActionStateSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideUpdateEntityActionStateInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterUpdateEntityActionStateSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterUpdateEntityActionStateInferiors = new Hashtable<String, String[]>(0);
        beforeUpdatePotionEffectsHookTypes = new LinkedList<String>();
        overrideUpdatePotionEffectsHookTypes = new LinkedList<String>();
        afterUpdatePotionEffectsHookTypes = new LinkedList<String>();
        allBaseBeforeUpdatePotionEffectsSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeUpdatePotionEffectsInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideUpdatePotionEffectsSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideUpdatePotionEffectsInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterUpdatePotionEffectsSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterUpdatePotionEffectsInferiors = new Hashtable<String, String[]>(0);
        beforeWriteEntityToNBTHookTypes = new LinkedList<String>();
        overrideWriteEntityToNBTHookTypes = new LinkedList<String>();
        afterWriteEntityToNBTHookTypes = new LinkedList<String>();
        allBaseBeforeWriteEntityToNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeWriteEntityToNBTInferiors = new Hashtable<String, String[]>(0);
        allBaseOverrideWriteEntityToNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseOverrideWriteEntityToNBTInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterWriteEntityToNBTSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterWriteEntityToNBTInferiors = new Hashtable<String, String[]>(0);
        keys = new HashSet<String>();
        keysToVirtualIds = new HashMap<String, String>();
        dynamicTypes = new HashSet<Class<?>>();
        virtualDynamicHookMethods = new HashMap<Class<?>, Map<String, Method>>();
        beforeDynamicHookMethods = new HashMap<Class<?>, Map<String, Method>>();
        overrideDynamicHookMethods = new HashMap<Class<?>, Map<String, Method>>();
        afterDynamicHookMethods = new HashMap<Class<?>, Map<String, Method>>();
        beforeLocalConstructingHookTypes = new LinkedList<String>();
        afterLocalConstructingHookTypes = new LinkedList<String>();
        beforeDynamicHookTypes = new Hashtable<String, List<String>>(0);
        overrideDynamicHookTypes = new Hashtable<String, List<String>>(0);
        afterDynamicHookTypes = new Hashtable<String, List<String>>(0);
        allBaseConstructors = new Hashtable<String, Constructor<?>>();
        unmodifiableAllIds = Collections.unmodifiableSet((Set<? extends String>)ServerPlayerAPI.allBaseConstructors.keySet());
        allBaseBeforeLocalConstructingSuperiors = new Hashtable<String, String[]>(0);
        allBaseBeforeLocalConstructingInferiors = new Hashtable<String, String[]>(0);
        allBaseAfterLocalConstructingSuperiors = new Hashtable<String, String[]>(0);
        allBaseAfterLocalConstructingInferiors = new Hashtable<String, String[]>(0);
        allBaseBeforeDynamicSuperiors = new Hashtable<String, Map<String, String[]>>(0);
        allBaseBeforeDynamicInferiors = new Hashtable<String, Map<String, String[]>>(0);
        allBaseOverrideDynamicSuperiors = new Hashtable<String, Map<String, String[]>>(0);
        allBaseOverrideDynamicInferiors = new Hashtable<String, Map<String, String[]>>(0);
        allBaseAfterDynamicSuperiors = new Hashtable<String, Map<String, String[]>>(0);
        allBaseAfterDynamicInferiors = new Hashtable<String, Map<String, String[]>>(0);
        ServerPlayerAPI.initialized = false;
    }
}
