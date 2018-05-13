package service;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.security.ldap.userdetails.LdapUserDetailsManager;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class UserService {

  @Autowired(required = true)
  @Qualifier(value = "ldapTemplate")
  private LdapTemplate ldapTemplate;

  public void setLdapTemplate(LdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  // https://docs.spring.io/spring-ldap/docs/2.3.2.RELEASE/reference/#basic-queries
  public List<String> getPersonNamesByLastName() {
    LdapQuery query = query()
      .base("cn=Users,dc=grsu,dc=local")
      .where("objectclass").is("person")
      .and("cn").is("jack.sparrow");

    return ldapTemplate.search(query,
      new AttributesMapper<String>() {
        public String mapFromAttributes(Attributes attrs)
          throws NamingException {

          return (String) attrs.get("cn").get();
        }
      });
  }

  public boolean setAttributeValueByUsername(String username, String attribute, String valueAttribute) {
    try{
      StringBuilder sb = new StringBuilder();
      sb.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
      DirContextOperations context = ldapTemplate.lookupContext(sb.toString());
      context.setAttributeValue("cn", username);
      context.setAttributeValue(attribute, valueAttribute);
      ldapTemplate.modifyAttributes(context);
      return true;
    }catch (Exception ex){
      return false;
    }

  }

  public String getAttributePersonByUsername(String username, String attribute) {
    StringBuilder attributeUser = new StringBuilder();
    StringBuilder sb = new StringBuilder();
    sb.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
    DirContextOperations context = ldapTemplate.lookupContext(sb.toString());
    attributeUser.append(context.getStringAttribute(attribute));
    return attributeUser.toString();
  }

  public void tmpMethod(String username){
    StringBuilder sb = new StringBuilder();
    sb.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
    LdapContextSource contextSource = new LdapContextSource();
    //contextSource.setPassword("1");
    contextSource.setUserDn(sb.toString());
    LdapUserDetailsManager ldapUserDetailsManager = new LdapUserDetailsManager(contextSource);
    //LdapUserDetailsManager ldapUserDetailsManager1 = new LdapUserDetailsManager(ldapTemplate.getContextSource());
    ldapUserDetailsManager.changePassword("1","123");
  }
}
