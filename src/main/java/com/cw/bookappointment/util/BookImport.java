package com.cw.bookappointment.util;

import com.cw.bookappointment.service.BooksService;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;



public class BookImport implements FileReader {
    //记录当前总共执行的字节
    private static AtomicLong currentProcessLen = new AtomicLong(0L);
    private static long beginTime;

    private String inputFile;
    private int threadNum;
    private ExecutorService threadPool;
    private CountDownLatch countDownLatch;

    public BookImport(String inputFile,int threadNum){
        this.inputFile = inputFile;
        this.threadNum = threadNum;
        this.threadPool = Executors.newFixedThreadPool(threadNum);
        this.countDownLatch = new CountDownLatch(threadNum);
    }

    public BookImport(String inputFile){
        this(inputFile,1);
    }

    // 模拟测试数据
    private static void writeData(String outputFile) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        Random random = new Random();
        for (int n = 0; n < 1000; n++) {
            int count = random.nextInt(10) + 1;
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < count; i++) {
                builder.append(UUID.randomUUID().toString());
            }
            builder.append("\n");
            fileOutputStream.write(builder.toString().getBytes());
        }
        fileOutputStream.close();
        System.out.println("数据创建完成！");
    }

    public void read(Callback callback) throws IOException {
        if (StringUtils.isEmpty(inputFile)){
            System.out.println("输入路径为空！");
        }
        if (threadNum < 1){
            System.out.println("处理线程数不能小于1！");
        }
        if (callback == null){
            System.out.println("回调函数不能为空！");
        }
        RandomAccessFile raf = new RandomAccessFile(inputFile,"r");

        long totalLength = raf.length();
        System.out.println("文件总大小： " + totalLength);
        long gapLength = totalLength/threadNum;
        long[] beginIndexs = new long[threadNum];
        long[] endIndexs = new long[threadNum];

        for (int i = 0; i < threadNum ; i++) {
            beginIndexs[i] = i*gapLength;
            if (threadNum == (i+1)){
                endIndexs[i] = totalLength;
                break;
            }
            endIndexs[i] = beginIndexs[i] + gapLength -1;
        }
        raf.close();
        mulRead(beginIndexs,endIndexs,callback);
    }

    public void read(String path) {

    }

    public void logRecord(String path, String str) {

    }

    private void mulRead(final long[] beginIndexs, final long[] endIndexs, final Callback callback) throws FileNotFoundException {
        System.out.println("开始读取文件。。。");
        beginTime = System.currentTimeMillis();
        for (int i = 0; i < threadNum; i++) {
            final long begin = beginIndexs[i];
            final long end = endIndexs[i];
            threadPool.submit(new Runnable() {
                public void run() {
                    try {
                        readData(inputFile,begin,end,callback);
                        countDownLatch.countDown();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("读取文件结束。。。");
        threadPool.shutdown();
    }

    private void readData(String path,long begin,long end,Callback callback) throws IOException {
        System.out.println("线程：【" + Thread.currentThread().getName() + "】begin：【" + begin + "】end:" + "【"+ end +"】");
        RandomAccessFile raf = new RandomAccessFile(path,"r");
        RandomAccessFile rafOut = new RandomAccessFile("./out.txt","rw");
        long totalLen = raf.length();
        raf.seek(begin);
        rafOut.seek(begin);
        byte[] buf = new byte[50];
        int len;
        long currentPoint;
        while((len = raf.read(buf)) != -1 ){
            rafOut.write(buf,0,len);
//            callback.callback(line);
            if ((currentPoint = raf.getFilePointer()) == end){
                System.out.println("最终字节数：" + currentPoint);
                break;
            }
            long proceessLen = currentProcessLen.addAndGet(len);

            if (proceessLen%10240 == 0){
                double percent = (double) proceessLen / totalLen;
                System.out.println("当前线程：" + Thread.currentThread() + ", 已经完成：" +
                        "【 "+"("+proceessLen+"/" +totalLen +") " +(percent*100)+"% 】");
            }
            if (proceessLen == totalLen){
                long totalTime = System.currentTimeMillis() - beginTime;
                System.out.println("当前线程：" + Thread.currentThread() + ", 任务完成！总共耗时：" + "【"+ totalTime +"毫秒】");
            }
            System.out.println("当前字节：" + proceessLen);
        }
        raf.close();
        rafOut.close();
    }
    public static void main(String[] args) throws IOException {
        //生成测试数据
        String inputFile = "F:\\java\\gitRepo\\bookappointment\\test.txt";
//        BookImport.writeData(inputFile);
        BookImport bookImport = new BookImport(inputFile,3);
        bookImport.read(new Callback() {
            public void callback() {

            }
            public void callback(String str) {

            }
        });
    }
}
