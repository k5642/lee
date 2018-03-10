package com.beuno.beuno.widgets

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import com.beuno.beuno.plugin.PluginGL101
import com.beuno.beuno.shortcut.logger
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * android.opengl 包提供的OpenGL-API是优化版本.
 *
 * Projection Matrix 用于坐标变换,
 * OpenGL假设渲染屏幕为正方形, 而实际屏幕为长方形, 所以坐标位置需要变换拉伸.
 * Camera同样也需要投影矩阵.
 * 两个矩阵结合在一起就是 uMVPMatrix.
 *
 * 3D环境中, 默认情况下, 逆时针坐标的图形是正面朝前
 */

class UnoGLSurfaceView @JvmOverloads constructor
(context: Context, attrs: AttributeSet? = null) : GLSurfaceView(context, attrs) {

    init {
        setEGLContextClientVersion(3)
        setRenderer(UnoGLRenderer(this, context))
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    fun draw() {

    }
}

class UnoGLRenderer(
        private val mView: GLSurfaceView,
        private val mContext: Context): GLSurfaceView.Renderer {

    override fun onDrawFrame(gl: GL10?) {
        logger("在每次绘制时被调用")
        GLES30.glClearColor(.3f, 0f, 0f, .5f)
        PluginGL101.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        logger("在View进行几何变化时触发, 比如尺寸变化, 屏幕旋转. 通常处理 ViewPort, Ratio")
        PluginGL101.setViewPort(width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        logger("这个方法只会被调用一次, 用于存放只需要执行一次的命令. 比如设置GL环境参数, 初始化OpenGL相关对象.")
        PluginGL101.initProgram(mContext)
    }
}