package us.someteamname.partysystem.gui;

import org.bukkit.entity.Player;

import java.util.UUID;

public class Manager {
	private Player PartyOwner;
	private Player PlayerFetch;
	private UUID PlayerUUID;
	
	public Manager(Player p) {
		this.PartyOwner = p;		
	}
	
	public Player getPartyOwner() {
		return this.PartyOwner;	
	}
	
	public Player getPlayerFetch() {
		return this.PlayerFetch;
	}
	
	public UUID getPlayerUUID() {
		return this.PlayerUUID;
	}
	
	public void setPlayerUUID(UUID playerID) {
		this.PlayerUUID = playerID;
	}
	public void setPlayerFetch(Player playerFetching) {
		this.PlayerFetch = playerFetching;
	}
}
