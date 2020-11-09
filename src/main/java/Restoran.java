import java.util.ArrayList;
import java.util.List;

public class Restoran {
    final int timeSleep = 2000;
    List<String> listCustomer = new ArrayList<>();
    List<String> listOrder = new ArrayList<>();

    public synchronized void makeOrder() {
        while (listCustomer.size() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Официант %s принял заказ у %s\n", Thread.currentThread().getName(), listCustomer.get(0));
        sleep();
        System.out.printf("Официант %s несет заказ %s\n", Thread.currentThread().getName(), listCustomer.get(0));
        listOrder.add(listCustomer.remove(0));
        notify();
    }

    public synchronized void eatAndGoOut() {
        sleep();
        notify();
        while (listOrder.size() < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        listOrder.remove(0);
        System.out.printf("Посетитель %s ест\n", Thread.currentThread().getName());
        sleep();
        System.out.printf("Посетитель %s уходит\n", Thread.currentThread().getName());
        notify();

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
}
