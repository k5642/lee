package com.beuno.beuno.plugin

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.opengl.GLES20.glViewport
import android.opengl.GLSurfaceView
import android.support.annotation.RawRes
import android.view.Surface
import com.beuno.beuno.plugin.openGL.UnoGLDynamicFilter
import com.beuno.beuno.plugin.openGL.UnoGLHelper
import com.beuno.beuno.plugin.openGL.grafika.GlUtil
import com.beuno.beuno.plugin.openGL.grafika.Texture2dProgram

/**
 * GL原图, 配合滤镜使用
 * 重构需要跟滤镜同级, 本类用于整合二者的功能
 */
object PluginGL {

    private lateinit var mFilter : UnoGLDynamicFilter

    private lateinit var mProgram: Texture2dProgram
    private lateinit var mTexture: SurfaceTexture
    private var mIdTexture: Int = 0
    private val mCoordVertex = floatArrayOf( 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f )
    private val mCoordTexture = floatArrayOf( 0f, 1f, 1f, 1f, 0f, 0f, 1f, 0f )
    private val mVertexBuffer = mCoordVertex.let { UnoGLHelper.getBufferFromArray(it) }
    private val mTextureBuffer = mCoordTexture.let { UnoGLHelper.getBufferFromArray(it) }

    fun initProgram() {
        mProgram = Texture2dProgram(Texture2dProgram.ProgramType.TEXTURE_EXT)
    }

//    fun setTexture(ctx: Context, view: GLSurfaceView, bmp: Bitmap) {
//        mTexture = mProgram.createTextureObject()
//                .also { mIdTexture = it }
//                .let { SurfaceTexture(it) }
//        GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bmp, 0)
////        _GLHelper.loadTexture(bmp)
//        mTexture.setOnFrameAvailableListener {
//            view.requestRender()
//        }
//
//        mFilter = UnoGLDynamicFilter(
//                UnoGLHelper.readShaderFromAssets(ctx, "Shaders/EasyFSH.glsl"),
//                mIdTexture, mVertexBuffer, mTextureBuffer
//        )
//    }

    /**
     * @param res 播放器用的资源文件路径
     */
    fun setTexture(ctx: Context, view: GLSurfaceView, @RawRes res: Int) {
        mTexture = mProgram.createTextureObject()
                .also { mIdTexture = it }
                .let { SurfaceTexture(it) }
        mTexture.setOnFrameAvailableListener {
            view.requestRender()
        }

        val player = MediaPlayer.create(ctx, res)
        player.apply {
            val surface = Surface(mTexture)
            player.setSurface(surface)
            player.start()
        }

        mFilter = UnoGLDynamicFilter(
                UnoGLHelper.readShaderFromAssets(ctx, "EasyFSH"),
                mIdTexture, mVertexBuffer, mTextureBuffer.duplicate()
        )
    }

    fun setViewPort(width: Int, height: Int) {
        glViewport(0, 0, width, height)
        mFilter.iResolution = floatArrayOf(width.toFloat(), height.toFloat())
    }

    fun draw() {
        mTexture.updateTexImage()
        val textureMatrix = FloatArray(16)
        mTexture.getTransformMatrix(textureMatrix)
        mProgram.draw(GlUtil.IDENTITY_MATRIX,
                mVertexBuffer,
                0,
                4,
                2,
                8,
                textureMatrix,
                mTextureBuffer,
                mIdTexture,
                8
                )
        mFilter.draw()
    }

    fun clean() {
        mProgram.release()
        mFilter.clean(null)
    }
}

