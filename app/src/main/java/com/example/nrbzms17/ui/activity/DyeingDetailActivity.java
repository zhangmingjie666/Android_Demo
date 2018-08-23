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
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.JSONUtils;
import com.example.nrbzms17.data.Api;
import com.example.nrbzms17.data.listener.OnNetRequest;
import com.example.nrbzms17.data.model.DateBean;
import com.example.nrbzms17.data.model.DateBeanResponse;
import com.example.nrbzms17.data.model.DyingBean;
import com.example.nrbzms17.data.model.PurchaseBean;
import com.example.nrbzms17.data.model.StraightBean;
import com.example.nrbzms17.data.model.StraightBeanResponse;
import com.example.nrbzms17.ui.adapter.SpinnerStraightAdapter;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DyeingDetailActivity extends AppCompatActivity implements View.OnClickListener {

    //拍照
    private ImageView imageView;
    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int NEXT_CRAFT_BACK = 3;

    public static final int FACTORY_BACK = 4;

    public static final int EMPLOYEE_BACK = 5;

    public static final int COLOR_BACK = 6;

    public static final int LABEL_BACK = 7;

    public static final int PURING_CRAFT_BACK = 8;

    public static final int NOW_CRAFT_BACK = 9;

    public static final int DEPOT_BACK  = 10;

    private Uri imageUri;

    static String imageBase64;

    //弹窗
    private View inflate;
    private TextView choosePhoto;
    private TextView takePhoto;
    private Dialog dialog;



    @BindView(R.id.dye_customcode)
    TextView dye_customcode;

    @BindView(R.id.dye_billdate)
    TextView dye_billdate;

    @BindView(R.id.dye_material)
    TextView dye_material;

    @BindView(R.id.dye_company)
    TextView dye_company;

    @BindView(R.id.dye_rquantity)
    TextView dye_rquantity;

    @BindView(R.id.dye_quantity)
    TextView dye_quantity;

    @BindView(R.id.dye_factory)
    TextView dye_factory;

    @BindView(R.id.dye_next_craft)
    TextView dye_next_craft;

    @BindView(R.id.dye_color)
    TextView dye_color;

    @BindView(R.id.dye_craft)
    TextView dye_craft;

    @BindView(R.id.dye_label)
    TextView dye_label;

    @BindView(R.id.dye_lot)
    TextView dye_lot;

    @BindView(R.id.dye_employee)
    TextView dye_employee;

    @BindView(R.id.dye_current_craft)
    TextView dye_current_craft;

    @BindView(R.id.dye_depot)
    TextView dye_depot;

    @BindView(R.id.depot_line)
    View depot_line;

    @BindView(R.id.label_line)
    View label_line;

    @BindView(R.id.factory_line)
    View factory_line;

    @BindView(R.id.craft_line)
    View craft_line;

    @BindView(R.id.dye_task_code)
    TextView dye_task_code;

    @BindView(R.id.set_back)
    TextView set_back;




    private DyingBean.Data dying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dyeing_detail);
        ButterKnife.bind(this);
        initview();
        imageView = findViewById(R.id.imageView);
        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void initview(){
        //初始化数据
        dying = (DyingBean.Data) getIntent().getSerializableExtra(DyingBean.Data.class.getSimpleName());
        dye_customcode.setText(dying.customcode);
        dye_billdate.setText(dying.billdate);
        dye_company.setText(dying.company);
        dye_material.setText(dying.material);
        dye_quantity.setText(dying.quantity_str);
        dye_rquantity.setText(dying.receive_str);
        dye_factory.setText(dying.factory);
        dye_next_craft.setText(dying.next_craft);
        dye_color.setText(dying.color);
        dye_craft.setText(dying.craft);
        dye_label.setText(dying.label);
        dye_lot.setText(dying.lot);
        dye_depot.setText(dying.depot);
        dye_task_code.setText(dying.taskcode);
        final LinearLayout depot_layout=(LinearLayout)findViewById(R.id.depot_layout);
        final LinearLayout label_layout=(LinearLayout)findViewById(R.id.label_layout);

        final LinearLayout factory_layout=(LinearLayout)findViewById(R.id.factory_layout);
        final LinearLayout craft_layout=(LinearLayout)findViewById(R.id.craft_layout);

        //切换染厂和后整理厂
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_title);
        radioGroup.check(R.id.dying_select);//默认选中的RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (R.id.dying_select == checkedId) {
                    depot_layout.setVisibility(View.GONE);
                    label_layout.setVisibility(View.GONE);
                    depot_line.setVisibility(View.GONE);
                    label_line.setVisibility(View.GONE);
                    factory_layout.setVisibility(View.VISIBLE);
                    craft_layout.setVisibility(View.VISIBLE);
                    craft_line.setVisibility(View.VISIBLE);
                    factory_line.setVisibility(View.VISIBLE);
                }
                if (R.id.after_select == checkedId) {
                    craft_line.setVisibility(View.GONE);
                    factory_line.setVisibility(View.GONE);
                    factory_layout.setVisibility(View.GONE);
                    craft_layout.setVisibility(View.GONE);
                    depot_layout.setVisibility(View.VISIBLE);
                    label_layout.setVisibility(View.VISIBLE);
                    depot_line.setVisibility(View.VISIBLE);
                    label_line.setVisibility(View.VISIBLE);
                }
            }
        });

        //判断去向 1:入库
            depot_layout.setVisibility(View.GONE);
            label_layout.setVisibility(View.GONE);
            depot_line.setVisibility(View.GONE);
            label_line.setVisibility(View.GONE);




        //选择下步工艺
        dye_next_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DyeingDetailActivity.this, CraftActivity.class);
                startActivityForResult(intent,NEXT_CRAFT_BACK);
            }
        });

        //选择加工厂
        dye_factory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DyeingDetailActivity.this, FactoryActivity.class);
                startActivityForResult(intent,FACTORY_BACK);
            }
        });

        //选择员工
        dye_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,EmployeeActivity.class);
                startActivityForResult(intent,EMPLOYEE_BACK);
            }
        });

        //选择颜色
        dye_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,ColorActivity.class);
                startActivityForResult(intent,COLOR_BACK);
            }
        });

        //选择收货工艺
        dye_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,CraftActivity.class);
                startActivityForResult(intent,PURING_CRAFT_BACK);
            }
        });

        //选择当前工艺
        dye_current_craft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,CraftActivity.class);
                startActivityForResult(intent,NOW_CRAFT_BACK);
            }
        });

        //选择标签
        dye_label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,LabelActivity.class);
                startActivityForResult(intent,LABEL_BACK);
            }
        });

        //选择仓库
        dye_depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(DyeingDetailActivity.this,DepotActivity.class);
                startActivityForResult(intent,DEPOT_BACK);
            }
        });
    }

    //弹窗
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

                    imageUri = FileProvider.getUriForFile(DyeingDetailActivity.this, "com.example.nrbzms17.ui.activity.fileprovider", outputImage);

                }

                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(DyeingDetailActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(DyeingDetailActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
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
                if (ContextCompat.checkSelfPermission(DyeingDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DyeingDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                break;
        }
        dialog.dismiss();
    }
    //打开相册
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
                                AlertDialog.Builder builder = new AlertDialog.Builder(DyeingDetailActivity.this);
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

            //返回下步工艺
            case NEXT_CRAFT_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_next_craft.setText(returnedData);
                }
                break;

            //返回加工厂
            case FACTORY_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_factory.setText(returnedData);
                }
                break;

            //返回收货员
            case EMPLOYEE_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_employee.setText(returnedData);
                }
                break;

            //返回颜色
            case COLOR_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_color.setText(returnedData);
                }
                break;

            //返回收货工艺
            case PURING_CRAFT_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_craft.setText(returnedData);
                }
                break;

            //返回当前工艺
            case NOW_CRAFT_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_current_craft.setText(returnedData);
                }
                break;

            //返回标签
            case LABEL_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_label.setText(returnedData);
                }
                break;

            //返回仓库
            case DEPOT_BACK:
                if(resultCode == RESULT_OK){
                    String returnedData =  data.getStringExtra("name");
                    dye_depot.setText(returnedData);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(DyeingDetailActivity.this);
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

}
