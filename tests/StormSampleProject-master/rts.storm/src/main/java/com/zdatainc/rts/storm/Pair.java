package com.zdatainc.rts.storm;

import java.io.Serializable;

public class Pair<L extends Serializable, R extends Serializable>
    implements Serializable
{
    private static final long serialVersionUID = 42L;
    private L car;
    private R cdr;

    public Pair(L car, R cdr)
    {
        this.setLeft(car);
        this.setRight(cdr);
    }

    public L getLeft()
    {
        return this.car;
    }

    public R getRight()
    {
        return this.cdr;
    }

    public Pair<L, R> setLeft(L value)
    {
        this.car = value;
        return this;
    }

    public Pair<L, R> setRight(R value)
    {
        this.cdr = value;
        return this;
    }
}
