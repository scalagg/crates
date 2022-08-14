package gg.scala.crates.crate

import gg.scala.commons.config.convert.annotations.ConfigConverted
import gg.scala.commons.config.convert.annotations.ConfigFileName
import gg.scala.crates.crate.prize.composable.test.ItemCratePrize
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@ConfigConverted("dummy")
@ConfigFileName("items.json")
data class CrateConfig(
    val crates: MutableList<Crate> = mutableListOf(
        Crate("testing", "heya", mutableListOf(
            ItemCratePrize(Material.ACACIA_DOOR, 100.0),
            ItemCratePrize(Material.WOOD, 25.0)
        ))
    )
)
