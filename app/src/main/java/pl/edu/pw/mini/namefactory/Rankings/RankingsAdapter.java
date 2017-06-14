package pl.edu.pw.mini.namefactory.Rankings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.edu.pw.mini.namefactory.*;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperAdapter;
import pl.edu.pw.mini.namefactory.DatabasePackage.DatabaseHandler;

/**
 * Created by Asus on 23.04.2017.
 */

public class RankingsAdapter extends RecyclerView.Adapter<RankingsAdapter.ViewHolder> implements SwipeHelperAdapter {

    private List<Ranking> rankingsList;
    private final Context context;
    private DatabaseHandler dbh;

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
        public TextView ranking, name1, name2, name3;

        public ViewHolder(View view) {
            super(view);
            ranking = (TextView) view.findViewById(R.id.ranking);
            name1 = (TextView) view.findViewById(R.id.name1);
            name2 = (TextView) view.findViewById(R.id.name2);
            name3 = (TextView) view.findViewById(R.id.name3);
        }
    }

    public RankingsAdapter(final List<Ranking> rankingsList, Context context, ShowRankingsFragment.OnRankingsListFragmentInteractionListener rListener) {
        this.rankingsList = rankingsList;
        this.context = context;
        this.dbh = RankingsListMain.dbh;
    }

    @Override
    public RankingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rankings_list_row, parent, false);

        return new RankingsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingsAdapter.ViewHolder holder, int position) {

        Ranking ranking = rankingsList.get(position);
        holder.ranking.setText(ranking.getRankingName());
        holder.name1.setText(ranking.getNames().get(0).getName());
        holder.name2.setText(ranking.getNames().get(1).getName());
        holder.name3.setText(ranking.getNames().get(2).getName());
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
