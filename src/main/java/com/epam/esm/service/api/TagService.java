package com.epam.esm.service.api;

import com.epam.esm.model.dto.TagDto;

import java.util.List;

/**
 * The interface Tag service. Describes CRD operations for Tag entity.
 */
public interface TagService {

    /**
     * Creates tag with given name value in {@code TagDto}.
     *
     * @param tag the tag to create.
     * @return created tag.
     * @throws com.epam.esm.exception.EntityNotCreatedException when tag cannot be created.
     */
    TagDto create(TagDto tag);


    /**
     * Find all tags. Supports pagination
     *
     * @param pageNum  the number of page
     * @param pageSize the size of page
     * @return list of tags found
     */
    List<TagDto> findAll(Integer pageNum, Integer pageSize);

    /**
     * Find all tags of certificate by its id.
     *
     * @param id the id of certificate which tags should be found.
     * @return the list of found tags.
     */
    List<TagDto> findByCertificateId(Long id);

    /**
     * Finds tag by its id.
     *
     * @param id the id of tag to find.
     * @return found tag.
     * @throws com.epam.esm.exception.EntityNotFoundException when tag cannot be found.
     */
    TagDto findById(Long id);

    /**
     * Delete tag by its id.
     *
     * @param deleteId the id of tag to delete.
     * @return deleted tag.
     * @throws com.epam.esm.exception.EntityNotUpdatedException when tag cannot be deleted.
     */
    TagDto delete(Long deleteId);

    /**
     * Find most used user tag list.
     *
     * @param userId the user id
     * @return the list of most used user tags
     */
    List<TagDto> findMostUsedUserTag(Long userId);

    /**
     * Count tags in database.
     *
     * @return tags count
     */
    Long countTags();

}
