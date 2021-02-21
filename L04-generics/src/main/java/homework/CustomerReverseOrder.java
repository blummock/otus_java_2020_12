package homework;


import java.util.*;

public class CustomerReverseOrder {

    LinkedList<Customer> deque;

    public CustomerReverseOrder() {
        deque = new LinkedList<>();
    }

    public void add(Customer customer) {
       deque.addLast(customer);
    }

    public Customer take()
    {
        return deque.pollLast();
    }
}
