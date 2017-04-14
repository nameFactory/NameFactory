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
 * Created by Asus on 15.04.2017.
 */

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.ViewHolder> implements SwipeHelperAdapter  {

    private List<Name> namesList;
    private final Context context;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

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

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public NamesAdapter(final List<Name> namesList, Context context) {
        this.namesList = namesList;
        this.context = context;
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
    public NamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.names_list_row, parent, false);

        return new NamesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NamesAdapter.ViewHolder holder, int position) {

        Name name = namesList.get(position);
        holder.name.setText(name.getName());
    }

    @Override
    public int getItemCount() {
        return namesList.size();
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(Name data) {
        namesList.add(data);
        notifyItemInserted(getItemCount());
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Name data) {
        int position = namesList.indexOf(data);
        namesList.remove(position);
        notifyItemRemoved(position);
    }
}
