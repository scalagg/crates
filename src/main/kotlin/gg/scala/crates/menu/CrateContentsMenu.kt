package gg.scala.crates.menu

import gg.scala.crates.configuration
import gg.scala.crates.crate.Crate
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.ChatColor
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
                CrateViewMenu.open(player)
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
                .name("${CC.B_AQUA}${prize.name}")
                .apply {
                    if (prize.description.isEmpty())
                    {
                        addToLore("${CC.RED}No description.")
                    } else
                    {
                        addToLore(*prize.description.toTypedArray())
                    }
                }
                .setUnbreakable(true)
                .addToLore(
                    "",
                    "${CC.WHITE}Rarity: ${prize.rarity.chatColor}${prize.rarity.name}",
                    "",
                    if (!prize.applicableTo(player)) "${CC.RED}You cannot win this item!" else "${CC.GREEN}You are able to win this item!"
                )
                .toButton()
        }

        return buttons
    }

    override fun getPrePaginatedTitle(player: Player) = String
        .format(
            configuration.crateViewScopeTitle,
            ChatColor.stripColor(crate.displayName)
        )
}
