package org.arxing.fileHelper.java;

abstract class AbstractHelper<TSpec, TBuilder> {
    protected TBuilder builder;

    public abstract TSpec create();
}
