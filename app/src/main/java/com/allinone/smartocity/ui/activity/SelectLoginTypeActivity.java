package com.allinone.smartocity.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.allinone.smartocity.Business.adapter.SliderAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.SliderItem;
import com.allinone.smartocity.ui.activity.mobileno.MobileActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.allinone.smartocity.retrofit.Constant.*;

public class SelectLoginTypeActivity extends AppCompatActivity implements View.OnClickListener {




    private static int currentPage = 0;
    private static int NUM_PAGES = 3;
    LinearLayout indicatorlay;
    private Button btn_business;
    private Button btn_customer;
    private SliderView sliderView;
    private SliderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_type);


        intializeWidgets();
        setOnClickListener();
        initilizeData();
setData();

    }

    private void initilizeData() {
        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void setData() {
        sliderItems();
    }

    private void setOnClickListener() {
        btn_business.setOnClickListener(this);
        btn_customer.setOnClickListener(this);
    }

    private void intializeWidgets() {
        btn_business = findViewById(R.id.btn_business);
        btn_customer = findViewById(R.id.btn_customer);
        sliderView = findViewById(R.id.imageSlider);



    }

    public void sliderItems() {
        List<SliderItem> sliderItemList = new ArrayList<>();
        //dummy data
        for (int i = 0; i < 5; i++) {
            SliderItem sliderItem = new SliderItem();
            sliderItem.setDescription("Slider Item " + i);
            if (i % 2 == 0) {
                sliderItem.setImageUrl("http://tvfiles.alphacoders.com/100/hdclearart-10.png");
            } else {
                sliderItem.setImageUrl("https://images.pexels.com/photos/747964/pexels-photo-747964.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260");
            }
            sliderItemList.add(sliderItem);
        }
        adapter.renewItems(sliderItemList);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_business:
                Intent mainIntent = new Intent(SelectLoginTypeActivity.this, MobileActivity.class);
                mainIntent.putExtra(LOGINTYPE, BUSINESS);
                startActivity(mainIntent);
                finish();
                break;
            case R.id.btn_customer:
                Intent cutomerIntent = new Intent(SelectLoginTypeActivity.this, MobileActivity.class);
                cutomerIntent.putExtra(LOGINTYPE, CUSTOMER);
                startActivity(cutomerIntent);
                finish();

                break;

        }
    }
}
