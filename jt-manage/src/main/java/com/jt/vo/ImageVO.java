package com.jt.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ImageVO {

	private Integer error;
	private String url;
	private Integer width;
	private Integer height;
}
