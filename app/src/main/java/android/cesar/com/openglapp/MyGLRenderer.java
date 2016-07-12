package android.cesar.com.openglapp;

import android.cesar.com.openglapp.programs.ColorShaderProgram;
import android.cesar.com.openglapp.shapes.Square;
import android.cesar.com.openglapp.shapes.Triangle;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by cesarsalazar on 7/8/16.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    public static String TAG = "MyGLRenderer";

    private Triangle mTriangle;
    private Square mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];


    private float zoom = 1.0f;



    private ColorShaderProgram colorProgram;
    private ColorShaderProgram SquarecolorProgram;

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();

        colorProgram = new ColorShaderProgram(mTriangle.vertexShaderCode,mTriangle.fragmentShaderCode);
        SquarecolorProgram = new ColorShaderProgram(mSquare.vertexShaderCode,mSquare.fragmentShaderCode);
        //mSquare = new Square();

        Log.d(TAG, "onSurfaceCreated: Suface Created");
    }

    public void setZoomlevel(float zoom)
    {
        this.zoom = zoom;
    }

    float mPos = 0f;
    int Direction = 1;
    private float[] mRotationMatrix = new float[16];
    @Override
    public void onDrawFrame(GL10 unused) {
        float[] scratch = new float[16];
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Multiply the view and projection matrices together.
        Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        positionObjectInScene(0.6f, 0.1f, -0.5f* zoom, mAngle++, 0f, 0f, 1f);
        colorProgram.userProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix, 1f, 1f, 1f);
        mTriangle.bindData(colorProgram);
        mTriangle.draw();


        positionObjectInScene(-0.6f, 0.1f, -0.5f * zoom, mAngle * (-1), 0f,0f,1f);
        colorProgram.userProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix);
        mTriangle.bindData(colorProgram);
        mTriangle.draw();

       /* positionObjectInScene(-0.9f, 0f, 0f,0f);
        colorProgram.userProgram();
        colorProgram.setUniforms(modelViewProjectionMatrix);
        mSquare.bindData(colorProgram);
        mSquare.draw();*/

        if(mPos > 1f)
        {
            Direction = -1;
            mPos = 1f;
        }
        else
        if(mPos < -1f)
        {
            Direction = 1;
            mPos = -1f;
        }


        mPos += ((0.01f) * Direction);
        Log.v(TAG,"mPos: " + mPos);
        Log.v(TAG,"Direction: " + Direction);
        positionObjectInScene(0.9f , -mPos,mPos* zoom, mAngle, 0f, 1f, 0f);
        SquarecolorProgram.userProgram();
        //colorProgram.setUniforms(modelViewProjectionMatrix);
        mSquare.bindData(SquarecolorProgram);
        mSquare.draw(modelViewProjectionMatrix);
       // Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

   //     Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1);
      //  Matrix.translateM(mRotationMatrix,0,  0f, 0f, mAngle);
   //     Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        // Draw shape
     //   mTriangle.draw(scratch);
       // Log.d(TAG, "onDrawFrame: Drawing Triangle " + this.zoomlevel *  -2.1f);
    }





    int mWidth =0;
    int mHeigth = 0;
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;


        Matrix.perspectiveM(projectionMatrix, 0, 90, (float) width / (float) height, 1f, 10f);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0f, -0.5f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);


        Log.d(TAG, "onSurfaceChanged: "+ aspectRatio);
    }


    private void positionObjectInScene(float x, float y, float z, float mAngle, float aX,float aY, float aZ) {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.setRotateM(modelMatrix, 0, mAngle, aX, aY, aZ);
        Matrix.translateM(modelMatrix, 0, x, y, z);
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix,
                0, modelMatrix, 0);
    }

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }
}
