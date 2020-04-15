/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (MoveListener.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer.listeners

import it.marko.freezer.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

internal class MoveListener : Listener {
    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        val p = e.player
        val list = Main.getInstance().freezedPlayers

        // cancello evento se è nella lista e non ha il permesso
        if (list.getStringList("freezed").contains(p.uniqueId.toString()) && !p.hasPermission("freezer.ignore")) {
            e.isCancelled = true

            //permetto movimento della testa
            p.location.pitch = e.to.pitch
            p.location.yaw = e.to.yaw
        }
    }
}