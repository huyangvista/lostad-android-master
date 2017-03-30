package com.lostad.applib.util.sys;


import com.lostad.applib.util.DialogUtil;
import com.lostad.applib.util.ReflectUtil;
import com.lostad.applib.util.sys.tuple.Digests;
import com.lostad.applib.util.sys.tuple.Encodes;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * package com.thinkgem.jeesite.modules.sys.service;SystemService
 * package com.thinkgem.jeesite.modules.sys.security;SystemAuthorizingRealm
 * Created by Hocean on 2017/2/27.
 */
public class TokenUtil {

    private static final String HASH_ALGORITHM = "SHA-1";
    private static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    public static void main(String[] args) {
        System.out.println(entryptPassword("vive"));
        System.out.println(validatePassword("vive", "a8aea1c8c51ede6aa907fa61cd090c0a38d84564f4d01be258fb96b0"));

    }

    /**
     * 快速
     * @param plainPassword
     * @return
     */
    public static String entryptPasswordBuild(String plainPassword) {
        return entryptPassword(new String(Digests.sha1(plainPassword.getBytes())));
    }

    /**
     * 快速
     * @param password
     * @param passwordData
     * @return
     */
    public static boolean validatePasswordBuild(String password, String passwordData) {
        return validatePassword(new String(Digests.sha1(password.getBytes())),passwordData);
    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = Encodes.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param password     明文密码
     * @param passwordData 密文密码
     * @return 验证成功返回true
     */

    public static boolean validatePassword(String password, String passwordData) {
        String plain = Encodes.unescapeHtml(password);
        byte[] salt = Encodes.decodeHex(passwordData.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return passwordData.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public static String md5(String data)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }

    public static String sha1(String data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        StringBuffer buf = new StringBuffer();
        byte[] bits = md.digest();
        for(int i=0;i<bits.length;i++){
            int a = bits[i];
            if(a<0) a+=256;
            if(a<16) buf.append("0");
            buf.append(Integer.toHexString(a));
        }
        return buf.toString();
    }


}
