package ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ra.model.Category;
import ra.model.Product;
import ra.service.CategoryService;
import ra.service.ProductService;

import java.sql.SQLException;

@Controller
@RequestMapping("/admin")
@PropertySource("classpath:upload.properties")
public class AdminController {
    @Value("${upload-path}")
    private String uploadPath;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @GetMapping
    public String home(){
        return "admin/index";
    }
    // Category
    @GetMapping("/category")
    public ModelAndView category(){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/category");
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.addObject("newCategory", new Category());
        return modelAndView;
    }
    @PostMapping("/category")
    public String categoryAdd(@ModelAttribute Category category) throws SQLException {
        categoryService.save(category);
        return "redirect:/admin/category";
    }
    @GetMapping("/category/{id}")
    public String category(@PathVariable("id") int id){
        categoryService.deleteById(id);
        return "redirect:/admin/category";
    }
//    @GetMapping("/category/edit/{id}")
//    public void categoryEdit(@PathVariable("id") int id, Model model){
//        model.addAttribute("categoryId", categoryService.findById(id));
//    }
    // Product
    @GetMapping("/product")
    public ModelAndView product(){
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/product");
        modelAndView.addObject("products", productService.findAll());
        modelAndView.addObject("newProduct", new Product());
        return modelAndView;
    }
    @GetMapping("/user")
    public String user(){
        return "admin/user";
    }
}
