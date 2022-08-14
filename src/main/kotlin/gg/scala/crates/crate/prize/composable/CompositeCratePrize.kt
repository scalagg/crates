package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.CratePrize
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
abstract class CompositeCratePrize
{
    abstract fun getName(): String

    abstract fun createSession(): CompositeCratePrizeEditSession
    abstract fun createSessionFromExisting(existing: CratePrize): CompositeCratePrizeEditSession

    abstract fun editorButtons(session: CompositeCratePrizeEditSession, menu: Menu): List<Button>

    abstract fun create(session: CompositeCratePrizeEditSession): CratePrize
    abstract fun update(session: CompositeCratePrizeEditSession, prize: CratePrize)
}
