import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import service.UserService;

@Component
public class Main {

  @Autowired
  private PersonRepoImpl personRepoImpl;

  @Autowired
  private UserService userService;

  public static void main(String[] args) {
    ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
    Main main = ctx.getBean(Main.class);
    main.callService();
  }

  public void callService() {
    //List<String> arr = personRepoImpl.getPersonNamesByLastName();
    //arr.forEach(System.out::println);
    // personRepoImpl.setAttributeValueByUsername("Admin", "description", "addAdminPass");
    //System.out.println(userService.getAttributePersonByUsername("Admin", "mail"));
    //System.out.println(userService.getAttributePersonByUsername("Admin", "telephoneNumber"));
    //System.out.println(userService.getAttributePersonByUsername("Admin", "mail"));
    System.out.println(userService.resetPassword("cn=JayZ,cn=Users,dc=grsu,dc=local", "123"));
    //userService.tmpMethod("Admin");
  }

  public void setPersonRepoImpl(PersonRepoImpl personRepoImpl) {
    this.personRepoImpl = personRepoImpl;
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
