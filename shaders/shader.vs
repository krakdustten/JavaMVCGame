#version 120

attribute vec3 vertices;
attribute vec4 color;
attribute vec2 textures;

varying vec2 tex_coords;
varying vec4 color_var;

uniform mat4 projection;

void main() {
    gl_Position = projection * vec4(vertices, 1);
    tex_coords = textures;
    color_var = color;
}