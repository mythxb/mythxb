package com.common.utils;

public class UploadFileVerify {
    public static  final String [] filesSuffix  = new String[]{"png","jpg","jpeg","gif","mp3","mp4","doc","docx","pdf","xls","xlsx","txt","wma","wmv","rmvb","mov","avi","flv","vob"};

    public static String verifyFileType(String type){
        String result = "error";
        for (String f:filesSuffix) {
            boolean v = false;
            if (f.equals(type)){
                v = true;
                result = "correct";
                break;
            }
        }
        return result;
    }
}
