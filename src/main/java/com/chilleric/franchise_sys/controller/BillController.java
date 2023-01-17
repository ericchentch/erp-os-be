package com.chilleric.franchise_sys.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chilleric.franchise_sys.constant.LanguageMessageKey;
import com.chilleric.franchise_sys.dto.bill.BillRequest;
import com.chilleric.franchise_sys.dto.bill.BillResponse;
import com.chilleric.franchise_sys.dto.common.CommonResponse;
import com.chilleric.franchise_sys.service.bill.BillService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(name = "bill-controller")
public class BillController extends AbstractController<BillService> {
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping(value = "init-bill")
  public ResponseEntity<CommonResponse<String>> initBill(@RequestBody BillRequest billRequest,
      HttpServletRequest request) {
    validateToken(request);

    service.createBill(billRequest);
    return new ResponseEntity<>(new CommonResponse<>(true, null, LanguageMessageKey.SUCCESS,
        HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()), null, HttpStatus.OK.value());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping(value = "get-bill-by-id")
  public ResponseEntity<CommonResponse<BillResponse>> getBillById(@RequestParam String billId,
      HttpServletRequest request) {
    validateToken(request);

    return response(service.getBillResponseById(billId), LanguageMessageKey.SUCCESS,
        new ArrayList<>(), new ArrayList<>());
  }

  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(value = "update-bill")
  public ResponseEntity<CommonResponse<String>> updateBill(@RequestParam String billId,
      @RequestBody BillRequest billRequest, HttpServletRequest request) {
    validateToken(request);

    service.updateBill(billRequest, billId);
    return new ResponseEntity<CommonResponse<String>>(new CommonResponse<>(true, null,
        LanguageMessageKey.SUCCESS, HttpStatus.OK.value(), new ArrayList<>(), new ArrayList<>()),
        null, HttpStatus.OK.value());
  }
}
