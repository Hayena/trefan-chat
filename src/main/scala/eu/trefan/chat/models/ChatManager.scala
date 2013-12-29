package eu.trefan.chat.models

import java.util.logging.Logger
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Player
import eu.trefan.chat.ScalaPlugin
import eu.trefan.chat.models.channel.Special
import eu.trefan.chat.utils.Towny

class ChatManager(plugin: ScalaPlugin) {

  var channels = new HashMap[String, Channel]
  var players = new HashMap[Player, Channel]
  
  var defaultChannels = ListBuffer[Channel]()
  
  def playerJoin(player: Player) {
    defaultChannels.foreach { channel =>
      channel.addMember(player)
    }
    playerTown(player)
    playerNation(player)
    players(player) = channels("Global")
    
  }
  
  def playerQuit(player: Player) {
    channels.foreach { case (name, channel) =>
      channel.removeMember(player)
    }
    players -= player
  }
  
  def getActiveChannel(player: Player): Channel = players(player)
  
  def setActiveChannel(player: Player, name: String) {
    val channel = channels(name)
    players.update(player, channel)
  }
  
  def addChannel(channel: Channel) = channels += (channel.name -> channel)
  
  def loadDefaultChannels = {
    val config = plugin.getConfig()
    config.getStringList("default.channels").foreach { channelName =>
      Logger.getLogger("minecraft").info(s"Loading $channelName")
      val channel = loadChannel(channelName)
      defaultChannels += channel
      channels.put(channel.name, channel)
    }
  }
  
  def loadChannel(channelName: String) : Channel = {
    val name = getConfigString(s"chat.$channelName.name")
    val tag = getConfigString(s"chat.$channelName.tag")
    val format = getConfigString(s"chat.$channelName.format")
    getConfigString(s"chat.$channelName.type") match {
        case "channel" => new Channel(name, tag, format)
        case "special" => new Special(name, tag, format, getConfigString(s"chat.$channelName.permission"))
      }
  }
  
  def playerTown(player: Player) {
    if(Towny.hasTown(player.getName)) {
      val townName = Towny.getTownName(player.getName)
      if(!channels.contains(townName)) {
        val channel = Towny.getTownChannel(player.getName, getConfigString("chat.town.format"))
        addChannel(channel)
      }
      channels(townName).addMember(player)
    } 
  }
  
  def playerNation(player: Player) {
    if(Towny.hasNation(player.getName)) {
      val nationName = Towny.getNationName(player.getName)
      if(!channels.contains(nationName)) {
        val channel = Towny.getNationChannel(player.getName, getConfigString("chat.nation.format"))
        addChannel(channel)
      }
      channels(nationName).addMember(player)
    } 
  }
  
  def getConfigString(name: String): String = {
    plugin.getConfig().getString(name)
  }
}