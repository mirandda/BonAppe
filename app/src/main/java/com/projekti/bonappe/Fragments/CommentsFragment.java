package com.projekti.bonappe.Fragments;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projekti.bonappe.Adapters.CommentsAdapter;
import com.projekti.bonappe.Adapters.ReservationAdapter;
import com.projekti.bonappe.ListItems.Comment;
import com.projekti.bonappe.ListItems.Reservation;
import com.projekti.bonappe.R;
import com.projekti.bonappe.Fragments.dummy.DummyContent;
import com.projekti.bonappe.Fragments.dummy.DummyContent.DummyItem;
import com.projekti.bonappe.UserInformation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CommentsFragment extends Fragment {
DatabaseReference databaseRef;
DatabaseReference databaseRef2;
FirebaseAuth mAuth;
ListView lista_komentet;
Button button2;
EditText editText4;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CommentsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CommentsFragment newInstance(int columnCount) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
           // recyclerView.setAdapter(new CommentsAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button2=getView().findViewById(R.id.button2);
        editText4=getView().findViewById(R.id.editText4);
        lista_komentet=getView().findViewById(R.id.lista_komentet);

        mAuth = FirebaseAuth.getInstance();


button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {String stringu=editText4.getText().toString();
        if(!stringu.equals("")) {
            databaseRef2 =FirebaseDatabase.getInstance().getReference().child("Komentet").child(System.currentTimeMillis()+UserInformation.telefoni);

            databaseRef2.child("emri").setValue(UserInformation.emri);
            databaseRef2.child("komenti").setValue(editText4.getText().toString());
            databaseRef2.child("photo").setValue(UserInformation.photoUrl);

        }
        editText4.setText("");

    }
});


        databaseRef = FirebaseDatabase.getInstance().getReference().child("Komentet");
        final ArrayList <Comment> arrKomentet=new ArrayList();
        final CommentsAdapter commentsAdapter=new CommentsAdapter(arrKomentet);
lista_komentet.setAdapter(commentsAdapter);
        databaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {arrKomentet.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.d("Blu9","b");
try{
                    arrKomentet.add(

                            new Comment(

    postSnapshot.child("emri").getValue().toString(),
            postSnapshot.child("komenti").getValue().toString(),
            postSnapshot.child("photo").getValue().toString()

                                   )
                    );}catch(NullPointerException npe){Log.d("blu11",String.valueOf(postSnapshot.child("komenti")));}

                }
                Collections.reverse(arrKomentet);
                commentsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("blu0", "loadPost:onCancelled", databaseError.toException());

            }
        });






/*


        final ArrayList<Reservation> listaRezervimet=new ArrayList<>();


        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Reservation kk=null;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(postSnapshot.child("userId").getValue().toString().equals(UserInformation.userID))//--
                        listaRezervimet.add(
                                kk=  new Reservation(
                                        (String) postSnapshot.child("ora").getValue(),
                                        (String) postSnapshot.child("data").getValue(),
                                        (String)   postSnapshot.child("nrPersonave").getValue()));
                }

                ReservationAdapter reservationAdapter=new ReservationAdapter(listaRezervimet);
                lv_reservimet.setAdapter(reservationAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("blu7", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });






     */

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
          //  throw new RuntimeException(context.toString()
                  //  + " must implement OnListFragmentInteractionListener"
            //  );
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
