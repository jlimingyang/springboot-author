<%@page import="com.lostad.app.common.util.RequestUtil"%>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@ page contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<html>
<head>
<title>Cluster App Session Test</title>
</head>
<body>
	Server IP:
	<%
	out.println( com.lostad.app.common.util.RequestUtil.getRemoteAddr(request) + "<br>");
	out.println("<br/> SESSIONID: <font color=red>" + session.getId() + "</font><br/>");
	String dataName = request.getParameter("dataName");

	if (dataName != null && dataName.length() > 0) {
		String dataValue = request.getParameter("dataValue");
		session.setAttribute(dataName, dataValue);
	}

	out.print("<br/><b>Session Info:</b><br/>");

	Enumeration e = session.getAttributeNames();

	while (e.hasMoreElements()) {
		String name = (String) e.nextElement();
		String value = session.getAttribute(name).toString();
		out.println(name + " = " + value );
		System.out.println(name + " = " + value);
	}
	out.print("<br/><br/>Form");
%>
	<form action="" method="POST">
		&nbsp; name: <input type=text size=20 name="dataName"> 
		&nbsp; value: <input type=text size=20 name="dataValue">  <input type=submit>
	</form>
	
	<p>
	 JVM��ʼ����Ķ��ڴ���-Xmsָ����Ĭ���������ڴ��1/64��
   <p>JVM������Ķ��ڴ���-Xmxָ����Ĭ���������ڴ��1/4��
   <p>Ĭ�Ͽ�����ڴ�С��40%ʱ��JVM�ͻ������ֱ��-Xmx��������ƣ�
    <p>������ڴ����70%ʱ��JVM����ٶ�ֱ��-Xms����С���ơ�
    <p>��˷�����һ������ -Xms��-Xmx <font color=red> ��� </font>�Ա�����ÿ��GC ������ѵĴ�С��
	
	<p>
	<font color=red>
	<%="JVM��������ڴ�(-xmx):\n "+(Runtime.getRuntime().maxMemory()/1024/1024)
		+"m, <b>�ѷ����ڴ�: "+(Runtime.getRuntime().totalMemory()/1024/1024)
		+"m,  <p><b>�ѷ����ڴ��е�ʣ��ռ�: "+(Runtime.getRuntime().freeMemory()/1024/1024)
		+"m,  <b>�������ڴ�: "+((Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()
				+Runtime.getRuntime().freeMemory())/1024/1024)+"m" %>
	</font>	
	
	<br>
	<br>
	<br>
	<b>
	<a href="/swagger-ui.html#"> �ӿ��ĵ� </a>
</body>
</html>