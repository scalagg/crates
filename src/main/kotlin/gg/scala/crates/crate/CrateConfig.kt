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
            ItemCratePrize(Material.ACACIA_DOOR, 100.0, CratePrizeRarity.Common, listOf()),
            ItemCratePrize(Material.WOOD, 25.0, CratePrizeRarity.Legendary, listOf())
        ))
    )
)
