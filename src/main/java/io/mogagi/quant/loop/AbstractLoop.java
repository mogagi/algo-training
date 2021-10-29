package io.mogagi.quant.loop;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public abstract class AbstractLoop implements Loop {

    protected String name;
    protected ExecutorService executor;
    protected List<Loop> observers = new LinkedList<>();

    public AbstractLoop() {
        name = getClass().getSimpleName();
    }

    public AbstractLoop(int nThreads) {
        this();
        executor = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public Loop notify(Loop... loops) {
        observers.addAll(Arrays.asList(loops));
        return this;
    }

    @Override
    public void submit() {
        submit(null, null);
    }

    @Override
    public void submit(String loopName, Object data) {
        executor.submit(() -> {
            try {
                LoopResult loopResult = work(loopName, data);
                if (loopResult != null && loopResult.getPass()) {
                    observers.forEach(loop -> loop.submit(name, loopResult.getData()));
                }
            } catch (Exception e) {
                log.error("{} work fail", name, e);
            }
        });
    }

    protected LoopResult work(String loopName, Object data) {
        return null;
    }
}