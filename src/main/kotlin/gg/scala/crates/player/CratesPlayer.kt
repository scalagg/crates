package gg.scala.crates.player

import gg.scala.common.Savable
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.storable.IDataStoreObject
import gg.scala.store.storage.type.DataStoreStorageType
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CratesPlayer(
    override val identifier: UUID
) : IDataStoreObject, Savable
{
    val balances = mutableMapOf<UUID, Int>()

    override fun save(): CompletableFuture<Void>
    {
        return DataStoreObjectControllerCache
            .findNotNull<CratesPlayer>()
            .save(this, DataStoreStorageType.MONGO)
    }
}
