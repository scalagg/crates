package gg.scala.crates.menu

import gg.scala.crates.configuration
import gg.scala.crates.crate.Crate
import gg.scala.crates.player.CratesPlayerService
import gg.scala.crates.sendToPlayer
import me.lucko.helper.Schedulers
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
class CrateOpenMenu(
    private val player: Player,
    private val crate: Crate
) : Menu("Opening crate...")
{
    private var crateRollStopped = false
    private var manuallyClosed = false

    init
    {
        autoUpdateInterval = 10L

        placeholdBorders = true
        autoUpdate = true

        val endChoice = (1000L..1100L).random()

        Schedulers.sync()
            .runRepeating({ task ->
                if (autoUpdateInterval >= endChoice || manuallyClosed)
                {
                    crateRollStopped = true
                    task.closeSilently()
                    return@runRepeating
                }

                autoUpdateInterval += 20L
            }, 4L, 5L)
    }

    private val applicable = this.crate.prizes
        .sortedBy { it.weight }
        .shuffled()
        .filter {
            it.applicableTo(this.player)
        }
        .toMutableList()

    override fun getButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()

        if (crateRollStopped)
        {
            this.autoUpdate = false
            val prize = this.applicable.getOrNull(4)

            if (prize == null)
            {
                configuration.crateWinFailure.sendToPlayer(player)
                player.closeInventory()

                refundCrateKey(player)
                return buttons
            }

            prize.apply(player)

            configuration.crateWin.sendToPlayer(
                player, "<cratePrizeName>" to prize.name
            )

            player.playSound(player.location, Sound.FIREWORK_LAUNCH, 1.0F, 1.0F)
            player.closeInventory()
        } else
        {
            // shift last to first, pushes everything else forward
            val last = this.applicable.removeLast()
            this.applicable.add(0, last)

            player.playSound(player.location, Sound.CLICK, 1.0F, 1.0F)
        }

        // add items in the current index to the button map
        for (index in 1..7)
        {
            val prizeInIndex = this.applicable
                .getOrNull(index)
                ?: continue

            if (this.crateRollStopped && index != 4)
            {
                buttons[index] = Button.placeholder(
                    Material.STAINED_GLASS_PANE, 5, "${CC.GREEN}You won!"
                )
                continue
            }

            buttons[index] = ItemBuilder
                .of(prizeInIndex.material)
                .name("${CC.AQUA}${prizeInIndex.name}")
                .addToLore(
                    "${CC.GRAY}Rarity: ${prizeInIndex.rarity.chatColor}${prizeInIndex.rarity.name}"
                )
                .apply {
                    if (crateRollStopped && index == 4)
                    {
                        this.glow()
                    }
                }
                .toButton()
        }

        return buttons
    }

    override fun onClose(player: Player, manualClose: Boolean)
    {
        if (manualClose && !this.crateRollStopped)
        {
            this.manuallyClosed = true

            if (this.autoUpdateInterval >= 900)
            {
                configuration.crateWinRefundFailure.sendToPlayer(player)
                return
            }

            configuration.crateWinRefundSuccess.sendToPlayer(player)
            this.refundCrateKey(player)
        }
    }

    private fun refundCrateKey(player: Player)
    {
        val cratePlayer = CratesPlayerService.find(player)
            ?: return kotlin.run {
                configuration.crateWinRefundFailureInternal.sendToPlayer(player)
            }

        cratePlayer.balances[crate.uniqueId] =
            (cratePlayer.balances[crate.uniqueId] ?: 0) + 1
        cratePlayer.save()
    }
}
