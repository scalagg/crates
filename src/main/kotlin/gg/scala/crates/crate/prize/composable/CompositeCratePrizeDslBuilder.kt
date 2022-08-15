package gg.scala.crates.crate.prize.composable

import gg.scala.crates.crate.prize.CratePrize
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
inline fun <reified T : CratePrize> composite(
    lambda: CompositeCratePrizeBuilder.() -> Unit
)
{
    val builder = CompositeCratePrizeBuilder()
    builder.lambda()

    CompositeCratePrizeService
        .composites[T::class] = builder.compose()
}

class CompositeCratePrizeBuilder
{
    lateinit var name: String

    private lateinit var sessionCreator: (CratePrize?) -> CompositeCratePrizeEditSession

    private lateinit var prizeCreator: (CompositeCratePrizeEditSession) -> CratePrize
    private lateinit var prizeUpdater: (CompositeCratePrizeEditSession, CratePrize) -> Unit

    private val buttons = mutableListOf<(CompositeCratePrizeEditSession, Menu) -> Button>()

    fun createSession(
        lambda: CratePrize?.() -> CompositeCratePrizeEditSession
    )
    {
        this.sessionCreator = lambda
    }

    fun createPrize(
        lambda: CompositeCratePrizeEditSession.() -> CratePrize
    )
    {
        this.prizeCreator = lambda
    }

    fun updatePrize(
        lambda: (CompositeCratePrizeEditSession, CratePrize) -> Unit
    )
    {
        this.prizeUpdater = lambda
    }

    fun button(
        lambda: (CompositeCratePrizeEditSession, Menu) -> Button
    )
    {
        this.buttons.add(lambda)
    }

    fun compose() = object : CompositeCratePrize()
    {
        override fun getName() = name

        override fun createSession() = sessionCreator(null)
        override fun createSessionFromExisting(existing: CratePrize) = sessionCreator(existing)

        override fun update(session: CompositeCratePrizeEditSession, prize: CratePrize) = prizeUpdater(session, prize)
        override fun create(session: CompositeCratePrizeEditSession) = prizeCreator(session)

        override fun editorButtons(session: CompositeCratePrizeEditSession, menu: Menu) = buttons.map { it(session, menu) }
    }
}
