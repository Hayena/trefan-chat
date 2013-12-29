package eu.trefan.chat.models.channel

import eu.trefan.chat.models.Channel
import org.bukkit.ChatColor
import org.bukkit.entity.Player

class Special(name: String, tag: String, format: String, permissionNode: String)
    extends Channel(name, tag, format) {

	override def addMember(player: Player) {
	  if(player.hasPermission(permissionNode))
	    super.addMember(player)
	}
}