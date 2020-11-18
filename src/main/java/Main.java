public class Main {
    public static void main(String[] args) {
        Restoran restoran = new Restoran();

        new Waiter(restoran, "1");
        new Waiter(restoran, "2");
        new Waiter(restoran, "3");

        new Visitors(restoran, "1");
        new Visitors(restoran, "2");
        new Visitors(restoran, "3");
        new Visitors(restoran, "4");
        new Visitors(restoran, "5");
        new Visitors(restoran, "6");
    }
}
