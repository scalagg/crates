package gg.scala.crates

import net.evilblock.cubed.util.CC
import xyz.mkotb.configapi.comment.Comment

/**
 * @author GrowlyX
 * @since 8/13/2022
 */
class CratesSpigotConfig
{
    val debugMode = false

    @Comment("The /crates menu layout mapping.")
    val menuScheme = arrayOf(
        "ZXZZXZZXZ",
        "XaXXZXXZX",
        "ZXZZXZZXZ"
    )

    val displayCratesBalanceOnCratesDefaultCommand = true

    @Comment("The following fields are configurable messages.")
    val noKeyOpenAttempt = "${CC.RED}You do not have a key for this crate!"
    val internalError = "${CC.RED}Something went terribly wrong while trying to perform this action."

    val crateCreated = "${CC.GREEN}Successfully created a new crate!"

    val crateDeletionQuit = "${CC.RED}We did not delete the crate."
    val crateDeletionSuccess = "${CC.GREEN}Successfully deleted the crate!"

    val crateBalanceHeader = "${CC.GRAY}Current crate key balance:"
    val crateBalanceEntry = " ${CC.AQUA}<crateDisplayName>: ${CC.WHITE}<crateKeyBalance>"

    val crateWin = "${CC.GREEN}You won ${CC.SEC}<cratePrizeName>${CC.GREEN} from your crate roll!"
    val crateWinRefundSuccess = "${CC.RED}We are refunding your crate key as you closed the menu."
    val crateWinRefundFailure = "${CC.RED}You were not refunded your crate key as you closed the menu too late into roll."
    val crateWinRefundFailureInternal = "${CC.RED}We were unable to refund your crate key. Please contact administration!"
}
