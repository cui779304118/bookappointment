package com.cw.bookappointment.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cw.bookappointment.constant.Constant;
import com.cw.bookappointment.controller.BookController;
import com.cw.bookappointment.dao.IAppointmentDao;
import com.cw.bookappointment.dao.IBookDao;
import com.cw.bookappointment.entity.Appointment;
import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.service.BookService;

public class BookServiceImpl implements BookService {

	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	@Autowired
	private IBookDao bookDao;
	@Autowired
	private IAppointmentDao appointmentDao;
	
	public List<Book> getList() {
		Book book = new Book();
		List<Book> list = null;
		try {
			list = bookDao.list(book);
		} catch (Exception e) {
			logger.error("bookService: 查询列表异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return list;
	}

	public List<Book> getSomeList(String name) {
		Book book = new Book();
		book.setName(name);
		List<Book> bookList = null;
		try {
			bookList = bookDao.list(book);
		} catch (Exception e) {
			logger.error("bookService: 查询列表异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return bookList;
	}
	
	public Book getById(Integer bookId){
		Book book = null;
		try {
			book = bookDao.get(bookId);
		} catch (Exception e) {
			logger.error("bookService: 查询book异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return book;
	}

	@Override
	public Integer addBook(Book book) {
		try {
			bookDao.save(book);
		} catch (Exception e) {
			logger.error("bookService: 插入book异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return book.getBookId();
	}

	@Override
	public boolean updateBook(Book book) {
		boolean isUpdateSuccess = false;
		try {
			bookDao.update(book);
			isUpdateSuccess = true;
		} catch (Exception e) {
			logger.error("bookService: 更新book异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return isUpdateSuccess;
	}

	@Override
	public boolean deleteBook(Integer bookId) {
		boolean isDeleteSuccess = false;
		try {
			bookDao.delete(bookId);
			isDeleteSuccess = true;
		} catch (Exception e) {
			logger.error("bookService: 删除book异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return isDeleteSuccess;
	}
	@Override
	public Integer appoint(String studentNum, String bookId){
		try {
			Appointment appointmentSel = appointmentDao.getByStuIdAndBookId(studentNum, bookId);
			if(null != appointmentSel){
				return Constant.FAIL_REPEAT_APPOINTMENT;
			}
			Book bookSel = bookDao.get(Integer.valueOf(bookId));
			Integer stock = bookSel.getNumber();
			if( stock < 0){
				return Constant.FAIL_LACK_STOCK;
			}
			synchronized (this) {
				if(stock > 0){
					bookSel.setNumber(stock - 1);
					bookDao.update(bookSel);
					if(!generateAppointment(studentNum,bookId)){
						return Constant.FAIL_UNKNOW;
					}
				}else{
					return Constant.FAIL_LACK_STOCK;
				}
			}
			return Constant.APPOINTMENT_SUCCESS;
		} catch (Exception e) {
			logger.error("bookService: 执行appointment异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return Constant.FAIL_UNKNOW;
	}

	private boolean generateAppointment(String studentNum, String bookId) {
		Appointment appointment = new Appointment();
		appointment.setBookId(Integer.valueOf(bookId));
		appointment.setStudentNum(studentNum);
		try {
			appointmentDao.save(appointment);
			return true;
		} catch (Exception e) {
			logger.error("bookService: 插入appointment异常，exception:{} " , new Object[]{e.getMessage()});
		}
		return false;
	}
}
