package eu.trefan.chat.models

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import de.bananaco.bpermissions.api.ApiLayer
import de.bananaco.bpermissions.api.CalculableType

object Format {
	def formatMessage(sender: Player, message: String, channel: Channel): String = {
	  var format = channel.format
	  val newMessage = formatMessage(sender, message)
	  format = format.replace("{channel}", channel.tag)
					 .replace("{prefix}", getInfo(sender, "prefix"))
					 .replace("{player}", sender.getName())
					 .replace("{suffix}", getInfo(sender, "suffix"))
					 .replace("  ", " ")
					 .replace("{msg}", newMessage)
	  
	  colorFormat(format)
	}
	
	
	private def getInfo(player: Player, info: String): String = {
	  ApiLayer.getValue(player.getWorld.getName, CalculableType.USER, player.getName, info)
	}
	
	private def colorFormat(format: String): String = {
	  format.replaceAll("&([a-z0-9])", "\u00A7$1")
	}
	
	private def removeFormat(format: String): String = {
	  format.replaceAll("&([a-z0-9])", "");
	}
	
	private def formatMessage(sender: Player, message: String): String = {
	  if(sender.hasPermission("chat.color"))
	    colorFormat(message)
	  else
	    removeFormat(message)
	}
}