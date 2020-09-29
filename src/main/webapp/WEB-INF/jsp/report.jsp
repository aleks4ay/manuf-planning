<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <title>КИЙ-В</title>
    <%--<link href="/WEB-INF/css/style1.css" rel="stylesheet" type="text/css">--%>
    <style>
        TABLE {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            /*width: 300px; !* Ширина таблицы *!*/
            table-layout: fixed; /*запрет растягивать колонки*/
            width: 95%
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
    </style>
</head>
<body>
<h3><a href="/">Home</a></h3>

<H1>Данные по Технологичке, записей ${numberOfReports} <i> (${dayStart} - ${dayEnd})</i> </H1>

<form:form method="POST" action="/tmc" modelAttribute="testItems">
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
            <%--<th width="55">Инвойс (дата)</th>--%>
            <%--<th width="55">Нужно</th>--%>
            <%--<th width="70">Не хватает</th>--%>

        </tr>

        <c:forEach var="element" items="${testItems.itemList}" varStatus="status">

            <tr class="green">
                <td>
                    <input type = "hidden" name = "items[${status.index}].id" value="${element.id}">
                    <label> ${status.index + 1} </label>
                </td>
                <td style="text-align: left;"> ${element.tmcDescription} </td>
                <td style="text-align: left;"> ${element.descrSecond} </td>
                <td> ${element.sizeA} </td>
                <td> ${element.sizeB} </td>
                <td> ${element.sizeC} </td>
                <td style="text-align: left;"> ${element.docNumber} </td>
                <td style="text-align: left;"> ${element.client} </td>
                <td style="text-align: left;"> ${element.manager} </td>
                <td> ${element.dateToFactory} </td>
                <td> ${element.quantity} </td>
                <%--<td><input type = "text" name = "itemList[${status.index}].docInvoice" value="${element.docInvoice}"></td>--%>
                <%--<td><input type = "text" name = "itemList[${status.index}].dateCreate" value="${element.dateCreate}"></td>--%>
                <%--<td><input type = "text" name = "itemList[${status.index}].needToShipment" value="${element.needToShipment}"></td>--%>
                <%--<td><input type = "text" name = "itemList[${status.index}].needForAll" value="${element.needForAll}"></td>--%>

                    <%--<td><form:checkbox path="itemList[${status.index}].check" value="${element.check}"/></td>--%>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="11">
                <input type="submit" value="Save Changes" />
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
