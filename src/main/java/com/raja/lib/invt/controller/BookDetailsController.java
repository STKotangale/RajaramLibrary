package com.raja.lib.invt.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.invt.objects.BookDetail;
import com.raja.lib.invt.request.UpdateBookDetailsRequest;
import com.raja.lib.invt.resposne.BookDetailResponse;
import com.raja.lib.invt.service.BookDetailsService;

@RestController
@RequestMapping("/api/bookdetails")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookDetailsController {

	@Autowired
	BookDetailsService bookDetailsService;
    
    
    @GetMapping("")
    List<BookDetail> findBooksByNullIsbn()
    {
    	return bookDetailsService.findBooksByNullIsbn();
    }
    
    @GetMapping("/copyno")
    public List<BookDetailResponse> getBookDetails() {
        return bookDetailsService.getBookDetails();
    }

    
    @PostMapping("/update/book-details/{id}")
    public Map<String, Object> updateBookDetails(@PathVariable int id, @RequestBody UpdateBookDetailsRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updateBookDetails(id, request);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    
    @PostMapping("/update-status-issue")
    public Map<String, Object> updateBookIssueStatus(@RequestBody List<Integer> bookDetailIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updateBookIssueStatus(bookDetailIds);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/update-status-issue-return")
    public Map<String, Object> updateIssuereturnStatus(@RequestBody List<Integer> bookDetailIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updateIssuereturnStatus(bookDetailIds);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    
    @PostMapping("/update-status-purchase-return")
    public Map<String, Object> updatePurchasereturnStatus(@RequestBody List<Integer> bookDetailIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updatePurchasereturnStatus(bookDetailIds);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/update-status-book-lost")
    public Map<String, Object> updateBookLostreturnStatus(@RequestBody List<Integer> bookDetailIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updateBookLostreturnStatus(bookDetailIds);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    @PostMapping("/update-status-book-scrap")
    public Map<String, Object> updateBookScraptreturnStatus(@RequestBody List<Integer> bookDetailIds) {
        Map<String, Object> response = new HashMap<>();
        try {
            String result = bookDetailsService.updateBookScraptreturnStatus(bookDetailIds);
            response.put("success", true);
            response.put("message", result);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
}
