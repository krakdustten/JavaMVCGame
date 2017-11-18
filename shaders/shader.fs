#version 120

uniform sampler2D sampler;

varying vec4 color_var;
varying vec2 tex_coords;

void main() {
    gl_FragColor = texture2D(sampler, tex_coords) * color_var;
}