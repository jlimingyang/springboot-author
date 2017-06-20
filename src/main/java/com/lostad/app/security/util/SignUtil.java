package com.lostad.app.security.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author waters
 * 
 */

public class SignUtil {

    /**
     *对字符串签名
     * @param 
     * @return String
     */
	public static String signMD5(String srcStr,String appId,String secretKey) {
		String dist = null ;//签名
		String ds = appId+srcStr+secretKey;
		//System.out.println("---------------ds未加密"+ds);
		dist = MD5Utils.md5Hex(ds);
		//System.out.println("- ----ds加密"+dist.toUpperCase());
		return dist.toUpperCase();//签名是大写的
	}

	
	//sign对业务数据的签名 
	public static boolean verifySignMD5(String src,String appId,String secretKey,String sign){
		boolean isOk = false;
		try{
			String signNew = signMD5(src,appId,secretKey);
		if(signNew.equals(sign)){
			isOk = true;
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return isOk;
	}
	
	
	/**
	 * 
	 * 获取request中的参数并组合成一个字符串，如：
	 * id=1234&name=小明&age=16
	 * @param 
	 * @return String
	 */
	public static String getParam4Sign(HttpServletRequest request) {
		String pId = request.getParameter("patientId");
		List<String> paramList = new ArrayList<String>();
		//方式一：getParameterMap()，获得请求参数map  
        Map<String,String[]> map= request.getParameterMap();  
        //参数迭代器  
        Iterator<String> iterator = map.keySet().iterator();  
        while(iterator.hasNext()){  
            String k=iterator.next();  
            paramList.add(k+"="+ map.get(k)[0]);
        }  
        Object[] params = paramList.toArray();
        Arrays.sort(params);
        
        StringBuilder sb = new StringBuilder();
        for(Object param:params){
        	sb.append(param);
        }
		return sb.toString();
	}

	public static String sign(HttpServletRequest request, String... strings) {
		List<String> paramList = new ArrayList<String>();
		for (String s : strings) {
			paramList.add(s);
		}
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			paramList.add(parameterName);
		}
		Object[] paramArray = paramList.toArray();
		Arrays.sort(paramArray);
		StringBuffer sb = new StringBuffer(500);
		for (Object obj : paramArray) {
			if (obj != null) {
				sb.append(obj.toString());
				if (request.getParameter(obj.toString()) == null) {
					sb.append(request.getHeader(obj.toString()));
				} else {
					sb.append(request.getParameter(obj.toString()));
				}
			}
		}
		return sb.toString();
	}

	/**
	 *方法的说明
	 * @param 
	 * @return boolean
	 */
	public static boolean verifySignRSA(String json, String pubKey, String sign) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/**
     * 第三方调用，验证签名
     *方法的说明
     * @param 
     * @return boolean
     */
	private boolean isRSASignOk(HttpServletRequest request) throws Exception{
		boolean isOk = false ;//默认不能通过验证
//		String appId = request.getHeader("appId");
//		String sign = request.getHeader("sign");//
//		try{
//			String json = IOUtils.toString(request.getInputStream(),"utf-8");
//			HisThirdParty p = cacheService.getHisThirdParty(Long.parseLong(appId));
//			if(p==null){
//				String ip = IpUtil.getIpAddr(request);
//				throw new ApiException("请联系平台维护人员配置您的公钥！IP:"+ip);
//			}
//			json = IOUtils.toString(request.getInputStream(),"utf-8");
//			if(Validator.isNotEmpty(json)){
//				if(json.startsWith("\"") && json.endsWith("\"")){
//					json = json.substring(1, json.length()-1);
//				}
//				byte[] data = json.getBytes("utf-8");
//				byte[] arrSign = sign.getBytes("utf-8"); 
//				byte[] pub = p.getPubKey().getBytes("utf-8");
//				
//				isOk = RsaUtil.verify(data,arrSign, pub);
//				if(!isOk){
//					String ip = IpUtil.getIpAddr(request);
//					logger.error("签名验证失败!ip:"+ip);
//					throw new ApiException("签名验证失败！");
//				}
//				
//			}else{
//				String ip = IpUtil.getIpAddr(request);
//				logger.error("XXXXXXXXXXXXXXX----> data is null，can not validate signature IP:"+ip);
//				throw new ApiException("未接收到任何json数据！请使用post提交json数据！");
//			
//			}
//			//验证签名
//			
//		}catch(Exception e){
//			e.printStackTrace();
//			String ip = IpUtil.getIpAddr(request);
//			logger.error(ip+"================\n"+e.getMessage());
//			throw new ApiException(e.getMessage());
//		}
		
		return isOk;
	}
	

}