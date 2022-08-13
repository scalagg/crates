package gg.scala.crates.menu

import gg.scala.commons.scheme.AbstractCompositeSchemedMenu
import gg.scala.commons.scheme.impl.SinglePageSchemedMenu
import gg.scala.crates.CratesSpigotConfig
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CratesMenu
{
    @Inject
    lateinit var plugin: CratesSpigotPlugin

    lateinit var template: AbstractCompositeSchemedMenu<Menu>

    @Configure
    fun configure()
    {
        this.regenerateTemplate()
    }

    private fun regenerateTemplate()
    {
        this.template = SinglePageSchemedMenu()
            .title("Select a crate to open!")
            .pattern(
                *this.plugin
                    .config<CratesSpigotConfig>()
                    .menuScheme
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
            .compose()
    }

    fun open(player: Player)
    {
        val template = SinglePageSchemedMenu()
            .copyOf(this.template)

        t
    }

}
