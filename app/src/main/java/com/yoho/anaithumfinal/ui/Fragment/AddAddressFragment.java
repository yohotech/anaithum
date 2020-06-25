package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.AddAreaModel;
import com.yoho.anaithumfinal.Model.AddCityModel;
import com.yoho.anaithumfinal.Model.AddPincodeModel;
import com.yoho.anaithumfinal.Model.AddStateModel;
import com.yoho.anaithumfinal.Model.AddressResponse;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAddressFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner pincode,city,state,area;
    private ArrayList<String> pinData,cityData,stateData,areaData;
    private EditText addressLine,Landmark,Alternate,name;
    private Button mAdd,mClear;
    private String mAddressLine,mLandmark,mAlternate,mPin,mCity,mState,mArea,id,mName;
    private SharedPreference sp;
    private ProgressDialog progressDialog;
    private static final String TAG = "ShippingAddressFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public AddAddressFragment() {
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
    public static AddAddressFragment newInstance(String param1, String param2) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp=new SharedPreference();
        progressDialog=new ProgressDialog(getActivity());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_add_address, container, false);
        progressDialog.startProgress();
        findids(view);
        id=sp.getString(getActivity(),"Id");

        if(addressLine.length()==0){
            addressLine.requestFocus();
            addressLine.setError("Field cannot be empty");

        }else if(Landmark.length()==0){
            Landmark.requestFocus();
            Landmark.setError("Field Cannot be empty");
        }else if(name.length()==0){
            name.requestFocus();
            name.setError("Field Cannot be empty");
        }
        InitPincode();
        InitCity();
        InitState();
        InitArea();
        mAdd.setOnClickListener(v -> {
            System.out.println("address    "+addressLine.getText().toString().trim());
            System.out.println("Landmark    "+Landmark.getText().toString().trim());
            System.out.println("mArea    "+mArea);
            System.out.println("mCity    "+mCity);
            System.out.println("mState    "+mState);
            System.out.println("mPin    "+mPin);

            mName=name.getText().toString().trim();
            mAddressLine=addressLine.getText().toString().trim();
            mLandmark=Landmark.getText().toString().trim();
            mAlternate=Alternate.getText().toString().trim();
            mPin=pincode.getSelectedItem().toString();
            mArea=area.getSelectedItem().toString();
            mCity=city.getSelectedItem().toString();

            mState=state.getSelectedItem().toString();
            if(mAddressLine.length()==0&&mLandmark.length()==0&&mName.length()==0){
                Toast.makeText(getActivity(),"Mandatory fields are empty",Toast.LENGTH_SHORT).show();
            }
            else {
                AddAddress(mName,mArea, mCity, mState, mPin,mAlternate);
            }
        });
        
        return view;
    }

    private void findids(View view) {
        pincode=view.findViewById(R.id.addPincode);
        city=view.findViewById(R.id.addCity);
        state=view.findViewById(R.id.addState);
        area=view.findViewById(R.id.addArea);
        addressLine=view.findViewById(R.id.edtAddress);
        Landmark=view.findViewById(R.id.edtLandmark);
        Alternate=view.findViewById(R.id.edtAlternate);
        mAdd=view.findViewById(R.id.btnAdd);
        mClear=view.findViewById(R.id.btnClear);
        name=view.findViewById(R.id.edtName);
    }

    private void AddAddress( String mName,String mArea, String mCity, String mState, String mPin,String mAlternate) {
        AddAddressFragment addAddressFragment=new AddAddressFragment();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<AddressResponse>call=service.addAddress(mName,mAddressLine,mArea,mCity,mState,mPin,mLandmark,mAlternate,id);
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                progressDialog.DismissDialog();
                if(response.isSuccessful()){
                    Log.i("onSuccess ", response.body().toString());
                    Toast.makeText(getActivity(),"Your address has been updated successfully",Toast.LENGTH_SHORT).show();
                    sp.putString(getActivity(),"Name",mName);
                    Bundle bundle=new Bundle();
                    bundle.putString("check_total",sp.getString(getActivity(),"Checktotal"));
                    Navigation.findNavController(getView()).navigate(R.id.nav_checkout);
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });


    }


    private void InitArea() {
        areaData=new ArrayList<>();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<AddAreaModel>call=service.AddArea();
        call.enqueue(new Callback<AddAreaModel>() {
            @Override
            public void onResponse(Call<AddAreaModel> call, Response<AddAreaModel> response) {
                progressDialog.DismissDialog();
                Log.i("onSuccess ", response.body().toString());

                if (response.body().getData() != null) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        areaData.add(response.body().getData().get(i).getD_area_name());
                    }

                    System.out.println("value   "+cityData.size());

                    ArrayAdapter areaAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,areaData);
                    areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    area.setAdapter(areaAdapter);
                }

            }

            @Override
            public void onFailure(Call<AddAreaModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });


    }

    private void InitState() {
        stateData=new ArrayList<>();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<AddStateModel>call=service.AddState();
        call.enqueue(new Callback<AddStateModel>() {
            @Override
            public void onResponse(Call<AddStateModel> call, Response<AddStateModel> response) {
                progressDialog.DismissDialog();
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess ", response.body().toString());

                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                stateData.add(response.body().getData().get(i).getD_state());
                            }

                            System.out.println("value   "+cityData.size());

                            ArrayAdapter stateAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,stateData);
                            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            state.setAdapter(stateAdapter);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<AddStateModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });

    }

    private void InitCity() {
        cityData=new ArrayList<>();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<AddCityModel>call=service.AddCity();
        call.enqueue(new Callback<AddCityModel>() {
            @Override
            public void onResponse(Call<AddCityModel> call, Response<AddCityModel> response) {
                progressDialog.DismissDialog();
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess ", response.body().toString());

                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                cityData.add(response.body().getData().get(i).getD_city());
                            }

                            System.out.println("value   "+cityData.size());

                            ArrayAdapter cityAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,cityData);
                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            city.setAdapter(cityAdapter);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<AddCityModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }

    private void InitPincode() {
        pinData=new ArrayList<>();
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<AddPincodeModel>call=service.AddPincode();
        call.enqueue(new Callback<AddPincodeModel>() {
            @Override
            public void onResponse(Call<AddPincodeModel> call, Response<AddPincodeModel> response) {
                progressDialog.DismissDialog();
                Log.i("onSuccess ", response.body().toString());
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess ", response.body().toString());

                        if (response.body().getData() != null) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                pinData.add(response.body().getData().get(i).getPin_code());
                            }

                            System.out.println("value   "+pinData.size());

                            ArrayAdapter pincodeAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,pinData);
                            pincodeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pincode.setAdapter(pincodeAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AddPincodeModel> call, Throwable t) {
                progressDialog.DismissDialog();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPin=pincode.getItemAtPosition(position).toString().trim();
        mArea=area.getItemAtPosition(position).toString().trim();
        mCity=city.getItemAtPosition(position).toString().trim();
        mState=state.getItemAtPosition(position).toString().trim();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
