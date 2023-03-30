package com.common.utils.enums;

/**
 * 文件类型 1：图片 2:文档 3：音频 4：视频 5:其他
 */
public enum FileType {

    IMG(1),
    DOC(2),
    AUDIO(3),
    VIDEO(4),
    OTHER(5);

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    FileType(Integer type) {
        this.type = type;
    }
}
