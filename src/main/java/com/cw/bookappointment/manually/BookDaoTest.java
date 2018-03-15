package com.cw.bookappointment.manually;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cw.bookappointment.dao.IBookDao;
import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.entity.PageModel;

public class BookDaoTest {

	public static void main(String[] args) {
		String path = "classpath:env/dev/applicationContext.xml";
		IBookDao bookDao = createDao(path);
//		testInsert(bookDao);
//		testUpdate(bookDao);
		testList(bookDao);
//		testDelete(bookDao);

	}
	public static IBookDao createDao(String path){
		ApplicationContext context = new ClassPathXmlApplicationContext(path);
		IBookDao bookDao = (IBookDao) context.getBean(IBookDao.class);
		return bookDao;
	}
	
	public static Book createBook(String name,String introdction,Integer number){
		Book book = new Book();
		book.setName(name);
		book.setIntrodction(introdction);
		book.setNumber(number);
		return book;
	}
	
	public static void testInsert(IBookDao bookDao){
		Book book = createBook("java虚拟机", "关于java底层的书", 30);
		bookDao.save(book);
		System.out.println("插入book成功！bookId= " + book.getBookId());
	}
	public static void testUpdate(IBookDao bookDao){
		Book book = createBook("疯狂讲义js", "关于前台的书", 29);
		book.setBookId(1007);
		bookDao.update(book);
		System.out.println("更新book成功！");
	}
	public static void testList(IBookDao bookDao){
		Book book = new Book();
		Integer count = bookDao.count(book);
		System.out.println("总共有：（ " + count + "） 本书！");
		
		PageModel pageModel = new PageModel();
		pageModel.setOffset(0);
		pageModel.setPageCount(3);
		List<Book> list = bookDao.listByPage(pageModel);
		for(Book b : list){
			System.out.println(b);
		}
	}
	public static void testDelete(IBookDao bookDao){
		bookDao.delete(1007);
		System.out.println("删除成功！");
	}
}
