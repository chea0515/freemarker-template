package com.cc.framework.freemarker.template.common;

/**
 * @author chea0515@163.com
 * 模板内容 输出信息
 */
public class TemplateOutInfo {

    /** 文件名称 */
    private String fileName;
    /** 输出路径 */
    private String outPath;

    public TemplateOutInfo() {}

    public TemplateOutInfo(String fileName, String outPath) {
        this.fileName = fileName;
        this.outPath = outPath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }
}
