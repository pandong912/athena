package com.ccb.athena.executor.scheduler.ductsume;


public interface Consumer {

	<T> T fetch(Class<T> clazz);
}
