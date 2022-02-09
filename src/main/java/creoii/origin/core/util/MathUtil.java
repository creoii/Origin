package creoii.origin.core.util;

public class MathUtil {
    public static int addWithCeil(int base, int add, int max) {
        if ((base += add) > max) return max;
        else return base;
    }
}
