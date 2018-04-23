package com.houtrry.bubble;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import static com.houtrry.bubble.Constants.TYPE_BUBBLE_LEFT;

/**
 * @author: houtrry
 * time: 2017/11/5
 *
 */

public class BubbleLinearLayout extends LinearLayout {

    private static final String TAG = BubbleLinearLayout.class.getSimpleName();

    private int mBubbleType = Constants.TYPE_BUBBLE_BOTTOM;

    /**
     * 想象一下, 你站在中心点, 正对着箭头所在的边的方向, 这个时候箭头在你的那个位置?
     * 左手边{@link Constants#TYPE_POSITION_LEFT},
     * 正前面{@link Constants#TYPE_POSITION_CENTER},
     * 右手边{@link Constants#TYPE_POSITION_RIGHT}
     */
    private int mArrowType = Constants.TYPE_POSITION_CENTER;

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
     * 针对mArrowType为{@link Constants#TYPE_POSITION_LEFT}和{@link Constants#TYPE_POSITION_RIGHT}的情况
     * 在{@link Constants#TYPE_POSITION_LEFT}情况下, mArrowOffset为箭头左侧到左侧边界的距离
     * 在{@link Constants#TYPE_POSITION_RIGHT}情况下, mArrowOffset为箭头右侧到右侧边界的距离
     */
    private float mArrowOffset = 100;

    private Path mClipPath = null;

    public BubbleLinearLayout(Context context) {
        this(context, null);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setWillNotDraw(false);
        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleLayout);
        mBubbleType = typedArray.getInt(R.styleable.BubbleLayout_bubbleType, TYPE_BUBBLE_LEFT);
        mArrowType = typedArray.getInt(R.styleable.BubbleLayout_arrowType, Constants.TYPE_POSITION_RIGHT);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.BubbleLayout_cornerRadius, 20);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.BubbleLayout_borderWidth, 0);
        mBorderColor = typedArray.getColor(R.styleable.BubbleLayout_borderColor, Color.GRAY);
        mArrowWidth = typedArray.getDimensionPixelSize(R.styleable.BubbleLayout_arrow_width, 40);
        mArrowHeight = typedArray.getDimensionPixelSize(R.styleable.BubbleLayout_arrow_height, 20);
        mArrowOffset = typedArray.getDimensionPixelSize(R.styleable.BubbleLayout_arrowOffset, 100);
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
        createClipPath(w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        /**
         * !!! 关键在这里
         * 通过裁剪canvas达到效果
         */
        canvas.clipPath(mClipPath);
        super.draw(canvas);
        canvas.drawPath(mClipPath, mPaint);
        canvas.restore();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    private void createClipPath(int width, int height) {
        mClipPath = BubblePathBuilder.builder()
                .setBubbleType(mBubbleType)
                .setArrowType(mArrowType)
                .setArrowWidth(mArrowWidth)
                .setArrowHeight(mArrowHeight)
                .setArrowOffset(mArrowOffset)
                .setBubbleWidth(width)
                .setBubbleHeight(height)
                .setCornerRadius(mCornerRadius)
                .create();
    }
}
