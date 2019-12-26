package com.jt.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		//查询总数
		Integer total = itemMapper.selectCount(null);
		//分页查询，保存到List中
		/**
		 * 分页之后回传数据
		 * 查询的sql : select * from tb_item limit startIndex(起始位置),rows(查询行数);
		 * 第1页:  20
		 * 	select * from tb_item limit 0,20
		 * 第2页:  
		 * 	select * from tb_item limit 20,20
		 * 第3页:
		 *  select * from tb_item limit 40,20
		 * 第N页:
		 * 	 select * from tb_item 
		 * 			limit (page-1)rows,rows
		 */
		//计算起始位置
		Integer startIndex = (page-1)*rows;
		List<Item> itemList = itemMapper.findItemByPage(startIndex,rows);
		
		return new EasyUIData(total,itemList);
	}

	@Transactional//添加事务控制
	@Override
	public void saveItem(Item item,ItemDesc itemDesc) {
		item.setStatus(1)
			.setCreated(new Date())
			.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//同时入库两张表
		itemDesc.setItemId(item.getId())
				.setCreated(item.getCreated())
				.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Transactional//添加事务控制
	@Override
	public void updateItem(Item item, ItemDesc itemDesc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		//同时更新两张表
		itemDesc.setItemId(item.getId())
				.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Transactional//添加事务控制
	@Override
	public void deleteItem(Long[] ids) {
		//1.手动删除
		//itemMapper.deleteItem(ids);
		//2.利用Mybatis-plus自动删除
		List<Long> itemList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(itemList);
		//2张表一起删除
		itemDescMapper.deleteBatchIds(itemList);
	}
	
	/*
	 * sql: update tb_item 
	 * 		set status=#{status},
	 * 		updated = #{updated} 
	 * 		where id in (100,200,300....)
	 * */
	@Transactional//添加事务控制
	@Override
	public void updateStatus(Long[] ids, int status) {
		Item item = new Item();
		item.setStatus(status)
			.setUpdated(new Date());
		List<Long> longIds = Arrays.asList(ids);
		UpdateWrapper<Item> updateWrapper = new UpdateWrapper<Item>();
		updateWrapper.in("id", longIds);
		itemMapper.update(item, updateWrapper);
		//手动更新status会报错
//		itemMapper.updateStatus(ids,status);
	}
	
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.findItemDescById(itemId);
//		return itemDescMapper.selectById(itemId);
	}

	@Override
	public ItemDesc findItemDescParamById(Long itemId) {
		return itemDescMapper.selectById(itemId);
	}

	
}
