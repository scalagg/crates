package gg.scala.crates.menu

import gg.scala.commons.scheme.impl.SinglePageSchemedMenu
import net.evilblock.cubed.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CratesMenu
{
    private val template = SinglePageSchemedMenu()
        .title("Select a crate to open!")
        .pattern(
            "0X00X00X0",
            "X1XX2XX3X",
            "0X00X00X0",
        )
        .map('0') { _, _ ->
            Button.placeholder(
                Material.STAINED_GLASS_PANE, 4, ""
            )
        }
        .map('X') { _, _ ->
            Button.placeholder(
                Material.STAINED_GLASS_PANE, 1, ""
            )
        }

    fun open(player: Player)
    {

    }

}
