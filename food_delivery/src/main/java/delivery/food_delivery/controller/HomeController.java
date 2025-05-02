package delivery.food_delivery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Начална страница. Влез за достъп до ресурси.";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "Добре дошъл, клиент!";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "Здравей, администратор!";
    }

    @GetMapping("/employee/home")
    public String employeeHome() {
        return "Привет, служител!";
    }
}
