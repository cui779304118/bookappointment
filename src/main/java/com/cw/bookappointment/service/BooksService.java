package com.cw.bookappointment.service;

import com.alibaba.fastjson.JSONObject;
import com.cw.bookappointment.entity.Book;

public interface BooksService {

    public JSONObject listAll(int pageNum, int pageCount);
    public JSONObject listByQuery(String queryWord);
    public JSONObject addBook(Book book);
    public JSONObject appointment(String studentNum, int bookId);
    public JSONObject findAppointmentBooks(String studentNum);
    public int executeAppointment(String studentNum, int bookId);
}
