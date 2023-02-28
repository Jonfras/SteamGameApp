package at.htlgkr.steam;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public class Game {
    public static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter LOCALDATE_FORMAT = DateTimeFormatter.ofPattern(DATE_FORMAT);
    private static final String REGEX = ";";

    private String name;
    private Date releaseDate;
    private double price;

    public Game() {
        // dieser Konstruktor muss existieren
    }

    public Game(String name, Date releaseDate, double price) {
        this.name = name;
        this.releaseDate = releaseDate;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[" +  new SimpleDateFormat(DATE_FORMAT).format(this.releaseDate) + "] " + name + " " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Game.class){
            if (((Game) o).name.equals(this.name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseDate, price);
    }

    public static Game deserialize(String g) {
        String[] parts = g.split(REGEX);
        String[] splittedDate = parts[1].split("\\.");
        Date date = new Date();
        date.setDate(Integer.parseInt(splittedDate[0]));
        date.setMonth(Integer.parseInt(splittedDate[1]) - 1);
        date.setYear(Integer.parseInt(splittedDate[2]) - 1900);

        return new Game(parts[0], date, Double.parseDouble(parts[2]));
    }

    public static String serialize(Game g){
        return g.name + REGEX +  new SimpleDateFormat(DATE_FORMAT).format(g.releaseDate) + REGEX + g.price;
    }
}

