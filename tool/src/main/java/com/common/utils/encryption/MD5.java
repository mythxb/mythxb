package com.common.utils.encryption;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5 加密
 */
public class MD5 {

    public static String md5(String text) throws Exception {
        return DigestUtils.md5Hex(text);
    }

    public static void main(String[] args) {
        try {
            System.out.println(MD5.md5("8D35010B-EB9A-40EE-BDEB-CDAE969D554t"));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
