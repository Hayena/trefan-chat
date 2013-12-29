package eu.trefan.chat.utils

import com.palmergames.bukkit.towny.`object`._
import eu.trefan.chat.models.ChatException
import org.bukkit.ChatColor
import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import org.bukkit.entity.Player
import org.bukkit.Bukkit
import eu.trefan.chat.models.Channel
import eu.trefan.chat.models.channel.TownChannel
import eu.trefan.chat.models.channel.NationChannel

object Towny {

  val towny = TownyUniverse.getDataSource()

  def getResident(playerName: String) = towny.getResident(playerName)
  def hasTown(playerName: String) = getResident(playerName).hasTown
  def hasNation(playerName: String) = getResident(playerName).hasNation

  private def getTownTag(resident: Resident): String = {
    if (resident.hasTown) {
      val town = resident.getTown
      return if (town.hasTag) town.getTag else town.getName
    }
    throw new ChatException("You are not a member of a town")
  }

  private def getNationTag(resident: Resident): String = {
    if (resident.hasNation) {
      val nation = resident.getTown.getNation
      return if (nation.hasTag) nation.getTag else nation.getName
    }
    throw new ChatException("Your town not a member of a nation")
  }

  def getTownyTag(playerName: String): String = {
    val resident = getResident(playerName)
    if (resident.hasNation())
      s"[${ChatColor.GOLD}${getNationTag(resident)}${ChatColor.WHITE}|${ChatColor.DARK_AQUA}${getTownTag(resident)}${ChatColor.WHITE}]"
    else
      s"[${ChatColor.DARK_AQUA}${getTownTag(resident)}${ChatColor.WHITE}]"
  }

  def getMembersTown(name: String): List[Player] = {
    val residents = towny.getTown(name).getResidents
    convertToPlayers(residents)
  }

  def getMembersNation(name: String): List[Player] = {
    val residents = towny.getNation(name).getResidents
    convertToPlayers(residents)
  }

  private def convertToPlayers(residents: java.util.List[Resident]): List[Player] = {
    val players = ListBuffer[Player]()
    for (resident <- residents)
      players += Bukkit.getServer.getPlayer(resident.getName)
    players.toList
  }

  def getTownName(playerName: String): String = {
    getResident(playerName).getTown.getName
  }

  def getTownChannel(playerName: String, format: String): Channel = {
    val town = getResident(playerName).getTown
    new TownChannel(town.getName, "TC", format)
  }

  def getNationName(playerName: String): String = {
    getResident(playerName).getTown.getNation.getName
  }

  def getNationChannel(playerName: String, format: String): Channel = {
    val nation = getResident(playerName).getTown.getNation()
    new NationChannel(nation.getName, "NC", format)
  }
}