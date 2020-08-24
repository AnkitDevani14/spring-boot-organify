package com.shopify.service;

import com.shopify.model.Cart;
import com.shopify.model.Invoice;
import com.shopify.model.User;
import com.shopify.repo.CartRepo;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ReportService {

    @Autowired
    private CartRepo repository;

    @Autowired
    private CartRepo cartRepo;
    public String exportReport(String reportFormat, Long uid) throws FileNotFoundException, JRException {
        String path = "E:\\Learning Material\\PROJECT\\shopify\\src\\assets\\invoice\\";
        String openPath = "assets/invoice/";
        List<Invoice> cart= cartRepo.join(uid);
        Random random = new Random();
        int randomWithNextInt = random.nextInt();

        //load file and compile it
        File file = ResourceUtils.getFile("classpath:554Invoice.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(cart);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\invoice.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            String val = random.toString()+uid+".pdf";
            System.out.println(path+val);
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + val);
            return openPath+val;
        }

        return path;
    }
}
