package gg.scala.crates.menu.editor

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.CrateService
import gg.scala.crates.menu.editor.prize.CratePrizeCompositeEditorContextMenu
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.Color
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
class CrateEditorMenu(
    private val crate: Crate,
    private val plugin: CratesSpigotPlugin
) : Menu("Editing crate ${crate.displayName}${CC.GRAY}...")
{
    init
    {
        updateAfterClick = true
        placeholder = true
    }

    override fun size(buttons: Map<Int, Button>) = 27

    override fun onClose(player: Player, manualClose: Boolean)
    {
        if (manualClose)
        {
            Tasks.delayed(1L) {
                CrateEditorViewMenu(plugin).openMenu(player)
            }
        }
    }

    override fun getButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[12] = ItemBuilder
            .of(Material.SIGN)
            .name("${CC.GREEN}Display Name")
            .addToLore(
                "${CC.GRAY}Current:",
                "${CC.WHITE}${this.crate.displayName}",
                "",
                "${CC.YELLOW}Click to edit!"
            )
            .toButton { _, _ ->
                player.closeInventory()

                InputPrompt()
                    .withText("${CC.GREEN}Enter a display name...")
                    .acceptInput { _, displayName ->
                        this.crate.displayName =
                            Color.translate(displayName)

                        CrateService.saveConfig()
                        openMenu(player)

                        player.sendMessage(
                            "${CC.SEC}Set new display name: ${CC.PRI}${this.crate.displayName}"
                        )
                    }
            }

        buttons[14] = ItemBuilder
            .of(Material.GRILLED_PORK)
            .name("${CC.GREEN}Manage Items")
            .addToLore(
                "${CC.GRAY}View all crate prizes.",
                "",
                "${CC.YELLOW}Click to manage!"
            )
            .toButton { _, _ ->
                player.closeInventory()
            }

        buttons[16] = ItemBuilder
            .copyOf(
                object : AddButton()
                {}.getButtonItem(player)
            )
            .name("${CC.GREEN}Add Items")
            .addToLore(
                "${CC.GRAY}Add a new crate prize.",
                "",
                "${CC.YELLOW}Click to add items!"
            )
            .toButton { _, _ ->
                CratePrizeCompositeEditorContextMenu(crate, plugin).openMenu(player)
            }

        return buttons
    }
}
