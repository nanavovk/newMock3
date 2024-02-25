package com.example.newMock2.Controller;

import com.example.newMock2.Model.RequestDTO;
import com.example.newMock2.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController

public class MainController {

    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String clientId = requestDTO.getClientId();
            char firstDigit = clientId.charAt(0);
            String currency = "";
            BigDecimal maxLimit;
            String RqUID = requestDTO.getRqUID();
            BigDecimal balance;
            if (firstDigit == '8') {
                maxLimit = new BigDecimal("2000.00");
                currency = "US";
                double value = Math.random() * 2000;
                balance = new BigDecimal(Double.toString(value));

            } else if (firstDigit == '9') {
                maxLimit = new BigDecimal("1000.00");
                currency = "EU";
                double value = Math.random() * 1000;
                balance = new BigDecimal(Double.toString(value));
            } else {
                maxLimit = new BigDecimal("10000.00");
                currency = "RUB";
                double value = Math.random() * 10000;
                balance = new BigDecimal(Double.toString(value));
            }

            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance.setScale(2, RoundingMode.HALF_UP));
            responseDTO.setMaxLimit(maxLimit);

            log.error("************ RequestDTO ************" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("************ ResponseDTO ************" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));

            return responseDTO;

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
