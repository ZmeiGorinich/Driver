package com.romarinichgmail.driver.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romarinichgmail.driver.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOnlineOrder extends Fragment {


    public FragmentOnlineOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_order, container, false);
    }

}
