package gg.scala.crates

import net.evilblock.cubed.util.Color
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
lateinit var configuration: CratesSpigotConfig

fun List<String>.sendToPlayer(
    player: Player,
    vararg replacements: Pair<String, String>
)
{
    for (line in this)
    {
        line.sendToPlayer(player, *replacements)
    }
}

fun String.sendToPlayer(
    player: Player,
    vararg replacements: Pair<String, String>
)
{
    var cached = this

    for (replacement in replacements)
    {
        cached = cached.replace(
            replacement.first, replacement.second
        )
    }

    player.sendMessage(
        Color.translate(cached)
    )
}
