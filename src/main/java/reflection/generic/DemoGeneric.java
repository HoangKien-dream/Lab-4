package reflection.generic;

import reflection.Customers;

import java.util.ArrayList;

public class DemoGeneric {
    public static void main(String[] args) {
        MasterModel<Customers> model = new MasterModel<>();
        Customers customers = new Customers();
        customers.setIdentityNumber("A004");
        customers.setBalance(1000);
        customers.setName("Hoàng Kiên");
        customers.setEmail("thanhmai@gmail.com");
        model.update(customers);
//        model.delete(customers);
    }
}
