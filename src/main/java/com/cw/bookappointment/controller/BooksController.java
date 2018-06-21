package com.cw.bookappointment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.service.BooksService;
import com.cw.bookappointment.serviceImpl.BooksServiceImpl;
import com.cw.bookappointment.util.EnvMyBean;
import com.cw.bookappointment.util.ResultOperationUtil;
import com.cw.bookappointment.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/books")
public class BooksController {

    private Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    private BooksServiceImpl booksService;

    @Autowired
    private EnvMyBean envMyBean;

    @RequestMapping(value = "/list" , method = RequestMethod.POST)
    public JSONObject list(@RequestBody JSONObject data){
        Integer pageNum = data.getInteger("pageNum");
        Integer pageCount = data.getInteger("pageCount");
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");

        if(pageNum == null || pageNum <= 0 || pageCount == null || pageCount <= 0
                || null == timeStamp || StringUtils.isEmpty(sign)){
            logger.error("查询图书接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }
        //校验秘钥是否通过
        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
            logger.error("查询图书接口：身份认证失败！参数：" + data.toJSONString());
            return ResultOperationUtil.generateFailResult("身份认证失败！");
        }
//        return booksService.listAll( pageNum,pageCount );
        return booksService.listAllByMemory(pageNum,pageCount);
    }

    @RequestMapping(value = "/listByQuery" , method = RequestMethod.POST)
    public JSONObject listByQuery(@RequestBody JSONObject data){
        String queryWord = data.getString("queryWord");
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");

        if (StringUtils.isEmpty(queryWord) || StringUtils.isEmpty(sign) || null == timeStamp){
            logger.error("关键词查询图书接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }
        //校验秘钥是否通过
        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
            logger.error("查询图书接口：身份认证失败！参数：" + data.toJSONString());
            return ResultOperationUtil.generateFailResult("身份认证失败！");
        }
        return booksService.listByQuery(queryWord);
    }

    @RequestMapping(value = "/addBook",method = RequestMethod.POST)
    public JSONObject addBook(@RequestBody JSONObject data){
        String name = data.getString("name");
        String introduction = data.getString("introduction");
        Integer number = data.getInteger("number");
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(introduction) || null == number
                || null == timeStamp || StringUtils.isEmpty(sign)){
            logger.error("增加图书接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }
        //校验秘钥是否通过
        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
            logger.error("查询图书接口：身份认证失败！参数：" + data.toJSONString());
            return ResultOperationUtil.generateFailResult("身份认证失败！");
        }
        //直接将json转化为对象
        Book book = JSON.parseObject(data.toJSONString(),Book.class);
        return booksService.addBook(book);
    }

    @RequestMapping(value = "/appointmentBook",method = RequestMethod.POST)
//    public JSONObject appointmentBook(@RequestBody JSONObject data, @SessionAttribute String studentNum){
    public JSONObject appointmentBook(@RequestBody JSONObject data){
        String studentNum = data.getString("studentNum");
        Integer bookId = data.getInteger("bookId");
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");

        if (null == bookId || null == timeStamp || StringUtils.isEmpty(sign)){
            logger.error("预约图书接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }
        //校验秘钥是否通过
        if(!SignUtil.isSignValid(sign,data,envMyBean.getProperty("secret"))){
            logger.error("查询图书接口：身份认证失败！参数：" + data.toJSONString());
            return ResultOperationUtil.generateFailResult("身份认证失败！");
        }
        if (StringUtils.isEmpty(studentNum)){
            return ResultOperationUtil.generateFailResult("登录认证失败！");
        }
        return  booksService.appointment(studentNum,bookId);
    }

    @RequestMapping(value = "/findAppointmentBooks",method = RequestMethod.POST)
    public JSONObject findAppointmentBooks(@RequestBody JSONObject data, @SessionAttribute String studentNum){
        Long timeStamp = data.getLong("timeStamp");
        String sign = data.getString("sign");

        if (null == timeStamp || StringUtils.isEmpty(sign)){
            logger.error("查询预约详情接口:参数校验失败！exception: " +  data.toJSONString());
            return ResultOperationUtil.generateFailResult("参数异常！");
        }

        if (StringUtils.isEmpty(studentNum)){
            return ResultOperationUtil.generateFailResult("登录认证失败！");
        }

        return booksService.findAppointmentBooks(studentNum);
    }

}
