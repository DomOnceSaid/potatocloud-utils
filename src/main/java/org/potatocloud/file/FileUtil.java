package org.potatocloud.file;

import org.apache.commons.compress.utils.IOUtils;
import org.potatocloud.console.Print;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileUtil {

    public InputStream openFile(String path) {
        try {
            return new FileInputStream(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fileToBase64(InputStream sourceStream) {
        try {
            byte[] sourceBytes = IOUtils.toByteArray(sourceStream);
            return byteArrayToBase64(sourceBytes);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static byte[] fileToByteArray(InputStream sourceStream) {
        try {
            return IOUtils.toByteArray(sourceStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String byteArrayToBase64(byte[] sourceBytes) {
        return Base64Utils.encodeToString(sourceBytes);
    }

    public static InputStream byteArrayToFile(byte[] sourceBytes) {
        return new ByteArrayInputStream(sourceBytes);
    }

    public static InputStream base64ToInputStream(String base64) {
        return new ByteArrayInputStream(stringToByteArray(base64));
    }

    public static byte[] base64ToByteArray(String base64) {
        return base64.getBytes();
    }

    public static byte[] stringToByteArray(String string) {
        return string.getBytes();
    }

    public static String getMimeType(byte[] bytes) {
        return getMimeType(byteArrayToFile(bytes));
    }

    public static String getMimeType(String base64) {
        String[] strings = base64.split(",");
        return !Objects.equals(strings[0], "") ? strings[0] : getMimeType(base64ToInputStream(base64));
    }

    public static String getMimeType(InputStream stream) {
        try {
            return URLConnection.guessContentTypeFromStream(stream);
        } catch (IOException e) {
            Print.ln("error : " + e.getMessage());
        } return null;
    }

    public static Boolean saveInputStreamToLocal(InputStream stream) {
        try {
            Files.write(Path.of("src/main/resources/targetFile.png"), fileToByteArray(stream));
        } catch (Exception e) {
            Print.ln("error : " + e.getMessage());
            return false;
        }
        return true;
    }

    public static int calculateChecksum(byte[] bytes) {
        int checksum = 0;
        for (byte aByte : bytes) {
            checksum += aByte;
        }
        return checksum;
    }

}
