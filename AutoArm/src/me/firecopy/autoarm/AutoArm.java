// 
// Decompiled by Procyon v0.5.29
// 

package me.firecopy.autoarm;

import org.bukkit.Material;
import org.bukkit.GameMode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoArm extends JavaPlugin
{
    Player player;
    int ArmorCounter;
    int[] Diamond_Armor;
    int[] Iron_Armor;
    int[] Chain_Armor;
    int[] Gold_Armor;
    int[] Leather_Armor;
    int[] Weapons;
    ItemStack ArmorSelector;
    ItemStack temp;
    ItemStack[] InventorySlot;
    int ArmorSlotId;
    int InventorySlotId;
    int i;
    int WeaponSlotId;
    ItemStack WeaponSlot;
    
    public AutoArm() {
        this.ArmorCounter = 0;
        this.Diamond_Armor = new int[] { 310, 311, 312, 313 };
        this.Iron_Armor = new int[] { 306, 307, 308, 309 };
        this.Chain_Armor = new int[] { 302, 303, 304, 305 };
        this.Gold_Armor = new int[] { 314, 315, 316, 317 };
        this.Leather_Armor = new int[] { 298, 299, 300, 301 };
        this.Weapons = new int[] { 276, 267, 272, 268, 283 };
        this.WeaponSlotId = 0;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        this.player = (Player)sender;
        if (!commandLabel.equalsIgnoreCase("autoarm") && !commandLabel.equalsIgnoreCase("arm")) {
            return false;
        }
        if (sender.hasPermission("autoarm.arm")) {
            if (sender.hasPermission("autoarm.message")) {
            }
            if (this.player.getGameMode() == GameMode.CREATIVE && sender.hasPermission("autoarm.autodiamond")) {
                this.autoCreativeDiamond();
            }
            else {
                this.getPlayerArmor();
            }
            return true;
        }
        return false;
    }
    
    void getPlayerArmor() {
        this.ArmorSelector = this.player.getInventory().getHelmet();
        this.switchPlayerArmor();
        ++this.ArmorCounter;
        this.ArmorSelector = this.player.getInventory().getChestplate();
        this.switchPlayerArmor();
        ++this.ArmorCounter;
        this.ArmorSelector = this.player.getInventory().getLeggings();
        this.switchPlayerArmor();
        ++this.ArmorCounter;
        this.ArmorSelector = this.player.getInventory().getBoots();
        this.switchPlayerArmor();
        this.ArmorCounter = 0;
    }
    
    void switchPlayerArmor() {
        this.autoArmor();
        this.autoWeapon();
    }
    
    void autoArmor() {
        this.i = 0;
        while (this.i <= 35) {
            this.InventorySlot = this.player.getInventory().getContents();
            if (this.ArmorSelector != null) {
                this.ArmorSlotId = this.ArmorSelector.getTypeId();
            }
            if (this.ArmorSlotId == this.Diamond_Armor[this.ArmorCounter]) {
                break;
            }
            if (this.ArmorSlotId != this.Iron_Armor[this.ArmorCounter]) {
                if (this.ArmorSlotId != this.Chain_Armor[this.ArmorCounter]) {
                    if (this.ArmorSlotId != this.Gold_Armor[this.ArmorCounter]) {
                        if (this.ArmorSlotId != this.Leather_Armor[this.ArmorCounter]) {
                            this.allArmorCheck();
                        }
                        else {
                            this.diamondIronChainGoldCheck();
                        }
                    }
                    else {
                        this.diamondIronChainCheck();
                    }
                }
                else {
                    this.diamondIronCheck();
                }
            }
            else {
                this.diamondCheck();
            }
            if (this.ArmorCounter == 0) {
                this.player.getInventory().setHelmet(this.ArmorSelector);
            }
            if (this.ArmorCounter == 1) {
                this.player.getInventory().setChestplate(this.ArmorSelector);
            }
            if (this.ArmorCounter == 2) {
                this.player.getInventory().setLeggings(this.ArmorSelector);
            }
            if (this.ArmorCounter == 3) {
                this.player.getInventory().setBoots(this.ArmorSelector);
            }
            this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            this.InventorySlot[this.i] = null;
            ++this.i;
        }
    }
    
    void autoWeapon() {
        this.i = 0;
        while (this.i <= 35) {
            this.InventorySlot = this.player.getInventory().getContents();
            if (this.InventorySlot[0] != null) {
                this.WeaponSlotId = this.InventorySlot[0].getTypeId();
            }
            this.WeaponSlot = this.InventorySlot[0];
            if (this.InventorySlot[this.i] != null) {
                this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
                if (this.WeaponSlotId == this.Weapons[0]) {
                    break;
                }
                if (this.WeaponSlotId == this.Weapons[1]) {
                    this.diamondWeaponCheck();
                }
                else if (this.WeaponSlotId == this.Weapons[2]) {
                    this.diamondIronWeaponCheck();
                }
                else if (this.WeaponSlotId == this.Weapons[3]) {
                    this.diamondIronStoneWeaponCheck();
                }
                else if (this.WeaponSlotId == this.Weapons[4]) {
                    this.diamondIronStoneWoodWeaponCheck();
                }
                else {
                    this.allWeaponCheck();
                }
            }
            this.player.getInventory().setItem(0, this.WeaponSlot);
            this.InventorySlot[this.i] = null;
            ++this.i;
        }
    }
    
    private void diamondWeaponCheck() {
        if (this.InventorySlotId == this.Weapons[0]) {
            this.weaponSwitch();
        }
    }
    
    private void diamondIronWeaponCheck() {
        if (this.InventorySlotId == this.Weapons[0]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[1]) {
            this.weaponSwitch();
        }
    }
    
    private void diamondIronStoneWeaponCheck() {
        if (this.InventorySlotId == this.Weapons[0]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[1]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[2]) {
            this.weaponSwitch();
        }
    }
    
    private void diamondIronStoneWoodWeaponCheck() {
        if (this.InventorySlotId == this.Weapons[0]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[1]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[2]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[3]) {
            this.weaponSwitch();
        }
    }
    
    private void allWeaponCheck() {
        if (this.InventorySlotId == this.Weapons[0]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[1]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[2]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[3]) {
            this.weaponSwitch();
        }
        else if (this.InventorySlotId == this.Weapons[4]) {
            this.weaponSwitch();
        }
    }
    
    private void weaponSwitch() {
        this.temp = this.WeaponSlot;
        this.WeaponSlot = this.InventorySlot[this.i];
        this.InventorySlot[this.i] = this.temp;
        this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
    }
    
    private void diamondCheck() {
        if (this.InventorySlot[this.i] != null) {
            this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
            if (this.InventorySlotId == this.Diamond_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
        }
    }
    
    private void diamondIronCheck() {
        if (this.InventorySlot[this.i] != null) {
            this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
            if (this.InventorySlotId == this.Diamond_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Iron_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
        }
    }
    
    private void diamondIronChainCheck() {
        if (this.InventorySlot[this.i] != null) {
            this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
            if (this.InventorySlotId == this.Diamond_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Iron_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Chain_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
        }
    }
    
    private void diamondIronChainGoldCheck() {
        if (this.InventorySlot[this.i] != null) {
            this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
            if (this.InventorySlotId == this.Diamond_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Iron_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Chain_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
            else if (this.InventorySlotId == this.Gold_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
                this.player.getInventory().setItem(this.i, this.InventorySlot[this.i]);
            }
        }
    }
    
    private void allArmorCheck() {
        if (this.InventorySlot[this.i] != null) {
            this.InventorySlotId = this.InventorySlot[this.i].getTypeId();
            if (this.InventorySlotId == this.Diamond_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
            }
            else if (this.InventorySlotId == this.Iron_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
            }
            else if (this.InventorySlotId == this.Chain_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
            }
            else if (this.InventorySlotId == this.Gold_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
            }
            else if (this.InventorySlotId == this.Leather_Armor[this.ArmorCounter]) {
                this.temp = this.ArmorSelector;
                this.ArmorSelector = this.InventorySlot[this.i];
                this.InventorySlot[this.i] = this.temp;
            }
        }
    }
    
    private void autoCreativeDiamond() {
        final ItemStack Diamond_Helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        final ItemStack Diamond_Chest = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        final ItemStack Diamond_Legs = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        final ItemStack Diamond_Boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        this.player.getInventory().setHelmet(Diamond_Helmet);
        this.player.getInventory().setChestplate(Diamond_Chest);
        this.player.getInventory().setLeggings(Diamond_Legs);
        this.player.getInventory().setBoots(Diamond_Boots);
    }
}
