package com.imooc.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICategoryService;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import javafx.scene.control.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.imooc.mall.enums.ProductStatusEnum.*;
import static com.imooc.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 * Created by 廖师兄
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private ProductMapper productMapper;

	@Override
	public ResponseVo<List<ProductVo>> list(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		Set<Integer> categoryIdSet = new HashSet<>();
//		if (categoryId != null) {
//			categoryService.findSubCategoryId(categoryId, categoryIdSet);
//			categoryIdSet.add(categoryId);
//		}

		QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
		if (!StringUtils.isEmpty(param.get("categoryId")))
			queryWrapper.eq("category_id", param.get("categoryId"));

		if (!StringUtils.isEmpty(param.get("name")))
			queryWrapper.like("name", param.get("name"));

		if (!StringUtils.isEmpty(param.get("status")))
			queryWrapper.eq("status", param.get("status"));

//		queryWrapper.eq("is_del", 0);
		queryWrapper.orderByDesc("create_time");

		Page<Product> page = new Page<Product>(pageNum, pageSize);
		Page<Product> productList = productMapper.selectPage(page, queryWrapper);
		List<Product> records = page.getRecords();
		List<ProductVo> productVoList = records.stream()
				.map(e -> {
					ProductVo productVo = new ProductVo();
					BeanUtils.copyProperties(e, productVo);
					return productVo;
				})
				.collect(Collectors.toList());

		return ResponseVo.success(productVoList);
	}

	@Override
	public ResponseVo<ProductDetailVo> detail(Integer productId) {
		Product product = productMapper.selectById(productId);

		//只对确定性条件判断
		if (product == null || product.getStatus().equals(OFF_SALE.getCode())
				|| product.getIsDel().equals(DELETE.getCode())) {
			return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
		}

		ProductDetailVo productDetailVo = new ProductDetailVo();
		BeanUtils.copyProperties(product, productDetailVo);
//		//敏感数据处理
//		productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
		return ResponseVo.success(productDetailVo);
	}

	@Override
	public boolean saveBatch(Collection<Product> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean saveOrUpdateBatch(Collection<Product> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean updateBatchById(Collection<Product> entityList, int batchSize) {
		return false;
	}

	@Override
	public boolean saveOrUpdate(Product entity) {
		return false;
	}

	@Override
	public Product getOne(Wrapper<Product> queryWrapper, boolean throwEx) {
		return null;
	}

	@Override
	public Map<String, Object> getMap(Wrapper<Product> queryWrapper) {
		return null;
	}

	@Override
	public <V> V getObj(Wrapper<Product> queryWrapper, Function<? super Object, V> mapper) {
		return null;
	}

	@Override
	public ProductMapper getBaseMapper() {
		return productMapper;
	}
}
