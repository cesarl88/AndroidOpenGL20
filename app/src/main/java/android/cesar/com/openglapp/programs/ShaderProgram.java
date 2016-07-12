package android.cesar.com.openglapp.programs;

import android.cesar.com.openglapp.util.ShaderHelper;
import android.opengl.GLES20;

/**
 * Created by cesarsalazar on 7/9/16.
 */
abstract class ShaderProgram {

    //Uniform Constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "U_Texture_Unit";
    protected static final String U_COLOR = "u_Color";


    //Attributes Constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    //Shader Program
    protected final int mProgram;
    protected ShaderProgram(String vertexShader, String fragmentShader)
    {
        mProgram = ShaderHelper.buildProgram(vertexShader, fragmentShader);
    }

    public void userProgram()
    {
        GLES20.glUseProgram(mProgram);
    }


}
