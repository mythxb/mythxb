package com.common.utils.AMAP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.utils.StringUtils;
import com.common.utils.httpClient.WebUtilsOld;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class Amap {

    private static String GAODE_KEY = "d027232f9389bd7a052e9f755133604c";

    /**
     * 高德地图 通过经纬度获取地址
     * @param lon
     * @param lat
     * @return
     * @throws Exception
     */
    public static String getAddress(Double lon,Double lat)throws Exception{
        String address = "";
        String url= "https://restapi.amap.com/v3/geocode/regeo";
        Map<String,String> params = new HashMap<String,String>();
        params.put("key", Amap.GAODE_KEY);
        params.put("location",lon+","+lat);
        String result = WebUtilsOld.doGet(url,params,30*1000,30*1000,new HashMap<String, String>());
        Map<String,Object> map = JSON.parseObject(result,HashMap.class);
        String regeocode = map.get("regeocode")+"";
        if(StringUtils.isNotBlank(regeocode)){
            Map<String,Object> regeocodeMap = JSON.parseObject(regeocode,HashMap.class);
            address = regeocodeMap.get("formatted_address")+"";

        }
        return address;
    }

    /**
     * 通过地址获取经纬度
     * @param address
     * @return
     * @throws Exception
     */
    public static String getLonLat(String address)throws Exception{
        String location = "";
        String url = "https://restapi.amap.com/v3/geocode/geo?key="+ Amap.GAODE_KEY+"&address="+address;
        String result = WebUtilsOld.doGet(url,new HashMap<String, String>(),30*1000,30*1000,new HashMap<String, String>());
        if(StringUtils.isNotBlank(result)){
            Map<String,Object> resultMap = JSON.parseObject(result,HashMap.class);
            JSONArray geocodes = (JSONArray)resultMap.get("geocodes");
            if(null != geocodes && geocodes.size() > 0){
                String locations = JSON.toJSONString(geocodes.get(0));
                if(StringUtils.isNotBlank(locations)){
                    Map<String,String> map = JSON.parseObject(locations,HashMap.class);
                    location = map.get("location");
                }
            }
        }
        return location;
    }

    /**
     * gps坐标转换
     * @param lonlats
     * @return
     * @throws Exception
     */
    public static String handleLonlats(String lonlats)throws Exception {
        String url = "https://restapi.amap.com/v3/assistant/coordinate/convert";
        Map<String,String> params = new HashMap<String,String>();
        params.put("key", Amap.GAODE_KEY);
        params.put("locations",URLEncoder.encode(lonlats,"utf-8"));
        params.put("coordsys","gps");
        String reuslt = WebUtilsOld.doGet(url,params,30*1000,30*1000,new HashMap<String, String>());
        Map<String,String> resultMap = JSON.parseObject(reuslt,HashMap.class);
        String lonlatStr = "";
        if("ok".equals(resultMap.get("info"))){
            lonlatStr = resultMap.get("locations");
        }
        return lonlatStr;
    }

    public static void main(String [] args){
        try {
            System.out.println(Amap.getAddress(104.06476,30.5702));
        }catch (Exception e){
            e.printStackTrace();
        }

    }





}
