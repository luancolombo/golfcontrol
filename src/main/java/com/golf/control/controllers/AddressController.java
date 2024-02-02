package com.golf.control.controllers;

import com.golf.control.dtos.AddressDTO;
import com.golf.control.services.IAddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final IAddressService service;
    public AddressController(IAddressService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<List<AddressDTO>> saveAddress(@Validated @RequestBody List<AddressDTO> addressDTOList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveClient(addressDTOList));
    }
    @PostMapping("/findallbyid")
    public ResponseEntity<List<AddressDTO>> findByIdList(@RequestBody @NotNull List<Long> idList) {
        List<AddressDTO> result = service.findByIdList(idList);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update")
    public ResponseEntity<List<AddressDTO>> update(@RequestBody List<AddressDTO> addressDTOList) {
        List<AddressDTO> updatedAddress = service.update(addressDTOList);
        return ResponseEntity.ok(updatedAddress);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteAddress(@RequestBody List<Long> ids) {
        service.deleteAddress(ids);
        return new ResponseEntity<>("Addresses deleted successfully", HttpStatus.OK);
    }
}
