package me.espoo.quest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class GUI {
	public static void QuestListGUI(Player p, int num)
	{
		Inventory G = Bukkit.createInventory(null, 0, "����Ʈ ��� �ҷ�������..");
		p.openInventory(G);
		
		List<String> list = API.getSubNPCList(p);
		int num2 = (list.size() / 27) + 1;
		
		int i = (num - 1) * 27; i++;	// � ������ŭ ������ ������
		int y = 1;	// for�� ����
		int z = 0;
		
		Inventory GUI = Bukkit.createInventory(null, 54, "����Ʈ ��� " + num + "/" + num2);
		
		for (String npc : list)
		{
			if (y < i && num != 1) {
				y++;
				continue;
			}
			
			if (i >= ((num - 1) * 27) + 28) break;
			if (API.isSubQuestComplete(p, npc)) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7NPC �̸�:��f " + npc);
				lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
				lore.add("��7���൵: ��f���� ȹ�� ����");
				List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 =  API.getSubRewardColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ����Ʈ ��, NPC�� ��");
				lore.add("��7��, ���� �ٽú��Ⱑ �����մϴ�.");
				
				Stack("��6��l�� " + API.getSubQuestName(p, npc), 403, 0, 1, lore, z, GUI);
			} else {
				List<String> lore = new ArrayList<String>();
				lore.add("��7NPC �̸�:��f " + npc);
				lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
				lore.add("��7���൵: ��f����Ʈ ������");
				List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 =  API.getSubRewardColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ����Ʈ ��, NPC�� ��");
				lore.add("��7��, ���� �ٽú��Ⱑ �����մϴ�.");
				
				Stack("��6��l�� " + API.getSubQuestName(p, npc), 340, 0, 1, lore, z, GUI);
			}
			
			i++; z++;
		}
		
		if (list.size() > (i - 1)) {
			Stack2("��6���� ��� Ȯ��", 10, 0, 1, Arrays.asList(), 53, GUI);
		} else {
			Stack2("", 119, 0, 1, Arrays.asList(), 53, GUI);
		}
		
		if (num == 1) {
			Stack("��fâ �ݱ�", 324, 0, 1, GUIMessage.CloseGUI, 45, GUI);
		} else {
			Stack("��6���� ��� Ȯ��", 8, 0, 1, GUIMessage.BackRanking, 45, GUI);
		}
		
		int o = 27;
		for (int x = 0; x < 24; x++) {
			if (x == 13 || x == 17) o += 1;
			Stack2("", 119, 0, 1, Arrays.asList(), o, GUI);
			o++;
		}
		
		if (!API.isMainNextFinish(p)) {
			if (!API.isMainQuestIng(p)) {
				Stack2("��f��lŬ���� ���� ����Ʈ�� �ִ� NPC���Է� �̵��մϴ�.", 368, 0, 1, Arrays.asList(), 40, GUI);
			} else {
				if (API.isMainQuestComplete(p)) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7NPC �̸�:��f " + API.getMainNPCName(p));
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f���� ȹ�� ����");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ����Ʈ ��, NPC�� ��");
					lore.add("��7��, ���� �ٽú��Ⱑ �����մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 403, 0, 1, lore, 40, GUI);
				} else {
					List<String> lore = new ArrayList<String>();
					lore.add("��7NPC �̸�:��f " + API.getMainNPCName(p));
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f����Ʈ ������");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ����Ʈ ��, NPC�� ��");
					lore.add("��7��, ���� �ٽú��Ⱑ �����մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 340, 0, 1, lore, 40, GUI);
				}
			}
		} else {
			Stack2("��f��� ���� ����Ʈ�� �Ϸ��ϼ̽��ϴ�.", 323, 0, 1, Arrays.asList(), 40, GUI);
		}
		
		p.openInventory(GUI);
	}
	
	public static void NPCQuestGUILoding(Player p)
	{
		Inventory GUI = Bukkit.createInventory(null, 0, "����Ʈ ����� �ҷ����� �ֽ��ϴ�..");
		p.openInventory(GUI);
	}
	
	public static void NPCQuestInfoGUI(Player p, String npc, String type)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "����Ʈ ����: " + npc + "��0��" + type);
		
		if (ChatColor.stripColor(type).equalsIgnoreCase("����"))
			Stack("��a����Ʈ ��", 395, 0, 1, API.getMainTipList(p), 11, GUI);
		else Stack("��a����Ʈ ��", 395, 0, 1, API.getSubTipList(p, npc), 11, GUI);
		Stack("��6�ٷ� �̵�", 345, 0, 1, GUIMessage.NowMove, 13, GUI);
		Stack("��b���� �ٽú���", 323, 0, 1, GUIMessage.ReViewContents, 15, GUI);
		Stack("��7�ڷ� ����", 324, 0, 1, GUIMessage.BackInfo, 26, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void NPCQuestListGUI(Player p, String npc)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, npc);
		int is1 = 0, is2 = 0;
		if (!API.isMainNextFinish(p)) {
			if (API.getMainNPCName(p).equalsIgnoreCase(npc)) {
				if (!API.isMainQuestIng(p)) {
					if (API.getMainNPCName(p).equalsIgnoreCase(npc)) {
						is1 = 1;
						NPCQuestGUILoding(p);
					}
				} else {
					if (API.isMainQuestComplete(p)) {
						is1 = 3;
						NPCQuestGUILoding(p);
					} else {
						is1 = 2;
						NPCQuestGUILoding(p);
					}
				}	
			}
		}
		
		if (API.isSubExist(npc)) {
			if (!API.isSubQuestIng(p, npc)) {
				if (!API.isSubNextFinish(p, npc)) {
					is2 = 1;
					NPCQuestGUILoding(p);
				}
			} else {
				if (API.isSubQuestComplete(p, npc)) {
					is2 = 3;
					NPCQuestGUILoding(p);
				} else {
					is2 = 2;
					NPCQuestGUILoding(p);
				}
			}
		}

		if (is1 != 0 && is2 != 0) {
			if (is1 == 1) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f���� ����");

				List<String> list1 = API.getMainTaskColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
				
				Stack("��6��l�� " + API.getMainQuestName(p), 339, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 2) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f����Ʈ ������");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				Stack("��6��l�� " + API.getMainQuestName(p), 340, 0, 1, lore, 11, GUI);
			}
			
			else if (is1 == 3) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: ��6����");
				lore.add("��7���൵: ��f���� ȹ�� ����");

				List<String> list1 = API.getMainPlayerTaskingColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getMainRewardColor(p);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
				
				Stack("��6��l�� " + API.getMainQuestName(p), 403, 0, 1, lore, 11, GUI);
			}
			
			if (is2 == 1) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
				lore.add("��7���൵: ��f���� ����");

				List<String> list1 = API.getSubTaskColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getSubRewardColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
				
				Stack("��6��l�� " + API.getSubQuestName(p, npc), 339, 0, 1, lore, 15, GUI);
			}
			
			else if (is2 == 2) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
				lore.add("��7���൵: ��f����Ʈ ������");

				List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getSubRewardColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				Stack("��6��l�� " + API.getSubQuestName(p, npc), 340, 0, 1, lore, 15, GUI);
			}
			
			else if (is2 == 3) {
				List<String> lore = new ArrayList<String>();
				lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
				lore.add("��7���൵: ��f���� ȹ�� ����");

				List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
					lore.add("");
				}
				
				list1 = API.getSubRewardColor(p, npc);
				if (list1 == null || list1.isEmpty()) {
					lore.add("��7����: ��f����");
				} else {
					lore.add("��7 ����:");
					for (String str : list1) lore.add(str);
				}
				
				lore.add("");
				lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
				
				Stack("��6��l�� " + API.getSubQuestName(p, npc), 403, 0, 1, lore, 15, GUI);
			}
			
			p.openInventory(GUI);
		} else {
			if (is1 != 0) {
				if (is1 == 1) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f���� ����");

					List<String> list1 = API.getMainTaskColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 339, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 2) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f����Ʈ ������");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					Stack("��6��l�� " + API.getMainQuestName(p), 340, 0, 1, lore, 13, GUI);
				}
				
				else if (is1 == 3) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: ��6����");
					lore.add("��7���൵: ��f���� ȹ�� ����");

					List<String> list1 = API.getMainPlayerTaskingColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getMainRewardColor(p);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
					
					Stack("��6��l�� " + API.getMainQuestName(p), 403, 0, 1, lore, 13, GUI);
				}
				
				p.openInventory(GUI);
			}
			
			else if (is2 != 0) {
				if (is2 == 1) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
					lore.add("��7���൵: ��f���� ����");

					List<String> list1 = API.getSubTaskColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getSubRewardColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ����Ʈ�� �����մϴ�.");
					
					Stack("��6��l�� " + API.getSubQuestName(p, npc), 339, 0, 1, lore, 13, GUI);
				}
				
				else if (is2 == 2) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
					lore.add("��7���൵: ��f����Ʈ ������");

					List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getSubRewardColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					Stack("��6��l�� " + API.getSubQuestName(p, npc), 340, 0, 1, lore, 13, GUI);
				}
				
				else if (is2 == 3) {
					List<String> lore = new ArrayList<String>();
					lore.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
					lore.add("��7���൵: ��f���� ȹ�� ����");

					List<String> list1 = API.getSubPlayerTaskingColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
						lore.add("");
					}
					
					list1 = API.getSubRewardColor(p, npc);
					if (list1 == null || list1.isEmpty()) {
						lore.add("��7����: ��f����");
					} else {
						lore.add("��7 ����:");
						for (String str : list1) lore.add(str);
					}
					
					lore.add("");
					lore.add("��8��l��> ��7Ŭ���� ������ ȹ���մϴ�.");
					
					Stack("��6��l�� " + API.getSubQuestName(p, npc), 403, 0, 1, lore, 13, GUI);
				}
				
				p.openInventory(GUI);
			}
			
			else return;
		}
	}
	
	public static void QuestMainADGUI(Player p, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��6��l����Ʈ: ��0" + questname);
		List<String> lore1 = new ArrayList<String>();
		lore1.add("��7����Ʈ ��û: ��f" + API.getMainNPCName(p));
		List<String> list = API.getMainTaskColor(p);
		if (list != null && !list.isEmpty())
			for (String str : list) lore1.add(str);
		Stack("��a��o" + questname, 339, 0, 1, lore1, 13, GUI);
		
		Stack2("��aŬ���� ����Ʈ�� �����մϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��cŬ���� ����Ʈ�� �����մϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void QuestSubADGUI(Player p, String npc, String questname)
	{
		Inventory GUI = Bukkit.createInventory(null, 36, "��6��l����Ʈ: ��0" + questname);
		List<String> lore1 = new ArrayList<String>();
		lore1.add("��7����Ʈ ��û: ��f" + npc);
		lore1.add("��7����Ʈ ���: " + API.getSubRank(p, npc));
		List<String> list = API.getSubTaskColor(p, npc);
		if (list != null && !list.isEmpty())
			for (String str : list) lore1.add(str);
		Stack("��a��o" + questname, 339, 0, 1, lore1, 13, GUI);
		
		Stack2("��aŬ���� ����Ʈ�� �����մϴ�.", 35, 5, 1, Arrays.asList(), 20, GUI);
		Stack2("��cŬ���� ����Ʈ�� �����մϴ�.", 35, 14, 1, Arrays.asList(), 24, GUI);
		
		p.openInventory(GUI);
	}
	
	public static void QuestIngGUI(Player p, String questname, boolean skip)
	{
		Inventory GUI = Bukkit.createInventory(null, 27, "��6��l����Ʈ: ��0" + questname);
		Stack2("��f��l��nŬ���� ����Ʈ�� �����մϴ�.", 143, 0, 1, Arrays.asList(), 13, GUI);
		
		p.openInventory(GUI);
	}

	@SuppressWarnings("deprecation")
	public static void updateQuestIngGUI(Player p, InventoryView v, String questname, String itemname, String lore, int code, int date, boolean skip)
	{
		List<String> listlore = new ArrayList<String>();
		
		if (lore.contains(";")) {
			String[] str = lore.split(";");
			for (String strr : str) {
				listlore.add("��f" + API.replaceAllColors(strr));
			}
		} else {
			listlore.add("��f" + API.replaceAllColors(lore));
		}
		
		ItemStack item = new MaterialData(code, (byte) date).toItemStack(1);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName("��6��l�� ��f��l" + itemname);
		item_Meta.setLore(listlore);
		item.setItemMeta(item_Meta);
		v.setItem(13, item);
		
		if (skip) {
			if (v.getItem(26).getType() == Material.AIR) {
				ItemStack item2 = new MaterialData(337, (byte) 0).toItemStack(1);
				ItemMeta item_Meta2 = item2.getItemMeta();
				item_Meta2.setDisplayName("��e��l��ŵ�ϱ�");
				item_Meta2.setLore(GUIMessage.SkipLore);
				item2.setItemMeta(item_Meta2);
				v.setItem(26, item2);
			}
		}

		p.updateInventory();
	}
	
	public static void Stack(String Display, int ID, int DATA, int STACK, List<String> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item_Meta.setLore(lore);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}

	public static void Stack2(String Display, int ID, int DATA, int STACK, List<Object> lore, int loc, Inventory inv) {
		ItemStack item = new MaterialData(ID, (byte) DATA).toItemStack(STACK);
		ItemMeta item_Meta = item.getItemMeta();
		item_Meta.setDisplayName(Display);
		item.setItemMeta(item_Meta);
		inv.setItem(loc, item);
	}
	
	public static void Stack3(ItemStack item, int loc, Inventory inv) {
		inv.setItem(loc, item);
	}
}
