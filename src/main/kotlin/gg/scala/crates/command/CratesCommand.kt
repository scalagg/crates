package gg.scala.crates.command

import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.Default
import gg.scala.commons.acf.annotation.Subcommand
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.crates.configuration
import gg.scala.crates.crate.CrateService
import gg.scala.crates.menu.CrateViewMenu
import gg.scala.crates.player.CratesPlayerService
import gg.scala.crates.sendToPlayer
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
        if (configuration.displayCratesBalanceOnCratesDefaultCommand)
        {
            val cratePlayer = CratesPlayerService
                .find(player) ?: return

            configuration.crateBalanceHeader.sendToPlayer(player)

            CrateService.allCrates().forEach {
                configuration.crateBalanceEntry
                    .sendToPlayer(
                        player,
                        "<crateDisplayName>" to it.displayName,
                        "<crateKeyBalance>" to (cratePlayer.balances[it.uniqueId] ?: 0).toString().format("%,d"),
                    )
            }
        } else
        {
            CrateViewMenu.open(player)
        }
    }

    @Subcommand("menu")
    fun onMenu(player: Player)
    {
        CrateViewMenu.open(player)
    }
}
