package com.jt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUITree;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public String findItemCatNameById(Long itemCatId) {
		ItemCat itemCat = itemCatMapper.selectById(itemCatId);
		return itemCat.getName();
	}

	/**
	 * 1.根据parentId查询数据库记录返回itemCatList集合
	 * 2.将itemCatList集合中的数据按照指定的格式封装为List<EasyUITree>
	 */
	@Override
	public List<EasyUITree> findItemCatByParentId(Long parentId) {
		List<ItemCat> catList = findItemCatList(parentId);
		List<EasyUITree> treeList = new ArrayList<>();
		for(ItemCat itemCat : catList) {
			EasyUITree uiTree = new EasyUITree();
			uiTree.setId(itemCat.getId());
			uiTree.setText(itemCat.getName());
			//如果父级is_parent是true则closed， 不是则open
			String state = itemCat.getIsParent()?"closed":"open";
			uiTree.setState(state);
			treeList.add(uiTree);
		}
		return treeList;
	}

	private List<ItemCat> findItemCatList(Long parentId) {
		QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<ItemCat>();
		queryWrapper.eq("parent_id", parentId);
		return itemCatMapper.selectList(queryWrapper);
	}
	
}
