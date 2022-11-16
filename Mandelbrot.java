import java.awt.geom.Rectangle2D;


public class Mandelbrot extends FractalGenerator {
    // константа с максимальным количеством итераций
    public static final int MAX_ITERATIONS = 2000;

    public void getInitialRange (Rectangle2D.Double range) {
        // устанавливаем начальный диапозон
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;

    }

    public int numIterations (double x, double y) {
        // выделение действительной и мнимой части, их квадратов и начального значения z
        double real = x;
        double image = y;
        double real2 = real*real;
        double image2 = image*image;
        double z0 = 0;
        // счетчик итераций
        int count = 0;

        while (count < MAX_ITERATIONS && z0 < 4 ) {
            // zn = z(n-1)^2 + c = (real + image)^2 + c = real^2 + 2real*image -image^2 + c
            image = 2*real*image + y;
            real = real2 - image2 + x;
            // обновляем наши квадраты и z0 = zn
            real2 = real*real;
            image2 = image*image;
            z0 = real2 + image2;
            count ++;
        }
        // если счетчик не дойдет до максимума - вернем его значение, иначе -1
        return count < MAX_ITERATIONS ? count : -1;
    }
}