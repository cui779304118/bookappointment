package com.cw.bookappointment.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
	
	private static Logger logger = LoggerFactory.getLogger(BookController.class);
	
//	@Autowired
	BookService bookService;
	
	//获取图书馆列表
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView List(ModelAndView mv){
		List<Book> list = bookService.getList();
		mv.addObject("list", list);
		mv.setViewName("list");
		return mv;
	}
	
	//搜索是否有某图书
	@RequestMapping(value="/search",method = RequestMethod.POST)
	public void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//接受页面的值
		String name = req.getParameter("name");
		name = name.trim();
		//向页面传值
		req.setAttribute("name", name);
		req.setAttribute("list", bookService.getSomeList(name));
		req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
	}
	

}
