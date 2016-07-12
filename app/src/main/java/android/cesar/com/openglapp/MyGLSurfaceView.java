package android.cesar.com.openglapp;

import android.cesar.com.openglapp.shapes.Square;
import android.cesar.com.openglapp.shapes.Triangle;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

import java.util.jar.Attributes;

/**
 * Created by cesarsalazar on 7/8/16.
 */
public class MyGLSurfaceView extends GLSurfaceView {


    public static final String TAG = "MyGLSurfaceView";
    public static final int OPENGL_VERSION = 2;

    private final MyGLRenderer mRenderer;

    private float speed = 0.2f;
    private float zoomLevel = 1.0f;
    private boolean isRotating;
    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(OPENGL_VERSION);

        mRenderer = new MyGLRenderer();

        this.isRotating = true;
        this.speed = 0.2f;
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public MyGLSurfaceView(Context context,AttributeSet attr) {
        super(context, attr);

        setEGLContextClientVersion(OPENGL_VERSION);

        mRenderer = new MyGLRenderer();

        this.isRotating = true;
        this.speed = 0.2f;

        setRenderer(mRenderer);
       // setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 0.1f;//180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;


    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;

        this.mRenderer.setZoomlevel(this.zoomLevel);
       //this.requestRender();
    }

    public void setSpeed(float speed) {
        Log.d(TAG, "setSpeed: " + this.speed);
        this.speed = speed;
    }

    public float speed() {

        return speed;
    }

    public void setIsRotating(boolean isRotating) {
        this.isRotating = isRotating;
    }

    public boolean isRotating() {

        return isRotating;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        final float normalizedX =
                (e.getX() / (float) getWidth()) * 2 - 1;
        final float normalizedY =
                -((e.getY() / (float) getHeight()) * 2 - 1);
        Log.d(TAG, "onTouchEvent: X : "+ normalizedX);
        Log.d(TAG, "onTouchEvent: Y : "+ normalizedY);

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:



                if(this.isRotating)
                {
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    // reverse direction of rotation above the mid-line
                    if (y > getHeight() / 2) {
                        dx = dx * -1 ;
                    }

                    // reverse direction of rotation to left of the mid-line
                    if (x < getWidth() / 2) {
                        dy = dy * -1 ;
                    }
                    mRenderer.setAngle(
                            mRenderer.getAngle() +
                                    ((dx + dy) * this.speed));
                }

                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
