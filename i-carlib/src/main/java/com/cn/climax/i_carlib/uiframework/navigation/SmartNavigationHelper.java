package com.cn.climax.i_carlib.uiframework.navigation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;

import com.cn.climax.i_carlib.R;

/**
 * author：leo on 2017/3/3 15:37
 * email： leocheung4ever@gmail.com
 * description: this is utils class specific for this library, most the common code goes here
 * what & why is modified:
 */

public class SmartNavigationHelper {


    private SmartNavigationHelper() {
    }

    /**
     * Used to get Measurements for MODE_FIXED
     *
     * @param context     to fetch measurements
     * @param screenWidth total screen width
     * @param noOfTabs    no of bottom bar tabs
     * @param scrollable  is bottom bar scrollable
     * @return width of each tab
     */
    public static int[] getMeasurementsForFixedMode(Context context, int screenWidth, int noOfTabs, boolean scrollable) {

        int[] result = new int[2];

        int minWidth = (int) context.getResources().getDimension(R.dimen.fixed_min_width_small_views);
        int maxWidth = (int) context.getResources().getDimension(R.dimen.fixed_min_width);

        int itemWidth = screenWidth / noOfTabs;

        if (itemWidth < minWidth && scrollable) {
            itemWidth = (int) context.getResources().getDimension(R.dimen.fixed_min_width);
        } else if (itemWidth > maxWidth) {
            itemWidth = maxWidth;
        }

        result[0] = itemWidth;

        return result;
    }

    /**
     * Used to get Measurements for MODE_SHIFTING
     *
     * @param context     to fetch measurements
     * @param screenWidth total screen width
     * @param noOfTabs    no of bottom bar tabs
     * @param scrollable  is bottom bar scrollable
     * @return min and max width of each tab
     */
    public static int[] getMeasurementsForShiftingMode(Context context, int screenWidth, int noOfTabs, boolean scrollable) {

        int[] result = new int[2];

        int minWidth = (int) context.getResources().getDimension(R.dimen.shifting_min_width_inactive);
        int maxWidth = (int) context.getResources().getDimension(R.dimen.shifting_max_width_inactive);

        double minPossibleWidth = minWidth * (noOfTabs + 0.5);
        double maxPossibleWidth = maxWidth * (noOfTabs + 0.75);
        int itemWidth;
        int itemActiveWidth;

        if (screenWidth < minPossibleWidth) {
            if (scrollable) {
                itemWidth = minWidth;
                itemActiveWidth = (int) (minWidth * 1.5);
            } else {
                itemWidth = (int) (screenWidth / (noOfTabs + 0.5));
                itemActiveWidth = (int) (itemWidth * 1.5);
            }
        } else if (screenWidth > maxPossibleWidth) {
            itemWidth = maxWidth;
            itemActiveWidth = (int) (itemWidth * 1.75);
        } else {
            double minPossibleWidth1 = minWidth * (noOfTabs + 0.625);
            double minPossibleWidth2 = minWidth * (noOfTabs + 0.75);
            itemWidth = (int) (screenWidth / (noOfTabs + 0.5));
            itemActiveWidth = (int) (itemWidth * 1.5);
            if (screenWidth > minPossibleWidth1) {
                itemWidth = (int) (screenWidth / (noOfTabs + 0.625));
                itemActiveWidth = (int) (itemWidth * 1.625);
                if (screenWidth > minPossibleWidth2) {
                    itemWidth = (int) (screenWidth / (noOfTabs + 0.75));
                    itemActiveWidth = (int) (itemWidth * 1.75);
                }
            }
        }

        result[0] = itemWidth;
        result[1] = itemActiveWidth;

        return result;
    }

