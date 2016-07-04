package com.sinoservices.trace.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class HydraWebException extends RuntimeException {

    public HydraWebException(){
        super();
    }

    public HydraWebException(String msg){
        super(msg);
    }

    public HydraWebException(Throwable e){
        super(e);
    }

    public HydraWebException(String msg, Throwable e){
        super(msg, e);
    }

}
