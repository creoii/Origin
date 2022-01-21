package creoii.origin.core.display.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private final Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(0f, 32f * 40f, 0f, 32f * 21f, 0f, 100f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f camFront = new Vector3f(0f, 0f, -1f);
        Vector3f camUp = new Vector3f(0f, 1f, 0f);
        viewMatrix.identity();
        return viewMatrix.lookAt(new Vector3f(position.x, position.y, 20), camFront.add(position.x, position.y, 0f), camUp);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Vector2f getPosition() {
        return position;
    }
}
