package android.cesar.com.openglapp.shapes;

import android.cesar.com.openglapp.MyGLRenderer;
import android.cesar.com.openglapp.data.VertexArray;
import android.cesar.com.openglapp.programs.ColorShaderProgram;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


/**
 * Created by cesarsalazar on 7/8/16.
 */
public class Triangle {

    private FloatBuffer vertexBuffer;

    static final int COORDS_PER_VERTEX = 3;
    static final int COORDS_PER_COLOR = 4;

    static float triangleCoords[] = {   // in counterclockwise order:
            0.0f,  0.8f, 1.0f,  // top
            -0.8f, 0.5f, 1.0f, // bottom left
            0.8f, 0.5f, 1.0f  // bottom right
    };

    private float[] colors = { // Colors for the vertices (NEW)
            1.0f, 0.0f, 0.0f, 1.0f, // Red (NEW)
            0.0f, 1.0f, 0.0f, 1.0f, // Green (NEW)
            0.0f, 0.0f, 1.0f, 1.0f  // Blue (NEW)
    };

    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = 0;
            //(COORDS_PER_VERTEX + COLOR_COMPONENT_COUNT);



    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = 3;
    //   private final int STRIDE = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    private final VertexArray vertexArray;
    private final VertexArray colorArray;


    public final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 u_Matrix;" +
                    "varying vec4 v_Color;"+
                    "attribute vec4 a_Color;" +
                    "attribute vec4 a_Position;" +
                    "void main() {" +
                    "v_Color = a_Color;" +
                    // the matrix must; be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = u_Matrix * a_Position;" +
                    "}";

    public final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 u_Color;" +
                    "varying vec4 v_Color;" +
                    "void main() {" +
                    "  gl_FragColor = v_Color;" +
                    "}";


    private int mProgram;

    public Triangle()
    {
        vertexArray =  new VertexArray(triangleCoords);
        colorArray = new VertexArray(colors);
    }



    public void bindData(ColorShaderProgram colorProgram)
    {
        vertexArray.setVetxAttribPointer(
                0,
                colorProgram.getPositionAttributeLocation(),
                COORDS_PER_VERTEX,
                STRIDE);

        colorArray.setVetxAttribPointer(
                0,
                colorProgram.getColorAttributeLocation(),
                COORDS_PER_COLOR,
                STRIDE
        );



    }

    public void draw()
    {
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    }


}
