package com.epam.esm.controller;

import com.epam.esm.controller.hateoas.api.CertificateControllerHateoasLinkBuilder;
import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.UpdatingCertificateDto;
import com.epam.esm.service.api.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CertificateController {

    private final CertificateService certificateService;
    private final CertificateControllerHateoasLinkBuilder linkBuilder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CertificateDto> read(@RequestBody(required = false)
                                                        CertificateQueryDto certificatesQueryDto,
                                                @RequestParam(name = "page", required = false, defaultValue = "0")
                                                @Min(value = 0, message = "Page size should be greater than or equal to zero")
                                                        Long pageNum,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                                @Min(value = 1, message = "Page size should be represented as positive number")
                                                @Max(value = 100, message = "Page size should not be greater than 100")
                                                        Long pageSize) {
        Long certificatesCount = certificateService.countCertificates();
        List<CertificateDto> certificateDtos = certificateService.findAll(certificatesQueryDto, pageNum, pageSize);
        return linkBuilder.buildReadAllLinks(certificateDtos, certificatesCount, pageNum, pageSize);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto read(@PathVariable Long id) {
        CertificateDto dto = certificateService.findById(id);
        return linkBuilder.buildReadSingleLinks(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody UpdatingCertificateDto certificate) {
        CertificateDto dto = certificateService.create(certificate);
        return linkBuilder.buildCreateLinks(dto);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@PathVariable Long id, @RequestBody UpdatingCertificateDto certificateDto) {
        CertificateDto dto = certificateService.update(id, certificateDto);
        return linkBuilder.buildUpdateLinks(dto);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updatePrice(@PathVariable Long id, @RequestBody Double price) {
        CertificateDto dto = certificateService.updatePrice(id, price);
        return linkBuilder.buildUpdatePriceLinks(dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto delete(@PathVariable Long id) {
        CertificateDto dto = certificateService.delete(id);
        return linkBuilder.buildDeleteLinks(dto);
    }

}
