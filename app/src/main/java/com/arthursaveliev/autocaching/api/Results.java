package com.arthursaveliev.autocaching.api;

import io.reactivex.functions.Predicate;
import retrofit2.adapter.rxjava2.Result;

public final class Results {

  private static final Predicate<Result<?>> SUCCESSFUL = result -> !result.isError() && result.response().isSuccessful();

  public static Predicate<Result<?>> isSuccessful() {
    return SUCCESSFUL;
  }

  private Results() {
    throw new AssertionError("No instances.");
  }
}