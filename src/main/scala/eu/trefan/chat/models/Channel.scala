package eu.trefan.chat.models

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import scala.collection.mutable.ListBuffer

class Channel(val name: String, val tag: String, val format: String) {
	val members = ListBuffer[Player]()
	
	def send(sender: Player, message: String): Unit = {
	  val formatedMessage = Format.formatMessage(sender, message, this)
	  members.map(player => player.sendMessage(formatedMessage))
	}
	def addMember(player: Player): Unit = members += player
	def removeMember(player: Player): Unit = members -= player
}