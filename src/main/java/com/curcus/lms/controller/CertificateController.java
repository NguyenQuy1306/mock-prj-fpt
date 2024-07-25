package com.curcus.lms.controller;

import com.curcus.lms.auditing.ApplicationAuditAware;
import com.curcus.lms.exception.ApplicationException;
import com.curcus.lms.exception.NotFoundException;
import com.curcus.lms.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    @GetMapping(value = "")
    public ModelAndView certificate(Model model, @RequestParam Long studentId, @RequestParam Long courseId) {
        try {
            certificateService.updateModel(model, studentId, courseId);
            return new ModelAndView("certificate");
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }
}
