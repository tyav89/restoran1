import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Restoran {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();
    private Queue<Visitors> dequeVisitors = new ConcurrentLinkedDeque<>();
    private Queue<String> dequeOrders = new ConcurrentLinkedDeque<>();
    private int timeSleep = 1000;

    public void takeOrders() {
        synchronized (lock1) {
            while (dequeVisitors.isEmpty()) {
                try {
                    lock1.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        String name = dequeVisitors.remove().getName();
        System.out.printf("Официант %s принял заказ у %s\n", Thread.currentThread().getName(), name);
        sleep();
        System.out.printf("Официант %s несет заказ %s\n", Thread.currentThread().getName(), name);
        dequeOrders.add(name);
        synchronized (lock2){
            lock2.notify();
        }
    }

    public void eatAndGoOut() {
        synchronized (lock2) {
            while (dequeOrders.isEmpty()){
                try {
                    lock2.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        dequeOrders.remove();
        System.out.printf("Посетитель %s ест\n", Thread.currentThread().getName());
        sleep();
        System.out.printf("Посетитель %s уходит\n", Thread.currentThread().getName());

    }

    public void addVisitors(Visitors visitors) {
        dequeVisitors.add(visitors);
        synchronized (lock1){
            lock1.notify();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
