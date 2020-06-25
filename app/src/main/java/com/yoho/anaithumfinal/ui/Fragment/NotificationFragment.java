package com.yoho.anaithumfinal.ui.Fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yoho.anaithumfinal.Adapter.NotificationAdapter;
import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Api.NotificationDatum;
import com.yoho.anaithumfinal.Api.NotificationModel;
import com.yoho.anaithumfinal.R;
import com.yoho.anaithumfinal.Util.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    private RecyclerView rNotification;
    private NotificationAdapter nAdapter;
    private TextView noNotification;
    private List<NotificationDatum> nData;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        progressDialog.startProgress();
        rNotification=view.findViewById(R.id.rNotification);
        noNotification=view.findViewById(R.id.tv_No_Notification);
        nData=new ArrayList<>();
        InitNotification();
        return view;
    }

    private void InitNotification() {
        ApiInterface service= ApiClient.getClient().create(ApiInterface.class);
        Call<NotificationModel> call=service.getNotifications();
        call.enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if(response.body()!=null){
                    progressDialog.DismissDialog();
                    if(response.body().getData()!=null){
                        noNotification.setVisibility(View.GONE);
                        nData=response.body().getData();
                        setAdapter(nData);


                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                progressDialog.DismissDialog();
                noNotification.setVisibility(View.VISIBLE);

            }
        });
    }

    private void setAdapter(List<NotificationDatum> nData) {
        rNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        rNotification.setHasFixedSize(true);
        nAdapter=new NotificationAdapter(getActivity(),nData);
        rNotification.setAdapter(nAdapter);
    }
}