package reflection.generic;

import reflection.Customers;

public class DemoGeneric {
    public static void main(String[] args) {
        MasterModel<Customers> model = new MasterModel<>();
        Customers customers = new Customers();
        customers.setIdentityNumber("A002");
        customers.setBalance(1000);
        customers.setName("Thanh Mai");
        customers.setEmail("thanhmai@gmail.com");
        model.save(customers);
    }
}
