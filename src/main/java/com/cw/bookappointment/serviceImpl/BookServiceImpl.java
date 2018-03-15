package com.cw.bookappointment.serviceImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cw.bookappointment.controller.BookController;
import com.cw.bookappointment.dao.IAppointmentDao;
import com.cw.bookappointment.dao.IBookDao;
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
}
