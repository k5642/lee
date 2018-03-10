attribute vec4 position;
attribute vec4 inputTextureCoordinate;
varying vec2 textureCoordinate;

# be sure to use the same position when shading multiple times
# invariant gl_Position;

void main() {
    gl_Position = position;
    textureCoordinate = inputTextureCoordinate.xy;
}