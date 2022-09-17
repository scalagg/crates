package gg.scala.crates.keys

import gg.scala.crates.crate.Crate
import gg.scala.crates.player.CratesPlayerService
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 9/17/2022
 */
object DefaultKeyProvider : KeyProvider
{
    override fun getKeysFor(player: UUID, crate: Crate): Int
    {
        val cratesPlayer = CratesPlayerService.find(player)
            ?: return 0

        return cratesPlayer.balances[crate.uniqueId] ?: 0
    }

    override fun useKeyFor(player: UUID, crate: String): CompletableFuture<Void>
    {
        val cratesPlayer = CratesPlayerService.find(player)
            ?: return CompletableFuture.completedFuture(null)

        cratesPlayer.balances[crate] =
            (cratesPlayer.balances[crate] ?: 1) - 1

        return cratesPlayer.save()
    }

    override fun addKeysFor(player: UUID, crate: String, amount: Int): CompletableFuture<Void>
    {
        val cratesPlayer = CratesPlayerService.find(player)
            ?: return CompletableFuture.completedFuture(null)

        cratesPlayer.balances[crate] =
            (cratesPlayer.balances[crate] ?: 0) + amount

        return cratesPlayer.save()
    }
}
