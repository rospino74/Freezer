/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (FreezerExecutor.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer.commands

import it.marko.freezer.FreezedLister
import it.marko.freezer.Main
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

internal class FreezerExecutor : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {

        //errore se non è specificato primo argomento
        if (args.isEmpty()) {
            sender.sendMessage(Main.PREFIX + "Inserisci il sotto-comando!")
            return false
        }
        //errore se troppi argomenti sono inseriti
        if (args.size > 1) {
            sender.sendMessage(Main.PREFIX + "Troppi argomenti inseriti!")
            return false
        }

        when (args[0]) {
            "reload" -> {
                //ricarico la configurazione
                Main.getInstance().loadConfig()

                //avviso il sender
                sender.sendMessage(Main.PREFIX + "Reload Completato!")
            }
            "unfreeze-all" -> {
                //prendo la configurazione
                val c = Main.getInstance().freezedPlayers

                //svuoto la lista
                c.set("freezed", ArrayList<String>())

                //salvo la configurazione
                Main.getInstance().saveFreezedPlayers(c)

                //avviso il sender
                sender.sendMessage(Main.PREFIX + "Tutti i player possono ora muoversi!")
            }
            "list" -> FreezedLister(sender).exec()
            else -> sender.sendMessage(Main.PREFIX + "Comando non riconosciuto!")
        }

        return true
    }
}
