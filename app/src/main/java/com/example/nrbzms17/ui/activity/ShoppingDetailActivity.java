package com.example.nrbzms17.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.data.model.PurchasingBean;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private static final int CROP_PHOTO = 2;
    private static final int REQUEST_CODE_PICK_IMAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private File output;
    private Uri imageUri;

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

    @BindView(R.id.pur_next_craft)
    TextView pur_next_craft;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        ButterKnife.bind(this);
        imageView = findViewById(R.id.imageView);
        initview();
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
    }

    /**
     * 拍照
     */
    void takePhoto() {
        /**
         * 最后一个参数是文件夹的名称，可以随便起
         */
        File file = new File(Environment.getExternalStorageDirectory(), "拍照");
        if (!file.exists()) {
            file.mkdir();
        }
        /**
         * 这里将时间作为不同照片的名称
         */
        output = new File(file, System.currentTimeMillis() + ".jpg");

        /**
         * 如果该文件夹已经存在，则删除它，否则创建一个
         */
        try {
            if (output.exists()) {
                output.delete();
            }
            output.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * 隐式打开拍照的Activity，并且传入CROP_PHOTO常量作为拍照结束后回调的标志
         */
        imageUri = Uri.fromFile(output);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CROP_PHOTO);

    }

    /**
     * 从相册选取图片
     */
    void choosePhoto() {
        /**
         * 打开选择图片的界面
         */
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            /**
             * 拍照的请求标志
             */
            case CROP_PHOTO:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri就是照片文件夹对应的uri
                         */
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        imageView.setMaxWidth(800);
                        imageView.setMaxHeight(1000);
                        imageView.setImageBitmap(bit);
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

                    } catch (Exception e) {
                        Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("tag", "失败");
                }

                break;
            /**
             * 从相册中选取图片的请求标志
             */

            case REQUEST_CODE_PICK_IMAGE:
                if (res == RESULT_OK) {
                    try {
                        /**
                         * 该uri是上一个Activity返回的
                         */
                        Uri uri = data.getData();
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        imageView.setMaxWidth(800);
                        imageView.setMaxHeight(1000);
                        imageView.setImageBitmap(bit);
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("tag", e.getMessage());
                        Toast.makeText(this, "程序崩溃", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("liang", "失败");
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhoto();
            } else {
                // Permission Denied
                Toast.makeText(ShoppingDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                // Permission Denied
                Toast.makeText(ShoppingDetailActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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

    //选择
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takePhoto:
                if (ContextCompat.checkSelfPermission(ShoppingDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShoppingDetailActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                } else {
                    takePhoto();
                }
                break;
            case R.id.choosePhoto:
                if (ContextCompat.checkSelfPermission(ShoppingDetailActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ShoppingDetailActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                } else {
                    choosePhoto();
                }
                break;
        }
        dialog.dismiss();
    }


}
