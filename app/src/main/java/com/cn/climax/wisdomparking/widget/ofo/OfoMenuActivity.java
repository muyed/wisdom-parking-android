package com.cn.climax.wisdomparking.widget.ofo;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.base.activity.BaseActivity;
import com.cn.climax.wisdomparking.ui.PeterMainActivity;
import com.cn.climax.wisdomparking.ui.main.community.NearbySearchActivity;
import com.cn.climax.wisdomparking.widget.numberkeyboard.OfoKeyboardView;
import com.cn.climax.wisdomparking.widget.ofo.drawable.MenuBrawable;
import com.cn.climax.wisdomparking.widget.ofo.view.OfoContentLayout;
import com.cn.climax.wisdomparking.widget.ofo.view.OfoMenuLayout;

import butterknife.BindView;

public class OfoMenuActivity extends BaseActivity {

    //最外层的view，用来管理title和content的动画
    protected OfoMenuLayout ofoMenuLayout;
    protected OfoMenuLayout ofoMenuKeyBoardLayout;
    //content中列表view，主要是控制自己的动画
    protected OfoContentLayout ofoContentLayout;
    protected OfoContentLayout ofoContentKeyboardLayout;
    protected FrameLayout menu;
    protected FrameLayout menuKeyboard;
    protected ImageView ivSkip2Pcenter;
    protected LinearLayout llSkip2Publish;
    protected ImageView ivSkip2MessageList;

    protected int count;
    protected boolean isOpenPCenter = false; //是否点击打开个人设置界面 默认未打开
    protected boolean isOpenInputCenter = false; //是否点击打开输入界面 默认未打开

    protected int getType() {
        return MenuBrawable.NONE;
    }

    protected int getKeyboardType() {
        return MenuBrawable.CONVEX;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_peter_main_nav;
    }

    @Override
    protected void initUiAndListener(Bundle savedInstanceState) {
    }

    @SuppressLint("InlinedApi")
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
        llSkip2Publish = ((LinearLayout) findViewById(R.id.llSkip2Publish));
        llSkip2Publish.setVisibility(View.VISIBLE);
        ivSkip2MessageList = ((ImageView) findViewById(R.id.ivSkip2MessageList));
        ivSkip2MessageList.setOnClickListener(new ForbidQuickClickListener() {  //跳转搜索
            @Override
            protected void forbidClick(View view) {
                startActivity(new Intent(OfoMenuActivity.this, NearbySearchActivity.class));
            }
        });
        
        ivSkip2Pcenter = ((ImageView) findViewById(R.id.ivSkip2Pcenter));
        ofoMenuLayout = ((OfoMenuLayout) findViewById(R.id.ofo_menu));
        ofoContentLayout = ((OfoContentLayout) findViewById(R.id.ofo_content));
        menu = (FrameLayout) findViewById(R.id.menu_content);
        ofoMenuKeyBoardLayout = ((OfoMenuLayout) findViewById(R.id.ofo_menu_keyboard));
        ofoContentKeyboardLayout = ((OfoContentLayout) findViewById(R.id.ofo_content_keyboard));
        menuKeyboard = (FrameLayout) findViewById(R.id.menu_content_keyboard);
        
        final MenuBrawable menuBrawable = new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.mipmap.default_avatar_img), OfoMenuActivity.this, menu, getType(), R.color.color_f5f5f5);
        final MenuBrawable menuKeyboardBrawable = new MenuBrawable(BitmapFactory.decodeResource(getResources(), R.drawable.transparent), OfoMenuActivity.this, menuKeyboard, getKeyboardType(), R.color.white);
        menu.setBackground(menuBrawable);
        menuKeyboard.setBackground(menuKeyboardBrawable);
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
                isOpenPCenter = false;
                ofoMenuLayout.close();

                llSkip2Publish.setClickable(true);
                ivSkip2MessageList.setClickable(true);
            }
        });

        //关闭menu
        findViewById(R.id.close_keyboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenInputCenter = false;
                ofoMenuKeyBoardLayout.closeKeyboard();

                llSkip2Publish.setClickable(true);
                ivSkip2MessageList.setClickable(true);
            }
        });

        ivSkip2Pcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOpenPCenter = true;
                //启动menu
                ofoMenuLayout.setVisibility(View.VISIBLE);
                ofoMenuLayout.open();

                llSkip2Publish.setClickable(false);
                ivSkip2MessageList.setClickable(false);
            }
        });
        //menu的监听
        ofoMenuLayout.setOfoMenuStatusListener(new OfoMenuLayout.OfoMenuStatusListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
            }
        });
        //给menu设置content部分
        ofoMenuLayout.setOfoContentLayout(ofoContentLayout);

        //menu的监听
        ofoMenuKeyBoardLayout.setOfoMenuStatusListener(new OfoMenuLayout.OfoMenuStatusListener() {
            @Override
            public void onOpen() {

            }

            @Override
            public void onClose() {
            }
        });
        //给menu设置content部分
        ofoMenuKeyBoardLayout.setOfoContentKeyboardLayout(ofoContentKeyboardLayout);
    }

    @Override
    public void onBackPressed() {
        if (ofoMenuLayout.isOpen()) {
            ofoMenuLayout.close();
            llSkip2Publish.setClickable(true);
            ivSkip2MessageList.setClickable(true);
            return;
        }
        if (ofoMenuKeyBoardLayout.isOpen()) {
            llSkip2Publish.setClickable(true);
            ivSkip2MessageList.setClickable(true);
            ofoMenuKeyBoardLayout.closeKeyboard();
            return;
        }
        super.onBackPressed();
    }

    public void openKeyboard() {
        //启动menu
        ofoMenuKeyBoardLayout.setVisibility(View.VISIBLE);
        ofoMenuKeyBoardLayout.openKeyboard();

        llSkip2Publish.setClickable(false);
        ivSkip2MessageList.setClickable(false);
    }
}

