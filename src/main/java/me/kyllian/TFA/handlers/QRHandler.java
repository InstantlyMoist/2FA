package me.kyllian.TFA.handlers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;

public class QRHandler {

    public Image getQRCode(String uuid, String maker, String key) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BitMatrix matrix = new QRCodeWriter().encode(generateQRDate(uuid, maker, key), BarcodeFormat.QR_CODE, 128, 128);
            MatrixToImageWriter.writeToStream(matrix, "PNG", byteArrayOutputStream);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setBackground(Color.WHITE);
            graphics2D.setColor(Color.BLACK);
            graphics2D.setFont(new Font("Arial", Font.PLAIN, 10));
            graphics2D.drawString("Enter a code from your", 128 / 2 - graphics2D.getFontMetrics().stringWidth("Enter a code from your") / 2, 10);
            graphics2D.drawString("app to verify the setup!", 128 / 2 - graphics2D.getFontMetrics().stringWidth("app to verify the setup!") / 2, 118);
            graphics2D.dispose();
            return image;
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return null;
    }

    public String generateQRDate(String account, String maker, String secret) {
        try {
            URI uri = new URI("otpauth", "totp", "/" + maker + ":" + account, "secret=" + secret + "&issuer=" + maker, null);
            return uri.toASCIIString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
