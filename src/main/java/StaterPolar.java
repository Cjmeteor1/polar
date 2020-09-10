import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description:
 * @author: zhangwenzhi
 * @time: 2020/4/21 17:13
 */
@SpringBootApplication
@ComponentScan(basePackages = { "web" })
public class StaterPolar {
    public static void main(String[] args) {
        SpringApplication.run(StaterPolar.class);
    }
}
