package com.nexia.vct.impl;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;

public record ViaTaskImpl(Task task) implements PlatformTask<Task> {
    @Override
    public void cancel() {
        this.task.cancel();
    }
}
