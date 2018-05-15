package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import service.UserService;

@RestController
@RequestMapping("/ldap/user")
public class UserController {
  RestTemplate restTemplate = new RestTemplate();

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/getInfo/{username}/{atrribute}")
  public ResponseEntity<String> getAttributeInfo(@PathVariable(value = "username") String username, @PathVariable(value = "atrribute") String attribute) {
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

  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
