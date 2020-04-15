/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (InteractListener.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer.listeners

import it.marko.freezer.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

internal class PlaceListener : Listener {
    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        val p = e.player
        val list = Main.getInstance().freezedPlayers

        // cancello evento se è nella lista e non è ignorabile
        if (list.getStringList("freezed").contains(p.uniqueId.toString()) && !p.hasPermission("freezer.ignore"))
            e.isCancelled = true
    }
}