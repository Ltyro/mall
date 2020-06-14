package com.imooc.mall.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;

import java.util.List;
import java.util.Map;

/**
 * Created by 廖师兄
 */
public interface IProductService extends IService<Product> {

	ResponseVo<List<ProductVo>> list(Map<String, Object> param, Integer pageNum, Integer pageSize);

	ResponseVo<ProductDetailVo> detail(Integer productId);
}
