package eu.trefan.chat

import org.bukkit.command._
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import eu.trefan.chat.utils.Towny

class ChatCommandHandler(plugin: ScalaPlugin) extends CommandExecutor {

  override def onCommand(sender: CommandSender, cmd: Command, label: String, args: Array[String]): Boolean = {
    label match {
      case "mod" => {
        args.length match {
          case 0 => false
          case _ => {
            if (sender.isInstanceOf[Player]) {
              val player = getPlayer(sender)
              plugin.chatManager.channels("Moderator").send(player, args)
            }
          }
        }
      }

      case "chat" => {
        args.length match {
          case 0 => false
          case 1 => args(0).toLowerCase match {
            case "reload" => {
              if (sender.hasPermission("chat.moderator")) {
                plugin.reloadConfig()
                sender.sendMessage(ChatColor.GREEN + "Config reloaded")
              }
            }
            case _ => false
          }
        }
      }

      case "tc" => {
        if (sender.isInstanceOf[Player] && Towny.hasTown(sender.getName)) {
          val player = getPlayer(sender)
          val channel = plugin.chatManager.channels(Towny.getTownName(player.getName))
          channel.send(player, args)
        }
      }

      case "nc" => {
        if (sender.isInstanceOf[Player] && Towny.hasNation(sender.getName)) {
          val player = getPlayer(sender)
          val channel = plugin.chatManager.channels(Towny.getNationName(player.getName))
          channel.send(player, args)
        }

      }
    }

    false
  }

  def getPlayer(sender: CommandSender): Player = {
    sender.asInstanceOf[Player]
  }

  implicit def argsToString(args: Array[String]): String = {
    args.mkString(" ")
  }

}