package com.epam.esm.repository.api;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.repository.CriteriaSpecification;

import java.util.List;
import java.util.Optional;

/**
 * The interface Certificate repository describes operations with Certificate entity.
 *
 * @param <K> the identification type of Certificate entity.
 */
public interface CertificateRepository<K> {

    /**
     * Find all list.
     *
     * @param criteriaSpecification the criteria specification
     * @param pageNum               the page num
     * @param pageSize              the page size
     * @return the list
     */
    List<Certificate> findAll(CriteriaSpecification<Certificate> criteriaSpecification, Integer pageNum, Integer pageSize);

    /**
     * Finds certificate by its id.
     *
     * @param id the id of certificate to find.
     * @return Optional certificate object.
     */
    Optional<Certificate> findById(K id);

    /**
     * Create certificate with given values in {@code CreatingCertificateDto}.
     *
     * @param certificate the certificate to be created.
     * @return the id of created certificate.
     */
    Certificate create(Certificate certificate);

    /**
     * Update certificate with given id. Updates only fields which is set to non-null in {@code UpdatingCertificateDto}.
     *
     * @param updateId    the id of certificate to update.
     * @param replacement the object that contains fields to update.
     * @return true if successfully updated, else false.
     */
    Certificate update(K updateId, Certificate replacement);

    /**
     * Update price of certificate by id.
     *
     * @param updateId id of certificate to update
     * @param price    new certificate price
     * @return updated certificate
     */
    Certificate updatePrice(K updateId, Double price);

    /**
     * Deletes certificate with given id.
     *
     * @param deleteId the id of certificate to delete.
     * @return true if successfully deleted, else false.
     */
    Certificate delete(K deleteId);

    /**
     * Count certificates in database.
     *
     * @return certificates count
     */
    Long countCertificates();
}
