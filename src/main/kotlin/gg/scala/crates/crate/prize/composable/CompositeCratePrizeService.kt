package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.composable.test.ItemCompositeCratePrize
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CompositeCratePrizeService
{
    val composites = mutableListOf<CompositeCratePrize<*>>()

    @Configure
    fun configure()
    {
        this.composites.add(ItemCompositeCratePrize)
    }
}
