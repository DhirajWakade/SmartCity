package com.allinone.smartocity.Business.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.CustomFloatAttributes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.allinone.smartocity.BuildConfig;
import com.allinone.smartocity.Business.activity.addproduct.UpdateKiranaProductActivity;
import com.allinone.smartocity.Business.adapter.PincodeMultipleAdapter;
import com.allinone.smartocity.Business.adapter.ProductSizeMultipleAdapter;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.*;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.ProductSizeModel;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.Business.adapter.CategoryAdapter;
import com.allinone.smartocity.util.FileCompressor;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static android.Manifest.permission.*;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.allinone.smartocity.retrofit.ApiEndPoint.BASE_URL;

import static com.allinone.smartocity.retrofit.ApiEndPoint.ServiceMode.*;

import static com.allinone.smartocity.retrofit.Constant.SHARED_HOME_API_DETAILS;
import static com.allinone.smartocity.retrofit.Constant.SHARED_LOGIN_DETAILS;

public class AddBusinessActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback, AdapterView.OnItemSelectedListener {
    final Calendar myCalendar = Calendar.getInstance();
    private String TAG = "AddBusinessActivity";
    private EditText edt_business_name, edt_pancard_no, edt_pancard_name, edt_gst_no, edt_food_licence_no, edt_food_licence_expiry_date;
    private EditText edt_ifsc_code, edt_account_no, edt_confirm_account_no;
    private EditText edt_address_one, edt_address_two, edt_country;
    private EditText edt_state, edt_city, edt_pincode, edt_email, edt_otp, edt_min_order, edt_delivery_charges_per_km;
    private Button btn_submit_business, btn_send_otp, btn_otp_verify;
    private String business_name = "", pancard_no = "", pancard_name = "", ifsc_code = "", account_no = "";
    private String confirm_account_no = "", address_one = "", address_two = "", country = "";
    private String state = "", city = "", pincode = "", gstno = "", foodlicenceno = "", foodlicenceexpirydate = "";
    private Spinner spiner_category, spiner_delivery, spinner_pincode,spiner_deliverybelowmoacharges;
    private ArrayList<BusinessTypeModel> business_type_list;
    private CategoryAdapter categoryAdapter;
    private MainLoginResponse mainLoginResponse = null;
    private CardView card_business_details, card_other_details, card_order_details, card_images,
            card_bank_details, card_address_details;
    private FloatingActionButton fab_prev, fab_next;
    private String email = "";
    private String resultotp = "";
    private String otp = "";
    private boolean flagotp = false;
    private boolean checkORpassbookflag = false, shopflag = false, pancopyflag = false, foodlicenceproflag = false, addressproofflag = false;

