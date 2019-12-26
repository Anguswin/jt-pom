package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.ImageVO;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
	// 定义虚拟路径
	@Value("${image.urlPath}")
	private String urlPath;
//	private String urlPath = "http://image.jt.com/";
	//定义本地文件路径
	@Value("${image.localPath}")
	private String localDir;

	@Override
	public ImageVO uploadFile(MultipartFile uploadFile) {
		
		ImageVO imageVO = new ImageVO();
		//1.获取文件名
		String fileName = uploadFile.getOriginalFilename().toLowerCase();
		//2.判断是否为图片
		if(!fileName.matches("^.+\\.(jpg|gif|png)$")) {
			imageVO.setError(1);
			return imageVO;
		}
		try {
			//3.判断是否为恶意程序
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			if(width==0||height==0) {
				imageVO.setError(1);
				return imageVO;
			}
			//4.将时间转换为字符串
			String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			//5.定义本地上传文件夹
//			String localDir = "G:/CGBv3.0/JT/image/";
			
			File fileDir = new File(localDir+dateDir);
			if(!fileDir.exists()) {
				fileDir.mkdirs();
			}
			//6.获取文件类型
			String type = fileName.substring(fileName.lastIndexOf("."));
			//7.定义uuid
			String uuid = UUID.randomUUID().toString().replace("-", "");
			//8.拼接最终上传路径
			String realLocalDir = localDir+dateDir+"/"+uuid+type;
			//9.上传文件
			uploadFile.transferTo(new File(realLocalDir));
			//拼接url路径
			String realUrlPath = urlPath+dateDir+"/"+uuid+type;
			//10.返回信息
			imageVO.setError(0).setWidth(width).setHeight(height)
					.setUrl(realUrlPath);
		} catch (IOException e) {
			e.printStackTrace();
			imageVO.setError(1);
			return imageVO;
		}
		return imageVO;
	}

}
