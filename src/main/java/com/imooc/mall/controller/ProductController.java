package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.IProductService;
import com.imooc.mall.vo.ProductDetailVo;
import com.imooc.mall.vo.ProductVo;
import com.imooc.mall.vo.ResponseVo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.imooc.mall.enums.ResponseEnum.ERROR;

/**
 * 商品
 *
 * by Lnstark
 * 2020年6月14日20:05:17
 */
@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private IProductService productService;

	@PostMapping("add")
	public ResponseVo<String> add(@RequestBody Product product) {
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		boolean result = productService.save(product);
		if (!result)
			ResponseVo.error(ERROR, "新增失败");
		return ResponseVo.successByMsg("新增成功");
	}

	@PutMapping("update")
	public ResponseVo<String> update(@RequestBody Product product) {
		product.setUpdateTime(new Date());
		boolean result = productService.updateById(product);
		if (!result)
			ResponseVo.error(ERROR, "修改失败");
		return ResponseVo.successByMsg("修改成功");
	}

	@GetMapping("list")
	public ResponseVo<List<ProductVo>> list(
			@RequestParam(required = false, value = "categoryId") Integer categoryId,
			@RequestParam(required = false, value = "name") String name,
			@RequestParam(required = false, value = "status") Integer status,
			@RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "20") Integer pageSize) {
		Map<String, Object> param = new HashMap<>();
		param.put("categoryId", categoryId);
		param.put("name", name);
		param.put("status", status);
		return productService.list(param, pageNum, pageSize);
	}

	@GetMapping("{productId}")
	public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId) {
		return productService.detail(productId);
	}

	@DeleteMapping("delete")
	public ResponseVo<String> delete(
			@RequestBody List<Integer> ids) {
		boolean result = productService.removeByIds(ids);
		if (!result)
			ResponseVo.error(ERROR, "删除失败");
		return ResponseVo.successByMsg("删除成功");
	}
}
