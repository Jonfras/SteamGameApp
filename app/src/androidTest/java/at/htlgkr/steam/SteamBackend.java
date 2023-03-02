package at.htlgkr.steam;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SteamBackend {

    private List<Game> gameList = new ArrayList<>();
    private final String REGEX = ";";
    private final String HEADER = "Name;Release Date;Price";
    private final File FILE = new File("assets/games.csv");


    public SteamBackend() {

    }


    public void loadGames(InputStream inputStream) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            br.readLine();
            while (br.ready()) {
                gameList.add(Game.deserialize(br.readLine()));
            }
        } catch (IOException e) {
            System.err.println("There was an error reading the CSV-File...");
        }

        //gameList.forEach(System.out::println);
    }


    public void store(OutputStream fileOutputStream) {
        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(fileOutputStream));
            for (Game game :
                    gameList) {
                pw.println(Game.serialize(game));
            }
            pw.flush();
        } catch (Exception e) {
            System.err.println("There was an error writing the Games into the File...");
        }
    }

    public List<Game> getGames() {
        return gameList;
    }

    public void setGames(List<Game> games) {
        gameList = new ArrayList<>();
        gameList.addAll(games);
    }

    public void addGame(Game newGame) {
        gameList.add(newGame);
    }

    public double sumGamePrices() {
        return gameList.stream()
                .mapToDouble(Game::getPrice)
                .sum();
    }

    public double averageGamePrice() {
        return gameList.stream()
                .mapToDouble(Game::getPrice)
                .average()
                .getAsDouble();
    }

    public List<Game> getUniqueGames() {
        List<Game> games = new ArrayList<>();
        for (Game game : gameList) {
            if (!games.stream()
                    .map(Game::getName)
                    .collect(Collectors.toList())
                    .contains(game.getName())){

                games.add(game);
            }
        }
        return games;
    }

    public List<Game> selectTopNGamesDependingOnPrice(int n) {
        return gameList.stream()
                .sorted(Comparator.comparingDouble(Game::getPrice).reversed())
                .collect(Collectors.toList())
                .subList(0, n);
    }
}

