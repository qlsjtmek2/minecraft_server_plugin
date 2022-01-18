package me.espoo.book;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class mainEvent extends JavaPlugin implements Listener {
	main M;
	GUIMessage G;
	PlayerYml P;
	
	public mainEvent(main main)
	{
		this.M = main;
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		File folder = new File("plugins/OnePunchBook");
		File folder2 = new File("plugins/OnePunchBook/Player");
		File f = new File("plugins/OnePunchBook/Player/" + p.getName() + ".yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		if (!f.exists()) P.CreatePlayerInfo(f, folder, folder2, config);
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onCancelClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;

		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (main.shop.get(p.getName()) != null) {
        	System.out.println(e.getCurrentItem().getItemMeta().getDisplayName());
        	e.setCancelled(true);
     	}

     	if (e.getInventory().getName().equalsIgnoreCase("����")) {
     		e.setCancelled(true);
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ���� ����")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�������� ��f]")) {
     			GUIXWaepon.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� �尩")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��c��b��7��l��6 ������ ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.�������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��d��6��7��l��6 �ݼ� ��Ʈ")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.�ݼӹ�Ʈ")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��e��1��7��l��6 ������")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��8��7��7��l��6 ���� �ӽŰ�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��e��c��7��l��6 ��Ż����Ʈ �̻���")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.��Ż����Ʈ")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��f��7��7��l��6 ���߸��� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.���߸�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��0��d��7��l��6 ������ �÷��� ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.�������÷���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��2��a��7��l��6 �����ϼ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ����.�ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��1��8��7��l��6 �ɹ̳� ����")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.�Ƹ��̸���ũ")) e.setCancelled(true);
     		}
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��2��3��7��l��6 ����� �繫���� ��")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.�����")) e.setCancelled(true);
         	}
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��5��a��7��l��6 ���� ��Ÿ��")) {
             	if (P.getInfoBoolean(p, "�Ϲ� ����.�������")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��c��0��7��l��6 ������ �Ҵ� Į")) {
                if (P.getInfoBoolean(p, "�Ϲ� ����.�����ǼҴ�")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��2��e��7��l��6 ���뽺�� ����")) {
                if (P.getInfoBoolean(p, "�Ϲ� ����.���뽺")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��3��9��7��l��6 ����� ����")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.�����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��4��4��7��l��6 �������� ����")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.���ձݰ�����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��5��8��7��l��6 ŷ�� ���ӱ�")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.ŷ")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��1��f��7��l��6 Ÿ����Ű ������")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.Ÿ����Ű")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��4��f��7��l��6 ��ũ�� ������ ����")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.��ũ�鸶����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��6��5��7��l��6 �ĺ�Ű ������")) {
         		if (P.getInfoBoolean(p, "�Ϲ� ����.�ĺ�Ű")) e.setCancelled(true);
            }
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Ǯ�� ���� ����")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUIWaepon.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��a��l��  ��b10�� ��6��ȭ �ֹ���  ��a��l��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��d��5��7��l��6 ������ �� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.�������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��e��0��7��l��6 �ݼ� ��Ʈ ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.�ݼӹ�Ʈ")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��e��b��7��l��6 ������ ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��9��1��7��l��6 ���� �ӽŰ� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��f��6��7��l��6 ��Ż����Ʈ �̻��� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.��Ż����Ʈ")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��0��1��7��l��6 ���߸��� ���� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.���߸�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��1��7��7��l��6 ������ �÷��� �� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.�������÷���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��3��4��7��l��6 �����ϼ�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ����.�ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��2��2��7��l��6 �ɹ̳� ���� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.�Ƹ��̸���ũ")) e.setCancelled(true);
     		}
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��2��d��7��l��6 ����� �繫���� �� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.�����")) e.setCancelled(true);
         	}
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��6��4��7��l��6 ���� ��Ÿ�� ��f+10")) {
             	if (P.getInfoBoolean(p, "Ǯ�� ����.�������")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��5��c��a��7��l��6 ������ �Ҵ� Į ��f+10")) {
                if (P.getInfoBoolean(p, "Ǯ�� ����.�����ǼҴ�")) e.setCancelled(true);
            }
     		
            else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��3��8��7��l��6 ���뽺�� ���� ��f+10")) {
                if (P.getInfoBoolean(p, "Ǯ�� ����.���뽺")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��4��3��7��l��6 ����� ���� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.�����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��4��e��7��l��6 �������� ���� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.���ձݰ�����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��6��2��7��l��6 ŷ�� ���ӱ� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.ŷ")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��2��9��7��l��6 Ÿ����Ű ������ ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.Ÿ����Ű")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��5��9��7��l��6 ��ũ�� ������ ���� ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.��ũ�鸶����")) e.setCancelled(true);
            }
     		
         	else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��6��6��f��7��l��6 �ĺ�Ű ������ ��f+10")) {
         		if (P.getInfoBoolean(p, "Ǯ�� ����.�ĺ�Ű")) e.setCancelled(true);
            }
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 1/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�������� ��f]")) {
     			GUInGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��f��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��0��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������Ʃ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��1��7��l��6 ������� ���뽺")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��2��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��5��f��7��l��6 �ݼ� ��Ʈ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��0��7��l��6 �ݼ� ��Ʈ Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�ƮƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��1��7��l��6 �ݼ� ��Ʈ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��2��7��l��6 �ݼ� ��Ʈ �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ݼӹ�Ʈ�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��f��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���Ÿ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��0��7��l��6 ���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��1��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���Ź���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��2��7��l��6 ���� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���ŽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��3��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��4��7��l��6 ���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��5��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��6��7��l��6 ���� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��f��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��0��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��Ż����ƮƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��1��7��l��6 ��Ż����Ʈ ���뽺")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��2��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��Ż����Ʈ�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��d��f��7��l��6 ���߸� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���߸Ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��0��7��l��6 ���߸� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���߸�Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��1��7��l��6 ���߸� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���߸ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��2��7��l��6 ���߸� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���߸ǽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��1��7��l��6 ������ �÷��� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ø���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��2��7��l��6 ������ �÷��� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������÷���Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��3��7��l��6 ������ �÷��� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ù���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��4��7��l��6 ������ �÷��� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������÷��ýŹ�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 2/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUInGear.openGUI1(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�������� ��f]")) {
     			GUInGear.openGUI3(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��4��3��7��l��f �ǹ��� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ظ���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��4��4��7��l��f �ǹ��� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ǹ���Ʃ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��4��5��7��l��f �ǹ��� ���뽺")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ع���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��4��6��7��l��f �ǹ��� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ǹ��ؽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��f��7��l��f �Ƹ��̸���ũ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��0��7��l��f �Ƹ��̸���ũ Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��1��7��l��f �Ƹ��̸���ũ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��2��7��l��f �Ƹ��̸���ũ �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�Ƹ��̸���ũ�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��3��f��7��l��f ����� �繫���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����͸���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��0��7��l��f ����� �繫���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��1��7��l��f ����� �繫���� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����͹���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��2��7��l��f ����� �繫���� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����ͽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��b��f��7��l��f ������ �Ҵ� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴи���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��0��7��l��f ������ �Ҵ� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴ�Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��1��7��l��f ������ �Ҵ� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴй���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��2��7��l��f ������ �Ҵ� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����ǼҴнŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��3��f��7��l��f ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���뽺����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��0��7��l��f ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���뽺Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��1��7��l��f ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���뽺����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��2��7��l��f ���뽺 �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���뽺�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��f��7��l��f ����� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����Ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��0��7��l��f ����� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��1��7��l��f ����� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��2��7��l��f ����� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.����ǽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��f��f��7��l��f ���ձ� ������ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ���������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��0��0��7��l��f ���ձ� ������ Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��0��1��7��l��f ���ձ� ������ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ���������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��0��2��7��l��f ���ձ� ������ �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.���ձݰ������Ź�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 3/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUInGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��7��f��7��l��f ŷ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.ŷ����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��0��7��l��f ŷ Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.ŷƩ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��1��7��l��f ŷ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.ŷ����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��2��7��l��f ŷ �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.ŷ�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��3��7��l��f Ÿ����Ű ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��4��7��l��f Ÿ����Ű Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����ŰƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��5��7��l��f Ÿ����Ű ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��6��7��l��f Ÿ����Ű �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.Ÿ����Ű�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��f��7��l��f ��ũ�� ������ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���͸���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��0��7��l��f ��ũ�� ������ Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��1��7��l��f ��ũ�� ������ ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���͹���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��2��7��l��f ��ũ�� ������ �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.��ũ�鸶���ͽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��f��7��l��f �������� ������� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.������ʸ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��0��7��l��f �������� ������� Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�������Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��1��7��l��f �������� ������� ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.������ʹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��2��7��l��f �������� ������� �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.������ʽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��f��7��l��f �ĺ�Ű ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��0��7��l��f �ĺ�Ű Ʃ��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�ŰƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��1��7��l��f �ĺ�Ű ����")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��2��7��l��f �ĺ�Ű �Ź�")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ���.�ĺ�Ű�Ź�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 1/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�������� ��f]")) {
     			GUIuGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��b��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��c��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������Ʃ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��d��7��l��6 ������� ���뽺")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����������")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��b��e��7��l��6 ������� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��b��7��l��6 �ݼ� ��Ʈ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��c��7��l��6 �ݼ� ��Ʈ Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�ƮƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��d��7��l��6 �ݼ� ��Ʈ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��6��e��7��l��6 �ݼ� ��Ʈ �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ݼӹ�Ʈ�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��b��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���Ÿ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��c��7��l��6 ���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��d��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���Ź���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��c��e��7��l��6 ���� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���ŽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��f��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��6��0��7��l��6 ���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��6��1��7��l��6 ���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��6��2��7��l��6 ���� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��b��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��c��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��Ż����ƮƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��d��7��l��6 ��Ż����Ʈ ���뽺")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��9��e��7��l��6 ��Ż����Ʈ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��Ż����Ʈ�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��b��7��l��6 ���߸� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���߸Ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��c��7��l��6 ���߸� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���߸�Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��d��7��l��6 ���߸� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���߸ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��e��e��7��l��6 ���߸� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���߸ǽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��d��7��l��6 ������ �÷��� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������÷��ø���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��e��7��l��6 ������ �÷��� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������÷���Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��d��f��7��l��6 ������ �÷��� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������÷��ù���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��4��e��0��7��l��6 ������ �÷��� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������÷��ýŹ�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 2/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUIuGear.openGUI1(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��e�������� ��f]")) {
     			GUIuGear.openGUI3(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��4��f��7��l��6 �ǹ��� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ظ���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��0��7��l��6 �ǹ��� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ǹ���Ʃ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��1��7��l��6 �ǹ��� ���뽺")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ع���")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��5��2��7��l��6 �ǹ��� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ǹ��ؽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��b��7��l��6 �Ƹ��̸���ũ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��c��7��l��6 �Ƹ��̸���ũ Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��d��7��l��6 �Ƹ��̸���ũ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��9��e��7��l��6 �Ƹ��̸���ũ �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�Ƹ��̸���ũ�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��b��7��l��6 ����� �繫���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����͸���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��c��7��l��6 ����� �繫���� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��d��7��l��6 ����� �繫���� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����͹���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��4��e��7��l��6 ����� �繫���� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����ͽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��b��7��l��6 ������ �Ҵ� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴи���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��c��7��l��6 ������ �Ҵ� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴ�Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��d��7��l��6 ������ �Ҵ� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴй���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��e��7��l��6 ������ �Ҵ� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����ǼҴнŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��b��7��l��6 ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���뽺����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��c��7��l��6 ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���뽺Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��d��7��l��6 ���뽺 ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���뽺����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��4��e��7��l��6 ���뽺 �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���뽺�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��b��7��l��6 ����� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����Ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��c��7��l��6 ����� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��d��7��l��6 ����� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����ǹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��a��e��7��l��6 ����� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.����ǽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��b��f��7��l��6 ���ձ� ������ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���ձݰ���������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��c��0��7��l��6 ���ձ� ������ Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���ձݰ�����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��0��d��7��l��6 ���ձ� ������ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���ձݰ���������")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��0��e��7��l��6 ���ձ� ������ �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.���ձݰ������Ź�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 3/3")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUIuGear.openGUI2(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ����Ÿ�� ��� ť��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��b��7��l��6 ŷ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.ŷ����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��c��7��l��6 ŷ Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.ŷƩ��")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��d��7��l��6 ŷ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.ŷ����")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��1��8��e��7��l��6 ŷ �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.ŷ�Ź�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��f��7��l��6 Ÿ����Ű ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��0��7��l��6 Ÿ����Ű Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.Ÿ����ŰƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��1��7��l��6 Ÿ����Ű ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��3��3��2��7��l��6 Ÿ����Ű �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.Ÿ����Ű�Ź�")) e.setCancelled(true);
     		}
     			
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��b��7��l��6 ��ũ�� ������ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͸���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��c��7��l��6 ��ũ�� ������ Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶����Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��d��7��l��6 ��ũ�� ������ ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���͹���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��7��e��7��l��6 ��ũ�� ������ �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.��ũ�鸶���ͽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��b��7��l��6 �������� ������� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.������ʸ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��c��7��l��6 �������� ������� Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�������Ʃ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��d��7��l��6 �������� ������� ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.������ʹ���")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��8��e��7��l��6 �������� ������� �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.������ʽŹ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��b��7��l��6 �ĺ�Ű ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��c��7��l��6 �ĺ�Ű Ʃ��")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ĺ�ŰƩ��")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��d��7��l��6 �ĺ�Ű ����")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű����")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��2��a��e��7��l��6 �ĺ�Ű �Ź�")) {
     			if (P.getInfoBoolean(p, "����ũ ���.�ĺ�Ű�Ź�")) e.setCancelled(true);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�� ����")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ���� �Ӽ� ��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6 ���� �Ӽ� �� ��f+10")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��0��0��7��l��c �� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.�ҼӼ�")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��5��8��7��l��b �ٶ� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.�ٶ��Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��2��1��7��l��d ġ�� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.ġ���Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��2��c��7��l��7��l ��� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.��ҼӼ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��3��7��7��l��a �� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.���Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��4��2��7��l��e ���� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.���мӼ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��4��d��7��l��3 ���� �Ӽ� ��6��")) {
     			if (P.getInfoBoolean(p, "�Ϲ� ��.�����Ӽ�")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��0��a��7��l��c �� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.�ҼӼ�")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��6��2��7��l��b �ٶ� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.�ٶ��Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��2��b��7��l��d ġ�� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.ġ���Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��3��6��7��l��7��l ��� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.��ҼӼ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��4��1��7��l��a �� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.���Ӽ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��4��c��7��l��e ���� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.���мӼ�")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��0��0��0��0��0��7��5��7��7��l��3 ���� �Ӽ� ��6�� ��f+10")) {
     			if (P.getInfoBoolean(p, "Ǯ�� ��.�����Ӽ�")) e.setCancelled(true);
     		}
     	}
     	
    	else if (e.getInventory().getName().equalsIgnoreCase("���� ����")) {
     		if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
     			e.setCancelled(true);
     		}

     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;

     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f����")) {
         		e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��7�ڷΰ��� ��f]")) {
     			GUImain.openGUI(p);
         		e.setCancelled(true);
     		}
     		
     		else if (i.getTypeId() == 389 || i.getTypeId() == 323 || i.getTypeId() == 102) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��4��l��  ��d�����̾� ��6��ȭ �ֹ���  ��4��l��")) {
     			e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ���")) {
     			if (P.getInfoBoolean(p, "����.����0")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+1")) {
     			if (P.getInfoBoolean(p, "����.����1")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+2")) {
     			if (P.getInfoBoolean(p, "����.����2")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+3")) {
     			if (P.getInfoBoolean(p, "����.����3")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+4")) {
     			if (P.getInfoBoolean(p, "����.����4")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+5")) {
     			if (P.getInfoBoolean(p, "����.����5")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+6")) {
     			if (P.getInfoBoolean(p, "����.����6")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+7")) {
     			if (P.getInfoBoolean(p, "����.����7")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+8")) {
     			if (P.getInfoBoolean(p, "����.����8")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+9")) {
     			if (P.getInfoBoolean(p, "����.����9")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��e���� ��� ��f+10")) {
     			if (P.getInfoBoolean(p, "����.����10")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ���")) {
     			if (P.getInfoBoolean(p, "����.����0")) e.setCancelled(true);
     		}

     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+1")) {
     			if (P.getInfoBoolean(p, "����.����1")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+2")) {
     			if (P.getInfoBoolean(p, "����.����2")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+3")) {
     			if (P.getInfoBoolean(p, "����.����3")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+4")) {
     			if (P.getInfoBoolean(p, "����.����4")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+5")) {
     			if (P.getInfoBoolean(p, "����.����5")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+6")) {
     			if (P.getInfoBoolean(p, "����.����6")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+7")) {
     			if (P.getInfoBoolean(p, "����.����7")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+8")) {
     			if (P.getInfoBoolean(p, "����.����8")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+9")) {
     			if (P.getInfoBoolean(p, "����.����9")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��6���� ��� ��f+10")) {
     			if (P.getInfoBoolean(p, "����.����10")) e.setCancelled(true);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��b�����͸� ���")) {
     			if (P.getInfoBoolean(p, "����.�����͸�")) e.setCancelled(true);
     		}
     	}
	}
	
	@SuppressWarnings("static-access")
	@EventHandler
    public void onClick(InventoryClickEvent e) {
     	if (e.getCurrentItem() == null) return;
		HumanEntity h = e.getView().getPlayer();
		Player p = Bukkit.getPlayerExact(h.getName());
     	ItemStack i = e.getCurrentItem();
     	
     	if (e.getInventory().getName().equalsIgnoreCase("����")) {
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��c���� ��6���� ��f]")) {
     			GUIWaepon.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��f�Ϲ� ��b��� ��6���� ��f]")) {
     			GUInGear.openGUI1(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��6����ũ ��b��� ��6���� ��f]")) {
     			GUIuGear.openGUI1(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��a���� ��6���� ��f]")) {
     			GUITool.openGUI(p);
     		}
     		
     		else if (i.getItemMeta().getDisplayName().equalsIgnoreCase("��f[ ��d�� ��6���� ��f]")) {
     			GUILoon.openGUI(p);
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ���� ����")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("��������")) {
     				Method.voidWaepon(p, e, str, c, "�������");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ")) {
     				Method.voidWaepon(p, e, str, c, "�ݼӹ�Ʈ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������")) {
     				Method.voidWaepon(p, e, str, c, "����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ӽŰ�")) {
     				Method.voidWaepon(p, e, str, c, "����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ�̻���")) {
     				Method.voidWaepon(p, e, str, c, "��Ż����Ʈ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸��Ǽ���")) {
     				Method.voidWaepon(p, e, str, c, "���߸�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ð�")) {
     				Method.voidWaepon(p, e, str, c, "�������÷���");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ϼ��")) {
     				Method.voidWaepon(p, e, str, c, "�ǹ���");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ɹ̳�����")) {
     				Method.voidWaepon(p, e, str, c, "�Ƹ��̸���ũ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̰�")) {
     				Method.voidWaepon(p, e, str, c, "�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴ�Į")) {
     				Method.voidWaepon(p, e, str, c, "�����ǼҴ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺������")) {
     				Method.voidWaepon(p, e, str, c, "���뽺");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǵ���")) {
     				Method.voidWaepon(p, e, str, c, "�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������ǿ���")) {
     				Method.voidWaepon(p, e, str, c, "���ձݰ�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ�ǰ��ӱ�")) {
     				Method.voidWaepon(p, e, str, c, "ŷ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű������")) {
     				Method.voidWaepon(p, e, str, c, "Ÿ����Ű");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���ͱ���")) {
     				Method.voidWaepon(p, e, str, c, "��ũ�鸶����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������Ÿ��")) {
     				Method.voidWaepon(p, e, str, c, "�������");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű������")) {
     				Method.voidWaepon(p, e, str, c, "�ĺ�Ű");
     			}
     			
     			if (Method.isWaepon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ������ give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "�Ϲ� ����.���� ȹ��", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("��6�̰�ȭ ���� ������ ��� ä������Ƿ� ��c����Ÿ�� �尩 ��6�� ���޵Ǿ����ϴ�.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("Ǯ�� ���� ����")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("��������+10")) {
     				Method.voidXWaepon(p, e, str, c, "�������");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ+10")) {
     				Method.voidXWaepon(p, e, str, c, "�ݼӹ�Ʈ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+10")) {
     				Method.voidXWaepon(p, e, str, c, "����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ӽŰ�+10")) {
     				Method.voidXWaepon(p, e, str, c, "����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ�̻���+10")) {
     				Method.voidXWaepon(p, e, str, c, "��Ż����Ʈ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸��Ǽ���+10")) {
     				Method.voidXWaepon(p, e, str, c, "���߸�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ð�+10")) {
     				Method.voidXWaepon(p, e, str, c, "�������÷���");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ϼ��+10")) {
     				Method.voidXWaepon(p, e, str, c, "�ǹ���");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ɹ̳�����+10")) {
     				Method.voidXWaepon(p, e, str, c, "�Ƹ��̸���ũ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̰�+10")) {
     				Method.voidXWaepon(p, e, str, c, "�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴ�Į+10")) {
     				Method.voidXWaepon(p, e, str, c, "�����ǼҴ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺������+10")) {
     				Method.voidXWaepon(p, e, str, c, "���뽺");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǵ���+10")) {
     				Method.voidXWaepon(p, e, str, c, "�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������ǿ���+10")) {
     				Method.voidXWaepon(p, e, str, c, "���ձݰ�����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ�ǰ��ӱ�+10")) {
     				Method.voidXWaepon(p, e, str, c, "ŷ");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű������+10")) {
     				Method.voidXWaepon(p, e, str, c, "Ÿ����Ű");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���ͱ���+10")) {
     				Method.voidXWaepon(p, e, str, c, "��ũ�鸶����");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������Ÿ��+10")) {
     				Method.voidXWaepon(p, e, str, c, "�������");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű������+10")) {
     				Method.voidXWaepon(p, e, str, c, "�ĺ�Ű");
     			}
     			
     			if (Method.isXWaepon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
     						}
     						
     						ItemStack item = me.espoo.upgrade.main.getUpGrade();
    						ItemMeta meta = item.getItemMeta();
    						String st = meta.getDisplayName().replaceAll("<num>", "10");
    						List<String> lore = new ArrayList<String>();
    						for (String str : item.getItemMeta().getLore()) {
    							if (str.contains("<num>")) {
    								str = str.replace("<num>", "10");
    							} lore.add(str);
    						}
    						
    						meta.setDisplayName(st);
    						meta.setLore(lore);
    						item.setItemMeta(meta);
    		                p.getInventory().addItem(new ItemStack[] { item });
     	     				P.setInfoBoolean(p, "Ǯ�� ����.���� ȹ��", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("��6Ǯ��ȭ ���� ������ ��� ä������Ƿ� ��c10�� ��ȭ �ֹ��� ��6�� ���޵Ǿ����ϴ�.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 1/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("�Ϲ�")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("�����������")) {
         				Method.voidnGear1(p, e, str, c, "����������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("������簩��")) {
         				Method.voidnGear1(p, e, str, c, "�������Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("������緹�뽺")) {
         				Method.voidnGear1(p, e, str, c, "����������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����������")) {
         				Method.voidnGear1(p, e, str, c, "�������Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ����")) {
         				Method.voidnGear1(p, e, str, c, "�ݼӹ�Ʈ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�ƮƩ��")) {
         				Method.voidnGear1(p, e, str, c, "�ݼӹ�ƮƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ����")) {
         				Method.voidnGear1(p, e, str, c, "�ݼӹ�Ʈ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ�Ź�")) {
         				Method.voidnGear1(p, e, str, c, "�ݼӹ�Ʈ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ÿ���")) {
         				Method.voidnGear1(p, e, str, c, "���Ÿ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ʃ��")) {
         				Method.voidnGear1(p, e, str, c, "����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ź���")) {
         				Method.voidnGear1(p, e, str, c, "���Ź���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ŽŹ�")) {
         				Method.voidnGear1(p, e, str, c, "���ŽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������")) {
         				Method.voidnGear1(p, e, str, c, "��������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ʃ��")) {
         				Method.voidnGear1(p, e, str, c, "����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������")) {
         				Method.voidnGear1(p, e, str, c, "��������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ź�")) {
         				Method.voidnGear1(p, e, str, c, "�����Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voidnGear1(p, e, str, c, "��Ż����Ʈ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voidnGear1(p, e, str, c, "��Ż����ƮƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ���뽺")) {
         				Method.voidnGear1(p, e, str, c, "��Ż����Ʈ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voidnGear1(p, e, str, c, "��Ż����Ʈ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸Ǹ���")) {
         				Method.voidnGear1(p, e, str, c, "���߸Ǹ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸�Ʃ��")) {
         				Method.voidnGear1(p, e, str, c, "���߸�Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸ǹ���")) {
         				Method.voidnGear1(p, e, str, c, "���߸ǹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸ǽŹ�")) {
         				Method.voidnGear1(p, e, str, c, "���߸ǽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ø���")) {
         				Method.voidnGear1(p, e, str, c, "�������÷��ø���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷���Ʃ��")) {
         				Method.voidnGear1(p, e, str, c, "�������÷���Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ù���")) {
         				Method.voidnGear1(p, e, str, c, "�������÷��ù���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ýŹ�")) {
         				Method.voidnGear1(p, e, str, c, "�������÷��ýŹ�");
         			}
         			

         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ���Ϲ�ť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "�Ϲ� ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6�Ϲ� ��� ������ ��� ä������Ƿ� ��c����Ÿ�� �Ϲ� ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 2/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("�Ϲ�")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ظ���")) {
         				Method.voidnGear2(p, e, str, c, "�ǹ��ظ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ���Ʃ��")) {
         				Method.voidnGear2(p, e, str, c, "�ǹ���Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ط��뽺")) {
         				Method.voidnGear2(p, e, str, c, "�ǹ��ع���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ؽŹ�")) {
         				Method.voidnGear2(p, e, str, c, "�ǹ��ؽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ����")) {
         				Method.voidnGear2(p, e, str, c, "�Ƹ��̸���ũ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũƩ��")) {
         				Method.voidnGear2(p, e, str, c, "�Ƹ��̸���ũƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ����")) {
         				Method.voidnGear2(p, e, str, c, "�Ƹ��̸���ũ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ�Ź�")) {
         				Method.voidnGear2(p, e, str, c, "�Ƹ��̸���ũ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̸���")) {
         				Method.voidnGear2(p, e, str, c, "����͸���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫����Ʃ��")) {
         				Method.voidnGear2(p, e, str, c, "�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̹���")) {
         				Method.voidnGear2(p, e, str, c, "����͹���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̽Ź�")) {
         				Method.voidnGear2(p, e, str, c, "����ͽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴи���")) {
         				Method.voidnGear2(p, e, str, c, "�����ǼҴи���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴ�Ʃ��")) {
         				Method.voidnGear2(p, e, str, c, "�����ǼҴ�Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴй���")) {
         				Method.voidnGear2(p, e, str, c, "�����ǼҴй���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴнŹ�")) {
         				Method.voidnGear2(p, e, str, c, "�����ǼҴнŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voidnGear2(p, e, str, c, "���뽺����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voidnGear2(p, e, str, c, "���뽺Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voidnGear2(p, e, str, c, "���뽺����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺�Ź�")) {
         				Method.voidnGear2(p, e, str, c, "���뽺�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ǹ���")) {
         				Method.voidnGear2(p, e, str, c, "����Ǹ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ʃ��")) {
         				Method.voidnGear2(p, e, str, c, "�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǹ���")) {
         				Method.voidnGear2(p, e, str, c, "����ǹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǽŹ�")) {
         				Method.voidnGear2(p, e, str, c, "����ǽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ���������")) {
         				Method.voidnGear2(p, e, str, c, "���ձݰ���������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ�����Ʃ��")) {
         				Method.voidnGear2(p, e, str, c, "���ձݰ�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ���������")) {
         				Method.voidnGear2(p, e, str, c, "���ձݰ���������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ������Ź�")) {
         				Method.voidnGear2(p, e, str, c, "���ձݰ������Ź�");
         			}
         			
         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ���Ϲ�ť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "�Ϲ� ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6�Ϲ� ��� ������ ��� ä������Ƿ� ��c����Ÿ�� �Ϲ� ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�Ϲ� ��� ���� 3/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("�Ϲ�")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ����")) {
         				Method.voidnGear3(p, e, str, c, "ŷ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷƩ��")) {
         				Method.voidnGear3(p, e, str, c, "ŷƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ����")) {
         				Method.voidnGear3(p, e, str, c, "ŷ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ�Ź�")) {
         				Method.voidnGear3(p, e, str, c, "ŷ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű����")) {
         				Method.voidnGear3(p, e, str, c, "Ÿ����Ű����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����ŰƩ��")) {
         				Method.voidnGear3(p, e, str, c, "Ÿ����ŰƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű����")) {
         				Method.voidnGear3(p, e, str, c, "Ÿ����Ű����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű�Ź�")) {
         				Method.voidnGear3(p, e, str, c, "Ÿ����Ű�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���͸���")) {
         				Method.voidnGear3(p, e, str, c, "��ũ�鸶���͸���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶����Ʃ��")) {
         				Method.voidnGear3(p, e, str, c, "��ũ�鸶����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���͹���")) {
         				Method.voidnGear3(p, e, str, c, "��ũ�鸶���͹���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���ͽŹ�")) {
         				Method.voidnGear3(p, e, str, c, "��ũ�鸶���ͽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʸ���")) {
         				Method.voidnGear3(p, e, str, c, "������ʸ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���������������Ʃ��")) {
         				Method.voidnGear3(p, e, str, c, "�������Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʹ���")) {
         				Method.voidnGear3(p, e, str, c, "������ʹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʽŹ�")) {
         				Method.voidnGear3(p, e, str, c, "������ʽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű����")) {
         				Method.voidnGear3(p, e, str, c, "�ĺ�Ű����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�ŰƩ��")) {
         				Method.voidnGear3(p, e, str, c, "�ĺ�ŰƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű����")) {
         				Method.voidnGear3(p, e, str, c, "�ĺ�Ű����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű�Ź�")) {
         				Method.voidnGear3(p, e, str, c, "�ĺ�Ű�Ź�");
         			}         			

         			if (Method.isnGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ���Ϲ�ť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "�Ϲ� ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6�Ϲ� ��� ������ ��� ä������Ƿ� ��c����Ÿ�� �Ϲ� ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 1/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("����ũ")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("�����������")) {
         				Method.voiduGear1(p, e, str, c, "����������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("������簩��")) {
         				Method.voiduGear1(p, e, str, c, "�������Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("������緹�뽺")) {
         				Method.voiduGear1(p, e, str, c, "����������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����������")) {
         				Method.voiduGear1(p, e, str, c, "�������Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ����")) {
         				Method.voiduGear1(p, e, str, c, "�ݼӹ�Ʈ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�ƮƩ��")) {
         				Method.voiduGear1(p, e, str, c, "�ݼӹ�ƮƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ����")) {
         				Method.voiduGear1(p, e, str, c, "�ݼӹ�Ʈ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ݼӹ�Ʈ�Ź�")) {
         				Method.voiduGear1(p, e, str, c, "�ݼӹ�Ʈ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ÿ���")) {
         				Method.voiduGear1(p, e, str, c, "���Ÿ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ʃ��")) {
         				Method.voiduGear1(p, e, str, c, "����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ź���")) {
         				Method.voiduGear1(p, e, str, c, "���Ź���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ŽŹ�")) {
         				Method.voiduGear1(p, e, str, c, "���ŽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������")) {
         				Method.voiduGear1(p, e, str, c, "��������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ʃ��")) {
         				Method.voiduGear1(p, e, str, c, "����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������")) {
         				Method.voiduGear1(p, e, str, c, "��������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ź�")) {
         				Method.voiduGear1(p, e, str, c, "�����Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voiduGear1(p, e, str, c, "��Ż����Ʈ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voiduGear1(p, e, str, c, "��Ż����ƮƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ���뽺")) {
         				Method.voiduGear1(p, e, str, c, "��Ż����Ʈ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��Ż����Ʈ����")) {
         				Method.voiduGear1(p, e, str, c, "��Ż����Ʈ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸Ǹ���")) {
         				Method.voiduGear1(p, e, str, c, "���߸Ǹ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸�Ʃ��")) {
         				Method.voiduGear1(p, e, str, c, "���߸�Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸ǹ���")) {
         				Method.voiduGear1(p, e, str, c, "���߸ǹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���߸ǽŹ�")) {
         				Method.voiduGear1(p, e, str, c, "���߸ǽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ø���")) {
         				Method.voiduGear1(p, e, str, c, "�������÷��ø���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷���Ʃ��")) {
         				Method.voiduGear1(p, e, str, c, "�������÷���Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ù���")) {
         				Method.voiduGear1(p, e, str, c, "�������÷��ù���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�������÷��ýŹ�")) {
         				Method.voiduGear1(p, e, str, c, "�������÷��ýŹ�");
         			}
         			

         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ������ũť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "����ũ ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6����ũ ��� ������ ��� ä������Ƿ� ��c����Ÿ�� ����ũ ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 2/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("����ũ")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ظ���")) {
         				Method.voiduGear2(p, e, str, c, "�ǹ��ظ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ���Ʃ��")) {
         				Method.voiduGear2(p, e, str, c, "�ǹ���Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ط��뽺")) {
         				Method.voiduGear2(p, e, str, c, "�ǹ��ع���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ǹ��ؽŹ�")) {
         				Method.voiduGear2(p, e, str, c, "�ǹ��ؽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ����")) {
         				Method.voiduGear2(p, e, str, c, "�Ƹ��̸���ũ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũƩ��")) {
         				Method.voiduGear2(p, e, str, c, "�Ƹ��̸���ũƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ����")) {
         				Method.voiduGear2(p, e, str, c, "�Ƹ��̸���ũ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�Ƹ��̸���ũ�Ź�")) {
         				Method.voiduGear2(p, e, str, c, "�Ƹ��̸���ũ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̸���")) {
         				Method.voiduGear2(p, e, str, c, "����͸���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫����Ʃ��")) {
         				Method.voiduGear2(p, e, str, c, "�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̹���")) {
         				Method.voiduGear2(p, e, str, c, "����͹���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ͻ繫���̽Ź�")) {
         				Method.voiduGear2(p, e, str, c, "����ͽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴи���")) {
         				Method.voiduGear2(p, e, str, c, "�����ǼҴи���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴ�Ʃ��")) {
         				Method.voiduGear2(p, e, str, c, "�����ǼҴ�Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴй���")) {
         				Method.voiduGear2(p, e, str, c, "�����ǼҴй���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����ǼҴнŹ�")) {
         				Method.voiduGear2(p, e, str, c, "�����ǼҴнŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voiduGear2(p, e, str, c, "���뽺����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voiduGear2(p, e, str, c, "���뽺Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺����")) {
         				Method.voiduGear2(p, e, str, c, "���뽺����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���뽺�Ź�")) {
         				Method.voiduGear2(p, e, str, c, "���뽺�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����Ǹ���")) {
         				Method.voiduGear2(p, e, str, c, "����Ǹ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ʃ��")) {
         				Method.voiduGear2(p, e, str, c, "�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǹ���")) {
         				Method.voiduGear2(p, e, str, c, "����ǹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("����ǽŹ�")) {
         				Method.voiduGear2(p, e, str, c, "����ǽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ���������")) {
         				Method.voiduGear2(p, e, str, c, "���ձݰ���������");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ�����Ʃ��")) {
         				Method.voiduGear2(p, e, str, c, "���ձݰ�����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ���������")) {
         				Method.voiduGear2(p, e, str, c, "���ձݰ���������");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ձݰ������Ź�")) {
         				Method.voiduGear2(p, e, str, c, "���ձݰ������Ź�");
         			}
         			
         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ������ũť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "����ũ ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6����ũ ��� ������ ��� ä������Ƿ� ��c����Ÿ�� ����ũ ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("����ũ ��� ���� 3/3")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		if (!i.getItemMeta().hasLore()) return;
     		if (!c.getItemMeta().hasLore()) return;
     		boolean is = false;
     		
     		for (String str : c.getItemMeta().getLore()) {
     			if (str.contains("����ũ")) {
     				is = true; break;
     			}
     		}
     		
     		if (is) {
     			String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");

         		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
         			if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ����")) {
         				Method.voiduGear3(p, e, str, c, "ŷ����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷƩ��")) {
         				Method.voiduGear3(p, e, str, c, "ŷƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ����")) {
         				Method.voiduGear3(p, e, str, c, "ŷ����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("ŷ�Ź�")) {
         				Method.voiduGear3(p, e, str, c, "ŷ�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű����")) {
         				Method.voiduGear3(p, e, str, c, "Ÿ����Ű����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����ŰƩ��")) {
         				Method.voiduGear3(p, e, str, c, "Ÿ����ŰƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű����")) {
         				Method.voiduGear3(p, e, str, c, "Ÿ����Ű����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("Ÿ����Ű�Ź�")) {
         				Method.voiduGear3(p, e, str, c, "Ÿ����Ű�Ź�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���͸���")) {
         				Method.voiduGear3(p, e, str, c, "��ũ�鸶���͸���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶����Ʃ��")) {
         				Method.voiduGear3(p, e, str, c, "��ũ�鸶����Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���͹���")) {
         				Method.voiduGear3(p, e, str, c, "��ũ�鸶���͹���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ũ�鸶���ͽŹ�")) {
         				Method.voiduGear3(p, e, str, c, "��ũ�鸶���ͽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʸ���")) {
         				Method.voiduGear3(p, e, str, c, "������ʸ���");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("���������������Ʃ��")) {
         				Method.voiduGear3(p, e, str, c, "�������Ʃ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʹ���")) {
         				Method.voiduGear3(p, e, str, c, "������ʹ���");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("��������������ʽŹ�")) {
         				Method.voiduGear3(p, e, str, c, "������ʽŹ�");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű����")) {
         				Method.voiduGear3(p, e, str, c, "�ĺ�Ű����");
         			}
         			
         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�ŰƩ��")) {
         				Method.voiduGear3(p, e, str, c, "�ĺ�ŰƩ��");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű����")) {
         				Method.voiduGear3(p, e, str, c, "�ĺ�Ű����");
         			}

         			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ĺ�Ű�Ź�")) {
         				Method.voiduGear3(p, e, str, c, "�ĺ�Ű�Ź�");
         			}         			

         			if (Method.isuGear(p)) {
         				Timer timer1 = new Timer();
         				Date timeToRun = new Date(System.currentTimeMillis() + 600);
         				timer1.schedule(new TimerTask() {
         					public void run() {
         						ItemStack[] contents; int num = 0;
         						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
         							ItemStack it = contents[j];
         							if (it == null) {
         								num++;
         							}
         						}
         						
         						if (num < 1) {
         							int z = new Random().nextInt(26) + 9;
         							ItemStack item = p.getInventory().getItem(z);
         							p.getWorld().dropItemNaturally(p.getLocation(), item);
         							p.getInventory().setItem(z, null);
         							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
         						}
         						
         						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ÿ������ũť�� give " + p.getName() + " 1");
         	     				P.setInfoBoolean(p, "����ũ ���.���� ȹ��", true);
         	     				p.closeInventory();
         	     				Method.castLvup(p);
         	     				p.sendMessage("");
         	     				p.sendMessage("��6����ũ ��� ������ ��� ä������Ƿ� ��c����Ÿ�� ����ũ ��� ť�� ��6�� ���޵Ǿ����ϴ�.");
         	     				p.sendMessage("");
         						return;
         					}
         				}, timeToRun);
         			}
         		}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("�� ����")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("�ҼӼ���")) {
     				Method.voidLoon(p, e, str, c, "�ҼӼ�");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ٶ��Ӽ���")) {
     				Method.voidLoon(p, e, str, c, "�ٶ��Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("ġ���Ӽ���")) {
     				Method.voidLoon(p, e, str, c, "ġ���Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ҼӼ���")) {
     				Method.voidLoon(p, e, str, c, "��ҼӼ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ӽ���")) {
     				Method.voidLoon(p, e, str, c, "���Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���мӼ���")) {
     				Method.voidLoon(p, e, str, c, "���мӼ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ӽ���")) {
     				Method.voidLoon(p, e, str, c, "�����Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ҼӼ���+10")) {
     				Method.voidXLoon(p, e, str, c, "�ҼӼ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�ٶ��Ӽ���+10")) {
     				Method.voidXLoon(p, e, str, c, "�ٶ��Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("ġ���Ӽ���+10")) {
     				Method.voidXLoon(p, e, str, c, "ġ���Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("��ҼӼ���+10")) {
     				Method.voidXLoon(p, e, str, c, "��ҼӼ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���Ӽ���+10")) {
     				Method.voidXLoon(p, e, str, c, "���Ӽ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���мӼ���+10")) {
     				Method.voidXLoon(p, e, str, c, "���мӼ�");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����Ӽ���+10")) {
     				Method.voidXLoon(p, e, str, c, "�����Ӽ�");
     			}

     			if (Method.isLoon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem ����Ӽ� give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "�Ϲ� ��.���� ȹ��", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("��6�̰�ȭ �� ������ ��� ä������Ƿ� ��c���� �Ӽ� �� ��6�� ���޵Ǿ����ϴ�.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     			
     			if (Method.isXLoon(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
     						}
     						
     						Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "rpgitem 10����Ӽ� give " + p.getName() + " 1");
     	     				P.setInfoBoolean(p, "Ǯ�� ��.���� ȹ��", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("��6Ǯ�� �� ������ ��� ä������Ƿ� ��c���� �Ӽ� �� +10 ��6�� ���޵Ǿ����ϴ�.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
     	
     	else if (e.getInventory().getName().equalsIgnoreCase("���� ����")) {
     		if (e.getCursor() == null) return;
     		ItemStack c = e.getCursor();
     		if (!i.hasItemMeta()) return;
     		if (!i.getItemMeta().hasDisplayName()) return;
     		if (!c.hasItemMeta()) return;
     		if (!c.getItemMeta().hasDisplayName()) return;
     		String str = ChatColor.stripColor(i.getItemMeta().getDisplayName()).replaceAll(" ����", "");
     		
     		if (str.equalsIgnoreCase(ChatColor.stripColor(c.getItemMeta().getDisplayName()))) {
     			if (str.replaceAll(" ", "").equalsIgnoreCase("������")) {
     				Method.voidTool(p, e, str, c, "����0");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+1")) {
     				Method.voidTool(p, e, str, c, "����1");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+2")) {
     				Method.voidTool(p, e, str, c, "����2");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+3")) {
     				Method.voidTool(p, e, str, c, "����3");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+4")) {
     				Method.voidTool(p, e, str, c, "����4");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+5")) {
     				Method.voidTool(p, e, str, c, "����5");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+6")) {
     				Method.voidTool(p, e, str, c, "����6");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+7")) {
     				Method.voidTool(p, e, str, c, "����7");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+8")) {
     				Method.voidTool(p, e, str, c, "����8");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+9")) {
     				Method.voidTool(p, e, str, c, "����9");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("������+10")) {
     				Method.voidTool(p, e, str, c, "����10");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��")) {
     				Method.voidTool(p, e, str, c, "����0");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+1")) {
     				Method.voidTool(p, e, str, c, "����1");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+2")) {
     				Method.voidTool(p, e, str, c, "����2");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+3")) {
     				Method.voidTool(p, e, str, c, "����3");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+4")) {
     				Method.voidTool(p, e, str, c, "����4");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+5")) {
     				Method.voidTool(p, e, str, c, "����5");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+6")) {
     				Method.voidTool(p, e, str, c, "����6");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+7")) {
     				Method.voidTool(p, e, str, c, "����7");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+8")) {
     				Method.voidTool(p, e, str, c, "����8");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+9")) {
     				Method.voidTool(p, e, str, c, "����9");
     			}

     			else if (str.replaceAll(" ", "").equalsIgnoreCase("���ɰ��+10")) {
     				Method.voidTool(p, e, str, c, "����10");
     			}
     			
     			else if (str.replaceAll(" ", "").equalsIgnoreCase("�����͸����")) {
     				Method.voidTool(p, e, str, c, "�����͸�");
     			}
     			
     			if (Method.isTool(p)) {
     				Timer timer1 = new Timer();
     				Date timeToRun = new Date(System.currentTimeMillis() + 600);
     				timer1.schedule(new TimerTask() {
     					public void run() {
     						ItemStack[] contents; int num = 0;
     						for (int length = (contents = p.getInventory().getContents()).length, j = 0; j < length; ++j) {
     							ItemStack it = contents[j];
     							if (it == null) {
     								num++;
     							}
     						}
     						
     						if (num < 1) {
     							int z = new Random().nextInt(26) + 9;
     							ItemStack item = p.getInventory().getItem(z);
     							p.getWorld().dropItemNaturally(p.getLocation(), item);
     							p.getInventory().setItem(z, null);
     							p.sendMessage("��c�κ��丮�� ������ ������ �κ��丮���� �������� �������� ����Ʈ���� ���������� �Ǿ����ϴ�.");
     						}
     						
     					    ItemStack item = new ItemStack(Material.PAPER);
     					    ItemMeta gm = item.getItemMeta();
     					    gm.setDisplayName("��4��l��  ��d�����̾� ��6��ȭ �ֹ���  ��4��l��");
     					    List<String> list = new ArrayList<String>();
     					    list.add("��7��l=================");
     					    list.add("��f�����̾� ��ȭ �ֹ����̴�.");
     					    list.add("��f�� �������� ��� ��ȭ");
     					    list.add("��f�ϰ� ���� �����ۿ� �÷�");
     					    list.add("��f�θ� �� �������� ��ȭ�ȴ�.");
     					    list.add("");
     					    list.add("��7Ư�� �ɷ�: ��f��ȭ �� ����");
     					    list.add("��f���� ��c�ı���f���� �ʰ�, ��ȭ");
     					    list.add("��f���� Ȯ���� ���� ��b��¡�f�Ѵ�.");
     					    list.add("��7��l=================");
     					    
     					    gm.setLore(list);
     					    item.setAmount(50);
     					    item.setItemMeta(gm);
     						p.getInventory().addItem(item);
     						
     	     				P.setInfoBoolean(p, "����.���� ȹ��", true);
     	     				p.closeInventory();
     	     				Method.castLvup(p);
     	     				p.sendMessage("");
     	     				p.sendMessage("��6���� ������ ��� ä������Ƿ� ��c�����̾� ��ȭ �ֹ��� 50�� ��6�� ���޵Ǿ����ϴ�.");
     	     				p.sendMessage("");
     						return;
     					}
     				}, timeToRun);
     			}
     		}
     	}
	}
}
