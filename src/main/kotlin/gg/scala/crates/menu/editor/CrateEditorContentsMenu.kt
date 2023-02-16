package gg.scala.crates.menu.editor

import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeService
import gg.scala.crates.menu.editor.prize.CratePrizeCompositeEditorConfigureMenu
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
class CrateEditorContentsMenu(
    private val crate: Crate,
    private val plugin: CratesSpigotPlugin
) : PaginatedMenu()
{
    init
    {
        placeholdBorders = true
    }

    override fun size(buttons: Map<Int, Button>) = 45

    override fun getAllPagesButtonSlots() = mutableListOf<Int>()
        .apply {
            addAll(10..16)
            addAll(19..25)
            addAll(28..34)
        }

    override fun onClose(player: Player, manualClose: Boolean)
    {
        if (manualClose)
        {
            Tasks.delayed(1L)
            {
                CrateEditorMenu(this.crate, this.plugin).openMenu(player)
            }
        }
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        for (prize in crate.prizes.sortedByDescending { it.weight })
        {
            buttons[buttons.size] = ItemBuilder
                .copyOf(prize.material)
                .name("${CC.B_GOLD}${prize.name}")
                .setUnbreakable(true)
                .addToLore(
                    "${CC.WHITE}Rarity: ${prize.rarity.chatColor}${prize.rarity.name}",
                    ""
                )
                .apply {
                    if (prize.description.isEmpty())
                    {
                        addToLore("${CC.RED}No description.")
                    } else
                    {
                        addToLore(*prize.description.toTypedArray())
                    }

                    addToLore("")
                    addToLore("${CC.WHITE}Rarity: ${CC.GOLD}${prize.rarity.name}")
                    addToLore("${CC.WHITE}Material: ${CC.GOLD}${prize.material.type.name}")
                    addToLore("${CC.WHITE}Weight: ${CC.GOLD}${prize.weightInternal}")
                    addToLore("")
                    addToLore("${CC.GREEN}Click to edit!")
                }
                .toButton { _, _ ->
                    val mapping = CompositeCratePrizeService
                        .composites[prize::class]!!

                    CratePrizeCompositeEditorConfigureMenu(
                        this.crate, this.plugin, mapping,
                        mapping.createSessionFromExisting(prize),
                        prize, this
                    ).openMenu(player)
                }
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player) = "Viewing crate ${crate.displayName}..."
}
