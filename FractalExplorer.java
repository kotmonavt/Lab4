import java.awt.*;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.io.File;
import java.io.IOException;



public class FractalExplorer {
    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(600); // инициализация изображения
        explorer.createAndShowGUI(); // отображение интерфейса
        explorer.drawFractal(); // рисовка фрактала
    }
    // размер экрана
    private int dSize;
    // ссылка для обновления отображения в разных методах в процессе вычисления фрактала
    private JImageDisplay img;
    // ссылка на базовый класс для отображения других фракталов в будущем
    private FractalGenerator gener;
    // диапозон комплексной плоскости, которая выводится на экран
    private Rectangle2D.Double rect;

    // конструктор отображения
    public FractalExplorer (int displaySize) {
        // сохраняем размер отображения
        dSize = displaySize;
        // инициализируем объекты диапозона
        gener = new Mandelbrot();
        // инициализируем объекты генератора
        rect = new Rectangle2D.Double();
        gener.getInitialRange(rect);
    }
    // метод для отображения результата
    public void createAndShowGUI () {
        img = new JImageDisplay(dSize, dSize);
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Fractal Generator"); // даем заголовок нашему окну
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // закрытие окна по умолчанию
        ResetButtom res = new ResetButtom(); // добавление функции обработки кнопки

        // создание панельки для отображения фрактала
        JPanel fractalPanel = new JPanel();
        fractalPanel.setLayout(new BorderLayout());
        // кнопка для сброса
        JButton resD = new JButton("Reset Display");
        resD.setActionCommand("reset");
        resD.addActionListener(res);
        // устанавливаем область и кнопку на места
        fractalPanel.add(img, BorderLayout.CENTER);
        fractalPanel.add(resD, BorderLayout.SOUTH);

        frame.getContentPane().add(fractalPanel);
        frame.addMouseListener(new MouseHandler()); // обновление функции мыши
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
    // геттеры для координат фрактала - упрощение обращения к главному геттеру
    private double getXCoord (int x) {
        return FractalGenerator.getCoord(rect.x, rect.x + rect.width, dSize, x);
    }
    private double getYCoord (int y) {
        return FractalGenerator.getCoord(rect.y, rect.y + rect.height, dSize, y);
    }


    // рисовка фрактала
    private void drawFractal () {
        double xCoord = 0;
        double yCoord = 0;

        float numIters = 0;
        float hue = 0;

        int rgbColor = 0; // цвет по умолчанию - черный
        // обработка пикселей
        for (int x = 0; x < dSize; x++) {
            xCoord = getXCoord(x);
            for (int y = 0; y < dSize; y++) {
                yCoord = getYCoord(y);
                numIters = gener.numIterations(xCoord, yCoord);
                if (numIters < 0) {
                    rgbColor = 0;
                } else {
                    hue = 0.7f + (float)numIters / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                img.drawPixel(x,y,rgbColor);
            }
        }
        img.repaint();
    }
    // обработка кнопки сброса
    private class ResetButtom implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String action = event.getActionCommand();
            if (action.equals("reset")) {
                rect = new Rectangle2D.Double();
                // возвращаемся к начальному диапазону
                gener.getInitialRange(rect);
                // рисуем фрактал заново
                drawFractal();
            }

        }
    }
    // обработка работы мыши
    private class MouseHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            double xCoord = getXCoord(event.getX());
            double yCoord = getYCoord(event.getY());
            gener.recenterAndZoomRange(rect, xCoord, yCoord, 0.5);
            drawFractal();
        }
    }

}