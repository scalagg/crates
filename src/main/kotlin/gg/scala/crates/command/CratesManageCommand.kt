package gg.scala.crates.command

import gg.scala.commons.acf.CommandHelp
import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.CommandPermission
import gg.scala.commons.acf.annotation.Default
import gg.scala.commons.acf.annotation.HelpCommand
import gg.scala.commons.acf.annotation.Subcommand
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.menu.creator.CrateCreatorCompositeCtxMenu
import gg.scala.flavor.inject.Inject
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@AutoRegister
@CommandAlias("crates-manage")
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

    @Subcommand("add-item")
    fun onAddItem(player: Player, crate: Crate)
    {
        CrateCreatorCompositeCtxMenu(crate, this.plugin).openMenu(player)
    }
}
