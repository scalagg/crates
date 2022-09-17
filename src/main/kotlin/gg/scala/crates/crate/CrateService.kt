package gg.scala.crates.crate

import gg.scala.commons.acf.ConditionFailedException
import gg.scala.commons.annotations.commands.customizer.CommandManagerCustomizer
import gg.scala.commons.command.ScalaCommandManager
import gg.scala.commons.util.Files
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.configuration
import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.keyProvider
import gg.scala.crates.menu.CrateOpenMenu
import gg.scala.crates.player.CratesPlayerService
import gg.scala.crates.sendToPlayer
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import net.evilblock.cubed.serializers.Serializers
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializer
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player
import java.io.File

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service(priority = 100)
object CrateService
{
    @Inject
    lateinit var plugin: CratesSpigotPlugin

    private lateinit var config: CrateConfig

    fun config() = this.config
    fun allCrates() = config().crates

    fun findCrate(name: String) =
        this.config().crates
            .firstOrNull {
                it.uniqueId == name
            }

    fun openCrate(player: Player, crate: Crate)
    {
        val balance = keyProvider()
            .getKeysFor(
                player.uniqueId, crate
            )

        if (balance <= 0)
        {
            configuration.noKeyOpenAttempt.sendToPlayer(player)
            return
        }

        keyProvider().useKeyFor(player.uniqueId, crate.uniqueId)
            .thenRun {
                CrateOpenMenu(player, crate).openMenu(player)
            }
    }

    @Configure
    fun configure()
    {
        Serializers.create {
            this.setPrettyPrinting()
            this.registerTypeAdapter(
                CratePrize::class.java,
                AbstractTypeSerializer<CratePrize>()
            )
        }

        this.loadConfig()
    }

    fun loadConfig()
    {
        val file = File(
            this.plugin.dataFolder, "crates.json"
        )

        if (!file.exists())
        {
            file.createNewFile()

            Files.write(
                Serializers.gson.toJson(CrateConfig()), file
            )
        }

        if (file.readText().isEmpty())
        {
            Files.write(
                Serializers.gson.toJson(CrateConfig()), file
            )
        }

        Files.read(file) {
            this.config = Serializers
                .gson.fromJson(
                    it.readText(), CrateConfig::class.java
                )
        }
    }

    fun saveConfig()
    {
        Files.write(
            Serializers.gson.toJson(this.config),
            File(this.plugin.dataFolder, "crates.json")
        )
    }

    @CommandManagerCustomizer
    fun customize(manager: ScalaCommandManager)
    {
        manager.commandContexts
            .registerContext(Crate::class.java) { context ->
                val firstArg = context.popFirstArg()

                this.config().crates
                    .firstOrNull {
                        it.uniqueId == firstArg
                    }
                    ?: throw ConditionFailedException(
                        "No crate by the name ${CC.YELLOW}$firstArg${CC.RED} exists."
                    )
            }

        manager.commandCompletions
            .registerCompletion("crates") {
                this.config().crates.map { it.uniqueId }
            }
    }
}
