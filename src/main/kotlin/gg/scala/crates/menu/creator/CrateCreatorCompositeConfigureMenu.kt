package gg.scala.crates.menu.creator

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.CrateConfig
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
    }

    override fun size(buttons: Map<Int, Button>) = 18

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

        buttons[buttons.size] = ItemBuilder
            .of(Material.CHEST)
            .name("Weight")
            .name("${CC.GRAY}Current: ${this.session.weight}")
            .toButton()

        this.composite.editorButtons(this.session)
            .forEach {
                buttons[1 + buttons.size] = it
            }

        buttons[14] = ItemBuilder
            .copyOf(
                object : AddButton()
                {}.getButtonItem(player)
            )
            .name("add")
            .toButton { _, _ ->
                val prize = this.composite
                    .create(this.session)

                this.crate.prizes.add(prize)

                this.plugin.configContainerized
                    .container<CrateConfig>()
                    .save(crossSync = false)

                player.closeInventory()
                player.sendMessage("${CC.GREEN}Saved crate")
            }

        return buttons
    }
}
