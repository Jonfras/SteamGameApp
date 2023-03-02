package at.htlgkr.steamgameapp;


import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.htlgkr.steam.Game;
import at.htlgkr.steam.GameFilter;

public class GameAdapter extends BaseAdapter {
    private List<Game> gameList = new ArrayList<>();
    private List<Game> originalList;
    private int layoutId;
    private LayoutInflater inflater;
    private GameFilter filter;


    public GameAdapter(Context context, int listViewItemLayoutId, List<Game> games) {
        originalList = games;
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

    public Filter getFilter() {
        if (filter == null){
            filter  = new GameFilter();
        }
        return filter;
    }

    private class GameFilter extends Filter {



        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (constraint.toString().length() > 0) {
                ArrayList<Game> filteredItems = new ArrayList<Game>();

                for (int i = 0, l = originalList.size(); i < l; i++) {
                    Game game = originalList.get(i);
                    if (game.toString().toLowerCase().contains(constraint)) {
                        filteredItems.add(game);
                    }
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            } else {
                synchronized (this) {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        //TODO des verstehen
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            gameList = (ArrayList<Game>)results.values;
            notifyDataSetChanged();
            for(int i = 0, l = gameList.size(); i < l; i++)
                add(gameList.get(i));
            notifyDataSetInvalidated();
        }
    }
}
