package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.Model.ProfileModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    private EditText Name,Email;
    private Button Update;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+",name,email;
    private SharedPreference sharedPreference;
    private String Id;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditProfileFragment() {
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
    public static EditProfileFragment newInstance(String param1, String param2) {
        EditProfileFragment fragment = new EditProfileFragment();
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
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);
        /*Id=sharedPreference.getString(getActivity(),"Id");

        Name=view.findViewById(R.id.txtProfile_name);
        Email=view.findViewById(R.id.txtProfile_mail);
        Update=view.findViewById(R.id.update);
        Update.setOnClickListener(v -> {
            name=Name.getText().toString().trim();
            email=Email.getText().toString().trim();
            if(Name.length()==0){
                Toast.makeText(getActivity(),"Enter your Name",Toast.LENGTH_SHORT).show();
            }
            else if(Email.length()==0){
                Toast.makeText(getActivity(),"Enter your Email",Toast.LENGTH_SHORT).show();
            }else {
                if(email.matches(emailPattern)){
                    progressDialog.startProgress();
                    PostDetails(Id,name,email);
                    Toast.makeText(getActivity(),"Profile has been updated successfully ",Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getActivity(),"Enter all details ",Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        return view;
    }

    private void PostDetails(String id, String name, String email) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileModel> call=service.editProfile(id,name,email);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    ProfileModel model=response.body();
                    if(model.getData()!=null){
                        System.out.println("onSuccess"+name+""+email);
                        NavOptions options=new NavOptions.Builder().setPopUpTo(R.id.nav_edit_profile,true).build();
                        NavHostFragment.findNavController(EditProfileFragment.this).navigate(R.id.action_nav_edit_profile_to_nav_profile,null,options);



                    }

                }
            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                progressDialog.DismissDialog();
                Toast.makeText(getActivity(),"Failure",Toast.LENGTH_SHORT).show();

            }
        });
    }
}