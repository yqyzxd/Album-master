/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.wind.album.executor;

import java.util.concurrent.Executor;

/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the {@link Interactor} out of the UI thread.
 *
 * Use this class to execute an {@link Interactor}.
 */
public interface ThreadExecutor extends Executor {
  /**
   * Executes a {@link Runnable}.
   *
   * @param runnable The class that implements {@link Runnable} callback.
   */
  void execute(final Runnable runnable);
}
