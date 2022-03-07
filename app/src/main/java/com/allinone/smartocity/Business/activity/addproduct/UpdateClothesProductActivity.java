package com.allinone.smartocity.Business.activity.addproduct;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.allinone.smartocity.BuildConfig;
import com.allinone.smartocity.Business.adapter.*;
import com.allinone.smartocity.R;
import com.allinone.smartocity.model.addBusiness.BusinessListResponse;
import com.allinone.smartocity.model.addBusiness.BusinessTypeModel;
import com.allinone.smartocity.model.home.MainResponseBusinessHomeModel;
import com.allinone.smartocity.model.home.TaxRatesItemModel;
import com.allinone.smartocity.model.mainresponse.MainLoginResponse;
import com.allinone.smartocity.model.product.MainProductCategoryResponse;
import com.allinone.smartocity.model.product.ProductSizeModel;
import com.allinone.smartocity.model.product.ProductSubCategory;
import com.allinone.smartocity.model.product.ProductTypeModel;
import com.allinone.smartocity.retrofit.RestAPIInterface;
import com.allinone.smartocity.retrofit.RestCalllback;
import com.allinone.smartocity.retrofit.RestProcess;
import com.allinone.smartocity.retrofit.ServiceGenerator;
import com.allinone.smartocity.util.FileCompressor;
import com.allinone.smartocity.util.InternetConnection;
import com.allinone.smartocity.util.SharedPref;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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


public class UpdateClothesProductActivity extends AppCompatActivity implements View.OnClickListener, RestCalllback, AdapterView.OnItemSelectedListener {

    private String TAG = "AddBusinessActivity";
    private Button btn_submit_product;
    File mPhotoFile = null;
    FileCompressor mCompressor;
    private Spinner spinner_color,spiner_single_size;


    private MainLoginResponse mainLoginResponse = null;




    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    private static final int PERMISSION_REQUEST_CODE = 200;


    private EditText
             edt_product_final_price, edt_product_available_quantity;
    private String product_available_quantity = "";


    private MainResponseBusinessHomeModel mainResponseBusinessHomeModel = null;
    private BusinessListResponse businessListResponse = null;

    private ProductSizeModel productSizeModel = null;
    private ImageView img_product;

    private String uploadedImageName = "",productDetailsId="";

    private ArrayList<ProductSizeModel> product_size_list;

