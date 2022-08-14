package gg.scala.crates.crate.prize

import me.lucko.helper.random.Weighted
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializable
import org.bukkit.entity.Player
import java.util.function.UnaryOperator

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
abstract class CratePrize(
    private val name: String,
    var weightInternal: Double,
    private val description: List<String>,
    private val rarity: CratePrizeRarity = CratePrizeRarity.Common
) : UnaryOperator<Player>, Weighted, AbstractTypeSerializable
{
    fun getName() = this.name
    fun getDescription() = this.description

    fun getRarity() = this.rarity

    override fun getWeight() = this.weightInternal
    abstract fun applicableTo(player: Player): Boolean
}
