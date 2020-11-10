import java.util.ArrayList;
import java.util.List;

public class Restoran {
    final int timeSleep = 1000;
    List<String> listCustomer = new ArrayList<>();
    List<String> listOrder = new ArrayList<>();
    final Object customer = new Object();
    final Object waiter = new Object();

    public synchronized void makeOrder() {
        synchronized (waiter) {
            while (listCustomer.size() < 1) {
                try {
                    waiter.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("Официант %s принял заказ у %s\n", Thread.currentThread().getName(), listCustomer.get(0));
            sleep();
            System.out.printf("Официант %s несет заказ %s\n", Thread.currentThread().getName(), listCustomer.get(0));
            listOrder.add(listCustomer.remove(0));
            orderReady();
        }
    }

    public void eatAndGoOut() {
        readyToOrder();
        synchronized (customer){
            while (listOrder.size() < 1) {
                try {
                    customer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            listOrder.remove(0);
            System.out.printf("Посетитель %s ест\n", Thread.currentThread().getName());
            sleep();
            System.out.printf("Посетитель %s уходит\n", Thread.currentThread().getName());
            customer.notify();
        }

    }

    private void sleep() {
        try {
            Thread.sleep(timeSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(String name) {
        listCustomer.add(name);
    }

    private void readyToOrder() {
        synchronized (waiter) {
            waiter.notify();
        }
    }

    private void orderReady() {
        synchronized (customer) {
            customer.notify();
        }
    }
}
