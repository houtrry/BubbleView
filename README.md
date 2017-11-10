# BubbleView
裁剪聊天气泡控件
## 实现效果


## 实现原理

实现的方法其实很简单, 并没有什么高大上的东西. 主要有两点:
* Canvas#clipPath  
    关于这个, 可以看[HenCoder Android 开发进阶：自定义 View 1-4 Canvas 对绘制的辅助 clipXXX() 和 Matrix](http://hencoder.com/ui-1-4/)  

* View的绘制顺序  
    关于这个, 可以看[HenCoder Android 开发进阶：自定义 View 1-5 绘制顺序](http://hencoder.com/ui-1-5/)