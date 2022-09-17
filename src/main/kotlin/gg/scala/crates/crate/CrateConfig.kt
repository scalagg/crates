package gg.scala.crates.crate

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
data class CrateConfig(
    val crates: MutableList<Crate> = mutableListOf(
        Crate("testing", "heya", mutableListOf())
    )
)
