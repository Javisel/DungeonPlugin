package us.someteamname.partysystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import us.someteamname.partysystem.util.Config;
import us.someteamname.partysystem.util.enums.FriendAction;
import us.someteamname.partysystem.util.enums.MailingAction;

public class FriendAPI {
	public Player t;
	  
	  public Config data;
	  
	  public Config pData;
	  
	  private String partyID;
	  
	  public HashMap<Player, Boolean> chat = new HashMap<>();
	  
	  public String prefix = "&b&oFRIEND&f&l >> &r ";
	  
	  public String firstMsg = "Get ahold of me when you get online";
	  
	  public String secondMsg = "We need to talk, its serious";
	  
	  public String thirdMsg = "You've been ignoring my calls, are you seeing someone else?";
	  
	  public String fourthMsg = "I've done something i think you're gonna like. Get back to me";
	  
	  public String fifthMsg = "WHAT ARE YOU DOING";
	  
	  public String sixthMsg = "You wanna do something?";
	  
	  public String seventhMsg = "Hiiiiiiiiiii";
	  
	  public String eighthMsg = "I left something at your place :)";
	  
	  public String ninethMsg = "Check your chests";
	  
	  public String tenthMsg = "Where did you go? Ill be back";
	  
	  public void msg(Player p, String msg) {
	    p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	  }
	  
	  public void sendMail(MailingAction action, Player sender, UUID receiver) {
	    Config newFile, file;
	    ArrayList<String> requests, sent;
	    Player target = Bukkit.getPlayer(receiver);
	    this.t = target;
	    this.pData = new Config(sender.getUniqueId().toString());
	    switch (action) {
	      case EIGHT:
	        if (target == null) {
			OfflinePlayer tar = Bukkit.getOfflinePlayer(receiver);
	          this.data = new Config(tar.getUniqueId().toString());
	          if (!this.data.exists()) {
	            msg(sender, this.prefix + "&c&oThe player " + '"' + t + '"' + " was not found.");
	            return;
	          } 
	          if (hasFile(tar) && hasFile(sender)) {
	            if (isWaiting(sender, tar)) {
	              makeFriendship(sender, tar);
	              return;
	            } 
	            if (tar.getName().equals(sender.getName())) {
	              msg(sender, this.prefix + "&c&oYou cannot befriend yourself... Nice try though");
	              return;
	            } 
	            if (getRequests(tar).contains(sender.getName())) {
	              msg(sender, this.prefix + "&c&oYou have already sent a request to: " + tar.getName());
	              return;
	            } 
	            if (getFriends(sender).contains(tar.getName())) {
	              msg(sender, this.prefix + "&c&oYou are already friends with player: " + tar.getName());
	              return;
	            } 
	            ArrayList<String> arrayList1 = new ArrayList<>(getRequests(tar));
	            ArrayList<String> arrayList2 = new ArrayList<>();
	            arrayList1.add(sender.getName());
	            arrayList2.add(tar.getName());
	            updateRequests(this.data, arrayList1);
	            updateSentRequests(this.pData, arrayList2);
	            msg(sender, this.prefix + "&a&oYou sent a friend request to: " + tar.getName());
	          } else {
	            Config config1 = new Config(tar.getUniqueId().toString());
	            Config config2 = new Config(sender.getUniqueId().toString());
	            ArrayList<String> arrayList1 = new ArrayList<>(getRequests(tar));
	            ArrayList<String> arrayList2 = new ArrayList<>();
	            arrayList1.add(sender.getName());
	            arrayList2.add(tar.getName());
	            updateRequests(config1, arrayList1);
	            updateSentRequests(config2, arrayList2);
	            msg(sender, this.prefix + "&a&oYou sent a friend request to: " + tar.getName());
	          } 
	          return;
	        } 
	        if (hasFile(this.t) && hasFile(sender)) {
	          this.data = new Config(this.t.getUniqueId().toString());
	          if (isWaiting(sender, this.t)) {
	            makeFriendship(sender, this.t);
	            return;
	          } 
	          if (this.t.getName().equals(sender.getName())) {
	            msg(sender, this.prefix + "&c&oYou cannot befriend yourself... Nice try though");
	            return;
	          } 
	          if (getRequests(this.t).contains(sender.getName())) {
	            msg(sender, this.prefix + "&c&oYou have already sent a request to: " + this.t.getName());
	            return;
	          } 
	          if (getFriends(sender).contains(this.t.getName())) {
	            msg(sender, this.prefix + "&c&oYou are already friends with player: " + this.t.getName());
	            return;
	          } 
	          ArrayList<String> arrayList1 = new ArrayList<>(getRequests(this.t));
	          ArrayList<String> arrayList2 = new ArrayList<>();
	          arrayList1.add(sender.getName());
	          arrayList2.add(this.t.getName());
	          updateRequests(this.data, arrayList1);
	          updateSentRequests(this.pData, arrayList2);
	          msg(sender, this.prefix + "&a&oYou sent a friend request to: " + this.t.getName());
	          msg(this.t, this.prefix + "&e&oYou recieved a friend request from: " + sender.getName());
	          break;
	        } 
	        newFile = new Config(this.t.getUniqueId().toString());
	        file = new Config(sender.getUniqueId().toString());
	        requests = new ArrayList<>(getRequests(this.t));
	        sent = new ArrayList<>();
	        requests.add(sender.getName());
	        sent.add(this.t.getName());
	        updateRequests(newFile, requests);
	        updateSentRequests(file, sent);
	        msg(sender, this.prefix + "&a&oYou sent a friend request to: " + this.t.getName());
	        msg(this.t, this.prefix + "&e&oYou recieved a friend request from: " + sender.getName());
	        break;
	      case FIVE:
	        if (target == null) {
	          msg(sender, this.prefix + "&c&oThe player " + '"' + t + '"' + " was not found.");
	          return;
	        } 
	        break;
	      case FOUR:
	        if (target == null) {
			OfflinePlayer tar = Bukkit.getOfflinePlayer(receiver);
	          Config conf = new Config(tar.getUniqueId().toString());
	          if (!conf.exists()) {
	            msg(sender, this.prefix + "&c&oThe player " + '"' + t + '"' + " was not found.");
	            return;
	          } 
	          removeFriend(sender, tar);
	          return;
	        } 
	        removeFriend(sender, this.t);
	        break;
	      case NINE:
	        cancelRequest(sender, receiver);
	        break;
		case ONE:
			break;
		case SEVEN:
			break;
		case SIX:
			break;
		case TEN:
			break;
		case THREE:
			break;
		case TWO:
			break;
		default:
			break;
	    } 
	  }
	  
