package com.cw.bookappointment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.entity.PageModel;
@Repository
public interface IBookDao extends IGenericDao<Book, Integer>{

	public List<Book> listByPage(PageModel pageModel);
	public int decreaseNumberByVersion(Book book);
}
