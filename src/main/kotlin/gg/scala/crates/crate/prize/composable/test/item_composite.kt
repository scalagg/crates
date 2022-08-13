package gg.scala.crates.crate.prize.composable.test

import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeCreatorSession
import gg.scala.crates.crate.prize.impl.BasicCratePrize
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.prompt.InputPrompt
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class ItemCratePrize(
    private val material: Material
) : BasicCratePrize(
    "Item", 100.0, listOf(), ItemCratePrize::class.java
)
{
    override fun applicableTo(player: Player) = true

    override fun apply(t: Player): Player
    {
        t.inventory.addItem(ItemStack(material))
        return t
    }
}

class ItemCompositeCratePrizeCreatorSession(
    var material: Material = Material.ACACIA_DOOR
) : CompositeCratePrizeCreatorSession

object ItemCompositeCratePrize : CompositeCratePrize<ItemCompositeCratePrizeCreatorSession>()
{
    override fun getName() = "Item"
    override fun createSession() = ItemCompositeCratePrizeCreatorSession()

    override fun create(session: ItemCompositeCratePrizeCreatorSession): CratePrize
    {
        return ItemCratePrize(session.material)
    }

    override fun editorButtons(
        session: ItemCompositeCratePrizeCreatorSession
    ): List<Button>
    {
        return listOf(
            ItemBuilder
                .of(Material.PAPER)
                .name("what item")
                .addToLore(session.material.name)
                .toButton { player, _ ->
                    player!!.sendMessage("give")

                    InputPrompt()
                        .acceptInput { _, s ->
                            session.material = Material.valueOf(s)
                        }
                        .start(player)
                }
        )
    }
}
