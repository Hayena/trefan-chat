package eu.trefan.chat.models.channel

import org.bukkit.entity.Player
import eu.trefan.chat.models.Channel
import org.bukkit.ChatColor

class Private(name: String, tag: String, format: String, owner: Player) 
	extends Channel(name, tag, format) {
  
  var password = ""
  var isProtected = false
  
  def setPassword(password: String) {
    this.password = password
    isProtected = true
  }
  
  def removePassword {
    isProtected = false
  }
  
  override def addMember(player: Player) {
    if(!isProtected || player.hasPermission("chat.mod")) {
      super.addMember(player)
    } else {
      player.sendMessage(ChatColor.RED + "This chat is protected by a password.")
    }
  }
  
  def addMember(player: Player, password: String) {
    if(isProtected) {
      if(this.password.equals(password))
        super.addMember(player)
      else
        player.sendMessage(ChatColor.RED + "Invalid password")
    } else
      player.sendMessage(ChatColor.YELLOW + "This channel is not password protected")
  }
}