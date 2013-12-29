package eu.trefan.chat

import org.bukkit.plugin.java.JavaPlugin
import eu.trefan.chat.models.ChatManager
import org.bukkit.command.CommandSender
import org.bukkit.ChatColor
import java.util.logging.Logger

class ScalaPlugin extends JavaPlugin {

  val chatManager = new ChatManager(this)
  
  override def onEnable {
    this.saveDefaultConfig
    chatManager.loadDefaultChannels
    
    getServer.getPluginManager.registerEvents(new ChatEventHandler(chatManager), this)
    addCommand("chat")
    addCommand("mod")
    addCommand("tc")
    addCommand("nc")
  }
  
  
  def addCommand(name: String) {
    this.getCommand(name).setExecutor(new ChatCommandHandler(this))
  }
}