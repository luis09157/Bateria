package com.example.fluidsynthandroidhelloworld.Model;

import android.graphics.Bitmap;

public class Drum {
    private String id;
    private int midiResourceId;
    private Bitmap bitmap;
    private float scale;
    private float xPosition;
    private float yPosition;

    public Drum(String id, int midiResourceId, Bitmap bitmap, float scale, float xPosition, float yPosition) {
        this.id = id;
        this.midiResourceId = midiResourceId;
        this.bitmap = bitmap;
        this.scale = scale;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public String getId() {
        return id;
    }

    public int getMidiResourceId() {
        return midiResourceId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getScale() {
        return scale;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public boolean isTouchInside(float touchX, float touchY) {
        float scaledWidth = bitmap.getWidth() * scale;
        float scaledHeight = bitmap.getHeight() * scale;

        float leftX = xPosition - scaledWidth / 2f;
        float rightX = xPosition + scaledWidth / 2f;
        float topY = yPosition - scaledHeight / 2f;
        float bottomY = yPosition + scaledHeight / 2f;

        return touchX >= leftX && touchX <= rightX && touchY >= topY && touchY <= bottomY;
    }
}
