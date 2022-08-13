package gg.scala.crates.crate

import gg.scala.commons.acf.ConditionFailedException
import gg.scala.commons.annotations.commands.customizer.CommandManagerCustomizer
import gg.scala.commons.command.ScalaCommandManager
import gg.scala.crates.CratesSpigotPlugin
import gg.scala.crates.crate.prize.CratePrize
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import net.evilblock.cubed.serializers.Serializers
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializer
import net.evilblock.cubed.util.CC

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CrateService
{
    @Inject
    lateinit var plugin: CratesSpigotPlugin

    fun allCrates() = this.plugin.config<CrateConfig>().crates

    fun findCrate(name: String) =
        this.plugin.config<CrateConfig>().crates
            .firstOrNull {
                it.uniqueId == name
            }

    @Configure
    fun configure()
    {
        Serializers.create {
            this.registerTypeAdapter(
                CratePrize::class.java,
                AbstractTypeSerializer<CratePrize>()
            )
        }
    }

    @CommandManagerCustomizer
    fun customize(manager: ScalaCommandManager)
    {
        manager.commandContexts
            .registerContext(Crate::class.java) { context ->
                val firstArg = context.popFirstArg()

                this.plugin.config<CrateConfig>().crates
                    .firstOrNull {
                        it.uniqueId == firstArg
                    }
                    ?: throw ConditionFailedException(
                        "No crate by the name ${CC.YELLOW}$firstArg${CC.RED} exists."
                    )
            }

        manager.commandCompletions
            .registerCompletion("crates") {
                this.plugin.config<CrateConfig>().crates.map { it.uniqueId }
            }
    }
}
