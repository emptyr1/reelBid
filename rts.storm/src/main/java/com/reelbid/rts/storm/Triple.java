package com.zdatainc.rts.storm;

import java.io.Serializable;

public class Triple<T1 extends Serializable,
                    T2 extends Serializable,
                    T3 extends Serializable> implements Serializable
{
    private static final long serialVersionUID = 42L;

    private T1 car;
    private T2 cdr;
    private T3 caar;

    public Triple(T1 car, T2 cdr, T3 caar)
    {
        this.setCar(car);
        this.setCdr(cdr);
        this.setCaar(caar);
    }

    public T1 getCar()
    {
        return this.car;
    }

    public T2 getCdr()
    {
        return this.cdr;
    }

    public T3 getCaar()
    {
        return this.caar;
    }

    public void setCar(T1 value)
    {
        this.car = value;
    }

    public void setCdr(T2 value)
    {
        this.cdr = value;
    }

    public void setCaar(T3 value)
    {
        this.caar = value;
    }
}
