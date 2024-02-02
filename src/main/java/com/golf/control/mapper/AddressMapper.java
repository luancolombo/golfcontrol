package com.golf.control.mapper;


import com.golf.control.dtos.AddressDTO;
import com.golf.control.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDTO addressToAddressDTO(Address address);
    Address addressDTOToAddress(AddressDTO addressDTO);
    List<AddressDTO> addressToAddressDTOList(List<Address> address);
    List<Address> addressDTOToAddressList(List<AddressDTO> addressDTOList);
    void updateAddressFromDto(AddressDTO addressDTO, @MappingTarget Address address);
}
