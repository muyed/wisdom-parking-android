package com.cn.climax.wisdomparking.widget.ofo;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.widget.ofo.drawable.MenuBrawable;
import com.cn.climax.wisdomparking.widget.ofo.view.OfoContentLayout;
import com.cn.climax.wisdomparking.widget.ofo.view.OfoMenuLayout;

public class OfoMenuActivity extends BaseActivity {

    //最外层的view，用来管理title和content的动画
    OfoMenuLayout ofoMenuLayout;
    //contennt中列表view，主要是控制自己的动画
    OfoContentLayout ofoContentLayout;
    FrameLayout menu;
    RelativeLayout rlMainPage;
    ImageView ivSkip2Pcenter;
    int count;
//    protected int type = MenuBrawable.CONVEX;

    protected int getType() {
        return MenuBrawable.CONVEX;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_peter_main;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        rlMainPage = ((RelativeLayout) findViewById(R.id.rlMainPage));
        ofoMenuLayout = ((OfoMenuLayout) findViewById(R.id.ofo_menu));
        ivSkip2Pcenter = ((ImageView) findViewById(R.id.ivSkip2Pcenter));
        ofoContentLayout = ((OfoContentLayout) findViewById(R.id.ofo_content));
        menu = (FrameLayout) findViewById(R.id.menu_content);
        final MenuBrawable menuBrawable = new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.default_avatar_img), OfoMenuActivity.this, menu, getType());
        menu.setBackground(menuBrawable);

        menuBrawable.setOnBitmapClickListener(new MenuBrawable.OnBitmapClickListener() {
            @Override
            public void bitmapClick() {
                count++;
                if (count % 2 == 0) {
                    menuBrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.single));
                } else {
                    menuBrawable.setBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.timg));
                }

            }
        });
        //关闭menu
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ofoMenuLayout.close();
            }
        });

        ivSkip2Pcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动menu
                ofoMenuLayout.setVisibility(View.VISIBLE);
                ofoMenuLayout.open();
            }
        });
        //menu的监听
        ofoMenuLayout.setOfoMenuStatusListener(new OfoMenuLayout.OfoMenuStatusListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
//                startConcaveBtn.setVisibility(View.VISIBLE);
            }
        });
        //给menu设置content部分
        ofoMenuLayout.setOfoContentLayout(ofoContentLayout);
    }

    @Override
    public void onBackPressed() {
        if (ofoMenuLayout.isOpen()) {
            ofoMenuLayout.close();
            return;
        }
        super.onBackPressed();
    }
}