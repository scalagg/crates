package gg.scala.crates.crate

import gg.scala.crates.crate.prize.CratePrize

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class Crate(
    val uniqueId: String,
    var displayName: String,
    val prizes: MutableList<CratePrize>,
    var applicable: Boolean = false
)
