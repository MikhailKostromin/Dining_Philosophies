
public class Philosopher extends Thread {
    private String name;
    private Fork leftFork;
    private Fork rightFork;
    private PhilosopherState state = PhilosopherState.THINKING;
    private int eatCount = 0; // уровень сытости
    private int number;
    private static int count;
    private StringBuilder log;

    public Philosopher(String name, Fork leftFork, Fork rightFork) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        number = ++count;
        log = new StringBuilder();
    }

    @Override
    public void run() {
        try {
            while (eatCount < 3) {
                // проверяем состояние атомарного поля левой вилки. если равно 0, то устаналиваем значение, равное number философа
                if (leftFork.getOwnerNumber().compareAndSet(0, number)) {

                    // аналогично проверяем состояние правой вилки
                    if (rightFork.getOwnerNumber().compareAndSet(0, number)) {
                        // если обе вилки были свободны, то выводим сообщение, что философ взял их в руку и ест 20 мс
                        System.out.println(">>> " + name + " взял " + leftFork + " и " + rightFork + ", ЕСТ " + ++eatCount + " РАЗ \t\t");
                        log.append("* ПОЕЛ");
                        sleep(20);

                        // после того, как философ поел, он кладет вилки на стол и устанавливает значение атомарного поля = 0
                        System.out.println("<<< " + name + " кладет " + leftFork + " и " + rightFork);
                        rightFork.getOwnerNumber().compareAndSet(number, 0);
                        leftFork.getOwnerNumber().compareAndSet(number, 0);

                        // ждет еще 20 мс, чтобы другие философы успели взять освободившиеся вилки
                        sleep(20);

                    } else {
                        // если правая вилка занята, атомарное значение левой вилки устанавливается равным 0 и философ думает 18 мс
                        leftFork.getOwnerNumber().compareAndSet(number, 0);
                        think();
                    }
                } else {
                    // если левая вилка занята, философ думает 18 мс
                    think();
                }
            }
            // завершается поток сообщением, что философ насытился и выводится вся история о нем
            log.append("* СЫТЫЙ");
            System.out.println(name + ": " + log);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void think()  {
        state = PhilosopherState.THINKING;
        System.out.println("Philosopher " + name + " is thinking.");
        log.append("* подумал ");
        try {
            sleep(18);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);

        // проверяем, какое значение атомарного поля установлено у вилки, когда философ есть ею
        if (leftFork.getOwnerNumber().doubleValue() == number)
            sb.append(": ").append(leftFork.toString());
        if (rightFork.getOwnerNumber().doubleValue() == number)
            sb.append(": ").append(rightFork.toString());
        return sb.toString();
    }
}
