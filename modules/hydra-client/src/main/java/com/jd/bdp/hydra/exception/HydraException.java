package com.jd.bdp.hydra.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class HydraException extends RuntimeException {

    public HydraException(){
        super();
    }

    public HydraException(String msg){
        super(msg);
    }

    public HydraException(Throwable e){
        super(e);
    }

    public HydraException(String msg, Throwable e){
        super(msg, e);
    }

}
