package eu.trefan.chat

import org.bukkit.event.Listener
import org.bukkit.event.player._
import org.bukkit.event.EventHandler
import eu.trefan.chat.models._
import org.bukkit.event.EventPriority._

class ChatEventHandler(chatManager: ChatManager) extends Listener {

  @EventHandler
  def onLogin(event: PlayerLoginEvent) = chatManager.playerJoin(event.getPlayer)
  
  @EventHandler
  def onLogout(event: PlayerQuitEvent) = chatManager.playerQuit(event.getPlayer)
  
  @EventHandler(priority = HIGHEST)
  def onPlayerChat(event: AsyncPlayerChatEvent) {
    val channel = chatManager.getActiveChannel(event.getPlayer)
    channel.send(event.getPlayer, event.getMessage)
    event.setCancelled(true)
  }
}