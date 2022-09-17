package gg.scala.crates.menu

import gg.scala.commons.acf.ConditionFailedException
import gg.scala.commons.scheme.AbstractCompositeSchemedMenu
import gg.scala.commons.scheme.impl.SinglePageSchemedMenu
import gg.scala.crates.CratesSpigotConfig
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.CrateService
import gg.scala.crates.keyProvider
import gg.scala.crates.player.CratesPlayerService
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
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
@Service
object CrateViewMenu
{
    @Inject
    lateinit var plugin: CratesSpigotPlugin

    private lateinit var template: AbstractCompositeSchemedMenu<Menu>

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
            .map('Z') { _, _ ->
                Button.placeholder(
                    Material.STAINED_GLASS_PANE, 15, " "
                )
            }
            .map('X') { _, _ ->
                Button.placeholder(
                    Material.STAINED_GLASS_PANE, 3, " "
                )
            }
    }

    fun open(player: Player)
    {
        val template = SinglePageSchemedMenu()
            .copyOf(this.template)

        val cratePlayer = CratesPlayerService.find(player)
            ?: return player.sendMessage(
                "${CC.RED}Sorry, we weren't able to open this menu."
            )

        CrateService.allCrates()
            .forEachIndexed { index, crate ->
                template.map(
                    ('a'..'z').toList()[index]
                ) { _, _ ->
                    ItemBuilder
                        .of(Material.CHEST)
                        .name("${CC.B_AQUA}${crate.displayName}")
                        .addToLore(
                            "${CC.GRAY}Current crate balance:",
                            "${CC.WHITE}${
                                keyProvider().getKeysFor(player.uniqueId, crate.uniqueId)
                            } keys",
                            "",
                            "${CC.AQUA}Right-click to open crate.",
                            "${CC.D_GRAY}Left-click to view contents.",
                        )
                        .toButton { _, type ->
                            if (type!!.isRightClick)
                            {
                                try
                                {
                                    CrateService.openCrate(player, crate)
                                } catch (exception: ConditionFailedException)
                                {
                                    player.sendMessage("${CC.RED}${exception.message}")
                                }
                            } else
                            {
                                CrateContentsMenu(crate).openMenu(player)
                            }
                        }
                }
            }

        template.compose()
        template.createAndOpen(player)
    }
}
