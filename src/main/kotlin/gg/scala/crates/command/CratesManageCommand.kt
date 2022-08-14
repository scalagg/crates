package gg.scala.crates.command

import gg.scala.commons.acf.CommandHelp
import gg.scala.commons.acf.ConditionFailedException
import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.CommandCompletion
import gg.scala.commons.acf.annotation.CommandPermission
import gg.scala.commons.acf.annotation.Default
import gg.scala.commons.acf.annotation.HelpCommand
import gg.scala.commons.acf.annotation.Subcommand
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.menu.editor.CrateEditorViewMenu
import gg.scala.crates.menu.editor.prize.CratePrizeCompositeEditorContextMenu
import gg.scala.crates.player.CratesPlayerService
import gg.scala.flavor.inject.Inject
import gg.scala.lemon.player.LemonPlayer
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@AutoRegister
@CommandAlias("crates-manage|cm|cratesadmin")
@CommandPermission("crates.command.manage")
object CratesManageCommand : ScalaCommand()
{
    @Inject
    lateinit var plugin: CratesSpigotPlugin

    @Default
    @HelpCommand
    fun onHelp(help: CommandHelp)
    {
        help.showHelp()
    }

    @Subcommand("give-key")
    @CommandCompletion("@players @crates")
    fun onGiveKey(player: Player, target: LemonPlayer, crate: Crate, amount: Int)
    {
        val targetProfile = CratesPlayerService.find(target.uniqueId)
            ?: throw ConditionFailedException("Target's profile is corrupt.")

        targetProfile.balances[crate.uniqueId] =
            (targetProfile.balances[crate.uniqueId] ?: 0) + amount

        player.sendMessage("${CC.SEC}Gave ${target.getColoredName()}${CC.SEC} ${CC.PRI}$amount${CC.SEC} crate keys.")
    }

    @Subcommand("control-panel")
    fun onControlPanel(player: Player)
    {
        CrateEditorViewMenu(this.plugin).openMenu(player)
    }
}
