package us.someteamname.partysystem.util;

import org.bukkit.entity.Player;

import us.someteamname.partysystem.Friend;
import us.someteamname.partysystem.PartyUp;

public class API {
	public PartyUp plugin;

	 public Friend api = new Friend();
	  
	  public void Placeholders(PartyUp plugin) {
	    this.plugin = plugin;
	  }
	  
	  public boolean persist() {
	    return true;
	  }
	  
	  public boolean canRegister() {
	    return true;
	  }
	  
	  public String getAuthor() {
	    return this.plugin.getDescription().getAuthors().toString();
	  }
	  
	  public String getIdentifier() {
	    return "PartyUp";
	  }
	  
	  public String getVersion() {
	    return this.plugin.getDescription().getVersion();
	  }
	  
	  public String onPlaceholderRequest(Player player, String identifier) {
	    if (player == null)
	      return ""; 
	    if (identifier.equals("getparty")) {
	      Config data = new Config(player.getUniqueId().toString());
	      if (this.api.isInParty(data, player))
	        return "In a party"; 
	      return "You're not in a party";
	    } 
	    if (identifier.equals("friend_check"))
	      return this.api.isNearybyFriends(player); 
	    return null;
	  }
	}

