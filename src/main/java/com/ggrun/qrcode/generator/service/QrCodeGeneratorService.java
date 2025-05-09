package com.ggrun.qrcode.generator.service;

import com.ggrun.qrcode.generator.dto.QrCodeGeneratorResponse;
import com.ggrun.qrcode.generator.ports.StoragePort;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGeneratorService {

    private final StoragePort storagePort;
    private final int QR_CODE_WIDTH = 200;
    private final int QR_CODE_HEIGHT = 200;
    private final String QR_CODE_FORMAT = "PNG";
    private final String QR_CODE_CONTENT_TYPE = "image/png";

    public QrCodeGeneratorService(StoragePort storagePort) {
        this.storagePort = storagePort;
    }

    public QrCodeGeneratorResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, QR_CODE_FORMAT, pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        String url = storagePort.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), QR_CODE_CONTENT_TYPE);

        return new QrCodeGeneratorResponse(url);
    }
}
