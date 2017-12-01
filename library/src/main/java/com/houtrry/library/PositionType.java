package com.houtrry.library;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author houtrry
 * @version $Rev$
 * @time 2017/12/1 9:25
 * @desc ${TODO}
 */
@IntDef({Constants.TYPE_POSITION_LEFT, Constants.TYPE_POSITION_CENTER, Constants.TYPE_POSITION_RIGHT})
@Retention(RetentionPolicy.SOURCE)
public @interface PositionType {
}
