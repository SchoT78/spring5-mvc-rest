package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class CustomerControllerTest extends AbstractRestControllerTest {
  public static final String LASTNAME = "Hubeldubel";

  @Mock
  CustomerService customerService;

  @InjectMocks
  CustomerController testling;

  MockMvc mockMvc;

  @Before
  public void setUp() throws Exception{
    MockitoAnnotations.initMocks(this);

    mockMvc = MockMvcBuilders.standaloneSetup(testling).build();
  }

  @Test
  public void testListCustomers() throws Exception{
    CustomerDTO customerDTOOne = new CustomerDTO();
    customerDTOOne.setId(1L);
    customerDTOOne.setFirstName("Heinz");
    customerDTOOne.setLastName(LASTNAME);

    CustomerDTO customerDTOTwo = new CustomerDTO();
    customerDTOTwo.setId(2L);
    customerDTOTwo.setFirstName("Joe");
    customerDTOTwo.setLastName("Pocke");

    List<CustomerDTO> customerDTOList = Arrays.asList(customerDTOOne, customerDTOTwo);

    when(customerService.getAllCustomer()).thenReturn(customerDTOList);

    mockMvc.perform(get("/api/v1/customers/")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.customers", hasSize(2)));
  }

  @Test
  public void testGetCustomerById() throws  Exception{
    CustomerDTO customerDTOOne = new CustomerDTO();
    customerDTOOne.setFirstName("Heinz");
    customerDTOOne.setLastName(LASTNAME);
    customerDTOOne.setCustomerURL("/api/v1/customer/1");

    when(customerService.getCustomerById(anyLong())).thenReturn(customerDTOOne);

    mockMvc.perform(get("/api/v1/customers/1")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.lastName", equalTo(LASTNAME)));
    // lastName muss genauso wie die Variable geschrieben werden!!!
  }

  @Test
  public void createNewCustomer() throws Exception {
    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstName("Fred");
    customer.setLastName("Flintstone");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstName(customer.getFirstName());
    returnDTO.setLastName(customer.getLastName());
    returnDTO.setCustomerURL("/api/v1/customers/1");

    when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

    //when/then
    mockMvc.perform(post("/api/v1/customers/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.firstname", equalTo("Fred")))
      .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testUpdateCustomer() throws Exception {
    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstName("Fred");
    customer.setLastName("Flintstone");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstName(customer.getFirstName());
    returnDTO.setLastName(customer.getLastName());
    returnDTO.setCustomerURL("/api/v1/customers/1");

    when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

    //when/then
    mockMvc.perform(put("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstname", equalTo("Fred")))
      .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
      .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }

  @Test
  public void testPatchCustomer() throws Exception {

    //given
    CustomerDTO customer = new CustomerDTO();
    customer.setFirstName("Fred");

    CustomerDTO returnDTO = new CustomerDTO();
    returnDTO.setFirstName(customer.getFirstName());
    returnDTO.setLastName("Flintstone");
    returnDTO.setCustomerURL("/api/v1/customers/1");

    when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

    mockMvc.perform(patch("/api/v1/customers/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(customer)))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.firstname", equalTo("Fred")))
      .andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
      .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
  }
}
