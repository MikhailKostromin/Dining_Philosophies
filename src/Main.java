/*
1. Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом
   стоит тарелка спагетти.
2. Вилки лежат на столе между каждой парой ближайших философов.
3. Каждый философ может либо есть, либо размышлять.
4. Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
5. Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)

Описать в виде кода такую ситуацию. Каждый философ должен поесть три раза

 */
public class Main {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        Fork[] forks = new Fork[numPhilosophers];

        String[] philosopherNames = {
                "Сократ",
                "Платон",
                "Аристотель",
                "Рене Декарт",
                "Фридрих Ницше"
        };

        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Fork();
        }

        for (int i = 0; i < numPhilosophers; i++) {
            philosophers[i] = new Philosopher(philosopherNames[i], forks[i], forks[(i + 1) % numPhilosophers]);
            philosophers[i].start();
        }

        // Ждем, пока все философы закончат ужинать
        try {
            for (Philosopher philosopher : philosophers) {
                philosopher.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}