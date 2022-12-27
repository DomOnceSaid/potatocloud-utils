package org.potatocloud.totp;

import dev.potato.totp.code.*;
import dev.potato.totp.exceptions.QrGenerationException;
import dev.potato.totp.qr.QrData;
import dev.potato.totp.qr.QrGenerator;
import dev.potato.totp.qr.ZxingPngQrGenerator;
import dev.potato.totp.secret.DefaultSecretGenerator;
import dev.potato.totp.secret.SecretGenerator;
import dev.potato.totp.time.SystemTimeProvider;
import dev.potato.totp.time.TimeProvider;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static dev.potato.totp.util.Utils.getDataUriForImage;

public class TOTPHelper {
    public static String generateSecret() {
        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        return secretGenerator.generate();
    }

    public static Boolean verifyTOTP(String secret, String totpCode) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, totpCode);
    }

    public static InputStream generateQRasFile(String issuer, String label, String secret, Integer digit, Integer period) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(label)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(digit)
                .period(period)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);
        return new ByteArrayInputStream(imageData);
    }

    public static byte[] generateQRasByte(String issuer, String label, String secret, Integer digit, Integer period) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(label)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(digit)
                .period(period)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        return generator.generate(data);
    }

    public static String generateQRasBase64(String issuer, String label, String secret, Integer digit, Integer period) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(label)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(digit)
                .period(period)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);
        String mimeType = generator.getImageMimeType();
        return getDataUriForImage(imageData, mimeType);
    }

//    byte[] buffer = TOTPHelper.generateQRasByte(
//            "dom", "dom@mai.com", "hasdbasihbdiasbdiasbd", 6, 30
//    );
//    File targetFile = new File("src/main/resources/targetFile.tmp");
//        Files.write(Path.of("src/main/resources/targetFile.png"), buffer);

}
