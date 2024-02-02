package com.golf.control.services;

import com.golf.control.dtos.ClientDTO;

import java.util.List;

public interface IClientService {
    List<ClientDTO> findByIdList(List<Long> ids);
    List<ClientDTO> update(List<ClientDTO> clientDTOs);
    List<ClientDTO> saveClient(List<ClientDTO> clientDTOsList);
    void deleteClient(List<Long> ids);
}
