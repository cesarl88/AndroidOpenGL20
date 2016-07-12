package android.cesar.com.openglapp.util;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by cesarsalazar on 7/9/16.
 */
public class ShaderHelper {

    private static final String TAG = "ShaderHelper";

    /**
     * Loads and compiles a vertex shader, returning the opengl object ID
     */
    public static int compileVertexShader(String ShaderCode)
    {
        return CompileShader(GLES20.GL_VERTEX_SHADER,ShaderCode);
    }

    /**
     * Loads and compiles a fragment shader, returning the opengl object ID
     */

    public static int compileFragmentShader(String ShaderCode)
    {
        return CompileShader(GLES20.GL_FRAGMENT_SHADER,ShaderCode);
    }

    /**
     * Compiles a shader, returning the OpenGL object ID.
     */
    private static int CompileShader(int type, String ShaderCode)
    {
        final int shaderObjectID = GLES20.glCreateShader(type);
        
        if(shaderObjectID == 0)
        {
            Log.w(TAG, "CompileShader: Could not create new shader" );
            return 0;
        }


        //pass in the shader resource
        GLES20.glShaderSource(shaderObjectID,ShaderCode);

        //Compile shader
        GLES20.glCompileShader(shaderObjectID);


        // Get the compilation status

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);


        Log.v(TAG, "Results of compiling source:" + "\n" + ShaderCode
                + "\n:" + GLES20.glGetShaderInfoLog(shaderObjectID));

        if(compileStatus[0] == 0)
        {
            GLES20.glDeleteShader(shaderObjectID);
            Log.w(TAG, "Compilation of shader failed.");

            return 0;
        }

        // Return the shader object ID.
        return shaderObjectID;
    }

    /**
     * Links a vertex shader and a fragment shader together into an OpenGL
     * program. Returns the OpenGL program object ID, or 0 if linking failed.
     */

    public static int linkProgram(int vertextShaderId, int fragmentShaderId)
    {

        final int programObjectId = GLES20.glCreateProgram();

        //Attach the vertex shader to the program
        GLES20.glAttachShader(programObjectId, vertextShaderId);

        //Attach the fragment shader to the program
        GLES20.glAttachShader(programObjectId, fragmentShaderId);

        //Link the two shaders together into a program
        GLES20.glLinkProgram(programObjectId);

        final int[] linkstatus =  new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkstatus, 0);


        Log.v(TAG, "Results of linking program:\n" + GLES20.glGetProgramInfoLog(programObjectId));


        //Verify the link status
        if(linkstatus[0] == 0)
        {
            // If it failed, delete the program object.
            GLES20.glDeleteProgram(programObjectId);
            Log.w(TAG, "Linking of program failed.");

            return 0;

        }
        // Return the program object ID.
        return programObjectId;

    }

    /**
     * Validates an OpenGL program. Should only be called when developing the
     * application.
     */
    public static boolean validateProgram(int programId)
    {
        GLES20.glValidateProgram(programId);

        final int[] validateStatus =  new int[1];

        GLES20.glGetProgramiv(programId, GLES20.GL_VALIDATE_STATUS, validateStatus,0);

        Log.v(TAG, "Results of validating program: " + validateStatus[0]
                + "\nLog:" + GLES20.glGetProgramInfoLog(programId));

        return validateStatus[0] != 0;
    }

    /**
     * Helper function that compiles the shaders, links and validates the
     * program, returning the program ID.
     */
    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource)
    {
        int program;

        //Compile the shaders
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        // link them into a shader program
        program = linkProgram(vertexShader, fragmentShader);


        validateProgram(program);

        return program;
    }
}
