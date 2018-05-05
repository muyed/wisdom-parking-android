package com.cn.climax.wisdomparking.widget.bottomdialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.cn.climax.i_carlib.okgo.app.ForbidQuickClickListener;
import com.cn.climax.wisdomparking.R;
import com.cn.climax.wisdomparking.data.response.NearbyParkingMineBean;
import com.cn.climax.wisdomparking.util.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BottomDialog {

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;
    public static final int VERTICAL = OrientationHelper.VERTICAL;

    public static final int LINEAR = 0;
    public static final int GRID = 1;

    private CustomDialog customDialog;

    public BottomDialog(Context context) {
        customDialog = new CustomDialog(context);
    }

    public BottomDialog title(String title) {
        customDialog.title(title);
        return this;
    }

    public BottomDialog title(int title) {
        customDialog.title(title);
        return this;
    }

    public BottomDialog background(int res) {
        customDialog.background(res);
        return this;
    }

    public BottomDialog inflateMenu(int menu, OnItemClickListener onItemClickListener) {
        customDialog.inflateMenu(menu, onItemClickListener);
        return this;
    }

    public BottomDialog layout(int layout) {
        customDialog.layout(layout);
        return this;
    }

    public BottomDialog nav(OnSkip2NavigationListener listener) {
        customDialog.nav(listener);
        return this;
    }

    public BottomDialog match(OnSkip2MatchListener listener) {
        customDialog.match(listener);
        return this;
    }

    public BottomDialog setData(AMapLocation myLocation, NearbyParkingMineBean parkingMineBean) {
        customDialog.setData(myLocation, parkingMineBean);
        return this;
    }

    public BottomDialog orientation(int orientation) {
        customDialog.orientation(orientation);
        return this;
    }

    public BottomDialog addItems(List<Item> items, OnItemClickListener onItemClickListener) {
        customDialog.addItems(items, onItemClickListener);
        return this;
    }

    /**
     * @deprecated
     */
    public BottomDialog itemClick(OnItemClickListener listener) {
        customDialog.setItemClick(listener);
        return this;
    }

    public void show() {
        customDialog.show();
    }

    private final class CustomDialog extends Dialog {
        private LinearLayout background;
        private LinearLayout container;
        private TextView titleView;

        private TextView tvNavStartAddr;
        private TextView tvNavStartDate;
        private TextView tvNavStartWeek;
        private TextView tvNavEndAddr;
        private TextView tvNavEndDate;
        private TextView tvNavEndWeek;
        private TextView tvUnitPrice;
        private TextView tvNavCurrentParkingSpace;
        private TextView tvSkip2Match;
        private ImageView ivNav2DestAddr;

        private DialogAdapter adapter;

        private OnSkip2NavigationListener listener;
        private OnSkip2MatchListener matchListener;

        private int padding;
        private int topPadding;
        private int leftPadding;
        private int topIcon;
        private int leftIcon;

        private int orientation;
        private int layout;

        CustomDialog(Context context) {
            super(context, R.style.BottomDialog);
            init();
        }

        private void init() {
            padding = getContext().getResources().getDimensionPixelSize(R.dimen.app_normal_margin);
            topPadding = getContext().getResources().getDimensionPixelSize(R.dimen.app_tiny_margin);
            leftPadding = getContext().getResources().getDimensionPixelSize(R.dimen.app_normal_margin);
            topIcon = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_dialog_top_icon);
            leftIcon = getContext().getResources().getDimensionPixelSize(R.dimen.bottom_dialog_left_icon);

            setContentView(R.layout.bottom_dialog);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

            background = (LinearLayout) findViewById(R.id.background);
            titleView = (TextView) findViewById(R.id.title);
            container = (LinearLayout) findViewById(R.id.container);

            tvNavStartAddr = (TextView) findViewById(R.id.tvNavStartAddr);
            tvNavStartDate = (TextView) findViewById(R.id.tvNavStartDate);
            tvNavStartWeek = (TextView) findViewById(R.id.tvNavStartWeek);
            tvNavEndAddr = (TextView) findViewById(R.id.tvNavEndAddr);
            tvNavEndDate = (TextView) findViewById(R.id.tvNavEndDate);
            tvNavEndWeek = (TextView) findViewById(R.id.tvNavEndWeek);
            tvUnitPrice = (TextView) findViewById(R.id.tvUnitPrice);
            tvNavCurrentParkingSpace = (TextView) findViewById(R.id.tvNavCurrentParkingSpace);
            tvSkip2Match = (TextView) findViewById(R.id.tvSkip2Match);
            ivNav2DestAddr = (ImageView) findViewById(R.id.ivNav2DestAddr);

            ivNav2DestAddr.setOnClickListener(new ForbidQuickClickListener() {
                @Override
                protected void forbidClick(View view) {
                    if (listener != null)
                        listener.nav();
                    dismiss();
                }
            });

            tvSkip2Match.setOnClickListener(new ForbidQuickClickListener() {
                @Override
                protected void forbidClick(View view) {
                    if (matchListener != null)
                        matchListener.match();
                    dismiss();
                }
            });
        }

        void addItems(List<Item> items, OnItemClickListener onItemClickListener) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RecyclerView.LayoutManager manager;

            adapter = new DialogAdapter(items, layout, orientation);
            adapter.setItemClick(onItemClickListener);

            if (layout == LINEAR)
                manager = new LinearLayoutManager(getContext(), orientation, false);
            else if (layout == GRID)
                manager = new GridLayoutManager(getContext(), 5, orientation, false);
            else manager = new LinearLayoutManager(getContext(), orientation, false);

            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutParams(params);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);

            container.addView(recyclerView);
        }

        public void title(int title) {
            title(getContext().getString(title));
        }

        public void title(String title) {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        }

        public void layout(int layout) {
            this.layout = layout;
            if (adapter != null) adapter.setLayout(layout);
        }

        public void nav(OnSkip2NavigationListener listener) {
            this.listener = listener;
        }

        public void match(OnSkip2MatchListener listener) {
            this.matchListener = listener;
        }

        @SuppressLint("SetTextI18n")
        public void setData(AMapLocation myLocation, NearbyParkingMineBean parkingMineBean) {
            tvNavStartAddr.setText(myLocation.getAddress());
            tvNavStartDate.setText(TimeUtils.getCurrentDate(parkingMineBean.getStartTime()));
            tvNavStartWeek.setText(TimeUtils.getDateAndDayOfWeek(TimeUtils.getTimeStamp(parkingMineBean.getStartTime(), "yyyy-MM-dd HH:mm:ss")) + " " + TimeUtils.getCurrentHour(parkingMineBean.getStartTime()));

            tvNavEndAddr.setText(parkingMineBean.getAddr());
            tvNavEndDate.setText(TimeUtils.getCurrentDate(parkingMineBean.getStopTime()));
            tvNavEndWeek.setText(TimeUtils.getDateAndDayOfWeek(TimeUtils.getTimeStamp(parkingMineBean.getStopTime(), "yyyy-MM-dd HH:mm:ss")) + " " + TimeUtils.getCurrentHour(parkingMineBean.getStopTime()));

            tvUnitPrice.setText(parkingMineBean.getPrice() + "元/小时");
            tvNavCurrentParkingSpace.setText("车位 " + parkingMineBean.getCarportNum());
        }

        public void orientation(int orientation) {
            this.orientation = orientation;
            if (adapter != null) adapter.setOrientation(orientation);
        }

        public void background(int res) {
            background.setBackgroundResource(res);
        }

        void inflateMenu(int menu, OnItemClickListener onItemClickListener) {
            MenuInflater menuInflater = new SupportMenuInflater(getContext());
            MenuBuilder menuBuilder = new MenuBuilder(getContext());
            menuInflater.inflate(menu, menuBuilder);
            List<Item> items = new ArrayList<>();
            for (int i = 0; i < menuBuilder.size(); i++) {
                MenuItem menuItem = menuBuilder.getItem(i);
                items.add(new Item(menuItem.getItemId(), menuItem.getTitle().toString(), menuItem.getIcon()));
            }
            addItems(items, onItemClickListener);
        }

        void setItemClick(OnItemClickListener onItemClickListener) {
            adapter.setItemClick(onItemClickListener);
        }

        /**
         * recycler view adapter, provide HORIZONTAL and VERTICAL item style
         */
        private class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            private List<Item> mItems = Collections.emptyList();
            private OnItemClickListener itemClickListener;

            private int orientation;
            private int layout;

            DialogAdapter(List<Item> mItems, int layout, int orientation) {
                setList(mItems);
                this.layout = layout;
                this.orientation = orientation;
            }

            private void setList(List<Item> items) {
                mItems = items == null ? new ArrayList<Item>() : items;
            }

            void setItemClick(OnItemClickListener onItemClickListener) {
                this.itemClickListener = onItemClickListener;
            }

            public void setOrientation(int orientation) {
                this.orientation = orientation;
                notifyDataSetChanged();
            }

            public void setLayout(int layout) {
                this.layout = layout;
                notifyDataSetChanged();
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                if (layout == GRID)
                    return new TopHolder(new LinearLayout(parent.getContext()));
                else if (orientation == HORIZONTAL)
                    return new TopHolder(new LinearLayout(parent.getContext()));
                else return new LeftHolder(new LinearLayout(parent.getContext()));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                final Item item = mItems.get(position);

                TopHolder topHolder;
                LeftHolder leftHolder;

                if (layout == GRID) {
                    topHolder = (TopHolder) holder;

                    topHolder.item.setText(item.getTitle());
                    topHolder.item.setCompoundDrawablesWithIntrinsicBounds(null, topHolder.icon(item.getIcon()), null, null);
                    topHolder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (itemClickListener != null) itemClickListener.click(item);
                        }
                    });
                } else if (orientation == HORIZONTAL) {
                    topHolder = (TopHolder) holder;

                    topHolder.item.setText(item.getTitle());
                    topHolder.item.setCompoundDrawablesWithIntrinsicBounds(null, topHolder.icon(item.getIcon()), null, null);
                    topHolder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (itemClickListener != null) itemClickListener.click(item);
                        }
                    });
                } else {
                    leftHolder = (LeftHolder) holder;

                    leftHolder.item.setText(item.getTitle());
                    leftHolder.item.setCompoundDrawablesWithIntrinsicBounds(leftHolder.icon(item.getIcon()), null, null, null);
                    leftHolder.item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (itemClickListener != null) itemClickListener.click(item);
                        }
                    });
                }
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }

            /**
             * horizontal item adapter
             */
            class TopHolder extends RecyclerView.ViewHolder {
                private TextView item;

                TopHolder(View view) {
                    super(view);

                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.width = Utils.getScreenWidth(getContext()) / 5;

                    item = new TextView(view.getContext());
                    item.setLayoutParams(params);
                    item.setMaxLines(1);
                    item.setEllipsize(TextUtils.TruncateAt.END);
                    item.setGravity(Gravity.CENTER);
                    item.setTextColor(ContextCompat.getColor(view.getContext(), R.color.gray_font_dark));
                    item.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.font_small));
                    item.setCompoundDrawablePadding(topPadding);
                    item.setPadding(0, padding, 0, padding);

                    TypedValue typedValue = new TypedValue();
                    view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                    item.setBackgroundResource(typedValue.resourceId);

                    ((LinearLayout) view).addView(item);
                }

                private Drawable icon(Drawable drawable) {
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        @SuppressWarnings("SuspiciousNameCombination") Drawable resizeIcon = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, topIcon, topIcon, true));
                        Drawable.ConstantState state = resizeIcon.getConstantState();
                        resizeIcon = DrawableCompat.wrap(state == null ? resizeIcon : state.newDrawable().mutate());
                        return resizeIcon;
                    }
                    return null;
                }
            }

            class LeftHolder extends RecyclerView.ViewHolder {
                private TextView item;

                LeftHolder(View view) {
                    super(view);

                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    view.setLayoutParams(params);
                    item = new TextView(view.getContext());
                    item.setLayoutParams(params);
                    item.setMaxLines(1);
                    item.setEllipsize(TextUtils.TruncateAt.END);
                    item.setGravity(Gravity.CENTER_VERTICAL);
                    item.setTextColor(ContextCompat.getColor(view.getContext(), R.color.black));
                    item.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.font_normal));
                    item.setCompoundDrawablePadding(leftPadding);
                    item.setPadding(padding, padding, padding, padding);

                    TypedValue typedValue = new TypedValue();
                    view.getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
                    item.setBackgroundResource(typedValue.resourceId);

                    ((LinearLayout) view).addView(item);
                }

                private Drawable icon(Drawable drawable) {
                    if (drawable != null) {
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        @SuppressWarnings("SuspiciousNameCombination") Drawable resizeIcon = new BitmapDrawable(getContext().getResources(), Bitmap.createScaledBitmap(bitmap, leftIcon, leftIcon, true));
                        Drawable.ConstantState state = resizeIcon.getConstantState();
                        resizeIcon = DrawableCompat.wrap(state == null ? resizeIcon : state.newDrawable().mutate());
                        return resizeIcon;
                    }
                    return null;
                }
            }
        }
    }

    public interface OnSkip2NavigationListener {
        void nav();
    }

    public interface OnSkip2MatchListener {
        void match();
    }

    private OnSkip2NavigationListener navigationListener;

    private OnSkip2MatchListener matchListener;

    public void setNavigationListener(OnSkip2NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public OnSkip2MatchListener getMatchListener() {
        return matchListener;
    }

    public void setMatchListener(OnSkip2MatchListener matchListener) {
        this.matchListener = matchListener;
    }

}
