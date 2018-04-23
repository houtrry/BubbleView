package com.houtrry.bubble;

import android.graphics.Path;

/**
 * @author houtrry
 * @version $Rev$
 * time 2017/12/1 9:13
 *
 */

public class BubblePathBuilder {

    private BubblePathBuilder() {

    }

    public static BubblePathBuilder builder() {
        return new BubblePathBuilder();
    }

    private Path mClipPath = new Path();

    private float mCenterArrow = 0;

    /**
     * 圆角的半径
     */
    private float mCornerRadius = 40;

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

    /**
     * 想象一下, 你站在中心点, 正对着箭头所在的边的方向, 这个时候箭头在你的那个位置?
     * 左手边{@link Constants#TYPE_POSITION_LEFT},
     * 正前面{@link Constants#TYPE_POSITION_CENTER},
     * 右手边{@link Constants#TYPE_POSITION_RIGHT}
     */
    private int mArrowType = Constants.TYPE_POSITION_CENTER;

    private int mBubbleType = Constants.TYPE_BUBBLE_BOTTOM;

    /**
     * 当前控件的宽度
     */
    private int mBubbleWidth;

    /**
     * 当前控件的高度
     */
    private int mBubbleHeight;

    public BubblePathBuilder setCornerRadius(float cornerRadius) {
        this.mCornerRadius = cornerRadius;
        return this;
    }

    public BubblePathBuilder setArrowHeight(float arrowHeight) {
        this.mArrowHeight = arrowHeight;
        return this;
    }

    public BubblePathBuilder setArrowWidth(float arrowWidth) {
        this.mArrowWidth = arrowWidth;
        return this;
    }

    public BubblePathBuilder setArrowOffset(float arrowOffset) {
        this.mArrowOffset = arrowOffset;
        return this;
    }

    public BubblePathBuilder setArrowType(@PositionType int type) {
        this.mArrowType = type;
        return this;
    }

    public BubblePathBuilder setBubbleType(@BubbleType int type) {
        this.mBubbleType = type;
        return this;
    }

    public BubblePathBuilder setBubbleWidth(int width) {
        this.mBubbleWidth = width;
        return this;
    }

    public BubblePathBuilder setBubbleHeight(int height) {
        this.mBubbleHeight = height;
        return this;
    }

    public Path create() {
        checkArgs();
        return generateBubblePath();
    }

    /**
     * 检查各个参数是否正常
     */
    private void checkArgs() {

    }

    /**
     * 生成目标Path
     *
     * @return
     */
    private Path generateBubblePath() {
        mClipPath.reset();
        switch (mBubbleType) {
            case Constants.TYPE_BUBBLE_LEFT: {
                createLeftArrowPath();
                break;
            }
            case Constants.TYPE_BUBBLE_TOP: {
                createTopArrowPath();
                break;
            }
            case Constants.TYPE_BUBBLE_RIGHT: {
                createRightArrowPath();
                break;
            }
            case Constants.TYPE_BUBBLE_BOTTOM: {
                createBottomArrowPath();
                break;
            }
            default:
                break;
        }
        return mClipPath;
    }

    private void createLeftArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius + mArrowHeight, 0);
        mClipPath.lineTo(mBubbleWidth - mCornerRadius, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mBubbleWidth, mBubbleHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius + mArrowHeight, mBubbleHeight);
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
        mClipPath.lineTo(mBubbleWidth - mCornerRadius, mArrowHeight);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mBubbleWidth, mBubbleHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius, mBubbleHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius + mArrowHeight);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private void createRightArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius, 0);
        mClipPath.lineTo(mBubbleWidth - mCornerRadius - mArrowHeight, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mBubbleWidth - mArrowHeight, mCenterArrow - mArrowWidth * 0.5f);
        mClipPath.lineTo(mBubbleWidth, mCenterArrow);
        mClipPath.lineTo(mBubbleWidth - mArrowHeight, mCenterArrow + mArrowWidth * 0.5f);
        mClipPath.lineTo(mBubbleWidth - mArrowHeight, mBubbleHeight - mCornerRadius);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCornerRadius, mBubbleHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private void createBottomArrowPath() {
        mCenterArrow = getArrowCenter();
        mClipPath.moveTo(mCornerRadius, 0);
        mClipPath.lineTo(mBubbleWidth - mCornerRadius, 0);
        mClipPath.rQuadTo(mCornerRadius, 0, mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mBubbleWidth, mBubbleHeight - mCornerRadius - mArrowHeight);
        mClipPath.rQuadTo(0, mCornerRadius, -mCornerRadius, mCornerRadius);
        mClipPath.lineTo(mCenterArrow + mArrowWidth * 0.5f, mBubbleHeight - mArrowHeight);
        mClipPath.lineTo(mCenterArrow, mBubbleHeight);
        mClipPath.lineTo(mCenterArrow - mArrowWidth * 0.5f, mBubbleHeight - mArrowHeight);
        mClipPath.lineTo(mCornerRadius, mBubbleHeight - mArrowHeight);
        mClipPath.rQuadTo(-mCornerRadius, 0, -mCornerRadius, -mCornerRadius);
        mClipPath.lineTo(0, mCornerRadius);
        mClipPath.rQuadTo(0, -mCornerRadius, mCornerRadius, -mCornerRadius);
        mClipPath.close();
    }

    private float getArrowCenter() {
        final float baseLine = mBubbleType == Constants.TYPE_BUBBLE_BOTTOM || mBubbleType == Constants.TYPE_BUBBLE_TOP ? mBubbleWidth : mBubbleHeight;
        final float ratioForPosition = mArrowType == Constants.TYPE_POSITION_LEFT ? 1 : (mArrowType == Constants.TYPE_POSITION_CENTER ? 0 : -1);
        final float ratioFor = mBubbleType == Constants.TYPE_BUBBLE_LEFT || mBubbleType == Constants.TYPE_BUBBLE_BOTTOM ? 1 : -1;
        return baseLine * 0.5f + ratioFor * ratioForPosition * (baseLine * 0.5f - mArrowOffset - mArrowWidth * 0.5f);
    }
}
