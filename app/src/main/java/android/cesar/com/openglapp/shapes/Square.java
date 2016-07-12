package android.cesar.com.openglapp.shapes;

import android.cesar.com.openglapp.data.VertexArray;
import android.cesar.com.openglapp.programs.ColorShaderProgram;
import android.opengl.GLES20;
import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by cesarsalazar on 7/8/16.
 */
public class Square {


    private final static String TAG = "Square";
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    // number of coordinates per vertex in this array

    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;
    static final int COORDS_PER_FACE = 4;
    private static final int STRIDE = 0;

    private float[][] colors = {  // Colors of the 6 faces
            {1.0f, 0.5f, 0.0f, 1.0f},  // 0. orange
            {1.0f, 0.0f, 1.0f, 1.0f},  // 1. violet
            {0.0f, 1.0f, 0.0f, 1.0f},  // 2. green
            {0.0f, 0.0f, 1.0f, 1.0f},  // 3. blue
            {1.0f, 0.0f, 0.0f, 1.0f},  // 4. red
            {1.0f, 1.0f, 0.0f, 1.0f}   // 5. yellow
    };

    private float[] squareCoords = {  // Vertices of the 6 faces
            // FRONT
            -0.2f, -0.2f,  0.2f,  // 0. left-bottom-front
            0.2f, -0.2f,  0.2f,  // 1. right-bottom-front
            -0.2f,  0.2f,  0.2f,  // 2. left-top-front
            0.2f,  0.2f,  0.2f,  // 3. right-top-front
            // BACK
            0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
            -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
            0.2f,  0.2f, -0.2f,  // 7. right-top-back
            -0.2f,  0.2f, -0.2f,  // 5. left-top-back
            // LEFT
            -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
            -0.2f, -0.2f,  0.2f,  // 0. left-bottom-front
            -0.2f,  0.2f, -0.2f,  // 5. left-top-back
            -0.2f,  0.2f,  0.2f,  // 2. left-top-front
            // RIGHT
            0.2f, -0.2f,  0.2f,  // 1. right-bottom-front
            0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
            0.2f,  0.2f,  0.2f,  // 3. right-top-front
            0.2f,  0.2f, -0.2f,  // 7. right-top-back
            // TOP
            -0.2f,  0.2f,  0.2f,  // 2. left-top-front
            0.2f,  0.2f,  0.2f,  // 3. right-top-front
            -0.2f,  0.2f, -0.2f,  // 5. left-top-back
            0.2f,  0.2f, -0.2f,  // 7. right-top-back
            // BOTTOM
            -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
            0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
            -0.2f, -0.2f,  0.2f,  // 0. left-bottom-front
            0.2f, -0.2f,  0.2f   // 1. right-bottom-front*/
    };


    public final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 u_Matrix;" +
                    "attribute vec4 a_Position;" +
                    "void main() {" +
                    // the matrix must; be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.s
                    "  gl_Position = u_Matrix * a_Position;" +
                    "}";

    public final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 u_Color;" +
                    "void main() {" +
                    "  gl_FragColor = u_Color;" +
                    "}";


    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    private int mProgram;
    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = squareCoords.length / 3;
    //   private final int STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private final VertexArray vertexArray;
    //private final VertexArray colorArray;

    public Square() {

        vertexArray =  new VertexArray(squareCoords);
     //   colorArray = new VertexArray(colors);
    }


    private ColorShaderProgram mCOlorProgram;
    public void bindData(ColorShaderProgram colorProgram)
    {
        vertexArray.setVetxAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                COORDS_PER_VERTEX,
                STRIDE);

        /*colorArray.setVetxAttribPointer(
                0,
                colorProgram.getColorAttributeLocation(),
                COORDS_PER_COLOR,
                STRIDE
        );*/

        this.mCOlorProgram = colorProgram;

    }

    public void draw(float[] m)
    {
       // mCOlorProgram.setUniforms(m,colors[0][0],colors[0][1],colors[0][2]);
       // GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount * 12);

        for(int i = 0; i < 6 ; i++)
        {
            Log.v(TAG,"i : " + i);
            Log.v(TAG,"colors["+i+"][0] : " + colors[i][0]);
            Log.v(TAG,"colors["+i+"][1] : " + colors[i][1]);
            Log.v(TAG,"colors["+i+"][2] : " + colors[i][2]);
            Log.v(TAG,"i * COORDS_PER_FACE : " + i * COORDS_PER_FACE);
            Log.v(TAG,"vertexCount : " + vertexCount);
            mCOlorProgram.setUniforms(m,colors[i][0],colors[i][1],colors[i][2]);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, i * COORDS_PER_FACE, 4);
        }



    }
}
