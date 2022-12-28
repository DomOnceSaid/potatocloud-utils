package org.potatocloud.authentication;

import dev.potato.totp.code.*;
import dev.potato.totp.exceptions.QrGenerationException;
import dev.potato.totp.qr.QrData;
import dev.potato.totp.qr.QrGenerator;
import dev.potato.totp.qr.ZxingPngQrGenerator;
import dev.potato.totp.recovery.RecoveryCodeGenerator;
import dev.potato.totp.secret.DefaultSecretGenerator;
import dev.potato.totp.secret.SecretGenerator;
import dev.potato.totp.time.SystemTimeProvider;
import dev.potato.totp.time.TimeProvider;
import org.potatocloud.console.Print;

import java.io.ByteArrayInputStream;

import java.io.InputStream;

import static dev.potato.totp.util.Utils.getDataUriForImage;

public class TOTPHelper {

    private static final int DEFAULT_RECOVERY_AMOUNT = 12;

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
    public static String[] generateBackupKey(Integer number) {
        RecoveryCodeGenerator recoveryCodes = new RecoveryCodeGenerator();
        return recoveryCodes.generateCodes(number);
    }

    public static String[] generateBackupKey() {
        RecoveryCodeGenerator recoveryCodes = new RecoveryCodeGenerator();
        return recoveryCodes.generateCodes(DEFAULT_RECOVERY_AMOUNT);
    }
    public static InputStream generateQRasFile(String issuer, String label, String secret, Integer digit, Integer period) {
        QrData data = new QrData.Builder()
                .label(label)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(digit)
                .period(period)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData;
        try {
            imageData = generator.generate(data);
        } catch (QrGenerationException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(imageData);
    }

    public static byte[] generateQRasByte(String issuer, String label, String secret, Integer digit, Integer period) {
        QrData data = new QrData.Builder()
                .label(label)
                .secret(secret)
                .issuer(issuer)
                .algorithm(HashingAlgorithm.SHA1) // More on this below
                .digits(digit)
                .period(period)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        try {
            return generator.generate(data);
        } catch (QrGenerationException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateQRasBase64(String issuer, String label, String secret, Integer digit, Integer period) {
        try {
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
        } catch (Exception e) {
            Print.ln("error: " + e.getMessage());
        }
        return null;
    }

//    byte[] buffer = TOTPHelper.generateQRasByte(
//            "dom", "dom@mai.com", "hasdbasihbdiasbdiasbd", 6, 30
//    );
//    File targetFile = new File("src/main/resources/targetFile.tmp");
//        Files.write(Path.of("src/main/resources/targetFile.png"), buffer);

}
