package com.example.nrbzms17.ui.Fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nrbzms17.R;
import com.example.nrbzms17.ui.activity.MainActivity;
import com.example.nrbzms17.ui.activity.SettingActivity;

/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.Fragment
 * @filename FindFragment
 * @date on 2018/7/31 14:22
 * @descibe TODO
 * @email 865193491@qq.com
 */
public class FindFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,null);
//         ScanActivity activity = (ScanActivity) getActivity();
        TextView demo =view.findViewById(R.id.demo);
        demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
