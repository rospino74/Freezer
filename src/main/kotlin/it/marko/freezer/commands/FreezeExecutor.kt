/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (FreezeExecutor.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer.commands

import it.marko.freezer.Main
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

internal class FreezeExecutor : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        //configurazione
        val c = Main.getInstance().freezedPlayers

        //errore se non specificato player
        if (args.isEmpty()) {
            sender.sendMessage(Main.PREFIX + "Inserisci il nome del player!")
            return false
        }
        //errore se troppi argomenti inseriti
        if (args.size > 1) {
            sender.sendMessage(Main.PREFIX + "Troppi argomenti inseriti!")
            return false
        }

        //prendo il player specificato
        val p = Bukkit.getPlayer(args[0])
        //prendo la lista
        val list = c.getStringList("freezed")

        //se il player è nella lista...
        if (list.contains(p.uniqueId.toString())) {
            //...lo rimuovo
            list.remove(p.uniqueId.toString())
            //e avviso della rimozione
            sender.sendMessage(Main.PREFIX + "${p.displayName} non è più freezato")
        } else {
            //...altrimenti lo aggiungo
            list.add(p.uniqueId.toString())
            //e avviso dell'inserimento
            sender.sendMessage(Main.PREFIX + "${p.displayName} è ora freezato")
        }

        //salvo la lista
        c.set("freezed", list)
        Main.getInstance().saveFreezedPlayers(c)

        return true
    }
}
