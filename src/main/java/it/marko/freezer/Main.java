/*
 * Copyright (c) 2020 MemoryOfLife
 * This file (Main.java) and its related project (Freezer) are governed by the Apache 2.0 license.
 * You may not use them except in compliance with the License which can be found at:
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package it.marko.freezer;

import it.marko.freezer.commands.FreezeExecutor;
import it.marko.freezer.listeners.InitListeners;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Main extends JavaPlugin {
    /**
     * Prefisso del plugin per messaggi in chat. Utilizza i {@link ChatColor color codes} di Bukkit.
     *
     * @see ChatColor
     */
    public static final String PREFIX = ChatColor.AQUA + "" + ChatColor.BOLD + "[Freezer] " + ChatColor.RESET;
    private static Main instance;
    private File freezedFile;

    /**
     * Usa questo medoto per ottenere l'istanza della classe {@link Main}
     *
     * @return L'istanza corrente del plugin
     * @see JavaPlugin
     */
    public static Main getInstance() {
        return instance;
    }

    /**
     * Usa questo metodo per ottenere un'instanza di {@link YamlConfiguration} contenente i players freezati
     */
    public YamlConfiguration getFreezedPlayers() {
        return YamlConfiguration.loadConfiguration(freezedFile);
    }

    /**
     * Usa questo metodo per salvare un'instanza di {@link YamlConfiguration} contenente i players freezati.
     *
     * Nel caso in cui il {@link File} non dovesse essere disponibile il plugin verrà disattivato
     *
     * @param freezedPlayers Istanza di {@link YamlConfiguration} contenente i players freezati
     */
    public void saveFreezedPlayers(@NotNull YamlConfiguration freezedPlayers) {
        try {
            freezedPlayers.save(freezedFile);
        } catch (IOException e) {
            //stampo l'errore
            getLogger().log(Level.WARNING, PREFIX + "Errore nel salvare il file freezed.yml. Esiste?");
            getLogger().log(Level.WARNING, ChatColor.RED + "" + ChatColor.BOLD + "===== " + PREFIX + ChatColor.RED + "" + ChatColor.BOLD + "Inizio Report =====");
            e.printStackTrace();
            getLogger().log(Level.WARNING, ChatColor.RED + "" + ChatColor.BOLD + "===== Fine Report =====");

            //disabilito il plugin
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();

        //cancello l'instanza
        instance = null;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        //creo l'instanza
        instance = this;

        //registro gli event listeners
        new InitListeners(this).bind();

        //registro il comando
        getCommand("freeze").setExecutor(new FreezeExecutor());

        //apro il file degli utenti freezati
        File f = new File(getDataFolder(), "freezed.yml");

        //creo cartella e file se non esistono
        try {
            //creo cartella
            if (!f.getParentFile().exists())
                //noinspection ResultOfMethodCallIgnored
                f.getParentFile().mkdir();

            //creo file
            if (!f.exists())
                //noinspection ResultOfMethodCallIgnored
                f.createNewFile();

        } catch (IOException e) {
            //stampo l'errore
            getLogger().log(Level.WARNING, PREFIX + "Errore nel creare il file freezed.yml. Ho i permessi?");
            getLogger().log(Level.WARNING, ChatColor.RED + "" + ChatColor.BOLD + "===== " + PREFIX + ChatColor.RED + "" + ChatColor.BOLD + "Inizio Report =====");
            e.printStackTrace();
            getLogger().log(Level.WARNING, ChatColor.RED + "" + ChatColor.BOLD + "===== Fine Report =====");

            //disabilito il plugin
            getServer().getPluginManager().disablePlugin(this);

        }

        //assegno il config
        freezedFile = f;
    }
}
