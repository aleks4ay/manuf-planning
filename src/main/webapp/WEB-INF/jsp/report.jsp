<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>

<html>
<head>
    <title>КИЙ-В</title>
    <link href="resources/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="/">Home</a></h3>

<H1>Данные по Технологичке, записей ${numberOfReports} <i> (${dayStart} - ${dayEnd})</i> </H1>

<%--<form:form method="POST" action="/tmc" modelAttribute="testItems">--%>
    <table style="font-size: 14px; text-align: center">
        <tr class="red" style="height: 1.8em">
            <th width="30">№</th>
            <th width="280">Т М С</th>
            <th width="200">Дополнительное описание</th>
            <th width="60">Длина</th>
            <th width="65">Ширина</th>
            <th width="60">Высота</th>
            <th width="220">Заказ (дата)</th>
            <th width="200">Клиент</th>
            <th width="135">Менеджер</th>
            <th width="90">Дата (в&nbsp;произв.)</th>
            <th width="35">Кол-во</th>
            <th width="55">Отгру- жено</th>
            <th>Инвойс (дата)</th>
            <th width="55">Нужно</th>
            <th width="70">Не хватает</th>

        </tr>

        <c:forEach var="element" items="${testItems.itemList}" varStatus="status">

            <tr>
                <td>
                    <%--<input type = "hidden" name = "items[${status.index}].id" value="${element.id}">--%>
                    <label> ${status.index + 1} </label>
                </td>
                <td style="text-align: left;">
                        ${element.tmcDescription} <b>(на&nbsp;складе&nbsp;${element.balance}&nbsp;шт&nbsp;)</b>
                </td>
                <td style="text-align: left;"> ${element.descrSecond} </td>
                <td> ${element.sizeA} </td>
                <td> ${element.sizeB} </td>
                <td> ${element.sizeC} </td>
                <td style="text-align: left;"> ${element.docNumber}, поз.${element.position}, (${element.dateCreate})</td>
                <td style="text-align: left;"> ${element.client} </td>
                <td style="text-align: left;"> ${element.manager} </td>
                <td> ${element.dateToFactory} </td>
                <td> ${element.quantity} </td>
                <td> ${element.quantityShipped} </td>
                <td>
                <c:set var="tempInvoice" value="${element.invoices}"/>
                <c:if test="${!empty tempInvoice}">
                    <c:set var="size" value="${fn:length(tempInvoice)}" />
                    <c:forEach var="innerElement" items="${element.invoices}" varStatus="i">
                        <c:if test="${i.first}"> size: ${size} </c:if>
                        &mdash; ${innerElement.docNomberInvoice}<br/>
                    </c:forEach>
                </c:if>
                </td>
                    <%--<td> ${element.docInvoice} </td>--%>
                <td> ${element.needToShipment} </td>
                <td> ${element.demand} </td>

                    <%--<td><form:checkbox path="itemList[${status.index}].check" value="${element.check}"/></td>--%>
            </tr>
        </c:forEach>
    </table>
<%--</form:form>--%>

</body>
</html>
