package com.company.myproject.service;

import com.company.myproject.dao.AssetTypeRepository;
import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.AssetType;
import com.company.myproject.model.Pageable;
import com.company.myproject.util.ConvertAssetTypeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AssetTypeServiceImpl implements AssetTypeService {

    private static final Logger logger = LoggerFactory.getLogger(AssetTypeService.class);

    private AssetTypeRepository assetTypeRepository;

    @Autowired
    public AssetTypeServiceImpl(AssetTypeRepository assetTypeRepository) {
        this.assetTypeRepository = assetTypeRepository;
    }

    @Override
    public AssetTypeDto findById(UUID assetTypeId) {
        AssetType assetType = assetTypeRepository.findById(assetTypeId);
        if (assetType == null) {
            logger.warn("Asset Type id not found - " + assetTypeId);
            throw new EntityNotFoundException("Asset Type id not found - " + assetTypeId);
        }
        return ConvertAssetTypeDto.convertToAssetTypeDtoPlain(assetType);
    }

    @Override
    public List<AssetTypeDto> findAll(Pageable pageable) {
        List<AssetType> assetTypes = assetTypeRepository.findAllPaging(pageable);
        return ConvertAssetTypeDto.convertToAssetTypeDtoPlainList(assetTypes);
    }

    @Override
    public Long getTotalCount() {
        Long totalCount = assetTypeRepository.findTotalCount();
        if (totalCount == null) {
            logger.warn("Total count could not be found");
            throw new BadRequestException("Total count could not be found");
        }
        return totalCount;
    }

    @Override
    @Transactional
    public AssetTypeDto save(AssetTypeDto assetTypeDto) {
        if (assetTypeDto.getId() != null) {
            logger.warn("Asset Type id should be null");
            throw new BadRequestException("Asset Type id should be null");
        }
        AssetType assetType = ConvertAssetTypeDto.convertToAssetType(assetTypeDto);
        assetTypeRepository.save(assetType);
            return ConvertAssetTypeDto.convertToAssetTypeDto(assetType);
    }

    @Override
    @Transactional
    public AssetTypeDto update(AssetTypeDto assetTypeDto) {
        AssetType assetType = assetTypeRepository.findById(assetTypeDto.getId());
        if (assetType == null) {
            logger.warn("Asset Type id not found - " + assetTypeDto.getId());
            throw new EntityNotFoundException("Asset Type id not found - " + assetTypeDto.getId());
        }
        if (assetTypeDto.getType() != null && !(assetTypeDto.getType().isBlank())) {
            assetType.setType(assetTypeDto.getType());
        }
        assetTypeRepository.save(assetType);
        return ConvertAssetTypeDto.convertToAssetTypeDto(assetType);
    }

    @Override
    @Transactional
    public AssetTypeDto deleteById(UUID assetTypeId) {
        AssetType assetType = assetTypeRepository.findById(assetTypeId);
        if (assetType == null) {
            logger.warn("Asset Type id not found - " + assetTypeId);
            throw new EntityNotFoundException("Asset Type id not found - " + assetTypeId);
        }
        assetTypeRepository.deleteById(assetTypeId);
        return ConvertAssetTypeDto.convertToAssetTypeDtoPlain(assetType);
    }


}
