package me.shinkhan.trade.data;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

public class GameData {
	private final CopyOnWriteArrayList<InventoryData> inventoryDatas = new CopyOnWriteArrayList<InventoryData>();
	private int distance = -1;
	private int time = 30;

	public InventoryData getInventoryData(HumanEntity entity) {
		for (InventoryData id : this.inventoryDatas) {
			if (id.isPlayer(entity)) {
				return id;
			}
		}
		return null;
	}

	public void addInventoryData(Player player1, Player player2) {
		this.inventoryDatas.add(new InventoryData(new Player[] { player1, player2 }));
	}

	public void removeInventoryData(InventoryData id) {
		this.inventoryDatas.remove(id);
	}

	public int getDistance() {
		return this.distance;
	}

	public int getTime() {
		return this.time;
	}
}