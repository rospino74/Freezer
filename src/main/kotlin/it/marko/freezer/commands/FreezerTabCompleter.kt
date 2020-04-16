/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (FreezerCompleter.java) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package it.marko.freezer.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.util.*

class FreezerTabCompleter : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        //crea lista di sotto comandi
        val subCommands = arrayOf("reload", "list", "unfreeze-all")

        //ritorno la lista di elementi
        return MutableList(subCommands.size) {
            subCommands[it]
        }
    }
}
