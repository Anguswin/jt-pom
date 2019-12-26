package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {

	/**
	 * 	当用户上传完成时重定向到上传页面
	 * 思路:
	 * 	1.获取用户文件信息 包含文件名称
	 *  2.指定文件上传路径，如果没有则创建文件夹
	 *  3.实现文件上传
	 *  4.重定向到原页面
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String fileController(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取input标签中的值
		String inputName = fileImage.getName();
		//获取文件名
		String fileName = fileImage.getOriginalFilename().toLowerCase();
		//定义上传文件路径
		File fileDir = new File("G:/CGBv3.0/JT/image/i");
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		//上传文件
		fileImage.transferTo(new File(fileDir+"/"+fileName));
		//重定向到原页面
		return "redirect:/file.jsp";
	}
	
	@Autowired
	private FileService fileService;
	
	//localhost:8090/pic/upload?dir=image
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO fileImage(MultipartFile uploadFile) {
		return fileService.uploadFile(uploadFile);
	}
}
