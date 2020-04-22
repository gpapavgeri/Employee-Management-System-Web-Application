package com.company.myproject.service;

import com.company.myproject.dao.AssetRepository;
import com.company.myproject.dao.AssetTypeRepository;
import com.company.myproject.dao.CompanyRepository;
import com.company.myproject.dao.OfficeRepository;
import com.company.myproject.dto.AssetDto;
import com.company.myproject.dto.AssetPersistDto;
import com.company.myproject.dto.AssetTypeDto;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.model.*;
import com.company.myproject.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AssetServiceImpl implements AssetService {

    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);

    private AssetRepository assetRepository;
    private OfficeRepository officeRepository;
    private AssetTypeRepository assetTypeService;
    private CompanyRepository companyService;
    private AssetOfficeService assetOfficeService;

    @Autowired
    public AssetServiceImpl(AssetRepository assetRepository,
                            OfficeRepository officeRepository,
                            AssetTypeRepository assetTypeService,
                            CompanyRepository companyService,
                            AssetOfficeService assetOfficeService) {
        this.assetRepository = assetRepository;
        this.officeRepository = officeRepository;
        this.assetTypeService = assetTypeService;
        this.companyService = companyService;
        this.assetOfficeService = assetOfficeService;
    }

    @Override
    public AssetDto findById(UUID assetId) {
        Asset asset = assetRepository.findByIdWithList(assetId);
        if (asset == null) {
            logger.warn("Asset id not found - " + assetId);
            throw new EntityNotFoundException("Asset id not found - " + assetId);
        }
        return ConvertAssetDto.convertToAssetDto(asset);
    }

    @Override
    public List<AssetDto> findAll(Pageable pageable) {

        List<Asset> assets;
        if (pageable == null) {
            assets = assetRepository.findAll();
        } else {
            assets = assetRepository.findAllPaging(pageable);
        }
        return ConvertAssetDto.convertToAssetDtoPlainList(assets);
    }

    @Override
    public List<AssetDto> findAssets(UUID companyId, UUID officeId, UUID assetTypeId, Pageable pageable) {
        List<Asset> assets = assetRepository.findAssets(companyId, officeId, assetTypeId, pageable);
        return ConvertAssetDto.convertToAssetDtoPlainList(assets);
    }

    @Override
    public Long getTotalCount(UUID companyId, UUID officeId, UUID assetTypeId) {
        if (companyId == null && officeId == null && assetTypeId == null) {
            return assetRepository.findTotalCount();
        }
        return assetRepository.getTotalCount(companyId, officeId, assetTypeId);
    }

    @Override
    @Transactional
    public AssetDto save(AssetDto assetDto) {
        if (assetDto.getId() != null) {
            logger.warn("Asset id should be null");
            throw new BadRequestException("Asset id should be null");
        }
        Asset asset = ConvertAssetDto.convertToAssetPlain(assetDto);
        // Set AssetType
        findOrCreateAssetType(assetDto, asset);
        assetRepository.save(asset);

        Set<AssetOffice> officesToSave = new HashSet<>();
        if (assetDto.getOffices() != null) {
            Set<AssetDto.OfficeDto> officeDtos = assetDto.getOffices();
            for (AssetDto.OfficeDto officeDto : officeDtos) {
                AssetOffice assetOffice = ConvertAssetOfficeDto.convertToAssetOffice(officeDto);
                assetOffice.setAssetId(asset.getId());
                officesToSave.add(assetOffice);
            }
        }

        checkDatesForOffice(officesToSave);
        officesToSave.stream().forEach(x -> assetOfficeService.save(x));

        return ConvertAssetDto.convertToAssetDto(asset);
    }


    @Override
    @Transactional
    public AssetDto update(AssetDto assetDto) {
        Asset asset = assetRepository.findByIdWithList(assetDto.getId());

        if (asset == null) {
            logger.warn("Asset id not found - " + assetDto.getId());
            throw new EntityNotFoundException("Asset id not found - " + assetDto.getId());
        }
        if (assetDto.getSerialNumber() != null && !(assetDto.getSerialNumber().isBlank())) {
            asset.setSerialNumber(assetDto.getSerialNumber());
        }
        if (assetDto.getBrand() != null && !(assetDto.getBrand().isBlank())) {
            asset.setBrand(assetDto.getBrand());
        }
        if (assetDto.getOffices() != null) {
            addOrUpdateOffice(assetDto, asset);
            removeOffice(assetDto, asset);
        }
        // Set AssetType
        findOrCreateAssetType(assetDto, asset);

        checkDatesForOffice(asset.getOffices());
        assetRepository.update(asset);
        return ConvertAssetDto.convertToAssetDto(asset);
    }

    @Override
    @Transactional
    public void addOrUpdateOffice(AssetDto assetDto, Asset asset) {
        Set<AssetDto.OfficeDto> officeDtos = assetDto.getOffices();
        Set<AssetOffice> offices = asset.getOffices();
        Set<AssetOffice> toBeAddedOrUpdated = new HashSet<>();

        for (AssetDto.OfficeDto officeDto : officeDtos) {
            boolean isNew = !offices.stream().anyMatch(x -> new AssetOfficeId(x.getAssetId(), officeDto.getOfficeId())
                    .equals(x.getId()));
            AssetOffice assetOffice = null;
            if (isNew) {
                assetOffice = new AssetOffice();
                assetOffice.setAssetId(asset.getId());
                assetOffice.setOfficeId(officeDto.getOfficeId());
            } else {
                assetOffice = offices.stream().filter(x -> x.getId()
                        .equals(new AssetOfficeId(x.getAssetId(), officeDto.getOfficeId()))).findFirst().get();
            }
            assetOffice.setDateFrom(officeDto.getDateFrom());
            assetOffice.setDateTo(officeDto.getDateTo());
            toBeAddedOrUpdated.add(assetOffice);
        }

        if (toBeAddedOrUpdated != null) {
            offices.addAll(toBeAddedOrUpdated);
            toBeAddedOrUpdated.stream().forEach(x -> {
                if (assetOfficeService.findByCompositeId(x) == null) {
                    assetOfficeService.save(x);
                } else {
                    assetOfficeService.update(x);
                }
            });
        }
    }

    @Override
    @Transactional
    public void removeOffice(AssetDto assetDto, Asset asset) {
        Set<AssetDto.OfficeDto> officeDtos = assetDto.getOffices();
        Set<AssetOffice> offices = asset.getOffices();
        Set<AssetOffice> toBeRemoved = new HashSet<>();

        for (AssetOffice assetOffice : offices) {
//            AssetDto.OfficeDto officeDto = ConvertAssetOfficeDto.convertToOfficeDto(assetOffice);
//            if (!officeDtos.contains(officeDto)) {
            if (!officeDtos.stream().anyMatch(x -> x.getOfficeId().equals(assetOffice.getOfficeId()))) {
                toBeRemoved.add(assetOffice);
            }
        }

        if (toBeRemoved != null) {
            offices.removeAll(toBeRemoved);
            toBeRemoved.stream().forEach(x -> assetOfficeService.deleteById(x.getId()));
        }
    }

    @Override
    @Transactional
    public void findOrCreateAssetType(AssetDto assetDto, Asset asset) {
        UUID assetTypeId = assetDto.getAssetType().getId();
        if (assetTypeId != null) {
            AssetType assetType = assetTypeService.findById(assetTypeId);
            asset.setAssetType(assetType);
        } else {
            AssetType newAssetType = ConvertAssetTypeDto.convertToAssetType(assetDto.getAssetType());
            assetTypeService.save(newAssetType);
            asset.setAssetType(newAssetType);
        }
    }


    @Override
    @Transactional
    public AssetDto deleteById(UUID assetId) {
        Asset asset = assetRepository.findById(assetId);
        if (asset == null) {
            logger.warn("Asset id not found - " + assetId);
            throw new EntityNotFoundException("Asset id not found - " + assetId);
        }
        Set<AssetOffice> offices = asset.getOffices();
        offices.stream().forEach(office -> assetOfficeService.deleteById(office.getId()));
        assetRepository.deleteById(assetId);
        return ConvertAssetDto.convertToAssetDtoPlain(asset);
    }

    @Override
    public void checkDatesForOffice(Set<AssetOffice> offices) {
        offices.stream().forEach(x -> {
            Office office = officeRepository.findById(x.getOfficeId());
            if (x.getDateTo() != null && x.getDateFrom().isAfter(x.getDateTo())) {
                logger.warn("'DateTo' should be later than 'DateFrom' for Office: " + office.getCode());
                throw new BadRequestException("'DateTo' should be later than 'DateFrom' for Office: " + office.getCode());
            }
        });
    }

    @Override
    public void checkDatesForAsset(Set<AssetOffice> offices) {
        offices.stream().forEach(x -> {
            Asset asset = assetRepository.findById(x.getAssetId());
            if (x.getDateTo() != null && x.getDateFrom().isAfter(x.getDateTo())) {
                logger.warn("'DateTo' should be later than 'DateFrom' for Asset: " + asset.getSerialNumber());
                throw new BadRequestException("'DateTo' should be later than 'DateFrom' for Asset: " + asset.getSerialNumber());
            }
        });
    }

}
