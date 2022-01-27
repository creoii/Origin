package creoii.origin.core.util;

import creoii.origin.core.input.MouseListener;
import org.apache.commons.math3.util.FastMath;
import org.joml.Vector2f;

public class MouseUtil {
    public static Vector2f getDirectionVector(Vector2f from) {
        return from.sub(MouseListener.getMousePosWorldSpace()).normalize();
    }

    public static double getDirectionDegrees(Vector2f from) {
        from = from.sub(MouseListener.getMousePosWorldSpace());
        return FastMath.toDegrees(FastMath.atan2(from.y, from.x));
    }
}
