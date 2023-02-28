package at.htlgkr.steamgameapp;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import at.htlgkr.steam.Game;

public class GameAdapter extends BaseAdapter {
    private List<Game> gameList = new ArrayList<>();
    private int layoutId;
    private LayoutInflater inflater;
    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        this.gameList = games;
        this.layoutId = listViewItemLayoutId;
        this.inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        return gameList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View givenView, ViewGroup parent) {
        Game game = gameList.get(position);
        View listItem = (givenView == null) ? inflater.inflate(this.layoutId, parent) : givenView; // won wos ned geht schreib null stott parent
        ((TextView) listItem.findViewById(R.id.txt_name)).setText(game.getName());
        ((TextView) listItem.findViewById(R.id.txt_date)).setText(game.getReleaseDate().toString());
        ((TextView) listItem.findViewById(R.id.txt_price)).setText(String.valueOf(game.getPrice()));
        return listItem;
    }
}
