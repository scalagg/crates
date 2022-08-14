package gg.scala.crates.menu

import gg.scala.crates.crate.Crate
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
class CrateContentsMenu(
    private val crate: Crate
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
                CrateMenu.open(player)
            }
        }
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        for (prize in crate.prizes.sortedByDescending { it.weight })
        {
            buttons[buttons.size] = ItemBuilder
                .of(prize.material)
                .name("${CC.B_AQUA}${prize.name}")
                .addToLore(
                    "${CC.GRAY}Rarity: ${prize.rarity.chatColor}${prize.rarity.name}",
                    ""
                )
                .apply {
                    addToLore(*prize.description.toTypedArray())
                }
                .toButton()
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player) = "Viewing crate ${crate.displayName}${CC.D_GRAY}..."
}
