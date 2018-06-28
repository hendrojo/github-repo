package roman.convert.controller;

import java.util.Map.Entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import roman.convert.util.Constant;
import roman.convert.util.Util;

@Controller
@EnableWebMvc
public class IndexController {
	String strPath = Constant.strPath;
	
	@RequestMapping(value = { "/index","/" })
	public String index(Model model) {
		if (Constant.mapRomanSymbol.isEmpty()) {
			Util.initializeMap();
		}
		model.addAttribute("RomanSymbol",Constant.mapRomanSymbol);
		model.addAttribute("AlienSymbol",Constant.mapAlienSymbol);
		model.addAttribute("MetalCredits",Constant.mapMetalCredits);
		return strPath+"/index";
	}
	
	@ResponseBody
	@RequestMapping(value = { "/convert_text" })
	public String convert_text(
		@RequestParam("data") String strData
	) {
		String strResult = "";
		try {
			strData = strData.toUpperCase();
			String [] arrInput = strData.split("\n");
			String [] arrSplitLine = null;
			for (int i=0;i<arrInput.length;i++) {
				arrSplitLine = arrInput[i].split(" ");
				if (arrSplitLine.length==3) {
					strResult += Util.setAlienSymbol(arrSplitLine);
				} else if ("CREDITS".equals(arrSplitLine[arrSplitLine.length-1])) {
					strResult += Util.setMetalCredits(arrSplitLine);
				} else if ("?".equals(arrSplitLine[arrSplitLine.length-1])) {
					if ("HOW MUCH IS".equals(arrSplitLine[0] + " " + arrSplitLine[1] + " " + arrSplitLine[2])) {
						strResult += Util.countHowMuch(arrSplitLine);
					} else if ("HOW MANY CREDITS IS".equals(arrSplitLine[0] + " " + arrSplitLine[1] + " " + 
							arrSplitLine[2] + " " + arrSplitLine[3])) {
						strResult += Util.countHowMany(arrSplitLine);
					} else {
						strResult = strResult + Constant.strNoResult+"\n";
					}
				} else {
					strResult = strResult + Constant.strNoResult+"\n";
				}
			}
		} catch (Exception ex) {
			strResult = strResult + Constant.strNoResult+"\n";
		}
		return strResult;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/get_alien_symbol" })
	public String get_alien_symbol() {
		String strTableBody = "";
		for (Entry<String,String> entry : Constant.mapAlienSymbol.entrySet()) {
			strTableBody = strTableBody + "<tr>";
			strTableBody = strTableBody + "<td>"+entry.getKey()+"</td>";
			strTableBody = strTableBody + "<td>"+entry.getValue()+"</td>";
			strTableBody = strTableBody + "</tr>";
        }
		return strTableBody;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/get_metal_credits" })
	public String get_metal_credits() {
		String strTableBody = "";
		for (Entry<String,Double> entry : Constant.mapMetalCredits.entrySet()) {
			strTableBody = strTableBody + "<tr>";
			strTableBody = strTableBody + "<td>"+entry.getKey()+"</td>";
			strTableBody = strTableBody + "<td>"+entry.getValue()+"</td>";
			strTableBody = strTableBody + "</tr>";
        }
		return strTableBody;
	}
}
