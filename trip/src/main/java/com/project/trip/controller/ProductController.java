package com.project.trip.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.trip.dto.ReviewBoardDTO;
import com.project.trip.mapper.IBoardMapper;
import com.project.trip.mapper.ProductMapper;
import com.project.trip.vo.ProductVO;
import com.project.trip.vo.QNAVO;
import com.project.trip.vo.ReviewVO;

@Controller
public class ProductController {

	@Autowired
	ProductMapper mapper;
	
	@Autowired
	IBoardMapper b_mapper;
	
	@GetMapping("/list")
	public String japanList(@RequestParam("prod_category")String prod_category,Model model) {
		List<ProductVO> list = mapper.getList(prod_category);
		model.addAttribute("list", list);
	return "prod_list/japan_hongkong";
	}

	
	@GetMapping("/prod_detail")
	public String prod_detail(@RequestParam("no") String prod_nol, Model model) {
		System.out.println("상품디테일 : " + prod_nol);
		int prod_no = Integer.parseInt(prod_nol);
		ProductVO vo = mapper.getListOne(prod_no);
		List<ProductVO> list = new ArrayList<>();
		list.add(vo);
		model.addAttribute("list", list);

		List<ReviewBoardDTO> review_prod = b_mapper.review_selectProduct(prod_no);
		model.addAttribute("reviews", review_prod);
		List<QNAVO> qna_prod = b_mapper.qna_selectProduct(prod_no);
		model.addAttribute("qnas", qna_prod);
		
		
	return "prod_list/prod_detail";
	}
	
	@GetMapping("/board1")
	public @ResponseBody List<ReviewBoardDTO> getboard(int rev_no){
		List<ReviewBoardDTO> p1 = b_mapper.review_selectOne(rev_no);
		return p1;
	}
	@GetMapping("/board2")
	public @ResponseBody List<QNAVO> getQna(int qna_no){
		List<QNAVO> q1 = b_mapper.qna_selectOne(qna_no);
		return q1;
	}
	
	@PostMapping("/reg_review")
	public @ResponseBody int reviewReg(ReviewVO rev) {
		int result = b_mapper.insert_review(rev);
		return result;
		
	}
	
	@GetMapping("/like_prod")
	public @ResponseBody String product_liked(@RequestParam("id") String id, @RequestParam("prod_no") int prod_no, @RequestParam("like") int like) {
		int result = 0;
		String msg = null;
		if(like == 0) {
			result = mapper.reg_like(id, prod_no);
			msg = "등록 완료";
			
		}else if(like == 1){
			result = mapper.remove_like(id, prod_no);
			msg = "제거 완료";
		}
		return msg;
	}
	@GetMapping("/like_search")
	public @ResponseBody int search_like(@RequestParam("id") String id, @RequestParam("prod_no") int prod_no) {
		int result = mapper.search_like(id, prod_no);
		return result;
	}
	
	@GetMapping("/payno")
	public @ResponseBody int show_payNo(@RequestParam("id") String id, @RequestParam("prod_no") int prod_no) {
	//	String prodName = mapper.search_prodName(id, prod_no);
	//	return prodName;
		int payNo = b_mapper.pay_no_one(id, prod_no);
		return payNo;
	}
	
	@GetMapping("/prod_list2")
	public @ResponseBody List<ProductVO> show_prodName2(@RequestParam("id") String id) {
		List<ProductVO> prodNames = mapper.search_prodName2(id);
		return prodNames;
	}
	
	@GetMapping("/prod_info")
	public @ResponseBody ProductVO show_prodInfo(@RequestParam("prod_no") int prod_no) {
		ProductVO pro1 = mapper.getListOne(prod_no);
		return pro1;
	}
	// pay_no_one
}
