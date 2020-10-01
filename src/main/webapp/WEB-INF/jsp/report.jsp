<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <title>КИЙ-В</title>
    <link href="resources/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<h3><a href="/">Home</a></h3>

<H1>Данные по Технологичке, записей ${numberOfReports} <i>(${beginStringDay} - ${endStringDay}) </i>
</H1>


<form:form method="POST" action="/tmcChange" modelAttribute="period">
    <%--<table style="font-size: 18px; text-align: center">--%>
        <%--<tr>--%>
            <%--<input type="datetime-local" value="${period.beginDay}" name="beginDay" required>--%>
            <%--<td>--%>
                <form:input path="beginDay" />
<%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<input type="datetime-local" value="${period.endDay}" name="endDay" required>--%>
            <%--<td>--%>
                <form:input path="endDay" />
            <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <td colspan="2">
                <input type="submit" value="Save Changes" />
            <%--</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
</form:form>





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

        <c:forEach var="tmc" items="${testItems}" varStatus="status">

            <tr>
                <c:set var="rowSpan" value="${fn:length(tmc.descriptions)}" />
                <td rowspan="${rowSpan}">
                    <%--<input type = "hidden" name = "items[${status.index}].id" value="${element.id}">--%>
                    <label> ${status.index + 1} </label>
                </td>
                <td style="text-align: left;" rowspan="${rowSpan}">
                        ${tmc.tmcDescription} <b>(на&nbsp;складе&nbsp;${tmc.balance}&nbsp;шт&nbsp;)</b>
                </td>
                <td style="text-align: left;" rowspan="${rowSpan}"> ${tmc.descrSecond} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeA} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeB} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeC} </td>

                <c:forEach var="descr" items="${tmc.descriptions}" varStatus="pos" >
                    <c:if test="${! pos.first}">
                        <tr>
                    </c:if>
                    <td style="text-align: left;"> ${descr.docNumber}, поз.${descr.position}, (${descr.dateCreate})</td>
                    <td style="text-align: left;"> ${descr.client} </td>
                    <td style="text-align: left;"> ${descr.manager} </td>
                    <td> ${descr.dateToFactory} </td>
                    <td> ${descr.quantity} </td>
                    <td> ${descr.quantityShipped} </td>
                    <td>
                    <%--<c:set var="tempInvoice" value="${descr.invoices}"/>--%>
                    <c:if test="${!empty descr.invoices}">
                        <c:forEach var="innerInvoice" items="${descr.invoices}" varStatus="i">
                            ${innerInvoice.docNomberInvoice} (${innerInvoice.timeInvoiceString})<br/>
                        </c:forEach>
                    </c:if>
                    </td>
                    <td> ${descr.needToShipment} </td>
                    <c:if test="${pos.first}">
                        <td rowspan="${rowSpan}"> ${tmc.demand} </td>
                    </c:if>
                    </tr>
                </c:forEach>
        </c:forEach>
    </table>
<%--</form:form>--%>

</body>
</html>
