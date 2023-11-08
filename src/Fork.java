import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Fork {
    //    private Lock lock = new ReentrantLock();
//
//    public void pickUp() {
//        lock.lock();
//    }
//
//    public void putDown() {
//        lock.unlock();
//    }
    private AtomicInteger ownerNumber; // номер философа. Проверяется и изменяется философами атомарно,
    // что исключает возможность взятия вилки двумя философами
    private static int count;
    private int index;

    public Fork() {
        index = ++count;
        ownerNumber = new AtomicInteger(0);
    }

    public AtomicInteger getOwnerNumber() {
        return ownerNumber;
    }

    @Override
    public String toString() {
        return "Fork " + index;
    }
}
