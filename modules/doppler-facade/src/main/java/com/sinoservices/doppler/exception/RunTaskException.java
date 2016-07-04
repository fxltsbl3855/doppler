package com.sinoservices.doppler.exception;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
public class RunTaskException extends RuntimeException {

    public RunTaskException(){
        super();
    }

    public RunTaskException(String msg){
        super(msg);
    }

    public RunTaskException(Throwable e){
        super(e);
    }

    public RunTaskException(String msg, Throwable e){
        super(msg, e);
    }

}
