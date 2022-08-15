package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.CratePrizeRarity

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
interface CompositeCratePrizeEditSession
{
    var name: String
    var weight: Double
    var rarity: CratePrizeRarity
    var description: MutableList<String>
}
