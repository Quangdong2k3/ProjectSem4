package com.StoreBook.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.StoreBook.entity.Author;
import com.StoreBook.service.AuthorService;

@Controller
@RequestMapping(value = "/BookStore/admin/author")
public class AuthorController {
	@Autowired
	AuthorService authorService;

	@GetMapping(value = "")
	public String index(Model model) {
		model.addAttribute("titleName", "Author");
		model.addAttribute("pageName", "Author");
		model.addAttribute("list", authorService.getAll());
		return "admin/author/author";
	}

	@GetMapping(value = "create")
	public String create(Model model) {
		model.addAttribute("titleName", "Author");
		model.addAttribute("pageName", "Thêm tác giả");
		model.addAttribute("author", new Author());

		return "admin/author/insert";
	}

	@PostMapping(value = "insert")
	public String add(@Validated @ModelAttribute("author") Author author, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println(result);
			return "admin/author/insert";

		}
		authorService.add(author);
		return "redirect:/BookStore/admin/author";
	}

	@GetMapping(value = "edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		model.addAttribute("titleName", "Author");
		model.addAttribute("pageName", "Thêm tác giả");
		model.addAttribute("Author", authorService.getById(id));

		return "admin/author/edit";
	}

	@PostMapping(value = "update/{id}")
	public ModelAndView update(@PathVariable("id") Long id, @Valid @ModelAttribute("Author") Author author,
			BindingResult rs) {
		if (rs.hasErrors()) {
			ModelAndView view = new ModelAndView("admin/author/edit");
			view.addObject("Author", author);
			return view;
		} else {
			authorService.update(id, author);
			return new ModelAndView(new RedirectView("/admin/author"));
		}

	}

	@GetMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id) {

		authorService.delete(id);
		return "redirect:/BookStore/admin/author";
	}
}
