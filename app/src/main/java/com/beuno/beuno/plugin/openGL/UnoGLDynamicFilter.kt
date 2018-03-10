package com.beuno.beuno.plugin.openGL

import android.opengl.GLES20.*
import android.os.SystemClock
import com.beuno.beuno.shortcut.logger
import java.nio.FloatBuffer

/**
 * 动态滤镜模板
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
class UnoGLDynamicFilter(
        private val fsh: String,
        private val textureId: Int,
        private val vertexBuffer: FloatBuffer,
        private val textureBuffer: FloatBuffer,
        private val customPrepare: (() -> Unit)? = null,
        private val customDrawBefore: (() -> Unit)? = null,
        private val customDrawAfter: (() -> Unit)? = null,
        private val vsh: String = UnoGLHelper.NO_FILTER_VERTEX_SHADER)
    : UnoGLFilter(fsh, textureId, vertexBuffer, textureBuffer,
        customPrepare, customDrawBefore, customDrawAfter, vsh) {

    private val mUniformResolution by lazy {
        glGetUniformLocation(mProgramHandle, "iResolution")
    }
    private val mUniformTime by lazy {
        glGetUniformLocation(mProgramHandle, "iTime")
    }
    private val mUniformMouse by lazy {
        glGetUniformLocation(mProgramHandle, "iMouse")
    }

    private var mTimestamp: Long = -1
    var iResolution = floatArrayOf(960f, 540f)
    var iMouse = floatArrayOf(0f, 0f)
    var iTime = 0f

    override fun drawBefore() {
        super.drawBefore()
        logger("更新动态滤镜的参数")
        glUniform2fv(mUniformResolution, 1, iResolution, 0)
        glUniform2fv(mUniformMouse, 1, iMouse, 0)
        glUniform1f(mUniformTime, iTime)

        logger("开始计时")
        if (mTimestamp < 0) {
            mTimestamp = SystemClock.currentThreadTimeMillis()
        }
        iTime = (SystemClock.currentThreadTimeMillis() - mTimestamp) / 1000.toFloat()
    }
}

