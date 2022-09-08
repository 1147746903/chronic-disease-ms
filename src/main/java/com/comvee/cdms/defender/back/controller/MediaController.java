/**
 * @File name:   MediaController.java  媒体资源接口控制层
 * @Create on:  2018-7-30 15:15:57
 * @Author   :  admin
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
**/


package com.comvee.cdms.defender.back.controller;


import com.comvee.cdms.common.utils.ValidateTool;
import com.comvee.cdms.common.wrapper.PageRequest;
import com.comvee.cdms.common.wrapper.PageResult;
import com.comvee.cdms.common.wrapper.Result;
import com.comvee.cdms.defender.model.MediaModel;
import com.comvee.cdms.defender.service.MediaServiceI;
import com.comvee.cdms.upload.tool.UploadUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/back/media")
public class MediaController {
	@Autowired
	@Qualifier("mediaService")
	private MediaServiceI mediaService;
	
	
	/**
	 * @TODO  根据id获取媒体资源表信息
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @author  admin
	 * @date   2018-7-30
	 * 请求样例  http://localhost:8080/comveeframe/web/media/loadMediaById.do?sid=xxxxx
	 * @return
	 */
	@RequestMapping("/loadMediaById")
	public Result loadMediaById(Long sid)  {
		ValidateTool.checkParameterIsNull("媒体资源主键id(sid)", sid);
		MediaModel mediaModel = this.mediaService.loadMediaById(sid);
		return new Result(mediaModel);
	}
	
	
	/**
	 * @TODO  获取媒体资源分页信息
	 * @param request
	 * @param response
	 * @param page 当前页
	 * @param rows 每页条数
	 * @return
	 * @author  admin
	 * @date   2018-7-30
	 * 请求样例     http://localhost:8089/comveeframe/web/media/loadMedia.do?page=1&rows=10
	 */
	@RequestMapping("/loadMedia")
	public Result loadMedia(PageRequest pager, String param, Integer type) {
		PageResult<MediaModel> mediaModelList = this.mediaService.loadMedia(pager,param,type);
		return new Result(mediaModelList);
	}
	
	/**
	 * @TODO  添加媒体资源记录
	 * @param request
	 * @param response
	 * @param MediaModel 媒体资源 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-30
	 * 请求样例  http://localhost:8089/comveeframe/web/media/addMedia.do?@testparams
	 */
	@RequestMapping("/addMedia")
	public Result addMedia(MediaModel mediaModel, HttpServletRequest request, HttpServletResponse response)  {
		MultipartHttpServletRequest multipartRequest =(MultipartHttpServletRequest)request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile file = fileMap.get("file");
		String name = file.getOriginalFilename();
		String extension = FilenameUtils.getExtension(name);
		String attachUrl = "";
		try {
			attachUrl = UploadUtils.uploadFile(file.getInputStream() ,extension);
			if(mediaModel.getType().intValue() == 3){
				MultipartFile imgFile = fileMap.get("imgFile");
				String imName = imgFile.getOriginalFilename();
				String imgExtension = FilenameUtils.getExtension(imName);
				String imgUrl = UploadUtils.uploadFile(imgFile.getInputStream() ,imgExtension);

				mediaModel.setMemo(imgUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        mediaModel.setUrl(attachUrl);
		this.mediaService.addMedia(mediaModel);
        return new Result("添加成功");
	}

/*	@RequestMapping("/addMedia")
	public Result addMedia(MediaModel mediaModel)  {
		ValidateTool.checkParameterIsNull("媒体资源类型(type)", mediaModel.getType());
		this.mediaService.addMedia(mediaModel);
		return new Result("添加成功");
	}*/

	
	/**
	 * @TODO  修改媒体资源记录
	 * @param request
	 * @param response
	 * @param MediaModel 媒体资源 bean对像
	 * @return
	 * @author  admin
	 * @date   2018-7-30
	 * 请求样例  http://localhost:8089/comveeframe/web/media/modifyMedia.do?@testparams
	 */
	@RequestMapping("/modifyMedia")
	public Result modifyMedia(MediaModel mediaModel) {
		ValidateTool.checkParameterIsNull("媒体资源主键id(sid)", mediaModel.getSid());
		this.mediaService.modifyMedia(mediaModel);
		return new Result("修改成功");
	}
	
	/**
	 * @TODO  删除媒体资源记录
	 * @param request
	 * @param response
	 * @param sid　主键id
	 * @return
	 * @author  admin
	 * @date   2018-7-30
	 * 请求样例  http://localhost:8089/comveeframe/web/media/delMedia.do?sid=xxxxx
	 */
	@RequestMapping("/delMedia")
	public Result delMedia(Long sid) {
		ValidateTool.checkParameterIsNull("媒体资源主键id(sid)", sid);
		this.mediaService.delMedia(sid);
        return new Result("删除成功");
	}
}
