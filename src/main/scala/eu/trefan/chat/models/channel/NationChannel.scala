package eu.trefan.chat.models.channel

import eu.trefan.chat.models.Channel
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import eu.trefan.chat.utils.Towny
import eu.trefan.chat.models.Format

class NationChannel(name: String, tag: String, format: String)
    extends Channel(name, tag, format) {

	override def send(sender: Player, message: String): Unit = {
	  if(Towny.hasNation(sender.getName())) {
	    val residents = Towny.getMembersNation(name)
	    val formattedMessage = Format.formatMessage(sender, message, this)
	    residents.map(resident => resident.sendMessage(formattedMessage))
	  }
	}
  
}