package gg.scala.crates.keys

import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 9/17/2022
 */
interface KeyProvider
{
    fun getKeysFor(player: UUID, crate: String): Int

    fun useKeyFor(player: UUID, crate: String): CompletableFuture<Void>
    fun addKeysFor(player: UUID, crate: String, amount: Int): CompletableFuture<Void>
}
