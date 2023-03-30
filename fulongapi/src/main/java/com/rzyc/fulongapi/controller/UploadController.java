package com.rzyc.fulongapi.controller;

import com.common.utils.RandomNumber;
import com.common.utils.StringUtils;
import com.common.utils.TypeConversion;
import com.common.utils.upload.FileUpload;
import com.rzyc.fulongapi.bean.FileResult;
import com.rzyc.fulongapi.bean.FileResults;
import com.rzyc.fulongapi.mapper.SysDocumentMapper;
import com.rzyc.fulongapi.model.SysDocument;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Api(tags = "文件上传")
@RestController
@CrossOrigin("*")
public class UploadController extends BaseController{

    //文件接口
    @Autowired
    private SysDocumentMapper sysDocumentMapper;

    private static Map<String,String> mFileTypes = new HashMap<String,String>();

    static {
        /* 图片文件 start */
        mFileTypes.put("jpg", "1");
        mFileTypes.put("png", "1");
        mFileTypes.put("gif", "1");
        mFileTypes.put("jpeg", "1");
        /* 图片文件 end */

        /* 文档 start */
        mFileTypes.put("pdf","2");
        mFileTypes.put("xls","2");
        mFileTypes.put("xlsx","2");
        mFileTypes.put("doc","2");
        mFileTypes.put("docx","2");
        mFileTypes.put("wps","2");
        mFileTypes.put("rtf","2");
        mFileTypes.put("txt","2");
        /* 文档 end */

        /* 音频 start */
        mFileTypes.put("MP3","3");
        /* 音频 end */

        /* 视频 start */
        mFileTypes.put("rmvb","4");
        mFileTypes.put("mp4","4");
        mFileTypes.put("avi","4");
        /* 视频 start */

    }


    /**
     * 获取文件头
     * @param src
     * @return
     * @throws Exception
     */
    public static String bytesToHexString(byte[] src) throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 新增文件
     * @param targetId
     * @param targetType
     * @param fileUrl
     * @param fileType
     * @throws Exception
     */
    public SysDocument addDocument(String targetId, String targetType,
                                   String fileUrl, String fileType, String fileName,
                                   String userId, String fileSize)throws Exception{
        fileUrl = delServiceFile(fileUrl);
        SysDocument document = new SysDocument();
        document.setDocumentId(RandomNumber.getUUid());
        document.setFilePath(fileUrl);
        document.setName(fileName);
        document.setFileType(TypeConversion.StringToInteger(fileType));
        document.setFileSize(fileSize);
        document.setTargetId(targetId);
        document.setTargetType(targetType);
        document.setCreateTime(new Date());
        document.setCreated(userId);
        sysDocumentMapper.insert(document);
        return document;
    }

    /**
     * 文件上传
     * @param userId
     * @param userId
     * @param targetType
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = false, dataType = "string"),
            @ApiImplicitParam(name = "targetId", value = "目标uuid", required = true, dataType = "string"),
            @ApiImplicitParam(name = "targetType", value = "文件类型", required = true, dataType = "string")
    })
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public FileResult upload(String userId, String targetId, String targetType, MultipartFile file) {
        FileResult fileResult = new FileResult();

        Boolean success = false;
        try {
            String url = "";
            String fileName = file.getOriginalFilename();
            System.out.println("file name is -> "+fileName);
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
            suffix.toLowerCase(Locale.ROOT);
            if(StringUtils.isNotBlank(mFileTypes.get(suffix))){
                url = FileUpload.uploadFile(file.getInputStream(),fileName,constantsConfigure.getFileHeader(),constantsConfigure.getFileLocation());
                String fileSize = FileUpload.getFileSize(file.getInputStream().available());
                System.out.println("file url is -> "+url);
                String fileType = mFileTypes.get(suffix);
                SysDocument sysdocument = addDocument(targetId,targetType,url,fileType,fileName,userId,fileSize);
                handleDocument(sysdocument);
                fileResult.setData(sysdocument);
                success = true;
            }else{
                fileResult.setSuccess(false);
            }

            System.out.println("url -> "+url);
        }catch (Exception e){
            e.printStackTrace();
        }
        fileResult.setSuccess(success);
        return fileResult;
    }

    /**
     * 文件路径处理
     * @param document
     * @throws Exception
     */
    private void handleDocument(SysDocument document)throws Exception{
//        document.setFilePath(delServiceFile(document.getFilePath()));
//        document.setFilePath(setServiceFile(document.getFilePath()));
    }


    /**
     * 文件查询
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "文件查询", notes = "文件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "目标uuid 多个逗号隔开", required = true, dataType = "string"),
            @ApiImplicitParam(name = "targetType", value = "文件类型", required = true, dataType = "string")
    })
    @ResponseBody
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public FileResults getFile(String targetId, String targetType)throws Exception{
        FileResults fileResults = new FileResults();
        Boolean success = false;
        String[] strs = targetId.split(",");
        if(strs.length > 0){
            List<String> targetIds = Arrays.asList(strs);
            List<SysDocument> sysdocuments = sysDocumentMapper.findByTargetId(targetIds,targetType);
            if(sysdocuments.size() > 0){
                for (SysDocument sysdocument : sysdocuments){
                    handleDocument(sysdocument);
                }
                fileResults.setData(sysdocuments);
                success = true;
            }else{
                fileResults.setData(new ArrayList<SysDocument>());
            }
        }else{
            fileResults.setData(new ArrayList<SysDocument>());
        }
        fileResults.setSuccess(success);
        return fileResults;
    }

    /**
     * 删除文件
     * @param deletedId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "删除文件", notes = "删除文件")
    @ApiImplicitParam(name = "deletedId",value = "文档表主键 documentId",required = true)
    @ResponseBody
    @RequestMapping(value = "/delFile", method = RequestMethod.GET)
    public FileResult delFile(String deletedId)throws Exception{
        FileResult fileResult = new FileResult();
        sysDocumentMapper.deleteById(deletedId);
        fileResult.setSuccess(true);
        return fileResult;
    }





}
