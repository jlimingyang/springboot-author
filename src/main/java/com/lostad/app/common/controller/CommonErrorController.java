package com.lostad.app.common.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonErrorController implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(ERROR_PATH)
	public String handleError() {
		return "common/error";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}