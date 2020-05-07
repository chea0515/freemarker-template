package com.cc.framework.freemarker.template.utils;

import com.cc.framework.freemarker.template.common.TemplateOutInfo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * @author chea0515@163.com
 */
public class FreemarkerUtils {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerUtils.class);

    private static final Resource CLASSPATH_RESOURCE = new ClassPathResource("/static/");

    /** 默认模板路径 */
    private static final String DEFAULT_TEMPLATE_DIR;
    /** 默认输出路径 */
    private static final String DEFAULT_OUT_DIR;

    /** 默认配置项 */
    private static final Configuration TEMPLATE_CFG;
    static {
        TEMPLATE_CFG = new Configuration(Configuration.VERSION_2_3_29);
        String defaultTemplateDir = "", defaultOutDir = "";
        try {
            defaultTemplateDir = CLASSPATH_RESOURCE.getURL().getPath();
            defaultOutDir = defaultTemplateDir + "out";

            // 默认路径
            TEMPLATE_CFG.setDirectoryForTemplateLoading(new File(defaultTemplateDir));
            TEMPLATE_CFG.setDefaultEncoding("UTF-8");
            TEMPLATE_CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DEFAULT_TEMPLATE_DIR = defaultTemplateDir;
        DEFAULT_OUT_DIR = defaultOutDir;
    }

    /**
     * 生成静态模板页面
     * @param templateDir   模板所在路径
     * @param templateName  模板名称
     * @param outDir        输出路径
     * @param fillData      填充内容
     */
    public static TemplateOutInfo createTemplateFile(String templateDir, String templateName, String outDir, Object fillData) {
        TemplateOutInfo resultInfo = null;
        Writer writerFile = null;
        try {
            // 检查模板文件路径
            checkTemplateDir(templateDir);
            // 重置模板路径
            resetTemplateDir(templateDir);
            // 检查是否有输出文件夹
            File outFileDir = new File(outDir);
            if(!outFileDir.exists()) {
                outFileDir.mkdirs();
            }
            String outFileName = templateName.substring(0, templateName.lastIndexOf("."));
            // 初始化输出文件
            String outFilePath = outDir + File.separator + outFileName + ".html";
            outFilePath = outFilePath.replaceAll("\\\\", "/");
            File outFile = new File(outFilePath);
            if(outFile.exists()) {
                outFile.delete();
            }
            outFile.createNewFile();

            // 模板处理
            Template template = TEMPLATE_CFG.getTemplate(templateName);
            writerFile = new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8);

            // 模板内容替换并输出
            template.process(fillData, writerFile);

            resultInfo = new TemplateOutInfo(templateName, outFilePath);

            log.info("模板内容输出：\r\n --> 模板文件名称: {} \r\n --> 输出文件路径: {}", templateName, outFilePath);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(writerFile != null) {
                    writerFile.close();
                }
            } catch (IOException ignored) {
            }
        }

        return resultInfo;
    }

    /**
     * 生成静态模板页面
     * @param templateName  模板名称
     * @param fillData      填充内容
     */
    public static TemplateOutInfo createTemplateFile(String templateName, Object fillData) {
        // 使用默认的文件地址
        return createTemplateFile(DEFAULT_TEMPLATE_DIR, templateName, DEFAULT_OUT_DIR, fillData);
    }

    private static void checkTemplateDir(String templateDir) {
        File templateFileDir = new File(templateDir);
        if(!templateFileDir.exists()) {
            throw new RuntimeException("模板文件夹不存在");
        }
    }

    private static void resetTemplateDir(String newDir) throws IOException {
        if(!DEFAULT_TEMPLATE_DIR.equals(newDir)) {
            // 重置模板路径
            TEMPLATE_CFG.setDirectoryForTemplateLoading(new File(newDir));
        }
    }
}
