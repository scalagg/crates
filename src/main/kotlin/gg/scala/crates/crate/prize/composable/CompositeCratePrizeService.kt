package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.composable.test.ItemCompositeCratePrize
import gg.scala.crates.crate.prize.composable.test.ItemCratePrize
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import kotlin.reflect.KClass

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CompositeCratePrizeService
{
    val composites = mutableMapOf<KClass<*>, CompositeCratePrize>()

    @Configure
    fun configure()
    {
        this.composites[ItemCratePrize::class] = ItemCompositeCratePrize
    }
}
