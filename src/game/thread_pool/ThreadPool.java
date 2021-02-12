package game.thread_pool;

import java.util.LinkedList;

public class ThreadPool extends ThreadGroup {
    private boolean isAlive;
    private LinkedList taskQueue;
    private int threadID;
    private static int threadPoolID;


    public ThreadPool(int numThreads) {
        super("Threadpool-" + (threadPoolID++));
        setDaemon(true);

        isAlive = true;

        taskQueue = new LinkedList();
        for (int i = 0; i < numThreads; i++) {
            new PooledThread().start();
        }
    }

    public synchronized void runTask(Runnable task) {
        if (!isAlive) {
            throw new IllegalStateException();
        }

        if (task != null) {
            taskQueue.add(task);
            notify();
        }
    }

    public synchronized void close() {
        if (isAlive) {
            isAlive = false;
            taskQueue.clear();
            interrupt();
        }
    }

    public void join() {
        synchronized (this) {
            isAlive = false;
            notifyAll();
        }

        Thread[] threads = new Thread[activeCount()];
        int count = enumerate(threads);
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected synchronized Runnable getTask() throws InterruptedException {
        while (taskQueue.size() == 0) {
            if (!isAlive) {
                return null;
            }
            wait();
        }
        return (Runnable) taskQueue.removeFirst();
    }

    private class PooledThread extends Thread {

        public PooledThread() {
            super(ThreadPool.this,
                    "PooledThread-" + threadID++);
        }

        public void run() {
            while (!isInterrupted()) {
                Runnable task = null;

                try {
                    task = getTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task == null) {
                    return;
                }

                try {
                    task.run();
                } catch (Throwable throwable) {
                    uncaughtException(this, throwable);
                }
            }
        }


    }

}
