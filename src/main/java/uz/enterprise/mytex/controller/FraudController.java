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
import uz.enterprise.mytex.dto.BlockedDeviceCreateDto;
import uz.enterprise.mytex.service.FraudService;

@RestController
@RequestMapping("/api/v1/fraud-management")
@RequiredArgsConstructor
public class FraudController {
    private final FraudService fraudService;

    @PostMapping(value = "/block-device", produces = "application/json")
    public ResponseEntity<?> blockDevice(@Valid @RequestBody BlockedDeviceCreateDto blockDeviceDto) {
        return fraudService.blockDevice(blockDeviceDto);
    }

    @PutMapping(value = "/unblock-device", produces = "application/json")
    public ResponseEntity<?> unblockDevice(@RequestParam("id") Long deviceId) {
        return fraudService.unblockDevice(deviceId);
    }
}
