package com.cw.bookappointment.Listener;

import com.cw.bookappointment.dao.IBookDao;
import com.cw.bookappointment.entity.Book;
import com.cw.bookappointment.serviceImpl.BooksServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

@Controller
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    IBookDao bookDao;

    @Autowired
    BooksServiceImpl booksService;

    int times;

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null){
            System.out.println("初始化ScheduleTask!!");
            init();
        }
    }
    public void init(){
        initScheduleTask();
    }
    public void initScheduleTask(){
        ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("refreshBookListThread");
                return thread;
            }
        });

        Runnable scheduleTask = new Runnable() {
            public void run() {
                System.out.println("第【" + (++times) + "】次加载bookList, 时间为：【" + System.currentTimeMillis() +"】");
                checkout();
            }
        };
        schedule.scheduleWithFixedDelay(scheduleTask,0,20,TimeUnit.SECONDS);
    }

    private void checkout(){
        List<Book>  newBookList = listBook();
        List<Book>  oldBookList = booksService.getAllBooks();
        if (oldBookList == null || !equal(oldBookList,newBookList) ){
            booksService.setAllBooks(newBookList);
        }
    }

    private List<Book> listBook(){
        List<Book> bookList = null;
        try{
            bookList = bookDao.list(new Book());
        }catch (Exception e){
            e.printStackTrace();
        }
        return bookList;
    }

    private boolean equal(List<Book> oldBookList, List<Book> newBookList){
        if (oldBookList.size() != newBookList.size()){
            return false;
        }
        for (int i = 0; i < oldBookList.size(); i++) {
            if (!oldBookList.get(i).equals(newBookList.get(i))){
                return false;
            }
        }
        return true;
    }
}
