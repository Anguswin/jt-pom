package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	//localhost:8090/item/query?page=1&rows=20
	//查询商品信息，返回EasyUIData
	@RequestMapping("/query")
	public EasyUIData findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	//localhost:8090/item/save
	//商品新增
	@RequestMapping("/save")
	public SysResult saveItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.saveItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/update
	//修改
	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc) {
		try {
			itemService.updateItem(item,itemDesc);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/delete
	//ids: 1474391965
	//删除
	@RequestMapping("/delete")
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItem(ids);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/reshelf
	//商品上架
	@RequestMapping("/reshelf")
	public SysResult reshelf(Long[] ids) {
		try {
			int status = 1;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/instock
	//商品下架
	@RequestMapping("/instock")
	public SysResult instock(Long[] ids) {
		try {
			int status = 2;
			itemService.updateStatus(ids,status);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/query/item/desc/1474391970
	//利用restFul
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//localhost:8090/item/param/item/query/1474391970
	@RequestMapping("/param/item/query/{itemId}")
	public SysResult findItemDescParamById(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescParamById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
}
