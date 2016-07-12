package android.cesar.com.openglapp.data;

import android.cesar.com.openglapp.Constants;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by cesarsalazar on 7/11/16.
 */
public class VertexArray {

    private final FloatBuffer floatBuffer;

    public VertexArray(float[] vertexData)
    {
        floatBuffer = ByteBuffer
                .allocateDirect(vertexData.length * Constants.BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }

    public void setVetxAttribPointer(int dataOffset, int attibuteLocation, int componentCount, int stride)
    {
        floatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attibuteLocation, componentCount, GLES20.GL_FLOAT, false, stride, floatBuffer);
        GLES20.glEnableVertexAttribArray(attibuteLocation);

        floatBuffer.position(0);
    }
}
