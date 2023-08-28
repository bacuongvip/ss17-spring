package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ra.service.CategoryService;
import ra.service.ProductService;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private ProductService productService;
    @GetMapping
    public String home() {
        return "user/index";
    }
    @GetMapping("/detail")
    public String detail(){
        return "user/detail";
    }
    @GetMapping("/cart")
    public String cart(){
        return "user/cart";
    }
    @GetMapping("/checkout")
    public String checkout(){
        return "user/checkout";
    }
    @GetMapping("/contact")
    public String contact(){
        return "user/contact";
    }
    @GetMapping("/shop/{id}")
    public String shop(@PathVariable("id") int id, Model model){
        model.addAttribute("products", productService.findProByCateId(id));
        return "user/shop";
    }
}
