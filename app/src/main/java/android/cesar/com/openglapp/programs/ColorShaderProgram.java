package android.cesar.com.openglapp.programs;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by cesarsalazar on 7/11/16.
 */
public class ColorShaderProgram extends ShaderProgram {


    private static final String TAG ="ColorShaderProgram";
    // Uniform locations
    private final int uMatrixLocation;
    private final int uColorLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;


    public ColorShaderProgram(String vertexShader, String fragmentShader)
    {
        super(vertexShader, fragmentShader);

        Log.v(TAG, "vertexShader : " + vertexShader);
        Log.v(TAG, "fragmentShader : " + fragmentShader);

        //Retrieve uniform Locations for the shader program
        uMatrixLocation = GLES20.glGetUniformLocation(this.mProgram, U_MATRIX);
        uColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);

        //Retrieve attribute locations
        aPositionLocation = GLES20.glGetAttribLocation(mProgram,A_POSITION);
        aColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR);
        Log.v(TAG, "uMatrixLocation : " + uMatrixLocation);
        Log.v(TAG, "uColorLocation : " + uColorLocation);
        Log.v(TAG, "aColorLocation : " + aColorLocation);

        Log.v(TAG, "aPositionLocation : " + aPositionLocation);
    }

    public void setUniforms(float[] Matrix, float r, float g, float b)
    {
        //Pass  the matrix into the shader program
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, Matrix, 0);
        GLES20.glUniform4f(uColorLocation, r, g, b, 1f);
    }

    public void setUniforms(float[] Matrix)
    {
        //Pass  the matrix into the shader program
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, Matrix, 0);

    }

    public int getPositionAttributeLocation()
    {
        return aPositionLocation;
    }

    public int getColorAttributeLocation()
    {

        return aColorLocation;
    }




}
