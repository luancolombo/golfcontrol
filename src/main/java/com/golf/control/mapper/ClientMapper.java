package com.golf.control.mapper;

import com.golf.control.dtos.ClientDTO;
import com.golf.control.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO clientToClientDTO(Client client);
    Client clientDTOToClient(ClientDTO clientDTO);
    List<ClientDTO> clientToClientDTOList(List<Client> clients);
    List<Client> clientDTOToClientList(List<ClientDTO> clientDTOList);
    void updateClientFromDto(ClientDTO clientDTO, @MappingTarget Client client);
}
