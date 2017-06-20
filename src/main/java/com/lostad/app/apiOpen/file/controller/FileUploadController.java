package com.lostad.app.apiOpen.file.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.lostad.app.apiOpen.file.entity.HisFileData;
import com.lostad.app.apiOpen.file.entity.HisFileReport;
import com.lostad.app.apiOpen.file.service.FileUploadService;
import com.lostad.app.common.util.FileGenerater;
import com.lostad.app.common.util.ResponseUtil;
import com.lostad.app.common.util.Validator;
import com.lostad.app.common.vo.JsonRe;
import com.lostad.app.conf.Const;
/**
 * 
 */
@Controller
@RequestMapping(value = "/apiOPen/fileUpload")
public class FileUploadController {
	private static final Logger logger = Logger.getLogger(FileUploadController.class);
    @Autowired
    private FileGenerater fileGenerater;
    @Autowired
    private FileUploadService fileUploadService;
	/**
	 * 上传数据文件
	 * @param 
	 * @return void
	 */
	@RequestMapping(value = "/data" ,method = RequestMethod.POST)
	///public void fileUpload(@RequestParam("file1") MultipartFile[] files,@RequestParam String patientId,HttpServletResponse response) {  
	public void fileUpload(@RequestParam("file1") MultipartFile file1,
			               HttpServletRequest request,
			               HttpServletResponse response,
			               @RequestParam String typeCode,
			               @RequestParam(required=false) String patientId,
			               @RequestParam(required=false) String doctorId) {  
		int success = 0;
		String msg = "上传成功！";
        
		Map<String, Object> obj = null;
         if (!file1.isEmpty()) {
            try {
            	String path = fileGenerater.getPathUpload(Const.PATH_DATA);
            	File dir = new File(path);
        		if (!dir.exists() && !dir.isDirectory()) {
        			dir.mkdir();// 文件不存在，创建文件
        		}
               // 转存文件  
				if (Validator.isNotEmpty(patientId)) {
					try {
						HisFileData data = fileUploadService.saveDataInfo(patientId,doctorId,file1,typeCode);
						obj = new HashMap<String, Object>();
						obj.put("id",data.getId());
						obj.put("fileName", file1.getOriginalFilename());
					} catch (Exception e) {
						e.getMessage();
						logger.error("文件上传失败" + e.getMessage());
						success = 1;
						msg = "文件上传失败" + e.getMessage();
					}
			    }else{
			    	success = 1;
					msg = "未设置病人ID" ;
			    }
           } catch (Exception e) {  
              e.printStackTrace();  
           }  
        }  
 		ResponseUtil.flushJson(response,  new JsonRe(success, msg, obj));
	} 
	
