package com.example.demo.controller;

import com.example.demo.mail.EmailServiceImpl;
import com.example.demo.model.User;
import com.example.demo.model.UserGender;
import com.example.demo.model.UserType;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.CurrentUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${image.upload.url}")
    private String url;
    private String str_ = "";



    @PostMapping("/forgot")
    public String forgot(@RequestParam("e") String email, RedirectAttributes attributes, ModelMap map) {
        User user = userRepository.findOneByEmail(email);
        if (user != null) {
            if (!user.isVerify()) {
                return "redirect:/verifyError";
            }
            str_ = email;
            int k = (int) (Math.floor(Math.random() * 8999) + 1000);
            user.setCode(k);
            userRepository.save(user);
            String message = String.format("Dear %s \n Use %s to verify your AlloMarket account", user.getName(), k);
            emailService.sendSimpleMessage(user.getEmail(), "Forgot Password", message);
            return "redirect:/swapPassword";
        }
        attributes.addFlashAttribute("err", "Email is not found");
        return "redirect:/forgotUsPass";
    }

    @GetMapping("/getImg/{picUrl}")
    public void getImg(@PathVariable(name = "picUrl") String str, HttpServletResponse response) throws IOException {
        InputStream in = new FileInputStream(url + str);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }
    @GetMapping("/getImgById/{id}")
    public void getImgById(@PathVariable(name = "id") int id, HttpServletResponse response) throws IOException {
        User user=userRepository.findOneById(id);
        InputStream in = new FileInputStream(url + user.getPicUrl());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, @RequestParam("confPassword") String pass, @RequestParam("gender") UserGender userGender, @RequestParam("picture") MultipartFile multipartFile
            , RedirectAttributes attributes) throws IOException {
        if (user.getPassword().equals(pass) && !userRepository.existsUserByEmail(user.getEmail()) && !userRepository.existsUserByPhone(user.getPhone()) && validPhone(user.getPhone()) && user.getPassword().length() >= 8) {
            if (!multipartFile.getOriginalFilename().isEmpty()) {
                File file = new File(url);
                file.mkdir();
                String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
                multipartFile.transferTo(new File(url + fileName));
                user.setPicUrl(fileName);
            } else
                user.setPicUrl("default.png");
            user.setType(UserType.USER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setGender(userGender);
            user.setToken(UUID.randomUUID().toString());
            user.setVerify(false);
            user.setActive(0);
            userRepository.save(user);
            String messUrl = String.format("http://localhost:8081/verify?email=%s&token=%s", user.getEmail(), user.getToken());
            String message = String.format("Welcome dear %s \n Please follow link to activate your profile in  AlloMarket \n %s", user.getName(), messUrl);
            emailService.sendSimpleMessage(user.getEmail(), "Welcome AlloMarket", message);
            return "redirect:/";
        }
        if (!user.getPassword().equals(pass))
            attributes.addFlashAttribute("errorConfPass", "Password is not correct");
        if (!validPhone(user.getPhone()))
            attributes.addFlashAttribute("errorPhone", "Phone is not correct");
        else if (userRepository.existsUserByPhone(user.getPhone()))
            attributes.addFlashAttribute("errorPhone", "Phone is busy");
        if (userRepository.existsUserByEmail(user.getEmail()))
            attributes.addFlashAttribute("errorMail", "Email is busy");
        if (user.getPassword().length() < 8)
            attributes.addFlashAttribute("errorPass", "Minimum password length 8 characters");

        return "redirect:/signUp";
    }

    @GetMapping("/verify")
    public String verify(@RequestParam(name = "email") String email, @RequestParam(name = "token") String token) {
        User user = userRepository.findOneByEmail(email);
        if (user != null) {
            if (user.getToken() != null && user.getToken().equals(token)) {
                user.setVerify(true);
                user.setToken(null);
                userRepository.save(user);
            }
        }
        return "redirect:/";
    }

    @PostMapping("/swapPass")
    public String swapPass(@RequestParam("confPass") String confPass, @RequestParam("pass") String pass, @RequestParam("code") String code, RedirectAttributes attributes) {
        User user = userRepository.findOneByEmail(str_);
        if (!pass.equals(confPass)) {
            attributes.addFlashAttribute("err1", "Password is not correct");
        }
        if (!String.valueOf(user.getCode()).equals(code)) {
            attributes.addFlashAttribute("err2", "Activate code is not correct");
        }
        if (pass.equals(confPass) && String.valueOf(user.getCode()).equals(code)) {
            user.setPassword(passwordEncoder.encode(pass));
            user.setCode(0);
            userRepository.save(user);
            return "redirect:/";
        }
        return "redirect:/swapPassword";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("edUs") User user, @RequestParam("password") String str,@RequestParam("img") MultipartFile multipartFile,@AuthenticationPrincipal UserDetails userDetails) throws IOException {
        User us = ((CurrentUser) userDetails).getUser();
        System.err.println(str);
        if (!str.equals(""))
            us.setPassword(passwordEncoder.encode(str));
        if (!multipartFile.getOriginalFilename().isEmpty()) {
            File file = new File(url);
            file.mkdir();
            String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(url + fileName));
            us.setPicUrl(fileName);
        }
        us.setName(user.getName());
        us.setSurname(user.getSurname());
        us.setAge(user.getAge());
        us.setGender(user.getGender());

        userRepository.save(us);
        return "redirect:/user";
    }


    public static boolean validPhone(String str) {
        Pattern pattern = Pattern.compile("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}