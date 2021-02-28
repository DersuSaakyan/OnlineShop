package com.example.demo.controller;


import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.security.CurrentUser;
import com.example.demo.security.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
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

    @PostMapping("/addNewProduct")
    public String addNewProduct(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute(name = "pr") Product product, @RequestParam("picUrl") List<MultipartFile> files, @RequestParam("radio") int catId) throws IOException {

        for (MultipartFile file : files) {

            File f = new File(imageUpload);
            f.mkdir();
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            file.transferTo(new File(imageUpload + fileName));
            product.getProductList().add(ProductImg.builder().product(product).picUrl(fileName).build());
        }
        product.setUser(((CurrentUser) userDetails).getUser());
        product.setCategory(categoryRepository.findOneById(catId));
        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        product.setDate(time);
        product.setActive(0);
        productRepository.save(product);
        return "redirect:/addProduct";
    }




    @GetMapping("/getProductImg/{id}")
    public void getImg(@PathVariable(name = "id") int id, HttpServletResponse response) throws IOException {
        List<ProductImg> imgs = productImgRepository.findAllByProductId(id);

        InputStream in = new FileInputStream(imageUpload + imgs.get(0).getPicUrl());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/getOrdersImg/{str}")
    public void getOrdersImg(@PathVariable(name = "str") String str, HttpServletResponse response) throws IOException {

        InputStream in = new FileInputStream(imageUpload + str);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/getProductAllImg/{picUrl}")
    public void getAllImg(@PathVariable(name = "picUrl") String picUrl, HttpServletResponse response) throws IOException {

        InputStream in = new FileInputStream(imageUpload + picUrl);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }


    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        Product product = productRepository.findOneById(id);
        redirectAttributes.addFlashAttribute("viewPr", product);
        return "redirect:/shopDetail";
    }

    @GetMapping("/editPr/{id}")
    public String editPr(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        prId_ = id;
        Product product = productRepository.findOneById(id);
        redirectAttributes.addFlashAttribute("edPr", product);
        return "redirect:/editProduct";
    }

    @GetMapping("/productsByCat/{str}")
    public String productsByCat(@PathVariable String str, RedirectAttributes attributes) {
        attributes.addFlashAttribute("prNameByCat", str);
        return "redirect:/productByCat";
    }

    @PostMapping("/editNewProduct")
    public String editProduct(@ModelAttribute("edPr") Product product, @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((CurrentUser) userDetails).getUser();
        Product product1 = productRepository.findOneById(prId_);
        product1.setUser(user);
        product1.setTitle(product.getTitle());
        product1.setDescription(product.getDescription());
        product1.setCount(product.getCount());
        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        product1.setDate(time);
        product1.setPrice(product.getPrice());
        product1.setCategory(product.getCategory());
        product1.setActive(0);
        productRepository.save(product1);
        return "redirect:/user";
    }





    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal UserDetails userDetails, ModelMap map) {
        sum = 0;
        if (userDetails != null) {
            User user = ((CurrentUser) userDetails).getUser();
            map.addAttribute("us", user);
            List<Product> products = productRepository.findAll();
            List<Cart> carts = cartRepository.findAllByUserId(user.getId());
            List<Product> usCarts = new ArrayList<>();
            for (Cart cart : carts) {
                for (Product product : products) {
                    if (cart.getProductId() == product.getId() && cart.getUserId() == user.getId()) {
                        sum += product.getPrice();
                        usCarts.add(product);
                        break;
                    }
                }
            }
            map.addAttribute("usCarts", usCarts);
            map.addAttribute("cart", carts);
            map.addAttribute("amount", sum * 100); // in cents
            map.addAttribute("stripePublicKey", stripePublicKey);
            map.addAttribute("currency", ChargeRequest.Currency.USD);
        }
        return "cart";
    }


    @GetMapping("/myOrder")
    public String myOrder() {
        return "redirect:/order";
    }


    @PostMapping("/charge")
    public String charge(ChargeRequest chargeRequest, Model model,@AuthenticationPrincipal UserDetails userDetails)
            throws StripeException {
        User user = ((CurrentUser) userDetails).getUser();
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("us", user);
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }


    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public Message register(@Payload Message chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message chatMessage) {
        User user = userRepository.findOneById(chatMessage.getUsId());
        String time = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        Product product = productRepository.findOneById(chatMessage.getPrId());
        OutputMessage out = OutputMessage.builder()
                .user(user)
                .product(product)
                .comment(chatMessage.getContent())
                .date(time)
                .build();
        System.out.println(chatMessage);
        feedBackRepository.save(out);
        return chatMessage;
    }


}
