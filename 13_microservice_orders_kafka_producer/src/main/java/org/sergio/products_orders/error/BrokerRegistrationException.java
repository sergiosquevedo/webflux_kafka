package org.sergio.products_orders.error;

public class BrokerRegistrationException extends RuntimeException{
    public BrokerRegistrationException(String msg){
        super(msg);
    }
}
