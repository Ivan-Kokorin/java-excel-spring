package org.linkapp.spring.link_for_price.controller;

import org.linkapp.spring.link_for_price.service.Process;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
public class MainController {
    private Process process;
    private String message;

    @Autowired
    public MainController(Process process) {
        this.process = process;
    }

    @GetMapping("/")
    public String main(Model model){
        return ("index");
    }

    @PostMapping("upload")
    public String handleFileUpload(@RequestParam("name") String name,
                                                 @RequestParam("file") MultipartFile file,
                                                 Model model){
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String nameFile = name + "-uploaded.xls";
                String pathFile = "src/main/resources/files/" + nameFile;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(pathFile)));
                stream.write(bytes);
                stream.close();
                message = "Вы удачно загрузили файл " + name + "!";
                model.addAttribute("message", message);
                model.addAttribute("path", pathFile);
                model.addAttribute("start", true);
                return ("upload");
            } catch (Exception e) {
                message = "Вам не удалось загрузить " + name + " => " + e.getMessage();
                model.addAttribute("message", message);
                model.addAttribute("start", false);
                return ("upload");
            }
        } else {
            message = "Вам не удалось загрузить " + name + " потому что файл пустой";
            model.addAttribute("message", message);
            model.addAttribute("start", false);
            return ("upload");
        }
    }

    @PostMapping("add-link")
    public String handleAddLinks(@RequestParam("path") String pathFile, Model model) {
        System.out.println(pathFile);
        process.initialize(new File(pathFile));
        process.run();
        message = "Файл обработан";
        model.addAttribute("message", message);
        return ("add-link");
    }

}