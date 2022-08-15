package gg.scala.crates

import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.config.annotations.ContainerConfig
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
    ]
)
@ContainerConfig(
    value = "config",
    model = CratesSpigotConfig::class
)
class CratesSpigotPlugin : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        configuration = this.config()

        if (!this.dataFolder.exists())
        {
            this.dataFolder.mkdirs()
        }
    }
}
