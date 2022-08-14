package gg.scala.crates.crate.prize.composable.test

import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.crate.prize.CratePrizeRarity
import gg.scala.crates.crate.prize.composable.CompositeCratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeEditSession
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
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
    var internalMaterial: Material,
    weight: Double,
    rarity: CratePrizeRarity,
    description: List<String>
) : CratePrize(
    "Item", Material.WOOD, weight, description, rarity
)
{
    override fun getAbstractType() = ItemCratePrize::class.java
    override fun applicableTo(player: Player) = true

    override fun apply(t: Player): Player
    {
        t.inventory.addItem(ItemStack(material))
        return t
    }
}

class ItemCompositeCratePrizeEditSession(
    var material: Material = Material.ACACIA_DOOR,
    override var weight: Double = 100.0,
    override var rarity: CratePrizeRarity = CratePrizeRarity.Common,
    override var description: List<String> = listOf()
) : CompositeCratePrizeEditSession

object ItemCompositeCratePrize : CompositeCratePrize()
{
    override fun getName() = "Item"
    override fun createSession() = ItemCompositeCratePrizeEditSession()

    override fun createSessionFromExisting(existing: CratePrize): CompositeCratePrizeEditSession
    {
        existing as ItemCratePrize

        return ItemCompositeCratePrizeEditSession(
            existing.internalMaterial, existing.weight, existing.rarity, existing.description
        )
    }

    override fun update(session: CompositeCratePrizeEditSession, prize: CratePrize)
    {
        prize as ItemCratePrize
        session as ItemCompositeCratePrizeEditSession

        prize.internalMaterial = session.material
        prize.weightInternal = session.weight
        prize.rarity = session.rarity
        prize.description = session.description
    }

    override fun create(session: CompositeCratePrizeEditSession): CratePrize
    {
        session as ItemCompositeCratePrizeEditSession

        return ItemCratePrize(session.material, session.weight, session.rarity, session.description)
    }

    override fun editorButtons(
        session: CompositeCratePrizeEditSession, menu: Menu
    ): List<Button>
    {
        session as ItemCompositeCratePrizeEditSession

        return listOf(
            ItemBuilder
                .of(Material.PAPER)
                .name("${CC.GREEN}Item Material")
                .addToLore("${CC.GRAY}Current: ${CC.WHITE}${session.material.name}")
                .toButton { player, _ ->
                    player!!.sendMessage("${CC.GREEN}Enter a material...")
                    player.closeInventory()

                    InputPrompt()
                        .acceptInput { _, s ->
                            session.material = Material.valueOf(s)
                            player.sendMessage("${CC.SEC}Set material to: ${CC.PRI}${session.material.name}")

                            menu.openMenu(player)
                        }
                        .start(player)
                }
        )
    }
}
