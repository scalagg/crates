package gg.scala.crates.menu

import gg.scala.commons.acf.ConditionFailedException
import gg.scala.crates.configuration
import gg.scala.crates.crate.Crate
import gg.scala.crates.crate.prize.CratePrize
import gg.scala.crates.keyProvider
import gg.scala.crates.sendDebug
import gg.scala.crates.sendToPlayer
import me.lucko.helper.random.RandomSelector
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 8/14/2022
 */
class CrateOpenMenu(
    private val player: Player,
    private val crate: Crate
) : Menu(
    configuration.crateOpenTitle
)
{
    companion object
    {
        const val ITERATION_SPEED = 700L
    }

    private var crateRollStopped = false
    private var manuallyClosed = false

    private val applicable = this.crate.prizes
        .shuffled()
        .filter {
            (this.crate.applicable || it.applicableTo(this.player))
        }
        .toMutableList()

    private val itemsRequired = LinkedList<CratePrize>()
    private val selectedRandom: CratePrize

    private var expectedIterationAmount = 0
    private var iterationAmount = 0

    private var start: Long? = null

    init
    {
        if (applicable.isEmpty())
        {
            throw ConditionFailedException("You cannot win any more items from this crate.")
        }

        autoUpdateInterval = 10L

        placeholdBorders = true
        autoUpdate = true

        sendDebug("=== Developer Debug ===")

        while (autoUpdateInterval <= ITERATION_SPEED)
        {
            expectedIterationAmount += 1
            autoUpdateInterval += 20L
        }

        sendDebug("Required by logic: $expectedIterationAmount")

        this.selectedRandom = RandomSelector
            .weighted(applicable).pick()

        sendDebug("Selected: ${selectedRandom.name}")

        while (expectedIterationAmount > itemsRequired.size)
        {
            itemsRequired += applicable.shuffled()
            sendDebug("  - Added applicable: ${applicable.size}, now ${itemsRequired.size}")
        }

        while (itemsRequired.size != expectedIterationAmount)
        {
            itemsRequired.removeLast()
            sendDebug("  - Removed element: now ${itemsRequired.size}")
        }

        val shuffled = applicable.shuffled()

        itemsRequired.removeLast()
        itemsRequired += shuffled.take(5)
        itemsRequired.add(selectedRandom)
        itemsRequired += shuffled

        sendDebug("  - Added selected random: now ${itemsRequired.size}")
        sendDebug("=======================")

        // reset our auto-update interval after we decide the end choice
        autoUpdateInterval = 10L
    }

    override fun getButtons(player: Player): Map<Int, Button>
    {
        if (start == null)
        {
            start = System.currentTimeMillis()
        }

        if (autoUpdateInterval >= ITERATION_SPEED || manuallyClosed)
        {
            sendDebug(
                "Took ${System.currentTimeMillis() - start!!} ms to roll"
            )
            sendDebug(
                "Went through $iterationAmount iterations, expected $expectedIterationAmount"
            )
            sendDebug("=======================")

            crateRollStopped = true
        } else
        {
            autoUpdateInterval += 20L
        }

        val buttons = mutableMapOf<Int, Button>()
        iterationAmount += 1

        if (crateRollStopped)
        {
            this.autoUpdate = false
            this.selectedRandom.apply(player)

            configuration.crateWin.sendToPlayer(
                player, "<cratePrizeName>" to this.selectedRandom.name
            )

            player.playSound(player.location, Sound.FIREWORK_LAUNCH, 1.0F, 1.0F)
            player.closeInventory()
        } else
        {
            // shift last to first, pushes everything else forward
            this.itemsRequired.removeFirst()
            player.playSound(player.location, Sound.CLICK, 1.0F, 1.0F)
        }

        // add items in the current index to the button map
        for (index in 1..7)
        {
            val prizeInIndex = if (this.crateRollStopped)
            {
                this.selectedRandom
            } else
            {
                this.itemsRequired
                    .getOrNull(index)
                    ?: continue
            }

            if (this.crateRollStopped && index != 4)
            {
                buttons[index] = Button.placeholder(
                    Material.STAINED_GLASS_PANE, 5, "${CC.GREEN}You won!"
                )
                continue
            }

            buttons[index] = ItemBuilder
                .copyOf(prizeInIndex.material)
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

            if (this.autoUpdateInterval >= 400)
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
        keyProvider().addKeysFor(player.uniqueId, crate.uniqueId, 1)
    }
}
