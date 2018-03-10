package com.beuno.beuno.plugin

import android.content.Context
import android.graphics.SurfaceTexture
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import com.beuno.beuno.plugin.openGL.UnoGLHelper
import com.beuno.beuno.plugin.openGL.grafika.GlUtil.checkLocation
import com.beuno.beuno.plugin.openGL.grafika.GlUtil.createProgram

/**
 * GL原图, 配合滤镜使用
 * 重构需要跟滤镜同级, 本类用于整合二者的功能
 *
 * Book Page 139
 */
object PluginGL101 {

    private lateinit var mTexture: SurfaceTexture
    private var mIdTexture: Int = 0
    private val mCoordVertex = floatArrayOf( 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f )
    private val mCoordTexture = floatArrayOf( 0f, 1f, 1f, 1f, 0f, 0f, 1f, 0f )
    private val mVertexBuffer = mCoordVertex.let { UnoGLHelper.getBufferFromArray(it) }
    private val mTextureBuffer = mCoordTexture.let { UnoGLHelper.getBufferFromArray(it) }
    private val mCustomAttribList = mutableListOf<Int>()

    private var mProgram: Int = 0
    private var mPosition: Int = 0
    private var mColor: Int = 0

    fun initProgram(ctx: Context) {
        val vsh = UnoGLHelper.readShaderFromAssets(ctx, "_vsh")
        val fsh = UnoGLHelper.readShaderFromAssets(ctx, "_fsh")
        mProgram = createProgram(vsh, fsh)
        mPosition = glGetAttribLocation(mProgram, "vPosition")
        checkLocation(mPosition, "vPosition")
    }

    /**
     * 添加自定义属性的句柄
     * todo 使用句柄
     */
    fun setAttribs(vararg attribNames: String) {
        for (attribName in attribNames)
            glGetAttribLocation(mProgram, attribName).also {
                mCustomAttribList.add(it)
            }
    }

    fun setTexture(ctx: Context, view: GLSurfaceView) {

    }

    fun setViewPort(width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    fun draw() {
        val vVertices = floatArrayOf(
                0f, .5f, 0f,
                -.5f, 0f, 0f,
                .5f, 0f, 0f,
                0f, -.5f, 0f
        ).let { UnoGLHelper.getBufferFromArray(it) }
        glClear(GL_COLOR_BUFFER_BIT)
        glUseProgram(mProgram)
        // 指定顶点着色器的属性, 存入VBO数组
        // index参数指定数组索引, 目测就是句柄值?
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, vVertices)
        // 启用VBO中的指定顶点对象. 只能启用一个缓存对象?, 其他的使用glVertexAttrib1f之类的方法赋值
        glEnableVertexAttribArray(0)
        // GL_TRIANGLES, 012, 345, 678.
        // GL_TRIANGLE_STRIP, 012, 213, 234
        // GL_TRIANGLE_FAN, 012, 023, 034
        // GL_LINES, 01, 23, 45
        // GL_LINE_STRIP, 01, 12, 23
        // GL_LINE_LOOP, 01, 12, 23, 30
        // GL_POINTS, 用于实现粒子效果的高速渲染, VSH中必须定义gl_PointSize, FSH中必须定义gl_PointCoord

        // glDrawArrays 简单图元, 顶点重用
        // glDrawElements 复杂图元, 比如立方体
        // 3.0 加入了实例渲染, 用于一次性渲染大量粒子.

        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4)
        glDisableVertexAttribArray(0)
    }

    fun clean() {

    }
}

