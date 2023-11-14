package com.StoreBook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.StoreBook.entity.Publisher;
import com.StoreBook.service.PublisherService;

@Controller
@RequestMapping(value = "/BookStore/admin/publisher")

public class PublisherController {
    @Autowired
    PublisherService publisherService;

    @GetMapping(value = "")
    public String index(Model model) {
        model.addAttribute("titleName", "Nhà xuất bản");
        model.addAttribute("pageName", "Nhà xuất bản");
        model.addAttribute("list", publisherService.getAll());
        return "admin/publisher/publisher";
    }

    @GetMapping(value = "create")
    public String create(Model model) {
        model.addAttribute("titleName", "Nhà xuất bản");
        model.addAttribute("pageName", "Thêm nhà xuất bản");
        model.addAttribute("Publishers", new Publisher());

        return "admin/publisher/insert";
    }

    @PostMapping(value = "insert")
    public String add(@Valid @ModelAttribute("Publishers") Publisher p, BindingResult result) {
        if (result.hasErrors()) {

            return "admin/publisher/insert";

        }
        publisherService.add(p);
        return "redirect:/BookStore/admin/publisher";
    }
    @GetMapping(value = "edit/{id}")
    public String edit(@PathVariable("id") Long id,Model model) {
        model.addAttribute("titleName", "Author");
        model.addAttribute("pageName", "Thêm tác giả");
        model.addAttribute("Publishers", publisherService.getById(id));

        return "admin/publisher/edit";
    }
    @PostMapping(value = "update/{id}")
    public ModelAndView update(@PathVariable("id") Long id,@Valid @ModelAttribute("Publishers") Publisher p,BindingResult rs) {
        if(rs.hasErrors()){
            ModelAndView view = new ModelAndView("admin/publisher/edit");
            view.addObject("Publishers", p);
            return view;
        }else{
            publisherService.update(id,p);
            return new ModelAndView(new RedirectView("/admin/publisher"));
        }
            
        
        
        
    }
    @GetMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        
        publisherService.delete(id);
        return "redirect:/BookStore/admin/publisher";
    }
}
