<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@page import="com.rapleaf.hank.coordinator.*"%>

<%
Coordinator coord = (Coordinator)getServletContext().getAttribute("coordinator");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hank: Domains</title>
</head>
<body>

<a href="index.jsp">Home</a>
<a href="domains.jsp">Domains</a>
<a href="domain_groups.jsp">Domain Groups</a>
<a href="ring_groups.jsp">Ring Groups</a>

<h1>Domains</h1>

<a href="new_domain.jsp">Create a new domain</a>
<table>
  <tr>
    <td><strong>Name</strong></td>
    <td><strong>Partitioner</strong></td>
    <td><strong>Num Partitions</strong></td>
    <td><strong>Storage Engine</strong></td>
    <td><strong>Version</strong></td>
  </tr>
  <%
  for (DomainConfig domainConfig : coord.getDomainConfigs()) {
    %>
    <tr>
      <td><%= domainConfig.getName() %></td>
      <td><%= domainConfig.getPartitioner().getClass().getSimpleName() %></td>
      <td align=center><%= domainConfig.getNumParts() %></td>
      <td><%= domainConfig.getStorageEngineFactoryClass().getName() %></td>
      <td align=center><%= domainConfig.getVersion() %></td>
    </tr>
    <%
  }
  %>
</table>

</body>
</html>