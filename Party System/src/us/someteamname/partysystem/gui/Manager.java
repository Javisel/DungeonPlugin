package us.someteamname.partysystem.gui;

import org.bukkit.entity.Player;

public class Manager {
	private Player PartyOwner;
	private Player PlayerFetch;
	private String PlayerEdit;
	
	public Manager(Player p) {
		this.PartyOwner = p;		
	}
	
	public Player getPartyOwner() {
		return this.PartyOwner;	
	}
	
	public Player getPlayerFetch() {
		return this.PlayerFetch;
	}
	
	public String getPlayerEdit() {
		return this.PlayerEdit;
	}
	
	public void setPlayerEdit(String Player1) {
		this.PlayerEdit = Player1;
	}
	public void setPlayerFetch(Player playerFetching) {
		this.PlayerFetch = playerFetching;
	}
}
