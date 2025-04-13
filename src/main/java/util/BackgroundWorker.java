package util;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class BackgroundWorker<T> extends SwingWorker<T, Void> {
    private final Supplier<T> backgroundTask;
    private final Consumer<T> onSuccess;
    private final Consumer<Throwable> onError;
    private final Runnable beforeTask;
    private final Runnable afterTask;

    public BackgroundWorker(
            Supplier<T> backgroundTask,
            Consumer<T> onSuccess,
            Consumer<Throwable> onError,
            Runnable beforeTask,
            Runnable afterTask
    ) {
        this.backgroundTask = backgroundTask;
        this.onSuccess = onSuccess;
        this.onError = onError;
        this.beforeTask = beforeTask;
        this.afterTask = afterTask;
    }

    @Override
    protected T doInBackground() throws Exception {
        return backgroundTask.get();
    }

    @Override
    protected void done() {
        try {
            if (beforeTask != null) {
                beforeTask.run();
            }
            T result = get();
            if (onSuccess != null) {
                onSuccess.accept(result);
            }
        } catch (InterruptedException | ExecutionException e) {
            if (onError != null) {
                onError.accept(e.getCause() instanceof RuntimeException ? (RuntimeException) e.getCause() : e);
            }
        } finally {
            if (afterTask != null) {
                afterTask.run();
            }
        }
    }
}
