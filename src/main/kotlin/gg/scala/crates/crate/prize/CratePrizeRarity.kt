package gg.scala.crates.crate.prize

import org.bukkit.ChatColor

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
enum class CratePrizeRarity(
    val chatColor: ChatColor
)
{
    Common(ChatColor.GRAY),
    Rare(ChatColor.AQUA),
    Legendary(ChatColor.GOLD)
}
