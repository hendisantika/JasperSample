package com.kritacademy.spring.sample.controller;

import com.kritacademy.spring.sample.models.entity.City;
import com.kritacademy.spring.sample.report.CustomJRDataSource;
import net.sf.jasperreports.engine.*;

import org.apache.log4j.Logger;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chertpong on 5/10/2016.
 */
@Controller
public class ReportController {
    final static Logger logger = Logger.getLogger(ReportController.class);
    final static String pdfSource = "src/main/resources/static/jasper/report.pdf";

    private void generateReport(List<City> cities) throws JRException{
        logger.info("[!] Start generate report");
        // Path to our template goes here
        JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/static/jasper/report.jrxml");
        // load data to datasource
        CustomJRDataSource<City> dataSource = new CustomJRDataSource<City>().using(cities);
        // Map datasource to template
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String,Object>(), dataSource);
        // Export to pdf
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfSource);
    }
    @RequestMapping(value = "/getReport", method = RequestMethod.GET)
    public HttpEntity<byte[]> getReport() throws JRException,IOException {
        // Stub data
        City c = new City("Chiang Mai",22);
        City c1 = new City("Bangkok", 11);
        List<City> cities = Arrays.asList(c,c1);
        generateReport(cities);
        logger.info("[+] Generated report successfully");

        // Force download
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition",
                "attachment; filename=" + "FileNameHere.pdf");
        byte[] documentBody = Files.readAllBytes(new File(pdfSource).toPath());
        header.setContentLength(documentBody.length);
        return new HttpEntity<byte[]>(documentBody, header);
    }
}
