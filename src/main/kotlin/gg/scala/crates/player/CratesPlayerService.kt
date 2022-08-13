package gg.scala.crates.player

import gg.scala.commons.persist.ProfileOrchestrator
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import java.util.*

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@Service
object CratesPlayerService : ProfileOrchestrator<CratesPlayer>()
{
    @Configure
    fun configure()
    {
        this.subscribe()
    }

    override fun new(uniqueId: UUID) = CratesPlayer(uniqueId)
    override fun type() = CratesPlayer::class
}
