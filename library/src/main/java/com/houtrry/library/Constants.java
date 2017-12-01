package com.houtrry.library;

/**
 * @author houtrry
 * @version $Rev$
 * @time 2017/12/1 9:22
 * @desc ${TODO}
 */

public interface Constants {
    /**
     * 箭头的朝向: 向左
     */
    int TYPE_BUBBLE_LEFT = 0x0000;
    /**
     * 箭头的朝向: 向上
     */
    int TYPE_BUBBLE_TOP = 0x0001;
    /**
     * 箭头的朝向: 向右
     */
    int TYPE_BUBBLE_RIGHT = 0x0002;
    /**
     * 箭头的朝向: 向下
     */
    int TYPE_BUBBLE_BOTTOM = 0x0003;

    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 左手边
     * 想象一下, 你站在中心点, 正对着箭头所在的边的方向, 这个时候箭头在你的那个位置?  左手边{@link #TYPE_POSITION_LEFT},  正前面{@link #TYPE_POSITION_CENTER} 右手边{@link #TYPE_POSITION_RIGHT}
     */
    int TYPE_POSITION_LEFT = 0x0100;
    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 中间
     */
    int TYPE_POSITION_CENTER = 0x0101;
    /**
     * 站在控件的中心点,
     * 箭头在箭头所在边的位置: 右手边
     */
    int TYPE_POSITION_RIGHT = 0x0102;
}
