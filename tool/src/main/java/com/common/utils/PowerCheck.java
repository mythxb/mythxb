package com.common.utils;

public  final  class  PowerCheck {
    public static boolean  powercheckUtil(String []arr,int type){
        boolean bo=false;
        for (int i=0;i<arr.length;i++){
            if (Integer.parseInt(arr[i])==type){
                bo=true;
            }
        }if (bo==false){
            return false;
        }else {
            return true;
        }
    }
}
