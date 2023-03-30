package com.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 序列化工具类
 * @author sunsharp
 *
 */
public class SerializeUtil {
	 /**
	  * 序列化对象
	  * @param object
	  * @return
	  */
	public static byte[] serialize(Object object) {
		 ObjectOutputStream oos = null;
		 ByteArrayOutputStream baos = null;
		 try {
			 //序列化
			 baos = new ByteArrayOutputStream();
			 oos = new ObjectOutputStream(baos);
			 oos.writeObject(object);
			 byte[] bytes = baos.toByteArray();
			 baos.close();
			 oos.close();
			 return bytes;
		 } catch (Exception e) {
			e.printStackTrace(); 
		 }
		 return null;
    }
	 /**
	  * 反序列化
	  * @param bytes
	  * @return
	  */
	public static Object unserialize(byte[] bytes) {
		 ByteArrayInputStream bais = null;
		 try {
			 //反序列化
			 bais = new ByteArrayInputStream(bytes);
			 ObjectInputStream ois = new ObjectInputStream(bais);
			 ois.close();
			 bais.close();
			 return ois.readObject();
		 } catch (Exception e) {
			   e.printStackTrace();
		 }
		 return null;
	}
	
	/**
	 * 复制两个对象相同属性名的值
	 * @param source
	 * @param dest
	 * @throws Exception
	 */
	public static void copy(Object source, Object dest)throws Exception {  
        //获取属性  
        BeanInfo sourceBean = Introspector.getBeanInfo(source.getClass(), Object.class);
        PropertyDescriptor[] sourceProperty = sourceBean.getPropertyDescriptors();
        BeanInfo destBean = Introspector.getBeanInfo(dest.getClass(), Object.class);
        PropertyDescriptor[] destProperty = destBean.getPropertyDescriptors();  
        try{  
            for(int i=0,source_len = sourceProperty.length;i<source_len;i++){  
                for(int j=0,dest_len = destProperty.length;j<dest_len;j++){  
                    if(sourceProperty[i].getName().equals(destProperty[j].getName())){  
                        //调用source的getter方法和dest的setter方法  
                    	if(null != destProperty[j].getWriteMethod()){
                    		destProperty[j].getWriteMethod().invoke(dest, sourceProperty[i].getReadMethod().invoke(source));  
                    	}
                        break;                    
                    }  
                }  
            }  
        }catch(Exception e){  
            throw new Exception("属性复制失败:"+e.getMessage());  
        }  
    }  
}
