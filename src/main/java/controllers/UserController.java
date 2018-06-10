package controllers;

import application.PersonRepoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.Main;
import service.UserService;


@Controller
@ComponentScan(basePackageClasses = UserService.class)
@RequestMapping(value = "/")
public class UserController {
  RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private UserService userService;

  @Autowired
  PersonRepoImpl personRepo;
  private PersonRepoImpl personRepoImpl;

  @RequestMapping(value = "/getHello")
  public ResponseEntity<String> getHello() {
//    ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
//    service.Main main = ctx.getBean(service.Main.class);
    String[] strings = {""};
    Main.main(strings);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @RequestMapping(value = "/getInfo/{username}/{attribute}")
  public ResponseEntity<String> getAttributeInfo(@PathVariable(value = "username") String username, @PathVariable(value = "attribute") String attribute) {
    return new ResponseEntity<>(userService.getAttributePersonByUsername(username, attribute), HttpStatus.OK);
  }

  @RequestMapping(value = "/setAttributeValue/{username}/{atrribute}/{value}")
  public ResponseEntity<?> setAttributeValue(@PathVariable(value = "username") String username,
                                             @PathVariable(value = "atrribute") String attribute,
                                             @PathVariable(value = "value") String value) {
    if (userService.setAttributeValueByUsername(username, attribute, value))
      return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/checkUserByUsernameAndPassword/{username}/{password")
  public ResponseEntity<?> checkUserByUsernameAndPassword(@PathVariable(value = "username") String username,
                                                          @PathVariable(value = "password") String password) {
    if (userService.checkUsernameAndPassword(username, password))
      return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/resetUserPassword/{username}/{password")
  public ResponseEntity<?> resetUserPassword(@PathVariable(value = "username") String username,
                                             @PathVariable(value = "password") String password) {
    if (userService.resetPassword(username, password))
      return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public UserService getUserService() {
    return userService;
  }

  public void setPersonRepoImpl(PersonRepoImpl personRepoImpl) {
    this.personRepoImpl = personRepoImpl;
  }

  public PersonRepoImpl getPersonRepoImpl() {
    return personRepoImpl;
  }
}
