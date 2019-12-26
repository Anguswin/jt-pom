package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;

public interface ItemService {

	EasyUIData findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, ItemDesc itemDesc);

	void updateItem(Item item, ItemDesc itemDesc);

	ItemDesc findItemDescById(Long itemId);

	ItemDesc findItemDescParamById(Long itemId);

	void deleteItem(Long[] ids);

	void updateStatus(Long[] ids, int status);

}
