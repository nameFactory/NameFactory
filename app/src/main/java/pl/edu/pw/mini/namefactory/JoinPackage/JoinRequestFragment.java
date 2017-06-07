package pl.edu.pw.mini.namefactory.JoinPackage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.edu.pw.mini.namefactory.Additional.DividerItem;
import pl.edu.pw.mini.namefactory.Additional.SwipeHelperCallback;
import pl.edu.pw.mini.namefactory.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the /*{@link OnRequestListFragmentInteractionListener}*/
 /* interface.
 */
public class JoinRequestFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ID = "rankingID";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnRequestListFragmentInteractionListener mListener;

    private List<JoinRequest> requestList  = new ArrayList<>();
    private RecyclerView recyclerView;
    private JoinRequestAdapter jAdapter;
    private String requestChosen;
    private int requestID = -1;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JoinRequestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JoinRequestFragment newInstance(int id) {
        JoinRequestFragment fragment = new JoinRequestFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_requests_list, container, false);

        mListener.hideFloatingButton();
        mListener.setTitleName("Requests inbox");
        recyclerView = (RecyclerView) view;
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            //requestList = dbh.getNameList(rankingID);
            prepareList();
            jAdapter = new JoinRequestAdapter(requestList,context, mListener);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItem(context, LinearLayoutManager.VERTICAL));
            ItemTouchHelper.Callback callback =
                    new SwipeHelperCallback(jAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerView);
            recyclerView.setAdapter(jAdapter);

            /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener( getActivity().getApplicationContext(), recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {

                    // Creating Bundle object
                    Bundle bundel = new Bundle();

                    // Storing data into bundle
                    Name element = namesList.get(position);
                    bundel.putInt("name", element.getID());

                    //przejdz do aktywnosci namecard
                    Intent in = new Intent(getActivity().getApplicationContext(), NameCard.class);
                    in.putExtras(bundel);
                    startActivity(in);

                }

                @Override
                public void onLongClick(View view, int position) {

                }

            }));*/

            //Log.i("FRAG", "ustawiony adapter");
            //prepareNamesList();
            //Log.i("FRAG", "przygotowana lista");
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRequestListFragmentInteractionListener) {
            mListener = (OnRequestListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnRequestListFragmentInteractionListener {
        // TODO: Update argument type and name
        void hideFloatingButton();
        void setTitleName(String name);
    }

    private void prepareList() {

        JoinRequest reg1 = new JoinRequest(1,"ranking1","user1");
        JoinRequest req2 = new JoinRequest(2,"raning2", "user2");
        requestList.add(reg1);
        requestList.add(req2);
    }
}
