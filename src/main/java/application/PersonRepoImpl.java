package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class PersonRepoImpl {

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
}
