package com.example.nrbzms17.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.Utils.APKVersionCodeUtils;
import com.example.nrbzms17.ui.activity.SettingActivity;
import com.example.nrbzms17.ui.activity.UpdateActivity;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.Fragment
 * @filename SettingFragment·
 * @date on 2018/8/1 13:47
 * @descibe TODO
 * @email 865193491@qq.com
 */
public class SettingFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        TextView address = view.findViewById(R.id.address);
        TextView updateOnline = view.findViewById(R.id.updateOnline);

        //开启地址
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        //开启升级
        updateOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
