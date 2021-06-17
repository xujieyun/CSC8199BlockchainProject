package ncl.client.action;

import java.io.FileWriter;
import java.io.IOException;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Key class for signing and verifying.
 *
 * Using ECDSA algorithm secp256r1 (aka. P-256, prime256v1).
 *
 * Public key (verifying key), private key (signing key), and signature
 * are all in base64 encoded string which is easily used to transmit.
 *
 * @author Vergil Choi
 * @version 1.0
 * @see <a href="https://csrc.nist.gov/csrc/media/events/workshop-on-elliptic-curve-cryptography-standards/documents/papers/session6-adalier-mehmet.pdf">Efficient and Secure ECC Implementation of Curve P-256</a>
 */
public class Key {

    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * Create a new instance of Key with generating a new key pair.
     */
    public Key() {
        try {
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            generator.initialize(ecSpec, new SecureRandom());
            KeyPair keyPair = generator.generateKeyPair();
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Get a instance of Key with an exist public key.
     *
     * @param publicKey base64 encoded string in X.509 format
     * @return the Key instance which can only verifying
     */
    public static Key withPublicKey(String publicKey) throws InvalidKeySpecException {
        byte[] x509 = Base64.getDecoder().decode(publicKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey pk = keyFactory.generatePublic(new X509EncodedKeySpec(x509));
            return new Key(pk, null);
        } catch (NoSuchAlgorithmException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Get a instance of Key with an exist key pair.
     *
     * @param publicKey base64 encoded string in X.509 format
     * @param privateKey base64 encoded string in PKCS#8 format
     * @return the Key instance which is able to sign and verify
     */
    public static Key withKeyPair(String publicKey, String privateKey) throws InvalidKeySpecException {
        byte[] x509 = Base64.getDecoder().decode(publicKey);
        byte[] pkcs8 = Base64.getDecoder().decode(privateKey);

        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            PublicKey vk = keyFactory.generatePublic(new X509EncodedKeySpec(x509));
            PrivateKey sk = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(pkcs8));
            return new Key(vk, sk);
        } catch (NoSuchAlgorithmException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    Key(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Check the Key instance can be used to sign.
     *
     * @return true if private key is not null
     */
    public boolean canSign() {
        return this.privateKey != null;
    }

    /**
     * The public key string.
     *
     * @return base64 encoded string in X.509 format
     */
    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
    }

    /**
     * The private key string.
     *
     * @return base64 encoded string in PKCS#8 format
     */
    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(this.privateKey.getEncoded());
    }

    /**
     * Sign data if the private key is not null.
     * Hash function SHA256 used.
     *
     * @param data the byte array that needs to be signed.
     * @return the base64 encoded signature in DER format
     */
    public String sign(byte[] data) throws SignatureException {
        if (!this.canSign()) return null;

        try {
            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
            ecdsaSign.initSign(this.privateKey);
            ecdsaSign.update(data);
            return Base64.getEncoder().encodeToString(ecdsaSign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }

        return null;
    }
    /*public byte[] sign(String data) throws SignatureException {
        if (!this.canSign()) return null;

        try {
            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
            ecdsaSign.initSign(this.privateKey);
            ecdsaSign.update(Byte.parseByte(data));
            return ecdsaSign.sign();
            //return Base64.getEncoder().encodeToString(ecdsaSign.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }

        return null;
    }*/
    /**
     * Verify signature with the data specified.
     *
     * @param data the data paired with the signature.
     * @param signature the signature needed to be verified.
     * @return true if the signature is valid
     */
    public boolean verify(byte[] data, String signature) throws SignatureException {
        try {
            Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
            ecdsaVerify.initVerify(this.publicKey);
            ecdsaVerify.update(data);
            return ecdsaVerify.verify(Base64.getDecoder().decode(signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }
    public boolean verify(byte[] data, byte[] signature) throws SignatureException {
        try {
            Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
            ecdsaVerify.initVerify(this.publicKey);
            ecdsaVerify.update(data);
            return ecdsaVerify.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // This line normally won't be run
            System.err.println(e.getLocalizedMessage());
        }
        return false;
    }
    private static String filePath = "E:\\test.txt";

    private static void saveAsFileWriter(String content) {
        FileWriter fwriter = null;
        try {
            // true表示不覆盖原来的内容，而是加到文件的后面。若要覆盖原来的内容，直接省略这个参数就好
            fwriter = new FileWriter(filePath, true);
            fwriter.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fwriter.flush();
                fwriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}