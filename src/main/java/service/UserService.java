package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import java.io.UnsupportedEncodingException;
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
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
      DirContextOperations context = ldapTemplate.lookupContext(sb.toString());
      context.setAttributeValue("cn", username);
      context.setAttributeValue(attribute, valueAttribute);
      ldapTemplate.modifyAttributes(context);
      return true;
    } catch (Exception ex) {
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

  public boolean checkUsernameAndPassword(String username, String password) {
    try {
      StringBuilder sb = new StringBuilder();
      sb.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
      DirContextOperations context = ldapTemplate.lookupContext(sb.toString());
      if (context.getStringAttribute("unicodepwd").equals(password))
        return true;
      else return false;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean resetPassword(String username, String password) {
    try {
      StringBuilder userDN = new StringBuilder();
      userDN.append("cn=").append(username).append(",cn=Users,dc=grsu,dc=local");
      ModificationItem repitem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodepwd", encodePassword(password)));
      ldapTemplate.modifyAttributes(userDN.toString(), new ModificationItem[]{repitem});
      return true;
    } catch (Exception e) {
      System.out.println("changePassword()");
      return false;
    }
  }

  private byte[] encodePassword(String password) throws UnsupportedEncodingException {
    String newQuotedPassword = "\"" + password + "\"";
    return newQuotedPassword.getBytes("UTF-16LE");
  }
}
