package com.raja.lib.invt.report.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {

    public ByteArrayInputStream exportReport() {
        try {
            // Load the jrxml file
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/Accession/sample_report.jrxml"));

            // Add the data
            List<Map<String, Object>> data = new ArrayList<>();
            Map<String, Object> item = new HashMap<>();
            item.put("Name", "John Doe");
            item.put("Age", 30);
            data.add(item);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

            // Parameters
            Map<String, Object> parameters = new HashMap<>();

            // Try the first path
            InputStream logoStream = this.getClass().getResourceAsStream("/Images/RajaramLogo.png");
            if (logoStream == null) {
                // If the first path is not found, try the second path
                logoStream = this.getClass().getResourceAsStream("./Images/RajaramLogo.png");
                if (logoStream == null) {
                    throw new RuntimeException("Logo image not found in both paths.");
                }
            }

            parameters.put("LOGO_PATH", logoStream);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

            return new ByteArrayInputStream(baos.toByteArray());
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }
}
