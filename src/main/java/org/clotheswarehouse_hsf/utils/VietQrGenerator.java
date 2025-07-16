package org.clotheswarehouse_hsf.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class VietQrGenerator {

    public static String generateVietQrBase64(String bankBin, String accountNumber, String accountName, String orderCode, long amountVnd) throws Exception {
        String payloadFormatIndicator = "000201";
        String pointOfInitiationMethod = "010211";

        String guid = "0010A000000727";
        String bankInfo = "0113" + bankBin;
        String accountInfo = "0208" + accountNumber;
        String accountNameField = accountName != null && !accountName.isBlank()
                ? "03" + String.format("%02d", accountName.length()) + accountName.toUpperCase()
                : "";

        String merchantValue = guid + bankInfo + accountInfo + accountNameField;
        String merchantAccountInfo = "38" + lengthPrefixed(merchantValue);

        String currency = "5303704";

        String amountField = "";
        if (amountVnd > 0) {
            String amountStr = String.valueOf(amountVnd);
            amountField = "54" + String.format("%02d", amountStr.length()) + amountStr;
        }

        String countryCode = "5802VN";

        String additionalData = "62" + lengthPrefixed("0108" + orderCode);

        String rawData = payloadFormatIndicator
                + pointOfInitiationMethod
                + merchantAccountInfo
                + currency
                + amountField
                + countryCode
                + additionalData
                + "6304";

        String crc = crc16Ccitt(rawData);
        rawData += crc;

        BitMatrix matrix = new MultiFormatWriter().encode(rawData, BarcodeFormat.QR_CODE, 300, 300);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        return Base64.getEncoder().encodeToString(os.toByteArray());
    }


    private static String lengthPrefixed(String data) {
        return String.format("%02d", data.length()) + data;
    }

    private static String crc16Ccitt(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        int crc = 0xFFFF;
        for (byte b : bytes) {
            crc ^= (b & 0xFF) << 8;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ 0x1021;
                } else {
                    crc <<= 1;
                }
            }
        }
        return String.format("%04X", crc & 0xFFFF);
    }
}
