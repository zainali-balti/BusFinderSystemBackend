package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.ReportDto;
import com.example.Bus.Finder.System.dto.TimeDto;
import com.example.Bus.Finder.System.service.Report.ReportService;
import com.itextpdf.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/get")
    public ResponseEntity<byte[]> getMonthlyReport(@RequestBody TimeDto timeDto) {
        ResponseEntity<List<ReportDto>> responseEntity = reportService.getAll(timeDto);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<ReportDto> reportDTOS = responseEntity.getBody();
        byte[] pdfReport = generatePdfReport(reportDTOS);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfReport);
    }

    private byte[] generatePdfReport(List<ReportDto> reportDTOS) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            document.open();
            PdfPTable table = new PdfPTable(8);
            table.setWidthPercentage(90);
            addTableHeader(table);
            for (ReportDto reportDTO : reportDTOS) {
                addRows(table, reportDTO);
            }
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Customer ID", "Customer Name", "Vehicle ID",
                        "Vehicle Name", "Booking Date", "Price", "Distance")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, ReportDto reportDTO) {
        table.addCell(String.valueOf(reportDTO.getId()));
        table.addCell(String.valueOf(reportDTO.getCustomerId()));
        table.addCell(reportDTO.getCustomerName());
        table.addCell(String.valueOf(reportDTO.getVehicleId()));
        table.addCell(reportDTO.getVehicleName());
        table.addCell(reportDTO.getBookingDate().toString());
        table.addCell(String.valueOf(reportDTO.getPrice()));
        table.addCell(String.valueOf(reportDTO.getDistance()));
    }
}
