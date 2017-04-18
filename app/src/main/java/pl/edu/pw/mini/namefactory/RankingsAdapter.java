package pl.edu.pw.mini.namefactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by Asus on 14.04.2017.
 */

public class RankingsAdapter extends RecyclerView.Adapter<RankingsAdapter.ViewHolder> implements SwipeHelperAdapter {

    private List<Ranking> rankingsList;
    private final Context context;
    private DatabaseHandler dbh;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(rankingsList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(rankingsList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

        Ranking element = rankingsList.get(position);
        remove(element);
        Toast.makeText(context, element.getRankingName() + " is deleted!", Toast.LENGTH_SHORT).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ranking, names;

        public ViewHolder(View view) {
            super(view);
            ranking = (TextView) view.findViewById(R.id.ranking);
            names = (TextView) view.findViewById(R.id.names);
        }
    }

    public RankingsAdapter(final List<Ranking> rankingsList, Context context) {
        this.rankingsList = rankingsList;
        this.context = context;
        this.dbh = RankingList.dbh;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                       /* if (key.equals("checkbox_edit_preference")) {
                        }
                        else if(key.equals("checkbox_mark_preference"))
                        {

                        }
                        else if(key.equals("checkbox_mark_color_preference"))
                        {
                            elemColor = prefs.getInt("checkbox_mark_color_preference",0);
                            for(ListPoint product:productsList)
                                mark(product, product.getisBought());
                        }*/
                    }
                };
        sharedPref.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public RankingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rankings_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingsAdapter.ViewHolder holder, int position) {

        Ranking product = rankingsList.get(position);
        holder.ranking.setText(product.getRankingName());
        holder.names.setText(product.getNamesString());
    }

    @Override
    public int getItemCount() {
        return rankingsList.size();
    }

/*    // Insert a new item to the RecyclerView on a predefined position
    public void insert(Ranking data) {
        rankingsList.add(data);
        notifyItemInserted(getItemCount());
    }*/

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Ranking data) {
        int position = rankingsList.indexOf(data);
        rankingsList.remove(position);
        dbh.deleteRanking(data.getID());
        notifyItemRemoved(position);
    }
}
