package com.cw.bookappointment.controller;

import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.constant.Constant;
import com.cw.bookappointment.util.ResultOperationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController  {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value = "/uploadBookPic",method = RequestMethod.POST)
    public JSONObject uploadBookPic(@RequestParam(value = "bookPic",required = false) MultipartFile multipartFile, HttpServletRequest request){
        String bookId = request.getParameter("bookId");
        if (StringUtils.isEmpty(bookId) || multipartFile.isEmpty()){
            return ResultOperationUtil.generateFailResult("关键参数为空！");
        }
        String originName = multipartFile.getOriginalFilename();
        if (!isPicFormRegix(originName)){
            return ResultOperationUtil.generateFailResult("文件格式不对！");
        }
        //上传路径
        String path = request.getServletContext().getRealPath("/bookPic/");
        System.out.println("path:" + path);
        //上传文件名
        String fileName = System.currentTimeMillis()/1000 + "_" + String.valueOf(bookId) + "." + getFileSuffix(originName);
        System.out.println("fileName:" + fileName);
        File filePath = new File(path,fileName);
        //判断路径是否存在，如果不存在就创建一个
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(filePath);
        } catch (IOException e) {
            logger.error("上传图片失败！exception:{}", new Object[]{e.getMessage()});
        }
        return ResultOperationUtil.generateCodeResult(0,"上传成功！");
    }

    private static  boolean isPicForm(String file){
        String suffix = getFileSuffix(file);
        if(Constant.PIC_FORM_SET.contains(suffix)){
            return true;
        }
        return false;
    }

    private static boolean isPicFormRegix(String fileName){
        String pattern = ".+\\.(jpg|jpeg|png|PNG|JPEG|JPG)";
        if(fileName.matches(pattern)){
            return true;
        }
        return false;
    }

    private static String getFileSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void main(String[] args) {
        String fileName = "111.jpg";
        System.out.println(getFileSuffix(fileName));
        System.out.println(isPicForm(fileName));

    }
}
