package com.epam.esm.controller;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<CertificateDto> read(@RequestBody(required = false)
                                                        CertificateQueryDto certificatesQueryDto,
                                                @RequestParam(name = "page", required = false, defaultValue = "1")
                                                @Min(value = 1, message = "Page size should be represented as positive number")
                                                        Integer pageNum,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "50")
                                                @Min(value = 1, message = "Page size should be represented as positive number")
                                                @Max(value = 100, message = "Page size should not be greater than 100")
                                                        Integer pageSize) {
        List<CertificateDto> certificateDtos = certificateService.findAll(certificatesQueryDto, pageNum, pageSize);
        Long certificatesCount = certificateService.countCertificates();
        for (CertificateDto dto : certificateDtos) {
            Link certificateLink = linkTo(methodOn(CertificateController.class).read(dto.getId())).withSelfRel();
            dto.add(certificateLink);
        }
        List<Link> collectionLinks = new ArrayList<>();
        if (pageNum != 1) {
            Link prevPageLink = linkTo(methodOn(CertificateController.class)
                    .read(certificatesQueryDto, pageNum - 1, pageSize))
                    .withRel("Previous page");
            collectionLinks.add(prevPageLink);
        }
        Link currentPageLink = linkTo(methodOn(CertificateController.class)
                .read(certificatesQueryDto, pageNum, pageSize))
                .withRel("Current page");
        boolean isLastPage = (long) pageNum * pageSize >= certificatesCount;
        if (!isLastPage) {
            Link nextPageLink = linkTo(methodOn(CertificateController.class)
                    .read(certificatesQueryDto, pageNum + 1, pageSize))
                    .withRel("Next page");
            collectionLinks.add(nextPageLink);
        }
        collectionLinks.add(currentPageLink);
        return CollectionModel.of(certificateDtos, collectionLinks);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto read(@PathVariable Long id) {
        CertificateDto dto = certificateService.findById(id);
        Link self = linkTo(methodOn(CertificateController.class).read(id)).withSelfRel();
        dto.add(self);
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CertificateDto create(@RequestBody UpdatingCertificateDto certificate) {
        CertificateDto dto = certificateService.create(certificate);
        Link self = linkTo(methodOn(CertificateController.class).create(certificate)).withSelfRel();
        Link toCreatedLink = linkTo(methodOn(CertificateController.class)
                .read(dto.getId()))
                .withRel("Read created");
        dto.add(self, toCreatedLink);
        return dto;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto update(@PathVariable Long id, @RequestBody UpdatingCertificateDto certificateDto) {
        CertificateDto dto = certificateService.update(id, certificateDto);
        Link self = linkTo(methodOn(CertificateController.class)
                .update(id, certificateDto))
                .withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(CertificateController.class)
                .read(id))
                .withRel("Read updated");
        dto.add(self, toUpdatedLink);
        return dto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto updatePrice(@PathVariable Long id, @RequestBody Double price) {
        CertificateDto dto = certificateService.updatePrice(id, price);
        Link self = linkTo(methodOn(CertificateController.class).updatePrice(id, price)).withSelfRel();
        Link toUpdatedLink = linkTo(methodOn(CertificateController.class)
                .read(id))
                .withRel("Read updated");
        dto.add(self, toUpdatedLink);
        return dto;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CertificateDto delete(@PathVariable Long id) {
        CertificateDto dto = certificateService.delete(id);
        Link self = linkTo(methodOn(CertificateController.class)
                .delete(id))
                .withSelfRel();
        dto.add(self);
        return dto;
    }

}
