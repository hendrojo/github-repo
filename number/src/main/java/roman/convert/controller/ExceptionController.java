package roman.convert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import roman.convert.util.Constant;

@Controller
public class ExceptionController {
	String strPath = Constant.strPath+"/error";
	
	@RequestMapping(value="/400")
	public String error400() {
		return strPath+"/400";
	}
	
	@RequestMapping(value ="/403")
	public String accessDenied() {
		return strPath+"/403";
	}
	
	@RequestMapping(value="/404")
	public String error404() {
		return strPath+"/404";
	}
	
	@RequestMapping(value="/500")
	public String error500() {
		return strPath+"/500";
	}
}