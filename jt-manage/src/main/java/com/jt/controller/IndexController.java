package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//localhost:8090/page/item-add
	@RequestMapping("/page/{moduleName}")
	public String indexPage(@PathVariable String moduleName) {
		return moduleName;
	}
}
