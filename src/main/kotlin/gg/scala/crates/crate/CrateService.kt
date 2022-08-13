package gg.scala.crates.crate

import gg.scala.crates.crate.prize.CratePrize
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.store.controller.DataStoreObjectControllerCache
import net.evilblock.cubed.serializers.Serializers
import net.evilblock.cubed.serializers.impl.AbstractTypeSerializer
import java.util.UUID

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CrateService
{
    val crates = mutableMapOf<UUID, Crate>()

    @Configure
    fun configure()
    {
        Serializers.create {
            this.registerTypeAdapter(
                CratePrize::class.java,
                AbstractTypeSerializer<CratePrize>()
            )
        }

        DataStoreObjectControllerCache.create<Crate>()
    }
}
