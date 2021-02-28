package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AjaxResponseController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductImgRepository productImgRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FeedBackRepository feedBackRepository;
    @Autowired
    private StripeService paymentsService;
    @Value("${stripe.public.key}")
    private String stripePublicKey;
    @Value("${image.upload.productUrl}")
    private String imageUpload;
    private int prId_;
    private int sum;


    @GetMapping("/viewCart")
    public @ResponseBody
    void viewCart(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) throws IOException {
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            response.setContentType("text/html;charset=UTF-8");
            List<Product> list = productRepository.findAll();
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                if (cartRepository.findOneByUserIdAndProductId(user.getId(), list.get(i).getId()) != null && j <= 3) {
                    j++;
                    response.getWriter().write(list.get(i).getId() + " " + list.get(i).getTitle() + " " + list.get(i).getPrice() + "&");
                }
            }

        }
    }

    @GetMapping("/blockUser")
    public
    @ResponseBody
    void blockUser(@RequestParam("id") int id, @RequestParam("sec") int sec) {
        userRepository.updateUserByActive(sec, id);
    }

    @GetMapping("/stopTimer")
    public
    @ResponseBody
    void stopTimer(@RequestParam("id") int id, @RequestParam("sec") int sec, HttpServletResponse response) throws IOException {
        userRepository.updateUserByActive(sec, id);
        response.setContentType("text/plain");
        if (userRepository.findAllByActive().isEmpty()) {
            response.getWriter().write("end");
        }
    }

    @GetMapping("/blockUserSec")
    public
    @ResponseBody
    void blockUserSec(HttpServletResponse response) throws IOException {
        List<User> list = userRepository.findAllByActive();
        response.setContentType("text/plain");
        // Map<Integer, Integer> m = new LinkedHashMap<>();
        for (User user : list) {
            int c = user.getActive();
            c--;
            userRepository.updateUserByActive(c, user.getId());
            //m.put(user.getId(), c);
            response.getWriter().write(user.getId() + "=" + user.getActive() + ",");
        }
    }

    @GetMapping("/unBlockUser")
    public
    @ResponseBody
    void unBlockUser(@RequestParam("id") int id) {
        userRepository.updateUserByActive(0, id);
    }

    @GetMapping("/buyProduct")
    public @ResponseBody
    void buyProduct(@AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        List<Cart> carts = cartRepository.findAllByUserId(user.getId());
        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        for (Cart cart : carts) {
            Product p = productRepository.findOneById(cart.getProductId());
            List<ProductImg> pics = productImgRepository.findAllByProductId(p.getId());
            double d = cart.getQuantity() * p.getPrice();
            Order order = Order.builder()
                    .prId(p.getId())
                    .usId(user.getId())
                    .date(time)
                    .total(d)
                    .quantity(cart.getQuantity())
                    .picUrl(pics.get(0).getPicUrl())
                    .productTitle(p.getTitle())
                    .build();
            orderRepository.save(order);
            if (p.getCount() == 1 || p.getCount() == cart.getQuantity()) {
                productRepository.deleteProductById(p.getId());
            } else {
                int c = p.getCount();
                c--;
                p.setCount(c);
                productRepository.updateCart(p.getCount(), p.getCount());
            }
        }

        cartRepository.deleteAll();
    }

    @GetMapping("/getProductByCat")
    public @ResponseBody
    void getProductByCat(@RequestParam("catId") int id, HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User user = ((CurrentUser) userDetails).getUser();
        List<Product> list = productRepository.findAllByCategoryIdAndUser(id, user);
        response.setContentType("text/html;charset=UTF-8");
        for (Product product : list) {
            if (product.getActive() == 0) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "orange" + "\n");
            } else if (product.getActive() == -1) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "red" + "\n");
            } else if (product.getActive() == 1) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "green" + "\n");
            }
        }
    }

    @PostMapping("/allProducts")
    public @ResponseBody
    void allProducts(HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        List<Product> list = productRepository.findAllByUser(((CurrentUser) userDetails).getUser());
        response.setContentType("text/html;charset=UTF-8");
        for (Product product : list) {
            if (product.getActive() == 0) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "orange" + "\n");
            } else if (product.getActive() == -1) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "red" + "\n");
            } else if (product.getActive() == 1) {
                response.getWriter().write(product.getTitle() + "&copy;" + product.getPrice() + "&copy;" + product.getId() + "&copy;" + "green" + "\n");
            }
        }
    }

    @PostMapping("deletePr")
    public @ResponseBody
    void deletePr(@RequestParam("prId") int id, HttpServletResponse response) throws IOException {
        Product product = productRepository.findOneById(id);

        List<ProductImg> imgs = product.getProductList();
        String str = "";
        for (int i = 0; i < imgs.size(); i++) {
            str = imgs.get(0).getPicUrl();
            System.out.println(imgs.get(i).getPicUrl());
            System.out.println(new File(imageUpload + imgs.get(i).getPicUrl()).delete());
        }
        System.err.println(str);
        if (!str.equals("")) {
            System.out.println(new File(imageUpload + str).delete());
        }
        productRepository.deleteProductById(id);
        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(id));

    }

    @GetMapping("/searchProduct")
    public @ResponseBody
    void searchProduct(@RequestParam("str") String str, HttpServletResponse resp) throws IOException {
        List<Product> list = productRepository.findAll();
        resp.setContentType("text/html;charset=UTF-8");
        for (Product product : list) {
            if (product.getTitle().toLowerCase().contains(str.toLowerCase())) {
                String s = product.getTitle() + "&" + product.getId() + "*";
                resp.getWriter().write(s);
            }
        }
    }

    @GetMapping("/addWishList")
    public @ResponseBody
    void addWishList(@RequestParam("id") int id, HttpServletResponse res, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        res.setContentType("text/plain");
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            if (cartRepository.findOneByUserIdAndProductId(user.getId(), id) == null) {
                productRepository.addLike(user.getId(), id);
            } else {
                res.getWriter().write("Wish");
            }
        } else {
            res.getWriter().write("notFound");
        }
    }

    @GetMapping("/deleteWishListPr")
    public @ResponseBody
    void deleteWishListPr(@RequestParam("prId") int prId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        wishListRepository.deleteWishListById(wishListRepository.findOneByUserIdAndProductId(user.getId(), prId).getId());
    }

    @GetMapping("addToCart")
    public @ResponseBody
    void addToCart(@RequestParam("id") int id, @AuthenticationPrincipal UserDetails userDetails, HttpServletResponse res) throws IOException {
        if (userDetails == null) {
            res.setContentType("text/plain");
            res.getWriter().write("notFound");
        } else {
            User user = ((CurrentUser) userDetails).getUser();
            Cart c = Cart.builder().userId(user.getId()).productId(id).quantity(1).build();
            cartRepository.save(c);
            if (wishListRepository.findOneByUserIdAndProductId(user.getId(), id) != null) {
                WishList wishList = wishListRepository.findOneByUserIdAndProductId(user.getId(), id);
                wishListRepository.deleteWishListById(wishList.getId());
            }
        }
    }

    @GetMapping("/addToCartWishList")
    public @ResponseBody
    void addToCartWishList(@RequestParam("id") int id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        Cart c = Cart.builder().userId(user.getId()).productId(id).quantity(1).build();
        cartRepository.save(c);
        WishList wishList = wishListRepository.findOneByUserIdAndProductId(user.getId(), id);
        System.err.println(wishList);
        wishListRepository.deleteWishListById(wishList.getId());
    }

    @GetMapping("/getQuantity")
    public @ResponseBody
    void getQuantity(@RequestParam("id") int prId, @RequestParam("val") String val, HttpServletResponse response, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        double price = productRepository.findOneById(prId).getPrice();
        double total = price * Integer.parseInt(val);
        User user = ((CurrentUser) userDetails).getUser();
        Cart cart = cartRepository.findOneByUserIdAndProductId(user.getId(), prId);
        cart.setQuantity(Integer.parseInt(val));
        cartRepository.updateCart(Integer.parseInt(val), cart.getId());
        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(total));
    }

    @GetMapping("/deleteCart")
    public @ResponseBody
    void deleteCart(@RequestParam("id") int id) {
        cartRepository.deleteCartByProductId(id);
    }

    @GetMapping("/acceptProduct")
    public
    @ResponseBody
    void acceptProduct(@RequestParam("id") int id) {
        productRepository.updateActive(1, id);
    }

    @GetMapping("/ignoreProduct")
    public
    @ResponseBody
    void ignoreProduct(@RequestParam("id") int id) {
        productRepository.updateActive(-1, id);
    }

}
