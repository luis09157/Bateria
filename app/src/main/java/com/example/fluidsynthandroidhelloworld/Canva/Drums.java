package com.example.fluidsynthandroidhelloworld.Canva;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

import com.example.fluidsynthandroidhelloworld.Model.Drum;
import com.example.fluidsynthandroidhelloworld.R;

import java.util.ArrayList;
import java.util.List;

public class Drums extends View {

    private List<Drum> drumList;
    private OnDrumsEventListener mListener;

    public Drums(Context context) {
        super(context);
        init();
    }

    private void init() {
        drumList = new ArrayList<>();
        setupDrums();
    }

    private void setupDrums() {
        drumList.add(new Drum("Drum1", 36, BitmapFactory.decodeResource(getResources(), R.drawable.kick), 0.5f, 50f, 50f));
        // Agregar más tambores si es necesario
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Drum drum : drumList) {
            drawDrum(canvas, drum);
        }
    }

    private void drawDrum(Canvas canvas, Drum drum) {
        float scaledWidth = drum.getBitmap().getWidth() * drum.getScale();
        float scaledHeight = drum.getBitmap().getHeight() * drum.getScale();
        float left = getXPositionInPixels(drum.getXPosition(), scaledWidth);
        float top = getYPositionInPixels(drum.getYPosition(), scaledHeight);
        canvas.drawBitmap(drum.getBitmap(), null, new RectF(left, top, left + scaledWidth, top + scaledHeight), null);
    }

    private float getXPositionInPixels(float xPosPercentage, float scaledWidth) {
        float screenWidth = getWidth();
        return (screenWidth * xPosPercentage / 100) - scaledWidth / 2;
    }

    private float getYPositionInPixels(float yPosPercentage, float scaledHeight) {
        float screenHeight = getHeight();
        return (screenHeight * yPosPercentage / 100) - scaledHeight / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerIndex = event.getActionIndex();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                float x = event.getX(pointerIndex);
                float y = event.getY(pointerIndex);
                for (Drum drum : drumList) {
                    if (isTouchInsideCircle(x, y, drum)) {
                        playDrums(drum.getMidiResourceId());
                        break;
                    }
                }
                break;
        }

        return true;
    }

    private boolean isTouchInsideCircle(float touchX, float touchY, Drum drum) {
        float scaledWidth = drum.getBitmap().getWidth() * drum.getScale();
        float scaledHeight = drum.getBitmap().getHeight() * drum.getScale();
        float centerX = getXPositionInPixels(drum.getXPosition(), scaledWidth) + scaledWidth / 2;
        float centerY = getYPositionInPixels(drum.getYPosition(), scaledHeight) + scaledHeight / 2;
        float radius = scaledWidth / 2; // Se asume que la imagen del tambor es un círculo

        float distance = (float) Math.sqrt(Math.pow(touchX - centerX, 2) + Math.pow(touchY - centerY, 2));
        return distance <= radius;
    }

    public interface OnDrumsEventListener {
        void onPlayDrums(int midiResourceId);
    }

    public void setOnDrumsEventListener(OnDrumsEventListener listener) {
        mListener = listener;
    }

    public void playDrums(int midiResourceId) {
        if (mListener != null) {
            mListener.onPlayDrums(midiResourceId);
        }
    }
}
