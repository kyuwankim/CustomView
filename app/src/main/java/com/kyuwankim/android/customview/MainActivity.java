package com.kyuwankim.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CustomView view;

    Vibrator vide;

    FrameLayout ground;
    Button btn_up, btn_down, btn_left, btn_right;

    private static final int GROUND_SIZE = 10;
    private int deviceWidth = 0;
    Toast toast;

    int player_x = 1;
    int player_y = 1;

    int box_x = 1;
    int box_y = 1;


    int player_radius = 0;
    int unit = 0;

    boolean isClear = false;

    final int map[][] = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 1, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0, 0, 3}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        Toast toast;


        ground = (FrameLayout) findViewById(R.id.ground);
        btn_down = (Button) findViewById(R.id.btn_down);
        btn_up = (Button) findViewById(R.id.btn_up);
        btn_left = (Button) findViewById(R.id.btn_left);
        btn_right = (Button) findViewById(R.id.btn_right);

        btn_down.setOnClickListener(this);
        btn_up.setOnClickListener(this);
        btn_left.setOnClickListener(this);
        btn_right.setOnClickListener(this);


        view = new CustomView(this);
        ground.addView(view);


        vide = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


    }


    public void VibratorStart() {
        vide.vibrate(1000);
    }


    private void init() {

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        deviceWidth = metrics.widthPixels;
        unit = metrics.widthPixels / GROUND_SIZE;
        player_radius = unit / 2;

        box_x = 1;
        box_y = 0;

        player_x = 0;
        player_y = 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_up:
                if (player_y > 0 && !collisionCheck("up"))
                    player_y = player_y - 1;

                break;

            case R.id.btn_down:
                if (player_y < GROUND_SIZE - 1 && !collisionCheck("down"))
                    player_y = player_y + 1;

                break;

            case R.id.btn_left:
                if (player_x > 0 && !collisionCheck("left"))
                    player_x = player_x - 1;

                break;

            case R.id.btn_right:
                if (player_x < GROUND_SIZE - 1 && !collisionCheck("right"))
                    player_x = player_x + 1;

                break;
        }
        // 화면을 다시 그려주는 함수 -> 화면을 지운후에 onDraw를 호출해준다
        view.invalidate();


    }

    private boolean collisionCheck(String direction) {

        if (direction.equals("right")) {
            if (map[player_y][player_x + 1] == 1) {
                return true;
            }

            if (map[player_y][player_x + 1] == 3) {

                VibratorStart();
                isClear = true;

                return false;
            }
        } else if (direction.equals("left")) {
            if (map[player_y][player_x - 1] == 1) {
                return true;
            }
            if (map[player_y][player_x - 1] == 3) {

                VibratorStart();

                isClear = true;

                return false;
            }
        } else if (direction.equals("up")) {
            if (map[player_y - 1][player_x] == 1) {
                return true;

            }
            if (map[player_y - 1][player_x] == 3) {

                VibratorStart();

                isClear = true;

                return false;
            }
        } else if (direction.equals("down")) {
            if (map[player_y + 1][player_x] == 1) {
                return true;
            }
            if (map[player_y + 1][player_x] == 3) {

                VibratorStart();

                isClear = true;

                return false;
            }
        }


        return false;
    }


    class CustomView extends View {
        Paint magenta = new Paint();
        Paint black = new Paint();
        Paint red = new Paint();
        Paint white = new Paint();
        Paint blue = new Paint();


        Paint outline = new Paint();

        public CustomView(Context context) {
            super(context);
            magenta.setColor(Color.MAGENTA);
            black.setColor(Color.BLACK);
            red.setColor(Color.RED);
            white.setColor(Color.WHITE);
            blue.setColor(Color.BLUE);


            outline.setStyle(Paint.Style.STROKE);
            outline.setColor(Color.BLACK);
            outline.setStrokeWidth(10);


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.drawRect(0, 0, deviceWidth, deviceWidth, outline);

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] != 0) {
                        canvas.drawRect(unit * j, unit * i, unit * j + unit, unit * i + unit, black);
                    }

                }
            }

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] == 2) {
                    }

                }
            }

            if (map[9][9] == 3) {
                canvas.drawRect(unit * 9, unit * 9, unit * 9 + unit, unit * 9 + unit, red);
            }

            if (isClear == true) {
                //canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                Toast toast = Toast.makeText(getContext(), "도착!", Toast.LENGTH_SHORT);
                toast.show();

                btn_down.setEnabled(false);
                btn_up.setEnabled(false);
                btn_left.setEnabled(false);
                btn_right.setEnabled(false);


            }


            // onDraw 함수에서 그림그리기
            // 1. 색상을 정의

            // 2. canvas 에 그림을 그린다
            canvas.drawCircle(player_x * unit + player_radius, player_y * unit + player_radius, player_radius, magenta);
            //canvas.drawRect(box_x * unit, box_y * unit, box_x * unit + unit, box_y * unit + unit, blue);


        }


    }
}
