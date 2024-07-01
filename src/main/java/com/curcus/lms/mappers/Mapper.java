package com.curcus.lms.mappers;

public interface Mapper<A,B> {

    B mapTo(A a);

    A mapFrom(B b);


}
