package gg.scala.crates.crate

import gg.scala.crates.crate.prize.CratePrizeRarity
import gg.scala.crates.crate.prize.composable.test.ItemCratePrize
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
data class CrateConfig(
    val crates: MutableList<Crate> = mutableListOf(
        Crate("testing", "heya", mutableListOf(
            ItemCratePrize("Door", Material.ACACIA_DOOR, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Star", Material.NETHER_STAR, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Rack", Material.NETHERRACK, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Brick", Material.BRICK, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Boots", Material.CHAINMAIL_BOOTS, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Minecrat", Material.MINECART, 100.0, CratePrizeRarity.Common, mutableListOf()),
            ItemCratePrize("Hoper", Material.HOPPER, 100.0, CratePrizeRarity.Common, mutableListOf())
        ))
    )
)