    private ProductSizeSingleAdapter productSizeSingleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_clothes_product);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeWidgets();
        initializeData();
        setOnClickListener();
        setData();

    }

    private void setOnClickListener() {
        btn_submit_product.setOnClickListener(this);
        img_product.setOnClickListener(this);
        spiner_single_size.setOnItemSelectedListener(this);

    }

    private void setData() {


        for (int i = 0; i < mainResponseBusinessHomeModel.getBusinessTypesWithProductType().size(); i++) {

            BusinessTypeModel typeModel = mainResponseBusinessHomeModel.getBusinessTypesWithProductType().get(i);

            if (businessListResponse.getBusinessTypes().getBusinessTypeId().equals(typeModel.getBusinessTypeId())) {

                setProductSizeList(typeModel);
            }
        }

    }



    private void setProductSizeList(BusinessTypeModel typeModel) {
        product_size_list.clear();
        ProductSizeModel category1 = new ProductSizeModel();
        category1.setSizeTypeName("Select size");
        product_size_list.add(category1);
        if (typeModel.getProductSizeTypes().size() > 0) {
            product_size_list.addAll(typeModel.getProductSizeTypes());

            productSizeSingleAdapter = new ProductSizeSingleAdapter(UpdateClothesProductActivity.this, 0,
                    product_size_list);
            spiner_single_size.setAdapter(productSizeSingleAdapter);
        }

    }



    private void initializeWidgets() {
        spiner_single_size = findViewById(R.id.spiner_single_size);
        spinner_color = findViewById(R.id.spinner_color);
        img_product = findViewById(R.id.img_product);

        edt_product_final_price = findViewById(R.id.edt_product_final_price);
        edt_product_available_quantity = findViewById(R.id.edt_product_available_quantity);
        btn_submit_product = findViewById(R.id.btn_submit_product);
        mCompressor = new FileCompressor(this);
    }

    private void initializeData() {
        SharedPref.init(getApplicationContext());
        String sharefloginDetail = SharedPref.read(SHARED_LOGIN_DETAILS, "");
        String sharefHomeAPIDetail = SharedPref.read(SHARED_HOME_API_DETAILS, "");
        mainLoginResponse = new Gson().fromJson(sharefloginDetail, MainLoginResponse.class);
        mainResponseBusinessHomeModel = new Gson().fromJson(sharefHomeAPIDetail, MainResponseBusinessHomeModel.class);
        productDetailsId = getIntent().getStringExtra("productDetailsId");
        businessListResponse = (BusinessListResponse) getIntent().getSerializableExtra("BusinessResponse");
        product_size_list = new ArrayList<>();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit_product:
                if (valid()) {
                    if (InternetConnection.checkConnection(UpdateClothesProductActivity.this)) {
                        uploadImage();
                    } else {
                        Toast.makeText(UpdateClothesProductActivity.this, getResources().getString(R.string.internetconnectio), Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.img_product:
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    selectImage(REQUEST_TAKE_PHOTO, REQUEST_GALLERY_PHOTO);
                }
                break;


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

    private void selectImage(final int requestTakePhoto, final int requestGalleryPhoto) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateClothesProductActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    try {

                        dispatchTakePictureIntent(requestTakePhoto);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    dispatchGalleryIntent(requestGalleryPhoto);
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
    private void dispatchGalleryIntent(int requestGalleryPhoto) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, requestGalleryPhoto);
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


    private void dispatchTakePictureIntent(int requestTakePhoto) {
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
                switch (requestTakePhoto) {
                    case 1:
                        mPhotoFile = photoFile;
                        break;

                }


                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, requestTakePhoto);
            }
        }
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

                                //selectImage(REQUEST_TAKE_PHOTO, REQUEST_GALLERY_PHOTO, mPhotoFile);
                            }
                        });
                    } else {

                        showMessageOKCancel("Permission Denied, You cannot access location data and camera.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA, READ_EXTERNAL_STORAGE},
                                            PERMISSION_REQUEST_CODE);
                                }
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
        new AlertDialog.Builder(UpdateClothesProductActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }




    private void addProduct() {

        JSONObject jsonObject = new JSONObject();
        try {





            jsonObject.put("productDetailsId", productDetailsId);


            JSONArray productKeywords = new JSONArray();
            JSONObject keywor = new JSONObject();
            keywor.put("color", spinner_color.getSelectedItem().toString());
            keywor.put("productImgUrl", uploadedImageName);
            keywor.put("price", edt_product_final_price.getText().toString().trim());
            keywor.put("availableQty", product_available_quantity);
            JSONObject businessDetails = new JSONObject();
            businessDetails.put("sizeTypeId", productSizeModel.getSizeTypeId());
            keywor.put("productSize",businessDetails);
            productKeywords.put(keywor);
            jsonObject.put("multiSizeWithPrice", productKeywords);


        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> walletDeductDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getupdateClothesProductDetails(headers, jsonObject.toString());
        walletDeductDetails.enqueue(new RestProcess<String>(UpdateClothesProductActivity.this, UpdateClothesProductActivity.this, UPDATE_CLOTHS_PRODUCT, true));

    }


    private void uploadImage() {



        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", mPhotoFile.getName(), RequestBody.create(MediaType.parse("image/*"), mPhotoFile));
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + mainLoginResponse.getToken());
        Call<String> productDetails = ServiceGenerator.createService(RestAPIInterface.class, BASE_URL).getProductUploadImage(headers, filePart);
        productDetails.enqueue(new RestProcess<String>(UpdateClothesProductActivity.this, UpdateClothesProductActivity.this, GETPROSUCTUPLOADIMAGE, true));
    }


    private boolean valid() {



        if (mPhotoFile == null) {

            Toast.makeText(UpdateClothesProductActivity.this, "Please select  product image", Toast.LENGTH_SHORT).show();
            return false;
        }


        if (edt_product_final_price.getText().toString().trim().equals("")) {
            Toast.makeText(UpdateClothesProductActivity.this, "Please enter price", Toast.LENGTH_SHORT).show();
            return false;
        }


        product_available_quantity = edt_product_available_quantity.getText().toString().trim();


       /* if (productSubCategory == null) {
            Toast.makeText(AddProductActivity.this, "Please select Sub Category", Toast.LENGTH_SHORT).show();
            return false;
        }*/


        if (product_available_quantity.equals("")) {
            Toast.makeText(UpdateClothesProductActivity.this, "Please enter product available quantity", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    @Override
    public void onFailure(Call call, Throwable t, int serviceMode) {
        switch (serviceMode) {
            case UPDATE_CLOTHS_PRODUCT:
                break;

            case GETPROSUCTUPLOADSUBIMAGE:
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


            case UPDATE_CLOTHS_PRODUCT:

                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {

                        Toast.makeText(UpdateClothesProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateClothesProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case GETPROSUCTUPLOADIMAGE:
                try {
                    JSONObject jsonObject = new JSONObject(ApiResponse);
                    if (jsonObject.getString("status").equals("1")) {
                        uploadedImageName = jsonObject.getString("result");

                        addProduct();
                    } else {
                        Toast.makeText(UpdateClothesProductActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

            finish();

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spiner_single_size:
                if (product_size_list.size() > 0) {
                    productSizeModel = product_size_list.get(position);
                    if (productSizeModel.getSizeTypeName().equals("Select size")) {
                        productSizeModel = null;
                    }
                }
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {


            switch (requestCode) {

                case 1:
                    try {
                        mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(UpdateClothesProductActivity.this)
                            .load(mPhotoFile)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product);
                    break;

                case 2:

                    Uri selectedImage = data.getData();
                    try {
                        mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Glide.with(UpdateClothesProductActivity.this)
                            .load(mPhotoFile)
                            .apply(new RequestOptions().centerCrop()

                                    .placeholder(R.mipmap.place_holder))
                            .into(img_product);
                    break;


            }

        }

    }
}

