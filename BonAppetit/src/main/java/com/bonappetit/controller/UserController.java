package com.bonappetit.controller;

import com.bonappetit.model.DTO.UserLoginDto;
import com.bonappetit.model.DTO.UserRegisterDto;
import com.bonappetit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userModel")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @GetMapping("/login")
    public String getLogin(Model model){
        if (!model.containsAttribute("isLoginInfoCorrect")){
            model.addAttribute("isLoginInfoCorrect",true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDto userLoginDto, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        boolean isLoginSuccessful = userService.login(userLoginDto);

        if (isLoginSuccessful){
            return "redirect:/";
        }
        redirectAttributes
                .addFlashAttribute("isLoginInfoCorrect", false);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
        userService.logout();
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister(Model model){
        if (!model.containsAttribute("isValid")) {
            model.addAttribute("isValid", true);
        }
        if (!model.containsAttribute("passwordMatch")){
            model.addAttribute("passwordMatch",true);
        }
        return "register";
    }

    @PostMapping("/register")
    public String Register(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userModel",userRegisterDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterDto", userRegisterDto);
            redirectAttributes
                    .addFlashAttribute("passwordMatch", false);
            return "redirect:register";
        }

        boolean isUsed = userService.checkUsername(userRegisterDto.getUsername());

        if (isUsed) {
            redirectAttributes.addFlashAttribute("userRegisterDto",userRegisterDto);
            redirectAttributes.addFlashAttribute("isValid", false);
            return "redirect:register";
        }

        userService.registerUser(userRegisterDto);

        return "redirect:login";
    }
}
