package com.example.Bus.Finder.System.service.Report;

import com.example.Bus.Finder.System.dto.ReportDto;
import com.example.Bus.Finder.System.dto.TimeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReportService {
    ResponseEntity<List<ReportDto>> getAll(TimeDto timeDto);
}
