package at.htlgkr.steamgameapp;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.ReportType;
import at.htlgkr.steam.SteamBackend;

public class MainActivity extends AppCompatActivity {
    private static final String GAMES_CSV = "games.csv";
    private static SteamBackend steamBackend = new SteamBackend();
    private static final File FILE = new File(GAMES_CSV);

    private ListView listView;
    private GameAdapter gameAdapter;
    private List<Game> gameList;

    private Spinner spinner;
    private ArrayAdapter<ReportTypeSpinnerItem> reportTypeArrayAdapter;
    private List<ReportTypeSpinnerItem> reportTypeSpinnerItems;

    Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.gamesList);
        spinner = findViewById(R.id.chooseReport);
        search = findViewById(R.id.search);

        loadGamesIntoListView();
        setUpReportSelection();
        setUpSearchButton();
        setUpAddGameButton();
        setUpSaveButton();



        /*
          Listener für spinner
         */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = getDialog(position);
                alertDialog.show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("Nothing selected"); //TODO wenns geht des wegdoa
            }
        });

        /*
          Listener für search Button
         */
        search.setOnClickListener(v -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());

            final EditText input = new EditText(getApplicationContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            input.setId(R.id.dialog_search_field);

            alert.setView(input);
            alert.setTitle(SteamGameAppConstants.ENTER_SEARCH_TERM);
            alert.setPositiveButton("Search", (dialogInterface, i) -> {
                gameAdapter.filter(input.getText());
            });
            alert.setNegativeButton("Cancel", null);
            alert.show();
        });


    }

    private AlertDialog.Builder getDialog(int position) {
        AlertDialog.Builder alertDialog = null;
        switch (position) {
            case 2:
                alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setMessage(SteamGameAppConstants.ALL_PRICES_SUM
                                + steamBackend.sumGamePrices())

                        .setNeutralButton("OK", null);
                break;

            case 3:
                alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setMessage(SteamGameAppConstants.ALL_PRICES_AVERAGE
                                + steamBackend.averageGamePrice())

                        .setNeutralButton("OK", null);
                break;

            case 4:
                alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setMessage(SteamGameAppConstants.UNIQUE_GAMES_COUNT
                                + steamBackend.getUniqueGames().size())

                        .setNeutralButton("OK", null);
                break;

            case 5:
                List<Game> list = steamBackend.selectTopNGamesDependingOnPrice(3);

                alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setMessage(SteamGameAppConstants.MOST_EXPENSIVE_GAMES
                                + "\n" + list.get(0) + "\n" + list.get(1) + "\n" + list.get(2))

                        .setNeutralButton("OK", null);
                break;

            default:
                System.out.println("Dummy");
                break;
        }
        return alertDialog;
    }

    private void loadGamesIntoListView() {
        steamBackend.loadGames(getInputStream());

        gameAdapter = new GameAdapter(this, R.layout.game_item_layout, steamBackend.getGames());
        listView.setAdapter(gameAdapter);

        listView.setTextFilterEnabled(true);
    }

    private void setUpReportSelection() {
        reportTypeSpinnerItems = new ArrayList<>();

        reportTypeSpinnerItems.add(new ReportTypeSpinnerItem(ReportType.NONE, SteamGameAppConstants.SELECT_ONE_SPINNER_TEXT));
        reportTypeSpinnerItems.add(new ReportTypeSpinnerItem(ReportType.SUM_GAME_PRICES, SteamGameAppConstants.SUM_GAME_PRICES_SPINNER_TEXT));
        reportTypeSpinnerItems.add(new ReportTypeSpinnerItem(ReportType.AVERAGE_GAME_PRICES, SteamGameAppConstants.AVERAGE_GAME_PRICES_SPINNER_TEXT));
        reportTypeSpinnerItems.add(new ReportTypeSpinnerItem(ReportType.UNIQUE_GAMES, SteamGameAppConstants.UNIQUE_GAMES_SPINNER_TEXT));
        reportTypeSpinnerItems.add(new ReportTypeSpinnerItem(ReportType.MOST_EXPENSIVE_GAMES, SteamGameAppConstants.MOST_EXPENSIVE_GAMES_SPINNER_TEXT));

        reportTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, reportTypeSpinnerItems);
        spinner.setAdapter(reportTypeArrayAdapter);
    }

    private void setUpSearchButton() {

    }

    private void setUpAddGameButton() {
        // Implementieren Sie diese Methode.
    }

    private void setUpSaveButton() {
        // Implementieren Sie diese Methode.
    }

    public InputStream getInputStream() {
        try {
            return new FileInputStream(FILE);
        } catch (FileNotFoundException e) {
            System.err.println("FileInputStream couldn't be opened...");
        }
        return null;
    }

    public OutputStream getOutputStream() {
        try {
            return new FileOutputStream(FILE);
        } catch (FileNotFoundException e) {
            System.err.println("FileOutputStream couldn't be opened...");
        }
        return null;
    }

}
