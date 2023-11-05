public class Philosopher extends Thread {
    private String name;
    private Fork leftFork;
    private Fork rightFork;
    private PhilosopherState state = PhilosopherState.THINKING;
    private int eatCount = 0;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (eatCount < 3) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        state = PhilosopherState.THINKING;
        System.out.println("Philosopher " + name + " is thinking.");
        Thread.sleep(100); // Simulating thinking
    }

    private void eat() throws InterruptedException {
        state = PhilosopherState.HUNGRY;
        System.out.println("Philosopher " + name + " is hungry.");
        leftFork.pickUp();
        System.out.println("Philosopher " + name + " picks up left fork.");
        rightFork.pickUp();
        System.out.println("Philosopher " + name + " picks up right fork and eats.");
        state = PhilosopherState.EATING;
        eatCount++;
        System.out.println("Philosopher " + name + " eats. (" + eatCount + " time)");
        rightFork.putDown();
        System.out.println("Philosopher " + name + " puts down right fork.");
        leftFork.putDown();
        System.out.println("Philosopher " + name + " puts down left fork.");
    }

}
