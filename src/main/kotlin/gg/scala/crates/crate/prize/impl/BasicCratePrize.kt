package gg.scala.crates.crate.prize.impl

import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.crate.prize.CratePrizeRarity
import java.lang.reflect.Type

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
abstract class BasicCratePrize(
    private val name: String,
    var weightInternal: Double,
    private val description: List<String>,
    private val prizeType: Type,
    private val rarity: CratePrizeRarity = CratePrizeRarity.Common
) : CratePrize
{
    override fun getAbstractType() = this.prizeType
    override fun getName() = this.name
    override fun getDescription() = this.description

    override fun getRarity() = this.rarity
    override fun getWeight() = this.weightInternal
}
