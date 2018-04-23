package com.houtrry.bubble;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author houtrry
 * @version $Rev$
 * time 2017/12/1 9:21
 *
 */
@IntDef({Constants.TYPE_BUBBLE_LEFT, Constants.TYPE_BUBBLE_TOP, Constants.TYPE_BUBBLE_RIGHT, Constants.TYPE_BUBBLE_BOTTOM})
@Retention(RetentionPolicy.SOURCE)
public @interface BubbleType {
}
