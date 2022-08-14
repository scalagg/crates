package gg.scala.crates.menu.creator

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.CrateConfig
import gg.scala.crates.crate.CrateService
import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeCreatorSession
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CrateCreatorCompositeConfigureMenu(
    private val crate: Crate, private val plugin: CratesSpigotPlugin,
    private val composite: CompositeCratePrize<out CratePrize>,
    private val session: CompositeCratePrizeCreatorSession
) : Menu("Configure your prize details")
{
    init
    {
        updateAfterClick = true
        placeholdBorders = true
    }

    override fun size(buttons: Map<Int, Button>) = 36

    override fun onClose(player: Player, manualClose: Boolean)
    {
        if (manualClose)
        {
            player.sendMessage("${CC.RED}Discarded your prize configuration.")

            Tasks.delayed(1L)
            {
                CrateCreatorCompositeCtxMenu(crate, this.plugin).openMenu(player)
            }
        }
    }

    override fun getButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        val mappings = mutableListOf(
            "General" to listOf(
                ItemBuilder
                    .of(Material.CHEST)
                    .name("Weight")
                    .addToLore("${CC.GRAY}Current: ${this.session.weight}")
                    .toButton()
            ),
            "Custom" to this.composite.editorButtons(this.session)
        )

        for ((index, category) in mappings.withIndex())
        {
            buttons[(9 * (index + 1))] = ItemBuilder
                .of(Material.STAINED_GLASS_PANE)
                .data((5 - index).toShort())
                .name("${CC.AQUA}$category")
                .toButton()

            buttons[(17 + (index * 9))] = ItemBuilder
                .of(Material.AIR)
                .toButton()

            for ((buttonIndex, button) in category.second.withIndex())
            {
                buttons[buttonIndex + (10 + (index * 9))] = button
            }
        }

        buttons[4] = ItemBuilder
            .copyOf(
                object : AddButton()
                {}.getButtonItem(player)
            )
            .name("${CC.B_GREEN}Click to add item")
            .addToLore(
                "${CC.GRAY}Add item to crate!",
                "",
                "${CC.GREEN}Click to add!",
            )
            .toButton { _, _ ->
                val prize = this.composite
                    .create(this.session)

                this.crate.prizes.add(prize)

                kotlin
                    .runCatching {
                        CrateService.saveConfig()
                    }
                    .onFailure {
                        it.printStackTrace()
                    }

                player.closeInventory()
                player.sendMessage("${CC.GREEN}Saved crate")
            }

        return buttons
    }
}
