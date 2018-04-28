package com.cn.climax.wisdomparking.ui.main.nav;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.cn.climax.wisdomparking.R;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * author：leo on 2018/4/28 0028 16:35
 * email： leocheung4ever@gmail.com
 * description: 语音播报组件
 * what & why is modified:
 */
public class TTSController implements SynthesizerListener, AMapNaviListener {

    public static final String TAG = TTSController.class.getSimpleName();
    public static TTSController ttsManager;
    boolean isfinish = true;
    private Context mContext;
    // 合成对象.
    private SpeechSynthesizer mSpeechSynthesizer;

    @Override
    public void onEvent(int i, int i1, int i2, Bundle bundle) {

    }

    TTSController(Context context) {
        mContext = context;
    }

    public static TTSController getInstance(Context context) {
        if (ttsManager == null) {
            ttsManager = new TTSController(context);
        }
        return ttsManager;
    }

    public void init() {
        // 初始化合成对象.
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
        if (mSpeechSynthesizer != null)
            initSpeechSynthesizer();
    }

    /**
     * 使用SpeechSynthesizer合成语音，不弹出合成Dialog.
     *
     * @param
     */
    public void playText(String playText) {
//        if (!isfinish) {
//            return;
//        }

        if (null == mSpeechSynthesizer) {
            // 创建合成对象.
            mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext, null);
            initSpeechSynthesizer();
        }
        // 进行语音合成.
        mSpeechSynthesizer.startSpeaking(playText, this);

    }

    public void stopSpeaking() {
        if (mSpeechSynthesizer != null)
            mSpeechSynthesizer.stopSpeaking();
    }

    public void startSpeaking() {
        isfinish = true;
    }

    private void initSpeechSynthesizer() {
        // 设置发音人
        mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,
                mContext.getString(R.string.preference_default_tts_role));
        // 设置语速
        mSpeechSynthesizer.setParameter(SpeechConstant.SPEED,
                "" + mContext.getString(R.string.preference_key_tts_speed));
        // 设置音量
        mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME,
                "" + mContext.getString(R.string.preference_key_tts_volume));
        // 设置语调
        mSpeechSynthesizer.setParameter(SpeechConstant.PITCH,
                "" + mContext.getString(R.string.preference_key_tts_pitch));

    }

    @Override
    public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
        Log.d(TAG, "onBufferProgress");

    }

    @Override
    public void onCompleted(SpeechError arg0) {
        Log.e(TAG, arg0.getErrorDescription());
        Log.d(TAG, "onCompleted");
        isfinish = true;
    }

    @Override
    public void onSpeakBegin() {
        Log.d(TAG, "onSpeakBegin");
        isfinish = false;

    }

    @Override
    public void onSpeakPaused() {
        Log.d(TAG, "onSpeakPaused");
    }

    @Override
    public void onSpeakProgress(int arg0, int arg1, int arg2) {

    }

    @Override
    public void onSpeakResumed() {
    }

    public void destroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stopSpeaking();
        }
    }

    @Override
    public void onArriveDestination() {
        // TODO Auto-generated method stub
        this.playText("到达目的地");
    }

    @Override
    public void onArrivedWayPoint(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        this.playText("路径计算失败，请检查网络或输入参数");
    }

//    @Override
//    public void onCalculateRouteSuccess() {
//        String calculateResult = "路径计算就绪";
//
//        this.playText(calculateResult);
//    }

    @Override
    public void onEndEmulatorNavi() {
        this.playText("导航结束");

    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {
        // TODO Auto-generated method stub
        this.playText(arg1);
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onInitNaviFailure() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onInitNaviSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        // TODO Auto-generated method stub
        this.playText("前方路线拥堵，路线重新规划");
    }

    @Override
    public void onReCalculateRouteForYaw() {

        this.playText("您已偏航");
    }

    @Override
    public void onStartNavi(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTrafficStatusUpdate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onGpsOpenStatus(boolean arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

//    @Override
//    public void onCalculateMultipleRoutesSuccess(int[] ints) {
//
//    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {

    }

    @Override
    public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

    }
}
