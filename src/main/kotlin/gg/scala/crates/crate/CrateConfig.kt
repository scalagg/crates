package gg.scala.crates.crate

import gg.scala.commons.config.convert.annotations.ConfigConverted
import gg.scala.commons.config.convert.annotations.ConfigFileName
import gg.scala.commons.config.convert.annotations.ConfigLateInject

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
@ConfigLateInject
@ConfigConverted("dummy")
@ConfigFileName("items.json")
data class CrateConfig(
    val crates: MutableList<Crate>
)
