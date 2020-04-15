/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (InitListeners.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer.listeners

import it.marko.freezer.Main

/**
 * Instanziatore dei [listeners][org.bukkit.event.Listener].
 *
 * Per registrare i [listeners][org.bukkit.event.Listener] invocare il metodo [bind]
 *
 * @param plugin Il plugin dove instanziare i listeners
 */
class InitListeners(private val plugin: Main) {
    private val classes = arrayOf(MoveListener::class, InteractListener::class, PlaceListener::class, BreakListener::class)

    /**
     * Instanzia le classi dei [listeners][org.bukkit.event.Listener]
     */
    fun bind() {
        classes.forEach { c -> plugin.server.pluginManager.registerEvents(c.constructors.first().call(), plugin) }
    }

}