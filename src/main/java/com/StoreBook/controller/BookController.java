package com.StoreBook.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;

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
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.StoreBook.DTO.BookAuthor;
import com.StoreBook.DTO.BookDTO;
import com.StoreBook.DTO.ImageDTO;

import com.StoreBook.entity.Image;
import com.StoreBook.repository.ImageRepository;
import com.StoreBook.service.AuthorService;
import com.StoreBook.service.BookService;
import com.StoreBook.service.CategoryService;
import com.StoreBook.service.PublisherService;

;

@Controller
@RequestMapping("/BookStore/admin/book")
public class BookController {
	@Autowired
	BookService bookService;

	@GetMapping("")

	public String index(Model model) {
		model.addAttribute("titleName", "Book");
		model.addAttribute("pageName", "Book");
		model.addAttribute("list", bookService.getAll());

		return "admin/book/book";
	}

	@GetMapping("BookImg")

	public String imgImg(Model model) {
		model.addAttribute("titleName", "Book");
		model.addAttribute("pageName", "Book");
		model.addAttribute("booklst", bookService.getAll());
		model.addAttribute("image", new ImageDTO());
		return "admin/book/bookimg";
	}

	@Autowired
	ImageRepository imageRepository;

	@GetMapping("img")
	public String anh() {
		return "admin/book/image";
	}

	private String uploadDir = "./src/main/resources/static/images/";

	@PostMapping("saveimg")
	public String up(@ModelAttribute(name = "image") @Valid ImageDTO fo, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("titleName", "Book");
			model.addAttribute("pageName", "Book");
			model.addAttribute("booklst", bookService.getAll());

			return "admin/book/bookimg";
		} else {
			Image image = new Image();

			int count = 0;

			for (MultipartFile f : fo.getFiles()) {
				if (count == 0) {
					image.setImgone(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}
				if (count == 1) {
					image.setImgtwo(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}
				if (count == 2) {
					image.setImgthree(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}
				if (count == 3) {
					image.setImgfour(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}
				if (count == 4) {
					image.setImgfive(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}
				count++;
			}

			imageRepository.save(image);
			return "redirect:";
		}

	}

	private boolean checkImgExist(String uploadDirectory, MultipartFile file) {
		boolean isFlag = false;
		try {

			// Kiểm tra xem tệp đã tồn tại trong thư mục hay chưa
			if (!Files.exists(Paths.get(uploadDirectory + file.getOriginalFilename()))) {

				// Lưu tệp vào thư mục
				Files.copy(file.getInputStream(), Paths.get(uploadDirectory + file.getOriginalFilename()));
			}

			// Lưu thông tin ảnh vào cơ sở dữ liệu

			isFlag = true;

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return isFlag;
	}

	@Autowired
	CategoryService categoryService;
	@Autowired
	PublisherService publisherService;

	@GetMapping("create")
	public String create(Model model) {
		model.addAttribute("titleName", "Book");
		model.addAttribute("pageName", "Book");
		model.addAttribute("book", new BookDTO());
		model.addAttribute("category", categoryService.getAll());
		model.addAttribute("publisher", publisherService.getAll());
		return "admin/book/insert";
	}

	@PostMapping(value = "insert")
	public String save(@Valid @ModelAttribute("book") BookDTO bookDTO,
			BindingResult rs, Model model, @RequestParam(name = "files") MultipartFile[] files,
			RedirectAttributes redirectAttributes) throws ParseException {

		if (rs.hasErrors()) {
			// System.out.println(rs.toString());
			// model.addAttribute("book", bookDTO);
			redirectAttributes.addFlashAttribute("publication_date", bookDTO.getPublication_date());
			model.addAttribute("category", categoryService.getAll());
			model.addAttribute("publisher", publisherService.getAll());
			return "admin/book/insert";
		} else {
			int dem = 0;
			for (MultipartFile f : bookDTO.getFiles()) {
				if (dem == 0) {
					bookDTO.setImage(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}

			}
			System.out.println(bookDTO.getPublication_date());
			redirectAttributes.addFlashAttribute("message", "Thêm dữ liệu thành công");
			bookService.add(bookDTO);
			return "redirect:/BookStore/admin/book";
		}

	}

	@PostMapping(value = "{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("message", "Xóa dữ liệu thành công");
		bookService.delete(id);
		return "redirect:/BookStore/admin/book";
	}

	@GetMapping("{id}/edit")
	public String edit(@PathVariable(name = "id") Long id, Model model) {
		model.addAttribute("titleName", "Book");
		model.addAttribute("pageName", "Book");
		model.addAttribute("book", bookService.getById(id));
		model.addAttribute("category", categoryService.getAll());
		model.addAttribute("publisher", publisherService.getAll());
		return "admin/book/edit";
	}

	@PostMapping(value = "{id}/update")
	public ModelAndView update(@PathVariable(name = "id") Long id, @Valid @ModelAttribute("book") BookDTO bookDTO,
			BindingResult rs, Model model, @RequestParam(name = "files") MultipartFile[] files,
			RedirectAttributes redirectAttributes) throws ParseException {

		if (rs.hasErrors()) {
			// System.out.println(rs.toString());
			// model.addAttribute("book", bookDTO);
			redirectAttributes.addFlashAttribute("publication_date", bookDTO.getPublication_date());
			model.addAttribute("category", categoryService.getAll());
			model.addAttribute("publisher", publisherService.getAll());
			return new ModelAndView("admin/book/edit");
		} else {
			int dem = 0;
			for (MultipartFile f : bookDTO.getFiles()) {
				if (dem == 0) {
					bookDTO.setImage(f.getOriginalFilename());
					checkImgExist(uploadDir, f);
				}

			}
			System.out.println(bookDTO.getPublication_date());
			redirectAttributes.addFlashAttribute("message", "Cập nhật dữ liệu thành công");

			bookService.update(id, bookDTO);
			return new ModelAndView(new RedirectView("/BookStore/admin/book"));
		}

	}
	@Autowired 
	 AuthorService authorService;
	@GetMapping(value="book_Author")
	public ModelAndView creatBookAuthor(@Valid @ModelAttribute BookAuthor bookAuthor,BindingResult bindingResult){
		ModelAndView view = new ModelAndView("admin/book_author/insert");
		view.addObject("titleName", "Create Book Author");
		view.addObject("pageName", "Create Book Author");
		view.addObject("book", bookService.getAll());
		view.addObject("author", authorService.getAll());
		view.addObject("bookauthor",new BookAuthor());

		return view;
	}
	@PostMapping(value="book_Author")
	public ModelAndView insertBookAuthor(@Valid @ModelAttribute(name="bookauthor") BookAuthor bookAuthor,BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()){
			ModelAndView view = new ModelAndView("admin/book_author/insert");
			view.addObject("titleName", "Create Book Author");
			view.addObject("pageName", "Create Book Author");
			view.addObject("book", bookService.getAll());
			view.addObject("author", authorService.getAll());
			// view.addObject("bookauthor",bookAuthor);
	
			return view;
		}else{
			redirectAttributes.addFlashAttribute("message", "Thêm dữ liệu thành công");
			bookService.addBookAuthor(bookAuthor);
			return new ModelAndView(new RedirectView("/admin/book/book_Author"));
		}
		
	}
}
