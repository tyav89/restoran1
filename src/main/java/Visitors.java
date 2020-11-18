public class Visitors implements Runnable {
    private Restoran restoran;
    private String name;


    public Visitors(Restoran restoran, String name) {
        this.restoran = restoran;
        this.name = name;
        System.out.printf("Посетитель %s в ресторане\n", name);
        restoran.addVisitors(this);
        new Thread(this, name).start();

    }

    @Override
    public void run() {
        restoran.eatAndGoOut();
    }

    public String getName() {
        return name;
    }
}
