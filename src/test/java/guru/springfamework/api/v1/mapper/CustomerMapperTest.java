package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

  public static final Long ID = 2L;
  public static final String FIRSTNAME = "Joe";
  public static final String LASTNAME = "Perfekt";

  CustomerMapper customerMapper = CustomerMapper.INSTANCE;

  @Test
  public void testCustomerToCustomerDTO() throws Exception{
    Customer customer = new Customer();
    customer.setId(ID);
    customer.setFirstName(FIRSTNAME);
    customer.setLastName(LASTNAME);

    CustomerDTO testling = customerMapper.customerToCustomerDTO(customer);

    assertEquals(Long.valueOf(ID), customer.getId());
    assertEquals(FIRSTNAME, customer.getFirstName());
    assertEquals(LASTNAME, customer.getLastName());
  }

}
