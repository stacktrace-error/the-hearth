uniform sampler2D u_texture;
uniform vec2 u_texsize;
uniform vec2 u_invsize;
uniform float u_time;
uniform float u_dp;
uniform vec2 u_offset;

varying vec2 v_texCoords;

void main(){
  vec2 T = v_texCoords.xy;
  vec2 coords = (T * u_texsize) + u_offset;
  vec4 color = texture2D(u_texture, T);

  float aberrAmount = 0.005;

  aberrAmount += sin(u_time * 0.05 /*scale*/) * (aberrAmount * 0.25/*magnitude*/);

  color.r = texture(u_texture, vec2(T.x + (aberrAmount * color.a), T.y)).r;
  color.g = texture(u_texture, T).g;
  color.b = texture(u_texture, vec2(T.x - (aberrAmount * color.a), T.y)).b;

  color.a *= (1 - (0.2 * (step(mod(coords.y / u_dp + u_time / 4.0, 20.0 /*dir*/), 18 /*width*/)))); //figure out which one is speed

  gl_FragColor = color;
}