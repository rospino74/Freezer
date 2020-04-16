/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (FreezedLister.kt) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.*

/**
 * Crea una lista di player freezati
 * @param sender [Player][CommandSender] che ha eseguito il comando
 */
@Suppress("KDocUnresolvedReference")
class FreezedLister(private val sender: CommandSender) {
    val c = Main.getInstance().freezedPlayers

    /**
     * Crea la lista ed esegue il comando
     */
    fun exec() {
        val msg = TextComponent(" ${Main.PREFIX} Player freezati:")

        //se la lista è vuota scrivo "vuoto" ed esco
        if (c.getStringList("freezed").isEmpty()) {
            //creo la scritta "(Vuoto)"
            val vuoto = TextComponent("  (Vuoto)")
            vuoto.color = net.md_5.bungee.api.ChatColor.RED
            vuoto.isItalic = true

            //appendo la scritta al messaggio
            msg.addExtra(vuoto)

            //invio il messaggio
            sender.spigot().sendMessage(msg)

            //esco
            return
        }

        c.getStringList("freezed").forEach {
            //prendo il player dall'UUID
            val player = Bukkit.getPlayer(UUID.fromString(it))

            //ritorno se il player è offline o non esiste
            if (player == null || !player.isOnline)
                return

            //TextComponent del nome
            val nome = TextComponent("\n   ${player.displayName + ChatColor.RESET + ChatColor.DARK_GRAY} - ${ChatColor.RESET}")

            //TextComponent del bottone
            val click = TextComponent("Unfreeze")
            click.color = net.md_5.bungee.api.ChatColor.AQUA
            click.isBold = true

            click.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/freeze ${player.name}")
            click.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("Unfreezza il player").create())

            //aggiungo il bottone al nome
            nome.addExtra(click)

            //aggiungo il nome alla lista da inviare
            msg.addExtra(nome)
        }

        //invio il messaggio
        sender.spigot().sendMessage(msg)
    }
}
