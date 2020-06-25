package com.yoho.anaithumfinal.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yoho.anaithumfinal.Adapter.SearchAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.Addtocartcount;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.LoginActivity;
import com.yoho.anaithumfinal.Model.CartResponse;
import com.yoho.anaithumfinal.Model.SearchDatum;
import com.yoho.anaithumfinal.Model.SearchModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;
import com.yoho.anaithumfinal.Util.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements RecyclerClickListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView rSearch;
    private SearchAdapter sAdapter;
    private List<SearchDatum>sData;
    private String value,Id;
    private Addtocartcount count;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
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
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics=FirebaseAnalytics.getInstance(getActivity());
        sharedPreference=new SharedPreference();
        progressDialog=new ProgressDialog(getActivity());
        // Obtain the FirebaseAnalytics instance.
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search, container, false);
        mFirebaseAnalytics.setCurrentScreen(getActivity(),"Search",null);
        progressDialog.startProgress();
        Id=sharedPreference.getString(getActivity(),"Id");
        rSearch=view.findViewById(R.id.rSearch);
        EditText edtSearch = view.findViewById(R.id.searchView);
        sData=new ArrayList<>();

        /*if (edtSearch!=null&&!edtSearch.getText().toString().equals("")&&!edtSearch.getQuery().toString().equals("null")) {
            value = edtSearch.getQuery().toString().trim();
            System.out.println("search value  "+value);
            InitSearch(value);

        }else {
            Toast.makeText(getActivity(), "please enter your key word", Toast.LENGTH_SHORT).show();
            System.out.println("search value 2 part  "+value);
            System.out.println("search value 2   "+edtSearch.get.toString());
            InitSearch(value);
        }*/
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s!=null){
                    String watcher=s.toString();
                    if (watcher!=null&&watcher.length()>0){
                        Bundle search=new Bundle();
                        search.putString(FirebaseAnalytics.Param.SEARCH_TERM,watcher);
                        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH,search);
                        InitSearch(watcher);
                    }else {
                        InitSearch("");
                    }
                   // InitSearch("");
                }else {
                    InitSearch("");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InitSearch("");


        return view;
    }

    private void InitSearch(String value) {
        try {
            ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
            Call<SearchModel>call=service.search(value,Id);
            call.enqueue(new Callback<SearchModel>() {
                @Override
                public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                    progressDialog.DismissDialog();
                    if(response.body()!=null) {
                        SearchModel model=response.body();
                        if(model.getData()!=null) {
                            sData=model.getData();
                            setAdapter(sData);
                        }
                        else{
                            System.out.println("data response    "+model.getCode());
                        }
                    }
                }

                @Override
                public void onFailure(Call<SearchModel> call, Throwable t) {
                    progressDialog.DismissDialog();
                    Toast.makeText(getActivity(),"Your search product is not available",Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(List<SearchDatum> sData) {
        sAdapter = new SearchAdapter(getActivity(),sData);
        rSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        rSearch.setAdapter(sAdapter);
        sAdapter.notifyDataSetChanged();
        sAdapter.setItemClickListener(this);
    }


    @Override
    public void OnItemClick(String v, int position) {
        if(v.equals("add")) {
            progressDialog.startProgress();
            String productId=sData.get(position).getPId();
            String btnStatus="1";

            if(Id.equals("")){
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
            else {
                InitAddtoCart(btnStatus,Id,productId);
            }
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            count=(Addtocartcount) context;
        }catch (Exception e){
            System.out.println("exception   "+e);
        }

    }

    private void InitAddtoCart(String btnStatus, String id, String productId) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<CartResponse>call=service.Cart(btnStatus,id,productId);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    Toast.makeText(getContext(),"Item is added to the cart",Toast.LENGTH_SHORT).show();
                    count.addcount();

                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.DismissDialog();

            }
        });
    }
}
