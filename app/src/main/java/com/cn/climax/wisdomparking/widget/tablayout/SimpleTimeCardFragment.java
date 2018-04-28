package com.cn.climax.wisdomparking.widget.tablayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cn.climax.i_carlib.uiframework.citypicker.listener.OnCityWheelComfirmListener;
import com.cn.climax.i_carlib.uiframework.citypicker.ppw.CityWheelPickerPopupWindow;
import com.cn.climax.wisdomparking.R;


@SuppressLint("ValidFragment")
public class SimpleTimeCardFragment extends Fragment {
    private String mTitle;

    public static SimpleTimeCardFragment getInstance(String title) {
        SimpleTimeCardFragment sf = new SimpleTimeCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_time_tab_card, null);
        LinearLayout selectedAddr = (LinearLayout) v.findViewById(R.id.llSelectedAddress);
        final CityWheelPickerPopupWindow wheelPickerPopupWindow = new CityWheelPickerPopupWindow(getActivity());
        wheelPickerPopupWindow.setListener(new OnCityWheelComfirmListener() {
            @Override
            public void onSelected(String Province, String City, String District, String PostCode) {
                Toast.makeText(getContext(), Province + City + District, Toast.LENGTH_LONG).show();
            }
        });
        selectedAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wheelPickerPopupWindow.show();
            }
        });
        return v;
    }
}