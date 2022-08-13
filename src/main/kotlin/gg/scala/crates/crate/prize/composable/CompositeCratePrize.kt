package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.CratePrize
import net.evilblock.cubed.menu.Button

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
abstract class CompositeCratePrize<U : CratePrize>
{
    abstract fun getName(): String
    abstract fun createSession(): CompositeCratePrizeCreatorSession

    abstract fun editorButtons(session: CompositeCratePrizeCreatorSession): List<Button>

    abstract fun create(session: CompositeCratePrizeCreatorSession): CratePrize
    abstract fun update(session: CompositeCratePrizeCreatorSession, prize: U)
}
