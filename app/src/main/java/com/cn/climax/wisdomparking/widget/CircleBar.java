package com.cn.climax.wisdomparking.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.text.DecimalFormat;

/**
 * author：leo on 2017/4/13 15:00
 * email： leocheung4ever@gmail.com
 * description: 加载进度框
 * what & why is modified:
 */

public class CircleBar extends View {

    private RectF mColorWheelRectangle = new RectF();//定义一个矩形,包含矩形的四个单精度浮点坐标
    private Paint mDefaultWheelPaint; //默认进度条画笔
    private Paint mColorWheelPaint;//进度条的画笔
    private Paint mColorWheelPaintCenter;
    private float circleStrokeWidth;
    private float mSweepAnglePer;
    private float mPercent;
    private int crowdnumber, crowdnumbernow;
    private float pressExtraStrokeWidth;
    private BarAnimation anim;
    private int crowdnumbermax = 12;// 默认最大时间
    private DecimalFormat fnum = new DecimalFormat("#.0");// 格式为保留小数点后一位

    private int mColors[];
    private Context context;

    public CircleBar(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public CircleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public CircleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        /**
         * 设置画笔渐变色
         */
        mColors = new int[]{0xFFBDD9F2, 0xFFBDD9F2, 0xFFBDD9F2};
        Shader s = new SweepGradient(0, 0, mColors, null);
        mColorWheelPaint = new Paint();
        mColorWheelPaint.setShader(s);
        mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心,只绘制轮廓线
        mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
        mColorWheelPaint.setAntiAlias(true);// 去锯齿

        mColorWheelPaintCenter = new Paint();
        mColorWheelPaintCenter.setColor(Color.rgb(249, 249, 249));
        mColorWheelPaintCenter.setStyle(Paint.Style.STROKE);
        mColorWheelPaintCenter.setStrokeCap(Paint.Cap.ROUND);
        mColorWheelPaintCenter.setAntiAlias(true);

        mDefaultWheelPaint = new Paint();
        mDefaultWheelPaint.setColor(Color.rgb(127, 127, 127));
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
        mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);
        mDefaultWheelPaint.setAntiAlias(true);

        anim = new BarAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /**
         * drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * oval是RecF类型的对象，其定义了椭圆的形状
         * startAngle指的是绘制的起始角度，钟表的3点位置对应着0度，如果传入的startAngle小于0或者大于等于360，那么用startAngle对360进行取模后作为起始绘制角度。
         * sweepAngle指的是从startAngle开始沿着钟表的顺时针方向旋转扫过的角度。如果sweepAngle大于等于360，那么会绘制完整的椭圆弧。如果sweepAngle小于0，那么会用sweepAngle对360进行取模后作为扫过的角度。
         * useCenter是个boolean值，如果为true，表示在绘制完弧之后，用椭圆的中心点连接弧上的起点和终点以闭合弧；如果值为false，表示在绘制完弧之后，弧的起点和终点直接连接，不经过椭圆的中心点。
         */
        canvas.drawArc(mColorWheelRectangle, 0, 359, false, mDefaultWheelPaint);
        canvas.drawArc(mColorWheelRectangle, 0, 359, false, mColorWheelPaintCenter);
        canvas.drawArc(mColorWheelRectangle, 270, mSweepAnglePer, false, mColorWheelPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
        circleStrokeWidth = Textscale(30, min);// 圆弧的宽度
        pressExtraStrokeWidth = Textscale(0, min);// 圆弧离矩形的距离
        mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
                circleStrokeWidth + pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth, min
                        - circleStrokeWidth - pressExtraStrokeWidth);// 设置矩形
        mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
        mColorWheelPaintCenter.setStrokeWidth(circleStrokeWidth); //设置进度条宽度和外围灰色条宽度 一样宽
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth - Textscale(2, min));
        mDefaultWheelPaint.setShadowLayer(Textscale(10, min), 0, 0, Color.rgb(127, 127, 127));// 设置阴影
    }

    /**
     * 进度条动画
     *
     * @author Administrator
     */
    public class BarAnimation extends Animation {
        public BarAnimation() {

        }

        /**
         * 每次系统调用这个方法时， 改变mSweepAnglePer，mPercent，crowdnumbernow的值，
         * 然后调用postInvalidate()不停的绘制view。
         */
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            if (interpolatedTime < 1.0f) {
                mPercent = Float.parseFloat(fnum.format(interpolatedTime * crowdnumber * 100f / crowdnumbermax));// 将浮点值四舍五入保留一位小数
                mSweepAnglePer = interpolatedTime * crowdnumber * 360 / crowdnumbermax;
                crowdnumbernow = (int) (interpolatedTime * crowdnumber);
            } else {
                mPercent = Float.parseFloat(fnum.format(crowdnumber * 100f / crowdnumbermax));// 将浮点值四舍五入保留一位小数
                mSweepAnglePer = crowdnumber * 360 / crowdnumbermax;
                crowdnumbernow = crowdnumber;
            }
            postInvalidate();
        }
    }

    /**
     * 根据控件的大小改变绝对位置的比例
     */
    public float Textscale(float n, float m) {
        return n / 500 * m;
    }

    /**
     * 更新众筹和设置一圈动画时间
     */
    public void update(int crowdnumber, int time) {
        this.crowdnumber = crowdnumber;
        anim.setDuration(time);
        //setAnimationTime(time);
        this.startAnimation(anim);
    }

    /**
     * 设置最大众筹进度
     */
    public void setMaxCrowding(int Maxcrowding) {
        crowdnumbermax = Maxcrowding;
    }

    /**
     * 设置进度条颜色
     */
    public void setColor(int red, int green, int blue) {
        mColorWheelPaint.setColor(Color.rgb(red, green, blue));
    }

    /**
     * 设置动画时间
     */
    public void setAnimationTime(int time) {
        anim.setDuration(time * crowdnumber / crowdnumbermax);// 按照比例设置动画执行时间
    }
}
