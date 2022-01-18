package me.espoo.rpg.party;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Party {
	public static HashMap<String, Party> players = new HashMap<String, Party>();
	public static List<Party> partylist = new ArrayList<Party>();
    private String reader;
    private String jointype;
    private List<String> lore;
    private List<String> list;
    private boolean isexp;
    
    public Party(String reader) {
        List<String> list = new ArrayList<String>();
        List<String> list2 = new ArrayList<String>();
		list.add(reader);
		list2.add("��f/��Ƽ -> ��Ƽ �ɼ� -> ��Ƽ ���� ���� ��7����");
		list2.add("��7��� Ŭ�� �� ������ ������ �ֽñ� �ٶ��ϴ�.");
        this.reader = reader;
        this.list = list;
        this.lore = list2;
        this.isexp = true;
        this.jointype = "����";
    }
    
    public void addParty() {
    	if (!players.containsKey(reader))
    		players.put(reader, this);
        if (!partylist.contains(this))
        	partylist.add(this);
    }
    
    public void removeParty() {
    	if (players.containsKey(reader))
    		players.remove(reader);
        if (partylist.contains(this))
        	partylist.remove(this);
    }
    
    public void changePartyReader(String newreader) {
        if (partylist.contains(this)) {
        	partylist.remove(this);
        	this.reader = newreader;
        	partylist.add(this);
        }
    }
    
    public String getName() {
    	return this.reader;
    }
    
	public String getJoinType() {
		return this.jointype;
	}
    
    public void setJoinType(String jointype) {
    	this.jointype = jointype;
    }
    
    public boolean getIsExp() {
    	return this.isexp;
    }
    
    public void setIsExp(boolean isexp) {
    	this.isexp = isexp;
    }
    
    public List<String> getLore() {
    	return this.lore;
    }
    
    public void setLore(List<String> lore) {
    	this.lore = lore;
    }
    
    public List<String> getUsers() {
    	return this.list;
    }
    
    public void addUser(OfflinePlayer user) {
    	List<String> list = this.list;
    	list.add(user.getName());
    	this.list = list;
    	if (players.containsKey(reader))
    		players.remove(reader);
    	players.put(user.getName(), this);
    }
    
    public void subUser(OfflinePlayer user) {
    	List<String> list = this.list;
    	if (list.contains(user.getName())) list.remove(user.getName());
    	this.list = list;
    	if (players.containsKey(reader))
    		players.remove(reader);
    	if (user.isOnline())
    		((Player) user).closeInventory();
    }
}
