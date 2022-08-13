package gg.scala.crates.command

import gg.scala.commons.acf.annotation.CommandAlias
import gg.scala.commons.acf.annotation.Default
import gg.scala.commons.annotations.commands.AutoRegister
import gg.scala.commons.command.ScalaCommand
import gg.scala.crates.menu.CratesMenu
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
        CratesMenu.open(player)
    }
}
