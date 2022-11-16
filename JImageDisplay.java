import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;


// создаем класс JImageDisplay, производный от JComponent
public class JImageDisplay extends JComponent {
    private BufferedImage img; // создаем private поле, экземпляр класса BufferedImage, управляющий изображением
    public JImageDisplay (int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // инициализируем объект класса с шириной, высотой и типом изображения

        Dimension dimen = new Dimension(width,height);
        super.setPreferredSize(dimen);
    }

    // отрисовка. переопределяем paintComponent из JComponent
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
    }

    // метод для установки всех пикселей в черный цвет
    public void clearImage () {
        // пробегаемся по высоте
        for (int j = 0; j < img.getHeight(); j++) {
            // пробегаемся по ширине
            for (int i = 0; i < img.getWidth(); i++) {
                // с помощью метода drawPixel красим все пиксели в черный
                this.drawPixel(i,j,0);
            }
        }

    }

    // метод для установки пикселя в определенный цвет
    public void drawPixel (int x, int y, int rgbColor) {
        img.setRGB(x,y,rgbColor); // устанавливаем цвет пикселя по параметрам, которые переадем
    }
}