package com.enigma.challengespringrestful.controller;

import com.enigma.challengespringrestful.constant.APIURL;
import com.enigma.challengespringrestful.constant.ConstantMessage;
import com.enigma.challengespringrestful.dao.OutletDAO;
import com.enigma.challengespringrestful.dto.request.OutletDTORequest;
import com.enigma.challengespringrestful.dto.response.CommonResponse;
import com.enigma.challengespringrestful.entity.Outlet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIURL.OUTLET)
public class OutletController {
    private final OutletDAO outletDAO;

    @PostMapping
    public ResponseEntity<CommonResponse<Outlet>> createOutlet(@RequestBody OutletDTORequest outletDTORequest) {
        Outlet newOutlet = outletDAO.create(outletDTORequest);

        CommonResponse<Outlet> commonResponse = CommonResponse.<Outlet>builder().statusCode(HttpStatus.CREATED.value()).message(ConstantMessage.CREATED).data(newOutlet).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }

    @GetMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> findOutletsById(@PathVariable String id) {
        Outlet outlet = outletDAO.findById(id);

        CommonResponse<Outlet> commonResponse = CommonResponse.<Outlet>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(outlet).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<Outlet>>> findAllOutlets(@RequestParam(name = "name", required = false) String name) {
        List<Outlet> outletList = outletDAO.findAll(name);

        CommonResponse<List<Outlet>> commonResponse = CommonResponse.<List<Outlet>>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.OK).data(outletList).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<Outlet>> updateOutlet(@RequestBody OutletDTORequest outletDTORequest) {
        Outlet updateOutlet = outletDAO.update(outletDTORequest);

        CommonResponse<Outlet> commonResponse = CommonResponse.<Outlet>builder().statusCode(HttpStatus.OK.value()).message(ConstantMessage.UPDATED).data(updateOutlet).build();

        return ResponseEntity.status(HttpStatus.OK).body(commonResponse);
    }

    @DeleteMapping(APIURL.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteOutlet(@PathVariable String id) {
        outletDAO.deleteById(id);
        CommonResponse<?> commonResponse = CommonResponse.builder().statusCode(HttpStatus.NO_CONTENT.value()).message(ConstantMessage.NO_CONTENT).build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commonResponse);
    }

}
