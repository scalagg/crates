package gg.scala.crates

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.config.annotations.ContainerConfig
import gg.scala.crates.keys.DefaultKeyProvider
import gg.scala.crates.keys.KeyProvider
import me.lucko.helper.plugin.ap.Plugin
import me.lucko.helper.plugin.ap.PluginDependency

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Plugin(
    name = "Crates",
    depends = [
        PluginDependency("scala-commons"),
        PluginDependency("store-spigot"),
        PluginDependency("Lemon"),
        PluginDependency("Cookie"),
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
