package gg.scala.crates.menu.editor

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.CrateService
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.menus.ConfirmMenu
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
class CrateEditorViewMenu(
    private val plugin: CratesSpigotPlugin
) : PaginatedMenu()
{
    init
    {
        placeholdBorders = true
        updateAfterClick = true
    }

    override fun size(buttons: Map<Int, Button>) = 36

    override fun getAllPagesButtonSlots() = mutableListOf<Int>()
        .apply {
            addAll(10..16)
            addAll(19..25)
            addAll(28..34)
        }

    override fun getGlobalButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        buttons[4] = ItemBuilder
            .copyOf(
                object : AddButton()
                {}.getButtonItem(player)
            )
            .name("${CC.B_GREEN}Create a Crate")
            .addToLore(
                "${CC.GRAY}Create a new crate.",
                "",
                "${CC.GREEN}Click to create!",
            )
            .toButton { _, _ ->
                player.closeInventory()

                InputPrompt()
                    .withText("${CC.GREEN}Enter a unique id...")
                    .acceptInput { _, uniqueId ->
                        if (uniqueId.contains(" "))
                        {
                            player.sendMessage("${CC.RED}Unique IDs must not contain spaces!")
                            return@acceptInput
                        }

                        val crate = Crate(
                            uniqueId = uniqueId,
                            displayName = "${CC.B_AQUA}Example crate",
                            prizes = mutableListOf()
                        )

                        kotlin
                            .runCatching {
                                CrateService.config().crates.add(crate)
                                CrateService.saveConfig()
                            }
                            .onSuccess {
                                player.sendMessage("${CC.GREEN}Successfully created a new crate!")
                                openMenu(player)
                            }
                            .onFailure {
                                player.sendMessage("${CC.RED}Something went terribly wrong while trying to create a crate.")
                            }
                    }
                    .start(player)
            }

        return buttons
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        CrateService.allCrates()
            .forEach {
                buttons[buttons.size] = ItemBuilder
                    .of(Material.CHEST)
                    .name("${CC.B_AQUA}${it.uniqueId}")
                    .addToLore(
                        "${CC.GRAY}Display name:",
                        "${CC.WHITE}${it.displayName}",
                        "",
                        "${CC.GRAY}Crate prizes: ${CC.WHITE}${it.prizes.size} prizes",
                        "",
                        "${CC.GREEN}Click to edit crate!",
                        "${CC.RED}Right-Click to delete crate!",
                    )
                    .toButton { _, type ->
                        if (type!!.isRightClick)
                        {
                            ConfirmMenu(confirm = true) { option ->
                                if (!option)
                                {
                                    player.sendMessage("${CC.RED}Didn't delete crate.")

                                    Tasks.delayed(1L)
                                    {
                                        openMenu(player)
                                    }
                                    return@ConfirmMenu
                                }

                                CrateService.allCrates()
                                    .removeIf { crate ->
                                        it.uniqueId == crate.uniqueId
                                    }

                                CrateService.saveConfig()

                                player.sendMessage("${CC.RED}Deleted crate.")
                            }.openMenu(player)
                            return@toButton
                        }

                        CrateEditorMenu(it, plugin).openMenu(player)
                    }
            }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player) = "Viewing crates"
}
