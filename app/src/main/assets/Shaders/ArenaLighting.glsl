precision mediump float;

varying mediump vec2 textureCoordinate;
uniform sampler2D inputImageTexture;
uniform vec2 iResolution;
uniform vec2 iMouse;
uniform float iTime;

vec2 getUV() {
    // 因为手机端会标准化坐标, 所以内部需要反向变换
    float ratio = iResolution.x / iResolution.y;
    vec2 uv = gl_FragCoord.xy / iResolution.xy;
    if (ratio > 1.)
        uv = vec2(uv.x * ratio, uv.y / ratio);
    else
        uv = vec2(uv.x / ratio, uv.y * ratio);
    return uv;
}

float rayStrength(vec2 raySource, vec2 rayRefDirection, vec2 coord, float seedA, float seedB, float speed) {
    vec2 sourceToCoord = coord - raySource;
    float cosAngle = dot(normalize(sourceToCoord), rayRefDirection);

    return clamp(
                 (0.45 + 0.15 * sin(cosAngle * seedA + iTime * speed)) +
                 (0.3 + 0.2 * cos(-cosAngle * seedB + iTime * speed)),
                 0.0, 1.0) *
    clamp((iResolution.x - length(sourceToCoord)) / iResolution.x, 0.5, 1.0);
}

void main() {
    vec2 uv = getUV();
    vec4 originColor = texture2D(inputImageTexture, textureCoordinate);
    vec4 fragColor = vec4(.0);
    vec2 fragCoord = vec2(gl_FragCoord.x, gl_FragCoord.y);

     uv.y = 1.0 - uv.y;
     vec2 coord = vec2(fragCoord.x, iResolution.y - fragCoord.y);

     // Set the parameters of the sun rays
     vec2 rayPos1 = vec2(iResolution.x * 0.4, iResolution.y * 1.4);
     vec2 rayRefDir1 = normalize(vec2(1.0, -0.116));
     float raySeedA1 = 36.2214;
     float raySeedB1 = 21.11349;
     float raySpeed1 = 1.5;

     vec2 rayPos2 = vec2(iResolution.x * 0.5, iResolution.y * 1.6);
     vec2 rayRefDir2 = normalize(vec2(1.0, 0.241));
     const float raySeedA2 = 22.39910;
     const float raySeedB2 = 18.0234;
     const float raySpeed2 = 1.1;

     // Calculate the colour of the sun rays on the current fragment
     vec4 rays1 =
     vec4(1.0, 1.0, 1.0, 1.0) *
     rayStrength(rayPos1, rayRefDir1, coord, raySeedA1, raySeedB1, raySpeed1);

     vec4 rays2 =
     vec4(1.0, 1.0, 1.0, 1.0) *
     rayStrength(rayPos2, rayRefDir2, coord, raySeedA2, raySeedB2, raySpeed2);

     fragColor = rays1 * 0.5 + rays2 * 0.4;

     // Attenuate brightness towards the bottom, simulating light-loss due to depth.
     // Give the whole thing a blue-green tinge as well.
     float brightness = 1.0 - ((1.-coord.y) / iResolution.y);
     fragColor.x *= 0.1 + (brightness * 0.8);
     fragColor.y *= 0.3 + (brightness * 0.6);
     fragColor.z *= 0.5 + (brightness * 0.5);

     vec2 uv2 = fragCoord.xy / iResolution.xy;
     vec4 texColor = texture2D(inputImageTexture, textureCoordinate);

     fragColor *= texColor*2.;

    gl_FragColor = fragColor;
}