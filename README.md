# BubbleView

裁剪聊天气泡控件

## 实现效果

![截图](https://raw.githubusercontent.com/houtrry/BubbleView/master/img/screenshot01.png)
上面的是RelativeLayout, 下面的是这次实现的控件com.houtrry.library.BubbleRelativeLayout

## 使用方法

1. 把BubbleRelativeLayout.java以及res/values/attrs.xml/BubbleRelativeLayout 拷到项目中  
2. 像使用不同RelativeLayout一样使用BubbleRelativeLayout  

```

    <com.houtrry.library.BubbleRelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="@dimen/dimens5"
        android:layout_marginRight="@dimen/dimens58"
        android:layout_marginTop="@dimen/dimens14"
        android:maxWidth="@dimen/dimens219"
        bubble:arrowOffset="@dimen/dimens15"
        bubble:arrowType="right"
        bubble:arrow_height="@dimen/dimens5"
        bubble:arrow_width="@dimen/dimens10"
        bubble:borderColor="@color/color_gray_dark"
        bubble:border_width="@dimen/px1"
        bubble:bubbleType="left"
        bubble:cornerRadius="@dimen/dimens5">

		<!-- 这里放各种子View  -->
		.....

    </com.houtrry.library.BubbleRelativeLayout>

```

注意事项: 因为BubbleRelativeLayout会直接裁剪当前控件, 所以要注意子View的位置, 不要贴着箭头所在的边, 以防被部分裁剪掉, 影响显示的效果.  

3. 相关属性的解释  
* arrowOffset: 箭头位置的偏移量, 当arrowType为left的时候, 箭头距离左侧边界的距离;当arrowType为right的时候, 箭头距离右侧边界的距离;当arrowType为center的时候, 该属性无效.  
* arrowType: 箭头的位置.注意:这里的位置是指, 站在控件的中心位置, 面朝箭头所在的边的方向, 箭头在这条边的中心点的左侧(left), 正中心(center), 还是右侧(right).  
* arrow_height: 箭头的高度. 我们这里, 可以将箭头当做一个三角形, 这个三角形以缺失的那条边为底边, 高度就是顶点到这条底边的垂直距离.  
* arrow_width: 箭头的宽度. 类似于arrow_height, arrow_width是缺失的那条底边的长度.  
* borderColor: 边框的的颜色, 如果边框的宽度border_width为0, 则borderColor无效.  
* border_width: 边框的的线条的宽度.  
* bubbleType: 箭头所在的边的位置, 在左边(left), 上边(top), 右边(right), 还是下边(bottom).  
* cornerRadius: 四个顶点(左上, 右上, 左下, 右下)的圆角的半径.  

## 实现原理

实现的方法其实很简单, 并没有什么高大上的东西. 主要有两点:
* Canvas#clipPath  
    关于这个, 可以看[HenCoder Android 开发进阶：自定义 View 1-4 Canvas 对绘制的辅助 clipXXX() 和 Matrix](http://hencoder.com/ui-1-4/)  

* View的绘制顺序  
    为什么会选择重写draw方法, 而不是onDraw/dispatchDraw或者其他方法, 可以看[HenCoder Android 开发进阶：自定义 View 1-5 绘制顺序](http://hencoder.com/ui-1-5/)