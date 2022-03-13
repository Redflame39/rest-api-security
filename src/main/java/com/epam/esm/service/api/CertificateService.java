package com.epam.esm.service.api;

import com.epam.esm.model.dto.CertificateDto;
import com.epam.esm.model.dto.CertificateQueryDto;
import com.epam.esm.model.dto.UpdatingCertificateDto;

import java.util.List;

/**
 * The interface Certificate service. Describes CRUD operations for Certificate entity.
 */
public interface CertificateService {

    /**
     * Create certificate with given parameters from {@code CreatingCertificateDto}.
     *
     * @param certificate {@code CreatingCertificateDto} to add.
     * @return the certificate dto with its dates and id.
     * @throws com.epam.esm.exception.EntityNotCreatedException when certificate cannot be created.
     */
    CertificateDto create(UpdatingCertificateDto certificate);


    /**
     * Find all certificates by specified query criteria.
     *
     * @param certificatesQueryDto query criteria
     * @param pageNum              number of page to return
     * @param pageSize             count of certificates on the page
     * @return list of {@code CertificateDto}
     */
    List<CertificateDto> findAll(CertificateQueryDto certificatesQueryDto, Integer pageNum, Integer pageSize);

    /**
     * Finds certificate by given id.
     *
     * @param id the id of certificate to find.
     * @return the {@code CertificateDto} if query executes successfully.
     * @throws com.epam.esm.exception.EntityNotFoundException when certificate with given id cannot be found.
     */
    CertificateDto findById(Long id);

    /**
     * Updates certificate with given id. Fields to update are described in {@code UpdatingCertificateDto}.
     * If field should not be updated, it sets to null in {@code UpdatingCertificateDto}.
     *
     * @param updateId    the id of certificate to update.
     * @param replacement dto which contains replacement values.
     * @return updated certificate.
     * @throws com.epam.esm.exception.EntityNotUpdatedException when entity cannot be updated.
     */
    CertificateDto update(Long updateId, UpdatingCertificateDto replacement);

    /**
     * Update price of specified gift certificate
     *
     * @param updateId the id of certificate to be updated
     * @param newPrice the new price of certificate
     * @return updated certificate dto
     */
    CertificateDto updatePrice(Long updateId, Double newPrice);

    /**
     * Deletes certificate by its id.
     *
     * @param deleteId the id of certificate to delete.
     * @return deleted certificate.
     * @throws com.epam.esm.exception.EntityNotUpdatedException when entity cannot be deleted.
     */
    CertificateDto delete(Long deleteId);

    /**
     * Count certificates in database.
     *
     * @return certificates count
     */
    Long countCertificates();

}
