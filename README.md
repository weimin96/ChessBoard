


# Android 棋盘覆盖
### 问题描述：
在一个 2^k×2^k 个方格组成的棋盘中, 若有一个方格与其他方格不同, 则称该方格为一特殊方格, 且称该棋盘为一个特殊棋盘. 显然特殊方格在棋盘上出现的位置有 4^k 种情形. 因而对任何 k≥0, 有 4^k 种不同的特殊棋盘.
     下图–图 (1) 中的特殊棋盘是当 k=3 时 16 个特殊棋盘中的一个：
     <img src="http://pic002.cnblogs.com/images/2011/320662/2011080809544435.png" />
     图一
     题目要求在棋盘覆盖问题中, 要用下图 - 图 (2) 所示的 4 种不同形态的 L 型骨牌覆盖一个给定的特殊棋盘上除特殊方格以外的所有方格, 且任何 2 个 L 型骨牌不得重叠覆盖.
     <img src="http://pic002.cnblogs.com/images/2011/320662/2011080809550625.png"/>
     图二
     
---
### 先看效果图
<img src="https://github.com/weimin96/ChessBoard/blob/master/ScreenShots/screenshot1.jpg" width="220"/>
<img src="https://github.com/weimin96/ChessBoard/blob/master/ScreenShots/screenshot1.jpg" width="220"/>
<img src="https://github.com/weimin96/ChessBoard/blob/master/ScreenShots/screenshot1.jpg" width="220"/>

---
### 算法（分治思想）
通过给定特殊方格位置，判断特殊方格是否在左上方，如果是就继续向左上方移动，若否就用t号L型骨牌覆盖左上角棋盘的右下角方格，接着以这个右下角方格作为特殊方格继续判断，直到填满棋盘左上角。然后用同样的方法填满右上角、左下角和右下角。

android实现
使用了GridLout动态生成n*n个button，再将button添加进list里并绑定id，
通过点击事件清除之前生成的button（这里通过调用之前存储了button的list直接匹配id并remove掉），再调用棋盘算法，直接修改button的颜色（这里并没有创建数组存储棋盘的骨牌号）。
