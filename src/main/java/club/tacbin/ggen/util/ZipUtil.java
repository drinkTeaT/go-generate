package club.tacbin.ggen.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @description:
 * @author: tacbin
 * @date: 2021-11-09
 **/
@Slf4j
public class ZipUtil {
    public static void downloadZip(String sourcePath, HttpServletResponse response) {
        try {
            //将目标文件压缩为ZIP并下载
            ZipUtil.zip(sourcePath, response);
        } catch (Exception e) {
            log.error("html压缩" + e.getMessage(), e);
        }
    }


    public static void zip(String sourceFileName, HttpServletResponse response) {

        try (ZipOutputStream out = new ZipOutputStream(response.getOutputStream()); BufferedOutputStream bos = new BufferedOutputStream(out)) {
            //将zip以流的形式输出到前台
            response.setHeader("content-type", "application/octet-stream");
            response.setCharacterEncoding("utf-8");
            // 设置浏览器响应头对应的Content-disposition
            //参数中 testZip 为压缩包文件名，尾部的.zip 为文件后缀
            response.setHeader("Access-Control-Expose-Headers","Content-disposition");
            response.setHeader("Content-disposition",
                    "attachment;filename=" + new String("testZip".getBytes("gbk"), "iso8859-1") + ".zip");
            //创建zip输出流
            //创建缓冲输出流
            File sourceFile = new File(sourceFileName);
            //调用压缩函数
            compress(out, bos, sourceFile, sourceFile.getName());
            out.flush();
            log.info("压缩完成");
        } catch (Exception e) {
            log.error("ZIP压缩异常：" + e.getMessage(), e);
        }
    }

    public static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) {

        try {
            //如果路径为目录（文件夹）
            if (sourceFile.isDirectory()) {
                //取出文件夹中的文件（或子文件夹）
                File[] fileList = sourceFile.listFiles();
                //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
                if (fileList.length == 0) {
                    out.putNextEntry(new ZipEntry(base + "/"));
                } else {//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                    for (int i = 0; i < fileList.length; i++) {
                        compress(out, bos, fileList[i], base + "/" + fileList[i].getName());
                    }
                }
            } else {//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
                try (FileInputStream fos = new FileInputStream(sourceFile);
                     BufferedInputStream bis = new BufferedInputStream(fos)) {
                    out.putNextEntry(new ZipEntry(base));
                    int tag;
                    //将源文件写入到zip文件中
                    while ((tag = bis.read()) != -1) {
                        out.write(tag);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
