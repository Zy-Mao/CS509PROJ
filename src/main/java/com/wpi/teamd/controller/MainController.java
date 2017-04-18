package com.wpi.teamd.controller;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mao on 2017/4/17.
 */
@Controller
public class MainController {
	private static Logger logger = LogManager.getLogger(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		logger.debug("log in index");
		return "index";
	}
}
