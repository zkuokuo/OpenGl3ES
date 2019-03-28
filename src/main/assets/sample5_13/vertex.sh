#version 300 es
uniform mat4 uMVPMatrix;
uniform mat4 uMMatrix;
in vec3 aPosition;
in vec4 aColor;
out vec4 vColor;
out vec3 vPosition;
void main()  {                            		
   gl_Position = uMVPMatrix * vec4(aPosition,1);
   vColor = aColor;
   vPosition=(uMMatrix * vec4(aPosition,1)).xyz;
}                      