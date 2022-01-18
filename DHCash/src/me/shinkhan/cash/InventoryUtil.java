package me.shinkhan.cash;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {
	public static int getAmount(ItemStack item, Inventory inventory) {
		if (!inventory.contains(item.getType())) {
			return 0;
		}

		if (inventory.getType() == null) {
			return 2147483647;
		}

		HashMap<Integer, ? extends ItemStack> items = inventory.all(item.getType());
		int itemAmount = 0;

		for (ItemStack iStack : items.values()) {
			if (!equals(iStack, item)) {
				continue;
			}
			itemAmount += iStack.getAmount();
		}

		return itemAmount;
	}

	public static boolean isEmpty(Inventory inventory) {
		for (ItemStack stack : inventory.getContents()) {
			if (!isEmpty(stack)) {
				return false;
			}
		}

		return true;
	}

	public static boolean hasItems(ItemStack[] items, Inventory inventory) {
		ItemStack[] arrayOfItemStack = items;
		int j = items.length;
		for (int i = 0; i < j; i++) {
			ItemStack item = arrayOfItemStack[i];
			if (!inventory.containsAtLeast(item, item.getAmount())) {
				return false;
			}
		}

		return true;
	}

	public static boolean isFits(ItemStack item, Inventory inventory) {
		int left = item.getAmount();

		if (inventory.getMaxStackSize() == 2147483647) {
			return true;
		}

		for (ItemStack iStack : inventory.getContents()) {
			if (left <= 0) {
				return true;
			}

			if (isEmpty(iStack)) {
				left -= item.getMaxStackSize();
			} else {
				if (!equals(iStack, item)) {
					continue;
				}
				left -= iStack.getMaxStackSize() - iStack.getAmount();
			}
		}
		return left <= 0;
	}

	public static int addItem(ItemStack item, Inventory inventory) {
		if (item.getAmount() < 1) {
			return 0;
		}
		int maxStackSize = item.getMaxStackSize();

		int amountLeft = item.getAmount();

		for (int currentSlot = 0; (currentSlot < inventory.getSize()) && (amountLeft > 0);) {
			ItemStack currentItem = inventory.getItem(currentSlot);
			ItemStack duplicate = item.clone();

			if (isEmpty(currentItem)) {
				duplicate.setAmount(Math.min(amountLeft, maxStackSize));
				duplicate.addUnsafeEnchantments(item.getEnchantments());

				amountLeft -= duplicate.getAmount();

				inventory.setItem(currentSlot, duplicate);
			} else if ((currentItem.getAmount() < maxStackSize) && (equals(currentItem, item))) {
				int currentAmount = currentItem.getAmount();
				int neededToAdd = Math.min(maxStackSize - currentAmount, amountLeft);

				duplicate.setAmount(currentAmount + neededToAdd);
				duplicate.addEnchantments(item.getEnchantments());

				amountLeft -= neededToAdd;

				inventory.setItem(currentSlot, duplicate);
			}
			currentSlot++;
		}

		return amountLeft;
	}

	public static void removeItem(ItemStack item, Inventory inventory) {
		inventory.removeItem(new ItemStack[] { item });
	}

	public static boolean isEmpty(ItemStack item) {
		return (item == null) || (item.getType() == Material.AIR);
	}

	public static boolean equals(ItemStack one, ItemStack two) {
		return one.isSimilar(two);
	}
}