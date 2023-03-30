package com.common.utils.upload;

import com.common.utils.*;
import com.common.utils.enums.FileType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class FileUpload {

	/**
	 * 上传文件
	 * @param is
	 * @return
	 */
	public static String uploadFile(InputStream is,String name,String fileHeader,String fileLocation)throws Exception{
		String url = fileHeader;
		String time = DateUtils.getNowDateTimeStr("yyyyMMdd");
		FileOutputStream fos = null;
		String folder = fileLocation+time;

		File secondFolder = new File(folder);
		if(!secondFolder.exists()){
			secondFolder.mkdirs();
		}
		String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
		String fileName = RandomNumber.randomUUidPK()+"."+suffix;
		String timeandfilename=time+"/"+fileName;
		url+=time+"/"+fileName;
		String fileAddress = folder+"/"+fileName;
		fos = new FileOutputStream(fileAddress);
		byte buffer[]=new byte[4*1024];
		int len = 0;
		while((len = is.read(buffer)) != -1)
		{
			fos.write(buffer,0,len);
		}
		fos.close();
		is.close();
		return url;
	}

	/**
	 * 上传图片
	 */
	public static String uploadImg(InputStream is,String suffix)throws Exception{
		String url = Constants.FILE_HEADER;
		String time = DateUtils.getNowDateTimeStr("yyyyMMdd");
		FileOutputStream fos = null;
		String folder = Constants.FILE_LOCATION+time;
		File secondFolder = new File(folder);
		if(!secondFolder.exists()){
			secondFolder.mkdirs();
		}
		String fileName = RandomNumber.randomUUidPK()+"."+suffix;
		url+=time+"/"+fileName;
		String fileAddress = folder+"/"+fileName;
		fos = new FileOutputStream(fileAddress);
		byte buffer[]=new byte[4*1024];
		int len = 0;
		while((len = is.read(buffer)) != -1)
		{
			fos.write(buffer,0,len);
		}
		fos.close();
		is.close();
		return url;
	}

	/**
	 * 压缩图片
	 * @param inputStream
	 * @param scale
	 * @return
	 * @throws Exception
	 */
	public static InputStream thumbImage(InputStream inputStream,int scale)throws Exception{
		if(scale <= 0){
			throw new Exception("scale must not be less than zero");
		}
		BufferedImage srcImage = ImageIO.read(inputStream);
		int width = srcImage.getWidth();
		int height = srcImage.getHeight();
		width /= scale;
		height /= scale;
		BufferedImage targetImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		targetImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(targetImage, "jpg", baos);
		return new ByteArrayInputStream(baos.toByteArray());
	}



	public static String uploadFilesuffix(InputStream is,String suffix)throws Exception{
		String url = Constants.FILE_HEADER;
		String time = DateUtils.getNowDateTimeStr("yyyyMMdd");
		FileOutputStream fos = null;
		String folder = Constants.FILE_LOCATION+time;
		File secondFolder = new File(folder);
		if(!secondFolder.exists()){
			secondFolder.mkdirs();
		}
		String fileName = RandomNumber.randomUUidPK()+"."+suffix;
		url+=time+"/"+fileName;
		String fileAddress = folder+"/"+fileName;
		fos = new FileOutputStream(fileAddress);
		byte buffer[]=new byte[4*1024];
		int len = 0;
		while((len = is.read(buffer)) != -1)
		{
			fos.write(buffer,0,len);
		}

		fos.close();
		is.close();
		return url;
	}

	//文件类型
	private static Map<String,String> mFileTypes = new HashMap<String,String>();

	static {
		/* 图片文件 start */
		mFileTypes.put("jpg", "1");
		mFileTypes.put("png", "1");
		mFileTypes.put("gif", "1");
		mFileTypes.put("tif", "1");
		mFileTypes.put("424D", "1");
		mFileTypes.put("jpeg", "1");
		/* 图片文件 end */

		/* 文档 start */
		mFileTypes.put("pdf","2");
		mFileTypes.put("xls","2");
		mFileTypes.put("doc","2");
		mFileTypes.put("wps","2");
		mFileTypes.put("rtf","2");
		mFileTypes.put("txt","2");
		/* 文档 end */

		/* 音频 start */
		mFileTypes.put("MP3","3");
		mFileTypes.put("MPEG","3");
		mFileTypes.put("WMA","3");
		mFileTypes.put("CDA","3");
		mFileTypes.put("RA","3");
		/* 音频 end */

		/* 视频 start */
		mFileTypes.put("wmv","4");
		mFileTypes.put("asf","4");
		mFileTypes.put("asx","4");
		mFileTypes.put("rm","4");
		mFileTypes.put("rmvb","4");
		mFileTypes.put("mp4","4");
		mFileTypes.put("3gp","4");
		mFileTypes.put("mov","4");
		mFileTypes.put("m4v","4");
		mFileTypes.put("avi","4");
		mFileTypes.put("dat","4");
		mFileTypes.put("mkv","4");
		mFileTypes.put("flv","4");
		mFileTypes.put("vob","4");
		/* 视频 start */

	}
	/**
	 * 通过文件名获取文件内容
	 * 截取文件的后缀名 判断是什么文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static Integer getFileType(String fileName){
		Integer fileType = FileType.OTHER.getType();
		String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
		String mapType = mFileTypes.get(suffix);
		if(StringUtils.isNotBlank(mapType)){
			fileType = TypeConversion.StringToInteger(mapType);
		}
		return fileType;
	}


	/**
	 * 获取文件大小
	 * @param available
	 * @return
	 * @throws Exception
	 */
	public static String getFileSize(Integer available)throws Exception{
		String fileSize = "";
		String[] unitNames = {"B","KB","M","G"};
		Integer unitType = 0;
		while (available > 1024 && unitType < 4){
			available = available / 1024;
			unitType++;
		}
		fileSize = available + unitNames[unitType];
		return fileSize;
	}

}
