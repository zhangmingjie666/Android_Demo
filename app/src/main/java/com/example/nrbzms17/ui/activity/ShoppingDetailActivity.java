package com.example.nrbzms17.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.Utils.UIHelper;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.CraftBean;
import com.example.nrbzms17.data.model.PurchasingBean;
import com.example.nrbzms17.data.model.ResponseBean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.activity
 * @filename ShoppingDetailActivity
 * @date on 2018/8/16 15:15
 * @descibe TODO
 * @email zhangmingjie@huansi.net
 */
public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {
    //拍照
    private ImageView imageView;
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CRAFT_BACK = 3;

    public static final int FACTORY_BACK = 4;
    private Uri imageUri;

    static String imageBase64;
    //弹窗
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;


    //业务
    private PurchasingBean.Data purchase;
    @BindView(R.id.pur_customcode)
    TextView pur_customcode;

    @BindView(R.id.pur_billdate)
    TextView pur_billdate;

    @BindView(R.id.pur_company)
    TextView pur_company;


    @BindView(R.id.pur_material)
    TextView pur_material;


    @BindView(R.id.pur_quantity)
    TextView pur_quantity;

    @BindView(R.id.pur_sup_material_code)
    TextView pur_sup_material_code;

    @BindView(R.id.pur_deliverdate)
    TextView pur_deliverdate;

    @BindView(R.id.pur_factory)
    TextView pur_factory;

    @BindView(R.id.pur_rquantity)
    TextView pur_rquantity;

    @BindView(R.id.pur_taskcode)
    TextView pur_taskcode;

    @BindView(R.id.pur_next_craft)
    TextView pur_next_craft;

    @BindView(R.id.pur_volume)
    TextView pur_volume;

    @BindView(R.id.pur_shquantity)
    TextView pur_shquantity;

    @BindView(R.id.check_Submit)
    Button check_Submit;

    @BindView(R.id.set_back)
    Button set_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        ButterKnife.bind(this);
        imageView = findViewById(R.id.imageView);
        initview();

        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initview() {
        purchase = (PurchasingBean.Data) getIntent().getSerializableExtra(PurchasingBean.Data.class.getSimpleName());
        pur_customcode.setText(purchase.customcode);
        pur_billdate.setText(purchase.billdate);
        pur_company.setText(purchase.company);
        pur_material.setText(purchase.material);
        pur_sup_material_code.setText(purchase.sup_material_code);
        pur_factory.setText(purchase.factory);
        pur_deliverdate.setText(purchase.deliverydate);
        pur_quantity.setText(purchase.quantity);
        pur_rquantity.setText(purchase.rquantity);
        pur_next_craft.setText(purchase.next_craft);
        check_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPurchasingDetail();
            }
        });

        //选择下步工艺
        pur_next_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingDetailActivity.this, CraftActivity.class);
                startActivityForResult(intent,CRAFT_BACK);
            }
        });

        //选择加工厂
        pur_factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShoppingDetailActivity.this, FactoryActivity.class);
                startActivityForResult(intent,FACTORY_BACK);
            }
        });
    }

    public void show(View view) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bottom, null);
        //初始化控件
        choosePhoto = (TextView) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (TextView) inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    //弹窗选择
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
//                outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT < 24) {
                    imageUri = Uri.fromFile(outputImage);


                } else {

                    imageUri = FileProvider.getUriForFile(ShoppingDetailActivity.this, "com.example.nrbzms17.ui.activity.fileprovider", outputImage);

                }

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(ShoppingDetailActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ShoppingDetailActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                        return;
                    } else {

                        // 启动相机程序
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(intent, TAKE_PHOTO);
                    }
                }


                // 启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);

                break;
            case R.id.choosePhoto:
                if (ContextCompat.checkSelfPermission(ShoppingDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShoppingDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
        }
        dialog.dismiss();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                        imageView.setMaxWidth(800);
                        imageView.setMaxHeight(1000);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingDetailActivity.this);
                                builder.setMessage("确认删除吗?");
                                builder.setTitle("提示");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        // 删除照片
                                        imageView.setImageDrawable(null);
                                    }
                                });
                                builder.setNegativeButton("取消", null);
                                builder.create().show();
                            }
                        });

//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.PNG, 5, baos);

//                        byte[] bytes = baos.toByteArray();

                        //base64 encode
//                        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
//                        imageBase64 = new String(encode);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            //返回工艺
            case CRAFT_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    pur_next_craft.setText(returnedData);

                }
                break;

            //返回加工厂
            case FACTORY_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    pur_factory.setText(returnedData);

                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();

        }
        displayImage(imagePath); // 根据图片路径显示图片
        Log.d("zhangmingjie", imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);

        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }

        return path;

    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
            imageView.setMaxWidth(800);
            imageView.setMaxHeight(1000);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingDetailActivity.this);
                    builder.setMessage("确认删除吗?");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            // 删除照片
                            imageView.setImageDrawable(null);
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.create().show();
                }
            });
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    //新增并审核
    public void addPurchasingDetail() {


        String sup_material_code = pur_sup_material_code.getText().toString().trim();
        String taskcode = pur_taskcode.getText().toString().trim();
        String volume = pur_volume.getText().toString().trim();
        String quantity = pur_shquantity.getText().toString().trim();


        Api api = new Api(this, new OnNetRequest(this, true, "请稍等...") {
            @Override
            public void onSuccess(String msg) {
                ResponseBean responseBean = JSONUtils.fromJson(msg, ResponseBean.class);
                if (responseBean != null && responseBean.status) {
                    UIHelper.showShortToast(ShoppingDetailActivity.this, "新增成功");
                    finish();
                } else {
                    UIHelper.showShortToast(ShoppingDetailActivity.this, responseBean.result);
                }
            }

            @Override
            public void onFail() {
            }
        });
        api.addPurchasingDetail(purchase.id,pur_next_craft.getText().toString().trim(), pur_factory.getText().toString().trim(), volume, quantity, taskcode, sup_material_code);
    }


    @Override
    protected void onResume() {

        super.onResume();

    }

}
