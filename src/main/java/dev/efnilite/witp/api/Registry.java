package dev.efnilite.witp.api;

import dev.efnilite.witp.api.gamemode.DefaultGamemode;
import dev.efnilite.witp.api.gamemode.Gamemode;
import dev.efnilite.witp.api.gamemode.SpectatorGamemode;
import dev.efnilite.witp.util.Verbose;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class which features registration for custom modes/addons
 */
public final class Registry {

    private volatile boolean closed;
    private final HashMap<String, Gamemode> gamemodes;

    public Registry() {
        this.gamemodes = new LinkedHashMap<>();

        gamemodes.put("default", new DefaultGamemode());
        gamemodes.put("spectator", new SpectatorGamemode());
    }

    public @Nullable Gamemode getGamemode(String name) {
        return gamemodes.get(name);
    }

    public void register(Gamemode gamemode) {
        if (!closed) {
            this.gamemodes.put(gamemode.getName(), gamemode);
            Verbose.info("Registered gamemode " + gamemode.getName() + "!");
        } else {
            throw new IllegalStateException("Register attempt while registry is closed");
        }
    }

    public List<Gamemode> getGamemodes() {
        return new ArrayList<>(gamemodes.values());
    }

    public void close() {
        closed = true;
    }
}