package com.beuno.beuno.plugin.openGL

import android.opengl.GLES20.*
import com.beuno.beuno.plugin.openGL.grafika.GlUtil
import com.beuno.beuno.shortcut.logger
import java.nio.FloatBuffer

/**
 * 滤镜模板
 *
 * @param vsh Vertex Shader
 * @param fsh Fragment Shader
 * @param textureId 纹理ID, bitmap可以通过UnoGLHelper.loadTexture()获取
 * @param vertexBuffer 顶点坐标数组通过UnoGLHelper转换获取
 * @param textureBuffer 纹理坐标数组通过UnoGLHelper转换获取
 * @param customPrepare 获取自定义着色器变量的句柄
 * @param customDrawBefore 绘制开始前自定义操作, 比如启用gl功能
 * @param customDrawAfter 绘制完成后自定义操作, 比如禁用gl功能
 */
open class UnoGLFilter(
        private val fsh: String,
        private val textureId: Int,
        private val vertexBuffer: FloatBuffer,
        private val textureBuffer: FloatBuffer,
        private val customPrepare: (() -> Unit)? = null,
        private val customDrawBefore: (() -> Unit)? = null,
        private val customDrawAfter: (() -> Unit)? = null,
        private val vsh: String = UnoGLHelper.NO_FILTER_VERTEX_SHADER) {
    protected val mProgramHandle by lazy {
        GlUtil.createProgram(vsh.logger(), fsh.logger())
    }
    private val mAttribPosition by lazy {
        glGetAttribLocation(mProgramHandle, "position") }
    private val mAttribTexCoord by lazy {
        glGetAttribLocation(mProgramHandle, "inputTextureCoordinate") }
    private val mUniformTexture by lazy {
        glGetUniformLocation(mProgramHandle, "inputImageTexture") }

    fun setup() {
        logger("成员变量初始化都延迟定义了")
        mAttribPosition
        mUniformTexture
        mAttribTexCoord
        GlUtil.checkGlError("成员变量初始化")
    }

    fun draw() {
        glUseProgram(mProgramHandle)
        drawBefore()
        drawing()
        drawAfter()
        glUseProgram(0)
    }

    protected open fun drawBefore() {
        logger("开始绘制前, 激活相关缓冲区")
        customPrepare?.invoke()
        vertexBuffer.clear()
        glVertexAttribPointer(mAttribPosition, 2, GL_FLOAT, false, 0, vertexBuffer)
        glEnableVertexAttribArray(mAttribPosition)
        GlUtil.checkGlError("激活Attribute Position失败")
        textureBuffer.clear()
        glVertexAttribPointer(mAttribTexCoord, 2, GL_FLOAT, false, 0, textureBuffer)
        GlUtil.checkGlError("开始激活Attribute Texture Coordinate失败")
        glEnableVertexAttribArray(mAttribTexCoord)
        GlUtil.checkGlError("激活Attribute Texture Coordinate失败")
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)
        glUniform1i(mUniformTexture, 0)
        GlUtil.checkGlError("激活Attribute Texture失败")
        customDrawBefore?.invoke()
    }

    private fun drawing() {
        GlUtil.checkGlError("绘制前已崩")
        glBindFramebuffer(GL_FRAMEBUFFER, UnoGLFrameHelper.getFrameBuffers()[0])
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, UnoGLFrameHelper.getTextures()[0], 0)
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        glBindFramebuffer(GL_FRAMEBUFFER, 0)

    }

    private fun drawAfter() {
        GlUtil.checkGlError("完成绘制后, 冻结相关缓冲区")
        glDisableVertexAttribArray(mAttribPosition)
        glDisableVertexAttribArray(mAttribTexCoord)
        glBindTexture(GL_TEXTURE_2D, 0)
        customDrawAfter?.invoke()
    }

    fun clean(textureHandles: IntArray?) {
        glDeleteProgram(mProgramHandle)
        if (textureHandles != null)
            glDeleteTextures(textureHandles.size, textureHandles, 0)
    }
}

