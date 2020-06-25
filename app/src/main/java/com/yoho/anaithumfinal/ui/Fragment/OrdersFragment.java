package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yoho.anaithumfinal.Adapter.ActiveOrderAdapter;
import com.yoho.anaithumfinal.Adapter.PastOrderAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Interface.RecyclerClickListener;
import com.yoho.anaithumfinal.Model.ActiveOrderDatum;
import com.yoho.anaithumfinal.Model.ActiveOrderModel;
import com.yoho.anaithumfinal.Model.PastOrderDatum;
import com.yoho.anaithumfinal.Model.PastOrderModel;
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
public class OrdersFragment extends Fragment implements RecyclerClickListener {
    private RecyclerView rCurrentOrder,rPastOrder;
    private ActiveOrderAdapter activeOrderAdapter;
    private PastOrderAdapter pastOrderAdapter;
    private TextView actTitle,pastTitle;
    private String Id;
    private List<ActiveOrderDatum> actData;
    private List<PastOrderDatum> pastOrderData;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
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
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
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
        View view= inflater.inflate(R.layout.fragment_orders, container, false);
        progressDialog.startProgress();
        rCurrentOrder=view.findViewById(R.id.rCurrentOrders);
        rPastOrder=view.findViewById(R.id.rPastOrders);
        actTitle=view.findViewById(R.id.active_order_title);
        pastTitle=view.findViewById(R.id.past_order_title);
        Id=sharedPreference.getString(getActivity(),"Id");
        actData=new ArrayList<>();
        InitCurrentOrder(Id);
        InitPastOrder(Id);

        return view;
    }

    private void InitPastOrder(String Id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<PastOrderModel>call=service.getPastOrder(Id);
        call.enqueue(new Callback<PastOrderModel>() {
            @Override
            public void onResponse(Call<PastOrderModel> call, Response<PastOrderModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    PastOrderModel pModel=response.body();
                    if(pModel.getData()!=null){
                        pastOrderData=new ArrayList<>();
                        pastOrderData=pModel.getData();
                        setpastAdapter(pastOrderData);
                    }
                    else{
                        System.out.println("value   "+response.errorBody().source());
                        pastTitle.setVisibility(View.GONE);
                        rPastOrder.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<PastOrderModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("value   "+t.getMessage());
            }
        });
    }

    private void setpastAdapter(List<PastOrderDatum> pastOrderData) {
        rPastOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rPastOrder.setHasFixedSize(true);
        pastOrderAdapter=new PastOrderAdapter(getActivity(),pastOrderData);
        rPastOrder.setAdapter(pastOrderAdapter);
        pastOrderAdapter.setOnclickListener(this);
    }

    private void InitCurrentOrder(String Id) {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<ActiveOrderModel>call=service.getActiveOrder(Id);
        call.enqueue(new Callback<ActiveOrderModel>() {
            @Override
            public void onResponse(Call<ActiveOrderModel> call, Response<ActiveOrderModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    ActiveOrderModel actModel=response.body();
                    if(actModel.getData()!=null){
                        actData=response.body().getData();
                        SetAdapter(actData);

                    }
                    else{
                        actTitle.setVisibility(View.GONE);
                        rCurrentOrder.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ActiveOrderModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("current orders: "+t.getMessage());

            }
        });

    }

    private void SetAdapter(List<ActiveOrderDatum> actData) {
        rCurrentOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rCurrentOrder.setHasFixedSize(true);
        activeOrderAdapter=new ActiveOrderAdapter(getActivity(),actData);
        rCurrentOrder.setAdapter(activeOrderAdapter);
        activeOrderAdapter.setOnClickListener(this);
    }

    @Override
    public void OnItemClick(String v, int position) {
        if(v=="past"){
            String mId=pastOrderData.get(position).getMId();
            Bundle bundle=new Bundle();
            bundle.putString("mId",mId);
            Navigation.findNavController(getView()).navigate(R.id.nav_orderDetails,bundle);
        }else if(v=="track"){
            String mId=actData.get(position).getMId();
            String orderId=actData.get(position).getMOrderId();
            Bundle bundle=new Bundle();
            bundle.putString("mId",mId);
            bundle.putString("orderId",orderId);
            Navigation.findNavController(getView()).navigate(R.id.nav_track,bundle);

        }
        else if(v=="active"){
            String mId=actData.get(position).getMId();
            Bundle bundle=new Bundle();
            bundle.putString("mId",mId);
            Navigation.findNavController(getView()).navigate(R.id.nav_orderDetails,bundle);

        }
    }
}
