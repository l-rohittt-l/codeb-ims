package com.codeb.ims.service;

import com.codeb.ims.dto.BrandDto;
import com.codeb.ims.model.Brand;
import com.codeb.ims.model.Chain;
import com.codeb.ims.repository.BrandRepository;
import com.codeb.ims.repository.ChainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ChainRepository chainRepository;

    @Override
    public BrandDto createBrand(BrandDto brandDto) {
        Chain chain = chainRepository.findById(brandDto.getChainId())
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());
        brand.setChain(chain);
        brand.setActive(true);

        Brand savedBrand = brandRepository.save(brand);
        return mapToDto(savedBrand);
    }

    @Override
    public BrandDto updateBrand(Long brandId, BrandDto brandDto) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        Chain chain = chainRepository.findById(brandDto.getChainId())
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        brand.setBrandName(brandDto.getBrandName());
        brand.setChain(chain);

        Brand updated = brandRepository.save(brand);
        return mapToDto(updated);
    }

    @Override
    public void deleteBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        brand.setActive(false);
        brandRepository.save(brand);
    }

    @Override
    public String reactivateBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        if (brand.isActive()) {
            return "Brand is already active";
        }

        brand.setActive(true);
        brandRepository.save(brand);
        return "success";
    }

    @Override
    public List<BrandDto> getAllActiveBrands() {
        return brandRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> getBrandsByChainId(Long chainId) {
        Chain chain = chainRepository.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        return brandRepository.findByChainAndIsActiveTrue(chain)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> getBrandsByGroupName(String groupName) {
        return brandRepository.findByChain_Group_GroupNameAndIsActiveTrue(groupName)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDto> getAllBrandsSortedByActive() {
        return brandRepository.findAllByOrderByIsActiveDesc()
                .stream()
                .map(this::mapToDtoWithStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBrandNamesByChainId(Long chainId) {
        Chain chain = chainRepository.findById(chainId)
                .orElseThrow(() -> new RuntimeException("Chain not found"));

        return brandRepository.findByChainAndIsActiveTrue(chain)
                .stream()
                .map(Brand::getBrandName)
                .collect(Collectors.toList());
    }

    private BrandDto mapToDto(Brand brand) {
        BrandDto dto = new BrandDto();
        dto.setBrandId(brand.getBrandId());
        dto.setBrandName(brand.getBrandName());
        dto.setChainId(brand.getChain().getChainId());
        dto.setActive(brand.isActive());
        return dto;
    }

    private BrandDto mapToDtoWithStatus(Brand brand) {
        BrandDto dto = mapToDto(brand);
        dto.setActive(brand.isActive());
        return dto;
    }
}
