<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <title>КИЙ-В</title>
    <%--<link href="resources/css/style.css" rel="stylesheet" type="text/css">--%>
    <style>
        TABLE {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            table-layout: fixed; /*запрет растягивать колонки*/
            /*width: 1500px; !* Ширина таблицы *!*/
            width: 83%
        }
        TH {
            border: 1px solid #1a851e; /* Параметры рамки */
            text-align: center; /* Выравнивание по центру */
            padding: 4px; /* Поля вокруг текста */
            background: rgba(255, 209, 0, 0.56); /* Цвет фона ячейки */
            height: 40px; /* Высота ячеек */
            vertical-align: middle; /* Выравнивание по нижнему краю */
            padding: 0; /* Убираем поля вокруг текста */
        }
        TD {
            border: 1px solid #1a851e; /* Параметры рамки */
            text-align: center; /* Выравнивание по центру */
            vertical-align: middle; /* Выравнивание по нижнему краю */
            padding: 2px; /* Поля вокруг текста */
            overflow-x: hidden;
            overflow-y: visible;
            word-wrap:break-word; /*Разбиение длинных слов*/
        }
        .date {
            width: 150px;
            font-size: 18px;
            padding: 6px 0 4px 10px;
            border: 1px solid #cecece;
            background: #F6F6f6;
            border-radius: 8px;
        }
        TR:hover {
            background: #def4f6;
        }
    </style>
</head>
<body>

<H1>Данные по Технологичке, записей ${numberOfReports} <i>(${beginStringDay} - ${endStringDay}) </i>
</H1>

<%--<h3><a href="/tmc">Общая таблица технологической продукции</a> </h3>--%>

    <form:form method="POST" action="/tmcChange" modelAttribute="period">
        <div>
            От&nbsp;
            <form:input path="beginDay" cssClass="date"/>
            &nbsp;до&nbsp;
            <form:input path="endDay" cssClass="date"/>
            <input style="display: none; background: #FFf; border: 0px solid #cecece;" type="submit" value="Применить"  />
        </div>

    </form:form>

    <table style="font-size: 14px; text-align: center">
        <tr class="red" style="height: 1.8em">
            <th width="1.88%">№</th>
            <th width="17.5%">Т М С</th>
            <th width="6.25%">Доп. описание</th>
            <th width="3.77%">Длина</th>
            <th width="4%">Ширина</th>
            <th width="3.77%">Высота</th>
            <th width="13.75%">Заказ (дата)</th>
            <th width="12.5%">Клиент</th>
            <th width="8.44%">Менеджер</th>
            <th width="5.63%">Дата (в&nbsp;произв.)</th>
            <th width="2.19%">Кол-во</th>
            <th width="3.44%">Отгру- жено</th>
            <th width="9.06%">Инвойс (дата)</th>
            <th width="3.44%">Нужно</th>
            <th width="4.38%">Не хватает</th>

        </tr>

        <c:forEach var="tmc" items="${testItems}" varStatus="status">

            <tr>
                <c:set var="rowSpan" value="${fn:length(tmc.descriptions)}" />
                <td rowspan="${rowSpan}">
                    <input type = "hidden" name = "tmc[${status.index}]" value="${tmc.id}">
                    <%--<input type = "hidden" name = "tmc[${status.index}]" value="${tmc.id}">--%>
                    <label> ${status.index + 1} </label>
                </td>
                <td style="text-align: left;" rowspan="${rowSpan}">
                    <a href="/tmc/one/${tmc.id}">
                        ${tmc.tmcDescription} <b>(на&nbsp;складе&nbsp;${tmc.balance}&nbsp;шт&nbsp;)</b>
                    </a>
                </td>
                <td style="text-align: left;" rowspan="${rowSpan}"> ${tmc.descrSecond} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeA} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeB} </td>
                <td rowspan="${rowSpan}"> ${tmc.sizeC} </td>

                <c:forEach var="descr" items="${tmc.descriptions}" varStatus="pos" >
                    <c:if test="${! pos.first}">
                        <tr>
                    </c:if>
                    <td style="text-align: left;">
                        ${descr.docNumber}, поз.${descr.position}, (${descr.dateCreate})
                    </td>
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

</body>
</html>
