package com.test.demo.mapper;

import com.test.demo.dao.entity.Response;
import com.test.demo.dto.ResponseDTO;
import org.mapstruct.Mapper;

@Mapper
public interface ResponseMapper {

    Response convertDTOToClass(ResponseDTO responseDTO);

    ResponseDTO convertClassToDTO(Response response);
}