    private ImageView img_check_or_passbook_image, img_shop_image, img_Food_licence_image, img_pancopy_image, img_address_proof_image;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mCheckPhotoFile = null;
    File mShopPhotoFile = null;
    File mPancopyPhotoFile = null;
    File mFoodLicencePhotoFile = null;
    File mAddressPhotoFile = null;
    FileCompressor mCompressor;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private String minorder = "", deliverychargesperkm = "";
    private CheckBox checkBox_term_condition;
    private RadioGroup radioGrp;
    private RadioButton radioButton;
    private BusinessTypeModel category = null;
    private String checkbookorpassbookImageUrl = "";
    private String pancardImageUrl = "";
    private String addressProofImageUrl = "";
    private String foodlicenceImageUrl = "";
    private String shopImageUrl = "";
    private MainResponseBusinessHomeModel mainResponseBusinessHomeModel = null;
    private TextView txt_check_or_passbook_proof_img, txt_shop_proof_img, txt_food_licence_proof_img,
            txt_pancopy_img, txt_address_proof_img, txt_minimumorderamount;
    private RelativeLayout rl_food_delivery, rl_deliverybelowmoacharges, rl_multiple_pincode;
    private PincodeMultipleAdapter pincodeMultipleAdapter;
    private ArrayList<Pincodemodel> pincode_list;
    List<Pincodemodel> selected_pincode_id_model = new ArrayList<Pincodemodel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_busniess);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        spiner_category.setOnItemSelectedListener(this);
        spiner_delivery.setOnItemSelectedListener(this);
        spinner_pincode.setOnItemSelectedListener(this);
        spiner_deliverybelowmoacharges.setOnItemSelectedListener(this);

        btn_submit_business.setOnClickListener(this);
        fab_next.setOnClickListener(this);
        fab_prev.setOnClickListener(this);
        btn_send_otp.setOnClickListener(this);
        btn_otp_verify.setOnClickListener(this);

        img_check_or_passbook_image.setOnClickListener(this);
        img_shop_image.setOnClickListener(this);
        img_Food_licence_image.setOnClickListener(this);
        img_address_proof_image.setOnClickListener(this);
        img_pancopy_image.setOnClickListener(this);
        edt_food_licence_expiry_date.setOnClickListener(this);


    }

    private void setData() {

        //getCategoryList();
        businessType();
        screen1();


    }

    private void businessType() {


        business_type_list.clear();
        BusinessTypeModel category = new BusinessTypeModel();
        category.setBusinessTypeName("Select business type");
        business_type_list.add(category);
        if (mainResponseBusinessHomeModel.getBusinessTypes().size() > 0)
            business_type_list.addAll(mainResponseBusinessHomeModel.getBusinessTypes());
        categoryAdapter = new CategoryAdapter(AddBusinessActivity.this, 0,
                business_type_list);
        spiner_category.setAdapter(categoryAdapter);
    }

    private void getBusinessValidationField(String bid) {

        Call<String> businessValidationField = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getBusinessValidationField(bid);
        businessValidationField.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, BUSINESS_VALIDATION_FIELD, true));

    }

    private void initializeWidgets() {
        spiner_category = findViewById(R.id.spiner_category);
        spiner_delivery = findViewById(R.id.spiner_delivery);
        spiner_deliverybelowmoacharges = findViewById(R.id.spiner_deliverybelowmoacharges);
        //cardView
        card_business_details = findViewById(R.id.card_business_details);
        card_address_details = findViewById(R.id.card_address_details);
        card_bank_details = findViewById(R.id.card_bank_details);
        card_images = findViewById(R.id.card_images);
        card_order_details = findViewById(R.id.card_order_details);
        card_other_details = findViewById(R.id.card_other_details);

        //other view
        edt_business_name = findViewById(R.id.edt_business_name);
        edt_pancard_no = findViewById(R.id.edt_pancard_no);
        edt_pancard_name = findViewById(R.id.edt_pancard_name);
        edt_gst_no = findViewById(R.id.edt_gst_no);
        edt_food_licence_no = findViewById(R.id.edt_food_licence_no);
        edt_food_licence_expiry_date = findViewById(R.id.edt_food_licence_expiry_date);


        edt_ifsc_code = findViewById(R.id.edt_ifsc_code);
        edt_account_no = findViewById(R.id.edt_account_no);
        edt_confirm_account_no = findViewById(R.id.edt_confirm_account_no);


        edt_address_one = findViewById(R.id.edt_address_one);
        edt_address_two = findViewById(R.id.edt_address_two);
        edt_country = findViewById(R.id.edt_country);
        edt_state = findViewById(R.id.edt_state);
        edt_city = findViewById(R.id.edt_city);
        edt_pincode = findViewById(R.id.edt_pincode);


        edt_min_order = findViewById(R.id.edt_min_order);
        radioGrp = findViewById(R.id.radioGrp);


        checkBox_term_condition = findViewById(R.id.checkBox_term_condition);
        edt_email = findViewById(R.id.edt_email);
        edt_otp = findViewById(R.id.edt_otp);


        btn_send_otp = findViewById(R.id.btn_send_otp);
        btn_otp_verify = findViewById(R.id.btn_otp_verify);
        btn_submit_business = findViewById(R.id.btn_submit_business);
        fab_prev = findViewById(R.id.fab_prev);
        fab_next = findViewById(R.id.fab_next);


        img_check_or_passbook_image = findViewById(R.id.img_check_or_passbook_image);
        img_shop_image = findViewById(R.id.img_shop_image);
        img_Food_licence_image = findViewById(R.id.img_Food_licence_image);
        img_pancopy_image = findViewById(R.id.img_pancopy_image);
        img_address_proof_image = findViewById(R.id.img_address_proof_image);


        txt_check_or_passbook_proof_img = findViewById(R.id.txt_check_or_passbook_proof_img);
        txt_shop_proof_img = findViewById(R.id.txt_shop_proof_img);
        txt_food_licence_proof_img = findViewById(R.id.txt_food_licence_proof_img);
        txt_pancopy_img = findViewById(R.id.txt_pancopy_img);
        txt_address_proof_img = findViewById(R.id.txt_address_proof_img);


        rl_food_delivery = findViewById(R.id.rl_food_delivery);
        rl_multiple_pincode = findViewById(R.id.rl_multiple_pincode);
        spinner_pincode = findViewById(R.id.spinner_pincode);
        rl_deliverybelowmoacharges = findViewById(R.id.rl_deliverybelowmoacharges);
        edt_delivery_charges_per_km = findViewById(R.id.edt_delivery_charges_per_km);
        txt_minimumorderamount = findViewById(R.id.txt_minimumorderamount);


    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);


        business_type_list = new ArrayList<>();
        mCompressor = new FileCompressor(this);

        pincode_list = new ArrayList<>();
        pincode_list.clear();
        Pincodemodel category1 = new Pincodemodel();
        category1.setPincode("Select pincode");
        pincode_list.add(category1);
        Pincodemodel category2 = new Pincodemodel();
        category2.setPincode("412308");
        pincode_list.add(category2);
        Pincodemodel category3 = new Pincodemodel();
        category3.setPincode("412309");
        pincode_list.add(category3);


        pincodeMultipleAdapter = new PincodeMultipleAdapter(AddBusinessActivity.this, 0,
                pincode_list);
        spinner_pincode.setAdapter(pincodeMultipleAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_business:
                if (Valid6())
                    if (InternetConnection.checkConnection(AddBusinessActivity.this)) {
                        uploadCheckImage();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }

                break;

            case R.id.fab_next:

                if (card_business_details.getVisibility() == View.VISIBLE) {

                    if (Valid1())
                        screen2();
                } else if (card_address_details.getVisibility() == View.VISIBLE) {
                    if (Valid2())
                        screen3();
                } else if (card_bank_details.getVisibility() == View.VISIBLE) {
                    if (Valid3())
                        screen4();
                } else if (card_images.getVisibility() == View.VISIBLE) {
                    if (Valid4())
                        screen5();

                } else if (card_order_details.getVisibility() == View.VISIBLE) {
                    if (Valid5())
                        screen6();
                }


                break;

            case R.id.fab_prev:

                if (card_address_details.getVisibility() == View.VISIBLE) {
                    screen1();
                } else if (card_bank_details.getVisibility() == View.VISIBLE) {
                    screen2();
                }  else if (card_order_details.getVisibility() == View.VISIBLE) {
                    screen3();
                }else if (card_images.getVisibility() == View.VISIBLE) {
                    screen4();
                } else if (card_other_details.getVisibility() == View.VISIBLE) {
                    screen6();
                }

                break;

            case R.id.btn_send_otp:
                if (InternetConnection.checkConnection(AddBusinessActivity.this)) {

                    sendOTP();
                } else {
                    Toast.makeText(AddBusinessActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_otp_verify:
                if (validOTP()) {
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    Toast.makeText(AddBusinessActivity.this, "Otp validate!", Toast.LENGTH_SHORT).show();
                    flagotp = true;
                } else
                    flagotp = false;
                break;

            case R.id.img_shop_image:
                checkORpassbookflag = false;
                shopflag = true;
                pancopyflag = false;
                foodlicenceproflag = false;
                addressproofflag = false;
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage();

                }
                break;

            case R.id.img_check_or_passbook_image:
                checkORpassbookflag = true;
                shopflag = false;
                pancopyflag = false;
                foodlicenceproflag = false;
                addressproofflag = false;
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage();

                }
                break;
            case R.id.img_Food_licence_image:
                checkORpassbookflag = false;
                shopflag = false;
                pancopyflag = false;
                foodlicenceproflag = true;
                addressproofflag = false;
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage();

                }
                break;


            case R.id.img_pancopy_image:
                checkORpassbookflag = false;
                shopflag = false;
                pancopyflag = true;
                foodlicenceproflag = false;
                addressproofflag = false;
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage();

                }
                break;


            case R.id.img_address_proof_image:
                checkORpassbookflag = false;
                shopflag = false;
                pancopyflag = false;
                foodlicenceproflag = false;
                addressproofflag = true;
                if (!checkPermission()) {

                    requestPermission();

                } else {

                    selectImage();

                }
                break;

            case R.id.edt_food_licence_expiry_date:


                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddBusinessActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
                break;

        }
    }




    private void updateDate() {
        String myFormat = "dd-MM-yyyy"; //put your date format in which you need to display
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        edt_food_licence_expiry_date.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean Valid6() {
        if (!flagotp) {
            Toast.makeText(AddBusinessActivity.this, "Please valid email", Toast.LENGTH_SHORT).show();

            return false;
        }

        if (!checkBox_term_condition.isChecked()) {
            Toast.makeText(AddBusinessActivity.this, "Please accept term and condition", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }


    private void sendOTP() {
        if (validEmail()) ;
        {
            Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getSentOTPOnEmail(email);
            walletDeductDetails.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, SENDOTPONEMAIL, true));
        }
    }


    private boolean validEmail() {

        email = edt_email.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(AddBusinessActivity.this, "Please enter valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private boolean validOTP() {

        otp = edt_otp.getText().toString().trim();

        if (otp.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter 6 digit otp", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!otp.equals(resultotp)) {
            Toast.makeText(AddBusinessActivity.this, "Please enter valid otp", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private void screen1() {
        card_business_details.setVisibility(View.VISIBLE);
        card_other_details.setVisibility(View.GONE);
        card_order_details.setVisibility(View.GONE);
        card_images.setVisibility(View.GONE);
        card_bank_details.setVisibility(View.GONE);
        card_address_details.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.GONE);
        selected_pincode_id_model.clear();
    }


    private void screen2() {
        card_business_details.setVisibility(View.GONE);
        card_other_details.setVisibility(View.GONE);
        card_order_details.setVisibility(View.GONE);
        card_images.setVisibility(View.GONE);
        card_bank_details.setVisibility(View.GONE);
        card_address_details.setVisibility(View.VISIBLE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
    }

    private void screen3() {
        card_business_details.setVisibility(View.GONE);
        card_other_details.setVisibility(View.GONE);
        card_order_details.setVisibility(View.GONE);
        card_images.setVisibility(View.GONE);
        card_bank_details.setVisibility(View.VISIBLE);
        card_address_details.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
    }



    private void screen4() {
        card_business_details.setVisibility(View.GONE);
        card_other_details.setVisibility(View.GONE);
        card_order_details.setVisibility(View.VISIBLE);
        card_images.setVisibility(View.GONE);
        card_bank_details.setVisibility(View.GONE);
        card_address_details.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
    }

    private void screen5() {
        card_business_details.setVisibility(View.GONE);
        card_other_details.setVisibility(View.VISIBLE);
        card_order_details.setVisibility(View.GONE);
        card_images.setVisibility(View.GONE);
        card_bank_details.setVisibility(View.GONE);
        card_address_details.setVisibility(View.GONE);
        fab_next.setVisibility(View.GONE);
        fab_prev.setVisibility(View.VISIBLE);
    }


    private void screen6() {
        card_business_details.setVisibility(View.GONE);
        card_other_details.setVisibility(View.GONE);
        card_order_details.setVisibility(View.GONE);
        card_images.setVisibility(View.VISIBLE);
        card_bank_details.setVisibility(View.GONE);
        card_address_details.setVisibility(View.GONE);
        fab_next.setVisibility(View.VISIBLE);
        fab_prev.setVisibility(View.VISIBLE);
    }

    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddBusinessActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
               /* if (options[item].equals("Take Photo")) {
                    try {

                        dispatchTakePictureIntent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else */if (options[item].equals("Choose from Gallery")) {
                    dispatchGalleryIntent();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    /**
     * Select image fro gallery
     */
    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                if (checkORpassbookflag) {

                    try {
                        mCheckPhotoFile = mCompressor.compressToFile(mCheckPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mCheckPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_check_or_passbook_image);
                }
                if (shopflag) {

                    try {
                        mShopPhotoFile = mCompressor.compressToFile(mShopPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mShopPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_shop_image);
                }

                if (pancopyflag) {

                    try {
                        mPancopyPhotoFile = mCompressor.compressToFile(mPancopyPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mPancopyPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_pancopy_image);
                }

                if (foodlicenceproflag) {

                    try {
                        mFoodLicencePhotoFile = mCompressor.compressToFile(mFoodLicencePhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mFoodLicencePhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_Food_licence_image);
                }


                if (addressproofflag) {

                    try {
                        mAddressPhotoFile = mCompressor.compressToFile(mAddressPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mAddressPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_address_proof_image);
                }

            } else if (requestCode == REQUEST_GALLERY_PHOTO) {

                if (checkORpassbookflag) {
                    Uri selectedImage = data.getData();
                    try {
                        mCheckPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mCheckPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_check_or_passbook_image);
                }
                if (shopflag) {
                    Uri selectedImage = data.getData();
                    try {
                        mShopPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mShopPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_shop_image);
                }


                if (pancopyflag) {
                    Uri selectedImage = data.getData();
                    try {
                        mPancopyPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mPancopyPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_pancopy_image);
                }

                if (addressproofflag) {
                    Uri selectedImage = data.getData();
                    try {
                        mAddressPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mAddressPhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_address_proof_image);
                }

                if (foodlicenceproflag) {
                    Uri selectedImage = data.getData();
                    try {
                        mFoodLicencePhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(AddBusinessActivity.this)
                            .load(mFoodLicencePhotoFile)
                            .apply(new RequestOptions().centerCrop()
                                    .centerInside()
                                    .placeholder(R.mipmap.place_holder))
                            .into(img_Food_licence_image);
                }


            }
        }
    }

    /**
     * Get real file path from URI
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                if (checkORpassbookflag)
                    mCheckPhotoFile = photoFile;
                if (shopflag)
                    mShopPhotoFile = photoFile;
                if (pancopyflag)
                    mPancopyPhotoFile = photoFile;
                if (foodlicenceproflag)
                    mFoodLicencePhotoFile = photoFile;
                if (addressproofflag)
                    mAddressPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (writeAccepted && cameraAccepted && readAccepted) {
                        showMessageOKCancel("Permission Granted, Now you can access location data and camera.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                selectImage();
                            }
                        });
                    } else {

                        showMessageOKCancel("Permission Denied, You cannot access location data and camera.", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                        PERMISSION_REQUEST_CODE);
                            }
                        });

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                                        PERMISSION_REQUEST_CODE);
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(AddBusinessActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void addBusiness() {

        int selectedId = radioGrp.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);
        boolean term = true;
        JSONObject jsonObject = new JSONObject();
        try {


            JSONObject businessType = new JSONObject();
            businessType.put("businessTypeId", Integer.parseInt(category.getBusinessTypeId()));

            JSONObject businessmanUser = new JSONObject();
            businessmanUser.put("bmId", Integer.parseInt(mainLoginResponse.getResult().getBmId()));


            JSONArray productpincodeArray = new JSONArray();
            for (int i = 0; i < selected_pincode_id_model.size(); i++) {
                Pincodemodel pincodemodel = selected_pincode_id_model.get(i);
                //allEds.get(i).getText().toString();
                JSONObject pincodejson = new JSONObject();

                pincodejson.put("pinCode", pincodemodel.getPincode());


                productpincodeArray.put(pincodejson);
            }


            jsonObject.put("businessName", business_name);
            jsonObject.put("panCard", pancard_no);

            jsonObject.put("gstNumber", gstno);
            jsonObject.put("nameAsPerPan", pancard_name);
            jsonObject.put("foodLicenseNumber", foodlicenceno);
            jsonObject.put("foodLicenseNumberExpiryDate", foodlicenceexpirydate);

            jsonObject.put("addressLine1", address_one);
            jsonObject.put("addressLine2", address_two);
            jsonObject.put("country", country);
            jsonObject.put("city", city);
            jsonObject.put("State", state);
            jsonObject.put("pincode", Integer.parseInt(pincode));


            jsonObject.put("ifscCode", ifsc_code);
            jsonObject.put("accountNo", Integer.parseInt(account_no));
            jsonObject.put("accountNo", Integer.parseInt(confirm_account_no));
            jsonObject.put("checkImageUrl", checkbookorpassbookImageUrl);
            jsonObject.put("shopImageUrl", shopImageUrl);
            jsonObject.put("foodLicenseImageUrl", foodlicenceImageUrl);
            jsonObject.put("panCardImageUrl", pancardImageUrl);
            jsonObject.put("addressProfImageUrl", addressProofImageUrl);


            jsonObject.put("freeDelivery", spiner_delivery.getSelectedItem().toString());

            jsonObject.put("minimumOrderAmount", Integer.parseInt(minorder));
            jsonObject.put("mOAWithCharges", spiner_deliverybelowmoacharges.getSelectedItem().toString());
            jsonObject.put("deliverPChargesPerKM", deliverychargesperkm);
            jsonObject.put("deliverablePIN_Code", productpincodeArray);
            jsonObject.put("termAndCondition", true);

            jsonObject.put("cashOnDelivery", radioButton.getText());
            jsonObject.put("emailId", email);
            jsonObject.put("businessType", businessType);

            jsonObject.put("businessmanUser", businessmanUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getAddBusinessDetails(headers, jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, ADDBUSINESS, true));

    }


    private void uploadCheckImage() {

        MultipartBody.Part filePart = null;

        filePart = MultipartBody.Part.createFormData("file", mCheckPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mCheckPhotoFile));
        if (filePart != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
            Call<String> checkbook = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
            checkbook.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, UPLOADIMAGE_CHECKBOOK, true));
        }
    }


    private void uploadShopImage() {

        MultipartBody.Part filePart = null;
        filePart = MultipartBody.Part.createFormData("file", mShopPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mShopPhotoFile));
        if (filePart != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
            Call<String> shopimage = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
            shopimage.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, UPLOADIMAGE_SHOP, true));
        }
    }


    private void uploadPancardImage() {

        MultipartBody.Part filePart = null;
        filePart = MultipartBody.Part.createFormData("file", mPancopyPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mPancopyPhotoFile));
        if (filePart != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
            Call<String> shopimage = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
            shopimage.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, UPLOADIMAGE_PANCARD, true));
        }
    }

    private void uploadAddressProofImage() {

        MultipartBody.Part filePart = null;
        filePart = MultipartBody.Part.createFormData("file", mAddressPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mAddressPhotoFile));
        if (filePart != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
            Call<String> shopimage = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
            shopimage.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, UPLOADIMAGE_ADDRESS_PROOF, true));
        }
    }


    private void uploadFoodLicenceProofImage() {

        MultipartBody.Part filePart = null;
        filePart = MultipartBody.Part.createFormData("file", mFoodLicencePhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mFoodLicencePhotoFile));
        if (filePart != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
            Call<String> shopimage = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
            shopimage.enqueue(new RestProcess<String>(AddBusinessActivity.this, AddBusinessActivity.this, UPLOADIMAGE_FOOD_LICENCE, true));
        }
    }

    private boolean Valid1() {
//        business_name = edt_business_name.getText().toString().trim();
//        pancard_no = edt_pancard_no.getText().toString().trim();
//        pancard_name = edt_pancard_name.getText().toString().trim();
//        gstno = edt_gst_no.getText().toString().trim();
//        foodlicenceno = edt_food_licence_no.getText().toString().trim();
//        foodlicenceexpirydate = edt_food_licence_expiry_date.getText().toString().trim();

        if (category == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select business type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (business_name.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter business name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pancard_no.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter pan card no", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pancard_name.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter pancard holder name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (gstno.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter GST NO", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (foodlicenceno.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter food licence no", Toast.LENGTH_SHORT).show();
            return false;

        }

        if (foodlicenceexpirydate.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter food licence expiry date", Toast.LENGTH_SHORT).show();
            return false;

        }


        return true;
    }


    private boolean Valid2() {

        address_one = edt_address_one.getText().toString().trim();
        address_two = edt_address_two.getText().toString().trim();
        country = edt_country.getText().toString().trim();
        state = edt_state.getText().toString().trim();
        city = edt_city.getText().toString().trim();
        pincode = edt_pincode.getText().toString().trim();


        if (address_one.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (address_two.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter address second", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (country.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter country", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (state.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter state", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pincode.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter pincode", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean Valid3() {

        ifsc_code = edt_ifsc_code.getText().toString().trim();
        account_no = edt_account_no.getText().toString().trim();
        confirm_account_no = edt_confirm_account_no.getText().toString().trim();


        if (ifsc_code.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter Ifsc code", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (account_no.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter account no", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (confirm_account_no.equals("")) {
            Toast.makeText(AddBusinessActivity.this, "Please enter confirm account no", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!account_no.equals(confirm_account_no)) {
            Toast.makeText(AddBusinessActivity.this, "Account no mismatch", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean Valid4() {

        if (mCheckPhotoFile == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select CheckBook or Passbook image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mShopPhotoFile == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select shop image", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mFoodLicencePhotoFile == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select food licence image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mPancopyPhotoFile == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select pan image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mAddressPhotoFile == null) {
            Toast.makeText(AddBusinessActivity.this, "Please select address image", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    private boolean Valid5() {
        minorder = edt_min_order.getText().toString().trim();
        deliverychargesperkm = edt_delivery_charges_per_km.getText().toString().trim();


        String selectedItem = spiner_delivery.getSelectedItem().toString(); //

        if (selectedItem.equals("Yes")) {
            if (minorder.equals("")) {
                Toast.makeText(AddBusinessActivity.this, "Please enter Min order", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        String selectedItem1 = spiner_deliverybelowmoacharges.getSelectedItem().toString(); //

        if (selectedItem1.equals("Yes")) {
            if (deliverychargesperkm.equals("")) {
                Toast.makeText(AddBusinessActivity.this, "Please enter Min order charges per km", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (pincodeMultipleAdapter.getProductSizeList() != null) {
            if (pincodeMultipleAdapter.getProductSizeList().size() > 0) {
                for (int i = 0; i < pincodeMultipleAdapter.getProductSizeList().size(); i++) {
                    Pincodemodel pincodemodel = pincodeMultipleAdapter.getProductSizeList().get(i);
                    if (pincodemodel.getSelected()) {
                        selected_pincode_id_model.add(pincodemodel);
                    }

                }
            }
        }

        if (selected_pincode_id_model.size() == 0) {
            Toast.makeText(AddBusinessActivity.this, "Please select at list one pincode", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case ADDBUSINESS:
                break;

            case GETBUSINESSCATEGORIESLIST:
                break;

            case BUSINESS_VALIDATION_FIELD:
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
            case ADDBUSINESS:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case BUSINESS_VALIDATION_FIELD:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    MainFieldValidationResponse response = new Gson().fromJson(jsonObject.toString(), MainFieldValidationResponse.class);
                    if (response.getStatus().equals("1")) {
                        //Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (response.getResult() != null) {
                            if (response.getResult().size() > 0) {
                                for (int i = 0; i < response.getResult().size(); i++) {
                                    SubFieldValadationModel subFieldValadationModel = response.getResult().get(i);

                                    switch (subFieldValadationModel.getBusinessField().getFieldCode()) {


                                        case "BUSINESS_NAME":
                                            edt_business_name.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_business_name.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    business_name = "";
                                                } else {
                                                    business_name = "-";
                                                }
                                            } else {
                                                business_name = "-";
                                                edt_business_name.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "PAN":
                                            edt_pancard_no.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_pancard_no.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    pancard_no = "";
                                                } else {
                                                    pancard_no = "-";
                                                }
                                            } else {
                                                pancard_no = "-";
                                                edt_pancard_no.setVisibility(View.GONE);
                                            }


                                            break;
                                        case "NAME_AS_PER_PAN":

                                            edt_pancard_name.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_pancard_name.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    pancard_name = "";
                                                } else {
                                                    pancard_name = "-";
                                                }
                                            } else {
                                                pancard_name = "-";
                                                edt_pancard_name.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "GST_NUMBER":

                                            edt_gst_no.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_gst_no.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    gstno = "";
                                                } else {
                                                    gstno = "-";
                                                }
                                            } else {
                                                gstno = "-";
                                                edt_gst_no.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "FOOD_LICENSE_NUMBER_(FSSAI)":

                                            edt_food_licence_no.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_food_licence_no.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    foodlicenceno = "";
                                                } else {
                                                    foodlicenceno = "-";
                                                }
                                            } else {
                                                foodlicenceno = "-";
                                                edt_food_licence_no.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "FSSAI_EXPIRY_DATE":

                                            edt_food_licence_expiry_date.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_food_licence_expiry_date.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    foodlicenceexpirydate = "";
                                                } else {
                                                    foodlicenceexpirydate = "-";
                                                }
                                            } else {
                                                foodlicenceexpirydate = "-";
                                                edt_food_licence_expiry_date.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "ADDRESS_LINE_1":
                                            edt_address_one.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_address_one.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    address_one = "";
                                                } else {
                                                    address_one = "-";
                                                }
                                            } else {
                                                address_one = "-";
                                                edt_address_one.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "ADDRESS_LINE_2":

                                            edt_address_two.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_address_two.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    address_two = "";
                                                } else {
                                                    address_two = "-";
                                                }
                                            } else {
                                                address_two = "-";
                                                edt_address_two.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "COUNTY":
                                            edt_country.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_country.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    country = "";
                                                } else {
                                                    country = "-";
                                                }
                                            } else {
                                                country = "-";
                                                edt_country.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "STATE":
                                            edt_state.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_state.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    state = "";
                                                } else {
                                                    state = "-";
                                                }
                                            } else {
                                                state = "-";
                                                edt_state.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "CITY":
                                            edt_city.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_city.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    city = "";
                                                } else {
                                                    city = "-";
                                                }
                                            } else {
                                                city = "-";
                                                edt_city.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "PINCODE":
                                            edt_pincode.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_pincode.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    pincode = "";
                                                } else {
                                                    pincode = "-";
                                                }
                                            } else {
                                                pincode = "-";
                                                edt_pincode.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "IFSC_CODE":
                                            edt_ifsc_code.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_ifsc_code.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    ifsc_code = "";
                                                } else {
                                                    ifsc_code = "-";
                                                }
                                            } else {
                                                ifsc_code = "-";
                                                edt_ifsc_code.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "ACCOUNT_NO":

                                            edt_account_no.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_account_no.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    account_no = "";
                                                } else {
                                                    account_no = "-";
                                                }
                                            } else {
                                                account_no = "-";
                                                edt_account_no.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "CONFIRM_ACCOUNT_NO":

                                            edt_confirm_account_no.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_confirm_account_no.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    confirm_account_no = "";
                                                } else {
                                                    confirm_account_no = "-";
                                                }
                                            } else {
                                                confirm_account_no = "-";
                                                edt_confirm_account_no.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "CHEQUE_IMAGE":
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                txt_check_or_passbook_proof_img.setVisibility(View.VISIBLE);
                                                img_check_or_passbook_image.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    mCheckPhotoFile = null;
                                                } else {
                                                    mCheckPhotoFile = new File("");
                                                }
                                            } else {
                                                mCheckPhotoFile = new File("");
                                                txt_check_or_passbook_proof_img.setVisibility(View.GONE);
                                                img_check_or_passbook_image.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "SHOP_IMAGE":
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                txt_shop_proof_img.setVisibility(View.VISIBLE);
                                                img_shop_image.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    mShopPhotoFile = null;
                                                } else {
                                                    mShopPhotoFile = new File("");
                                                }
                                            } else {
                                                mShopPhotoFile = new File("");
                                                txt_shop_proof_img.setVisibility(View.GONE);
                                                img_shop_image.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "FOOD_LICENSE_IMAGE":

                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                txt_food_licence_proof_img.setVisibility(View.VISIBLE);
                                                img_Food_licence_image.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    mFoodLicencePhotoFile = null;
                                                } else {
                                                    mFoodLicencePhotoFile = new File("");
                                                }
                                            } else {
                                                mFoodLicencePhotoFile = new File("");
                                                txt_food_licence_proof_img.setVisibility(View.GONE);
                                                img_Food_licence_image.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "PAN_COPY":
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                txt_pancopy_img.setVisibility(View.VISIBLE);
                                                img_pancopy_image.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    mPancopyPhotoFile = null;
                                                } else {
                                                    mPancopyPhotoFile = new File("");
                                                }
                                            } else {
                                                mPancopyPhotoFile = new File("");
                                                img_pancopy_image.setVisibility(View.GONE);
                                                txt_pancopy_img.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "ADDRESS_PROOF":

                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                txt_address_proof_img.setVisibility(View.VISIBLE);
                                                img_address_proof_image.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    mAddressPhotoFile = null;
                                                } else {
                                                    mAddressPhotoFile = new File("");
                                                }
                                            } else {
                                                mAddressPhotoFile = new File("");
                                                img_address_proof_image.setVisibility(View.GONE);
                                                txt_address_proof_img.setVisibility(View.GONE);
                                            }

                                            break;
                                        case "FREE_DELIVERY":

                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                rl_food_delivery.setVisibility(View.VISIBLE);
                                            } else {
                                                rl_food_delivery.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "MINIMUM_ORDER_AMOUNT_(MOA)":

                                            edt_min_order.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                // edt_min_order.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    minorder = "";
                                                } else {
                                                    minorder = "-";
                                                }
                                            } else {
                                                minorder = "-";
                                                // edt_min_order.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "DELIVERY_BELOW_MOA_WITH_CHARGES?":
                                          /*  if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                rl_deliverybelowmoacharges.setVisibility(View.VISIBLE);
                                            } else {
                                                rl_deliverybelowmoacharges.setVisibility(View.GONE);
                                            }*/
                                            break;
                                        case "IF_YES_DELIVERY_CHARGES_PER_K.M":

                                            edt_delivery_charges_per_km.setHint(subFieldValadationModel.getBusinessField().getFieldCode());
                                            if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                edt_delivery_charges_per_km.setVisibility(View.VISIBLE);
                                                if (subFieldValadationModel.getMandaryOrOptional().equals("Mandatory")) {
                                                    deliverychargesperkm = "";
                                                } else {
                                                    deliverychargesperkm = "-";
                                                }
                                            } else {
                                                deliverychargesperkm = "-";
                                                edt_delivery_charges_per_km.setVisibility(View.GONE);
                                            }
                                            break;
                                        case "DELIVERABLE_TO_PIN_CODE":


                                            break;
                                        case "CASH_ON_DELIVERY":
                                            /*if (subFieldValadationModel.getIsVisible().equals("Yes")) {
                                                rl_cashon_delivery.setVisibility(View.VISIBLE);
                                            } else {
                                                rl_cashon_delivery.setVisibility(View.GONE);
                                            }*/
                                            break;
                                        case "EMAIL":
                                            break;
                                        case "OTP":
                                            break;
                                        case "TERMS_AND_CONDITIONS":
                                            break;


                                        default:
                                            throw new IllegalStateException("Unexpected value: " + subFieldValadationModel.getBusinessField().getFieldCode());
                                    }


                                }
                            }
                        }

                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case SENDOTPONEMAIL:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        resultotp = jsonObject.getString("result");
                        btn_send_otp.setText("Resend");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message" + resultotp), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case UPLOADIMAGE_CHECKBOOK:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        checkbookorpassbookImageUrl = jsonObject.getString("result");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        uploadPancardImage();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case UPLOADIMAGE_PANCARD:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        pancardImageUrl = jsonObject.getString("result");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        uploadAddressProofImage();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case UPLOADIMAGE_ADDRESS_PROOF:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        addressProofImageUrl = jsonObject.getString("result");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                       if(mFoodLicencePhotoFile==null) {
                           uploadShopImage();
                       }else {
                           uploadFoodLicenceProofImage();
                       }
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case UPLOADIMAGE_FOOD_LICENCE:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        foodlicenceImageUrl = jsonObject.getString("result");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        uploadShopImage();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case UPLOADIMAGE_SHOP:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        shopImageUrl = jsonObject.getString("result");
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        addBusiness();
                    } else {
                        Toast.makeText(AddBusinessActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            // close this activity and return to preview activity (if there is any)
            if (card_address_details.getVisibility() == View.VISIBLE) {
                screen1();
            } else if (card_bank_details.getVisibility() == View.VISIBLE) {
                screen2();
            } else if (card_images.getVisibility() == View.VISIBLE) {
                screen3();
            } else if (card_order_details.getVisibility() == View.VISIBLE) {
                screen4();
            } else if (card_other_details.getVisibility() == View.VISIBLE) {
                screen5();
            } else {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (card_address_details.getVisibility() == View.VISIBLE) {
            screen1();
        } else if (card_bank_details.getVisibility() == View.VISIBLE) {
            screen2();
        } else if (card_images.getVisibility() == View.VISIBLE) {
            screen3();
        } else if (card_order_details.getVisibility() == View.VISIBLE) {
            screen4();
        } else if (card_other_details.getVisibility() == View.VISIBLE) {
            screen5();
        } else {
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("checkboo", checkORpassbookflag);
        outState.putBoolean("sho", shopflag);
        outState.putBoolean("pa", pancopyflag);
        outState.putBoolean("foodlicenc", foodlicenceproflag);
        outState.putBoolean("addres", addressproofflag);

        if (checkORpassbookflag) {
            if (mCheckPhotoFile != null)
                outState.putString("checkbook", mCheckPhotoFile.getAbsolutePath());
        }
        if (shopflag) {
            if (mShopPhotoFile != null)
                outState.putString("shop", mShopPhotoFile.getAbsolutePath());
        }
        if (pancopyflag) {
            if (mPancopyPhotoFile != null)
                outState.putString("pan", mPancopyPhotoFile.getAbsolutePath());
        }
        if (foodlicenceproflag) {
            if (mFoodLicencePhotoFile != null)
                outState.putString("foodlicence", mFoodLicencePhotoFile.getAbsolutePath());
        }
        if (addressproofflag) {
            if (mAddressPhotoFile != null)
                outState.putString("address", mAddressPhotoFile.getAbsolutePath());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        checkORpassbookflag = savedInstanceState.getBoolean("checkboo");
        shopflag = savedInstanceState.getBoolean("sho");
        pancopyflag = savedInstanceState.getBoolean("pa");
        foodlicenceproflag = savedInstanceState.getBoolean("foodlicenc");
        addressproofflag = savedInstanceState.getBoolean("addres");
        if (checkORpassbookflag)
            mCheckPhotoFile = new File(savedInstanceState.getString("checkbook"));
        if (shopflag)
            mShopPhotoFile = new File(savedInstanceState.getString("shop"));
        if (pancopyflag)
            mPancopyPhotoFile = new File(savedInstanceState.getString("pan"));
        if (foodlicenceproflag)
            mFoodLicencePhotoFile = new File(savedInstanceState.getString("foodlicence"));
        if (addressproofflag)
            mAddressPhotoFile = new File(savedInstanceState.getString("address"));

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spiner_category:
                if (business_type_list.size() > 0) {
                    category = business_type_list.get(position);
                    if (category.getBusinessTypeName().equals("Select business type")) {
                        category = null;
                    } else {
                        if (InternetConnection.checkConnection(AddBusinessActivity.this)) {
                            getBusinessValidationField(category.getBusinessTypeId());
                        } else {
                            Toast.makeText(AddBusinessActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
            case R.id.spiner_delivery:
                String selectedItem = parent.getItemAtPosition(position).toString(); //

                if (selectedItem.equals("Yes")) {
                    txt_minimumorderamount.setVisibility(View.VISIBLE);
                    edt_min_order.setVisibility(View.VISIBLE);
                } else {
                    edt_min_order.setText("");
                    txt_minimumorderamount.setVisibility(View.GONE);
                    edt_min_order.setVisibility(View.GONE);
                }

                break;


            case R.id.spiner_deliverybelowmoacharges:
                String selectedItem1 = parent.getItemAtPosition(position).toString(); //

                if (selectedItem1.equals("Yes")) {
                    edt_delivery_charges_per_km.setVisibility(View.VISIBLE);

                } else {
                    edt_delivery_charges_per_km.setVisibility(View.GONE);
                    edt_delivery_charges_per_km.setText("");
                }

                break;
           /* case R.id.spinner_pincode:
            if (pincode_list.size() > 0) {
                pincodemodel = pincode_list.get(position);
                if (pincodemodel.getPincode().equals("Select pincode")) {
                    pincodemodel = null;
                }
            }
break;*/

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

