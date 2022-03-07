package com.allinone.smartocity.Business.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.allinone.smartocity.Business.adapter.BusinessCategoryAdapter;
import com.allinone.smartocity.Business.adapter.OrderPendingAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.order.MainBusinessOrderHomeResponse;
import com.allinone.smartocity.model.order.MyOrderDetailsResponseModel;
import com.allinone.smartocity.retrofit.*;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.google.gson.Gson;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;
import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.BUSINESS_MYORDER_HOME;
import static com.allinone.smartocity.retrofit.Constant.SHARED_HOME_API_DETAILS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class CompleteOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, RestCalllback, OnitemClick, AdapterView.OnItemSelectedListener {

    private String TAG = "BusinessListActivity";
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private OrderPendingAdapter businessListAdapter;
    private MainLoginResponse mainLoginResponse = null;
    private ArrayList<BusinessListResponse> business_list;
    private ArrayList<MyOrderDetailsResponseModel> order_list;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialiJitnamde1@
    // Jitnzation parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private View view;
    private Spinner spinner_business_category;
    private BusinessListResponse businessListResponse;
    private MainResponseBusinessHomeModel mainResponseBusinessHomeModel;

    public CompleteOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static CompleteOrderFragment newInstance(String param1, String param2) {
        CompleteOrderFragment fragment = new CompleteOrderFragment();
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
        view = inflater.inflate(R.layout.fragment_complete_order, container, false);
        return view;
    }


    @Override
    public void itemClick(int pos, String type) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();
    }

    private void setData() {
        business_list = new ArrayList<>();
        if (mainResponseBusinessHomeModel.getBusinessDetailsList().size() > 0)
            business_list.addAll(mainResponseBusinessHomeModel.getBusinessDetailsList());


        BusinessCategoryAdapter businessAdapter = new BusinessCategoryAdapter(getActivity(), 0,
                business_list);
        spinner_business_category.setAdapter(businessAdapter);


    }

    private void setOnClickListener() {
        spinner_business_category.setOnItemSelectedListener(this);
    }

    private void initializeWidgets() {
        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        recyclerView = view.findViewById(R.id.recyclerView);
        spinner_business_category = view.findViewById(R.id.spinner_business_category);


    }

    private void initializeData() {
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);

        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);



        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()));
        recyclerView.setLayoutManager(layoutManager);

        order_list = new ArrayList<>();

        businessListAdapter = new OrderPendingAdapter(getContext(), order_list, this);
        recyclerView.setAdapter(businessListAdapter);


    }


    private void doApiCall() {
        if (InternetConnection.checkConnection(Objects.requireNonNull(getActivity()))) {
            getBusiness();
        } else {
            Toast.makeText(Objects.requireNonNull(getActivity()), getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
        }
    }


    private void getBusiness() {
        JSONObject jsonObject = new JSONObject();
        try {
        /*    jsonObject.put("firstName", first_name);
            jsonObject.put("lastName", last_name);
            jsonObject.put("password", password);
            jsonObject.put(MOBILENO, mobile_no);
            jsonObject.put("emailId", email);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());

        Call<String> business_list = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessOrderDetailsList(businessListResponse.getBusinessId());
        business_list.enqueue(new RestProcess<String>(Objects.requireNonNull(getActivity()), this, BUSINESS_MYORDER_HOME, false));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_business_category:
                if (business_list.size() > 0) {
                    businessListResponse = business_list.get(position);
                    doApiCall();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case BUSINESS_MYORDER_HOME:
                break;
        }
    }

    @Override
    public void onResponse(Call call, Response backendResponse, int serviceMode) {
        String ApiResponse = "";
        if (backendResponse.body() != null) {
            ApiResponse = backendResponse.body().toString();
        }
        Log.d(TAG, "onResponse: " + ApiResponse.toString());
        switch (serviceMode) {
            case BUSINESS_MYORDER_HOME:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainBusinessOrderHomeResponse response = new Gson().fromJson(jsonObject.toString(), MainBusinessOrderHomeResponse.class);
                    if (response.getStatus().equals("1")) {
                        Toast.makeText(Objects.requireNonNull(getActivity()), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {
                                //business_list.clear();
                                order_list.addAll(response.getResult());
                                businessListAdapter.updateBusinessList(order_list);
                            }
                        }


                    } else {
                        Toast.makeText(Objects.requireNonNull(getActivity()), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onRefresh() {
        doApiCall();
        swipeRefresh.setRefreshing(false);
    }
}