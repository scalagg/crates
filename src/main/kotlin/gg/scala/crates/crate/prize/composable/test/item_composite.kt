package gg.scala.crates.crate.prize.composable.test

import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.crate.prize.CratePrizeRarity
import gg.scala.crates.crate.prize.composable.CompositeCratePrize
import gg.scala.crates.crate.prize.composable.CompositeCratePrizeEditSession
import gg.scala.crates.crate.prize.composable.composite
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
    name: String,
    var internalMaterial: Material,
    weight: Double,
    rarity: CratePrizeRarity,
    description: MutableList<String>
) : CratePrize(
    name, ItemStack(internalMaterial), weight, description, rarity
)
{
    override fun getAbstractType() = ItemCratePrize::class.java
    override fun applicableTo(player: Player) = true

    override fun apply(player: Player): Player
    {
        player.inventory.addItem(material)
        return player
    }
}

class ItemCompositeCratePrizeEditSession(
    override var name: String = "some item",
    var material: Material = Material.ACACIA_DOOR,
    override var weight: Double = 100.0,
    override var rarity: CratePrizeRarity = CratePrizeRarity.Common,
    override var description: MutableList<String> = mutableListOf()
) : CompositeCratePrizeEditSession
