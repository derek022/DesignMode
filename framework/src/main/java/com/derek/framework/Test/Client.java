package com.derek.framework.Test;

import android.content.Intent;

import com.derek.framework.Decorator.Boy;
import com.derek.framework.Decorator.CheapCloth;
import com.derek.framework.Decorator.ExpensiveCloth;
import com.derek.framework.Decorator.Person;
import com.derek.framework.Decorator.PersonCloth;
import com.derek.framework.Expression.Calculator;
import com.derek.framework.Factory.AudiCar;
import com.derek.framework.Factory.AudiCarFactory;
import com.derek.framework.Factory.AudiFactory;
import com.derek.framework.Factory.AudiQ3;
import com.derek.framework.Flyweight.Ticket;
import com.derek.framework.Flyweight.TicketFactory;
import com.derek.framework.Handler.Handler1;
import com.derek.framework.Handler.Handler2;
import com.derek.framework.Handler.Handler3;
import com.derek.framework.Handler.Request1;
import com.derek.framework.Handler.Request2;
import com.derek.framework.Handler.Request3;
import com.derek.framework.Handler.base.AbstractHandler;
import com.derek.framework.Handler.base.AbstractRequest;
import com.derek.framework.Observer.Coder;
import com.derek.framework.Observer.DevTechFrontier;
import com.derek.framework.Proxy.Consignor;
import com.derek.framework.Proxy.DynamicProxy;
import com.derek.framework.Proxy.ILawsuit;
import com.derek.framework.State.TvController;
import com.derek.framework.Strategy.BusStrategy;
import com.derek.framework.Strategy.TranficCalculator;
import com.derek.framework.Visitor.BusinessReport;
import com.derek.framework.Visitor.CEOVisitor;
import com.derek.framework.Visitor.CTOVisitor;

import java.lang.reflect.Proxy;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


/**
 * 线程是属于异步计算模型，所以你不可能直接从别的线程中得到方法返回值。 这时候，Future就出场了。
 * Futrue可以监视目标线程调用call的情况，当你调用Future的get()方法以获得结果时，当前线程就开始阻塞，直接call方法结束返回结果。
 * Future引用对象指向的实际是FutureTask。
 */
class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("my callable call,do some thing cost time");
        Thread.sleep(5000);
        return "OK";
    }
}

public class Client {

    static class Num{
        int num = 10;
        long num2 = 10;
        public Num(){
            num = 30;
        }
    }

    public static void main(String argv[]){

//        ticket();

        Num a = new Num();
        System.out.println("methodTest：" + a.num);

//        AutoBoolean


//        try {
//            Class clazz = Class.forName("com.derek.framework.Client$Num");
//            Field field = clazz.getDeclaredField("num2");
//
//            System.out.println("filed " + field.getType().toString());
//            System.out.println("filed " + field.getGenericType().toString());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }

    }

    static void futureTask() throws ExecutionException, InterruptedException {
        /**
         * feature
         */
        ExecutorService service = Executors.newCachedThreadPool();
        Future<String> future = service.submit(new MyCallable());
        System.out.println("future do something.....");
        System.out.println("future get result " + future.get()); // 等待结果发生
        System.out.println("future Completed!!!!!!");
        System.out.println("============================================");
        System.out.println("============================================");
        MyCallable callable = new MyCallable();
        final FutureTask<String> task = new FutureTask<>(callable);
//        new Thread(task).start();
        new Thread(){
            @Override
            public void run() {
                task.run();
            }
        }.start();

        System.out.println("task do something.....");
        System.out.println("task get result " + task.get());
        System.out.println("task Completed!!!!!!");
    }

    public void intent(){
        Intent sharedIntent = new Intent();
        sharedIntent.clone();
    }

    static void factoryTest(){
        AudiFactory factory = new AudiCarFactory();
        AudiCar car = factory.createAudiCar(AudiQ3.class);
        car.drive();
        car.selfNavigation();
    }

    static void strategyTest(){
        TranficCalculator calculator = new TranficCalculator();
        calculator.setmStrategy(new BusStrategy());
        System.out.println("price : " + calculator.calculatePrice(20));
    }

    static void stateTest(){
        TvController controller = new TvController();
        controller.powerOn();

        controller.nextChannel();
        controller.prevChannel();
        controller.turnDown();

        controller.powerOff();
        controller.turnUp();
    }

    static void handler(){
        AbstractHandler handler1 = new Handler1();
        AbstractHandler handler2 = new Handler2();
        AbstractHandler handler3 = new Handler3();

        handler1.nextHandler = handler2;
        handler2.nextHandler = handler3;

        AbstractRequest request1 = new Request1("Request1");
        AbstractRequest request2 = new Request2("Request2");
        AbstractRequest request3 = new Request3("Request3");

        handler1.handleRequest(request1);
        handler1.handleRequest(request2);
        handler1.handleRequest(request3);
    }

    static void expressionTest(){
        Calculator calculator = new Calculator("23 + 34 + 56");
        System.out.println(calculator.calculate());

    }

    static void observerTest(){
        DevTechFrontier frontier = new DevTechFrontier();

        Coder mrSample = new Coder("mr.sample");
        Coder coder1 = new Coder("coder1");
        Coder coder2 = new Coder("coder2");
        Coder coder3 = new Coder("coder3");

        frontier.addObserver(mrSample);
        frontier.addObserver(coder1);
        frontier.addObserver(coder2);
        frontier.addObserver(coder3);

        frontier.postNewPublication("新一期的内容发布啦。。。");
    }

    static void threadLocalTest(){
        final ThreadLocalTest test = new ThreadLocalTest();
        test.set();
        test.print();
        Thread t = new Thread(){
            @Override
            public void run() {
                test.set();
                test.print();
            }
        };
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        test.print();
    }

    public void methodTest(String name){
        System.out.println("methodTest：" + name);
    }

    static void visitorTest(){
        BusinessReport report = new BusinessReport();
        System.out.println("==========给CEO看的报表==============");
        report.showReport(new CEOVisitor());

        System.out.println("==========给CTO看的报表==============");
        report.showReport(new CTOVisitor());

    }

    /**
     * 动态代理
     */
    static void dynamicTest(){
        ILawsuit consignor = new Consignor();
        DynamicProxy proxy = new DynamicProxy(consignor);
        ClassLoader loader = consignor.getClass().getClassLoader();

        ILawsuit lawyer = (ILawsuit) Proxy.newProxyInstance(loader,new Class[]{ILawsuit.class},proxy);

        lawyer.submit();
        lawyer.defend();
        lawyer.burden();
        lawyer.finish();

    }

    static void decorator(){
        Person boy = new Boy();
        PersonCloth  expensiveCloth = new ExpensiveCloth(boy);
        expensiveCloth.dressed();
        System.out.println("=====================");
        PersonCloth cheapCloth = new CheapCloth(boy);
        cheapCloth.dressed();
    }

    static void ticket(){

        for (int i = 0;i<1000;i++){
            int from = new Random().nextInt(100);
            int to = new Random().nextInt(100);
            Ticket ticket = TicketFactory.getTicket("" + from,"" + to);
            ticket.showTicketInfo("上铺");
        }
    }

}
