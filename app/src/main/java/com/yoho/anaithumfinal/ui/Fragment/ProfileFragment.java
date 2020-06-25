package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RefreshMain;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.MainActivity;
import com.yoho.anaithumfinal.Model.ViewProfileModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private FirebaseAnalytics mFirebaseAnalytics;
    private Button logout,btnEdit;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    private String Id,Name,Email;
    private TextView pName,pEmail,pMobile,pAddress,pArea,pCity,pState,pPincode,pLandmark;
    private Addtocartcount count;
    private RefreshMain Main;
    public String SName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        progressDialog=new ProgressDialog(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Profile",null);
        progressDialog.startProgress();
        Id=sharedPreference.getString(getActivity(),"Id");
        pName=view.findViewById(R.id.txtProfile_name);
        pMobile=view.findViewById(R.id.txtProfile_mobile);
        pEmail=view.findViewById(R.id.txtProfile_mail);
        logout=view.findViewById(R.id.logout);
        btnEdit=view.findViewById(R.id.btnEdit);
        pAddress=view.findViewById(R.id.profile_address);
        pArea=view.findViewById(R.id.profile_area);
        pCity=view.findViewById(R.id.profile_city);
        pState=view.findViewById(R.id.profile_state);
        pPincode=view.findViewById(R.id.profile_pincode);
        pLandmark=view.findViewById(R.id.profile_landmark);

        /*try {
            if(sharedPreference.getString(getActivity(),"Name").equals("")){
                Intent i=new Intent(getActivity(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        InitProfile(Id);




        btnEdit.setOnClickListener(v -> {
            progressDialog.DismissDialog();
            /*Navigation.findNavController(view).navigate(R.id.nav_edit_profile);*/
            NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_add_address,true).build();
            Navigation.findNavController(btnEdit).navigate(R.id.nav_add_address,null,options);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            if(sharedPreference.getString(getActivity(),"Id")!=null){
                logout.setVisibility(View.VISIBLE);
                btnEdit.setVisibility(View.VISIBLE);
            }
            logout.setOnClickListener(v -> {
                progressDialog.startProgress();
                Logout();

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Logout() {
        try {
            if(sharedPreference.getString(getActivity(),"Id")!=null){
                sharedPreference.removeString(getActivity(),"Id");
                sharedPreference.removeString(getActivity(),"Name");
                sharedPreference.removeString(getActivity(),"Email");
                progressDialog.DismissDialog();
                Main.refresh();
                Toast.makeText(getActivity(),"Logout Successful",Toast.LENGTH_SHORT).show();

                NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_profile,true).build();
                Navigation.findNavController(getView()).navigate(R.id.nav_home,null,options);

                logout.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);


            }
            else {
                logout.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitProfile(String id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ViewProfileModel>call=service.getProfile(id);
        call.enqueue(new Callback<ViewProfileModel>() {
            @Override
            public void onResponse(Call<ViewProfileModel> call, Response<ViewProfileModel> response) {
                if(response.body()!=null){
                    progressDialog.DismissDialog();
                    ViewProfileModel vModel=response.body();
                    if(vModel.getData()!=null){
                        pName.setText(vModel.getData().get(0).getRName());
                        pMobile.setText(vModel.getData().get(0).getRMobile());
                        pEmail.setText(vModel.getData().get(0).getREmail());
                        SName=vModel.getData().get(0).getRName();
                        pAddress.setText(vModel.getData().get(0).getRAddress());
                        pArea.setText(vModel.getData().get(0).getRArea());
                        pCity.setText(vModel.getData().get(0).getRCity());
                        pState.setText(vModel.getData().get(0).getRState());
                        pPincode.setText(vModel.getData().get(0).getRPincode());
                        pLandmark.setText(vModel.getData().get(0).getRLandmark());
                        sharedPreference.putString(getActivity(),"Name",vModel.getData().get(0).getRName());
                    }
                }
            }

            @Override
            public void onFailure(Call<ViewProfileModel> call, Throwable t) {
                progressDialog.DismissDialog();

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Main=(RefreshMain) context;
        }catch (Exception e){
            System.out.println("exception   "+e);
        }
    }


}