	/**
	 * 报告文件上传
	 * @param 
	 * @return void
	 */
	@RequestMapping(value = "/reportUp" ,method = RequestMethod.POST)
	public void reportUp(@RequestParam("file1") MultipartFile file,
			               @RequestParam Integer dataId,
			               @RequestParam String typeCode,
			               @RequestParam(required=false) String patientId,
			               @RequestParam(required=false) String doctorId,
			               HttpServletRequest request,
			               HttpServletResponse response) {  
		int success = 0;
		String msg = "上传成功！";
		Map<String, Object> obj = null;
         if (!file.isEmpty()) {  
            try {  
               // 转存文件  
				if (Validator.isNotEmpty(patientId)) {
					try {
						HisFileReport data = fileUploadService.saveReportInfo(patientId,doctorId,dataId,file,typeCode);
						obj = new HashMap<String, Object>();
						obj.put("id",data.getId());
						obj.put("fileName", file.getOriginalFilename());
					} catch (Exception e) {
						e.getMessage();
						logger.error("文件上传失败" + e.getMessage());
						success = 1;
						msg = "文件上传失败" + e.getMessage();
					}
			    }else{
			    	success = 1;
					msg = "未设置病人ID" ;
			    }
           } catch (Exception e) {  
              e.printStackTrace();  
           }  
        }  
 		ResponseUtil.flushJson(response,new JsonRe(success, msg, obj));
	} 
	
//	@RequestMapping(value = "/fileUp.do" ,method = RequestMethod.POST)
//	@ResponseBody
//	private void uploadFile(HttpServletRequest request,
//			HttpServletResponse response) throws UnsupportedEncodingException {
//		int success = 0;
//		String msg = "上传成功！";
//		HisFileReport report = null;
//		request.setCharacterEncoding("utf-8");
//		response.setCharacterEncoding("utf-8");
//		// 1、创建一个DiskFileItemFactory工厂
//		DiskFileItemFactory factory = new DiskFileItemFactory();
//		// 2、创建一个文件上传解析器
//		ServletFileUpload upload = new ServletFileUpload(factory);
//		// 解决上传文件名的中文乱码
//		upload.setHeaderEncoding("UTF-8");
//		factory.setSizeThreshold(1024 * 1024 * 10);// 设置内存的临界值为500K
//		File file = new File(UPLOADFILEPATH);// 当超过500K的时候，存到一个临时文件夹中
//		if (!file.exists() && !file.isDirectory()) {
//			file.mkdir();// 文件不存在，创建文件
//		}
//		factory.setRepository(file);
//		upload.setSizeMax(1024 * 1024 * 10);// 设置上传的文件总的大小不能超过100M
//		// 1. 得到 FileItem 的集合 items
//		List<FileItem> items = null;
//		try {
//			items = upload.parseRequest(request);
//		} catch (FileUploadException e) {
//			e.printStackTrace();
//		}
//		String fileName;
//		String filePath;
//		String patientId = null;
//		String api_key = null;
//		for (FileItem item : items) {
//			if (item.isFormField()) {
//				// 表单域
//				try {
//					//String name = item.getName();
//					String name = item.getFieldName();
//					String value = item.getString("utf-8");
//					if (name.toUpperCase().equals("API_KEY")) {
//						api_key = value;
//					}
//					if (name.equals("patientId")) {
//						patientId = value;
//					}
//				} catch (Exception e) {
//					e.getMessage();
//					success = 1;
//					msg = "错误" + e.getMessage();
//					log.error("错误");
//				}
//			} else {
//				// 文件域
//				try {
//					fileName = item.getName();
//					//String fileMD5 = FileMD5Util.getFileMD5((File) item);
//					//String createEncrypPassword = MD5_util.createEncrypPassword(fileMD5 += fileName);
//					//if (createEncrypPassword == api_key) {
//						long sizeInBytes = item.getSize();
//						String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
//						String lowerCase = suffix.toLowerCase();
//						if (lowerCase == "zip" || lowerCase.equals("zip")) {
//							InputStream in = item.getInputStream();
//							byte[] buffer = new byte[1024];
//							int len = 0;
//							filePath = UPLOADFILEPATH
//									+ System.currentTimeMillis() + fileName;
//							OutputStream out = new FileOutputStream(filePath);
//							while ((len = in.read(buffer)) != -1) {
//								out.write(buffer, 0, len);
//							}
//							out.close();
//							in.close();
//							if (patientId.length() != 0 && patientId != "") {
//								try {
//									fileDateUploadService.savePatientMsg(patientId,fileName, filePath, sizeInBytes);
//								} catch (Exception e) {
//									e.getMessage();
//									log.error("文件上传成功，id写入失败" + e.getMessage());
//									success = 1;
//									msg = "文件上传成功，id写入失败" + e.getMessage();
//								}
//							}
//						} else {
//							success = 1;
//							msg = "只允许上传zip格式的文件";
//						}
//					//}
//				} catch (Exception e) {
//					e.getMessage();
//					log.error("写入文件失败");
//					success = 1;
//					msg = "写入文件失败" + e.getMessage();
//				}
//			}
//		}
//		JsonRe re = new JsonRe(success, msg, report);
//		ResponseUtil.flushJson(response, re);
//	}
}