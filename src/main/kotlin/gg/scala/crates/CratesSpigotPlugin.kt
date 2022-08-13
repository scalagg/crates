package gg.scala.crates

import gg.scala.commons.ExtendedScalaPlugin
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
        PluginDependency("Lemon")
    ]
)
class CratesSpigotPlugin : ExtendedScalaPlugin()
{
}
