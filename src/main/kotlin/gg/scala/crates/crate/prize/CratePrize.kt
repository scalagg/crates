package gg.scala.crates.crate.prize

import me.lucko.helper.random.Weighted
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializable
import org.bukkit.entity.Player
import java.util.function.UnaryOperator

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
interface CratePrize : AbstractTypeSerializable, UnaryOperator<Player>, Weighted
{
    fun getName(): String
    fun getDescription(): List<String>

    fun getRarity(): CratePrizeRarity
    fun applicableTo(player: Player): Boolean
}
