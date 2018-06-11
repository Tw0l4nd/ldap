package controllers;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import service.UserService;


@Controller
@ComponentScan(basePackageClasses = UserService.class)
@RequestMapping(value = "/ldap")
public class UserController {
  RestTemplate restTemplate = new RestTemplate();
  private UserService userService;

  @RequestMapping(value = "/getInfo/{username}/{attribute}")
  public ResponseEntity<String> getAttributeInfo(@PathVariable(value = "username") String username,
                                                 @PathVariable(value = "attribute") String attribute) {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
    UserService userService = (UserService) context.getBean("userService");
    return new ResponseEntity<>(userService.getAttributePersonByUsername(username, attribute), HttpStatus.OK);
  }

  @RequestMapping(value = "/setAttributeValue/{username}/{atrribute}/{value}")
  public ResponseEntity<?> setAttributeValue(@PathVariable(value = "username") String username,
                                             @PathVariable(value = "atrribute") String attribute,
                                             @PathVariable(value = "value") String value) {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
    UserService userService = (UserService) context.getBean("userService");
    if (userService.setAttributeValueByUsername(username, attribute, value))
      return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/checkUserByUsernameAndPassword/{username}/{password")
  public ResponseEntity<?> checkUserByUsernameAndPassword(@PathVariable(value = "username") String username,
                                                          @PathVariable(value = "password") String password) {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
    UserService userService = (UserService) context.getBean("userService");
    if (userService.checkUsernameAndPassword(username, password))
      return new ResponseEntity<>(HttpStatus.OK);
    else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  @RequestMapping(value = "/resetUserPassword/{username}/{password")
  public ResponseEntity<?> resetUserPassword(@PathVariable(value = "username") String username,
                                             @PathVariable(value = "password") String password) {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
    UserService userService = (UserService) context.getBean("userService");
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
}
