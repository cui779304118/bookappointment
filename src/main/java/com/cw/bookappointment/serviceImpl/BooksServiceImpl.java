package com.cw.bookappointment.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.constant.Constant;
import com.cw.bookappointment.dao.IAppointmentDao;
import com.cw.bookappointment.dao.IBookDao;
import com.cw.bookappointment.entity.Appointment;
import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.entity.PageModel;
import com.cw.bookappointment.service.BooksService;
import com.cw.bookappointment.util.ResultOperationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("booksService")
public class BooksServiceImpl implements BooksService {
    private Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);

    @Autowired
    private IBookDao bookDao;
    @Autowired
    private IAppointmentDao appointmentDao;

    public JSONObject listAll(int pageNum, int pageCount) {
        PageModel pageModel = new PageModel();
        Integer recordCount = countBooks(new Book());
        if (recordCount == null || recordCount == 0){
            return ResultOperationUtil.generateFailResult("查询异常！");
        }
        pageModel.setRecordCount(recordCount);
        pageModel.setOffset((pageNum-1)*pageCount);
        pageModel.setPageCount(pageCount);

        List<Book> bookList = bookDao.listByPage(pageModel);
        if (bookList == null || bookList.size() == 0){
            return ResultOperationUtil.generateFailResult("查询异常！");
        }

        JSONObject data = new JSONObject();
        data.put("bookList",bookList);
        return ResultOperationUtil.generateSuccessResult(data);
    }

    public JSONObject listByQuery(String queryWord) {
        Book book = new Book();
        book.setName(queryWord);
        List<Book> bookList = bookDao.list(book);
        if (bookList == null || bookList.size() == 0){
            return ResultOperationUtil.generateFailResult("查询异常！");
        }

        JSONObject data = new JSONObject();
        data.put("bookList",bookList);
        return ResultOperationUtil.generateSuccessResult(data);
    }
    
    public Integer countBooks(Book book){
        Integer recordCount = null;
        try{
            recordCount = bookDao.count(book);
        }catch (Exception e){
            logger.error("bookService:查询图书总数异常,exception:{}", new Object[]{e.getMessage()});
        }
        return  recordCount;
    }

    public JSONObject addBook(Book book) {
        try{
            bookDao.save(book);
        }catch (Exception e){
            logger.error("bookService:添加图书异常,exception:{}", new Object[]{e.getMessage()});
        }
        Integer bookId = book.getBookId();
        if(bookId == null){
            return ResultOperationUtil.generateFailResult("添加图书失败！");
        }
        JSONObject data = new JSONObject();
        data.put("bookId",bookId);
        return ResultOperationUtil.generateSuccessResult(data);
    }

    public JSONObject appointment(String studentNum, int bookId) {
        JSONObject returnData = null;
        int appointmentResult = executeAppointment(studentNum, bookId);
        switch (appointmentResult){
            case Constant.APPOINTMENT_SUCCESS:
                returnData = ResultOperationUtil.generateCodeResult(0, "预约成功！");
                break;
            case Constant.FAIL_LACK_STOCK:
                returnData = ResultOperationUtil.generateFailResult("预约失败，库存不足！");
                break;
            case Constant.FAIL_REPEAT_APPOINTMENT:
                returnData = ResultOperationUtil.generateFailResult("预约失败，您已经预约过该本书了！");
                break;
            default:
                returnData = ResultOperationUtil.generateFailResult("预约失败,系统内部原因！");
                break;
        }
        return returnData;
    }

    private int executeAppointment(String studentNum, int bookId){
        //如果该书已经被预约过了
        if(isRepeatAppointment(studentNum,bookId)){
            return Constant.FAIL_REPEAT_APPOINTMENT;
        }
        Long startTime = System.currentTimeMillis();
        while(true){
            Long endTime = System.currentTimeMillis();
            //默认CAS100ms
            if (endTime - startTime > Constant.TRY_TIME){
                return Constant.FAIL_UNKNOW;
            }
            try{
                Book book = bookDao.get(bookId);
                //如果没有该书相关的信息，则直接返回失败
                if (book == null) return Constant.FAIL_UNKNOW;
                //该书库存不足
                if (book.getNumber() <= 0){
                    return Constant.FAIL_LACK_STOCK;
                }
                //更新失败，可能是由于并发原因
                int update = bookDao.decreaseNumberByVersion(book);
                if (update == 0 ) continue;
                //插入预约记录
                Appointment appointment = new Appointment();
                appointment.setStudentNum(studentNum);
                appointment.setBookId(bookId);
                appointmentDao.save(appointment);
                //返回预约成功
                return Constant.APPOINTMENT_SUCCESS;
            }catch (Exception e){
                logger.error("bookService:执行预约逻辑失败！exception:{}", new Object[]{e.getMessage()});
                return Constant.FAIL_UNKNOW;
            }
        }
    }

    private boolean isRepeatAppointment(String studentNum,int bookId){
        boolean isRepeatAppointment = false;

        Appointment appointment = new Appointment();
        appointment.setBookId(bookId);
        appointment.setStudentNum(studentNum);

        List<Appointment> appointments = queryAppointment(appointment);
        if (appointments == null){
           isRepeatAppointment = true;
        }
        if (appointments.size() != 0){
            isRepeatAppointment = true;
        }
        return isRepeatAppointment;
    }

    public List<Appointment> queryAppointment(Appointment appointment){
        List<Appointment> resultList = null;
        try{
            resultList = appointmentDao.list(appointment);
        }catch (Exception e){
            logger.error("bookService:查询预约记录异常,exception:{}", new Object[]{e.getMessage()});
        }
        return resultList;
    }

    public JSONObject findAppointmentBooks(String studentNum) {
        Appointment appointment = new Appointment();
        appointment.setStudentNum(studentNum);
        List<Appointment> appointmentResults = queryAppointment(appointment);
        if (null == appointmentResults || appointmentResults.size() == 0){
            return ResultOperationUtil.generateFailResult("您没有预约任何图书！");
        }
        List<Integer> ids = new ArrayList<Integer>(appointmentResults.size());
        for(Appointment a : appointmentResults){
            ids.add(a.getBookId());
        }
        List<Book> bookList = getBooksByIds(ids);
        if(null == bookList || bookList.size() == 0){
            return ResultOperationUtil.generateFailResult("查询失败！系统内部错误！");
        }

        JSONObject data = new JSONObject();
        data.put("bookList",bookList);
        return ResultOperationUtil.generateSuccessResult(data);
    }

    public List<Book> getBooksByIds(List<Integer> ids){
        List<Book> bookList = null;
        try{
            bookList = bookDao.getbyIdList(ids);
        }catch (Exception e){
            logger.error("bookService:通过ids查询bookList异常,exception:{}", new Object[]{e.getMessage()});
        }
        return  bookList;
    }
}
