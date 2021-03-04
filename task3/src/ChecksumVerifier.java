import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ChecksumVerifier {

    private String name;
    private String algorithm;
    private String checksum;
    private String stateOfCheck;

    public ChecksumVerifier(String name, String algorithm, String checksum) {
        this.name = name;
        this.checksum = checksum.toLowerCase();
        setAlgorithm(algorithm.toLowerCase());
    }

    public String checkChecksum(String directory) {
        byte[] readingFile;
        String hex;

        try  {
            readingFile = Files.readAllBytes(Paths.get(directory+"/"+name));
            hex = calculateChecksum(algorithm, readingFile);
            stateOfCheck = checksum.equals(hex) ? "OK" : "FAIL";
        } catch (IOException | NoSuchAlgorithmException e) {
            stateOfCheck = "NOT FOUND";
        }

        return new StringBuilder().append(name).append(" ").append(stateOfCheck).toString();
    }

    public String calculateChecksum(String algorithm, byte [] readingFile) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        byte[] digest = messageDigest.digest(readingFile);
        StringBuilder sb = new StringBuilder();
        for (byte d:digest) {
            sb.append(String.format("%02x",d));
        }
        return sb.toString();
    }

    public void setAlgorithm(String algorithm) {
        switch (algorithm) {
            case "sha256":
                algorithm = "SHA-256";
                break;
            case "sha1":
                algorithm = "SHA-1";
                break;
        }
        this.algorithm = algorithm;
    }
}
