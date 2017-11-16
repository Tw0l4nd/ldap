import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main {

    @Autowired
    private PersonRepoImpl personRepoImpl;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("Beans.xml");
        Main main = ctx.getBean(Main.class);
        main.callService();
    }

    public void callService() {
        List<String> arr = personRepoImpl.getPersonNamesByLastName();
        arr.forEach(System.out::println);
    }

    public void setPersonRepoImpl(PersonRepoImpl personRepoImpl) {
        this.personRepoImpl = personRepoImpl;
    }
}
