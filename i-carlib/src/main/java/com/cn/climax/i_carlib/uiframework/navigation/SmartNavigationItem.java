package com.cn.climax.i_carlib.uiframework.navigation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

/**
 * author：leo on 2017/3/2 16:59
 * email： leocheung4ever@gmail.com
 * description: Holds data for tabs
 * what & why is modified:
 */

public class SmartNavigationItem {


    protected int mIconResource;
    protected Drawable mIcon;

    protected int mInactiveIconResource;
    protected Drawable mInactiveIcon;
    protected boolean inActiveIconAvailable = false;

    protected int mTitleResource;
    protected String mTitle;

    protected int mActiveColorResource;
    protected String mActiveColorCode;
    protected int mActiveColor;

    protected int mInActiveColorResource;
    protected String mInActiveColorCode;
    protected int mInActiveColor;

    protected BadgeItem mBadgeItem;

    public SmartNavigationItem(@NonNull String mTitle) {
        this.mTitle = mTitle;
    }

    /**
     * @param mIconResource resource for the Tab icon.
     * @param mTitle        title for the Tab.
     */
    public SmartNavigationItem(@DrawableRes int mIconResource, @NonNull String mTitle) {
        this.mIconResource = mIconResource;
        this.mTitle = mTitle;
    }

    /**
     * @param mIcon  drawable icon for the Tab.
     * @param mTitle title for the Tab.
     */
    public SmartNavigationItem(Drawable mIcon, @NonNull String mTitle) {
        this.mIcon = mIcon;
        this.mTitle = mTitle;
    }

    /**
     * @param mIcon          drawable icon for the Tab.
     * @param mTitleResource resource for the title.
     */
    public SmartNavigationItem(Drawable mIcon, @StringRes int mTitleResource) {
        this.mIcon = mIcon;
        this.mTitleResource = mTitleResource;
    }

    /**
     * @param mIconResource  resource for the Tab icon.
     * @param mTitleResource resource for the title.
     */
    public SmartNavigationItem(@DrawableRes int mIconResource, @StringRes int mTitleResource) {
        this.mIconResource = mIconResource;
        this.mTitleResource = mTitleResource;
    }

    /**
     * By default library will switch the color of icon provided (in between active and in-active icons)
     * This method is used, if people need to set different icons for active and in-active modes.
     *
     * @param mInactiveIcon in-active drawable icon
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setInactiveIcon(Drawable mInactiveIcon) {
        if (mInactiveIcon != null) {
            this.mInactiveIcon = mInactiveIcon;
            inActiveIconAvailable = true;
        }
        return this;
    }

    /**
     * By default library will switch the color of icon provided (in between active and in-active icons)
     * This method is used, if people need to set different icons for active and in-active modes.
     *
     * @param mInactiveIconResource resource for the in-active icon.
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setInActiveIconResource(@DrawableRes int mInactiveIconResource) {
        this.mInactiveIconResource = mInactiveIconResource;
        inActiveIconAvailable = true;
        return this;
    }


    /**
     * @param colorResource resource for active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setActiveColorResource(@ColorRes int colorResource) {
        this.mActiveColorResource = colorResource;
        return this;
    }

    /**
     * @param colorCode color code for active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setActiveColor(@Nullable String colorCode) {
        this.mActiveColorCode = colorCode;
        return this;
    }

    /**
     * @param color active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setActiveColor(int color) {
        this.mActiveColor = color;
        return this;
    }

    /**
     * @param colorResource resource for in-active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setInActiveColorResource(@ColorRes int colorResource) {
        this.mInActiveColorResource = colorResource;
        return this;
    }

    /**
     * @param colorCode color code for in-active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setInActiveColor(@Nullable String colorCode) {
        this.mInActiveColorCode = colorCode;
        return this;
    }

    /**
     * @param color in-active color
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setInActiveColor(int color) {
        this.mInActiveColor = color;
        return this;
    }

    /**
     * @param badgeItem badge that needs to be displayed for this tab
     * @return this, to allow builder pattern
     */
    public SmartNavigationItem setBadgeItem(@Nullable BadgeItem badgeItem) {
        this.mBadgeItem = badgeItem;
        return this;
    }

    /**
     * @param context to fetch drawable
     * @return icon drawable
     */
    protected Drawable getIcon(Context context) {
        if (this.mIconResource != 0) {
            return ContextCompat.getDrawable(context, this.mIconResource);
        } else {
            return this.mIcon;
        }
    }

    /**
     * @param context to fetch resource
     * @return title string
     */
    protected String getTitle(Context context) {
        if (this.mTitleResource != 0) {
            return context.getString(this.mTitleResource);
        } else {
            return this.mTitle;
        }
    }

    /**
     * @param context to fetch resources
     * @return in-active icon drawable
     */
    protected Drawable getInactiveIcon(Context context) {
        if (this.mInactiveIconResource != 0) {
            return ContextCompat.getDrawable(context, this.mInactiveIconResource);
        } else {
            return this.mInactiveIcon;
        }
    }

    /**
     * @return if in-active icon is set
     */
    protected boolean isInActiveIconAvailable() {
        return inActiveIconAvailable;
    }

    /**
     * @param context to fetch color
     * @return active color (or) -1 if no color is specified
     */
    protected int getActiveColor(Context context) {
        if (this.mActiveColorResource != 0) {
            return ContextCompat.getColor(context, mActiveColorResource);
        } else if (!TextUtils.isEmpty(mActiveColorCode)) {
            return Color.parseColor(mActiveColorCode);
        } else if (this.mActiveColor != 0) {
            return mActiveColor;
        } else {
            return -1;
        }
    }

    /**
     * @param context to fetch color
     * @return in-active color (or) -1 if no color is specified
     */
    protected int getInActiveColor(Context context) {
        if (this.mInActiveColorResource != 0) {
            return ContextCompat.getColor(context, mInActiveColorResource);
        } else if (!TextUtils.isEmpty(mInActiveColorCode)) {
            return Color.parseColor(mInActiveColorCode);
        } else if (this.mInActiveColor != 0) {
            return mInActiveColor;
        } else {
            return -1;
        }
    }

    /**
     * @return badge item that needs to set to respective view
     */
    protected BadgeItem getBadgeItem() {
        return mBadgeItem;
    }

}
