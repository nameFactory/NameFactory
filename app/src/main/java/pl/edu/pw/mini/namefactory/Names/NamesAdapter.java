package pl.edu.pw.mini.namefactory.Names;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.edu.pw.mini.namefactory.*;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperAdapter;

/**
 * Created by Asus on 23.04.2017.
 */

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> implements SwipeHelperAdapter {

    private List<Name> namesList;
    private final Context context;
    //SharedPreferences.OnSharedPreferenceChangeListener listener;
    //private final RankingViewFragment.OnRankingsListFragmentInteractionListener mListener;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(namesList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(namesList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

        Name element = namesList.get(position);
        remove(element);
        Toast.makeText(context, element.getName() + " is deleted!", Toast.LENGTH_SHORT).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            name = (TextView) view.findViewById(R.id.name);
            img = (ImageView) view.findViewById(R.id.winner);
        }
    }

    public NamesAdapter(final List<Name> namesList, Context context, ShowNamesFragment.OnNamesListFragmentInteractionListener _mlistener) {
        this.namesList = namesList;
        this.context = context;
        //mListener = _mlistener;
        /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                       *//* if (key.equals("checkbox_edit_preference")) {
                        }
                        else if(key.equals("checkbox_mark_preference"))
                        {

                        }
                        else if(key.equals("checkbox_mark_color_preference"))
                        {
                            elemColor = prefs.getInt("checkbox_mark_color_preference",0);
                            for(ListPoint product:productsList)
                                mark(product, product.getisBought());
                        }*//*
                    }
                };
        sharedPref.registerOnSharedPreferenceChangeListener(listener);*/
    }

    @Override
    public NamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_list_row, parent, false);

        return new NamesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NamesAdapter.ViewHolder holder, int position) {

        final Name name = namesList.get(position);

        holder.name.setText(name.getName());
        if(namesList.get(0).getID() == name.getID() )
        {
            if(name.getIsGirl())
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorGirl));
            else
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBoy));
            holder.img.setImageResource(R.drawable.trophy_48);
        }

        //MOZE TO JEST NIEPOTRZEBNE _________________________________
       /* holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(name);
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }


    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        //ZMIEN-----------------------------------------------------------------------------------------
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }
}
