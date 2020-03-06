//package com.practica.security;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//public class CustomErrorControler implements ErrorController {
//
//	@RequestMapping("/error")
//	public String handleError(Model model, HttpServletRequest request) {
//		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//		String msg = "We regret to inform something went wrong, please try again later";
//		if (status != null) {
//			Integer statusCode = Integer.valueOf(status.toString());
//
//			if (statusCode == HttpStatus.NOT_FOUND.value()) {// 404
//				msg = "The page you are looking for is not available";
//			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {// 403
//				msg = "The page you are looking for needs special permissions";
//			} else if (statusCode == HttpStatus.BAD_REQUEST.value()) {// 400
//				msg = "We regret to inform something went wrong, please try again later";
//			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {// 500
//				msg = "We regret to inform something went wrong, please try again later";
//			}
//		}
//		model.addAttribute("errormsg", msg);
//		return "error";
//	}
//
//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
//}