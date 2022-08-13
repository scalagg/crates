package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.CratePrize
import net.evilblock.cubed.menu.Button

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
abstract class CompositeCratePrize<T : CompositeCratePrizeCreatorSession>
{
    abstract fun getName(): String
    abstract fun createSession(): T

    abstract fun editorButtons(session: T): List<Button>
    abstract fun create(session: T): CratePrize
}
