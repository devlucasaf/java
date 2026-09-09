package application.exercicios.faculdade.bsbcompute.worker;

import application.exercicios.faculdade.bsbcompute.model.*;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker implements Runnable {

    private final int                           workerId;
    private final int                           capacity;
    private final BlockingQueue<TaskMessage>    inQueue;
    private final BlockingQueue<EventMessage>   outQueue;
    private final AtomicBoolean                 stopFlag;

    private final ExecutorService               executor;
    private int                                 activeCount = 0;

    public Worker(int workerId, int capacity,
                  BlockingQueue<TaskMessage> inQueue,
                  BlockingQueue<EventMessage> outQueue,
                  AtomicBoolean stopFlag) {

        this.workerId = workerId;
        this.capacity = Math.max(1, capacity);
        this.inQueue = inQueue;
        this.outQueue = outQueue;
        this.stopFlag = stopFlag;
        this.executor = Executors.newFixedThreadPool(this.capacity);
    }

    private synchronized int setActive(int delta) {
        activeCount += delta;
        return activeCount;
    }

    private void runTask(TaskMessage msg) {
        Request request = msg.request;

        double start = System.nanoTime() / 1e9;
        int curActive = setActive(+1);

        outQueue.offer(new EventMessage("START", workerId, request.id,
                request.prioridade, request.tipo, request.tempoExecucao,
                0.0, msg.absoluteArrivalTs, start, 0, curActive));

        double effective = request.tempoExecucao / capacity;

        try {
            Thread.sleep((long) (effective * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double end = System.nanoTime() / 1e9;
        curActive = setActive(-1);

        outQueue.offer(new EventMessage("DONE", workerId, request.id,
                request.prioridade, request.tipo, request.tempoExecucao,
                effective, msg.absoluteArrivalTs, start, end, curActive));
    }

    @Override
    public void run() {
        try {
            while (!stopFlag.get()) {
                TaskMessage msg = inQueue.poll(100, TimeUnit.MILLISECONDS);
                if (msg != null) {
                    executor.submit(() -> runTask(msg));
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
}
