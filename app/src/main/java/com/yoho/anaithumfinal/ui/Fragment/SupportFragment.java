package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.OrderSpinnerModel;
import com.yoho.anaithumfinal.Model.SupportDatum;
import com.yoho.anaithumfinal.Model.SupportModel;
import com.yoho.anaithumfinal.Model.SupportModeldata;
import com.yoho.anaithumfinal.Model.SupportResponse;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SupportFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private Button submit;
    private Spinner sId,sReason;
    private ArrayList<String> Reason,oId;
    private EditText sDescription;
    private String Id,s_reason,s_Id,s_description,name;
    private SharedPreference sharedPreference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SupportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_support, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Support Desk",null);
        sId=view.findViewById(R.id.order_id);
        sDescription=view.findViewById(R.id.description);
        Id=sharedPreference.getString(getActivity(),"Id");
        name=sharedPreference.getString(getActivity(),"Name");
        sReason=view.findViewById(R.id.reason);
        submit=view.findViewById(R.id.submit_form);
//        if(sDescription.getText().toString()!=null){
//            Toast.makeText(getActivity(),"Fill all the fields",Toast.LENGTH_SHORT).show();
//        } else {


            s_description = sDescription.getText().toString();
        //}



        fetchReason();
        fetchId(Id);
        submit.setOnClickListener(v -> {
            System.out.println("des    "+s_description);
            System.out.println("des 1   "+sDescription.getText().toString());
            if(sDescription.getText().toString()!=null&&s_Id!=null&&s_reason!=null){
                post(s_Id,name,Id,s_reason,sDescription.getText().toString());
            }
            else{
                Toast.makeText(getActivity(),"Fill all the fields",Toast.LENGTH_SHORT).show();
            }





        });









        return view;
    }

    private void post(String s_id, String name, String id, String s_reason, String s_description) {
        System.out.println("s_id   "+s_id);
        System.out.println("name   "+name);
        System.out.println("id   "+id);
        System.out.println("s_reason   "+s_reason);
        System.out.println("s_description   "+s_description);
        ApiInterface service=ApiClient.getClient().create(ApiInterface.class);
        Call<SupportResponse>call=service.postSupport(s_id,name,id,s_reason,s_description);
        call.enqueue(new Callback<SupportResponse>() {
            @Override
            public void onResponse(Call<SupportResponse> call, Response<SupportResponse> response) {
                System.out.println("value   "+response.body());
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        System.out.println("value 1  "+response.body().getData());
                        if(response.body().getData()!=null){
                            Toast.makeText(getActivity(),"Your Query Has been Submitted Successfully",Toast.LENGTH_SHORT).show();
                            NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_support,true).build();
                            Navigation.findNavController(getView()).navigate(R.id.nav_home,null,options);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SupportResponse> call, Throwable t) {

            }
        });
    }

    private void fetchId(String id) {
        oId=new ArrayList<>();
        ApiInterface service=ApiClient.getClient().create(ApiInterface.class);
        Call<OrderSpinnerModel>call=service.getOrderId(id);
        call.enqueue(new Callback<OrderSpinnerModel>() {
            @Override
            public void onResponse(Call<OrderSpinnerModel> call, Response<OrderSpinnerModel> response) {
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess ", response.body().toString());


                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                oId.add(response.body().getData().get(i).getM_order_id());
                            }
                            System.out.println("value   "+Reason.size());

                            ArrayAdapter oAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,oId);
                            oAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sId.setAdapter(oAdapter);
                            sId.setOnItemSelectedListener(SupportFragment.this);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderSpinnerModel> call, Throwable t) {

            }
        });

    }

    private void fetchReason() {
        Reason= new ArrayList<>();
        ApiInterface service=ApiClient.getClient().create(ApiInterface.class);
        Call<SupportModel>call=service.getReason();
        call.enqueue(new Callback<SupportModel>() {
            @Override
            public void onResponse(Call<SupportModel> call, Response<SupportModel> response) {
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        Log.i("onSuccess ", response.body().toString());


                       if (response.body().getData()!=null){
                           for (int i=0;i<response.body().getData().size();i++){
                               Reason.add(response.body().getData().get(i).getSs_reason());
                           }
                          // Reason=  response.body().getData();
                           System.out.println("value   "+Reason.size());

                           ArrayAdapter pAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,Reason);
                           pAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                           sReason.setAdapter(pAdapter);
                           sReason.setOnItemSelectedListener(SupportFragment.this);
                       }


                    }else {
                        Log.i("Error Success   ", String.valueOf(response.errorBody().source()));
                    }
                }else {
                    Log.i("Error Success   1   ", String.valueOf(response.errorBody().source()));
                }
            }

            @Override
            public void onFailure(Call<SupportModel> call, Throwable t) {
                System.out.println("Error msg   "+t.getMessage());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
      //  if(s_reason==null&&s_Id==null) {
        try {
            s_reason = sReason.getItemAtPosition(position).toString().trim();
            s_Id = sId.getItemAtPosition(position).toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*}
        else{
            Toast.makeText(getActivity(),"Please select the values",Toast.LENGTH_SHORT).show();
        }*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
