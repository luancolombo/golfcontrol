package com.golf.control.services;

import com.golf.control.dtos.AddressDTO;

import java.util.List;

public interface IAddressService {
    List<AddressDTO> findByIdList(List<Long> ids);
    List<AddressDTO> update(List<AddressDTO> addressDTOS);
    List<AddressDTO> saveClient(List<AddressDTO> addressDTOList);
    void deleteAddress(List<Long> ids);
}
