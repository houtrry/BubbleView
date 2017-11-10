package com.houtrry.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author: houtrry
 * @time: 2017/11/5
 * @desc: ${TODO}
 */

public class BubbleRelativeLayout extends RelativeLayout {

    private static final String TAG = BubbleRelativeLayout.class.getSimpleName();

    /**
     * 箭头的朝向: 向左
     */
    public static final int TYPE_BUBBLE_LEFT = 0x0000;
    /**
     * 箭头的朝向: 向上
     */
    public static final int TYPE_BUBBLE_TOP = 0x0001;
    /**
     * 箭头的朝向: 向右
     */
    public static final int TYPE_BUBBLE_RIGHT = 0x0002;
    /**
     * 箭头的朝向: 向下
     */
    public static final int TYPE_BUBBLE_BOTTOM = 0x0003;

    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 左手边
     * 想象一下, 你站在中心点, 正对着箭头所在的边的方向, 这个时候箭头在你的那个位置?  左手边{@link #TYPE_POSITION_LEFT},  正前面{@link #TYPE_POSITION_CENTER} 右手边{@link #TYPE_POSITION_RIGHT}
     */
    public static final int TYPE_POSITION_LEFT = 0x0100;
    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 中间
     */
    public static final int TYPE_POSITION_CENTER = 0x0101;
    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 右手边
     */
    public static final int TYPE_POSITION_RIGHT = 0x0102;


    private int mBubbleType = TYPE_BUBBLE_BOTTOM;

    /**
     * 想象一下, 你站在中心点, 正对着箭头所在的边的方向, 这个时候箭头在你的那个位置?
     * 左手边{@link #TYPE_POSITION_LEFT},
     * 正前面{@link #TYPE_POSITION_CENTER},
     * 右手边{@link #TYPE_POSITION_RIGHT}
     */
    private int mArrowType = TYPE_POSITION_CENTER;

    private int mWidth;
    private int mHeight;
    /**
     * 圆角的半径
     */
    private float mCornerRadius = 40;
    private Paint mPaint;
    /**
     * 边缘线的颜色
     */
    private int mBorderColor = Color.GRAY;
    /**
     * 边缘线的宽度, 默认为0, 也就是没有边缘线
     */
    private float mBorderWidth = 0;

    /**
     * 突出部分的箭头的宽, 也就是缺失的那一部分的线条的宽度
     */
    private float mArrowHeight = 20;
    /**
     * 突出部分的箭头的高, 也就是顶点到缺失的那一部分的线条的距离
     */
    private float mArrowWidth = 40;
    /**
     * 针对mArrowType为{@link #TYPE_POSITION_LEFT}和{@link #TYPE_POSITION_RIGHT}的情况
     * 在{@link #TYPE_POSITION_LEFT}情况下, mArrowOffset为箭头左侧到左侧边界的距离
     * 在{@link #TYPE_POSITION_RIGHT}情况下, mArrowOffset为箭头右侧到右侧边界的距离
     */
    private float mArrowOffset = 100;

    public BubbleRelativeLayout(Context context) {
        this(context, null);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleRelativeLayout);
        mBubbleType = typedArray.getInt(R.styleable.BubbleRelativeLayout_bubbleType, TYPE_BUBBLE_LEFT);
        mArrowType = typedArray.getInt(R.styleable.BubbleRelativeLayout_arrowType, TYPE_POSITION_RIGHT);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.BubbleRelativeLayout_cornerRadius, 20);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.BubbleRelativeLayout_borderWidth, 0);
        mBorderColor = typedArray.getColor(R.styleable.BubbleRelativeLayout_borderColor, Color.GRAY);
        mArrowWidth = typedArray.getDimensionPixelSize(R.styleable.BubbleRelativeLayout_arrow_width, 40);
        mArrowHeight = typedArray.getDimensionPixelSize(R.styleable.BubbleRelativeLayout_arrow_height, 20);
        mArrowOffset = typedArray.getDimensionPixelSize(R.styleable.BubbleRelativeLayout_arrowOffset, 100);
        typedArray.recycle();
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth * 2);
        mPaint.setColor(mBorderColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        createClipPath();
    }

    private Path mClipPath = new Path();

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(mClipPath);
        super.draw(canvas);
        canvas.drawPath(mClipPath, mPaint);
        canvas.restore();
    }

    private void createClipPath() {
        mClipPath.reset();
        switch (mBubbleType) {
            case TYPE_BUBBLE_LEFT: {
                createLeftArrowPath();
                break;
            }
            case TYPE_BUBBLE_TOP: {
                createTopArrowPath();
                break;
            }
            case TYPE_BUBBLE_RIGHT: {
                createRightArrowPath();
                break;
            }
            case TYPE_BUBBLE_BOTTOM: {
                createBottomArrowPath();
                break;
            }
            default:
                break;
        }

    }

    private float mCenterArrow = 0;

    private void createLeftArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius + mArrowHeight, 0);
        mClipPath.lineTo(mWidth - mCornerRadius, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mWidth, mHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius + mArrowHeight, mHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(mArrowHeight, mCenterArrow + mArrowWidth * 0.5f);
        mClipPath.lineTo(0, mCenterArrow);
        mClipPath.lineTo(mArrowHeight, mCenterArrow - mArrowWidth * 0.5f);
        mClipPath.lineTo(mArrowHeight, mCornerRadius);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private void createTopArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius, mArrowHeight);
        mClipPath.lineTo(mCenterArrow - mArrowWidth * 0.5f, mArrowHeight);
        mClipPath.lineTo(mCenterArrow, 0);
        mClipPath.lineTo(mCenterArrow + mArrowWidth * 0.5f, mArrowHeight);
        mClipPath.lineTo(mWidth - mCornerRadius, mArrowHeight);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mWidth, mHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius, mHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius + mArrowHeight);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private void createRightArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius, 0);
        mClipPath.lineTo(mWidth - mCornerRadius - mArrowHeight, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mWidth - mArrowHeight, mCenterArrow - mArrowWidth * 0.5f);
        mClipPath.lineTo(mWidth, mCenterArrow);
        mClipPath.lineTo(mWidth - mArrowHeight, mCenterArrow + mArrowWidth * 0.5f);
        mClipPath.lineTo(mWidth - mArrowHeight, mHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius, mHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private void createBottomArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius, 0);
        mClipPath.lineTo(mWidth - mCornerRadius, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mWidth, mHeight - mCornerRadius - mArrowHeight);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCenterArrow + mArrowWidth * 0.5f, mHeight - mArrowHeight);
        mClipPath.lineTo(mCenterArrow, mHeight);
        mClipPath.lineTo(mCenterArrow - mArrowWidth * 0.5f, mHeight - mArrowHeight);
        mClipPath.lineTo(mCornerRadius, mHeight - mArrowHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private float getArrowCenter() {
        final float baseLine = mBubbleType == TYPE_BUBBLE_BOTTOM || mBubbleType == TYPE_BUBBLE_TOP ? mWidth : mHeight;
        final float ratioForPosition = mArrowType == TYPE_POSITION_LEFT ? 1 : (mArrowType == TYPE_POSITION_CENTER ? 0 : -1);
        final float ratioFor = mBubbleType == TYPE_BUBBLE_LEFT || mBubbleType == TYPE_BUBBLE_BOTTOM ? 1 : -1;
        return baseLine * 0.5f + ratioFor * ratioForPosition * (baseLine * 0.5f - mArrowOffset - mArrowWidth * 0.5f);
    }


    /**
     * @hide
     */
    @IntDef({TYPE_BUBBLE_LEFT, TYPE_BUBBLE_TOP, TYPE_BUBBLE_RIGHT, TYPE_BUBBLE_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BubbleType {
    }

    /**
     * @hide
     */
    @IntDef({TYPE_POSITION_LEFT, TYPE_POSITION_CENTER, TYPE_POSITION_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PositionType {
    }
}
