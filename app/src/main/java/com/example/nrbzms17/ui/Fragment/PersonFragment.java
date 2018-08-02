package com.example.nrbzms17.ui.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.nrbzms17.R;



/**
 * @author MJ@ZHANG
 * @package: com.example.nrbzms17.ui.Fragment
 * @filename PersonFragment
 * @date on 2018/8/1 13:49
 * @descibe TODO
 * @email 865193491@qq.com
 */
public class PersonFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, null);
        return view;
    }
}
