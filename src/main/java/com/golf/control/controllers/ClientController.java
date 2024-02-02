package com.golf.control.controllers;

import com.golf.control.dtos.ClientDTO;
import com.golf.control.services.IClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final IClientService service;
    public ClientController(IClientService service) {
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<List<ClientDTO>> saveClient(@Validated @RequestBody List<ClientDTO> clientDTOList) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveClient(clientDTOList));
    }
    @PostMapping("/findallbyid")
    public ResponseEntity<List<ClientDTO>> findByIdList(@RequestBody @NotNull List<Long> idList) {
        List<ClientDTO> result = service.findByIdList(idList);
        return ResponseEntity.ok(result);
    }
    @PutMapping("/update")
    public ResponseEntity<List<ClientDTO>> update(@RequestBody List<ClientDTO> clientDTOList) {
        List<ClientDTO> updatedClients = service.update(clientDTOList);
        return ResponseEntity.ok(updatedClients);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteClient(@RequestBody List<Long> ids) {
        service.deleteClient(ids);
        return new ResponseEntity<>("Clients deleted successfully", HttpStatus.OK);
    }
}
