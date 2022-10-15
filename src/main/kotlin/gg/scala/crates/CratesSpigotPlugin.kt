package gg.scala.crates

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.config.annotations.ContainerConfig
import gg.scala.crates.keys.DefaultKeyProvider
import gg.scala.crates.keys.KeyProvider
import gg.scala.crates.menu.CrateViewMenu
import me.lucko.helper.Events
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency
import net.evilblock.cubed.entity.EntityHandler.forgetEntity
import net.evilblock.cubed.entity.EntityHandler.trackEntity
import net.evilblock.cubed.entity.hologram.HologramEntity
import net.evilblock.cubed.entity.hologram.HologramHandler.getHolograms
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.event.EventPriority
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Plugin(
    name = "Crates",
    depends = [
        PluginDependency("scala-commons"),
        PluginDependency("cloudsync", soft = true)
    ]
)
@ContainerConfig(
    value = "config",
    model = CratesSpigotConfig::class
)
class CratesSpigotPlugin : ExtendedScalaPlugin()
{
    var keyProvider: KeyProvider = DefaultKeyProvider

    @ContainerEnable
    fun containerEnable()
    {
        configuration = this.config()
        plugin = this
    }
}
