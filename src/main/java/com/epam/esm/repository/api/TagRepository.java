package com.epam.esm.repository.api;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.repository.NativeSpecification;

import java.util.List;
import java.util.Optional;

/**
 * The interface Tag repository which describes operations with Tag entity.
 *
 * @param <K> the identifier type of Tag entity.
 */
public interface TagRepository<K> {

    /**
     * Finds all tags.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the list of all tags.
     */
    List<Tag> findAll(Integer pageNum, Integer pageSize);

    /**
     * Finds tag by its id.
     *
     * @param id the id of tag to find.
     * @return Optional tag object.
     */
    Optional<Tag> findById(K id);

    /**
     * Create certificate with given values in {@code TagDto}.
     *
     * @param t the tag to be created
     * @return the id of created tag.
     */
    Tag create(TagDto t);

    /**
     * Delete tag with given id.
     *
     * @param deleteId the id of tag to delete.
     * @return true if successfully deleted, else false.
     */
    Tag delete(K deleteId);

    /**
     * Finds certificate by its name.
     *
     * @param name the name of certificate to find.
     * @return Optional value of Tag object.
     */
    Optional<Tag> findByName(String name);

    /**
     * Finds all tags of certificate by certificate id.
     *
     * @param id the id of certificate.
     * @return the list of certificate tags.
     */
    List<Tag> findByCertificateId(K id);

    /**
     * Find by native specification list.
     *
     * @param specification the specification
     * @return the list
     */
    List<Tag> findByNativeSpecification(NativeSpecification specification);

    /**
     * Count tags in database.
     *
     * @return tags count
     */
    Long countTags();

}
