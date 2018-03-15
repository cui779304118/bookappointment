package com.cw.bookappointment.service;

import java.util.List;

import com.cw.bookappointment.entity.Book;

public interface BookService {

	/**
	 * 
	 * @return
	 */
	public List<Book> getList();
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Book> getSomeList(String name);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Book getById(Integer id);
	
	/**
	 * 
	 * @param book
	 * @return
	 */
	public Integer addBook(Book book);
	
	/**
	 * 
	 * @param book
	 * @return
	 */
	public boolean updateBook(Book book);
	/**
	 * 	
	 * @param id
	 * @return
	 */
	public boolean deleteBook(Integer id);
}
