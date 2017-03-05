package com.aoliao.chessboard;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText sizeText;
    private Button button;
    private GridLayout gridLayout;
    private int size;
    private List<Button> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        sizeText = (EditText) findViewById(R.id.sizeId);
        Button run = (Button) findViewById(R.id.run);
        run.setOnClickListener(this);
    }

    private void initGridView() {
        size = Integer.parseInt(sizeText.getText().toString());
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                GridLayout.Spec rowSpec = GridLayout.spec(row);
                GridLayout.Spec colSpec = GridLayout.spec(col);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                params.width = 900 / size;
                params.height = 900 / size;
                params.leftMargin = 2;
                params.topMargin = 2;
                button = new Button(this);
                button.setOnClickListener(this);
                button.setId(row * size + col);
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.chess));
                gridLayout.addView(button, params);
            }
        }
        list = new ArrayList<>();
        for (int id = 0; id < size * size; id++) {
            button = (Button) gridLayout.findViewById(id);
            list.add(button);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.run) {    //点击执行
            if (list.size() != 0) {     //如果之前生成过棋盘则清除
                for (int id = 0; id < size * size; id++) {
                    gridLayout.removeView(list.get(id));
                }
            }
            if ("".equals(sizeText.getText().toString())) { //判断输入是否为空
                Toast.makeText(getApplication(), "请输入有效数字", Toast.LENGTH_SHORT).show();
            } else {
                int t = Integer.parseInt(sizeText.getText().toString());
                if ((t & (t - 1)) == 0 && t != 0) {   //判断是否为2的n次方（除开0）
                    hideKeyboard();
                    initGridView();
                } else {
                    Toast.makeText(getApplication(), "请输入有效数字", Toast.LENGTH_SHORT).show();
                }
            }
        } else {                        //点击棋盘选择特殊点
            int dr = v.getId() / size;
            int dc = v.getId() % size;
            button = list.get(v.getId());
            button.setBackground(ContextCompat.getDrawable(this, R.color.gray));
            chessBoard(0, 0, dr, dc, size);
        }
    }

    /**
     * 棋盘算法
     * 不需要创建数组存储L型骨牌类别，改而直接用随机数列设置rgb颜色
     */
    private void chessBoard(int tr, int tc, int dr, int dc, int size) {
        if (size == 1)
            return;
        int s = size / 2;
        Random random = new Random();
        int randomColorRed = random.nextInt(255);
        int randomColorGreen = random.nextInt(255);
        int randomColorBlue = random.nextInt(255);
        if (dr < tr + s && dc < tc + s) {
            chessBoard(tr, tc, dr, dc, s);
        } else {
            button = list.get((tr + s - 1) * (int) (Math.sqrt(list.size())) + tc + s - 1);
            button.setBackgroundColor(Color.rgb(randomColorRed, randomColorGreen, randomColorBlue));
            chessBoard(tr, tc, tr + s - 1, tc + s - 1, s);
        }

        if (dr < tr + s && dc >= tc + s) {
            chessBoard(tr, tc + s, dr, dc, s);
        } else {
            button = list.get((tr + s - 1) * (int) (Math.sqrt(list.size())) + tc + s);
            button.setBackgroundColor(Color.rgb(randomColorRed, randomColorGreen, randomColorBlue));
            chessBoard(tr, tc + s, tr + s - 1, tc + s, s);
        }

        if (dr >= tr + s && dc < tc + s) {
            chessBoard(tr + s, tc, dr, dc, s);
        } else {
            button = list.get((tr + s) * (int) (Math.sqrt(list.size())) + tc + s - 1);
            button.setBackgroundColor(Color.rgb(randomColorRed, randomColorGreen, randomColorBlue));
            chessBoard(tr + s, tc, tr + s, tc + s - 1, s);
        }

        if (dr >= tr + s && dc >= tc + s) {
            chessBoard(tr + s, tc + s, dr, dc, s);
        } else {
            button = list.get((tr + s) * (int) (Math.sqrt(list.size())) + tc + s);
            button.setBackgroundColor(Color.rgb(randomColorRed, randomColorGreen, randomColorBlue));
            chessBoard(tr + s, tc + s, tr + s, tc + s, s);
        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
