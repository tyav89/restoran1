import java.util.ArrayList;
import java.util.List;

public class Restoran {
    final int timeSleep = 1000;
    List<String> listCustomer = new ArrayList<>();
    List<String> listOrder = new ArrayList<>();
    final Object customer = new Object();
    final Object waiter = new Object();
    String name;

    public void makeOrder() {
        synchronized (waiter) {
            while (listCustomer.size() == 0) {
                try {
                    waiter.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            name = listCustomer.remove(0);
            System.out.printf("Официант %s принял заказ у %s\n", Thread.currentThread().getName(), name);
            sleep();
            System.out.printf("Официант %s несет заказ %s\n", Thread.currentThread().getName(), name);
            listOrder.add(name);
        }
        orderReady();
    }

    public void eatAndGoOut() {
        readyToOrder();
        synchronized (customer) {
            while (listOrder.size() == 0 || listOrder.size() > 3) {
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
