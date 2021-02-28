package com.example.demo.controller;

import com.example.demo.mail.EmailServiceImpl;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    private int id;
    private List<Product> list_ = new ArrayList<>();
    private List<Product> cartsProduct = new ArrayList<>();
    private String prName_;


    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            for (Product product : productRepository.findAll()) {
                if (wishListRepository.countByUserIdAndProductId(user.getId(), product.getId()) == 1) {
                    list_.add(product);
                }
                if (cartRepository.findOneByUserIdAndProductId(user.getId(), product.getId()) != null) {
                    cartsProduct.add(product);
                }
            }
        }
        map.addAttribute("carts", cartsProduct);
        map.addAttribute("wishProduct", list_);
        map.addAttribute("allProduct", productRepository.findAll());


        return "index";
    }

    @GetMapping("/editProduct")
    public String editProduct(@ModelAttribute("edPr") Product product, ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {

        map.addAttribute("edPr", product);
        map.addAttribute("us", ((CurrentUser) userDetails).getUser());
        map.addAttribute("cats", categoryRepository.findAll());
        return "/editProduct";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            map.addAttribute("us", user);
        }
        return "about";
    }

    @GetMapping("/productByCat")
    public String productByCat(@AuthenticationPrincipal UserDetails userDetails, ModelMap map, @ModelAttribute("prNameByCat") String str) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            for (Product product : productRepository.findAll()) {
                if (wishListRepository.countByUserIdAndProductId(user.getId(), product.getId()) == 1) {
                    list_.add(product);
                }
                if (cartRepository.findOneByUserIdAndProductId(user.getId(), product.getId()) != null) {
                    cartsProduct.add(product);
                }
            }
        }
        if (str.equals(""))
            str = prName_;
        prName_ = str;

        map.addAttribute("prByCat", productRepository.findAllByCategoryName(str));
        map.addAttribute("catName", str);
        map.addAttribute("wishProduct", list_);
        map.addAttribute("carts", cartsProduct);
        map.addAttribute("allCat", categoryRepository.findAll());
        return "productByCat";
    }



    @GetMapping("/order")
    public String order(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            map.addAttribute("myOrders", orderRepository.findAllByUsId(user.getId()));
        }
        return "order";
    }

    @GetMapping("/wishlist")
    public String wishlist(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            for (Product product : productRepository.findAll()) {
                if (wishListRepository.countByUserIdAndProductId(user.getId(), product.getId()) == 1) {
                    list_.add(product);
                }
            }
        }
        map.addAttribute("wishProduct", list_);
        return "wishlist";
    }


    @GetMapping("/shopDetail")
    public String shopDetail(@AuthenticationPrincipal UserDetails userDetails, ModelMap map, @ModelAttribute("viewPr") Product product) {
        int likeCount = 0;
        boolean containsCart = false;
        if (product.getId() == 0) {
            product = productRepository.findOneById(id);
        }
        if(product==null){
            return "redirect:/";
        }
        id = product.getId();
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            map.addAttribute("us", user);
            likeCount = wishListRepository.countByUserIdAndProductId(user.getId(), product.getId());
            if (cartRepository.findOneByUserIdAndProductId(user.getId(), product.getId()) != null) {
                containsCart = true;
            }
        }

        List<ProductImg> list = product.getProductList();
        ProductImg productImg = product.getProductList().get(0);
        list.remove(0);
        List<OutputMessage> comments = feedBackRepository.findAllByProductId(product.getId());

        map.addAttribute("containsCart", containsCart);
        map.addAttribute("comments", comments);
        map.addAttribute("viewPr", product);
        map.addAttribute("prImg", list);
        map.addAttribute("activeImg", productImg);
        map.addAttribute("likeCount", likeCount);
        return "shop-detail";
    }

    @GetMapping("/service")
    public String service(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            map.addAttribute("us", user);
        }
        return "service";
    }


    @GetMapping("/shop")
    public String shop(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            if(user.getType().equals(UserType.USER)) {
                map.addAttribute("type", "USER");
            }else{
                map.addAttribute("type", "ADMIN");
            }
            for (Product product : productRepository.findAll()) {
                if (wishListRepository.countByUserIdAndProductId(user.getId(), product.getId()) == 1) {
                    list_.add(product);
                }
                if (cartRepository.findOneByUserIdAndProductId(user.getId(), product.getId()) != null) {
                    cartsProduct.add(product);
                }
            }
        }
        map.addAttribute("wishProduct", list_);
        map.addAttribute("carts", cartsProduct);
        map.addAttribute("allProduct", productRepository.findAll());
        map.addAttribute("allCat", categoryRepository.findAll());
        return "shop";
    }

    @GetMapping("editUs")
    public String editUs(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("edUs", user);
        if(user.getType().equals(UserType.USER)) {
            map.addAttribute("type", "USER");
        }else{
            map.addAttribute("type", "ADMIN");
        }
        return "editUs";
    }

    @GetMapping("/user")
    public String userPage(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("us", user);
        map.addAttribute("cats", categoryRepository.findAll());
        map.addAttribute("products", productRepository.findAllByUser(user));
        return "userPage";
    }


    @GetMapping("/admin")
    public String adminPage(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("us", user);
        return "adminPage";
    }

    @GetMapping("/allUser")
    public String allUser(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("us", user);

        map.addAttribute("allUsers", userRepository.findAll());
        return "allUser";
    }

    @GetMapping("/allProduct")
    public String allProduct(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("us", user);

        map.addAttribute("allProducts", productRepository.findAll());
        return "allProduct";
    }

    @GetMapping("/signIn")
    public String signIn(ModelMap map) {
        map.addAttribute("user", new User());
        return "signIn";
    }

    @GetMapping("/verifyError")
    public String verifyError() {
        return "verifyError";
    }

    @GetMapping("/block")
    public String block() {

        return "block";
    }


    @GetMapping("/addProduct")
    public String addProduct(ModelMap map, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        map.addAttribute("us", user);

        map.addAttribute("pr", new Product());
        map.addAttribute("cats", categoryRepository.findAll());
        return "addProduct";
    }

    @GetMapping("/successForgotPass")
    public String successForgotPass() {
        return "successForgotPass";
    }

    @GetMapping("/swapPassword")
    public String swapPassword(ModelMap map, @ModelAttribute("err") String str, @ModelAttribute("err1") String str1, @ModelAttribute("err2") String str2) {
        if (str.isEmpty())
            str = "Enter new password";
        if (str1.isEmpty())
            str1 = "Confirm password";
        if (str2.isEmpty())
            str2 = "Enter activate code";
        map.addAttribute("err", str);
        map.addAttribute("err1", str1);
        map.addAttribute("err2", str2);
        return "swapPassword";
    }

    @GetMapping("/forgotUsPass")
    public String forgotPass(ModelMap map, @ModelAttribute("err") String err) {
        if (err.isEmpty())
            err = "Enter email";
        map.addAttribute("err", err);
        return "forgotPass";
    }


    @GetMapping("/login")
    public String login(@AuthenticationPrincipal UserDetails userDetails) {
        CurrentUser currentUser = (CurrentUser) userDetails;
        if (currentUser != null) {
            if (currentUser.getUser().getType().equals(UserType.ADMIN))
                return "redirect:/admin";
            else
                return "redirect:/user";
        }
        return "redirect:/signIn";
    }

    @GetMapping("/signUp")
    public String signUp(ModelMap map, @ModelAttribute("errorPhone1") String phone1, @ModelAttribute("errorPhone") String phone, @ModelAttribute("errorPass") String pass1, @ModelAttribute("errorConfPass") String pass, @ModelAttribute("errorMail") String mail) {
        if (pass.equals("")) {
            pass = "Confirm Password";
        }
        if (pass1.equals("")) {
            pass1 = "Enter Password";
        }
        if (mail.equals("")) {
            mail = "Enter Mail";
        }
        if (phone.equals("")) {
            phone = "Enter phone";
        }

        map.addAttribute("errorConfPass", pass);
        map.addAttribute("errorPass", pass1);
        map.addAttribute("errorMail", mail);
        map.addAttribute("errorPhone", phone);
        map.addAttribute("user", new User());
        return "signUp";
    }
}
