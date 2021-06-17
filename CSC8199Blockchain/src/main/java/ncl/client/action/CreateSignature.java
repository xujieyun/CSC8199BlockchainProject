	package ncl.client.action;

    import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
    /**
     * @ClassName CreateKey
     * @Description: TODO
     * @Author Jesse
     * @Date 2020/3/17
     * @Version V1.0
     * curl -X POST "http://ec2-3-8-149-94.eu-west-2.compute.amazonaws.com:8080/api/users/xujie/123"
     * -H "accept: application/json"
     **/
    @WebServlet("/CreateSignature")
    public class CreateSignature extends HttpServlet {
        private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
        private static final long serialVersionUID = 1L;
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { Key key = null;
            System.out.println("\rrequest：\r\n" + request);
            String userName=request.getParameter("userName");
            String firstName=request.getParameter("firstName");
            String lastName=request.getParameter("lastName");
            String email=request.getParameter("email");
            String role=request.getParameter("role");
            String password=request.getParameter("password");
            //生成公钥和私钥
            try {
                genKeyPair();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //加密字符串
            String message = userName+"#"+firstName+"#"+lastName+"#"+email+"#"+role+"#"+password;
            System.out.println("message:" + message);
            System.out.println("publicKey:" + keyMap.get(0));
            System.out.println("priveteKey:" + keyMap.get(1));
            String messageEn = null;
            try {
                messageEn = encrypt(message,keyMap.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(message + "\t加密后的字符串为:" + messageEn);
            String messageDe = null;
            try {
                messageDe = decrypt(messageEn,keyMap.get(1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("还原后的字符串为:" + messageDe);
            String privatekey = keyMap.get(1);
            String privatekey1 = privatekey.substring(0,250);
            String privatekey2 = privatekey.substring(250,500);
            String privatekey3 = privatekey.substring(500,750);
            String privatekey4 = privatekey.substring(750);
            String messageEn1 = messageEn.substring(0,30);
            String messageEn2 = messageEn.substring(30);
            System.out.println(message + "\t加密后的字符串为:" + messageEn1);
            System.out.println(message + "\t加密后的字符串为:" + messageEn2);
            
             //response.getWriter().print("privateKey:"+keyMap.get(0)+"   publicKey:"+keyMap.get(1) +"   messageEn:"+keyMap.get(1));
            response.getWriter().print(privatekey1+"###"+privatekey2+"###"+privatekey3+"###"+privatekey4+"###"+keyMap.get(0) +"###"+messageEn1 +"###"+messageEn2);
      }

        /**
         * 随机生成密钥对
         * @throws NoSuchAlgorithmException
         */
        public static void genKeyPair() throws NoSuchAlgorithmException {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024,new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 得到私钥字符串
            String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
            // 将公钥和私钥保存到Map
            keyMap.put(0,publicKeyString);  //0表示公钥
            keyMap.put(1,privateKeyString);  //1表示私钥
        }
        /**
         * RSA公钥加密
         *
         * @param str
         *            加密字符串
         * @param publicKey
         *            公钥
         * @return 密文
         * @throws Exception
         *             加密过程中的异常信息
         */
        public static String encrypt( String str, String publicKey ) throws Exception{
            //base64编码的公钥
            byte[] decoded = Base64.decodeBase64(publicKey);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
            return outStr;
        }

        /**
         * RSA私钥解密
         *
         * @param str
         *            加密字符串
         * @param privateKey
         *            私钥
         * @return 铭文
         * @throws Exception
         *             解密过程中的异常信息
         */
        public static String decrypt(String str, String privateKey) throws Exception{
            //64位解码加密后的字符串
            byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
            //base64编码的私钥
            byte[] decoded = Base64.decodeBase64(privateKey);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            String outStr = new String(cipher.doFinal(inputByte));
            return outStr;
        }

    }


	
	
