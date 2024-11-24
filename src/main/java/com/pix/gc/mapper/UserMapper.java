package com.pix.gc.mapper;

import com.pix.gc.dto.BankAccountDto;
import com.pix.gc.dto.UserDto;
import com.pix.gc.entities.BankAccount;
import com.pix.gc.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    // Mapear User para UserDto
    @Mapping(target = "account", source = "account", qualifiedByName = "mapAccount")
    UserDto userToUserDto(User user);

    // Mapear UserDto para User
    @Mapping(target = "account", ignore = true)
    User userDtoToUser(UserDto userDto);

    @Named("mapAccount")
    default BankAccountDto bankAccountToBankAccountDto(BankAccount account) {
        if (account == null) {
            return null;
        }
        return new BankAccountDto(account.getId(), account.getAgency(), account.getAccountNumber(), account.getBalance());
    }
}


