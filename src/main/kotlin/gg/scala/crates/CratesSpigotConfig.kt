package gg.scala.crates

import net.evilblock.cubed.util.CC
import xyz.mkotb.configapi.comment.Comment

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CratesSpigotConfig
{
    @Comment("The menu scheme.")
    val menuScheme = arrayOf(
        "0X00X00X0",
        "X1XX0XX0X",
        "0X00X00X0"
    )

    @Comment("The following fields are configurable messages.")
    val noKeyOpenAttempt = "${CC.RED}You do not have a key for this crate!"
    val internalError = "${CC.RED}Something went terribly wrong while trying to perform this action."

    val crateCreated = "${CC.GREEN}Successfully created a new crate!"

    val crateDeletionQuit = "${CC.RED}We did not delete the crate."
    val crateDeletionSuccess = "${CC.GREEN}Successfully deleted the crate!"

    val crateBalanceHeader = "${CC.GRAY}Current crate key balance:"
    val crateBalanceEntry = " ${CC.AQUA}<crateDisplayName>: ${CC.WHITE}<crateKeyBalance>"
}
