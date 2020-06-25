package com.yoho.anaithumfinal.ui.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoho.anaithumfinal.Api.ApiClient;
import com.yoho.anaithumfinal.Api.ApiInterface;
import com.yoho.anaithumfinal.Model.TrackOrderModel;
import com.yoho.anaithumfinal.Model.TrackOrderdatum;
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
public class TrackOrderFragment extends Fragment {
    private TextView tvOrderPlaced,txtOrderPlaced,tvInProgress,txtInProgress,tvShipped,txtShipped,tvDelivery,txtDelivery,orderId;
    private ImageView imgPlaced,ImgProgress,ImgShipped,ImgDelivery;
    private View view1,view2,view3;
    private String mId,dStatus,order;
    private SharedPreference sharedPreference;
    private ProgressDialog progressDialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrackOrderFragment() {
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
    public static TrackOrderFragment newInstance(String param1, String param2) {
        TrackOrderFragment fragment = new TrackOrderFragment();
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
        View view= inflater.inflate(R.layout.fragment_track_order, container, false);
        progressDialog.startProgress();
        orderId=view.findViewById(R.id.order_id);
        tvOrderPlaced=view.findViewById(R.id.order_placed);
        txtOrderPlaced=view.findViewById(R.id.order_placed_text);
        tvInProgress=view.findViewById(R.id.order_progress);
        txtInProgress=view.findViewById(R.id.order_progress_text);
        tvShipped=view.findViewById(R.id.order_shipped);
        txtShipped=view.findViewById(R.id.order_shipped_text);
        tvDelivery=view.findViewById(R.id.order_delivered);
        txtDelivery=view.findViewById(R.id.order_delivery_text);
        imgPlaced=view.findViewById(R.id.placed);
        ImgProgress=view.findViewById(R.id.progress);
        ImgShipped=view.findViewById(R.id.shipped);
        ImgDelivery=view.findViewById(R.id.delivered);
        view1=view.findViewById(R.id.view1);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);
        mId=getArguments().getString("mId");
        System.out.println("mId: "+mId);
        orderId.setText(getArguments().getString("orderId"));
        InitTrack(order);




        return view;
    }

    private void InitTrack(String mId) {
        ApiInterface service=ApiClient.getClient().create(ApiInterface.class);
        Call<TrackOrderModel>call=service.trackOrder(mId);
        call.enqueue(new Callback<TrackOrderModel>() {
            @Override
            public void onResponse(Call<TrackOrderModel> call, Response<TrackOrderModel> response) {
                progressDialog.DismissDialog();
                if(response.body()!=null){
                    if (response.body().getData()!=null){
                        dStatus=response.body().getData().get(0).getM_delivery_status();
                        System.out.println("status: "+dStatus);
                        switch (dStatus){
                            case "1":
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.radiobox_marked);
                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.GONE);
                                txtShipped.setVisibility(View.GONE);
                                txtDelivery.setVisibility(View.GONE);
                                break;
                            case "2":
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.radiobox_marked);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.GONE);
                                txtDelivery.setVisibility(View.GONE);
                                break;
                            case "3":
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.check_circle);
                                ImgDelivery.setImageResource(R.drawable.radiobox_marked);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.VISIBLE);
                                txtDelivery.setVisibility(View.GONE);
                                break;
                            case "4":
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.check_circle);
                                ImgDelivery.setImageResource(R.drawable.check_circle);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvDelivery.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtDelivery.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.VISIBLE);
                                txtDelivery.setVisibility(View.VISIBLE);
                                break;
                            default:
                                txtInProgress.setVisibility(View.GONE);
                                txtShipped.setVisibility(View.GONE);
                                txtDelivery.setVisibility(View.GONE);
                                break;



                        }

                           /* if (dStatus.equals("1")) {
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.radiobox_marked);
                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.GONE);
                                txtShipped.setVisibility(View.GONE);
                                txtDelivery.setVisibility(View.GONE);
                            }
                            else if(dStatus.equals("2")){
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.radiobox_marked);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.GONE);
                                txtDelivery.setVisibility(View.GONE);
                            }
                            else if(dStatus.equals("3")){
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.check_circle);
                                ImgDelivery.setImageResource(R.drawable.radiobox_marked);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.VISIBLE);
                                txtDelivery.setVisibility(View.GONE);

                            }
                            else if(dStatus.equals("4")){
                                imgPlaced.setImageResource(R.drawable.check_circle);
                                ImgProgress.setImageResource(R.drawable.check_circle);
                                ImgShipped.setImageResource(R.drawable.check_circle);
                                ImgDelivery.setImageResource(R.drawable.check_circle);

                                tvOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtOrderPlaced.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtShipped.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                tvDelivery.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtDelivery.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                view1.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                view3.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                                txtInProgress.setVisibility(View.VISIBLE);
                                txtShipped.setVisibility(View.VISIBLE);
                                txtDelivery.setVisibility(View.VISIBLE);

                            }*/

                    }
                }
            }

            @Override
            public void onFailure(Call<TrackOrderModel> call, Throwable t) {
                progressDialog.DismissDialog();
                System.out.println("error track"+(t.getMessage()));

            }
        });
    }


}
