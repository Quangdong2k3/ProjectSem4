package com.StoreBook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.StoreBook.service.OrderItemService;
import com.StoreBook.service.OrderService;

@Controller
@RequestMapping(value = "/BookStore/admin")

public class AdminController {
	@GetMapping()
	public String index() {
		return "admin/index";
	}

	@Autowired
	OrderService orderService;

	@GetMapping(value = "order")
	public String order(Model model) {
		model.addAttribute("title", "Hóa đơn");

		model.addAttribute("order", orderService.getAll());
		return "admin/order/order";
	}

	@Autowired
	OrderItemService orderItemService;

	@GetMapping(value = "invoice/{user_id}/order/{order_id}")
	public String invoice(Model model, @PathVariable Long user_id,@PathVariable Long order_id) {
		model.addAttribute("title", "Chi tiết Hóa đơn");

		System.out.println("Ádasdasdasd: " + user_id);

		model.addAttribute("orderItem", orderItemService.getByOrderITemUser_id(user_id));
		model.addAttribute("subtotal", orderItemService.subtotal(order_id));
		System.out.println(orderItemService.subtotal(order_id));

		return "admin/order/invoice";
	}


	@PostMapping(value = "order/{order_id}/update-status")
	public String updateStt( @RequestParam Long status,@PathVariable Long order_id,RedirectAttributes redirectAttributes) {
		orderService.updateStatus(status, order_id);
		redirectAttributes.addFlashAttribute("message", "Duyệt thành công!!!");
		System.out.println("STatus"+status);
		return "redirect:/BookStore/admin/order";
	}


	@GetMapping(value = "review")
	public String review( Model model) {
		model.addAttribute("title", "Danh sách khách hàng đánh giá");

		return "admin/review/review";
	}

}
