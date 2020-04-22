package com.projekti.bonappe.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.projekti.bonappe.MainActivity;
import com.projekti.bonappe.R;
import com.projekti.bonappe.RestaurantActivity;
import com.projekti.bonappe.UserInformation;
import com.projekti.bonappe.WeatherActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
  private TextView tv_meal1,tv_meal2,tv_meal3;
  public static TextView tv_UserPreferencesId1,tv_UserPreferencesId2,tv_UserPreferencesId3;
  private double temp;
    // TODO: Rename and change types of parameters private TextView tv_meal1,tv_meal2,tv_meal3;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_restaurant_menu, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        find_weather();
        userPreferences();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
              //      + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android .com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void find_weather()
    {
        final String temp_url="http://api.openweathermap.org/data/2.5/weather?q=mitrovice&appid=c28b84017ea13605303ebdde86c16f0e";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, temp_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    //String temp = String.valueOf(main_object.getDouble("temp"));
                    temp=main_object.getDouble("temp");
                    // double temp_double = Double.parseDouble(temp);
                    //textTemp.setText(String.valueOf(temp));

                    tv_meal1= (TextView)getView().findViewById(R.id.tvPreferencesId1);
                    tv_meal2= (TextView)getView().findViewById(R.id.tvPreferencesId2);
                    tv_meal3= (TextView)getView().findViewById(R.id.tvPreferencesId3);

                    if(temp>270.00) {
                        tv_meal1.setText("Coffee                2$");
                        tv_meal2.setText("Tea                   1.5$");
                        tv_meal3.setText("Hot Chocoloate        2.5$");
                    }
                    else if(temp<270.00){
                        tv_meal1.setText("Ice tea               1$");
                        tv_meal2.setText("Juice                 1.5$");
                        tv_meal3.setText("Cold water            0.5$");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                ,new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse (VolleyError error){

            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext().getApplicationContext());
        queue.add(jor);
    }

    public void userPreferences() {

        UserInformation.DatabaseRef=FirebaseDatabase.getInstance().getReference("users").child(UserInformation.userID);
        UserInformation.DatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tv_UserPreferencesId1=(TextView)getView().findViewById(R.id.tv_UserPreferencesId1);
                tv_UserPreferencesId2=(TextView)getView().findViewById(R.id.tv_UserPreferencesId2);
                tv_UserPreferencesId3=(TextView)getView().findViewById(R.id.tv_UserPreferencesId3);

                String userPreferencesId1=dataSnapshot.child("selPreference1").getValue().toString();
                String em=dataSnapshot.child("emri").getValue().toString();

                 if(userPreferencesId1.equals("Pizza"))
                 {
                     tv_UserPreferencesId1.setText(userPreferencesId1);
                     tv_UserPreferencesId2.setText("Margarita Pizza      4.00$");
                     tv_UserPreferencesId3.setText("Greek Pizza          4.00$");
                 }
                 else if(userPreferencesId1.equals("Soup"))
                 {
                     tv_UserPreferencesId1.setText("Chicken Soup         2.00$");
                     tv_UserPreferencesId2.setText("Vegetable Soup       3.00$");
                     tv_UserPreferencesId3.setText("Cow meat  Soup       2.70$");
                 }
                 else if(userPreferencesId1.equals("Pancakes"))
                 {
                     tv_UserPreferencesId1.setText("Hot Chocolate        2.00$");
                     tv_UserPreferencesId2.setText("Orange Cake          3.00$");
                     tv_UserPreferencesId3.setText("Apple Cake           2.70$");
                 }
                 else if(userPreferencesId1.equals("Chinese"))
                 {
                     tv_UserPreferencesId1.setText("Shrimp and Garlic        5.00$");
                     tv_UserPreferencesId2.setText("Dumplings.               3.00$");
                     tv_UserPreferencesId3.setText("Chow Mein                3.70$");
                 }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("blu3 --", "loadPost:onCancelled", databaseError.toException());
            }
        });
    }
}
