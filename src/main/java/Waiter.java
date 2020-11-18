public class Waiter implements Runnable {
    private Restoran restoran;
    private String name;

    public Waiter(Restoran restoran, String name) {
        this.restoran = restoran;
        this.name = name;
        System.out.printf("Официант %s на работе\n", name);
        new Thread(this, name).start();
    }

    @Override
    public void run() {
        while (true){
            restoran.takeOrders();
        }
    }

}
