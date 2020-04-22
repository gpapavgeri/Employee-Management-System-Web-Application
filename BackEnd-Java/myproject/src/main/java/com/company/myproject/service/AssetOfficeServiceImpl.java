package com.company.myproject.service;

import com.company.myproject.dao.AssetOfficeRepository;
import com.company.myproject.exception.BadRequestException;
import com.company.myproject.exception.EntityNotFoundException;
import com.company.myproject.model.AssetOffice;
import com.company.myproject.model.AssetOfficeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssetOfficeServiceImpl implements AssetOfficeService {

    private static final Logger logger = LoggerFactory.getLogger(AssetOfficeService.class);

    private AssetOfficeRepository assetOfficeRepository;

    public AssetOfficeServiceImpl(AssetOfficeRepository assetOfficeRepository){
        this.assetOfficeRepository = assetOfficeRepository;
    }

    @Override
    public AssetOffice findByCompositeId(AssetOffice assetOffice) {
        return assetOfficeRepository.findByCompositeId(assetOffice);
    }

    @Override
    public Set<AssetOffice> findAll() {
        return assetOfficeRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public void save(AssetOffice assetOffice) {
        assetOfficeRepository.save(assetOffice);
    }

    @Override
    public void update(AssetOffice assetOffice) {
        AssetOffice existedRecord = assetOfficeRepository.findByCompositeId(assetOffice);
        if (existedRecord == null) {
            logger.warn("AssetOffice record not found!");
            throw new BadRequestException("AssetOffice record not found!");
        }
        existedRecord.setDateFrom(assetOffice.getDateFrom());
        existedRecord.setDateTo(assetOffice.getDateTo());
        assetOfficeRepository.update(existedRecord);
    }

    @Override
    public void deleteById(AssetOfficeId id) {
        AssetOffice assetOffice = assetOfficeRepository.findById(id);
        if(assetOffice == null) {
            logger.warn("AssetOffice id not found - " + id);
            throw new EntityNotFoundException("AssetOffice id not found - " + id);
        }
        assetOfficeRepository.deleteById(id);
    }
}
