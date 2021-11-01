package guru.springfamework.bootstrap;

import guru.springfamework.domain.Category;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

  private CategoryRepository categoryRepository;
  private CustomerRepository customerRepository;

  public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
    this.categoryRepository = categoryRepository;
    this.customerRepository = customerRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    loadCategory();
    loadCustomers();
  }

  private void loadCustomers() {
    // Create customer
    Customer customerOne = new Customer();
    customerOne.setFirstName("Heinz");
    customerOne.setLastName("Wurst");

    Customer customerTwo = new Customer();
    customerTwo.setFirstName("Axel");
    customerTwo.setLastName("Schweiss");

    Customer customerThree = new Customer();
    customerThree.setFirstName("Fritz");
    customerThree.setLastName("Klein");

    Customer customerFour = new Customer();
    customerFour.setFirstName("Ines");
    customerFour.setLastName("Heiz");

    Customer customerFive = new Customer();
    customerFive.setFirstName("Taya");
    customerFive.setLastName("Fee");

    customerRepository.save(customerOne);
    customerRepository.save(customerTwo);
    customerRepository.save(customerThree);
    customerRepository.save(customerFour);
    customerRepository.save(customerFive);

    System.out.println("Customer data load " + customerRepository.count());
  }

  private void loadCategory() {
    Category fruits = new Category();
    fruits.setName("Fruits");

    Category dried = new Category();
    dried.setName("Dried");

    Category fresh = new Category();
    fresh.setName("Fresh");

    Category exotic = new Category();
    exotic.setName("Exotic");

    Category nuts = new Category();
    nuts.setName("Nuts");

    categoryRepository.save(fruits);
    categoryRepository.save(dried);
    categoryRepository.save(fresh);
    categoryRepository.save(exotic);
    categoryRepository.save(nuts);

    System.out.println("Data loaded = " + categoryRepository.count());
  }
}
