package com.rzyc.fulongapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "onstants")
public class ConstantsConfigure {


    //上传文件存放地址
    @Value("${onstants.file_location}")
    private String fileLocation;

    //上传文件公共地址
    @Value("${onstants.file_header}")
    private String fileHeader;

    //上传文件基础路径
    @Value("${onstants.service_file_header}")
    private String serviceFileHeader;

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getFileHeader() {
        return fileHeader;
    }

    public void setFileHeader(String fileHeader) {
        this.fileHeader = fileHeader;
    }

    public String getServiceFileHeader() {
        return serviceFileHeader;
    }

    public void setServiceFileHeader(String serviceFileHeader) {
        this.serviceFileHeader = serviceFileHeader;
    }
}
