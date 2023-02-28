package at.htlgkr.steamgameapp;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import at.htlgkr.steam.SteamBackend;

public class SteamBackendTest {
    private final File file = new File("java/test.csv");

    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Test
    public void loadGames() {
        SteamBackend steamBackend = new SteamBackend();
        try {
            steamBackend.loadGames(getInputStream());
        } catch (Exception e) {
            System.err.println("File wasnt found or couldnt be accessed...");
        }
    }

    @Test
    public void deserialize() {
    }
}