package gg.scala.crates.menu.creator

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeService
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CrateCreatorCompositeCtxMenu(
    private val crate: Crate, private val plugin: CratesSpigotPlugin
) : Menu("Choose a prize type")
{
    override fun getButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        for (composite in CompositeCratePrizeService.composites)
        {
            buttons[buttons.size] = ItemBuilder
                .of(Material.PAPER)
                .name("${CC.PRI}${composite.getName()}")
                .toButton { _, _ ->
                    CrateCreatorCompositeConfigureMenu(
                        crate, plugin, composite, composite.createSession()
                    ).openMenu(player)
                }
        }

        return buttons
    }
}
