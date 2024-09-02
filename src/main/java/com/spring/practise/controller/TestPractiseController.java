package com.spring.practise.controller;

import com.spring.practise.exception.ServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for test REST endpoints
 *
 */
@RestController
@RequestMapping(path = "api/rest/practise")
public class TestPractiseController extends BaseController {

    @GetMapping("/test")
    public void getTestApi() throws ServiceException {

    }

}
