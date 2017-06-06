package pl.edu.pw.mini.namefactory.JoinPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import pl.edu.pw.mini.namefactory.Additional.SwipeHelperAdapter;
import pl.edu.pw.mini.namefactory.R;

/**
 * Created by Asus on 07.06.2017.
 */

public class JoinRequestAdapter  extends RecyclerView.Adapter<JoinRequestAdapter.ViewHolder> implements SwipeHelperAdapter {
    private List<JoinRequest> requestList;
    private final Context context;
    //SharedPreferences.OnSharedPreferenceChangeListener listener;
    //private final RankingViewFragment.OnRankingsListFragmentInteractionListener mListener;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(requestList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(requestList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {

        JoinRequest element = requestList.get(position);
        remove(element);
        Toast.makeText(context, element.getUserName() +": "+ element.getRankingName() + " is deleted!", Toast.LENGTH_SHORT).show();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView uname;
        public TextView rname;
        public ImageButton imgBt;
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            uname = (TextView) view.findViewById(R.id.userNameJoin);
            rname = (TextView) view.findViewById(R.id.ranknigNameJoin);
            imgBt = (ImageButton) view.findViewById(R.id.acceptBt);
        }
    }

    public JoinRequestAdapter(final List<JoinRequest> requestList, Context context, JoinRequestFragment.OnRequestListFragmentInteractionListener _mlistener) {
        this.requestList = requestList;
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
    public JoinRequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.join_rankings_row, parent, false);

        return new JoinRequestAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JoinRequestAdapter.ViewHolder holder, int position) {

        final JoinRequest request = requestList.get(position);

        holder.uname.setText(request.getUserName()+" : ");
        holder.rname.setText(request.getRankingName());

        holder.imgBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                accept(v,request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


    // Remove a RecyclerView item containing a specified Data object
    public void remove(JoinRequest data) {
        //ZMIEN-----------------------------------------------------------------------------------------
        int position = requestList.indexOf(data);
        requestList.remove(position);
        notifyItemRemoved(position);
    }

    public void accept(View v, JoinRequest request)
    {
        Toast.makeText(context,"ACCEPTED", Toast.LENGTH_SHORT).show();
        remove(request);
    }

}
