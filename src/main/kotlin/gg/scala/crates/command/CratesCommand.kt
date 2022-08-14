package gg.scala.crates.command

import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.Default
import gg.scala.commons.acf.annotation.Subcommand
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.crates.crate.CrateService
import gg.scala.crates.menu.CratesMenu
import gg.scala.crates.player.CratesPlayerService
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@AutoRegister
@CommandAlias("crates|crate|cr")
object CratesCommand : ScalaCommand()
{
    @Default
    fun onDefault(player: Player)
    {
        val cratePlayer = CratesPlayerService
            .find(player) ?: return

        player.sendMessage("${CC.GRAY}Current crate key balance:")

        CrateService.allCrates().forEach {
            player.sendMessage(" ${CC.AQUA}${it.displayName}: ${CC.WHITE}${cratePlayer.balances[it.uniqueId] ?: 0}")
        }
    }

    @Subcommand("menu")
    fun onMenu(player: Player)
    {
        CratesMenu.open(player)
    }
}
