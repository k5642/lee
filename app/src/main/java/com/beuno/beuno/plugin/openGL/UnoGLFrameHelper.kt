package com.beuno.beuno.plugin.openGL

import android.opengl.GLES20

//TODO Refactor
object UnoGLFrameHelper {

    private val mFrameBuffers: IntArray by lazy {
        IntArray(1)
    }

    private val mFrameBufferTextures: IntArray by lazy {
        IntArray(2)
    }

    private var mFrameWidth = -1
    private var mFrameHeight = -1

    fun getFrameBuffers(): IntArray = mFrameBuffers

    fun getTextures(): IntArray = mFrameBufferTextures

    fun initFrameBuffer(width: Int, height: Int) {
        if (mFrameWidth != -1 || mFrameHeight != -1 || mFrameWidth != width || mFrameHeight != height)
            destroyFrameBuffers()
        mFrameWidth = width
        mFrameHeight = height

        GLES20.glGenFramebuffers(1, mFrameBuffers, 0)
        GLES20.glGenTextures(2, mFrameBufferTextures, 0)
        for (i in 0..1) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFrameBufferTextures[i])
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0,
                    GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null)
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE.toFloat())
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE.toFloat())
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
    }

    fun destroyFrameBuffers() {
        if (mFrameWidth != -1 || mFrameHeight != -1) {
            GLES20.glDeleteTextures(1, mFrameBufferTextures, 0)
            GLES20.glDeleteFramebuffers(1, mFrameBuffers, 0)
            mFrameWidth = -1
            mFrameHeight = -1
        }
    }


}