    /**
     * Used to get set data to the Tab views from navigation items
     *
     * @param smartNavigationItem holds all the data
     * @param smartNavigationTab  view to which data need to be set
     * @param smartNavigationBar  view which holds all the tabs
     */
    public static void bindTabWithData(SmartNavigationItem smartNavigationItem, SmartNavigationTab smartNavigationTab, SmartNavigationBar smartNavigationBar) {

        Context context = smartNavigationBar.getContext();

        smartNavigationTab.setLabel(smartNavigationItem.getTitle(context));
        smartNavigationTab.setIcon(smartNavigationItem.getIcon(context));

        int activeColor = smartNavigationItem.getActiveColor(context);
        int inActiveColor = smartNavigationItem.getInActiveColor(context);

        if (activeColor != -1) {
            smartNavigationTab.setActiveColor(activeColor);
        } else {
            smartNavigationTab.setActiveColor(smartNavigationBar.getActiveColor());
        }

        if (inActiveColor != -1) {
            smartNavigationTab.setInactiveColor(inActiveColor);
        } else {
            smartNavigationTab.setInactiveColor(smartNavigationBar.getInActiveColor());
        }

        if (smartNavigationItem.isInActiveIconAvailable()) {
            Drawable inactiveDrawable = smartNavigationItem.getInactiveIcon(context);
            if (inactiveDrawable != null) {
                smartNavigationTab.setInactiveIcon(inactiveDrawable);
            }
        }

        smartNavigationTab.setItemBackgroundColor(smartNavigationBar.getBackgroundColor());

        setBadgeForTab(smartNavigationItem.getBadgeItem(), smartNavigationTab);
    }

    /**
     * Used to set badge for given tab
     *
     * @param badgeItem          holds badge data
     * @param smartNavigationTab bottom navigation tab to which badge needs to be attached
     */
    private static void setBadgeForTab(BadgeItem badgeItem, SmartNavigationTab smartNavigationTab) {
        if (badgeItem != null) {

            Context context = smartNavigationTab.getContext();

            GradientDrawable shape = getBadgeDrawable(badgeItem, context);
            smartNavigationTab.badgeView.setBackgroundDrawable(shape);

            smartNavigationTab.setBadgeItem(badgeItem);
            badgeItem.setTextView(smartNavigationTab.badgeView);
            smartNavigationTab.badgeView.setVisibility(View.VISIBLE);

            smartNavigationTab.badgeView.setTextColor(badgeItem.getTextColor(context));
            smartNavigationTab.badgeView.setText(badgeItem.getText());


            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) smartNavigationTab.badgeView.getLayoutParams();
            layoutParams.gravity = badgeItem.getGravity();
            smartNavigationTab.badgeView.setLayoutParams(layoutParams);

            if (badgeItem.isHidden()) {
                // if hide is called before the initialisation of bottom-bar this will handle that
                // by hiding it.
                badgeItem.hide();
            }
        }
    }

    static GradientDrawable getBadgeDrawable(BadgeItem badgeItem, Context context) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadius(context.getResources().getDimensionPixelSize(R.dimen.badge_corner_radius));
        shape.setColor(badgeItem.getBackgroundColor(context));
        shape.setStroke(badgeItem.getBorderWidth(), badgeItem.getBorderColor(context));
        return shape;
    }

    /**
     * Used to set the ripple animation when a tab is selected
     *
     * @param clickedView       the view that is clicked (to get dimens where ripple starts)
     * @param backgroundView    temporary view to which final background color is set
     * @param bgOverlay         temporary view which is animated to get ripple effect
     * @param newColor          the new color i.e ripple color
     * @param animationDuration duration for which animation runs
     */
    public static void setBackgroundWithRipple(View clickedView, final View backgroundView,
                                               final View bgOverlay, final int newColor, int animationDuration) {
        int centerX = (int) (clickedView.getX() + (clickedView.getMeasuredWidth() / 2));
        int centerY = clickedView.getMeasuredHeight() / 2;
        int finalRadius = backgroundView.getWidth();

        backgroundView.clearAnimation();
        bgOverlay.clearAnimation();

        Animator circularReveal;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils
                    .createCircularReveal(bgOverlay, centerX, centerY, 0, finalRadius);
        } else {
            bgOverlay.setAlpha(0);
            circularReveal = ObjectAnimator.ofFloat(bgOverlay, "alpha", 0, 1);
        }

        circularReveal.setDuration(animationDuration);
        circularReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onCancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onCancel();
            }

            private void onCancel() {
                backgroundView.setBackgroundColor(newColor);
                bgOverlay.setVisibility(View.GONE);
            }
        });

        bgOverlay.setBackgroundColor(newColor);
        bgOverlay.setVisibility(View.VISIBLE);
        circularReveal.start();
    }
}
