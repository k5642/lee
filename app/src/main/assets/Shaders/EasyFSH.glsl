precision mediump float;

varying mediump vec2 textureCoordinate;
uniform sampler2D inputImageTexture;
uniform vec2 iResolution;
uniform vec2 iMouse;
uniform float iTime;

//vec2 getUV() {
//    // 因为手机端会标准化坐标, 所以内部需要反向变换
//    float ratio = iResolution.x / iResolution.y;
//    vec2 uv = gl_FragCoord.xy / iResolution.xy;
//    if (ratio > 1.)
//        uv = vec2(uv.x * ratio, uv.y / ratio);
//    else
//        uv = vec2(uv.x / ratio, uv.y * ratio);
//    return uv;
//}

void main() {
//    vec2 uv = getUV();
//    vec4 originColor = texture2D(inputImageTexture, textureCoordinate);
//    vec4 fragColor = vec4(.0);
//    vec2 fragCoord = vec2(gl_FragCoord.x, gl_FragCoord.y);
//
//    gl_FragColor = vec4(uv, .5+.5*sin(iTime), 1.);
    gl_FragColor = vec4(.5,.5,.5, 1.);
}