	  public void makeFriendship(Player asker, Player target) {
	    this.data = new Config(target.getUniqueId().toString());
	    this.pData = new Config(asker.getUniqueId().toString());
	    ArrayList<String> friend = new ArrayList<>();
	    ArrayList<String> friends = new ArrayList<>();
	    ArrayList<String> request = new ArrayList<>();
	    ArrayList<String> requestt = new ArrayList<>();
	    ArrayList<String> requests = new ArrayList<>(getSentRequests(target));
	    ArrayList<String> requestss = new ArrayList<>(getSentRequests(asker));
	    requests.remove(asker.getName());
	    requestss.remove(target.getName());
	    request.addAll(getRequests(asker));
	    if (request.contains(target.getName()))
	      request.remove(target.getName()); 
	    requestt.addAll(getRequests(target));
	    if (requestt.contains(asker.getName()))
	      requestt.remove(asker.getName());
	    friend.addAll(getFriends(asker));
	    friends.addAll(getFriends(target));
	    friends.add(asker.getName());
	    friend.add(target.getName());
	    updateFriends(this.data, friends);
	    updateFriends(this.pData, friend);
	    updateSentRequests(this.data, requests);
	    updateSentRequests(this.pData, requestss);
	    updateRequests(this.pData, request);
	    updateRequests(this.data, requestt);
	    msg(asker, this.prefix + "&a&oYou are now friends with: " + target.getName());
	    msg(target, this.prefix + "&e&oYou are now friends with: " + asker.getName());
	    asker.playSound(asker.getLocation(), Sound.ENTITY_VILLAGER_YES, 8.0F, 1.0F);
	    target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_YES, 8.0F, 1.0F);
	  }
	  
	  public void makeFriendship(Player p, OfflinePlayer target) {
	    this.data = new Config(target.getUniqueId().toString());
	    this.pData = new Config(p.getUniqueId().toString());
	    ArrayList<String> friend = new ArrayList<>();
	    ArrayList<String> friends = new ArrayList<>();
	    ArrayList<String> request = new ArrayList<>();
	    ArrayList<String> requestt = new ArrayList<>();
	    ArrayList<String> requests = new ArrayList<>(getSentRequests(target));
	    ArrayList<String> requestss = new ArrayList<>(getSentRequests(p));
	    requests.remove(p.getName());
	    requestss.remove(target.getName());
	    request.addAll(getRequests(p));
	    if (request.contains(target.getName()))
	      request.remove(target.getName()); 
	    requestt.addAll(getRequests(target));
	    if (requestt.contains(p.getName()))
	      requestt.remove(p.getName()); 
	    friend.addAll(getFriends(p));
	    friends.addAll(getFriends(target));
	    friends.add(p.getName());
	    friend.add(target.getName());
	    updateFriends(this.data, friends);
	    updateFriends(this.pData, friend);
	    updateSentRequests(this.data, requests);
	    updateSentRequests(this.pData, requestss);
	    updateRequests(this.pData, request);
	    updateRequests(this.data, requestt);
	    msg(p, this.prefix + "&a&oYou are now friends with: " + target.getName());
	    addToList(this.data, "accepted-requests", p.getName());
	    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 8.0F, 1.0F);
	  }
	  
	  public boolean inPartyChat(Player player) {
	    Config data = new Config(player.getUniqueId().toString());
	    return sectionExists(data, "party-chat") ? data
	      .getConfig().getBoolean("party-chat") : false;
	  }
	  
	  private boolean sectionExists(Config conf, String path) {
	    if (conf.getConfig().getString(path) != null)
	      return true; 
	    return false;
	  }
	  
	  public boolean isPlayersAddable(Player p) {
	    for (Player target : Bukkit.getOnlinePlayers()) {
	      if (!isFriends(p, target) || !getRequests(target).contains(p.getName()))
	        return true; 
	    } 
	    return false;
	  }
	  
	  public boolean isFriends(Player p, Player target) {
	    if (getFriends(p).contains(target.getName()) && getFriends(target).contains(p.getName()))
	      return true; 
	    return false;
	  }
	  
	  public boolean isFriends(Player p, OfflinePlayer target) {
	    if (getFriends(p).contains(target.getName()) && getFriends(target).contains(p.getName()))
	      return true; 
	    return false;
	  }
	  
	  public void removeFriend(Player p, Player target) {
	    if (isFriends(p, target)) {
	      updateFriends(p, target);
	      msg(p, this.prefix + "&4&oYou are no longer friends with: " + target.getName());
	      msg(target, this.prefix + p.getName() + " &4&ohas decided to... part ways...");
	      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
	      target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_YES, 8.0F, 1.0F);
	      return;
	    } 
	  }
	  
	  public void removeFriend(Player p, OfflinePlayer target) {
	    if (isFriends(p, target)) {
	      updateFriends(p, target);
	      msg(p, this.prefix + "&4&oYou are no longer friends with: " + target.getName());
	      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 8.0F, 1.0F);
	      Config conf = new Config(target.getUniqueId().toString());
	      addToList(conf, "unfriended-people", p.getName());
	      return;
	    } 
	  }
	  
	  public List<String> getFriends(Player target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "friends-list");
	  }
	  
	  public List<String> getRequests(Player target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "friends-request-list");
	  }
	  
	  public List<String> getSentRequests(Player target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "sent-requests");
	  }
	  
	  public List<String> getFriends(OfflinePlayer target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "friends-list");
	  }
	  
	  public List<String> getRequests(OfflinePlayer target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "friends-request-list");
	  }
	  
	  public List<String> getSentRequests(OfflinePlayer target) {
	    this.data = new Config(target.getUniqueId().toString());
	    return getStringList(this.data, "sent-requests");
	  }
	  
	  public boolean hasFile(Player target) {
	    this.data = new Config(target.getUniqueId().toString());
	    if (this.data.exists())
	      return true; 
	    return false;
	  }
	  
	  public String isNearybyFriends(Player p) {
	    for (Entity e : p.getNearbyEntities(10.0D, 10.0D, 10.0D)) {
	      if (e instanceof Player) {
	        Player t = ((Player)e).getPlayer();
	        if (isFriends(p, t))
	          return "&f(&6Friend&f) " + t.getName(); 
	        return t.getName();
	      } 
	    } 
	    return "&cNo one near.";
	  }
	  
	  public boolean hasFile(OfflinePlayer target) {
	    this.data = new Config(target.getUniqueId().toString());
	    if (this.data.exists())
	      return true; 
	    return false;
	  }
	  
	  public boolean hasUpdate(Player target) {
	    Config conf = new Config(target.getUniqueId().toString());
	    ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("unfriended-people"));
	    ArrayList<String> f2 = new ArrayList<>(conf.getConfig().getStringList("accepted-requests"));
	    if (conf.getConfig().getConfigurationSection("incoming-mail") != null) {
	      ArrayList<String> f3 = new ArrayList<>(conf.getConfig().getConfigurationSection("incoming-mail").getKeys(false));
	      if (!f3.isEmpty())
	        return true; 
	    } 
	    if (f1.equals(null) || f2.equals(null))
	      return false; 
	    if (!f1.isEmpty() || !f2.isEmpty())
	      return true; 
	    return false;
	  }
	  
	  public boolean isWaiting(Player p, Player target) {
	    if (getRequests(p).contains(target.getName()))
	      return true; 
	    return false;
	  }
	  
	  public boolean isWaiting(Player p, OfflinePlayer target) {
	    if (getRequests(p).contains(target.getName()))
	      return true; 
	    return false;
	  }
	  
	  public boolean isPartyLeader(Player p) {
	    Config parties = new Config("party_list");
	    if (parties.getConfig().getString(getParty(p) + ".leader").equals(p.getName()))
	      return true; 
	    return false;
	  }
	  
	  public String getPartyLeader(Player p) {
	    Config parties = new Config("party_list");
	    return parties.getConfig().getString(getParty(p) + ".leader");
	  }
	  
	  public boolean isInParty(Config conf, Player p) {
	    if (conf.getConfig().getString("Party") != null)
	      return true; 
	    return false;
	  }
	  
	  public List<String> getStringList(Config conf, String section) {
	    return conf.getConfig().getStringList(section);
	  }
	  
	  public String getMailResponse(MailingAction response) {
	    String result = "";
	    switch (response) {
	      case EIGHT:
	        result = this.eighthMsg;
	        break;
	      case FIVE:
	        result = this.fifthMsg;
	        break;
	      case FOUR:
	        result = this.fourthMsg;
	        break;
	      case NINE:
	        result = this.ninethMsg;
	        break;
	      case ONE:
	        result = this.firstMsg;
	        break;
	      case SEVEN:
	        result = this.seventhMsg;
	        break;
	      case SIX:
	        result = this.sixthMsg;
	        break;
	      case TEN:
	        result = this.tenthMsg;
	        break;
	      case THREE:
	        result = this.thirdMsg;
	        break;
	      case TWO:
	        result = this.secondMsg;
	        break;
	    } 
	    return result;
	  }
	  
	  public void sendMail(MailingAction response, Player p, String t) {
	    String result = "";
	    switch (response) {
	      case EIGHT:
	        result = this.eighthMsg;
	        break;
	      case FIVE:
	        result = this.fifthMsg;
	        break;
	      case FOUR:
	        result = this.fourthMsg;
	        break;
	      case NINE:
	        result = this.ninethMsg;
	        break;
	      case ONE:
	        result = this.firstMsg;
	        break;
	      case SEVEN:
	        result = this.seventhMsg;
	        break;
	      case SIX:
	        result = this.sixthMsg;
	        break;
	      case TEN:
	        result = this.tenthMsg;
	        break;
	      case THREE:
	        result = this.thirdMsg;
	        break;
	      case TWO:
	        result = this.secondMsg;
	        break;
	    } 
	    Player target = Bukkit.getPlayer(t);
	    if (target == null) {
	      @SuppressWarnings("deprecation")
		OfflinePlayer tar = Bukkit.getOfflinePlayer(t);
	      Config config = new Config(tar.getUniqueId().toString());
	      if (!config.exists()) {
	        msg(p, this.prefix + "&c&oThe player " + '"' + t + '"' + " was not found.");
	        return;
	      } 
	      addToList(config, "incoming-mail." + p.getName(), result);
	      msg(p, this.prefix + "&e&oSent mail to friend: " + t);
	      return;
	    } 
	    Config conf = new Config(target.getUniqueId().toString());
	    addToList(conf, "incoming-mail." + p.getName(), result);
	    msg(p, this.prefix + "&e&oSent mail to friend: " + t);
	  }
	  
	  public void addToList(Config con, String path, String value) {
	    ArrayList<String> content = new ArrayList<>(con.getConfig().getStringList(path));
	    if (!content.contains(value))
	      content.add(value); 
	    con.getConfig().set(path, content);
	    con.saveConfig();
	  }
	  
	  public void newString(Config con, String section, String value, String section2, String value2) {
	    if (section2.isEmpty() || value2.isEmpty()) {
	      con.getConfig().set(section, value);
	      con.saveConfig();
	      return;
	    } 
	    con.getConfig().set(section, value);
	    con.saveConfig();
	    con.getConfig().set(section2, value2);
	    con.saveConfig();
	  }
	  
	  public void updateSentRequests(Config conf, List<String> requestList) {
	    conf.getConfig().set("sent-requests", requestList);
	    conf.saveConfig();
	  }
	  
	  public void updateFriends(Config conf, List<String> friendList) {
	    conf.getConfig().set("friends-list", friendList);
	    conf.saveConfig();
	  }
	  
	  public void updateFriends(Player p, Player target) {
	    Config conf = new Config(p.getUniqueId().toString());
	    Config conf2 = new Config(target.getUniqueId().toString());
	    ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("friends-list"));
	    ArrayList<String> f2 = new ArrayList<>(conf2.getConfig().getStringList("friends-list"));
	    f1.remove(target.getName());
	    conf.getConfig().set("friends-list", f1);
	    conf.saveConfig();
	    f2.remove(p.getName());
	    conf2.getConfig().set("friends-list", f2);
	    conf2.saveConfig();
	  }
	  
	  public void handleUpdate(Player target) {
	    Config conf = new Config(target.getUniqueId().toString());
	    ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("unfriended-people"));
	    ArrayList<String> f2 = new ArrayList<>(conf.getConfig().getStringList("accepted-requests"));
	    f1.clear();
	    f2.clear();
	    conf.getConfig().set("unfriended-people", f1);
	    conf.getConfig().set("accepted-requests", f2);
	    conf.saveConfig();
	  }
	  
	  public void updateFriends(Player p, OfflinePlayer target) {
	    Config conf = new Config(p.getUniqueId().toString());
	    Config conf2 = new Config(target.getUniqueId().toString());
	    ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("friends-list"));
	    ArrayList<String> f2 = new ArrayList<>(conf2.getConfig().getStringList("friends-list"));
	    f1.remove(target.getName());
	    conf.getConfig().set("friends-list", f1);
	    conf.saveConfig();
	    f2.remove(p.getName());
	    conf2.getConfig().set("friends-list", f2);
	    conf2.saveConfig();
	  }
	  
	  public String getParty(Player p) {
	    Config data = new Config(p.getUniqueId().toString());
	    return data.getConfig().getString("Party");
	  }
	  
	  public String getPartyStatus(String partyID) {
	    this.data = new Config("party_list");
	    return this.data.getConfig().getString(partyID + ".status");
	  }
	  
	  public void joinParty(Player p, Player target) {
	    Config data = new Config("party_list");
	    this.pData = new Config(p.getUniqueId().toString());
	    this.data = new Config(target.getUniqueId().toString());
	    if (!isInParty(this.data, target)) {
	      msg(p, this.prefix + "&c&o" + target.getName() + " isn't in a party.");
	      return;
	    } 
	    if (getPartyStatus(getParty(target)).equals("LOCKED") && !isFriends(p, target)) {
	      msg(p, this.prefix + target.getName() + " &cis in a &r" + getPartyStatus(getParty(target)) + " &cparty and you are not friends.");
	      return;
	    } 
	    if (PartyMembers(getParty(target)).size() == maxPartySize(getParty(target))) {
	      msg(p, this.prefix + target.getName() + "'s &c&oparty is full. &r[&4" + PartyMembers(getParty(target)).size() + "&r]");
	      return;
	    } 
	    if (isInParty(this.pData, p)) {
	      msg(p, this.prefix + "&c&oYou can only be in one party at a time.");
	      return;
	    } 
	    ArrayList<String> members = new ArrayList<>(data.getConfig().getStringList(getParty(target) + ".members"));
	    members.add(p.getName());
	    this.pData.getConfig().set("Party", getParty(target));
	    this.pData.saveConfig();
	    data.getConfig().set(getParty(target) + ".members", members);
	    data.saveConfig();
	    msg(p, this.prefix + "&e&oYou have joined " + target.getName() + "'s party.");
	    for (String players : PartyMembers(getParty(target)))
	      msg(Bukkit.getPlayer(players), this.prefix + p.getName() + " &a&ohas joined the party."); 
	  }
	  
	  public void leaveParty(Player p) {
	    this.data = new Config(p.getUniqueId().toString());
	    Config parties = new Config("party_list");
	    if (isPartyLeader(p)) {
	      disbandParty(p);
	      msg(p, this.prefix + "&4&oYou disbanded your party.");
	      return;
	    } 
	    ArrayList<String> members = new ArrayList<>(PartyMembers(getParty(p)));
	    members.remove(p.getName());
	    parties.getConfig().set(getParty(p) + ".members", members);
	    parties.saveConfig();
	    for (String players : PartyMembers(getParty(p)))
	      msg(Bukkit.getPlayer(players), this.prefix + p.getName() + " &c&oleft the party."); 
	    this.data.getConfig().set("Party", null);
	    this.data.saveConfig();
	    msg(p, this.prefix + "&c&oYou left the party.");
	  }
	  
	  public int maxPartySize(Player target) {
	    int returnv = 0;
	    if (target == null)
	      return 0; 
	    for (int i = 100; i >= 0; i--) {
	      if (target.hasPermission("PartySystem.party.infinite")) {
	        returnv = -1;
	        break;
	      } 
	      if (target.hasPermission("PartySystem.party." + i)) {
	        returnv = i;
	        break;
	      } 
	    } 
	    if (returnv == -1)
	      return 999; 
	    return returnv;
	  }
	  
	  public int maxFriends(Player target) {
	    int returnv = 0;
	    if (target == null)
	      return 0; 
	    for (int i = 100; i >= 0; i--) {
	      if (target.hasPermission("PartySystem.friends.infinite")) {
	        returnv = -1;
	        break;
	      } 
	      if (target.hasPermission("PartySystem.friends." + i)) {
	        returnv = i;
	        break;
	      } 
	    } 
	    if (returnv == -1)
	      return 999; 
	    return returnv;
	  }
	  
	  public int maxPartySize(String partyID) {
	    this.data = new Config("party_list");
	    return this.data.getConfig().getInt(partyID + ".max-size");
	  }
	  
	  public List<String> PartyMembers(String partyID) {
	    Config parties = new Config("party_list");
	    return parties.getConfig().getStringList(partyID + ".members");
	  }
	  
	  public void disbandAllMembers(Player p) {
	    for (String player : PartyMembers(getParty(p))) {
	      @SuppressWarnings("deprecation")
		OfflinePlayer pl = Bukkit.getOfflinePlayer(player);
	      if (pl.isOnline()) {
	        Player pla = Bukkit.getPlayer(player);
	        msg(pla, this.prefix + "&4&oYour party was disband by &e&o" + p.getName());
	        Config data = new Config(pla.getUniqueId().toString());
	        if (sectionExists(data, "party-chat"))
	          data.getConfig().set("party-chat", null); 
	        data.getConfig().set("Party", null);
	        data.saveConfig();
	      } 
	      this.data = new Config(pl.getUniqueId().toString());
	      this.data.getConfig().set("Party", null);
	      this.data.saveConfig();
	    } 
	  }
	  
	  private void setParty(String partyID) {
	    this.partyID = partyID;
	  }
	  
	  private String getPartyID() {
	    return this.partyID;
	  }
	  
	  public void disbandParty(Player p) {
	    Config parties = new Config("party_list");
	    setParty(getParty(p));
	    disbandAllMembers(p);
	    parties.getConfig().set(getPartyID(), null);
	    parties.saveConfig();
	  }
	  
	  public void formParty(Player p, FriendAction action) {
	    String status = "";
	    if (action.equals(FriendAction.OPEN))
	      status = "OPEN"; 
	    if (action.equals(FriendAction.CLOSED))
	      status = "LOCKED"; 
	    this.data = new Config(p.getUniqueId().toString());
	    Config parties = new Config("party_list");
	    if (isInParty(this.data, p)) {
	      msg(p, this.prefix + "&c&oYou can only be in one party at a time.");
	      return;
	    } 
	    UUID partyID = UUID.randomUUID();
	    ArrayList<String> members = new ArrayList<>();
	    members.add(p.getName());
	    parties.getConfig().set(partyID.toString() + ".leader", p.getName());
	    parties.getConfig().set(partyID.toString() + ".members", members);
	    parties.getConfig().set(partyID.toString() + ".status", status);
	    parties.getConfig().set(partyID.toString() + ".max-size", Integer.valueOf(maxPartySize(p)));
	    parties.saveConfig();
	    this.data.getConfig().set("Party", partyID.toString());
	    this.data.saveConfig();
	    for (String friends : getFriends(p)) {
		OfflinePlayer friend = Bukkit.getOfflinePlayer(friends);
	      if (friend.isOnline()) {
	        Player f = Bukkit.getPlayer(friends);
	        msg(f, this.prefix + "&e&oYour friend " + '"' + "&6" + p.getName() + "&e&o" + '"' + " created a &f" + status + "&e&o party!");
	      } 
	    } 
	    msg(p, this.prefix + "&3&oYou have created a new party.");
	  }
	  
	  public void updateUser(Player target) {
	    Config conf = new Config(target.getUniqueId().toString());
	    ArrayList<String> msg = new ArrayList<>();
	    ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("unfriended-people"));
	    ArrayList<String> f2 = new ArrayList<>(conf.getConfig().getStringList("accepted-requests"));
	    ArrayList<String> f3 = new ArrayList<>(conf.getConfig().getConfigurationSection("incoming-mail").getKeys(false));
	    for (String player : f1)
	      msg.add(player + " &4&ohas decided to... part ways..."); 
	    for (String player : f2)
	      msg.add("&a&oYou are now friends with: " + player); 
	    for (String player : f3) {
	      ArrayList<String> mail = new ArrayList<>(conf.getConfig().getStringList("incoming-mail." + player));
	      for (String m : mail)
	        msg.add("&6&oMessage from player \"" + player + '"' + " &r: &7&o " + m); 
	      conf.getConfig().createSection("incoming-mail." + player);
	    } 
	    for (String list : msg)
	      msg(target, this.prefix + list); 
	    conf.saveConfig();
	    target.playSound(target.getLocation(), Sound.ENTITY_WANDERING_TRADER_YES, 8.0F, 1.0F);
	  }
	  
	public void cancelRequest(Player p, UUID t) {
	    Player target = Bukkit.getPlayer(t);
	    if (target == null) {
		OfflinePlayer tar = Bukkit.getOfflinePlayer(t);
	      if (tar == null) {
	        msg(p, this.prefix + "&c&oThe player " + '"' + t + '"' + " was not found.");
	        return;
	      } 
	      if (getRequests(tar).contains(p.getName())) {
	        Config conf = new Config(p.getUniqueId().toString());
	        Config conf2 = new Config(tar.getUniqueId().toString());
	        ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("sent-requests"));
	        ArrayList<String> f2 = new ArrayList<>(conf2.getConfig().getStringList("friends-request-list"));
	        f1.remove(tar.getName());
	        conf.getConfig().set("sent-requests", f1);
	        conf.saveConfig();
	        f2.remove(p.getName());
	        conf2.getConfig().set("friends-request-list", f2);
	        conf2.saveConfig();
	        msg(p, this.prefix + "&e&oYou cancelled your request of friendship to: " + t);
	        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
	        return;
	      } 
	      return;
	    } 
	    if (getRequests(target).contains(p.getName())) {
	      Config conf = new Config(p.getUniqueId().toString());
	      Config conf2 = new Config(target.getUniqueId().toString());
	      ArrayList<String> f1 = new ArrayList<>(conf.getConfig().getStringList("sent-requests"));
	      ArrayList<String> f2 = new ArrayList<>(conf2.getConfig().getStringList("friends-request-list"));
	      f1.remove(target.getName());
	      conf.getConfig().set("sent-requests", f1);
	      conf.saveConfig();
	      f2.remove(p.getName());
	      conf2.getConfig().set("friends-request-list", f2);
	      conf2.saveConfig();
	      msg(p, this.prefix + "&e&oYou cancelled your request of friendship to: " + t);
	      msg(target, this.prefix + p.getName() + ": &c&oNevermind... I don't want to be friends.");
	      p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
	      target.playSound(target.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 8.0F, 1.0F);
	      return;
	    } 
	  }
	  
	  public void updateRequests(Config conf, List<String> friendList) {
	    conf.getConfig().set("friends-request-list", friendList);
	    conf.saveConfig();
	  }

	public void doFriendAction(FriendAction action, Player p, UUID target) {
		// TODO Auto-generated method stub
		
	}

	public String getMailingAction(MailingAction action) {
		// TODO Auto-generated method stub
		return null;
	}
	}

