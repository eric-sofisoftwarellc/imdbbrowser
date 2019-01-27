package com.sofisoftware.imdbbrowser;

import com.orhanobut.mockwebserverplus.MockWebServerPlus;
import com.sofisoftware.imdbbrowser.api.ImdbApi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.MockitoAnnotations;
import org.robolectric.shadows.ShadowLog;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BaseApiTest {
    @Rule
    public MockWebServerPlus server = new MockWebServerPlus();

    protected String getBaseUrl() {
        return server.server().url("/").toString();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ShadowLog.stream = System.out;

        ImdbApi.setExecutorService(new ExecutorService() {
            @Override
            public void shutdown() {
            }

            @Override
            public List<Runnable> shutdownNow() {
                return null;
            }

            @Override
            public boolean isShutdown() {
                return false;
            }

            @Override
            public boolean isTerminated() {
                return false;
            }

            @Override
            public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                return false;
            }

            @Override
            public <T> Future<T> submit(Callable<T> task) {
                return null;
            }

            @Override
            public <T> Future<T> submit(Runnable task, T result) {
                return null;
            }

            @Override
            public Future<?> submit(Runnable task) {
                return null;
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
                return null;
            }

            @Override
            public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
                return null;
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException, InterruptedException {
                return null;
            }

            @Override
            public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }

            @Override
            public void execute(Runnable command) {
                command.run();
            }
        });
        ImdbApi.makeTestService(getBaseUrl());
    }

    @After
    public void after() {
        try {
            server.server().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
