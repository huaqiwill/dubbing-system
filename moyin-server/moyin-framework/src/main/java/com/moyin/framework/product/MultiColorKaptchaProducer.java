package com.moyin.framework.product;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.google.code.kaptcha.util.Configurable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MultiColorKaptchaProducer extends Configurable implements Producer {

    private static final Random RANDOM = new Random();
    private static final Color TEXT_COLOR = new Color(0, 0, 255); // 蓝色

    @Override
    public BufferedImage createImage(String text) {
        Config config = getConfig();
        int width = config.getWidth();
        int height = config.getHeight();
        int fontSize = config.getTextProducerFontSize();
        String[] fontNames = config.getProperties().getProperty("kaptcha.textproducer.font.names").split(",");

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        g2.setColor(config.getBackgroundColorFrom());
        g2.fillRect(0, 0, width, height);

        // 绘制干扰线
        drawNoise(g2, width, height);

        // 绘制字符
        Font font = new Font(fontNames[0], Font.BOLD, fontSize);
        g2.setFont(font);

        int charSpace = config.getTextProducerCharSpace();
        int charWidth = width / text.length();

        for (int i = 0; i < text.length(); i++) {
            g2.setColor(TEXT_COLOR);
            int x = i * charWidth + (charWidth - fontSize) / 2 + RANDOM.nextInt(charSpace);
            int y = height - (height - fontSize) / 2;
            g2.drawString(String.valueOf(text.charAt(i)), x, y);
        }

        g2.dispose();
        return image;
    }

    @Override
    public String createText() {
        return getConfig().getTextProducerImpl().getText();
    }

    private Color getRandomColor() {
        Color randomColor;
        do {
            randomColor = new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));
        } while (isColorCloseToBlue(randomColor));
        return randomColor;
    }

    private boolean isColorCloseToBlue(Color color) {
        int blueThreshold = 100;
        int blueDistance = Math.abs(color.getRed() - TEXT_COLOR.getRed())
                + Math.abs(color.getGreen() - TEXT_COLOR.getGreen())
                + Math.abs(color.getBlue() - TEXT_COLOR.getBlue());
        return blueDistance < blueThreshold;
    }

    private void drawNoise(Graphics2D g2, int width, int height) {
        int noiseLines = 20;
        for (int i = 0; i < noiseLines; i++) {
            g2.setColor(getRandomColor());
            int x1 = RANDOM.nextInt(width);
            int y1 = RANDOM.nextInt(height);
            int x2 = RANDOM.nextInt(width);
            int y2 = RANDOM.nextInt(height);
            g2.drawLine(x1, y1, x2, y2);
        }
    }
}
