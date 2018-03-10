package com.beuno.beuno.plugin.openGL

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.support.annotation.RawRes
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

object UnoGLHelper {
    val fullVertex = floatArrayOf(
            -1.0f, 1.0f,
            1.0f, 1.0f,
            -1.0f, -1.0f,
            1.0f, -1.0f
    )

    val fullTexture = floatArrayOf(
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
    )

    val flipVerticalTexture = floatArrayOf(
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
    )

    val testTexture = floatArrayOf(
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            0.0f, 0.0f
    )

    val testTextureBuffer = getBufferFromArray(testTexture)

    val fullVertexBuffer = getBufferFromArray(fullVertex)

    val fullTextureBuffer = getBufferFromArray(fullTexture)

    val flipVerticalTextureBuffer = getBufferFromArray(flipVerticalTexture)

    val NO_FILTER_VERTEX_SHADER = "" +
            "attribute vec4 position;\n" +
            "attribute vec4 inputTextureCoordinate;\n" +
            " \n" +
            "varying vec2 textureCoordinate;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "    gl_Position = position;\n" +
            "    textureCoordinate = inputTextureCoordinate.xy;\n" +
            "}"

    val NO_FILTER_FRAGMENT_SHADER = "" +
            "varying highp vec2 textureCoordinate;\n" +
            " \n" +
            "uniform sampler2D inputImageTexture;\n" +
            " \n" +
            "void main()\n" +
            "{\n" +
            "     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n" +
            "}"

    fun getBufferFromArray(array: FloatArray): FloatBuffer {
        val buffer = ByteBuffer.allocateDirect(array.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
        buffer.put(array).position(0)
        return buffer
    }

    fun readShaderFromRawResource(context: Context, @RawRes resourceId: Int): String {
        val reader = context.resources.openRawResource(resourceId)
        return realReadShader(reader)
    }

    /**
     * @param fileName Shaders文件夹下的简单文件名, 且无需后缀(glsl).
     */
    fun readShaderFromAssets(context: Context, fileName: String): String {
        val fullName = "Shaders/$fileName.glsl"
        val reader = context.assets.open(fullName)
        return realReadShader(reader)
    }

    fun loadTexture(context: Context, name: String): Int {
        val textureHandle = IntArray(1)

        glGenTextures(1, textureHandle, 0)

        if (textureHandle[0] != 0) {

            // Read in the resource
            val bitmap = getImageFromAssetsFile(context, name)

            // Bind to the texture in OpenGL
            glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0])

            // Set filtering
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
            // Load the bitmap into the bound texture.
            Log.d("loadTexture", "before texImage2D $name")
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            Log.d("loadTexture", "after texImage2D $name")
            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap!!.recycle()
        }

        if (textureHandle[0] == 0) {
            throw RuntimeException("Error loading texture.")
        }

        return textureHandle[0]
    }

    fun loadTexture(bitmap: Bitmap): Int {
        val textureHandler = IntArray(1)

        glGenTextures(1, textureHandler, 0)

        if (textureHandler[0] != 0) {
            // Bind to the texture in OpenGL
            glBindTexture(GLES20.GL_TEXTURE_2D, textureHandler[0])

            // Set filtering
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
            glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)
            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
        }
        else if (textureHandler[0] == 0) {
            throw RuntimeException("Error loading texture.")
        }

        return textureHandler[0]
    }

    /**
     * 创建缓冲区对象
     * @param size 缓冲区大小, 不小于二
     * @param ptr 缓冲区对象, 用于保存图元的数据和索引
     * @param numVertices 顶点数组长度
     * @param vertexBuffer 顶点缓冲对象(保存数据)
     * @param numIndices 索引, glDrawElements使用
     * @param indices 索引对象
     */
    fun initVBO(size: Int, ptr: IntBuffer,
                numVertices: Int, vertexBuffer: IntBuffer,
                numIndices: Int, indices: IntBuffer) {
        // STATIC 用于一次赋值多次使用.
        glGenBuffers(size, ptr)
        glBindBuffer(GL_ARRAY_BUFFER, ptr[0])
        glBufferData(GL_ARRAY_BUFFER, numVertices, vertexBuffer, GL_STATIC_DRAW)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ptr[1])
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, numIndices, indices, GL_STATIC_DRAW)
    }

    // ------------------------------------------------------------------------------------------
    //                                       Private
    // ------------------------------------------------------------------------------------------

    private fun getImageFromAssetsFile(context: Context, fileName: String): Bitmap? {
        var image: Bitmap? = null
        val assetManager = context.resources.assets
        try {
            val inputStream = assetManager.open(fileName)
            image = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return image
    }

    /**
     * 读取着色脚本的IO流
     */
    private fun realReadShader(input: InputStream): String {
        val reader = input
                .let { InputStreamReader(it) }
                .let { BufferedReader(it) }
        var nextLine: String?
        val body = StringBuilder()

        try {
            nextLine = reader.readLine()
            while (nextLine != null) {
                body.append(nextLine)
                body.append('\n')
                nextLine = reader.readLine()
            }
        } catch (e: IOException) {
            Log.w("GLHelper", "GLSL Error")
            return String()
        }
        return body.toString()
    }
}

