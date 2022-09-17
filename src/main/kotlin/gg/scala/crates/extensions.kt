package gg.scala.crates

import me.lucko.helper.utils.Players
import net.evilblock.cubed.util.Color
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
lateinit var configuration: CratesSpigotConfig
lateinit var plugin: CratesSpigotPlugin

fun keyProvider() = plugin.keyProvider

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

fun sendDebug(message: String)
{
    if (configuration.debugMode)
    {
        Players.all()
            .filter { it.isOp }
            .forEach {
                it.sendMessage(message)
            }
    }
}
