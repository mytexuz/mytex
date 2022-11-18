package uz.enterprise.mytex.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.enterprise.mytex.dto.request.BlockedAccountCreateRequest;
import uz.enterprise.mytex.dto.request.BlockedDeviceCreateRequest;
import uz.enterprise.mytex.service.FraudService;

@RestController
@RequestMapping("/api/v1/fraud-management")
@RequiredArgsConstructor
public class FraudController {
    private final FraudService fraudService;

    @PostMapping(value = "/block-device", produces = "application/json")
    public ResponseEntity<?> blockDevice(@Valid @RequestBody BlockedDeviceCreateRequest blockDeviceDto) {
        return fraudService.blockDevice(blockDeviceDto);
    }

    @PutMapping(value = "/unblock-device", produces = "application/json")
    public ResponseEntity<?> unblockDevice(@RequestParam("deviceId") Long deviceId) {
        return fraudService.unblockDevice(deviceId);
    }

    @PostMapping(value = "/block-account", produces = "application/json")
    public ResponseEntity<?> blockAccount(@Valid @RequestBody BlockedAccountCreateRequest blockDeviceDto) {
        return fraudService.blockAccount(blockDeviceDto);
    }

    @PutMapping(value = "/unblock-account", produces = "application/json")
    public ResponseEntity<?> unblockAccount(@RequestParam("accountId") Long accountId) {
        return fraudService.unblockAccount(accountId);
    }
}
